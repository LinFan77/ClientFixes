package l1j.server.GameSystem.Lastabard;

import java.util.Random;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Spawn;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.templates.L1Npc;

public class LastabardSpawn extends L1Spawn {
	// private static Logger _log = Logger.getLogger(L1Spawn.class.getName());
	private final L1Npc template;
	private int id; // just to find this in the spawn table
	private String name;
	private String location;
	private int maximumCount;
	private int npcid;
	private int groupId;
	private int locx;
	private int locy;
	private int randomx;
	private int randomy;
	private int locx1;
	private int locy1;
	private int locx2;
	private int locy2;
	private int heading;
	private int minRespawnDelay;
	private int maxRespawnDelay;
	private int movementDistance;
	private int delayInterval;
	private short mapid;
	private boolean rest;
	private boolean spawnUsingThread;
	private boolean respawnScreen;
	private int doorId;
	private int countMapId;

	private static Random _random = new Random(System.nanoTime());

	public LastabardSpawn(L1Npc mobTemplate) throws SecurityException,
			ClassNotFoundException {
		super(mobTemplate);
		template = mobTemplate;
	}

	private int calcRespawnDelay() {
		int respawnDelay = minRespawnDelay * 1000;

		if (delayInterval > 0) {
			respawnDelay += _random.nextInt(delayInterval) * 1000;
		}

		return respawnDelay;
	}

	@Override
	public void executeSpawnTask(int spawnNumber, int objectId) {
		SpawnTask task = new SpawnTask(spawnNumber, objectId);
		GeneralThreadPool.getInstance().schedule(task, calcRespawnDelay());
	}

	public void init(boolean wannaUsingThread) {
		delayInterval = maxRespawnDelay - minRespawnDelay;
		setSpawnUsingThread(wannaUsingThread);
		int spawnNum = 0;
		L1MonsterInstance mon = null;
		for (L1Object object : L1World.getInstance().getObject()) {
			if (object instanceof L1MonsterInstance) {
				mon = (L1MonsterInstance) object;

				if (mon.getNpcTemplate().get_npcId() == getNpcId()
						&& mon.getMapId() == getMapId()) {
					spawnNum++;
				}
			}
		}

		while (spawnNum < maximumCount) {
			doSpawn(++spawnNum);
		}
	}

	private class SpawnTask implements Runnable {
		private int spawnNumber;
		private int objectId;

		private SpawnTask(final int SPAWN_NUMBER, final int OBJECT_ID) {
			spawnNumber = SPAWN_NUMBER;
			objectId = OBJECT_ID;
		}

