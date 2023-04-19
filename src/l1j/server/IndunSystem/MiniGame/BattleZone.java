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
package l1j.server.IndunSystem.MiniGame;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.Config;
import l1j.server.server.Announcements;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.RealTimeClock;
/*import l1j.server.server.serverpackets.S_HPMeter;*/
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;

public class BattleZone implements Runnable {
	protected final Random _random = new Random();

	private static BattleZone _instance;

	int 정각2 = 3*3600;
	int 정각5 = 6*3600;
	int 정각8 = 9*3600;
	int 정각11 = 12*3600;
	int 정각14 = 15*3600;
	int 정각17 = 18*3600;
	int 정각20 = 21*3600;
	int 정각23 = 24*3600;

	//Whether to start a duel
	private boolean _DuelStart;

	public boolean getDuelStart() {
		return _DuelStart;
	}

	public void setDuelStart(boolean duel) {
		_DuelStart = duel;
	}

	//Whether or not to enter the duel
	private boolean _DuelOpen;

	public boolean getDuelOpen() {
		return _DuelOpen;
	}

	public void setDuelOpen(boolean duel) {
		_DuelOpen = duel;
	}
	//Whether to start a duel
	private boolean _progress;

	public boolean battleZoneProgression() {
		return _progress;
	}

	public void setBattleZoneProgress(boolean flag) {
		_progress = flag;
	}


	private boolean _end;

	public boolean battleZoneEnd() {
		return _end;
	}

	public void setBattleZoneExit(boolean flag) {
		_end = flag;
	}
	//public int DuelCount;//

	private int enddueltime;

	private boolean Close;

	protected ArrayList<L1PcInstance> battleZoneUser = new ArrayList<>();
	public void addBattleZoneUser(L1PcInstance pc) {
		battleZoneUser.add(pc);
	}
	public void removeBattleZoneUser(L1PcInstance pc) {
		battleZoneUser.remove(pc);
	}
	public void clearBattleZoneUser() {
		battleZoneUser.clear();
	}
	public boolean isBattleZoneUser(L1PcInstance pc) {
		return battleZoneUser.contains(pc);
	}
	public int getBattleZoneUserCount(){
		return battleZoneUser.size();
	}

	private boolean GmStart = false;
	public void setGmStart(boolean ck){	GmStart = ck; }
	public boolean getGmStart(){	return GmStart;	}

