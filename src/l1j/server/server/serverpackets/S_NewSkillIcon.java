package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.skill.L1SkillId;

public class S_NewSkillIcon extends ServerBasePacket {

	private static final String S_NEWSKILLICON = "[S] S_NewSkillIcon";

	public S_NewSkillIcon(int skillId, boolean on) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(0x6E);
		writeC(0x08);
		writeC(on ? 2 : 3);
		writeC(0x10);
		byteWrite(skillId);
		if (on) {
			/*writeC(0x18);
			if (time < 0) {
				byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x01 };
				writeByte(minus);
			} else
				byteWrite(time);*/
			writeC(0x20);
			writeC(0x08);
			writeC(0x28);
			if (skillId == L1SkillId.순간이동지배) {// 순간이동 지배반지
				byteWrite(8463);// 인벤 이미지.
			} else if (skillId == L1SkillId.SEAL_BUFF) {// 데스힐
				byteWrite(5989);
			} /*else if (skillId == L1SkillId.ASSASSIN) {// 어쌔신
				byteWrite(7445);
			} else if (skillId == L1SkillId.BLAZING_SPIRITS) {// 블레이징 스피릿츠
				byteWrite(7448);
			} else if (skillId == L1SkillId.GRACE_AVATAR) {// 그레이스 아바타
				byteWrite(7428);
			} else if (skillId == L1SkillId.SOUL_BARRIER) {// 소울 배리어
				byteWrite(7436);
			} else if (skillId == L1SkillId.DESTROY) {// 디스트로이
				byteWrite(7451);
			} else if (skillId == L1SkillId.IMPACT) {// 임팩트
				byteWrite(7457);
			} else if (skillId == L1SkillId.TITANL_RISING) {// 타이탄: 라이징
				byteWrite(7461);
			} else if (skillId == L1SkillId.STATUS_CASHSCROLL3) {// 전강
				byteWrite(2430);
			}*/
		}
		writeH(0x0030);
		if (on) {
			writeC(0x38);
			writeC(0x03);
			writeC(0x40);
			int msgNum = 0;
			if (skillId == L1SkillId.순간이동지배)// 앱솔루트 배리어
				msgNum = 5119;// 메세지.
			else if (skillId == L1SkillId.SEAL_BUFF)// 데스힐
				msgNum = 4994;
			/*else if (skillId == L1SkillId.ASSASSIN)// 어쌔신
				msgNum = 4738;
			else if (skillId == L1SkillId.BLAZING_SPIRITS)// 블레이징 스피릿츠
				msgNum = 4750;
			else if (skillId == L1SkillId.GRACE_AVATAR)// 그레이스 아바타
				msgNum = 4734;
			else if (skillId == L1SkillId.SOUL_BARRIER)// 소울 배리어
				msgNum = 4736;
			else if (skillId == L1SkillId.DESTROY)// 디스트로이
				msgNum = 4739;
			else if (skillId == L1SkillId.IMPACT)// 임팩트
				msgNum = 4761;
			else if (skillId == L1SkillId.STATUS_CASHSCROLL3) // 전강
				msgNum = 1316;*/

			byteWrite(msgNum);
			writeC(0x48);
			writeC(0x00);
		}
		writeH(0x0050);
		if (on) {
			writeC(0x58);
			writeC(0x01);
			writeC(0x60);
			writeC(0x00);
			writeC(0x68);
			writeC(0x00);
			writeC(0x70);
			writeC(0x00);
		}
		writeH(0x00);
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1,
			0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8,
			0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef,
			0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	private void byteWrite(long value) {
		long temp = value / 128;
		if (temp > 0) {
			writeC(hextable[(int) value % 128]);
			while (temp >= 128) {
				writeC(hextable[(int) temp % 128]);
				temp = temp / 128;
			}
			if (temp > 0)
				writeC((int) temp);
		} else {
			if (value == 0) {
				writeC(0);
			} else {
				writeC(hextable[(int) value]);
				writeC(0);
			}
		}
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_NEWSKILLICON;
	}
}
