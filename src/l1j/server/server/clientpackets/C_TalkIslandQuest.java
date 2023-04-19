package l1j.server.server.clientpackets;

import java.io.IOException;

import l1j.server.Config;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.QuestInfoTable;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_QuestTalkIsland;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1QuestInfo;
import l1j.server.server.templates.L1QuestView;
import l1j.server.server.utils.CalcExp;
import server.LineageClient;

public class C_TalkIslandQuest extends ClientBasePacket {
	private static final String C_CludiaQuest = "[C] C_CludiaQuest";
	private static final int subQuestIds[] = { 309 }; // Complete unimplemented.

	public C_TalkIslandQuest(byte[] decrypt, LineageClient client) throws IOException {
		super(decrypt);

		L1PcInstance pc = client.getActiveChar();
		if (pc == null)
			return;
		int type = readH();
		if (type == 0x208 || type == 0x020a || type == 0x020c || type == 0x020f) {
		}
		switch (type) {
		case 0x0208: { // Register new quest
			readP(3);
			int nextid = readBit();
			//synchronized (pc.syncTalkIsland) {
				L1QuestInfo Q_info = pc.getQuestList(nextid);
				if (Q_info != null)
					return;

				Q_info = new L1QuestInfo();
				Q_info.quest_id = nextid;

				pc.quest_list.put(nextid, Q_info);
				if (nextid == 259) { // lantern
					pc.getInventory().storeItem(60154, 1);
				}

				if (nextid == 260) { // Trainee Transformation Scroll
					pc.getInventory().storeItem(40096, 5);
				}

				if (nextid == 262) { // Trainer's Armor Magic Scroll
					pc.getInventory().storeItem(60718, 1);
				}

				if (nextid == 264) { // Trainer's Weapon Magic Scroll
					pc.getInventory().storeItem(60717, 1);
				}
				if (nextid == 267) { // Scroll of Escape
					pc.getInventory().storeItem(40095, 1);
				}
				if (nextid == 268) { // Trainer's Weapon Magic Scroll
					pc.getInventory().storeItem(60717, 1);
				}
				if (nextid == 274) { // Solvent
					pc.getInventory().storeItem(20089, 1);
					pc.getInventory().storeItem(41245, 10);
				}
				if (nextid == 275) { // summon magic doll
					pc.getInventory().storeItem(430506, 1);
					pc.getInventory().storeItem(41246, 50);
				}
				if (nextid == 276) { // Condensed Flame Energy
					pc.getInventory().storeItem(60738, 1);
				}

				if (nextid == 282) { // Trainer's Accessory Magic Scroll
					pc.getInventory().storeItem(60731, 1);
					pc.getInventory().storeItem(21100, 1);
				}

				/*if (nextid == 361) { // Create Gereng's Magic Pouch
					pc.getInventory().TalkIsland_storeItem(6012, 1);
				}*/
				pc.sendPackets(new S_QuestTalkIsland(9, nextid));
		//	}
		}
			break;
		case 0x020a: { // time update
			readP(3);
			int startid = readBit();

			L1QuestInfo info = pc.getQuestList(startid);
			boolean isSub = false;
			if (info == null || info.end_time != 0)
				return;

			if (info.st_time == 0)
				info.st_time = System.currentTimeMillis();


			if (startid == 257) { // In case of level quest, information update/attack training
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 5) {
					info.ck[0] = 5;
				}
			}

			if (startid == 266) {
				info.ck[0] = 1;
			}
			if (startid == 268) { // use return scroll
				info.ck[0] = 1;
			}
			if (startid == 269) {
				info.ck[0] = 1;
			}
			if (startid == 279) { // In the case of level quests, achieve level 35 for updating information
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 35) {
					info.ck[0] = 35;
				}
			}
			if (startid == 286) {
				info.ck[0] = 1;
			}

			if (startid == 290) {
				info.ck[0] = 1;
			}

			if (startid == 292) {
				info.ck[0] = 1;
			}

			if (startid == 303) {
				info.ck[0] = 1;
			}

			/*if (startid == 309) {
				info.ck[0] = 1;
			}*/

			if (startid == 308) {
				info.ck[0] = 1;
				info.ck[1] = 1;
				info.ck[2] = 1;
				info.ck[3] = 1;
			}

			if (startid == 283) { // quest, level 40
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 40) {
					info.ck[0] = 40;
				}
			}

