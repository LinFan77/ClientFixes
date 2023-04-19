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
package l1j.server.server.model;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.ARMOR_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BLOWATTACK;
import static l1j.server.server.model.skill.L1SkillId.BRAVE_MENTAL;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_닭고기;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_연어;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_칠면조;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐닭고기;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐연어;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐칠면조;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐한우;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_한우;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.CUBE_AVATAR;
import static l1j.server.server.model.skill.L1SkillId.DOUBLE_BRAKE;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FIRE;
import static l1j.server.server.model.skill.L1SkillId.ENCHANT_VENOM;
import static l1j.server.server.model.skill.L1SkillId.ENTANGLE;
import static l1j.server.server.model.skill.L1SkillId.FEAR;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static l1j.server.server.model.skill.L1SkillId.FOCUS_WAVE;
import static l1j.server.server.model.skill.L1SkillId.HEUKSA;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.ILLUSION_AVATAR;
import static l1j.server.server.model.skill.L1SkillId.IMMUNE_TO_HARM;
import static l1j.server.server.model.skill.L1SkillId.LIFE_MAAN;
import static l1j.server.server.model.skill.L1SkillId.LUCIFER;
import static l1j.server.server.model.skill.L1SkillId.MAJESTY;
import static l1j.server.server.model.skill.L1SkillId.METIS_CUISINE;
import static l1j.server.server.model.skill.L1SkillId.METIS_SOUP;
import static l1j.server.server.model.skill.L1SkillId.MIRROR_IMAGE;
import static l1j.server.server.model.skill.L1SkillId.MOBIUS;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.MOB_COCA;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.PSY_COOL_DRINK;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_LINDVIOR;
import static l1j.server.server.model.skill.L1SkillId.SOUL_OF_FLAME;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING2;
import static l1j.server.server.model.skill.L1SkillId.SPICY_RAMEN;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_BARLOG;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_YAHEE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import static l1j.server.server.model.skill.L1SkillId.STRIKER_GALE;
import static l1j.server.server.model.skill.L1SkillId.UNCANNY_DODGE;
import static l1j.server.server.model.skill.L1SkillId.VALA_MAAN;
import static l1j.server.server.model.skill.L1SkillId.CYCLONE;
import static l1j.server.server.model.skill.L1SkillId.메티스축복주문서;

import java.util.Random;

import l1j.server.Config;
import l1j.server.GameSystem.Astar.World;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.MJInstanceSystem.MJInstanceEnums.InstStatus;
import l1j.server.server.ActionCodes;
import l1j.server.server.Opcodes;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.WeaponAddDamage;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.poison.L1ParalysisPoison;
import l1j.server.server.model.poison.L1SilencePoison;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_AttackCritical;
import l1j.server.server.serverpackets.S_AttackMissPacket;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_AttackPacketForNpc;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_NewSkillIcons;
import l1j.server.server.serverpackets.S_PetWindow;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseArrowSkill;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.types.Point;
import l1j.server.server.utils.CalcStat;
import l1j.server.server.utils.CommonUtil;

public class L1Attack {

	private static final Random _Random = new Random(System.nanoTime());

	private L1PcInstance _pc = null;

	private L1Character _user = null;

	private L1Character _target = null;

	private L1PcInstance _targetPc = null;

	private L1NpcInstance _npc = null;

	private L1NpcInstance _targetNpc = null;

	private final int _targetId;

	private int _getBuffIconDuration;

	private int _targetX;

	private int _targetY;

	private int _statusDamage = 0;

	private static final Random _random = new Random(System.nanoTime());

	private int _hitRate = 0;

	private int _calcType;

	private static final int PC_PC = 1;

	private static final int PC_NPC = 2;

	private static final int NPC_PC = 3;

	private static final int NPC_NPC = 4;

	private boolean _isHit = false;

	private int _damage = 0;

	private int _drainMana = 0;

	/** Joe's Stone Golem **/
	private int _drainHp = 0;

	private int _attckGrfxId = 0;

	private int _attckActId = 0;

	private static final int[] strDmg = new int[128];

