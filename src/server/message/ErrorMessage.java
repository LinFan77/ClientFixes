package server.message;

import java.util.logging.Logger;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;


public class ErrorMessage {
	private static Logger _log = Logger.getLogger(ErrorMessage.class.getName());
	public static String MESSAGE_1 = "Character login success";

	public static void reportMessage(String msg) {
		System.out.println(msg);
		L1World.getInstance().broadcastPacketTo8888(new S_SystemMessage(msg));
	}

	public static void reportPlayerAction(String msg) {
		System.out.println(msg);
		L1World.getInstance().broadcastPacketTo8888(new S_SystemMessage(msg));
		_log.info(msg);
	}

	public static void reportPlayerAction(L1PcInstance player, String ip, String msg) {
		System.out.println(msg);
		L1World.getInstance().broadcastPacketTo8888(new S_SystemMessage(msg));
		_log.info(msg);
	}

	/*public static void reportErrors(L1PcInstance player, String ip, String msg) {
		System.out.println(msg);
		L1World.getInstance().broadcastPacketTo8888(new S_SystemMessage(msg));
		LogTable.logPlayerAction(player, ip, msg);
	}*/

	public static void reportConnection(String msg) {
		System.out.println(msg);
		L1World.getInstance().broadcastPacketTo8888(new S_SystemMessage(msg));
		_log.info(msg);
	}

	public static void reportConnection(String ip, String msg) {
		String output = ip + " " + msg;
		System.out.println(output);
		L1World.getInstance().broadcastPacketTo8888(new S_SystemMessage(output));
		_log.info(output);
	}
}
