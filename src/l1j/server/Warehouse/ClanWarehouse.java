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
package l1j.server.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.SQLUtil;

public class ClanWarehouse extends Warehouse {
	private static final long serialVersionUID = 1L;
	private static Logger _log = Logger.getLogger(ClanWarehouse.class.getName());
	private boolean key = false;
	private int pcIdUsingClanWarehouse = -1;

	public ClanWarehouse(String clan) {
		super(clan);
	}

	public synchronized boolean lock(int id) {
		if (!key || pcIdUsingClanWarehouse == id) {
			key = true;
			pcIdUsingClanWarehouse = id;
			return key;
		} else
			return false;
	}

	public synchronized void unlock(int id) {
		if (id == pcIdUsingClanWarehouse)
			key = false;
	}

	@Override
	protected int getMax() {
		return Config.MAX_CLAN_WAREHOUSE_ITEM;
	}

	@Override
	/*public synchronized boolean checkPledgeWareHouseItems(int itemid, int count) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT count FROM clan_warehouse WHERE clan_name = ? AND item_id = ?");
			pstm.setString(1, getName());
			pstm.setInt(2, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				if (count <= rs.getInt("count")) {
					return true;
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}*/

	public synchronized boolean checkPledgeWareHouseItems(int itemId, int count) {
	    try (Connection con = L1DatabaseFactory.getInstance().getConnection();
	         PreparedStatement pstmt = con.prepareStatement(
	             "SELECT count FROM clan_warehouse WHERE clan_name = ? AND item_id = ?")) {

	        pstmt.setString(1, getName());
	        pstmt.setInt(2, itemId);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                if (count <= rs.getInt("count")) {
	                    return true;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
	    }
	    return false;
	}


	@SuppressWarnings("resource")
	@Override
	public synchronized void loadItems() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM clan_warehouse WHERE clan_name = ?");
			pstm.setString(1, getName());
			rs = pstm.executeQuery();
			L1ItemInstance item = null;
			L1Item itemTemplate = null;
			while (rs.next()) {
				item = new L1ItemInstance();
				int objectId = rs.getInt("id");
				item.setId(objectId);
				int itemId = rs.getInt("item_id");
				itemTemplate = ItemTable.getInstance().getTemplate(itemId);
				if (itemTemplate == null) {
					throw new NullPointerException("item_id=" + itemId + " not found");
				}
				item.setItem(itemTemplate);
				item.setCount(rs.getInt("count"));
				item.setEquipped(false);
				item.setEnchantLevel(rs.getInt("enchantlvl"));
				item.setIdentified(rs.getInt("is_id") != 0 ? true : false);
				item.set_durability(rs.getInt("durability"));
				item.setChargeCount(rs.getInt("charge_count"));
				item.setRemainingTime(rs.getInt("remaining_time"));
				item.setLastUsed(rs.getTimestamp("last_used"));
				item.setBless(item.getItem().getBless());
				item.setAttrEnchantLevel(rs.getInt("attr_enchantlvl"));
				item.setEndTime(rs.getTimestamp("end_time"));
				item.setCreaterName(rs.getString("CreaterName"));
				item.setDemonBongin(rs.getInt("demon_bongin") == 0 ? false : true);
				_items.add(item);
				L1World.getInstance().storeObject(item);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/*@Override
	public synchronized void insertItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con.prepareStatement("SELECT * FROM clan_warehouse WHERE id = ?");
			pstm2.setInt(1, item.getId());
			rs = pstm2.executeQuery();
			if (!rs.next()) {
				pstm = con.prepareStatement(
						"INSERT INTO clan_warehouse SET id = ?, clan_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id= ?, durability = ?, charge_count = ?, remaining_time = ?, last_used = ?, attr_enchantlvl = ?, end_time=?, CreaterName=?, demon_bongin=?");
				pstm.setInt(1, item.getId());
				pstm.setString(2, getName());
				pstm.setInt(3, item.getItemId());
				pstm.setString(4, item.getName());
				pstm.setInt(5, item.getCount());
				pstm.setInt(6, item.getEnchantLevel());
				pstm.setInt(7, item.isIdentified() ? 1 : 0);
				pstm.setInt(8, item.get_durability());
				pstm.setInt(9, item.getChargeCount());
				pstm.setInt(10, item.getRemainingTime());
				pstm.setTimestamp(11, item.getLastUsed());
				pstm.setInt(12, item.getAttrEnchantLevel());
				pstm.setTimestamp(13, item.getEndTime());
				pstm.setString(14, item.getCreaterName());
				pstm.setInt(15, item.isDemonBongin() ? 1 : 0);
				pstm.executeUpdate();
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(pstm2);
			SQLUtil.close(con);
		}
	}*/

	@Override
	public synchronized void insertItem(L1ItemInstance item) {
	    String query = "SELECT * FROM clan_warehouse WHERE id = ?";
	    String insertQuery = "INSERT INTO clan_warehouse "
	            + "SET id = ?, clan_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id= ?, durability = ?, charge_count = ?, remaining_time = ?, last_used = ?, attr_enchantlvl = ?, end_time=?, CreaterName=?, demon_bongin=?";
	    try (Connection con = L1DatabaseFactory.getInstance().getConnection();
	         PreparedStatement selectStmt = con.prepareStatement(query);
	         PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
	        selectStmt.setInt(1, item.getId());
	        ResultSet rs = selectStmt.executeQuery();
	        if (!rs.next()) {
	            insertStmt.setInt(1, item.getId());
	            insertStmt.setString(2, getName());
	            insertStmt.setInt(3, item.getItemId());
	            insertStmt.setString(4, item.getName());
	            insertStmt.setInt(5, item.getCount());
	            insertStmt.setInt(6, item.getEnchantLevel());
	            insertStmt.setInt(7, item.isIdentified() ? 1 : 0);
	            insertStmt.setInt(8, item.get_durability());
	            insertStmt.setInt(9, item.getChargeCount());
	            insertStmt.setInt(10, item.getRemainingTime());
	            insertStmt.setTimestamp(11, item.getLastUsed());
	            insertStmt.setInt(12, item.getAttrEnchantLevel());
	            insertStmt.setTimestamp(13, item.getEndTime());
	            insertStmt.setString(14, item.getCreaterName());
	            insertStmt.setInt(15, item.isDemonBongin() ? 1 : 0);
	            insertStmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
	    }
	}


	@Override
	public synchronized void updateItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE clan_warehouse SET count = ? WHERE id = ?");
			pstm.setInt(1, item.getCount());
			pstm.setInt(2, item.getId());
			pstm.executeUpdate();

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public synchronized void deleteItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM clan_warehouse WHERE id = ?");
			pstm.setInt(1, item.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		_items.remove(_items.indexOf(item));
	}

	public synchronized void deleteAllItems() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM clan_warehouse WHERE clan_name = ?");
			pstm.setString(1, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}

