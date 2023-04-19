package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.templates.L1QuestInfo;
import l1j.server.server.utils.PerformanceTimer;
import l1j.server.server.utils.SQLUtil;

public class CharacterQuestTable {
	private static CharacterQuestTable _instance;

	public static CharacterQuestTable getInstance() {
		if (_instance == null) {
			_instance = new CharacterQuestTable();
		}
		return _instance;
	}

	private static Logger _log = Logger.getLogger(CharacterQuestTable.class.getName());

	private FastTable<QuestTemp> QuestList = new FastTable<>();

	public static void reload() {
		CharacterQuestTable oldInstance = _instance;
		_instance = new CharacterQuestTable();
		oldInstance.QuestList.clear();
	}

	private CharacterQuestTable() {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[CharacterQuestTable] loading...");
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_new_quest");
			rs = pstm.executeQuery();

			QuestTemp mon = null;
			while (rs.next()) {
				mon = new QuestTemp();
				mon.name = rs.getString("name");
				mon.text = rs.getString("info");
				QuestList.add(mon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			System.out.println("OK! " + timer.get() + "ms");
		}
	}

	/**
	 * Uploads monster information to the character when logging in.
	 *
	 * @param pc
	 */
	public void LoginQuestInfo(L1PcInstance pc) {
		for (QuestTemp quest : QuestList) {
			if (quest.name.equalsIgnoreCase(pc.getName())) {
				StringTokenizer s = new StringTokenizer(quest.text, "\r\n");
				String temp = null;
				L1QuestInfo info = null;
				while (s.hasMoreElements()) { // 엔터기준
					temp = s.nextToken();
					StringTokenizer mdata = new StringTokenizer(temp, ":");
					info = new L1QuestInfo();
					info.quest_id = Integer.parseInt(mdata.nextToken().trim());
					info.st_time = Long.parseLong(mdata.nextToken().trim());
					info.end_time = Long.parseLong(mdata.nextToken().trim());
					info.ck[0] = Integer.parseInt(mdata.nextToken().trim());
					info.ck[1] = Integer.parseInt(mdata.nextToken().trim());
					info.ck[2] = Integer.parseInt(mdata.nextToken().trim());
					info.ck[3] = Integer.parseInt(mdata.nextToken().trim());
					pc.quest_list.put(info.quest_id, info);
				}
				return;
			}
		}
		QuestTemp quest = new QuestTemp();
		quest.name = pc.getName();
		quest.text = "";
		createQuest(quest);
	}

	/**
	 * Upload logout information
	 *
	 * @param pc
	 */
	public void LogOutQuest(L1PcInstance pc) {
		synchronized (pc.syncTalkIsland) {
			for (QuestTemp quest : QuestList) {
				if (quest.name.equalsIgnoreCase(pc.getName())) {
					StringBuffer NewText = new StringBuffer();
					// String temp = null;]
					for (L1QuestInfo info : pc.quest_list.values()) {
						NewText.append(Integer.toString(info.quest_id) + ":");
						NewText.append(Long.toString(info.st_time) + ":");
						NewText.append(Long.toString(info.end_time) + ":");
						NewText.append(Integer.toString(info.ck[0]) + ":");
						NewText.append(Integer.toString(info.ck[1]) + ":");
						NewText.append(Integer.toString(info.ck[2]) + ":");
						NewText.append(Integer.toString(info.ck[3]) + "\r\n");
					}
					quest.text = NewText.toString();
					update(quest);
					break;
				}
			}
		}
	}

	public void update(QuestTemp quest) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE character_new_quest SET info=? WHERE name=?");
			pstm.setString(1, quest.text);
			pstm.setString(2, quest.name);
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void delete(String name) {
		Connection con = null;
		PreparedStatement pstm = null;
		for (QuestTemp quest : QuestList) {
			if (quest.name.equalsIgnoreCase(name)) {
				QuestList.remove(quest);
				break;
			}
		}
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_new_quest WHERE name=?");
			pstm.setString(1, name);
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * Update full information when server shuts down
	 */
	public void updateAll() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE character_new_quest SET info=? WHERE name=?");

			for (QuestTemp QuestTemp : QuestList) {
				pstm.setString(1, QuestTemp.text);
				pstm.setString(2, QuestTemp.name);
				pstm.execute();
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "MonsterBookTable[]Error1", e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * Created in database upon login
	 *
	 * @param mon
	 */
	public void createQuest(QuestTemp mon) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO character_new_quest SET name=?, info=?");
			pstm.setString(1, mon.name);
			pstm.setString(2, mon.text);
			pstm.execute();

			QuestList.add(mon);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "MonsterBookTable[]Error2", e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public class QuestTemp {
		public String name;
		public String text;
	}
}
