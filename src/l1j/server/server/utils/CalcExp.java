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

package l1j.server.server.utils;

import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_닭고기;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_연어;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_칠면조;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐닭고기;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐연어;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐칠면조;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_탐한우;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_한우;
import static l1j.server.server.model.skill.L1SkillId.MISO_3_GRW;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_PetPack;
import l1j.server.server.serverpackets.S_PetWindow;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import server.GameServer;

// Referenced classes of package l1j.server.server.utils:
// CalcStat

public class CalcExp {
	private static final String TAG = "CalcExp";


	// private static final long serialVersionUID = 1L;

	private static Logger _log = Logger.getLogger(CalcExp.class.getName());

	public static final int MAX_EXP = ExpTable.getExpByLevel(100) - 1;

	private static L1NpcInstance npc = null;

	private static Random _random = new Random(System.nanoTime()); // please add.

	private CalcExp() {

	}

	public static void calcExp(L1PcInstance l1pcinstance, int targetid, ArrayList<?> acquisitorList, ArrayList<?> hateList, int exp) {
		try {
			int i = 0;
			double party_level = 0;
			double dist = 0;
			int member_exp = 0;
			int member_lawful = 0;
			L1Object l1object = L1World.getInstance().findObject(targetid);
			npc = (L1NpcInstance) l1object;

			// Get the total of your height
			L1Character acquisitor;
			int hate = 0;
			int acquire_exp = 0;
			int acquire_lawful = 0;
			int party_exp = 0;
			int party_lawful = 0;
			int totalHateExp = 0;
			int totalHateLawful = 0;
			int partyHateExp = 0;
			int partyHateLawful = 0;
			int ownHateExp = 0;

			if (acquisitorList.size() != hateList.size()) {
				return;
			}

			for (i = hateList.size() - 1; i >= 0; i--) {
				acquisitor = (L1Character) acquisitorList.get(i);
				hate = (Integer) hateList.get(i);

				if (acquisitor != null && !acquisitor.isDead()) {
					totalHateExp += hate;
					if (acquisitor instanceof L1PcInstance) {
						totalHateLawful += hate;
					}
				} else { // Exclude if null or dead
					acquisitorList.remove(i);
					hateList.remove(i);
				}
			}

			if (totalHateExp == 0) { // If there is no acquire
				return;
			}

			if (l1object != null && !(npc instanceof L1PetInstance) && !(npc instanceof L1SummonInstance)) {
				// int exp = npc.get_exp();
				if (!L1World.getInstance().isProcessingContributionTotal() && l1pcinstance.getHomeTownId() > 0) {
					int contribution = npc.getLevel() / 10;
					l1pcinstance.addContribution(contribution);
				}

				int lawful = npc.getLawful();

				if (l1pcinstance.isInParty()) { // during a party
					// Calculate the sum of the party's height
					// Distributed as it is except for party members
					partyHateExp = 0;
					partyHateLawful = 0;

					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = (Integer) hateList.get(i);

						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;

							if (pc == l1pcinstance) {
								partyHateExp += hate;
								partyHateLawful += hate;
							} else if (l1pcinstance.getParty().isMember(pc)) {
								partyHateExp += hate;
								partyHateLawful += hate;
							} else {
								if (totalHateExp > 0) {
									acquire_exp = (exp * hate / totalHateExp);
								}

								if
								(totalHateLawful > 0) {
									acquire_lawful = (lawful * hate / totalHateLawful);
								}

								AddExp(pc, acquire_exp, acquire_lawful);
							}
						} else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) pet.getMaster();

							if (master == l1pcinstance) {
								partyHateExp += hate;
							} else if (l1pcinstance.getParty().isMember(master)) {
								partyHateExp += hate;
							} else {
								if (totalHateExp > 0) {
									acquire_exp = (exp * hate / totalHateExp);
								}

								AddExpPet(pet, acquire_exp);
							}
						} else if (acquisitor instanceof L1SummonInstance) {
							L1SummonInstance summon = (L1SummonInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) summon.getMaster();
							if (master == l1pcinstance) {
								partyHateExp += hate;
							} else if (l1pcinstance.getParty().isMember(master)) {
								partyHateExp += hate;
							} else {
							}
						}
					}

