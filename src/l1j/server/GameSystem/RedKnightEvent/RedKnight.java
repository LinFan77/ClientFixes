package l1j.server.GameSystem.RedKnightEvent;

import java.util.Random;

import javolution.util.FastTable;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.L1SpawnUtil;

public class RedKnight implements Runnable {

	private static Random rnd = new Random(System.currentTimeMillis());
	private int _mapnum = 0;
	private int step = 0;
	private int READY_TIME = 4;
	private int ROUND_1_STEP = 8;
	private int ROUND_2_STEP = 2;
	private int ROUND_3_STEP = 2;
	private int END_TIME = 13;
	private static final int SPAWN = 0;
	private static final int READY = 1;
	private static final int MEMBER_CHECK = 2;
	private static final int ROUND_1 = 3;
	private static final int ROUND_2 = 4;
	private static final int ROUND_3 = 5;
	private static final int END = 6;
	private static final int TIME_OVER = 7;

	private FastTable<L1NpcInstance> load1 = null;
	private FastTable<L1NpcInstance> load2 = null;
	private FastTable<L1NpcInstance> load3 = null;

	private FastTable<L1NpcInstance> miscMob = null;
	private L1NpcInstance boss = null;

	private L1NpcInstance 데포로쥬 = null;
	private L1NpcInstance redKnights1 = null;
	private L1NpcInstance redKnights2 = null;

	private boolean on = true;