	static {
		// STR Damage Compensation
		for (int str = 0; str <= 8; str++) {
			// 1~8 -2
			strDmg[str] = -2;
		}
		for (int str = 9; str <= 10; str++) {
			// 9~10 -1
			strDmg[str] = -1;
		}
		strDmg[11] = 0;
		strDmg[12] = 0;
		strDmg[13] = 1;
		strDmg[14] = 1;
		strDmg[15] = 2;
		strDmg[16] = 2;
		strDmg[17] = 3;
		strDmg[18] = 3;
		strDmg[19] = 4;
		strDmg[20] = 4;
		strDmg[21] = 5;
		strDmg[22] = 5;
		strDmg[23] = 6;
		strDmg[24] = 6;
		strDmg[25] = 6;
		strDmg[26] = 7;
		strDmg[27] = 7;
		strDmg[28] = 7;
		strDmg[29] = 8;
		strDmg[30] = 8;
		strDmg[31] = 9;
		strDmg[32] = 9;
		strDmg[33] = 10;
		strDmg[34] = 11;
		strDmg[35] = 12;
		strDmg[36] = 12;
		strDmg[37] = 12;
		strDmg[38] = 12;
		strDmg[39] = 13;
		strDmg[40] = 13;
		strDmg[41] = 13;
		strDmg[42] = 13;
		strDmg[43] = 14;
		strDmg[44] = 14;
		strDmg[45] = 14;
		strDmg[46] = 14;
		strDmg[47] = 15;
		strDmg[48] = 15;
		strDmg[49] = 16;
		strDmg[50] = 17;
		//int dmg = 18;
		int dmg = 0;
		for (int str = 51; str <= 127; str++) { // 51~127은 4마다＋1
			if (str % 4 == 1) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
	}

	private static final int[] dexDmg = new int[128];

	static {
		// DEX damage correction
		for (int dex = 0; dex <= 11; dex++) {
			// 0~11는 0
			dexDmg[dex] = 0;
		}
		dexDmg[9] = 3;
		dexDmg[10] = 3;
		dexDmg[11] = 3;
		dexDmg[12] = 4;
		dexDmg[13] = 4;
		dexDmg[14] = 4;
		dexDmg[15] = 5;
		dexDmg[16] = 5;
		dexDmg[17] = 5;
		dexDmg[18] = 6;
		dexDmg[19] = 6;
		dexDmg[20] = 6;
		dexDmg[21] = 7;
		dexDmg[22] = 7;
		dexDmg[23] = 7;
		dexDmg[24] = 8;
		dexDmg[25] = 9;
		dexDmg[26] = 9;
		dexDmg[27] = 10;
		dexDmg[28] = 10;
		dexDmg[29] = 10;
		dexDmg[30] = 11;
		dexDmg[31] = 11;
		dexDmg[32] = 11;
		dexDmg[33] = 12;
		dexDmg[34] = 12;
		dexDmg[35] = 13;
		dexDmg[36] = 14;
		dexDmg[37] = 14;
		dexDmg[38] = 14;
		dexDmg[39] = 15;
		dexDmg[40] = 15;
		dexDmg[41] = 15;
		dexDmg[42] = 16;
		dexDmg[43] = 16;
		dexDmg[44] = 16;
		dexDmg[45] = 20;
		dexDmg[46] = 20;
		dexDmg[47] = 20;
		dexDmg[48] = 21;
		dexDmg[49] = 21;
		dexDmg[50] = 21;
		dexDmg[51] = 22;
		dexDmg[52] = 22;
		dexDmg[53] = 22;
		dexDmg[54] = 23;
		dexDmg[55] = 23;
		dexDmg[56] = 23;
		dexDmg[57] = 24;
		dexDmg[58] = 24;
		dexDmg[59] = 24;
		dexDmg[60] = 25;
		dexDmg[61] = 25;
		dexDmg[62] = 25;
		dexDmg[63] = 26;
		dexDmg[64] = 26;
		dexDmg[65] = 26;
		dexDmg[66] = 27;
		dexDmg[67] = 27;
		dexDmg[68] = 27;
		dexDmg[69] = 28;
		dexDmg[70] = 28;
		dexDmg[71] = 28;
		dexDmg[72] = 29;
		dexDmg[73] = 29;
		dexDmg[74] = 29;
		dexDmg[75] = 30;
		dexDmg[76] = 30;
		dexDmg[77] = 30;
		dexDmg[78] = 31;
		dexDmg[79] = 31;
		dexDmg[80] = 31;
		dexDmg[81] = 32;
		dexDmg[82] = 32;
		dexDmg[83] = 32;
		dexDmg[84] = 33;
		dexDmg[85] = 33;
		dexDmg[86] = 33;
		dexDmg[87] = 34;
		dexDmg[88] = 34;
		dexDmg[89] = 34;
		dexDmg[90] = 35;

		/*int dmg = 7;
		for (int dex = 36; dex <= 127; dex++) { // 48~127은 4마다＋1 //#
			if (dex % 4 == 1) {
				dmg++;
			}
			dexDmg[dex] = dmg;
		}*/
	}

	private static final int[] IntDmg = new int[128]; // 키링크 인트

	static {
		for (int Int = 0; Int <= 8; Int++) {
			// 1~8는 -2
			IntDmg[Int] = -2;
		}
		for (int Int = 9; Int <= 14; Int++) {
			// 9~10는 -1
			IntDmg[Int] = -1;
		}
		IntDmg[15] = 0;
		IntDmg[16] = 0;
		IntDmg[17] = 1;
		IntDmg[18] = 1;
		IntDmg[19] = 2;
		IntDmg[20] = 2;
		IntDmg[21] = 2;
		IntDmg[22] = 2;
		IntDmg[23] = 3;
		IntDmg[24] = 3;
		IntDmg[25] = 3;
		IntDmg[26] = 4;
		IntDmg[27] = 4;
		IntDmg[28] = 4;
		IntDmg[29] = 5;
		IntDmg[30] = 5;
		IntDmg[31] = 5;
		IntDmg[32] = 6;
		IntDmg[33] = 6;
		IntDmg[34] = 6;
		int dmg = 7;
		for (int Int = 35; Int <= 127; Int++) { // 35~127은 4마다＋1
			if (Int % 4 == 1) {
				dmg++;
			}
			IntDmg[Int] = dmg;
		}
	}

	// 공격자가 플레이어의 경우의 무기 정보
	private L1ItemInstance Sweapon = null;// 세컨웨폰
	private int _SweaponId = 0;
	private int _SweaponType = 0;
	private int _SweaponType1 = 0;
	private int _SweaponAddHit = 0;
	private int _SweaponAddDmg = 0;
	private int _SweaponSmall = 0;
	private int _SweaponLarge = 0;
	private int _SweaponRange = 1;
	private int _SweaponBless = 1;
	private int _SweaponEnchant = 0;
	private int _SweaponMaterial = 0;
	private int _SweaponAttrEnchantLevel = 0;

	private L1ItemInstance weapon = null;

	private int _weaponId = 0;
	private int _weaponType2 = 0;
	private int _weaponType = 0;
	private int _weaponAddHit = 0;
	private int _weaponAddDmg = 0;
	private int _weaponSmall = 0;
	private int _weaponLarge = 0;
	private int _weaponRange = 1;
	private int _weaponBless = 1;
	private int _weaponEnchant = 0;
	private int _weaponMaterial = 0;
	private int _weaponAttr = 0;
	private int _weaponAttrEnchantLevel = 0;
	private int _weaponDoubleDmgChance = 0;
	private int _attackType = 0;

	private boolean _critical = false;
	private boolean _속성공격 = false;

	private int _속성공격임팩트 = 0;

	private boolean _속성치명 = false;
	private int _속성치명임팩트 = 0;

	private L1ItemInstance _arrow = null;
	private L1ItemInstance _sting = null;

	private int _leverage = 10; // 1/10배로 표현한다.

	public void setLeverage(int i) {
		_leverage = i;
	}

	private int getLeverage() {
		return _leverage;
	}

	// 공격자가 플레이어의 경우의 스테이터스에 의한 보정
	public void setActId(int actId) {
		_attckActId = actId;
	}

	public void setGfxId(int gfxId) {
		_attckGrfxId = gfxId;
	}

	public int getActId() {
		return _attckActId;
	}

	public int getGfxId() {
		return _attckGrfxId;
	}

	public L1Attack(L1Character attacker, L1Character target) {
		if (attacker instanceof L1PcInstance) {
			_pc = (L1PcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = PC_PC;
			} else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = PC_NPC;
			}
			// Acquisition of weapon information
			weapon = _pc.getWeapon();
			Sweapon = _pc.getSecondWeapon();
			if (Sweapon != null) {
				_SweaponId = Sweapon.getItem().getItemId();
				_SweaponType = Sweapon.getItem().getType1();
				_SweaponAddHit = Sweapon.getItem().getHitModifier() + Sweapon.getHitByMagic();
				_SweaponAddDmg = Sweapon.getItem().getDmgModifier() + Sweapon.getDmgByMagic();
				_SweaponType1 = Sweapon.getItem().getType();
				_SweaponSmall = Sweapon.getItem().getDmgSmall();
				_SweaponLarge = Sweapon.getItem().getDmgLarge();

				_SweaponRange = Sweapon.getItem().getRange();
				_SweaponBless = Sweapon.getItem().getBless();
				_SweaponEnchant = Sweapon.getEnchantLevel() - Sweapon.get_durability(); // 손상분 마이너스
				_SweaponMaterial = Sweapon.getItem().getMaterial();
				_SweaponAttrEnchantLevel = Sweapon.getAttrEnchantLevel();
			}
			if (weapon != null) {
				_weaponId = weapon.getItem().getItemId();
				_weaponType2 = weapon.getItem().getType1();
				_weaponAddHit = weapon.getItem().getHitModifier() + weapon.getHitByMagic();
				_weaponAddDmg = weapon.getItem().getDmgModifier() + weapon.getDmgByMagic() + weapon.getItem().getaddDmg();
				_weaponType = weapon.getItem().getType();
				_weaponSmall = weapon.getItem().getDmgSmall();
				_weaponLarge = weapon.getItem().getDmgLarge();
				_weaponRange = weapon.getItem().getRange();
				_weaponBless = weapon.getItem().getBless();
				if (_weaponType2 == 0) {
                    _weaponEnchant = 0;
                }
				if (_weaponType2 != 20 && _weaponType2 != 62) {
					_weaponEnchant = weapon.getEnchantLevel() - weapon.get_durability(); // minus damage
				} else {
					_weaponEnchant = weapon.getEnchantLevel();
				}
				_weaponMaterial = weapon.getItem().getMaterial();
				if (_weaponType2 == 20) { // arrows
					_arrow = _pc.getInventory().getArrow();
					if (_arrow != null) {
						if (_arrow.getItemId() == 50747)// 수령
							_weaponAttr = 1;
						if (_arrow.getItemId() == 50748)// 풍령
							_weaponAttr = 2;
						if (_arrow.getItemId() == 50749)// 지령
							_weaponAttr = 3;
						if (_arrow.getItemId() == 50750)// 화령
							_weaponAttr = 4;

						_weaponBless = _arrow.getItem().getBless();
						_weaponMaterial = _arrow.getItem().getMaterial();
					}
				} else if (_weaponType2 == 62) { // Acquisition of Sting
					_sting = _pc.getInventory().getSting();
					if (_sting != null) {
						_weaponBless = _sting.getItem().getBless();
						_weaponMaterial = _sting.getItem().getMaterial();
					}
				}
				_weaponDoubleDmgChance = weapon.getItem().getDoubleDmgChance();
				_weaponAttrEnchantLevel = weapon.getAttrEnchantLevel();
			}
			// Additional damage from stats
			if (_weaponType2 == 20 || _weaponType2 == 62) { // In the case of a bow/gauntlet, refer to the DEX value
				_statusDamage = CalcStat.calcDamageFromDEX(_pc.getAbility().getTotalDex()) + CalcStat.calcDEXBonusDamage(_pc.getAbility().getBaseDex());
			} else {
				_statusDamage = CalcStat.calcDamageFromSTR(_pc.getAbility().getTotalStr()) + CalcStat.calcSTRBonusDamage(_pc.getAbility().getBaseStr());
			}
		}

		else if (_weaponId == 410003 || _weaponId == 410004 || _weaponId == 411030 || _weaponId == 450006 || _weaponId == 450013) { // In case of Kiringku, refer to INT value
			_statusDamage = CalcStat.calcMagicDmg(_pc.getAbility().getTotalInt() + CalcStat.calcINTBonusDamage(_pc.getAbility().getBaseInt()));
		} else if (attacker instanceof L1NpcInstance) {
			_npc = (L1NpcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = NPC_PC;
			} else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = NPC_NPC;
			}
		}
		if (target != null) {
			_target = target;
			_targetId = target.getId();
			_targetX = target.getX();
			_targetY = target.getY();
		} else {
			_targetId = 0;
		}
	}

	/* ■■■■■■■■■■■■■■■■ hit check ■■■■■■■■■■■■■■■■ */
	/**
	 *Used to change the direction to the corresponding coordinates.
	 */
	public int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}

	public int calcheading(L1Character o, int x, int y) {
		return calcheading(o.getX(), o.getY(), x, y);
	}

	public boolean calcHit() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			/** 2016.11.26 MJ App Center LFC **/
			if (_pc instanceof L1PcInstance) {
				/* Miss during the lfc game Laddy*/
				if (_pc.getInstStatus() == InstStatus.INST_USERSTATUS_LFCINREADY)
					return false;
			}

			if (_target.getX() == _pc.getX() && _target.getY() == _pc.getY() && _target.getMapId() == _target.getMapId()) {
				_isHit = true;
				return true;
			}
			if (_target instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) _target;
				if (mon.isreodie) {
					return false;
				}
			}
			boolean door = World.moveTheDoor(_pc.getX(), _pc.getY(), _pc.getMapId(), calcheading(_pc, _target.getX(), _target.getY()));
			boolean tail = World.isThroughAttack(_pc.getX(), _pc.getY(), _pc.getMapId(), calcheading(_pc, _target.getX(), _target.getY()));
			if (_pc.getX() == _target.getX() && _pc.getY() == _target.getY() && _pc.getMapId() == _target.getMapId())
				tail = true;
			if (door || !tail) {
				if (!(_target instanceof L1DoorInstance)) {
					_isHit = false;
					return _isHit;
				}
			}
			if (_pc instanceof L1RobotInstance && _pc.isElf()) {
				if (!_pc.getLocation().isInScreen(_target.getLocation())) {
					_isHit = false;
					return _isHit;
				}
			} else if (_weaponRange != -1) {
				if (_target instanceof L1MonsterInstance) {
					if (((L1MonsterInstance) _target).getNpcId() == 100584 || ((L1MonsterInstance) _target).getNpcId() == 100588
							|| ((L1MonsterInstance) _target).getNpcId() == 100589)
						_weaponRange++;
				}
				if (_pc.getLocation().getTileLineDistance(_target.getLocation()) > _weaponRange + 1) {
					_isHit = false;
					return _isHit;
				}
				if (_weaponType == 17) { // kiringku
					if (_target.hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER)) {
	                    _isHit = false;
	                } else {
	                    _isHit = true;
	                }

					if (_target instanceof L1NpcInstance) {
						int npcId = ((L1NpcInstance) _target).getNpcTemplate().get_npcId();
						if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_BARLOG) && (npcId == 445752 || npcId == 445753 // 발록버프 해제
								|| npcId == 7000026 || npcId == 7000027 || npcId == 7000012 || npcId == 7000019 || npcId == 7000006 || npcId == 7000013
								|| npcId == 7000020 || npcId == 7000007 || npcId == 7000014 || npcId == 7000021 || npcId == 7000030 || npcId == 7000031
								|| npcId == 7000032 || npcId == 7000033 || npcId == 7000034 || npcId == 7000035 || npcId == 7000036 || npcId == 7000037
								|| npcId == 7000038 || npcId == 7000039 || npcId == 7000040 || npcId == 7000041 || npcId == 7000008 || npcId == 7000015
								|| npcId == 7000022 || npcId == 7000009 || npcId == 7000016 || npcId == 7000023 || npcId == 7000010 || npcId == 7000017
								|| npcId == 7000024 || npcId == 7000028 || npcId == 7000029 || npcId == 7000030 || npcId == 7000011 || npcId == 7000018
								|| npcId == 7000025 || npcId == 7000042)) {// 발록
							_isHit = false;
						} else if (!_pc.getSkillEffectTimerSet() // 야히 버프없이 때리게 가능
								.hasSkillEffect(STATUS_CURSE_YAHEE)
								&& (npcId == 45675111 || npcId == 810821111 || npcId == 45625111 || npcId == 456741111 || npcId == 1005701111)) {
							_isHit = false;
						}
					} else if (_target instanceof L1PcInstance) {
						if (_target.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MAGIC)) {
							_target.getSkillEffectTimerSet().removeSkillEffect(COUNTER_MAGIC);
							int castgfx = 10702;
							Broadcaster.broadcastPacket(_target, new S_SkillSound(_target.getId(), castgfx), true);
							((L1PcInstance) _target).sendPackets(new S_SkillSound(_target.getId(), castgfx), true);
							_isHit = false;
						}
					}
					return _isHit;
				}
			} else {
				if (!_pc.getLocation().isInScreen(_target.getLocation())) {
					_isHit = false;
					return _isHit;
				}
			}
			if (!(_pc instanceof L1RobotInstance) && _weaponType2 == 20 && _weaponId != 190 && _weaponId != 100190 && _weaponId != 11011 && _weaponId != 11012
					&& _weaponId != 11013 && _weaponId != 7201 && _weaponId != 30082 && _weaponId != 30091 && _arrow == null) {
				_isHit = false; // 화살이 없는 경우는 미스
			} else if (_weaponType2 == 62 && _sting == null) {
				_isHit = false; // 스팅이 없는 경우는 미스
			} else if (!(_target instanceof L1DoorInstance) && (!CharPosUtil.isAreaAttack(_pc, _targetX, _targetY, _target.getMapId())
					|| !CharPosUtil.isAreaAttack(_target, _pc.getX(), _pc.getY(), _pc.getMapId()))) {
				_isHit = false; // 공격자가 플레이어의 경우는 장애물 판정
			} else if (_weaponId == 247 || _weaponId == 248 || _weaponId == 249) {
				_isHit = false; // 시련의 검B~C 공격 무효
			} else if (_calcType == PC_PC) {
				if (CharPosUtil.getZoneType(_pc) == 1 || CharPosUtil.getZoneType(_targetPc) == 1) {
					// _pc.sendPackets(new S_SkillSound(_target.getId(), 13418));// 이펙트
					// _targetPc.sendPackets(new S_SkillSound(_target.getId(), 13418));// 이펙트
					_isHit = false;
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ABSOLUTE_BLADE) && _targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)) {
					int c = _pc.getLevel() - 80;
					if (c > 8) // max
						c = 8;
					if (c < 3)
						c = 1; // min
					if (CommonUtil.random(100) <= c) {
						_targetPc.getSkillEffectTimerSet().removeSkillEffect(ABSOLUTE_BARRIER);
						_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14539));
						_pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14539));
					}
				}
				_isHit = calcPcPcHit();
				if (!_isHit) {
					// _pc.sendPackets(new S_SkillSound(_target.getId(), 13418));// 이펙트
					// _targetPc.sendPackets(new S_SkillSound(_target.getId(), 13418));// 이펙트
				}
			} else if (_calcType == PC_NPC) {
				/** 오브젝트가 만약 펫이라면 어택 리셋 */
				if (_targetNpc instanceof L1PetInstance) {
					L1PcInstance PetMaster = (L1PcInstance) _targetNpc.getMaster();
					if (PetMaster == null)
						_isHit = false;
					/** 보라 상태에서만 pc를 공격 할수잇음 */
					if (_pc.isPinkName() && PetMaster.isPinkName()) {
						_isHit = calcPcNpcHit();
					} else
						_isHit = false;
				} else {
					_isHit = calcPcNpcHit();
					if (!_isHit) {
						_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 13418));// 이펙트
					}
				}
			}
		} else if (_calcType == NPC_PC) {
			/** 오브젝트가 만약 펫이라면 어택 리셋 */
			if (_npc instanceof L1PetInstance) {
				/** 보라 상태에서만 pc를 공격 할수잇음 */
				L1PcInstance PetMaster = (L1PcInstance) _npc.getMaster();
				if (_targetPc.isPinkName() && PetMaster.isPinkName()) {
					_isHit = calcNpcPcHit();
				} else
					_isHit = false;
				if (!_isHit) {
					if (_npc instanceof L1PetInstance) {
						L1PetInstance Pet = (L1PetInstance) _npc;
						/** 투지 상태라면 크리티컬 적용 */
						if (Pet.SkillCheck(L1SkillId.Fighting))
							Pet.setFightingCombo(0);
						if (PetMaster != null)
							PetMaster.sendPackets(new S_SkillSound(_npc.getId(), 13418), true);
					}
				}
			} else {
				_isHit = calcNpcPcHit();
				if (!_isHit) {
					_targetPc.sendPackets(new S_SkillSound(_target.getId(), 13418));// 이펙트
				}
			}
		} else

		if (_calcType == NPC_NPC) {
			_isHit = calcNpcNpcHit();
			if (!_isHit) {
				if (_npc instanceof L1PetInstance) {
					L1PetInstance Pet = (L1PetInstance) _npc;
					/** 투지 상태라면 크리티컬 적용 */
					if (Pet.SkillCheck(L1SkillId.Fighting))
						Pet.setFightingCombo(0);
					L1PcInstance PetMaster = (L1PcInstance) Pet.getMaster();
					if (PetMaster != null)
						PetMaster.sendPackets(new S_SkillSound(_npc.getId(), 13418), true);
				}
			}
		}
		return _isHit;
	}

	// ●●●● Hit judgment from player to player ●●●●
	/*
	 * Accuracy rate to PC = (PC Lv + class correction + STR correction + DEX correction + weapon correction + number of DAI / 2 + magic correction) × 0.68-10
	 * The calculated value is the AC of the opponent's PC that can do the job of giving the maximum hit (95%). From there, the opponent's PC's AC increases by one.
	 * 1 pull from self-accuracy rate Minimum accuracy rate 5% Maximum accuracy rate 95%
	 */
	private boolean calcPcPcHit() {

        if (_pc.hasSkillEffect(L1SkillId.ABSOLUTE_BLADE)) {
            if (_target.hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER)) {
                int chance = _pc.getLevel() - 79;
                if (chance >= 10)
                    chance = 10;
                if (chance >= _random.nextInt(100) + 1) {
                    _targetPc.removeSkillEffect(L1SkillId.ABSOLUTE_BARRIER);
                    _targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 14539));
                    _targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14539));
                }
            }
        }

		_hitRate = (_pc.getLevel() / 3);
		/** battle zone **/
		if (_calcType == PC_PC) {
			if (_pc.getMapId() == 5153) {
				if (_pc.get_DuelLine() == _targetPc.get_DuelLine()) {
					return false;
				}
			}
		}
		if (_weaponId == 450044 || _weaponId == 450045 || _weaponId == 450046 || _weaponId == 450047 || _weaponId == 450048 || _weaponId == 450049
				|| _weaponId == 450050 || _weaponId == 450051) { // Roaring Two Swords
			_hitRate -= 500;
		}
		if (_weaponType2 != 20 && _weaponType2 != 62) {
			_hitRate += CalcStat.calcMeleeHitFromSTR(_pc.getAbility().getTotalStr()) * 2;
			_hitRate += (_weaponAddHit * 3) + _pc.getHitup() + (_pc.getHitupByArmor() * 2) + (_weaponEnchant / 2);

		} else {
			_hitRate += CalcStat.calcRangeHitUp(_pc.getAbility().getTotalDex()) * 2;
			_hitRate += (_weaponAddHit * 3) + _pc.getBowHitup() + (_pc.getBowHitupByArmor() * 2) + _pc.getBowHitupByDoll() + (_weaponEnchant / 2)
					- (_targetPc.get_PlusEr());
		}

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 10;

		int defenderDice = 0;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 50;
		}

		int defenderValue = (int) ((_targetPc.getAC().getAc() * 3.2) + (_hitRate * 1.8)) * -1;
		int levelDmg = (_pc.getLevel()) / 12;
		if (levelDmg <= 0)
			levelDmg = 0;

		defenderValue += levelDmg;

		if (_targetPc.getAC().getAc() >= 0)
			defenderDice = 10 - _targetPc.getAC().getAc();
		else if (_targetPc.getAC().getAc() < 0) {
			defenderDice = defenderValue;
			// defenderDice = 10 + _random.nextInt(defenderValue) + 1;
			int ac = _targetPc.getAC().getAc();
			if (ac <= -170)
				defenderDice += Config.AC_170;
			else if (ac <= -160)
				defenderDice += Config.AC_160;
			else if (ac <= -150)
				defenderDice += Config.AC_150;
			else if (ac <= -140)
				defenderDice += Config.AC_140;
			else if (ac <= -130)
				defenderDice += Config.AC_130;
			else if (ac <= -120)
				defenderDice += Config.AC_120;
			else if (ac <= -110)
				defenderDice += Config.AC_110;
			else if (ac <= -100)
				defenderDice += Config.AC_100;
			else if (ac <= -90)
				defenderDice += Config.AC_90;
			else if (ac <= -80)
				defenderDice += Config.AC_80;
			else if (ac <= -70)
				defenderDice += Config.AC_70;
			else if (ac <= -60)
				defenderDice += Config.AC_60;
			else if (ac <= -50)
				defenderDice += Config.AC_50;
			else if (ac <= -40)
				defenderDice += Config.AC_40;
			else if (ac <= -30)
				defenderDice += Config.AC_30;
			else if (ac <= -20)
				defenderDice += Config.AC_20;
			else if (ac <= -10)
				defenderDice += Config.AC_10;
		}

		if (_weaponType2 == 20 || _weaponType2 == 62) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(STRIKER_GALE)) {
				defenderDice += (_targetPc.getAC().getAc() * -1); // original 10
			} else {
				if (_targetPc.isDragonknight() || _targetPc.isIllusionist()) {
					defenderDice += (_targetPc.get_PlusEr() * (Config.활명중)) + (_targetPc.getAC().getAc() * -2); // 원래 10
				} else {
					defenderDice += (_targetPc.get_PlusEr() * (Config.활명중)) + (_targetPc.getAC().getAc() * -2.5); // 원래 10
				}
			}
		}

		// _pc.sendPackets(new
		// S_SystemMessage("pc_pc 명중 포인트: "+attackerDice+" 방어포인트 : "+defenderDice));

		double absoluteMissProbability = 2;

		double FinalAttackSuccessRate = 0;

		/*
		 * if (attackerDice > defenderDice){
		 *
		 *
		 *
		 * }else
		 */if (attackerDice <= defenderDice) {
			double temp = ((defenderDice - attackerDice) * 0.1);

			if (temp < 1) {
				temp = 1;
			}

			absoluteMissProbability += temp;
			// _pc.sendPackets(new
			// S_SystemMessage("[방어포인트 높음] 방어포인트미스확률 : "+temp+"%"));
			/*
			 * if(_random.nextInt(100) >= temp){ _pc.sendPackets(new
			 * S_SystemMessage("[방어포인트 높음] 공격 성공 확률 : "+(100-temp)+"% (성공함)")); 최종공격성공확률 =
			 * 100-절대미스확률; }else{ _pc.sendPackets(new
			 * S_SystemMessage("[방어포인트 높음] 공격 미스 확률 : "+temp+"% (미스남)")); _hitRate = 0; }
			 */
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MIRROR_IMAGE)) {
			if (_targetPc.getAC().getAc() >= -69) {
				absoluteMissProbability += 7;
			} else if (_targetPc.getAC().getAc() >= -79) {
				absoluteMissProbability += 11;
			} else if (_targetPc.getAC().getAc() >= -89) {
				absoluteMissProbability += 14;
			} else if (_targetPc.getAC().getAc() >= -99) {
				absoluteMissProbability += 15;
			} else if (_targetPc.getAC().getAc() <= -100) {
				absoluteMissProbability += 16;
			} else if (_targetPc.getAC().getAc() <= -110) {
				absoluteMissProbability += 17;
			} else if (_targetPc.getAC().getAc() <= -120) {
				absoluteMissProbability += 18;
			} else if (_targetPc.getAC().getAc() <= -130) {
				absoluteMissProbability += 19;
			} else if (_targetPc.getAC().getAc() <= -140) {
				absoluteMissProbability += 20;
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(AWAKEN_LINDVIOR)) {
			if (_targetPc.getAC().getAc() >= -69) {
				absoluteMissProbability += 1;
			} else if (_targetPc.getAC().getAc() >= -79) {
				absoluteMissProbability += 2;
			} else if (_targetPc.getAC().getAc() >= -89) {
				absoluteMissProbability += 2;
			} else if (_targetPc.getAC().getAc() >= -99) {
				absoluteMissProbability += 3;
			} else if (_targetPc.getAC().getAc() <= -100) {
				absoluteMissProbability += 4;
			} else if (_targetPc.getAC().getAc() <= -110) {
				absoluteMissProbability += 5;
			} else if (_targetPc.getAC().getAc() <= -120) {
				absoluteMissProbability += 6;
			} else if (_targetPc.getAC().getAc() <= -130) {
				absoluteMissProbability += 7;
			} else if (_targetPc.getAC().getAc() <= -140) {
				absoluteMissProbability += 8;
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
			if (_targetPc.getAC().getAc() >= -69) {
				absoluteMissProbability += 6;
			} else if (_targetPc.getAC().getAc() >= -79) {
				absoluteMissProbability += 8;
			} else if (_targetPc.getAC().getAc() >= -89) {
				absoluteMissProbability += 10;
			} else if (_targetPc.getAC().getAc() >= -99) {
				absoluteMissProbability += 12;
			} else if (_targetPc.getAC().getAc() <= -100) {
				absoluteMissProbability += 14;
			} else if (_targetPc.getAC().getAc() <= -110) {
				absoluteMissProbability += 15;
			} else if (_targetPc.getAC().getAc() <= -120) {
				absoluteMissProbability += 16;
			} else if (_targetPc.getAC().getAc() <= -130) {
				absoluteMissProbability += 17;
			} else if (_targetPc.getAC().getAc() <= -140) {
				absoluteMissProbability += 18;
			}
		}

		if (_targetPc.survive) {
			int AcDg = ((_targetPc.getAC().getAc() * -1) - 100) / 10;
			if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 1 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 2) {
				absoluteMissProbability += 2;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 3 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 4) {
				absoluteMissProbability += 4;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 5 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 6) {
				absoluteMissProbability += 6;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 7 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 8) {
				absoluteMissProbability += 8;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 9 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 10) {
				absoluteMissProbability += 10;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 11 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 12) {
				absoluteMissProbability += 11;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 13 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 14) {
				absoluteMissProbability += 12;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 15 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 16) {
				absoluteMissProbability += 13;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 17 && _targetPc.getDg() + AcDg + _targetPc.getINFIDg() <= 18) {
				absoluteMissProbability += 14;
			} else if (_targetPc.getDg() + AcDg + _targetPc.getINFIDg() >= 19) {
				absoluteMissProbability += 14;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ANTA_MAAN) // 지룡의 마안 - 물리 일정확률 회피
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BIRTH_MAAN) // 탄생의 마안 - 물리 일정확률 회피
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHAPE_MAAN) // 형상의 마안 - 물리 일정확률 회피
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.LIFE_MAAN)) { // 생명의 마안 - 물리 일정확률 회피
			int MaanHitRnd = _random.nextInt(100) + 1;
			if (MaanHitRnd <= 10) { // 확률
				absoluteMissProbability += 10;
			}
		}

		FinalAttackSuccessRate = 100 - absoluteMissProbability;

		// _pc.sendPackets(new S_SystemMessage("최종 공격 성공 확률 : "+최종공격성공확률+"%"));

		if (_random.nextInt(100) + 1 < absoluteMissProbability) {
			FinalAttackSuccessRate = 0;
			// _pc.sendPackets(new
			// S_SystemMessage("[공격 실패] 닷지+절대미스확률+방어포인트미스확률 : "+절대미스확률+"% (미스남)"));
		}

		if (FinalAttackSuccessRate > 0) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) {
				FinalAttackSuccessRate = 0;
			}
		}

		if (FinalAttackSuccessRate > 0 && (_weaponType2 == 20 || _weaponType2 == 62)) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MOBIUS)) {
				FinalAttackSuccessRate = 0;
			}
		}

		int rnd = _random.nextInt(100) + 1;

		/** ranged bug defense */

		int _jX = _pc.getX() - _targetPc.getX();
		int _jY = _pc.getY() - _targetPc.getY();
		if (!(_pc instanceof L1RobotInstance)) {
			if (_weaponType2 == 24) { // when spear
				if ((_jX > 3 || _jX < -3) && (_jY > 3 || _jY < -3)) {
					FinalAttackSuccessRate = 0;
				}
			} else if (_weaponType2 == 20 || _weaponType2 == 62) { // when it's a bow
				if ((_jX > 15 || _jX < -15) && (_jY > 15 || _jY < -15)) {
					FinalAttackSuccessRate = 0;
				}
			} else {
				if ((_jX > 2 || _jX < -2) && (_jY > 2 || _jY < -2)) {
					FinalAttackSuccessRate = 0;
				}
			}
		}
		/** ranged bug defense */

		return FinalAttackSuccessRate >= rnd;
	}

	// ●●●● Determination of Hits from Players to NPCs ●●●●
	private boolean calcPcNpcHit() {
		try {

			// NPC에의 명중율
			// =(PC의 Lv＋클래스 보정＋STR 보정＋DEX 보정＋무기 보정＋DAI의 매수/2＋마법 보정)×5－{NPC의
			// AC×(-5)}
			_hitRate = (_pc.getLevel() / 3);

			/*
			 * if (_weaponType != 20 && _weaponType != 62) { _hitRate +=
			 * CalcStat.근거리명중(_pc.getAbility().getTotalStr())*2; _hitRate +=
			 * (_weaponAddHit*3) + _pc.getHitup() + (_pc.getHitupByArmor()*2) +
			 * (_weaponEnchant/2);
			 *
			 * } else { _hitRate += CalcStat.원거리명중(_pc.getAbility().getTotalDex())*2;
			 * _hitRate += (_weaponAddHit*3) + _pc.getBowHitup() +
			 * (_pc.getBowHitupByArmor()*2) + _pc.getBowHitupByDoll() + (_weaponEnchant/2);
			 * }
			 *
			 * if(_pc.isWarrior()){ _hitRate += 10; }
			 */

			if (_weaponType2 != 20 && _weaponType2 != 62) {
				_hitRate += CalcStat.calcMeleeHitFromSTR(_pc.getAbility().getTotalStr()) * 2;
				_hitRate += (_weaponAddHit * 3) + _pc.getHitup() + (_pc.getHitupByArmor() * 2) + (_weaponEnchant / 2);
			} else {
				_hitRate += CalcStat.calcRangeHitUp(_pc.getAbility().getTotalDex()) * 2;
				_hitRate += (_weaponAddHit * 3) + _pc.getBowHitup() + (_pc.getBowHitupByArmor() * 2) + _pc.getBowHitupByDoll() + (_weaponEnchant / 2);
			}

			int attackerDice = _random.nextInt(20) + 1 + _hitRate - 10;

			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(FEAR))
				attackerDice += 5;

			int defenderDice = 0;

			int defenderValue = (int) ((_targetNpc.getAC().getAc() * 3.2) + (_hitRate * 2)) * -1;

			if (_targetNpc.getAC().getAc() >= 0)
				defenderDice = 10 - _targetNpc.getAC().getAc();
			else if (_targetNpc.getAC().getAc() < 0) {
				defenderDice = defenderValue;
				// defenderDice = 10 + _random.nextInt(defenderValue) + 1;
				int ac = _targetNpc.getAC().getAc();
				if (ac <= -170)
					defenderDice += Config.AC_170;
				else if (ac <= -160)
					defenderDice += Config.AC_160;
				else if (ac <= -150)
					defenderDice += Config.AC_150;
				else if (ac <= -140)
					defenderDice += Config.AC_140;
				else if (ac <= -130)
					defenderDice += Config.AC_130;
				else if (ac <= -120)
					defenderDice += Config.AC_120;
				else if (ac <= -110)
					defenderDice += Config.AC_110;
				else if (ac <= -100)
					defenderDice += Config.AC_100;
				else if (ac <= -90)
					defenderDice += Config.AC_90;
				else if (ac <= -80)
					defenderDice += Config.AC_80;
				else if (ac <= -70)
					defenderDice += Config.AC_70;
				else if (ac <= -60)
					defenderDice += Config.AC_60;
				else if (ac <= -50)
					defenderDice += Config.AC_50;
				else if (ac <= -40)
					defenderDice += Config.AC_40;
				else if (ac <= -30)
					defenderDice += Config.AC_30;
				else if (ac <= -20)
					defenderDice += Config.AC_20;
				else if (ac <= -10)
					defenderDice += Config.AC_10;
			}

			// _pc.sendPackets(new
			// S_SystemMessage("pc_npc 명중 포인트: "+attackerDice+" 방어포인트 : "+defenderDice));

			double absoluteMissProbability = 2;
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(MIRROR_IMAGE)) {
				if (_targetNpc.getAC().getAc() >= -69) {
					absoluteMissProbability += 7;
				} else if (_targetPc.getAC().getAc() >= -79) {
					absoluteMissProbability += 11;
				} else if (_targetPc.getAC().getAc() >= -89) {
					absoluteMissProbability += 14;
				} else if (_targetPc.getAC().getAc() >= -99) {
					absoluteMissProbability += 15;
				} else if (_targetPc.getAC().getAc() <= -100) {
					absoluteMissProbability += 16;
				} else if (_targetPc.getAC().getAc() <= -110) {
					absoluteMissProbability += 17;
				} else if (_targetPc.getAC().getAc() <= -120) {
					absoluteMissProbability += 18;
				} else if (_targetPc.getAC().getAc() <= -130) {
					absoluteMissProbability += 19;
				} else if (_targetPc.getAC().getAc() <= -140) {
					absoluteMissProbability += 20;
				}
			}
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
				if (_targetNpc.getAC().getAc() >= -69) {
					absoluteMissProbability += 6;
				} else if (_targetPc.getAC().getAc() >= -79) {
					absoluteMissProbability += 8;
				} else if (_targetPc.getAC().getAc() >= -89) {
					absoluteMissProbability += 10;
				} else if (_targetPc.getAC().getAc() >= -99) {
					absoluteMissProbability += 12;
				} else if (_targetPc.getAC().getAc() <= -100) {
					absoluteMissProbability += 14;
				} else if (_targetPc.getAC().getAc() <= -110) {
					absoluteMissProbability += 15;
				} else if (_targetPc.getAC().getAc() <= -120) {
					absoluteMissProbability += 16;
				} else if (_targetPc.getAC().getAc() <= -130) {
					absoluteMissProbability += 17;
				} else if (_targetPc.getAC().getAc() <= -140) {
					absoluteMissProbability += 18;
				}
			}

			double finalAttackSuccessRate = 0;

			/*
			 * if (attackerDice > defenderDice){
			 *
			 *
			 *
			 * }else
			 */if (attackerDice <= defenderDice) {
				double temp = ((defenderDice - attackerDice) * 0.1);

				if (temp < 1) {
					temp = 1;
				}

				absoluteMissProbability += temp;
				// _pc.sendPackets(new
				// S_SystemMessage("[방어포인트 높음] 방어포인트미스확률 : "+temp+"%"));
				/*
				 * if(_random.nextInt(100) >= temp){ _pc.sendPackets(new S_SystemMessage
				 * ("[방어포인트 높음] 공격 성공 확률 : "+(100-temp)+"% (성공함)")); 최종공격성공확률 = 100-절대미스확률;
				 * }else{ _pc.sendPackets(new
				 * S_SystemMessage("[방어포인트 높음] 공격 미스 확률 : "+temp+"% (미스남)")); _hitRate = 0; }
				 */
			}

			finalAttackSuccessRate = 100 - absoluteMissProbability;

			// _pc.sendPackets(new
			// S_SystemMessage("최종 공격 성공 확률 : "+최종공격성공확률+"%"));

			if (_random.nextInt(100) + 1 < absoluteMissProbability) {
				finalAttackSuccessRate = 0;
				// _pc.sendPackets(new
				// S_SystemMessage("[공격 실패] 닷지+최소미스확률+방어포인트미스확률 : "+절대미스확률+"% (미스남)"));
			}

			/*
			 * int fumble = _hitRate - 9; int critical = _hitRate + 10;
			 *
			 *
			 * if (attackerDice <= fumble) { _pc.sendPackets(new
			 * S_SystemMessage("랜덤 확률 로 인한 100% 미스!")); _hitRate = 0; } else if
			 * (attackerDice >= critical) { _pc.sendPackets(new
			 * S_SystemMessage("랜덤 확률 로 인한 100% 명중!")); _hitRate = 100; } else { if
			 * (attackerDice > defenderDice){ _pc.sendPackets(new
			 * S_SystemMessage("명중 계산값 > 방어계산값 = 100% 공격성공")); _hitRate = 100; }else if
			 * (attackerDice <= defenderDice){ _pc.sendPackets(new
			 * S_SystemMessage("명중 계산값 < 방어계산값 = 100% 공격미스")); _hitRate = 0; } }
			 */

			if (finalAttackSuccessRate > 0) {
				int npcId = _targetNpc.getNpcTemplate().get_npcId();
				if (npcId >= 45912 && npcId <= 45915 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER)) {
					finalAttackSuccessRate = 0;
				} else if (npcId >= 145685 && npcId <= 145686 && !_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HALPAS_FOLLOWER_BUFF)) {
					finalAttackSuccessRate = 0;
				} else if (npcId == 45916 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
					finalAttackSuccessRate = 0;
				} else if (npcId == 45941 && !_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
					finalAttackSuccessRate = 0;
				} else if (!_pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_BARLOG) && (npcId == 445752 || npcId == 445753// 발록버프 해제
						|| npcId == 7000026 || npcId == 7000027 || npcId == 7000012 || npcId == 7000019 || npcId == 7000006 || npcId == 7000013
						|| npcId == 7000020 || npcId == 7000007 || npcId == 7000014 || npcId == 7000021 || npcId == 7000030 || npcId == 7000031
						|| npcId == 7000032 || npcId == 7000033 || npcId == 7000034 || npcId == 7000035 || npcId == 7000036 || npcId == 7000037
						|| npcId == 7000038 || npcId == 7000039 || npcId == 7000040 || npcId == 7000041 || npcId == 7000008 || npcId == 7000015
						|| npcId == 7000022 || npcId == 7000009 || npcId == 7000016 || npcId == 7000023 || npcId == 7000010 || npcId == 7000017
						|| npcId == 7000024 || npcId == 7000028 || npcId == 7000029 || npcId == 7000030 || npcId == 7000011 || npcId == 7000018
						|| npcId == 7000025 || npcId == 7000042)) {// 발록
					finalAttackSuccessRate = 0;
				} else if (!_pc.getSkillEffectTimerSet().hasSkillEffect( // 야히 버프없이 때리게 가능
						STATUS_CURSE_YAHEE) && (npcId == 45675111 || npcId == 81082111 || npcId == 45625111 || npcId == 45674111 || npcId == 100570111)) {
					finalAttackSuccessRate = 0;
				} else if (npcId >= 46068 && npcId <= 46091 && _pc.getGfxId().getTempCharGfx() == 6035) {
					finalAttackSuccessRate = 0;
				} else if (npcId >= 46092 && npcId <= 46106 && _pc.getGfxId().getTempCharGfx() == 6034) {
					finalAttackSuccessRate = 0;
				}

				/** 원거리버그 방어 */
				int _jX = _pc.getX() - _targetNpc.getX();
				int _jY = _pc.getY() - _targetNpc.getY();
				if (!(_pc instanceof L1RobotInstance)) {
					if (_weaponType2 == 24) { // 창일때
						if ((_jX > 3 || _jX < -3) && (_jY > 3 || _jY < -3)) {
							finalAttackSuccessRate = 0;
						}
					} else if (_weaponType2 == 20 || _weaponType2 == 62) { // 활일때
						if ((_jX > 20 || _jX < -20) && (_jY > 20 || _jY < -20)) {
							finalAttackSuccessRate = 0;
						}
					} else {
						if ((_jX > 2 || _jX < -2) && (_jY > 2 || _jY < -2)) {
							finalAttackSuccessRate = 0;
						}
					}
				}
			}
			/** ranged bug defense */
			int rnd = _random.nextInt(100) + 1;

			return finalAttackSuccessRate >= rnd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Hit rate from NPC to PC
	private boolean calcNpcPcHit() {
		_hitRate += _npc.getLevel() + _npc.getHitup();

		int attackerValue = 0;
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;

			_hitRate = Pet.getLevel();
			_hitRate += Pet.SkillsTable(L1PetInstance.천리안);

			int PetLevel = Pet.getLevel() + 10;
			attackerValue = _Random.nextInt(PetLevel / 4);
			int Bonus = Pet.getHunt() + Pet.getElixirHunt();

			if (Bonus >= 20)
				attackerValue += (Bonus - 10) / 10;
		} else {
			int Level = _npc.getLevel() + 10;
			attackerValue = _Random.nextInt(Level / 4) + 1;
		}

		int attackerDice = attackerValue + _hitRate;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
			if (_targetPc.getAC().getAc() >= -69) {
				attackerDice -= 6;
			} else if (_targetPc.getAC().getAc() >= -79) {
				attackerDice -= 8;
			} else if (_targetPc.getAC().getAc() >= -89) {
				attackerDice -= 10;
			} else if (_targetPc.getAC().getAc() >= -99) {
				attackerDice -= 12;
			} else if (_targetPc.getAC().getAc() < -100) {
				attackerDice -= 14;
			} else if (_targetPc.getAC().getAc() < -110) {
				attackerDice -= 15;
			} else if (_targetPc.getAC().getAc() < -120) {
				attackerDice -= 16;
			} else if (_targetPc.getAC().getAc() < -130) {
				attackerDice -= 17;
			} else if (_targetPc.getAC().getAc() < -140) {
				attackerDice -= 18;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MIRROR_IMAGE)) {
			if (_targetPc.getAC().getAc() >= -69) {
				attackerDice -= 6;
			} else if (_targetPc.getAC().getAc() >= -79) {
				attackerDice -= 8;
			} else if (_targetPc.getAC().getAc() >= -89) {
				attackerDice -= 10;
			} else if (_targetPc.getAC().getAc() >= -99) {
				attackerDice -= 12;
			} else if (_targetPc.getAC().getAc() < -100) {
				attackerDice -= 14;
			} else if (_targetPc.getAC().getAc() < -110) {
				attackerDice -= 15;
			} else if (_targetPc.getAC().getAc() < -120) {
				attackerDice -= 16;
			} else if (_targetPc.getAC().getAc() < -130) {
				attackerDice -= 17;
			} else if (_targetPc.getAC().getAc() < -140) {
				attackerDice -= 18;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ANTA_MAAN) // 지룡의 마안 - 물리 일정확률 회피
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BIRTH_MAAN) // 탄생의 마안 - 물리 일정확률 회피
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHAPE_MAAN) // 형상의 마안 - 물리 일정확률 회피
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.LIFE_MAAN)) { // 생명의 마안 - 물리 일정확률 회피
			int MaanHitRnd = _random.nextInt(100) + 1;
			if (MaanHitRnd <= 3) { // 확률
				_hitRate = 0;
			}
		}
		if (_pc != null) {
			if (_pc.getInventory().checkEquipped(420112)
					|| _pc.getInventory().checkEquipped(420113)
					|| _pc.getInventory().checkEquipped(420114)
					|| _pc.getInventory().checkEquipped(420115)) { // Valakas Armours
				int chance = _random.nextInt(100) + 1;
				if (chance <= 5) {
					// weaponDamage = 10;
					// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
					// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
				}
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 5;
		}
		int defenderDice = 0;

		int defenderValue = (_targetPc.getAC().getAc()) * -1;

		if (_targetPc.getAC().getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAC().getAc();
		} else if (_targetPc.getAC().getAc() < 0) {
			defenderDice = 10 + _random.nextInt(defenderValue) + 1;
		}
		int fumble = _hitRate;
		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			} else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) {
			_hitRate = 0;
		} else if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MOBIUS) && _npc.getNpcTemplate().getBowActId() == 66) {
			_hitRate = 0;
		} else if (_npc.getNpcId() >= 100584 && _npc.getNpcId() <= 100589) {
			_hitRate = 100;
			return true;
		}

        /** close range avoidance **/
		if (_npc.getNpcTemplate().get_ranged() <= 2 && _npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) <= 2) {

			/** Short range avoidance according to DG value **/
		    if (_targetPc.getDg() > 0){
			    int chance = _random.nextInt(120) + 1;
		        int dg = _targetPc.getDg();
		        if (chance <= dg) {
		        	return false;
		        }
		    } else if (_targetPc.getDg() < 0){ //if negative
		    	_hitRate += _targetPc.getDg() * -1;
		    }
		}

		int rnd = _random.nextInt(100) + 1;
		// If the NPC's attack range is 10 or more, if it is 2 or more away, it is considered a bow attack
		if (_npc.getNpcTemplate().get_ranged() >= 10 && _hitRate > rnd && _npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) >= 3) {
			return calcErEvasion();
		}
		if (_hitRate <= 100) {
			if (_npc.getMapId() >= 550 && _npc.getMapId() <= 558) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 90 ? 100 : 0;
			} else if (_npc.getNpcId() == 100081) {
				_hitRate *= 0.75;
			} else if (_npc.getNpcId() >= 100055 && _npc.getNpcId() <= 100058) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getNpcId() >= 100059 && _npc.getNpcId() <= 100060) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getNpcId() == 45295 || _npc.getNpcId() == 45318 || _npc.getNpcId() == 45337 || _npc.getNpcId() == 45351) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 90 ? 100 : 0;
			} else if (_npc.getMapId() >= 307 && _npc.getMapId() <= 309) { // 침공로
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 90 ? 100 : 0;
			} else if (_npc.getMapId() >= 59 && _npc.getMapId() <= 63) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getMapId() == 440) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getMapId() == 4 && ((_npc.getX() >= 33472 && _npc.getX() <= 33856 && _npc.getY() >= 32191 && _npc.getY() <= 32511)
					|| (_npc.getX() >= 32511 && _npc.getX() <= 32960 && _npc.getY() >= 32191 && _npc.getY() <= 32537))) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getMapId() >= 30 && _npc.getMapId() <= 36) {
				if (_npc.getNpcTemplate().get_undead() == 1)
					_hitRate *= 0.85;
				else
					_hitRate *= 0.95;
			} else if (_npc.getMapId() >= 1 && _npc.getMapId() <= 2) {
				if (_npc.getNpcTemplate().get_undead() == 1)
					_hitRate *= 0.80;
			} else {
				_hitRate *= 0.80;
			}
		}
		return _hitRate >= rnd;
	}

	// ●●●● Hit rate from NPC to NPC ●●●●
	private boolean calcNpcNpcHit() {
		int target_ac = 10 - _targetNpc.getAC().getAc();
		int attacker_lvl = _npc.getNpcTemplate().get_level();

		/** Calculation including pet-related siege skills */
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;
			int PetLevel = Pet.getLevel() / 2 + 10;
			attacker_lvl = attacker_lvl + (PetLevel + _Random.nextInt(PetLevel / 4));
			attacker_lvl += Pet.SkillsTable(L1PetInstance.천리안);
			int Bonus = Pet.getHunt() + Pet.getElixirHunt();
			if (Bonus >= 20)
				attacker_lvl += (Bonus - 10) / 10;
		}

		if (target_ac != 0) {
			_hitRate = (100 / target_ac * attacker_lvl); // 피공격자 AC = 공격자 Lv
			// 100% hit rate
		} else {
			_hitRate = 100 / 1 * attacker_lvl;
		}

		if (_hitRate < attacker_lvl) {
			_hitRate = attacker_lvl; // Minimum Accuracy Rate=Lv%
		}
		if (_hitRate > 95) {
			_hitRate = 95; // highest hit rate 95%
		}
		if (_hitRate < 5) {
			_hitRate = 5; // lowest hit rate 5%
		}

		int rnd = _random.nextInt(100) + 1;
		return _hitRate >= rnd;
	}

	// ●●●● Evasion rate by ER ●●●●
	private boolean calcErEvasion() {
		int er = _targetPc.get_PlusEr();

		int rnd = _random.nextInt(130) + 1;
		return er < rnd;
	}

	/* ■■■■■■■■■■■■■■■ damage calculation ■■■■■■■■■■■■■■■ */
	public int calcDamage(int adddmg) {
		switch (_calcType) {
		case PC_PC:
			_damage = calcPcPcDamage(adddmg);
			break;
		case PC_NPC:
			_damage = calcPcNpcDamage(adddmg);
			break;
		case NPC_PC:
			_damage = calcNpcPcDamage();
			break;
		case NPC_NPC:
			_damage = calcNpcNpcDamage();
			break;
		default:
			break;
		}
		return _damage;
	}

	public int calcDamage() {
		switch (_calcType) {
		case PC_PC:
			_damage = calcPcPcDamage(0);
			break;
		case PC_NPC:
			_damage = calcPcNpcDamage(0);
			break;
		case NPC_PC:
			_damage = calcNpcPcDamage();
			break;
		case NPC_NPC:
			_damage = calcNpcNpcDamage();
			break;
		default:
			break;
		}
		return _damage;
	}

	boolean burning = false;
	boolean double_burning = false;

	// ●●●● Calculate damage from player to player ●●●●
	public int calcPcPcDamage(int adddmg) {
		if (_pc instanceof L1RobotInstance) {
			double dmg = 0;
			if (_pc.getCurrentWeapon() == 20) { // 활
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_ELFBRAVE)) { // 활
					int rnd = _random.nextInt(100);
					if (rnd < 9) {
						_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // 방향세트
						_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
						Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));// 새로만드셔서 ㅎ
					}
					if (Config.BOTFAIRYCYCLONE) {
						if ((_random.nextInt(100) + 1) <= 5) {
							_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 17557), true);
							Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 17557), true);
							dmg *= 1.4;
						}
					}
					if (Config.BOTFAIRYMOVEMENT) {
						dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					}
					dmg += _random.nextInt(Config.요정활랜덤타격치PC) + Config.요정활기본타격치PC;
				} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FOCUS_WAVE)) { // 활
					int rnd = _random.nextInt(100);
					if (rnd < 9) {
						_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // 방향세트
						_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
						Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));// 새로만드셔서 ㅎ
					}
					if (_pc.getSkillEffectTimerSet().hasSkillEffect(FOCUS_WAVE)) {
						if (_random.nextInt(100) >= (90 + _pc.getFocusWave() * 4)) {
							_pc.set포커스웨이브(_pc.getFocusWave() + 1);
							switch (_pc.getFocusWave()) {
							case 1:
								_pc.sendPackets(new S_SkillBrave(_pc.getId(), 11, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 11, 0), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16532), true);
								break;
							case 2:
								_pc.sendPackets(new S_SkillBrave(_pc.getId(), 12, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 12, 0), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16533), true);
								break;
							case 3:
								_pc.set포커스웨이브(0);
								_pc.sendPackets(new S_SkillBrave(_pc.getId(), 10, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 10, 0), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16540), true);
								break;
							}
						}
					}
					if (Config.BOTFAIRYMOVEMENT) {
						dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					}
					dmg += _random.nextInt(Config.요정활랜덤타격치PC) + Config.요정활기본타격치PC;
				} else {
					int rnd = _random.nextInt(100);
					if (rnd < 9) {
						_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // 방향세트
						_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
						Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));// 새로만드셔서 ㅎ
					}
					if (Config.BOTFAIRYMOVEMENT) {
						dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					}
					dmg += _random.nextInt(Config.요정활랜덤타격치PC) + Config.요정활기본타격치PC;
				}
			} else if (_pc.getCurrentWeapon() == 54) { // 이도류
				int rnd = _random.nextInt(100);
				if (rnd < 38) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 54));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 54));
					dmg *= 2;
				}
				if ((_random.nextInt(100) + 1) <= 33) {
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 6532), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 6532), true);
					dmg *= 1.5;
				}
				dmg += _random.nextInt(Config.다엘랜덤타격치PC) + Config.다엘기본타격치PC;
			} else if (_pc.getCurrentWeapon() == 50) { // 이도류
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 50));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 50));
				}
				dmg += _random.nextInt(Config.기사랜덤타격치PC) + Config.기사기본타격치PC;
			} else if (_pc.getCurrentWeapon() == 4) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 4));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 4));
				}
				if ((_random.nextInt(100) + 1) <= 33) {
					dmg *= 1.5;
					burning = true;
				}
				dmg += WeaponSkill.getbotKurtSwordDamage(_pc, _target, _weaponEnchant);
				dmg += _random.nextInt(Config.군검요랜덤타격치PC) + Config.군검요기본타격치PC;
			} else if (_pc.getCurrentWeapon() == 88) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 11));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 11));
				}
				int rnd2 = _random.nextInt(100);
				int rnd3 = _random.nextInt(100);
				if (rnd2 < 10) {
					int gfx = 12487;
					if (rnd3 < 10) {
						gfx = 12489;
					}
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), gfx));// 12489
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), gfx));
				}
				if (Config.BOTWARRIORSTORM) {
					dmg += WeaponSkill.getTyphoonDmg(this._pc, this._target);
				}
				dmg += _random.nextInt(Config.전사랜덤타격치PC) + Config.전사기본타격치PC;
			} else if (_pc.getCurrentWeapon() == 24) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 24));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 24));
				}
				dmg += _random.nextInt(Config.용기사랜덤타격치PC) + Config.용기사기본타격치PC;
			} else if (_pc.getCurrentWeapon() == 40) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 40));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 40));
				}
				dmg += WeaponSkill.botgetZerosDamagePc(_pc, _target, _weaponEnchant);
				dmg += _random.nextInt(30) + 10;
			} else if (_pc.getCurrentWeapon() == 58) { // 환술사
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 91));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 91));
				} else {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 89));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 89));
				}
				if (_pc.getGfxId().getTempCharGfx() == Config.로봇칼1) {
					dmg += WeaponSkill.boksalkiPc(_pc, _target, _weaponEnchant);
				}
				if (_pc.getGfxId().getTempCharGfx() == Config.로봇칼2 || _pc.getGfxId().getTempCharGfx() == Config.로봇칼3) {
					dmg += WeaponSkill.botIcekiringpc(_pc, _target, _weaponEnchant);
				}
				dmg += _random.nextInt(50) + 10;
			} else {
				dmg += _random.nextInt(30) + 70;
			}
			return (int) dmg;
		}
		int weaponMaxDamage = 0;

		weaponMaxDamage = _weaponSmall + _weaponAddDmg;
		boolean secondw = false;
		if (_pc.isWarrior() && _pc.isSlayer && _pc.getSecondWeapon() != null) {
			int ran = _random.nextInt(100);
			if (ran < 50) {
				secondw = true;
				weaponMaxDamage = _SweaponSmall + _SweaponAddDmg;
			}
		}
		/*
		 * double warr = (_weaponSmall + _weaponAddDmg + _SweaponSmall + _SweaponAddDmg)
		 * * 0.7; weaponMaxDamage = (int)warr;
		 */
		// }else{

		// }

		int weaponDamage = 0;

		int doubleChance = _random.nextInt(120) + 1;

		if (_target != null) {
			int chance1 = _random.nextInt(100);
			for (L1ItemInstance item : _targetPc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() >= 420104 && item.getItemId() <= 420107) {
						int addchan = item.getEnchantLevel() - 5;
						if (addchan < 0)
							addchan = 0;
						if (chance1 < /* 6+addchan */5) {
							// 123456 일때 80~100
							// 파푸 가호 7일때 120~140 / 8일때 140~160 9일때 160~180
							int addhp = _random.nextInt(20) + 1;
							int basehp = 80;
							if (item.getEnchantLevel() == 7)
								basehp = 120;
							if (item.getEnchantLevel() == 8)
								basehp = 140;
							if (item.getEnchantLevel() == 9)
								basehp = 160;
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + basehp + addhp);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2187), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 2187), true);
						}
						break;
					} else if (item.getItemId() >= 420108 && item.getItemId() <= 420111) {
						int addchan = item.getEnchantLevel() - 5;
						if (addchan < 0)
							addchan = 0;
						if (chance1 < 6 + addchan) {
							if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.린드가호딜레이)) {
								break;
							}
							_targetPc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.린드가호딜레이, 4000);

							if (item.getItemId() == 420108)// 완력
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 8 + _random.nextInt(7));
							else if (item.getItemId() == 420109)// 예지
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 9 + _random.nextInt(7));
							else if (item.getItemId() == 420110)// 인내
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 16 + _random.nextInt(9));
							else if (item.getItemId() == 420111)// 마력
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 20 + _random.nextInt(11));

							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2188), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 2188), true);
						}
						break;
					} else if (item.getItemId() == 21255) {
						if (chance1 < 4) {
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + 31);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2183), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 2183), true);
						}
						break;
					} else if (item.getItemId() == 222351) {
						if (chance1 < 3) {
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + 5);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 15355), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 15355), true);
						}
						break;
					}
				}
			}
		}
		// Papuga Lake

		int weaponTotalDamage = 0;
		double dmg = 0;

		if (_weaponId == 90083) { // Roaring Two Swords
			_weaponDoubleDmgChance += _weaponEnchant + 1;
		}

		if (_weaponType2 == 0) { // bear hands
			weaponDamage = 0;
		} else { // when not empty handed
			if (_weaponType2 == 58 && doubleChance <= _weaponDoubleDmgChance) { // claw
				// 더블
				weaponDamage = weaponMaxDamage;
				weaponDamage += weaponDamage * 0.2;
				_attackType = 2;
			} else {
				weaponDamage = _random.nextInt(weaponMaxDamage + 2) + 1;
			}

			if (weaponDamage > weaponMaxDamage) {
				_critical = true;
			}

			int critical = _random.nextInt(100) + 1;
			int criticalRate = 0;
			if (_weaponType2 == 20 || _weaponType2 == 62) {
				criticalRate = CalcStat.calcRangeCrit(_pc.getAbility().getTotalDex());
			} else {
				criticalRate = CalcStat.meleeCrit(_pc.getAbility().getTotalStr());
			}

			if (critical <= criticalRate) {
				_critical = true;
				weaponDamage = weaponMaxDamage;
			}

			if (_pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
				if (_weaponType2 != 20 && _weaponType2 != 62) {
					weaponDamage = weaponMaxDamage /* + 20 */;
					_critical = false;
				}
			}

			if (secondw) {
				weaponTotalDamage = weaponDamage + _SweaponEnchant;
			} else {
				weaponTotalDamage = weaponDamage + _weaponEnchant;
			}

			/*
			 * if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.블레이징스피릿츠)) { if
			 * (_weaponType == 54 ) { // 이도류 // 더블 weaponTotalDamage *= 2.4; if (_weaponId
			 * != 415011) { weaponTotalDamage *= 1.1; } _attackType = 4; _pc.sendPackets(new
			 * S_SkillSound(_targetPc.getId(), 14547) ,true); _pc.broadcastPacket(new
			 * S_SkillSound(_targetPc.getId(), 14547) ,true); } }else{ if (_weaponType == 54
			 * && doubleChance <= _weaponDoubleDmgChance) { // 이도류 // 더블 weaponTotalDamage
			 * *= 1.8; if (_weaponId != 415011) { weaponTotalDamage *= 1.1; } _attackType =
			 * 4; if (_pc.어쌔신 && _pc.블레이징) { int timeb = 3; if(_pc.getLevel() == 86) timeb
			 * +=1; if(_pc.getLevel() >= 87) timeb +=1;
			 * _pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.블레이징스피릿츠, timeb *
			 * 1000); _pc.sendPackets(new S_CreateItem(L1SkillId.블레이징스피릿츠, timeb));
			 * _pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14547));
			 * _pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14547)); } _pc.어쌔신 =
			 * false; _pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.어쌔신);
			 *
			 * } }
			 */
			if (_weaponType2 == 54 && _weaponEnchant <= 9) { // 이도류
				int rnd = _random.nextInt(100);
				if (rnd < 30) {
					weaponTotalDamage *= 2;
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 54));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 54));
				}
			}

			if (_weaponType2 == 54 && _weaponEnchant >= 10) { //dual wield
				int rnd = _random.nextInt(100);
				if (rnd < 30) {
					weaponTotalDamage *= 2;
					_pc.sendPackets(new S_AttackCritical(_pc, _targetPc.getId(), 54));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetPc.getId(), 54));
				}
			}
			if (_pc.isCrash) {
				int rnd = _random.nextInt(100);
				if (rnd < 3) {
					int crashdmg = _pc.getLevel() / 1;
					double purrydmg = crashdmg * 1;
					int plusdmg = crashdmg;
					int gfx = 12487;
					if (_pc.isFury) {
						int rnd2 = _random.nextInt(100);
						if (rnd2 < 3) {
							gfx = 12489;
							plusdmg = (int) purrydmg;
						}
					}
					weaponTotalDamage += plusdmg;
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), gfx));// 12489
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), gfx));
				}
			}
			/** 레이지 스킬 */
			if (_pc.rage) {
				int rnd = _random.nextInt(100);
				if (rnd < 33) {
					int ragedmg = _pc.getAbility().getTotalStr() * 2;
					int plusdmg = ragedmg;
					int gfx = 18517;
					weaponTotalDamage += plusdmg;
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), gfx));// 12489
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), gfx));
				}
			}
			/** 레이지 스킬 */

			/** 플레임 스킬 */
			if (_pc.flame) {
				int rnd = _random.nextInt(100);
				if (rnd < 7) {
					if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FLAME)) {
						if (_targetPc.플레임th != null) {
							_targetPc.플레임th.stop();
						}
					}
					L1플레임 FLAME = new L1플레임(_pc, _targetPc);
					FLAME.begin();
					_targetPc.플레임th = FLAME;
					_targetPc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.FLAME, 3 * 1000);
					_targetPc.sendPackets(new S_NewSkillIcons(L1SkillId.FLAME, true, -1));
				}
			}
			/** 플레임 스킬 */

			if (_pc.getSkillEffectTimerSet().hasSkillEffect(FOCUS_WAVE)) {
				if (_random.nextInt(100) >= (90 + _pc.getFocusWave() * 4)) {
					_pc.set포커스웨이브(_pc.getFocusWave() + 1);
					switch (_pc.getFocusWave()) {
					case 1:
						_pc.sendPackets(new S_SkillBrave(_pc.getId(), 11, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
						Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 11, 0), true);
						_pc.sendPackets(new S_SkillSound(_pc.getId(), 16532));
						break;
					case 2:
						_pc.sendPackets(new S_SkillBrave(_pc.getId(), 12, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
						Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 12, 0), true);
						_pc.sendPackets(new S_SkillSound(_pc.getId(), 16533));
						break;
					case 3:
						_pc.set포커스웨이브(0);
						_pc.sendPackets(new S_SkillBrave(_pc.getId(), 10, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
						Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 10, 0), true);
						_pc.sendPackets(new S_SkillSound(_pc.getId(), 16540));
						break;
					}
				}
			}
			/*
			 * double enchatDmgRate = 1; try { if (_weaponEnchant < 7){ }else if
			 * (_weaponEnchant == 7 && (_random.nextInt(100) + 1) <= Config.RATE_7_DMG_PER)
			 * { // 인첸트 7일때 더블데미지 5% enchatDmgRate = Config.RATE_7_DMG_RATE; }else if
			 * (_weaponEnchant == 8 && (_random.nextInt(100) + 1) <= Config.RATE_8_DMG_PER)
			 * { // 인첸트 8일때 더블데미지 10% enchatDmgRate = Config.RATE_8_DMG_RATE; }else if
			 * (_weaponEnchant == 9 && (_random.nextInt(100) + 1) <= Config.RATE_9_DMG_PER)
			 * { // 인첸트 9일때 enchatDmgRate = Config.RATE_9_DMG_RATE; }else if (_weaponEnchant
			 * == 10 && (_random.nextInt(100) + 1) <= Config.RATE_10_DMG_PER) { // 인첸트 10일때
			 * enchatDmgRate = Config.RATE_10_DMG_RATE; }else if (_weaponEnchant == 11 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_11_DMG_PER) { // 인첸트 11일때
			 * enchatDmgRate = Config.RATE_11_DMG_RATE; }else if (_weaponEnchant == 12 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_12_DMG_PER) { // 인첸트 12일때
			 * enchatDmgRate = Config.RATE_12_DMG_RATE; }else if (_weaponEnchant == 13 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_13_DMG_PER) { // 인첸트 13일때
			 * enchatDmgRate = Config.RATE_13_DMG_RATE; }else if (_weaponEnchant == 14 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_14_DMG_PER) { // 인첸트 14일때
			 * enchatDmgRate = Config.RATE_14_DMG_RATE; }else if (_weaponEnchant == 15 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_15_DMG_PER) { // 인첸트 15일때
			 * enchatDmgRate = Config.RATE_15_DMG_RATE; }else if (_weaponEnchant == 16 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_16_DMG_PER) { // 인첸트 15일때
			 * enchatDmgRate = Config.RATE_16_DMG_RATE; }else if (_weaponEnchant == 17 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_17_DMG_PER) { // 인첸트 17일때
			 * enchatDmgRate = Config.RATE_17_DMG_RATE; }else if (_weaponEnchant == 18 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_18_DMG_PER) { // 인첸트 18일때
			 * enchatDmgRate = Config.RATE_18_DMG_RATE; } } catch (Exception e) { }
			 */
			if (secondw) {
				weaponTotalDamage += calcSAttrEnchantDmg();
			} else {
				weaponTotalDamage += calcAttrEnchantDmg();
			}

			/*
			 * if (_weaponId == 61){ weaponTotalDamage += _weaponEnchant*2; }else
			 */if (_pc.getSkillEffectTimerSet().hasSkillEffect(DOUBLE_BRAKE) && (_weaponType2 == 54 || _weaponType2 == 58)) {
				int rnd = 33;
				int c = _pc.getLevel() - 80;
				if (_pc.getLevel() >= 90) {
					rnd += 10;
				} else if (_pc.getLevel() >= 85) {
					rnd += 9;
				} else if (_pc.getLevel() >= 80) {
					rnd += 8;
				} else if (_pc.getLevel() >= 75) {
					rnd += 7;
				} else if (_pc.getLevel() >= 70) {
					rnd += 6;
				} else if (_pc.getLevel() >= 65) {
					rnd += 5;
				} else if (_pc.getLevel() >= 60) {
					rnd += 4;
				} else if (_pc.getLevel() >= 55) {
					rnd += 3;
				} else if (_pc.getLevel() >= 50) {
					rnd += 2;
				} else if (_pc.getLevel() >= 45) {
					rnd += 1;
				}
				if (_pc.DOUBLE_BREAK_DESTINY) {
					if (_pc.getLevel() > 80)
						rnd += (_pc.getLevel() - 80);
				}
				if (_pc.FINALBURN) {
					if (c > 8) // 최대
						c = 8;
					if (c < 3)
						c = 3; // 최소
				}
				if ((_random.nextInt(100) + 1) <= rnd + c) {
					weaponTotalDamage *= 3;
					double_burning = true;
				}
			}

			dmg = weaponTotalDamage + _statusDamage;

			if (_weaponType2 != 20 && _weaponType2 != 62) {
				dmg += _pc.getDmgup() + _pc.getDmgupByArmor();
			} else {
				dmg += _pc.getBowDmgup() + _pc.getBowDmgupByArmor() + _pc.getBowDmgupByDoll();
			}

			if (_weaponType2 == 20) { // 활
				if (_weaponId == 190 || _weaponId == 100190 || _weaponId == 450029 || _weaponId == 30082 || _weaponId == 30091
						|| (_weaponId >= 11011 && _weaponId <= 11013)) { // 사이하의활
					dmg += 2;
				} else if (_weaponId == 7201) {
					dmg += 10;
				} else if (_arrow.getItemId() == 40743) {// 수령
					dmg += 1;
				} else if (_arrow.getItemId() == 40744) {// 수령
					dmg += 5;
				} else if (_arrow.getItemId() == 40745) {// 수령
					dmg += 10;
					_hitRate += 3;
				}
			} else if (_weaponType2 == 62) { // 암 토토 렛
				int add_dmg = _sting.getItem().getDmgSmall();
				if (add_dmg == 0) {
					add_dmg = 1;
				}
				dmg = dmg + _random.nextInt(add_dmg) + 1;
			}

			dmg = calcBuffDamage(dmg);

			/*
			 * if(_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SPIRIT)){
			 * _pc.sendPackets(new S_SkillSound(_targetPc.getId(), 7065), true);
			 * Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 7065),
			 * true); }
			 */
		}

		try {
			weaponTotalDamage += WeaponAddDamage.getInstance().getWeaponAddDamage(_weaponId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (_weaponType2 == 0) { // bear hands
			dmg = (_random.nextInt(5) + 4) / 4;
		}

		if (_weaponType2 != 0) { // when not bare handed
			if (_weaponType == 17) { // kiringku pc-pc
				int 키링크대미지인트 = _pc.getAbility().getTotalInt();
				int 키링크대미지스펠 = _pc.getAbility().getSp();
				int 키링크최대 = 키링크대미지인트 + 키링크대미지스펠;
				int 키링크랜덤 = (_random.nextInt(키링크대미지인트) + 8) + (_random.nextInt(키링크대미지스펠) + 8);
				/*
				 * if(_target.getSkillEffectTimerSet().hasSkillEffect(L1SkillId. ERASE_MAGIC)){
				 * _target.getSkillEffectTimerSet().removeSkillEffect (L1SkillId.ERASE_MAGIC); }
				 */
				dmg += 키링크랜덤;

				dmg = calcMrDefense(_target.getResistance().getEffectedMrBySkill(), dmg);

				if (키링크랜덤 >= 키링크최대) {
					_critical = true;
				}

			}

			if (_weaponType == 18) {
				dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId, _weaponEnchant);
			}

			if (secondw) {
				switch (_SweaponId) {
				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
				case 110051:
				case 110052:
				case 110053:
				case 110054:
				case 110055:
				case 110056:
				case 110057:
				case 110058:

					WeaponSkill.블레이즈쇼크(_pc, _targetPc, _weaponEnchant);

					break;
				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _targetPc);
					break;
				case 2:
				case 200002:
					dmg = WeaponSkill.getDiceDaggerDamage(_pc, _targetPc, weapon);
					return (int) dmg; // break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _targetPc);
					break;
				case 100047:
				case 47:
				case 450031:

					WeaponSkill.getSilenceSword(_pc, _targetPc, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 30095:
				case 222204:
				case 450046:
					dmg += WeaponSkill.get수결지Damage(_pc, _target, _weaponEnchant);
					break;
				case 294: // 전설군주의검 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					// dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				case 30081:
				case 30090:
				case 31081:
				case 222207:
					dmg += WeaponSkill.hyperionsDespair(this._pc, this._target, 12248, this._weaponEnchant);
					break;
				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _targetPc, _weaponEnchant);
					break;
				case 30110:
					dmg += WeaponSkill.getRepperSwordDamage(_pc, _targetPc, _weaponEnchant);
					break;
				case 30111:
					dmg += WeaponSkill.getgrankainjudge(_pc, _targetPc, _weaponEnchant);
					break;
				case 30112:
					dmg += WeaponSkill.ainhasadflash(_pc, _targetPc, _weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _targetPc, _weaponEnchant);
					break;
				// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
				// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _targetPc, _weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 191:// 살천의활
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9361);
					break;
				// case 114: WeaponSkill.getGunjuSword(_pc,_target
				// ,_weaponEnchant); break; //추가
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamagePc(_pc, _target);
					break;
				case 204: // 진홍의 크로스보우
				case 100204:
				case 30087:
				case 30096:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _targetPc);
					break;

				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target, _weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3940);
					break;
				/*
				 * case 410000: case 410001: case 450009: case 450014: case 450004: dmg +=
				 * WeaponSkill.getChainSwordDamage(_pc, _target); break;
				 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3704);
					break;
				case 7228:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 5524);
					break;
				// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
				// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 0);
					break;

				case 415015:
				case 415016:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 1); // 쿠창
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target, _weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;
				case 7238:
					dmg += WeaponSkill.salkiPc(_pc, _target, _weaponEnchant);
					break;

				case 450008:
				case 450022:
				case 450024:
				case 450010:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break;

				case 450011:
				case 450012:
				case 450013:
				case 450023:
				case 450025:
					WeaponSkill.이블트릭(_pc, _target, _weaponEnchant);
					break;

				case 30080:
				case 30089:
				case 31080:
				case 222205:
					dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId, _weaponEnchant);
					break;

				case 263:
				case 4500028: // dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// 각궁
				// case 263:
				// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
				// break;
				case 100259:
				case 100260:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9359);
					break; // 파괴의 크로우, 이도류
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target, _weaponEnchant);
					break; // 공명의 키링크
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 7398);
					break; // 환영의 체인소드
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break; // 붉은 사자 무기
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3939);
					break; // 환상의 무기 (10검 이벤트)
				case 291: // 제로스의지팡이
					dmg += WeaponSkill.getZerosDamagePc(_pc, _target, _weaponEnchant);
					break;
				case 203025:
				case 203026: // 진싸울
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getSSaulHonDamage(this._pc, this._target);
					break;
				case 293: // 악몽의장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					break;
				case 30117: // 발라카스의 장검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon1Damage(this._pc, this._target);
					break;
				case 30118: // 발라카스의 양손검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon2Damage(this._pc, this._target);
					break;
				case 30121: // 파푸리온의 장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon1Dmg(this._pc, this._target);
					break;
				case 30122: // 파푸리온의 이도류
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon2Damage(this._pc, this._target);
					break;
				case 30115: // 안타라스의 도끼
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAntaHon1Damage(this._pc, this._target);
					break;
				case 30116: // 안타라스의 지팡이
					dmg += WeaponSkill.getAntaHon2DamagePc(_pc, _target, _weaponEnchant);
					break;
				case 30119: // 린드비오르의 체인소드
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getLindHon1Damage(this._pc, this._target);
					break;
				case 30120:// 린드비오르의 키링크
					dmg += WeaponSkill.getLindHon1Damage(_pc, _target, _weaponEnchant);
					break;
				case 90084:
					WeaponSkill.섬멸자의체인소드(_pc);
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAnnihilationDmg(this._pc, this._target);
					break;
				case 7227:
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getTyphoonDmg(this._pc, this._target);
					break;
				default:
					break;
				}
			} else {
				switch (_weaponId) {
				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
				case 110051:
				case 110052:
				case 110053:
				case 110054:
				case 110055:
				case 110056:
				case 110057:
				case 110058:
					WeaponSkill.블레이즈쇼크(_pc, _targetPc, _weaponEnchant);
					break;
				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _targetPc);
					break;
				case 2:
				case 200002:
					dmg = WeaponSkill.getDiceDaggerDamage(_pc, _targetPc, weapon);
					return (int) dmg; // break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _targetPc);
					break;
				case 100047:
				case 47:
				case 450031:
					WeaponSkill.getSilenceSword(_pc, _targetPc, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 30095:
				case 222204:
				case 450046:
					dmg += WeaponSkill.get수결지Damage(_pc, _target, _weaponEnchant);

					break;
				case 294: // 전설군주의검 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					// dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				case 30081:
				case 30090:
				case 31081:
				case 222207:
					dmg += WeaponSkill.hyperionsDespair(this._pc, this._target, 12248, this._weaponEnchant);
					break;
				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _targetPc, _weaponEnchant);
					break;
				case 30110:
					dmg += WeaponSkill.getRepperSwordDamage(_pc, _targetPc, _weaponEnchant);
					break;
				case 30111:
					dmg += WeaponSkill.getgrankainjudge(_pc, _targetPc, _weaponEnchant);
					break;
				case 30112:
					dmg += WeaponSkill.ainhasadflash(_pc, _targetPc, _weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _targetPc, _weaponEnchant);
					break;
				// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
				// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _targetPc, _weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 191:// 살천의활
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9361);
					break;
				// case 114: WeaponSkill.getGunjuSword(_pc,_target
				// ,_weaponEnchant); break; //추가
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamagePc(_pc, _target);
					break;
				case 204: // 진홍의 크로스보우
				case 100204:
				case 30087:
				case 30096:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _targetPc);
					break;
				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target, _weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3940);
					break;
				/*
				 * case 410000: case 410001: case 450009: case 450014: case 450004: dmg +=
				 * WeaponSkill.getChainSwordDamage(_pc, _target); break;
				 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3704);
					break;
				case 7228:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 5524);
					break;
				// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
				// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 0);
					break;
				case 415015:
				case 415016:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target, _weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;

				case 7238:
					dmg += WeaponSkill.salkiPc(_pc, _target, _weaponEnchant);
					break;

				case 450008:
				case 450022:
				case 450024:
				case 450010:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break;

				case 450011:
				case 450012:
				case 450013:
				case 450023:
				case 450025:
					WeaponSkill.이블트릭(_pc, _target, _weaponEnchant);
					break;
				// 크로노스의공포
				case 30080:
				case 30089:
				case 31080:
				case 222205:
					dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId, _weaponEnchant);
					break;
				case 3000081:
					dmg += WeaponSkill.Icekiring11(_pc, _target, _weaponEnchant);
					break;
				case 263:
				case 4500028: // dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// 각궁
				// case 263:
				// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
				// break;
				case 100259:
				case 100260:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9359);
					break; // 파괴의 크로우, 이도류
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target, _weaponEnchant);
					break; // 공명의 키링크
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 7398);
					break; // 환영의 체인소드
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break; // 붉은 사자 무기
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3939);
					break; // 환상의 무기 (10검 이벤트)
				case 291: // 제로스의지팡이
					dmg += WeaponSkill.getZerosDamagePc(_pc, _target, _weaponEnchant);
					break;
				case 203025:
				case 203026: // 진싸울
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getSSaulHonDamage(this._pc, this._target);
					break;
				case 293: // 악몽의장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					break;
				case 30117: // 발라카스의 장검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon1Damage(this._pc, this._target);
					break;
				case 30118: // 발라카스의 양손검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon2Damage(this._pc, this._target);
					break;
				case 30121: // 파푸리온의 장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon1Dmg(this._pc, this._target);
					break;
				case 30122: // 파푸리온의 이도류
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon2Damage(this._pc, this._target);
					break;
				case 30115: // 안타라스의 도끼
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAntaHon1Damage(this._pc, this._target);
					break;
				case 30116: // 안타라스의 지팡이
					dmg += WeaponSkill.getAntaHon2DamagePc(_pc, _target, _weaponEnchant);
					break;
				case 30119: // 린드비오르의 체인소드
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getLindHon1Damage(this._pc, this._target);
					break;
				case 30120:// 린드비오르의 키링크
					dmg += WeaponSkill.getLindHon1Damage(_pc, _target, _weaponEnchant);
					break;
				case 90084:
					WeaponSkill.섬멸자의체인소드(_pc);
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAnnihilationDmg(this._pc, this._target);
					break;
				default:
					break;
				}
			}
			if (_weaponId == 450009) {
				dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId, _weaponEnchant);
				WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
			}
			if (secondw) {
				if (_SweaponId == 7233) {
					WeaponSkill.이블리버스(_pc, _target, _SweaponEnchant);
				}
			} else {
				if (_weaponId == 7233) {
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
				}
			}
		}

		// Added bless Weapon Damage 0 is blessed
		if (weapon != null) {
			if (weapon.getBless() == 0) {
				dmg += 1;
			}
		}

		/*
		 * if(_weaponId >= 90085 && _weaponId <= 90092){ dmg += _weaponEnchant; }
		 */
		// 붉은 사자의 무기 (PVP 데미지)
		if (_weaponId >= 277 && _weaponId <= 283) {
			dmg += _weaponEnchant;
		}
		// 환상의 무기 (10검 이벤트)
		/*
		 * if(_weaponId >= 284 && _weaponId <= 290){ if(_weaponEnchant == 7) dmg += 3;
		 * else if(_weaponEnchant == 8) dmg += 5; else if(_weaponEnchant == 9) dmg += 7;
		 * else if(_weaponEnchant == 10) dmg += 10;
		 *
		 * dmg *= 0.92; }
		 */
		// 붉은 사자의 갑옷 (PVP 데미지감소)
		for (int i = 21242; i <= 21245; i++) {
			L1ItemInstance PVP방어구 = _targetPc.getInventory().checkEquippedItem(i);
			if (PVP방어구 != null) {
				dmg -= PVP방어구.getEnchantLevel() + 1;
				break;
			}
		}

		if (_calcType == PC_PC) {
			if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_1)
					|| _pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_2)) {
				dmg += 2;
			}
			if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_3)) {
				dmg += 1;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_1)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_2)) {
				dmg -= 2;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_3)) {
				dmg -= 1;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) {
				dmg -= 2;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_5)) {
				dmg -= 1;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_6)) {
				dmg -= 1;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.NORMAL_PROTECTION))
				dmg -= 8;
		}
		/*
		 * if(_weaponId >= 90085 && _weaponId <= 90092){ dmg += _weaponEnchant; }
		 */
		if (_weaponId >= 277 && _weaponId <= 283) { // red lion event items
			dmg += _weaponEnchant;
		}

		if (_weaponId == 276 || _weaponId == 100276 || // Red Knight's Greatsword
				_weaponId == 292 || _weaponId == 293) { // Crow of Wrath, Longbow of Nightmare
			if (_pc.getLawful() <= -20000)
				dmg += 5;
			else if (_pc.getLawful() <= -10000)
				dmg += 2;
			else if (_pc.getLawful() <= 0)
				dmg += 1;
			/*
			 * double lawful = 32767 - _pc.getLawful(); lawful *= 0.0000037;//만카 24%정도 만라 0%
			 * System.out.println(_pc.getLawful()); dmg += dmg * lawful;
			 */
		}
		if (_pc.getDollList().size() > 0) {
			for (L1DollInstance doll : _pc.getDollList()) {
				if (_weaponType2 != 20 && _weaponType2 != 62) {
					int d = doll.getDamageByDoll(_targetPc);
					if (d > 0 && doll.getDollType() == L1DollInstance.DOLLTYPE_GREG)
						_pc.setCurrentHp(_pc.getCurrentHp() + _random.nextInt(5) + 1);
					dmg += d;
				}

				if (doll.getDollType() == L1DollInstance.DOLLTYPE_BLACKELDER || doll.getDollType() == L1DollInstance.DOLLTYPE_BLESSEDBLACKELDER
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_DEATHKNIGHT || doll.getDollType() == L1DollInstance.DOLLTYPE_BLESSEDDEATHKNIGHT
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_헌신1등급) {
					dmg += doll.getMagicDamageByDoll(_targetPc);
				}

				doll.attackPoisonDamage(_pc, _targetPc);
			}
		}

		if (adddmg != 0)
			dmg += adddmg;

		/** magic doll stone golem **/
		if (_targetPc.getDollList().size() > 0 && _weaponType2 != 20 && _weaponType2 != 62) {
			for (L1DollInstance doll : _targetPc.getDollList()) {
				if (doll.getDollType() == L1DollInstance.DOLLTYPE_STONEGOLEM || doll.getDollType() == L1DollInstance.DOLLTYPE_DRAKE
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_BLESSEDDRAKE)
					dmg -= doll.getDamageReductionByDoll();
			}
		}
		/** magic doll stone golem **/

		/*
		 * if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SLASH)) { dmg += 50;
		 * _pc.sendPackets(new S_SkillSound(_targetPc.getId(), 6591), true);
		 * Broadcaster.broadcastPacket(_pc, new S_SkillSound( _targetPc.getId(), 6591),
		 * true); _pc.getSkillEffectTimerSet().killSkillEffectTimer(BURNING_SLASH); }
		 */

		/*
		 * if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.주군의버프)) { if
		 * (_pc.getClanRank() >= L1Clan.CLAN_RANK_GUARDIAN) dmg += 30; }
		 */

		dmg += 룸티스검귀추가데미지();

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg -= (dmg * Config.IMMUNE_FACTOR);

		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(LUCIFER)) {
			dmg -= dmg / 10;

		}

		if (_targetPc.글로리어스) {
			if (_random.nextInt(100) < 2 + 8 - 5) {
				dmg -= 30;
				_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 19318));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 19318));
			}
		}

		/** Reduce Dragon's Blessing damage **/
		if (_targetPc._dragonbless_1) {
			dmg -= 2;
		} else if (_targetPc._dragonbless_2) {
			dmg -= 3;
		} else if (_targetPc._dragonbless_3) {
			dmg -= 4;
		}

		if (_targetPc.isWizard()) {
			dmg -= dmg / 10;

		}

		if (_targetPc.isArmourGuard) { // Damage reduction by armor guard
			int d = _targetPc.getAC().getAc() / 10;

			if (d < 0) {
				dmg += d;
			} else {
				dmg -= d;
			}
		}

		// if(_weaponType1 != 17) // Only when it is not a kiringku
		dmg -= _targetPc.getDamageReductionByArmor(); // Damage Reduction by Defense Mechanism

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) { // Reduction of damage by special cooking
			dmg -= 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(HEUKSA)) {
			dmg -= 3;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING2)) {
			dmg -= 5;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EARTH_BLESS))
			dmg -= 2;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_닭고기) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_연어)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_칠면조) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_한우)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐닭고기) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐연어)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐칠면조) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐한우)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MISO_2_DEF)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(METIS_SOUP) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(PSY_COOL_DRINK))
			dmg -= 5;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(METIS_CUISINE) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(SPICY_RAMEN))
			dmg -= 5;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(메티스축복주문서) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ENERGY_OF_BLACK))
			dmg -= 3;


		dmg -= rumtisRedEarringDReduc();

		// Add only when it is not a kiringku
		if (_weaponType != 17 && _targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			/*int dmg2 = (targetPcLvl - 50) / 5;
			dmg -= dmg2 > 0 ? dmg2 : 0; // +1*/
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		if (_weaponType != 17 && _targetPc.infinity_A) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 45) {
				targetPcLvl = 45;
			}
			int dmg2 = ((targetPcLvl - 41) / 4);
			dmg -= dmg2 > 0 ? dmg2 : 0; // +1
		}

		if (_weaponType != 17 && _targetPc.getSkillEffectTimerSet().hasSkillEffect(MAJESTY)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 80) {
				targetPcLvl = 80;
			}
			int dmg2 = (targetPcLvl - 80) + 3;
			dmg -= dmg2 > 0 ? dmg2 : 0;
		}

		/*
		 * //상아탑 장갑류 착용시 if (_targetPc.getInventory().checkEquipped(20173) ||
		 * _targetPc.getInventory().checkEquipped(21103) ||
		 * _targetPc.getInventory().checkEquipped(21108)) dmg *= 1.05;
		 */
		if (_target != _targetNpc) {
			L1ItemInstance 반역자의방패 = _targetPc.getInventory().checkEquippedItem(21093);
			if (반역자의방패 != null) {
				int chance = 5 + (반역자의방패.getEnchantLevel() * 2);
				if (_random.nextInt(100) <= chance) {
					dmg -= 30;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 6320));
					Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 6320));
				}
			}

			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) { // Armor Break
				if (_weaponType2 != 20 || _weaponType2 != 62) {
					dmg *= 1.6;
				}
			}
			if (_targetPc.DRAGONSKIN) {
				dmg -= 2;
			}
			if(_targetPc.DRAGONSKIN && _targetPc.getLevel() >= 80) {
				int ddmg = _targetPc.getLevel() - 78;
				int i = (ddmg / 2) * 1;
				dmg -= 2 + i;

		}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ILLUSION_AVATAR) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(CUBE_AVATAR)) {
			dmg += dmg / 20;
		}
		if (_targetPc.DRAGONSKIN) {
			dmg -= 3;
		}
		if(_targetPc.DRAGONSKIN && _targetPc.getLevel() >= 80) {
			int ddmg = _targetPc.getLevel() - 78;
			int i = (ddmg / 2) * 1;
			dmg -= 3 + i;

	}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			dmg -= 4;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(VALA_MAAN) // 화룡의 마안 -
				// 일정확률로
				// 물리추가타격+2
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(LIFE_MAAN)) { // 생명의
			// 마안 -
			// 일정확률로
			// 물리추가타격+2
			int MaanAttDmg = _random.nextInt(100) + 1;
			if (MaanAttDmg <= 15) { // 확률
				dmg += 2;
			}
		}
		if (_pc != null) {
			if (_weaponType2 == 20 || _weaponType2 == 62) { // New Ranged Critical Sweet Potato
				int Bowcritical = CalcStat.calcRangeCrit(_pc.getAbility().getTotalDex()) + _pc.getBowDmgCritical();
				int chance = _random.nextInt(100) + 1;
				if (_pc.getInventory().checkEquipped(231004)) { // Kurtz's Bow Insignia
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(231004);
					if (item.getEnchantLevel() == 6) {
						Bowcritical += 1;
					} else if (item.getEnchantLevel() == 7) {
						Bowcritical += 3;
					} else if (item.getEnchantLevel() >= 8) {
						Bowcritical += 5;
					}
				}
				if (_pc.getInventory().checkEquipped(30220)) { // Longbow of Destruction
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30220);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 2;
					}
				}
				if (_pc.getInventory().checkEquipped(189)) { // 흑왕궁
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(189);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(100189)) { // black palace
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(100189);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(293)) { // Nightmare Longbow
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(293);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(30121)) { // Pafurion's Longbow
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(293);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(30082)) { // Gaia's Wrath
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30082);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(30091)) { // Gaia's Fury (Engraved)
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30091);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(413105)) { // Demon King's Bow
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(413105);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 1;
					}
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_WALK)) { // Eagle Eye Critical Hit
					Bowcritical += 2;
				}
				if (_pc != null) {
					/** Valakas Strike **/
					if (_pc.getInventory().checkEquipped(420112) || _pc.getInventory().checkEquipped(420113) || _pc.getInventory().checkEquipped(420114)
							|| _pc.getInventory().checkEquipped(420115)) { // 발라카스의 리뉴얼
						Bowcritical += 2;
						if (chance <= 5) {
							_pc.sendPackets(new S_SkillSound(_pc.getId(), 15841));
							_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 15841));
							// weaponDamage = 10;
							// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
							// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
						}
					}
				}
				if (chance <= Bowcritical) {
					weaponDamage = weaponMaxDamage + _weaponAddDmg;
					_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // direction set
					_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, _weaponType2, _isHit));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, _weaponType2, _isHit));// 새로만듬
					_critical = true;
				}
			} else {
				int chance = _random.nextInt(100) + 1;
				if (_pc != null) {
					/** Valakas Strike **/
					if (_pc.getInventory().checkEquipped(420112) || _pc.getInventory().checkEquipped(420113) || _pc.getInventory().checkEquipped(420114)
							|| _pc.getInventory().checkEquipped(420115)) { // 발라카스의 3차
						// 마갑주
						if (chance <= 5) {
							_pc.sendPackets(new S_SkillSound(_pc.getId(), 15841));
							_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 15841));
							// weaponDamage = 10;
							// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
							// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
						}
					}
				}
			}
		}
		if (_target != null) { // Added Kurtz's Insignia Critical Damage
			for (L1ItemInstance item : _pc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() == 231004 || item.getItemId() == 231006) {
						if (item.getEnchantLevel() == 6)
							_weaponDoubleDmgChance += 5;
						if (item.getEnchantLevel() == 7)
							_weaponDoubleDmgChance += 15;
						if (item.getEnchantLevel() == 8)
							_weaponDoubleDmgChance += 25;
					}
					break;
				}
			}
		}
		if (_target != null) { // Added Kurtz's Insignia Critical Damage
			for (L1ItemInstance item : _pc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() == 231005) {
						if (item.getEnchantLevel() == 6)
							_weaponDoubleDmgChance += 5;
						if (item.getEnchantLevel() == 7)
							_weaponDoubleDmgChance += 10;
						if (item.getEnchantLevel() == 8)
							_weaponDoubleDmgChance += 20;
					}
					break;
				}
			}
		}

		/** Balakas Renewal, Desul Level 10 **/
		if (_weaponId == 100034 && weapon.getEnchantLevel() >= 10) {
			int locx = _targetPc.getX();
			int locy = _targetPc.getY();
			int chance = _random.nextInt(100) + 1;
			int intel = _pc.getAbility().getTotalInt();
			if (10 >= chance) {
				dmg = _random.nextInt(intel / 2) + (intel);
				if (dmg <= 0)
					dmg = 0;
				S_EffectLocation packet = new S_EffectLocation(locx, locy, 15926);
				_pc.sendPackets(packet);
				_pc.broadcastPacket(packet);
			}

		}

		if(_targetPc.is_halpas_armor()) {
			L1ItemInstance halpas_armor = null;
			for(L1ItemInstance armor : _target.getInventory().getItems()) {
				if(armor.getItem().getType2() != 2) {
					continue;
				}
				if((armor.getItem().getItemId() >= 27528 && armor.getItem().getItemId() <= 27530) && armor.isEquipped()) {
					halpas_armor = armor;
					break;
				}
			}
			halpasArmorBless(_targetPc, halpas_armor);
		}

		if (_weaponId == 22) { // mail breaker
			dmg -= dmg * 0.30;
		}

		if (_weaponId == 450009) { // chainsword
			dmg -= dmg * 0.25;
		}

		/*if (_weaponId == 59 && _weaponEnchant == 10) { // Knightvald's Two-handed Sword
			dmg += 25;
		}

		if (_weaponId == 59 && _weaponEnchant == 9) { // 10나양
			dmg += 15;
		}

		if (_weaponId == 59 && _weaponEnchant == 8) { // 10나양
			dmg += 5;
		}

		if (_weaponId == 30083 || _weaponId == 30092) { // Titan Fury
			dmg += 12;
		}

		if (_weaponId == 7227 || _weaponId == 7225) { // 태도 , 혈풍
			dmg += dmg * 0.08;
		}*/
		if (_targetPc != null) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.TRUE_TARGET)) {
				if (_pc != null) {
					if (_targetPc.tt_clanid == _pc.getClanid() || _targetPc.tt_partyid == _pc.getPartyID()) {
						if (_targetPc.tt_level >= 90) {
							dmg += dmg * 0.08;
						} else if (_targetPc.tt_level >= 85) {
							dmg += dmg * 0.07;
						} else if (_targetPc.tt_level >= 80) {
							dmg += dmg * 0.06;
						} else if (_targetPc.tt_level >= 75) {
							dmg += dmg * 0.05;
						} else if (_targetPc.tt_level >= 60) {
							dmg += dmg * 0.04;
						} else if (_targetPc.tt_level >= 45) {
							dmg += dmg * 0.03;
						} else if (_targetPc.tt_level >= 30) {
							dmg += dmg * 0.02;
						} else if (_targetPc.tt_level >= 15) {
							dmg += dmg * 0.01;
						}
					}
				}
			}
		}

		if (_targetPc.getInventory().checkEquipped(21104) || _targetPc.getInventory().checkEquipped(21111)) {
			// dmg += dmg*0.2;
			if (Config.traineeArmourBalance != 0) {
				double balance = Config.traineeArmourBalance * 0.01;
				dmg += dmg * balance;// 35% down on apprentice weapons
			}
		}

		if (_weaponId == 7 || _weaponId == 35 || _weaponId == 48 || _weaponId == 73 || _weaponId == 105 || _weaponId == 120 || _weaponId == 147
				|| _weaponId == 156 || _weaponId == 174 || _weaponId == 175 || _weaponId == 224 || _weaponId == 7232) {
			if (Config.traineeWeaponBalance != 0) {
				double balance = Config.traineeWeaponBalance * 0.01;
				dmg -= dmg * balance; // 35% down on apprentice weapons
			}
		}

		// dmg -= dmg*0.05;///클래스전체 10%하향

		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0;
		}
		// 피흡수 공식 바꾸기위해 옮김.
		if (_weaponId == 262) { // 블러드서커
			if (dmg >= 30 && _random.nextInt(100) <= 90) {
				// _pc.sendPackets(new
				// S_SystemMessage("피흡량 :"+_drainHp+" 데미지 :"+dmg));
				int _dhp = (int) ((dmg - 10) / 10);
				_drainHp += _dhp;// +_random.nextInt(3)+1;
			}
		}