					if (totalHateExp > 0) {
						party_exp = (exp * partyHateExp / totalHateExp);
					}

					if (totalHateLawful > 0) {
						party_lawful = (lawful * partyHateLawful / totalHateLawful);
					}

					// EXP, Raw Whole Distribution

					// free bonus
					double pri_bonus = 0;
					L1PcInstance leader = l1pcinstance.getParty().getLeader();
					if (leader.isCrown() && (l1pcinstance.getNearObjects().knownsObject(leader) || l1pcinstance.equals(leader))) {
						pri_bonus = 0.059;
					}

					// Calculation of PT EXP
					L1PcInstance[] ptMembers = l1pcinstance.getParty().getMembers();
					double pt_bonus = 0;
					for (L1PcInstance each : l1pcinstance.getParty().getMembers()) {
						if (l1pcinstance.getNearObjects().knownsObject(each) || l1pcinstance.equals(each)) {
							party_level += each.getLevel() * each.getLevel();
						}

						if (l1pcinstance.getNearObjects().knownsObject(each)) {
							pt_bonus += 0.04;
						}
					}

					party_exp = (int) (party_exp * (1 + pt_bonus + pri_bonus));

					// Calculate the total height of the child character and its pet Samon
					if (party_level > 0) {
						dist = ((l1pcinstance.getLevel() * l1pcinstance.getLevel()) / party_level);
					}

					member_exp = (int) (party_exp * dist);
					member_lawful = (int) (party_lawful * dist);

