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
package l1j.server.server.serverpackets;

import java.util.logging.Logger;

import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.utils.PerformanceTimer;

public class S_WhoCharinfo extends ServerBasePacket {
	private static final String S_WHO_CHARINFO = "[S] S_WhoCharinfo";
	private static Logger _log = Logger.getLogger(S_WhoCharinfo.class.getName());

	private byte[] _byte = null;

	public S_WhoCharinfo(L1PcInstance pc) {
		_log.fine("Who charpack for : " + pc.getName());

		String lawfulness = "";
		int lawful = pc.getLawful();
		if (lawful < 0) {
			lawfulness = "\\fA[Chaotic] ";
		} else if (lawful >= 0 && lawful < 500) {
			lawfulness = "\\f>[Neutral] ";
		} else if (lawful >= 500) {
			lawfulness = "\\fU[Lawful] ";
		}

		writeC(Opcodes.S_MESSAGE);
		writeC(0x08);

		String title = "";
		String clan = "";

		if (!pc.getTitle().equalsIgnoreCase("")) {
			title = "\\aD\"" + pc.getTitle() + "\" ";
		}

		if (pc.getClanid() > 0) {
			clan = "[" + pc.getClanname() + "]";
		}
		// show kills
		//writeS(title + "\\f>" + pc.getName() + " " + lawfulness + " " + clan + "\n\r" + "\\fF Kills: " + pc.getKills() + "\\fA Deaths: " + pc.getDeaths());
		StringBuilder string = new StringBuilder();
		string.append(title).append("\\f>").append(pc.getName()).append(" ").append(lawfulness).append(" ").append(clan).append("\n\r").append("\\fF Kills: ").append(pc.getKills()).append("\\fA Deaths: ").append(pc.getDeaths());
		String output = string.toString();
		
		writeS(output);
		writeD(0);
		
		// testing
		//stringPerformanceOld(pc);
		//stringPerformanceNew(pc);
	}

	public S_WhoCharinfo(L1NpcInstance pc) {
		_log.fine("Who charpack for : " + pc.getName());

		String lawfulness = "";
		int lawful = pc.getLawful();
		if (lawful < 0) {
			lawfulness = "\\fA[Chaotic] ";
		} else if (lawful >= 0 && lawful < 500) {
			lawfulness = "\\f>[Neutral] ";
		} else if (lawful >= 500) {
			lawfulness = "\\fU[Lawful] ";
		}

		writeC(Opcodes.S_MESSAGE);
		writeC(0x08);

		String title = "";
		String clan = "";

		if (pc instanceof GambleInstance) {
			clan = ((GambleInstance) pc).getClanname();
			clan = clan == null ? "" : ("\\aE[" + clan + "]");
		}

		if (!pc.getTitle().equalsIgnoreCase("")) {
			title = "\\aD\"" + pc.getTitle() + "\" ";
		}

		writeS("\\aA" + pc.getName() + lawfulness + title + clan);
		// writeD(0x80157FE4);
		writeD(0);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_WHO_CHARINFO;
	}
	
	private void stringPerformanceOld(L1PcInstance pc) {
		PerformanceTimer timer = new PerformanceTimer();
		String title = "";
		String clan = "";
		String lawfulness = "";
		int lawful = pc.getLawful();
		if (lawful < 0) {
			lawfulness = "\\fA[Chaotic] ";
		} else if (lawful >= 0 && lawful < 500) {
			lawfulness = "\\f>[Neutral] ";
		} else if (lawful >= 500) {
			lawfulness = "\\fU[Lawful] ";
		}
		for (int i = 0; i < 10000; i++) {
				writeS(title + "\\f>" + pc.getName() + " " + lawfulness + " " + clan + "\n\r" + "\\fF Kills: " + pc.getKills() + "\\fA Deaths: " + pc.getDeaths());
			}
		System.out.println("Flat String: " + timer.get() + "ms");
		// TODO Auto-generated method stub
	}
	
	private void stringPerformanceNew(L1PcInstance pc) {
		PerformanceTimer timer = new PerformanceTimer();
		String title = "";
		String clan = "";
		String lawfulness = "";
		int lawful = pc.getLawful();
		if (lawful < 0) {
			lawfulness = "\\fA[Chaotic] ";
		} else if (lawful >= 0 && lawful < 500) {
			lawfulness = "\\f>[Neutral] ";
		} else if (lawful >= 500) {
			lawfulness = "\\fU[Lawful] ";
		}
		
		for (int i = 0; i < 10000; i++) {
			StringBuilder string = new StringBuilder();
			string.append(title).append("\\f>").append(pc.getName()).append(" ").append(lawfulness).append(" ").append(clan).append("\n\r").append("\\fF Kills: ").append(pc.getKills()).append("\\fA Deaths: ").append(pc.getDeaths());
			String output = string.toString();
				writeS(output);
			}
		System.out.println("String Build: " + timer.get() + "ms");
		// TODO Auto-generated method stub
	}
}

