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
package l1j.server.server.model.Instance;

import java.util.List;

import l1j.server.server.datatables.ClanTable;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1WarSpawn;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_CastleMaster;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Npc;

public class L1CustomCrownInstance extends L1NpcInstance {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public L1CustomCrownInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance player) {
		boolean in_war = false;
		if (player.getClanid() == 0) { // 크란미소속
			return;
		}
		String playerClanName = player.getClanname();
		L1Clan clan = L1World.getInstance().getClan(playerClanName);
		if ((clan == null) || !player.isCrown() || (player.getGfxId().getTempCharGfx() != 0 && // 변신중
				player.getGfxId().getTempCharGfx() != 1)) {
			return;
		}
		if (player.getId() != clan.getLeaderId()) // 혈맹주 이외
			return;

		if (!checkRange(player)) // 크라운의 1 셀 이내
			return;

		if (clan.getCastleId() != 0) {// 성주 크란
			player.sendPackets(new S_ServerMessage(474));// 당신은 벌써 성을 소유하고 있으므로,
															// 다른 시로를 잡을 수 없습니다.
			return;
		}

		// 크라운의 좌표로부터 castle_id를 취득
		int castle_id = L1CastleLocation
				.getCastleId(getX(), getY(), getMapId());

		// 포고하고 있을까 체크.단, 성주가 없는 경우는 포고 불요
		boolean existDefenseClan = false;
		L1Clan defence_clan = null;
		for (L1Clan defClan : L1World.getInstance().getAllClans()) {
			if (castle_id == defClan.getCastleId()) {
				// 전의 성주 크란
				defence_clan = L1World.getInstance().getClan(
						defClan.getClanName());
				existDefenseClan = true;
				break;
			}
		}
		List<L1War> wars = L1World.getInstance().getWarList(); // 전전쟁 리스트를 취득
		for (L1War war : wars) {
			if (castle_id == war.GetCastleId()) { // 이마이성의 전쟁
				in_war = war.CheckClanInWar(playerClanName);
				break;
			}
		}
		if (existDefenseClan && !in_war) { // 성주가 있어, 포고하고 있지 않는 경우
			return;
		}

		if (player.isDead())
			return;

		// clan_data의 hascastle를 갱신해, 캐릭터에 크라운을 붙인다
		if (existDefenseClan && defence_clan != null) { // 전의 성주 크란이 있다
			defence_clan.setCastleId(0);
			ClanTable.getInstance().updateClan(defence_clan);
			L1PcInstance defence_clan_member[] = defence_clan
					.getOnlineClanMember();
			for (L1PcInstance element : defence_clan_member) {
				if (element.getId() == defence_clan
						.getLeaderId()) { // 전의 성주 크란의 군주
					element.sendPackets(new S_CastleMaster(0,
							element.getId()), true);
					// Broadcaster.broadcastPacket(defence_clan_member[m], new
					// S_CastleMaster(0, defence_clan_member[m].getId()));
					L1World.getInstance().broadcastPacketToAll(
							new S_CastleMaster(0,
									element.getId()), true);
					break;
				}
			}
		}
		clan.setCastleId(castle_id);
		ClanTable.getInstance().updateClan(clan);
		player.sendPackets(new S_CastleMaster(castle_id, player.getId()), true);
		// Broadcaster.broadcastPacket(player, new S_CastleMaster(castle_id,
		// player.getId()));
		L1World.getInstance().broadcastPacketToAll(
				new S_CastleMaster(castle_id, player.getId()), true);

		// 크란원 이외를 거리에 강제 텔레포트
		int[] loc = new int[3];
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc.getClanid() != player.getClanid() && !pc.isGm()) {
				if (L1CastleLocation.checkInWarArea(castle_id, pc)) {
					// 기내에 있다
					loc = L1CastleLocation.getGetBackLoc(castle_id);
					int locx = loc[0];
					int locy = loc[1];
					short mapid = (short) loc[2];
					L1Teleport.teleport(pc, locx, locy, mapid, pc
							.getMoveState().getHeading(), true);
				}
			}
		}

		// 메세지 표시
		for (L1War war : wars) {
			if (war.CheckClanInWar(playerClanName) && existDefenseClan) {
				// 자크란이 참가중에서, 성주가 교대
				war.WinCastleWar(playerClanName);
				break;
			}
		}

		if (clan.getOnlineClanMember().length > 0) {
			// 성을 점거했습니다.
			S_ServerMessage s_serverMessage = new S_ServerMessage(643);
			for (L1PcInstance pc : clan.getOnlineClanMember()) {
				pc.sendPackets(s_serverMessage);
			}
		}
		deleteMe();
		L1TowerInstance lt = null;
		for (L1Object l1object : L1World.getInstance().getObject()) {
			if (l1object instanceof L1TowerInstance) {
				lt = (L1TowerInstance) l1object;
				if (L1CastleLocation.checkInWarArea(castle_id, lt)) {
					lt.deleteMe();
				}
			}

		}
		// 타워를 spawn 한다
		L1WarSpawn warspawn = new L1WarSpawn();
		warspawn.SpawnTower(castle_id);

		/*
		 * for (L1DoorInstance door :
		 * DoorSpawnTable.getInstance().getDoorList()) { if
		 * (L1CastleLocation.checkInWarArea(castle_id, door)) {
		 * door.repairGate(); } }
		 */

		// 면류관을 잡았으니 전쟁중 삭제해야 다시 선포할수잇음
		L1War[] wr = L1World.getInstance().get_wars();
		for (L1War element : wr) {
			if (castle_id == element.GetCastleId())
				L1World.getInstance().removeWar(element);
		}
		wr = null;
	}

	@Override
	public void deleteMe() {
		_destroyed = true;
		if (getInventory() != null) {
			getInventory().clearItems();
		}
		allTargetClear();
		_master = null;
		L1World.getInstance().removeVisibleObject(this);
		L1World.getInstance().removeObject(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.getNearObjects().removeKnownObject(this);
			pc.sendPackets(new S_RemoveObject(this), true);
		}
		getNearObjects().removeAllKnownObjects();
	}

	private boolean checkRange(L1PcInstance pc) {
		return (getX() - 1 <= pc.getX() && pc.getX() <= getX() + 1
				&& getY() - 1 <= pc.getY() && pc.getY() <= getY() + 1);
	}
}

