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

package l1j.server.server.model.Instance;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1World;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_DollPack;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.types.Point;

public class L1DollInstance extends L1NpcInstance {
	private static final long serialVersionUID = 1L;

	public static final int DOLLTYPE_BUGBEAR = 0;
	public static final int DOLLTYPE_SUCCUBUS = 1;
	public static final int DOLLTYPE_WEREWOLF = 2;
	public static final int DOLLTYPE_STONEGOLEM = 3;
	public static final int DOLLTYPE_ELDER = 4;
	public static final int DOLLTYPE_CRUSTACEAN = 5;
	public static final int DOLLTYPE_SEADANCER = 6;
	public static final int DOLLTYPE_SNOWMAN = 7;
	public static final int DOLLTYPE_COCKATRICE = 8;
	public static final int DOLLTYPE_DRAGON_M = 9;
	public static final int DOLLTYPE_DRAGON_W = 10;
	public static final int DOLLTYPE_HIGH_DRAGON_M = 11;
	public static final int DOLLTYPE_HIGH_DRAGON_W = 12;
	public static final int DOLLTYPE_RAMIA = 13;
	public static final int DOLLTYPE_ETTINREMOVE = 17;
	public static final int DOLLTYPE_HELPER = 20;
	public static final int DOLLTYPE_SPARTOI = 21;
	public static final int DOLLTYPE_HUSUABI = 22;
	public static final int DOLLTYPE_SNOWMAN_A = 23; // 눈사람(A)
	public static final int DOLLTYPE_SNOWMAN_B = 24; // 눈사람(B)
	public static final int DOLLTYPE_SNOWMAN_C = 25; // 눈사람(C)
	public static final int DOLLTYPE_ETTIN = 26; // the real ettin?
	public static final int DOLLTYPE_LICH = 27;
	public static final int DOLLTYPE_BLEG = 28;
	public static final int DOLLTYPE_LEDEGG = 29;
	public static final int DOLLTYPE_ELEG = 30;
	public static final int DOLLTYPE_GREG = 31;
	public static final int DOLLTYPE_GANGNAM = 56; // do not use
	public static final int DOLLTYPE_DANDY = 57; // do not use
	public static final int DOLLTYPE_SERIE = 58; // do not use
	public static final int DOLLTYPE_DRAKE = 59;
	public static final int DOLLTYPE_남자_여자 = 60; // man woman do not use
	public static final int DOLLTYPE_GREMLIN = 61;
	public static final int DOLLTYPE_HW_HUSUABI = 62;
	public static final int DOLLTYPE_튼튼한기사 = 63; // sturdy knight do not use
	public static final int DOLLTYPE_행운의기사 = 79; // lucky knight do not use
	public static final int DOLLTYPE_IRIS = 80;
	public static final int DOLLTYPE_VAMPIRE = 81;
	public static final int DOLLTYPE_BARANKA = 82;
	public static final int DOLLTYPE_MUMMYLORD = 3000089;
	public static final int DOLLTYPE_TARAK = 3000090;

	public static final int DOLLTYPE_CYCLOPS = 64;
	public static final int DOLLTYPE_GIANT = 65;
	public static final int DOLLTYPE_BLACKELDER = 66;
	public static final int DOLLTYPE_SUCCUBUSQUEEN = 67;

	public static final int DOLLTYPE_MERMAID = 68;
	public static final int DOLLTYPE_KINGBUGBEAR = 69;
	public static final int DOLLTYPE_SNOWMAN2 = 70;
	public static final int DOLLTYPE_EVENTDOLL = 71;

	public static final int DOLLTYPE_WOODCHIP = 72;
	public static final int DOLLTYPE_LAVAGOLEM = 73;
	public static final int DOLLTYPE_DIAMONDGOLEM = 74;
	public static final int DOLLTYPE_SEER = 75;
	public static final int DOLLTYPE_KNIGHTVALD = 76;
	public static final int DOLLTYPE_DEMON = 77;
	public static final int DOLLTYPE_DEATHKNIGHT = 78;
	public static final int DOLLTYPE_ANTHARAS = 83;
	public static final int DOLLTYPE_FAFURION = 84;
	public static final int DOLLTYPE_LINDVIOR = 85;
	public static final int DOLLTYPE_VALAKAS = 86;
	public static final int DOLLTYPE_헌신1등급 = 87; // rank 1? no in client
	//tier 3
	public static final int DOLLTYPE_BLESSEDBLACKELDER = 88;
	public static final int DOLLTYPE_BLESSEDGIANT = 89;
	public static final int DOLLTYPE_BLESSEDSUCCUBUSQUEEN = 90;
	public static final int DOLLTYPE_BLESSEDDRAKE = 91;
	public static final int DOLLTYPE_BLESSEDKINGBUGGEAR = 92;
	public static final int DOLLTYPE_BLESSEDDIAMONDGOLEM = 93;
	//tier 4
	public static final int DOLLTYPE_BLESSEDLICH = 94;
	public static final int DOLLTYPE_BLESSEDCYCLOPS = 95;
	public static final int DOLLTYPE_BLESSEDKNIGHTVALD = 96;
	public static final int DOLLTYPE_BLESSEDSEER = 97;
	public static final int DOLLTYPE_BLESSEDIRIS = 98;
	public static final int DOLLTYPE_BLESSEDVAMPIRE = 99;
	public static final int DOLLTYPE_BLESSEDMUMMYLORD = 100;
	//tier 5
	public static final int DOLLTYPE_BLESSEDDEMON = 101;
	public static final int DOLLTYPE_BLESSEDDEATHKNIGHT = 102;
	public static final int DOLLTYPE_BLESSEDBARANKA = 103;
	public static final int DOLLTYPE_BLESSEDTARAK = 104;
	public static final int DOLLTYPE_BLESSEDBAPHOMET = 105;
	public static final int DOLLTYPE_BLESSEDICEQUEEN = 106;
	public static final int DOLLTYPE_BLESSEDKURTZ = 107;


	public static final int DOLLTYPE_BAPHOMET = 3000091;
	public static final int DOLLTYPE_ICEQUEEN = 3000092;
	public static final int DOLLTYPE_KURTZ = 3000093;

	// public static final int DOLL_TIME = 1800000;

	private static Random _random = new Random(System.nanoTime());
	private int _dollType;
	private int _itemObjId;
	private ScheduledFuture<?> _future = null;

	private static int Buff[] = { 26, 42, 43, 79 }; // Dex, Strength, Hey, Avengers

