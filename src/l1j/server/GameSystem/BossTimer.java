package l1j.server.GameSystem;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import l1j.server.Config;
import l1j.server.MJBookQuestSystem.Loader.UserWeekQuestLoader;
import l1j.server.MJBookQuestSystem.Templates.WeekQuestDateCalculator;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.C_ItemUSe;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.utils.L1SpawnUtil;

public class BossTimer implements Runnable {
	private static BossTimer _instance;

	public static BossTimer getInstance() {
		if (_instance == null) {
			_instance = new BossTimer();
		}
		return _instance;
	}

	public boolean 젠사용 = false;

	public boolean noticeUse = false;

	private Date day = new Date(System.currentTimeMillis());

	public BossTimer() {
		// super("l1j.server.GameSystem.BossTimer");
		GeneralThreadPool.getInstance().execute(this);
	}

	@Override
	public void run() {
		try {
			// while (true) {
			day.setTime(System.currentTimeMillis());
			boss();
			fairlyQueen();
			MerchantOneDayBuyReset();
			// Thread.sleep(1000L);
			// }
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	private void MerchantOneDayBuyReset() {
	    if (LocalDateTime.now().getMinute() == 0 && LocalDateTime.now().getSecond() == 0 && LocalDateTime.now().getHour() == 0) {
	        L1MerchantInstance.resetOneDayBuy();
	        C_ItemUSe.reset시공의항아리횟수();
	        C_ItemUSe.reset마빈주머니_계정횟수();
	     // C_Shop.reset상점개설계정횟수();
	    }
	}


	private boolean QueenAMspawn = false;
	private boolean QueenPMspawn = false;

	private void fairlyQueen() {
		long time = GameTimeClock.getInstance().getGameTime().getSeconds() % 86400;
		if ((time > 60 * 60 * 9 && time < 60 * 60 * 10) || (time < -60 * 60 * 9 && time > -60 * 60 * 10)) {
			if (!QueenAMspawn) {
				QueenAMspawn = true;
				// 9~12
				fairlyQueenSpawn();
			}
		} else {
			QueenAMspawn = false;
		}

		if ((time > 60 * 60 * 19 && time < 60 * 60 * 20) || (time < -60 * 60 * 19 && time > -60 * 60 * 20)) {
			if (!QueenPMspawn) {
				QueenPMspawn = true;
				// 19~24
				fairlyQueenSpawn();
			}
		} else {
			QueenPMspawn = false;
		}
	}

	private void fairlyQueenSpawn() {
		Random _rnd = new Random(System.nanoTime());
		int delay = _rnd.nextInt(600000 * 3) + 1;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				Random _rnd = new Random(System.nanoTime());
				int deletetime = (_rnd.nextInt(11) + 10) * 60000;
				L1NpcInstance n = L1SpawnUtil.spawn2(33164, 32284, (short) 4, 70852, 0, deletetime, 0);
				L1MobGroupSpawn.getInstance().doSpawn(n, 107, true, false);
				for (L1NpcInstance npc : n.getMobGroupInfo().getMember()) {
					L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, deletetime);
					timer.begin();
				}
			}

		}, delay);
	}

	private int _4hourCycle = (60000 * 60 * 3) + (60000 * 40);
	Random _random = new Random(System.nanoTime());

	public void boss() {
		try {
			if (젠사용) {
				return;
			}
			if (LocalDateTime.now().getSecond() == 0 && LocalDateTime.now().getMinute() == 0) {
				int rh = LocalDateTime.now().getHour();
				젠사용 = true;
				BossTimerCheck check = new BossTimerCheck(this);
				check.begin();

				if (rh == Config.ATTENDANCERESETTIME) {
					L1SpawnUtil.spawn6(33174, 33001, (short) 4, 203048, 0, _4hourCycle, 0); // dark lord
					WeekQuestDateCalculator.getInstance().reloadTime();
					L1World.getInstance().broadcastServerMessage("\\aDRenewed attendance list and weekly quests (Every day at 9:00am)");
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						UserWeekQuestLoader.load(pc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * ==== First Gen bosses ====
		 * Crocodile, Captain Drake, Mambo Rabbit. Ifrit Drake Mambo Rabbit Black Elder Doppelganger Great Minotaur, Kurtz, Ancient
		 * Giant, phoenix, giant squid, half-headed fish, Contaminated Orc Warrior, Spirit, Kuman. Caspar Family.
		 * Unicorn, Dream Great Spirit, Cursed Great Water Spirit, Cursed Shaman Sael, Water Spirit, Master of the Abyss, Beast Lord
		 * Baranca, Kapu, Giant Worm, Kuman, Necromancer
		 **/
	}

	public class EventNoticeTimer implements Runnable {
		private BossTimer noticeCheck = null;

		public EventNoticeTimer(BossTimer bt) {
			noticeCheck = bt;
		}

		@Override
		public void run() {
			try {
				noticeCheck.noticeUse = false;
				noticeCheck = null;
				// this.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void begin() {
			// Timer timer = new Timer();
			// timer.schedule(this, 3000);
			GeneralThreadPool.getInstance().schedule(this, 3000);
		}
	}

	public class BossTimerCheck implements Runnable {
		private BossTimer genCheck = null;

		public BossTimerCheck(BossTimer bt) {
			genCheck = bt;
		}

		@Override
		public void run() {
			try {
				genCheck.젠사용 = false;
				genCheck = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void begin() {
			// Timer timer = new Timer();
			// timer.schedule(this, 3000);
			GeneralThreadPool.getInstance().schedule(this, 3000);
		}
	}
}
