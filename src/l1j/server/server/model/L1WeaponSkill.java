package l1j.server.server.model;

import java.util.Random;

import l1j.server.server.ActionCodes;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.WeaponSkillTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconWindShackle;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1Skills;

public class L1WeaponSkill {

	private static Random _random = new Random(System.nanoTime());

	private int _weaponId;

	private int _probability;

	private int _fixDamage;

	private int _randomDamage;

	private int _area;

	private int _skillId;

	private int _skillTime;

	private int _effectId;

	private int _effectTarget;

	private boolean _isArrowType;

	private int _attr;

	public L1WeaponSkill(int weaponId, int probability, int fixDamage,
			int randomDamage, int area, int skillId, int skillTime,
			int effectId, int effectTarget, boolean isArrowType, int attr) {
		_weaponId = weaponId;
		_probability = probability;
		_fixDamage = fixDamage;
		_randomDamage = randomDamage;
		_area = area;
		_skillId = skillId;
		_skillTime = skillTime;
		_effectId = effectId;
		_effectTarget = effectTarget;
		_isArrowType = isArrowType;
		_attr = attr;
	}

	public int getWeaponId() {
		return _weaponId;
	}

	public int getProbability() {
		return _probability;
	}

	public int getFixDamage() {
		return _fixDamage;
	}

	public int getRandomDamage() {
		return _randomDamage;
	}

	public int getArea() {
		return _area;
	}

	public int getSkillId() {
		return _skillId;
	}

	public int getSkillTime() {
		return _skillTime;
	}

	public int getEffectId() {
		return _effectId;
	}

	public int getEffectTarget() {
		return _effectTarget;
	}

	public boolean isArrowType() {
		return _isArrowType;
	}

	public int getAttr() {
		return _attr;
	}

	public static double getWeaponSkillDamage(L1PcInstance pc, L1Character cha, int weaponId) {
		L1WeaponSkill weaponSkill = WeaponSkillTable.getInstance().getTemplate(weaponId);
		if (pc == null || cha == null || weaponSkill == null) {
			return 0;
		}

		int chance = _random.nextInt(100) + 1;
		if (weaponSkill.getProbability() < chance) {
			return 0;
		}

		int skillId = weaponSkill.getSkillId();

		if (skillId == L1SkillId.SILENCE && cha instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) cha;

			if (npc.getNpcId() == 45684 || npc.getNpcId() == 45683 || npc.getNpcId() == 45681
					|| npc.getNpcId() == 45682
					|| npc.getNpcId() == 900013
					|| npc.getNpcId() == 900040
                    || npc.getNpcId() == 5100) {
				return 0;
			}
		}

		/*if (skillId != 0) {
			L1Skills skill = SkillsTable.getInstance().getTemplate(skillId);
			if (skill != null && skill.getTarget().equals("buff")) {
				if (!isFreeze(cha)) {
					if (skillId == L1SkillId.ENCHANT_ACCURACY) {
						if (!cha.hasSkillEffect(skillId)) {
							cha.addHitup(5);
							if (cha instanceof L1PcInstance) {
								L1PcInstance target = (L1PcInstance) cha;
								target.sendPackets(new S_OwnCharAttrDef(target));
							}
						}
					}
					//cha.setSkillEffect(skillId, weaponSkill.getSkillTime() * 1000);
					return 0;
				}
			}
		}*/

		int effectId = weaponSkill.getEffectId();
		if (effectId != 0) {
			int chaId = 0;
			if (weaponSkill.getEffectTarget() == 0) {
				chaId = cha.getId();
			} else {
				chaId = pc.getId();
			}
			boolean isArrowType = weaponSkill.isArrowType();
			if (!isArrowType) {
				pc.sendPackets(new S_SkillSound(chaId, effectId));
				pc.broadcastPacket(new S_SkillSound(chaId, effectId));
			} else {
				S_UseAttackSkill packet = new S_UseAttackSkill(pc, cha.getId(),effectId, cha.getX(), cha.getY(), ActionCodes.ACTION_Attack, false);
				pc.sendPackets(packet);
				pc.broadcastPacket(packet, cha);
			}
		}

		double damage = 0;
		int randomDamage = weaponSkill.getRandomDamage();
		if (randomDamage != 0) {
			damage = _random.nextInt(randomDamage);
		}
		damage += weaponSkill.getFixDamage();

		if(effectId == 6985){
			damage += pc.getAbility().getTotalInt()*3;
		}else{
			damage += pc.getAbility().getTotalInt()*2;
		}