					ownHateExp = 0;
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = (Integer) hateList.get(i);
						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;
							if (pc == l1pcinstance) {
								ownHateExp += hate;
							}
						} else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) pet.getMaster();
							if (master == l1pcinstance) {
								ownHateExp += hate;
							}
						} else if (acquisitor instanceof L1SummonInstance) {
							L1SummonInstance summon = (L1SummonInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) summon.getMaster();
							if (master == l1pcinstance) {
								ownHateExp += hate;
							}
						}
					}

					// Distribution to child characters and their pets, summons
					if (ownHateExp != 0) { // was taking part in the attack
						for (i = hateList.size() - 1; i >= 0; i--) {
							acquisitor = (L1Character) acquisitorList.get(i);
							hate = (Integer) hateList.get(i);

							if (acquisitor instanceof L1PcInstance) {
								L1PcInstance pc = (L1PcInstance) acquisitor;

								if (pc == l1pcinstance) {

									if (ownHateExp > 0) {
										acquire_exp = (member_exp * hate / ownHateExp);
									}

									AddExp(pc, acquire_exp, member_lawful);
								}
							} else if (acquisitor instanceof L1PetInstance) {
								L1PetInstance pet = (L1PetInstance) acquisitor;
								L1PcInstance master = (L1PcInstance) pet.getMaster();

								if (master == l1pcinstance) {

									if (ownHateExp > 0) {
										acquire_exp = (member_exp * hate / ownHateExp);
									}
									AddExpPet(pet, acquire_exp);
								}

							} else if (acquisitor instanceof L1SummonInstance) {
							}
						}
					} else { // did not participate in the attack
						// Distributed only to child characters
						AddExp(l1pcinstance, member_exp, member_lawful);
					}

					// Calculate the total height of a party member and their pet summon
					for (L1PcInstance ptMember : ptMembers) {
						if (l1pcinstance.getNearObjects().knownsObject(ptMember)) {
							if (party_level > 0) {
								dist = ((ptMember.getLevel() * ptMember.getLevel()) / party_level);
							}
							member_exp = (int) (party_exp * dist);
							member_lawful = (int) (party_lawful * dist);

							ownHateExp = 0;
							for (i = hateList.size() - 1; i >= 0; i--) {
								acquisitor = (L1Character) acquisitorList.get(i);
								hate = (Integer) hateList.get(i);
								if (acquisitor instanceof L1PcInstance) {
									L1PcInstance pc = (L1PcInstance) acquisitor;
									if (pc == ptMember) {
										ownHateExp += hate;
									}
								} else if (acquisitor instanceof L1PetInstance) {
									L1PetInstance pet = (L1PetInstance) acquisitor;
									L1PcInstance master = (L1PcInstance) pet.getMaster();
									if (master == ptMember) {
										ownHateExp += hate;
									}
								} else if (acquisitor instanceof L1SummonInstance) {
									L1SummonInstance summon = (L1SummonInstance) acquisitor;
									L1PcInstance master = (L1PcInstance) summon.getMaster();
									if (master == ptMember) {
										ownHateExp += hate;
									}
								}
							}

							// Distribute to party members and their pets, summons that participated in the attack
							if (ownHateExp != 0) {
								for (i = hateList.size() - 1; i >= 0; i--) {
									acquisitor = (L1Character) acquisitorList.get(i);
									hate = (Integer) hateList.get(i);

									if (acquisitor instanceof L1PcInstance) {
										L1PcInstance pc = (L1PcInstance) acquisitor;

										if (pc == ptMember) {

											if (ownHateExp > 0) {
												acquire_exp = (member_exp * hate / ownHateExp);
											}

											AddExp(pc, acquire_exp, member_lawful);
										}
									} else if (acquisitor instanceof L1PetInstance) {
										L1PetInstance pet = (L1PetInstance) acquisitor;
										L1PcInstance master = (L1PcInstance) pet.getMaster();

										if (master == ptMember) {

											if (ownHateExp > 0) {
												acquire_exp = (member_exp * hate / ownHateExp);
											}

											AddExpPet(pet, acquire_exp);
										}

									} else if (acquisitor instanceof L1SummonInstance) {
									}
								}
							} else { // did not participate in the attack
								// Distribute only to party members
								AddExp(ptMember, member_exp, member_lawful);
							}
						}
					}
				} else { // not in party distribution of EXP, raw
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = (Integer) hateList.get(i);
						acquire_exp = (exp * hate / totalHateExp);

						if (acquisitor instanceof L1PcInstance) {
							if (totalHateLawful > 0) {
								acquire_lawful = (lawful * hate / totalHateLawful);
							}
						}

						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;
							AddExp(pc, acquire_exp, acquire_lawful);
						} else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							AddExpPet(pet, acquire_exp);
						} else if (acquisitor instanceof L1SummonInstance) {
						}
					}
				}
			}

		} catch (Exception e) {
		}
	}

	private static void AddExp(L1PcInstance pc, int exp, int lawful) {
		/** Waiting for server open */
		if (Config.STANDBY_SERVER || (pc.getLevel() > Config.MAXLEVEL) || pc.isDead())
			return;

		int pclevel = pc.getLevel();

		if (pclevel >= 45) {
			if (npc instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) npc;
				if ((mon.getNpcId() >= 100225 && mon.getNpcId() <= 100231) || (mon.getNpcId() >= 100236 && mon.getNpcId() <= 100241)) {
					return;
				}
			}
		}


		int add_lawful = (int) (lawful * Config.RATE_LAWFUL) * -1;
		pc.addLawful(add_lawful);

		if (npc instanceof L1MonsterInstance) {
			L1MonsterInstance mon = (L1MonsterInstance) npc;
			if (mon.getUbId() != 0) {
				int ubexp = (exp / 10);
				pc.setUbScore(pc.getUbScore() + ubexp);
			}
		}

		double exppenalty = ExpTable.getPenaltyRate(pclevel);
		double foodBonus = 1;
		double expposion = 1;
		double levelBonus = 1;
		double dragonexp = 1;
		// double ainhasadBonus = 1;
		double clanBonus = 1;
		double castleBonus = 1;
		double dollBonus = 1;
		double gereng = 1;
		double dragoneme = 1;
	    double comboBonus = 1;
		double etcBonus = 1;
		double 진귀한 = 1;
		double clan20Bonus = 1;
		double levelupBonus = 1;
		double newbSupport = 1;
		double scrollExperience = 1;
		if(pc.getScrollLevel() > 0) {
			scrollExperience += pc.getScrollLevel() * 0.01;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_7_N)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_7_S)) {
			foodBonus = 1.01;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_15_N)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_15_S)) {
			foodBonus = 1.02;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_23_N)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_23_S)) {
			foodBonus = 1.03;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_연어)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_칠면조)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_한우)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐연어)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐칠면조)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐한우)) {
			foodBonus = 1.02;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_닭고기)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_탐닭고기)) {
			foodBonus = 1.04;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.METIS_SOUP)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PSY_COOL_DRINK)) {
			foodBonus = 1.05;
		} else if (pc.getDessertId() == L1SkillId.천하장사버프) {
			foodBonus = 1.2;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(MISO_3_GRW)) {
			foodBonus += 1.1;
		}


		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_GROWTH_BUFF) && Config.DRAGON_GROWTH_POTION_EVENT){
			if(pc.getLevel() <= 79) {
				dragonexp += 1.3;
			} else if(pc.getLevel() >= 80 && pc.getLevel() <= 81) {
				dragonexp += 0.7;
			} else if(pc.getLevel() >= 82 && pc.getLevel() <= 83) {
				dragonexp += 0.5;
			} else if(pc.getLevel() >= 84 && pc.getLevel() <= 85) {
				dragonexp += 0.3;
			} else if(pc.getLevel() == 86) {
				dragonexp += 0.2;
			} else if(pc.getLevel() == 87) {
				dragonexp += 0.2;
			} else if(pc.getLevel() == 88) {
				dragonexp += 0.2;
			} else if(pc.getLevel() >= 89) {
				dragonexp += 0.1;
			}
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_COMA_5)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION_cash)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION)) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION_cash)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION)) {
				if (pc.PCROOM_BUFF) {
					expposion = 1.3;
				} else {
					expposion = 1.2;
				}
			} else {
				expposion = 1.2;
			}
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION2)) {
			gereng = 1.4;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION3)) {
			gereng = 1.3;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) && pc.getAinHasad() > 10000) {
			dragoneme = 1.8;
			if (pc.getAinHasad() < 0){
				pc.calAinHasad(0);
			}
			pc.calAinHasad(-exp);
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_PUPLE) && pc.getAinHasad() > 10000) {
			if (pc.getLevel() >= 49 && pc.getLevel() <= 54) {
				dragoneme = 1.53;
			} else if (pc.getLevel() >= 55 && pc.getLevel() <= 59) {
				dragoneme = 1.43;
			} else if (pc.getLevel() >= 60 && pc.getLevel() <= 64) {
				dragoneme = 1.33;
			} else if (pc.getLevel() >= 65 && pc.getLevel() <= 70) {
				dragoneme = 1.23;
			} else if (pc.getLevel() >= 71 && pc.getLevel() <= 80) {
				dragoneme = 1.13;
			} else if (pc.getLevel() >= 81 && pc.getLevel() <= 85) {
				dragoneme = 1.03;
			} else if (pc.getLevel() >= 86 && pc.getLevel() <= 90) {
				dragoneme = 0.5;
			}

			pc.calAinHasad(-exp);
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
			if (pc.getAinHasad() <= 10000) {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_PUPLE);
			}
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_TOPAZ)&& pc.getAinHasad() > 10000) {
			dragoneme = 1.5;
			pc.calAinHasad(-exp);
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
			if (pc.getAinHasad() <= 10000) {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_TOPAZ);
			}
		}
		if (pc.getInventory().checkEquipped(427300)) {
			etcBonus += 0.15;
		}

		if (pc.getInventory().checkEquipped(427301)) {
			etcBonus += 0.15;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_FANTASY_FOOD)) {
			진귀한 = 1.25;
		}

		if (pc.getInventory().checkEquipped(421217) || pc.getInventory().checkEquipped(421218) || pc.getInventory().checkEquipped(421219)
				|| pc.getInventory().checkEquipped(431217) || pc.getInventory().checkEquipped(431218) || pc.getInventory().checkEquipped(431219)) {
			etcBonus += 0.10;
		}

		if (pc.getInventory().checkEquippedEnchant(200851, 8)) {
			etcBonus += 0.02;
		}
		if (pc.getInventory().checkEquippedEnchant(200851, 9)) {
			etcBonus += 0.04;
		}
		if (pc.getInventory().checkEquippedEnchant(200851, 10)) {
			etcBonus += 0.06;
		}
		/**Chukjiryong Tshirt*/
		if (pc.getInventory().checkEquippedEnchant(30030, 7)) {
			etcBonus += 0.02;
		}
		if (pc.getInventory().checkEquippedEnchant(30030, 8)) {
			etcBonus += 0.04;
		}
		if (pc.getInventory().checkEquippedEnchant(30030, 9)) {
			etcBonus += 0.06;
		}
		if (pc.getInventory().checkEquippedEnchant(30030, 10)) {
			etcBonus += 0.08;
		}
		/**Chukjiryong Tshirt*/

		/**Hunter's Pendant*/
		if (pc.getInventory().checkEquippedEnchant(30035, 4)) {
			etcBonus += 0.04;
		}

		if (pc.getInventory().checkEquippedEnchant(30035, 5)) {
			etcBonus += 0.08;
		}

		if (pc.getInventory().checkEquippedEnchant(30035, 6)) {
			etcBonus += 0.12;
		}

		if (pc.getInventory().checkEquippedEnchant(30035, 7)) {
			etcBonus += 0.16;
		}

		if (pc.getInventory().checkEquippedEnchant(30035, 8)) {
			etcBonus += 0.20;
		}
		/**Hunter's Pendant*/


		/** Zoe's Relic **/
		if (pc.getInventory().checkEquippedEnchant(421220, 0)) {
			etcBonus += 0.04;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 1)) {
			etcBonus += 0.08;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 2)) {
			etcBonus += 0.12;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 3)) {
			etcBonus += 0.16;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 4)) {
			etcBonus += 0.20;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 5)) {
			etcBonus += 0.24;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 6)) {
			etcBonus += 0.28;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 7)) {
			etcBonus += 0.32;
		}

		if (pc.getInventory().checkEquippedEnchant(421220, 8)) {
			etcBonus += 0.36;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 0)) {
			etcBonus += 0.04;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 1)) {
			etcBonus += 0.08;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 2)) {
			etcBonus += 0.12;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 3)) {
			etcBonus += 0.16;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 4)) {
			etcBonus += 0.20;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 5)) {
			etcBonus += 0.24;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 6)) {
			etcBonus += 0.28;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 7)) {
			etcBonus += 0.32;
		}

		if (pc.getInventory().checkEquippedEnchant(421221, 8)) {
			etcBonus += 0.36;
		}
		/** Zoe's Relic **/


		// Crest of Growth
	    if (pc.getInventory().getEnchantEquipped(490020, 0)) {
	        etcBonus += 0.01D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 1)) {
	        etcBonus += 0.02D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 2)) {
	        etcBonus += 0.03D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 3)) {
	        etcBonus += 0.04D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 4)) {
	        etcBonus += 0.05D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 5)) {
	        etcBonus += 0.06D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 6)) {
	        etcBonus += 0.07D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 7)) {
	        etcBonus += 0.09D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 8)) {
	        etcBonus += 0.11D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 9)) {
	        etcBonus += 0.13D;
	      }
	      if (pc.getInventory().getEnchantEquipped(490020, 10)) {
	        etcBonus += 0.15D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 1)) {
	        etcBonus += 0.01D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 2)) {
	        etcBonus += 0.02D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 3)) {
	        etcBonus += 0.03D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 4)) {
	        etcBonus += 0.04D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 5)) {
	        etcBonus += 0.05D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 6)) {
	        etcBonus += 0.06D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 7)) {
	        etcBonus += 0.07D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30022, 8)) {
	        etcBonus += 0.08D;
	      }

	      // Growth Restoration Sentence
	      if (pc.getInventory().getEnchantEquipped(30013, 0)) {
	        etcBonus += 0.01D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 1)) {
	        etcBonus += 0.02D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 2)) {
	        etcBonus += 0.03D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 3)) {
	        etcBonus += 0.04D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 4)) {
	        etcBonus += 0.05D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 5)) {
	        etcBonus += 0.06D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 6)) {
	        etcBonus += 0.07D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 7)) {
	        etcBonus += 0.09D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 8)) {
	        etcBonus += 0.11D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 9)) {
	        etcBonus += 0.13D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30013, 10)) {
	        etcBonus += 0.15D;
	      }

	      // Sentence of full growth
		  if (pc.getInventory().getEnchantEquipped(30007, 0)) {
	        etcBonus += 0.01D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 1)) {
	        etcBonus += 0.02D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 2)) {
	        etcBonus += 0.03D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 3)) {
	        etcBonus += 0.04D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 4)) {
	        etcBonus += 0.05D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 5)) {
	        etcBonus += 0.06D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 6)) {
	        etcBonus += 0.07D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 7)) {
	        etcBonus += 0.09D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 8)) {
	        etcBonus += 0.11D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 9)) {
	        etcBonus += 0.13D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30007, 10)) {
	        etcBonus += 0.15D;
	      }

		  // Agile growth crest
		  if (pc.getInventory().getEnchantEquipped(30008, 0)) {
	        etcBonus += 0.01D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 1)) {
	        etcBonus += 0.02D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 2)) {
	        etcBonus += 0.03D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 3)) {
	        etcBonus += 0.04D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 4)) {
	        etcBonus += 0.05D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 5)) {
	        etcBonus += 0.06D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 6)) {
	        etcBonus += 0.07D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 7)) {
	        etcBonus += 0.09D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 8)) {
	        etcBonus += 0.11D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 9)) {
	        etcBonus += 0.13D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30008, 10)) {
	        etcBonus += 0.15D;
	      }

	      // Sentence of knowledge growth
		  if (pc.getInventory().getEnchantEquipped(30009, 0)) {
			  etcBonus += 0.01D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 1)) {
	    	  etcBonus += 0.02D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 2)) {
	    	  etcBonus += 0.03D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 3)) {
	    	  etcBonus += 0.04D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 4)) {
	    	  etcBonus += 0.05D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 5)) {
	    	  etcBonus += 0.06D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 6)) {
	       	etcBonus += 0.07D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 7)) {
	    	  etcBonus += 0.09D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 8)) {
	    	  etcBonus += 0.11D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 9)) {
	    	  etcBonus += 0.13D;
	      }
	      if (pc.getInventory().getEnchantEquipped(30009, 10)) {
	    	  etcBonus += 0.15D;
	      }

		if (GameServer.NEWB_SUPPORT_EXP_BONUS && pc.getLevel() <= 69) {
			newbSupport = 1.50;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.LEVEL_UP_BONUS)) {
			levelupBonus = 2.0;
		}

		if (pc.getAinHasad() > 0) {
			pc.calAinHasad(-exp);
			if (pc.getAinHasad() > 0) {
				gereng += 1.5;
			} else {
				gereng += 1;
			}

			if (pc.PCROOM_BUFF) {
				gereng += 0.10;
				pc.calAinHasad(-exp);
			}
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
		}

		for (L1DollInstance doll : pc.getDollList()) {
			int dollType = doll.getDollType();
			dollBonus = doll.getAddExpByDoll();
			if (dollType == L1DollInstance.DOLLTYPE_SNOWMAN_A
					|| dollType == L1DollInstance.DOLLTYPE_GIANT
					|| dollType == L1DollInstance.DOLLTYPE_BLESSEDGIANT
					|| dollType == L1DollInstance.DOLLTYPE_MUMMYLORD
					|| dollType == L1DollInstance.DOLLTYPE_BLESSEDMUMMYLORD) {
				dollBonus = 1.1;
			} else if (dollType == L1DollInstance.DOLLTYPE_DEATHKNIGHT
					|| dollType == L1DollInstance.DOLLTYPE_BLESSEDDEATHKNIGHT) {
				dollBonus = 1.2;
			} else if (dollType == L1DollInstance.DOLLTYPE_ANTHARAS) {
				dollBonus = 1.25;
			} else if (dollType == L1DollInstance.DOLLTYPE_헌신1등급) {
				dollBonus = 1.25;
			}

		} // Add

		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		if (clan != null) {
			if (clan.getCastleId() != 0) {
			    castleBonus = 1.05;
			}
			if (pc.pledgeBuff) {
				double l = 0.05;
				clan20Bonus = 1.01;
				if (pc.getLevel() > 45) {
					clan20Bonus += l * (pc.getLevel() - 45);
				}
			}
		}
		int newchar = 1;

		int settingEXP = (int) Config.RATE_XP;
    	/* Prevention of flooding */
		if (pc.getLevel() >= 1) {
		  if ((settingEXP + pc.getExp()) > ExpTable.getExpByLevel((pc.getLevel()+1))) {
			  settingEXP =  ExpTable.getExpByLevel((pc.getLevel()+1)) - pc.getExp();
			}
		}

		if (Config.RUMTIS_EVENT) { // rumtis event?
			if (Config.룸티스드랍진행중) { // rumtis drop in progress
				if (dragoneme != 1) {
					dragoneme *= 2;
				}
			}
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COMBO_BUFF)) {
			if (pc.getComboCount() > 0 && pc.getComboCount() <= 30) {
				comboBonus = 1 + (pc.getComboCount() * 0.1);
			} else if (pc.getComboCount() > 30) {
				comboBonus = 3;
			}
		}

		int add_exp = (int) (exp * settingEXP * foodBonus * expposion * levelBonus * exppenalty
				* newchar * clanBonus * castleBonus * dollBonus * gereng * dragoneme * comboBonus
				* etcBonus * 진귀한 * clan20Bonus * levelupBonus * newbSupport * scrollExperience
				* dragonexp);

		 if (pclevel >= 1) { //Over level 49 to prevent flooding
			 if ((add_exp + pc.getExp()) > ExpTable.getExpByLevel((pc.getLevel() + 1))) {
				 add_exp = (ExpTable.getExpByLevel((pc.getLevel() + 1)) - pc.getExp());
				 }
		 }

		if (add_exp < 0) {
			return;
		}

		if (ExpTable.getExpByLevel(Config.MAXLEVEL + 1) <= pc.getExp()+ add_exp) {
			pc.setExp(ExpTable.getExpByLevel(Config.MAXLEVEL + 1) - 1);
		} else {
			pc.addExp(add_exp);
			CheckQuize(pc);
		}
	}

	private static void CheckQuize(L1PcInstance pc) {
		if(pc.noPlayerCK)
			return;
		Account account = Account.load(pc.getAccountName());
		if (pc.getLevel() >= 99) // to float from 50
			if (account.getquize() == null || account.getquize() == "") {
		//		pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,""+Config.SERVER_NAME+"에서는 퀴즈설정을 하셔야 합니다. [.퀴즈설정 OOOO]"));
		//	    pc.sendPackets(new S_ChatPacket(pc, "\\aG퀴즈를 설정하지않아 대미지리덕션이 -1 감소합니다."));
			    pc.sendPackets(new S_SkillSound(pc.getId(), 6251));	//
				if (!pc.getExcludingList().contains(pc.getName())) {
				}
			}
	}

	public static void AddExp(L1PcInstance pc, int exp) {
		/** Waiting for server open */
		if (Config.STANDBY_SERVER || (pc.getLevel() > ExpTable.getExpByLevel(Config.MAXLEVEL)) || pc.isDead())
			return;

		int add_exp = (exp);
		// Excluding experience points that increase by multiplier

		if (add_exp < 0) {
			return;
		}

		if (ExpTable.getExpByLevel(Config.MAXLEVEL) <= pc.getExp() + add_exp) {
			pc.setExp(ExpTable.getExpByLevel(ExpTable.getExpByLevel(Config.MAXLEVEL)));
		} else {
			pc.addExp(add_exp);
		}
		pc.onChangeExp(); // forced experience renewal.
	}

	/** Pet related experience check */
	public static void AddExpPet(L1PetInstance pet, int exp) {
		L1PcInstance pc = (L1PcInstance) pet.getMaster();
		int levelBefore = pet.getLevel();

		/** Only 20% of the pet's basic experience is acquired
		 * However, if there is a buff, an additional percentage is added. */
		int BonusExp = exp * 200;
		if(pet.SkillCheck(L1SkillId.GrowthFoliage)) BonusExp *= 1.3;
			int totalExp = BonusExp + pet.getExp();
		if (totalExp >= ExpTable.getExpByLevel(100)) {
			totalExp = ExpTable.getExpByLevel(100) - 1;
		}
		pet.setExp(totalExp);
		pet.setLevel(ExpTable.getLevelByExp(totalExp));
		pc.sendPackets(new S_PetWindow(S_PetWindow.PatExp, pet), true);

		/** Fighting spirit points are also given when experience points are given
		 * Let's set it so that it can be computed */
		int FightingBonuseExp = (int)Math.round((exp) / 100.0) * 100;
		if (FightingBonuseExp < 5000) FightingBonuseExp = 5000;
		if (FightingBonuseExp >= 20000) FightingBonuseExp = 20000;
		pet.Fighting(FightingBonuseExp);

		int gap = pet.getLevel() - levelBefore;
		for (int i = 1; i <= gap; i++) {
			IntRange hpUpRange = pet.getPetType().getHpUpRange();
			pet.addMaxHp(hpUpRange.randomValue());
			pet.setCurrentHp(pet.getMaxHp());
		}
		try {
			pc.sendPackets(new S_PetPack(pet, pc), true);
		} catch (Exception e) {}

		/** If you are leveling up, write*/
		if (gap != 0) {
			/** Renew pet-related ac settings */
			pet.getPetAc();
			pet.getPetMr();
			/** Pet related information Bonus stat relevance check */
			int BonusPoint = pet.getHunt() + pet.getSurvival() + pet.getSacred();
			int BonusPointTemp = 0;
			if (pet.getLevel() > 50) {
				BonusPointTemp = (5 + (pet.getLevel() - 50)) - BonusPoint;
			} else BonusPointTemp = (pet.getLevel() / 10) - BonusPoint;

			if (BonusPointTemp > 0)
				pet.setBonusPoint(BonusPointTemp);

			L1ItemInstance item = pc.getInventory().getItem(pet.getItemObjId());
			PetTable.UpDatePet(pet);

			pc.sendPackets(new S_ItemName(item), true);
			pc.sendPackets(new S_PetWindow(S_PetWindow.PatLevel, pet), true);
			pc.sendPackets(new S_PetWindow(S_PetWindow.PatStatUpDate, pet), true);
			try {
				pc.sendPackets(new S_ServerMessage(320, pet.getName()), true);
			} catch (Exception e) {}
		}
	}
}

