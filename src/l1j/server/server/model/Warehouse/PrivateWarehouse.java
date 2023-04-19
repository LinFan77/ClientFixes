package l1j.server.server.model.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.SQLUtil;

public class PrivateWarehouse extends Warehouse {
	private static final long serialVersionUID = 1L;
	protected static Logger _log = Logger.getLogger(PrivateWarehouse.class.getName());

	public PrivateWarehouse(String an) {
		super(an);
	}

	@Override
	protected int getMax() {
		return Config.MAX_PERSONAL_WAREHOUSE_ITEM;
	}

	@Override
	public synchronized void loadItems() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_warehouse WHERE account_name = ?");
			pstm.setString(1, getName());
			rs = pstm.executeQuery();
			L1ItemInstance item = null;
			L1Item itemTemplate = null;
			while (rs.next()) {
				item = new L1ItemInstance();
				int objectId = rs.getInt("id");
				item.setId(objectId);
				itemTemplate = ItemTable.getInstance().getTemplate(rs.getInt("item_id"));
				item.setItem(itemTemplate);
				item.setCount(rs.getInt("count"));
				item.setEquipped(false);
				item.setEnchantLevel(rs.getInt("enchantlvl"));
				item.setIdentified(rs.getInt("is_id") != 0 ? true : false);
				item.set_durability(rs.getInt("durability"));
				item.setChargeCount(rs.getInt("charge_count"));
				item.setRemainingTime(rs.getInt("remaining_time"));
				item.setLastUsed(rs.getTimestamp("last_used"));
				item.setAttrEnchantLevel(rs.getInt("attr_enchantlvl"));
				item.setSpecialEnchant(rs.getInt("special_enchant"));
				item.setBless(rs.getInt("bless"));

				if (item.getItemId() == L1ItemId.ADENA) {
					L1ItemInstance itemExist = findItemId(item.getItemId());
					if (itemExist != null) {
						deleteItem(item);
						int newCount = itemExist.getCount() + item.getCount();
						if (newCount <= L1Inventory.MAX_AMOUNT) {
							if (newCount < 0) {
								newCount = 0;
							}
							itemExist.setCount(newCount);
							updateItem(itemExist);
						}
					} else {
						_items.add(item);
						L1World.getInstance().storeObject(item);
					}
				} else {
					_items.add(item);
					L1World.getInstance().storeObject(item);
				}
			}

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public synchronized void insertItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO character_warehouse SET id = ?, account_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, remaining_time = ?, last_used = ?, attr_enchantlvl = ?, bless = ?, special_enchant = ?, package=?");
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
			pstm.setInt(13, item.getBless());
			//pstm.setInt(14, item.getSpecialEnchant());
			pstm.setInt(15, !item.isPackage() ? 0 : 1);
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public synchronized void updateItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE character_warehouse SET count = ? WHERE id = ?");
			pstm.setInt(1, item.getCount());
			pstm.setInt(2, item.getId());
			pstm.execute();
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
			pstm = con.prepareStatement("DELETE FROM character_warehouse WHERE id = ?");
			pstm.setInt(1, item.getId());
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		_items.remove(_items.indexOf(item));
	}

	public synchronized void deleteItem2(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_warehouse WHERE id = ?");
			pstm.setInt(1, item.getId());
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		int index = 0;
		boolean ch = false;
		for (L1ItemInstance itemtemp : _items) {
			if (itemtemp.getId() == item.getId()) {
				index = _items.indexOf(itemtemp);
				ch = true;
				break;
			}
		}
		if (ch) {
			_items.remove(index);
		}
	}
}
