/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package l1j.server.server.clientpackets;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.Antharas.AntharasRaid;
import l1j.server.GameSystem.Antharas.AntharasRaidSystem;
import l1j.server.GameSystem.Antharas.AntharasRaidTimer;
import l1j.server.GameSystem.Fafurion.FafurionRaid;
import l1j.server.GameSystem.Fafurion.FafurionRaidSystem;
import l1j.server.GameSystem.Fafurion.FafurionTimer;
import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.GameSystem.Lindvior.LindviorRaid;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.MJInstanceSystem.MJInstanceEnums.InstStatus;
import l1j.server.MJInstanceSystem.MJLFC.Creator.MJLFCCreator;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.BoardTable;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.HouseTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ChatParty;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Clan.ClanMember;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Party;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Question;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BuffNpcInstance;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_QuestTalkIsland;
import l1j.server.server.serverpackets.S_Resurrection;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_Trade;
import l1j.server.server.serverpackets.S_bonusstats;
import l1j.server.server.serverpackets.S_문장주시;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.templates.L1House;
import l1j.server.server.templates.L1QuestInfo;
import l1j.server.server.types.Point;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;
import server.message.ServerMessage;

public class C_Attr extends ClientBasePacket {

	private static final Logger _log = Logger.getLogger(C_Attr.class.getName());
	private static final String C_ATTR = "[C] C_Attr";

	public static final int MSGCODE_622_LFC		= 2;
	public static final int MSGCODE_NO 			= 0;
	public static final int MSGCODE_YES 		= 1;

