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
/*
 * $Header: /cvsroot/l2j/L2_Gameserver/java/net/sf/l2j/Server.java,v 1.5 2004/11/19 08:54:43 l2chef Exp $
 *
 * $Author: l2chef $
 * $Date: 2004/11/19 08:54:43 $
 * $Revision: 1.5 $
 * $Log: Server.java,v $
 * Revision 1.5  2004/11/19 08:54:43  l2chef
 * database is now used
 *
 * Revision 1.4  2004/07/08 22:42:28  l2chef
 * logfolder is created automatically
 *
 * Revision 1.3  2004/06/30 21:51:33  l2chef
 * using jdk logger instead of println
 *
 * Revision 1.2  2004/06/27 08:12:59  jeichhorn
 * Added copyright notice
 */
package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.netty.CodecFactory;
import l1j.server.netty.ProtocolHandler;
//import server.monitor.MonitorManager;
import l1j.server.server.utils.SQLUtil;

public class Server {
	private static final String LOG_PROP = "./config/log.properties";
	static private ServerBootstrap sb;
	static private CodecFactory cf;
	@SuppressWarnings("unused")
	static private Channel channel;
	public static Calendar StartTime;

    public static void main(String[] args)
    {
        try {
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public Server() {
		//Config.load();
		initLogManager();
		initDBFactory();
		try {
			startGameServer();
			startLoginServer();
			//clearDB();
		} catch (Exception e) {
			e.printStackTrace();
		}

		StartTime = Calendar.getInstance();
		StartTime.setTimeInMillis(System.currentTimeMillis());
	}

	public void shutdown() {
		GameServer.getInstance().shutdown();
	}

	private void initLogManager() {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(LOG_PROP));
			LogManager.getLogManager().readConfiguration(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Config.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDBFactory() { // L1DatabaseFactory initial setting
		L1DatabaseFactory.setDatabaseSettings(Config.DB_DRIVER, Config.DB_URL, Config.DB_LOGIN, Config.DB_PASSWORD);
		try {
			L1DatabaseFactory.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startGameServer() {
		try {
			GameServer.getInstance().initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startLoginServer() {
		try {
			LoginController.getInstance().setMaxAllowedOnlinePlayers(Config.MAX_ONLINE_USERS);
			sb = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
			cf = new CodecFactory(new ProtocolHandler());
			sb.setPipelineFactory(cf);

			// It is an algorithm that maintains the session even if the client disconnects to increase server
			// performance. false because it is not needed
			sb.setOption("child.keepAlive", false);
			// Naggle inactive.
			sb.setOption("child.tcpNoDelay", true);
			// The maximum amount of packets to receive.
			sb.setOption("child.receiveBufferSize", 2048);
			sb.setOption("connectTimeoutMillis", 300);
			// server activation.
			channel = sb.bind(new InetSocketAddress(Config.GAME_SERVER_PORT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearDB() throws SQLException {
		Connection c = null;
		PreparedStatement p = null;
		try {
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement("call deleteData(?)");
			p.setInt(1, Config.DELETE_DB_DAYS);
			p.executeUpdate();
		} catch (Exception e) {
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
	}
}