	public RedKnight(int mapid) {
		_mapnum = mapid;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			private int TIMER = 90;

			@Override
			public void run() {
				try {
					if (!on)
						return;
					if (TIMER == 5) {
						GREEN_MSG("Messsenger: Hurry up. Enemy reinforcements are likely to arrive in 5 minutes. If we do not complete the operation before then, we must retreat to the village.");
					} else if (TIMER == 4) {
						GREEN_MSG("Messsenger: Hurry up.. Enemy reinforcements are likely to arrive in 4 minutes.");
					} else if (TIMER == 3) {
						GREEN_MSG("Messsenger: Hurry up.. Enemy reinforcements are likely to arrive in 3 minutes.");
					} else if (TIMER == 2) {
						GREEN_MSG("Messsenger: Hurry up.. Enemy reinforcements are likely to arrive in two minutes.");
					} else if (TIMER == 1) {
						GREEN_MSG("Messsenger: Hurry up.. Enemy reinforcements are likely to arrive in a minute.");
					} else if (TIMER == 0) {
						GREEN_MSG("Messsenger: Enemy reinforcements have arrived right under your nose. I can't delay any longer, so I'll retreat to the village.");
						step = TIME_OVER;
						return;
					}
					TIMER--;
				} catch (Exception e) {
				}
				GeneralThreadPool.getInstance().schedule(this, 60000);
			}
		}, 60000);
	}

	@Override
	public void run() {
		int sleep = 1;
		try {
			switch (step) {
			case SPAWN:
				load1 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 0);
				load2 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 1);
				load3 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 2);
				sleep = 60;
				step++;
				break;
			case READY:
				if (READY_TIME == 4)
					GREEN_MSG("Messenger: We are leaving in four minutes.");
				else
					GREEN_MSG("Messsenger: " + READY_TIME
							+ "We are leaving in minutes. If there are less than 10 participants, the trip will be canceled.");
				sleep = 60;
				READY_TIME--;
				if (READY_TIME <= 0)
					step++;
				break;
			case MEMBER_CHECK:
				int count = 0;
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob == null)
						continue;
					if (ob instanceof L1PcInstance)
						count++;
				}
				if (count < 1) {
					GREEN_MSG("Messsenger: This trip has been canceled due to lack of participants. Please wait for the next outing.");
					Thread.sleep(3000);
					HOME_TELEPORT();
					Object_Delete();
					return;
				} else {
					GREEN_MSG("Messsenger: Prior to the campaign, Depo Roju will come to encourage you.");
					sleep = 5;
				}
				step++;
				break;
			case ROUND_1:
				sleep = 5;
				if (ROUND_1_STEP == 8) {
					redKnights1 = L1SpawnUtil.spawn4(32772, 32814, (short) _mapnum, 4, 100660, 0, 0, 0);
					redKnights2 = L1SpawnUtil.spawn4(32768, 32814, (short) _mapnum, 4, 100660, 0, 0, 0);
					GeneralThreadPool.getInstance().schedule(new NpcMove(redKnights1, 4, 5), 50);
					GeneralThreadPool.getInstance().schedule(new NpcMove(redKnights2, 4, 5), 50);
				} else if (ROUND_1_STEP == 7) {
					SHOUT_MSG(redKnights2, "Red Knights: Please pay attention!! Depo Roju is coming.");
					데포로쥬 = L1SpawnUtil.spawn4(32770, 32814, (short) _mapnum, 4, 100659, 0, 0, 0);
					GeneralThreadPool.getInstance().schedule(new NpcMove(데포로쥬, 4, 7), 50);
				} else if (ROUND_1_STEP == 6) {
					SHOUT_MSG(데포로쥬,
							"Depo Rouge: I commend you for coming to help us, the Red Knights, for your hard work.");
					sleep = 10;
				} else if (ROUND_1_STEP == 5) {
					SHOUT_MSG(데포로쥬,
							"Depo Rouge: As you may have already heard from correspondence and adjutants, this mission is very important.");
					sleep = 10;
				} else if (ROUND_1_STEP == 4) {
					SHOUT_MSG(데포로쥬,
							"Depo Rouge: Soon... we'll break down the roadblocks blocking our way, so go forward and find the clues.");
					sleep = 10;
				} else if (ROUND_1_STEP == 3) {
					SHOUT_MSG(데포로쥬, "Depo Rouge: Trusting you, I will go back and prepare to recapture the castle.");
					GeneralThreadPool.getInstance().schedule(new NpcMove(redKnights1, 0, 5), 2500);
					GeneralThreadPool.getInstance().schedule(new NpcMove(redKnights2, 0, 5), 2500);
					GeneralThreadPool.getInstance().schedule(new NpcMove(데포로쥬, 0, 7), 50);
					sleep = 10;
				} else if (ROUND_1_STEP == 2) {
					deleteBarrier(load1);
					miscMob = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 3);
					GREEN_MSG("Messsenger: The first roadblock is destroyed. Advance everyone!!");
				} else if (ROUND_1_STEP == 1) {
					if (checkMob()) {
						boss = L1SpawnUtil.spawn2(32770, 32923, (short) _mapnum,100653, 3, 0, 0); // Lamias
						GREEN_MSG("Captain: How did you find this place? I'll make you pay for your sins against our Black Knights!!");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_1_STEP == 0) {
					if (bossCheck())
						step++;
				}
				if (ROUND_1_STEP != 0)
					ROUND_1_STEP--;
				break;
			case ROUND_2:
				sleep = 5;
				if (ROUND_2_STEP == 2) {
					deleteBarrier(load2);
					miscMob = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 5);
					GREEN_MSG("Messsenger: The second roadblock has been destroyed. Assault~Forward~~!!");
				} else if (ROUND_2_STEP == 1) {
					if (checkMob()) {
						boss = L1SpawnUtil.spawn2(32771, 33009, (short) _mapnum, 100654, 3, 0, 0); //bar load
						GREEN_MSG("Vice Captain: To have come this far means that we have defeated the previous unit... but they are not easy to see. I'll deal with you!!");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_2_STEP == 0) {
					if (bossCheck())
						step++;
				}
				if (ROUND_2_STEP != 0)
					ROUND_2_STEP--;
				break;
			case ROUND_3:
				sleep = 5;
				if (ROUND_3_STEP == 2) {
					deleteBarrier(load3);
					miscMob = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 7);
					GREEN_MSG("Messsenger: The last stockade has been destroyed. Give me a little more strength~~!!");
				} else if (ROUND_3_STEP == 1) {
					if (checkMob()) {
						boss = L1SpawnUtil.spawn2(32769, 33093, (short) _mapnum, 100655, 3, 0, 0);// Grim Reaper
						GREEN_MSG("Vice-captain: Even if you chew on them, they won't be happy!! I will not leave you alone!!");
					} else {
						GeneralThreadPool.getInstance().schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_3_STEP == 0) {
					if (bossCheck())
						step++;
				}
				if (ROUND_3_STEP != 0)
					ROUND_3_STEP--;
				break;
			case END:
				if (END_TIME <= 0) {
					HOME_TELEPORT();
					Object_Delete();
					return;
				} else if (END_TIME == 13) {
					GREEN_MSG("Messsenger: Depo Roju will be delighted with the successful completion of this expedition.");
					sleep = 3;
				} else if (END_TIME == 12) {
					GREEN_MSG("Messsenger: Please bring the 3 types of clues you have acquired to the 'Chief'.");
					sleep = 3;
				} else if (END_TIME == 11) {
					GREEN_MSG("System Message: You will be forcibly teleported to town in 1 minute.");
					sleep = 50;
				} else {
					GREEN_MSG("System message: " + END_TIME + " end time");
				}
				END_TIME--;
				break;
			case TIME_OVER:
				HOME_TELEPORT();
				Object_Delete();
				return;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, sleep * 1000);
	}

	private boolean bossCheck() {
		if (boss == null || boss._destroyed || boss.isDead())
			return true;
		return false;
	}

	private boolean checkMob() {
		if (miscMob == null || miscMob.size() <= 0)
			return true;
		for (L1NpcInstance npc : miscMob) {
			if (npc == null || npc._destroyed || npc.isDead())
				continue;
			return false;
		}
		return true;
	}

	private void deleteBarrier(FastTable<L1NpcInstance> list) {
		if (list == null || list.size() <= 0)
			return;
		for (L1NpcInstance npc : list) {
			if (npc == null || npc._destroyed)
				return;
			npc.getMap().setPassable(npc.getLocation(), true);
			npc.deleteMe();
		}
	}

	private void GREEN_MSG(String msg) {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null)
				continue;
			if (ob instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) ob;
				pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, msg));
			}
		}
	}

	private void SHOUT_MSG(L1NpcInstance npc, String msg) {
		Broadcaster.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 2), true);
		/*
		 * for(L1Object ob :
		 * L1World.getInstance().getVisibleObjects(_mapnum).values()){ if(ob ==
		 * null) continue; if(ob instanceof L1PcInstance){ L1PcInstance pc =
		 * (L1PcInstance) ob; pc.sendPackets(new S_SystemMessage(msg, true)); }
		 * }
		 */
	}

	private void HOME_TELEPORT() {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null)
				continue;
			if (ob instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) ob;
				L1Teleport.teleport(pc, 33436 + rnd.nextInt(12),
						32795 + rnd.nextInt(14), (short) 4, 5, true);
			}
		}
	}

	private void Object_Delete() {
		on = false;
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				npc.deleteMe();
			}
		}
		for (L1ItemInstance obj : L1World.getInstance().getAllItem()) {
			if (obj.getMapId() != _mapnum)
				continue;
			L1Inventory groundInventory = L1World.getInstance().getInventory(
					obj.getX(), obj.getY(), obj.getMapId());
			groundInventory.removeItem(obj);
		}
	}

	class NpcMove implements Runnable {

		private L1NpcInstance npc = null;
		private int count = 0;
		private int direct = 0;

		public NpcMove(L1NpcInstance _npc, int _direct, int _count) {
			npc = _npc;
			count = _count;
			direct = _direct;
		}

		@Override
		public void run() {
			try {
				if (count <= 0) {
					if (direct == 0)
						npc.deleteMe();
					return;
				}
				count--;
				npc.setDirectionMove(direct);
				GeneralThreadPool.getInstance().schedule(this, 640);
			} catch (Exception e) {
			}
		}
	}
}
