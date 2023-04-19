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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.GameSystem.DogFight.DogFight;
import l1j.server.IndunSystem.MiniGame.BattleZone;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.datatables.TownTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1NpcTalkData;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1TownLocation;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseArrowSkill;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.oneDayBuyCheck;

public class L1MerchantInstance extends L1NpcInstance {
    private static final long serialVersionUID = 1L;
    // private GameServerSetting _GameServerSetting;
    private int clanid = 0;
    private String clanname;

    public int getClanid() {
        return clanid;
    }

    public void setClanid(int i) {
        clanid = i;
    }

    public String getClanname() { // 크란명
        return clanname;
    }

    public void setClanname(String s) {
        clanname = s;
    }

    /**
     * @param template
     */
    public L1MerchantInstance(L1Npc template) {
        super(template);
        if (getNpcId() == 70802)// 은말 대장장이 아논
            GeneralThreadPool.getInstance().execute(new AnonAction());
        else if (getNpcId() >= 100547 && getNpcId() <= 100550) // 우드벡 병사 모션
            GeneralThreadPool.getInstance().execute(new WoodSoldierMotion());
    }

    @Override
    public void onAction(L1PcInstance pc) {
        L1Attack attack = new L1Attack(pc, this);
        if (attack.calcHit()) {
            attack.calcDamage();
        }
        attack.action();
        attack = null;
    }

    @Override
    public void onAction(L1PcInstance pc, int adddmg) {
        L1Attack attack = new L1Attack(pc, this);
        if (attack.calcHit()) {
            attack.calcDamage(adddmg);
        }
        attack.action();
        attack = null;
    }

    @Override
    public void onNpcAI() {
        if (isAiRunning()) {
            return;
        }
        setActived(false);
        startAI();
    }

