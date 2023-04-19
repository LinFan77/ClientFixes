package l1j.server.GameSystem.IceQeen;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.map.L1V1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_Door;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class IceQeen {

	public L1PcInstance _pc;
	private int _mapnum;

	private int guardSpear = 90023;
	private int guardBow = 90022;
	//private int handMaid = 90026;
	private int iceMan = 90021;
	private int iceGolem = 90024;
	private int yeti = 90027;
	private int scorpion = 90025;

	// private int MonsterCount = 200;
	private Random random = new Random(System.nanoTime());
	private ArrayList<L1DoorInstance> DoorList = new ArrayList<>();
	private ArrayList<L1NpcInstance> MonList = new ArrayList<>();
	private MonsterCheckThread mct;

	public IceQeen() {
	}

	public int Start(L1PcInstance pc, int type) {
		synchronized (GameList.IQList) {
			try {
				int mapid = GameList.getIceQueenMapId();

				if (mapid == 0) {
					S_SystemMessage sm = new S_SystemMessage("All maps are in use. Please try again later.");
					pc.sendPackets(sm, true);
					return 0;
				}

				boolean ok = GameList.addIceQeen(mapid, this);

				if (!ok) {
					S_SystemMessage sm = new S_SystemMessage("Map settings are incorrect. Please apply again.");
					pc.sendPackets(sm, true);
					return 0;
				}

				if (!World.get_map(mapid)) {
					L1WorldMap.getInstance().cloneMap(2101, mapid);
				}

				_pc = pc;
				_mapnum = mapid;

				GeneralThreadPool.getInstance().execute(new start_spawn(mapid, type));

				return mapid;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	class start_spawn implements Runnable {

		private int mapid;
		private int type;

		public start_spawn(int m, int t) {
			mapid = m;
			type = t;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				type = random.nextInt(3);

				IceQeenSpawn.getInstance().Spawn(mapid, 0);
				IceQeenSpawn.getInstance().Spawn(mapid, random.nextInt(3)+1);

				int idlist[] = new int[6];
					idlist[0] = iceGolem;
					idlist[1] = guardSpear;
					idlist[2] = iceMan;
					idlist[3] = yeti;
					idlist[4] = scorpion;
					idlist[5] = guardBow;
				int ranid1 = 0;
				int ranid = 0;
				int ranx = 0;
				int rany = 0;
				for (int i = 0; i < 16; i++) {
					try {
						ranid1 = random.nextInt(4);
						ranid = random.nextInt(6);
						ranx = random.nextInt(10);
						rany = random.nextInt(10);
						// 1st room spawn
						L1SpawnUtil.spawn2(32760 + ranx, 32813 + rany, (short) mapid, idlist[ranid1], 5, 0, 0);
						// 2nd room spawn
						L1SpawnUtil.spawn2(32832 + ranx, 32801 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
						// Room 3 spawn
						L1SpawnUtil.spawn2(32843 + ranx, 32847 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
						// Room 4 spawn
						L1SpawnUtil.spawn2(32763 + ranx, 32882 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
						// Room 5 spawn
						L1SpawnUtil.spawn2(32820 + ranx, 32916 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
					} catch (Exception e) {
						// Seeing that there is a null value error here, it seems that there is no NPC. Please check it.
						e.printStackTrace();
					}
				}
				idlist = null;
				mct = new MonsterCheckThread();
				GeneralThreadPool.getInstance().schedule(mct, 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private IceQueenTimeCheck iqt = null;

	class IceQueenTimeCheck implements Runnable {

		private long time = 0;

		public IceQueenTimeCheck() {
			time = System.currentTimeMillis() + 60000 * 10;
		}

		@Override
		public void run() {
			try {
				if (_pc == null
						|| L1World.getInstance().getPlayer(_pc.getName()) == null || (_pc.getMapId() != _mapnum)) {
					iqt = null;
					return;
				}
				if (System.currentTimeMillis() >= time) {
					_pc.dx = 34058;
					_pc.dy = 32281;
					_pc.dm = (short) 4;
					_pc.dh = 5;
					_pc.setTelType(7);
					_pc.sendPackets(new S_SabuTell(_pc), true);
					iqt = null;
				} else
					GeneralThreadPool.getInstance().schedule(this, 3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class MonsterCheckThread implements Runnable {

		private boolean on = true;
		private boolean door1 = false, door2 = false, door3 = false,
				door4 = false, door5 = false;

		@Override
		public void run() {
			if (!on)
				return;
			try {
				boolean ck1 = false, ck2 = false, ck3 = false, ck4 = false, ck5 = false;

				for (L1Object obj : L1World.getInstance().getVisibleObjects(_mapnum).values()) {
					if (obj != null && obj instanceof L1MonsterInstance) {
						L1MonsterInstance mon = (L1MonsterInstance) obj;
						if (mon._destroyed || mon.isDead())
							continue;
						if (obj.getX() >= 32720 && obj.getX() <= 32784
								&& obj.getY() >= 32792 && obj.getY() <= 32847) {
							ck1 = true;
						} else if (obj.getX() >= 32787 && obj.getX() <= 32851
								&& obj.getY() >= 32788 && obj.getY() <= 32827) {
							ck2 = true;
						} else if ((obj.getX() >= 32853 && obj.getX() <= 32871
								&& obj.getY() >= 32798 && obj.getY() <= 32835)
								|| (obj.getX() >= 32824 && obj.getX() <= 32870
										&& obj.getY() >= 32834 && obj.getY() <= 32873)) {
							ck3 = true;
						} else if ((obj.getX() >= 32788 && obj.getX() <= 32822
								&& obj.getY() >= 32846 && obj.getY() <= 32894)
								|| (obj.getX() >= 32747 && obj.getX() <= 32790
										&& obj.getY() >= 32865 && obj.getY() <= 32915)) {
							ck4 = true;
						} else if (obj.getX() >= 32747 && obj.getX() <= 32790
								&& obj.getY() >= 32865 && obj.getY() <= 32915) {
							ck4 = true;
						} else if (obj.getX() >= 32750 && obj.getX() <= 32866
								&& obj.getY() >= 32898 && obj.getY() <= 32944) {
							ck5 = true;
						}
					}
				}
				if (!door1 && !ck1) {
					door1 = true;
					DoorOpen(4040000);
				} else if (!door2 && !ck2) {
					door2 = true;
					DoorOpen(4040001);
				} else if (!door3 && !ck3) {
					door3 = true;
					DoorOpen(4040002);
				} else if (!door4 && !ck4) {
					door4 = true;
					DoorOpen(4040003);
				} else if (!door5 && !ck5) {
					door5 = true;
					DoorOpen(4040004);
				}
				if (door5)
					return;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (!door5)
					GeneralThreadPool.getInstance().schedule(this, 3000);
			}
		}

		public void off() {
			on = false;
		}
	}

	private void doorPacket(L1DoorInstance door) {
		S_Door packet = new S_Door(door);
		_pc.sendPackets(packet);
	}

	private void DoorOpen(int id) {
		try {
			for (L1DoorInstance door : DoorList) {
				if (door.getNpcId() == id) {
					door.isPassibleDoor(true);
					door.setPassable(0);
					if (_pc != null) {
						doorPacket(door);
					}
					door.deleteMe();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AddDoor(L1DoorInstance door) {
		DoorList.add(door);
	}

	public void AddMon(L1NpcInstance npc) {
		MonList.add(npc);
	}

	public int MonSize() {
		return MonList.size();
	}

	public void Reset() {
		try {
		//	System.out.println("Deleted single player dungeon map: " + _mapnum);
			if (mct != null)
				mct.off();
			mct = null;
			for (L1NpcInstance mon : MonList) {
				if (mon == null || mon._destroyed || mon.isDead()) {
					continue;
				}
				mon.deleteMe();
			}
			Object_Delete();
			if (MonList.size() > 0)
				MonList.clear();

			L1V1Map m = (L1V1Map) L1WorldMap.getInstance().getMap((short) _mapnum);
			m.reset((L1V1Map) L1WorldMap.getInstance().getMap((short) 2101));
			// World.resetMap(2101, _mapnum);

			GameList.removeIceQeen(_mapnum);

			_pc = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Object_Delete() {

		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum).values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance || ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				if (npc._destroyed || npc.isDead())
					continue;
				npc.deleteMe();
			}
		}
		for (L1ItemInstance obj : L1World.getInstance().getAllItem()) {
			if (obj.getMapId() != _mapnum)
				continue;
			L1Inventory groundInventory = L1World.getInstance().getInventory(obj.getX(), obj.getY(), obj.getMapId());
			groundInventory.removeItem(obj);
		}
	}
}