		int area = weaponSkill.getArea();
		if (area > 0 || area == -1) {
			L1PcInstance targetPc = null;
			L1NpcInstance targetNpc = null;
			for (L1Object object : L1World.getInstance().getVisibleObjects(cha, area)) {
				if ((object == null) || !(object instanceof L1Character) || (object.getId() == pc.getId()) || (object.getId() == cha.getId())) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					if (targetPc.getZoneType() == 1) {
						continue;
					}
				}

				if (cha instanceof L1MonsterInstance) {
					if (!(object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				if (cha instanceof L1PcInstance
						|| cha instanceof L1SummonInstance
						|| cha instanceof L1PetInstance) {
					if (!(object instanceof L1PcInstance
							|| object instanceof L1SummonInstance
							|| object instanceof L1PetInstance
							|| object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				damage = calcDamageReduction((L1Character) object, damage,
						weaponSkill.getAttr());
				if (damage <= 0) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					targetPc.sendPackets(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					targetPc.broadcastPacket(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					//targetPc.receiveDamage(pc, (int)damage);
				} else if (object instanceof L1SummonInstance
						|| object instanceof L1PetInstance
						|| object instanceof L1MonsterInstance) {
					targetNpc = (L1NpcInstance) object;
					targetNpc.broadcastPacket(new S_DoActionGFX(targetNpc .getId(), ActionCodes.ACTION_Damage));
					targetNpc.receiveDamage(pc, (int)damage);
				}
			}
		}

		return calcDamageReduction(cha, damage, weaponSkill.getAttr());
	}

	public static double 악운의단검(L1PcInstance pc, L1PcInstance targetPc, L1ItemInstance weapon) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (3 >= chance) {
			dmg = targetPc.getCurrentHp() / 2;
			if (targetPc.getCurrentHp() - dmg < 0) {
				dmg = 0;
			}
			String msg = weapon.getLogName();
			pc.sendPackets(new S_ServerMessage(158, msg));
			pc.getInventory().removeItem(weapon, 1);
		}
		L1PinkName.onAction(targetPc, pc);
		return dmg;
	}

	public static void 홀드(L1PcInstance pc, L1Character cha) {
		int fettersTime = 8000;
		if (isFreeze(cha)) {
			return;
		}
		if ((_random.nextInt(100) + 1) <= 2) {
			L1EffectSpawn.getInstance().spawnEffect(81182, fettersTime, cha.getX(), cha.getY(), cha.getMapId());
			if (cha instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) cha;
				//targetPc.setSkillEffect(L1SkillId.STATUS_FREEZE, fettersTime);
				targetPc.sendPackets(new S_SkillSound(targetPc.getId(), 4184));
				targetPc.broadcastPacket(new S_SkillSound(targetPc.getId(), 4184));
				targetPc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true));
			} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
				L1NpcInstance npc = (L1NpcInstance) cha;
				//npc.setSkillEffect(L1SkillId.STATUS_FREEZE, fettersTime);
				npc.broadcastPacket(new S_SkillSound(npc.getId(), 4184));
				//npc.set발묶임상태(true);
			}
		}
	}

	public static void 체인소드( L1PcInstance pc ) { // 체인소드 약점노출
		int chance = 15;
		if (pc.getInventory().checkEquipped(203017)) { // 섬멸자의 체인소드
			chance += 5;
		}
		/*if (_random.nextInt(100) < chance){
			if (pc.hasSkillEffect(L1SkillId.CHAINSWORD1)) {
				pc.killSkillEffectTimer(L1SkillId.CHAINSWORD1);
				pc.setSkillEffect(L1SkillId.CHAINSWORD2, 15 * 1000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 2));
			} else if (pc.hasSkillEffect(L1SkillId.CHAINSWORD2)) {
				pc.killSkillEffectTimer(L1SkillId.CHAINSWORD2);
				pc.setSkillEffect(L1SkillId.CHAINSWORD3, 15 * 1000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 3));
			} else if (pc.hasSkillEffect(L1SkillId.CHAINSWORD3)) {
				if (SkillsTable.getInstance().spellCheck(pc.getId(), L1SkillId.FOU_SLAYER_BRAVE)) {//포우 슬레이어:브레이브을 배우고있을경우
				    pc.killSkillEffectTimer(L1SkillId.CHAINSWORD3);
				    pc.setSkillEffect(L1SkillId.CHAINSWORD4, 15 * 1000);
				    pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 4));
			    }
			} else if (pc.hasSkillEffect(L1SkillId.CHAINSWORD4)) {
			} else {
				pc.setSkillEffect(L1SkillId.CHAINSWORD1, 15 * 1000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 1));
			}
		}*/
	}

