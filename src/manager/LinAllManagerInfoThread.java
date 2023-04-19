package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.SystemUtil;

public class LinAllManagerInfoThread implements Runnable {
	public static Long AdenMake = Long.valueOf(0L);
	public static Long AdenConsume = Long.valueOf(0L);
	public static int AdenTax = 0;
	public static float Bugdividend = 0.0F;
	public static int AccountCount = 0;
	public static int CharCount = 0;
	public static int PvPCount = 0;
	public static int PenaltyCount = 0;
	public static int ClanMaker = 0;
	public static int MaxUser = 0;
	public static int count = 0;
	public static NumberFormat nf = NumberFormat.getInstance();
	private static LinAllManagerInfoThread _instance;
	private final int _runTime;

	public static LinAllManagerInfoThread getInstance() {
		if (_instance == null) {
			_instance = new LinAllManagerInfoThread();
			_instance.ServerInfoLoad();
			LinAllManager.getInstance().ServerInfoPrint("" + AdenMake, "" + AdenConsume, "" + AdenTax, "" + nf.format(Bugdividend),
					"" + AccountCount, "" + CharCount, "" + PvPCount, "" + PenaltyCount, "" + ClanMaker, "" + MaxUser,
					"" + Thread.activeCount(), "" + SystemUtil.getUsedMemoryMB());
			_instance.start();
		}
		return _instance;
	}

	public LinAllManagerInfoThread() {
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
		_runTime = 500;
	}

	public void start() {
		GeneralThreadPool.getInstance().scheduleAtFixedRate(_instance, 0L, this._runTime);
	}