			if (startid == 287) { // quest, level 45
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 45) {
					info.ck[0] = 45;
				}
			}

			if (startid == 291) { // quest, level 48
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 48) {
					info.ck[0] = 48;
				}
			}

			if (startid == 294) { // quest, level 50
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 50) {
					info.ck[0] = 50;
				}
			}

			if (startid == 297) { // quest, level 52
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 52) {
					info.ck[0] = 52;
				}
			}

			if (startid == 300) { // quest, level 54
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 54) {
					info.ck[0] = 54;
				}
			}

			if (startid == 302) { // quest, level 55
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 55) {
					info.ck[0] = 55;
				}
			}

			/*if (startid == 361) { // In the case of level quest, level 70
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 70) {
					info.ck[0] = 70;
				}
			}*/

			for (int isSubQuestId : subQuestIds) {
				if (isSubQuestId == startid) {
					isSub = true;
					info.ck[0] = 1; // If it is a sub-quest, it is set to complete immediately
					break;
				}
			}

			pc.sendPackets(new S_QuestTalkIsland(pc, startid, info));

			if (isSub) {
				// System.out.println("서브퀘스트 완료 : " + startid);
				info.end_time = System.currentTimeMillis();
				pc.sendPackets(new S_QuestTalkIsland(14, startid)); // Quest ending right away
			}
		}
			break;
		case 0x020c: { // Receive Quest Rewards
			try {
				if (Config.STANDBY_SERVER) {
					pc.sendPackets(new S_SystemMessage("\\aHNotice: 오픈대기 상태에선 해당 행동이 불가능 합니다 ."), true);
//					pc.sendPackets("정식오픈후 다시 한번 수련자의 속도향상 물약을 클릭하여,퀘스트를 진행하세요");
					return;
				}
				readP(3);
				int questid = readBit();

				L1QuestInfo E_info = pc.getQuestList(questid);
				if (E_info == null || E_info.end_time != 0 || E_info.ck[0] == 0)
					return;

				E_info.end_time = System.currentTimeMillis();

				/*
				 * r If necessary, the material is checked and deleted.
				 */
				if (questid == 258 // food
						|| questid == 263 // 뼈조각
						|| questid == 273 // 무기 도면
						|| questid == 281 // 이상한 뼈조각
						|| questid == 285 // 이상한 장신구 조각
						|| questid == 289 // 이상한 눈알
						|| questid == 298 // 보석
						|| questid == 304 // 룬 만들기 1단계
						|| questid == 305 // 룬 만들기 2단계
						|| questid == 306 // 룬 만들기 3단계
						|| questid == 307 // 룬 만들기 4단계
						|| questid == 308 // 룬 만들기 4단계
				) {
					L1QuestView view = QuestInfoTable.getInstance().getQuestView(questid);
					if (view != null) { // Just in case, check null
						for (int i = 0; i < 4; i++) {
							if (view.pick_item[i] != 0) { // material item
								if (!pc.getInventory().checkItem(view.pick_item[i], view.max_count[i])) {
									pc.sendPackets(new S_SystemMessage("Insufficient materials."));
									return;
								}
							}
						}
						// Delete if material exists
						for (int i = 0; i < 4; i++) {
							if (view.pick_item[i] != 0) {
								//pc.getInventory().consumeItem(view.pick_item[i], view.max_count[i]);
								pc.getInventory().consumeItem(view.pick_item[i]);
							}
						}
					}
				}

				/*
				 * 279~286 45 achievement quest 320 Ana Clan, 322 Ancient Giant, 324 Jinus 329 Dead King, 341
				 * Lightning Dragon Kashube, 346 Water Dragon Shark 350 Spirit King's Confrontation,
				 */


				if (questid == 301) {
					L1Teleport.teleport(pc, 32562, 33089,(short) 9, 5, true, true, 5000);
					pc.sendPackets(new S_SystemMessage("\\aH알림: 네르바에게 대화를 걸어 '엘릭서의 룬' 제작 하세요 ."), true);
				}

				int cooktype = 0;
				if (questid == 290) {
					readC();
					cooktype = readC() + 1;
				}
				quest_storeitem(pc, questid, cooktype);

				pc.sendPackets(new S_QuestTalkIsland(13, questid));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case 0x020f: { // Click Go Quest
			readP(3);
			int telid = readBit();
			L1QuestView view = QuestInfoTable.getInstance().getQuestView(telid);
			if (view != null) {
				if (view.tel[1] != 0) {
					//L1Teleport.teleport(pc, view.tel[1], view.tel[2],(short) view.tel[0], 5, true, true, 5000);
					pc.start_teleport(view.tel[1], view.tel[2], (short) view.tel[0], 5, 169, true, true);
					pc.sendPackets(new S_QuestTalkIsland(16, telid)); // close window

				} else {
					System.out.println("Unregistered quest tel id =" + telid);
				}
			}
		}
			break;
		}

	}

	/**
	 * Quest item payment
	 */
	private void quest_storeitem(L1PcInstance pc, int questid, int type) {
		try {
			// If you are registered on the quest list
			if (QuestInfoTable.getInstance().getQuestViewid(questid)) {
				L1QuestView view = QuestInfoTable.getInstance().getQuestView(questid);
				if (view == null) {
					return;
				}
				if (view._exp != 0) {
					CalcExp.AddExp(pc, view._exp);
					if (pc.getLevel() < 35 && questid == 278) { // Reach level 35
						pc.setExp(ExpTable.getExpByLevel(35));
					} else if (pc.getLevel() < 40 && questid == 282) { // Reach level 40
						pc.setExp(ExpTable.getExpByLevel(40));
					} else if (pc.getLevel() < 45 && questid == 286) { // Reach level 45
						pc.setExp(ExpTable.getExpByLevel(45));
					} else if (pc.getLevel() < 48 && questid == 290) { // Reach level 48
						pc.setExp(ExpTable.getExpByLevel(48));
					} else if (pc.getLevel() < 50 && questid == 293) { // Reach level 50
						pc.setExp(ExpTable.getExpByLevel(50));
					} else if (pc.getLevel() < 52 && questid == 296) { // Reach level 52
						pc.setExp(ExpTable.getExpByLevel(52));
					} else if (pc.getLevel() < 54 && questid == 299) { // Reach level 54
						pc.setExp(ExpTable.getExpByLevel(54));
					} else if (pc.getLevel() < 55 && questid == 301) { // Reach level 55
						pc.setExp(ExpTable.getExpByLevel(55));
					}
				}
				for (int i = 0; i < 4; i++) {
					if (view.item_id[i] != 0) {
						pc.getInventory().storeItem(view.item_id[i], view.item_count[i]);
					}
				}

				if (type != 0) {
					pc.getInventory().storeItem(60725 + type, 1);
				} else if (type != 0 && questid == 303) {
					pc.getInventory().storeItem(50751, 1); // Warrior's Welcome Potion Box
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getType() {
		return C_CludiaQuest;
	}
}
