package server.threads.pc;

import l1j.server.Config;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

public class AutoSaveThread extends Thread {

	private static AutoSaveThread _instance;
	// private static Logger _log = Logger.getLogger(AutoSaveThread.class.getName());
	private final int _saveCharTime;
	private final int _saveInvenTime;

	public static AutoSaveThread getInstance() {
		if (_instance == null) {
			_instance = new AutoSaveThread();
			_instance.start();
		}
		return _instance;
	}

	public AutoSaveThread() {
		super("server.threads.pc.AutoSaveThread");
		_saveCharTime = Config.AUTOSAVE_INTERVAL;
		_saveInvenTime = Config.AUTOSAVE_INTERVAL_INVENTORY;
	}

	@Override
	public void run() {
		while (true) {
			try {
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc instanceof L1RobotInstance) {
						continue;
					}
					if (pc == null || pc.getNetConnection() == null) {
						continue;
					} else {
						// character
						if (_saveCharTime * 959 < System.currentTimeMillis() - pc.getlastSavedTime()) {
							pc.save();
							pc.setlastSavedTime(System.currentTimeMillis());
						}

						// inventory
						if (_saveInvenTime * 983 < System.currentTimeMillis() - pc.getlastSavedTime_inventory()) {
							pc.saveInventory();
							pc.setlastSavedTime_inventory(System.currentTimeMillis());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