	public L1PcInstance[] toArrayBattleZoneUser() {
		return battleZoneUser.toArray(new L1PcInstance[battleZoneUser.size()]);
	}
	public static BattleZone getInstance() {
		if (_instance == null) {
			_instance = new BattleZone();
		}
		return _instance;
	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					if (!battleZoneEnd()){
						Thread.sleep(1000*60*60*2); //2 hour waiting time
						setBattleZoneExit(false);
					} else {
						checkDuelTime(); // Check available dueling time
						if (battleZoneProgression())	{
							userCheck();
						}
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void userCheck() {
		L1PcInstance[] pc = toArrayBattleZoneUser();
		for (L1PcInstance element : pc) {
			if (element == null)
				continue;

			if (element.getMapId() == 5001 || element.getMapId() == 5153) {
				continue;
			} else {
				if (isBattleZoneUser(element)) {
					removeBattleZoneUser(element);
				}
				element.set_DuelLine(0);
			}
		}
	}

	//dual time check
	public void checkDuelTime() {
		//get game time
		try{
			int servertime = RealTimeClock.getInstance().getRealTime().getSeconds();
			//current time
			int nowdueltime = servertime % 86400;
			int count1 = 0;
			int count2 = 0;
			int winLine = 4;
			if (!getDuelStart()){
				if (  	nowdueltime >= 정각2-31 && nowdueltime <= 정각2+31 // /2시
						|| nowdueltime >= 정각5-31 && nowdueltime <= 정각5+31 ///5시
						|| nowdueltime >= 정각8-31 && nowdueltime <= 정각8+31 ////8시
						|| nowdueltime >= 정각11-31 && nowdueltime <= 정각11+31 //11시
						|| nowdueltime >= 정각14-31 && nowdueltime <= 정각14+31//14시
						|| nowdueltime >= 정각17-31 && nowdueltime <= 정각17+31//17시
						|| nowdueltime >= 정각20-31 && nowdueltime <= 정각20+31//20시
						|| nowdueltime >= 정각23-31 && nowdueltime <= 정각23+31//23시
						|| getGmStart())
				{
					setDuelOpen(true);
					setDuelStart(true);
					minute3Entry();
				}
				if (battleZoneProgression())	{
					L1PcInstance[] c = toArrayBattleZoneUser();
					for (L1PcInstance element : c) {
						if(element.getMapId() == 5001){
							if(!element.isDead()){
								enterBattleZone(element);
							}
						}
					}
					setDuelStart(true);
					//end time specified
					enddueltime = nowdueltime + 600; //End after 10 minutes Where to set the end time
				}
			} else {
				//If it is an end time or a forced end
				if(nowdueltime >= enddueltime || Close){
					L1PcInstance[] c1 = toArrayBattleZoneUser();
					for (L1PcInstance element : c1) {
						if(element.getMapId() == 5153){
							if(!element.isDead()){
								if(element.get_DuelLine() == 1){
									count1 += 1;
								}else{
									count2 += 1;
								}
							}
						}
					}
					//Winner Check
					String ment = null;
					if(count1 > count2){
						//1st line winner
						winLine = 1;
						ment = "This is the victory of the premium battle zone 'Dark' line.";
						L1World.getInstance().broadcastServerMessage("\\fW* Battle Zone End! It's a win for the 'dark' line *");
					}else if(count1 < count2){
						//2nd line winner//
						winLine = 2;
						ment = "This is the victory of the premium battle zone 'Silver' line.";
						L1World.getInstance().broadcastServerMessage("\\fW* Battle Zone End! It's a victory for the 'Silver' line *");
					}else{
						winLine = 3;
						ment = "The 'Dark' and 'Silver' lines of the Premium Battle Zone have been drawn.";
						L1World.getInstance().broadcastServerMessage("\\fW* Battle Zone End! 'Dark' and 'Silver' lines are tied *");
					}

					L1PcInstance[] c2 = toArrayBattleZoneUser();
					for (L1PcInstance element : c2) {
						if(element == null) continue;
						if(element.get_DuelLine() != 0){
							element.sendPackets(new S_SystemMessage(ment)); //Edit comment
							//Items are given to the winning line
							 if(element.get_DuelLine() == winLine){
						    	 String[] itemIds = null;
							 		try{
							 			int idx = Config.BATTLE_ZONE_ITEM.indexOf(",");
							 			// , if there is
							 			if(idx > -1){
							 				itemIds = Config.BATTLE_ZONE_ITEM.split(",");
							 			}else{
							 				itemIds = new String[1];
							 				itemIds[0] = Config.BATTLE_ZONE_ITEM;
							 			}
							 		} catch (Exception e){}
							 		// number of items to pay
							 		String[] counts = null;
							 		try {
							 			int idx = Config.BATTLE_ZONE_ITEMS.indexOf(",");
							 			// , if there is
							 			if(idx > -1){
							 				counts = Config.BATTLE_ZONE_ITEMS.split(",");
							 			} else {
							 				counts = new String[1];
							 				counts[0] = Config.BATTLE_ZONE_ITEMS;
							 			}
							 		}catch(Exception e){}
							 		// If there is no item ID or count
							 		if (itemIds == null || counts == null)
							 			return;
							 		for (int j = 0; j < itemIds.length; j++) {
							 			int itemId = 0;
							 			int count = 0;
							 			itemId = Integer.parseInt(itemIds[j]);
							 			count = Integer.parseInt(counts[j]);
							 			if (itemId <= 0 || count <= 0)
							 				continue;
							 			L1ItemInstance item = element.getInventory().storeItem(itemId, count);
							 			if (item != null)
							 				element.sendPackets(new S_SystemMessage(item.getName() + " (" + count + ")을 얻었습니다."));
							 		}
							      element.sendPackets(new S_SystemMessage("\\fU* Items have been given to the winning team *"));
							     }

							deleteMiniHp(element);
							element.set_DuelLine(0);
							//If it's a battle zone
							if(element.getMapId() == 5153 || element.getMapId() == 5001){
								if(!element.isDead()){
									L1Teleport.teleport(element, 33090, 33402, (short) 4, 0, true);//
								}
							}
						}
					}
					ment = null;
					Announcements.getInstance().announceToAll("\\fW* Premium Battle Zone has ended *");
					Announcements.getInstance().announceToAll("\\fW* Battlezones open every 3 hours *");
					setBattleZoneExit(true);
					setBattleZoneProgress(false);
					setDuelStart(false);
					//	DuelCount = 0;
					Close = false;
					battleZoneUser.clear();
					setGmStart(false);
				} else {
					//If the entry is closed
					if(!getDuelOpen()){
						int count3 = 0;
						int count4 = 0;
						L1PcInstance[] c3 = toArrayBattleZoneUser();
						for (L1PcInstance element : c3) {
							if(element == null) continue;
							// If it's a battle zone
							if(element.getMapId() == 5153){
								if(!element.isDead()){ //Non-dead user check
									if(element.get_DuelLine() == 1){
										count3 += 1;
									}else if(element.get_DuelLine() == 2){
										count4 += 1;
									}else{
										removeBattleZoneUser(element);
									}
								}
							}
						}

						//Execute forced shutdown when there are 0 remaining users<//
						if(count3 == 0 || count4 == 0){
							Close = true;
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void createMiniHp(L1PcInstance pc) {
		// Display each other's HP during battle
		for (L1PcInstance member : BattleZone.getInstance().toArrayBattleZoneUser()) {
			// Show hp to the same line
			if (member != null) {
				if (pc.get_DuelLine() == member.get_DuelLine()) {
					/*member.sendPackets(new S_HPMeter(pc));
					pc.sendPackets(new S_HPMeter(member));*/
				}
			}
		}
	}

	//Battle Zone Transformation
	private void battleZoneTransformation(L1PcInstance pc) {
		if (pc == null)
			return;
		int DuelLine = pc.get_DuelLine();
		int polyid = 0;
		int time = 1800;
		if (pc != null) {
			if (pc.isKnight() || pc.isCrown() || pc.isDarkelf() || pc.isDragonknight() || pc.isWarrior() || pc.isFencer()) {
				// Knight Lord Dark Elf Dragon Knight Warrior
				if (DuelLine == 1) {
					polyid = 13152; // <<Line 1 Transformation Dark>
				} else {
					polyid = 13153; // 2nd line arc transformation
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
			// Sorcerer Illusioner
			if (pc.isWizard() || pc.isIllusionist()) {
				if (DuelLine == 1) {
					polyid = 13152;
				} else {
					polyid = 13153;
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
			// fairy
			if (pc.isElf()) {
				if (DuelLine == 1) {
					polyid = 13635;
				} else {
					polyid = 13631;
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
		}
	}

	private void enterBattleZone(L1PcInstance pc) {
		try {
			battleZoneTransformation(pc);
			createMiniHp(pc);
			if (pc.get_DuelLine() == 1) {
				int ranx = 32628 + _random.nextInt(4);
				int rany = 32896 + _random.nextInt(5);
				L1Teleport.teleport(pc, ranx, rany, (short) 5153, 1, true);
			} else {
				int ranx2 = 32650 - _random.nextInt(4);
				int rany2 = 32893 + _random.nextInt(5);
				L1Teleport.teleport(pc, ranx2, rany2, (short) 5153, 5, true);
			}

			setBattleZoneProgress(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void minute3Entry() {
		try {
			L1World.getInstance().broadcastPacketToAll(
					new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "The battle zone has been opened. The waiting time for entry is 3 minutes."), true);

			Announcements.getInstance().announceToAll("After 3 minutes, the group exhibition premium battle zone will be held.");
			Announcements.getInstance().announceToAll("Admission is available at Giran Village on a first-come, first-served basis.");
			Announcements.getInstance().announceToAll("If you lease after entering the waiting room, you will not be able to participate.");
			try {
				Thread.sleep(1000 * 120);
			} catch (Exception e) {
			}
			Announcements.getInstance().announceToAll("Entry to the Premium Battle Zone ends in 1 minute.");
			Announcements.getInstance().announceToAll("Entry is possible through the Giran 'Premium Battle Zone'.");
			try {
				Thread.sleep(1000 * 50);
			} catch (Exception e) {
			}
			Announcements.getInstance().announceToAll("10 seconds left to enter Premium Battle Zone.");
			try {
				Thread.sleep(1000 * 10);
			} catch (Exception e) {
			}
			if (getDuelOpen()) {
				setDuelOpen(false);
			}
			Announcements.getInstance().announceToAll("Entry to the Premium Battle Zone has been closed.");
			try {
				Thread.sleep(1000 * 5);
			} catch (Exception e) {
			}
			setBattleZoneProgress(true);
			setGmStart(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteMiniHp(L1PcInstance pc) {
		// At the end of the battle, the HP bar is deleted.
		for (L1PcInstance member : pc.getKnownPlayers()){
			//Show hp to the same line
			if(member != null){
				if(pc.get_DuelLine() == member.get_DuelLine()){
					/*pc.sendPackets(new S_HPMeter(member.getId(), 0xff, 0xff));
					member.sendPackets(new S_HPMeter(pc.getId(), 0xff, 0xff));*/
				}
			}
		}
	}
}
