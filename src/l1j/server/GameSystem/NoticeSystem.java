package l1j.server.GameSystem;

import java.util.Calendar;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.model.gametime.BaseTime;
import l1j.server.server.model.gametime.RealTimeClock;
import l1j.server.server.model.gametime.TimeListener;
import l1j.server.server.serverpackets.S_SystemMessage;

public class NoticeSystem implements TimeListener {
	private static NoticeSystem _instance;

	public static void start() {
		if (_instance == null) {
			_instance = new NoticeSystem();
		}
		_instance.some();
		RealTimeClock.getInstance().addListener(_instance);
	}

	private void some() {
	}

	private final int ubMsg = 1;

	class NoticeTimer implements Runnable {
		private int _type = 0;
		// private String _msg = null;
		private S_SystemMessage sm1 = new S_SystemMessage("Welcome to Joy Server");
		private S_SystemMessage sm2 = new S_SystemMessage("Make sure to use Infinite Battles and Forgotten Islands every evening.");

		public NoticeTimer(int type, String MSG) {
			_type = type;
			// _msg = MSG;
		}

		@Override
		public void run() {
			try {
				switch (_type) {
				case 1:
					L1World.getInstance().set_worldChatElabled(false);
					L1World.getInstance().broadcastPacketToAll(sm1);
					Thread.sleep(1000);
					L1World.getInstance().broadcastPacketToAll(sm2);
					Thread.sleep(1000);
					L1World.getInstance().set_worldChatElabled(true);
					break;
				case 2:
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDayChanged(BaseTime time) {
	}

	@Override
	public void onHourChanged(BaseTime time) {
	}

	@Override
	public void onMinuteChanged(BaseTime time) {
		int rm = time.get(Calendar.MINUTE);
		int rh = time.get(Calendar.HOUR_OF_DAY);
		if (rm == 55)
			ubStartMSG(rh);
	}

	@Override
	public void onMonthChanged(BaseTime time) {
	}

	private void ubStartMSG(int hour) {
		String MSG = null;

		switch (hour) {
		case 21:
			MSG = "$1242"; // No more letter paper can be saved.?
			break;
		default:
			return;
		}
		NoticeTimer nt = new NoticeTimer(ubMsg, MSG);
		GeneralThreadPool.getInstance().execute(nt);
	}

}