	// Handling when there is no target
		@Override
		public boolean noTarget() {
			if(_master == null) {
				return false;
			}
			if (_master != null
					&& (_master.isDead() || (!(_master instanceof L1RobotInstance) && _master
							.getNetConnection() == null))) {
				deleteDoll();
				return true;
			} else if (_master != null && _master.getMapId() == getMapId()) {
				if (getLocation().getTileLineDistance(_master.getLocation()) > 2) {
					int dir = moveDirection(_master.getMapId(), _master.getX(),
							_master.getY());
					if (dir == -1) {
						if(_master == null) return false;
						teleport(_master.getX(), _master.getY(), getMoveState()
								.getHeading());
					} else {
						setDirectionMove(dir);
						setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
					}
				}
				return false;
			}
		if (_master == null) {
			deleteDoll();
			return true;
		}

		if (_master.getMapId() != getMapId()) {
			teleport(_master.getX(), _master.getY(), getMoveState()
					.getHeading(), _master.getMapId());
			return false;
		}

		return false;
	}

	// for timekeeping
	class DollTimer implements Runnable {
		@Override
		public void run() {
			try {

				if (_destroyed) { // Check if it has already been destroyed
					return;
				}
				deleteDoll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class HelpTimer implements Runnable {
		@Override
		public void run() {
			try {

				if (_destroyed) { //Check if it has already been destroyed
					return;
				}
				getHelperAction();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class RemaningTimer implements Runnable {
		@Override
		public void run() {
			try {
				if (_destroyed || getMaster() == null) { // Check if it has already been destroyed
					return;
				}
				L1ItemInstance _item = getMaster().getInventory().getItem(
						getItemObjId());
				if (_item == null) {
					deleteDoll();
					return;
				}
				L1PcInstance pc = (L1PcInstance) getMaster();
				_item.getLastStatus().updateRemainingTime();
				if ((_item.getRemainingTime() - 1) > 0) {
					if (pc.getOnlineStatus() == 0) {
						return;
					}
					_item.setRemainingTime(_item.getRemainingTime() - 1);
				} else {
					deleteDoll();
					pc.getInventory().removeItem(_item, 1);
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public L1DollInstance(L1Npc template, L1PcInstance master, int dollType,
			int itemObjId, int dollTime) {
		super(template);
		setId(ObjectIdFactory.getInstance().nextId());

		setDollType(dollType);
		setItemObjId(itemObjId);
		GeneralThreadPool.getInstance().schedule(new DollTimer(), dollTime);

		setMaster(master);
		setX(master.getX() + _random.nextInt(5) - 2);
		setY(master.getY() + _random.nextInt(5) - 2);
		setMap(master.getMapId());
		getMoveState().setHeading(5);
		setLightSize(template.getLightSize());

		L1World.getInstance().storeObject(this);
		L1World.getInstance().addVisibleObject(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			onPerceive(pc);
		}
		master.addDoll(this);
		if (!isAiRunning()) {
			startAI();
		}
		if (isMpRegeneration()) {
			master.startMpRegenerationByDoll();
		}
		if (isHpRegeneration()) {
			master.startHpRegenerationByDoll();
		}

		int type = getDollType();
		if (type == DOLLTYPE_SNOWMAN) {
			master.getAC().addAc(-3);
		}
		if (type == DOLLTYPE_ETTINREMOVE) {
			_master.getAC().addAc(-2);
			_master.removeHasteSkillEffect();
			if (_master.getMoveState().getMoveSpeed() != 1) {
				_master.getMoveState().setMoveSpeed(1);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 1, -1);
				_master.sendPackets(sh, true);
				S_SkillHaste sh2 = new S_SkillHaste(_master.getId(), 1, 0);
				Broadcaster.broadcastPacket(_master, sh2, true);
			}
			_master.set에틴(true);
		}

		if (type == DOLLTYPE_SNOWMAN_A) {
			_master.addBowHitupByDoll(5);
		}

		if (type == DOLLTYPE_ANTHARAS) {
			hasadbuff(_master);
		}
		if (type == DOLLTYPE_헌신1등급) {
			hasadbuff(_master);
		}
		if (type == DOLLTYPE_MUMMYLORD) {
			hasadbuff(_master);
		}
		if (type == DOLLTYPE_DEATHKNIGHT) {
			hasadbuff(_master);
		}
		if (type == DOLLTYPE_BLESSEDMUMMYLORD) {
			hasadbuff(_master);
		}
		if (type == DOLLTYPE_BLESSEDDEATHKNIGHT) {
			hasadbuff(_master);
		}


		if (type == DOLLTYPE_COCKATRICE) {
			_master.addBowHitupByDoll(1);
			_master.addBowDmgupByDoll(1);
			// _master.addMpr(10);
		}
		if (type == DOLLTYPE_DRAGON_M || type == DOLLTYPE_DRAGON_W
				|| type == DOLLTYPE_HIGH_DRAGON_M
				|| type == DOLLTYPE_HIGH_DRAGON_W) {
			_master.addMpr(4);
		}
		if (type == DOLLTYPE_HUSUABI) {
			_master.addBowHitupByDoll(2);
			_master.addHitup(2);
			_master.addMaxHp(50);
			_master.addMaxMp(30);
		}

		/*
		 * 스턴내성 + 10 홀드내성 + 10 피틱 엠틱 + 10 스펠파워 + 1 추가데미지 + 1 명중 + 1
		 */
		if (type == DOLLTYPE_HW_HUSUABI) {
			_master.addTechniqueTolerance(10);
			_master.getAbility().addSp(1);
			_master.addHpr(10);
			_master.addMpr(10);
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.sendPackets(new S_SPMR(_master));
		}
		// 공성 1 추타 1 스펠 1 피 50 엠 30 스턴내성 + 10
		if (type == DOLLTYPE_튼튼한기사) {
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.addBowDmgup(1);
			_master.addBowHitup(1);
			_master.getAbility().addSp(1);
			_master.addMaxHp(50);
			_master.addMaxMp(30);
			_master.addTechniqueTolerance(10);
			_master.sendPackets(new S_SPMR(_master));
		}

		if (type == DOLLTYPE_RAMIA) {
			_master.addMpr(4);
			// _master.addTechniqueTolerance(5);

		}

		if (type == DOLLTYPE_BUGBEAR) {
			_master.sendPackets(new S_NewCreateItem("무게", _master));
		}

		if (type == DOLLTYPE_SPARTOI) {
			_master.addTechniqueTolerance(10);
			_master.addDmgup(2);
		}
		// if (type == DOLLTYPE_ELDER) {
		// _master.addMpr(18);
		// }
		if (type == DOLLTYPE_ETTIN) {
			_master.getAC().addAc(+2);
			_master.removeHasteSkillEffect();
			if (_master.getMoveState().getMoveSpeed() != 1) {
				_master.getMoveState().setMoveSpeed(1);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 1, -1);
				_master.sendPackets(sh, true);
				S_SkillHaste sh2 = new S_SkillHaste(_master.getId(), 1, 0);
				Broadcaster.broadcastPacket(_master, sh2, true);
			}
			_master.set에틴(true);
		}

		if (type == DOLLTYPE_LICH) {
			_master.getAbility().addSp(2);
			_master.addMaxHp(80);
			_master.sendPackets(new S_SPMR(_master), true);
		}
		if (type == DOLLTYPE_BLESSEDLICH) {
			_master.getAbility().addSp(2);
			_master.addMaxHp(80);
			_master.sendPackets(new S_SPMR(_master), true);
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}


		if (type == DOLLTYPE_GREMLIN) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.addBowDmgup(2);
			_master.addBowHitup(2);
			_master.getAbility().addSp(2);
			_master.addMaxHp(30);

			_master.sendPackets(new S_SPMR(_master), true);
			_master.sendPackets(new S_HPUpdate(_master), true);
		}

		if (type >= DOLLTYPE_BLEG && type <= DOLLTYPE_GREG) {
			_master.getAbility().addSp(1);
			_master.addDmgup(2);
			_master.sendPackets(new S_SPMR(_master), true);
		}

		if (type == DOLLTYPE_GANGNAM) {
			if (getNpcId() == 100431) { // 강남 스타일
				_master.getAbility().addSp(1);
				_master.addMaxHp(30);
				_master.sendPackets(new S_SPMR(_master), true);
			} else if (getNpcId() == 100432) { // 새
				_master.addBowDmgupByDoll(2);
				_master.addMaxHp(30);
			} else if (getNpcId() == 100433) { // 챔피언
				_master.addDmgup(2);
				_master.addMaxHp(30);
			}
		}
		if (type == DOLLTYPE_DANDY || type == DOLLTYPE_SERIE) {
			_master.addMaxHp(50);
			_master.addMaxMp(30);
		}
		if (type == DOLLTYPE_DRAKE) {
			_master.addBowDmgupByDoll(2);
		}
		if (type == DOLLTYPE_BLESSEDDRAKE) {
			_master.addBowDmgupByDoll(2);
			_master.getAC().addAc(-2);
		}
		if (type == DOLLTYPE_남자_여자) {
			_master.addMaxHp(100);
		}
		if (type == DOLLTYPE_CYCLOPS) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.addTechniqueTolerance(12);
		}
		if (type == DOLLTYPE_BLESSEDCYCLOPS) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.addTechniqueTolerance(12);
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}
		if (type == DOLLTYPE_SNOWMAN2) {
			_master.addDmgup(1);
			_master.addHitup(1);
		}

		if (type == DOLLTYPE_EVENTDOLL) {
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.addBowHitupByDoll(1);
			_master.addBowDmgupByDoll(1);
			_master.getAbility().addSp(1);
			_master.addMaxHp(100);
			_master.addMaxMp(50);
			_master.sendPackets(new S_SPMR(_master));
			_master.sendPackets(new S_HPUpdate(_master), true);
			_master.sendPackets(
					new S_MPUpdate(_master.getCurrentMp(), _master.getMaxMp()),
					true);
		}
		if (type == DOLLTYPE_SUCCUBUSQUEEN) {
			_master.getAbility().addSp(1);
			_master.sendPackets(new S_SPMR(_master));
		}
		if (type == DOLLTYPE_BLESSEDSUCCUBUSQUEEN) {
			_master.getAbility().addSp(1);
			_master.sendPackets(new S_SPMR(_master));
			_master.getAC().addAc(-2);

		}
		if (type == DOLLTYPE_BLESSEDGIANT) {
			_master.getAC().addAc(-2);

		}
		if (type == DOLLTYPE_BLESSEDBLACKELDER) {
			_master.getAC().addAc(-2);

		}
		if (type == DOLLTYPE_BLESSEDDIAMONDGOLEM) {
			_master.getAC().addAc(-2);

		}

		if (type == DOLLTYPE_KINGBUGBEAR) {
			_master.addTechniqueTolerance(8);
		}
		if (type == DOLLTYPE_BLESSEDKINGBUGGEAR) {
			_master.addTechniqueTolerance(8);
			_master.getAC().addAc(-2);
		}
		if (type == DOLLTYPE_TARAK) {// 타락
			_master.addTechniqueTolerance(10);
			_master.addDragonLangHit(5);
			_master.getAbility().addSp(3);
			_master.sendPackets(new S_SPMR(_master));
		}

		if (type == DOLLTYPE_BLESSEDTARAK) {
			_master.addTechniqueTolerance(10);
			_master.addDragonLangHit(5);
			_master.getAbility().addSp(3);
			_master.sendPackets(new S_SPMR(_master));
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}



		if (type == DOLLTYPE_WOODCHIP) {
			_master.addMaxHp(50);
			_master.sendPackets(new S_HPUpdate(_master), true);
		}
		if (type == DOLLTYPE_LAVAGOLEM) {
			_master.addDmgup(1);
		}
		if (type == DOLLTYPE_KNIGHTVALD) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.addTechniqueHit(5);
		}
		if (type == DOLLTYPE_BLESSEDKNIGHTVALD) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.addTechniqueHit(5);
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}
		if (type == DOLLTYPE_BLESSEDIRIS) {
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}
		if (type == DOLLTYPE_VAMPIRE) {
			_master.addDmgup(2);
			_master.addHitup(2);
		}
		if (type == DOLLTYPE_BLESSEDVAMPIRE) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}

		if (type == DOLLTYPE_SEER) {
			_master.addBowDmgup(5);
		}
		if (type == DOLLTYPE_BLESSEDSEER) {
			_master.addBowDmgup(5);
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}
		if (type == DOLLTYPE_BLESSEDMUMMYLORD) {
			_master.getAC().addAc(-2);
			_master.getResistance().addPVPweaponTotalDamage(2);
		}
		if (type == DOLLTYPE_DEMON) {
			_master.addTechniqueHit(10);
			_master.addTechniqueTolerance(12);
		}

		if (type == DOLLTYPE_BLESSEDDEMON) {
			_master.addTechniqueHit(10);
			_master.addTechniqueTolerance(12);
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}

		if (type == DOLLTYPE_FAFURION) {
			_master.addTechniqueTolerance(8);
			_master.addSpiritTolerance(8);
			_master.addDragonLangTolerance(8);
			_master.addFearTolerance(8);
			_master.addTechniqueHit(3);
			_master.addSpiritHit(3);
			_master.addDragonLangHit(3);
			_master.addFearHit(3);
			_master.getAbility().addSp(4);
			_master.sendPackets(new S_SPMR(_master));
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(4);
			_master.getResistance().addcalcPcDefense(2);
		}
		if (type == DOLLTYPE_LINDVIOR) {
			_master.addTechniqueTolerance(8);
			_master.addSpiritTolerance(8);
			_master.addDragonLangTolerance(8);
			_master.addFearTolerance(8);
			_master.addTechniqueHit(3);
			_master.addSpiritHit(3);
			_master.addDragonLangHit(3);
			_master.addFearHit(3);
			_master.addBowDmgup(4);
			_master.addBowHitup(8);
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(4);
			_master.getResistance().addcalcPcDefense(2);
		}
		if (type == DOLLTYPE_VALAKAS) {
			_master.addTechniqueTolerance(8);
			_master.addSpiritTolerance(8);
			_master.addDragonLangTolerance(8);
			_master.addFearTolerance(8);
			_master.addTechniqueHit(3);
			_master.addSpiritHit(3);
			_master.addDragonLangHit(3);
			_master.addFearHit(3);
			_master.addDmgup(4);
			_master.addHitup(8);
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(4);
			_master.getResistance().addcalcPcDefense(2);
		}
		if (type == DOLLTYPE_BARANKA) {
			_master.addTechniqueTolerance(12);
			_master.addSpiritHit(10);
		}
		if (type == DOLLTYPE_BLESSEDBARANKA) {
			_master.addTechniqueTolerance(12);
			_master.addSpiritHit(10);
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}
		if (type == DOLLTYPE_BAPHOMET) {
			_master.addTechniqueTolerance(10);
			_master.addFearHit(5);
		}

		if (type == DOLLTYPE_BLESSEDBAPHOMET) {
			_master.addTechniqueTolerance(10);
			_master.addFearHit(5);
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}
		if (type == DOLLTYPE_ICEQUEEN) {
			_master.addBowHitupByDoll(5);
			_master.addBowDmgupByDoll(5);
			_master.addTechniqueTolerance(10);
			_master.addSpiritHit(5);
		}
		if (type == DOLLTYPE_BLESSEDICEQUEEN) {
			_master.addBowHitupByDoll(5);
			_master.addBowDmgupByDoll(5);
			_master.addTechniqueTolerance(10);
			_master.addSpiritHit(5);
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}
		if (type == DOLLTYPE_BLESSEDKURTZ) {
			_master.getAC().addAc(-5);
			_master.addTechniqueTolerance(10);
			_master.addDragonLangHit(5);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}
		if (type == DOLLTYPE_KURTZ) {
			_master.getAC().addAc(-2);
			_master.addTechniqueTolerance(10);
			_master.addDragonLangHit(5);
		}

		if (type == DOLLTYPE_ANTHARAS) {
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(4);
			_master.getResistance().addcalcPcDefense(2);
		}
		if (type == DOLLTYPE_BLESSEDDEATHKNIGHT) {
			_master.getAC().addAc(-3);
			_master.getResistance().addPVPweaponTotalDamage(2);
			_master.getResistance().addcalcPcDefense(4);
		}
		startHelpTimer();
		L1ItemInstance _item = getMaster().getInventory().getItem(
				getItemObjId());
		if (_item != null && _item.getRemainingTime() > 0)
			GeneralThreadPool.getInstance().schedule(new RemaningTimer(), 1000);
	}

	public void deleteDoll() {
		try {
			_master.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _master), true);
			_master.sendPackets(new S_ACTION_UI(S_ACTION_UI.SUMMON_PET_NOTI, 0, this, null, false));
			//_master.sendPackets((ServerBasePacket)new S_SkillIconGFX(56, 0, 0, 0), true);
			_master.sendPackets(new S_OwnCharStatus(this._master), true);
			_master.sendPackets(new S_PacketBox(132, this._master.get_PlusEr()), true);
			if (isMpRegeneration()) {
				_master.stopMpRegenerationByDoll();
			} else if (isHpRegeneration()) {
				_master.stopHpRegenerationByDoll();
			}
			int type = getDollType();
			if (type == DOLLTYPE_SNOWMAN) {
				_master.getAC().addAc(3);
			}

			if (type == DOLLTYPE_ETTINREMOVE) {
				_master.getAC().addAc(2);
				_master.removeHasteSkillEffect();
				_master.getMoveState().setMoveSpeed(0);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 0, 0);
				_master.sendPackets(sh);
				Broadcaster.broadcastPacket(_master, sh, true);
				_master.set에틴(false);
			}

