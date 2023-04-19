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
package l1j.server.server.datatables;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.utils.SQLUtil;

public class CharBuffTable {
	private CharBuffTable() {
	}

	private static Logger _log = Logger.getLogger(CharBuffTable.class.getName());

	// list of buffs to save
	private static final int[] BUFF_SKILL_LIST = {
			BOUNCE_ATTACK,
			COUNTER_BARRIER,
			EXP_POTION2,
			EXP_POTION3,
			STATUS_DRAGONPERL,
			LIGHT,
			SHAPE_CHANGE, // Pearl, light, shape change
			3,
			99,
			151,
			159,
			168, //Shield, Shadow Armor, Earth Skin, Earth Breath, Iron Skin
			43,
			54,
			1000,
			1001,
			STATUS_ELFBRAVE, // Hay Strike, Greater Hay Strike, Bleak Eve Part, Green Part, Elven Whopper
			52,
			101,
			150, // Hor-Lee Walk, Moving Acceleration, Wind Walk
			26,
			42,
			109,
			110, // PE:DEX, PE:STR,Dress Mighty, Dress Deck Starity
			114,
			115,
			116,
			117, // Growing Aura, Shining Aura, Deviation Eve Aura
			148,
			155,
			177,
			178,
			179,
			163, // Fire Weapon, Fire Breath, Banning Weapon
			149,
			156,
			166, // Window Short, Storm Eye, Storm Short
			1002,
			STATUS_CHAT_PROHIBITED, // Some blue, no chat

			/** Buff Save Packet Order */
			BLESS_WEAPON,
			DECREASE_WEIGHT, DECAY_POTION, SILENCE, VENOM_RESIST, WEAKNESS,
			ENCHANT_ACCURACY, DRESS_EVASION, BERSERKERS, NATURES_TOUCH, WIND_SHACKLE,
			ERASE_MAGIC, ADDITIONAL_FIRE, ELEMENTAL_FALL_DOWN, ELEMENTAL_FIRE,
			STRIKER_GALE, SOUL_OF_FLAME, POLLUTE_WATER, STATUS_TIKAL_BOSSDIE,
			CONCENTRATION, INSIGHT, PANIC, MORTAL_BODY, DESTROY_HORROR, FEAR,
			PATIENCE, IMMUNE_TO_HARM, IMPACT, FORCE_STUN,
			HALPHAS, POTENTIAL,JUDGEMENT,

			/** Elf only buff */
			RESIST_MAGIC,
			CLEAR_MIND,
			CYCLONE,
			RESIST_ELEMENTAL,
			ELEMENTAL_PROTECTION,
			BLOOD_LUST,

			/** lord buff */
			PRIME,
			GRACE_AVATAR,

			/** Colorful package items and celestial potion */
			EXP_POTION_cash,
			EXP_POTION,
			EXP_POTION2,
			EXP_POTION3,
			STATUS_BLUE_POTION2,
			STATUS_FRUIT,
			STATUS_CASHSCROLL,
			STATUS_CASHSCROLL2,
			STATUS_CASHSCROLL3,
			STATUS_커츠투사,
			STATUS_커츠현자,
			STATUS_커츠명궁,
			STATUS_시원한얼음조각,
			STATUS_COMA_3,
			STATUS_COMA_5,

			/** special dish */
			SPECIAL_COOKING,
			// DRAGON_EME_1, DRAGON_EME_2,

			/** Items related to the Mystic Town renewal */
			STATUS_BLUE_POTION3,
			FEATHER_BUFF_A,
			FEATHER_BUFF_B,
			FEATHER_BUFF_C,
			FEATHER_BUFF_D,
			STATUS_UNDERWATER_BREATH,

			/** Reapply cooking level 1 effect */
			COOKING_1_0_N,
			COOKING_1_0_S,
			COOKING_1_1_N,
			COOKING_1_1_S, // cooking
			COOKING_1_2_N, COOKING_1_2_S, COOKING_1_3_N, COOKING_1_3_S,
			COOKING_1_4_N, COOKING_1_4_S, COOKING_1_5_N, COOKING_1_5_S,
			COOKING_1_6_N, COOKING_1_6_S,

			/** Reapply cooking level 2 effect */
			COOKING_1_8_N, COOKING_1_8_S, COOKING_1_9_N, COOKING_1_9_S,
			COOKING_1_10_N, COOKING_1_10_S, COOKING_1_11_N, COOKING_1_11_S,
			COOKING_1_12_N, COOKING_1_12_S, COOKING_1_13_N, COOKING_1_13_S,
			COOKING_1_14_N, COOKING_1_14_S,

			/** Reapply cooking level 3 effect */
			COOKING_1_16_N, COOKING_1_16_S, COOKING_1_17_N, COOKING_1_17_S,
			COOKING_1_18_N, COOKING_1_18_S, COOKING_1_19_N, COOKING_1_19_S,
			COOKING_1_20_N, COOKING_1_20_S, COOKING_1_21_N, COOKING_1_21_S,
			COOKING_1_22_N, COOKING_1_22_S, COOKING_NEW_한우, COOKING_NEW_연어,
			COOKING_NEW_칠면조, COOKING_NEW_닭고기, SPICY_RAMEN, PSY_COOL_DRINK,

			COOKING_NEW_탐한우, COOKING_NEW_탐연어,
			COOKING_NEW_탐칠면조, COOKING_NEW_탐닭고기,

			천하장사버프,
			LEVEL_UP_BONUS,
			POLY_SAFE,
			DRAGON_GROWTH_BUFF,
			STATUS_WISDOM_POTION
			/** blood spatter */
			// DRAGONBLOOD_A, DRAGONBLOOD_P
			// ,강화버프_활력, 강화버프_공격, 강화버프_방어, 강화버프_마법, 강화버프_스턴, 강화버프_홀드
			, FAFU_MAAN, ANTA_MAAN, LIND_MAAN, VALA_MAAN, LIFE_MAAN,
			BIRTH_MAAN, SHAPE_MAAN, HALPAS_MAAN, NEVER_MAAN, MISO_1_ATT, MISO_2_DEF, MISO_3_GRW
			/**Revenge Chaser Time*/
			, REVENGE_TIME
	        };


	/**
	 * Save the list of buffs being applied to the character
	 */
	private static void StoreBuff(int objId, int skillId, int time, int polyId) {
		java.sql.Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO character_buff SET char_obj_id=?, skill_id=?, remaining_time=?, poly_id=?");

			pstm.setInt(1, objId);
			pstm.setInt(2, skillId);
			pstm.setInt(3, time);
			pstm.setInt(4, polyId);
			pstm.executeUpdate();

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 *  Delete character buff list stored in DB
	 */
	public static void DeleteBuff(L1PcInstance pc) {
		java.sql.Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_buff WHERE char_obj_id=?");
			pstm.setInt(1, pc.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	// Check the list of buffs being applied to the character to be saved in the DB
	public static void SaveBuff(L1PcInstance pc) {
		for (int skillId : BUFF_SKILL_LIST) {
			int timeSec = pc.getSkillEffectTimerSet().getSkillEffectTimeSec(skillId);

			if (0 < timeSec) {
				int polyId = 0;
				if (skillId == SHAPE_CHANGE) {
					polyId = pc.getGfxId().getTempCharGfx();
					if (pc.getSkillEffectTimerSet().GetDominationSkill()) polyId *= -1;
				}
				// save to DB
				StoreBuff(pc.getId(), skillId, timeSec, polyId);
				}
			}
		}
	}

