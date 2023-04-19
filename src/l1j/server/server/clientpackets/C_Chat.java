package l1j.server.server.clientpackets;

import java.util.Calendar;
import java.util.TimeZone;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.UserCommands;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

public class C_Chat extends ClientBasePacket {

	private static final String C_CHAT = "[C] C_Chat";
	private static final String[] textFilter = { "세호" };

	public C_Chat(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		try {
			L1PcInstance pc = clientthread.getActiveChar();
			int chatType = readC();
			String chatText = readS();


			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.AREA_OF_SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_POISON_SILENCE)) {
				return;
			}
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) { // chat is banned
				S_ServerMessage sm = new S_ServerMessage(242);
				pc.sendPackets(sm); // You are currently banned from chatting.
				sm = null;
				return;
			}

			if (pc.isDeathMatch() && !pc.isGhost() && !pc.isGm()) {
				S_ServerMessage sm = new S_ServerMessage(912);
				pc.sendPackets(sm); // I am unable to chat.
				sm = null;
				return;
			}

			if (!pc.isGm()) {
				for (String tt : textFilter) {
					int indexof = chatText.indexOf(tt);
					if (indexof != -1) {
						int count = 100;
						while ((indexof = chatText.indexOf(tt)) != -1) {
							if (count-- <= 0)
								break;
							char[] dd = chatText.toCharArray();
							chatText = "";
							for (int i = 0; i < dd.length; i++) {
								if (i >= indexof && i <= (indexof + tt.length() - 1)) {
									chatText = chatText + "  ";
								} else
									chatText = chatText + dd[i];
							}
						}
					}
				}
			}

			switch (chatType) {
			case 0: {
				if (pc.isGhost() && !(pc.isGm() || pc.isMonitor())) {
					return;
				}
				if (chatText.startsWith(".시각")) {
					StringBuilder sb = null;
					sb = new StringBuilder();
					TimeZone kst = TimeZone.getTimeZone("GMT+9");
					Calendar cal = Calendar.getInstance(kst);
					sb.append("[Server Time]" + cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 "
							+ cal.get(Calendar.DATE) + "일 " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)
							+ ":" + cal.get(Calendar.SECOND));
					S_SystemMessage sm = new S_SystemMessage(sb.toString());
					pc.sendPackets(sm, true);
					sb = null;
					return;
				}
				// GM command
				if (chatText.startsWith(".") && (pc.getAccessLevel() == Config.GMCODE)) {
					String cmd = chatText.substring(1);
					//GMCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) {
					String text = chatText.substring(1);
					chatWorld(pc, text, 12);
					if (!pc.isGm()) {
						pc.checkChatInterval();
					}
					return;
				}


				if (chatText.startsWith(".")) {
					String cmd = chatText.substring(1);
					if (cmd == null) {
						return;
					}
					UserCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) { // world chat
					String text = chatText.substring(1);
					chatWorld(pc, text, 12);
					if (!pc.isGm()) {
						pc.checkChatInterval();
					}
					return;
				}

				/** unlock telek? **/

				S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_SAY, 0);
				if (!pc.getExcludingList().contains(pc.getName())) {
					if (pc.getMapId() != 2699) {
						pc.sendPackets(s_chatpacket);
					}
				}
				for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						if (listner.getMapId() == 2699) {
							continue;
						}
						listner.sendPackets(s_chatpacket);
					}
				}
				// doppel treatment
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, chatText, 0), true);
						}
					}
				}
			}
				break;
			case 2: {
				try {
				if (pc.isGhost()) {
					return;
				}

				S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_SAY, 2);
				if (!pc.getExcludingList().contains(pc.getName())) {
					pc.sendPackets(s_chatpacket);
				}

				for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(pc, 50)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(s_chatpacket);
					}
				}

				// doppel treatment
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(mob, 30)) {
								listner.sendPackets(new S_NpcChatPacket(mob, chatText, 2), true);
							}
						}
					}
				}
				} catch (Exception e) {}
			}

				break;
			case 3: {
				chatWorld(pc, chatText, chatType);
			}
				break;
			case 4: {
				if (pc.getClanid() != 0) { // belonging to pledge
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_MESSAGE, 4);
					for (L1PcInstance listner : clan.getOnlineClanMember()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
			}
				break;
			case 11: {
				if (pc.isInParty()) { // party
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_MESSAGE, 11);
					for (L1PcInstance listner : pc.getParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
			}
				break;
			case 12: {
				chatWorld(pc, chatText, chatType);
			}
				break;
			case 13: { // Guardian Knight Chat
				if (pc.getClanid() != 0) { // in pledge
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					int rank = pc.getClanRank();
					if (clan != null && (rank == L1Clan.CLAN_RANK_GUARDIAN || rank == L1Clan.CLAN_RANK_SUBPRINCE
							|| rank == L1Clan.CLAN_RANK_PRINCE)) {
						S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_SAY, 15);
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							int listnerRank = listner.getClanRank();
							if (!listner.getExcludingList().contains(pc.getName()) && (listnerRank == L1Clan.CLAN_RANK_GUARDIAN
									|| rank == L1Clan.CLAN_RANK_SUBPRINCE || listnerRank == L1Clan.CLAN_RANK_PRINCE)) {
								listner.sendPackets(s_chatpacket);
							}
						}
					}
				}
			}
				break;
			case 14: { // chat party
				if (pc.isInChatParty()) { // in a chat party
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_SAY, 14);
					for (L1PcInstance listner : pc.getChatParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
			}
				break;
			case 15: { // alliance chat
				if (pc.getClanid() != 0) { // in pledge
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());

					if (clan != null) {
						Integer allianceids[] = clan.Alliance();
						if (allianceids.length > 0) {
							String TargetClanName = null;
							L1Clan TargegClan = null;

							S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_SAY, 13);
							for (L1PcInstance listner : clan.getOnlineClanMember()) {
								int AllianceClan = listner.getClanid();
								if (pc.getClanid() == AllianceClan) {
									listner.sendPackets(s_chatpacket);
								}
							} // For transmission of own clan

							for (Integer allianceid : allianceids) {
								TargegClan = clan.getAlliance(allianceid);
								if (TargegClan != null) {
									TargetClanName = TargegClan.getClanName();
									if (TargetClanName != null) {
										for (L1PcInstance alliancelistner : TargegClan.getOnlineClanMember()) {
											alliancelistner.sendPackets(s_chatpacket);
										} // For transmission of allied clans
									}
								}
							}
						}
					}
				}
				break;
			}
			case 17:
				if (pc.getClanid() != 0) { // belong to clan
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					if (clan != null && (pc.isCrown() && pc.getId() == clan.getLeaderId())) {
						S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_MESSAGE, 17);
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							if (!listner.getExcludingList().contains(pc.getName())) {
								listner.sendPackets(s_chatpacket);
							}
						}
					}
				}
				break;
			}

			if (!pc.isGm()) {
				pc.checkChatInterval();
			}
			pc = null; // Leak-proof
			chatText = null; // Leak-proof

		} catch (Exception e) {

		} finally {
		  	clear();
		}
	}

	private void chatWorld(L1PcInstance pc, String chatText, int chatType) {
		if (pc.getLevel() >= Config.GLOBAL_CHAT_LEVEL) {
			if (pc.isGm() || L1World.getInstance().isWorldChatElabled()) {
				if (pc.get_food() >= 12) { // got 5%?
					S_PacketBox pb = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
					pc.sendPackets(pb, true);
					if (chatType == 3) {
						S_PacketBox pb2 = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
						pc.sendPackets(pb2, true);
						if (pc.isGm()) {
							L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "\\f=[******] " + chatText));
							L1World.getInstance().broadcastPacketToAll(new S_ChatPacket(pc, "[******] " + chatText, Opcodes.S_MESSAGE, chatType));
							return;
						}
					} else if (chatType == 12) {
						S_PacketBox pb3 = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
						pc.sendPackets(pb3, true);
						if (pc.isGm()) {
							L1World.getInstance().broadcastPacketToAll(new S_ChatPacket(pc, "[******] " + chatText, Opcodes.S_MESSAGE, chatType));
							return;
						}
					}
					for (L1PcInstance listner : L1World.getInstance().getAllPlayers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							if (listner.isShowTradeChat() && chatType == 12) {
								S_ChatPacket cp = new S_ChatPacket(pc, chatText, Opcodes.S_MESSAGE, chatType);
								listner.sendPackets(cp, true);
							} else if (listner.isShowWorldChat() && chatType == 3) {
								S_ChatPacket cp = new S_ChatPacket(pc, chatText, Opcodes.S_MESSAGE, chatType);
								listner.sendPackets(cp, true);
							}
						}
					}
				} else {
					S_ServerMessage sm = new S_ServerMessage(462);
					pc.sendPackets(sm, true);
				}
			} else {
				S_ServerMessage sm = new S_ServerMessage(510);
				pc.sendPackets(sm, true);
			}
		} else {
			S_ServerMessage sm = new S_ServerMessage(195, String.valueOf(Config.GLOBAL_CHAT_LEVEL));
			pc.sendPackets(sm, true);
		}
	}

	@Override
	public String getType() {
		return C_CHAT;
	}
}

