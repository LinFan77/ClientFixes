package l1j.server.GameSystem.Lindvior;

import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Random;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseAttackSkill;

public class LindviorThread extends Thread {

	private FastTable<Lindvior> _list;
	private Random _rnd = new Random(System.nanoTime());

	private static LindviorThread _instance;

	public static LindviorThread get() {
		if (_instance == null)
			_instance = new LindviorThread();
		return _instance;
	}

	public LindviorThread() {
		super("l1j.server.GameSystem.LindviorThread");
		_list = new FastTable<>();
		start();
	}

	@Override
	public void run() {
		int size = 0;
		while (true) {
			try {
				Lindvior[] list = null;
				synchronized (_list) {
					if ((size = _list.size()) > 0) {
						list = _list.toArray(new Lindvior[size]);
					}
				}
				if (size > 0) {
					for (Lindvior lindvior : list) {
						if (lindvior == null) {
							_list.remove(lindvior);
							continue;
						}
						if (lindvior.getEndTIme() < System.currentTimeMillis())
							quit(lindvior);

						/** user map check **/
						mapUserCheck(lindvior);

						if (lindvior.Sleep > System.currentTimeMillis())
							continue;
						Sleep_Setting(lindvior);
						switch (lindvior.Step) {
						case 1: // Rare entry
							if (lindvior.Sub_Step == 1)
								SystemChat(lindvior, "Lindvior: Who disturbs my sleep?");
							else if (lindvior.Sub_Step == 2)
								SystemChat(lindvior,
										"Lindvior: Cerenis Are you trying to offend me again?");
							else if (lindvior.Sub_Step == 3)
								SystemChat(lindvior, "Lindvior: Foolish humans...");
							else if (lindvior.Sub_Step == 4)
								SystemChat(lindvior, "Lindvior: I will make you pay for offending Lindvior.");
							else if (lindvior.Sub_Step == 5)
								lindvior.dragon_lind = LindviorSpawn.getInstance()
										.fillSpawnTable(lindvior.getMap().getId(), 1); // Lind Spawn
							lindvior.Sub_Step++;
							if (lindvior.Sub_Step == 6) {
								lindvior.Step = 2;
								lindvior.Sub_Step = 0;
							}
							break;
						case 2: // Lind Naom
							if (lindvior.dragon_lind == null || lindvior.dragon_lind.isDead()
									|| lindvior.dragon_lind._destroyed) {
								lindvior.Step = 3;
								// giveItem(lind, 787878); tek123
								// SystemChat(lind, "Lindvior gave you the trail of the escaped dragon.");
							}
							break;
						case 3: // lind 2 pe air
							if (lindvior.Sub_Step == 0)
								SystemChat(lindvior,
										"Lindvior: What a laugh! I will make you deeply regret your foolishness!");
							else if (lindvior.Sub_Step == 1)
								SystemChat(lindvior,
										"Lindvior: Still, it's quite good! But how long can you last?");
							else if (lindvior.Sub_Step == 2)
								SystemChat(lindvior,
										"Lindvior: I haven't warmed up for a long time, let's play!!");
							else if (lindvior.Sub_Step == 3)
								Lind_level2_setting(lindvior);
							else if (lindvior.Sub_Step == 5)
								cloud_npcChat(lindvior,
										"Great Cloud Spirit: Who broke into Lord Lindvior's sanctuary?");
							else if (lindvior.Sub_Step == 6)
								cloud_npcChat(lindvior,
										"Great Cloud Spirit: Ooh... how dare you make Sir Lindvior feel uncomfortable!");
							else if (lindvior.Sub_Step == 7)
								cloud_npcChat(lindvior, "Great Cloud Spirit: I won't let you go!");
							else if (lindvior.Sub_Step == 8)
								cloud_npcChat(lindvior, "Great Cloud Spirit: Cloud spirits, give me strength!");

							if (lindvior.Sub_Step != 4 && lindvior.Sub_Step != 9)
								lindvior.Sub_Step++;
							if (lindvior.Sub_Step >= 4) {
								if (lindvior.dragon_lind != null
										&& (lindvior.dragon_lind.getMaxHp() / 2) > lindvior.dragon_lind
												.getCurrentHp()
										&& !((L1MonsterInstance) lindvior.dragon_lind).lind_level2_cloud) {
									((L1MonsterInstance) lindvior.dragon_lind).lind_level2_cloud = true;
									Broadcaster.broadcastPacket(lindvior.dragon_lind,
											new S_NpcChatPacket(lindvior.dragon_lind,
													"Ben Lyr! Suberuke..", 0), true);
									lindvior.cloud_list = LindviorSpawn.getInstance()
											.fillSpawnTable(lindvior.getMap().getId(), 4, true); // Great Cloud Spirit
									lindvior.Sub_Step++;
								}
								if (lindvior.dragon_lind == null
										|| lindvior.dragon_lind.isDead()
										|| lindvior.dragon_lind._destroyed) {
									Lind_level2_die(lindvior);
									lindvior.Step = 4;
									lindvior.Sub_Step = 0;
									// giveItem(lind, 787878);
									// SystemChat(lind,
									// "Lindvior gave you the trail of the escaped dragon.");
								}
								continue;
							}
							break;
						case 4: // lind 3 pe
							if (lindvior.Sub_Step == 0)
								SystemChat(lindvior,
										"Lindvior: These annoying... stupid things~~ Are you testing me now?");
							else if (lindvior.Sub_Step == 1)
								SystemChat(lindvior, "Lindvior: Now, try harder.");
							else if (lindvior.Sub_Step == 2)
								SystemChat(lindvior,
										"Lindvior: I'll blame you guys for your stupidity.");
							else if (lindvior.Sub_Step == 3)
								SystemChat(lindvior,
										"Lindvior: I won't look at you guys anymore. Let's start again!");
							else if (lindvior.Sub_Step == 4)
								lindvior.dragon_lind = LindviorSpawn.getInstance()
										.fillSpawnTable(lindvior.getMap().getId(), 3); // Lind Spawn
							else if (lindvior.Sub_Step == 5) {
								if (lindvior.dragon_lind == null
										|| lindvior.dragon_lind.isDead()
										|| lindvior.dragon_lind._destroyed) {
									lindvior.Step = 6;
									lindvior.Sub_Step = 0;
									continue;
								}
								if (((L1MonsterInstance) lindvior.dragon_lind).lind_fly) {
									lindvior.Step = 5;
									lindvior.Sub_Step = 0;
								}
								continue;
							}
							lindvior.Sub_Step++;
							break;
						case 5: // lindvior lightning
							if (lindvior.dragon_lind == null
									|| lindvior.dragon_lind.isDead()
									|| lindvior.dragon_lind._destroyed) {
								lindvior.Step = 6;
								lindvior.Sub_Step = 0;
								continue;
							}
							if (lindvior.Sub_Step == 0) {
							} else if (lindvior.Sub_Step < 11) {
								int x = 32838 + _rnd.nextInt(22);
								int y = 32866 + _rnd.nextInt(22);
								for (L1PcInstance pc : lindvior.getMember()) {
									S_UseAttackSkill uas = new S_UseAttackSkill(pc, 0, 10, x, y, 13, false);
									S_UseAttackSkill uas2 = new S_UseAttackSkill(pc, 0, 8118, x, y, 13, false);
									pc.sendPackets(uas);
									uas = null;
									pc.sendPackets(uas2);
									uas2 = null;
									S_PacketBox pb = new S_PacketBox(83, 2);
									pc.sendPackets(pb);
									pb = null;
									if (pc.getX() == x && pc.getY() == y) {
										if (!pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND) && !pc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE))
											pc.receiveDamage(lindvior.dragon_lind, 5000, true);
									}
								}
							} else if (lindvior.Sub_Step == 11) {
								if (lindvior.dragon_lind.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_FLY) {
									lindvior.dragon_lind.setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_NONE);
									Broadcaster.broadcastPacket(lindvior.dragon_lind, new S_DoActionGFX(lindvior.dragon_lind.getId(), 11), true);
									lindvior.dragon_lind.setActionStatus(11);
									Broadcaster.broadcastPacket(lindvior.dragon_lind, new S_NPCPack(lindvior.dragon_lind), true);
								}
							} else {
								continue;
							}
							lindvior.Sub_Step++;
							break;
						case 6: // lindvior death
							if (lindvior.Sub_Step == 0)
								SystemChat(lindvior,
										"Lindvior: Unbelievable!! Kwaaaah... I was so stupid for underestimating you...");
							else if (lindvior.Sub_Step == 1)
								SystemChat(lindvior,
										"Lindvior: Ah~!! My mother Shilen, hold me...");
							else if (lindvior.Sub_Step == 2) {
								// bloodstain
								health(lindvior);
								giveItem(lindvior, 5000065);
								// SystemChat(lind, "When Lindvior died, he left a mark.");
							} else if (lindvior.Sub_Step == 3)
								SystemChat(lindvior, "System: You will be teleported after 10 seconds.");
							else if (lindvior.Sub_Step == 4)
								SystemChat(lindvior, "System: You will be teleported after 5 seconds.");
							else if (lindvior.Sub_Step == 5)
								SystemChat(lindvior, "System: You will be teleported after 4 seconds.");
							else if (lindvior.Sub_Step == 6)
								SystemChat(lindvior, "System: You will be teleported after 3 seconds.");
							else if (lindvior.Sub_Step == 7)
								SystemChat(lindvior, "System: You will be teleported after 2 seconds.");
							else if (lindvior.Sub_Step == 8)
								SystemChat(lindvior, "System: You will be teleported after 1 second.");
							else {
								lindvior.Step = 7;
								lindvior.Sub_Step = 0;
								continue;
							}
							lindvior.Sub_Step++;
							break;
						case 7: // End
							home(lindvior);
							quit(lindvior);

							break;
						default:
							break;
						}
						// System.out.println("스탭 >> "+lind.Step);
					}
				}
				list = null;
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Bloodstains & Items **/
	private void health(Lindvior lindvior) {
		// L1SkillUse l1skilluse;
		S_PacketBox pb = new S_PacketBox(S_PacketBox.DRAGONBLOOD, 88, 4320);
		for (L1PcInstance pc : lindvior.getMember()) {
			pc.sendPackets(new S_SkillSound(pc.getId(), 7783), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7783),
					true);
			pc.getSkillEffectTimerSet().setSkillEffect(
					L1SkillId.DRAGONRAID_BUFF, (86400 * 2) * 1000);
			Timestamp deleteTime = new Timestamp(System.currentTimeMillis()
					+ (86400000 * Config.RAIDTIME)); // 7 days
			pc.getNetConnection().getAccount().setDragonRaid(deleteTime);
			pc.getNetConnection().getAccount().updateDragonRaidBuff();
			pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONRAIDBUFF, 86400 * 2),
					true);
			// pc.sendPackets(new
			// S_SystemMessage("드래곤 레이드 마법으로 인해 "+C_SelectCharacter.ss.format(pc.getNetConnection().getAccount().getDragonRaid())+" 이후에 드래곤 포탈 입장이 가능합니다."),
			// true);

			/*
			 * l1skilluse = new L1SkillUse(); l1skilluse.handleCommands(pc,
			 * L1SkillId.DRAGONBLOOD_L, pc.getId(), pc.getX(), pc.getY(), null,
			 * 0, L1SkillUse.TYPE_GMBUFF); l1skilluse = null;
			 * pc.sendPackets(pb); Timestamp deleteTime = null; deleteTime = new
			 * Timestamp(System.currentTimeMillis() + 259200000);// 3일
			 * pc.setlindTime(deleteTime);
			 */
		}
		pb = null;
		L1World.getInstance().broadcastServerMessage("Dwarf's Cry: The warriors who broke Lindvior's wings were born!!");
		/*
		 * if(GameList.get용땅() == false){
		 * L1World.getInstance().broadcastServerMessage
		 * ("난쟁이의 외침 : 웰던 마을에 숨겨진 용들의 땅으로 가는 문이 열렸습니다."); L1SpawnUtil.spawn2(
		 * 33726, 32506, (short)4 , 4212013, 0, 1000*60*60*12 , 0);
		 * GameList.set용땅(true); }else{
		 * L1World.getInstance().broadcastServerMessage
		 * ("난쟁이의 외침 : 숨겨진 용들의 땅으로 가는 문이 이미 웰던 마을에 열려 있습니다."); }
		 */
	}

	private void giveItem(Lindvior lindvior, int id) {
		for (L1PcInstance pc : lindvior.getMember()) {
			if (pc == null)
				continue;
			createNewItem(lindvior, pc, id, 1);
		}
	}

	private boolean createNewItem(Lindvior lindvior, L1PcInstance pc, int item_id,
			int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // When we cannot have, we do not cancel processing to drop in the ground (prevention of fraud)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			for (L1PcInstance temp : lindvior.getMember()) {
				temp.sendPackets(new S_ServerMessage(813, "Lindvior", item.getName(), temp.getName()));
			}
			// S_ServerMessage sm = new S_ServerMessage(403, item.getLogName());
			// pc.sendPackets(sm); // %0를 손에 넣었습니다.
			// sm = null;
			return true;
		} else {
			return false;
		}
	}

	private void home(Lindvior lindvior) {
		for (L1PcInstance pc : lindvior.getMember()) {
			if (pc == null)
				continue;
			pc.dx = 33718;
			pc.dy = 32506;
			pc.dm = (short) 4;
			pc.dh = 4;
			pc.setTelType(7);
			S_SabuTell st = new S_SabuTell(pc);
			pc.sendPackets(st);
			st = null;
		}
	}

	/** Lindvior map exit handling **/
	public void quit(Lindvior lindvior) {
		for (L1FieldObjectInstance npc : L1World.getInstance().getAllField()) {
			if (npc.moveMapId == lindvior.getMap().getId()) {
				npc.deleteMe();
			}
		}
		remove(lindvior);
		LindviorRaid.get().quit(lindvior.getMap());
		Object_Delete(lindvior);
		lindvior.clear();
	}

	private void Object_Delete(Lindvior p) {
		for (L1Object ob : L1World.getInstance()
				.getVisibleObjects(p.getMap().getId()).values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1ItemInstance) {
				L1ItemInstance obj = (L1ItemInstance) ob;
				L1Inventory groundInventory = L1World.getInstance()
						.getInventory(obj.getX(), obj.getY(), obj.getMapId());
				groundInventory.removeItem(obj);
			} else if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				npc.deleteMe();
			}
		}
	}

	public void remove(Lindvior lindvior) {
		_list.remove(lindvior);
	}

	public void add(Lindvior lindvior) {
		_list.add(lindvior);
	}

	public Lindvior getLind(int mapid) {
		for (Lindvior lindvior : _list) {
			if (lindvior == null)
				continue;
			if (lindvior.getMap().getId() == mapid)
				return lindvior;
		}
		return null;
	}

	private void cloud_npcChat(Lindvior lindvior, String chat) {
		if (lindvior.cloud_list == null || lindvior.cloud_list.size() == 0)
			return;
		for (FastMap.Entry<String, L1NpcInstance> e = lindvior.cloud_list.head(), mapEnd = lindvior.cloud_list
				.tail(); (e = e.getNext()) != mapEnd;) {
			L1NpcInstance npc = e.getValue();
			if (npc == null || npc._destroyed || npc.isDead())
				continue;
			Broadcaster.broadcastPacket(npc, new S_NpcChatPacket(npc, chat, 0),
					true);
		}
	}

	/** Excluded from the list if the map user check returned or moved to the map **/
	public void mapUserCheck(Lindvior lindvior) {
		boolean ck = false;
		boolean ck2 = false;
		for (L1PcInstance pc : lindvior.getMember()) {
			if (pc == null || pc.getMapId() != lindvior.getMap().getId()
					|| pc.getNetConnection() == null) {
				ck2 = true;
			} else if ((pc.getX() >= 32825 && pc.getX() <= 32867)
					&& (pc.getY() >= 32857 && pc.getY() <= 32899)) {
				ck = true;
				lindvior.mapckCount = 0;
				// System.out.println("user in");
			}
		}
		if ((lindvior.Step > 0 && lindvior.Step < 6) && !ck) {
			if (lindvior.mapckCount++ > 3) {
				Collection<L1Object> list = L1World.getInstance()
						.getVisibleObjects(lindvior.getMap().getId()).values();
				for (L1Object obj : list) {
					if (obj == null || !(obj instanceof L1MonsterInstance))
						continue;
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					mon.deleteMe();
				}
				lindvior.Step = 0;
				lindvior.mapckCount = 0;
				lindvior.Sleep = System.currentTimeMillis();
				// System.out.println("Reset because there is no cancer");
			}
		}
		if (ck2) {
			if (lindvior.MembermapckCount++ > 3) {
				for (L1PcInstance pc : lindvior.getMember()) {
					if (pc == null || pc.getMapId() != lindvior.getMap().getId()
							|| pc.getNetConnection() == null)
						lindvior.removeMember(pc);
				}
				lindvior.MembermapckCount = 0;
			}
		}

	}

	private void Sleep_Setting(Lindvior lindvior) {
		long time = 0;
		switch (lindvior.Step) {
		case 1:
			if (lindvior.Sub_Step == 0)
				time = 90000;
			else if (lindvior.Sub_Step == 4)
				time = 5000;
			else
				time = 2000;
			break;
		case 3:
			if (lindvior.Sub_Step >= 0 && lindvior.Sub_Step <= 2)
				time = 5000;
			else if (lindvior.Sub_Step >= 5 && lindvior.Sub_Step <= 7)
				time = 2000;
			break;
		case 4:
			if (lindvior.Sub_Step >= 0 && lindvior.Sub_Step <= 3)
				time = 5000;
			break;
		case 5:
			if (lindvior.Sub_Step >= 0 && lindvior.Sub_Step <= 10)
				time = 2000;
			break;
		case 6:
			if (lindvior.Sub_Step >= 0 && lindvior.Sub_Step <= 1)
				time = 5000;
			else if (lindvior.Sub_Step == 2)
				time = 10000;
			else if (lindvior.Sub_Step == 3)
				time = 5000;
			break;
		default:
			break;
		}
		lindvior.Sleep = System.currentTimeMillis() + time;
	}

	/*
	 * private void ServerChat(Lind p, int msg){ S_ServerMessage sm = new
	 * S_ServerMessage(msg); for(L1PcInstance pc : p.getMember()){ if(pc !=
	 * null){ pc.sendPackets(sm); } } sm = null; }
	 */

	private void SystemChat(Lindvior p, String msg) {
		S_SystemMessage sm = new S_SystemMessage(msg);
		for (L1PcInstance pc : p.getMember()) {
			if (pc != null) {
				pc.sendPackets(sm);
			}
		}
		sm = null;
	}

	private void Lind_level2_setting(Lindvior lindvior) {
		lindvior.lind_level2 = LindviorSpawn.getInstance().fillSpawnTable(
				lindvior.getMap().getId(), 2, true); // 기본 NPC 스폰
		int c = _rnd.nextInt(lindvior.lind_level2.size());
		int c2 = 0;
		for (FastMap.Entry<String, L1NpcInstance> e = lindvior.lind_level2.head(), mapEnd = lindvior.lind_level2
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (c2 == c) {
				lindvior.dragon_lind = e.getValue();
			}
			c2++;
		}
	}

	private void Lind_level2_die(Lindvior lindvior) {
		for (FastMap.Entry<String, L1NpcInstance> e = lindvior.lind_level2.head(), mapEnd = lindvior.lind_level2
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (e.getValue() == null || e.getValue().isDead()
					|| e.getValue()._destroyed)
				continue;
			e.getValue().deleteMe();
		}
	}
}

