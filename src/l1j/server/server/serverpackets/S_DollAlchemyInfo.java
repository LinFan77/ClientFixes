package l1j.server.server.serverpackets;

public class S_DollAlchemyInfo {

	public static String 제작패킷(int i) {
		if (i == 0) {
			return "7b 00 08 00 12 20 08 01 12 03 08 f2 13 12 03 "
					+ "08 f1 13 12 03 08 f3 16 12 03 08 f2 16 12 03 08 "
					+ "d1 1f 12 03 08 ed 7d c3 f0";
		} else if (i == 1) {
			return "7b 00 08 01 12 20 08 02 12 03 08 f1 16 12 03 "
					+ "08 d4 15 12 03 08 d2 1f 12 03 08 b3 7d 12 03 08 "
					+ "b4 7d 12 03 08 eb 7d 00 00";
		} else if (i == 2) {
			return "7b 00 08 01 12 20 08 03 12 03 08 c2 7b 12 03 "
					+ "08 c1 7b 12 03 08 bd 7b 12 03 08 b5 7d 12 03 08 "
					+ "b6 7d 12 03 08 ec 7d 1f 3c";
		} else if (i == 3) {
			return "7b 00 08 01 12 16 08 04 12 03 08 bc 7b 12 03 "
					+ "08 8e 71 12 03 08 b8 7d 12 03 08 ba 7d b1 26";
		} else if (i == 4) {
			return "7b 00 08 01 1a 32 08 01 12 06 08 f2 13 10 96 "
					+ "35 12 06 08 f1 13 10 97 35 12 06 08 f3 16 10 98 "
					+ "35 12 06 08 f2 16 10 99 35 12 06 08 d1 1f 10 9a "
					+ "35 12 06 08 ed 7d 10 b2 36 33 06";
		} else if (i == 5) {
			return "7b 00 08 01 1a 32 08 02 12 06 08 f1 16 10 9b "
					+ "35 12 06 08 d4 15 10 9c 35 12 06 08 d2 1f 10 9d "
					+ "35 12 06 08 b3 7d 10 9f 35 12 06 08 b4 7d 10 9e "
					+ "35 12 06 08 eb 7d 10 b0 36 27 a9";
		} else if (i == 6) {
			return "7b 00 08 01 1a 32 08 03 12 06 08 c2 7b 10 a0 "
					+ "35 12 06 08 c1 7b 10 a1 35 12 06 08 bd 7b 10 a2 "
					+ "35 12 06 08 b5 7d 10 a3 35 12 06 08 b6 7d 10 b0 "
					+ "35 12 06 08 ec 7d 10 b1 36 1f 5b";
		} else if (i == 7) {
			return "7b 00 08 01 1a 22 08 04 12 06 08 bc 7b 10 a4 "
					+ "35 12 06 08 8e 71 10 a5 35 12 06 08 b8 7d 10 db "
					+ "35 12 06 08 ba 7d 10 de 35 d3 d5";
		} else if (i == 8) {
			return "7b 00 08 01 1a 12 08 05 12 06 08 b7 7d 10 dc "
					+ "35 12 06 08 b9 7d 10 dd 35 ae 57";
		} else if (i == 9) {
			return "7b 00 08 01 22 8a 01 08 01 12 06 08 01 10 00 "
					+ "18 01 12 06 08 02 10 00 18 01 12 06 08 03 10 00 "
					+ "18 01 12 06 08 04 10 00 18 01 1a 06 08 01 08 02 "
					+ "10 02 1a 06 08 01 08 03 10 02 1a 06 08 01 08 04 "
					+ "10 02 1a 06 08 02 08 03 10 02 1a 06 08 02 08 04 "
					+ "10 02 1a 06 08 03 08 04 10 02 1a 08 08 01 08 02 "
					+ "08 03 10 02 1a 08 08 01 08 02 08 04 10 02 1a 08 "
					+ "08 01 08 03 08 04 10 02 1a 08 08 02 08 03 08 04 "
					+ "10 02 1a 0e 08 01 08 02 08 03 08 04 10 02 1a 02 "
					+ "10 03 f5 d5";
		} else if (i == 10) {
			return "7b 00 08 01 22 8a 01 08 02 12 06 08 01 10 00 "
					+ "18 02 12 06 08 02 10 00 18 02 12 06 08 03 10 00 "
					+ "18 02 12 06 08 04 10 00 18 02 1a 06 08 01 08 02 "
					+ "10 03 1a 06 08 01 08 03 10 03 1a 06 08 01 08 04 "
					+ "10 03 1a 06 08 02 08 03 10 03 1a 06 08 02 08 04 "
					+ "10 03 1a 06 08 03 08 04 10 03 1a 08 08 01 08 02 "
					+ "08 03 10 03 1a 08 08 01 08 02 08 04 10 03 1a 08 "
					+ "08 01 08 03 08 04 10 03 1a 08 08 02 08 03 08 04 "
					+ "10 03 1a 0e 08 01 08 02 08 03 08 04 10 03 1a 02 "
					+ "10 04 59 cb";
		} else if (i == 11) {
			return "7b 00 08 01 22 8a 01 08 03 12 06 08 01 10 00 "
					+ "18 03 12 06 08 02 10 00 18 03 12 06 08 03 10 00 "
					+ "18 03 12 06 08 04 10 00 18 03 1a 06 08 01 08 02 "
					+ "10 04 1a 06 08 01 08 03 10 04 1a 06 08 01 08 04 "
					+ "10 04 1a 06 08 02 08 03 10 04 1a 06 08 02 08 04 "
					+ "10 04 1a 06 08 03 08 04 10 04 1a 08 08 01 08 02 "
					+ "08 03 10 04 1a 08 08 01 08 02 08 04 10 04 1a 08 "
					+ "08 01 08 03 08 04 10 04 1a 08 08 02 08 03 08 04 "
					+ "10 04 1a 0e 08 01 08 02 08 03 08 04 10 04 1a 02 "
					+ "10 05 d8 84";
		} else if (i == 12) {
			return "7b 00 08 01 22 86 01 08 04 12 06 08 01 10 00 "
					+ "18 04 12 06 08 02 10 00 18 04 12 06 08 03 10 00 "
					+ "18 04 12 06 08 04 10 00 18 04 1a 06 08 01 08 02 "
					+ "10 05 1a 06 08 01 08 03 10 05 1a 06 08 01 08 04 "
					+ "10 05 1a 06 08 02 08 03 10 05 1a 06 08 02 08 04 "
					+ "10 05 1a 06 08 03 08 04 10 05 1a 08 08 01 08 02 "
					+ "08 03 10 05 1a 08 08 01 08 02 08 04 10 05 1a 08 "
					+ "08 01 08 03 08 04 10 05 1a 08 08 02 08 03 08 04 "
					+ "10 05 1a 0a 08 01 08 02 08 03 08 04 10 05 79 b7";
		} else if (i == 13) {
			return "7b 00 08 02 e5 2e";
		}
		return null;
	}
}
