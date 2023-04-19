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

import java.util.ArrayList;
import java.util.logging.Logger;

import l1j.server.server.datatables.LetterTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

public class L1DeleteGroundItem implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1DeleteGroundItem.class
			.getName());

	private L1DeleteGroundItem() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1DeleteGroundItem();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			ArrayList<L1PcInstance> players = null;
			L1Inventory groundInventory = null;
			LetterTable lettertable = null;
			L1ItemInstance litem = null;
			for (L1Object l1object : L1World.getInstance().getAllItem()) {
				if (l1object instanceof L1ItemInstance) {
					litem = (L1ItemInstance) l1object;
					if (litem.getX() == 0 && litem.getY() == 0) {
						continue;
					}
					players = L1World.getInstance().getVisiblePlayer(litem, 0);
					if (0 == players.size()) {
						groundInventory = L1World.getInstance().getInventory(
								litem.getX(), litem.getY(), litem.getMapId());
						int itemId = litem.getItem().getItemId();
						if (itemId == 40314 || itemId == 40316) { // pet's amulet
							PetTable.getInstance().deletePet(litem.getId());
						} else if (itemId >= 49016 && itemId <= 49025) { // mail
							lettertable = new LetterTable();
							lettertable.deleteLetter(litem.getId());
						} else if (itemId >= 41383 && itemId <= 41400) { // furniture
							// This is caught from an exception in the instance above,
							// but I think the bottom one won't work, so I commented out
							/* tek123
							 * if (l1object instanceof L1FurnitureInstance) {
							 * furniture = (L1FurnitureInstance) l1object; if
							 * (furniture.getItemObjId() == l1iteminstance
							 * .getId()) { // 이미 꺼내고 있는 가구
							 * FurnitureSpawnTable.getInstance()
							 * .deleteFurniture(furniture); } }
							 */
						}
						groundInventory.removeItem(litem);
					}
				}
			}
			L1World.getInstance().broadcastServerMessage("\\aKItems on the world map have been removed by the GM.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}