			if (type == DOLLTYPE_SNOWMAN_A) {
				_master.addBowHitupByDoll(-5);
			}

			if (type == DOLLTYPE_COCKATRICE) {
				_master.addBowHitupByDoll(-1);
				_master.addBowDmgupByDoll(-1);
				// _master.addMpr(-10);
			}
			if (type == DOLLTYPE_HUSUABI) {
				_master.addBowHitupByDoll(-2);
				_master.addHitup(-2);
				_master.addMaxHp(-50);
				_master.addMaxMp(-30);

			}

			if (type == DOLLTYPE_BUGBEAR) {
				_master.sendPackets(new S_NewCreateItem("무게", _master));
			}

			if (type == DOLLTYPE_HW_HUSUABI) {
				_master.addTechniqueTolerance(-10);
				_master.getAbility().addSp(-1);
				_master.addHpr(-10);
				_master.addMpr(-10);
				_master.addDmgup(-1);
				_master.addHitup(-1);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_튼튼한기사) {
				_master.addDmgup(-1);
				_master.addHitup(-1);
				_master.addBowDmgup(-1);
				_master.addBowHitup(-1);
				_master.getAbility().addSp(-1);
				_master.addMaxHp(-50);
				_master.addMaxMp(-30);
				_master.addTechniqueTolerance(-10);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_DRAGON_M || type == DOLLTYPE_DRAGON_W
					|| type == DOLLTYPE_HIGH_DRAGON_M
					|| type == DOLLTYPE_HIGH_DRAGON_W) {
				_master.addMpr(-4);
			}
			if (type == DOLLTYPE_RAMIA) {
				_master.addMpr(-4);
				// _master.addTechniqueTolerance(-5);
			}
			if (type == DOLLTYPE_SPARTOI) {
				_master.addTechniqueTolerance(-10);
				_master.addDmgup(-2);
			}
			if (type == DOLLTYPE_ELDER) {
				_master.addMpr(-18);
			}
			if (type == DOLLTYPE_ETTIN) {
				_master.getAC().addAc(-2);
				_master.removeHasteSkillEffect();
				_master.getMoveState().setMoveSpeed(0);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 0, 0);
				_master.sendPackets(sh);
				Broadcaster.broadcastPacket(_master, sh, true);
				_master.set에틴(false);
			}
			if (type == DOLLTYPE_LICH) {
				_master.getAbility().addSp(-2);
				_master.addMaxHp(-80);
				_master.sendPackets(new S_SPMR(_master), true);
			}
			if (type == DOLLTYPE_BLESSEDLICH) {
				_master.getAbility().addSp(-2);
				_master.addMaxHp(-80);
				_master.sendPackets(new S_SPMR(_master), true);
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}

