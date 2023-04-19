package l1j.server.GameSystem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import l1j.server.server.datatables.EvaSystemTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1EvaSystem;
import l1j.server.server.utils.L1SpawnUtil;

public class CrockSystem extends Thread {

	private static CrockSystem _instance;

	public static CrockSystem getInstance() {
		if (_instance == null) {
			_instance = new CrockSystem();
		}
		return _instance;
	}

	L1EvaSystem eva = EvaSystemTable.getInstance().getSystem(1);
	/**
	 * Crack time setting
	 */

	private boolean isOpen = false;

	/** Boss time has started */
	private boolean isBossTime = false;

	/** Time Rift Thebe Boss Count */
	private static int dieCount = 0;

	/** Number of people in the boss room **/
	public int in_count = 0;

	/** crack coordinates */
	private static final int[][] loc = { { 32737, 32796, 99 },
			{ 32737, 32796, 99 },{ 32737, 32796, 99 },{ 32737, 32796, 99 },
			{ 32737, 32796, 99 },{ 32737, 32796, 99 },{ 32737, 32796, 99 },
			{ 32737, 32796, 99 },{ 32737, 32796, 99 }};
	// 33258 32742 783
	/** List for the first 20 people in the boss room */
	private static ArrayList<L1PcInstance> sList = new ArrayList<>();

	public void reload() {
		EvaSystemTable.getInstance().reload(eva);
	}

	private CrockSystem() {
		super("l1j.server.GameSystem.CrockSystem.CrockSystem");
		if (eva.getOpenContinuation() == 1) {
			isOpen = true; // Thebes open
			ready();
		}
		start();
	}

	private int ckck = 5;

