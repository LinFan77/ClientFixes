package l1j.server.GameSystem.Boss;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.GameSystem.Boss.NewBossSpawnTable.BossTemp;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.IdFactory;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.templates.L1Npc;


public class BossTimeController implements Runnable {
	private static Logger _log = Logger.getLogger(BossTimeController.class.getName());

	private static BossTimeController _instance;

	private Random rnd = new Random(System.nanoTime());
	private int _time = 0;
	private int _timeM = 0;
	private Date day = new Date(System.currentTimeMillis());

	public static BossTimeController getInstance() {
		if (_instance == null)
			_instance = new BossTimeController();
		return _instance;
	}

	ArrayList<BossTemp> bosslist = null;

	private boolean isNow = false;

	public BossTimeController() {
		GeneralThreadPool.getInstance().execute(this);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10000);
				BossCheck();
			} catch (Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}

	private void BossCheck() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);

		bosslist = NewBossSpawnTable.getInstance().getlist();
		for (BossTemp temp : bosslist) {
			boolean isDay = false;
			for (int i : temp.Day) {
				if (i == day) {
					isDay = true;
					break;
				}
			}
			if (!isDay) {
				continue;
			}

			if(temp.isSpawn){
				boolean ck = false;
				for (int Minute_temp : temp.SpawnMinute) {
					if(minute == Minute_temp){
						ck = true;
					}
				}
				if(ck)continue;
				else temp.isSpawn = false;
			}

			for (int i = 0; i < temp.SpawnHour.length; i++) {
				if(hour == temp.SpawnHour[i] && minute == temp.SpawnMinute[i]){
					temp.isSpawn = true;
					GeneralThreadPool.getInstance().execute(new BossThread(temp));
				}
			}
		}
	}


	/**Boss spawn handling */
	class BossThread implements Runnable {
		BossTemp temp;
		public BossThread(BossTemp _temp){
			temp = _temp;
		}

		@Override
		public void run(){
			try {
				int rndtime = rnd.nextInt(temp.rndTime) + 1;
				if (rndtime > 0) {
					Thread.sleep(rndtime * 60 * 1000);
				}
				StoreBoss(temp.npcid, temp.SpawnLoc, temp.rndLoc, temp.Groupid, temp.isYn, temp.isMent, temp.Ment, temp.RMent,temp.DeleteTime);
			} catch (Exception e){}
		}
	}



	public void StoreBoss(int npcid, int[] Loc, int rndXY, int groupid, boolean isYN, boolean isMent, String ment, String Rment, int deleteTime) {
		try {
			L1Npc template = NpcTable.getInstance().getTemplate(npcid);
			if (template == null) {
				_log.warning("Boss mob data for id: " + npcid + " missing in npc table");
				System.out.println("Boss spawn controller boss npcid " + npcid + " does not exist.");
				return;
			}
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcid);

			npc.setId(IdFactory.getInstance().nextId());

			L1Location loc = new L1Location(Loc[0], Loc[1], Loc[2]);
			 if (rndXY != 0) {
				loc.randomLocation(rndXY, false);
			}

			/** Updating NPC location information */
			npc.setLocation(loc);
			npc.getLocation().forward(5);

			/** Part of adding a specific NPC object
			* When appearing, add action 4 and spray all users within range
			* Appear Action True False Chat Comment Play */
			boolean Appear = false; String Chat = null;
			switch (npcid){
				case 910021: Appear = true; Chat = "\\fY$27422"; break; // ZQ
				case 910028: Appear = true; Chat = "\\fY$27432"; break; // seer
				case 910036: Appear = true; Chat = "\\fY$27442"; break; // Vampire
				case 910042: Appear = true; Chat = "\\fY$27452"; break; // ZLord
				case 910050: Appear = true; Chat = "\\fY$27462"; break; // cougar
				case 910056: Appear = true; Chat = "\\fY$27472"; break;
				case 910062: Appear = true; Chat = "\\fY$27482"; break;
				case 910069: Appear = true; Chat = "\\fY$27649"; break;
				case 910075: Appear = true; Chat = "\\fY$27659"; break;
				case 910014: Appear = true; Chat = "\\fY$27669"; break;
				//case 7310078: Appear = true; Chat = "\\fY$27679"; break;
				//case 7310079: Appear = true; Chat = "\\fY$27689"; break;
				case 910088: Appear = true; Chat = "\\fY$27699"; break;
			}

			/** Changed so that only actions with comments are handled by appearing actions */
			for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(npc)) {
				npc.onPerceive(pc);
				pc.sendPackets(new S_NPCPack(npc), true);
				if(Appear) pc.sendPackets(new S_DoActionGFX(npc.getId(), ActionCodes.ACTION_Appear), true);
				if(Chat != null) pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, Chat), true);
			}

			/** Change each rotation information for each alarm rotation */
			switch(npcid){
				case 100338:
					/** Rotation controller check statement from current time to when */
					long Time = System.currentTimeMillis();
					/** hold for 30 minutes */
					long EndTime = System.currentTimeMillis() + (1000 * 1800);
					RotationNoticeTable.getInstance().UpdateNotice(1, new Timestamp(Time), new Timestamp(EndTime));
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc instanceof L1RobotInstance) continue;
						pc.알림서비스(pc, true);
					}
					break;

				case 100420:{
					long Time2 = System.currentTimeMillis();
					/** hold for 30 minutes */
					long EndTime2 = System.currentTimeMillis() + (1000 * 1800);
					RotationNoticeTable.getInstance().UpdateNotice(2, new Timestamp(Time2), new Timestamp(EndTime2));
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc instanceof L1RobotInstance) continue;
						pc.알림서비스(pc, true);
					}
					break;
				}
				case 45752:
					long Time3 = System.currentTimeMillis();
					/** hold for 30 minutes */
					long EndTime3 = System.currentTimeMillis() + (1000 * 1800);
					RotationNoticeTable.getInstance().UpdateNotice(11, new Timestamp(Time3), new Timestamp(EndTime3));
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc instanceof L1RobotInstance) continue;
						pc.알림서비스(pc, true);
					}
					break;
			}

			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			if (groupid > 0)
				L1MobGroupSpawn.getInstance().doSpawn(npc, groupid, true, false);

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);

			BossAlive.getInstance().BossSpawn(loc.getMapId());

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE);

			if (isMent) {
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "\\aH[Boss]: "+ ment.toString()));
				L1World.getInstance().broadcastServerMessage("\\aH[Boss]: "+ ment.toString());
				L1World.getInstance().broadcastServerMessage("\\aJ[Top Rewards]: "+ Rment.toString());
			}
			if (isYN) {

			}

			if (0 < deleteTime) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, deleteTime * 1000);
				timer.begin();
			} else {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, 3600 * 1000);
				timer.begin();
			}

			//LinAllManager.getInstance().BossAppend(npc.getName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