			if (type == DOLLTYPE_EVENTDOLL) {
				_master.addDmgup(-1);
				_master.addHitup(-1);
				_master.addBowHitupByDoll(-1);
				_master.addBowDmgupByDoll(-1);
				_master.getAbility().addSp(-1);
				_master.addMaxHp(-100);
				_master.addMaxMp(-50);
				_master.sendPackets(new S_SPMR(_master));
				_master.sendPackets(new S_HPUpdate(_master), true);
				_master.sendPackets(new S_MPUpdate(_master.getCurrentMp(),
						_master.getMaxMp()), true);
			}

			// 1800초 동안 근거리 대미지 + 1 원거리 대미지 + 1 명중 + 1 sp + 1 마법 명중 +1 최대 HP +
			// 30 64초마다 MP 10회복
			if (type == DOLLTYPE_GREMLIN) {
				_master.addDmgup(-2);
				_master.addHitup(-2);

				_master.addBowDmgup(-2);
				_master.addBowHitup(-2);

				_master.getAbility().addSp(-2);
				_master.addMaxHp(-30);
				_master.sendPackets(new S_HPUpdate(_master), true);
				_master.sendPackets(new S_SPMR(_master), true);
			}

			if (type >= DOLLTYPE_BLEG && type <= DOLLTYPE_GREG) {
				_master.getAbility().addSp(-1);
				_master.addDmgup(-2);
				_master.sendPackets(new S_SPMR(_master), true);
			}
			if (type == DOLLTYPE_GANGNAM) {
				if (getNpcId() == 100431) { // 강남 스타일
					_master.getAbility().addSp(-1);
					_master.addMaxHp(-30);
					_master.sendPackets(new S_SPMR(_master), true);
				} else if (getNpcId() == 100432) { // 새
					_master.addBowDmgupByDoll(-2);
					_master.addMaxHp(-30);
				} else if (getNpcId() == 100433) { // 챔피언
					_master.addDmgup(-2);
					_master.addMaxHp(-30);
				}
			}
			if (type == DOLLTYPE_DANDY || type == DOLLTYPE_SERIE) {
				_master.addMaxHp(-50);
				_master.addMaxMp(-30);
			}
			if (type == DOLLTYPE_DRAKE) {
				_master.addBowDmgupByDoll(-2);
			}
			if (type == DOLLTYPE_BLESSEDDRAKE) {
				_master.addBowDmgupByDoll(-2);
				_master.getAC().addAc(2);
			}
			if (type == DOLLTYPE_남자_여자) {
				_master.addMaxHp(-100);
			}

