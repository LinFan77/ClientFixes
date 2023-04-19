package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.Dungeon.DungeonSystem;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.CharBuffTable;
import l1j.server.server.datatables.MonsterBookTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Trade;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FollowerInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import manager.LinAllManager;
import server.LoginController;

public class quit_Q implements Runnable {
	private long time = 0;
	private final Queue<L1PcInstance> _queue;

	public quit_Q() {
		_queue = new ConcurrentLinkedQueue<>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(L1PcInstance name) {
		_queue.offer(name);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10L);
				synchronized (this){
					L1PcInstance pc = _queue.peek();
					if (_queue.size() > 100) {
						if (System.currentTimeMillis() > time) {
							System.out.println("More than 100 exit queues: " + _queue.size());
							time = System.currentTimeMillis() + (1000 * 10);
						}
					}

					if (pc == null) {
						continue;
					}

					if (pc.isPrivateShop()) {
						pc.deleteShopItem(pc.getId());
					}

					//LinAllManager.getInstance().LogLogOutAppend(pc.getName(), pc.getNetConnection().getHostname());

					try {
						if (Config.connectionChatMonitor() > 0) {
							for (L1PcInstance gm : Config.toArrayConnectionChatMonitor()) {
								if (gm.getNetConnection() == null) {
									Config.removeConnection(gm);
									continue;
								}

								if (gm == pc) {
									continue;
								}
								String parameters[] = {pc.getName(), pc.getAccountName()};
								gm.sendPackets(new S_SystemMessage(pc.getLanguage(), "\\fY[%1] (end) / Account:%2", parameters));
							}
						}

						if (pc.isGm()) Config.removeAll(pc);

						pc.set_delete(true); // air bug fix
						pc.setadFeature(1);
						pc.setDeathMatch(false);
						pc.setHaunted(false);
						pc.setPetRacing(false);

						// If it dies, return it to the city and put it in an empty state.
						if (pc.isDead()) {
							int[] loc = Getback.GetBack_Location(pc, true);
							pc.setX(loc[0]);
							pc.setY(loc[1]);
							pc.setMap((short) loc[2]);
							pc.setCurrentHp(pc.getLevel());
							pc.set_food(39); // 10%
							loc = null;
						}

						// When ending near your castle, set the position with resistance
						if (pc.getClan() != null && pc.getClan().getCastleId() > 0) {
							if (L1CastleLocation.checkInWarArea(pc.getClan().getCastleId(), pc)) {
								int[] loc = L1CastleLocation.getCastleLoc(pc.getClan().getCastleId());
								pc.setX(loc[0]);
								pc.setY(loc[1]);
								pc.setMap((short) loc[2]);
								loc = null;
							}
						}

						if (pc.isGhost()) {
							pc.setX(pc._ghostSaveLocX);
							pc.setY(pc._ghostSaveLocY);
							pc.setMap(pc._ghostSaveMapId);
							pc.getMoveState().setHeading(pc._ghostSaveHeading);
						}

						// If the clan warehouse is in use or terminated during use, the clan warehouse is canceled (Kuu)
						ClanWarehouse clanWarehouse = null;
						L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
						if (clan != null) {
							clanWarehouse = WarehouseManager.getInstance().getPledgeStorage(clan.getClanName());
							clan.removeOnlineClanMember(pc.getName());
						}

						if (clanWarehouse != null) {
							clanWarehouse.unlock(pc.getId());
						}

						// stop trading
						if (pc.getTradeID() != 0) { // in trade
							L1Trade trade = new L1Trade();
							trade.TradeCancel(pc);
						}

						// in a duel
						if (pc.getFightId() != 0) {
							pc.setFightId(0);
							L1PcInstance fightPc = (L1PcInstance) L1World.getInstance().findObject(pc.getFightId());
							if (fightPc != null) {
								fightPc.setFightId(0);
								fightPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, 0, 0));
							}
						}

						if (DeathMatch.getInstance().isEnterMember(pc)) {
							DeathMatch.getInstance().removeEnterMember(pc);
						}

						if (GhostHouse.getInstance().isEnterMember(pc)) {
							GhostHouse.getInstance().removeEnterMember(pc);
						}

						if (PetRacing.getInstance().isEnterMember(pc)) {
							PetRacing.getInstance().removeEnterMember(pc);
						}

						/** Check if the pet is being summoned, and if cleanup pet is dead, leave it alone */
						if (pc.getPetList() != null && pc.getPetListSize() > 0) {
							for (Object petObject : pc.getPetList()) {
								if (petObject == null) continue;
								if (petObject instanceof L1PetInstance) {
									L1PetInstance pet = (L1PetInstance) petObject;
									PetTable.UpDatePet(pet);
									pc.removePet(pet);
									if(!pet.isDead()) pet.deletePet();
								} else if (petObject instanceof L1SummonInstance) {
									L1SummonInstance sunm = (L1SummonInstance) petObject;
									sunm.dropItem();
									pc.removePet(sunm);
									sunm.deleteMe();
								}
							}
						}

						// Erase magic doll from world map
						if (pc.getDollList() != null && pc.getDollListSize() > 0) {
							for (L1DollInstance doll : pc.getDollList()) {
								if (doll != null) {
									doll.deleteDoll();
								}
							}
						}

						if (pc.getFollowerList() != null && pc.getFollowerList().size() > 0) {
							L1FollowerInstance follower = null;
							for (Object followerObject : pc.getFollowerList().values()) {
								if (followerObject == null)continue;
								follower = (L1FollowerInstance) followerObject;
								follower.setParalyzed(true);
								follower.spawn(follower.getNpcTemplate().get_npcId(), follower.getX(), follower.getY(), follower.getMoveState().getHeading(), follower.getMapId());
								follower.deleteMe();
							}
						}

						// Deleting buff list DB while applying character
						CharBuffTable.DeleteBuff(pc);
						// Saves currently applied buff list to DB when logout
						CharBuffTable.SaveBuff(pc);
						MonsterBookTable.getInstace().saveMonsterBookList(pc.getId());
						pc.getSkillEffectTimerSet().clearRemoveSkillEffectTimer();

						for (L1ItemInstance item : pc.getInventory().getItems()) {
							if (item != null && item.getCount() <= 0) {
								pc.getInventory().deleteItem(item);
							}
						}

						pc.setLogOutTime();
						pc.setOnlineStatus(0);

						/** Save char info **/
						pc.save();
						/** Save char inv **/
						pc.saveInventory();

						/** If Character DB Connection is not play */
						if(pc.getNetConnection() != null){
							/** Check if there is dung information and delete indung */
							DungeonSystem.isDungeonInfoPcCheck(pc);
							/** If you leased from an interserver, set it to exit from the interserver */
							if(!pc.getNetConnection().getInterServer()){
								if (pc.isInParty()) {
									pc.getParty().leaveMember(pc);
								}

								if (pc.isInChatParty()) {
									pc.getChatParty().leaveMember(pc);
								}

								/** Save the last logout time with logout if only the socket is alive. */
								LoginController.getInstance().logout(pc.getNetConnection());
							}

							/** Stored separately so that DB data information can be saved.
							 * Stored separately because sockets must be open to be saved. */
							pc.getNetConnection().getAccount().updateAttendanceTime();
							pc.getNetConnection().getAccount().updateDGTime();
							pc.getNetConnection().getAccount().updateTam();
							pc.getNetConnection().getAccount().updateNcoin();

							/** socket close check  */
							pc.setNetConnection(null);
						}

						/** delete object */
						pc.logout();
					} catch (Exception e) {
						e.printStackTrace();
					}
					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
