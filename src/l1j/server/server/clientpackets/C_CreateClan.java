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

package l1j.server.server.clientpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ClanBlessHuntInfo;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_QuestTalkIsland;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1QuestInfo;
// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;

public class C_CreateClan extends ClientBasePacket {


	private static final String C_CREATE_CLAN = "[C] C_CreateClan";

	public C_CreateClan(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		try {
			String s = readS();

			if (s == null || s.equalsIgnoreCase(""))
				return;

			L1PcInstance l1pcinstance = clientthread.getActiveChar();

			for (char ac : s.toCharArray()) {
				if (!Character.isLetterOrDigit(ac)) {
					S_SystemMessage sm = new S_SystemMessage("Pledge cannot be created, invalid characters found. (" + ac + ")");
					l1pcinstance.sendPackets(sm, true);
					return;
				}
			}

			int numOfNameBytes = 0;
			numOfNameBytes = s.getBytes("UTF-8").length;

			if (l1pcinstance.isCrown()) { // Prince or Princess
				if (l1pcinstance.getClanid() == 0) {
					if (!l1pcinstance.getInventory().checkItem(40308, 30000)) {
						S_SystemMessage sm = new S_SystemMessage("Pledge cannot be created, 30,000 adena is required.");
						l1pcinstance.sendPackets(sm, true);
						return;
					}

					l1pcinstance.getInventory().consumeItem(L1ItemId.ADENA, 30000);

					if (l1pcinstance.getLevel() < 30) {
						S_SystemMessage sm = new S_SystemMessage("Players below level 30 cannot create a pledge.");
						l1pcinstance.sendPackets(sm, true);
						return;
					}
					for (int i = 0; i < s.length(); i++) {
						if (s.charAt(i) == ' ' || s.charAt(i) == 'ㅤ') {
							S_ServerMessage sm = new S_ServerMessage(53);
							l1pcinstance.sendPackets(sm, true);
							return;
						}
					}
					if (8 < (numOfNameBytes - s.length()) || 16 < numOfNameBytes) {

						S_SystemMessage sm = new S_SystemMessage("That pledge name is too long.");
						l1pcinstance.sendPackets(sm, true);
					}

					S_SystemMessage sm99 = new S_SystemMessage("There is already a pledge with that name.");
					if (s.equalsIgnoreCase("ruphy")) {
						l1pcinstance.sendPackets(sm99, true);
						return;
					}

					for (L1Clan clan : L1World.getInstance().getAllClans()) {
						if (clan.getClanName().toLowerCase().equals(s.toLowerCase())) {
							l1pcinstance.sendPackets(sm99, true);
							return;
						}
					}

					if (gambleClanCheck(s)) {
						l1pcinstance.sendPackets(sm99, true);
						return;
					}
					L1Clan clan = ClanTable.getInstance().createClan(l1pcinstance, s); // Creation of clan

					if (clan != null) {
						S_ServerMessage sm = new S_ServerMessage(84, s);
						l1pcinstance.sendPackets(sm, true);
					}
					if (l1pcinstance.getClanid() != 0) {
						L1QuestInfo info = l1pcinstance.getQuestList(272);
						if (info != null && info.end_time == 0) {
							info.ck[0] = l1pcinstance.getClanid();
							if (info.ck[0] > 1) {
								info.ck[0] = 1;
							}
							l1pcinstance.sendPackets(new S_QuestTalkIsland(l1pcinstance, 272, info));
						}
					}

					if (Config.CLAN_BLESS_ALL_USE) {
						l1pcinstance.getClan().setCaveOpen(true);
						ClanBlessHuntInfo.getInstance().settingClanBlessHuntMaps(clan);
						ClanTable.getInstance().updateClan(clan);
					}

				} else {

					S_SystemMessage sm = new S_SystemMessage("I have already created a blood pledge.");
					l1pcinstance.sendPackets(sm, true);
				}
			} else {

				S_SystemMessage sm = new S_SystemMessage("Only princes and princesses can create blood pledges.");
				l1pcinstance.sendPackets(sm, true);
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private boolean gambleClanCheck(String s) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM npc_gamble_spawnlist WHERE clan_name=?");
			pstm.setString(1, s);
			rs = pstm.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	@Override
	public String getType() {
		return C_CREATE_CLAN;
	}

}