			if (type == DOLLTYPE_SNOWMAN2) {
				_master.addDmgup(-1);
				_master.addHitup(-1);
			}

			if (type == DOLLTYPE_CYCLOPS) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
				_master.addTechniqueTolerance(-12);
			}
			if (type == DOLLTYPE_BLESSEDCYCLOPS) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
				_master.addTechniqueTolerance(-12);
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}

			if (type == DOLLTYPE_SUCCUBUSQUEEN) {
				_master.getAbility().addSp(-1);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_BLESSEDSUCCUBUSQUEEN) {
				_master.getAbility().addSp(-1);
				_master.sendPackets(new S_SPMR(_master));
				_master.getAC().addAc(2);

			}
			if (type == DOLLTYPE_BLESSEDGIANT) {
				_master.getAC().addAc(2);

			}
			if (type == DOLLTYPE_BLESSEDBLACKELDER) {
				_master.getAC().addAc(2);

			}
			if (type == DOLLTYPE_BLESSEDDIAMONDGOLEM) {
				_master.getAC().addAc(2);

			}

			if (type == DOLLTYPE_KINGBUGBEAR) {
				_master.addTechniqueTolerance(-8);
			}
			if (type == DOLLTYPE_BLESSEDKINGBUGGEAR) {
				_master.addTechniqueTolerance(-8);
				_master.getAC().addAc(2);
			}
			if (type == DOLLTYPE_TARAK) {// 타락
				_master.addTechniqueTolerance(-10);
				_master.addDragonLangHit(-5);
				_master.getAbility().addSp(-3);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_BLESSEDTARAK) {// 타락
				_master.addTechniqueTolerance(-10);
				_master.addDragonLangHit(-5);
				_master.getAbility().addSp(-3);
				_master.sendPackets(new S_SPMR(_master));
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}


			if (type == DOLLTYPE_WOODCHIP) {
				_master.addMaxHp(-50);
				_master.sendPackets(new S_HPUpdate(_master), true);
			}
			if (type == DOLLTYPE_LAVAGOLEM) {
				_master.addDmgup(-1);
			}
			if (type == DOLLTYPE_KNIGHTVALD) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
				_master.addTechniqueHit(-5);
			}
			if (type == DOLLTYPE_BLESSEDKNIGHTVALD) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
				_master.addTechniqueHit(-5);
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}
			if (type == DOLLTYPE_BLESSEDIRIS) {
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}
			if (type == DOLLTYPE_VAMPIRE) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
			}
			if (type == DOLLTYPE_BLESSEDVAMPIRE) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}
			if (type == DOLLTYPE_SEER) {
				_master.addBowDmgup(-5);
			}
			if (type == DOLLTYPE_BLESSEDSEER) {
				_master.addBowDmgup(-5);
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}
			if (type == DOLLTYPE_BLESSEDMUMMYLORD) {
				_master.getAC().addAc(2);
				_master.getResistance().addPVPweaponTotalDamage(-2);
			}
			if (type == DOLLTYPE_DEMON) {
				_master.addTechniqueTolerance(-12);
				_master.addTechniqueHit(-10);
			}
			if (type == DOLLTYPE_BLESSEDDEMON) {
				_master.addTechniqueHit(-10);
				_master.addTechniqueTolerance(-12);
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}
			if (type == DOLLTYPE_FAFURION) {
				_master.addTechniqueTolerance(-8);
				_master.addSpiritTolerance(-8);
				_master.addDragonLangTolerance(-8);
				_master.addFearTolerance(-8);
				_master.addTechniqueHit(-3);
				_master.addSpiritHit(-3);
				_master.addDragonLangHit(-3);
				_master.addFearHit(-3);
				_master.getAbility().addSp(-4);
				_master.sendPackets(new S_SPMR(_master));
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-4);
				_master.getResistance().addcalcPcDefense(-2);
			}
			if (type == DOLLTYPE_LINDVIOR) {
				_master.addTechniqueTolerance(-8);
				_master.addSpiritTolerance(-8);
				_master.addDragonLangTolerance(-8);
				_master.addFearTolerance(-8);
				_master.addTechniqueHit(-3);
				_master.addSpiritHit(-3);
				_master.addDragonLangHit(-3);
				_master.addFearHit(-3);
				_master.addBowDmgup(-4);
				_master.addBowHitup(-8);
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-4);
				_master.getResistance().addcalcPcDefense(-2);
			}
			if (type == DOLLTYPE_VALAKAS) {
				_master.addTechniqueTolerance(-8);
				_master.addSpiritTolerance(-8);
				_master.addDragonLangTolerance(-8);
				_master.addFearTolerance(-8);
				_master.addTechniqueHit(-3);
				_master.addSpiritHit(-3);
				_master.addDragonLangHit(-3);
				_master.addFearHit(-3);
				_master.addDmgup(-4);
				_master.addHitup(-8);
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-4);
				_master.getResistance().addcalcPcDefense(-2);
			}
			if (type == DOLLTYPE_BARANKA) {
				_master.addTechniqueTolerance(-12);
				_master.addSpiritHit(-10);
			}
			if (type == DOLLTYPE_BLESSEDBARANKA) {
				_master.addTechniqueTolerance(-12);
				_master.addSpiritHit(-10);
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}
		    if (type == DOLLTYPE_BAPHOMET) {
			   _master.addTechniqueTolerance(-10);
			   _master.addFearHit(-5);
	     	}
		    if (type == DOLLTYPE_BLESSEDBAPHOMET) {
				_master.addTechniqueTolerance(-10);
				_master.addFearHit(-5);
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}
		    if (type == DOLLTYPE_ICEQUEEN) {
			   _master.addBowHitupByDoll(-5);
			   _master.addBowDmgupByDoll(-5);
			   _master.addTechniqueTolerance(-10);
			   _master.addSpiritHit(-5);
		    }
		    if (type == DOLLTYPE_BLESSEDICEQUEEN) {
				_master.addBowHitupByDoll(-5);
				_master.addBowDmgupByDoll(-5);
				_master.addTechniqueTolerance(-10);
				_master.addSpiritHit(-5);
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}
			if (type == DOLLTYPE_BLESSEDKURTZ) {
				_master.getAC().addAc(5);
				_master.addTechniqueTolerance(-10);
				_master.addDragonLangHit(-5);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}
		    if (type == DOLLTYPE_KURTZ) {
			   _master.getAC().addAc(2);
			   _master.addTechniqueTolerance(-10);
			   _master.addDragonLangHit(-5);
		    }

		    if (type == DOLLTYPE_ANTHARAS) {
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-4);
				_master.getResistance().addcalcPcDefense(-2);
			}

		    if (type == DOLLTYPE_BLESSEDDEATHKNIGHT) {
				_master.getAC().addAc(3);
				_master.getResistance().addPVPweaponTotalDamage(-2);
				_master.getResistance().addcalcPcDefense(-4);
			}
			stopHelpTimer();
			if (_master.isDead() || !(_master instanceof L1RobotInstance)) {
				S_SkillSound sh = new S_SkillSound(getId(), 5936);
				_master.sendPackets(sh);
				Broadcaster.broadcastPacket(_master, sh, true);
			}
			if (_master instanceof L1RobotInstance) {
				L1RobotInstance bot = (L1RobotInstance) _master;
				bot.인형스폰 = false;
			}
		} catch (Exception e) {
		}
		try {
			if (_master.getDollList() != null) {
				_master.removeDoll(this);
			}
		} catch (Exception e) {
		}
		try {
			deleteMe();
		} catch (Exception e) {
		}
	}

	@Override
	public void onPerceive(L1PcInstance perceivedFrom) {
		perceivedFrom.getNearObjects().addKnownObject(this);
		S_DollPack dp = new S_DollPack(this, perceivedFrom);
		perceivedFrom.sendPackets(dp, true);
	}

	@Override
	public void onItemUse() {
		if (!isActived()) {
			// 100%의 확률로 헤이 파업 일부 사용
			useItem(USEITEM_HASTE, 100);
		}
	}

	@Override
	public void onGetItem(L1ItemInstance item) {
		if (getNpcTemplate().get_digestitem() > 0) {
			setDigestItem(item);
		}
		if (Arrays.binarySearch(haestPotions, item.getItem().getItemId()) >= 0) {
			useItem(USEITEM_HASTE, 100);
		}
	}

	public int getDollType() {
		return _dollType;
	}

	public void setDollType(int i) {
		_dollType = i;
	}

	public int getItemObjId() {
		return _itemObjId;
	}

	public void setItemObjId(int i) {
		_itemObjId = i;
	}

	public int getDamageByDoll(L1Character target) {
		int damage = 0;
		int type = getDollType();
		int chance = _random.nextInt(100) + 1;
		if (chance <= 7) {
			if (type == DOLLTYPE_WEREWOLF) {
				damage = 15;
			} else if (type == DOLLTYPE_CRUSTACEAN) {
				damage = 16;
			} else if (type >= DOLLTYPE_BLEG && type <= DOLLTYPE_GREG) {
				if ((target instanceof L1PcInstance) || (getLocation().getTileLineDistance(
						new Point(target.getX(), target.getY())) > 10))
					return 0;
				int gfx = 0;
				if (type == DOLLTYPE_GREG)
					gfx = 4022;
				else if (type == DOLLTYPE_BLEG)
					gfx = 1809;
				else if (type == DOLLTYPE_LEDEGG)
					gfx = 1583;
				else if (type == DOLLTYPE_ELEG)
					gfx = 7331;
				Broadcaster.broadcastPacket(this, new S_UseAttackSkill(this,
						target.getId(), gfx, target.getX(), target.getY(), 1),
						true);
				Broadcaster.broadcastPacketExceptTargetSight(target,
						new S_DoActionGFX(target.getId(),
								ActionCodes.ACTION_Damage), this, true);
				/*
				 * if (_master instanceof L1PcInstance) { L1PcInstance pc =
				 * (L1PcInstance) _master; pc.sendPackets(new
				 * S_SkillSound(_master.getId(), 7624), true); }
				 * Broadcaster.broadcastPacket(_master, new
				 * S_SkillSound(_master.getId(), 7624), true);
				 */
				return 15;
			} else
				return 0;
			S_SkillSound ss = new S_SkillSound(_master.getId(), 6319);
			if (_master instanceof L1PcInstance) {
				L1PcInstance pc = _master;
				pc.sendPackets(ss);
			}
			Broadcaster.broadcastPacket(_master, ss, true);
		}

		return damage;
	}

	public double getAddExpByDoll() {
		double addexp = 1;

		if (getDollType() == DOLLTYPE_EVENTDOLL) {
			if (_master.getLevel() < 81) {
				addexp = 2;
			}
		} else if (getDollType() == DOLLTYPE_GIANT) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_BLESSEDGIANT) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_행운의기사) {
			addexp = 1.3;
		} else if (getDollType() == DOLLTYPE_DEATHKNIGHT) {
			addexp = 1.2;
		} else if (getDollType() == DOLLTYPE_BLESSEDDEATHKNIGHT) {
			addexp = 1.2;
		} else if (getDollType() == DOLLTYPE_ANTHARAS) {
			addexp = 1.25;
		} else if (getDollType() == DOLLTYPE_헌신1등급) {
			addexp = 1.25;
		} else if (getDollType() == DOLLTYPE_MUMMYLORD) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_BLESSEDMUMMYLORD) {
			addexp = 1.1;

		} else if (getDollType() == DOLLTYPE_MERMAID) {
			addexp = 1.03;
		}
		return addexp;
	}

	public int getAinhasadblessByDoll() {
		int addAinhasadbless = 1;

		if (getDollType() == DOLLTYPE_DEATHKNIGHT) {
			addAinhasadbless = 7;
		} else if (getDollType() == DOLLTYPE_ANTHARAS) {
			addAinhasadbless = 7;
		} else if (getDollType() == DOLLTYPE_헌신1등급) {
			addAinhasadbless = 8;
		} else if (getDollType() == DOLLTYPE_MUMMYLORD) {
			addAinhasadbless = 2;
		} else if (getDollType() == DOLLTYPE_BLESSEDMUMMYLORD) {
			addAinhasadbless = 2;
		} else if (getDollType() == DOLLTYPE_BLESSEDDEATHKNIGHT) {
			addAinhasadbless = 7;
		}
		return addAinhasadbless;
	}

	private static double calcAttrResistance(L1Character cha, int attr) {
		int resist = 0;
		if (cha instanceof L1PcInstance) {
			switch (attr) {
			case L1Skills.ATTR_EARTH:
				resist = cha.getResistance().getEarth();
				break;
			case L1Skills.ATTR_FIRE:
				resist = cha.getResistance().getFire();
				break;
			case L1Skills.ATTR_WATER:
				resist = cha.getResistance().getWater();
				break;
			case L1Skills.ATTR_WIND:
				resist = cha.getResistance().getWind();
				break;
			}
		}
		double attrDeffence = resist / 4 * 0.01;

		return attrDeffence;
	}

	public static int calcMrDefense(int MagicResistance, int dmg) {
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

	public int getMagicDamageByDoll(L1Character _target) {// 장로 인형 추가 대미지용 메서드
		int magicDamage = 0;
		int type = getDollType();
		int chance = _random.nextInt(100) + 1;

		int pcInt = _master.getAbility().getTotalInt();
		int sp = _master.getAbility().getSp();

		if (chance <= 5) {// 발동 확률
			if (type == DOLLTYPE_BLACKELDER || type == DOLLTYPE_BLESSEDBLACKELDER) {
				magicDamage = (pcInt + sp) / 2; // 추가대미지로 줄 대미지 수치(변수)
				S_SkillSound ss = new S_SkillSound(_target.getId(), 11736);
				if (_target instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) _target;
					pc.sendPackets(ss);
				}
				Broadcaster.broadcastPacket(_target, ss);
				ss.clear();
				ss = null;
				if (magicDamage < 10)
					magicDamage = 10;
				if (magicDamage > 50)
					magicDamage = 50;
			} else if (type == DOLLTYPE_DEATHKNIGHT || type == DOLLTYPE_헌신1등급 || type == DOLLTYPE_BLESSEDDEATHKNIGHT) {
				/*
				 * int targetMr =
				 * _target.getResistance().getEffectedMrBySkill(); int randmg =
				 * _random.nextInt((pcInt*5)); magicDamage = sp*6 + randmg;
				 * magicDamage = calcMrDefense(targetMr, (int)magicDamage);
				 * magicDamage -= magicDamage * calcAttrResistance(_target, 2);
				 */

				magicDamage = _random.nextInt(50) + 50;/* pcInt+sp */// 추가대미지로
																		// 줄 대미지
																		// 수치(변수)

				S_EffectLocation ss = new S_EffectLocation(this.getX(),
						this.getY(), 11660);
				/*
				 * if (_target instanceof L1PcInstance) { L1PcInstance pc =
				 * (L1PcInstance) _target; pc.sendPackets(ss); }
				 */
				Broadcaster.broadcastPacket(this, ss);
				// if(magicDamage < 40)magicDamage=40;
				// if(magicDamage > 80)magicDamage=80;
			}
		}

		return magicDamage;
	}

	public void attackPoisonDamage(L1PcInstance pc, L1Character cha) {
		int type = getDollType();
		if (type == DOLLTYPE_RAMIA) {
			int chance = _random.nextInt(100) + 1;
			if (10 >= chance) {
				L1DamagePoison.doInfection(pc, cha, 3000, 10);
			}
		}
	}

	public int getprobabilityLevelAdd() { //공포 적중
		int addprobability = 0;
		if (getDollType() == DOLLTYPE_BAPHOMET ||getDollType() == DOLLTYPE_BLESSEDBAPHOMET) {
			addprobability = 5; // 5
		}
		return addprobability;
	}

	/*public int getStunLevelAdd() {
		int addStun = 0;
		if (getDollType() == DOLLTYPE_나이트발드) {
			addStun = 5;
		} else if (getDollType() == DOLLTYPE_데몬) {
			addStun = 10;

		}

		return addStun;
	}*/

	/*public int getamorLevelAdd() { //아머
		int addamor = 0;
		 if (getDollType() == DOLLTYPE_바란카) {
			addamor = 10;
		}
		return addamor;
	}*/
	public int get마법명중() { //마법명중
		int addmagic = 0;
		 if (getDollType() == DOLLTYPE_TARAK || getDollType() == DOLLTYPE_BLESSEDTARAK) {
			 addmagic = 5;
		}else if (getDollType() == DOLLTYPE_FAFURION) {
			 addmagic = 8;
		}
		return addmagic;
	}
	public int getDamageReductionByDoll() {
		int DamageReduction = 0;
		if (getDollType() == DOLLTYPE_LAVAGOLEM
				|| getDollType() == DOLLTYPE_STONEGOLEM
				|| getDollType() == DOLLTYPE_GIANT
				|| getDollType() == DOLLTYPE_BLESSEDGIANT) {
			DamageReduction = 1;
			/*
			 * int chance = _random.nextInt(100) + 1; if (chance <= 2) {
			 * DamageReduction = 15; S_SkillSound ss = new
			 * S_SkillSound(_master.getId(), 6320); if (_master instanceof
			 * L1PcInstance) { L1PcInstance pc = (L1PcInstance) _master;
			 * pc.sendPackets(ss); } Broadcaster.broadcastPacket(_master, ss,
			 * true); }
			 */
		} else if (getDollType() == DOLLTYPE_DIAMONDGOLEM) {
			DamageReduction = 2;
		} else if (getDollType() == DOLLTYPE_BLESSEDDIAMONDGOLEM) {
			DamageReduction = 2;
		} else if (getDollType() == DOLLTYPE_DEATHKNIGHT) {
			DamageReduction = 5;
		} else if (getDollType() == DOLLTYPE_BLESSEDDEATHKNIGHT) {
			DamageReduction = 5;
		} else if (getDollType() == DOLLTYPE_ANTHARAS) {
			DamageReduction = 6;
		} else if (getDollType() == DOLLTYPE_헌신1등급) {
			DamageReduction = 5;
		} else if (getDollType() == DOLLTYPE_MUMMYLORD) {
			DamageReduction = 2;
		} else if (getDollType() == DOLLTYPE_BLESSEDMUMMYLORD) {
			DamageReduction = 2;

		} else if (getDollType() == DOLLTYPE_IRIS || getDollType() == DOLLTYPE_BLESSEDIRIS
				|| getDollType() == DOLLTYPE_KURTZ || getDollType() == DOLLTYPE_BLESSEDKURTZ) {
			DamageReduction = 3;
		}

		return DamageReduction;
	}

	public boolean isMpRegeneration() {
		boolean isMpRegeneration = false;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_SUCCUBUS:
		case DOLLTYPE_ELDER:
		case DOLLTYPE_SNOWMAN_B:
		case DOLLTYPE_GANGNAM:
		case DOLLTYPE_DANDY:
		case DOLLTYPE_남자_여자:
		case DOLLTYPE_GREMLIN:
		case DOLLTYPE_BLACKELDER:
		case DOLLTYPE_BLESSEDBLACKELDER:
		case DOLLTYPE_SUCCUBUSQUEEN:
		case DOLLTYPE_BLESSEDSUCCUBUSQUEEN:
		case DOLLTYPE_ANTHARAS:
		case DOLLTYPE_헌신1등급:
		case DOLLTYPE_FAFURION:
		case DOLLTYPE_LINDVIOR:
		case DOLLTYPE_VALAKAS:
		case DOLLTYPE_EVENTDOLL:
		case DOLLTYPE_KINGBUGBEAR:
		case DOLLTYPE_BLESSEDKINGBUGGEAR:
		case DOLLTYPE_DRAKE:
		case DOLLTYPE_BLESSEDDRAKE:
		case DOLLTYPE_MUMMYLORD:
		case DOLLTYPE_BLESSEDMUMMYLORD:
			isMpRegeneration = true;
			break;
		}
		return isMpRegeneration;
	}

	public boolean isHpRegeneration() {
		boolean isHpRegeneration = false;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_SEADANCER:
		case DOLLTYPE_SNOWMAN_C:
		case DOLLTYPE_SERIE:
		case DOLLTYPE_SEER:
		case DOLLTYPE_BLESSEDSEER:
			isHpRegeneration = true;
			break;
		}
		return isHpRegeneration;
	}
	public int fou_DamageUp() { // 포우슬레이어 데미지업
		int fou = 0;
		switch (getDollType()) {
		case DOLLTYPE_IRIS:
		case DOLLTYPE_KURTZ:
		case DOLLTYPE_BLESSEDIRIS:
		case DOLLTYPE_BLESSEDKURTZ:
			fou = 10;
			break;
		}
		return fou;
	}

	public int getWeightReductionByDoll() {
		int weightReduction = 0;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_HIGH_DRAGON_M:
		case DOLLTYPE_HIGH_DRAGON_W:
			weightReduction = 5;
			break;
		case DOLLTYPE_BUGBEAR:
			weightReduction = 500;
			break;
		case DOLLTYPE_남자_여자:
			weightReduction = 15;
			break;
		}
		return weightReduction;
	}

	public int getMpRegenerationValues() {
		int regenMp = 0;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_VALAKAS:
		case DOLLTYPE_LINDVIOR:
		case DOLLTYPE_FAFURION:
			regenMp = 5;
			break;
		case DOLLTYPE_DRAKE:
		case DOLLTYPE_BLESSEDDRAKE:
			regenMp = 6;
			break;
		case DOLLTYPE_GREMLIN:
		case DOLLTYPE_KINGBUGBEAR:
		case DOLLTYPE_BLESSEDKINGBUGGEAR:
			regenMp = 10;
			break;
		case DOLLTYPE_EVENTDOLL:
		case DOLLTYPE_SUCCUBUS:
		case DOLLTYPE_ELDER:
		case DOLLTYPE_GANGNAM:
		case DOLLTYPE_DANDY:
		case DOLLTYPE_남자_여자:
		case DOLLTYPE_BLACKELDER:
		case DOLLTYPE_BLESSEDBLACKELDER:
		case DOLLTYPE_SUCCUBUSQUEEN:
		case DOLLTYPE_BLESSEDSUCCUBUSQUEEN:
		case DOLLTYPE_MUMMYLORD:
		case DOLLTYPE_BLESSEDMUMMYLORD:
		case DOLLTYPE_ANTHARAS:
		case DOLLTYPE_헌신1등급:
			regenMp = 15;
			break;
		case DOLLTYPE_SNOWMAN_B:
			regenMp = 20;
			break;

		}
		return regenMp;
	}

	public int getHpRegenerationValues() {
		int regenHp = 0;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_SEADANCER:
		case DOLLTYPE_SNOWMAN_C:
		case DOLLTYPE_SERIE:
			regenHp = 25;
			break;
		case DOLLTYPE_SEER:
		case DOLLTYPE_BLESSEDSEER:
			regenHp = 30;
			break;
		}
		return regenHp;
	}

	private void getHelperAction() {
		if (_master.getCurrentHp() < _master.getMaxHp() / 2) {
			L1SkillUse su = new L1SkillUse();
			su.handleCommands(null, 35, _master.getId(), _master.getX(),
					_master.getY(), null, 0, L1SkillUse.TYPE_NORMAL, this);
			su = null;
			return;
		}
		for (int element : Buff) {
			if (!_master.getSkillEffectTimerSet().hasSkillEffect(element)) {
				L1SkillUse su = new L1SkillUse();
				su.handleCommands(null, element, _master.getId(),
						_master.getX(), _master.getY(), null, 0,
						L1SkillUse.TYPE_NORMAL, this);
				su = null;
				break;
			}
		}
	}

	private void hasadbuff(L1PcInstance pc) {
		try {
			if (pc.getAinHasad() >= 80000000) {
				pc.setAinHasad(80000000);
				pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc), true);
				return;
			}

			if (pc.getAinHasad() >= 2000000) {
				pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc), true);
				return;
			}
			int temp = 0;

			if (pc.getLogOutTime() != null) {
				temp = (int) ((System.currentTimeMillis() - pc.getLogOutTime().getTime()) / 900000);
			}

			int sum = pc.getAinHasad() + (temp * 10000);
			if (sum >= 2000000)
				pc.setAinHasad(2000000);
			else
				pc.setAinHasad(sum);

			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc), true);
		} catch (Exception e) {
		}
	}

	public void startHelpTimer() {
		if (getDollType() != DOLLTYPE_HELPER)
			return;
		_future = GeneralThreadPool.getInstance().scheduleAtFixedRate(
				new HelpTimer(), 4000, 4000);
	}

	public void stopHelpTimer() {
		if (getDollType() != DOLLTYPE_HELPER)
			return;
		if (_future != null) {
			_future.cancel(false);
		}
	}
}

