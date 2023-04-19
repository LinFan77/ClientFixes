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
package l1j.server.server.TimeController;

import java.util.ArrayList;
import java.util.List;

import l1j.server.server.datatables.LightSpawnTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.gametime.BaseTime;
import l1j.server.server.model.gametime.GameTime;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.model.gametime.TimeListener;

public class LightTimeController implements TimeListener {
	private static LightTimeController _instance;
	private static boolean isSpawnerActive = true;

	public static void start() {
		if (_instance == null) {
			_instance = new LightTimeController();
		}

		GameTimeClock.getInstance().addListener(_instance);

		if (GameTimeClock.getInstance().getGameTime().isNight())
			isSpawnerActive = false;
		else
			isSpawnerActive = true;

		_instance.changeLight(GameTimeClock.getInstance().getGameTime());
		System.out.println("[LightTimeController] started!");
	}

	private void changeLight(GameTime gameTime) {
		if (gameTime.isNight())
			activateLightSpawner();
		else
			turnOff();
	}

	/*private void turnOn() {
		if (!isSpawn) {
			isSpawn = true;
			LightSpawnTable.getInstance().FillLightSpawnTable();
		}
	}*/

	private void activateLightSpawner() {
	    if (!isSpawnerActive) {
	        isSpawnerActive = true;
	        LightSpawnTable lightSpawnTable = LightSpawnTable.getInstance();
	        if (lightSpawnTable != null) {
	            lightSpawnTable.FillLightSpawnTable();
	        } else {
	            System.err.println("Failed to get LightSpawnTable instance");
	        }
	    }
	}

	private void turnOff() {
	    if (!isSpawnerActive) {
	        return;
	    }

	    isSpawnerActive = false;
	    deleteSpawnedNpcs();
	}

	private void deleteSpawnedNpcs() {
	    for (L1FieldObjectInstance fieldObjectInstance : getSpawnedNpcs()) {
	        fieldObjectInstance.deleteMe();
	    }
	}

	private List<L1FieldObjectInstance> getSpawnedNpcs() {
	    List<L1FieldObjectInstance> spawnedNpcs = new ArrayList<>();

	    for (L1Object object : L1World.getInstance().getObject()) {
	        if (!(object instanceof L1FieldObjectInstance)) {
	            continue;
	        }

	        L1FieldObjectInstance fieldObjectInstance = (L1FieldObjectInstance) object;
	        if (isSpawnedNpc(fieldObjectInstance)) {
	            spawnedNpcs.add(fieldObjectInstance);
	        }
	    }

	    return spawnedNpcs;
	}

	private boolean isSpawnedNpc(L1FieldObjectInstance fieldObjectInstance) {
	    int npcId = fieldObjectInstance.getNpcTemplate().get_npcId();
	    int mapId = fieldObjectInstance.getMapId();

	    boolean isInRange = (npcId >= 81177 && npcId <= 81181) || npcId == 4500000 || npcId == 4500002 || npcId == 81160;

	    boolean isInAllowedMap = mapId == 0 || mapId == 4;

	    return isInRange && isInAllowedMap;
	}


	/*private void turnOff() {
		if (isSpawn) {
			isSpawn = false;
			L1FieldObjectInstance npc = null;
			for (L1Object object : L1World.getInstance().getObject()) {
				if (object instanceof L1FieldObjectInstance) {
					npc = (L1FieldObjectInstance) object;
					// 81177, 81178, 81179, 81180, 81181
					if (((npc.getNpcTemplate().get_npcId() >= 81177 && npc.getNpcTemplate().get_npcId() <= 81181)
							|| npc.getNpcTemplate().get_npcId() == 4500000 || npc.getNpcTemplate().get_npcId() == 4500002
							|| npc.getNpcTemplate().get_npcId() == 81160) && (npc.getMapId() == 0 || npc.getMapId() == 4)) {
						npc.deleteMe();
						npc = null;
					}
				}
			}
		}
	}*/

	@Override
	public void onHourChanged(BaseTime time) {
		changeLight((GameTime) time);
	}

	@Override
	public void onDayChanged(BaseTime time) {
	}

	@Override
	public void onMinuteChanged(BaseTime time) {
	}

	@Override
	public void onMonthChanged(BaseTime time) {
	}
}

