package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.templates.L1QuestView;
import l1j.server.server.utils.PerformanceTimer;
import l1j.server.server.utils.SQLUtil;

public class QuestInfoTable {
	private static QuestInfoTable _instance;

	public static QuestInfoTable getInstance() {
		if (_instance == null) {
			_instance = new QuestInfoTable();
		}
		return _instance;
	}

	private FastTable<QuestInfoTemp> QuestInfoList = new FastTable<>();
	private FastMap<Integer, L1QuestView> Info_list = new FastMap<>();
	private FastMap<Integer, QuestMonTemp> MonInfo_list = new FastMap<>();
	private FastMap<Integer, QuestItemTemp> Pickup_list = new FastMap<>();
	private FastMap<Integer, QuestItemTemp> Drop_list = new FastMap<>();

	private QuestInfoTable() {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[QuestInfoTable] loading...");
		LoadQuestInfo();
		LoadInfo();
		LoadMonInfo();
		LoadPickupItemInfo();
		System.out.println("OK! " + timer.get() + "ms");
	}

	public static void reload() {
		QuestInfoTable oldInstance = _instance;
		_instance = new QuestInfoTable();
		oldInstance.QuestInfoList.clear();
		oldInstance.Info_list.clear();
		oldInstance.MonInfo_list.clear();
		oldInstance.Pickup_list.clear();
		oldInstance.Drop_list.clear();
	}

	private void LoadQuestInfo() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM quest_view");
			rs = pstm.executeQuery();

			QuestInfoTemp info = null;
			while (rs.next()) {
				info = new QuestInfoTemp();
				info.quest_id = rs.getInt("quest_id");
				info.text = rs.getString("info");
				QuestInfoList.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void LoadInfo() {
		for (QuestInfoTemp quest : QuestInfoList) {
			StringTokenizer s = new StringTokenizer(quest.text, "\r\n");
			String temp = null;
			L1QuestView view = new L1QuestView();
			int count = 0;
			while (s.hasMoreElements()) {
				temp = s.nextToken();
				String temp2 = "";
				//StringTokenizer values = new StringTokenizer(temp, "퀘스트 목표: 몬스터 사냥 아이템 획득 지급 경험치 번호 수량 텔레포트 좌표");
				StringTokenizer values = new StringTokenizer(temp, "Quest Goal: Hunt Monster: Acquire Items: EXP Paid: Pay Item Num: Pay Item Quantity: Tele Coor:");
				while (values.hasMoreElements()) { // remove spaces
					temp2 += values.nextToken();
				}

				StringTokenizer mdata = new StringTokenizer(temp2, ",");
				if (count == 0) { // quest goal
					view.max_count[0] = Integer.parseInt(mdata.nextToken().trim());
					view.max_count[1] = Integer.parseInt(mdata.nextToken().trim());
					view.max_count[2] = Integer.parseInt(mdata.nextToken().trim());
					view.max_count[3] = Integer.parseInt(mdata.nextToken().trim());
				} else if (count == 1) { // monster hunting
					view.monid[0] = Integer.parseInt(mdata.nextToken().trim());
					view.monid[1] = Integer.parseInt(mdata.nextToken().trim());
					view.monid[2] = Integer.parseInt(mdata.nextToken().trim());
					view.monid[3] = Integer.parseInt(mdata.nextToken().trim());
				} else if (count == 2) { // Acquire an item
					view.pick_item[0] = Integer.parseInt(mdata.nextToken().trim());
					view.pick_item[1] = Integer.parseInt(mdata.nextToken().trim());
					view.pick_item[2] = Integer.parseInt(mdata.nextToken().trim());
					view.pick_item[3] = Integer.parseInt(mdata.nextToken().trim());
				} else if (count == 3) { // Experience
					view._exp = Integer.parseInt(mdata.nextToken().trim());
				} else if (count == 4) { // itemid
					view.item_id[0] = Integer.parseInt(mdata.nextToken().trim());
					view.item_id[1] = Integer.parseInt(mdata.nextToken().trim());
					view.item_id[2] = Integer.parseInt(mdata.nextToken().trim());
					view.item_id[3] = Integer.parseInt(mdata.nextToken().trim());
				} else if (count == 5) { // Item Quantity
					view.item_count[0] = Integer.parseInt(mdata.nextToken().trim());
					view.item_count[1] = Integer.parseInt(mdata.nextToken().trim());
					view.item_count[2] = Integer.parseInt(mdata.nextToken().trim());
					view.item_count[3] = Integer.parseInt(mdata.nextToken().trim());
				} else if (count == 6) { // tel coordinates
					//System.out.println(view.tel);
					view.tel[0] = Integer.parseInt(mdata.nextToken().trim());
					//System.out.println(view.tel);
					view.tel[1] = Integer.parseInt(mdata.nextToken().trim());
					view.tel[2] = Integer.parseInt(mdata.nextToken().trim());
				}
				count++;
			}
			view.quest_id = quest.quest_id;
			Info_list.put(view.quest_id, view);
		}
	}

	/**
	 * Maximum numerical information by monster id
	 */
	private void LoadMonInfo() {
		for (L1QuestView view : Info_list.values()) {
			for (int i = 0; i < 4; i++) {
				if (view.monid[i] != 0) {
					QuestMonTemp temp = new QuestMonTemp();
					temp.mon_id = view.monid[i];
					temp.quest_id = view.quest_id;
					temp.type = i;
					temp.count = view.max_count[i];
					MonInfo_list.put(temp.mon_id, temp);
				}
			}
		}
	}

	/**
	 * Material Acquisition Item Information
	 */
	private void LoadPickupItemInfo() {
		for (L1QuestView view : Info_list.values()) {
			for (int i = 0; i < 4; i++) {
				if (view.pick_item[i] != 0) {
					QuestItemTemp temp = new QuestItemTemp();
					temp.quest_id = view.quest_id;
					temp.item_id = view.pick_item[i];
					temp.count = view.max_count[i];
					temp.type = i;
					temp.monid = view.monid[i];
					Pickup_list.put(temp.item_id, temp);
					if (temp.monid != 0 && temp.item_id != 0) {
						Drop_list.put(temp.monid, temp);
					}
				}
			}
		}
	}

	public boolean getPickupItem(int itemid) {
		if (Pickup_list.get(itemid) != null) {
			return true;
		}
		return false;
	}

	public boolean getDropItem(int monid) {
		if (Drop_list.get(monid) != null) {
			return true;
		}
		return false;
	}

	public QuestItemTemp getDropInfo(int monid) {
		QuestItemTemp temp = Drop_list.get(monid);
		return temp;
	}

	public QuestItemTemp getPickupInfo(int itemid) {
		QuestItemTemp temp = Pickup_list.get(itemid);
		return temp;
	}

	public L1QuestView getQuestView(int questid) {
		L1QuestView view = Info_list.get(questid);
		return view;
	}

	public boolean getQuestViewid(int questid) {
		if (Info_list.get(questid) != null) {
			return true;
		}
		return false;
	}

	public QuestMonTemp getQuestMonTemp(int monid) {
		QuestMonTemp temp = MonInfo_list.get(monid);
		return temp;
	}

	public boolean getQuestMonid(int monid) {
		if (MonInfo_list.get(monid) != null) {
			return true;
		}
		return false;
	}

	public class QuestItemTemp {
		public int item_id;
		public int quest_id;
		public int monid;
		public int type;
		public int count;
	}

	public class QuestMonTemp {
		public int mon_id;
		public int quest_id;
		public int type;
		public int count;
	}

	public class QuestInfoTemp {
		public int quest_id;
		public String text;
	}
}