    @Override
    public void onTalkAction(L1PcInstance player) {
        int objid = getId();
        L1NpcTalkData talking = NPCTalkDataTable.getInstance().getTemplate(getNpcTemplate().get_npcId());
        int npcid = getNpcTemplate().get_npcId();
        L1Quest quest = player.getQuest();
        String htmlid = null;
        String[] htmldata = null;

		// 수련장 관리인, 라던 클릭이동
        if (talking == null && npcid != 100669 && npcid != 100219 && npcid != 7000096 && !(npcid >= 100403 && npcid <= 100406)) {
            // 엔피씨
            // player.sendPackets(new S_SystemMessage("현제 준비 중입니다."));
            return;
        }

        int pcX = player.getX();
        int pcY = player.getY();
        int npcX = getX();
        int npcY = getY();

        if (getNpcTemplate().getChangeHead()) {
            int heading = 0;
            if (pcX == npcX && pcY < npcY) {
                heading = 0;
            } else if (pcX > npcX && pcY < npcY) {
                heading = 1;
            } else if (pcX > npcX && pcY == npcY) {
                heading = 2;
            } else if (pcX > npcX && pcY > npcY) {
                heading = 3;
            } else if (pcX == npcX && pcY > npcY) {
                heading = 4;
            } else if (pcX < npcX && pcY > npcY) {
                heading = 5;
            } else if (pcX < npcX && pcY == npcY) {
                heading = 6;
            } else if (pcX < npcX && pcY < npcY) {
                heading = 7;
            }

            getMoveState().setHeading(heading);
            S_ChangeHeading ch = new S_ChangeHeading(this);
            Broadcaster.broadcastPacket(this, ch, true);

            synchronized (this) {
                if (_monitor != null) {
                    _monitor.cancel();
                }
                setRest(true);
                _monitor = new RestMonitor();
                _restTimer.schedule(_monitor, REST_MILLISEC);
            }
        }

        if (npcid == 7000096) {
            if (player.getLevel() >= Config.배틀존입장레벨) {
                DuelZone(player);
            } else {
                player.sendPackets(new S_SystemMessage("\\aG[!] : 레벨 " + Config.배틀존입장레벨 + " 이상만 입장할수 있습니다."));
            }
            return;
        }


        if (talking != null) {
            switch (npcid) {
                case 100224: // 숨계 정령사
                case 70760://엘뤼온
                case 100802://샤베스
                    if (!player.isElf()) {
                        htmlid = "ellyonne15";
                    } else {
                        if (player.getElfAttr() == 0 && player.글로리어스) {
                            htmlid = "ellyonne17";
                        } else if (player.getElfAttr() != 0) {
                            htmlid = "ellyonne11";
                        }
                    }
                    break;
                case 100831:// 햄
                    if (player.getLevel() < 60) {
                        htmlid = "hamo3";
                    }
                    break;
                case 100879:
                    if (player.getQuest().get_step(L1Quest.QUEST_55_Roon) == 1
                            || player.getQuest().get_step(L1Quest.QUEST_70_Roon) == 1
                            || player.getInventory().checkItem(60381)
                            || player.getLevel() < 55) {
                        htmlid = "oldbook2";
                    }
                    break;
                case 70798:
                    if (player.getLevel() > 51) {
                        htmlid = "newbiegate2";
                    }
                    break;
                case 100832:// 엘드나스
                    if (player.getLevel() < 60) {
                        htmlid = "eldnas3";
                    }
                    break;
                case 100724:// 에킨스
                    if (player.getLevel() >= 56) {
                        if (player.getInventory().checkItem(60499, 1) || player.getInventory().checkItem(60500, 1)) {
                            htmlid = "ekins2";
                        } else {
                            htmlid = "ekins1";
                        }
                    } else {
                        htmlid = "ekins3";
                    }
                    break;
                case 100691:// 민티스
                    if (player.get솔로타운day() == null) {
                        player.set솔로타운time(1);
                        player.set솔로타운day(new Timestamp(System.currentTimeMillis()));
                        if (!player.getInventory().checkItem(60444)
                                && !player.getInventory().checkItem(60445)
                                && !player.getInventory().checkItem(60446))
                            player.getInventory().storeItem(60444, 1);
                    }
                    break;
                case 100663:
                case 100666:
                    if (!player.isCrown() || player.getClanid() == 0) {
                        player.sendPackets(new S_ServerMessage(2498), true); // 혈맹 군주만 사용 가능
                        return;
                    } else if (공성병기딜레이) {
                        player.sendPackets(new S_ServerMessage(3680), true); // 재장전시간 필요
                        return;
                    } else if (!WarTimeController.getInstance().isNowWar(L1CastleLocation.KENT_CASTLE_ID)) {
                        player.sendPackets(new S_ServerMessage(3683), true); // 공성시간에만 사용 가능
                        return;
                    } else if (player.getClan().getCastleId() != L1CastleLocation.KENT_CASTLE_ID && npcid == 100666) {
                        player.sendPackets(new S_ServerMessage(3682), true); // 성혈 군주만 사용가능
                        return;
                    } else {
                        if (npcid == 100663) {
                            int castleid = L1CastleLocation.getCastleIdByArea(player);
                            if (castleid == 0) {
								return;
							}

                            boolean in_war = false;
                            List<L1War> wars = L1World.getInstance().getWarList(); // 전전쟁 리스트를 취득

                            for (L1War war : wars) {
								// 이마이성의 전쟁
                                if (castleid == war.GetCastleId()) {
                                    in_war = war.CheckClanInWar(player.getClanname());
                                    break;
                                }
                            }

                            if (player.getClan().getCastleId() != 0 || !in_war) {
                                player.sendPackets(new S_ServerMessage(3681), true); // 선포한 군주만 사용가능
                                return;
                            }
                        }
                    }
                    break;
                case 101042:
                    if (player.PCROOM_BUFF) {
                        htmlid = "pc_tell1";
                    }
                    break;
                case 100664:
                case 100665:
                    if (!player.isCrown() || player.getClanid() == 0) {
                        player.sendPackets(new S_ServerMessage(2498), true); // 혈맹 군주만 사용 가능
                        return;
                    } else if (공성병기딜레이) {
                        player.sendPackets(new S_ServerMessage(3680), true); // 재장전시간 필요
                        return;
                    } else if (!WarTimeController.getInstance().isNowWar(L1CastleLocation.GIRAN_CASTLE_ID)) {
                        player.sendPackets(new S_ServerMessage(3683), true); // 공성시간에만 사용 가능
                        return;
                    } else if (player.getClan().getCastleId() != L1CastleLocation.GIRAN_CASTLE_ID && npcid == 100665) {
                        player.sendPackets(new S_ServerMessage(3682), true); // 성혈 군주만 사용가능
                        return;
                    } else {
                        if (npcid == 100664) {
                            int castleid = L1CastleLocation.getCastleIdByArea(player);
                            if (castleid == 0)
                                return;
                            boolean in_war = false;
                            List<L1War> wars = L1World.getInstance().getWarList(); // 전전쟁 리스트를 취득
                            for (L1War war : wars) {
								// 이마이성의 전쟁
                                if (castleid == war.GetCastleId()) {
                                    in_war = war.CheckClanInWar(player
                                            .getClanname());
                                    break;
                                }
                            }
                            if (player.getClan().getCastleId() != 0 || !in_war) {
                                player.sendPackets(new S_ServerMessage(3681), true); // 선포한 군주만 사용가능
                                return;
                            }
                        }
                    }
                    break;
                case 100667:
                case 100668:
                    if (!player.isCrown() || player.getClanid() == 0) {
                        player.sendPackets(new S_ServerMessage(2498), true); // 혈맹 군주만 사용 가능
                        return;
                    } else if (공성병기딜레이) {
                        player.sendPackets(new S_ServerMessage(3680), true); // 재장전시간 필요
                        return;
                    } else if (!WarTimeController.getInstance().isNowWar(
                            L1CastleLocation.OT_CASTLE_ID)) {
                        player.sendPackets(new S_ServerMessage(3683), true); // 공성시간에만 사용 가능
                        return;
                    } else if (player.getClan().getCastleId() != L1CastleLocation.OT_CASTLE_ID
                            && npcid == 100668) {
                        player.sendPackets(new S_ServerMessage(3682), true); // 성혈 군주만 사용가능
                        return;
                    } else {
                        if (npcid == 100667) {
                            int castleid = L1CastleLocation
                                    .getCastleIdByArea(player);
                            if (castleid == 0)
                                return;
                            boolean in_war = false;
                            List<L1War> wars = L1World.getInstance().getWarList(); // 전전쟁 리스트를 취득
                            for (L1War war : wars) {
                                if (castleid == war.GetCastleId()) { // 이마이성의 전쟁
                                    in_war = war.CheckClanInWar(player
                                            .getClanname());
                                    break;
                                }
                            }
                            if (player.getClan().getCastleId() != 0 || !in_war) {
                                player.sendPackets(new S_ServerMessage(3681), true); // 선포한 군주만 사용가능
                                return;
                            }
                        }
                    }
                    break;
                case 100645:
                    if (!player.getNetConnection().getAccount().RedKnightEventItem) {
                        player.getNetConnection().getAccount().updateRedKnightEvent();
                        L1ItemInstance item = player.getInventory().storeItem(60391, 7);
                        player.sendPackets(new S_ServerMessage(143, getName(), item.getName())); // \f1%0이%1를 주었습니다.
                    }
                    Date day = new Date(System.currentTimeMillis());
                    if (day.getHours() % 2 != 0 || day.getMinutes() >= 5) {
                        htmlid = "evsiege102";
                    } else if (player.getLevel() < 45) {
                        htmlid = "evsiege106";
                    } else if (!player.getInventory().checkItem(60392)) {
                        htmlid = "evsiege105";
                    }
                    break;
                case 100646:
                    if (player.getLevel() < 45) {
                        htmlid = "evsiege202";
                    }
                    break;
                case 100583:
                    if (!player.isGhost())
                        htmlid = "exitkir1";
                    break;
                case 100644:
                    if (player.getLevel() < 55)
                        htmlid = "seirune1";
                    break;
                case 100376:
                case 100377:
                case 100378:
                case 100379:
                case 100380:
                case 100381:
                case 100382:
                case 100383:
                case 100384:
                case 100385:
                case 4203000: // 젠도르
                case 100434:
                    player._젠도르빈줌갯수 = 0;
                    break;
                case 100371: // 감시자의 눈
                    if (player.isGhost())
                        htmlid = "exitghostel";
                    break;
                case 100324: // 사냥터 관리인^노베르
                    if (player.getLevel() < 45) {
                        htmlid = "teldkev06";
                    } else if (!player.getInventory().checkItem(60177)) {
                        htmlid = "teldkev03";
                    }
                    break;
                case 100326: // 포고령 배포자^시이니
                    if (player.getLevel() < 45) {
                        htmlid = "sini3";
                    } else if (player.getInventory().checkItem(60177)) {
                        htmlid = "sini2";
                    }
                    break;
                case 100325: // 국왕의 보좌관^노무르
                    if (player.getLevel() < 45) {
                        htmlid = "rewarddkev6";
                    }
                    break;
                case 100214: // 기초 훈련 교관
                    int step = quest.get_step(L1Quest.QUEST_SILVER_FIRST);
                    if (step == 0) {
                        htmlid = "hpass1";
                    } else if ((step == 1 || step == 2) && player.getLevel() >= 20) {
                        htmlid = "hpass2";
                    } else if ((step == 3 || step == 4) && player.getLevel() >= 25) {
                        htmlid = "hpass3";
                    } else if ((step == 5 || step == 6) && player.getLevel() >= 30) {
                        htmlid = "hpass4";
                    } else if ((step == 7 || step == 8) && player.getLevel() >= 35) {
                        htmlid = "hpass5";
                    } else if ((step == 9 || step == 10) && player.getLevel() >= 45) {
                        htmlid = "hpass6";
                    } else if (step == 11 || step == 255) {
                        htmlid = "hpass7";
                    } else {
                        htmlid = "";
                    }
                    // if(step == 0 || step == 1 || step == 3 || step == 5 || step
                    // == 7 || step == 9)
                    htmldata = new String[]{"상아탑 상위 아이템 교환"};
                    break;
                case 100217: // 최종 훈련 심사관
                    int step2 = player.getQuest().get_step(
                            L1Quest.QUEST_SILVER_FIRST);
                    if (step2 == 255) {
                        htmlid = "highpass43";
                    } else if (step2 != 11) {
                        htmlid = "highpass41";
                    } else if (player.getLevel() < 52) {
                        htmlid = "highpass42";
                    } else {
                        if (player.getInventory().consumeItem(60148, 1)) {
                            // 드래곤의 자수정
                            player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(5000067, 1).getName() + " (1)"), true);
                            // player.sendPackets(new
                            // S_SystemMessage(player.getInventory().storeItem(5000067,
                            // 3).getName()+" (3) 을 획득하였습니다."), true);
                            player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(60381, 1).getName()), true);
                            if (player.isCrown()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(51, 1).getName()), true); // 황금 지휘봉
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(20051, 1).getName()), true); // 군주의 위엄
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450001, 1).getName()), true); // 엘모어 한손검
                            } else if (player.isKnight()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(56, 1).getName()), true); // 데스 블레이드
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(20318, 1).getName()), true); // 용기의 벨트
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450001, 1).getName()), true); // 엘모어 한손검
                            } else if (player.isElf()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(50, 1).getName()), true); // 화염의 검
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(184, 1).getName()), true); // 화염의 활
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450007, 1).getName()), true); // 엘모어 보우건
                            } else if (player.isWizard()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(20225, 1).getName()), true); // 마나 수정구
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(20055, 1).getName()), true); // 마나 망토
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450003, 1).getName()), true); // 엘모어 지팡이
                            } else if (player.isDragonknight()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(420001, 1).getName()), true); // 용비늘 가더
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(410000, 1).getName()), true); // 소멸자의 체인소드
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450004, 1).getName()), true); // 엘모어 체인소드
                            } else if (player.isIllusionist()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(410003, 1).getName()), true); // 사파이어 키링크
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(420006, 1).getName()), true); // 환술사 마법서
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450006, 1).getName()), true); // 엘모어 키링크
                            } else if (player.isDarkelf()) {
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(20195, 1).getName()), true); // 그림자 부츠
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(13, 1).getName()), true); // 핑거 오브 데스
                                player.sendPackets(new S_ServerMessage(143, getNpcTemplate().get_name(), player.getInventory().storeItem(450005, 1).getName()), true); // 엘모어 크로우
                            }
                            player.getQuest().set_end(L1Quest.QUEST_SILVER_FIRST);
                        }
                    }
                    break;
                case 100215: // 토벌대원
                    if (player.getLevel() < 15)
                        htmlid = "highdaily0";
                    else if (player.getLevel() > 44
                            || player.getQuest().isEnd(L1Quest.QUEST_SILVER_HUNT))
                        htmlid = "highdaily3";
                    else if (player.getQuest().get_step(L1Quest.QUEST_SILVER_HUNT) == 0
                            && !player.getInventory().checkItem(60149))
                        htmlid = "highdaily1";
                    else if (player.getQuest().get_step(L1Quest.QUEST_SILVER_HUNT) != 0
                            && player.getQuest()
                            .get_step(L1Quest.QUEST_SILVER_HUNT) % 2 != 0)
                        htmlid = "highdaily6";
                    else if (!player.getInventory().checkItem(60150))
                        htmlid = "highdaily10";
                    else if (player.getInventory().checkItem(60150))
                        htmlid = "highdaily2";
                    break;
                case 100216:
					// 드래곤뼈 수집꾼
                    if (player.getLevel() < 45) {
						htmlid = "highdailyb0";
					} else if (player.getLevel() >= 60 || player.getQuest().isEnd(L1Quest.QUEST_SILVER_DRAGON_TEARS)) {
						htmlid = "highdailyb3";
					} else if (player.getQuest().get_step(L1Quest.QUEST_SILVER_DRAGON_TEARS) == 0 && !player.getInventory().checkItem(60159)) {
						htmlid = "highdailyb1";
					} else if (player.getQuest().get_step(L1Quest.QUEST_SILVER_DRAGON_TEARS) != 0 && player.getQuest().get_step(L1Quest.QUEST_SILVER_DRAGON_TEARS) % 2 != 0) {
						htmlid = "highdailyb6";
					} else if (!player.getInventory().checkItem(60160)) {
						htmlid = "highdailyb10";
					} else if (player.getInventory().checkItem(60160)) {
						htmlid = "highdailyb2";
					}
                    break;
                case 70017: // 오림
                    if (player.getInventory().checkItem(60123)) {
                        htmlid = "orim6";
                    }
                    break;
                case 50112: // 세리안
                    if (player.isCrown() || player.isWizard() || player.isDragonknight()) {
                        int talk_step = quest.get_step(L1Quest.QUEST_FIRSTQUEST);
                        if (talk_step == 1) {
                            if (player.getLevel() >= 5) {
                                htmlid = "orenb4";
                            } else {
                                htmlid = "orenb14";
                            }
                        } else if (talk_step == 255) {
                            htmlid = "orenb11";
                        }
                    } else {
                        htmlid = "orenb12";
                    }
                    break;
                case 50113: // 레크만
                    if (player.isKnight() || player.isElf() || player.isDarkelf() || player.isIllusionist()) {
                        int talk_step = quest.get_step(L1Quest.QUEST_FIRSTQUEST);
                        if (talk_step == 1) {
                            if (player.getLevel() >= 5) {
                                htmlid = "orena4";
                            } else {
                                htmlid = "orena14";
                            }
                        } else if (talk_step == 255) {
                            htmlid = "orena11";
                        }
                    } else {
                        htmlid = "orena12";
                    }
                    break;
                case 70000:// new수동 마빈
                    if (quest.isEnd(L1Quest.QUEST_MARBIN)) {
                        htmlid = "marbinquest9";
                    } else {
                        if (quest.get_step(L1Quest.QUEST_MARBIN) == 1) {
                            htmlid = "marbinquest3";
                        } else if (quest.get_step(L1Quest.QUEST_MARBIN) == 0) {
                            htmlid = "marbinquest1";
                        }
                    }
                    if (player.getLevel() < 52) {
                        htmlid = "marbinquest8";
                    }
                    break;
                case 70005:// 파고
                    htmlid = "pago";
                    break;
                case 70009:
                    if (player.isCrown()) {
                        // htmlid = "gerengp1";
                        htmlid = "gerengw3";
                    } else if (player.isKnight() || player.isWarrior() || player.isFencer()) {
                        htmlid = "gerengw3";
                    } else if (player.isElf()) {
                        htmlid = "gerengw3";
                    } else if (player.isWizard()) {
                        /*
                         * if (player.getLevel() >= 30) { if
                         * (quest.isEnd(L1Quest.QUEST_LEVEL15)) { int lv30_step =
                         * quest.get_step(L1Quest.QUEST_LEVEL30); if (lv30_step >=
                         * 4) { htmlid = "gerengw3"; } else if (lv30_step >= 3) {
                         * htmlid = "gerengT4"; } else if (lv30_step >= 2) { htmlid
                         * = "gerengT3"; } else if (lv30_step >= 1) { htmlid =
                         * "gerengT2"; } else { htmlid = "gerengT1"; } } else {
                         * htmlid = "gerengw3"; } } else { htmlid = "gerengw3"; }
                         */
                        htmlid = "gerengw3";
                    } else if (player.isDarkelf()) {
                        htmlid = "gerengw3";
                    } else if (player.isDragonknight()) {
                        htmlid = "gerengdk1";
                    } else if (player.isIllusionist()) {
                        htmlid = "gerengi1";
                    }
                    break;
                case 70011:
                    long time = GameTimeClock.getInstance().getGameTime().getSeconds() % 86400;
                    // if(time < 0){
                    // time=time-time-time;
                    // }
                    if (time < 60 * 60 * 6 || time > 60 * 60 * 20 || time > -60 * 60 * 6 || time < -60 * 60 * 20) { // 20:00?6:00
                        htmlid = "shipEvI6";
                    }
                    break;

                case 70041:
                case 70042:
                case 70035:// 버경 상인
                    L1BugBearRace bugrace = L1BugBearRace.getInstance();
                    if (bugrace.getBugRaceStatus() == L1BugBearRace.STATUS_NONE) {
                        htmlid = "maeno5";
                    } else if (bugrace.getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
                        htmlid = "maeno3";
                    }
                    break;

                case 72320:
                    DogFight DogFightBug = DogFight.getInstance();
                    if (DogFightBug.getBugRaceStatus() == DogFight.STATUS_NONE) {
                        htmlid = "maeno5";
                    } else if (DogFightBug.getBugRaceStatus() == DogFight.STATUS_PLAYING) {
                        htmlid = "maeno3";
                    }
                    break;

                case 70087:
                    if (player.isDarkelf()) {
                        htmlid = "sedia";
                    }
                    break;
                case 70099:
                    if (!quest.isEnd(L1Quest.QUEST_OILSKINMANT)) {
                        if (player.getLevel() > 13) {
                            htmlid = "kuper1";
                        }
                    }
                    break;
                /*
                 * case 70522: if (player.isCrown()) { if (player.getLevel() >= 15)
                 * { int lv15_step = quest.get_step(L1Quest.QUEST_LEVEL15); if
                 * (lv15_step == 2 || lv15_step == L1Quest.QUEST_END) { htmlid =
                 * "gunterp11"; } else { htmlid = "gunterp9"; } } else { htmlid =
                 * "gunterp12"; } } else if (player.isKnight()) { int lv30_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL30); if (lv30_step == 0) {
                 * htmlid = "gunterk9"; } else if (lv30_step == 1) { htmlid =
                 * "gunterkE1"; } else if (lv30_step == 2) { htmlid = "gunterkE2"; }
                 * else if (lv30_step >= 3) { htmlid = "gunterkE3"; } } else if
                 * (player.isElf()) { htmlid = "guntere1"; } else if
                 * (player.isWizard()) { htmlid = "gunterw1"; } else if
                 * (player.isDarkelf()) { htmlid = "gunterde1"; } break;
                 */
                case 70528:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_TALKING_ISLAND);
                    break;
                /*
                 * case 70531: if (player.isWizard()) { if (player.getLevel() >= 15)
                 * { if (quest.isEnd(L1Quest.QUEST_LEVEL15)) { htmlid = "jem6"; }
                 * else { htmlid = "jem1"; } } } break;
                 */
                case 70534:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_TALKING_ISLAND);
                    break;
                /*
                 * case 70545: if (player.isCrown()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step >= 1 &&
                 * lv45_step != L1Quest.QUEST_END) { if
                 * (player.getInventory().checkItem(40586)) { htmlid = "richard4"; }
                 * else { htmlid = "richard1"; } } } break;
                 */
                case 70546:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_KENT);
                    break;
                case 70553:
                    boolean hascastle = checkHasCastle(player, L1CastleLocation.KENT_CASTLE_ID);
                    if (hascastle) {
                        if (checkClanLeader(player)) {
                            htmlid = "ishmael1";
                        } else {
                            htmlid = "ishmael6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "ishmael7";
                    }
                    break;
                /*
                 * case 70554: if (player.isCrown()) { if (player.getLevel() >= 15)
                 * { int lv15_step = quest.get_step(L1Quest.QUEST_LEVEL15); if
                 * (lv15_step == 1) { htmlid = "zero5"; } else if (lv15_step ==
                 * L1Quest.QUEST_END) { htmlid = "zero1"; } else { htmlid = "zero1";
                 * } } else { htmlid = "zero6"; } } break;
                 */
                /*
                 * case 70555: if (player.getGfxId().getTempCharGfx() == 2374) { if
                 * (player.isKnight()) { if (quest.get_step(L1Quest.QUEST_LEVEL30)
                 * == 6) { htmlid = "jim2"; } else { htmlid = "jim4"; } } else {
                 * htmlid = "jim4"; } } break;
                 */
                case 70633:
                    boolean hascastle04 = checkHasCastle(player, L1CastleLocation.GIRAN_CASTLE_ID);
                    if (hascastle04) {
                        htmlid = "colbert1";
                        htmldata = new String[]{player.getName()};
                    } else {
                        htmlid = "colbert4";
                    }
                    break;
                /*
                 * case 70653: if (player.isCrown()) { if (player.getLevel() >= 45)
                 * { if (quest.isEnd(L1Quest.QUEST_LEVEL30)) { int lv45_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL45); if (lv45_step ==
                 * L1Quest.QUEST_END) { htmlid = "masha4"; } else if (lv45_step >=
                 * 1) { htmlid = "masha3"; } else { htmlid = "masha1"; } } } } else
                 * if (player.isKnight()) { if (player.getLevel() >= 45) { if
                 * (quest.isEnd(L1Quest.QUEST_LEVEL30)) { int lv45_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL45); if (lv45_step ==
                 * L1Quest.QUEST_END) { htmlid = "mashak3"; } else if (lv45_step ==
                 * 0) { htmlid = "mashak1"; } else if (lv45_step >= 1) { htmlid =
                 * "mashak2"; } } } } else if (player.isElf()) { if
                 * (player.getLevel() >= 45) { if
                 * (quest.isEnd(L1Quest.QUEST_LEVEL30)) { int lv45_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL45); if (lv45_step ==
                 * L1Quest.QUEST_END) { htmlid = "mashae3"; } else if (lv45_step >=
                 * 1) { htmlid = "mashae2"; } else { htmlid = "mashae1"; } } } }
                 * break;
                 */
                case 70654:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_WERLDAN);
                    break;
                case 70556:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_KENT);
                    break;
                case 70567:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_GLUDIO);
                    break;
                case 70572:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_GLUDIO);
                    break;
                case 70594:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_GIRAN);
                    break;
                case 70623:
                    boolean hascastle3 = checkHasCastle(player, L1CastleLocation.GIRAN_CASTLE_ID);
                    if (hascastle3) {
                        if (checkClanLeader(player)) {
                            htmlid = "orville1";
                        } else {
                            htmlid = "orville6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "orville7";
                    }
                    break;
                case 70631:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_GIRAN);
                    break;
                case 7: // 아놀드
                    if (player.getLevel() >= 52) {
                        htmlid = "anold1";
                    } else {
                        htmlid = "anold2";
                    }
                    break;
                case 70663:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_WERLDAN);
                    break;
                case 70665:
                    boolean hascastle5 = checkHasCastle(player, L1CastleLocation.DOWA_CASTLE_ID);
                    if (hascastle5) {
                        if (checkClanLeader(player)) {
                            htmlid = "potempin1";
                        } else {
                            htmlid = "potempin6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "potempin7";
                    }
                    break;
                /*
                 * case 70711: if (player.isKnight()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step == 2) { if
                 * (player.getInventory().checkItem(20026)) { htmlid = "giantk1"; }
                 * } else if (lv45_step == 3) { htmlid = "giantk2"; } else if
                 * (lv45_step >= 4) { htmlid = "giantk3"; } } break;
                 */
                /*
                 * case 70715: if (player.isKnight()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step == 1) {
                 * htmlid = "jimuk1"; } else if (lv45_step >= 2) { htmlid =
                 * "jimuk2"; } } break;
                 */
                case 70721:
                    boolean hascastle6 = checkHasCastle(player, L1CastleLocation.ADEN_CASTLE_ID);
                    if (hascastle6) {
                        if (checkClanLeader(player)) {
                            htmlid = "timon1";
                        } else {
                            htmlid = "timon6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "timon7";
                    }
                    break;
                /*
                 * case 70724: if (player.isElf()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step >= 4) {
                 * htmlid = "heit5"; } else if (lv45_step >= 3) { htmlid = "heit3";
                 * } else if (lv45_step >= 2) { htmlid = "heit2"; } else if
                 * (lv45_step >= 1) { htmlid = "heit1"; } } break;
                 */
                /*
                 * case 70739: if (player.getLevel() >= 50) { int lv50_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL50); if (lv50_step ==
                 * L1Quest.QUEST_END) { if (player.isCrown()) { htmlid =
                 * "dicardingp3"; } else if (player.isKnight()) { htmlid =
                 * "dicardingk3"; } else if (player.isElf()) { htmlid =
                 * "dicardinge3"; } else if (player.isWizard()) { htmlid =
                 * "dicardingw3"; } else if (player.isDarkelf()) { htmlid =
                 * "dicarding"; } } else if (lv50_step >= 1) { if (player.isCrown())
                 * { htmlid = "dicardingp2"; } else if (player.isKnight()) { htmlid
                 * = "dicardingk2"; } else if (player.isElf()) { htmlid =
                 * "dicardinge2"; } else if (player.isWizard()) { htmlid =
                 * "dicardingw2"; } else if (player.isDarkelf()) { htmlid =
                 * "dicarding"; } } else if (lv50_step >= 0) { if (player.isCrown())
                 * { htmlid = "dicardingp1"; } else if (player.isKnight()) { htmlid
                 * = "dicardingk1"; } else if (player.isElf()) { htmlid =
                 * "dicardinge1"; } else if (player.isWizard()) { htmlid =
                 * "dicardingw1"; } else if (player.isDarkelf()) { htmlid =
                 * "dicarding"; } } else { htmlid = "dicarding"; } } else { htmlid =
                 * "dicarding"; } break;
                 */
                /*
                 * case 70744: if (player.isDarkelf()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step >= 5) {
                 * htmlid = "roje14"; } else if (lv45_step >= 4) { htmlid =
                 * "roje13"; } else if (lv45_step >= 3) { htmlid = "roje12"; } else
                 * if (lv45_step >= 2) { htmlid = "roje11"; } else { htmlid =
                 * "roje15"; } } break;
                 */
                case 70748:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_OREN);
                    break;
                case 70761:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_OREN);
                    break;
                case 70762:
                    if (!player.isDarkelf())
                        htmlid = "karif9";
                    int karif_step = quest.get_step(L1Quest.QUEST_KARIF);
                    if (karif_step == L1Quest.QUEST_END) {
                        htmlid = "karif9";
                    }
                    if (player.getLevel() < 50) {
                        htmlid = "karif9";
                    }
                    break;
                /*
                 * case 70763: if (player.isWizard()) { int lv30_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL30); if (lv30_step ==
                 * L1Quest.QUEST_END) { if (player.getLevel() >= 45) { int lv45_step
                 * = quest .get_step(L1Quest.QUEST_LEVEL45); if (lv45_step >= 1 &&
                 * lv45_step != L1Quest.QUEST_END) { htmlid = "talassmq2"; } else if
                 * (lv45_step <= 0) { htmlid = "talassmq1"; } } } else if (lv30_step
                 * == 4) { htmlid = "talassE1"; } else if (lv30_step == 5) { htmlid
                 * = "talassE2"; } } break;
                 */
                case 70774:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_WINDAWOOD);
                    break;
                /*
                 * case 70775: if (player.isKnight()) { if (player.getLevel() >= 30)
                 * { if (quest.isEnd(L1Quest.QUEST_LEVEL15)) { int lv30_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL30); if (lv30_step == 0) { htmlid =
                 * "mark1"; } else { htmlid = "mark2"; } } } } break;
                 */
                /*
                 * case 70776 : if (player.isCrown()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step == 1) {
                 * htmlid = "meg1"; } else if (lv45_step == 2 && lv45_step <= 3 ) {
                 * htmlid = "meg2"; } else if (lv45_step >= 4) { htmlid = "meg3"; }
                 * } break;
                 */
                /*
                 * case 70782: if (player.getGfxId().getTempCharGfx() == 1037) { if
                 * (player.isCrown()) { if (quest.get_step(L1Quest.QUEST_LEVEL30) ==
                 * 1) { htmlid = "ant1"; } else { htmlid = "ant3"; } } else { htmlid
                 * = "ant3"; } } break;
                 */
                /*
                 * case 70783: if (player.isCrown()) { if (player.getLevel() >= 30)
                 * { if (quest.isEnd(L1Quest.QUEST_LEVEL15)) { int lv30_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL30); if (lv30_step ==
                 * L1Quest.QUEST_END) { htmlid = "aria3"; } else if (lv30_step == 1)
                 * { htmlid = "aria2"; } else { htmlid = "aria1"; } } } } break;
                 */
                case 70784:
                    boolean hascastle2 = checkHasCastle(player, L1CastleLocation.WW_CASTLE_ID);
                    if (hascastle2) {
                        if (checkClanLeader(player)) {
                            htmlid = "othmond1";
                        } else {
                            htmlid = "othmond6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "othmond7";
                    }
                    break;
                case 70788:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_WINDAWOOD);
                    break;
                /*
                 * case 70794: if (player.isCrown()) { htmlid = "gerardp1"; } else
                 * if (player.isKnight()) { int lv30_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL30); if (lv30_step ==
                 * L1Quest.QUEST_END) { htmlid = "gerardkEcg"; } else if (lv30_step
                 * < 3) { htmlid = "gerardk7"; } else if (lv30_step == 3) { htmlid =
                 * "gerardkE1"; } else if (lv30_step == 4) { htmlid = "gerardkE2"; }
                 * else if (lv30_step == 5) { htmlid = "gerardkE3"; } else if
                 * (lv30_step >= 6) { htmlid = "gerardkE4"; } } else if
                 * (player.isElf()) { htmlid = "gerarde1"; } else if
                 * (player.isWizard()) { htmlid = "gerardw1"; } else if
                 * (player.isDarkelf()) { htmlid = "gerardde1"; } break;
                 */
                case 70796:
                    if (!quest.isEnd(L1Quest.QUEST_OILSKINMANT)) {
                        if (player.getLevel() > 69) {
                            htmlid = "dunham1";
                        }
                    }
                    break;
                /*
                 * case 70798: if (player.isKnight()) { if (player.getLevel() >= 15)
                 * { int lv15_step = quest.get_step(L1Quest.QUEST_LEVEL15); if
                 * (lv15_step >= 1) { htmlid = "riky5"; } else { htmlid = "riky1"; }
                 * } else { htmlid = "riky6"; } } break;
                 */
                case 70799:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_SILVER_KNIGHT_TOWN);
                    break;
                /*
                 * case 70802: if (player.isKnight()) { if (player.getLevel() >= 15)
                 * { int lv15_step = quest.get_step(L1Quest.QUEST_LEVEL15); if
                 * (lv15_step == L1Quest.QUEST_END) { htmlid = "aanon7"; } else if
                 * (lv15_step == 1) { htmlid = "aanon4"; } } } break;
                 */
                case 70806:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_SILVER_KNIGHT_TOWN);
                    break;
                case 70811:
                    /*
                     * if (quest.get_step(L1Quest.QUEST_LYRA) >= 1) { htmlid =
                     * "lyraEv3"; } else { htmlid = "lyraEv1"; }
                     */
                    break;
                case 70815:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_ORCISH_FOREST);
                    break;
                case 70822:
                    boolean hascastle1 = checkHasCastle(player, L1CastleLocation.OT_CASTLE_ID);
                    if (hascastle1) {
                        if (checkClanLeader(player)) {
                            htmlid = "seghem1";
                        } else {
                            htmlid = "seghem6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "seghem7";
                    }
                    break;
                /*
                 * case 70824: if (player.isDarkelf()) { if
                 * (player.getGfxId().getTempCharGfx() == 3634) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step == 1) {
                 * htmlid = "assassin1"; } else if (lv45_step == 2) { htmlid =
                 * "assassin2"; } else { htmlid = "assassin3"; } } else { htmlid =
                 * "assassin3"; } } break;
                 */
                /*
                 * case 70826: if (player.isElf()) { if (player.getLevel() >= 15) {
                 * if (quest.isEnd(L1Quest.QUEST_LEVEL15)) { htmlid = "oth5"; } else
                 * { htmlid = "oth1"; } } else { htmlid = "oth6"; } } break;
                 */
                case 70830:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_ORCISH_FOREST);
                    break;
                case 70838: // 네루파
                    if (player.isCrown() || player.isKnight() || player.isWizard() || player.isDragonknight() || player.isIllusionist() || player.isFencer()) {
                        htmlid = "nerupam1";
                    } else if (player.isDarkelf() && (player.getLawful() <= -1)) {
                        htmlid = "nerupaM2";
                    } else if (player.isDarkelf()) {
                        htmlid = "nerupace1";
                    } else if (player.isElf()) {
                        htmlid = "nerupae1";
                    }
                    break;
                case 70841:
                    if (player.isElf()) {
                        htmlid = "luudielE1";
                    } else if (player.isDarkelf()) {
                        htmlid = "luudielCE1";
                    } else {
                        htmlid = "luudiel1";
                    }
                    break;
                case 70842: // 마르바
                    if (player.getLawful() <= -501) {
                        htmlid = "marba1";
                    } else if (!player.isElf()) {
                        htmlid = "marba2";
                    } else if (player.getInventory().checkItem(40665)
                            && (player.getInventory().checkItem(40693)
                            || player.getInventory().checkItem(40694)
                            || player.getInventory().checkItem(40695)
                            || player.getInventory().checkItem(40697)
                            || player.getInventory().checkItem(40698) || player.getInventory().checkItem(40699))) {
                        htmlid = "marba8";
                    } else if (player.getInventory().checkItem(40665)) {
                        htmlid = "marba17";
                    } else if (player.getInventory().checkItem(40664)) {
                        htmlid = "marba19";
                    } else if (player.getInventory().checkItem(40637)) {
                        htmlid = "marba18";
                    } else {
                        htmlid = "marba3";
                    }
                    break;
                /*
                 * case 70844: if (player.isElf()) { if (player.getLevel() >= 30) {
                 * if (quest.isEnd(L1Quest.QUEST_LEVEL15)) { int lv30_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL30); if (lv30_step ==
                 * L1Quest.QUEST_END) { htmlid = "motherEE3"; } else if (lv30_step
                 * >= 1) { htmlid = "motherEE2"; } else if (lv30_step <= 0) { htmlid
                 * = "motherEE1"; } } else { htmlid = "mothere1"; } } else { htmlid
                 * = "mothere1"; } } break;
                 */
                case 70845: // 아라스
                    if (player.getLawful() <= -501) {
                        htmlid = "aras12";
                    } else if (!player.isElf()) {
                        htmlid = "aras11";
                    } else if (player.getInventory().checkItem(40665)
                            && (player.getInventory().checkItem(40679)
                            || player.getInventory().checkItem(40680)
                            || player.getInventory().checkItem(40681)
                            || player.getInventory().checkItem(40682)
                            || player.getInventory().checkItem(40683) || player.getInventory().checkItem(40684))) {
                        htmlid = "aras3";
                    } else if (player.getInventory().checkItem(40665)) {
                        htmlid = "aras8";
                    } else if (player.getInventory().checkItem(40679)
                            || player.getInventory().checkItem(40680)
                            || player.getInventory().checkItem(40681)
                            || player.getInventory().checkItem(40682)
                            || player.getInventory().checkItem(40683)
                            || player.getInventory().checkItem(40684)
                            || player.getInventory().checkItem(40693)
                            || player.getInventory().checkItem(40694)
                            || player.getInventory().checkItem(40695)
                            || player.getInventory().checkItem(40697)
                            || player.getInventory().checkItem(40698)
                            || player.getInventory().checkItem(40699)) {
                        htmlid = "aras3";
                    } else if (player.getInventory().checkItem(40664)) {
                        htmlid = "aras6";
                    } else if (player.getInventory().checkItem(40637)) {
                        htmlid = "aras1";
                    } else {
                        htmlid = "aras7";
                    }
                    break;
                case 70860:
                    htmlid = talkToTownmaster(player, L1TownLocation.TOWNID_HEINE);
                    break;
                case 70876:
                    htmlid = talkToTownadviser(player, L1TownLocation.TOWNID_HEINE);
                    break;
                case 70880:
                    boolean hascastle4 = checkHasCastle(player, L1CastleLocation.HEINE_CASTLE_ID);
                    if (hascastle4) {
                        if (checkClanLeader(player)) {
                            htmlid = "fisher1";
                        } else {
                            htmlid = "fisher6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "fisher7";
                    }
                    break;
                /*
                 * case 70885: if (player.isDarkelf()) { if (player.getLevel() >=
                 * 15) { int lv15_step = quest.get_step(L1Quest.QUEST_LEVEL15); if
                 * (lv15_step == L1Quest.QUEST_END) { htmlid = "kanguard3"; } else
                 * if (lv15_step >= 1) { htmlid = "kanguard2"; } else { htmlid =
                 * "kanguard1"; } } else { htmlid = "kanguard5"; } } break;
                 */
                /*
                 * case 70892: if (player.isDarkelf()) { if (player.getLevel() >=
                 * 30) { if (quest.isEnd(L1Quest.QUEST_LEVEL15)) { int lv30_step =
                 * quest .get_step(L1Quest.QUEST_LEVEL30); if (lv30_step ==
                 * L1Quest.QUEST_END) { htmlid = "ronde5"; } else if (lv30_step >=
                 * 2) { htmlid = "ronde3"; } else if (lv30_step >= 1) { htmlid =
                 * "ronde2"; } else { htmlid = "ronde1"; } } else { htmlid =
                 * "ronde7"; } } else { htmlid = "ronde7"; } } break;
                 */
                /*
                 * case 70895: if (player.isDarkelf()) { if (player.getLevel() >=
                 * 45) { if (quest.isEnd(L1Quest.QUEST_LEVEL30)) { int lv45_step =
                 * quest .get_step(L1Quest.QUEST_LEVEL45); if (lv45_step ==
                 * L1Quest.QUEST_END) { if (player.getLevel() < 50) { htmlid =
                 * "bluedikaq3"; } else { int lv50_step = quest
                 * .get_step(L1Quest.QUEST_LEVEL50); if (lv50_step ==
                 * L1Quest.QUEST_END) { htmlid = "bluedikaq8"; } else { htmlid =
                 * "bluedikaq6"; } } } else if (lv45_step >= 1) { htmlid =
                 * "bluedikaq2"; } else { htmlid = "bluedikaq1"; } } else { htmlid =
                 * "bluedikaq5"; } } else { htmlid = "bluedikaq5"; } } break;
                 */
                /*
                 * case 70904: if (player.isDarkelf()) { if
                 * (quest.get_step(L1Quest.QUEST_LEVEL45) == 1) { htmlid = "koup12";
                 * } } break;
                 */
                case 70906: // 키마
                    if (player.getLevel() < 50)
                        htmlid = "kima1";
                    break;
                case 71013:
                    if (player.isDarkelf()) {
                        if (player.getLevel() <= 3) {
                            htmlid = "karen1";
                        } else if (player.getLevel() > 3 && player.getLevel() < 50) {
                            htmlid = "karen3";
                        } else if (player.getLevel() >= 50) {
                            htmlid = "karen4";
                        }
                    }
                    break;
                case 71036:
                    if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == L1Quest.QUEST_END) {
                        htmlid = "kamyla26";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 4 && player.getInventory().checkItem(40717)) {
                        htmlid = "kamyla15";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 4) {
                        htmlid = "kamyla14";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 3 && player.getInventory().checkItem(40630)) {
                        htmlid = "kamyla12";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 3) {
                        htmlid = "kamyla11";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 2 && player.getInventory().checkItem(40644)) {
                        htmlid = "kamyla9";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 1) {
                        htmlid = "kamyla8";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_CADMUS) == L1Quest.QUEST_END && player.getInventory().checkItem(40621)) {
                        htmlid = "kamyla1";
                    }
                    break;
                case 71038:
                    if (player.getInventory().checkItem(41060)) {
                        if (player.getInventory().checkItem(41090) || player.getInventory().checkItem(41091) || player.getInventory().checkItem(41092)) {
                            htmlid = "orcfnoname7";
                        } else {
                            htmlid = "orcfnoname8";
                        }
                    } else {
                        htmlid = "orcfnoname1";
                    }
                    break;
                case 71040:
                    if (player.getInventory().checkItem(41060)) {
                        if (player.getInventory().checkItem(41065)) {
                            if (player.getInventory().checkItem(41086)
									|| player.getInventory().checkItem(41087)
									|| player.getInventory().checkItem(41088)
									|| player.getInventory().checkItem(41089)) {
                                htmlid = "orcfnoa6";
                            } else {
                                htmlid = "orcfnoa5";
                            }
                        } else {
                            htmlid = "orcfnoa2";
                        }
                    } else {
                        htmlid = "orcfnoa1";
                    }
                    break;
                case 71041:
                    if (player.getInventory().checkItem(41060)) {
                        if (player.getInventory().checkItem(41064)) {
                            if (player.getInventory().checkItem(41081)
                                    || player.getInventory().checkItem(41082)
                                    || player.getInventory().checkItem(41083)
                                    || player.getInventory().checkItem(41084)
                                    || player.getInventory().checkItem(41085)) {
                                htmlid = "orcfhuwoomo2";
                            } else {
                                htmlid = "orcfhuwoomo8";
                            }
                        } else {
                            htmlid = "orcfhuwoomo1";
                        }
                    } else {
                        htmlid = "orcfhuwoomo5";
                    }
                    break;
                case 71042:
                    if (player.getInventory().checkItem(41060)) {
                        if (player.getInventory().checkItem(41062)) {
                            if (player.getInventory().checkItem(41071)
                                    || player.getInventory().checkItem(41072)
                                    || player.getInventory().checkItem(41073)
                                    || player.getInventory().checkItem(41074)
                                    || player.getInventory().checkItem(41075)) {
                                htmlid = "orcfbakumo2";
                            } else {
                                htmlid = "orcfbakumo8";
                            }
                        } else {
                            htmlid = "orcfbakumo1";
                        }
                    } else {
                        htmlid = "orcfbakumo5";
                    }
                    break;
                case 71043:
                    if (player.getInventory().checkItem(41060)) {
                        if (player.getInventory().checkItem(41063)) {
                            if (player.getInventory().checkItem(41076)
                                    || player.getInventory().checkItem(41077)
                                    || player.getInventory().checkItem(41078)
                                    || player.getInventory().checkItem(41079)
                                    || player.getInventory().checkItem(41080)) {
                                htmlid = "orcfbuka2";
                            } else {
                                htmlid = "orcfbuka8";
                            }
                        } else {
                            htmlid = "orcfbuka1";
                        }
                    } else {
                        htmlid = "orcfbuka5";
                    }
                    break;
                case 71044:
                    if (player.getInventory().checkItem(41060)) {
                        if (player.getInventory().checkItem(41061)) {
                            if (player.getInventory().checkItem(41066)
                                    || player.getInventory().checkItem(41067)
                                    || player.getInventory().checkItem(41068)
                                    || player.getInventory().checkItem(41069)
                                    || player.getInventory().checkItem(41070)) {
                                htmlid = "orcfkame2";
                            } else {
                                htmlid = "orcfkame8";
                            }
                        } else {
                            htmlid = "orcfkame1";
                        }
                    } else {
                        htmlid = "orcfkame5";
                    }
                    break;
                case 71055:
                    if (player.getQuest().get_step(L1Quest.QUEST_RESTA) == 3) {
                        htmlid = "lukein13";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == L1Quest.QUEST_END
                            && player.getQuest().get_step(L1Quest.QUEST_RESTA) == 2
                            && player.getInventory().checkItem(40631)) {
                        htmlid = "lukein10";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == L1Quest.QUEST_END) {
                        htmlid = "lukein0";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 11) {
                        if (player.getInventory().checkItem(40716)) {
                            htmlid = "lukein9";
                        }
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) >= 1
                            && player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) <= 10) {
                        htmlid = "lukein8";
                    }
                    break;
                case 71056:
                    if (player.getQuest().get_step(L1Quest.QUEST_RESTA) == 4) {
                        if (player.getInventory().checkItem(40631)) {
                            htmlid = "simizz11";
                        } else {
                            htmlid = "simizz0";
                        }
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SIMIZZ) == 2) {
                        htmlid = "simizz0";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SIMIZZ) == L1Quest.QUEST_END) {
                        htmlid = "simizz15";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SIMIZZ) == 1) {
                        htmlid = "simizz6";
                    }
                    break;
                case 71057:
                    if (player.getQuest().get_step(L1Quest.QUEST_DOIL) == L1Quest.QUEST_END) {
                        htmlid = "doil4b";
                    }
                    break;
                case 71059:
                    if (player.getQuest().get_step(L1Quest.QUEST_RUDIAN) == L1Quest.QUEST_END) {
                        htmlid = "rudian1c";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_RUDIAN) == 1) {
                        htmlid = "rudian7";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_DOIL) == L1Quest.QUEST_END) {
                        htmlid = "rudian1b";
                    } else {
                        htmlid = "rudian1a";
                    }
                    break;
                case 71060:
                    if (player.getQuest().get_step(L1Quest.QUEST_RESTA) == L1Quest.QUEST_END) {
                        htmlid = "resta1e";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SIMIZZ) == L1Quest.QUEST_END) {
                        htmlid = "resta14";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_RESTA) == 4) {
                        htmlid = "resta13";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_RESTA) == 3) {
                        htmlid = "resta11";
                        player.getQuest().set_step(L1Quest.QUEST_RESTA, 4);
                    } else if (player.getQuest().get_step(L1Quest.QUEST_RESTA) == 2) {
                        htmlid = "resta16";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SIMIZZ) == 2
                            && player.getQuest().get_step(L1Quest.QUEST_CADMUS) == 1
                            || player.getInventory().checkItem(40647)) {
                        htmlid = "resta1a";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_CADMUS) == 1
                            || player.getInventory().checkItem(40647)) {
                        htmlid = "resta1c";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SIMIZZ) == 2) {
                        htmlid = "resta1b";
                    }
                    break;
                case 71061:
                    if (player.getQuest().get_step(L1Quest.QUEST_CADMUS) == L1Quest.QUEST_END) {
                        htmlid = "cadmus1c";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_CADMUS) == 3) {
                        htmlid = "cadmus8";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_CADMUS) == 2) {
                        htmlid = "cadmus1a";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_DOIL) == L1Quest.QUEST_END) {
                        htmlid = "cadmus1b";
                    }
                    break;
                case 71063:
                    if (player.getQuest().get_step(L1Quest.QUEST_TBOX1) == L1Quest.QUEST_END) {
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 1) {
                        htmlid = "maptbox";
                    }
                    break;
                case 71064:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 2) {
                        htmlid = talkToSecondtbox(player);
                    }
                    break;
                case 71065:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 3) {
                        htmlid = talkToSecondtbox(player);
                    }
                    break;
                case 71066:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 4) {
                        htmlid = talkToSecondtbox(player);
                    }
                    break;
                case 71067:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 5) {
                        htmlid = talkToThirdtbox(player);
                    }
                    break;
                case 71068:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 6) {
                        htmlid = talkToThirdtbox(player);
                    }
                    break;
                case 71069:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 7) {
                        htmlid = talkToThirdtbox(player);
                    }
                    break;
                case 71070:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 8) {
                        htmlid = talkToThirdtbox(player);
                    }
                    break;
                case 71071:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 9) {
                        htmlid = talkToThirdtbox(player);
                    }
                    break;
                case 71072:
                    if (player.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 10) {
                        htmlid = talkToThirdtbox(player);
                    }
                    break;
                case 71074:
                    if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == L1Quest.QUEST_END) {
                        htmlid = "lelder0";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == 3
                            && player.getInventory().checkItem(40634)) {
                        htmlid = "lelder12";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == 3) {
                        htmlid = "lelder11";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == 2
                            && player.getInventory().checkItem(40633)) {
                        htmlid = "lelder7";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == 2) {
                        htmlid = "lelder7b";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == 1) {
                        htmlid = "lelder7b";
                    } else if (player.getLevel() >= 40) {
                        htmlid = "lelder1";
                    }
                    break;
                case 71076:
                    if (player.getQuest().get_step(L1Quest.QUEST_LIZARD) == L1Quest.QUEST_END) {
                        htmlid = "ylizardb";
                    } else {
                    }
                    break;
                case 71089:
                    if (player.getQuest().get_step(L1Quest.QUEST_KAMYLA) == 2) {
                        htmlid = "francu12";
                    }
                    break;
                case 71090:
                    if (player.getQuest().get_step(L1Quest.QUEST_CRYSTAL) == 1
                            && player.getInventory().checkItem(40620)) {
                        htmlid = "jcrystal2";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_CRYSTAL) == 1) {
                        htmlid = "jcrystal3";
                    }
                    break;
                case 71091:
                    if (player.getQuest().get_step(L1Quest.QUEST_CRYSTAL) == 2
                            && player.getInventory().checkItem(40654)) {
                        htmlid = "jcrystall2";
                    }
                    break;
                case 71141:
                    if (player.getGfxId().getTempCharGfx() == 3887) {
                        htmlid = "moumthree1";
                    }
                    break;
                case 71142:
                    if (player.getGfxId().getTempCharGfx() == 3887) {
                        htmlid = "moumtwo1";
                    }
                    break;
                case 71145:
                    if (player.getGfxId().getTempCharGfx() == 3887) {
                        htmlid = "moumone1";
                    }
                    break;
                case 71167:
                    if (player.getGfxId().getTempCharGfx() == 3887) {
                        htmlid = "frim1";
                    }
                    break;
                case 71168: // 진명황 단테스
                    if (player.getInventory().checkItem(41028)) {
                        htmlid = "dantes1";
                    }
                    break;
                case 71180: // 제이프
                    if (player.get_sex() == 0)
                        htmlid = "jp1";// 남자
                    else
                        htmlid = "jp3";
                case 71198:
                    if (player.getQuest().get_step(71198) == 1) {
                        htmlid = "tion4";
                    } else if (player.getQuest().get_step(71198) == 2) {
                        htmlid = "tion5";
                    } else if (player.getQuest().get_step(71198) == 3) {
                        htmlid = "tion6";
                    } else if (player.getQuest().get_step(71198) == 4) {
                        htmlid = "tion7";
                    } else if (player.getQuest().get_step(71198) == 5) {
                        htmlid = "tion5";
                    } else if (player.getInventory().checkItem(21059, 1)) {
                        htmlid = "tion19";
                    }
                    break;
                case 71199:
                    if (player.getQuest().get_step(71199) == 1) {
                        htmlid = "jeron3";
                    } else if (player.getInventory().checkItem(21059, 1)
                            || player.getQuest().get_step(71199) == 255) {
                        htmlid = "jeron7";
                    }
                    break;
                /*
                 * case 71200: if (player.isCrown()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step == 2 &&
                 * player.getInventory().checkItem(41422)) {
                 * player.getInventory().consumeItem(41422, 1); final int[] item_ids
                 * = { 40568 }; final int[] item_amounts = { 1 }; for (int i = 0; i
                 * < item_ids.length; i++) { player.getInventory().storeItem(
                 * item_ids[i], item_amounts[i]); } } } break;
                 */
                case 80047:
                    if (player.getKarmaLevel() > -3) {
                        htmlid = "uhelp1";
                    } else {
                        htmlid = "uhelp2";
                    }
                    break;

                case 80049:
                    if (player.getKarma() <= -10000000) {
                        htmlid = "betray11";
                    } else {
                        htmlid = "betray12";
                    }
                    break;
                case 80050:
                    if (player.getKarmaLevel() > -1) {
                        htmlid = "meet103";
                    } else {
                        htmlid = "meet101";
                    }
                    break;
			/*case 80053:
				int karmaLevel = player.getKarmaLevel();
				if (karmaLevel == 0) {
					htmlid = "aliceyet";
				} else if (karmaLevel >= 1) {
					if (player.getInventory().checkItem(196)
							|| player.getInventory().checkItem(197)
							|| player.getInventory().checkItem(198)
							|| player.getInventory().checkItem(199)
							|| player.getInventory().checkItem(200)
							|| player.getInventory().checkItem(201)
							|| player.getInventory().checkItem(202)
							|| player.getInventory().checkItem(203)) {
						htmlid = "alice_gd";
					} else {
						htmlid = "gd";
					}
				} else if (karmaLevel <= -1) {
					if (player.getInventory().checkItem(40991)) {
						if (karmaLevel <= -1) {
							htmlid = "Mate_1";
						}
					} else if (player.getInventory().checkItem(196)) {
						if (karmaLevel <= -2) {
							htmlid = "Mate_2";
						} else {
							htmlid = "alice_1";
						}
					} else if (player.getInventory().checkItem(197)) {
						if (karmaLevel <= -3) {
							htmlid = "Mate_3";
						} else {
							htmlid = "alice_2";
						}
					} else if (player.getInventory().checkItem(198)) {
						if (karmaLevel <= -4) {
							htmlid = "Mate_4";
						} else {
							htmlid = "alice_3";
						}
					} else if (player.getInventory().checkItem(199)) {
						if (karmaLevel <= -5) {
							htmlid = "Mate_5";
						} else {
							htmlid = "alice_4";
						}
					} else if (player.getInventory().checkItem(200)) {
						if (karmaLevel <= -6) {
							htmlid = "Mate_6";
						} else {
							htmlid = "alice_5";
						}
					} else if (player.getInventory().checkItem(201)) {
						if (karmaLevel <= -7) {
							htmlid = "Mate_7";
						} else {
							htmlid = "alice_6";
						}
					} else if (player.getInventory().checkItem(202)) {
						if (karmaLevel <= -8) {
							htmlid = "Mate_8";
						} else {
							htmlid = "alice_7";
						}
					} else if (player.getInventory().checkItem(203)) {
						htmlid = "alice_8";
					} else {
						htmlid = "alice_no";
					}
				}
				break;*/
                case 80055:
                    int amuletLevel = 0;
                    if (player.getInventory().checkItem(20358)) {
                        amuletLevel = 1;
                    } else if (player.getInventory().checkItem(20359)) {
                        amuletLevel = 2;
                    } else if (player.getInventory().checkItem(20360)) {
                        amuletLevel = 3;
                    } else if (player.getInventory().checkItem(20361)) {
                        amuletLevel = 4;
                    } else if (player.getInventory().checkItem(20362)) {
                        amuletLevel = 5;
                    } else if (player.getInventory().checkItem(20363)) {
                        amuletLevel = 6;
                    } else if (player.getInventory().checkItem(20364)) {
                        amuletLevel = 7;
                    } else if (player.getInventory().checkItem(20365)) {
                        amuletLevel = 8;
                    }
                    if (player.getKarmaLevel() == -1) {
                        if (amuletLevel >= 1) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet1";
                        }
                    } else if (player.getKarmaLevel() == -2) {
                        if (amuletLevel >= 2) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet2";
                        }
                    } else if (player.getKarmaLevel() == -3) {
                        if (amuletLevel >= 3) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet3";
                        }
                    } else if (player.getKarmaLevel() == -4) {
                        if (amuletLevel >= 4) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet4";
                        }
                    } else if (player.getKarmaLevel() == -5) {
                        if (amuletLevel >= 5) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet5";
                        }
                    } else if (player.getKarmaLevel() == -6) {
                        if (amuletLevel >= 6) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet6";
                        }
                    } else if (player.getKarmaLevel() == -7) {
                        if (amuletLevel >= 7) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet7";
                        }
                    } else if (player.getKarmaLevel() == -8) {
                        if (amuletLevel >= 8) {
                            htmlid = "uamuletd";
                        } else {
                            htmlid = "uamulet8";
                        }
                    } else {
                        htmlid = "uamulet0";
                    }
                    break;
                case 80056:
                    if (player.getKarma() <= -10000000) {
                        htmlid = "infamous11";
                    } else {
                        htmlid = "infamous12";
                    }
                    break;
                case 80057:
                    switch (player.getKarmaLevel()) {
                        case 0:
                            htmlid = "alfons1";
                            break;
                        case -1:
                            htmlid = "cyk1";
                            break;
                        case -2:
                            htmlid = "cyk2";
                            break;
                        case -3:
                            htmlid = "cyk3";
                            break;
                        case -4:
                            htmlid = "cyk4";
                            break;
                        case -5:
                            htmlid = "cyk5";
                            break;
                        case -6:
                            htmlid = "cyk6";
                            break;
                        case -7:
                            htmlid = "cyk7";
                            break;
                        case -8:
                            htmlid = "cyk8";
                            break;
                        case 1:
                            htmlid = "cbk1";
                            break;
                        case 2:
                            htmlid = "cbk2";
                            break;
                        case 3:
                            htmlid = "cbk3";
                            break;
                        case 4:
                            htmlid = "cbk4";
                            break;
                        case 5:
                            htmlid = "cbk5";
                            break;
                        case 6:
                            htmlid = "cbk6";
                            break;
                        case 7:
                            htmlid = "cbk7";
                            break;
                        case 8:
                            htmlid = "cbk8";
                            break;
                        default:
                            htmlid = "alfons1";
                            break;
                    }
                    break;
                case 80058:// 욕망버땅
                    int level5 = player.getLevel();
                    if (level5 <= 44) {
                        htmlid = "cpass03";
                    } else if (level5 >= 60 && level5 <= 69) {
                        htmlid = "cpass02";
                    } else {
                        htmlid = "cpass01";
                    }
                    break;
                case 80048:// 버땅
                    int level = player.getLevel();
                    if (level <= 44) {
                        htmlid = "entgate3";
                    } else if (level >= 60 && level <= 69) {
                        htmlid = "entgate2";
                    } else {
                        htmlid = "entgate";
                    }
                    break;
                case 80059:
                    if (player.getKarmaLevel() >= 3) {
                        htmlid = "cpass03";
                    } else if (player.getInventory().checkItem(40921)) {
                        htmlid = "wpass02";
                    } else if (player.getInventory().checkItem(40917)) {
                        htmlid = "wpass14";
                    } else if (player.getInventory().checkItem(40912)
                            || player.getInventory().checkItem(40910)
                            || player.getInventory().checkItem(40911)) {
                        htmlid = "wpass04";
                    } else if (player.getInventory().checkItem(40909)) {
                        int count = getNecessarySealCount(player);
                        if (player.getInventory().checkItem(40913, count)) {
                            createRuler(player, 1, count);
                            htmlid = "wpass06";
                        } else {
                            htmlid = "wpass03";
                        }
                    } else if (player.getInventory().checkItem(40913)) {
                        htmlid = "wpass08";
                    } else {
                        htmlid = "wpass05";
                    }
                    break;
                case 80060:
                    if (player.getKarmaLevel() >= 3) {
                        htmlid = "cpass03";
                    } else if (player.getInventory().checkItem(40921)) {
                        htmlid = "wpass02";
                    } else if (player.getInventory().checkItem(40920)) {
                        htmlid = "wpass13";
                    } else if (player.getInventory().checkItem(40909)
                            || player.getInventory().checkItem(40910)
                            || player.getInventory().checkItem(40911)) {
                        htmlid = "wpass04";
                    } else if (player.getInventory().checkItem(40912)) {
                        int count = getNecessarySealCount(player);
                        if (player.getInventory().checkItem(40916, count)) {
                            createRuler(player, 8, count);
                            htmlid = "wpass06";
                        } else {
                            htmlid = "wpass03";
                        }
                    } else if (player.getInventory().checkItem(40916)) {
                        htmlid = "wpass08";
                    } else {
                        htmlid = "wpass05";
                    }
                    break;
                case 80061:
                    if (player.getKarmaLevel() >= 3) {
                        htmlid = "cpass03";
                    } else if (player.getInventory().checkItem(40921)) {
                        htmlid = "wpass02";
                    } else if (player.getInventory().checkItem(40918)) {
                        htmlid = "wpass11";
                    } else if (player.getInventory().checkItem(40909)
                            || player.getInventory().checkItem(40912)
                            || player.getInventory().checkItem(40911)) {
                        htmlid = "wpass04";
                    } else if (player.getInventory().checkItem(40910)) {
                        int count = getNecessarySealCount(player);
                        if (player.getInventory().checkItem(40914, count)) {
                            createRuler(player, 4, count);
                            htmlid = "wpass06";
                        } else {
                            htmlid = "wpass03";
                        }
                    } else if (player.getInventory().checkItem(40914)) {
                        htmlid = "wpass08";
                    } else {
                        htmlid = "wpass05";
                    }
                    break;
                case 80062:
                    if (player.getKarmaLevel() >= 3) {
                        htmlid = "cpass03";
                    } else if (player.getInventory().checkItem(40921)) {
                        htmlid = "wpass02";
                    } else if (player.getInventory().checkItem(40919)) {
                        htmlid = "wpass12";
                    } else if (player.getInventory().checkItem(40909)
                            || player.getInventory().checkItem(40912)
                            || player.getInventory().checkItem(40910)) {
                        htmlid = "wpass04";
                    } else if (player.getInventory().checkItem(40911)) {
                        int count = getNecessarySealCount(player);
                        if (player.getInventory().checkItem(40915, count)) {
                            createRuler(player, 2, count);
                            htmlid = "wpass06";
                        } else {
                            htmlid = "wpass03";
                        }
                    } else if (player.getInventory().checkItem(40915)) {
                        htmlid = "wpass08";
                    } else {
                        htmlid = "wpass05";
                    }
                    break;
                case 80064:
                    if (player.getKarmaLevel() < 1) {
                        htmlid = "meet003";
                    } else {
                        htmlid = "meet001";
                    }
                    break;
                case 80065:
                    if (player.getKarmaLevel() < 3) {
                        htmlid = "uturn0";
                    } else {
                        htmlid = "uturn1";
                    }
                    break;
                case 80066:
                    if (player.getKarma() >= 10000000) {
                        htmlid = "betray01";
                    } else {
                        htmlid = "betray02";
                    }
                    break;
                case 80067: // 첩보원(욕망의 동굴)
                    if (player.getQuest().get_step(L1Quest.QUEST_DESIRE) == L1Quest.QUEST_END) {
                        htmlid = "minicod10";
                    } else if (player.getKarmaLevel() >= 1) {
                        htmlid = "minicod07";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_DESIRE) == 1
                            && player.getGfxId().getTempCharGfx() == 6034) { // 코라프프리스트
                        // 변신
                        htmlid = "minicod03";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_DESIRE) == 1
                            && player.getGfxId().getTempCharGfx() != 6034) {
                        htmlid = "minicod05";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SHADOWS) == L1Quest.QUEST_END // 그림자의 신전측 퀘스트 종료
                            || player.getInventory().checkItem(41121) // 카헬의 지령서
                            || player.getInventory().checkItem(41122)) { // 카헬의 명령서
                        htmlid = "minicod01";
                    } else if (player.getInventory().checkItem(41130) // 핏자국의 지령서
                            && player.getInventory().checkItem(41131)) { // 핏자국의 명령서
                        htmlid = "minicod06";
                    } else if (player.getInventory().checkItem(41130)) { // 핏자국의 명령서
                        htmlid = "minicod02";
                    }
                    break;
                case 80071:
                    int earringLevel = 0;
                    if (player.getInventory().checkItem(21020)) {
                        earringLevel = 1;
                    } else if (player.getInventory().checkItem(21021)) {
                        earringLevel = 2;
                    } else if (player.getInventory().checkItem(21022)) {
                        earringLevel = 3;
                    } else if (player.getInventory().checkItem(21023)) {
                        earringLevel = 4;
                    } else if (player.getInventory().checkItem(21024)) {
                        earringLevel = 5;
                    } else if (player.getInventory().checkItem(21025)) {
                        earringLevel = 6;
                    } else if (player.getInventory().checkItem(21026)) {
                        earringLevel = 7;
                    } else if (player.getInventory().checkItem(21027)) {
                        earringLevel = 8;
                    }
                    if (player.getKarmaLevel() == 1) {
                        if (earringLevel >= 1) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring1";
                        }
                    } else if (player.getKarmaLevel() == 2) {
                        if (earringLevel >= 2) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring2";
                        }
                    } else if (player.getKarmaLevel() == 3) {
                        if (earringLevel >= 3) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring3";
                        }
                    } else if (player.getKarmaLevel() == 4) {
                        if (earringLevel >= 4) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring4";
                        }
                    } else if (player.getKarmaLevel() == 5) {
                        if (earringLevel >= 5) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring5";
                        }
                    } else if (player.getKarmaLevel() == 6) {
                        if (earringLevel >= 6) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring6";
                        }
                    } else if (player.getKarmaLevel() == 7) {
                        if (earringLevel >= 7) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring7";
                        }
                    } else if (player.getKarmaLevel() == 8) {
                        if (earringLevel >= 8) {
                            htmlid = "lringd";
                        } else {
                            htmlid = "lring8";
                        }
                    } else {
                        htmlid = "lring0";
                    }
                    break;
			/*case 80072:
				int karmaLevel1 = player.getKarmaLevel();
				switch (karmaLevel1) {
				case 1:
					htmlid = "lsmith0";
					break;
				case 2:
					htmlid = "lsmith1";
					break;
				case 3:
					htmlid = "lsmith2";
					break;
				case 4:
					htmlid = "lsmith3";
					break;
				case 5:
					htmlid = "lsmith4";
					break;
				case 6:
					htmlid = "lsmith5";
					break;
				case 7:
					htmlid = "lsmith7";
					break;
				case 8:
					htmlid = "lsmith8";
					break;
				default:
					htmlid = "";
					break;
				}
				break;*/
                case 80074:
                    if (player.getKarma() >= 10000000) {
                        htmlid = "infamous01";
                    } else {
                        htmlid = "infamous02";
                    }
                    break;

                case 80076: // 넘어진 항해사
                    if (player.getInventory().checkItem(41058)) { // 완성한 항해 일지
                        htmlid = "voyager8";
                    } else if (player.getInventory().checkItem(49082) // 미완성의 항해 일지
                            || player.getInventory().checkItem(49083)) { // 페이지를 추가하고 있지 않는 상태
                        if (player.getInventory().checkItem(41038) // 항해 일지 1 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 2 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 3 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 4 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 5 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 6 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 7 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 8 페이지
                                || player.getInventory().checkItem(41039) // 항해 일지 9 페이지
                                || player.getInventory().checkItem(41039)) { // 항해일지10 페이지
                            htmlid = "voyager9";
                        } else {
                            htmlid = "voyager7";
                        }// 잠만요
                    } else if (player.getInventory().checkItem(49082) // 미완성의 항해 일지
                            || player.getInventory().checkItem(49083)
                            || player.getInventory().checkItem(49084)
                            || player.getInventory().checkItem(49085)
                            || player.getInventory().checkItem(49086)
                            || player.getInventory().checkItem(49087)
                            || player.getInventory().checkItem(49088)
                            || player.getInventory().checkItem(49089)
                            || player.getInventory().checkItem(49090)
                            || player.getInventory().checkItem(49091)) { //페이지를 추가한 상태
                        htmlid = "voyager7";
                    }
                    break;
                case 80079:
                    if (player.getQuest().get_step(L1Quest.QUEST_KEPLISHA) == L1Quest.QUEST_END
                            && !player.getInventory().checkItem(41312)) {
                        htmlid = "keplisha6";
                    } else {
                        if (player.getInventory().checkItem(41314)) {
                            htmlid = "keplisha3";
                        } else if (player.getInventory().checkItem(41313)) {
                            htmlid = "keplisha2";
                        } else if (player.getInventory().checkItem(41312)) {
                            htmlid = "keplisha4";
                        }
                    }
                    break;
                case 80104:
                    if (!player.isCrown()) {
                        htmlid = "horseseller4";
                    }
                    break;
                /*
                 * case 81105: if (player.isWizard()) { int lv45_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL45); if (lv45_step >= 3) {
                 * htmlid = "stoenm3"; } else if (lv45_step >= 2) { htmlid =
                 * "stoenm2"; } else if (lv45_step >= 1) { htmlid = "stoenm1"; } }
                 * break;
                 */

                case 81155:
                    boolean hascastle7 = checkHasCastle(player,
                            L1CastleLocation.DIAD_CASTLE_ID);
                    if (hascastle7) {
                        if (checkClanLeader(player)) {
                            htmlid = "olle1";
                        } else {
                            htmlid = "olle6";
                            htmldata = new String[]{player.getName()};
                        }
                    } else {
                        htmlid = "olle7";
                    }
                    break;
                case 81200:
                    if (player.getInventory().checkItem(21069)
                            || player.getInventory().checkItem(21074)) {
                        htmlid = "c_belt";
                    }
                    break;
                case 81202: // 첩보원(그림자의 신전)
                    if (player.getQuest().get_step(L1Quest.QUEST_SHADOWS) == L1Quest.QUEST_END) {
                        htmlid = "minitos10";
                    } else if (player.getKarmaLevel() <= -1) {
                        htmlid = "minitos07";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SHADOWS) == 1
                            && player.getGfxId().getTempCharGfx() == 6035) { // 렛서데이몬
                        // 변신
                        htmlid = "minitos03";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_SHADOWS) == 1
                            && player.getGfxId().getTempCharGfx() != 6035) {
                        htmlid = "minitos05";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_DESIRE) == L1Quest.QUEST_END  // 욕망의 동굴측 퀘스트 종료
                            || player.getInventory().checkItem(41130) // 핏자국의 지령서
                            || player.getInventory().checkItem(41131)) { // 핏자국의 명령서
                        htmlid = "minitos01";
                    } else if (player.getInventory().checkItem(41121) // 카헬의 지령서
                            && player.getInventory().checkItem(41122)) { // 카헬의 명령서
                        htmlid = "minitos06";
                    } else if (player.getInventory().checkItem(41121)) { // 카헬의 명령서
                        htmlid = "minitos02";
                    }
                    break;
                case 81208: // 더러워진 브롭브
                    if (player.getInventory().checkItem(41129) // 핏자국의 정수
                            || player.getInventory().checkItem(41138)) { // 카헬의 정수
                        htmlid = "minibrob04";
                    } else if (player.getInventory().checkItem(41126) // 핏자국의 타락 한 정수
                            && player.getInventory().checkItem(41127) // 핏자국의 무력한 정수
                            && player.getInventory().checkItem(41128) // 핏자국의 아집인 정수
                            || player.getInventory().checkItem(41135) // 카헬의 타락 한 정수
                            && player.getInventory().checkItem(41136) // 카헬의 아집인 정수
                            && player.getInventory().checkItem(41137)) { // 카헬의 아집인정수
                        htmlid = "minibrob02";
                    }
                    break;
                // 경험치 지급단
                case 4200008:
                    if (player.getLevel() >= 71) {
                        htmlid = "expgive3";
                    }
                    break;
			/*case 4200018:
				if (player.getLevel() >= 75) {
					htmlid = "expgive3";
				}
				break;
				*/


                /** 생일 시스템 by이러버엉 **/
                // 야메 시스템 ㅋ 이런방식인것만...
                case 450001797:
                    HappyBrithday(player);
                    break;
                /** 생일 시스템 by이러버엉 **/
                // 야메 시스템 ㅋ 이런방식인것만...
                case 4200009: // 보석세공사 데이빗(얼녀귀걸이)
                    /*
                     * 보석이 있는지 최상위 전 귀걸이 있는지 순차적으로내리고
                     */
                    if (checkItem(player, 49031)) {
                        if (checkItem(player, 21088)) { // 얼녀귀걸이 7
                            htmlid = "gemout8";
                        } else if (checkItem(player, 21087)) { // 얼녀귀걸이 6
                            htmlid = "gemout7";
                        } else if (checkItem(player, 21086)) { // 얼녀귀걸이 5
                            htmlid = "gemout6";
                        } else if (checkItem(player, 21085)) { // 얼녀귀걸이 4
                            htmlid = "gemout5";
                        } else if (checkItem(player, 21084)) { // 얼녀귀걸이 3
                            htmlid = "gemout4";
                        } else if (checkItem(player, 21083)) { // 얼녀귀걸이 2
                            htmlid = "gemout3";
                        } else if (checkItem(player, 21082)) { // 얼녀귀걸이 1
                            htmlid = "gemout2";
                        } else if (checkItem(player, 21081)) { // 얼녀귀걸이 0
                            htmlid = "gemout1";
                        } else { // 보석만 가지고있다.
                            htmlid = "gemout17";
                        }
                    }
                    /*
                     * if (checkItem(player, 49031)) { if (checkItem(player,
                     * 21081)){ //얼녀귀걸이 1 htmlid = "gemout1"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 1){ htmlid = "gemout2"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 2){ htmlid = "gemout3"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 3){ htmlid = "gemout4"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 4){ htmlid = "gemout5"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 5){ htmlid = "gemout6"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 6){ htmlid = "gemout7"; } else if
                     * (player.getQuest().get_step(L1Quest.QUEST_ICEQUEENRING) ==
                     * 7){ htmlid = "gemout8"; } else { // 보석만 가지고있다. htmlid =
                     * "gemout17"; } }
                     */
                    break;
                case 4201000: // 환술사 아샤
                    if (player.isIllusionist())
                        htmlid = "asha1";
                    else
                        htmlid = "asha2";
                    break;
                case 4202000: // 용기사 피에나
                    if (player.isDragonknight())
                        htmlid = "feaena1";
                    else
                        htmlid = "feaena2";
                    break;
                case 4204000: // 요정 달장퀘스트 로빈후드
                    if (!player.isElf()) {
                        // int MOONBOW_step = quest.get_step(L1Quest.QUEST_MOONBOW);
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 0) {
                        htmlid = "robinhood1";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 1) {
                        htmlid = "robinhood8";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 2) {
                        htmlid = "robinhood13";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 6) {
                        htmlid = "robinhood9";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 7) {
                        htmlid = "robinhood11";
                    } else {
                        htmlid = "robinhood3";
                    }
                    break;
                /*
                 * case 4205000: // 기사30퀘 던전 입장 문지기 if (player.isKnight()){ if
                 * (player.getLevel() >= 30){ int q30step =
                 * quest.get_step(L1Quest.QUEST_LEVEL30); if (q30step <= 2) { htmlid
                 * = "dgkeeperk4"; } else if (q30step == 3 && player.getArmor() !=
                 * null){ htmlid = "dgkeeperk3"; } else if (q30step == 3 &&
                 * player.getArmor() == null) { htmlid = "dgkeeperk1"; } else if
                 * (q30step >= 4 || q30step == L1Quest.QUEST_END) { htmlid =
                 * "dgkeeperk5"; } } }else{ htmlid = "dgkeepero1"; } break;
                 */
                case 4210000:
                    if (!player.isElf()) {
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 2) {
                        htmlid = "zybril1";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 3) {
                        htmlid = "zybril7";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 4) {
                        htmlid = "zybril8";
                    } else if (player.getQuest().get_step(L1Quest.QUEST_MOONBOW) == 5) {
                        htmlid = "zybril18";
                    } else {
                        htmlid = "zybril16";
                    }
                    break;
                /******************************* 추가 *******************************/
                /******************************* 시즌3 이후 추가 *******************************/
                // 베히모스 엘라스
                case 4218002:
                    if (!player.isDragonknight()) {
						htmlid = "elas3";
					}
                    break;
                /*
                 * case 4218003: // 장로 프로켈 if (player.isDragonknight()) { if
                 * (player.getLevel() >= 15) { int lv15_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL15); if(lv15_step == 1){ htmlid
                 * = "prokel4"; }else if (lv15_step == 2 || lv15_step ==
                 * L1Quest.QUEST_END) { htmlid = "prokel7"; } else { htmlid =
                 * "prokel2"; } } else { htmlid = "prokel1"; //레벨 15이하 } } break;
                 */
                // 베히모스 탈리온
                case 4218004:
                    if (!player.isDragonknight()) {
						htmlid = "talrion4";
					}
                    break;
                /*
                 * case 4219004: // 장로 실레인 if (player.isIllusionist()) { if
                 * (player.getLevel() >= 15) { int lv15_step =
                 * quest.get_step(L1Quest.QUEST_LEVEL15); if(lv15_step == 1){ htmlid
                 * = "silrein4"; }else if (lv15_step == 2 || lv15_step ==
                 * L1Quest.QUEST_END) { htmlid = "silrein5"; } else { htmlid =
                 * "silrein2"; } } else { htmlid = "prokel1"; //레벨 15이하 } } break;
                 */
                /***************************** 에피소드 2 New System *****************************/
                // 숨겨진 용들의 땅 입구(노랑포탈)
                case 4212013:
                    // if (안타레이드 성공후 오픈상태체크){
                    if (player.getLevel() >= 30 && player.getLevel() <= 51) {
                        htmlid = "dsecret2";
                    } else if (player.getLevel() > 51) {
                        htmlid = "dsecret1";
                    } else {
                        htmlid = "dsecret3";
                    }
                    // } else {
                    // htmlid = "dsecret3";
                    // }
                    break;
                // 마야의 그림자
                case 6000015:
                    if (player.getInventory().checkItem(41158)) {
                        htmlid = "adenshadow1";
                    } else {
                        htmlid = "adenshadow2";
                    }
                    break;
                // 수상한 요리사
                case 4220012:
                    if (player.getInventory().checkItem(437027, 1)
                            || player.getInventory().checkItem(437028, 1)
                            || player.getInventory().checkItem(437029, 1)
                            || player.getInventory().checkItem(437030, 1)) {
                        htmlid = "suschef2";
                    }/*
                     * else { htmlid = "suschef1"; }
                     */
                    break;
                case 4206003:
                    player.c_dm = 0;
                    player.c_gh = 0;
                    player.c_pr = 0;
                    player.c_pm = 0;
                    player.c_md = 0;
                    break;
                case 7000044:
                    if (player.getLevel() < 30) {
                        htmlid = "rabbita2";
                    }
                    break;
                case 100221:// 숨계 마법전수자 페이하스
                    if (player.isWizard() && player.getLevel() >= 4) {
                    } else if (player.isElf() && player.getLevel() >= 8) {
                    } else if (player.isCrown() && player.getLevel() >= 10) {
                    } else if (player.isDarkelf() && player.getLevel() >= 12) {
                    } else if (player.isKnight()) {
						htmlid = "sirissnw";
					} else if (player.isDragonknight() || player.isIllusionist()) {
						htmlid = "sirissdk";
					} else {
						htmlid = "sirissnt"; // 레벨 부족
					}
                    // siriss1 다알고있음
                    break;
                case 100415: // 지그프리드
                    if (player.getLevel() < 70)
                        htmlid = "zigpride2";
                    break;
                default:
                    break;
            }

            // html 표시 패킷 송신
            if (htmlid != null) { // htmlid가 지정되고 있는 경우
                if (htmldata != null) { // html 지정이 있는 경우는 표시
                    S_NPCTalkReturn nt = new S_NPCTalkReturn(objid, htmlid, htmldata);
                    player.sendPackets(nt, true);
                } else {
                    S_NPCTalkReturn nt = new S_NPCTalkReturn(objid, htmlid);
                    player.sendPackets(nt, true);
                }
            } else {
                if (player.getLawful() < -1000) { // 플레이어가 카오틱
                    // 바포 시스템으로 성향 제한 없음
                    // player.sendPackets(new S_NPCTalkReturn(talking, objid,
                    // 2));
                    S_NPCTalkReturn nt = new S_NPCTalkReturn(talking, objid, 1);
                    player.sendPackets(nt, true);
                } else {
                    S_NPCTalkReturn nt = new S_NPCTalkReturn(talking, objid, 1);
                    player.sendPackets(nt, true);
                }
            }
        } else { // npcaction 테이블에 NPC 없을때
            if (npcid == 100219) {// 수련장 관리인
                if (player.getLevel() < 2) {
                    player.sendPackets(new S_NPCTalkReturn(objid, "adminrw3"));
                } else if (player.getLevel() < 3) {
                    player.setExp(ExpTable.getExpByLevel(3));
                } else if (player.getLevel() < 5) {
                    player.sendPackets(new S_NPCTalkReturn(objid, "adminrw1"));
                } else if (player.getLevel() < 6) {
                    player.setExp(ExpTable.getExpByLevel(6));
                    player.sendPackets(new S_NPCTalkReturn(objid, "newadmin2"));
                } else {
                    player.sendPackets(new S_NPCTalkReturn(objid, "newadmin2"));
                }
            } else if (npcid == 100403)  {
				// 명법군이동
				L1Teleport.teleport(player, 32757, 32870, (short) 471, player.getMoveState().getHeading(), true, true, 5000);
			} else if (npcid == 100404) {
				// 마령군이동
				L1Teleport.teleport(player, 32664, 32862, (short) 461, player.getMoveState().getHeading(), true, true, 5000);
			} else if (npcid == 100405) {
            	// 마수군이동
				L1Teleport.teleport(player, 32727, 32824, (short) 452, player.getMoveState().getHeading(), true, true, 5000);
			} else if (npcid == 100406) {
            	// 암살군이동
				L1Teleport.teleport(player, 32772, 32790, (short) 495, player.getMoveState().getHeading(), true, true, 5000);
			} else if (npcid == 100669) {
                if (player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BEGINNER_HELP_CLICK_DAILY)) {
                    player.sendPackets(new S_SystemMessage(player.getSkillEffectTimerSet().getSkillEffectTimeSec(L1SkillId.BEGINNER_HELP_CLICK_DAILY) + "초 뒤에 사용 가능합니다."), true);
                    return;
                }

                player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.BEGINNER_HELP_CLICK_DAILY, 5000);
                int[] allBuffSkill = {L1SkillId.PHYSICAL_ENCHANT_STR, L1SkillId.PHYSICAL_ENCHANT_DEX, L1SkillId.BLESS_WEAPON};
                player.setBuffnoch(1); // 추가적으로 버프는 미작동
                L1SkillUse l1skilluse = new L1SkillUse();
                for (int element : allBuffSkill) {
                    l1skilluse.handleCommands(player, element, player.getId(), player.getX(), player.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
                }
            }
        }

        htmlid = null;
        htmldata = null;

    }


    private static String talkToTownadviser(L1PcInstance pc, int town_id) {
        String htmlid;
        if (pc.getHomeTownId() == town_id && TownTable.getInstance().isLeader(pc, town_id)) {
            htmlid = "secretary1";
        } else {
            htmlid = "secretary2";
        }

        return htmlid;
    }

    private static String talkToTownmaster(L1PcInstance pc, int town_id) {
        String htmlid;
        if (pc.getHomeTownId() == town_id) {
            htmlid = "hometown";
        } else {
            htmlid = "othertown";
        }
        return htmlid;
    }

    @Override
    public void onFinalAction(L1PcInstance player, String action) {
    }

    public void doFinalAction(L1PcInstance player) {
    }

    private boolean checkItem(L1PcInstance player, int itemid) {
        return player.getInventory().checkItem(itemid);
    }

    private boolean checkHasCastle(L1PcInstance player, int castle_id) {
        if (player.getClanid() != 0) {
            L1Clan clan = L1World.getInstance().getClan(player.getClanname());
            if (clan != null) {
                if (clan.getCastleId() == castle_id) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkClanLeader(L1PcInstance player) {
        if (player.isCrown()) {
            L1Clan clan = L1World.getInstance().getClan(player.getClanname());
            if (clan != null) {
                if (player.getId() == clan.getLeaderId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getNecessarySealCount(L1PcInstance pc) {
        int rulerCount = 0;
        int necessarySealCount = 10;
        if (pc.getInventory().checkItem(40917)) {
            rulerCount++;
        }
        if (pc.getInventory().checkItem(40920)) {
            rulerCount++;
        }
        if (pc.getInventory().checkItem(40918)) {
            rulerCount++;
        }
        if (pc.getInventory().checkItem(40919)) {
            rulerCount++;
        }
        if (rulerCount == 0) {
            necessarySealCount = 10;
        } else if (rulerCount == 1) {
            necessarySealCount = 100;
        } else if (rulerCount == 2) {
            necessarySealCount = 200;
        } else if (rulerCount == 3) {
            necessarySealCount = 500;
        }
        return necessarySealCount;
    }


    private void HappyBrithday(L1PcInstance pc) {
        if (pc.getInventory().checkItem(5000123, 1)) {
            S_SystemMessage sm = new S_SystemMessage("이미 선물을 받으셨습니다.");
            pc.sendPackets(sm, true);

        } else {
            pc.getInventory().checkItem(5000123, 0);
            pc.getInventory().storeItem(5000123, 1);
            pc.getInventory().storeItem(5000124, 1);
            pc.getInventory().storeItem(5000136, 1);
            L1SkillUse su = new L1SkillUse();
            su.handleCommands(pc, L1SkillId.STATUS_COMA_5, pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_SPELLSC);
            su = null;
            S_SystemMessage sm = new S_SystemMessage("생일 선물을 받았습니다.");
            pc.sendPackets(sm, true);
            return;
        }
    }

    /**
     * 생일 시스템 by이러버엉
     **/
    // 야메 시스템 ㅋ 이런방식인것만...
    private void createRuler(L1PcInstance pc, int attr, int sealCount) {
        int rulerId = 0;
        int protectionId = 0;
        int sealId = 0;
        if (attr == 1) {
            rulerId = 40917;
            protectionId = 40909;
            sealId = 40913;
        } else if (attr == 2) {
            rulerId = 40919;
            protectionId = 40911;
            sealId = 40915;
        } else if (attr == 4) {
            rulerId = 40918;
            protectionId = 40910;
            sealId = 40914;
        } else if (attr == 8) {
            rulerId = 40920;
            protectionId = 40912;
            sealId = 40916;
        }
        pc.getInventory().consumeItem(protectionId, 1);
        pc.getInventory().consumeItem(sealId, sealCount);
        L1ItemInstance item = pc.getInventory().storeItem(rulerId, 1);
        if (item != null) {
            S_ServerMessage sm = new S_ServerMessage(143, getNpcTemplate().get_name(), item.getLogName());
            pc.sendPackets(sm, true);
        }
    }

    private String talkToSecondtbox(L1PcInstance pc) {
        String htmlid = "";
        if (pc.getQuest().get_step(L1Quest.QUEST_TBOX1) == L1Quest.QUEST_END) {
            if (pc.getInventory().checkItem(40701)) {
                htmlid = "maptboxa";
            } else {
                htmlid = "maptbox0";
            }
        } else {
            htmlid = "maptbox0";
        }
        return htmlid;
    }

    private String talkToThirdtbox(L1PcInstance pc) {
        String htmlid = "";
        if (pc.getQuest().get_step(L1Quest.QUEST_TBOX2) == L1Quest.QUEST_END) {
            if (pc.getInventory().checkItem(40701)) {
                htmlid = "maptboxd";
            } else {
                htmlid = "maptbox0";
            }
        } else {
            htmlid = "maptbox0";
        }
        return htmlid;
    }

    private static final long REST_MILLISEC = 10000;

    private static final Timer _restTimer = new Timer(true);

    private RestMonitor _monitor;

    public class RestMonitor extends TimerTask {
        @Override
        public void run() {
            setRest(false);
        }
    }

    private static final int[] ShootCount = {6, 13, 1, 11};
    private static final int SleepTime[] = {20000, 3000, 2000, 20000};

    public class AnonAction implements Runnable {
        // 6 (20초딜) 13 (3초딜) 1(2초딜) 11 (20초)
        private int i = 0;
        private int a = 0;

        @Override
        public void run() {
            try {

                i++;

                for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(L1MerchantInstance.this)) {
                    if (pc == null || pc.getNetConnection() == null) {
						continue;
					}

                    pc.sendPackets(new S_DoActionGFX(L1MerchantInstance.this.getId(), 30));
                }

                if (ShootCount[a] <= i) {
                    GeneralThreadPool.getInstance().schedule(this, SleepTime[a]);
                    a++;
                    i = 0;
                    if (a >= 4) {
						a = 0;
					}
                } else {
					GeneralThreadPool.getInstance().schedule(this, 2000);
				}
                /*
                 * while(true){ try{ for(int a = 0; a < ShootCount.length; a++){
                 * for(int i = 0; i < ShootCount[a]; i++){ for(L1PcInstance pc :
                 * L1World
                 * .getInstance().getVisiblePlayer(L1MerchantInstance.this)){
                 * if(pc == null || pc.getNetConnection() == null) continue;
                 * pc.sendPackets(new
                 * S_DoActionGFX(L1MerchantInstance.this.getId(), 30)); }
                 * Thread.sleep(2000L); } if(a == 0 || a == 3)
                 * Thread.sleep(20000L); else if(a == 1) Thread.sleep(3000L);
                 * else if(a == 2) Thread.sleep(2000L); } }catch(Exception e){}
                 * }
                 */
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public class WoodSoldierMotion implements Runnable {
        private int targetX = 0;
        private int targetY = 0;
        private Random _rnd = new Random(System.currentTimeMillis());

        @Override
        public void run() {
            int rnd = _rnd.nextInt(130);
            int sleeptime = getNpcTemplate().get_atkspeed();
            int x = L1MerchantInstance.this.getX();
            int y = L1MerchantInstance.this.getY();
            if (getNpcId() == 100547 || getNpcId() == 100549) { // 검, 창
                if (x == 32601 || x == 32615 || x == 32617) {
					Broadcaster.broadcastPacket(L1MerchantInstance.this, new S_AttackPacket(L1MerchantInstance.this, 0, ActionCodes.ACTION_Attack), true);
				} else {
					return;
				}
            } else if (getNpcId() == 100548) { // 활
                if ((x == 32601 && y == 33220) || (x == 32598 && y == 33220) || (x == 32595 && y == 33220)) {
					Broadcaster.broadcastPacket(L1MerchantInstance.this, new S_UseArrowSkill(L1MerchantInstance.this, 0, 66, x, x == 32595 ? y - 6 : y - 7, false), true);
				} else {
					return;
				}
            } else if (getNpcId() == 100550) { // 감독관
                sleeptime = getNpcTemplate().get_passispeed();
                if (getHomeX() == 32595 && getHomeY() == 33224) {
                    if (x == 32595 && y == 33224) {
                        targetX = 32601;
                        targetY = 33224;
                    } else if (x == 32601 && y == 33224) {
                        targetX = 32595;
                        targetY = 33224;
                    }
                } else if (getHomeX() == 32614 && getHomeY() == 33227) {
                    if (x == 32614 && y == 33227) {
                        targetX = 32614;
                        targetY = 33237;
                    } else if (x == 32614 && y == 33237) {
                        targetX = 32614;
                        targetY = 33227;
                    }
                }
                if (targetX != 0 && targetY != 0) {
                    int dir = moveDirection(L1MerchantInstance.this.getMapId(),
                            targetX, targetY);
                    if (dir != -1) {
						setDirectionMove(dir);
					}
                } else {
					return;
				}
            }

            GeneralThreadPool.getInstance().schedule(this, sleeptime + rnd);
        }

    }

    private static FastTable<oneDayBuyCheck> buyChecklist = new FastTable<>();

    private static boolean OneDaybuyNpc(int npcid, int itemid) {
        if (npcid == 70017123 || (npcid == 80080123 && itemid == 60327123)) {
			return true;
		}
        return false;
    }

    public static boolean oneDayBuyAdd(String account, int npcid, int itemid) {
        if (!OneDaybuyNpc(npcid, itemid)) {
			return false;
		}

        synchronized (buyChecklist) {
            for (oneDayBuyCheck db : buyChecklist) {
                if (db.account.equalsIgnoreCase(account) && db.npcid == npcid && db.itemid == itemid) {
                    return true;
                }
            }
            buyChecklist.add(new oneDayBuyCheck(account, npcid, itemid));
            return false;
        }
    }

    public static boolean getOneDayBuy(String account, int npcid, int itemid) {
        if (!OneDaybuyNpc(npcid, itemid)) {
			return false;
		}

        synchronized (buyChecklist) {
            for (oneDayBuyCheck db : buyChecklist) {
                if (db.account.equalsIgnoreCase(account) && db.npcid == npcid && db.itemid == itemid) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void resetOneDayBuy() {
        synchronized (buyChecklist) {
            buyChecklist.clear();
        }
    }

    /**
     * 생일 시스템 by이러버엉
     **/
    //배틀존
    private void DuelZone(L1PcInstance pc) {
        // 배틀존이 열려 있고 , 입장이 가능하다면
        if (BattleZone.getInstance().getDuelOpen()) {
            if (pc.get_DuelLine() != 0 || BattleZone.getInstance().isBattleZoneUser(pc)) {
                pc.sendPackets(new S_SystemMessage("배틀존에서 나왔다가 다시 들어갈 수 없습니다."));
                return;
            }
            if (BattleZone.getInstance().getBattleZoneUserCount() > 50) {
                pc.sendPackets(new S_SystemMessage("프리미엄 배틀존의 인원이 모두 찼습니다."));
                return;
            }
            if (pc.isInParty()) {
                pc.sendPackets(new S_SystemMessage("파티중에는 프리미엄 배틀존 입장이 불가능합니다."));
                return;
            }
            // 라인을 나누자..
            if (BattleZone.getInstance().getBattleZoneUserCount() % 2 == 0) {
                // 짝수라인
                pc.set_DuelLine(2);
            } else {
                // 홀수라인
                pc.set_DuelLine(1);
            }
            pc.sendPackets(new S_SystemMessage("프리미엄 배틀존 대기실로 입장하셨습니다."));
            BattleZone.getInstance().addBattleZoneUser(pc);
            Random random = new Random();
            int i13 = 32770 + random.nextInt(5);
            int k19 = 32771 + random.nextInt(5);
            L1Teleport.teleport(pc, i13, k19, (short) 5001, 4, true);


        } else {
            pc.sendPackets(new S_SystemMessage("현재 프리미엄 배틀존이 열리지 않았습니다."));
        }
    }

    public boolean 공성병기딜레이 = false;

    public void 공성병기투척딜레이() {
        if (공성병기딜레이) {
			return;
		}

        공성병기딜레이 = true;
        GeneralThreadPool.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                공성병기딜레이 = false;
            }
        }, 10000);
    }

}