	@Override
	public void run() {
		try {
			if (++count >= 60) {
				count = 0;
				//save();
			} else {
				//LinAllManager.getInstance().progressBarPrint(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		ServerInfoUPDATE();
		LinAllManager.getInstance().ServerInfoPrint("" + AdenMake, "" + AdenConsume, "" + AdenTax, "" + nf.format(Bugdividend),
				"" + AccountCount, "" + CharCount, "" + PvPCount, "" + PenaltyCount, "" + ClanMaker, "" + MaxUser,
				"" + Thread.activeCount(), "" + SystemUtil.getUsedMemoryMB());
	}

	public static String getDate() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		return localSimpleDateFormat.format(Calendar.getInstance().getTime());
	}

	/*public synchronized void ServerInfoUPDATE() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int i = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT count(*) as cnt FROM serverinfo WHERE id = ?");
			pstm.setString(1, getDate());
			rs = pstm.executeQuery();
			if (rs.next())
				i = rs.getInt("cnt");
			if (i == 0) {
				AdenMake = Long.valueOf(0L);
				AdenConsume = Long.valueOf(0L);
				MaxUser = 0;
				Bugdividend = 0.0F;
				AccountCount = 0;
				CharCount = 0;
				PvPCount = 0;
				PenaltyCount = 0;
				ClanMaker = 0;
				pstm = con.prepareStatement("INSERT INTO serverinfo SET adenmake=?, adenconsume=?, adentax=?, bugdividend=?, accountcount=?, "
						+ "charcount=?, pvpcount=?, penaltycount=?, clanmaker=?,maxuser=?, id=?");
				pstm.setLong(1, AdenMake.longValue());
				pstm.setLong(2, AdenConsume.longValue());
				pstm.setInt(3, AdenTax);
				pstm.setFloat(4, Bugdividend);
				pstm.setInt(5, AccountCount);
				pstm.setInt(6, CharCount);
				pstm.setInt(7, PvPCount);
				pstm.setInt(8, PenaltyCount);
				pstm.setInt(9, ClanMaker);
				pstm.setInt(10, MaxUser);
				pstm.setString(11, getDate());
				pstm.execute();
			} else {
				pstm = con.prepareStatement("UPDATE serverinfo SET adenmake = ?, adenconsume = ?, adentax = ?, bugdividend = ?, accountcount = ?, "
						+ "charcount = ?, pvpcount = ?, penaltycount = ?, clanmaker = ?, maxuser = ? WHERE id = ?");
				pstm.setLong(1, AdenMake.longValue());
				pstm.setLong(2, AdenConsume.longValue());
				pstm.setInt(3, AdenTax);
				pstm.setFloat(4, Bugdividend);
				pstm.setInt(5, AccountCount);
				pstm.setInt(6, CharCount);
				pstm.setInt(7, PvPCount);
				pstm.setInt(8, PenaltyCount);
				pstm.setInt(9, ClanMaker);
				pstm.setInt(10, MaxUser);
				pstm.setString(11, getDate());
				pstm.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs); // close the ResultSet first
			SQLUtil.close(pstm); // then the PreparedStatement
			SQLUtil.close(con); // finally the Connection
		}
	}*/

	public void ServerInfoUPDATE() {
	    try (Connection con = L1DatabaseFactory.getInstance().getConnection();
	         PreparedStatement pstm = con.prepareStatement("SELECT count(*) as cnt FROM serverinfo WHERE id = ?");
	         PreparedStatement insertPstm = con.prepareStatement("INSERT INTO serverinfo SET adenmake = ?, adenconsume = ?, adentax = ?, bugdividend = ?, accountcount = ?, "
	                     + "charcount = ?, pvpcount = ?, penaltycount = ?, clanmaker = ?,maxuser = ?, id = ?");
	         PreparedStatement updatePstm = con.prepareStatement("UPDATE serverinfo SET adenmake = ?, adenconsume = ?, adentax = ?, bugdividend = ?, accountcount = ?, "
	                     + "charcount = ?, pvpcount = ?, penaltycount = ?, clanmaker = ?, maxuser = ? WHERE id = ?")) {

	        ResultSet rs = null;
	        int i = 0;

	        pstm.setString(1, getDate());
	        rs = pstm.executeQuery();
	        if (rs.next())
	            i = rs.getInt("cnt");

	        if (i == 0) {
	            insertPstm.setLong(1, AdenMake.longValue());
	            insertPstm.setLong(2, AdenConsume.longValue());
	            insertPstm.setInt(3, AdenTax);
	            insertPstm.setFloat(4, Bugdividend);
	            insertPstm.setInt(5, AccountCount);
	            insertPstm.setInt(6, CharCount);
	            insertPstm.setInt(7, PvPCount);
	            insertPstm.setInt(8, PenaltyCount);
	            insertPstm.setInt(9, ClanMaker);
	            insertPstm.setInt(10, MaxUser);
	            insertPstm.setString(11, getDate());
	            insertPstm.execute();
	        } else {
	            updatePstm.setLong(1, AdenMake.longValue());
	            updatePstm.setLong(2, AdenConsume.longValue());
	            updatePstm.setInt(3, AdenTax);
	            updatePstm.setFloat(4, Bugdividend);
	            updatePstm.setInt(5, AccountCount);
	            updatePstm.setInt(6, CharCount);
	            updatePstm.setInt(7, PvPCount);
	            updatePstm.setInt(8, PenaltyCount);
	            updatePstm.setInt(9, ClanMaker);
	            updatePstm.setInt(10, MaxUser);
	            updatePstm.setString(11, getDate());
	            updatePstm.execute();
	        }

	        SQLUtil.close(rs); // close the ResultSet
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void ServerInfoLoad() {
	    Connection con = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        con = L1DatabaseFactory.getInstance().getConnection();
	        pstm = con.prepareStatement("SELECT * FROM serverinfo WHERE id=?");
	        pstm.setString(1, getDate());
	        rs = pstm.executeQuery();
	        if (!rs.next())
	            return;
	        AdenMake = Long.valueOf(rs.getLong("adenmake"));
	        AdenConsume = Long.valueOf(rs.getLong("adenconsume"));
	        AdenTax = rs.getInt("adentax");
	        Bugdividend = rs.getInt("bugdividend");
	        AccountCount = rs.getInt("accountcount");
	        CharCount = rs.getInt("charcount");
	        PvPCount = rs.getInt("pvpcount");
	        PenaltyCount = rs.getInt("penaltycount");
	        ClanMaker = rs.getInt("clanmaker");
	        MaxUser = rs.getInt("maxuser");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        SQLUtil.close(rs);
	        SQLUtil.close(pstm);
	        SQLUtil.close(con);
	    }
	}
}