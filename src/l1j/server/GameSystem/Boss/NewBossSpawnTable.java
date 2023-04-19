package l1j.server.GameSystem.Boss;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.utils.PerformanceTimer;
import l1j.server.server.utils.SQLUtil;


public class NewBossSpawnTable {
	private static NewBossSpawnTable _instance;

	public static NewBossSpawnTable getInstance() {
		if (_instance == null) {
			_instance = new NewBossSpawnTable();
		}
		return _instance;
	}

	private static Logger _log = Logger.getLogger(NewBossSpawnTable.class.getName());

	private ArrayList<BossTemp> bosslist = new ArrayList<>();

	private Random rnd = new Random(System.nanoTime());

	public ArrayList<BossTemp> getlist() {
		return bosslist;
	}

	public static void reload() {
		NewBossSpawnTable oldInstance = _instance;
		_instance = new NewBossSpawnTable();
		oldInstance.bosslist.clear();
	}

	private NewBossSpawnTable() {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[BossSpawnTable] loading...");
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		FileOutputStream fos;
		BufferedOutputStream bos;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM spawnlist_boss_new");
			rs = pstm.executeQuery();

			while (rs.next()) {
				BossTemp temp = new BossTemp();
				temp.npcid = rs.getInt("npcid");
				String text = rs.getString("info");
				temp.rndLoc = rs.getInt("rndXY");
				temp.Groupid = rs.getInt("groupid");
				temp.isYn = rs.getInt("is_yn") == 0 ? false : true;
				temp.isMent = rs.getInt("is_ment") == 0 ? false : true;
				temp.Ment = rs.getString("ment");
				temp.RMent = rs.getString("Rment");

				StringTokenizer s = new StringTokenizer(text, "\r\n");
				int number = 0;
				while (s.hasMoreElements()) {
					String temp2 = "";
					StringTokenizer values = new StringTokenizer(s.nextToken(), "스폰데이: 타임랜덤삭제좌표범위그룹YN메세지멘트");
					while (values.hasMoreElements()) { // remove spaces
						temp2 += values.nextToken();
					}
					if (number == 0) {
					    StringTokenizer Day = new StringTokenizer(temp2, ",");
					    ArrayList<Integer> list = new ArrayList<>();
					    while (Day.hasMoreElements()) {
					        String day = Day.nextToken();
					        if (day.equalsIgnoreCase("일")) { // sunday
					            list.add(Integer.valueOf(0));
					        } else if (day.equalsIgnoreCase("월")) { // mon
					            list.add(Integer.valueOf(1));
					        } else if (day.equalsIgnoreCase("화")) { // tues
					            list.add(Integer.valueOf(2));
					        } else if (day.equalsIgnoreCase("수")) { // wed
					            list.add(Integer.valueOf(3));
					        } else if (day.equalsIgnoreCase("목")) { // thurs
					            list.add(Integer.valueOf(4));
					        } else if (day.equalsIgnoreCase("금")) { // fri
					            list.add(Integer.valueOf(5));
					        } else if (day.equalsIgnoreCase("토")) { // sat
					            list.add(Integer.valueOf(6));
					        }
					    }
						temp.Day = new int[list.size()];
						for (int i = 0; i < list.size(); i++) {
							temp.Day[i] = list.get(i);
						}
					} else if (number == 1) { // spawn time
						StringTokenizer mdata = new StringTokenizer(temp2, ",");
						ArrayList<Integer> Hourlist = new ArrayList<>();
						ArrayList<Integer> Minutelist = new ArrayList<>();
						while (mdata.hasMoreElements()) {
							String Times = mdata.nextToken();
							StringTokenizer Hours = new StringTokenizer(Times, "시"); // hours
							String Hour = Hours.nextToken();
							StringTokenizer Minutes = new StringTokenizer(Hours.nextToken(), "분"); // minute
							String Minute = Minutes.nextToken();
							Hourlist.add(Integer.parseInt(Hour.trim()));
							Minutelist.add(Integer.parseInt(Minute.trim()));
						}

						temp.SpawnHour = new int[Hourlist.size()];
						temp.SpawnMinute = new int[Hourlist.size()];
						for (int i = 0; i < Hourlist.size(); i++) {
							int Hour = Hourlist.get(i);
							int Minute = Minutelist.get(i);
							temp.SpawnHour[i] = Hour;
							temp.SpawnMinute[i] = Minute;
						}

					} else if (number == 2) { // 랜덤타임 분
						StringTokenizer mdata = new StringTokenizer(temp2, "분");
						temp.rndTime = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("랜덤시간 = " + temp.rndTime);
					} else if (number == 3) { // 삭제시간 초
						StringTokenizer mdata = new StringTokenizer(temp2, "초");
						temp.DeleteTime = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("삭제시간 초 = " + temp.DeleteTime);
					} else if (number == 4) { // 스폰좌표
						StringTokenizer mdata = new StringTokenizer(temp2, ",");
						temp.SpawnLoc = new int[3];
						temp.SpawnLoc[0] = Integer.parseInt(mdata.nextToken().trim());
						temp.SpawnLoc[1] = Integer.parseInt(mdata.nextToken().trim());
						temp.SpawnLoc[2] = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("스폰좌표x = " + temp.SpawnLoc[0]);
						// System.out.println("스폰좌표y = " + temp.SpawnLoc[1]);
						// System.out.println("스폰좌표m = " + temp.SpawnLoc[2]);
					}

					number++;
				}
				bosslist.add(temp);
			}

			/*for (BossTemp temp : bosslist) {
				temp.NearTime = new int[temp.SpawnHour.length];
				for (int i = 0; i < temp.SpawnHour.length; i++) {
					if (temp.rndTime != 0) {
						int Hour = temp.SpawnHour[i];
						int rndtime = rnd.nextInt(temp.rndTime) + 1;
						int Minute = temp.SpawnMinute[i] + rndtime;
						if (Minute >= 120) {
							Hour += 2;
							Minute -= 120;
						} else if (Minute >= 60) {
							Hour++;
							Minute -= 60;
						}
						StringBuffer NewText = new StringBuffer();
						NewText.append(Hour);
						NewText.append(Minute < 10 ? "0" + Minute : Minute);
						temp.NearTime[i] = Integer.parseInt(NewText.toString().trim());
						// System.out.println("temp.NearTime[i] = " + temp.NearTime[i]);
					} else {
						StringBuffer NewText = new StringBuffer();
						NewText.append(temp.SpawnHour[i]);
						NewText.append(temp.SpawnMinute[i] < 10 ? "0" + temp.SpawnMinute[i] : temp.SpawnMinute[i]);
						temp.NearTime[i] = Integer.parseInt(NewText.toString().trim());
						// System.out.println("temp.NearTime[i] = " + temp.NearTime[i]);

					}
				}
			}*/

		} catch (SQLException e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			System.out.println("OK! " + timer.get() + "ms");
		}
	}


	/*private NewBossSpawnTable() {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[BossSpawnTable] loading...");
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		FileOutputStream fos;
		BufferedOutputStream bos;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM spawnlist_boss_new");
			rs = pstm.executeQuery();

			while (rs.next()) {
				BossTemp temp = new BossTemp();
				temp.npcid = rs.getInt("npcid");
				String text = rs.getString("info");
				temp.rndLoc = rs.getInt("rndXY");
				temp.Groupid = rs.getInt("groupid");
				temp.isYn = rs.getInt("is_yn") == 0 ? false : true;
				temp.isMent = rs.getInt("is_ment") == 0 ? false : true;
				temp.Ment = rs.getString("ment");
				temp.RMent = rs.getString("Rment");

				StringTokenizer s = new StringTokenizer(text, "\r\n");
				int number = 0;
				while (s.hasMoreElements()) {
					String temp2 = "";
					//StringTokenizer values = new StringTokenizer(s.nextToken(), "스폰데이: 타임랜덤삭제좌표범위그룹YN메세지멘트");
					StringTokenizer values = new StringTokenizer(s.nextToken(), "SpDay: SpTime: RandomTime: DeleteTime: SpawnCoor: Range: Group: YN: Message:");
					while (values.hasMoreElements()) { // remove spaces
						temp2 += values.nextToken();
					}
					if (number == 0) {
					    StringTokenizer Day = new StringTokenizer(temp2, ",");
					    ArrayList<Integer> list = new ArrayList<Integer>();
					    while (Day.hasMoreElements()) {
					        String day = Day.nextToken();
					        if (day.equalsIgnoreCase("Sun")) { // sunday
					            list.add(Integer.valueOf(0));
					        } else if (day.equalsIgnoreCase("Mon")) { // mon
					            list.add(Integer.valueOf(1));
					        } else if (day.equalsIgnoreCase("Tues")) { // tues
					            list.add(Integer.valueOf(2));
					        } else if (day.equalsIgnoreCase("Wed")) { // wed
					            list.add(Integer.valueOf(3));
					        } else if (day.equalsIgnoreCase("Thur")) { // thurs
					            list.add(Integer.valueOf(4));
					        } else if (day.equalsIgnoreCase("Fri")) { // fri
					            list.add(Integer.valueOf(5));
					        } else if (day.equalsIgnoreCase("Sat")) { // sat
					            list.add(Integer.valueOf(6));
					        }
					    }
						temp.Day = new int[list.size()];
						for (int i = 0; i < list.size(); i++) {
							temp.Day[i] = list.get(i);
						}
					} else if (number == 1) { // spawn time
						StringTokenizer mdata = new StringTokenizer(temp2, ",");
						ArrayList<Integer> Hourlist = new ArrayList<Integer>();
						ArrayList<Integer> Minutelist = new ArrayList<Integer>();
						while (mdata.hasMoreElements()) {
							String Times = mdata.nextToken();
							StringTokenizer Hours = new StringTokenizer(Times, "hr"); // hours
							String Hour = Hours.nextToken();
							StringTokenizer Minutes = new StringTokenizer(Hours.nextToken(), "min"); // minute
							String Minute = Minutes.nextToken();
							Hourlist.add(Integer.parseInt(Hour.trim()));
							Minutelist.add(Integer.parseInt(Minute.trim()));
						}

						temp.SpawnHour = new int[Hourlist.size()];
						temp.SpawnMinute = new int[Hourlist.size()];
						for (int i = 0; i < Hourlist.size(); i++) {
							int Hour = Hourlist.get(i);
							int Minute = Minutelist.get(i);
							temp.SpawnHour[i] = Hour;
							temp.SpawnMinute[i] = Minute;
						}

					} else if (number == 2) { // random time minutes
						StringTokenizer mdata = new StringTokenizer(temp2, "min");
						temp.rndTime = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("random time = " + temp.rndTime);
					} else if (number == 3) { // delete time seconds
						StringTokenizer mdata = new StringTokenizer(temp2, "sec");
						temp.DeleteTime = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("delete time seconds = " + temp.DeleteTime);
					} else if (number == 4) { // spawn coordinates
						StringTokenizer mdata = new StringTokenizer(temp2, ",");
						temp.SpawnLoc = new int[3];
						temp.SpawnLoc[0] = Integer.parseInt(mdata.nextToken().trim());
						temp.SpawnLoc[1] = Integer.parseInt(mdata.nextToken().trim());
						temp.SpawnLoc[2] = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("스폰좌표x = " + temp.SpawnLoc[0]);
						// System.out.println("스폰좌표y = " + temp.SpawnLoc[1]);
						// System.out.println("스폰좌표m = " + temp.SpawnLoc[2]);
					}

					number++;
				}
				bosslist.add(temp);
			}

			/*for (BossTemp temp : bosslist) {
				temp.NearTime = new int[temp.SpawnHour.length];
				for (int i = 0; i < temp.SpawnHour.length; i++) {
					if (temp.rndTime != 0) {
						int Hour = temp.SpawnHour[i];
						int rndtime = rnd.nextInt(temp.rndTime) + 1;
						int Minute = temp.SpawnMinute[i] + rndtime;
						if (Minute >= 120) {
							Hour += 2;
							Minute -= 120;
						} else if (Minute >= 60) {
							Hour++;
							Minute -= 60;
						}
						StringBuffer NewText = new StringBuffer();
						NewText.append(Hour);
						NewText.append(Minute < 10 ? "0" + Minute : Minute);
						temp.NearTime[i] = Integer.parseInt(NewText.toString().trim());
						// System.out.println("temp.NearTime[i] = " + temp.NearTime[i]);
					} else {
						StringBuffer NewText = new StringBuffer();
						NewText.append(temp.SpawnHour[i]);
						NewText.append(temp.SpawnMinute[i] < 10 ? "0" + temp.SpawnMinute[i] : temp.SpawnMinute[i]);
						temp.NearTime[i] = Integer.parseInt(NewText.toString().trim());
						// System.out.println("temp.NearTime[i] = " + temp.NearTime[i]);

					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			System.out.println("OK! " + timer.get() + "ms");
		}
	}*/

	/*
	 * 1~6 0
	 * 스폰데이: 월,화,수,목,금,토,일
	 * 스폰타임: 2시 00분, 5시 30분
	 * 랜덤타임: 0분
	 * 삭제타임: 3600초
	 * 스폰좌표: 32726, 32832, 603
	 * 랜덤범위: 0
	 * 그룹스폰: 0
	 * YN메세지: 1
	 * 스폰멘트: 1
	 */
	public static class BossTemp {
		public int npcid;
		public boolean isSpawn;
		public int[] Day;
		public int[] SpawnHour;
		public int[] SpawnMinute;
		public int[] NearTime;
		public int rndTime;
		public int DeleteTime;
		public int[] SpawnLoc;
		public int rndLoc;
		public int Groupid;
		public boolean isYn;
		public boolean isMent;
		public String Ment;
		public String RMent;
		public int timeM;
	}
}
