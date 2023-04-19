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

import java.util.logging.Logger;

import l1j.server.server.GMCommandsConfig;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1GMRoom implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1GMRoom.class.getName());

	private L1GMRoom() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1GMRoom();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			int i = 0;
			try {
				i = Integer.parseInt(arg);
			} catch (NumberFormatException e) {
			}

			if (i == 1) {
				L1Teleport.teleport(pc, 32737, 32796, (short) 99, 5, false); // Hotel
			} else if (i == 2) {
				L1Teleport.teleport(pc, 33052, 32339, (short) 4, 5, false); // Mother tree
			} else if (i == 3) {
				L1Teleport.teleport(pc, 32644, 32955, (short) 0, 5, false); // pandora
			} else if (i == 4) {
				L1Teleport.teleport(pc, 34055, 32290, (short) 4, 5, false); // Oren
			} else if (i == 5) {
				L1Teleport.teleport(pc, 33427, 32816, (short) 4, 5, false); // Giran
			} else if (i == 6) {
				L1Teleport.teleport(pc, 33047, 32761, (short) 4, 5, false); // Kent
			} else if (i == 7) {
				L1Teleport.teleport(pc, 32612, 33191, (short) 4, 5, false); // Windawood
			} else if (i == 8) {
				L1Teleport.teleport(pc, 33611, 33253, (short) 4, 5, false); // Heine
			} else if (i == 9) {
				L1Teleport.teleport(pc, 33082, 33390, (short) 4, 5, false); // Silver Knights Town
			} else if (i == 10) {
				L1Teleport.teleport(pc, 32572, 32944, (short) 0, 5, false); // Talking island
			} else if (i == 11) {
				L1Teleport.teleport(pc, 33964, 33254, (short) 4, 5, false); // Aden
			} else if (i == 12) {
				L1Teleport.teleport(pc, 32635, 32818, (short) 303, 5, false); // Lawful Temple
			} else if (i == 13) {
				L1Teleport.teleport(pc, 32828, 32848, (short) 70, 5, false); // Forgotten Island
			} else if (i == 14) {
				L1Teleport.teleport(pc, 32736, 32787, (short) 15, 5, false); // Kent Castle
			} else if (i == 15) {
				L1Teleport.teleport(pc, 32735, 32788, (short) 29, 5, false); // Windawood Castle
			} else if (i == 16) {
				L1Teleport.teleport(pc, 32730, 32802, (short) 52, 5, false); // Giran Castle
			} else if (i == 17) {
				L1Teleport.teleport(pc, 32572, 32826, (short) 64, 5, false); // Heine Castle
			} else if (i == 18) {
				L1Teleport.teleport(pc, 32895, 32533, (short) 300, 5, false); // Aden castle
			} else if (i == 19) {
				L1Teleport.teleport(pc, 33167, 32775, (short) 4, 5, false); // Kent guard tower
			} else if (i == 20) {
				L1Teleport.teleport(pc, 32674, 33408, (short) 4, 5, false); // Windawood guard tower
			} else if (i == 21) {
				L1Teleport.teleport(pc, 33630, 32677, (short) 4, 5, false); // Giran guard tower
			} else if (i == 22) {
				L1Teleport.teleport(pc, 33524, 33394, (short) 4, 5, false); // Heine guard tower
			} else if (i == 23) {
				L1Teleport.teleport(pc, 32424, 33068, (short) 440, 5, false); // Pirate island
			} else if (i == 24) {
				L1Teleport.teleport(pc, 32800, 32868, (short) 1001, 5, false); // Behemoth
			} else if (i == 25) {
				L1Teleport.teleport(pc, 32800, 32856, (short) 1000, 5, false); // Sylveria
			} else if (i == 26) {
				L1Teleport.teleport(pc, 32630, 32903, (short) 780, 5, false); // Thebes desert
			} else if (i == 27) {
				L1Teleport.teleport(pc, 32743, 32799, (short) 781, 5, false); // Inside Thebes Pyramid
			} else if (i == 28) {
				L1Teleport.teleport(pc, 32735, 32830, (short) 782, 5, false); // Altar of Thebes orisis
			} else if (i == 29) {
				L1Teleport.teleport(pc, 32538, 32958, (short) 777, 5, false); // Land of Abandoned
			} else if (i == 30) {
				L1Teleport.teleport(pc, 33052, 32339, (short) 4, 5, false); // fairy forest
			} else if (i == 31) {
				L1Teleport.teleport(pc, 33506, 32735, (short) 88, 5, false); // Giran arena
			} else if (i == 32) {
				L1Teleport.teleport(pc, 32768, 32829, (short) 610, 5, false); // Cherry Blossom Village
			} else if (i == 33) {
				L1Teleport.teleport(pc, 32791, 32869, (short) 612, 5, false); // broken
			} else if (i == 34) {
				L1Teleport.teleport(pc, 32769, 32827, (short) 5554, 5, false); // broken
			} else if (i == 35) {
				L1Teleport.teleport(pc, 32685, 32870, (short) 2005, 5, false); // hidden valley
			} else if (i == 36) {
				L1Teleport.teleport(pc, 32895, 32525, (short) 300, 5, true); // Aden castle
			} else if (i == 37) {
				L1Teleport.teleport(pc, 32928, 32864, (short) 6202, 5, true); // broken
			} else {
				L1Location loc = GMCommandsConfig.ROOMS.get(arg.toLowerCase());
				if (loc == null) {
					pc.sendPackets(new S_SystemMessage(
							"1.GMRoom 2.M Tree 3.Pandora 4.Oren 5.Giran 6.Kent"), true);
					pc.sendPackets(new S_SystemMessage(
							"7.WW 8.Heine 9.SKT 10.TI 11.Aden 12.Law Temple"), true);
					pc.sendPackets(new S_SystemMessage(
							"13.FI 14.KentC 15.WWC 16.GiranC 17.HeineC"), true);
					pc.sendPackets(new S_SystemMessage(
							"18.AdenC 19.KentGT 20.WWGT 21.GiranGT"), true);
					pc.sendPackets(new S_SystemMessage(
							"22.HeineGT 23.PI 24.Behemoth 25.Sylveria"), true);
					pc.sendPackets(new S_SystemMessage(
							"26.Thebes 27.Inside Thebes 28.Altar of Thebes"), true);
					pc.sendPackets(new S_SystemMessage(
							"29.LoA 30.M Tree 31.Giran Arena"), true);
					pc.sendPackets(new S_SystemMessage(
							"32.Cherry Village 33.Broke 34.Broke 35.HV 36.Aden C"), true);
					pc.sendPackets(new S_SystemMessage("37.Broke"), true);
					return;
				}
				L1Teleport.teleport(pc, loc.getX(), loc.getY(),
						(short) loc.getMapId(), 5, false);
			}

			if (i > 0 && i < 38) {
				pc.sendPackets(new S_SystemMessage("System: Moved to teleport (" + i + ")"), true);
			}
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".gmroom [num] (Place names refer to GMCommands.xml)"), true);
		}
	}
}