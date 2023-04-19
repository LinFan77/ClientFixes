package l1j.server.server.model;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.ServerBasePacket;

public class Broadcaster {
	/**
	 * Send packets to players within the character's visual range.
	 *
	 * @param packet A ServerBasePacket object representing the packet to send.
	 */
	public static void broadcastPacket(L1Character cha, ServerBasePacket packet) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(cha)) {
			pc.sendPackets(packet);
		}
	}

	public static void broadcastPacket(L1Character cha, ServerBasePacket packet, boolean clear) {
		try {
			if (cha == null)
				return;
			for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(cha)) {
				pc.sendPackets(packet);
			}
			if (clear) {
				packet.clear();
				packet = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send packets to players within the character's visual range.
	 * However, it is not transmitted within the screen of the target.
	 *
	 * @param packet A ServerBasePacket object representing the packet to send.
	 */
	public static void broadcastPacketExceptTargetSight(L1Character cha,
			ServerBasePacket packet, L1Character target) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayerExceptTargetSight(cha, target)) {
			pc.sendPackets(packet);
		}
	}

	public static void broadcastPacketExceptTargetSight(L1Character cha,
			ServerBasePacket packet, L1Character target, boolean clear) {
		try {
			for (L1PcInstance pc : L1World.getInstance()
					.getVisiblePlayerExceptTargetSight(cha, target)) {
				pc.sendPackets(packet);
			}
			if (clear) {
				packet.clear();
				packet = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send packets to players within 30 blocks of the character.
	 *
	 * @param packet A ServerBasePacket object representing the packet to send.
	 */
	public static void wideBroadcastPacket(L1Character cha, ServerBasePacket packet) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(cha, 30)) {
			pc.sendPackets(packet);
		}
	}

	public static void wideBroadcastPacket(L1Character cha, ServerBasePacket packet, boolean clear) {
		try {
			for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(cha, 30)) {
				pc.sendPackets(packet);
			}
			if (clear) {
				packet.clear();
				packet = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

