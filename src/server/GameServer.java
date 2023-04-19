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
package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.IND_Q;
import l1j.server.INN_Q;
import l1j.server.L1DatabaseFactory;
import l1j.server.quit_Q;
import l1j.server.GameSystem.BossTimer;
import l1j.server.GameSystem.DesertTornadoController;
import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.HomeTownController;
import l1j.server.GameSystem.NewTimeController;
import l1j.server.GameSystem.NpcShopSystem;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.Boss.BossTimeController;
import l1j.server.GameSystem.Boss.NewBossSpawnTable;
import l1j.server.GameSystem.Delivery.DeliverySystem;
import l1j.server.GameSystem.DogFight.DogFight;
import l1j.server.GameSystem.Hadin.Hadin;
import l1j.server.GameSystem.Hadin.HadinThread;
import l1j.server.GameSystem.InterServer.InterServer;
import l1j.server.GameSystem.Lindvior.LindviorRaid;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShop;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShopSell;
import l1j.server.GameSystem.NpcTradeShop.NpcTradeShop;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable;
import l1j.server.GameSystem.SupportSystem.SupportMapTable;
import l1j.server.GameSystem.TraningCenter.TraningCenter;
import l1j.server.GameSystem.UserRanking.UserRankingController;
/*import l1j.server.server.TimeController.주퀘Controller;*/
import l1j.server.IndunSystem.MiniGame.BattleZone;
import l1j.server.MJ3SEx.MJSprBoundary;
import l1j.server.MJ3SEx.Loader.SpriteInformationLoader;
import l1j.server.MJBookQuestSystem.Loader.MonsterBookCompensateLoader;
import l1j.server.MJBookQuestSystem.Loader.MonsterBookLoader;
import l1j.server.MJBookQuestSystem.Templates.WeekQuestDateCalculator;
import l1j.server.MJCTSystem.Loader.MJCTLoadManager;
import l1j.server.MJDShopSystem.MJDShopStorage;
import l1j.server.MJInstanceSystem.Loader.MJInstanceLoadManager;
import l1j.server.Warehouse.SupplementaryService;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.netty.coder.manager.DecoderManager;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.GiftBossController;
import l1j.server.server.GiftBoxController;
import l1j.server.server.Jibaetower;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.Shutdown;
import l1j.server.server.TimeController.AuctionTimeController;
import l1j.server.server.TimeController.FishingTimeController;
import l1j.server.server.TimeController.HouseTaxTimeController;
import l1j.server.server.TimeController.LightTimeController;
import l1j.server.server.TimeController.NoticeTimeController;
import l1j.server.server.TimeController.NpcChatTimeController;
import l1j.server.server.TimeController.UbTimeController;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.TimeController.영자상점Controller;
import l1j.server.server.command.executor.L1CreateItem;
import l1j.server.server.datatables.AdenShopTable;
import l1j.server.server.datatables.AttendanceTable;
import l1j.server.server.datatables.CastleTable;
import l1j.server.server.datatables.CharacterQuestTable;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.CharcterRevengeTable;
import l1j.server.server.datatables.ClanHistoryTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.CraftInfoTable;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.datatables.DragonRaidItemTable;
import l1j.server.server.datatables.DropItemTable;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.EvaSystemTable;
import l1j.server.server.datatables.FurnitureSpawnTable;
import l1j.server.server.datatables.GetBackRestartTable;
import l1j.server.server.datatables.IpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.LightSpawnTable;
import l1j.server.server.datatables.MapsTable;
import l1j.server.server.datatables.MobGroupTable;
import l1j.server.server.datatables.ModelSpawnTable;
import l1j.server.server.datatables.MonsterBookTable;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.datatables.NpcChatTable;
import l1j.server.server.datatables.NpcShopTable;
import l1j.server.server.datatables.NpcSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.PetTypeTable;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.QuestInfoTable;
import l1j.server.server.datatables.RaceTable;
import l1j.server.server.datatables.ResolventTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SoldierTable;
import l1j.server.server.datatables.SpawnTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.datatables.UBSpawnTable;
import l1j.server.server.datatables.WeaponAddDamage;
import l1j.server.server.model.Dungeon;
import l1j.server.server.model.ElementalStoneGenerator;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Cube;
import l1j.server.server.model.L1DeleteItemOnGround;
import l1j.server.server.model.L1NpcRegenerationTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Sys;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1MonsterInstance.감시자리퍼시간체크;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.model.gametime.RealTimeClock;
import l1j.server.server.model.item.L1TreasureBox;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.trap.L1WorldTraps;
import l1j.server.server.templates.L1EvaSystem;
import l1j.server.server.utils.PerformanceTimer;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.SystemUtil;
import server.controller.BraveAvatarController;
import server.controller.ExpMonitorController;
import server.controller.InvSwapController;
import server.threads.pc.ArrowTrapThread;
import server.threads.pc.AutoSaveThread;
import server.threads.pc.CharacterQuickCheckThread;
import server.threads.pc.ClanBuffThread;
import server.threads.pc.DollObserverThread;
import server.threads.pc.HpMpRegenThread;
import server.threads.pc.ItemEndTimeThread;
import server.threads.pc.PetHpRegenThread;
import server.threads.pc.PremiumAinThread;
import server.threads.pc.SabuDGTime;
import server.threads.pc.SkillReiterationThread;
import server.threads.pc.SpeedHackThread;