	/*public static void 디지즈(L1PcInstance pc, L1Character cha, int weaponid) {
		int chance = _random.nextInt(100) + 1;
		int skilltime = 64;
		if (7 >= chance) {
			if (!cha.hasSkillEffect(L1SkillId.ENCHANT_ACCURACY)) {
				cha.addDmgup(-6);
				cha.getAC().addAc(12);
				if (cha instanceof L1PcInstance) {
					L1PcInstance target = (L1PcInstance) cha;
					target.sendPackets(new S_OwnCharAttrDef(target));
				}
			}
			//cha.setSkillEffect(L1SkillId.DISEASE, skilltime * 1000);
			pc.sendPackets(new S_SkillSound(cha.getId(), 2230));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 2230));
			if (cha.hasSkillEffect(L1SkillId.ERASE_MAGIC))
				cha.removeSkillEffect(L1SkillId.ERASE_MAGIC);
		}
	}*/

	public static double 펌프킨커스(L1PcInstance pc, L1Character cha) {
		double dmg = 0;
		int skilltime = 4; //패킷인식이 4의 배수만 인식하므로 4의배수로만 설정
		if (pc.getWeapon() == null)
			return 0;
		int chance = 1 + (pc.getWeapon().getEnchantLevel() / 2); //확률
		if (isFreeze(cha)) {
			return 0;
		}
		if (cha.hasSkillEffect(L1SkillId.COUNTER_MAGIC)) {
			cha.removeSkillEffect(L1SkillId.COUNTER_MAGIC);
			pc.sendPackets(new S_SkillSound(cha.getId(), 10702));
			pc.broadcastPacket(new S_SkillSound(cha.getId(), 10702));
			return 0;
		}
		if (_random.nextInt(100) + 1 <= chance) {// 조정
			int sp = pc.getAbility().getSp();
			int intel = pc.getAbility().getTotalInt();
			dmg = (intel + sp) + _random.nextInt(intel + sp) * 0.3;
			if (cha instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) cha;
				if (targetPc.hasSkillEffect(L1SkillId.WIND_SHACKLE)){
					targetPc.removeSkillEffect(L1SkillId.WIND_SHACKLE);
				}
				//targetPc.setSkillEffect(L1SkillId.WIND_SHACKLE, skilltime * 1000);
				targetPc.sendPackets(new S_SkillIconWindShackle(targetPc.getId(), skilltime));
				Broadcaster.broadcastPacket(targetPc, new S_SkillIconWindShackle(targetPc.getId(), skilltime));
				targetPc.sendPackets(new S_SkillSound(targetPc.getId(), 7849));
				Broadcaster.broadcastPacket(targetPc, new S_SkillSound(targetPc.getId(), 7849));
				if (cha.hasSkillEffect(L1SkillId.ERASE_MAGIC)){
					cha.removeSkillEffect(L1SkillId.ERASE_MAGIC);
				}
			} else if (cha instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) cha;
				Broadcaster.broadcastPacket(npc, new S_SkillSound(npc.getId(), 7849));
				if (npc.hasSkillEffect(L1SkillId.WIND_SHACKLE)){
					npc.removeSkillEffect(L1SkillId.WIND_SHACKLE);
				}
				//npc.setSkillEffect(L1SkillId.WIND_SHACKLE, skilltime * 1000);
			}
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_RAY);
	}

	public static double 블레이즈쇼크(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;

		if (val >= chance) {
			int randmg = _random.nextInt(20) + 20;
			dmg = randmg;
			if (dmg < 20){
				dmg = 20;
			}
			pc.sendPackets(new S_SkillSound(cha.getId(), 3939));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 3939));
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_FIRE);
	}

	public static double 레져넌스쇼크(L1PcInstance pc, L1Character cha, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int chance = _random.nextInt(100) + 1;
		if (5 + enchant >= chance) {
			int randmg = _random.nextInt(20) + 20;
			dmg = randmg;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, 7398);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WATER);
	}

	public static double 군주의검(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 섬멸(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 태풍(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (5 >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 악몽(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 싸울혼(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 드래곤의일격(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {
			dmg = _random.nextInt(intel) + intel;
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 심판(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			int randmg = _random.nextInt(25) + 1;
			dmg = 50 + randmg;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_EARTH);
	}
	public static double 필살(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			int randmg = _random.nextInt(25) + 1;
			dmg = 50 + randmg;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 폭풍(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			int randmg = _random.nextInt(25) + 1;
			dmg = 50 + randmg;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 낙뢰(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			int randmg = _random.nextInt(25) + 1;
			dmg = 50 + randmg;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 베놈블레이즈(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (1 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_EARTH);
	}

	public static double 다크라이트닝(L1PcInstance pc, L1Character cha, int effect, int enchant){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 콜드체이서(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 엘븐에로우(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (1 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 일렉트릭쇼크(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (1 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 팬텀쇼크(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}
	public static double 소울커터(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel / 2) + intel;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 마인드브레이크(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int chance = _random.nextInt(100) + 1;
		int 감소량 = 5;
		if (2 + enchant >= chance) {
			dmg = 감소량 * 5;
			if (cha.getCurrentMp() >= 5) {
				cha.setCurrentMp(cha.getCurrentMp() - 감소량);
				if (dmg <= 0) {
					dmg = 0;
				}
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WATER);
	}

	public static double 다크미티어(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int sp = pc.getAbility().getSp();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (5 + enchant >= chance) {
			dmg = _random.nextInt(sp + intel) + ((sp + intel) * 1.5);
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_FIRE);
	}

	public static double 발라카스키링크(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int sp = pc.getAbility().getSp();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (3 + enchant >= chance) {
			dmg = _random.nextInt(sp + intel) + ((sp + intel) * 1.5);
			if (cha.getCurrentMp() >= 5) {
				cha.setCurrentMp(cha.getCurrentMp() - 5);
				if (dmg <= 0) {
					dmg = 0;
				}
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WATER);
	}

	public static double 커츠의검(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel * 2) + intel + 20;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static double 헬파이어(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel * 2) + intel + 20;
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_FIRE);
	}

	public static double 레이징이럽션(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int sp = pc.getAbility().getSp();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (18 + enchant >= chance) {
			dmg = _random.nextInt(sp + intel) + ((sp + intel) * 1.7);
			if (dmg <= 0){
				dmg = 0;
			}
		    S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_EARTH);
	}

	public static double 콘오브콜드(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int sp = pc.getAbility().getSp();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (12 + (enchant) >= chance) {
			dmg = _random.nextInt(sp + intel) + ((sp + intel) * 1.5);
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WATER);
	}

	public static double 크리티컬선버스트(L1PcInstance pc, L1Character cha, int enchant, int effect) {
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int sp = pc.getAbility().getSp();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (8 + enchant >= chance) {
			dmg = _random.nextInt(sp + intel) + ((sp + intel) * 2);
			if (dmg <= 0) {
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_FIRE);
	}

	public static double 라이트닝스트라이크(L1PcInstance pc, L1Character cha, int enchant, int effect){
		double dmg = 0;
		int locx = cha.getX();
		int locy = cha.getY();
		int sp = pc.getAbility().getSp();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (18 + enchant >= chance) {
			dmg = _random.nextInt(sp + intel) + ((sp + intel) * 3);
			if (dmg <= 0){
				dmg = 0;
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_WIND);
	}

	public static void 턴언데드(L1PcInstance pc, L1Character cha, int enchant) {
		int chance = _random.nextInt(100) + 1;
		int undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
		if (undeadType == 1 || undeadType == 3){
			if (2 + enchant >= chance) {
				new L1SkillUse().handleCommands(pc, L1SkillId.TURN_UNDEAD, cha.getId(), cha.getX(), cha.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
			}
		}
	}

	public static double calcDamageReduction(L1Character cha, double dmg, int attr) {
		if (isFreeze(cha)) {
			return 0;
		}

	/*	int ran1 = 0; //랜덤 수치 적용
		int mrset = 0; //엠알에서 랜덤 수치를 뺀값
		int mrs = cha.getResistance().getEffectedMrBySkill();
		ran1 = _random.nextInt(5) + 1;
		mrset = mrs - ran1 ;
		double calMr = 0.00D;
		calMr = (220 - mrset) / 250.00D;
		dmg *= calMr;  */

		if (dmg < 0){
			dmg = 0;
		}

		int resist = 0;
		if (attr == L1Skills.ATTR_EARTH) {
			resist = cha.getResistance().getEarth();
		} else if (attr == L1Skills.ATTR_FIRE) {
			resist = cha.getResistance().getFire();
		} else if (attr == L1Skills.ATTR_WATER) {
			resist = cha.getResistance().getWater();
		} else if (attr == L1Skills.ATTR_WIND) {
			resist = cha.getResistance().getWind();
		}
		int resistFloor = (int) (0.32 * Math.abs(resist));
		if (resist >= 0) {
			resistFloor *= 1;
		} else {
			resistFloor *= -1;
		}
		double attrDeffence = resistFloor / 32.0;
		dmg = (1.0 - attrDeffence) * dmg;

		return dmg;
	}

	private static boolean isFreeze(L1Character cha) {

		if (cha.hasSkillEffect(L1SkillId.STATUS_FREEZE) || cha.hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER) || cha.hasSkillEffect(L1SkillId.ICE_LANCE) || cha.hasSkillEffect(L1SkillId.EARTH_BIND)) {
			return true;
		}

//		if (cha.hasSkillEffect(COUNTER_MAGIC)) {
//			cha.removeSkillEffect(COUNTER_MAGIC);
//			int castgfx = SkillsTable.getInstance().getTemplate(COUNTER_MAGIC).getCastGfx();
//			cha.broadcastPacket(new S_SkillSound(cha.getId(), castgfx));
//			if (cha instanceof L1PcInstance) {
//				L1PcInstance pc = (L1PcInstance) cha;
//				pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
//			}
//			return true;
//		}
		return false;
	}
}