//		if (dmg > 0 && _pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.디스트로이)) {
//			int c = _pc.getLevel() - 80;
//			if (c > 5) // 최대
//				c = 5;
//			if (c < 3)
//				c = 3; // 최소
//			if (CommonUtil.random(100) <= c) {
//				L1ItemInstance item = _targetPc.getEquipSlot().gettypeArmor();
//				int maxc = item.get_durability();
//				int ac = item.getItem().get_ac();
//				if (ac < 0)
//					ac = Math.abs(ac);
//				if (maxc < ac) {
//					if (item != null) {
//						_targetPc.sendPackets(new S_ServerMessage(268, item.getLogName()));
//						_targetPc.getInventory().receiveDamageArmor(_targetPc, item);
//						_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14549));
//						_pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14549));
//					}
//				}
//			}
//		}

		return (int) dmg;
	}

	private double 룸티스검귀추가데미지() {
		int dmg = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			L1ItemInstance blackRumti = _pc.getInventory().checkEquippedItem(500010);
			if (blackRumti == null)
				blackRumti = _pc.getInventory().checkEquippedItem(502010);
			if (blackRumti != null) {
				int chance = 0;
				if (blackRumti.getBless() == 0 && blackRumti.getEnchantLevel() >= 4) {
					chance = 2 + blackRumti.getEnchantLevel() - 4;
				} else if (blackRumti.getEnchantLevel() >= 5) {
					chance = 2 + blackRumti.getEnchantLevel() - 5;
				}
				if (chance != 0) {
					if (_random.nextInt(100) < chance) {
						dmg += 20;
						_pc.sendPackets(new S_SkillSound(_pc.getId(), 13931));
						_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 13931));
					}
				}
			}
		}
		return dmg;
	}

	private int calcSAttrEnchantDmg() {
		if (_SweaponAttrEnchantLevel <= 0)
			return 0;
		int dmg = 0;
		/** Attribute enchantment additional hit value */
		switch (_SweaponAttrEnchantLevel) {
		case 1:
		case 4:
		case 7:
		case 10:
			dmg = 1;
			break;
		case 2:
		case 5:
		case 8:
		case 11:
			dmg = 3;
			break;
		case 3:
		case 6:
		case 9:
		case 12:
			dmg = 5;
			break;
		case 16:
		case 18:
		case 20:
		case 22:
			dmg = 7;
			break;
		case 17:
		case 19:
		case 21:
		case 23:
			dmg = 9;
			break;

		case 33:
		case 35:
		case 37:
		case 39:
			dmg = 7;
			break;
		case 34:
		case 36:
		case 38:
		case 40:
			dmg = 9;
			break;

		case 13: // Specialized Enhancement Scroll
			dmg = 6; // Chuta 6
			_hitRate += 2;// Siege 2

			break; // Level 1
		case 14:
			dmg = 8;
			_hitRate += 2;
			break;
		case 15:
			dmg = 10;
			_hitRate += 2;// 3단계
			break;
		default:
			dmg = 0;
			break;
		}
		int attr = 0;
		switch (_SweaponAttrEnchantLevel) {
		case 1:
		case 2:
		case 3:
		case 13:
		case 14:
		case 33:
		case 34:
			attr = 2;
			break;
		case 4:
		case 5:
		case 6:
		case 15:
		case 16:
		case 35:
		case 36:
			attr = 4;
			break;
		case 7:
		case 8:
		case 9:
		case 17:
		case 18:
		case 37:
		case 38:
			attr = 8;
			break;
		case 10:
		case 11:
		case 12:
		case 19:
		case 20:
		case 39:
		case 40:
			attr = 1;
			break;
		default:
			break;
		}
		dmg -= dmg * calcAttrResistance(attr);
		return dmg;

	}

	private int calcAttrEnchantDmg() {
		if (_weaponAttrEnchantLevel <= 0)
			return 0;
		int dmg = 0;
		/** Attribute enchantment additional hit value */

		switch (_weaponAttrEnchantLevel) {
		case 1:
		case 4:
		case 7:
		case 10:
			dmg = 1;
			break;
		case 2:
		case 5:
		case 8:
		case 11:
			dmg = 3;
			break;
		case 3:
		case 6:
		case 9:
		case 12:
			dmg = 5;
			break;
		case 16:
		case 18:
		case 20:
		case 22:
			dmg = 7;
			break;
		case 17:
		case 19:
		case 21:
		case 23:
			dmg = 9;
			break;

		case 33:
		case 35:
		case 37:
		case 39:
			dmg = 7;
			break;
		case 34:
		case 36:
		case 38:
		case 40:
			dmg = 9;
			break;

		case 13:// 특화 강화주문서
			dmg = 6;// 추타6
			_hitRate += 2;// 공성2
			break;// 1단계
		case 14:
			dmg = 8;
			_hitRate += 2;//
			break;
		case 15:
			dmg = 10;
			_hitRate += 2;// 3단계
			break;
		default:
			dmg = 0;
			break;
		}

		int attr = 0;
		switch (_weaponAttrEnchantLevel) {
		case 1:
		case 2:
		case 3:
		case 13:
		case 14:
		case 33:
		case 34:
			attr = 2;
			break;
		case 4:
		case 5:
		case 6:
		case 15:
		case 16:
		case 35:
		case 36:
			attr = 4;
			break;
		case 7:
		case 8:
		case 9:
		case 17:
		case 18:
		case 37:
		case 38:
			attr = 8;
			break;
		case 10:
		case 11:
		case 12:
		case 19:
		case 20:
		case 39:
		case 40:
			attr = 1;
			break;
		default:
			break;
		}

		dmg -= dmg * calcAttrResistance(attr);
		return dmg;

	}

	private int addattrdmg(int attr) {
		int adddmg = 0;
		int orgattr = 0;
		int npc_att = _targetNpc.getNpcTemplate().get_weakAttr();
		if (npc_att == 0)
			return 0;
		switch (attr) {
		case 1:
			orgattr = 4;
			break; // 수
		case 2:
			orgattr = 8;
			break; // wind
		case 3:
			orgattr = 1;
			break; // earth
		case 4:
			orgattr = 2;
			break; // fury
		default:
			break;
		}

		if (orgattr == npc_att) {
			adddmg = 3;
		}
		return adddmg;
	}

	private double calcAttrResistance(int attr) {
		int resist = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			switch (attr) {
			case L1Skills.ATTR_EARTH:
				resist = _targetPc.getResistance().getEarth();
				break;
			case L1Skills.ATTR_FIRE:
				resist = _targetPc.getResistance().getFire();
				break;
			case L1Skills.ATTR_WATER:
				resist = _targetPc.getResistance().getWater();
				break;
			case L1Skills.ATTR_WIND:
				resist = _targetPc.getResistance().getWind();
				break;
			default:
				break;
			}
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			// In case of non-vulnerable property, property damage is not applied
			int npc_att = _targetNpc.getNpcTemplate().get_weakAttr();
			if (npc_att == 0)
				return 0;
			if (npc_att >= 8) {
				npc_att -= 8;
				if (attr == 8)
					return 0;
			}
			if (npc_att >= 4) {
				npc_att -= 4;
				if (attr == 4)
					return 0;
			}
			if (npc_att >= 2) {
				npc_att -= 2;
				if (attr == 2)
					return 0;
			}
			if (npc_att >= 1) {
				npc_att -= 1;
				if (attr == 1)
					return 0;
			}
			return 1;
		}

		/*
		 * int resistFloor = (int) (0.32 * Math.abs(resist)); if (resist >= 0) {
		 * resistFloor *= 1; } else { resistFloor *= -1; }
		 *
		 * double attrDeffence = resistFloor / 32.0;
		 */

		double attrDeffence = resist / 5 * 0.01;

		return attrDeffence;
	}

	// ●●●● Calculate damage from player to NPC ●●●●
	private int calcPcNpcDamage(int adddmg) {
		if (_pc instanceof L1RobotInstance) {
			double dmg = 0;
			if (_pc.getCurrentWeapon() == 20) { // bow
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_ELFBRAVE)) {
					int rnd = _random.nextInt(100);
					if (rnd < 9) {
						_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // direction set
						_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
						Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit)); // You made it new
					}
					if (Config.BOTFAIRYCYCLONE) {
						if ((_random.nextInt(100) + 1) <= 9) {
							_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 17557), true);
							Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 17557), true);
							dmg *= 1.4;
						}
					}
					if (Config.BOTFAIRYMOVEMENT) {
						dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					}
					dmg += _random.nextInt(Config.요정활랜덤타격치NPC) + Config.요정활기본타격치NPC;
				} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FOCUS_WAVE)) { // bow
					int rnd = _random.nextInt(100);
					if (rnd < 9) {
						_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // direction set
						_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
						Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
					}
					if (_pc.getSkillEffectTimerSet().hasSkillEffect(FOCUS_WAVE)) {
						if (_random.nextInt(100) >= (90 + _pc.getFocusWave() * 4)) {
							_pc.set포커스웨이브(_pc.getFocusWave() + 1);
							switch (_pc.getFocusWave()) {
							case 1:
								_pc.sendPackets(new S_SkillBrave(_pc.getId(), 11, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 11, 0), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16532), true);
								break;
							case 2:
								_pc.sendPackets(new S_SkillBrave(_pc.getId(), 12, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 12, 0), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16533), true);
								break;
							case 3:
								_pc.set포커스웨이브(0);
								_pc.sendPackets(new S_SkillBrave(_pc.getId(), 10, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 10, 0), true);
								Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16540), true);
								break;
							}
						}
					}
					if (Config.BOTFAIRYMOVEMENT) {
						dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					}
					dmg += _random.nextInt(Config.요정활랜덤타격치NPC) + Config.요정활기본타격치NPC;
				} else {
					int rnd = _random.nextInt(100);
					if (rnd < 9) {
						_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // direction set
						_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
						Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, 20, _isHit));
					}
					if (Config.BOTFAIRYMOVEMENT) {
						dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					}
					dmg += _random.nextInt(Config.요정활랜덤타격치NPC) + Config.요정활기본타격치NPC;
				}
			} else if (_pc.getCurrentWeapon() == 54) { // dual wield
				int rnd = _random.nextInt(100);
				if (rnd < 38) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 54));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 54));
					dmg *= 2;
				}
				if ((_random.nextInt(100) + 1) <= 33) {
					_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 6532), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 6532), true);
					dmg *= 1.5;
				}
				dmg += _random.nextInt(Config.다엘랜덤타격치NPC) + Config.다엘기본타격치NPC;
			} else if (_pc.getCurrentWeapon() == 50) { // dual wield
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 50));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 50));
				}
				dmg += _random.nextInt(Config.기사랜덤타격치NPC) + Config.기사기본타격치NPC;
			} else if (_pc.getCurrentWeapon() == 4) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 4));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 4));
				}
				if ((_random.nextInt(100) + 1) <= 33) {
					dmg *= 1.5;
					burning = true;
				}
				dmg += WeaponSkill.getbotKurtSwordDamage(_pc, _target, _weaponEnchant);
				dmg += _random.nextInt(Config.군검요랜덤타격치NPC) + Config.군검요기본타격치NPC;
			} else if (_pc.getCurrentWeapon() == 88) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 11));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 11));
				}
				int rnd2 = _random.nextInt(100);
				int rnd3 = _random.nextInt(100);
				if (rnd2 < 10) {
					int gfx = 12487;
					if (rnd3 < 10) {
						gfx = 12489;
					}
					_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), gfx)); // 12489
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), gfx));
				}
				if (Config.BOTWARRIORSTORM) {
					dmg += WeaponSkill.getTyphoonDmg(this._pc, this._target);
				}
				dmg += _random.nextInt(Config.전사랜덤타격치NPC) + Config.전사기본타격치NPC;
			} else if (_pc.getCurrentWeapon() == 24) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 24));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 24));
				}
				dmg += _random.nextInt(Config.용기사랜덤타격치NPC) + Config.용기사기본타격치NPC;
			} else if (_pc.getCurrentWeapon() == 40) {
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 40));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 40));
				}
				dmg += WeaponSkill.botgetZerosDamageNPc(_pc, _target, _weaponEnchant);
				dmg += _random.nextInt(30) + 10;
			} else if (_pc.getCurrentWeapon() == 58) { // Illusionist
				int rnd = _random.nextInt(100);
				if (rnd < 9) {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 91));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 91));
				} else {
					_pc.sendPackets(new S_AttackCritical(_pc, _targetNpc.getId(), 89));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetNpc.getId(), 89));
				}
				if (_pc.getGfxId().getTempCharGfx() == Config.로봇칼1) {
					dmg += WeaponSkill.boksalkiNPc(_pc, _target, _weaponEnchant);
				}
				if (_pc.getGfxId().getTempCharGfx() == Config.로봇칼2 || _pc.getGfxId().getTempCharGfx() == Config.로봇칼3) {
					dmg += WeaponSkill.botIcekiringNpc(_pc, _target, _weaponEnchant);
				}
				dmg += _random.nextInt(50) + 10;
			} else {
				dmg += _random.nextInt(30) + 70;
			}
			return (int) dmg;
		}
		int weaponMaxDamage = 0;
		boolean secondw = false;

		int doubleChance = _random.nextInt(100) + 1;

		if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("small") && _weaponSmall > 0) {
			weaponMaxDamage = _weaponSmall + _weaponAddDmg;
		} else if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large") && _weaponLarge > 0) {
			weaponMaxDamage = _weaponLarge + _weaponAddDmg;
		} else {
			weaponMaxDamage = _weaponSmall + _weaponAddDmg;
		}

		if (_pc.isWarrior() && _pc.isSlayer && _pc.getSecondWeapon() != null) {
			int ran = _random.nextInt(100);
			if (ran < 50) {
				secondw = true;
				if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("small") && _SweaponSmall > 0) {
					weaponMaxDamage = _SweaponSmall + _SweaponAddDmg;
				} else if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large") && _SweaponLarge > 0) {
					weaponMaxDamage = _SweaponLarge + _SweaponAddDmg;
				} else {
					weaponMaxDamage = _SweaponSmall + _SweaponAddDmg;
				}
			}
		}

		if (_weaponId == 90083) { // Roaring Two Swords
			_weaponDoubleDmgChance += _weaponEnchant + 1;
		}

		int weaponDamage = 0;
		if (_weaponId == 154 || _weaponId == 84 || _weaponId == 100084) {
			if (_weaponType2 == 58 && doubleChance <= _weaponDoubleDmgChance + _weaponEnchant) { // 위기
				// 히트
				weaponDamage = weaponMaxDamage;
				_attackType = 2;
				// _pc.sendPackets(new S_SkillSound(_pc.getId(), 3671));
				// Broadcaster.broadcastPacket(_pc, new
				// S_SkillSound(_pc.getId(), 3671));
			}
		} else {
			if (_weaponType2 == 58 && doubleChance <= _weaponDoubleDmgChance) { // 위기
				// 히트
				weaponDamage = weaponMaxDamage;
				_attackType = 2;
				// _pc.sendPackets(new S_SkillSound(_pc.getId(), 3671));
				// Broadcaster.broadcastPacket(_pc, new
				// S_SkillSound(_pc.getId(), 3671));
			} else if (_weaponType2 == 0) { // 맨손
				weaponDamage = 0;
			} else {
				weaponDamage = _random.nextInt(weaponMaxDamage + 1) + 1;
			}
		}

		if (weaponDamage > weaponMaxDamage) {
			_critical = true;
		}

		//
		int 치명 = _random.nextInt(100) + 1;
		int 치명확률 = 0;
		if (_weaponType2 == 20 || _weaponType2 == 62) {
			치명확률 = CalcStat.calcRangeCrit(_pc.getAbility().getTotalDex());
		} else {
			치명확률 = CalcStat.meleeCrit(_pc.getAbility().getTotalStr());
		}

		if (치명 <= 치명확률) {
			_critical = true;
			weaponDamage = weaponMaxDamage;
		}

		if (_weaponType2 != 20 && _weaponType2 != 62) {
			if (_pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
				weaponDamage = weaponMaxDamage;
				_critical = false;
			}
		}

		int weaponTotalDamage = 0;

		if (secondw) {
			weaponTotalDamage = weaponDamage + _SweaponEnchant;
			weaponTotalDamage += calcSMaterialBlessDmg();
		} else {
			weaponTotalDamage = weaponDamage + _weaponEnchant;
			weaponTotalDamage += calcMaterialBlessDmg();
		}

		if (_weaponType2 == 54 && _weaponEnchant <= 9) { // dual wield
			int rnd = _random.nextInt(100);
			if (rnd < 38) {
				weaponTotalDamage *= 2.5;
				_pc.sendPackets(new S_AttackCritical(_pc, _target.getId(), 54));
				Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _target.getId(), 54));
			}
		}

		if (_weaponType2 == 54 && _weaponEnchant >= 10) { // dual wield
			int rnd = _random.nextInt(100);
			if (rnd < 38) {
				weaponTotalDamage *= 3;
				_pc.sendPackets(new S_AttackCritical(_pc, _target.getId(), 54));
				Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _target.getId(), 54));
			}
		}
		if (_pc.isCrash) {
			int rnd = _random.nextInt(100);
			if (rnd < 10) {
				int crashdmg = _pc.getLevel();
				double purrydmg = crashdmg;
				int plusdmg = crashdmg;
				int gfx = 12487;
				if (_pc.isFury) {
					int rnd2 = _random.nextInt(100);
					if (rnd2 < 10) {
						gfx = 12489;
						plusdmg = (int) purrydmg;
					}
				}
				weaponTotalDamage += plusdmg;
				_pc.sendPackets(new S_SkillSound(_target.getId(), gfx));// 12489
				Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(), gfx));
			}
		}

		/** Rage Skill */
		if (_pc.rage) {
			int rnd = _random.nextInt(100);
			if (rnd < 33) {
				int ragedmg = _pc.getAbility().getTotalStr() * 2;
				int plusdmg = ragedmg;
				int gfx = 18517;
				weaponTotalDamage += plusdmg;
				_pc.sendPackets(new S_SkillSound(_target.getId(), gfx));// 12489
				Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(), gfx));
			}
		}
		/** Rage Skill */

		/** flame skill */
		if (_pc.flame) {
			int rnd = _random.nextInt(100);
			if (rnd < 7) {
				if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FLAME)) {
					if (_targetNpc.플레임th != null) {
						_targetNpc.플레임th.stop();
					}
				}
				L1플레임 FLAME = new L1플레임(_pc, _targetNpc);
				FLAME.begin();
				_targetNpc.플레임th = FLAME;
				_targetNpc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.FLAME, 3 * 1000);
			}
		}
		/** flame skill */

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(FOCUS_WAVE)) {
			if (_random.nextInt(100) >= (90 + _pc.getFocusWave() * 4)) {
				_pc.set포커스웨이브(_pc.getFocusWave() + 1);
				switch (_pc.getFocusWave()) {
				case 1:
					_pc.sendPackets(new S_SkillBrave(_pc.getId(), 11, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 11, 0), true);
					_pc.sendPackets(new S_SkillSound(_pc.getId(), 16532));
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16532), true);
					break;
				case 2:
					_pc.sendPackets(new S_SkillBrave(_pc.getId(), 12, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 12, 0), true);
					_pc.sendPackets(new S_SkillSound(_pc.getId(), 16533));
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16533), true);
					break;
				case 3:
					_pc.set포커스웨이브(0);
					_pc.sendPackets(new S_SkillBrave(_pc.getId(), 10, _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(FOCUS_WAVE)), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillBrave(_pc.getId(), 10, 0), true);
					_pc.sendPackets(new S_SkillSound(_pc.getId(), 16540));
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_pc.getId(), 16540), true);
					break;
				}
			}
		}

		if (secondw) {
			weaponTotalDamage += calcSAttrEnchantDmg();
		} else {
			weaponTotalDamage += calcAttrEnchantDmg();
		}

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(DOUBLE_BRAKE) && (_weaponType2 == 54 || _weaponType2 == 58)) {
			int rnd = 33;
			int c = _pc.getLevel() - 80;
			if (_pc.getLevel() >= 90) {
				rnd += 10;
			} else if (_pc.getLevel() >= 85) {
				rnd += 9;
			} else if (_pc.getLevel() >= 80) {
				rnd += 8;
			} else if (_pc.getLevel() >= 75) {
				rnd += 7;
			} else if (_pc.getLevel() >= 70) {
				rnd += 6;
			} else if (_pc.getLevel() >= 65) {
				rnd += 5;
			} else if (_pc.getLevel() >= 60) {
				rnd += 4;
			} else if (_pc.getLevel() >= 55) {
				rnd += 3;
			} else if (_pc.getLevel() >= 50) {
				rnd += 2;
			} else if (_pc.getLevel() >= 45) {
				rnd += 1;
			}
			if (_pc.DOUBLE_BREAK_DESTINY) {
				if (_pc.getLevel() > 80)
					rnd += (_pc.getLevel() - 80);
			}
			if (_pc.FINALBURN) {
				if (c > 8) // maximum
					c = 8;
				if (c < 3)
					c = 3; // least
			}

			if ((_random.nextInt(100) + 1) <= rnd + c) {
				weaponTotalDamage *= 3;
				double_burning = true;
			}
		}
		/** Valakas Raid Renewal (Halfas' Follower) **/
		if (_targetNpc.getNpcId() == 3310033) {
			// Halfas's followers can only be dealt with with the Death Knight buff.
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(15837)) {
				_isHit = false;
				_drainHp = 0;
				return 0;
			}
		}
		if (_pc != null) {
			if (_weaponType2 == 20 || _weaponType2 == 62) { // New Ranged Critical Crystal Mandeuk
				int Bowcritical = CalcStat.calcRangeCrit(_pc.getAbility().getTotalDex()) + _pc.getBowDmgCritical();
				int chance = _random.nextInt(100) + 1;
				if (_pc.getInventory().checkEquipped(231004)) { // Kurtz's Bow Insignia
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(231004);
					if (item.getEnchantLevel() == 6) {
						Bowcritical += 1;
					} else if (item.getEnchantLevel() == 7) {
						Bowcritical += 3;
					} else if (item.getEnchantLevel() >= 8) {
						Bowcritical += 5;
					}
				}
				if (_pc.getInventory().checkEquipped(30220)) { // Longbow of Destruction
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30220);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 2;
					}
				}
				if (_pc.getInventory().checkEquipped(189)) { // black palace
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(189);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(100189)) { // black palace
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(100189);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(293)) { // Nightmare Longbow
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(293);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(30121)) { // Pafurion's Longbow
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30121);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(30082)) { // Gaia's Wrath
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30082);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(30091)) { // Gaia's Fury (Engraved)
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(30091);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 3;
					}
				}
				if (_pc.getInventory().checkEquipped(413105)) { // Demon King's Bow
					L1ItemInstance item = _pc.getInventory().findEquippedItemId(413105);
					if (item.getEnchantLevel() >= 0) {
						Bowcritical += 1;
					}
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_WALK)) {
					Bowcritical += 2;
				}
				if (_pc != null) {
					/** Valakas Strike **/
					if (_pc.getInventory().checkEquipped(420112) || _pc.getInventory().checkEquipped(420113) || _pc.getInventory().checkEquipped(420114)
							|| _pc.getInventory().checkEquipped(420115)) { // 3rd of Valakas
						Bowcritical += 2;
						// barding
						if (chance <= 5) {
							_pc.sendPackets(new S_SkillSound(_pc.getId(), 15841));
							_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 15841));
							// weaponDamage = 10;
							// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
							// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
						}
					}
				}
				if (chance <= Bowcritical) {
					weaponDamage = weaponMaxDamage + _weaponAddDmg;
					_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // 방향세트
					_pc.sendPackets(new S_AttackCritical(_pc, _targetId, _targetX, _targetY, _weaponType2, _isHit));
					Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _targetId, _targetX, _targetY, _weaponType2, _isHit));// 새로만드셔서 ㅎ
					_critical = true;
				}
			} else {
				int chance = _random.nextInt(100) + 1;
				if (_pc != null) {
					/** 발라카스의 일격 **/
					if (_pc.getInventory().checkEquipped(420112) || _pc.getInventory().checkEquipped(420113) || _pc.getInventory().checkEquipped(420114)
							|| _pc.getInventory().checkEquipped(420115)) { // 발라카스의 3차
						// 마갑주
						if (chance <= 5) {
							_pc.sendPackets(new S_SkillSound(_pc.getId(), 15841));
							_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 15841));
							// weaponDamage = 10;
							// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
							// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
						}
					}
				}
			}
		}
		if (_target != null) { // Added Kurtz's Insignia Critical Damage
			for (L1ItemInstance item : _pc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() == 231004 || item.getItemId() == 231006) {
						if (item.getEnchantLevel() == 6)
							_weaponDoubleDmgChance += 5;
						if (item.getEnchantLevel() == 7)
							_weaponDoubleDmgChance += 15;
						if (item.getEnchantLevel() == 8)
							_weaponDoubleDmgChance += 25;
					}
					break;
				}
			}
		}
		if (_target != null) { // Added Kurtz's Insignia Critical Damage
			for (L1ItemInstance item : _pc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() == 231005) {
						if (item.getEnchantLevel() == 6)
							_weaponDoubleDmgChance += 5;
						if (item.getEnchantLevel() == 7)
							_weaponDoubleDmgChance += 10;
						if (item.getEnchantLevel() == 8)
							_weaponDoubleDmgChance += 20;
					}
					break;
				}
			}
		}

		// Dragon Slayer renewal

		double dmg = weaponTotalDamage + _statusDamage;

		if (_weaponId == 100034 && weapon.getEnchantLevel() >= 10) {
			int locx = _targetNpc.getX();
			int locy = _targetNpc.getY();
			int chance = _random.nextInt(100) + 1;
			int intel = _pc.getAbility().getTotalInt();
			if (10 >= chance) {
				dmg = _random.nextInt(intel / 2) + (intel);
				if (dmg <= 0)
					dmg = 0;
				S_EffectLocation packet = new S_EffectLocation(locx, locy, 15926);
				_pc.sendPackets(packet);
				_pc.broadcastPacket(packet);
			}

		}

		if (_weaponType2 != 20 && _weaponType2 != 62) {
			dmg += _pc.getDmgup() + _pc.getDmgupByArmor() + _pc.get_regist_PVPweaponTotalDamage() + 2;
		} else {
			dmg += _pc.getBowDmgup() + _pc.getBowDmgupByArmor() + _pc.getBowDmgupByDoll() + _pc.get_regist_PVPweaponTotalDamage() + 2;
		}

		if (_weaponType2 == 20) { // 활
			if (_weaponId == 190 || _weaponId == 100190 || _weaponId == 450029 || _weaponId == 30082 || _weaponId == 30091
					|| (_weaponId >= 11011 && _weaponId <= 11013)) { // 사이하의 활
				int add_dmg = 0;
				add_dmg = (int) (dmg + (2));
				dmg = add_dmg;
			} else if (_weaponId == 7201) {
				int add_dmg = 0;
				add_dmg = (int) (dmg + 10);
				dmg = add_dmg;
			} else if (_arrow.getItemId() == 40743) {// 수령
				dmg += 1;
			} else if (_arrow.getItemId() == 40744) {// 수령
				dmg += 2;
			} else if (_arrow.getItemId() == 40745) {// 수령
				dmg += 5;
				_hitRate += 3;
			}
		} else if (_weaponType2 == 62) { // 암 토토 렛
			int add_dmg = 0;
			if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large")) {
				add_dmg = _sting.getItem().getDmgLarge();
			} else {
				add_dmg = _sting.getItem().getDmgSmall();
			}
			if (add_dmg == 0) {
				add_dmg = 1;
			}
			dmg = dmg + _random.nextInt(add_dmg) + 1;
		}

		dmg = calcBuffDamage(dmg);

		if (_weaponType2 == 0) { // bare hands
			dmg = (_random.nextInt(5) + 4) / 4;
		} else {
			if (_weaponType == 17) { // Kiringku pc - npc
				int 키링크대미지인트 = _pc.getAbility().getTotalInt();
				int 키링크대미지스펠 = _pc.getAbility().getSp();
				int kiringkuMax = 키링크대미지인트 + 키링크대미지스펠;
				int kiringkuRandom = (_random.nextInt(키링크대미지인트) + 1) + (_random.nextInt(키링크대미지스펠) + 1);
				/*
				 * if(_target.getSkillEffectTimerSet().hasSkillEffect(L1SkillId. ERASE_MAGIC)){
				 * _target.getSkillEffectTimerSet().removeSkillEffect (L1SkillId.ERASE_MAGIC); }
				 */
				dmg += kiringkuRandom;

				dmg = calcMrDefense(_target.getResistance().getEffectedMrBySkill(), dmg);

				if (kiringkuRandom >= kiringkuMax) {
					_critical = true;
				}
			} else if (_weaponType == 18) {
				dmg += WeaponSkill.getChainSwordDamageNpc(_pc, _target, _weaponId, _weaponEnchant);
			}

			if (secondw) {
				switch (_SweaponId) {
				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
				case 110051:
				case 110052:
				case 110053:
				case 110054:
				case 110055:
				case 110056:
				case 110057:
				case 110058:
					WeaponSkill.블레이즈쇼크(_pc, _target, _weaponEnchant);
					break;
				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _target);
					break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _target);
					break;
				case 100047:
				case 47:
				case 450031:
					WeaponSkill.getSilenceSword(_pc, _target, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 30095:
				case 222204:
				case 450046:
					dmg += WeaponSkill.get수결지Damage(_pc, _target, _weaponEnchant);
					break;
				case 294: // Legendary Lord's Sword 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					// dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				case 30081:
				case 30090:
				case 31081:
				case 222207:
				case 450050:
					dmg += WeaponSkill.hyperionsDespair(this._pc, this._target, 12248, this._weaponEnchant);
					break;
				case 3000081:

					dmg += WeaponSkill.Icekiring11(_pc, _target, _weaponEnchant);
				case 30080:
				case 30089:
				case 31080:
				case 222205:
					dmg += WeaponSkill.getChainSwordDamageNpc(_pc, _target, _weaponId, _weaponEnchant);
					break;

				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _target, _weaponEnchant);
					break;
				case 30110:
					dmg += WeaponSkill.getRepperSwordDamage(_pc, _target, _weaponEnchant);
					break;
				case 30111:
					dmg += WeaponSkill.getgrankainroar(_pc, _target, _weaponEnchant);
					break;
				case 30112:
					dmg += WeaponSkill.ainhasadblow(_pc, _target, _weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _target, _weaponEnchant);
					break;
				// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
				// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _target, _weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 191:// 살천의활
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9361);
					break;
				// case 114: WeaponSkill.getGunjuSword(_pc,
				// _target,_weaponEnchant); break;
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target);
					break;
				case 204:
				case 100204:
				case 30087:
				case 30096:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _target);
					break;
				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target, _weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3940);
					break;
				/*
				 * case 410000: case 410001: case 450009: case 450014: case 450004: dmg +=
				 * WeaponSkill.getChainSwordDamage(_pc, _target); break;
				 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3704);
					break;
				case 7228:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 5524);
					break;
				// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
				// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 263:
				case 4500028:
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// 각궁
				// case 263:
				// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
				// break;//호지
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 0);
					break;
				case 415015:
				case 415016:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target, _weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;

				case 7238:
					dmg += WeaponSkill.salkiNPc(_pc, _target, _weaponEnchant);
					break;
				case 450008:
				case 450010:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break;
				case 450011:
				case 450012:
				case 450013:
					WeaponSkill.이블트릭(_pc, _target, _weaponEnchant);
					break;
				case 450022:
				case 450024:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant, 8981);
					break;
				case 450023:
				case 450025:
					WeaponSkill.이블트릭(_pc, _target, _weaponEnchant, 8981);
					break;
				case 100259:
				case 100260:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9359);
					break; // Crow of Destruction, Dual Swords
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target, _weaponEnchant);
					break; // Resonance key link
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 7398);
					break; // Chainsword of Illusion
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break; // red lion weapon
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3939);
					break; // Fantasy weapon (10 sword event)
				case 291: // Zeroth's Staff
					dmg += WeaponSkill.getZerosDamageNpc(_pc, _target, _weaponEnchant);
					break;
				case 203025:
				case 203026: // to fight
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getSSaulHonDamage(this._pc, this._target);
					break;
				case 293: // 악몽의장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					break;
				case 30117: // 발라카스의 장검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon1Damage(this._pc, this._target);
					break;
				case 30118: // 발라카스의 양손검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon2Damage(this._pc, this._target);
					break;
				case 30121: // 파푸리온의 장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon1Dmg(this._pc, this._target);
					break;
				case 30122: // 파푸리온의 이도류
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon2Damage(this._pc, this._target);
					break;
				case 30115: // 안타라스의 도끼
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAntaHon1Damage(this._pc, this._target);
					break;
				case 30116: // 안타라스의 지팡이
					dmg += WeaponSkill.getAntaHon2DamageNpc(_pc, _target, _weaponEnchant);
					break;
				case 30119: // 린드비오르의 체인소드
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getLindHon1Damage(this._pc, this._target);
					break;
				case 30120:// 린드비오르의 키링크
					dmg += WeaponSkill.getLindHon1Damage(_pc, _target, _weaponEnchant);
					break;
				case 90084:
					WeaponSkill.섬멸자의체인소드(_pc);
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAnnihilationDmg(this._pc, this._target);
					break;
				default:
					break;
				}
			} else {
				switch (_weaponId) {

				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
				case 110051:
				case 110052:
				case 110053:
				case 110054:
				case 110055:
				case 110056:
				case 110057:
				case 110058:
					WeaponSkill.블레이즈쇼크(_pc, _target, _weaponEnchant);
					break;

				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _target);
					break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _target);
					break;
				case 100047:
				case 47:
				case 450031:
					WeaponSkill.getSilenceSword(_pc, _target, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 30095:
				case 222204:
				case 450046:
					dmg += WeaponSkill.get수결지Damage(_pc, _target, _weaponEnchant);
					break;
				case 294: // Legendary Lord's Sword 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					// dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				case 30081:
				case 30090:
				case 31081:
				case 222207:
				case 450050:
					dmg += WeaponSkill.hyperionsDespair(this._pc, this._target, 12248, this._weaponEnchant);
					break;
				case 3000081:
					dmg += WeaponSkill.Icekiring11(_pc, _target, _weaponEnchant);
				case 30080:
				case 30089:
				case 31080:
				case 222205:
					dmg += WeaponSkill.getChainSwordDamageNpc(_pc, _target, _weaponId, _weaponEnchant);
					break;
				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _target, _weaponEnchant);
					break;
				case 30110:
					dmg += WeaponSkill.getRepperSwordDamage(_pc, _target, _weaponEnchant);
					break;
				case 30111:
					dmg += WeaponSkill.getgrankainroar(_pc, _target, _weaponEnchant);
					break;
				case 30112:
					dmg += WeaponSkill.ainhasadblow(_pc, _target, _weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _target, _weaponEnchant);
					break;
				// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
				// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _target, _weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target, _weaponEnchant);
					break;
				case 191:// Killer's Bow
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9361);
					break;
				// case 114: WeaponSkill.getGunjuSword(_pc,
				// _target,_weaponEnchant); break;
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target);
					break;
				case 204:
				case 100204:
				case 30087:
				case 30096:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _target);
					break;
				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target, _weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3940);
					break;
				/*
				 * case 410000: case 410001: case 450009: case 450014: case 450004: dmg +=
				 * WeaponSkill.getChainSwordDamage(_pc, _target); break;
				 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3704);
					break;
				case 7228:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 5524);
					break;
				// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
				// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 263:
				case 4500028:
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// 각궁
				// case 263:
				// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
				// break;//호지
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 0);
					break;
				case 415015:
				case 415016:
					WeaponSkill.체이서(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target, _weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;

				case 7238:
					dmg += WeaponSkill.salkiNPc(_pc, _target, _weaponEnchant);
					break;
				case 450008:
				case 450010:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break;
				case 450011:
				case 450012:
				case 450013:
					WeaponSkill.이블트릭(_pc, _target, _weaponEnchant);
					break;
				case 450022:
				case 450024:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant, 8981);
					break;
				case 450023:
				case 450025:
					WeaponSkill.이블트릭(_pc, _target, _weaponEnchant, 8981);
					break;
				case 100259:
				case 100260:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 9359);
					break; // Crow of Destruction, Dual Swords
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target, _weaponEnchant);
					break; // Resonance key link
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 7398);
					break; // Chainsword of Illusion
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
					break; // red lion weapon
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target, _weaponEnchant, 3939);
					break; // Fantasy weapon (10 sword event)
				case 291: // Zeroth's Staff
					dmg += WeaponSkill.getZerosDamageNpc(_pc, _target, _weaponEnchant);
					break;
				case 203025:
				case 203026: // to fight
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getSSaulHonDamage(this._pc, this._target);
					break;
				case 293: // 악몽의 장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getNightmareDmg(this._pc, this._target);
					break;
				case 30117: // 발라카스의 장검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon1Damage(this._pc, this._target);
					break;
				case 30118: // 발라카스의 양손검
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getBalaHon2Damage(this._pc, this._target);
					break;
				case 30121: // 파푸리온의 장궁
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon1Dmg(this._pc, this._target);
					break;
				case 30122: // 파푸리온의 이도류
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getPapuHon2Damage(this._pc, this._target);
					break;
				case 30115: // 안타라스의 도끼
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAntaHon1Damage(this._pc, this._target);
					break;
				case 30116: // 안타라스의 지팡이
					dmg += WeaponSkill.getAntaHon2DamageNpc(_pc, _target, _weaponEnchant);
					break;
				case 30119: // 린드비오르의 체인소드
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getLindHon1Damage(this._pc, this._target);
					break;
				case 30120:// 린드비오르의 키링크
					dmg += WeaponSkill.getLindHon1Damage(_pc, _target, _weaponEnchant);
					break;
				case 90084:
					WeaponSkill.섬멸자의체인소드(_pc);
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getAnnihilationDmg(this._pc, this._target);
					break;
				case 7227:
					if (this._weaponEnchant <= 9)
						break;
					dmg += WeaponSkill.getTyphoonDmg(this._pc, this._target);
					break;
				default:
					break;
				}
			}
			if (_weaponId == 450009) {
				dmg += WeaponSkill.getChainSwordDamageNpc(_pc, _target, _weaponId, _weaponEnchant);
				WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
			}
			if (secondw) {
				if (_SweaponId == 7233) {
					WeaponSkill.이블리버스(_pc, _target, _SweaponEnchant);
				}
			} else {
				if (_weaponId == 7233) {
					WeaponSkill.이블리버스(_pc, _target, _weaponEnchant);
				}
			}
		}

		try {
			weaponTotalDamage += WeaponAddDamage.getInstance().getWeaponAddDamage(_weaponId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (_weaponId == 276 || _weaponId == 100276 || _weaponId == 292 || _weaponId == 293) {
			// Crow of Wrath, Longbow of Nightmare
			if (_pc.getLawful() <= -20000)
				dmg += 5;
			else if (_pc.getLawful() <= -10000)
				dmg += 3;
			else if (_pc.getLawful() <= 0)
				dmg += 1;
		}

		if (_weaponId >= 90085 && _weaponId <= 90092) { // halloween
			dmg += _weaponEnchant;
		}

		if (_pc.getDollList().size() > 0) {
			for (L1DollInstance doll : _pc.getDollList()) {
				if (_weaponType2 != 20 && _weaponType2 != 62) {
					int d = doll.getDamageByDoll(_targetNpc);
					if (d > 0 && doll.getDollType() == L1DollInstance.DOLLTYPE_GREG)
						_pc.setCurrentHp(_pc.getCurrentHp() + _random.nextInt(5) + 1);
					dmg += d;
				}

				if (doll.getDollType() == L1DollInstance.DOLLTYPE_BLACKELDER || doll.getDollType() == L1DollInstance.DOLLTYPE_DEATHKNIGHT
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_BLESSEDDEATHKNIGHT || doll.getDollType() == L1DollInstance.DOLLTYPE_BLESSEDBLACKELDER
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_헌신1등급) {
					dmg += doll.getMagicDamageByDoll(_targetNpc);
				}
				doll.attackPoisonDamage(_pc, _targetNpc);
			}
		}

		if (adddmg != 0)
			dmg += adddmg;

		// Added bless Weapon Damage 0 is blessed
		if (weapon != null) {
			if (weapon.getBless() == 0) {
				dmg += 1;
			}
		}

		/*
		 * if(Sweapon != null){ if(Sweapon.getBless() == 0){ dmg += 1; } }
		 */
		if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) {// Armor Break
			if (_weaponType2 != 20 || _weaponType2 != 62) {
				dmg *= 1.6;
			}
		}

		/*
		 * if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SLASH)) { dmg += 50;
		 * _pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 6591), true);
		 * Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 6591),
		 * true); _pc.getSkillEffectTimerSet().killSkillEffectTimer(BURNING_SLASH); }
		 */

		dmg += 룸티스검귀추가데미지(); // Lumtis Sword Demon Additional Damage

		/*
		 * if(_weaponType1 != 17 //When not a key link
		 */
		dmg -= calcNpcDamageReduction();

		if (_targetNpc.getNpcId() == 45640) {  // Unicorn why?
			dmg /= 2;
		}
		// Pets from players, attack Samon
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetNpc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}

		// Reduced damage to undead at night
		if (isUndeadDamage(_targetNpc)) {
			dmg -= dmg * 0.10;
		}

		if (_weaponId == 7 || _weaponId == 35 || _weaponId == 48 || _weaponId == 73 || _weaponId == 105 || _weaponId == 120 || _weaponId == 147
				|| _weaponId == 156 || _weaponId == 174 || _weaponId == 175 || _weaponId == 224 || _weaponId == 7232) {
			if (Config.traineeWeaponBalance != 0) {
				double balance = Config.traineeWeaponBalance * 0.01;
				dmg -= dmg * balance; // 35% down on apprentice weapons
			}
		}

		if (_weaponAttr != 0)
			dmg += addattrdmg(_weaponAttr);

		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0;
		}
		// Moved to change blood absorption formula.
		if (_weaponId == 262) { // bloodsucker
			if (dmg >= 30 && _random.nextInt(100) <= 90) {
				// _pc.sendPackets(new
				// S_SystemMessage("피흡량 :"+_drainHp+" 데미지 :"+dmg));
				int _dhp = (int) ((dmg - 10) / 10);
				_drainHp += _dhp;// +_random.nextInt(3)+1;
			}
		}

		/** If you are hitting a pet, set it so that only 20% of the damage is received */
		if (_targetNpc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _targetNpc;
			if (Pet.SkillCheck(L1SkillId.DogBlood))
				dmg *= 0.75;
			dmg -= Pet.SkillsTable(L1PetInstance.철갑등껍질);
			dmg /= 2;
		}

		return (int) dmg;
	}

	// ●●●● Calculate damage from NPC to player ●●●●
	private int calcNpcPcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 1D; // 0
		if (_targetPc instanceof L1RobotInstance) {
			if (_targetPc.isRobot()) {
				return _random.nextInt(2) + 10;
			} else {
				return 10;
			}
		}
		/** Information about pet-related modem */
		/** Level * 2 + Damage Stat Damage + Skill Damage Setting */
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;
			int DmgTamp = 20;
			if (Pet.getLevel() > 10) {
				DmgTamp += _Random.nextInt(Pet.getLevel() / 2);
			} else
				DmgTamp += Pet.getLevel();
			DmgTamp += Pet.getHunt() + (Pet.getElixirHunt() * 5);
			int Bonus = Pet.getHunt() + Pet.getElixirHunt();
			if (Bonus >= 20)
				DmgTamp += (Bonus - 10) / 10;
			DmgTamp += Pet.SkillsTable(L1PetInstance.칠흑의발톱) + Pet.SkillsTable(L1PetInstance.도살의발톱);
			dmg = DmgTamp;
		} else {
			if (lvl < 10) // mob level less than 10
				dmg = _random.nextInt(lvl) + 10D + _npc.getAbility().getTotalStr() + 2;
			else if (lvl >= 10 && lvl < 20) // mob rep 10 ~ 49
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 2;
			else if (lvl >= 20 && lvl < 30) // mob rep 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 4;
			else if (lvl >= 30 && lvl < 40) // mob rep 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 8;
			else if (lvl >= 40 && lvl < 50) // mob rep 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 12;
			else if (lvl >= 50 && lvl < 60) // mob rep 70 ~ 79
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 16;
			else if (lvl >= 60 && lvl < 70) // mob rep 80 ~ 86
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 20;
			else if (lvl >= 70 && lvl < 80) // mob rep 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 30;
			else if (lvl >= 80 && lvl < 87) // mob rep 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + 40;
			else if (lvl >= 87) // mob rep 87 more
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() * 2 + 100;
		}

		if (_target != null) {
			int chance = _random.nextInt(100) + 1;
			int rewardHp = 0;
			int plus = 0;
			if (_targetPc.getInventory().checkEquipped(420104) // Fafurion Armours
					|| _targetPc.getInventory().checkEquipped(420105)
					|| _targetPc.getInventory().checkEquipped(420106)
					|| _targetPc.getInventory().checkEquipped(420107)) {
				if (chance <= 5) {
					L1ItemInstance item = _targetPc.getInventory().findEquippedItemId(420104);
					L1ItemInstance item1 = _targetPc.getInventory().findEquippedItemId(420105);
                    L1ItemInstance item2 = _targetPc.getInventory().findEquippedItemId(420106);
                    L1ItemInstance item3 = _targetPc.getInventory().findEquippedItemId(420107);
					if (item.getEnchantLevel() <= 6) {
						plus = 0;
					} else if (item.getEnchantLevel() >= 7 && item.getEnchantLevel() <= 9) {
                        plus = item.getEnchantLevel() - 6;
                    } else if (item1.getEnchantLevel() >= 7 && item1.getEnchantLevel() <= 9) {
                        plus = item.getEnchantLevel() - 6;
                    } else if (item2.getEnchantLevel() >= 7 && item2.getEnchantLevel() <= 9) {
                        plus = item.getEnchantLevel() - 6;
                    } else if (item3.getEnchantLevel() >= 7 && item3.getEnchantLevel() <= 9) {
                        plus = item.getEnchantLevel() - 6;
                    } else if (item.getEnchantLevel() > 9 || item1.getEnchantLevel() > 9 || item2.getEnchantLevel() > 9
                            || item3.getEnchantLevel() > 9) {
                        plus = 3;
                    }
                    if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.POLLUTE_WATER)) {
                    	rewardHp += (50 + _random.nextInt(15) + (plus * 10)) / 2; // half from pollute water
                    } else if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WATER_LIFE)) {
                    	_target.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.WATER_LIFE);
                        rewardHp += (50 + _random.nextInt(15) + (plus * 10)) * 2; // double for water life
                        _targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2188), true);
                    } else {
                    rewardHp += 50 + _random.nextInt(15) + (plus * 10); // recover 50 + random 1-30
                    _targetPc.setCurrentHp(_targetPc.getCurrentHp() + rewardHp);
                    _targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2187));
                    _targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 2187));
                    }
				}
			}
			/*for (L1ItemInstance item : _targetPc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() >= 420104 && item.getItemId() <= 420107) {
						int addchan = item.getEnchantLevel() - 5;
						if (addchan < 0)
							addchan = 0;
						if (chance1 < 6 + addchan) {
							// 123456 일때 80~100
							// 파푸 가호 7일때 120~140 / 8일때 140~160 9일때 160~180
							int addhp = _random.nextInt(20) + 1;
							int basehp = 80;
							if (item.getEnchantLevel() == 7)
								basehp = 120;
							if (item.getEnchantLevel() == 8)
								basehp = 140;
							if (item.getEnchantLevel() == 9)
								basehp = 160;
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + basehp + addhp);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2187), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 2187), true);
						}
						break;
					} else if (item.getItemId() >= 420108 && item.getItemId() <= 420111) {
						int addchan = item.getEnchantLevel() - 5;
						if (addchan < 0)
							addchan = 0;
						if (chance1 < 6 + addchan) {
							if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.린드가호딜레이)) {
								break;
							}
							if (item.getItemId() == 420108) // strength
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 8 + _random.nextInt(7));
							else if (item.getItemId() == 420109) // prognosis
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 9 + _random.nextInt(7));
							else if (item.getItemId() == 420110) // prognosis
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 16 + _random.nextInt(9));
							else if (item.getItemId() == 420111) // horsepower
								_targetPc.setCurrentMp(_targetPc.getCurrentMp() + 20 + _random.nextInt(11));

							_targetPc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.린드가호딜레이, 4000);

							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2188), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 2188), true);
						}
						break;
					} else if (item.getItemId() == 21255) {
						if (chance1 < 4) {
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + 31);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 2183), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 2183), true);
						}
						break;
						// Divine Elf Plate Armor
					} else if (item.getItemId() == 222351) {
						if (chance1 < 3) {
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + 5);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 15355), true);
							Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 15355), true);
						}
						break;
					}
				}

			}*/
		}
		int chance66 = _random.nextInt(100) + 1;
		if (_target != null) {
			int dmg2 = 0;
			int plus = 0;
			if (_targetPc.getInventory().checkEquipped(222351)) {
				if (chance66 <= 3) { // Originally 5
					if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.POLLUTE_WATER)) {
						dmg2 += 15;
					}
					if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WATER_LIFE)) {
						dmg2 += 15;
					}
					dmg2 += 15;
					_targetPc.setCurrentHp(_targetPc.getCurrentHp() + dmg2);
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 15355));
					_targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 15355));
				}
			}
		}

		dmg += _npc.getDmgup();

		if (isUndeadDamage()) {
			dmg *= 1.10;
		}

		dmg = dmg * getLeverage() / 10;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg -= (dmg * Config.IMMUNE_FACTOR);
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(LUCIFER)) {
			dmg -= dmg / 10;
		}

		/** Reduce Dragon's Blessing damage **/
		if (_targetPc._dragonbless_1) {
			dmg -= 2;
		} else if (_targetPc._dragonbless_2) {
			dmg -= 3;
		} else if (_targetPc._dragonbless_3) {
			dmg -= 4;
		}

		if (_targetPc.isWizard()) {
			dmg -= dmg / 10;
		}

		if (_npc.isWeaponBreaked()) { // NPC during weapon break.
			dmg /= 2;
		}

		dmg -= _targetPc.getDamageReductionByArmor(); // Damage Reduction by Defense Mechanism

		/** magic doll stone golem **/
		if (_npc.getNpcTemplate().getBowActId() == 0 && _targetPc.getDollList().size() > 0) {
			for (L1DollInstance doll : _targetPc.getDollList()) {
				if (doll.getDollType() == L1DollInstance.DOLLTYPE_STONEGOLEM || doll.getDollType() == L1DollInstance.DOLLTYPE_DRAKE
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_BLESSEDDRAKE)
					dmg -= doll.getDamageReductionByDoll();
			}
		}
		/** 마법인형 돌골램 **/

		L1ItemInstance 반역자의방패 = _targetPc.getInventory().checkEquippedItem(21093);
		if (반역자의방패 != null) {
			int chance = 5 + (반역자의방패.getEnchantLevel() * 2);
			if (_random.nextInt(100) <= chance) {
				dmg -= 50;
				_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 6320));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 6320));
			}
		}
		L1ItemInstance 요정족방패 = _targetPc.getInventory().checkEquippedItem(222355);
		if (요정족방패 != null) {
			int chance = 3 + (요정족방패.getEnchantLevel() * 2);
			if (_random.nextInt(100) <= chance) {
				dmg -= 25;
				_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 14543));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 14543));
			}
		}
		if (_targetPc.isArmourGuard) { // 스페셜요리에 의한 데미지 경감
			int d = _targetPc.getAC().getAc() / 10;
			if (d < 0) {
				dmg += d;
			} else {
				dmg -= d;
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) { // 스페셜요리에
			// 의한
			// 데미지
			// 경감
			dmg -= 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING2)) {
			dmg -= 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_닭고기) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_연어)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_칠면조) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_한우)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐닭고기) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐연어)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐칠면조) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐한우)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(METIS_SOUP) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(PSY_COOL_DRINK))
			dmg -= 5;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(METIS_CUISINE) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(SPICY_RAMEN))
			dmg -= 5;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(메티스축복주문서) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ENERGY_OF_BLACK))
			dmg -= 3;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EARTH_BLESS))
			dmg -= 2;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		if (_weaponType != 17 && _targetPc.infinity_A) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 45) {
				targetPcLvl = 45;
			}
			int dmg2 = ((targetPcLvl - 41) / 4);
			dmg -= dmg2 > 0 ? dmg2 : 0;// +1
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MAJESTY)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 80) {
				targetPcLvl = 80;
			}
			dmg -= (targetPcLvl - 80) + 3;
		}

		if (_targetPc.DRAGONSKIN) {
			dmg -= 2;
		}
		if(_targetPc.DRAGONSKIN && _targetPc.getLevel() >= 80) {
			int ddmg = _targetPc.getLevel() - 78;
			int i = (ddmg / 2) * 1;
			dmg -= 2 + i;

	}
		if (_targetPc.글로리어스) {
			if (_random.nextInt(100) < 2 + 8 - 5) {
				dmg -= 30;
				_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 19318));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 19318));
			}
		}

		dmg -= rumtisRedEarringDReduc();

		// Attack on player from pet, Samon
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetPc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_npc instanceof L1PetInstance) {
				dmg /= 8;
			} else if (_npc instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) _npc;
				if (summon.isExsistMaster()) {
					if (summon.getNpcTemplate().get_npcId() == 81104 || summon.getNpcTemplate().get_npcId() == 81240) {
						dmg /= 5;
					} else {
						dmg /= 2;
					}
				}
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ILLUSION_AVATAR) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(CUBE_AVATAR)) {
			dmg += dmg / 20;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			dmg -= 4;
		}
		if (_targetPc.DRAGONSKIN) {
			dmg -= 3;
		}
		if(_targetPc.DRAGONSKIN && _targetPc.getLevel() >= 80) {
			int ddmg = _targetPc.getLevel() - 78;
			int i = (ddmg / 2) * 1;
			dmg -= 3 + i;

	}
		if (_targetPc.글로리어스) {
			if (_random.nextInt(100) < 2 + 8 - 5) {
				dmg -= 30;
				_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 19318));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 19318));
			}
		}

		/*
		 * //상아탑 장갑류 착용시 if (_targetPc.getInventory().checkEquipped(20173) ||
		 * _targetPc.getInventory().checkEquipped(21103) ||
		 * _targetPc.getInventory().checkEquipped(21108)) dmg *= 1.05;
		 */

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}
		/*
		 * if (_weaponType != 62 || _weaponType != 20 &&
		 * _targetPc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) { //
		 * 아머브레이크수정중 dmg *= 1.48; }
		 */
		addNpcPoisonAttack(_npc, _targetPc);

		if (_npc instanceof L1PetInstance || _npc instanceof L1SummonInstance) {
			if (CharPosUtil.getZoneType(_targetPc) == 1) {
				_isHit = false;
			}
		}

		if(_targetPc.is_halpas_armor()) {
			L1ItemInstance halpas_armor = null;
			for(L1ItemInstance armor : _target.getInventory().getItems()) {
				if(armor.getItem().getType2() != 2) {
					continue;
				}
				if((armor.getItem().getItemId() >= 27528 && armor.getItem().getItemId() <= 27530) && armor.isEquipped()) {
					halpas_armor = armor;
					break;
				}
			}
			halpasArmorBless(_targetPc, halpas_armor);
		}

		/** If the attacker is a pet, apply critical and combo to that pet. */
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;
			/** 펫 관련 치명타 공격 : 일반 공격 */
			int Random = _Random.nextInt(100) + 1;
			int 치명확률 = 5;
			if (치명확률 >= Random) {
				dmg *= 1.25;
				_critical = true;
			}

			/** Pet Related Magic Critical Hit: Property Magic Attack Power */
			Random = _Random.nextInt(100) + 1;
			int[] 속성정보 = Pet.AttributeDmg();
			if (속성정보[0] >= Random) {
				double calcAttr = calcAttrResistance(Pet.getNpcTemplate().get_weakAttr());
				dmg += (int) (1.0 - calcAttr) * 속성정보[1];
				_속성공격임팩트 = 속성정보[2];
				_속성공격 = true;
			}
			속성정보 = null;

			/** Pet Related Magic Critical Hit: Property Magic Attack Power */
			Random = _Random.nextInt(100) + 1;
			int[] 속성치명정보 = Pet.AttributeCriticalDmg();
			if (속성치명정보 != null && 속성치명정보[0] >= Random) {
				double calcAttr = calcAttrResistance(Pet.getNpcTemplate().get_weakAttr());
				dmg += (int) (1.0 - calcAttr) * 속성치명정보[1];
				_속성치명임팩트 = 속성치명정보[2];
				_속성치명 = true;
			}
			속성치명정보 = null;

			/** black blood work */
			int[] 흡혈 = Pet.SkillsBlood();
			if (흡혈[0] != 0) {
				Random = _Random.nextInt(100) + 1;
				if (흡혈[0] >= Random) {
					dmg += 흡혈[1];
					Pet.setCurrentHp(Pet.getCurrentHp() + 흡혈[1]);
					L1PcInstance PetMaster = (L1PcInstance) Pet.getMaster();
					if (PetMaster != null)
						PetMaster.sendPackets(new S_PetWindow(S_PetWindow.PatStatUpDate, Pet), true);
				}
			}
			흡혈 = null;

			/** Reduction ignored part */
			int DmgReduction = Pet.SkillsTable(L1PetInstance.백상아리이빨);
			if (DmgReduction != 0) {
				int DmgHit = _targetPc.getDamageReductionByArmor() - DmgReduction;
				if (DmgHit < 0) {
					dmg += _targetPc.getDamageReductionByArmor();
				} else {
					dmg += DmgReduction - DmgHit;
				}
			}

			/** Critical applied if in fighting spirit state */
			if (Pet.SkillCheck(L1SkillId.Fighting)) {
				if (Pet.getFightingTarget() == null)
					Pet.setFightingTarget(_targetPc);
				if (Pet.getFightingTarget() != _targetPc) {
					Pet.setFightingCombo(0);
					Pet.setFightingTarget(_targetPc);
				}
			}
			int DmgCombo = Pet.SkillsTable(L1PetInstance.포식자);
			if (DmgCombo != 0)
				DmgCombo = DmgCombo / 100;
			switch (Pet.getFightingCombo()) {
			case 0:
				dmg *= 1.10 + DmgCombo;
				Pet.setFightingCombo(1);
				break;

			case 1:
				dmg *= 1.25 + DmgCombo;
				Pet.setFightingCombo(2);
				break;

			case 2:
				dmg *= 1.50 + DmgCombo;
				Pet.setFightingCombo(0);
				Broadcaster.broadcastPacket(Pet, new S_SkillSound(Pet.getId(), 17326), true);
				break;
			}
			if (Pet.SkillCheck(L1SkillId.DogBlood)) {
				dmg *= 1.25;
			}
			/** Non-pet damage reduction 0.45-45% */
		} else
			dmg -= dmg * 0.45; // Modified on February 1, 2015.

		if (_targetPc.getInventory().checkEquipped(21104) || _targetPc.getInventory().checkEquipped(21111)) {
			// dmg += dmg*0.2;
			if (Config.traineeArmourBalance != 0) {
				double balance = Config.traineeArmourBalance * 0.01;
				dmg += dmg * balance; // 35% down on apprentice weapons
			}
		}

		// Increase apprentice armor damage.

		/*
		 * //모든 보스 10% 상향 if(_npc.getMaxHp() >= 10000){ dmg += dmg*0.10; }
		 */

		// Cold Slime
		if (_npc.getNpcId() == 90008) {
			dmg -= dmg * 0.30;
		}

		if (dmg <= 0) {
			_isHit = false;
		}

		/*
		 * if (dmg > 0 && _npc instanceof L1SummonInstance || _npc instanceof
		 * L1PetInstance) dmg /= 8;
		 */

		return (int) dmg;
	}

	// ●●●● Calculate damage from NPC to NPC ●●●●
	private int calcNpcNpcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 0;
		/** Information about pet-related modem */
		/** 레벨 * 2 + 데미지 스텟 데미지 + 스킬 데미지 설정 */
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;
			int DmgTamp = 30;
			if (Pet.getLevel() > 10) {
				DmgTamp += _Random.nextInt(Pet.getLevel() / 1);
			} else
				DmgTamp += Pet.getLevel();
			DmgTamp += Pet.getHunt() + (Pet.getElixirHunt() * 5);
			int Bonus = Pet.getHunt() + Pet.getElixirHunt();
			if (Bonus >= 20)
				DmgTamp += (Bonus - 10) / 10;
			DmgTamp += Pet.SkillsTable(L1PetInstance.칠흑의발톱) + Pet.SkillsTable(L1PetInstance.도살의발톱);
			dmg = DmgTamp;
		} else {
			dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() + (lvl * lvl / 100);
		}

		if (isUndeadDamage()) {
			dmg += 20;
		}

		dmg = dmg * getLeverage() / 10;

		dmg -= calcNpcDamageReduction();

		if (_npc.isWeaponBreaked()) { // NPC during weapon break.
			dmg /= 2;
		}
		if (_targetNpc.getNpcId() == 45640) {
			dmg /= 2;
		}
		if (_npc instanceof L1SummonInstance) {
			if (_npc.getNpcId() != 81104)
				dmg *= 0.7;
		}
		if (_targetNpc instanceof L1SummonInstance) {
			if (_targetNpc.getNpcId() != 81104)
				dmg *= 1.5;
		} // ARMOR_BREAK
		/*
		 * if (_targetNpc instanceof L1PetInstance) { dmg *= 1.6; }
		 */
		/*
		 * if (_weaponType != 62 || _weaponType != 20 &&
		 * _targetPc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) { //
		 * 아머브레이크수정중 dmg *= 1.48; }
		 */
		addNpcPoisonAttack(_npc, _targetNpc);

		// Reduced undead night damage
		if (isUndeadDamage(_targetNpc)) {
			dmg -= dmg * 0.10;
		}

		if (dmg <= 0)
			_isHit = false;

		/** 공격자가 펫이라면 그 펫에 대한 크리티컬 과 콤보 적용 */
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;
			/** 펫 관련 치명타 공격 : 일반 공격 */
			int Random = _Random.nextInt(100) + 1;
			/** 조작 엔피씨라면 확율 5프로 증가 */
			int 치명확률 = 20 + (Pet.isFabrication() ? 30 : 0);
			if (치명확률 >= Random) {
				dmg *= 1.5;
				_critical = true;
			}

			/** 펫 관련 마법 치명타 : 속성 마법 공격력 */
			Random = _Random.nextInt(100) + 1;
			int[] 속성정보 = Pet.AttributeDmg();
			/** 조작 엔피씨라면 확율 5프로 증가 */
			if (Pet.isFabrication())
				속성정보[0] += 10;
			if (속성정보[0] >= Random) {
				double calcAttr = calcAttrResistance(Pet.getNpcTemplate().get_weakAttr());
				dmg += (int) (1.0 - calcAttr) * 속성정보[1];
				_속성공격임팩트 = 속성정보[2];
				_속성공격 = true;
			}
			속성정보 = null;

			/** 펫 관련 마법 치명타 : 속성 마법 공격력 */
			Random = _Random.nextInt(100) + 1;
			int[] 속성치명정보 = Pet.AttributeCriticalDmg();
			if (Pet.isFabrication())
				속성치명정보[0] += 10;
			if (속성치명정보 != null && 속성치명정보[0] >= Random) {
				double calcAttr = calcAttrResistance(Pet.getNpcTemplate().get_weakAttr());
				dmg += (int) (1.0 - calcAttr) * 속성치명정보[1];
				_속성치명임팩트 = 속성치명정보[2];
				_속성치명 = true;
			}
			속성치명정보 = null;

			/** 흑혈 관련 작업 */
			int[] 흡혈 = Pet.SkillsBlood();
			if (흡혈[0] != 0) {
				Random = _Random.nextInt(100) + 1;
				if (흡혈[0] >= Random) {
					dmg += 흡혈[1];
					Pet.setCurrentHp(Pet.getCurrentHp() + 흡혈[1]);
					L1PcInstance PetMaster = (L1PcInstance) Pet.getMaster();
					if (PetMaster != null)
						PetMaster.sendPackets(new S_PetWindow(S_PetWindow.PatStatUpDate, Pet), true);
				}
			}
			흡혈 = null;

			/** 리덕션 무시부분 */
			int DmgReduction = Pet.SkillsTable(L1PetInstance.백상아리이빨);
			if (DmgReduction != 0) {
				int DmgHit = calcNpcDamageReduction() - DmgReduction;
				if (DmgHit < 0) {
					dmg += calcNpcDamageReduction();
				} else {
					dmg += DmgReduction - DmgHit;
				}
			}

			/** 투지 상태라면 크리티컬 적용 */
			if (Pet.SkillCheck(L1SkillId.Fighting)) {
				if (Pet.getFightingTarget() == null)
					Pet.setFightingTarget(_targetNpc);
				if (Pet.getFightingTarget() != _targetNpc) {
					Pet.setFightingCombo(0);
					Pet.setFightingTarget(_targetNpc);
				}
				int DmgCombo = Pet.SkillsTable(L1PetInstance.포식자);
				if (DmgCombo != 0)
					DmgCombo = DmgCombo / 100;
				switch (Pet.getFightingCombo()) {
				case 0:
					dmg *= 1.10 + DmgCombo;
					Pet.setFightingCombo(1);
					break;

				case 1:
					dmg *= 1.25 + DmgCombo;
					Pet.setFightingCombo(2);
					break;

				case 2:
					dmg *= 1.50 + DmgCombo;
					Pet.setFightingCombo(0);
					Broadcaster.broadcastPacket(Pet, new S_SkillSound(Pet.getId(), 17326), true);
					break;
				}
			}
			/** 만약 펫을 때리는거라면 데미지 의 20프로만 들오오도록 세팅함 */
		} else if (_targetNpc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _targetNpc;
			dmg -= Pet.SkillsTable(L1PetInstance.철갑등껍질);
			dmg /= 10;
		}
		return (int) dmg;
	}

	// ●●●● 플레이어의 데미지 강화 마법 ●●●●
	private double calcBuffDamage(double dmg) {
		if (_pc.BURNINGSPIRIT) {
			if ((_random.nextInt(100) + 1) <= 33) {
				if (double_burning) {
					if (_calcType == PC_PC) {
						_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 6532), true);
						Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 6532), true);
					} else if (_calcType == PC_NPC) {
						_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 6532), true);
						Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 6532), true);
					}
					dmg *= 1.5;
					burning = true;
				} else {
					dmg *= 1.5;
					burning = true;
				}
			}
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(BRAVE_MENTAL) && (_weaponType2 != 20 && _weaponType2 != 62 && _weaponType != 17)) {
			if ((_random.nextInt(100) + 1) <= 33) {
				dmg *= 1.5;
				burning = true;
			}
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(ELEMENTAL_FIRE) && (_weaponType2 != 20 && _weaponType2 != 62 && _weaponType != 17)) {
			if ((_random.nextInt(100) + 1) <= 33) {
				dmg *= 1.5;
				burning = true;
			}
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(ENTANGLE) && (_weaponType2 != 20 && _weaponType2 != 62 && _weaponType != 17)) {
			if ((_random.nextInt(100) + 1) <= 33) {
				dmg *= 1.5;
				burning = true;
			}
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(BLOWATTACK) && _pc.getInventory().getTypeEquipped(2, 7) != 0) {
			int chance = Config.BLOW_ATTACK_PROC;
			if (_pc.getLevel() >= 75) {
				chance = chance + (_pc.getLevel() - 74); // 1% added when level 75 or higher
			}

			if (chance >= 45) { // Edit Max Probability
				chance = 45;
			}

			if ((_random.nextInt(100) + 1) <= chance) {
				if (_calcType == PC_PC) {
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 17223), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 17223), true);
				} else if (_calcType == PC_NPC) {
					_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 17223), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 17223), true);
				}
				dmg *= 1.5;
			}
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(CYCLONE) && (_weaponType2 == 20 || _weaponType2 == 62 || _weaponType == 17)) {
			int chance = 1; // 기본 30퍼 80레벨에 35퍼 85레벨에 40퍼
			if (_pc.getLevel() > 75) {
				chance = chance + (_pc.getLevel() - 75); // 75레벨이상일때 1퍼씩 추가됨
			}

			if (chance >= 8) { // 최대 확률 수정하세요
				chance = 8;
			}

			if ((_random.nextInt(100) + 1) <= chance) {
				if (_calcType == PC_PC) {
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 17557), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 17557), true);
				} else if (_calcType == PC_NPC) {
					_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 17557), true);
					Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(), 17557), true);
				}
				dmg *= 1.4;
			}
		}
		return dmg;
	}

	/*
	 * // ●●●● 플레이어의 대미지 강화 마법 ●●●● private double calcBuffDamage(double dmg) {
	 *
	 * if (_pc.hasSkillEffect(BURNING_SPIRIT) || _pc.hasSkillEffect(BRAVE_AURA) ||
	 * _pc.hasSkillEffect(L1SkillId.ENTANGLE) || (_pc.hasSkillEffect(ELEMENTAL_FIRE)
	 * && _weaponType != 20 && _weaponType != 62)) { if ((_random.nextInt(100) + 1)
	 * <= 33) { double tempDmg = dmg; if (_pc.hasSkillEffect(BURNING_WEAPON)) {
	 * tempDmg -= 6; } if (_pc.hasSkillEffect(BERSERKERS)) { tempDmg -= 2; } double
	 * diffDmg = dmg - tempDmg; dmg = tempDmg * 1.5 + diffDmg;
	 *
	 * burning = true; //[TYTeam] 버닝스피리츠 이펙트 변경 if
	 * (_pc.hasSkillEffect(BURNING_SPIRIT)) { if (_calcType == PC_PC) {
	 * _pc.sendPackets(new S_SkillSound(_targetPc.getId(), 6532));
	 * Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 6532));
	 * } else if (_calcType == PC_NPC) { _pc.sendPackets(new
	 * S_SkillSound(_targetNpc.getId(), 6532)); Broadcaster.broadcastPacket(_pc, new
	 * S_SkillSound(_targetNpc.getId(), 6532)); } } } }else if
	 * (_pc.hasSkillEffect(L1SkillId.블로우어택)){ //기사 블로우 어택 int chance = 30; //기본 30퍼
	 * 80레벨에 35퍼 85레벨에 40퍼 if(_pc.getLevel() > 75){ chance = chance +
	 * (_pc.getLevel() - 75); //75레벨이상일때 1퍼씩 추가됨 }
	 *
	 * if(chance >= 45){ //최대 확률 수정하세요 chance = 45; }
	 *
	 * if ((_random.nextInt(100) + 1) <= chance) { double tempDmg = dmg; if
	 * (_pc.hasSkillEffect(BURNING_WEAPON)) { tempDmg -= 6; } if
	 * (_pc.hasSkillEffect(BERSERKERS)) { tempDmg -= 2; } double diffDmg = dmg -
	 * tempDmg; dmg = tempDmg * 1.5 + diffDmg;
	 *
	 * if (_calcType == PC_PC) { _pc.sendPackets(new
	 * S_SkillSound(_targetPc.getId(),17223)); Broadcaster.broadcastPacket(_pc, new
	 * S_SkillSound(_targetPc.getId(), 17223)); } else if (_calcType == PC_NPC) {
	 * _pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 17223));
	 * Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetNpc.getId(),
	 * 17223)); } //_pc.sendPackets(new S_SystemMessage("블로우 어택 발동!")); } }
	 *
	 * return dmg; }
	 */

	// ●●●● 플레이어의 AC에 의한 데미지 경감 ●●●●
	/*
	 * private int calcPcDefense() { int ac = Math.max(0, 10 -
	 * _targetPc.getAC().getAc()); int acDefMax =
	 * _targetPc.getClassFeature().getAcDefenseMax(ac); return
	 * _random.nextInt(acDefMax + 1); }
	 */

	// ●●●● Reduction by NPC damage reduction ●●●●
	private int calcNpcDamageReduction() {
		return _targetNpc.getNpcTemplate().get_damagereduction();
	}

	// ●●●● Additional damage calculated by weapon material and blessing ●●●●
	private int calcMaterialBlessDmg() {
		int damage = 0;
		int undead = _targetNpc.getNpcTemplate().get_undead();
		if ((_weaponMaterial == 14 || _weaponMaterial == 17 || _weaponMaterial == 22) && (undead == 1 || undead == 3 || undead == 5)) {
			// Silver, Mithril, Orichalcum, off-hand, undead, undead boss
			damage += _random.nextInt(10) + 20;
		}
		if ((_weaponMaterial == 17 || _weaponMaterial == 22) && undead == 2) {
			damage += _random.nextInt(3) + 1;
		}
		if ((_weaponBless == 0 || _weaponBless == 128) && (undead == 1 || undead == 2 || undead == 3)) { // 축복 무기, 한편,
			// 안
			// 데드계·악마계·안
			// 데드계 보스
			damage += _random.nextInt(4) + 1;
		}
		if (_pc != null && _pc.getWeapon() != null && _weaponType2 != 20 && _weaponType2 != 62 && weapon.getHolyDmgByMagic() != 0
				&& (undead == 1 || undead == 3)) {
			damage += weapon.getHolyDmgByMagic();
		}
		return damage;
	}

	private int calcSMaterialBlessDmg() {
		int damage = 0;
		/*
		 * 0:none 1:액체 2:web 3:식물성 4:동물성 5:지 6:포 7:피 8:목 9:골 10:룡의 린 11:철 12:강철 13:동
		 * 14:은 15:금 16:플라티나 17:미스릴 18:브락크미스릴 19:유리 20:보석 21:광물 22:오리하르콘
		 */
		int undead = _targetNpc.getNpcTemplate().get_undead();
		if ((_SweaponMaterial == 14 || _SweaponMaterial == 17 || _SweaponMaterial == 22) && (undead == 1 || undead == 3 || undead == 5)) { // 은·미스릴·오리하르콘,
			// 한편, 안
			// 데드계·안 데드계
			// 보스
			damage += _random.nextInt(10) + 20;
		}
		if ((_SweaponMaterial == 17 || _SweaponMaterial == 22) && undead == 2) {
			damage += _random.nextInt(3) + 1;
		}
		if ((_SweaponBless == 0 || _SweaponBless == 128) && (undead == 1 || undead == 2 || undead == 3)) {
			// Blessing weapon, on the other hand, undead, demon, undead boss
			damage += _random.nextInt(4) + 1;
		}
		if (_pc.getSecondWeapon() != null && _SweaponType != 20 && _SweaponType != 62 && Sweapon.getHolyDmgByMagic() != 0 && (undead == 1 || undead == 3)) {
			damage += Sweapon.getHolyDmgByMagic();
		}
		return damage;
	}

	// ●●●● Changes in NPC's and Dead's night attack power ●●●●
	private boolean isUndeadDamage() {
		return isUndeadDamage(_npc);
	}

	private boolean isUndeadDamage(L1NpcInstance npc) {
		boolean flag = false;
		int undead = npc.getNpcTemplate().get_undead();
		boolean isNight = GameTimeClock.getInstance().getGameTime().isNight();
		if (isNight && (undead == 1 || undead == 3 || undead == 4)) { // 18-6: On the other hand, the undead and undead bosses
			flag = true;
		}
		return flag;
	}

	// ●●●● Add poison attack to PC ●●●●
	public void addPcPoisonAttack(L1Character attacker, L1Character target) {
		int chance = _random.nextInt(100) + 1;
		if (chance <= 10 && (_weaponId == 13 || _weaponId == 44 || (_weaponId != 0 && _pc.getSkillEffectTimerSet().hasSkillEffect(ENCHANT_VENOM)))) {
			L1DamagePoison.doInfection(attacker, target, 2000, 5, 1);
		}
	}

	// ●●●● Added NPC poison attack ●●●●
	private void addNpcPoisonAttack(L1Character attacker, L1Character target) {
		if (_npc.getNpcTemplate().get_poisonatk() != 0) { // Poison attack
			if (15 >= _random.nextInt(100) + 1) { // Poison attack with a 15% chance
				if (_npc.getNpcTemplate().get_poisonatk() == 1) { // regular reading
					// 5 damage every 3 seconds
					L1DamagePoison.doInfection(attacker, target, 3000, 5);
				} else if (_npc.getNpcTemplate().get_poisonatk() == 2) { // silence poison
					L1SilencePoison.doInfection(target);
				} else if (_npc.getNpcTemplate().get_poisonatk() == 4) { // paralysis poison
					// Paralysis for 45 seconds after 20 seconds
					L1ParalysisPoison.doInfection(target, 20000, 3000);
				}
			}
		} /*
			 * else if (_npc.getNpcTemplate().get_paralysisatk() != 0) { /// 마비 공격 있어 }
			 */
	}

	// ■■■■ Calculation of MP absorption of mana staff and steel mana staff ■■■■
	public void calcStaffOfMana() {
		// SOM or SOM of Steel
		if (_weaponId == 1126 || _weaponId == 126 || _weaponId == 127 || _weaponId == 413103 || _weaponId == 100035 || _weaponId == 30101) {
			int som_lvl = _weaponEnchant + 3; // Set the maximum MP absorption amount

			if (som_lvl < 1) {
				som_lvl = 1;
			}
			// Acquisition of the dam by measuring the amount of MP absorbed
			_drainMana = _random.nextInt(som_lvl) + 1;
			// Limit maximum MP absorption to 9
			if (_drainMana > Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK) {
				_drainMana = Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK;
			}
		}

		if (_weaponId == 412002) { // magic dagger
			/*
			 * 무조건 1로 셋팅 되는대 연산 할 필요 없다고 생각 by.케인 int som_lvl = _weaponEnchant; // 최대 MP흡수량을
			 * 설정
			 *
			 * if (som_lvl < 1) { som_lvl = 1; } // MP흡수량을 란 댐 취득 _drainMana =
			 * _random.nextInt(som_lvl); // 최대 MP흡수량을 9에 제한 if (_drainMana > 1) { _drainMana
			 * = 1; }
			 */
			_drainMana = 1;
			try {
				if (_calcType == PC_PC && _target.getResistance().getEffectedMrBySkill() >= 100)
					_drainMana = 0;
			} catch (Exception e) {
			}
		}
	}

	// ■■■■ Greatsword of Destruction HP Absorption Calculation ■■■■
	public void calcDrainOfHp() {
		if (_weaponId == 412001 || _weaponId == 1412001 || _weaponId == 12 || _weaponId == 100032 || _weaponId == 450032 || _weaponId == 30088
				|| _weaponId == 30097 || _weaponId == 30089 || _weaponId == 31080 || _weaponId == 222202 || _weaponId == 222205) { // 파대,

			if (_damage > 0 && _random.nextInt(100) <= 40) {
				_drainHp = _damage / 10;
				if (_drainHp < 1)
					_drainHp = 1;
				else if (_drainHp >= 40)
					_drainHp = 40;
			}
		}
	}

	/* ■■■■■■■■■■■■■■ send attack animation ■■■■■■■■■■■■■■ */
	public void action() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			actionPc();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			actionNpc();
		}
	}

	// ●●●● Send player's attack animation ●●●●
	private void actionPc() {
		_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // direction set
		if (_pc.getCurrentWeapon() == 20 || _weaponType2 == 20) {
			if (_pc instanceof L1RobotInstance || _arrow != null) {
				_pc.getInventory().removeItem(_arrow, 1);
				if (_pc.getGfxId().getTempCharGfx() == 7968 || _pc.getGfxId().getTempCharGfx() == 7967) {
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit), true);
				} else if (_pc.getGfxId().getTempCharGfx() == 8842 || _pc.getGfxId().getTempCharGfx() == 8900 || _pc.getGfxId().getTempCharGfx() == 16002) { // 헬바인
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8904, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 8904, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 8845 || _pc.getGfxId().getTempCharGfx() == 8913 || _pc.getGfxId().getTempCharGfx() == 16074) { // 질리언
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8916, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 8916, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 13631) {
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 13656, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 13656, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 13346) { // 70 Jindael
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 66, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 66, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 13635) {
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 13658, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 13656, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 12314) { // Trainee's Hero Transformation Scroll
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 13658, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 13656, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else {
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 66, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 66, _targetX, _targetY, _isHit), true);
				}
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				}
			} else if (_weaponId == 190 || _weaponId == 100190 || _weaponId == 7201 || _weaponId == 450029 || _weaponId == 30082 || _weaponId == 31091) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 2349, _targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 2349, _targetX, _targetY, _isHit), true);
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				}
			} else if (_weaponId >= 11011 && _weaponId <= 11013) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8771, _targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 8771, _targetX, _targetY, _isHit), true);
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} // addition
			} else if (_arrow == null) {
				_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
				Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
			}
		} else if (_weaponType2 == 62 && _sting != null) {
			_pc.getInventory().removeItem(_sting, 1);
			if (_pc.getGfxId().getTempCharGfx() == 7968 || _pc.getGfxId().getTempCharGfx() == 7967) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 7972, _targetX, _targetY, _isHit), true);
			} else if (_pc.getGfxId().getTempCharGfx() == 8842 || _pc.getGfxId().getTempCharGfx() == 8900) { // 헬바인
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8904, _targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 8904, _targetX, _targetY, _isHit));
				Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
			} else if (_pc.getGfxId().getTempCharGfx() == 8845 || _pc.getGfxId().getTempCharGfx() == 8913) { // 질리언
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8916, _targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 8916, _targetX, _targetY, _isHit));
				Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
			} else {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 2989, _targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc, _targetId, 2989, _targetX, _targetY, _isHit), true);
			}
			if (_isHit) {
				Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
			}
		} else {
			if (_target.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER) || _target.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) {
				_isHit = false;
			} else if (_target.getSkillEffectTimerSet().hasSkillEffect(MOBIUS) && (_weaponType2 == 20 || _weaponType2 == 62)) {
				_isHit = false;
			}
			byte motion = ActionCodes.ACTION_Attack;
			if (_pc.getGfxId().getTempCharGfx() == 12237 && _random.nextInt(100) < 20)
				motion = 30;
			if (_isHit) {
				if (_critical) {
					int crigfx = 0;
					/**
					 * 1: one-handed sword 2: dagger, 3: two-handed sword, 4: bow, 5: spear
					 * 6: axe, 7: blunt weapon, 8: throwing knife, 9: arrow, 10: gauntlet
					 * 11: Crow, 12: Two-handed sword, 13: One-handed bow, 14: One-handed spear
					 * 15: Two-handed axe, 16: Two-handed blunt weapon, 17: Keylink 18 chainsword
					 */
					if (_weaponType == 1) { // one-handed sword
						crigfx = 13411;
					} else if (_weaponType == 2) { // dagger
						crigfx = 13412;
					} else if (_weaponType == 3) { // two-handed sword
						crigfx = 13410;
					} else if (_weaponType == 5 || _weaponType == 14) { // spear
						crigfx = 13402;
					} else if (_weaponType == 6 || _weaponType == 15) { // axe
						if (Sweapon != null) {
							crigfx = 13415;
						} else {
							crigfx = 13414;
						}
					} else if (_weaponType == 7 || _weaponType == 16) { // blunt
						crigfx = 13413;
					} else if (_weaponType == 11) { // claw
						crigfx = 13416;
					} else if (_weaponType == 12) { // dual wield
						crigfx = 13417;
					} else if (_weaponType == 17) { // kiringku
						crigfx = 13396;
					} else if (_weaponType == 18) { // chainsword
						crigfx = 13409;
					}
					if (_pc.getWeapon() != null) {
						_attackType = 2;
					} else {
						_attackType = 0;
					}
					if (burning) {
						_attackType = 132;
						burning = false;
					}
					_critical = false;
					_pc.sendPackets(new S_AttackPacket(_pc, _targetId, motion, _attackType, crigfx), true);
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc, _targetId, motion, _attackType, crigfx), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				} else {
					/*
					 * int crigfx = 0; if(_weaponType1 == 17){//키링크 if(_pc.getWeapon().getItemId()
					 * == 4110003){ crigfx = 6983; }else{ crigfx = 7049; } } if(crigfx != 0){
					 * _pc.sendPackets(new S_AttackPacket(_pc, _targetId, motion, _attackType,
					 * crigfx), true); Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc,
					 * _targetId, motion, _attackType, crigfx), true); }else{
					 */

					if (_pc.getWeapon() != null) {
						if (_pc.getWeapon().getItem().getType() == 17) {
							if (_pc.getWeapon().getItemId() == 410003) {
								S_SkillSound ss = new S_SkillSound(_pc.getId(), 6983);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							} else if (_pc.getWeapon().getItemId() == 6001 || _pc.getWeapon().getItemId() == 30081 || _pc.getWeapon().getItemId() == 7238
									|| _pc.getWeapon().getItemId() == 30120) {
								_pc.sendPackets(new S_AttackCritical(_pc, _target.getId(), 89));
								Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _target.getId(), 89));
							} else {
								S_SkillSound ss = new S_SkillSound(_pc.getId(), 7049);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							}
						}
					}
					if (burning) {
						_attackType = 131;
						burning = false;
					}
					_pc.sendPackets(new S_AttackPacket(_pc, _targetId, motion, _attackType), true);
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc, _targetId, motion, _attackType), true);
					// }
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc, true);
				}
			} else {
				burning = false;
				if (_targetId > 0) {
					if (_pc.getWeapon() != null) {
						if (_pc.getWeapon().getItem().getType() == 17) {
							if (_pc.getWeapon().getItemId() == 410003) {
								S_SkillSound ss = new S_SkillSound(_pc.getId(), 6983);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							} else if (_pc.getWeapon().getItemId() == 6001 || _pc.getWeapon().getItemId() == 30081 || _pc.getWeapon().getItemId() == 7238
									|| _pc.getWeapon().getItemId() == 30120) {
								_pc.sendPackets(new S_AttackCritical(_pc, _target.getId(), 89));
								Broadcaster.broadcastPacket(_pc, new S_AttackCritical(_pc, _target.getId(), 89));
							} else {
								S_SkillSound ss = new S_SkillSound(_pc.getId(), 7049);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							}
							_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId, motion), true);
							Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId, motion), true);
						} else {
							_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId, motion), true);
							Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId, motion), true);
						}
					} else {
						_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId, motion), true);
						Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId, motion), true);
					}
				} else {
					_pc.sendPackets(new S_AttackPacket(_pc, 0, motion), true);
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc, 0, motion), true);
				}
			}
		}

		if (_isHit) {
			if (_target instanceof L1PcInstance) {
				L1PcInstance target = (L1PcInstance) _target;
				if (target.샌드백) {
					target.타격++;
					target.누적++;
				}
			}
		} else {
			if (_target instanceof L1PcInstance) {
				L1PcInstance target = (L1PcInstance) _target;
				if (target.샌드백) {
					target.미스++;
					target.누적++;
					S_ChatPacket s_chatpacket = new S_ChatPacket(target,
							"[H:" + target.타격 + " M:" + target.미스 + " T:" + target.누적 + "] TotalDMG : " + target.토탈데미지, Opcodes.S_SAY, 0);
					Broadcaster.broadcastPacket(target, s_chatpacket, true);
					int heading = target.getMoveState().getHeading();
					if (heading < 7) {
						heading++;
					} else {
						heading = 0;
					}
					target.getMoveState().setHeading(heading);
					Broadcaster.broadcastPacket(target, new S_ChangeHeading(target), true);
					// S_DoActionGFX 데미지 = new
					// S_DoActionGFX(target.getId(),ActionCodes.ACTION_Damage );
					// Broadcaster.broadcastPacket(target, 데미지);
				}
			}
		}
	}

	// ●●●● Transmit NPC's attack motion ●●●●
	private void actionNpc() {
		int _npcObjectId = _npc.getId();
		int bowActId = 0;
		int actId = 0;

		_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트

		// Ranged attack when the distance from the target is 2 or more
		boolean isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1);
		bowActId = _npc.getNpcTemplate().getBowActId();

		if (_npc.getNpcTemplate().get_gfxid() == 10050) {
			isLongRange = true;
		}

		if (getActId() > 0) {
			actId = getActId();
		} else if (bowActId > 0) {
			actId = 18;

		} else {
			if (_npc.getNpcTemplate().get_gfxid() == 1780) // 혼켈베
				actId = 30;
			else
				actId = ActionCodes.ACTION_Attack;
		}

		if (!isLongRange) {
			if (_npc.getNpcTemplate().get_gfxid() == 3412 || _npc.getNpcTemplate().get_npcId() == 100055) {
				actId = 30;
			}
		}

		/** Critical This basic base fighting crit is the second */
		if (_npc instanceof L1PetInstance) {
			L1PetInstance Pet = (L1PetInstance) _npc;
			if ((Pet.SkillCheck(L1SkillId.Fighting) && Pet.getFightingCombo() == 2) || _critical || _속성치명) {
				actId = 18;
			} else if (_속성공격) {
				actId = 30;
			}
		}

		if (isLongRange && bowActId > 0) {
			Broadcaster.broadcastPacket(_npc, new S_UseArrowSkill(_npc, _targetId, bowActId, _targetX, _targetY, _isHit), true);

			// Broadcaster.broadcastPacket(_npc, new
			// S_NpcChatPacket(_npc,"1 내 액션값은 "+actId+"인데?"), true);
		} else {
			// Broadcaster.broadcastPacket(_npc, new
			// S_NpcChatPacket(_npc,"2 내 액션값은 "+actId+"인데?"), true);
			if (_isHit) {
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npcObjectId, getGfxId(), _targetX, _targetY, actId), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _npc, true);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackPacketForNpc(_target, _npcObjectId, actId), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target, new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _npc, true);
				}
				/** If in magic critical state, apply magic critical impact for each attribute */
				if (_속성공격) {
					Broadcaster.broadcastPacket(_npc, new S_SkillSound(_target.getId(), _속성공격임팩트), true);
				} else if (_속성치명) {
					Broadcaster.broadcastPacket(_npc, new S_SkillSound(_target.getId(), _속성치명임팩트), true);
				}
			} else {
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npcObjectId, getGfxId(), _targetX, _targetY, actId, 0), true);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
				}
			}
			/*
			 * if(_npc.getNpcTemplate().get_npcId() == 45618) // 나발 평타에 검기 추가
			 * Broadcaster.broadcastPacket(_npc, new S_SkillSound(_npc.getId(), 5680),
			 * true); else
			 */if (_npc.getNpcTemplate().get_npcId() == 45654) // 아이리스 평타 이펙트 추가
				Broadcaster.broadcastPacket(_npc, new S_SkillSound(_npc.getId(), 7337), true);

		}
	}

	/* ■■■■■■■■■■■■■■■ Reflect calculation result ■■■■■■■■■■■■■■■ */

	public void commit() {
		if (_isHit) {
			if (_calcType == PC_PC || _calcType == NPC_PC) {
				commitPc();
			} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
				commitNpc();
			}
		}

		// Message for confirming damage value
		if (!Config.ALT_ATKMSG || (_target == null))
			return;
		if (_pc != null /*&& _pc.isGm()*/) {
			StringBuffer sb = new StringBuffer();
			sb.append("\\fT[").append(_pc.getName()).append("] ==> [").append(_target.getName()).append("][== ").append(_damage).append(" DMG ==][HP ")
					.append(_target.getCurrentHp()).append("]");
			_pc.sendPackets(new S_SystemMessage(sb.toString()), true);
			sb = null;
		}
		if (_targetPc != null /*&& _targetPc.isGm()*/) {
			StringBuffer sb = new StringBuffer();
			sb.append("\\fY[").append(_target.getName()).append("] <== [").append((_pc == null ? _npc.getName() : _pc.getName())).append("][== ")
					.append(_damage).append(" DMG ==][HP ").append(_target.getCurrentHp()).append("]");
			_targetPc.sendPackets(new S_SystemMessage(sb.toString()), true);
			sb = null;
		}
	}

	// ●●●● Reflect the calculation result to the player ●●●●
	private void commitPc() {
		if (_calcType == PC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) // Basilisk Freeze Damage 0
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) { // Cockatrice freeze damage 0
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			} else if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MOBIUS) && (_weaponType2 == 20 || _weaponType2 == 62)) {
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			} else {
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.VALA_MAAN) //Mystic Eye of the Fire Dragon - Probability of additional physical damage +2
						|| _pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.LIFE_MAAN)) { // Mystic Eye of Life - with a certain probability
					// Physical Additional Hit +2
					int MaanAttDmg = _random.nextInt(100) + 1;
					if (MaanAttDmg <= 30) { // percentage
						_damage += 2;
					}
				}
				if (_drainMana > 0 && _targetPc.getCurrentMp() > 0) {
					if (_drainMana > _targetPc.getCurrentMp()) {
						_drainMana = _targetPc.getCurrentMp();
					}
					short newMp = (short) (_targetPc.getCurrentMp() - _drainMana);
					_targetPc.setCurrentMp(newMp);
					newMp = (short) (_pc.getCurrentMp() + _drainMana);
					_pc.setCurrentMp(newMp);
				}
				/** Joe's Stone Golem **/
				if (_drainHp > 0 && _targetPc.getCurrentHp() > 0) {
					if (_drainHp > _targetPc.getCurrentHp()) {
						_drainHp = _targetPc.getCurrentHp();
					}
					short newHp = (short) (_targetPc.getCurrentHp() - _drainHp);
					_targetPc.setCurrentHp(newHp);
					newHp = (short) (_pc.getCurrentHp() + _drainHp);
					_pc.setCurrentHp(newHp);
				}
				/** Joe's Stone Golem **/
			}
			// Non-damaging with bounce attack renewal
			// damagePcWeaponDurability(); // damage the weapon.

			_targetPc.receiveDamage(_pc, _damage, false);
		} else if (_calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) // 바실얼리기데미지0
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) { // 코카얼리기데미지0
				_damage = 0;
			} else if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MOBIUS) && _npc.getNpcTemplate().getBowActId() >= 1) {
				_damage = 0;
			}
			_targetPc.receiveDamage(_npc, _damage, false);
		}
	}

	// ●●●● Reflect the calculation result to NPC ●●●●
	private void commitNpc() {
		if (_calcType == PC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE) || _targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) // Basil Freeze Damage 0
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) { // Coca freeze damage 0
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			} else {
				if (_targetNpc instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) _targetNpc;
					if (mon.kir_counter_barrier) {
						commitCounterBarrier();
						return;
					} else if (mon.kir_poison_barrier) {
						if (15 >= _random.nextInt(100) + 1) { // Poison attack with a 15% chance
							L1DamagePoison.doInfection(_targetNpc, _pc, 3000, 100 + _random.nextInt(50));
						}
					} else if (mon.kir_absolute) {
						_damage = 0;
						_drainMana = 0;
						_drainHp = 0;
					}
				}
				if (_drainMana > 0) {
					int drainValue = _targetNpc.drainMana(_drainMana);
					int newMp = _pc.getCurrentMp() + drainValue;
					_pc.setCurrentMp(newMp);

					if (drainValue > 0) {
						int newMp2 = _targetNpc.getCurrentMp() - drainValue;
						_targetNpc.setCurrentMp(newMp2);
					}
				}

				/** Joe's Stone Golem **/

				if (_drainHp > 0) {
					int newHp = _pc.getCurrentHp() + _drainHp;
					_pc.setCurrentHp(newHp);
				}
				/** Joe's Stone Golem **/
			}
			damageNpcWeaponDurability(); // damage the weapon.
			if (_damage > 0)
				_damage = (int) (_damage * 1.20);
			_targetNpc.receiveDamage(_pc, _damage);
		} else if (_calcType == NPC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE) || _targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) // Basil freeze damage 0
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) { // Coca freezing damage 0
				_damage = 0;
			}
			_targetNpc.receiveDamage(_npc, _damage);
		}
	}

	public static double calcMrDefense(int MagicResistance, double dmg) {
		double cc = 0;
		if (MagicResistance <= 19) {
			cc = 0.05;
		} else if (MagicResistance <= 29) {
			cc = 0.07;
		} else if (MagicResistance <= 39) {
			cc = 0.1;
		} else if (MagicResistance <= 49) {
			cc = 0.12;
		} else if (MagicResistance <= 59) {
			cc = 0.17;
		} else if (MagicResistance <= 69) {
			cc = 0.20;
		} else if (MagicResistance <= 79) {
			cc = 0.22;
		} else if (MagicResistance <= 89) {
			cc = 0.25;
		} else if (MagicResistance <= 99) {
			cc = 0.27;
		} else if (MagicResistance <= 110) {
			cc = 0.31;
		} else if (MagicResistance <= 120) {
			cc = 0.32;
		} else if (MagicResistance <= 130) {
			cc = 0.34;
		} else if (MagicResistance <= 140) {
			cc = 0.36;
		} else if (MagicResistance <= 150) {
			cc = 0.38;
		} else if (MagicResistance <= 160) {
			cc = 0.40;
		} else if (MagicResistance <= 170) {
			cc = 0.42;
		} else if (MagicResistance <= 180) {
			cc = 0.44;
		} else if (MagicResistance <= 190) {
			cc = 0.46;
		} else if (MagicResistance <= 200) {
			cc = 0.48;
		} else if (MagicResistance <= 220) {
			cc = 0.49;
		} else {
			cc = 0.51;
		}

		dmg -= dmg * cc;

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	// Implement the Evilverse effect.

	public void EveilReverse() { // 마족무기 피빠는무기들
		int chance = _random.nextInt(100) + 1;
		if (5 + _weaponEnchant >= chance) { // 확률은 5프로
			_drainHp = 30; // 피를 30빤다는얘기
			_pc.sendPackets(new S_SkillSound(_target.getId(), 8150), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(), 8150), true);
		}

	}

	public void DrainofEvil() { // 여긴 활
		int chance = _random.nextInt(100) + 1;
		if (5 + _weaponEnchant >= chance) { // 확률은 5프로
			_drainMana = _random.nextInt(5) + 6; // 엠피를 3빤다는애기
			_pc.sendPackets(new S_SkillSound(_target.getId(), 8152), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(), 8152), true);
		}
	}

	public void DrainofEvil1() { // 여긴 지팡이 키링크
		int chance = _random.nextInt(100) + 1;
		if (12 >= chance) {// 확률은 12프로
			_drainMana = _random.nextInt(5) + 6; // 엠피를 6빤다는애기
			_pc.sendPackets(new S_SkillSound(_target.getId(), 8152), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(), 8152), true);
		}
	}

	public void actionTaitan(int type) {
		int gfx = 0;
		switch (type) {//
		case 0:// 락
			gfx = 12555;
			break;
		case 1:// 매직
			gfx = 12559;
			break;
		case 2:// 블릿
			gfx = 12557;
			break;
		}
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // 방향세트
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId));
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId));
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			_targetPc.sendPackets(new S_SkillSound(_target.getId(), gfx));
			// Broadcaster.broadcastPacket(_target, new
			// S_SkillSound(_target.getId(), gfx));
		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}

			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0), true);
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
			}

			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage), true);
			// Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId,
			// gfx), true);
			_targetPc.sendPackets(new S_SkillSound(_target.getId(), gfx));
		}
	}

	/* ■■■■■■■■■■■■■■■ 카운터 바리어 ■■■■■■■■■■■■■■■ */

	// ■■■■ 카운터 바리어시의 공격 모션 송신 ■■■■
	public void actionCounterBarrier() {
		if (_calcType == PC_PC) {
			if (_targetPc.COUNTER_BARRIER_VETERAN) {

				_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // 방향세트
				_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
				Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
				_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
				Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
				_pc.sendPackets(new S_SkillSound(_targetId, 17220), true);
				Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 17220), true);
			} else if (!_targetPc.COUNTER_BARRIER_VETERAN) {

				_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // direction set
				_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
				Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
				_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
				Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
				_pc.sendPackets(new S_SkillSound(_targetId, 10710), true);
				Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 10710), true);
			}
		} else if (_calcType == PC_NPC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // 방향세트
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			_pc.sendPackets(new S_SkillSound(_targetId, 10710), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 10710), true);
		} else if (_calcType == NPC_PC) {
			if (_targetPc.COUNTER_BARRIER_VETERAN) {
				int actId = 0;
				_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트
				if (getActId() > 0) {
					actId = getActId();
				} else {
					actId = ActionCodes.ACTION_Attack;
				}
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0), true);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
				}
				Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage), true);
				Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 17220), true);
			} else if (!_targetPc.COUNTER_BARRIER_VETERAN) {
				int actId = 0;
				_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트
				if (getActId() > 0) {
					actId = getActId();
				} else {
					actId = ActionCodes.ACTION_Attack;
				}
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0), true);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
				}
				Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage), true);
				Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 10710), true);
			}
		}

	}

	// ■■■■ 파라독스 공격 모션 송신 ■■■■
	public void actionParadox() {
		if (_calcType == PC_PC) {
			int damage = ParadoxDamage();
			if (damage == 0) {
				return;
			}
			_pc.receiveDamage(_targetPc, damage, false);
			_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 18518), true);
			Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 18518), true);
		} else {
			_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 18518), true);
			Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 18518), true);
		}
	}

	// ■■■■ 모탈바디 발동시의 공격 모션 송신 ■■■■
	public void actionMortalBody() {
		if (_calcType == PC_PC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // 방향세트
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			_pc.sendPackets(new S_SkillSound(_targetId, 9802), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 9802), true);
		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}
			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0), true);
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
			}
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 9802), true);
		}
	}

	// ■■■■ 인페르노 발동시의 공격 모션 송신 ■■■■
	public void actionInferno(int Type) {
		if (_calcType == PC_PC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // 방향세트
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			_pc.sendPackets(new S_SkillSound(_targetId, Type), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, Type), true);
		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}
			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0), true);
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
			}
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, Type), true);
		}
	}

	// ■■■■ 할파스 발동시의 공격 모션 송신 ■■■■
	public void actionHALPHAS() {
		if (_calcType == PC_PC) {
			_pc.getMoveState().setHeading(CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // 방향세트
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc, _targetId), true);
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage), true);
			_pc.sendPackets(new S_SkillSound(_targetId, 18410), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 18410), true);
		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // 방향세트
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}
			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0), true);
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc, _targetId, actId), true);
			}
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 18410), true);
		}
	}

	// ■■■■ 린드마갑주 발동시의 공격 모션 송신 ■■■■
	/*
	 * public void actionLindArmor() { if (_calcType == PC_PC || _calcType ==
	 * PC_NPC) { _pc.getMoveState().setHeading( CharPosUtil.targetDirection(_pc,
	 * _targetX, _targetY)); // 방향세트 _pc.sendPackets(new S_AttackMissPacket(_pc,
	 * _targetId), true); Broadcaster.broadcastPacket(_pc, new
	 * S_AttackMissPacket(_pc,_targetId), true); _pc.sendPackets(new
	 * S_DoActionGFX(_pc.getId(),ActionCodes.ACTION_Damage), true);
	 * Broadcaster.broadcastPacket(_pc, new
	 * S_DoActionGFX(_pc.getId(),ActionCodes.ACTION_Damage), true);
	 * //Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 8120));
	 * S_EffectLocation s = new S_EffectLocation(_targetX+1, _targetY, (short)
	 * 8120); _pc.sendPackets(s); Broadcaster.broadcastPacket(_pc, s, true); } else
	 * if (_calcType == NPC_PC) { int actId = 0;
	 * _npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc, _targetX,
	 * _targetY)); // 방향세트 if (getActId() > 0) { actId = getActId(); } else { actId
	 * = ActionCodes.ACTION_Attack; } if (getGfxId() > 0) {
	 * Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(_target,_npc.getId(),
	 * getGfxId(), _targetX, _targetY,actId, 0), true); } else {
	 * Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc,_targetId,
	 * actId), true); } Broadcaster.broadcastPacket(_npc, new
	 * S_DoActionGFX(_npc.getId(),ActionCodes.ACTION_Damage), true);
	 * //Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 4395));
	 * //Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 8120));
	 * Broadcaster.broadcastPacket(_npc, new S_EffectLocation(_targetX+1, _targetY,
	 * (short) 8120), true); } }
	 */

	// Determines whether the counter barrier is effective against the opponent's attack
	public boolean isShortDistance() {
		boolean isShortDistance = true;
		if (_calcType == PC_PC) {
			if (_weaponType2 == 20 || _weaponType2 == 62) { // Bow or Gauntlet
				isShortDistance = false;
			}
		} else if (_calcType == NPC_PC) {
			boolean isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1);
			int bowActId = _npc.getNpcTemplate().getBowActId();
			// 거리가 2이상, 공격자의 활의 액션 ID가 있는 경우는 원공격
			if (isLongRange && bowActId > 0) {
				isShortDistance = false;
			}
		}
		return isShortDistance;
	}

	// ■■■■ 타이탄 데미지를 반영 ■■■■
	public void commitTaitan(int type) {
		int damage = calcTaitanDamage(type);
		// _pc.sendPackets(new S_SystemMessage("타이탄 반사데미지 :"+damage));
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ■■■■ 카운터 바리어의 데미지를 반영 ■■■■
	public void commitCounterBarrier() {
		int damage = calcCounterBarrierDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ■■■■ 인페르노의 데미지를 반영 ■■■■
	public void commitisInferno(int Type) {
		_damage -= _damage * (1 / 2);
		int damage = (calcInfernoDamage() * Type);
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ■■■■ 할파스의 데미지를 반영 ■■■■
	public void commitisHALPHAS() {
		int damage = calcHALPHASDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ■■■■ 모탈바디의 데미지를 반영 ■■■■
	public void commitMortalBody() {
		int damage = 40;
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ■■■■ 린드 마갑주의 데미지를 반영 ■■■■
	public void commitLindArmor() {
		int damage = calcLindArmorDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ●●●● 린드 마갑주의 데미지를 산출 ●●●●
	private int calcLindArmorDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == PC_PC) {
			weapon = _pc.getWeapon();
		} else if (_calcType == NPC_PC) {
			weapon = _targetPc.getWeapon();
		} else
			damage = 60 + _random.nextInt(41);
		if (weapon != null) {
			if (weapon.getItem().getType1() == 20 || weapon.getItem().getType1() == 62) {
				// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
				damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 2;
			}
		}
		return damage;
	}

	// ●●●● 카운터 바리어의 데미지를 산출 ●●●●
	private int calcTaitanDamage(int type) {
		int damage = 0;
		L1ItemInstance weapon = null;
		weapon = _targetPc.getWeapon();
		if (weapon != null) {
			// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
			damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 2;
		}
		return damage;
	}

	// ●●●● 카운터 바리어의 데미지를 산출 ●●●●
	private int calcCounterBarrierDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == NPC_PC || _calcType == PC_PC) {
			weapon = _targetPc.getWeapon();

			if (weapon != null) {
				if (weapon.getItem().getType() == 3) {
					// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
					damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 2;
				}
			}
		} else
			damage = 60 + _random.nextInt(41);
		return damage;
	}

	// ●●●● 파라독스 데미지 산출 ●●●●
	private int ParadoxDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == NPC_PC || _calcType == PC_PC) {
			weapon = _targetPc.getWeapon();

			if (weapon != null) {
				// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
				damage = (weapon.getItem().getDmgSmall() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 4;
			}
		} else
			damage = 60 + _random.nextInt(41);
		return damage;
	}

	// ●●●● 할파스의 데미지를 산출 ●●●●
	private int calcHALPHASDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == NPC_PC || _calcType == PC_PC) {
			weapon = _targetPc.getWeapon();

			if (weapon != null) {
				if (weapon.getItem().getType() == 18) {
					// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
					damage = (weapon.getItem().getDmgSmall() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 3;
				}
			}
		} else
			damage = 60 + _random.nextInt(41);
		return damage;
	}

	// ●●●● 인페르노의 데미지를 산출 ●●●●
	private int calcInfernoDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == NPC_PC || _calcType == PC_PC) {
			weapon = _targetPc.getWeapon();

			if (weapon != null) {
				if (weapon.getItem().getType() == 1) {
					// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
					damage = (weapon.getItem().getDmgSmall() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier());
				}
			}
		} else
			damage = 60 + _random.nextInt(41);
		return damage;
	}

	/*
	 * 무기를 손상시킨다. 대NPC의 경우, 손상 확률은10%로 한다. 축복 무기는3%로 한다.
	 */

	private void damageNpcWeaponDurability() {
		int chance = 10;
		int bchance = 1;

		/*
		 * Non-damaging NPCs, bare hands, non-damaging weapons, and SOF do nothing.
		 */
		if (_calcType != PC_NPC || !_targetNpc.getNpcTemplate().is_hard() || _weaponType2 == 0 || weapon.getItem().get_canbedmg() == 0
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME) || _pc.damascus) {
			return;
		}
		// 축손상안가게 반장
		/*
		 * if(weapon.getBless() == 0||weapon.getBless() == 128){ return; }
		 */
		// 통상의 무기·저주해진 무기
		if ((_weaponBless == 1 || _weaponBless == 2) && ((_random.nextInt(100) + 1) < chance)) {
			// \f1당신의%0가 손상했습니다.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()), true);
			_pc.getInventory().receiveDamage(weapon);
			_pc.sendPackets(new S_SkillSound(_pc.getId(), 10712), true);
		}
		// 축복된 무기
		if ((_weaponBless == 0 || _weaponBless == 128) && ((_random.nextInt(100) + 1) < bchance)) {
			// \f1당신의%0가 손상했습니다.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
			_pc.getInventory().receiveDamage(weapon);
			_pc.sendPackets(new S_SkillSound(_pc.getId(), 10712), true);
		}
	}

	/*
	 * 바운스아탁크에 의해 무기를 손상시킨다. 바운스아탁크의 손상 확률은10%
	 */
	/*
	 * private void damagePcWeaponDurability() { int chance = 5; //int bchance = 2;
	 *
	 * // PvP 이외, 맨손, 활, 암 토토 렛, 상대가 바운스아탁크미사용, SOF중의 경우 아무것도 하지 않는다 if
	 * (_weaponBless == 0 || _weaponBless == 128 ||_calcType != PC_PC || _weaponType
	 * == 0 || _weaponType == 20 || _weaponType == 62 ||
	 * _targetPc.getSkillEffectTimerSet().hasSkillEffect(BOUNCE_ATTACK) == false ||
	 * _pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME) ||
	 * _targetPc.isParalyzed()) { return; }
	 *
	 * if ((_weaponBless == 1 || _weaponBless == 2) && ((_random.nextInt(100) + 1)
	 * <= chance)) { // \f1당신의%0가 손상했습니다. _pc.sendPackets(new S_ServerMessage(268,
	 * weapon.getLogName()), true); _pc.getInventory().receiveDamage(weapon); } }
	 */

	private int rumtisRedEarringDReduc() {
		int damage = 0;
		if (_calcType == NPC_PC || _calcType == PC_PC) {
			L1ItemInstance item = _targetPc.getInventory().checkEquippedItem(500007);
			if (item != null && item.getEnchantLevel() >= 5) {
				if (_random.nextInt(100) < 2 + item.getEnchantLevel() - 5) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 12118), true);
				}
			}

			L1ItemInstance item2 = _targetPc.getInventory().checkEquippedItem(502007);
			if (item2 != null && item2.getEnchantLevel() >= 4) {
				if (_random.nextInt(100) < 2 + item2.getEnchantLevel() - 4) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 12118), true);
				}
			}

			L1ItemInstance item3 = _targetPc.getInventory().checkEquippedItem(21248);
			if (item3 != null && item3.getEnchantLevel() >= 7) {
				if (_random.nextInt(100) < 2 + item3.getEnchantLevel() - 8) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 12118), true);
				}
			}

			L1ItemInstance item4 = _targetPc.getInventory().checkEquippedItem(21252);
			if (item4 != null && item4.getEnchantLevel() >= 6) {
				if (_random.nextInt(100) < 2 + item4.getEnchantLevel() - 7) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 12118), true);
				}
			}
			L1ItemInstance item5 = _targetPc.getInventory().checkEquippedItem(30014);
			if (item5 != null && item5.getEnchantLevel() >= 1) {
				if (_random.nextInt(100) < 1 + item5.getEnchantLevel() - 1) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 12118), true);
				}
			}
		}
		return damage;
	}

	private void halpasArmorBless(L1PcInstance pc, L1ItemInstance weapon) {

		if (CommonUtil.random(100) < 5 + weapon.getEnchantLevel()) {
			int effect = 19068;
			if(weapon.getEnchantLevel() >= 3 && weapon.getEnchantLevel() <= 4)
				effect += 2;
			if(weapon.getEnchantLevel() >= 5)
				effect += 4;

			int addHeal = CommonUtil.random(15, 30);
			if (weapon.getEnchantLevel() > 0) {
				addHeal += (addHeal * (weapon.getEnchantLevel() * 0.1));
			}

			pc.setCurrentHp(pc.getCurrentHp() + addHeal);
			pc.setCurrentMp(pc.getCurrentMp() + (addHeal / 2));
			pc.sendPackets(new S_SkillSound(pc.getId(), effect));
			if(pc.getParty() != null) {
				for(L1PcInstance member : pc.getParty().getMembers()) {
					if(pc.getLocation().isInScreen(member.getLocation())) {
						member.sendPackets(new S_SkillSound(pc.getId(), effect));
					}
				}
			}
		}
	}
}
