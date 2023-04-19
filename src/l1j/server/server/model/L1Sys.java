package l1j.server.server.model;

import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1Sys implements Runnable {
	private boolean loop = true;

	private static final Logger _log = Logger
			.getLogger(L1DeleteItemOnGround.class.getName());

	private static L1Sys _instance;

	public static L1Sys getInstance() {
		if (_instance == null) {
			_instance = new L1Sys();
		}
		return _instance;
	}



	@Override
	public void run() {
		while (loop) {
			try {
				String sys1 = String.format("%s", Config.SYSMESSAGE_1);
				String sys2 = String.format("%s", Config.SYSMESSAGE_2);
				String sys3 = String.format("%s", Config.SYSMESSAGE_3);
				String sys4 = String.format("%s", Config.SYSMESSAGE_4);
				String sys5 = String.format("%s", Config.SYSMESSAGE_5);
				String sys6 = String.format("%s", Config.SYSMESSAGE_6);
				String sys7 = String.format("%s", Config.SYSMESSAGE_7);
				String sys8 = String.format("%s", Config.SYSMESSAGE_8);
				String sys9 = String.format("%s", Config.SYSMESSAGE_9);
				String sys10 = String.format("%s", Config.SYSMESSAGE_10);
				String sys11 = String.format("%s", Config.SYSMESSAGE_11);
				String sys12 = String.format("%s", Config.SYSMESSAGE_12);

				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new  S_SystemMessage(sys1));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(sys2));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(sys3));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(sys4));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(sys5));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(sys6));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(sys7));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new  S_SystemMessage(sys8));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new  S_SystemMessage(sys9));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new  S_SystemMessage(sys10));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new  S_SystemMessage(sys11));
				Thread.sleep(1000*Config.INFO_MESSAGE_TIME);
				L1World.getInstance().broadcastPacketToAll(new  S_SystemMessage(sys12));
			} catch (Exception exception) {


			}
		}
	}
}
