package l1j.server.GameSystem.INN;

import java.util.Timer;
import java.util.TimerTask;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SabuTell;

public class InnTimer extends TimerTask {
	private int _keyMapNumber;
	private int _numberOfKeys;
	private int _timeMillis;
private boolean exit = false;
	public InnTimer(int keyMapNumber, int numberOfKeys, int timeMillis) {
		_keyMapNumber = keyMapNumber;
		_numberOfKeys = numberOfKeys;
		_timeMillis = timeMillis;
	}

	public synchronized void heightDifference(int count) {
		try {
			int check = _numberOfKeys - count;
			if (check <= 0) {
				run();
			} else {
				_numberOfKeys = check;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run() {
		try {
			if(exit)return;
			exit = true;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getMapId() == _keyMapNumber) {
					pc.setTelType(5);
					S_SabuTell st = new S_SabuTell(pc);
					pc.sendPackets(st, true);
				}
			}
			INN.setINN(_keyMapNumber, false);
			INN.setInnTimer(_keyMapNumber, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void begin() {
		Timer timer = new Timer();
		timer.schedule(this, _timeMillis);
	}
}
