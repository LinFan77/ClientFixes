/**
 * 본섭 리뉴얼된 린드비오르 레이드
 * 린드 맵 및 입장 관리 클래스
 * by. 케인
 */
package l1j.server.GameSystem.Lindvior;

import java.util.ArrayList;

import javolution.util.FastMap;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;
import l1j.server.server.utils.PerformanceTimer;

public class LindviorRaid {

	private final FastMap<L1Map, Boolean> mapList;
	private static LindviorRaid _instance;

	public static LindviorRaid get() {
		if (_instance == null)
			_instance = new LindviorRaid();
		return _instance;
	}

	private LindviorRaid() {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("[LindviorRaid] loading...");
		mapList = new FastMap<L1Map, Boolean>().shared();
		for (byte i = 0; i < 6; i++) {
			short mapid = (short) (10000 + i);
			L1WorldMap.getInstance().cloneMap(1017, mapid);
			// World.cloneMap(1017, mapid);
			mapList.put(L1WorldMap.getInstance().getMap(mapid), false);
		}
		LindviorThread.get();
		System.out.println("OK! " + timer.get() + "ms");
	}

	public synchronized boolean start(L1PcInstance pc) {
		L1Map map = mapCheck();
		if (map == null) {
			S_SystemMessage sm = new S_SystemMessage("Lindvior portal can no longer be summoned in Aden World.");
			pc.sendPackets(sm, true);
			return false;
		}
		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(pc, 0);
		if (list.size() > 0) {
			pc.sendPackets(new S_SystemMessage("Lindvior portals cannot be summoned at this location."), true);
			return false;
		}
		L1World.getInstance().broadcastServerMessage(
						"IG Gate Dwarf: Ugh... I can hear the dragon's howl from here. Someone must have opened the dragon portal! Glory and blessings to the prepared dragon slayers!");
		// pc.getInventory().consumeItem(430116, 1);
		mapList.put(map, true);

		Lindvior lindvior = new Lindvior(map);
		lindvior.portal = L1SpawnUtil.spawn2(pc.getX(), pc.getY(), pc.getMapId(), 100011, 0, 7200 * 1000, map.getId());
		L1FieldObjectInstance foi = (L1FieldObjectInstance) lindvior.portal;
		foi.Potal_Open_pcid = pc.getId();

		LindviorThread.get().add(lindvior);
		return true;
	}

	private L1Map mapCheck() {
		for (FastMap.Entry<L1Map, Boolean> e = mapList.head(), mapEnd = mapList
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (!e.getValue()) {
				return e.getKey();
			}
		}
		return null;
	}

	public void quit(L1Map map) {
		mapList.put(map, false);
	}

	public void in(L1PcInstance pc) {
		Lindvior lindvior = LindviorThread.get().getLind(pc.dragonmapid);
		if (lindvior == null)
			return;
		pc.dx = 32735;
		pc.dy = 32850;
		pc.dm = pc.dragonmapid;
		pc.dh = 4;
		pc.setTelType(7);
		pc.sendPackets(new S_SabuTell(pc));
		// L1Teleport.teleport(pc, 32674, 32926, (short) pc.dragonmapid, 4,
		// true);
		lindvior.addMember(pc);
	}
}

