/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.command.executor;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1PSS implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1Adena.class.getName());

	private L1PSS() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1PSS();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			StringTokenizer stringArg = new StringTokenizer(arg);
			String flag = stringArg.nextToken();
			//Collection<L1PcInstance> players = null;
			if (flag.compareToIgnoreCase("on") == 0) {
				Config.PSS_CONTROLLER = true;
				L1World.getInstance().broadcastServerMessage("GameMaster: " + pc.getName() + " has turned on PSS.");
			} else if (flag.compareToIgnoreCase("off") == 0) {
				Config.PSS_CONTROLLER = false;
				//pc.sendPackets(new S_SupportSystem(pc, S_SupportSystem.SC_FINISH_PLAY_SUPPORT_ACK));
				L1World.getInstance().broadcastServerMessage("GameMaster: " + pc.getName() + " has turned off PSS.");
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage((new StringBuilder()).append("GMC Exception: Use command " + cmdName + " [on/off]").toString()));
		}
	}
}