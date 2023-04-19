package l1j.server.GameSystem.Boss;

import java.util.HashMap;

public class BossAlive {
	public static BossAlive ins;

	public static BossAlive getInstance(){
		if(ins==null)
			ins = new BossAlive();
		return ins;
	}
	//MapID , 1 Survival 2 Death
	HashMap<Integer,Integer> isAlive = new HashMap<>();

	public boolean isBossAlive(int mapid){
		boolean alive = false;
			if(isAlive.containsKey(mapid)){
				alive = true;
			}

		return alive;
	}

	public void BossSpawn(int mapid){
		isAlive.put(mapid, 1);
	}
	public void BossDeath(int mapid){
		isAlive.remove(mapid);
	}
	//Make this a HashMap later
	public boolean isErzabe = false;
	public long ezTime = -1;
	public void setErzabeTime(long s){
		ezTime = s;
	}
	public boolean isBalrog = false;
	public long BLTime = -1;
	public void setBalrogTime(long s){
		BLTime = s;
	}
	public boolean isSandworm = false;
	public long sdTime = -1;
	public void setSandwormTime(long s){
		sdTime = s;
	}
}