	@Override
	public void run() {
		while (true) {
			try {
				if (ckck-- < 1) {
					if (size() > 0) {
						L1PcInstance[] list = toArrayCrackOfTime();
						for (L1PcInstance mem : list) {
							if (mem != null && mem.getNetConnection() != null) {
								if (mem.getMapId() != 782
										&& mem.getMapId() != 784) {
									del(mem);
								}
							}
						}
						list = null;
					}
					ckck = 5;
				}
				if (System.currentTimeMillis() >= eva.getEvaTime()
						.getTimeInMillis()) {
					openCrock(eva.getEvaTime().getTimeInMillis());
					if (eva.getOpenContinuation() == 0
							&& eva.bossTime < System.currentTimeMillis()) {
						boss();
					}
					if (eva.getOpenContinuation() == 0) {
						bosscheck();
					} else if (eva.getOpenContinuation() == 1) {
						close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					sleep(1000);
				} catch (Exception e) {
				}
			}
		}
	}

	private static final S_ServerMessage sm1469 = new S_ServerMessage(1469);

	private void openCrock(long time) {
		if (!isOpen()) {
			OpenTime = time;
			// System.out.println("Thebes Open");
			setOpen(true);
			ready();
			//L1World.getInstance().broadcastPacketToAll(sm1469);// opened
			in_count = 0;
		}
	}

	private boolean boss_room_in = false;
	private int boss_room_in_check_count = 0;

	private void boss() {
		try {
			if (!isBossTime()) {
				setBossTime(true);
				// System.out.println("Boss Time!!");
			} else {
				if (!boss_room_in) {
					if (boss_room_in_check_count-- <= 0) {
						// System.out.println("보스 타임 방에는 아무도 없음.");
						boss_room_in_check_count = 20;
					}
					if (sList.size() > 0) {
						boss_room_in = true;
						// System.out.println("보스 타임 방에 인원있음 스폰~");
						sleep(2000);
						if (eva.getMoveLocation() == 1) { // 테베
							sendSystemChat("Osiris: Foolish beings.. Leave this place!! Anubis! Horus! wipe them out!!");
							sleep(3000);
							sendSystemChat("Osiris: Death to you...");
							sendSystemChat("Horus: No mercy...");
						} else if (eva.getMoveLocation() == 2) { // Tikal
							sendSystemChat("Kukulkan: How dare you come here!! Zeb Requi!! Wake up!!");
							sleep(3000);
							sendSystemChat("Zeb Requi: oooooooo....uuuuuhhhh....");
							sendSystemChat("Zeb Requi: weeeeeeee...proooofit...");
						}
						bossStart();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bosscheck() {
		try {
			if (isOpen()) {
				if (isBossDie()) {
					// System.out.println("boss die time extension");
					if (eva.getMoveLocation() == 1) { // Thebes
						sendSystemChat("Thebes Osiris: Unbelievable..!!! we lost!");
						sleep(2000);
						sendSystemChat("Thebes Osiris: From now on, I will open Teveras for one day.");
					} else if (eva.getMoveLocation() == 2) { // 티칼
						sendSystemChat("Kukulkan: Unbelievable..!!! we lost!");
						sleep(2000);
						sendSystemChat("Kukulkan: From now on, we will open the Tikal Temple for one day.");
					}

					eva.bosscheck += (long) 60000 * (long) 60 * 19;
					CrockContinuation();
				} else if (eva.bosscheck < System.currentTimeMillis()) {
					if (isBossTime()) {
						if (eva.getMoveLocation() == 1) { // Thebes
							sendSystemChat("Thebes Osiris: You guys failed!!!");
						} else if (eva.getMoveLocation() == 2) { // Tikal
							sendSystemChat("Kukulkan: Remember your reckless courage and foolishness!!!");
						}
					}
					// System.out.println("just... clear");
					setOpen(false);
					setBossTime(false);
					clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() {
		if (isOpen()) {
			if (System.currentTimeMillis() > eva.bosscheck) {
				// System.out.println("End of extended time");
				setOpen(false);
				setBossTime(false);
				clear();
			}
		}
	}

	private void ready() {
		if (eva.getMoveLocation() == 0) {
			eva.setOpenLocation((int) (Math.random() * 8));
			eva.setMoveLocation((int) (Math.random() * 2 + 1));
		}
		int OL = eva.getOpenLocation();
		L1SpawnUtil.spawn2(loc[OL][0], loc[OL][1], (short) loc[OL][2], 4500100, 0, 0, 0); // spawn location
	}

	private void bossStart() {
		// Spawn the boss and time the boss
		// System.out.println("boss spawn!! " + eva.getMoveLocation());
		switch (eva.getMoveLocation()) {
		case 1: // Thebes
			L1SpawnUtil.spawn2(32794, 32825, (short) 782, 400016, 0, (3600 * 1000) * 3, 0);
			L1SpawnUtil.spawn2(32794, 32836, (short) 782, 400017, 0, (3600 * 1000) * 3, 0);
			break;
		case 2: // Tikal
			L1SpawnUtil.spawn2(32753, 32870, (short) 784, 4036016, 0, (3600 * 1000) * 3, 0);
			L1SpawnUtil.spawn2(32751, 32859, (short) 784, 4036017, 0, (3600 * 1000) * 3, 0);
			break;
		default:
			break;
		}
	}

	private static final S_ServerMessage sm1468 = new S_ServerMessage(1468);
	private static final S_ServerMessage sm1467 = new S_ServerMessage(1467);

	private void clear() {
		try {
			// Initialize all state and prepare for next open
			eva.setOpenLocation(0);
			eva.setMoveLocation(0);
			eva.setOpenContinuation(0);
			boss_room_in = false;
			// long longtime = OpenTime;
			Calendar cal = (Calendar) Calendar.getInstance().clone();
			cal.setTimeInMillis(eva.getEvaTime().getTimeInMillis()
					+ (long) 60000 * (long) 60 * 24 * 2);
			eva.setEvaTime(cal);
			eva.bossTime = eva.getEvaTime().getTimeInMillis()
					+ (long) 60000 * (long) 60 * 2
					+ ((long) 60000 * (long) 30);
			eva.bosscheck = eva.getEvaTime().getTimeInMillis()
					+ (long) 60000 * (long) 60 * 4;
			EvaSystemTable.getInstance().updateSystem(eva);

			L1World.getInstance().broadcastPacketToAll(sm1467);// The rift of time closes soon.

			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getInventory().checkItem(L1ItemId.TEBEOSIRIS_KEY, 1))
					pc.getInventory().consumeItem(L1ItemId.TEBEOSIRIS_KEY, 1);
			}
			teleportMsg();
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc == null)
					continue;
				if (pc.getInventory().checkItem(L1ItemId.TEBEOSIRIS_KEY, 1))
					pc.getInventory().consumeItem(L1ItemId.TEBEOSIRIS_KEY, 1);
				if (pc.getMap().getId() >= 780 && pc.getMap().getId() <= 784) {
					L1Teleport.teleport(pc, 33970, 33246, (short) 4, 4, true);
				}
			}
			dieCount = 0;
			if (sList.size() > 0)
				sList.clear();
			L1World.getInstance().broadcastPacketToAll(sm1468);// Time Rifts Disappear
			crockDelete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crockDelete() {
		L1FieldObjectInstance f = null;
		for (L1Object l1object : L1World.getInstance().getObject()) {
			if (l1object != null && l1object instanceof L1FieldObjectInstance) {
				f = (L1FieldObjectInstance) l1object;
				if (f.getNpcTemplate().get_npcId() == 4500100
						&& l1object != null)
					f.deleteMe();
			}
		}
	}

	private void teleportMsg() {
		try {
			sleep(2000);
			sendSystemChat("[System] You will teleport in 30 seconds..");
			sleep(10000);
			sendSystemChat("[System] You will teleport in 20 seconds.");
			sleep(10000);
			sendSystemChat("[System] You will teleport in 10 seconds.");
			sleep(5000);
			sendSystemChat("[System] You will teleport in 5 seconds.");
			sleep(1000);
			sendSystemChat("[System] You will teleport in 4 seconds.");
			sleep(1000);
			sendSystemChat("[System] You will teleport in 3 seconds.");
			sleep(1000);
			sendSystemChat("[System] You will teleport in 2 seconds.");
			sleep(1000);
			sendSystemChat("[System] You will teleport in 1 second.");
			sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Both bosses are caught, give gifts and set up extensions
	 */
	private static final S_ServerMessage sm1470 = new S_ServerMessage(1470);

	public void CrockContinuation() {
		try {
			if (eva.getMoveLocation() == 2)
				BossDieBuff(); // give a buff
			eva.setOpenContinuation(1); // change extension status
			EvaSystemTable.getInstance().updateExtend(1);
			L1World.getInstance().broadcastPacketToAll(sm1470); // Time Rifts Disappear
			teleportMsg();

			L1PcInstance[] list5 = toArrayCrackOfTime();
			if (list5.length > 0) {
				for (L1PcInstance pc : list5) {
					if (pc == null)
						continue;
					switch (pc.getMapId()) {
					case 782:
						L1Teleport.teleport(pc, 32628, 32906, (short) 780, 5, true);
						break;
					case 784:
						L1Teleport.teleport(pc, 32793, 32754, (short) 783, 2, false);
						break;
					default:
						break;
					}
				}
			}
			list5 = null;
			synchronized (sList) {
				if (sList.size() > 0)
					sList.clear();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Time Rift Boss Attack Check
	 *
	 * @return (boolean) true if 2 bosses died, false if less than 1 boss was killed
	 */
	private boolean isBossDie() {
		boolean sTemp = false;
		switch (dieCount()) {
		case 2:
			sTemp = true;
			break;
		default:
			sTemp = false;
			break;
		}
		return sTemp;
	}

	/**
	 * Time Rift Thebe Boss Die Return
	 *
	 * @return (int) dieCount boss die counter
	 */
	public int dieCount() {
		return dieCount;
	}

	public void dieCount(int dieCount) {
		CrockSystem.dieCount = dieCount;
	}

	/**
	 * Time Rift Movement State
	 *
	 * @return (boolean) whether to move
	 */
	public boolean isOpen() {
		return isOpen;
	}

	private void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * Whether Thebes Tikal Is Boss Time
	 *
	 * @return
	 */
	public boolean isBossTime() {
		return isBossTime;
	}

	private void setBossTime(boolean isBossTime) {
		this.isBossTime = isBossTime;
	}

	public boolean isContinuationTime() {
		if (eva.getOpenContinuation() == 0)
			return false;
		else
			return true;
	}

	/**
	 * Returns loc for the specified npcId
	 *
	 * @return (int[]) loc coordinate array
	 */
	public int[] loc() {
		return loc[eva.getOpenLocation()];
	}

	/**
	 * First 20 people to register
	 */
	public void add(L1PcInstance c) {
		/** not registered */
		synchronized (sList) {
			if (!sList.contains(c)) {
				/** register 20, first come first server **/
				if (sList.size() < 20)
					sList.add(c);
			}
		}
	}

	public void del(L1PcInstance c) {
		synchronized (sList) {
			/** not registered */
			if (sList.contains(c)) {
				/** If less than 20 people on first come first served */
				sList.remove(c);
			}
		}
	}

	public void sendSystemChat(String msg) {
		L1PcInstance[] list = toArrayCrackOfTime();
		if (list.length > 0) {
			S_SystemMessage smMsg = new S_SystemMessage(msg);
			for (L1PcInstance pc : toArrayCrackOfTime()) {
				if (pc == null || pc.getNetConnection() == null)
					continue;
				if (pc.getMapId() != 782 && pc.getMapId() != 784)
					del(pc);
				else
					pc.sendPackets(smMsg);
			}
		}
	}

	public static L1PcInstance[] toArrayCrackOfTime() {
		L1PcInstance[] list = null;
		synchronized (sList) {
			list = sList.toArray(new L1PcInstance[sList.size()]);
		}
		return list;
	}

	/**
	 * First come first served list
	 *
	 * @return (int) List size
	 */
	public int size() {
		synchronized (sList) {
			return sList.size();
		}
	}

	/**
	 * Now that the Tikal boss has been captured, give buffs to all of World PC.
	 */
	public void BossDieBuff() {
		L1PcInstance[] list = toArrayCrackOfTime();
		for (L1PcInstance pc : list) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_TIKAL_BOSSJOIN))
				pc.getSkillEffectTimerSet().removeSkillEffect(
						L1SkillId.STATUS_TIKAL_BOSSJOIN);
		}
		list = null;
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc == null || pc.getNetConnection() == null || pc.isPrivateShop()) {
				continue;
			}
			L1SkillUse su = new L1SkillUse();
			su.handleCommands(pc, L1SkillId.STATUS_TIKAL_BOSSDIE, pc.getId(),
					pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_SPELLSC);
			su = null;
		}
	}

	/**
	 * The boss is caught and returned if it is in extended state
	 *
	 * @return true : extended
	 */
	public boolean isCrockIng() {
		if (eva.getOpenContinuation() == 1)
			return true;
		else
			return false;
	}

	/** visual data format */
	private static final SimpleDateFormat ss = new SimpleDateFormat("MM-dd HH:mm", Locale.CANADA);
	private Timestamp ts = new Timestamp(System.currentTimeMillis());
	private long OpenTime = 0; // open hours

	public String OpenTime() {
		String result = "unavailable";
		if (OpenTime == 0) {
			return result;
		} else {
			ts.setTime(OpenTime);
			return ss.format(ts);
		}
	}
}

