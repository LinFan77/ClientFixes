package l1j.server.server.model.item.function;

import java.util.Random;

import l1j.server.Config;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class LeafItem {

	private static Random _random = new Random(System.nanoTime());

	public static void clickItem(L1PcInstance pc, int itemId,
			L1ItemInstance l1iteminstance, L1ItemInstance l1iteminstance1) {

		switch (itemId) {
		case 31086: {// 생명의나뭇잎
			int targetItem = l1iteminstance1.getItemId();
			int[] item = new int[] { 31086 };// 필요한재료
			int[] 금빛날개 = new int[] { 20049 }; // 거대 여왕 금빛 날개
			int[] 은빛날개 = new int[] { 20050 }; // 거대 여왕 은빛 날개
			int[] 극한체인소드 = new int[] { 6000 }; // 극한의 체인소드
			int[] 기백반지 = new int[] { 420009 }; // 기백의 반지
			int[] 카배 = new int[] { 41148 }; // 기술서(카운터 배리어)
			int[] 나발 = new int[] { 59 }; // 나이트발드의 양손검
			int[] 냉키 = new int[] { 6001 }; // 냉한의 키링크
			int[] 데지 = new int[] { 119 }; // 데몬의 지팡이
			int[] 데스갑옷 = new int[] { 20100 }; // 데스나이트의 갑옷
			int[] 데스부츠 = new int[] { 20198 }; // 데스나이트의 부츠
			int[] 데불 = new int[] { 58 }; // 데스나이트의 불검
			int[] 데스장갑 = new int[] { 20166 }; // 데스나이트의 장갑
			int[] 데스투구 = new int[] { 20010 }; // 데스나이트의 투구
			int[] 도펠목 = new int[] { 20250 }; // 도펠겡어 보스의 목걸이
			int[] 도펠오른 = new int[] { 20277 }; // 도펠겡어 보스의 오른쪽 반지
			int[] 도펠왼쪽 = new int[] { 20278 }; // 도펠겡어 보스의 왼쪽 반지
			int[] 돌장 = new int[] { 30219 }; // 돌 장갑
			int[] 라이아반지 = new int[] { 20279 }; // 라이아의 반지
			int[] 론드이도류 = new int[] { 76 }; // 론드의 이도류
			int[] 리치로브 = new int[] { 20107 }; // 리치 로브
			int[] 마나지팡 = new int[] { 126 }; // 마나의 지팡이
			int[] 마법각반 = new int[] { 500214 }; // 마법 방어 각반
			int[] 디스 = new int[] { 40222 }; // 마법서(디스인티그레이트)
			int[] 미티어 = new int[] { 40219 }; // 마법서(미티어 스트라이크)
			int[] 마왕반지 = new int[] { 420008 }; // 마왕의 반지
			int[] 머미왕관 = new int[] { 20017 }; // 머미로드의 왕관
			int[] 메르모자 = new int[] { 20018 }; // 메르키오르의 모자
			int[] 민첩반지 = new int[] { 7215 }; // 민첩의 반지
			int[] 바지 = new int[] { 124 }; // 바포메트의 지팡이
			int[] 반역자 = new int[] { 21093 }; // 반역자의 방패
			int[] 발터모자 = new int[] { 20025 }; // 발터자르의 모자
			int[] 뱀망 = new int[] { 20079 }; // 뱀파이어의 망토
			int[] 오만10 = new int[] { 40288 }; // 봉인된 오만의 탑 10층 이동 부적
			int[] 오만1 = new int[] { 60201 }; // 봉인된 오만의 탑 1층 이동 부적
			int[] 오만2 = new int[] { 40280 }; // 봉인된 오만의 탑 2층 이동 부적
			int[] 오만3 = new int[] { 40281 }; // 봉인된 오만의 탑 3층 이동 부적
			int[] 오만4 = new int[] { 40282 }; // 봉인된 오만의 탑 4층 이동 부적
			int[] 오만5 = new int[] { 40283 }; // 봉인된 오만의 탑 5층 이동 부적
			int[] 오만6 = new int[] { 40284 }; // 봉인된 오만의 탑 6층 이동 부적
			int[] 오만7 = new int[] { 40285 }; // 봉인된 오만의 탑 7층 이동 부적
			int[] 오만8 = new int[] { 40286 }; // 봉인된 오만의 탑 8층 이동 부적
			int[] 오만9 = new int[] { 40287 }; // 봉인된 오만의 탑 9층 이동 부적
			int[] 블서 = new int[] { 262 }; // 기운을 잃은 블러드서커
			int[] 빛목 = new int[] { 21268 }; // 빛나는 사이하의 목걸이
			int[] 빛반 = new int[] { 21267 }; // 빛나는 사이하의 반지
			int[] 세모 = new int[] { 20029 }; // 세마의 모자
			int[] 세반 = new int[] { 21168 }; // 세마의 반지
			int[] 심안 = new int[] { 22009 }; // 시어의 심안
			int[] 실티 = new int[] { 900019 }; // 실프의 티셔츠
			int[] 얼지 = new int[] { 121 }; // 얼음 여왕의 지팡이
			int[] 에반 = new int[] { 20314 }; // 에이션트 자이언트의 반지
			int[] 오단 = new int[] { 9 }; // 오리하루콘 단검
			int[] 오림목 = new int[] { 21167 }; // 오림의 목걸이
			int[] 완력반 = new int[] { 7214 }; // 완력의 반지
			int[] 용심 = new int[] { 40466 }; // 용의 심장
			int[] 데페 = new int[] { 7310 }; // 전사의 인장(데스페라도)
			int[] 타이탄락 = new int[] { 7304 }; // 전사의 인장(타이탄 락)
			int[] 타이탄매직 = new int[] { 7306 }; // 전사의 인장(타이탄 매직)
			int[] 타이탄블릿 = new int[] { 7305 }; // 전사의 인장(타이탄 블릿)
			int[] 소프 = new int[] { 41149 }; // 정령의 수정(소울 오브 프레임)
			int[] 게일 = new int[] { 41153 }; // 정령의 수정(스트라이크 게일)
			int[] 어바 = new int[] { 40249 }; // 정령의 수정(어스 바인드)
			int[] 워터 = new int[] { 41152 }; // 정령의 수정(폴루트 워터)
			int[] 제니스반 = new int[] { 20298 }; // 제니스의 반지
			int[] 지식반 = new int[] { 30229 }; // 지식의 반지
			int[] 지휘관 = new int[] { 21122 }; // 지휘관의 투구
			int[] 카스파모 = new int[] { 20040 }; // 카스파의 모자
			int[] 커츠갑 = new int[] { 20150 }; // 커츠의 갑옷
			int[] 커검 = new int[] { 54 }; // 커츠의 검
			int[] 커츠부 = new int[] { 20214 }; // 커츠의 부츠
			int[] 커츠장 = new int[] { 20184 }; // 커츠의 장갑
			int[] 커츠투 = new int[] { 20041 }; // 커츠의 투구
			int[] 크로벨 = new int[] { 20614 }; // 크로노스의 벨트
			int[] 타락부 = new int[] { 20216 }; // 타락의 부츠
			int[] 타락장 = new int[] { 20186 }; // 타락의 장갑
			int[] 타벨 = new int[] { 20320 }; // 타이탄의 벨트
			int[] 투망 = new int[] { 20077 }; // 투명 망토
			int[] 투사목 = new int[] { 21258 }; // 투사의 목걸이
			int[] 파장 = new int[] { 30220 }; // 파괴의 장궁
			int[] 현목 = new int[] { 21260 }; // 현자의 목걸이
			int[] 혼투 = new int[] { 20048 }; // 혼돈의 투구
			int[] 흑장로브 = new int[] { 20160 }; // 흑장로의 로브
			int[] 흑장샌달 = new int[] { 20218 }; // 흑장로의 샌달
			int[] 아머브 = new int[] { 60199 }; // 흑정령의 수정(아머 브레이크)
			int[] 힘지 = new int[] { 131 }; // 힘의 지팡이
			int[] 발라숨결 = new int[] { 40354 }; // 발라숨결
			int[] 신마투 = new int[] { 220011 }; // 신성한마법방어투구
			int[] temp = null;

			switch (targetItem) {
			case 56001: // 기운을 잃은 거대 여왕 금빛 날개
				temp = 금빛날개;
				break;
			case 56002: // 기운을 잃은 거대 여왕 은빛 날개
				temp = 은빛날개;
				break;
			case 56003: // 기운을 잃은 극한의 체인소드
				temp = 극한체인소드;
				break;
			case 56004: // 기운을 잃은 기백의 반지
				temp = 기백반지;
				break;
			case 56005: // 기운을 잃은 기술서(카운터 배리어)
				temp = 카배;
				break;
			case 56006: // 기운을 잃은 나이트발드의 양손검
				temp = 나발;
				break;
			case 56007: // 기운을 잃은 냉한의 키링크
				temp = 냉키;
				break;
			case 56008: // 기운을 잃은 데몬의 지팡이
				temp = 데지;
				break;
			case 56009: // 기운을 잃은 데스나이트의 갑옷
				temp = 데스갑옷;
				break;
			case 56010: // 기운을 잃은 데스나이트의 부츠
				temp = 데스부츠;
				break;
			case 56011: // 기운을 잃은 데스나이트의 불검
				temp = 데불;
				break;
			case 56012: // 기운을 잃은 데스나이트의 장갑
				temp = 데스장갑;
				break;
			case 56013: // 기운을 잃은 데스나이트의 투구
				temp = 데스투구;
				break;
			case 56014: // 기운을 잃은 도펠겡어 보스의 목걸이
				temp = 도펠목;
				break;
			case 56015: // 기운을 잃은 도펠겡어 보스의 오른쪽 반지
				temp = 도펠오른;
				break;
			case 56016: // 기운을 잃은 도펠겡어 보스의 왼쪽 반지
				temp = 도펠왼쪽;
				break;
			case 56017: // 기운을 잃은 돌 장갑
				temp = 돌장;
				break;
			case 56018: // 기운을 잃은 라이아의 반지
				temp = 라이아반지;
				break;
			case 56019: // 기운을 잃은 론드의 이도류
				temp = 론드이도류;
				break;
			case 56020: // 기운을 잃은 리치 로브
				temp = 리치로브;
				break;
			case 56021: // 기운을 잃은 마나의 지팡이
				temp = 마나지팡;
				break;
			case 56084: // 기운을 잃은 마법 방어 각반
				temp = 마법각반;
				break;
			case 56022: // 기운을 잃은 마법서(디스인티그레이트)
				temp = 디스;
				break;
			case 56000: // 기운을 잃은 마법서(미티어 스트라이크)
				temp = 미티어;
				break;
			case 56023: // 기운을 잃은 마왕의 반지
				temp = 마왕반지;
				break;
			case 56024: // 기운을 잃은 머미로드의 왕관
				temp = 머미왕관;
				break;
			case 56025: // 기운을 잃은 메르키오르의 모자
				temp = 메르모자;
				break;
			case 56026: // 기운을 잃은 민첩의 반지
				temp = 민첩반지;
				break;
			case 56027: // 기운을 잃은 바포메트의 지팡이
				temp = 바지;
				break;
			case 56028: // 기운을 잃은 반역자의 방패
				temp = 반역자;
				break;
			case 56029: // 기운을 잃은 발터자르의 모자
				temp = 발터모자;
				break;
			case 56030: // 기운을 잃은 뱀파이어의 망토
				temp = 뱀망;
				break;
			case 56031: // 기운을 잃은 봉인된 오만의 탑 10층 이동 부적
				temp = 오만10;
				break;
			case 56032: // 기운을 잃은 봉인된 오만의 탑 1층 이동 부적
				temp = 오만1;
				break;
			case 56033: // 기운을 잃은 봉인된 오만의 탑 2층 이동 부적
				temp = 오만2;
				break;
			case 56034: // 기운을 잃은 봉인된 오만의 탑 3층 이동 부적
				temp = 오만3;
				break;
			case 56035: // 기운을 잃은 봉인된 오만의 탑 4층 이동 부적
				temp = 오만4;
				break;
			case 56036: // 기운을 잃은 봉인된 오만의 탑 5층 이동 부적
				temp = 오만5;
				break;
			case 56037: // 기운을 잃은 봉인된 오만의 탑 6층 이동 부적
				temp = 오만6;
				break;
			case 56038: // 기운을 잃은 봉인된 오만의 탑 7층 이동 부적
				temp = 오만7;
				break;
			case 56039: // 기운을 잃은 봉인된 오만의 탑 8층 이동 부적
				temp = 오만8;
				break;
			case 56040: // 기운을 잃은 봉인된 오만의 탑 9층 이동 부적
				temp = 오만9;
				break;
			case 56041: // 기운을 잃은 블러드서커
				temp = 블서;
				break;
			case 56042: // 기운을 잃은 빛나는 사이하의 목걸이
				temp = 빛목;
				break;
			case 56043: // 기운을 잃은 빛나는 사이하의 반지
				temp = 빛반;
				break;
			case 56044: // 기운을 잃은 세마의 모자
				temp = 세모;
				break;
			case 56045: // 기운을 잃은 세마의 반지
				temp = 세반;
				break;
			case 56046: // 기운을 잃은 시어의 심안
				temp = 심안;
				break;
			case 56088: // 기운을 잃은 실프의 티셔츠 ㅇㅇ
				temp = 실티;
				break;
			case 56047: // 기운을 잃은 얼음 여왕의 지팡이
				temp = 얼지;
				break;
			case 56048: // 기운을 잃은 에이션트 자이언트의 반지
				temp = 에반;
				break;
			case 56049: // 기운을 잃은 오리하루콘 단검
				temp = 오단;
				break;
			case 56050: // 기운을 잃은 오림의 목걸이
				temp = 오림목;
				break;
			case 56051: // 기운을 잃은 완력의 반지
				temp = 완력반;
				break;
			case 56052: // 기운을 잃은 용의 심장
				temp = 용심;
				break;
			case 56054: // 기운을 잃은 전사의 인장(데스페라도)
				temp = 데페;
				break;
			case 56055: // 기운을 잃은 전사의 인장(타이탄 락)
				temp = 타이탄락;
				break;
			case 56056: // 기운을 잃은 전사의 인장(타이탄 매직)
				temp = 타이탄매직;
				break;
			case 56057: // 기운을 잃은 전사의 인장(타이탄 블릿)
				temp = 타이탄블릿;
				break;
			case 56058: // 기운을 잃은 정령의 수정(소울 오브 프레임)
				temp = 소프;
				break;
			case 56059: // 기운을 잃은 정령의 수정(스트라이크 게일)
				temp = 게일;
				break;
			case 56085: // 기운을 잃은 정령의 수정(어스 바인드)
				temp = 어바;
				break;
			case 56060: // 기운을 잃은 정령의 수정(폴루트 워터)
				temp = 워터;
				break;
			case 56061: // 기운을 잃은 제니스의 반지
				temp = 제니스반;
				break;
			case 56062: // 기운을 잃은 지식의 반지
				temp = 지식반;
				break;
			case 56063: // 기운을 잃은 지휘관의 투구
				temp = 지휘관;
				break;
			case 56064: // 기운을 잃은 카스파의 모자
				temp = 카스파모;
				break;
			case 56065: // 기운을 잃은 커츠의 갑옷
				temp = 커츠갑;
				break;
			case 56066: // 기운을 잃은 커츠의 검
				temp = 커검;
				break;
			case 56067: // 기운을 잃은 커츠의 부츠
				temp = 커츠부;
				break;
			case 56068: // 기운을 잃은 커츠의 장갑
				temp = 커츠장;
				break;
			case 56069: // 기운을 잃은 커츠의 투구
				temp = 커츠투;
				break;
			case 56070: // 기운을 잃은 크로노스의 벨트
				temp = 크로벨;
				break;
			case 56071: // 기운을 잃은 타락의 부츠
				temp = 타락부;
				break;
			case 56072: // 기운을 잃은 타락의 장갑
				temp = 타락장;
				break;
			case 56073: // 기운을 잃은 타이탄의 벨트
				temp = 타벨;
				break;
			case 56074: // 기운을 잃은 투명 망토
				temp = 투망;
				break;
			case 56075: // 기운을 잃은 투사의 목걸이
				temp = 투사목;
				break;
			case 56076: // 기운을 잃은 파괴의 장궁
				temp = 파장;
				break;
			case 56077: // 기운을 잃은 현자의 목걸이
				temp = 현목;
				break;
			case 56086: // 기운을 잃은 혼돈의 투구 ㅇ
				temp = 혼투;
				break;
			case 56078: // 기운을 잃은 흑장로의 로브
				temp = 흑장로브;
				break;
			case 56087: // 기운을 잃은 흑장로의 샌달
				temp = 흑장샌달;
				break;
			case 56079: // 기운을 잃은 흑정령의 수정(아머 브레이크)
				temp = 아머브;
				break;
			case 56080: // 기운을 잃은 힘의 지팡이
				temp = 힘지;
				break;
			case 56082: // 기운을 발라카스의 숨결
				temp = 발라숨결;
				break;
			case 56083: // 신마투
				temp = 신마투;
				break;
			default:
				pc.sendPackets(new S_SystemMessage("\\aA알림: 기운을 잃은 아이템만 가능합니다."));
				break;
			}
			if (temp != null) {
				boolean chance = false;
				for (int i = 0; i < item.length; i++) {
					if (l1iteminstance.getItemId() == item[i]) {
						if (_random.nextInt(99) + 1 <= Config.LEAFOFLIFE) {
							chance = true;
							// 지급 처리.
							createNewItem2(pc, temp[i], 1,
									l1iteminstance1.getEnchantLevel());
							pc.sendPackets(new S_SystemMessage(""
									+ l1iteminstance1.getName()
									+ "은(는) 새 생명이 부여 되었습니다."));
							break;
						}
						if (pc.isGm()) {
							pc.sendPackets(new S_SystemMessage("나뭇잎확률 >> "
									+ Config.LEAFOFLIFE));
						}
					}
				}
				// 확율 실패햇을때 메세지 처리.
				if (!chance) {
					pc.sendPackets(new S_SystemMessage(""
							+ l1iteminstance1.getName()
							+ "은(는) 기운을 흡수하지 못하고 소멸하였습니다."));
				}
				// 재료 제거 처리.
				pc.getInventory().DeleteEnchant(l1iteminstance1.getItemId(),
						l1iteminstance1.getEnchantLevel());
				pc.getInventory().removeItem(l1iteminstance, 1);
			}
		}
			break;

		}
	}

	private static boolean createNewItem2(L1PcInstance pc, int item_id,
			int count, int EnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));
				// 무게 게이지가 부족하거나 인벤토리가 꽉차서 더 들 수 없습니다.
				return false;
			}
			// pc.sendPackets(new S_ServerMessage(403, item.getLogName())); //
			// %0를 손에 넣었습니다.
			return true;
		} else {
			return false;
		}
	}

}
