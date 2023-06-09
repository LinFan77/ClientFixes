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

package l1j.server.server.model.item.function;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_IdentifyDesc;
import l1j.server.server.serverpackets.S_ItemStatus;
import l1j.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class IdentifyScroll extends L1ItemInstance {

	public IdentifyScroll(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(packet.readD());

			if (l1iteminstance1 == null)
				return;
			pc.sendPackets(new S_IdentifyDesc(l1iteminstance1), true);
			if (!l1iteminstance1.isIdentified()) {
				l1iteminstance1.setIdentified(true);
				pc.getInventory().updateItem(l1iteminstance1,
						L1PcInventory.COL_IS_ID);
			}
			int mainid1 = l1iteminstance1.getItem().getMainId();
			L1ItemInstance main = null;
			if (mainid1 == l1iteminstance1.getItem().getItemId()) {
				main = pc.getInventory().findItemId(mainid1);
				if (main != null) {
					if (main.isEquipped()) {
						pc.sendPackets(new S_ItemStatus(main, pc, true, true));
						//pc.sendPackets(new S_SystemMessage(Integer.toString(mainid1)));
					} else {
						pc.sendPackets(new S_ItemStatus(main, pc, true, false));
						//pc.sendPackets(new S_SystemMessage(Integer.toString(mainid1)));
					}
				}
			}
			// pc.sendPackets(new S_ItemStatus(l1iteminstance1), true);
			pc.getInventory().removeItem(useItem, 1);
		}
	}
}