public class GameServer {
	private static final Logger _log = Logger.getLogger(GameServer.class.getName());
	private static GameServer _instance;

	private GameServer() {
		// super("GameServer");
	}

	public static GameServer getInstance() {

		if (_instance == null) {
			synchronized (GameServer.class) {
				if (_instance == null)
					_instance = new GameServer();
			}
		}
		return _instance;
	}

	public static boolean NEWB_SUPPORT_EXP_BONUS = false;

	public void initialize() throws Exception {
		PerformanceTimer timer = new PerformanceTimer();
		Config._IND_Q = new IND_Q();
		Config._INN_Q = new INN_Q();
		Config._quit_Q = new quit_Q();

		/** Connections to Interserver Systems */
		InterServer.getInstance();

		// banAccountDelete();
		ObjectIdFactory.createInstance();

		L1World.getInstance();
		L1WorldTraps.getInstance();
		GeneralThreadPool.getInstance();
		// 계정및인벤아덴체크(); // Account and inventory check
		initializeNPCShopTable(); // NPC shop table initialization

		initTime();

		CharacterTable.getInstance().loadAllCharName();
		//CharacterTable.getInstance().loadAllBotName();



		// TODO change following code to be more effective

		// UB Time Controller
		GeneralThreadPool.getInstance().execute(UbTimeController.getInstance());
		GeneralThreadPool.getInstance().execute(영자상점Controller.getInstance());
		GeneralThreadPool.getInstance().execute(Jibaetower.getInstance());
		GeneralThreadPool.getInstance().execute(GiftBoxController.getInstance());
		GeneralThreadPool.getInstance().execute(GiftBossController.getInstance());

		// Spirit Stone Time Controller
		if (Config.ELEMENTAL_STONE_AMOUNT > 0) {
			GeneralThreadPool.getInstance().execute(ElementalStoneGenerator.getInstance());
		}

		// Sabu_CMBox.getInstance().Load();
		// home town
		//HomeTownController.getInstance();
		NpcShopTable.getInstance();
		NpcShopSystem.getInstance();
		// ChatTimeController chatTimeController =
		// ChatTimeController.getInstance();
		// GeneralThreadPool.getInstance().execute(chatTimeController);
		// battle zone
		if (Config.배틀존작동유무) {
			BattleZone battleZone = BattleZone.getInstance();
			GeneralThreadPool.getInstance().execute(battleZone);
		}
		// announcement
		// NoticeTimeController noticeTimeContorller = new
		// NoticeTimeController();
		// GeneralThreadPool.getInstance().execute(noticeTimeContorller);
		NoticeTimeController.getInstance();

		// DevilController.getInstance().start();
		/* 주퀘Controller.getInstance().start(); */
		// fishing time controller
		GeneralThreadPool.getInstance().schedule(FishingTimeController.getInstance(), 300);



		// BattleZone battleZone = BattleZone.getInstance();
		// GeneralThreadPool.getInstance().execute(battleZone);

		GeneralThreadPool.getInstance().execute(NpcChatTimeController.getInstance());

		L1DeleteItemOnGround deleteitem = new L1DeleteItemOnGround();
		deleteitem.initialize();

		if (!NpcTable.getInstance().isInitialized()) {
			throw new Exception("[GameServer] Could not initialize the npc table");
		}

		try {
			MapsTable.getInstance();

			// Training Dungeon 1-4f and Storm Training Map
			/*L1WorldMap.getInstance().cloneMap(25, 2221);
			L1WorldMap.getInstance().cloneMap(26, 2222);
			L1WorldMap.getInstance().cloneMap(27, 2223);
			L1WorldMap.getInstance().cloneMap(28, 2224);

			L1WorldMap.getInstance().cloneMap(25, 2225);
			L1WorldMap.getInstance().cloneMap(26, 2226);
			L1WorldMap.getInstance().cloneMap(27, 2227);
			L1WorldMap.getInstance().cloneMap(28, 2228);

			L1WorldMap.getInstance().cloneMap(25, 2229);
			L1WorldMap.getInstance().cloneMap(26, 2230);
			L1WorldMap.getInstance().cloneMap(27, 2231);
			L1WorldMap.getInstance().cloneMap(28, 2232);

			L1WorldMap.getInstance().cloneMap(2010, 2233);
			L1WorldMap.getInstance().cloneMap(2010, 2234);
			L1WorldMap.getInstance().cloneMap(2010, 2235);*/

			SpawnTable.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MobGroupTable.getInstance();

		PolyTable.getInstance();
		ItemTable.getInstance();
		DropTable.getInstance();
		DropItemTable.getInstance();
		ShopTable.getInstance();
		NPCTalkDataTable.getInstance();
		Dungeon.getInstance();

		try {
			NpcSpawnTable.getInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		IpTable.getInstance();
		UBSpawnTable.getInstance();

		L1CastleLocation.setCastleTaxRate(); // CastleTable initialization
		GetBackRestartTable.getInstance();
		DoorSpawnTable.getInstance();
		L1NpcRegenerationTimer.getInstance();

		//GMCommandsConfig.load();
		Getback.loadGetBack();
		PetTypeTable.load();

		RaceTable.getInstance();


		NpcChatTable.getInstance();

		SoldierTable.getInstance();

		/** Update alarm rotation information */
		RotationNoticeTable.getInstance();

		// Lastavard Dungeon
		/* LastabardController.start(); */

		// Time Controllers
		GeneralThreadPool.getInstance().execute(AuctionTimeController.getInstance());
		GeneralThreadPool.getInstance().execute(HouseTaxTimeController.getInstance());
		WarTimeController warTimeController = WarTimeController.getInstance();
		GeneralThreadPool.getInstance().execute(warTimeController);

		// Haunted House, Deathmatch
		GeneralThreadPool.getInstance().execute(DeathMatch.getInstance());
		GeneralThreadPool.getInstance().execute(GhostHouse.getInstance());
		GeneralThreadPool.getInstance().execute(PetRacing.getInstance());

		// Put a model in the world (torn in a dungeon, etc.)
		ModelSpawnTable.getInstance().ModelInsertWorld();

		// game notice
		//NoticeSystem.start();
		//Gamble.get().Load();
		Hadin.get();
		HadinThread.get();

		//NavalWarfare.getInstance();
		//DreamsTemple.getInstance();

		// Database Cleanup
		//deleteOldRows();

		// New Console logging enabled
		AdenShopTable.getInstance();
		AttendanceTable.getInstance();
		BossTimeController.getInstance();
		BossTimer.getInstance();
		//L1BugBearRace.getInstance();
		CastleTable.getInstance();
		CharacterTable.clearOnlineStatus();
		CharacterQuestTable.getInstance();
		ClanBuffThread.getInstance();
		ClanTable.getInstance();
		CraftInfoTable.getIns();
		L1Cube.getInstance();
		DeliverySystem.getInstance().Load();
		DogFight.getInstance();
		FurnitureSpawnTable.getInstance();
		InvSwapController.getInstance();
		LightSpawnTable.getInstance();
		LightTimeController.start();
		LindviorRaid.get();
		MJCTLoadManager.getInstance().load();
		MonsterBookCompensateLoader.getInstance();
		MonsterBookLoader.getInstance();
		MonsterBookTable.getInstace();
		NewBossSpawnTable.getInstance();
		NpcActionTable.load();
		NpcBuyShopSell.getInstance().load();
		NpcBuyShop.getInstance();
		NpcShopSystem.getInstance().npcShopStart();
		NpcTable.getInstance();
		NpcTradeShop.getInstance();
		PetTable.getInstance();

		SupportMapTable.getInstance();
		QuestInfoTable.getInstance();
		ResolventTable.getInstance();
		SkillsTable.getInstance();
		SprTable.getInstance();
		TraningCenter.get();
		L1TreasureBox.load();
		UserRankingController.getInstance();
		WeaponAddDamage.getInstance(); // hidden weapon damage
		WeekQuestDateCalculator.getInstance().run();
		L1WorldMap.createInstance(); // FIXME poor

		// crack in time
		// CrockSystem.getInstance();
		/* EvaSystemTable.getInstance(); */

		/** Used when running pet-related threads */
		PetHpRegenThread.getInstance();
		/** Related to mjSpr */
		SpriteInformationLoader.getInstance().loadSpriteInformation();
		MJSprBoundary.do_load();

		// delete the flag
		RaceTicket();
		AutoSaveThread.getInstance();
		DollObserverThread.getInstance();
		HpMpRegenThread.getInstance();
		SabuDGTime.getInstance();
		// NewLadunThread.getInstance();

		/** revenge */
		CharcterRevengeTable.getInstance();

		// Monitoring
		CharacterQuickCheckThread.getInstance();
		ExpMonitorController.getInstance();
		SpeedHackThread.getInstance();

		PremiumAinThread.getInstance();
		//Robot_Spawn bot = new Robot_Spawn();
		//GeneralThreadPool.getInstance().execute(bot);

		//DecoderManager.getInstance();
		// HouseTable.getInstance().BoardAuction_insert();
		// new ThreadMonitor(10);
		// NewLadunSpawn.getInstance().Spawn(12);
		// NewLadunSpawn.getInstance().Spawn(13);
		L1Sys.getInstance();
		L1Sys l1Sys = L1Sys.getInstance();
		GeneralThreadPool.getInstance().execute(l1Sys);
		SkillReiterationThread.getInstance();
		ArrowTrapThread.getInstance();
		NewTimeController.get();
		ItemEndTimeThread.get();
		try {
			DragonRaidItemTable.get();
			GeneralThreadPool.getInstance().execute(new DesertTornadoController());
			ClanHistoryTable.getInstance().dateCheckDelete();
			INNKeyDelete();
			//Robot_ConnectAndRestart.getInstance();
			//Robot_Hunt.getInstance();
			//Robot_Bugbear.getInstance();
			//Robot_Fish.getInstance();
			//Robot_Location.setRLOC();
			BraveAvatarController.getInstance();
			L1MonsterInstance.리퍼시간체크 = new 감시자리퍼시간체크();
			GeneralThreadPool.getInstance().schedule(L1MonsterInstance.리퍼시간체크, 1000);
			initializeCharShopTable(); // Character shop table initialization
			initializeWebTableImage(); // Web image table initialization
			웹이미지테이블넣기(); // Insert web image table

			/** 2016.11.26 MJ App Center LFC **/
			MJInstanceLoadManager.getInstance().load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Garbage collector runs (Null) object release

		Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());

		// check for server mode test or live
		String mode = "";
		if (!Config.TEST_SERVER) {
			mode = "Official";
		} else {
			mode = "Test";
		}

		String srvVer = Config.SERVER_VERSION;
		System.out.println("====================================================");
		System.out.println("[Server] Loading completed in " + timer.get() + "ms");
		System.out.println("====================================================");
		System.out.println(":: L1Open Remastered Server (v" + srvVer + ")");
		System.out.println(":: Client v2005201703 or v2002121701");
		System.out.println(":: Server Host: " + Config.GAME_SERVER_HOST_NAME);
		System.out.println(":: Server Port: " + Config.GAME_SERVER_PORT);
		System.out.println(":: Memory Used: " + SystemUtil.getUsedMemoryMB() + " Mb");
		System.out.println(":: Language: English");
		System.out.println(":: Server Mode: " + mode);
		System.out.println(":: Time Zone: " + Config.TIME_ZONE);
		System.out.println("====================================================");

		DecoderManager.getInstance();
		_log.info("Server start successful");
		
	}

	// function to delete rows from tables that are older than LOG_CLEANER_DELAY weeks
	/*public static void deleteOldRows() throws SQLException {
	    Connection conn = L1DatabaseFactory.getInstance().getConnection();

	    int logCleanerDelayWeeks = Config.LOG_CLEANER_DELAY;

	    StringBuilder logConnection = new StringBuilder();
	    logConnection.append("DELETE FROM _log_connection WHERE time < DATE_SUB(SYSDATE(), INTERVAL ");
	    logConnection.append(logCleanerDelayWeeks);
	    logConnection.append(" WEEK)");

	    StringBuilder logWhisper = new StringBuilder();
	    logWhisper.append("DELETE FROM _log_whisper WHERE time < DATE_SUB(SYSDATE(), INTERVAL ");
	    logWhisper.append(logCleanerDelayWeeks);
	    logWhisper.append(" WEEK)");

	    StringBuilder logSystem = new StringBuilder();
	    logSystem.append("DELETE FROM _log_system WHERE time < DATE_SUB(SYSDATE(), INTERVAL ");
	    logSystem.append(logCleanerDelayWeeks);
	    logSystem.append(" WEEK)");

	    StringBuilder logTrade = new StringBuilder();
	    logTrade.append("DELETE FROM _log_trade_ok WHERE time < DATE_SUB(SYSDATE(), INTERVAL ");
	    logTrade.append(logCleanerDelayWeeks);
	    logTrade.append(" WEEK)");

	    PreparedStatement stmt1 = conn.prepareStatement(logConnection.toString());
	    PreparedStatement stmt2 = conn.prepareStatement(logSystem.toString());
	    PreparedStatement stmt3 = conn.prepareStatement(logWhisper.toString());
	    PreparedStatement stmt4 = conn.prepareStatement(logTrade.toString());

	    stmt1.executeUpdate();
	    stmt2.executeUpdate();
	    stmt3.executeUpdate();
	    stmt4.executeUpdate();
	}*/


	private void initializeWebTableImage() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("TRUNCATE web_item_image");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	private void initializeNPCShopTable() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("TRUNCATE shop_npc");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	private void initializeCharShopTable() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("TRUNCATE character_shop");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	private void 웹이미지테이블넣기() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement(
					"insert into web_item_image (item_name, invgfx) select name, invgfx from weapon   union all select name, invgfx from armor   union all select name, invgfx from etcitem");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	class iqlog {
		public String name = "";
		public int count = 0;
		public String account = "";
		public String ip = "";
		public String host = "";
	}

	private void initTime() {
		GameTimeClock.init(); // game time clock
		RealTimeClock.init(); // current time clock
	}

	/*
	 * private void showGameServerSetting() {
	 * System.out.println("OPCODE : 2011.05.19"); //
	 * System.out.println("UI5ver. Modified by 감자");
	 * System.out.println("Thank You!"); }
	 */

	public void accountAndInventoryCheck() { // unused
		// int level_cut = 50; //보다 낮아야됨
		// int level_cut2 = 1; //보다 낮아야됨

		// int level_cut = 55; //보다 낮아야됨
		// int level_cut2 = 51; //보다 낮아야됨

		// int level_cut = 60; //보다 낮아야됨
		// int level_cut2 = 56; //보다 낮아야됨

		// int level_cut = 63; //보다 낮아야됨
		// int level_cut2 = 61; //보다 낮아야됨

		// int level_cut = 65; //보다 낮아야됨
		// int level_cut2 = 64; //보다 낮아야됨

		int level_cut = 70; // 보다 낮아야됨
		int level_cut2 = 1; // 보다 낮아야됨
		int adena_cut = 1000000; // 보다 커야됨
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		ArrayList<String> ll = new ArrayList<>();
		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM accounts WHERE banned=0");
			rs2 = pstm2.executeQuery();
			while (rs2.next()) {
				String account = rs2.getString("login");
				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM characters WHERE account_name=?");
				pstm1.setString(1, account);
				ResultSet rs = pstm1.executeQuery();
				boolean ck = false;

				while (rs.next()) {
					int level = rs.getInt("level");
					if (level > level_cut || level_cut2 > level) {
						ck = true;
					}
				}

				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);
				if (!ck) {
					ll.add(account);
				}
			}
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}

		StringBuilder sbb = new StringBuilder();
		for (String accountName : ll) {
			try {
				con2 = L1DatabaseFactory.getInstance().getConnection();
				pstm2 = con2.prepareStatement("SELECT * FROM characters WHERE account_name=?");
				pstm2.setString(1, accountName);
				rs2 = pstm2.executeQuery();
				StringBuilder sb = new StringBuilder();
				sb.append("-Account: " + accountName + "\n");
				int total = 0;
				boolean ck2 = false;

				while (rs2.next()) {
					String name = rs2.getString("char_name");
					int objid = rs2.getInt("objid");
					int level = rs2.getInt("level");
					sb.append("-Character: " + name + " Level: " + level + " Count: ");

					Connection con = L1DatabaseFactory.getInstance().getConnection();
					PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM character_items WHERE char_id=? AND item_id=?");
					pstm1.setInt(1, objid);
					pstm1.setInt(2, 40308);
					ResultSet rs = pstm1.executeQuery();
					int count = 0;

					while (rs.next()) {
						count = rs.getInt("count");
						total += count;
						if (count > adena_cut)
							ck2 = true;
					}

					sb.append(count + "\n");
					SQLUtil.close(rs);
					SQLUtil.close(pstm1);
					SQLUtil.close(con);
				}

				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM character_warehouse WHERE account_name=? AND item_id=?");
				pstm1.setString(1, accountName);
				pstm1.setInt(2, 40308);
				ResultSet rs = pstm1.executeQuery();
				int count = 0;

				while (rs.next()) {
					count = rs.getInt("count");
					total += count;
					if (count > adena_cut)
						ck2 = true;
				}

				sb.append(" - 창고 : " + count + "\n");
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);

				sb.append("   총 금액 : " + total + "\n\n");
				if (ck2 && total > adena_cut) {
					sbb.append(sb.toString());

				}
			} catch (SQLException e) {
				//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} finally {
				SQLUtil.close(rs2);
				SQLUtil.close(pstm2);
				SQLUtil.close(con2);
			}
		}

