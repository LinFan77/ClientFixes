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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.PerformanceTimer;
import l1j.server.server.utils.SQLUtil;

public class SkillsTable {
	private static Logger _log = Logger.getLogger(SkillsTable.class.getName());
	private static SkillsTable _instance;
	private final Map<Integer, L1Skills> _skills = new HashMap<>();
	private final boolean _initialized;

	public static SkillsTable getInstance() {
		if (_instance == null) {
			_instance = new SkillsTable();
		}
		return _instance;
	}

	private SkillsTable() {
		_initialized = true;
		RestoreSkills();
	}

	public static void reload() {
		SkillsTable oldInstance = _instance;
		_instance = new SkillsTable();
		oldInstance._skills.clear();
		oldInstance = null;
	}

	private void RestoreSkills() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM skills");
			rs = pstm.executeQuery();
			FillSkillsTable(rs);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "error while creating skills table", e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void FillSkillsTable(ResultSet rs) throws SQLException {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[SkillsTable] loading...");

		L1Skills l1skills = null;
		while (rs.next()) {
			l1skills = new L1Skills();
			int skill_id = rs.getInt("skill_id");
			l1skills.setSkillId(skill_id);
			l1skills.setName(rs.getString("name"));
			l1skills.setSkillLevel(rs.getInt("skill_level"));
			l1skills.setSkillNumber(rs.getInt("skill_number"));
			l1skills.setMpConsume(rs.getInt("mpConsume"));
			l1skills.setHpConsume(rs.getInt("hpConsume"));
			l1skills.setItemConsumeId(rs.getInt("itemConsumeId"));
			l1skills.setItemConsumeCount(rs.getInt("itemConsumeCount"));
			l1skills.setReuseDelay(rs.getInt("reuseDelay"));
			l1skills.setBuffDuration(rs.getInt("buffDuration"));
			l1skills.setTarget(rs.getString("target"));
			l1skills.setTargetTo(rs.getInt("target_to"));
			l1skills.setDamageValue(rs.getInt("damage_value"));
			l1skills.setDamageDice(rs.getInt("damage_dice"));
			l1skills.setDamageDiceCount(rs.getInt("damage_dice_count"));
			l1skills.setProbabilityValue(rs.getInt("probability_value"));
			l1skills.setProbabilityDice(rs.getInt("probability_dice"));
			l1skills.setAttr(rs.getInt("attr"));
			l1skills.setType(rs.getInt("type"));
			l1skills.setLawful(rs.getInt("lawful"));
			l1skills.setRanged(rs.getInt("ranged"));
			l1skills.setArea(rs.getInt("area"));
			l1skills.setThrough(rs.getBoolean("through"));
			l1skills.setId(rs.getInt("id"));
			l1skills.setNameId(rs.getString("nameid"));
			l1skills.setActionId(rs.getInt("action_id"));
			l1skills.setCastGfx(rs.getInt("castgfx"));
			l1skills.setCastGfx2(rs.getInt("castgfx2"));
			l1skills.setSysmsgIdHappen(rs.getInt("sysmsgID_happen"));
			l1skills.setSysmsgIdStop(rs.getInt("sysmsgID_stop"));
			l1skills.setSysmsgIdFail(rs.getInt("sysmsgID_fail"));
			l1skills.setScrollReuseDelay(rs.getInt("Scroll_use_delay"));

			_skills.put(skill_id, l1skills);
		}
		_log.config("Skill count " + _skills.size() + ": loaded");
		System.out.println("OK! " + timer.get() + "ms");
	}

	public void spellMastery(int playerobjid, int skillid, String skillname,
			int active, int time) {
		if (spellCheck(playerobjid, skillid)) {
			return;
		}
		L1PcInstance pc = (L1PcInstance) L1World.getInstance().findObject(
				playerobjid);
		if (pc != null) {
			pc.setSkillMastery(skillid);
		}

		Connection con = null;
		PreparedStatement pstm = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO character_skills SET char_obj_id=?, skill_id=?, skill_name=?, is_active=?, activetimeleft=?");
			pstm.setInt(1, playerobjid);
			pstm.setInt(2, skillid);
			pstm.setString(3, skillname);
			pstm.setInt(4, active);
			pstm.setInt(5, time);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void spellLost(int playerobjid, int skillid) {
		L1PcInstance pc = (L1PcInstance) L1World.getInstance().findObject(
				playerobjid);
		if (pc != null) {
			pc.removeSkillMastery(skillid);
		}

		Connection con = null;
		PreparedStatement pstm = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM character_skills WHERE char_obj_id=? AND skill_id=?");
			pstm.setInt(1, playerobjid);
			pstm.setInt(2, skillid);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	public boolean spellCheck(int playerobjid, int skillid) {
		boolean ret = false;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=? AND skill_id=?");
			pstm.setInt(1, playerobjid);
			pstm.setInt(2, skillid);
			rs = pstm.executeQuery();
			if (rs.next())
				ret = true;
			else
				ret = false;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return ret;
	}

	public boolean isInitialized() {
		return _initialized;
	}

	public L1Skills getTemplate(int i) {
		return _skills.get(i);
	}

	public L1Skills getTemplateByItem(int itemid) {
		int skillid = 0;
		switch (itemid) {
		case 55594: skillid = L1SkillId.ARMOUR_BREAK_DESTINY; break;
		case 55595: skillid = L1SkillId.DOUBLE_BREAK_DESTINY; break;
		case 55596: skillid = L1SkillId.COUNTER_BARRIER_VETERAN; break;
		case 6002651: skillid = L1SkillId.REDUCTION_ARMOUR_VETERAN; break;
		case 6002652: skillid = L1SkillId.RAGING_FORCE; break;
		case 55597: skillid = L1SkillId.THUNDER_GRAB_BRAVE; break;
		case 55598: skillid = L1SkillId.FOE_SLAYER_BRAVE; break;
		case 439107: skillid = L1SkillId.FEAR; break;
		case 439112: skillid = L1SkillId.DESTROY_HORROR; break;
		case 439100: skillid = L1SkillId.DRAGON_SKIN; break;
		case 55599: skillid = L1SkillId.DESPERADO_ABSOLUTE; break;
		case 600358: skillid = L1SkillId.DARK_HORSE; break;
		case 600360: skillid = L1SkillId.AURACIA; break;
		case 600361: skillid = L1SkillId.파이널번; break;
		case 40271: skillid = L1SkillId.BURNING_SPIRIT; break;
		case 40279: skillid = L1SkillId.DRESS_EVASION; break;
		case 6002650: skillid = L1SkillId.LUCIFER_DESTINY; break;
		case 40236: skillid = L1SkillId.RESIST_ELEMENTAL; break;
		}
		if (skillid == 0) return null;
		return _skills.get(skillid);
	}

}

