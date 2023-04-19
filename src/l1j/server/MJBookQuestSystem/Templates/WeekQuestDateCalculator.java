package l1j.server.MJBookQuestSystem.Templates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.MJBookQuestSystem.Loader.WeekQuestLoader;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.SQLUtil;

/** The class that will be responsible for the weekly quest time **/
public class WeekQuestDateCalculator {
	private static Logger _log = Logger.getLogger(WeekQuestDateCalculator.class.getName());

	private static WeekUpdator _updator = null;
	private static WeekQuestDateCalculator _instance;
	public static WeekQuestDateCalculator getInstance(){
		if(_instance == null)
			_instance = new WeekQuestDateCalculator();
		return _instance;
	}

	private Timestamp			_updateStamp;
	private ScheduledFuture<?> 	_future;
	private void setLastTime(){
		Connection con							= null;
		PreparedStatement pstm 					= null;
		try {
			con 	= L1DatabaseFactory.getInstance().getConnection();
			pstm 	= con.prepareStatement("insert into tb_weekquest_updateInfo set id=1, lastTime=? on duplicate key update lastTime=?");
			pstm.setTimestamp(1, _updateStamp);
			pstm.setTimestamp(2, _updateStamp);
			pstm.executeUpdate();
		}catch (Exception e){
			StringBuilder sb = new StringBuilder();
			sb.append("[ERROR - WeekQuestDateCalculator] setLastTime()...").append(_updateStamp).append(" write error. \r\n").append(e.getLocalizedMessage());
			_log.log(Level.SEVERE, sb.toString(), e);
			System.out.println(sb.toString());
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private Date getLastTime(){
		Connection con						= null;
		PreparedStatement pstm 				= null;
		ResultSet rs 						= null;
		String column						= "";
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from tb_weekquest_updateInfo");
			rs = pstm.executeQuery();
			if(rs.next()){
				Timestamp ts = rs.getTimestamp("lastTime");
				return new Date(ts.getTime());
			}
		}catch (Exception e){
			StringBuilder sb = new StringBuilder();
			sb.append("[ERROR - WeekQuestDateCalculator] getLastTime()...").append(column).append(" read error. \r\n").append(e.getLocalizedMessage());
			_log.log(Level.SEVERE, sb.toString(), e);
			System.out.println(sb.toString());
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return null;
	}

	public void run(){
		long sleepTime = 0;
		long nowMillis = System.currentTimeMillis();

		// Retrieve previous update information.
		Date oldDate = getLastTime();

		// No previous update information.
		if(oldDate == null){
			// update to the present.
			setUpdate(nowMillis);
		} else {
			// The next update information is retrieved from the previous update information.
			Calendar cal = getNextWeekCalendar(oldDate.getTime());

			// If the current time is greater than the next update time, the update was not performed.
			if(nowMillis >= cal.getTimeInMillis()){
				// Update with the current update time.
				setUpdate(nowMillis);

			// It's not yet time to update.
			} else {
				// Maps previous update information.
				_updateStamp = new Timestamp(oldDate.getTime());
				// Find time to rest until the next update
				sleepTime = cal.getTimeInMillis() - nowMillis;
				// take a break
				setNextUpdate(sleepTime);
			}
		}
	}

	private WeekQuestDateCalculator(){
	}

	/** Returns the number of days required from the current calendar to the next week. **/
	private int getDayToNextWeek(Calendar cal){
		int week 		= cal.get(Calendar.DAY_OF_WEEK); // get the day
		int nextWeek 	= 0;
		if(Config.WQ_UPDATE_TYPE == 0)	//If it is a daily type, it returns 1 day at a time.
			nextWeek = 1;
		else if(week >= Config.WQ_UPDATE_WEEK)
			nextWeek = (Config.WQ_UPDATE_WEEK + 7) - week;
		else
			nextWeek = Config.WQ_UPDATE_WEEK - week;
		return nextWeek;
	}

	/** Returns the calendar for the next week from the given time. **/
	private Calendar getNextWeekCalendar(long sysmillis){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		cal.setTime(new Date(sysmillis));

		Calendar nextCal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		nextCal.setTime(cal.getTime());
		nextCal.add(Calendar.DATE, getDayToNextWeek(cal));
		nextCal.set(Calendar.HOUR_OF_DAY, Config.WQ_UPDATE_TIME);
		nextCal.set(Calendar.MINUTE, 0);
		nextCal.set(Calendar.SECOND, 0);
		return nextCal;
	}

	/** Returns whether the weekly quest has been updated. **/
	public boolean isUpdateWeekQuest(Timestamp ts){
		if(ts == null)
			return true;

		// timezone A phenomenon in which the update is not updated due to a
		// decimal unit ms error due to the setting.. All errors within 1 second are the same.
		long time = Math.abs(ts.getTime() - _updateStamp.getTime());
		if(time <= 1000)
			return false;
		return true;
	}

	public Timestamp getUpdateStamp(){
		return _updateStamp;
	}

	/** Update from the current time. **/
	public void setUpdate(){
		setUpdate(System.currentTimeMillis());
	}

	/** Update from given time. **/
	public void setUpdate(long nowMillis){
		_updateStamp = new Timestamp(nowMillis); // Specify the current time and save it to DB.
		setLastTime();
		// take a break until next week
		long sleepTime = getNextWeekCalendar(_updateStamp.getTime()).getTimeInMillis() - _updateStamp.getTime();
		setNextUpdate(sleepTime);
	}

	/** Let the next update run. **/
	private void setNextUpdate(long sleepTime){
		WeekQuestLoader.reload();
		// If there is a possibility of reloading for any reason, to process it only once,
		if(_updator == null)
			_updator = new WeekUpdator();

		_future = GeneralThreadPool.getInstance().schedule(_updator, sleepTime);
	}

	public synchronized void reloadTime(){
		if(_future != null){
			_future.cancel(true);
		}

		setUpdate(System.currentTimeMillis());
		L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "Weekly quest and attendance lists have been updated."), true);
	}

	/** Helpers to help with periodic updates **/
	class WeekUpdator implements Runnable{

		@Override
		public void run() {
			try {
				long now = System.currentTimeMillis();
				if(_updateStamp.getTime() > now){
					GeneralThreadPool.getInstance().schedule(this, _updateStamp.getTime() - now);
					return;
				}
				String s = "Weekly Quest Update time is now " + now;
				System.out.println(s);
				_log.log(Level.SEVERE, s);

				WeekQuestDateCalculator.getInstance().setUpdate(now);
				Thread.sleep(500);
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "Weekly quests have been updated. Restart your character to receive a new quest list."), true);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