		saveLog(sbb);
		// System.out.println(sbb.toString());
	}

	// private BufferedWriter writer;
	public void saveLog(StringBuilder sb) {
		/*
		 * try { writer = new BufferedWriter(new FileWriter("인벤아덴체크.txt"));
		 * writer.write(sb.toString()); } catch (IOException e) { e.printStackTrace(); }
		 */
		// 저장
		try {
			/*
			 * ObjectOutputStream oos = new ObjectOutputStream(new
			 * FileOutputStream("Test.txt"));
			 *
			 * oos.writeObject(sb.toString());
			 *
			 * oos.close();
			 */

			BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
			writer.write(sb.toString());
			writer.close();

		}

		catch (Exception ex) {
			ex.printStackTrace();
		} // save end
	}

	public void accountCheck() {
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		FastTable<String> f20 = new FastTable<>();
		FastTable<String> f15 = new FastTable<>();
		FastTable<String> f10 = new FastTable<>();
		FastTable<String> f5 = new FastTable<>();

		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();

			String qu = "password";
			pstm2 = con2.prepareStatement("SELECT " + qu + " FROM accounts");
			rs2 = pstm2.executeQuery();

			FastTable<String> charname = new FastTable<>();

			while (rs2.next()) {
				String password = rs2.getString(qu);

				boolean ck = false;
				for (String nn : charname) {
					if (nn.equalsIgnoreCase(password)) {
						ck = true;
						break;
					}
				}

				if (ck) {
					continue;
				}

				charname.add(password);

				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT login FROM accounts WHERE " + qu + "=?");
				pstm1.setString(1, password);
				ResultSet rs = pstm1.executeQuery();
				StringBuilder sb = new StringBuilder();
				sb.append("----------------------");
				short count = 0;

				while (rs.next()) {
					String acc = rs.getString("login");
					sb.append(acc + ",");
					count++;
				}

				// if(!ck){
				sb.append("----------------------");
				// if(count > 5)
				// System.out.println("Count: "+count+" > "+sb.toString());
				if (count >= 20)
					f20.add(password + "Count: " + count + " > " + sb.toString());
				else if (count >= 15)
					f15.add(password + "Count: " + count + " > " + sb.toString());
				else if (count >= 10)
					f10.add(password + "Count: " + count + " > " + sb.toString());
				else if (count >= 5)
					f5.add(password + "Count: " + count + " > " + sb.toString());
				// }
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);
			}
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
	}

	/**
	 * Saves kick and character information for all online players.
	 */
	public void disconnectAllCharacters() {
		try {
			// Beogyeong ticket registration. (start of match -> set ticket sales before end)
			/*try {
				if (!L1BugBearRace.getInstance()._goal) {
					for (L1LittleBugInstance b : L1BugBearRace.getInstance()._littleBug) {
						if (b == null) {
							continue;
						}
						L1BugBearRace.getInstance().race_divAdd(L1BugBearRace.getInstance().getRoundId(), b.getNumber(), 1);
					}
				}
			} catch (Exception e) {
			}*/

			try {
				// Eva system storage
				L1EvaSystem es = EvaSystemTable.getInstance().getSystem(1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String fm = sdf.format(es.getEvaTime().getTime());
				if (es != null && fm.equalsIgnoreCase("2014")) {
					EvaSystemTable.getInstance().updateSystem(es);
				}

				sdf = null;
			} catch (Exception e) {
			}

			try {
				for (L1FieldObjectInstance foi : L1World.getInstance().getAllField()) {
					if (foi == null || foi._destroyed || foi.isDead() || foi.Potal_Open_pcid == 0) {
						continue;
					}

					String AccountName = null;
					L1Object obj = L1World.getInstance().findObject(foi.Potal_Open_pcid);

					if (obj != null && obj instanceof L1PcInstance) {
						AccountName = ((L1PcInstance) obj).getAccountName();
					} else {
						AccountName = LoadAccount(foi.Potal_Open_pcid);
					}

					if (AccountName == null) {
						continue;
					}

					SupplementaryService pwh = WarehouseManager.getInstance().getSupplementaryService(AccountName);
					L1ItemInstance item = ItemTable.getInstance().createItem(60422);
					item.setIdentified(true);
					item.setCount(1);
					pwh.storeTradeItem(item);
					foi.deleteMe();
				}
			} catch (Exception e) {
			}

			Collection<L1PcInstance> players = L1World.getInstance().getAllPlayers();

			// cut all characters
			for (L1PcInstance pc : players) {
				if ((pc instanceof L1RobotInstance) || (pc == null)) {
					continue; // when the pc is up.
				}

				if (pc.getNetConnection() != null) {
					try {
						if (pc.getMapId() >= 9000 && pc.getMapId() <= 9099) { // horse island dungeon
							pc.getInventory().storeItem(500017, 1);
						} else if (pc.getMapId() >= 2102 && pc.getMapId() <= 2151) { // Idiot Dungeon
							pc.getInventory().storeItem(6022, 1);
						}

						pc.save();
						pc.saveInventory();
						// System.out.println(pc.getName());
						pc.getNetConnection().setActiveChar(null);
						pc.getNetConnection().kick();
						pc.getNetConnection().quitGame(pc);
						L1World.getInstance().removeObject(pc);
					} catch (Exception e) {
						System.out.println("Disconnect all chars error!");
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// players.clear();
	}

	private String LoadAccount(int potal_Open_pcid) {
		String account = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM characters WHERE objid=?");
			pstm.setInt(1, potal_Open_pcid);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				return null;
			}
			account = rs.getString("account_name");
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return null;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return account;
	}

	public int saveAllCharInfo() {
		// exception Returns -1 if it occurs, otherwise returns the number of saved people
		int cnt = 0;
		try {
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc instanceof L1RobotInstance) {
					continue;
				}
				cnt++;
				pc.save();
				pc.saveInventory();
			}
		} catch (Exception e) {
			return -1;
		}
		return cnt;
	}

	//public int AdenaMinimum = 50000000;

	//Kicks an online player and saves character information.

	public void disconnectChar(String name) {
		L1PcInstance pc = L1World.getInstance().getPlayer(name);
		L1PcInstance Player = pc;
		synchronized (pc) {
			pc.getNetConnection().kick();
			pc.getNetConnection().quitGame(Player);
			Player.logout();
		}
	}

	public String[] getiplist() {
		return iplist.toArray(new String[iplist.size()]);
	}

	private ArrayList<String> iplist = new ArrayList<>();

	public boolean checkip(String cl) {
		if (iplist.contains(cl)) {
			return true;
		} else {
			return false;
		}

	}

	public void addipl(String cl) {
		iplist.add(cl);
	}

	public void removeip(String cl) {
		if (iplist.contains(cl)) {
			iplist.remove(cl);
		}
	}

	public String[] getaclist() {
		return aclist.toArray(new String[aclist.size()]);
	}

	private ArrayList<String> aclist = new ArrayList<>();

	public boolean checkac(String cl) {
		if (aclist.contains(cl)) {
			return true;
		} else {
			return false;
		}

	}

	public void addac(String cl) {
		aclist.add(cl);
	}

	public void removeac(String cl) {
		if (aclist.contains(cl)) {
			aclist.remove(cl);
		}
	}

	public String[] getbuglist() {
		return buglist.toArray(new String[buglist.size()]);
	}

	private ArrayList<String> buglist = new ArrayList<>();

	public boolean checkbug(String cl) {
		if (buglist.contains(cl)) {
			return true;
		} else {
			return false;
		}

	}

	public void addbug(String cl) {
		buglist.add(cl);
	}

	public void removebug(String cl) {
		if (buglist.contains(cl)) {
			buglist.remove(cl);
		}
	}

	private class ServerShutdownThread extends Thread {
		private final int _secondsCount;

		public ServerShutdownThread(int secondsCount) {
			_secondsCount = secondsCount;
		}

		public void run() {
		    L1World world = L1World.getInstance();
		    try {
		        int secondsCount = _secondsCount;
		        world.broadcastServerMessage("The server will be shutting down soon.");
		        world.broadcastServerMessage("Please log out in a safe place.");

		        // Array of secondsCount values to send broadcast messages
		        int[] broadcastSeconds = {60, 30, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		        int broadcastIndex = 0;

		        while (0 < secondsCount) {
		            if (secondsCount == broadcastSeconds[broadcastIndex]) {
		                if (secondsCount == 60) {
		                    world.broadcastServerMessage("The server will close in 1 minute. Please logout now.");
		                } else if (secondsCount == 30) {
		                    world.broadcastServerMessage("The server will close in 30 seconds. Please logout now.");
		                } else {
		                    world.broadcastServerMessage("The server will close in " + secondsCount + " seconds. Please logout now.");
		                }
		                broadcastIndex++;
		            } else if (secondsCount % 60 == 0) {
		                System.out.println("The game will shutdown in " + secondsCount / 60 + " minutes.");
		                world.broadcastServerMessage("The game will shutdown in " + secondsCount / 60 + " minutes.");
		            }
		            Thread.sleep(1000);
		            secondsCount--;
		        }
		        shutdown();
		    } catch (InterruptedException e) {
		        world.broadcastServerMessage("Server shutdown aborted. The server will continue running.");
		        return;
		    }
		}

		/*@Override
		public void run() {
			L1World world = L1World.getInstance();
			try {
				int secondsCount = _secondsCount;
				world.broadcastServerMessage("The server will be shutting down soon.");
				world.broadcastServerMessage("Please log out in a safe place.");
				while (0 < secondsCount) {
					if (secondsCount == 60) {
						world.broadcastServerMessage("The server will close in 1 minute. Please logout now.");
					} else if (secondsCount == 30) {
						world.broadcastServerMessage("The server will close in 30 seconds. Please logout now.");
					} else {
						if (secondsCount % 60 == 0) {
							System.out.println("The game will shutdown in " + secondsCount / 60 + " minutes.");
							world.broadcastServerMessage("The game will shutdown in " + secondsCount / 60 + " minutes.");
						}
					}
					Thread.sleep(1000);
					secondsCount--;
				}
				shutdown();
			} catch (InterruptedException e) {
				world.broadcastServerMessage("Server shutdown aborted. The server will continue running.");
				return;
			}
		}*/
	}

	private ServerShutdownThread _shutdownThread = null;

	public synchronized void shutdownWithCountdown(int secondsCount) {
		if (_shutdownThread != null) {
			// Already requesting a shutdown
			// TODO Error notification may be required
			return;
		}

		_shutdownThread = new ServerShutdownThread(secondsCount);
		GeneralThreadPool.getInstance().execute(_shutdownThread);
	}

	/*
	 * public void shutdown() { disconnectAllCharacters(); eva.savelog();
	 * System.exit(0); }
	 */
	public void shutdown() {
		/** 2016.11.26 MJ 앱센터 LFC **/
		MJInstanceLoadManager.getInstance().release();
		/** 2016.11.26 MJ 앱센터 LFC **/

		//LinAllManager.getInstance();
		//LinAllManager.savelog();

		/** 2016.11.24 MJ 앱센터 시세 **/
		MJDShopStorage.clearProcess();
		/** 2016.11.24 MJ 앱센터 시세 **/
		/** MJCTSystem **/
		MJCTLoadManager.getInstance().release();
		CharacterQuestTable.getInstance().updateAll();

		if (_shutdownThread != null) {
			disconnectAllCharacters();
			InvSwapController.getInstance().initDB();
			System.exit(0);
		}
	}

	public synchronized void abortShutdown() {
		if (_shutdownThread == null) {
			// Didn't ask for a shutdown
			// may need TODO error notification
			return;
		}

		_shutdownThread.interrupt();
		_shutdownThread = null;
	}

	public void Halloween() {
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm1 = con.prepareStatement("DELETE FROM character_elf_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm2 = con.prepareStatement("DELETE FROM clan_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm3 = con.prepareStatement("DELETE FROM character_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm3.executeUpdate();
			pstm2.executeUpdate();
			pstm1.executeUpdate();
			pstm.executeUpdate();
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(pstm1);
			SQLUtil.close(pstm2);
			SQLUtil.close(pstm3);
			SQLUtil.close(con);
		}
	}

	public void RaceTicket() {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id >= 8000000");
			pstm.executeUpdate();
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void INNKeyDelete() {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id = 49312 OR item_id = 40312");
			pstm.executeUpdate();
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void DuplicationLoginCheck(String name, String msg) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO duplication_login_check SET name=?, msg=?");
			pstm.setString(1, name);
			pstm.setString(2, msg);
			pstm.executeUpdate();
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void shopLogSalesCountCheck() {
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		FastTable<log_shop_total> list = new FastTable<>();
		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM log_shop");
			rs2 = pstm2.executeQuery();

			while (rs2.next()) {
				String name = rs2.getString("user_name");
				int count = rs2.getInt("total_price");
				String itemname = rs2.getString("item_name");
				if (itemname.equalsIgnoreCase("레이스 표")) {
					continue;
				}

				boolean ck = false;

				for (log_shop_total lst : list) {
					if (lst.name.equalsIgnoreCase(name)) {
						lst.total += count;
						ck = true;
						break;
					}
				}

				if (!ck) {
					log_shop_total lst = new log_shop_total();
					lst.name = name;
					lst.total = count;
					list.add(lst);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= 10; i++) {
				String temp_name = "";
				long temp_t = 0;

				for (log_shop_total lst : list) {
					if (temp_t < lst.total) {
						temp_t = lst.total;
						temp_name = lst.name;
					}
				}

				if (!temp_name.equalsIgnoreCase("")) {
					sb.append(i + ". 판매자: " + temp_name + " 판매액수: " + temp_t + "\n");
					for (log_shop_total lst : list.toArray(new log_shop_total[list.size()])) {
						if (lst.name.equalsIgnoreCase(temp_name)) {
							list.remove(lst);
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
	}

	class log_shop_total {
		public String name = "";
		public long total = 0;
	}
}