	private static final int HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final int HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	public C_Attr(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		try {
			int i = readH();
			int attrcode;
			int c;
			int msgIdx = 0;
			String name;

			if (i == 479) {
				attrcode = i;
			} else {
				msgIdx 		= readD();
				attrcode 	= readH();
				i			= attrcode;
			}
			L1PcInstance pc = clientthread.getActiveChar();
			if (pc == null)
				return;

			if (attrcode == 622) {
				/** 2016.11.26 MJ App Center LFC **/
				if (msgIdx == MSGCODE_622_LFC) {
					c = readC();
					if (c == MSGCODE_NO){
						MJLFCCreator.setInstStatus(pc, InstStatus.INST_USERSTATUS_NONE);
					} else if (c == MSGCODE_YES) {
						if (pc.getInstStatus() == InstStatus.INST_USERSTATUS_LFCREADY)
							MJLFCCreator.setInstStatus(pc, InstStatus.INST_USERSTATUS_LFCINREADY);
					}
					return;
				}
			}

			if (pc.system != -1)
				i = 622;
			switch (i) {
			case 180: // Transform with Shape Change
				c = readC();
				String polys = readS();
				// L1PolyMorph poly =
				// PolyTable.getInstance().getTemplate(polys);

				if (polys.equalsIgnoreCase("ranking class polymorph"))
				{
						if (pc.isCrown()) {
							if (pc.get_sex() == 0)
								polys = "rangking prince male";
							else
								polys = "rangking prince female";
						} else if (pc.isKnight()) {
							if (pc.get_sex() == 0)
								polys = "rangking knight male";
							else
								polys = "rangking knight female";
						} else if (pc.isElf()) {
							if (pc.get_sex() == 0)
								polys = "rangking elf male";
							else
								polys = "rangking elf female";
						} else if (pc.isWizard()) {
							if (pc.get_sex() == 0)
								polys = "rangking wizard male";
							else
								polys = "rangking wizard female";
						} else if (pc.isDarkelf()) {
							if (pc.get_sex() == 0)
								polys = "rangking darkelf male";
							else
								polys = "rangking darkelf female";
						} else if (pc.isDragonknight()) {
							if (pc.get_sex() == 0)
								polys = "rangking dragonknight male";
							else
								polys = "rangking dragonknight female";
						} else if (pc.isIllusionist()) {
							if (pc.get_sex() == 0)
								polys = "rangking illusionist male";
							else
								polys = "rangking illusionist female";
						} else if (pc.isWarrior()) {
							if (pc.get_sex() == 0)
								polys = "rangking warrior male";
							else
								polys = "rangking warrior female";
						} else if (pc.isFencer()) {
							if (pc.get_sex() == 0)
								polys = "rangking fencer male";
							else
								polys = "rangking fencer female";
						}
				}

				if (polys.startsWith("rangking ")) {
					int star = UserRankingController.getInstance().getStarCount(pc.getName());
					if (star <= 8) {
						if (!UserRankingController.getInstance().getStarCount(pc.getType(), pc.getName())) {
						return;
						}
					}
				}

				L1PolyMorph.handleCommands(pc, polys);
				pc.cancelAbsoluteBarrier();
				break;
			case 3348: // dim memory orb
				L1Clan target_clan = L1World.getInstance().getClan(pc.getClanid());
				L1Clan use_clan = L1World.getInstance().getClan(pc.get주시아이디());
				L1PcInstance target_clanMaster = L1World.getInstance().getPlayer(target_clan.getLeaderName());
				L1PcInstance use_clanMaster = L1World.getInstance().getPlayer(use_clan.getLeaderName());

				c = readC();
				if (c == 0) { // refuse
					target_clanMaster.sendPackets(new S_SystemMessage("Clan Watch: Declined the request for Clan Watch."));
					use_clanMaster.sendPackets(new S_SystemMessage("Clan Watch: The application for clan watch was rejected."));
					use_clanMaster.set주시아이디(0);
					target_clanMaster.set주시아이디(0);
				} else if (c == 1) {
					target_clan.addMarkSeeList(use_clan.getClanName());
					use_clan.addMarkSeeList(target_clan.getClanName());
					ClanTable.getInstance().insertObserverClan(target_clan.getClanId(), use_clan.getClanName());
					ClanTable.getInstance().insertObserverClan(use_clan.getClanId(), target_clan.getClanName());
					for (L1PcInstance tp : target_clan.getOnlineClanMember()) {
						tp.sendPackets(new S_문장주시(pc.getClan(), 2), true);
						tp.sendPackets(new S_문장주시(pc.getClan(), 0), true);
						tp.sendPackets(new S_ServerMessage(3360, use_clan.getClanName()), true);
					}
					for (L1PcInstance tp : use_clan.getOnlineClanMember()) {
						tp.sendPackets(new S_문장주시(pc.getClan(), 2), true);
						tp.sendPackets(new S_문장주시(pc.getClan(), 0), true);
						tp.sendPackets(new S_ServerMessage(3360, target_clan.getClanName()), true);
					}
					use_clanMaster.set주시아이디(0);
					target_clanMaster.set주시아이디(0);
				}

			case 2936:// forgotten memory orb
				c = readC();
				if (c == 0) {
				} else if (c == 1) {
					int size = pc.getBookMarkSize();
					L1ItemInstance item = pc.getInventory().getItem(pc.orbOfMemory);
					if (item.getItemId() == 60084) {
						int itemsize = L1BookMark.ItemBookmarkChehck(pc.orbOfMemory);
						if (size + itemsize > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, "" + (size - pc.getBookmarkMax() + itemsize)), true);
							pc.orbOfMemory = 0;
							return;
						}
						if (L1BookMark.ItemBookmarkLoad(pc, pc.orbOfMemory))
							pc.getInventory().removeItem(item, 1);
					} else {
						if (size + 29 > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, "" + (size - pc.getBookmarkMax() + 30)), true);
							pc.orbOfMemory = 0;
							return;
						}
						if (L1BookMark.itemMemory(pc, 29))
							pc.getInventory().removeItem(item, 1);
					}
				}
				pc.orbOfMemory = 0;
				break;
			case 3016: // mysterious memory orb
				c = readC();
				if (c == 0) {
				} else if (c == 1) {
					int size2 = pc.getBookMarkSize();
					if (size2 + 8 > pc.getBookmarkMax()) {
						pc.sendPackets(new S_ServerMessage(2961, "" + (size2 - pc.getBookmarkMax() + 8)), true);
						return;
					}
					L1ItemInstance item2 = pc.getInventory().getItem(pc.orbOfMemory);
					if (L1BookMark.itemMemory(pc, 8))
						pc.getInventory().removeItem(item2, 1);
				}
				pc.orbOfMemory = 0;
				break;
			/*
			 * case 2935: //케플리샤 기억저장구슬 c = readC(); if (c == 0){ }else if (c ==
			 * 1){ int size3 = pc.getBookMarkSize(); if (size3 <= 0) {
			 * pc.sendPackets(new S_ServerMessage(2963, ""), true); return; }
			 * L1ItemInstance item3 = pc.getInventory().getItem(pc.구슬아이템);
			 * pc.getInventory().removeItem(item3, 1);
			 *
			 * L1ItemInstance item4 = ItemTable.getInstance().createItem(60084);
			 * item4.setCount(1); item4.setIdentified(true);
			 * item4.setCreaterName(pc.getName());
			 * pc.getInventory().storeItem(item4); //L1ItemInstance item4 =
			 * pc.getInventory().storeItem(60084, 1);
			 * L1BookMark.ItemaddBookmark(pc, item4.getId()); } pc.구슬아이템 = 0;
			 * break;
			 */
			// case 3055: // 찾을 상인 이름
			// pc.상인찾기 = false;
			// break;
			case 1565: // Do you want to register your key?
				c = readC();
				if (c == 0) {
				} else if (c == 1) {
					if (checkDragonKey(pc)) {
						L1ItemInstance dragonkey = pc.getInventory().getItem(pc.드키등록체크id);
						// L1ItemInstance dragonkey =
						// pc.getInventory().findItemId(L1ItemId.DRAGON_KEY);
						BoardTable.getInstance().writeDragonKey(pc, dragonkey, currentTime(), 4212014);
						pc.sendPackets(new S_ServerMessage(1567), true);
						L1World.getInstance().broadcastServerMessage(
										"Iron Guild Dwarves: Wizard Gereng said that the Dragon Key has just appeared on the continent of Aden. Glory and blessings to the chosen dragon slayer!");
					}
				}
				pc.드키등록체크id = 0;
				break;
			case 1906: // Withdrawal renewal during clan blood battle
				c = readC();
				if (pc.getClanid() > 0) {
					if (c == 0) { // voluntary withdrawal
					} else if (c == 1) { // consent request
						pc.sendPackets(new S_SystemMessage("You are requesting approval from the Royal to leave the Pledge. Please wait a moment.")); // I'm requesting it, please wait
						L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
						L1PcInstance cl = L1World.getInstance().getPlayer(clan.getLeaderName());
						if (cl != null) {
							cl.sendPackets(new S_Message_YN(1908, pc.getName())); // I applied for withdrawal. Send to monarch
							cl.setTempID(pc.getId());
						} else {
							boolean ck = false;
							String sm = pc.getClan().getClanSubPrince();
							if (sm != null) {
								L1PcInstance subM = L1World.getInstance().getPlayer(sm);
								if (subM != null) {
									subM.sendPackets(new S_Message_YN(1908, pc.getName())); // I applied for withdrawal. send to lord
									subM.setTempID(pc.getId());
									ck = true;
								}
							}
							if (!ck)
								pc.sendPackets(new S_Message_YN(1914, null)); // Do you want to voluntarily withdraw from the monarch/sub-lord?
						}
					}
				}
				break;
			case 1908: // Processing monarch withdrawal request
				c = readC();
				L1PcInstance tpc = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());
				pc.setTempID(0);
				if (tpc != null && pc.getClanid() > 0) {
					if (c == 0) { // refuse
						pc.sendPackets(new S_ServerMessage(1910, tpc.getName())); // You refused to leave the Blood Pledge of %0.
						tpc.sendPackets(new S_Message_YN(1901, null)); // Rejected Would you like to withdraw?
					} else if (c == 1) { // approve
						leaveClan(tpc);
						pc.sendPackets(new S_ServerMessage(1909, tpc.getName())); // %0 has withdrawn from the Blood Pledge.
						tpc.sendPackets(new S_ServerMessage(1900, null)); // The monarch has agreed to approve the withdrawal of the clan.
					}
				}
			case 1914: // Do you want to leave the monarch/sub-monarch unconnected?
				c = readC();
				if (pc.getClanid() > 0) {
					if (c == 1) {
						pc.sendPackets(new S_Message_YN(1915, null)); // Are you really going to quit?
					}
				}
				break;
			case 1901: // The monarch refused. Would you like to leave voluntarily?
			case 1915: // Are you sure you want to leave?
				c = readC();
				if (pc.getClanid() > 0) {
					if (c == 1) {
						pc.sendPackets(new S_SystemMessage(pc.getClanname()
								+ " You have been voluntarily removed from the pledge. There will be a 1-hour restriction on joining a pledge."));
						leaveClan(pc);
						if (leaveNameCK == null) {
							leaveNameCK = new ClanLeave_joinNameCK();
							GeneralThreadPool.getInstance().execute(leaveNameCK);
						}
						leaveNameCK.add(pc.getName(), System.currentTimeMillis() + 3600000);
					}
				}
				break;
			case 97: // %0가 혈맹에 가입했지만은 있습니다. 승낙합니까? (Y/N)
				c = readC();
				L1PcInstance joinPc = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				if (joinPc != null) {
					if (c == 0) { // No
						joinPc.sendPackets(new S_ServerMessage(96, pc.getName())); // \f1%0은
																					// 당신의
																					// 요청을
																					// 거절했습니다.
					} else if (c == 1) { // Yes
						String clanName = pc.getClanname();
						L1Clan clan = L1World.getInstance().getClan(clanName);
						if (clan != null) {
							joinPledge(pc, joinPc, clan);
						}
					}
				}
				break;

			case 217: // %1 of %0Blood wants to go to war with your Blood Pledge. Will you go to war? (Y/N)
			case 221: // Blood Pledge %0 wants to surrender. do you accept (Y/N)
			case 222: // Blood Pledge %0 wants an end to the war. do you close? (Y/N)
				c = readC();
				L1PcInstance enemyLeader = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());
				if (enemyLeader == null) {
					return;
				}
				pc.setTempID(0);
				String clanName = pc.getClanname();
				String enemyClanName = enemyLeader.getClanname();
				if (c == 0) { // No
					if (i == 217) {
						enemyLeader.sendPackets(new S_ServerMessage(236, clanName)); // Blood Pledge %0 refused to go to war with your Blood Pledge.
					} else if (i == 221 || i == 222) {
						enemyLeader.sendPackets(new S_ServerMessage(237, clanName)); // Blood Pledge %0 declined your offer.
					}
				} else if (c == 1) { // Yes
					if (i == 217) {
						L1War war = new L1War();
						war.handleCommands(2, enemyClanName, clanName); // At the time of simulation
					} else if (i == 221 || i == 222) {
						for (L1War war : L1World.getInstance().getWarList()) { // get war list
							if (war.CheckClanInWar(clanName)) { // Discover the war Zakran is going on
								if (i == 221) {
									war.SurrenderWar(enemyClanName, clanName); // surrender
								} else if (i == 222) {
									war.CeaseWar(enemyClanName, clanName); // closing
								}
								break;
							}
						}
					}
				}
				break;

			case 223: // %0%s wants an ally. would you accept? (Y/N)
				c = readC();
				L1PcInstance allianceLeader = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());
				if (allianceLeader == null)
					return;
				pc.setTempID(0);
				int PcClanId = pc.getClanid();
				int TargetClanId = allianceLeader.getClanid();
				String PcClanName = pc.getClanname();
				String TargetClanName = allianceLeader.getClanname();
				L1Clan PcClan = L1World.getInstance().getClan(PcClanName);
				L1Clan TargegClan = L1World.getInstance().getClan(TargetClanName);

				if (c == 1) { // Yes
					PcClan.addAlliance(TargetClanId);
					// PcClan.setAlliance(TargetClanId);
					// TargegClan.setAlliance(PcClanId);
					TargegClan.addAlliance(PcClanId);
					ClanTable.getInstance().updateClan(PcClan);
					ClanTable.getInstance().updateClan(TargegClan);
					pc.sendPackets(new S_ServerMessage(1200, TargetClanName)); // Blood ally %0 has joined the alliance.
					allianceLeader.sendPackets(new S_ServerMessage(224, PcClanName, TargetClanName)); // Blood Pledge %0 and Blood Pledge %1 have formed an alliance.
				} else if (c == 0) { // No
					pc.sendPackets(new S_SystemMessage("Your opponent has rejected your alliance."));
				}

			case 1210: // Are you sure you want to leave the alliance? (Y/N)
				if (readC() == 1) {
					if (pc.getClan() != null && pc.getClan().AllianceSize() > 0) {
						for (int clanid : pc.getClan().Alliance()) {
							if (clanid == 0)
								continue;
							L1Clan clan = L1World.getInstance().getClan(clanid);
							if (clan == null)
								continue;
							for (L1PcInstance tempPc : clan.getOnlineClanMember()) {
								tempPc.sendPackets(new S_ServerMessage(1204, pc.getClan().getClanName()));
							}
							clan.removeAlliance(pc.getClanid());
							ClanTable.getInstance().updateClan(clan);
						}
					}
					pc.sendPackets(new S_SystemMessage(pc.getClanname() + " pledge has withdrawn from the alliance."));
					pc.getClan().AllianceDelete();
					ClanTable.getInstance().updateClan(pc.getClan());
				}
				break;
			case 252: // %0%s wants to trade items with you. do you trade? (Y/N)
				c = readC();
				if (pc.getTradeID() == 0 || pc.getTradeReady())
					return;
				L1Object trading_partner = L1World.getInstance().findObject(pc.getTradeID());

				if (trading_partner != null) {
					if (trading_partner instanceof L1PcInstance) {
						L1PcInstance target = (L1PcInstance) trading_partner;
						if (target.getTradeReady())
							return;
						if (c == 0) { // No
							target.sendPackets(new S_ServerMessage(253, pc.getName())); //%0%d did not agree to a deal with you.
							pc.setTradeID(0);
							target.setTradeID(0);
							pc.setTradeReady(false);
							target.setTradeReady(false);
						} else if (c == 1) { // Yes
							if (pc.getLocation().getTileLineDistance(new Point(target.getX(), target.getY())) > 20) {
								pc.sendPackets(new S_SystemMessage("The target is too far away to trade."));
								pc.setTradeID(0);
								target.setTradeID(0);
								pc.setTradeReady(false);
								target.setTradeReady(false);
								return;
							}

							pc.sendPackets(new S_Trade(target.getName()));
							target.sendPackets(new S_Trade(pc.getName()));
							pc.setTradeReady(true);
							target.setTradeReady(true);
						}
					} else if (trading_partner instanceof L1BuffNpcInstance) {
						L1BuffNpcInstance target = (L1BuffNpcInstance) trading_partner;
						if (c == 0) { // No
							pc.setTradeID(0);
							target.setTradeID(0);
							pc.setTradeReady(false);
						} else if (c == 1) { // Yes
							pc.sendPackets(new S_Trade(target.getName()));
							pc.setTradeReady(true);
							target.setTradeID(pc.getId()); // Save the opponent's object ID
						}
					} else if (trading_partner instanceof L1NpcShopInstance) {
						L1NpcShopInstance target = (L1NpcShopInstance) trading_partner;
						if (c == 0) { // No
							pc.setTradeID(0);
							pc.setTradeReady(false);
						} else if (c == 1) { // Yes
							target.Npc_trade = true;
							pc.setTradeReady(true);
							pc.sendPackets(new S_Trade(target.getName()));
						}
					} else if (trading_partner instanceof GambleInstance) {
						GambleInstance target = (GambleInstance) trading_partner;
						if (c == 0) { // No
							pc.setTradeID(0);
							pc.setTradeReady(false);
						} else if (c == 1) { // Yes
							if (target.getTileLineDistance(pc) > 1) {
								pc.setTradeID(0);
								pc.setTradeReady(false);
								pc.sendPackets(new S_SystemMessage("The distance is not acceptable."));
								return;
							}
							target.Npc_trade = true;
							pc.setTradeReady(true);
							pc.sendPackets(new S_Trade(target.getName()));
						}
					}

				}
				break;

			case 321: // Do you want to be resurrected again? (Y/N)
				c = readC();
				L1PcInstance resusepc1 = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());
				pc.setTempID(0);
				if (resusepc1 != null) { // resurrection scroll
					if (c == 0) { // No

					} else if (c == 1) { // Yes
						if (pc.isInParty()) { // Add party
							if (pc.isDead()) {
								pc.getParty().refresh(pc);
							}
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), '\346'));
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), '\346'));
						pc.resurrect(pc.getMaxHp() / 2);
						pc.setCurrentHp(pc.getMaxHp() / 2);
						// pc.startHpRegeneration();
						// pc.startMpRegeneration();
						pc.startMpRegenerationByDoll();
						pc.sendPackets(new S_Resurrection(pc, resusepc1, 0));
						Broadcaster.broadcastPacket(pc, new S_Resurrection(pc, resusepc1, 0));
						pc.sendPackets(new S_CharVisualUpdate(pc));
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc));
					}
				}
				break;

			case 322: // Do you want to be resurrected again? (Y/N)
				c = readC();
				L1PcInstance resusepc2 = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());
				pc.setTempID(0);
				if (resusepc2 != null) { // Blessed Resurrection Scroll, Rez Rection, Great Rez Rection
					if (c == 0) { // No

					} else if (c == 1) { // Yes
						if (pc.isInParty()) { // Add party
							if (pc.isDead()) {
								pc.getParty().refresh(pc);
							}
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), '\346'));
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), '\346'));
						pc.resurrect(pc.getMaxHp());
						pc.setCurrentHp(pc.getMaxHp());
						// pc.startHpRegeneration();
						// pc.startMpRegeneration();
						pc.startMpRegenerationByDoll();
						pc.startMpRegenerationByDoll();
						pc.sendPackets(new S_Resurrection(pc, resusepc2, 0));
						Broadcaster.broadcastPacket(pc, new S_Resurrection(pc, resusepc2, 0));
						pc.sendPackets(new S_CharVisualUpdate(pc));
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc));
						// EXP lost, G-RES applied, EXP lost death
						// Recover EXP only if all are filled
						if (pc.getExpRes() == 1 && pc.isGres() && pc.isGresValid()) {
							pc.resExp();
							pc.setExpRes(0);
							pc.setGres(false);
						}
					}
				}
				break;

			case 512: // What is your name?
				c = readC(); // ?
				name = readS();
				int houseId = pc.getTempID();
				if (houseId == 0)
					return;
				pc.setTempID(0);
				if (name.length() <= 16) {
					L1House house = HouseTable.getInstance().getHouseTable(houseId);
					house.setHouseName(name);
					HouseTable.getInstance().updateHouse(house); // write to DB
				} else {

					pc.sendPackets(new S_SystemMessage("Your house name is too long."));
				}
				break;

			case 622: // survey
				c = readC();
				 switch (pc.getMsgType()) {

				 //test spawn test
				 case L1PcInstance.SPAWN_GIRTAS:
		                pc.setMsgType(0);

		                if (c == 0) {

		                } else if (c == 1) {
		                    // teleport handling
		                }

		                break;
				 }
				 //test

				switch (pc.system) {
				case 1:
					pc.system = -1; // 초기화
					if (c == 0) {
					} else if (c == 1) {
						int count = 0;
						int trcount = 0;
						AntharasRaid ar = AntharasRaidSystem.getInstance().getAR(pc.dragonmapid);
						int count1 = ar.countUsersInLair();
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_A)) {
							pc.sendPackets(new S_SystemMessage(
									"혈흔 버프가 있어 입장 할 수 없습니다."));
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONRAID_BUFF)) {
							pc.sendPackets(new S_SystemMessage(
									"드래곤 레이드 마법으로 인해 드래곤 포탈에 입장 할 수 없습니다."));
							return;
						}
						if (count1 > 0) {
							for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
								if (player.getMapId() == pc.dragonmapid) {
									trcount++;
								}
							}
							if (trcount == 0) {
								for (L1Object npc : L1World.getInstance().getObject()) {
									if (npc instanceof L1MonsterInstance) {
										if (npc.getMapId() == pc.dragonmapid) {
											L1MonsterInstance _npc = (L1MonsterInstance) npc;
											_npc.deleteMe();
										}
									}
								}
								ar.clLairUser();
								ar.setAntaras(false);
								ar.setanta(null);
							}
						}
						for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
							if (player.getMapId() == pc.dragonmapid) {
								count += 1;
								if (count > 31) {
									pc.sendPackets(new S_SystemMessage("입장 가능 인원수를 초과 하였습니다."));
									return;
								}
							}
						}
						L1Teleport.teleport(pc, 32668, 32675, pc.dragonmapid, 5, true);
					}
					break;
				case 2:
					pc.system = -1; // 초기화
					if (c == 0) {
					} else if (c == 1) {
						int count = 0;
						int trcount = 0;
						FafurionRaid ar = FafurionRaidSystem.getInstance().getAR(pc.dragonmapid);
						int count1 = ar.countLairUser();
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_P)) {
							pc.sendPackets(new S_SystemMessage("You can't enter because there is a bloodstain buff."));
							return;
						}

						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONRAID_BUFF)) {
							pc.sendPackets(new S_SystemMessage("Due to dragon raid magic, you cannot enter the dragon portal."));
							return;
						}
						if (count1 > 0) {
							for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
								if (player.getMapId() == pc.dragonmapid) {
									trcount++;
								}
							}
							if (trcount == 0) {
								for (L1Object npc : L1World.getInstance().getObject()) {
									if (npc instanceof L1MonsterInstance) {
										if (npc.getMapId() == pc.dragonmapid) {
											L1MonsterInstance _npc = (L1MonsterInstance) npc;
											_npc.deleteMe();
										}
									}
								}
								ar.clLairUser();
								ar.setPapoo(false);
								ar.setPaPoo(null);
							}
						}
						for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
							if (player.getMapId() == pc.dragonmapid) {
								count += 1;
								if (count > 31) {
									pc.sendPackets(new S_SystemMessage("The number of people allowed to enter has been exceeded."));
									return;
								}
							}
						}
						L1Teleport.teleport(pc, 32920, 32672, pc.dragonmapid, 5, true);
					}
					break;
				case 3:
					pc.system = -1; // reset
					if (c == 0) {
					} else if (c == 1) {
						AntharasRaid ar = AntharasRaidSystem.getInstance().getAR(pc.dragonmapid);
						/*
						 * L1Party pcpt = pc.getParty(); if (pcpt == null){
						 * pc.sendPackets(new
						 * S_SystemMessage("당신은 파티 중이 아닙니다.")); return; }
						 * if (pcpt.getLeader().getName() != pc.getName()){
						 * pc.sendPackets(new
						 * S_SystemMessage("파티장만 입장을 할 수 있습니다.")); return; }
						 */
						boolean ok = true;
						int num = 0;
						/*
						 * for (int i1 = 1; i1 < 4 ; i1++){ num --; ok =
						 * ar.Check(i1); if (ok == false){ ar.setParty(pcpt, i1);
						 * System.out.println("파티 등록 "+i1+"번 째 방 등록"); break; }
						 * }
						 */

						num = -1;
						ok = ar.Check(1);
						ar.addMember(pc);
						if (ok) {

							// pc.sendPackets(new
							// S_SystemMessage("이미 시작되었습니다."));
							// return;
						} else {
							AntharasRaidTimer art = new AntharasRaidTimer(null, ar, num, 10000);// 2분 체크
							art.begin();
							ar.MiniRoom1 = true;
						}
						num *= -1;

						switch (num) {
						case 1:
							if (pc.getMapId() != pc.dragonmapid) {
								return;
							}
							L1Teleport.teleport(pc, 32679, 32803, pc.dragonmapid, 5, true);
							break;
						/*
						 * case 1: for (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32679, 32803, (short)
						 * pc.dragonmapid, 5, true); } break; case 2: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32935, 32611, (short)
						 * pc.dragonmapid, 5, true); } break; case 3: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32935, 32803, (short)
						 * pc.dragonmapid, 5, true); } break; case 4: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32807, 32803, (short)
						 * pc.dragonmapid, 5, true); } break;
						 */
						case 5:
							pc.sendPackets(new S_SystemMessage("It's already started."));
							break;
						}

					}
					break;

				case 4:
					pc.system = -1;// 초기화
					if (c == 0) {
					} else if (c == 1) {
						FafurionRaid ar = FafurionRaidSystem.getInstance().getAR(
								pc.dragonmapid);
						// System.out.println(pc.dragonmapid);
						/*
						 * L1Party pcpt = pc.getParty(); if (pcpt == null){
						 * pc.sendPackets(new
						 * S_SystemMessage("당신은 파티 중이 아닙니다.")); return; }
						 * if (pcpt.getLeader().getName() != pc.getName()){
						 * pc.sendPackets(new
						 * S_SystemMessage("파티장만 입장을 할 수 있습니다.")); return; }
						 */
						boolean ok = true;
						int num = 0;
						/*
						 * for (int i1 = 1; i1 < 4 ; i1++){ num --; ok =
						 * ar.Check(i1); if (ok == false){ ar.setParty(pcpt, i1);
						 * System.out.println("파푸 단계루트 파티 등록 "+i1+"번 째 방 등록");
						 * break; } }
						 */
						num = -1;
						ok = ar.Check(1);
						ar.addMember(pc);
						if (ok) {
							// pc.sendPackets(new
							// S_SystemMessage("이미 시작되었습니다."));
							// return;
						} else {

							FafurionTimer art = new FafurionTimer(null, ar, num, 10000);// 2분 체크
							art.begin();
							ar.MiniRoom1 = true;
						}
						num *= -1;
						switch (num) {
						case 1:
							if (pc.getMapId() != pc.dragonmapid) {
								return;
							}
							L1Teleport.teleport(pc, 32759, 32852, pc.dragonmapid, 5, true);
							break;
						/*
						 * case 1:
						 *
						 * for (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32759, 32852, (short)
						 * pc.dragonmapid, 5, true); } break; case 2: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 *
						 * L1Teleport.teleport(mem, 32759, 32726, (short)
						 * pc.dragonmapid, 5, true); } break; case 3: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 *
						 * L1Teleport.teleport(mem, 32759, 32597, (short)
						 * pc.dragonmapid, 5, true); } break; case 4: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if (mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32952, 32596, (short)
						 * pc.dragonmapid, 5, true); } break;
						 */
						case 5:
							pc.sendPackets(new S_SystemMessage("It's already started."));
							break;
						}

					}
					break;
				// 린드
				case 5:
					pc.system = -1;// 초기화
					if (c == 0) {
					} else if (c == 1) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_L)) {
							pc.sendPackets(new S_SystemMessage("You can't enter because there is a bloodstain buff."));
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONRAID_BUFF)) {
							pc.sendPackets(new S_SystemMessage("Due to dragon raid magic, you cannot enter the dragon portal."));
							return;
						}
						int count = 0;
						for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
							if (player.getMapId() == pc.dragonmapid) {
								count += 1;
								if (count > 31) {
									pc.sendPackets(new S_SystemMessage("The number of people allowed to enter has been exceeded."));
									return;
								}
							}
						}
						LindviorRaid.get().in(pc);
					}
					break;
				default:
					pc.system = -1; // reset
					if (c == 0)
						L1Question.bad += 1;
					else if (c == 1)
						L1Question.good += 1;
					break;
				}
				break;
			case 630:
				c = readC();
				L1PcInstance fightPc = (L1PcInstance) L1World.getInstance().findObject(pc.getFightId());
				if (c == 0) {
					pc.setFightId(0);
					fightPc.setFightId(0);
					fightPc.sendPackets(new S_ServerMessage(631, pc.getName()));
				} else if (c == 1) {
					fightPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, fightPc.getFightId(), fightPc.getId()));
					pc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, pc.getFightId(), pc.getId()));
				}
				break;

			case 653: // If you divorce, the ring will disappear. divorce? (Y/N)
				c = readC();
				L1PcInstance par = (L1PcInstance) L1World.getInstance().findObject(pc.getPartnerId());
				if (c == 0) { // No

				} else if (c == 1) { // Yes
					int[] divorceItemIds = { 40901, 40902, 40903, 40904, 40905, 40906 };
				    for (int itemId : divorceItemIds) {
				        if (pc.getInventory().checkItem(itemId)) {
				            pc.getInventory().consumeItem(itemId, pc.getInventory().countItems(itemId));
				        }
				    }

					if (par != null) {
						par.sendPackets(new S_SystemMessage(pc.getName() + " has divorced you... sorry!"));
						par.setPartnerId(0);
						par.save();
					} else {
						phone(pc.getPartnerId());
					}
					pc.setPartnerId(0);
					pc.save();
				}
				break;

			case 654: // %0%s wants to marry you. Are you marrying %0? (Y/N)
				c = readC();
				L1PcInstance partner = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());
				pc.setTempID(0);
				if (partner != null) {
					if (c == 0) { // No
						partner.sendPackets(new S_ServerMessage(656, pc.getName())); // %0%s refused to marry you.
					} else if (c == 1) { // Yes
						pc.setPartnerId(partner.getId());
						pc.save();
						pc.sendPackets(new S_SystemMessage(
								"The marriage of the two was made possible with everyone's blessings."));
						pc.sendPackets(new S_ServerMessage(655, partner.getName())); // congratulations! You are married to %0.
						pc.sendPackets(new S_SkillSound(pc.getId(), 2059));
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 2059));
						partner.setPartnerId(pc.getId());
						partner.save();
						partner.sendPackets(new S_ServerMessage(790)); // Among everyone's blessings, two people got married.
						partner.sendPackets(new S_ServerMessage(655, pc.getName())); // congratulations! You are married to %0.
						partner.sendPackets(new S_SkillSound(partner.getId(), 2059));
						Broadcaster.broadcastPacket(partner, new S_SkillSound(partner.getId(), 2059));
					}
				}
				break;

			// call pledge
			case 729: // A clan member is trying to teleport you. do you agree? (Y/N)
				c = readC();
				if (c == 0) {
				} else if (c == 1) { // Yes
					callClan(pc);
				}
				break;

			case 738: // %0 adena is required to restore experience. Do you recover experience points?
				c = readC();
				if (c == 0) {
				} else if (c == 1 && pc.getExpRes() == 1) { // Yes
					// int cost = readD();
					int cost = 0;
					int level = pc.getLevel();
					int lawful = pc.getLawful();

					if (level < 45) {
						cost = level * level * 50;
					} else {
						cost = level * level * 100;
					}

					if (lawful >= 0) {
						cost = (cost / 2);
					}

					cost *= 2; // ?

					if (cost >= 200000000 || cost < 0)
						return;

					if (pc.getInventory().consumeItem(L1ItemId.ADENA, cost)) {
						pc.resExpToTemple();
						pc.setExpRes(0);
					} else {
						pc.sendPackets(new S_SystemMessage("You don't have enough Adena."), true);
					}
				}
				break;
			case 2551: // Recover using the Relief Certificate
				c = readC();
				if (c == 0) {
				} else if (c == 1 && pc.getExpRes() == 1) { // Yes
					if (pc.getInventory().consumeItem(600256, 1)) {
						pc.resExpTo구호();
						pc.setExpRes(0);
					} else {
						pc.sendPackets(new S_SystemMessage("A relief certificate is required."), true);
					}
				}
				break;

			case 951: // Allow chat party invites? (Y/N)
				c = readC();
				L1PcInstance chatPc = (L1PcInstance) L1World.getInstance().findObject(pc.getPartyID());
				pc.setPartyID(0);
				if (chatPc != null) {
					if (c == 0) { // No
						chatPc.sendPackets(new S_ServerMessage(423, pc.getName())); // %0 declined your invitation.
					} else if (c == 1) { // Yes
						if (chatPc.isInChatParty()) {
							if (chatPc.getChatParty().isVacancy() || chatPc.isGm()) {
								chatPc.getChatParty().addMember(pc);
							} else {
								chatPc.sendPackets(new S_ServerMessage(417)); // Cannot accept any more party members.
							}
						} else {
							L1ChatParty chatParty = new L1ChatParty();
							chatParty.addMember(chatPc);
							chatParty.addMember(pc);
							chatPc.sendPackets(new S_ServerMessage(424, pc.getName())); // %0 joined the party.
						}
					}
				}
				break;

			case 953: // Do you accept party invites? (Y/N)
			case 954:
				c = readC();
				L1PcInstance target = (L1PcInstance) L1World.getInstance().findObject(pc.getPartyID());
				pc.setPartyID(0);
				if (target != null) {
					if (c == 0) { // No
						target.sendPackets(new S_ServerMessage(423, pc.getName())); // %0 declined your invitation.
					} else if (c == 1) { // Yes
						/** battle zone **/
						if (target.getMapId() == 5153 || target.getMapId() == 5001 || pc.getMapId() == 5153 || pc.getMapId() == 5001) {
							target.sendPackets(new S_ServerMessage(423, pc.getName())); // %0 declined your invitation.
							return;
						}

						if (target.isInParty()) { // invitee at party
							if (target.getParty().isVacancy() || target.isGm()) {
								target.getParty().addMember(pc);
							} else {
								target.sendPackets(new S_ServerMessage(417)); // We cannot accept any more party members.
							}
						} else { // The invitee is not at the party
							L1Party party = new L1Party();
							party.addMember(target);
							party.addMember(pc);
						}
					}
				}
				break;

			case 1256: // Would you like to enter the arena? (Y/N)
				c = readC();
				if (c == 0) {
					miniGameRemoveEnterMember(pc);
				} else if (c == 1) {
					if (pc.getInventory().checkItem(L1ItemId.ADENA, 1000)) {
						pc.getInventory().consumeItem(L1ItemId.ADENA, 1000);
						if (pc.isInParty())
							pc.getParty().leaveMember(pc);
						L1SkillUse l1skilluse = new L1SkillUse();
						l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION,
								pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_LOGIN);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_DRAGONPERL)) {
							pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_DRAGONPERL);
							pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONPERL, 0, 0));
							Broadcaster.broadcastPacket(pc, new S_DRAGONPERL(pc.getId(), 0));
							pc.sendPackets(new S_DRAGONPERL(pc.getId(), 0));
							pc.setPearlSpeed(0);
						}

						if (GhostHouse.getInstance().isEnterMember(pc)) {
							if (GhostHouse.getInstance().isPlayingNow()) {
								pc.sendPackets(new S_ServerMessage(1182));
								return;
							}
							for (L1DollInstance doll : pc.getDollList()) {
								doll.deleteDoll();
							}
							GhostHouse.getInstance().addPlayMember(pc);
							L1Teleport.teleport(pc, 32722, 32830, (short) 5140, 2, true);
						} else if (PetRacing.getInstance().isEnterMember(pc)) {
							if (PetRacing.getInstance().isPlay()) {
								pc.sendPackets(new S_ServerMessage(1182));
								return;
							}
							for (L1DollInstance doll : pc.getDollList()) {
								doll.deleteDoll();
							}
							pc.setPetRacing(true);
							PetRacing.getInstance().removeEnterMember(pc);
							PetRacing.getInstance().addPlayMember(pc);
							L1Teleport.teleport(pc, 32768, 32848, (short) 5143, 5, true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("You don't have enough Adena."));
						miniGameRemoveEnterMember(pc);
					}
				}
				break;

			case 1268: // 데스매치에 입장하시겠습니까? (Y/N)
				c = readC();
				if (c == 0)
					DeathMatch.getInstance().giveBackAdena(pc);
				else if (c == 1)
					DeathMatch.getInstance().addPlayMember(pc);
				break;
			case 479: // 어느 능력치를 향상시킵니까? (str, dex, int, con, wis, cha)
				if (readC() == 1) {
					String s = readS();
					final int BONUS_ABILITY = pc.getAbility().getBonusAbility();

					if (!(pc.getLevel() - 50 > BONUS_ABILITY))
						return;
					if (s.toLowerCase().equals("str".toLowerCase())) {
					 if (pc.getLevel() <= 89){
						if (pc.getAbility().getStr() < 50) {
							pc.getAbility().addStr((byte) 1); // 소의 STR치에+1
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 50. Please select a different stat."));
						}
					}
					 else if (pc.getLevel() >= 90){
						 if (pc.getAbility().getStr() < 55) {
								pc.getAbility().addStr((byte) 1); // 소의 STR치에+1
								pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
								pc.sendPackets(new S_OwnCharStatus2(pc));
								pc.sendPackets(new S_CharVisualUpdate(pc));
								pc.save();
							} else {
								pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 55. Please select a different stat."));
							}
					 }
					} else if (s.toLowerCase().equals("dex".toLowerCase())) {
					if (pc.getLevel() <= 89){
						if (pc.getAbility().getDex() < 50) {
							pc.getAbility().addDex((byte) 1);
							pc.resetBaseAc();
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 50. Please select a different stat."));
						}
					}
					else if (pc.getLevel() >= 90){
						if (pc.getAbility().getDex() < 55) {
							pc.getAbility().addDex((byte) 1);
							pc.resetBaseAc();
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 55. Please select a different stat."));
						}
					}

					} else if (s.toLowerCase().equals("con".toLowerCase())) {
					 if (pc.getLevel() <= 89){
						if (pc.getAbility().getCon() < 50) {
							pc.getAbility().addCon((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 50. Please select a different stat."));
						}
					 }
					 else if (pc.getLevel() >= 90){
							if (pc.getAbility().getCon() < 55) {
								pc.getAbility().addCon((byte) 1);
								pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
								pc.sendPackets(new S_OwnCharStatus2(pc));
								pc.sendPackets(new S_CharVisualUpdate(pc));
								pc.save();
							} else {
								pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 55. Please select a different stat."));
							}
						 }
					} else if (s.toLowerCase().equals("int".toLowerCase())) {
					  if (pc.getLevel() <= 89){
						if (pc.getAbility().getInt() < 50) {
							pc.getAbility().addInt((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_ServerMessage(481));
						}
					  }
					  else if (pc.getLevel() >= 90){
							if (pc.getAbility().getInt() < 55) {
								pc.getAbility().addInt((byte) 1);
								pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
								pc.sendPackets(new S_OwnCharStatus2(pc));
								pc.sendPackets(new S_CharVisualUpdate(pc));
								pc.save();
							} else {
								pc.sendPackets(new S_ServerMessage(481));
							}
						  }
					} else if (s.toLowerCase().equals("wis".toLowerCase())) {
					  if (pc.getLevel() <= 89){
						if (pc.getAbility().getWis() < 50) {
							pc.getAbility().addWis((byte) 1);
							pc.resetBaseMr();
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 50. Please select a different stat."));
						}
					  }
					  else if (pc.getLevel() >= 90){
							if (pc.getAbility().getWis() < 55) {
								pc.getAbility().addWis((byte) 1);
								pc.resetBaseMr();
								pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
								pc.sendPackets(new S_OwnCharStatus2(pc));
								pc.sendPackets(new S_CharVisualUpdate(pc));
								pc.save();
							} else {
								pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 55. Please choose another stat."));
							}
						  }
					} else if (s.toLowerCase().equals("cha".toLowerCase())) {
					  if (pc.getLevel() <= 89){
						if (pc.getAbility().getCha() < 50) {
							pc.getAbility().addCha((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 50. Please select a different stat."));
						}
					}
					} else if (pc.getLevel() >= 90){
						if (pc.getAbility().getCha() < 55) {
							pc.getAbility().addCha((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage("The maximum value of one stat is 55. Please select a different stat."));
						}
					}

					pc.CheckStatus();
					if (pc.getLevel() >= 51 && pc.getLevel() - 50 > pc.getAbility().getBonusAbility()) {
						if ((pc.getAbility().getStr() + pc.getAbility().getDex()
								+ pc.getAbility().getCon() + pc.getAbility().getInt()
								+ pc.getAbility().getWis() + pc.getAbility().getCha()) < 150) {
							int temp = (pc.getLevel() - 50) - pc.getAbility().getBonusAbility();
							pc.sendPackets(new S_bonusstats(pc.getId(), temp));
						}
					}
				}
				break;

			default:
				break;
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	// joinPc = user, pc = Monarch
	public static void joinPledge(L1PcInstance pc, L1PcInstance joinPc, L1Clan clan) {
		// try{
		if (pc == null)
			return;
		int maxMember = 0;
		/*
		 * int maxMember = 0; int charisma = 0; if (pc == null)return;
		 * if (pc.getClanRank() == L1Clan.CLAN_RANK_PRINCE ){ charisma =
		 * pc.getAbility().getTotalCha(); if (pc.getLevel() >= 50) { // Lv50 이상
		 * maxMember = charisma * 9; } else if (pc.getLevel() >= 45) { // Lv45
		 * 미만 maxMember = charisma * 6; }else{ maxMember = charisma * 3; }
		 *
		 * }else if (pc.getClanRank() >= L1Clan.CLAN_RANK_GUARDIAN){ maxMember =
		 * clan.getCrownMaxMember(); } //pc.sendPackets(new
		 * S_SystemMessage("dd "+maxMember)); if (maxMember > 300){ maxMember =
		 * 300; }
		 */
		maxMember = 20;

		if (joinPc.getClanid() == 0) { // pledge not subscribed
			if (leaveNameCK != null) {
				long ck = leaveNameCK.ck(joinPc.getName());
				if (ck > 0) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH시간 mm분");
					String time = dateFormat.format(new Timestamp((ck - System.currentTimeMillis()) + (60 * 1000 * 60 * 15)));
					if (pc != null)
						pc.sendPackets(new S_SystemMessage(joinPc.getName() + " You are currently subject to subscription restrictions."));
					joinPc.sendPackets(new S_SystemMessage(time + " You can re-enroll later."));
					return;
				}
			}
			if (maxMember <= clan.getClanMemberList().size()) { // check if pledge not full
				if (pc != null)
					joinPc.sendPackets(new S_ServerMessage(188, pc.getName())); // %0cannot accept you as a clan member.
				joinPc.sendPackets(new S_SystemMessage("You cannot join [" + clan.getClanName() + "] at this time."), true);
				return;
			}
			// Join the Claudia Pledge
			if (joinPc.getClanid() != 0) {
				L1QuestInfo info = joinPc.getQuestList(271);
				if (info != null && info.end_time == 0) {
					info.ck[0] = joinPc.getClanid();
					if (info.ck[0] > 1) {
						info.ck[0] = 1;
					}
					joinPc.sendPackets(new S_QuestTalkIsland(joinPc, 271, info));
				}
			}
			for (L1PcInstance clanMembers : clan.getOnlineClanMember()) {
				clanMembers.sendPackets(new S_ServerMessage(94, joinPc.getName())); // \f1%0 has been accepted as a member of the Blood Pledge.
			}
			joinPc.setClanid(clan.getClanId());
			joinPc.setClanname(clan.getClanName());
			joinPc.setClanRank(L1Clan.CLAN_RANK_PROBATION);
			joinPc.setTitle("");
			joinPc.sendPackets(new S_CharTitle(joinPc.getId(), ""));
			Broadcaster.broadcastPacket(joinPc, new S_CharTitle(joinPc.getId(),""));
			joinPc.setClanJoinDate(new Timestamp(System.currentTimeMillis()));
			try {
				joinPc.save();
			} catch (Exception e) {
				e.printStackTrace();
			} // Write character information to DB
			clan.addClanMember(joinPc.getName(), joinPc.getClanRank(),joinPc.getLevel(), joinPc.getType(), joinPc.getMemo(),
					joinPc.getOnlineStatus(), joinPc);
			joinPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED,0x07, joinPc.getName()), true);
			joinPc.sendPackets(new S_PacketBox(S_PacketBox.WORLDMAP_UNKNOWN1),true);
			joinPc.sendPackets(new S_ClanJoinLeaveStatus(joinPc), true);
			Broadcaster.broadcastPacket(joinPc, new S_ClanJoinLeaveStatus(joinPc));
			joinPc.sendPackets(new S_ReturnedStat(joinPc,S_ReturnedStat.CLAN_JOIN_LEAVE), true);
			Broadcaster.broadcastPacket(joinPc, new S_ReturnedStat(joinPc,S_ReturnedStat.CLAN_JOIN_LEAVE));
			joinPc.sendPackets(new S_ClanWindow(S_ClanWindow.혈마크띄우기, joinPc.getClan().getmarkon()), true);
			joinPc.sendPackets(new S_문장주시(joinPc.getClan(), 2), true);
			ClanTable.getInstance().updateClan(joinPc.getClan());
			if (pc != null) {
				pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_REFRESH_PLUS));
				joinPc.sendPackets(new S_ServerMessage(95, clan.getClanName())); // \f1You have joined %0 clan.
			}
		} else { // pledge Joined (pledge Alliance)
			if (Config.CLAN_ALLIANCE) {
				if (pc != null)
					changeClan(pc.getNetConnection(), pc, joinPc, maxMember);
			} else {
				joinPc.sendPackets(new S_SystemMessage("You have already joined the Blood Pledge.")); // \f1당신은벌써혈맹에가입하고있습니다.
			}
		}
		/*
		 * }catch(Exception e){ e.printStackTrace(); }
		 */
	}

	private void leaveClan(L1PcInstance pc) {
		try {
			String player_name = pc.getName();
			String clan_name = pc.getClanname();
			L1Clan clan = L1World.getInstance().getClan(clan_name);
			L1PcInstance clanMember[] = clan.getOnlineClanMember();

			for (L1PcInstance element : clanMember) {
				element.sendPackets(new S_ServerMessage(ServerMessage.LEAVE_CLAN, player_name, clan_name)); // \f1%0이%1혈맹을탈퇴했습니다.
			}

			pc.ClearPlayerClanData(clan);
			clan.removeOnlineClanMember(player_name);
			clan.removeClanMember(player_name);

			ClanTable.getInstance().updateClan(clan);

			pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.CLAN_JOIN_LEAVE), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void changeClan(LineageClient clientthread, L1PcInstance pc, L1PcInstance joinPc, int maxMember) {
		int clanId = pc.getClanid();
		String clanName = pc.getClanname();
		L1Clan clan = L1World.getInstance().getClan(clanName);
		int clanNum = clan.getClanMemberList().size();

		int oldClanId = joinPc.getClanid();
		String oldClanName = joinPc.getClanname();
		L1Clan oldClan = L1World.getInstance().getClan(oldClanName);
		int oldClanNum = oldClan.getClanMemberList().size();
		if (clan != null && oldClan != null && joinPc.isCrown() && joinPc.getId() == oldClan.getLeaderId()) {
			if (maxMember < clanNum + oldClanNum) { // no empty space
				joinPc.sendPackets(new S_ServerMessage(188, pc.getName())); // %0 cannot accept you as a Blood Pledge member.
				return;
			}
			L1PcInstance clanMember[] = clan.getOnlineClanMember();
			for (L1PcInstance element : clanMember) {
				element.sendPackets(new S_ServerMessage(94, joinPc.getName())); // \f1%0이 혈맹의 일원으로서 받아들여졌습니다.
			}

			for (ClanMember element : oldClan.getClanMemberList()) {
				L1PcInstance oldClanMember = L1World.getInstance().getPlayer(
						element.name);
				if (oldClanMember != null) { // Gookran member online
					oldClanMember.setClanid(clanId);
					oldClanMember.setClanname(clanName);
					// Lords who have joined the Blood Alliance are Guardians
					// Follow the example of the clan members brought by the monarch
					if (oldClanMember.getId() == joinPc.getId()) {
						// oldClanMember.setClanRank(L1Clan.CLAN_RANK_GUARDIAN);
						oldClanMember.setClanRank(L1Clan.CLAN_RANK_SUBPRINCE);
					} else {
						oldClanMember.setClanRank(L1Clan.CLAN_RANK_PROBATION);
					}
					try {
						oldClanMember.save();
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
					clan.addClanMember(oldClanMember.getName(), oldClanMember.getClanRank(),
							oldClanMember.getLevel(), oldClanMember.getType(), oldClanMember.getMemo(),
							oldClanMember.getOnlineStatus(), oldClanMember);
					oldClanMember.sendPackets(new S_ServerMessage(95, clanName)); // You have joined %0 clan.
				} else { // Gookran member offline?
					try {
						L1PcInstance offClanMember = CharacterTable.getInstance().restoreCharacter(element.name);
						offClanMember.setClanid(clanId);
						offClanMember.setClanname(clanName);
						offClanMember.setClanRank(L1Clan.CLAN_RANK_PROBATION);
						offClanMember.save();
						clan.addClanMember(offClanMember.getName(), offClanMember.getClanRank(), offClanMember.getLevel(),
								offClanMember.getType(), offClanMember.getMemo(), offClanMember.getOnlineStatus(), offClanMember);
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
			}
			// Delete previous clan
			String emblem_file = String.valueOf(oldClanId);
			File file = new File("emblem/" + emblem_file);
			file.delete();
			ClanTable.getInstance().deleteClan(oldClanName);
		}
	}

	private void callClan(L1PcInstance pc) {
		L1PcInstance callClanPc = (L1PcInstance) L1World.getInstance().findObject(pc.getTempID());

		if (callClanPc == null) {
			return;
		}

		boolean isInWarArea = false;
		short mapId = callClanPc.getMapId();
		int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);

		pc.setTempID(0);

		if (!pc.getMap().isEscapable() && !pc.isGm()) {
			pc.sendPackets(new S_ServerMessage(647));
			L1Teleport.teleport(pc, pc.getLocation(), pc.getMoveState().getHeading(), false);
			return;
		}

		if (pc.getId() != callClanPc.getCallClanId()) {
			return;
		}

		if (castleId != 0) {
			isInWarArea = true;
			if (WarTimeController.getInstance().isNowWar(castleId)) {
				isInWarArea = false;
			}
		}

		if (mapId != 0 && mapId != 4 && mapId != 304 || isInWarArea) {
			pc.sendPackets(new S_ServerMessage(547));
			return;
		}

		if (mapId == 4 // TODO where is this?
				&& ((callClanPc.getX() >= 33331 && callClanPc.getX() <= 33341
						&& callClanPc.getY() >= 32430 && callClanPc.getY() <= 32441)
						|| (callClanPc.getX() >= 33258 && callClanPc.getX() <= 33267
								&& callClanPc.getY() >= 32396 && callClanPc.getY() <= 32407)
						|| (callClanPc.getX() >= 33388 && callClanPc.getX() <= 33397
								&& callClanPc.getY() >= 32339 && callClanPc.getY() <= 32350) || (callClanPc.getX() >= 33443
						&& callClanPc.getX() <= 33483 && callClanPc.getY() >= 32315 && callClanPc.getY() <= 32357))) {
			pc.sendPackets(new S_ServerMessage(547));
			return;
		}

		L1Map map = callClanPc.getMap();
		int locX = callClanPc.getX();
		int locY = callClanPc.getY();
		int heading = callClanPc.getCallClanHeading();
		locX += HEADING_TABLE_X[heading];
		locY += HEADING_TABLE_Y[heading];
		heading = (heading + 4) % 4;

		boolean isExsistCharacter = false;
		L1Character cha = null;
		for (L1Object object : L1World.getInstance().getVisibleObjects(
				callClanPc, 1)) {
			if (object instanceof L1Character) {
				cha = (L1Character) object;
				if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
					isExsistCharacter = true;
					break;
				}
			}
		}
		if (mapId == 4 && ((locX >= 33331 && locX <= 33341 && locY >= 32430 && locY <= 32441)
						|| (locX >= 33258 && locX <= 33267 && locY >= 32396 && locY <= 32407)
						|| (locX >= 34197 && locX <= 34302 && locY >= 33327 && locY <= 33533)
						|| // twilight mountains
						(locX >= 33453 && locX <= 33468 && locY >= 32331 && locY <= 32341)
						|| // Koreans of Aden
						(locX >= 33388 && locX <= 33397 && locY >= 32339 && locY <= 32350) || (locX >= 33464
						&& locX <= 33531 && locY >= 33168 && locY <= 33248)
				/*
				 * (newX2 >= 33443 && newX2 <= 33483 && newY2 >= 32315 && newY2
				 * <= 32357)
				 */) /* && !pc.isGm() */) {
			pc.sendPackets(new S_ServerMessage(627));
			return;
		}
		if (locX == 0 && locY == 0 || !map.isPassable(locX, locY) || isExsistCharacter) {
			pc.sendPackets(new S_ServerMessage(627));
			return;
		}
		L1Teleport.teleport(pc, locX, locY, mapId, heading, true, L1Teleport.CALL_CLAN);
	}

	private void phone(int id) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE characters SET PartnerID=0 WHERE objid=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void miniGameRemoveEnterMember(L1PcInstance pc) {
		if (GhostHouse.getInstance().isEnterMember(pc))
			GhostHouse.getInstance().removeEnterMember(pc);
		else if (PetRacing.getInstance().isEnterMember(pc))
			PetRacing.getInstance().removeEnterMember(pc);
	}

	private static ClanLeave_joinNameCK leaveNameCK = null;

	class ClanLeave_joinNameCK implements Runnable {
		private final FastMap<String, Long> list;

		public ClanLeave_joinNameCK() {
			list = new FastMap<>();
		}

		@Override
		public void run() {
			while (true) {
				try {
					if (list.size() > 0) {
						FastTable<String> sl = new FastTable<>();
						for (FastMap.Entry<String, Long> e = list.head(), mapEnd = list.tail(); (e = e.getNext()) != mapEnd;) {
							if (e.getValue() < System.currentTimeMillis()) {
								sl.add(e.getKey());
							}
						}
						for (String name : sl) {
							list.remove(name);
						}
						Thread.sleep(1000);
					} else {
						Thread.sleep(5000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void add(String name, long time) {
			list.put(name, time);
		}

		public long ck(String name) {
			long d = 0;
			try {
				d = list.get(name);
			} catch (Exception e) {
			}
			return d;
		}
	}

	private boolean checkDragonKey(L1PcInstance pc) {
		int keyid = pc.드키등록체크id;
		if (keyid == 0)
			return false;
		L1ItemInstance item = pc.getInventory().getItem(keyid);
		if (item != null && item.getItemId() == L1ItemId.DRAGON_KEY) {
			if (BoardTable.getInstance().checkExistkey(keyid, 4212014)) {
				S_ServerMessage sm = new S_ServerMessage(1568);
				pc.sendPackets(sm); // already registered
				sm = null;
				return false;
			} else {
				return true;
			}
		} else {
			S_ServerMessage sm = new S_ServerMessage(1566);
			pc.sendPackets(sm); // must have dragon key
			sm = null;
			return false;
		}
	}

	private static String currentTime() {
		TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(tz);
		int year = cal.get(Calendar.YEAR) - 2000;
		String year2;
		if (year < 10) {
			year2 = "0" + year;
		} else {
			year2 = Integer.toString(year);
		}
		int Month = cal.get(Calendar.MONTH) + 1;
		String Month2 = null;
		if (Month < 10) {
			Month2 = "0" + Month;
		} else {
			Month2 = Integer.toString(Month);
		}
		int date = cal.get(Calendar.DATE);
		String date2 = null;
		if (date < 10) {
			date2 = "0" + date;
		} else {
			date2 = Integer.toString(date);
		}
		return year2 + "/" + Month2 + "/" + date2;
	}

	@Override
	public String getType() {
		return C_ATTR;
	}
}