		@Override
		public void run() {
			try {
				doSpawn(spawnNumber, objectId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doSpawn(int spawnNumber) {
		doSpawn(spawnNumber, 0);
	}

	@Override
	protected void doSpawn(int spawnNumber, int objectId) {
		L1MonsterInstance mob = new L1MonsterInstance(template);

		try {
			if (objectId == 0)
				mob.setId(ObjectIdFactory.getInstance().nextId());
			else
				mob.setId(objectId);

			int heading = getHeading();
			if (0 > heading || heading > 7)
				heading = 5;
			mob.getMoveState().setHeading(heading);
			mob.setMap(getMapId());
			mob.setMovementDistance(getMovementDistance());
			mob.setRest(isRest());

			int newlocx, newlocy, tryCount = 0, rangeX, rangeY;
			while (tryCount <= 50) {
				if (isAreaSpawn()) {
					rangeX = getLocX2() - getLocX1();
					rangeY = getLocY2() - getLocY1();

					newlocx = _random.nextInt(rangeX) + getLocX1();
					newlocy = _random.nextInt(rangeY) + getLocY1();

					if (tryCount > 49) {
						newlocx = getLocX();
						newlocy = getLocY();
					}
				} else if (isRandomSpawn()) {
					newlocx = (getLocX() + ((int) (Math.random() * getRandomx()) - (int) (Math
							.random() * getRandomx())));
					newlocy = (getLocY() + ((int) (Math.random() * getRandomy()) - (int) (Math
							.random() * getRandomy())));
				} else {
					newlocx = getLocX();
					newlocy = getLocY();
				}

				mob.setX(newlocx);
				mob.setY(newlocy);
				mob.setHomeX(newlocx);
				mob.setHomeY(newlocy);

				if (mob.getMap().isInMap(mob.getLocation())
						&& mob.getMap().isPassable(mob.getLocation())) {
					if (L1World.getInstance().getVisiblePlayer(mob).size() == 0
							|| isRespawnScreen())
						break;

					SpawnTask task = new SpawnTask(spawnNumber, mob.getId());
					GeneralThreadPool.getInstance().schedule(task, 3000L);
					return;
				}
				tryCount++;
			}
			mob.setSpawn(this);
			mob.setRespawn(isSpawnUsingThread());
			mob.setSpawnNumber(spawnNumber);

			L1World.getInstance().storeObject(mob);
			L1World.getInstance().addVisibleObject(mob);

			/*
			 * if(LastabardData.isFourthFloor(getMapId()))
			 * LastabardController.getInstance().alive(getMapId(),
			 * LastabardData.getPos(getMapId(), getLocX(), getLocY()));
			 */
			if (mob.getHiddenStatus() == 0)
				mob.onNpcAI();

			if (getGroupId() != 0) {
				L1MobGroupSpawn.getInstance().doSpawn(mob, getGroupId(),
						isRespawnScreen(), true);
			}
			mob.setDeath(new LastabardDead(mob, getLocX(), getLocY(),
					getDoorId(), getCountMapId()));
			if (getDoorId() != 0) {
				L1DoorInstance door = DoorSpawnTable.getInstance().getDoor(
						getDoorId());
				if (door != null) {
					door.setDead(false); // 있으나 마나?
					door.close();
				}
			}
			mob.getLight().turnOnOffLight();
			mob.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("[LastabardSpawn - Exception] NpcID: "+
			// mob.getNpcId() + " MapId: " + mob.getMapId());
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	private boolean isAreaSpawn() {
		return getLocX1() != 0 && getLocY1() != 0 && getLocX2() != 0
				&& getLocY2() != 0;
	}

	private boolean isRandomSpawn() {
		return getRandomx() != 0 || getRandomy() != 0;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int i) {
		id = i;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String n) {
		name = n;
	}

	@Override
	public int getNpcId() {
		return npcid;
	}

	@Override
	public void setNpcid(int id) {
		npcid = id;
	}

	@Override
	public short getMapId() {
		return mapid;
	}

	@Override
	public void setMapId(short id) {
		mapid = id;
	}

	@Override
	public int getGroupId() {
		return groupId;
	}

	@Override
	public void setGroupId(int i) {
		groupId = i;
	}

	@Override
	public int getLocX() {
		return locx;
	}

	@Override
	public int getLocY() {
		return locy;
	}

	@Override
	public int getLocX1() {
		return locx1;
	}

	@Override
	public int getLocY1() {
		return locy1;
	}

	@Override
	public int getLocX2() {
		return locx2;
	}

	@Override
	public int getLocY2() {
		return locy2;
	}

	@Override
	public void setLocX(int x) {
		locx = x;
	}

	@Override
	public void setLocY(int y) {
		locy = y;
	}

	@Override
	public void setLocX1(int x) {
		locx1 = x;
	}

	@Override
	public void setLocY1(int y) {
		locy1 = y;
	}

	@Override
	public void setLocX2(int x) {
		locx2 = x;
	}

	@Override
	public void setLocY2(int y) {
		locy2 = y;
	}

	@Override
	public int getHeading() {
		return heading;
	}

	@Override
	public int getRandomx() {
		return randomx;
	}

	@Override
	public int getRandomy() {
		return randomy;
	}

	@Override
	public void setHeading(int h) {
		heading = h;
	}

	@Override
	public void setRandomx(int rx) {
		randomx = rx;
	}

	@Override
	public void setRandomy(int ry) {
		randomy = ry;
	}

	@Override
	public void setRest(boolean flag) {
		rest = flag;
	}

	@Override
	public boolean isRest() {
		return rest;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String loc) {
		location = loc;
	}

	@Override
	public int getMinRespawnDelay() {
		return minRespawnDelay;
	}

	@Override
	public int getMaxRespawnDelay() {
		return maxRespawnDelay;
	}

	@Override
	public int getAmount() {
		return maximumCount;
	}

	@Override
	public void setAmount(int amount) {
		maximumCount = amount;
	}

	@Override
	public void setMinRespawnDelay(int i) {
		minRespawnDelay = i;
	}

	@Override
	public void setMaxRespawnDelay(int i) {
		maxRespawnDelay = i;
	}

	@Override
	public int getMovementDistance() {
		return movementDistance;
	}

	@Override
	public void setMovementDistance(int i) {
		movementDistance = i;
	}

	@Override
	public boolean isRespawnScreen() {
		return respawnScreen;
	}

	@Override
	public void setRespawnScreen(boolean flag) {
		respawnScreen = flag;
	}

	public void setSpawnUsingThread(boolean spawnUsingThread) {
		this.spawnUsingThread = spawnUsingThread;
	}

	public boolean isSpawnUsingThread() {
		return spawnUsingThread;
	}

	public void setDoorId(int doorId) {
		this.doorId = doorId;
	}

	public void setCountMapId(int countMapId) {
		this.countMapId = countMapId;
	}

	public int getDoorId() {
		return this.doorId;
	}

	public int getCountMapId() {
		return this.countMapId;
	}
}
