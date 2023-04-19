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

package l1j.server.server.model.item.function;

import l1j.server.Config;
import l1j.server.server.clientpackets.ClientBasePacket;
//import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;

@SuppressWarnings("serial")
public class MagicDoll extends L1ItemInstance {

	public MagicDoll(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			int itemId = this.getItemId();
			useMagicDoll(pc, itemId, this);
		}
	}

	private void useMagicDoll(L1PcInstance pc, int itemId, L1ItemInstance item) {
		if (pc.isInvisble()) {
			return;
		}
		boolean isAppear = true;

		L1DollInstance doll = null;
		for (Object dollObject : pc.getDollList()) {
			doll = (L1DollInstance) dollObject;
			if (doll.getItemObjId() == item.getId()) { //Magic Doll that is already taking out
				isAppear = false;
				break;
			}
		}

		if (isAppear) {

			int npcId = 0;
			int dollType = 0;
			int consumecount = 0;
			int dollTime = 0;

			/*
			 * int castle_id = L1CastleLocation.getCastleIdByArea(pc);//추가 if
			 * (castle_id != 0){ // 인형 공성존에서 사용 불가 if(itemId == 41248 || itemId
			 * == 41249 || itemId == 41250 || itemId == 430000 || itemId ==
			 * 430001 || itemId == 430002 || itemId == 430003 || itemId ==
			 * 430004 || itemId == 430500 || itemId == 430505 || itemId ==
			 * 430506 || itemId == 430501 || itemId == 430502 || itemId ==
			 * 430503 || itemId == 41915 || itemId == 500144 || itemId == 500145
			 * || itemId == 500146 || itemId == 5000034 || itemId == 5000035 ||
			 * itemId == 5000036 || itemId == 430504){ pc.sendPackets(new
			 * S_SystemMessage("\\fY공성지역에서는 사용 할 수 없습니다.")); return; } }
			 */

			switch (itemId) {
			//tier 1
			case L1ItemId.DOLL_BUGBEAR:
				npcId = 80106;
				dollType = L1DollInstance.DOLLTYPE_BUGBEAR;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_WEREWOLF:
				npcId = 80108;
				dollType = L1DollInstance.DOLLTYPE_WEREWOLF;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_CRUSTACEAN:
				npcId = 4500152;
				dollType = L1DollInstance.DOLLTYPE_CRUSTACEAN;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_STONEGOLEM:
				npcId = 4500150;
				dollType = L1DollInstance.DOLLTYPE_STONEGOLEM;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 600241:
				npcId = 1600241;
				dollType = L1DollInstance.DOLLTYPE_WOODCHIP;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Max HP+50."));
				break;
			//case where is magic doll yeti?!

			//tier 2
			case L1ItemId.DOLL_COCKATRICE:
				npcId = 4500155;
				dollType = L1DollInstance.DOLLTYPE_COCKATRICE;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Ranged Damage +1, Ranged Accuracy +1."));
				break;
			case L1ItemId.DOLL_ELDER:
				npcId = 4500151;
				dollType = L1DollInstance.DOLLTYPE_ELDER;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Recover +15 MP every 64 seconds."));
				break;
			case L1ItemId.DOLL_SUCCUBUS:
				npcId = 80107;
				dollType = L1DollInstance.DOLLTYPE_SUCCUBUS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Recover +15 MP every 64 seconds."));
				break;
			case 600242:
				npcId = 1600242;
				dollType = L1DollInstance.DOLLTYPE_LAVAGOLEM;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Melee damage +1, damage reduction +1."));
				break;
			case 500108:
				npcId = 1500108;
				dollType = L1DollInstance.DOLLTYPE_MERMAID;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Experience Bonus +3%."));
				break;
			case L1ItemId.DOLL_SNOWMAN:
				npcId = 4500154;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Melee Damage +1, Melee Hit +1."));
				break;

				// tier 3
			case 500204:
				npcId = 1500204;
				dollType = L1DollInstance.DOLLTYPE_BLACKELDER;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Recover +15 MP every 64 seconds, chance to cast Spell: Call Lightning when attacking"));
				break;
			case 600308:
				npcId = 101142;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDBLACKELDER;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Recover +15 MP every 64 seconds, chance to cast Spell: Call Lightning when attacking, AC-2."));
				break;

			case 60324:
				npcId = 100604;
				dollType = L1DollInstance.DOLLTYPE_DRAKE;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged damage +2, MP recovery +6 (64 seconds)"));
				break;
			case 600311:
				npcId = 101145;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDDRAKE;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged damage +2, MP recovery +6 (64 seconds), AC-2."));
				break;

			case 500203:
				npcId = 1500203;
				dollType = L1DollInstance.DOLLTYPE_GIANT;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("EXP bonus +10%, damage reduction +1, blessing consumption efficiency +5%"));
				break;
			case 600309:
				npcId = 101143;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDGIANT;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("EXP bonus +10%, damage reduction +1, blessing consumption efficiency +5%, AC-2."));
				break;

			case 600243:
				npcId = 1600243;
				dollType = L1DollInstance.DOLLTYPE_DIAMONDGOLEM;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Damage reduction +2"));
				break;
			case 600313:
				npcId = 101147;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDDIAMONDGOLEM;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Damage reduction +2, AC-2."));
				break;

			case 500205:
				npcId = 1500205;
				dollType = L1DollInstance.DOLLTYPE_SUCCUBUSQUEEN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("MP recovery +15 (64 seconds), SP +1."));
				break;
			case 600310:
				npcId = 101144;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDSUCCUBUSQUEEN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("MP recovery +15 (64 seconds), SP +1, AC-2."));
				break;

			case 500110:
				npcId = 1500109;
				dollType = L1DollInstance.DOLLTYPE_KINGBUGBEAR;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Skill resist +8, MP recovery +10 every 64 seconds."));
				break;
			case 600312:
				npcId = 101146;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDKINGBUGGEAR;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Skill resist +8, MP recovery +10 every 64 seconds, AC-2."));
				break;

			//tier 4
			case 600315:
				npcId = 101149;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDCYCLOPS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Melee Damage +2, Melee Accuracy +2, Skill resist +12, AC-2, PvP Bonus Damage +2."));
				break;
			case 500202:
				npcId = 1500202;
				dollType = L1DollInstance.DOLLTYPE_CYCLOPS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Melee Damage +2, Melee hit +2, Skill resist +12."));
				break;

			case 600318:
				npcId = 101152;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDIRIS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("+10 Foe Slayer damage per level, damage reduction +3, AC-2, PvP bonus damage +2."));
				break;
			case 142920:
				npcId = 101134;
				dollType = L1DollInstance.DOLLTYPE_IRIS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("+10 Foe Slayer damage per level, damage reduction +3."));
				break;

			case 600245:
				npcId = 1600245;
				dollType = L1DollInstance.DOLLTYPE_KNIGHTVALD;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Melee Damage +2, Melee Accuracy +2, Skill Accuracy +5"));
				break;
			case 600316:
				npcId = 101150;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDKNIGHTVALD;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Melee Damage +2, Melee Hit +2, Skill Hit +5, AC-2, PvP bonus damage +2"));
				break;

			case 600314:
				npcId = 101148;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDLICH;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Spell Power +2, Max HP+80, AC-2, PvP bonus Damage +2"));
				break;
			case L1ItemId.DOLL_RICH:
				npcId = 45000162;
				dollType = L1DollInstance.DOLLTYPE_LICH;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Spell Power +2, Max HP+80."));
				break;

			case 600320:
				npcId = 101154;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDMUMMYLORD;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Damage reduction +2, EXP bonus +10%, MP recovery +15 (64 seconds), blessing consumption efficiency +8%, AC-2, PvP bonus damage +2"));
				break;
			case 751:
				npcId = 101138;
				dollType = L1DollInstance.DOLLTYPE_MUMMYLORD;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Damage reduction +2, EXP bonus +10%, MP recovery +15 (64 seconds), blessing consumption efficiency +8%"));
				break;

			case 600244:
				npcId = 1600244;
				dollType = L1DollInstance.DOLLTYPE_SEER;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged damage +5, HP recovery +30 (32 seconds)"));
				break;
			case 600317:
				npcId = 101151;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDSEER;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged damage +5, HP recovery +30 (32 seconds), AC-2, PvP bonus damage +2"));
				break;

			case 600319:
				npcId = 101153;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDVAMPIRE;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Melee Damage +2, Melee Hit +2, Fear Hit +3, Skill Resistance +5, AC-2, PvP bonus Damage +2."));
				break;
			case 142921:
				npcId = 101135;
				dollType = L1DollInstance.DOLLTYPE_VAMPIRE;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Melee Damage +2, Melee Hit +2, Fear Hit +3, Skill Resist +5"));
				break;

			//tier 5
			case 600325:
				npcId = 101159;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDBAPHOMET;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Fear Hit +5, Skill Resist +10, AC-3, PvP bonus Damage +2, PvP Damage Reduction +4"));
				break;
			case 753:
				npcId = 101139;
				dollType = L1DollInstance.DOLLTYPE_BAPHOMET;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Fear Hit +5, Skill Resist +10"));
				break;

			case 600323:
				npcId = 101157;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDBARANKA;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Skill Resist +12, Spirit Hit +10, AC-3, PvP bonus Damage +2, PvP Damage Reduction +4."));
				break;
			case 142922:
				npcId = 101136;
				dollType = L1DollInstance.DOLLTYPE_BARANKA;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Skill Resist +12, Spirit Hit +10"));
				break;

			case 600322:// 축데스
				npcId = 101156;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDDEATHKNIGHT;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Damage Reduction +5, EXP Bonus +20%, Blessing Consumption Efficiency +10%, Magic Activation: Hellfire, AC-3, PvP bonus Damage +2, PvP Damage Reduction +4."));
				break;
			case 600247:
				npcId = 1600247;
				dollType = L1DollInstance.DOLLTYPE_DEATHKNIGHT;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Damage reduction +5, experience bonus +20%, blessing consumption efficiency +10%, magic activation: Hellfire."));
				break;

			case 600321:
				npcId = 101155;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDDEMON;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Skill Resist +12, Skill hit +10, AC-3, PvP bonus Damage +2, PvP Damage Reduction +4"));
				break;
			case 600246:
				npcId = 1600246;
				dollType = L1DollInstance.DOLLTYPE_DEMON;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Skill Resistance +12, Skill Accuracy +10"));
				break;

			case 600326:
				npcId = 101160;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDICEQUEEN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged Damage +5, Ranged hit +5, Skill Resist +10, Spirit Hit +5, AC-3, PvP bonus Damage +2, PvP Damage Reduction +4"));
				break;
			case 754:
				npcId = 101140;
				dollType = L1DollInstance.DOLLTYPE_ICEQUEEN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged Damage +5, Ranged Hit +5, Skill Resist +10, Spirit Hit +5"));
				break;

			case 600327:
				npcId = 101161;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDKURTZ;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("AC-5, +10 Foe Slayer damage per level, damage reduction +3, Skill resist +10, Draogn hit +5, PvP bonus damage +2, PvP damage reduction +4"));
				break;
			case 755:
				npcId = 101141;
				dollType = L1DollInstance.DOLLTYPE_KURTZ;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("AC-2, +10 Foe Slayer damage per level, Damage Reduction +3, Skill Resist +10, Dragon Hit +5"));
				break;

			case 600324:
				npcId = 101158;
				dollType = L1DollInstance.DOLLTYPE_BLESSEDTARAK;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("SP+3, Skill Resist +10, Magic Accuracy +5, Dragon Accuracy +5, AC-3, PvP bonus Damage +2, PvP Damage Reduction +4."));
				break;
			case 752:
				npcId = 101137;
				dollType = L1DollInstance.DOLLTYPE_TARAK;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("SP+3, Skill Resist +10, Magic Hit +5, Dragon Hit +5"));
				break;

			//tier 6

			//tier etc



			case L1ItemId.DOLL_SEADANCER:
				npcId = 4500153;
				dollType = L1DollInstance.DOLLTYPE_SEADANCER;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_DRAGON_M:
				npcId = 4500156;
				dollType = L1DollInstance.DOLLTYPE_DRAGON_M;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_DRAGON_W:
				npcId = 4500157;
				dollType = L1DollInstance.DOLLTYPE_DRAGON_W;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_HIGH_DRAGON_M:
				npcId = 4500158;
				dollType = L1DollInstance.DOLLTYPE_HIGH_DRAGON_M;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_HIGH_DRAGON_W:
				npcId = 4500159;
				dollType = L1DollInstance.DOLLTYPE_HIGH_DRAGON_W;
				consumecount = 50;
				dollTime = 18000;
				pc.sendPackets(new S_SystemMessage(
						"MP 회복+4, 일정 확률로 독 공격의 효과 적용"));
				break;
			case L1ItemId.DOLL_LAMIA:
				npcId = 4500160;
				dollType = L1DollInstance.DOLLTYPE_RAMIA;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"MP 회복+4, 일정 확률로 독 공격의 효과 적용"));
				break;
			case L1ItemId.DOLL_SPARTOI:
				npcId = 4500161;
				dollType = L1DollInstance.DOLLTYPE_SPARTOI;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Melee damage +2, Stun resistance +10."));
				break;
			case 141920:
				npcId = 4500161;
				dollType = L1DollInstance.DOLLTYPE_SPARTOI;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Melee damage +2, Stun resistance +10."));
				break;
			case 141919:
				npcId = 4500160;
				dollType = L1DollInstance.DOLLTYPE_RAMIA;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"MP 회복+4, 일정 확률로 독 공격의 효과 적용"));
				break;
			case 141922: // ettin
				npcId = 45000161;
				dollType = L1DollInstance.DOLLTYPE_ETTIN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Hasted, AC-2."));
				break;
			case 141921:// 허수아비
				npcId = 41915;
				dollType = L1DollInstance.DOLLTYPE_HUSUABI;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"원거리 명중+2, 근거리 명중+2, HP+50, MP+30"));
				break;
			case 141918:// 시댄서
				npcId = 4500153;
				dollType = L1DollInstance.DOLLTYPE_SEADANCER;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"32초마다 HP 회복 +25"));
				break;
			case 500109:// 눈사람
				npcId = 1500110;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN2;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"근거리 대미지+1, 근거리 명중+1"));
				break;
			case 600234:// 안씀
				npcId = 1600234;
				dollType = L1DollInstance.DOLLTYPE_EVENTDOLL;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"기술 내성+8, MP 절대 회복+10(64초)"));
				break;
			case 600259:
				npcId = 1600259;
				dollType = L1DollInstance.DOLLTYPE_ANTHARAS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Damage reduction +6, EXP +25%, Skill resist +8, Spirit resist +8, Word resist +8, Fear resist +8, MP recovery +15 (64 seconds), blessing consumption efficiency +10%, AC-3, PvP bonus +4, PvP damage reduction +2"));
				break;
			case 600263: // timed 3 days
				npcId = 1600259;
				dollType = L1DollInstance.DOLLTYPE_ANTHARAS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Damage reduction +6, EXP bonus +25%, MP recovery +15 (64 seconds), blessing consumption efficiency +10%, AC-3, PvP damage +4, PvP damage reduction +2"));
				break;
			case 600260:
				npcId = 1600260;
				dollType = L1DollInstance.DOLLTYPE_FAFURION;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"SP+4, Magic Hit +8, Skill Hit +3, Spirit Hit +3, Dragon Hit +3, Horror Hit +3, Skill Resist +8, Spirit Resist +8, Dragon Resist +8, Fear Resist +8, MP Recovery +5 (64 seconds), AC-3, PvP Damage +4, PvP Damage Reduction +2"));
				break;
			case 600264:
				npcId = 1600260;
				dollType = L1DollInstance.DOLLTYPE_FAFURION;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"SP+4, Magic Hit +8, Skill Hit +3, Spirit Hit +3, Dragon Hit +3, Horror Hit +3, Skill Resist +8, Spirit Resist +8, Dragon Resist +8, Fear Resist +8, MP Recovery +5 (64 seconds), AC-3, PvP Damage +4, PvP Damage Reduction +2"));
				break;
			case 600261:
				npcId = 1600261;
				dollType = L1DollInstance.DOLLTYPE_LINDVIOR;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Ranged Damage +4, Ranged Hit +8, Skill Hit +3, Spirit Hit +3, Dragon Hit +3, Horror Hit +3, Skill Resist +8, Spirit Resist +8, Dragon Resist +8, Fear Resist +8, MP recovery +5 (64 seconds), AC-3, PvP damage +4, PvP damage reduction +2"));
				break;
			case 600265:
				npcId = 1600261;
				dollType = L1DollInstance.DOLLTYPE_LINDVIOR;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Ranged Damage +4, Ranged Hit +8, Skill Hit +3, Spirit Hit +3, Dragon Hit +3, Horror Hit +3, Skill Resist +8, Spirit Resist +8, Dragon Resist +8, Fear Resist +8, MP recovery +5 (64 seconds), AC-3, PvP Damage +4, PvP Damage Reduction +2"));
				break;
			case 600262:
				npcId = 1600262;
				dollType = L1DollInstance.DOLLTYPE_VALAKAS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Melee Damage +4, Melee Hit +8, Skill Hit +3, Spirit Hit +3, Dragon Hit +3, Horror Hit +3, Skill Resist +8, Spirit Resist +8, Dragon Resist +8, Fear Resist +8, MP recovery +5 (64 seconds), AC-3, PvP Damage +4, PvP Damage Reduction +2"));
				break;
			case 600266:
				npcId = 1600262;
				dollType = L1DollInstance.DOLLTYPE_VALAKAS;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage(
						"Melee Damage +4, Melee Hit +8, Skill Hit +3, Spirit Hit +3, Dragon Hit +3, Horror Hit +3, Skill Resist +8, Spirit Resist +8, Dragon Resist +8, Fear Resist +8, MP recovery +5 (64 seconds), AC-3, PvP Damage +4, PvP Damage Reduction +2"));
				break;

			case L1ItemId.DOLL_GremRin:
				npcId = 100882;
				dollType = L1DollInstance.DOLLTYPE_GREMLIN;
				consumecount = 50;
				dollTime = 1800;
				break;

			case L1ItemId.DOLL_ETIN:
				npcId = 45000161;
				dollType = L1DollInstance.DOLLTYPE_ETTIN;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Haste, AC-2."));
				break;
			case L1ItemId.DOLL_PHENIX:
				npcId = 45000163;
				dollType = L1DollInstance.DOLLTYPE_ETTINREMOVE;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Spell Power +2, Max HP+80."));
				break;
			case 500144:
				npcId = 700196;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN_A;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500145:
				npcId = 700197;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN_B;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500146:
				npcId = 700198;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN_C;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 41915:
				npcId = 41915;
				dollType = L1DollInstance.DOLLTYPE_HUSUABI;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ranged hit +2, melee hit +2, HP+50, MP+30."));
				break;
			case 141915:
				npcId = 141915;
				dollType = L1DollInstance.DOLLTYPE_HW_HUSUABI;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 141916:
				npcId = 101033;
				dollType = L1DollInstance.DOLLTYPE_튼튼한기사;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 1419161:
				npcId = 1010331;
				dollType = L1DollInstance.DOLLTYPE_행운의기사; // lucky knight
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("\\fSItem drop rate doubled/EXP bonus increased by 30%"));
				break;
			case 5000045:
				npcId  = 45000364;
				dollType = L1DollInstance.DOLLTYPE_헌신1등급;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 437018:
				npcId = 4000009;
				dollType = L1DollInstance.DOLLTYPE_HELPER;
				consumecount = 50;
				dollTime = 300;
				break;
			case 60173:
				npcId = 100320;
				dollType = L1DollInstance.DOLLTYPE_BLEG;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60174:
				npcId = 100321;
				dollType = L1DollInstance.DOLLTYPE_LEDEGG;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60175:
				npcId = 100322;
				dollType = L1DollInstance.DOLLTYPE_ELEG;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60176:
				npcId = 100323;
				dollType = L1DollInstance.DOLLTYPE_GREG;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60261:
				npcId = 100431;
				dollType = L1DollInstance.DOLLTYPE_GANGNAM;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60262:
				npcId = 100432;
				dollType = L1DollInstance.DOLLTYPE_GANGNAM;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60263:
				npcId = 100433;
				dollType = L1DollInstance.DOLLTYPE_GANGNAM;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60309:
				npcId = 100579;
				dollType = L1DollInstance.DOLLTYPE_DANDY;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60310:
				npcId = 100580;
				dollType = L1DollInstance.DOLLTYPE_SERIE;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60447:
				npcId = 100677;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60448:
				npcId = 100678;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60449:
				npcId = 100679;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60450:
				npcId = 100680;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60451:
				npcId = 100681;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60452:
				npcId = 100682;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60453:
				npcId = 100683;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60454:
				npcId = 100684;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60455:
				npcId = 100685;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60456:
				npcId = 100686;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60457:
				npcId = 100687;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60458:
				npcId = 100688;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60459:
				npcId = 100689;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60460:
				npcId = 100690;
				dollType = L1DollInstance.DOLLTYPE_남자_여자;
				consumecount = 500;
				dollTime = 18000;
				break;
			}
			if (itemId >= 60173 && itemId <= 60176) {
				if (!pc.getInventory().checkItem(41159, consumecount)) {
					pc.sendPackets(new S_ServerMessage(337, "$5116"), true);
					return;
				}
			} else {
				if (!pc.getInventory().checkItem(41246, consumecount)) {
					pc.sendPackets(new S_ServerMessage(337, "$5240"), true);
					return;
				}
			}
			if (pc.getDollListSize() >= Config.MAX_DOLL_COUNT) {
				// doll use
				doll.deleteDoll();
				//pc.sendPackets(new S_SkillIconGFX(56, 0), true);
				pc.sendPackets(new S_OwnCharStatus(pc), true);
				pc.sendPackets(
						new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()),
						true);
			}
			if (itemId == 437018 && pc.getLevel() > 70) {
				pc.sendPackets(new S_SystemMessage(
						"Magic Doll: Princess can only be used up to level 70."), true);
				return;
			}
			L1Npc template = NpcTable.getInstance().getTemplate(npcId);
			doll = new L1DollInstance(template, pc, dollType, item.getId(),
					dollTime * 1000);
			pc.sendPackets(new S_SkillSound(doll.getId(), 5935), true);
			Broadcaster.broadcastPacket(pc,
					new S_SkillSound(doll.getId(), 5935), true);

			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.SUMMON_PET_NOTI, dollTime, doll, item, true));

			pc.sendPackets(new S_OwnCharStatus(pc), true);
			pc.sendPackets(
					new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
			pc.sendPackets(new S_ServerMessage(1143), true);
			if (itemId >= 60173 && itemId <= 60176)
				pc.getInventory().consumeItem(41159, consumecount);
			else
				pc.getInventory().consumeItem(41246, consumecount);
		} else {
			doll.deleteDoll();
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.SUMMON_PET_NOTI, -1, doll, item, false));
			pc.sendPackets(new S_OwnCharStatus(pc), true);
			pc.sendPackets(
					new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
		}
		pc.sendPackets(new S_ItemName(this), true);
	}

	/*
	 * private void DollMent(L1PcInstance pc, int itemObjectId){ switch
	 * (itemObjectId) { case L1ItemId.DOLL_BUGBEAR: pc.sendPackets(new
	 * S_SystemMessage("\\fS마법인형이 무게 게이지를 10% 늘려줍니다."), true); break; case
	 * L1ItemId.DOLL_SUCCUBUS: pc.sendPackets(new
	 * S_SystemMessage("\\fS마법인형에 의해 1분에 MP가 15씩 회복됩니다."), true); break; case
	 * L1ItemId.DOLL_ELDER: pc.sendPackets(new
	 * S_SystemMessage("\\fS마법인형에 의해 확율마법추타 1분에 MP가 18씩 회복됩니다."), true); break;
	 * case L1ItemId.DOLL_WAREWOLF: pc.sendPackets(new
	 * S_SystemMessage("\\fS7%확률로 물리추가타격 +15 효과가 발동됩니다."), true); break; case
	 * L1ItemId.DOLL_CRUSTACEA: pc.sendPackets(new
	 * S_SystemMessage("\\fS7%확률로 물리추가타격 +15 효과가 발동됩니다."), true); break; case
	 * L1ItemId.DOLL_행운의기사: pc.sendPackets(new
	 * S_SystemMessage("\\fS아이템드랍율 2배상승 / 경험치보너스 30% 증가"), true); break; case
	 * L1ItemId.DOLL_STONEGOLEM: pc.sendPackets(new
	 * S_SystemMessage("\\fS10%확률로 15씩 데미지가 경감됩니다."), true); break; case
	 * L1ItemId.DOLL_SEADANCER: pc.sendPackets(new
	 * S_SystemMessage("\\fS1분에 HP가 70씩 회복됩니다."), true); break; case
	 * L1ItemId.DOLL_SNOWMAN: pc.sendPackets(new
	 * S_SystemMessage("\\fSAC -3, 동빙내성 +7 효과가 유지됩니다."), true); break; case
	 * L1ItemId.DOLL_COCATRIS: pc.sendPackets(new
	 * S_SystemMessage("\\fS활 명중 +1, 활 추가타격 +1 효과가 발동됩니다."), true); break; case
	 * L1ItemId.DOLL_DRAGON_M: case L1ItemId.DOLL_DRAGON_W: pc.sendPackets(new
	 * S_SystemMessage("\\fS엠틱 +4 증가 효과가  발동됩니다."), true); break; case
	 * L1ItemId.DOLL_HIGH_DRAGON_M: case L1ItemId.DOLL_HIGH_DRAGON_W:
	 * pc.sendPackets(new S_SystemMessage("\\fS엠틱 4증가 무게 게이지 5%증가 효과가  발동됩니다."),
	 * true); break; case L1ItemId.DOLL_LAMIA: pc.sendPackets(new
	 * S_SystemMessage("\\fS엠틱+4, 독공격 의 효과가  발동됩니다."), true); break; case
	 * L1ItemId.DOLL_SPATOI: pc.sendPackets(new
	 * S_SystemMessage("\\fS근거리 추가타격 +2, 스턴내성 +10효과가  발동됩니다."), true); break;
	 * case L1ItemId.DOLL_ETIN: pc.sendPackets(new
	 * S_SystemMessage("\\fS무한 헤이스트(속도관련 디버프 무시) Ac-2, 홀드내성 +10 효과가  발동됩니다."),
	 * true); break; case 41915: pc.sendPackets(new
	 * S_SystemMessage("\\fS활명중+2 명중+2 HP+50 MP+30 효과가 발동됩니다."), true); break;
	 * case 500144: pc.sendPackets(new
	 * S_SystemMessage("\\fS경험치10%증가, 활명중+5 효과가 발동됩니다."), true); break; case
	 * 500145: pc.sendPackets(new
	 * S_SystemMessage("\\fS경험치10%증가, 1분당 MP 20회복 효과가  발동됩니다."), true); break;
	 * case 500146: pc.sendPackets(new
	 * S_SystemMessage("\\fS경험치10%증가, 32초당 Hp200회복 효과가  발동됩니다."), true); break;
	 * case L1ItemId.DOLL_RICH: pc.sendPackets(new
	 * S_SystemMessage("\\fS추가타격+2, 공격성공+1, 활추타+2, 활명중+1, SP+3"), true);
	 * pc.sendPackets(new
	 * S_SystemMessage("\\fS데미지리덕+3, 스턴내성+10, 홀드내성+10, 마방+10,"), true);
	 * pc.sendPackets(new
	 * S_SystemMessage("\\fSHP+30, MP+40, 엠틱+20 효과가 발동됩니다.")); break; case
	 * L1ItemId.DOLL_PHENIX: pc.sendPackets(new
	 * S_SystemMessage("\\fS스펠파워+3, 엠틱+20, 데미지리덕+3, "), true);
	 * pc.sendPackets(new S_SystemMessage("\\fS스턴내성+10, 홀드내성+10, 마방+10, "),
	 * true); pc.sendPackets(new S_SystemMessage("\\fSHP+30, MP+40 효과가 발동됩니다."),
	 * true); break; } }
	 */

}

