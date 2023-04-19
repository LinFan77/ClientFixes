package l1j.server.server.model;

import java.util.ArrayList;

import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * Illusionist Cube Class
 */
@SuppressWarnings("unchecked")
public class L1Cube {

	/** cube list */
	private ArrayList<L1EffectInstance> CUBE[] = new ArrayList[4];

	/** single class */
	private static L1Cube instance;

	/** instance initialization */
	{
		for (int i = 0; i < CUBE.length; i++)
			CUBE[i] = new ArrayList<>();
	}

	/**
	 * return cube class
	 *
	 * @return single class object
	 */
	public static L1Cube getInstance() {
		if (instance == null)
			instance = new L1Cube();
		return instance;
	}

	/**
	 * Return cube list
	 * @param index list
	 */
	private L1EffectInstance[] toArray(int index) {
		return CUBE[index].toArray(new L1EffectInstance[CUBE[index].size()]);
	}

	/**
	 * Cube list registration
	 * @param index list
	 * @param npc object to be registered
	 */
	public void add(int index, L1EffectInstance npc) {
		if (!CUBE[index].contains(npc)) {
			CUBE[index].add(npc);
		}
	}

	/**
	 * Delete cube list
	 * @param index list
	 * @param npc object to be deleted
	 */
	private void remove(int index, L1NpcInstance npc) {
		if (CUBE[index].contains(npc)) {
			CUBE[index].remove(npc);
		}
	}

	/** Private */
	private L1Cube() {
		new CUBE1().start();
		new CUBE2().start();
		new CUBE3().start();
		new CUBE4().start();
	}

	/** Level 1 */
	class CUBE1 extends Thread {
		private CUBE1() {
			super("l1j.server.server.model.L1Cube.CUBE1");
		}

		@Override
		public void run() {
			try {
				while (true) {
					sleep(1000L);
					for (L1EffectInstance npc : toArray(0)) {
						// If the duration is over
						if (npc.Cube()) {
							npc.setCubePc(null);
							remove(0, npc);
							continue;
						}
						if (npc.isCube()) {
							// The blood of the person who pulled the 3-cell Pc search cube around us. Once the other blood is red blood
							for (L1PcInstance pc : L1World.getInstance()
									.getVisiblePlayer(npc, 3)) {
								// If the person on the cube is the caster or a blood ally
								if (npc.CubePc().getId() == pc.getId() || npc.CubePc().getClanid() == pc.getClanid()
										|| (npc.CubePc().isInParty() && npc.CubePc().getParty().isMember(pc))) {
									if (!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_IGNITION)) {
										pc.getResistance().addFire(30);
										pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_IGNITION, 8 * 1000);
										pc.sendPackets(new S_OwnCharAttrDef(pc));
									} else {
										pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_IGNITION, 8 * 1000);
										pc.sendPackets(new S_OwnCharAttrDef(pc));
									}
									pc.sendPackets(new S_SkillSound(pc.getId(), 6708));
								} else {
									if (CharPosUtil.getZoneType(pc) == 1) {
										continue;
									}
									pc.receiveDamage(npc.CubePc(), 15, false);
									pc.sendPackets(new S_SkillSound(pc.getId(), 6709));
								}
							}
							npc.setCubeTime(4);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Level 2 */
	class CUBE2 extends Thread {
		private CUBE2() {
			super("l1j.server.server.model.L1Cube.CUBE2");
		}

		@Override
		public void run() {
			try {
				while (true) {
					sleep(1000L);
					for (L1EffectInstance npc : toArray(1)) {
						// If the duration is over
						if (npc.Cube()) {
							npc.setCubePc(null);
							remove(1, npc);
							continue;
						}
						if (npc.isCube()) {
							// The blood of the person who pulled the 3-cell Pc search cube around us. Once the other blood is red blood
							for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(npc, 3)) {
								// If the person on the cube is the caster or a blood ally
								if (npc.CubePc().getId() == pc.getId() || npc.CubePc().getClanid() == pc.getClanid()
										|| (npc.CubePc().isInParty() && npc.CubePc().getParty().isMember(pc))) {
									if (!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_QUAKE)) {
										pc.getResistance().addEarth(30);
										pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_QUAKE, 8 * 1000);
										pc.sendPackets(new S_OwnCharAttrDef(pc));
									} else {
										pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_QUAKE, 8 * 1000);
										pc.sendPackets(new S_OwnCharAttrDef(pc));
									}
									pc.sendPackets(new S_SkillSound(pc.getId(), 6714));
								} else {
									if (CharPosUtil.getZoneType(pc) == 1) {
										continue;
									}
									pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true));
									pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_FREEZE, 1 * 1000);
									pc.sendPackets(new S_SkillSound(pc.getId(), 6715));
								}
							}
							npc.setCubeTime(4);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Level 3 */
	class CUBE3 extends Thread {
		private CUBE3() {
			super("l1j.server.server.model.L1Cube.CUBE3");
		}

		@Override
		public void run() {
			try {
				while (true) {
					sleep(1000L);
					for (L1EffectInstance npc : toArray(2)) {
						// If the duration is over
						if (npc.Cube()) {
							npc.setCubePc(null);
							remove(2, npc);
							npc.deleteMe();
							continue;
						}
						if (npc.isCube()) {
							// The blood of the person who pulled the 3-cell Pc search cube around us. Once the other blood is red blood
							for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(npc, 3)) {
								// If the person on the cube is the caster or a blood ally
								if (npc.CubePc().getId() == pc.getId() || npc.CubePc().getClanid() == pc.getClanid()
										|| (npc.CubePc().isInParty() && npc.CubePc().getParty().isMember(pc))) {
									if (!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_SHOCK)) {
										pc.getResistance().addWind(30);
										pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_SHOCK, 8 * 1000);
										pc.sendPackets(new S_OwnCharAttrDef(pc));
									} else {
										pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_SHOCK, 8 * 1000);
										pc.sendPackets(new S_OwnCharAttrDef(pc));
									}
									pc.sendPackets(new S_SkillSound(pc.getId(), 6720));
								} else {
									if (CharPosUtil.getZoneType(pc) == 1) {
										continue;
									}
									pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_DESHOCK, 8 * 1000);
									pc.sendPackets(new S_SkillSound(pc.getId(), 6721));
								}
							}
							npc.setCubeTime(4);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Level 4 */
	class CUBE4 extends Thread {
		private CUBE4() {
			super("l1j.server.server.model.L1Cube.CUBE4");
		}

		@Override
		public void run() {
			try {
				while (true) {
					sleep(1000L);
					for (L1EffectInstance npc : toArray(3)) {
						// If the duration is over
						if (npc.Cube()) {
							npc.setCubePc(null);
							remove(3, npc);
							continue;
						}
						if (npc.isCube()) {
							// The blood of the person who pulled the 3-cell Pc search cube around us. Once the other blood is red blood
							for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(npc, 3)) {
								if (pc != null) {
									if (pc.getCurrentHp() > 0) {
										pc.receiveDamage(npc.CubePc(), 25, false);
										pc.setCurrentMp(pc.getCurrentMp() + 5);
										pc.sendPackets(new S_SkillSound(pc.getId(), 6727));
									}
								}
							}
							npc.setCubeTime(5);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
