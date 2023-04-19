package server.threads.pc;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_PetWindow;
import l1j.server.server.utils.PerformanceTimer;

public class ClanBuffThread implements Runnable {

	private static ClanBuffThread _instance;

	// private static Logger _log =
	// Logger.getLogger(SabuDGTime.class.getName());

	public static ClanBuffThread getInstance() {
		if (_instance == null) {
			_instance = new ClanBuffThread();
		}
		return _instance;
	}

	public ClanBuffThread() {
		// super("server.threads.pc.SabuDGTime");
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[ClanBuffThread] loading...");
		GeneralThreadPool.getInstance().schedule(this, 3000);
		System.out.println("OK! " + timer.get() + "ms");
	}

	@Override
	public void run() {
		try {
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc == null || pc.getNetConnection() == null || (pc instanceof L1RobotInstance)) {
					continue;
				}
				if (pc.getClanid() != 0) {
					L1Clan clan = L1World.getInstance().getClan(
							pc.getClanname());
					if (clan != null) {
						if (clan.getOnlinePrivateShopXMemberCount() >= 3) {
							if (!pc.pledgeBuff && pc.getLevel() < 99) {
								pc.pledgeBuff = true;
								pc.sendPackets(new S_PacketBox(
										S_PacketBox.혈맹버프, 1), true);
							}
						} else {
							if (pc.pledgeBuff) {
								pc.sendPackets(new S_PacketBox(
										S_PacketBox.혈맹버프, 0), true);
								pc.pledgeBuff = false;
							}
						}
					}
				} else if (pc.pledgeBuff) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.혈맹버프, 0), true);
					pc.pledgeBuff = false;
				}
				if (!pc.getMap().isTakePets()) {
					try {
						if (pc.getPetList() != null && pc.getPetListSize() > 0) {
							for (L1NpcInstance petNpc : pc.getPetList()) {
								if (petNpc instanceof L1SummonInstance) {
									((L1SummonInstance) petNpc).Death(null);
								} else if (petNpc instanceof L1PetInstance) {
									L1PetInstance Pet = (L1PetInstance)petNpc;
									L1ItemInstance useItem = pc.getInventory().getItem(Pet.getItemObjId());

									/** 오브젝트 제거 */
									Pet.deletePet();
									/** 패킷 처리 1번 온오프 2번 다이 3번 이름 4번 엘릭서 */
									pc.sendPackets(new S_PetWindow(useItem, Pet, true, false, false, false, false), true);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, 3000);
	}

}

