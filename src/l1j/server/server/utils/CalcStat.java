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

import java.util.Random;

import l1j.server.Config;

public class CalcStat {

	private static Random rnd = new Random(System.nanoTime());

	private CalcStat() {
	}

	public static int[] levelIncreaseAmountMP(int charType, int wis) {
		int minimumMP = 0;
		int maximumMP = 0;
		switch (charType) {
		case 0: // Royal
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				minimumMP = 3;
				maximumMP = 4;
				break;
			case 12:
			case 13:
			case 14:
				minimumMP = 3;
				maximumMP = 5;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 4;
				maximumMP = 6;
				break;
			case 18:
			case 19:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 20:
				minimumMP = 5;
				maximumMP = 7;
				break;
			case 21:
			case 22:
			case 23:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 24:
				minimumMP = 5;
				maximumMP = 9;
				break;
			case 25:
			case 26:
				minimumMP = 6;
				maximumMP = 9;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 6;
				maximumMP = 10;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 7;
				maximumMP = 11;
				break;
			case 33:
			case 34:
				minimumMP = 7;
				maximumMP = 12;
				break;
			case 35:
				minimumMP = 8;
				maximumMP = 12;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 8;
				maximumMP = 13;
				break;
			case 39:
				minimumMP = 8;
				maximumMP = 14;
				break;
			case 40:
			case 41:
				minimumMP = 9;
				maximumMP = 14;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 9;
				maximumMP = 15;
				break;
			case 45:
				minimumMP = 10;
				maximumMP = 16;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 10;
				maximumMP = 16;
			}
			break;
		case 1: // Knight
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				minimumMP = 0;
				maximumMP = 2;
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 1;
				maximumMP = 2;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 2;
				maximumMP = 3;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				minimumMP = 2;
				maximumMP = 4;
				break;
			case 24:
				minimumMP = 2;
				maximumMP = 5;
				break;
			case 25:
			case 26:
				minimumMP = 3;
				maximumMP = 5;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 3;
				maximumMP = 6;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 4;
				maximumMP = 6;
				break;
			case 33:
			case 34:
			case 35:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				minimumMP = 4;
				maximumMP = 8;
				break;
			case 40:
			case 41:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 5;
				maximumMP = 9;
				break;
			case 45:
				minimumMP = 6;
				maximumMP = 10;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				minimumMP = 6;
				maximumMP = 10;
			}
			break;

		case 2: // Elf
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 18:
			case 19:
				minimumMP = 5;
				maximumMP = 10;
				break;
			case 20:
				minimumMP = 7;
				maximumMP = 10;
				break;
			case 21:
			case 22:
			case 23:
				minimumMP = 7;
				maximumMP = 11;
				break;
			case 24:
				minimumMP = 7;
				maximumMP = 13;
				break;
			case 25:
			case 26:
				minimumMP = 8;
				maximumMP = 13;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 8;
				maximumMP = 14;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 10;
				maximumMP = 16;
				break;
			case 33:
			case 34:
				minimumMP = 10;
				maximumMP = 17;
				break;
			case 35:
				minimumMP = 11;
				maximumMP = 17;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 11;
				maximumMP = 19;
				break;
			case 39:
				minimumMP = 11;
				maximumMP = 20;
				break;
			case 40:
			case 41:
				minimumMP = 13;
				maximumMP = 20;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 13;
				maximumMP = 21;
				break;
			case 45:
				minimumMP = 14;
				maximumMP = 23;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 14;
				maximumMP = 23;
			}
			break;

		case 3: // Wizard
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 6;
				maximumMP = 10;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 8;
				maximumMP = 12;
				break;
			case 18:
			case 19:
				minimumMP = 8;
				maximumMP = 14;
				break;
			case 20:
				minimumMP = 10;
				maximumMP = 14;
				break;
			case 21:
			case 22:
			case 23:
				minimumMP = 10;
				maximumMP = 16;
				break;
			case 24:
				minimumMP = 10;
				maximumMP = 18;
				break;
			case 25:
			case 26:
				minimumMP = 12;
				maximumMP = 18;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 12;
				maximumMP = 20;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 14;
				maximumMP = 22;
				break;
			case 33:
			case 34:
				minimumMP = 14;
				maximumMP = 24;
				break;
			case 35:
				minimumMP = 16;
				maximumMP = 24;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 16;
				maximumMP = 26;
				break;
			case 39:
				minimumMP = 16;
				maximumMP = 28;
				break;
			case 40:
			case 41:
				minimumMP = 18;
				maximumMP = 28;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 18;
				maximumMP = 30;
				break;
			case 45:
				minimumMP = 20;
				maximumMP = 32;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 20;
				maximumMP = 32;
			}
			break;

		case 4: // Dark Elf
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				minimumMP = 4;
				maximumMP = 5;
				break;
			case 12:
			case 13:
			case 14:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 18:
			case 19:
				minimumMP = 5;
				maximumMP = 10;
				break;
			case 20:
				minimumMP = 7;
				maximumMP = 10;
				break;
			case 21:
			case 22:
			case 23:
				minimumMP = 7;
				maximumMP = 11;
				break;
			case 24:
				minimumMP = 7;
				maximumMP = 13;
				break;
			case 25:
			case 26:
				minimumMP = 8;
				maximumMP = 13;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 8;
				maximumMP = 14;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 10;
				maximumMP = 16;
				break;
			case 33:
			case 34:
				minimumMP = 10;
				maximumMP = 17;
				break;
			case 35:
				minimumMP = 11;
				maximumMP = 17;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 11;
				maximumMP = 19;
				break;
			case 39:
				minimumMP = 11;
				maximumMP = 20;
				break;
			case 40:
			case 41:
				minimumMP = 13;
				maximumMP = 20;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 13;
				maximumMP = 22;
				break;
			case 45:
				minimumMP = 14;
				maximumMP = 23;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 14;
				maximumMP = 23;
			}
			break;

		case 5: // Dragon Knight
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 2;
				maximumMP = 3;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 3;
				maximumMP = 4;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				minimumMP = 3;
				maximumMP = 5;
				break;
			case 24:
				minimumMP = 3;
				maximumMP = 6;
				break;
			case 25:
			case 26:
				minimumMP = 4;
				maximumMP = 6;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 5;
				maximumMP = 9;
				break;
			case 39:
				minimumMP = 5;
				maximumMP = 10;
				break;
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
				minimumMP = 6;
				maximumMP = 10;
				break;
			case 45:
				minimumMP = 7;
				maximumMP = 11;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 7;
				maximumMP = 11;
			}
			break;
		case 6: // Illusionist
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 6;
				maximumMP = 9;
				break;
			case 18:
			case 19:
				minimumMP = 6;
				maximumMP = 11;
				break;
			case 20:
				minimumMP = 7;
				maximumMP = 11;
				break;
			case 21:
			case 22:
			case 23:
				minimumMP = 7;
				maximumMP = 12;
				break;
			case 24:
				minimumMP = 7;
				maximumMP = 14;
				break;
			case 25:
			case 26:
				minimumMP = 9;
				maximumMP = 14;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 9;
				maximumMP = 16;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 11;
				maximumMP = 18;
				break;
			case 33:
			case 34:
				minimumMP = 11;
				maximumMP = 19;
				break;
			case 35:
				minimumMP = 12;
				maximumMP = 19;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 12;
				maximumMP = 21;
				break;
			case 39:
				minimumMP = 12;
				maximumMP = 23;
				break;
			case 40:
			case 41:
				minimumMP = 14;
				maximumMP = 23;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 14;
				maximumMP = 24;
				break;
			case 45:
				minimumMP = 16;
				maximumMP = 26;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 16;
				maximumMP = 26;
			}
			break;
		case 7: // Warrior
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				minimumMP = 0;
				maximumMP = 1;
				break;
			case 9:
				minimumMP = 0;
				maximumMP = 2;
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 1;
				maximumMP = 2;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 2;
				maximumMP = 3;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				minimumMP = 2;
				maximumMP = 4;
				break;
			case 24:
				minimumMP = 2;
				maximumMP = 5;
				break;
			case 25:
			case 26:
				minimumMP = 3;
				maximumMP = 5;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 3;
				maximumMP = 6;
				break;
			case 30:
			case 31:
			case 32:
				minimumMP = 4;
				maximumMP = 6;
				break;
			case 33:
			case 34:
			case 35:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				minimumMP = 4;
				maximumMP = 8;
				break;
			case 40:
			case 41:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 42:
			case 43:
			case 44:
				minimumMP = 5;
				maximumMP = 9;
				break;
			case 45:
				minimumMP = 6;
				maximumMP = 10;
				break;
			default:
				break;

			};
			if (wis > 45) {
				minimumMP = 6;
				maximumMP = 10;
			}
			break;
		case 8: // Fencer
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				minimumMP = 2;
				maximumMP = 3;
				break;
			case 15:
			case 16:
			case 17:
				minimumMP = 3;
				maximumMP = 4;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				minimumMP = 3;
				maximumMP = 5;
				break;
			case 24:
				minimumMP = 3;
				maximumMP = 6;
				break;
			case 25:
			case 26:
				minimumMP = 4;
				maximumMP = 6;
				break;
			case 27:
			case 28:
			case 29:
				minimumMP = 4;
				maximumMP = 7;
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
				minimumMP = 5;
				maximumMP = 8;
				break;
			case 36:
			case 37:
			case 38:
				minimumMP = 5;
				maximumMP = 9;
				break;
			case 39:
				minimumMP = 5;
				maximumMP = 10;
				break;
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
				minimumMP = 6;
				maximumMP = 10;
				break;
			case 45:
				minimumMP = 7;
				maximumMP = 11;
				break;
			default:
				break;
			};
			if (wis > 45) {
				minimumMP = 7;
				maximumMP = 11;
			}
			break;
		default:
			break;
		}

		if (wis <= 0) {
			minimumMP = 0;
			maximumMP = 1;
		}

		int[] bbb = { minimumMP, maximumMP };

		return bbb;
	}

	public static int levelUpMP(int charType, int baseMaxMp, int wis) {
		int addmp = 0;
		switch (charType) {
		case 0:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				addmp = rnd.nextInt(2) + 3;
				break;
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 20:
				addmp = rnd.nextInt(3) + 5;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 24:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(4) + 6;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(5) + 6;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(6) + 7;
				break;
			case 35:
				addmp = rnd.nextInt(5) + 8;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(6) + 8;
				break;
			case 39:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(6) + 9;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(7) + 9;
				break;
			case 45:
				addmp = rnd.nextInt(7) + 10;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(7) + 10;
			break;// 군주

		case 1: // Royal
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				addmp = rnd.nextInt(3);
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 1;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 2;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 2;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				addmp = rnd.nextInt(5) + 4;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 6;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(5) + 6;
			break;

		case 2: // Knight
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(6) + 5;
				break;
			case 20:
				addmp = rnd.nextInt(4) + 7;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 24:
				addmp = rnd.nextInt(7) + 7;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(6) + 8;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(7) + 10;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(8) + 10;
				break;
			case 35:
				addmp = rnd.nextInt(7) + 11;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(9) + 11;
				break;
			case 39:
				addmp = rnd.nextInt(10) + 11;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(8) + 13;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(10) + 13;
				break;
			case 45:
				addmp = rnd.nextInt(10) + 14;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(10) + 14;
			break;

		case 3:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(5) + 6;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(5) + 8;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 20:
				addmp = rnd.nextInt(5) + 10;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(7) + 10;
				break;
			case 24:
				addmp = rnd.nextInt(9) + 10;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(7) + 12;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(9) + 12;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(9) + 14;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(11) + 14;
				break;
			case 35:
				addmp = rnd.nextInt(9) + 16;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(11) + 16;
				break;
			case 39:
				addmp = rnd.nextInt(13) + 16;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(11) + 18;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(13) + 18;
				break;
			case 45:
				addmp = rnd.nextInt(13) + 20;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(13) + 20;
			break;

		case 4:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				addmp = rnd.nextInt(2) + 4;
				break;
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(6) + 5;
				break;
			case 20:
				addmp = rnd.nextInt(4) + 7;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 24:
				addmp = rnd.nextInt(7) + 7;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(6) + 8;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(7) + 10;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(8) + 10;
				break;
			case 35:
				addmp = rnd.nextInt(7) + 11;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(9) + 11;
				break;
			case 39:
				addmp = rnd.nextInt(10) + 11;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(8) + 13;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(10) + 13;
				break;
			case 45:
				addmp = rnd.nextInt(10) + 14;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(10) + 14;
			break;

		case 5:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 3;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 39:
				addmp = rnd.nextInt(6) + 5;
				break;
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 6;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 7;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(5) + 7;
			break;
		case 6:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(4) + 6;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(6) + 6;
				break;
			case 20:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(6) + 7;
				break;
			case 24:
				addmp = rnd.nextInt(8) + 7;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(6) + 9;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(8) + 9;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(8) + 11;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(9) + 11;
				break;
			case 35:
				addmp = rnd.nextInt(8) + 12;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(10) + 12;
				break;
			case 39:
				addmp = rnd.nextInt(12) + 12;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(10) + 14;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(11) + 14;
				break;
			case 45:
				addmp = rnd.nextInt(11) + 16;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(11) + 16;
			break;
		case 7: // Warrior
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				addmp = rnd.nextInt(2);
				break;
			case 9:
				addmp = rnd.nextInt(3);
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 1;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 2;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 2;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				addmp = rnd.nextInt(5) + 4;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 6;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(5) + 6;
			break;
		case 8: // Fencer
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				addmp = rnd.nextInt(3);
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 1;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 2;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 2;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				addmp = rnd.nextInt(5) + 4;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 6;
				break;
			default:
				break;
			};
			if (wis > 45)
				addmp = rnd.nextInt(5) + 6;
			break;
			default:
				break;
		}

		if (wis <= 0) {
			addmp = rnd.nextInt(2);
		}

		switch (charType) {
		case 0:
			if (baseMaxMp + addmp > Config.PRINCE_MAX_MP) {
				addmp = (Config.PRINCE_MAX_MP - baseMaxMp);
			}
			break;
		case 1:
			if (baseMaxMp + addmp > Config.KNIGHT_MAX_MP) {
				addmp = (Config.KNIGHT_MAX_MP - baseMaxMp);
			}
			break;
		case 2:
			if (baseMaxMp + addmp > Config.ELF_MAX_MP) {
				addmp = (Config.ELF_MAX_MP - baseMaxMp);
			}
			break;
		case 3:
			if (baseMaxMp + addmp > Config.WIZARD_MAX_MP) {
				addmp = (Config.WIZARD_MAX_MP - baseMaxMp);
			}
			break;
		case 4:
			if (baseMaxMp + addmp > Config.DARKELF_MAX_MP) {
				addmp = (Config.DARKELF_MAX_MP - baseMaxMp);
			}
			break;
		case 5:
			if (baseMaxMp + addmp > Config.DRAGONKNIGHT_MAX_MP) {
				addmp = (Config.DRAGONKNIGHT_MAX_MP - baseMaxMp);
			}
			break;
		case 6:
			if (baseMaxMp + addmp > Config.ILLUSIONIST_MAX_MP) {
				addmp = (Config.ILLUSIONIST_MAX_MP - baseMaxMp);
			}
			break;
		case 7: // Warrior
			if (baseMaxMp + addmp > Config.WARRIOR_MAX_MP) {
				addmp = (Config.WARRIOR_MAX_MP - baseMaxMp);
			}
			break;
		case 8: // Fencer
			if (baseMaxMp + addmp > Config.FENCER_MAX_MP) {
				addmp = (Config.FENCER_MAX_MP - baseMaxMp);
			}
			break;
		default:
			break;
		}

		return addmp;
	}

	////////////// CONSTITUTION //////////////

	public static int levelUpIncrease(int charType, int totalCon) {
		int startcon = 0;
		int starthp = 0;
		int returnhp = 0;
		switch (charType) {
		case 0: // Royal
			startcon = 11;
			starthp = 12;
			break;
		case 1: // Knight
			startcon = 16;
			starthp = 21;
			break;
		case 2: // elf
			startcon = 12;
			starthp = 10;
			break;
		case 3: // Wizard
			startcon = 12;
			starthp = 7;
			break;
		case 4: // Dark Elf
			startcon = 12;
			starthp = 11;
			break;
		case 5: // Dragon Knight
			startcon = 14;
			starthp = 15;
			break;
		case 6: // Illusionist
			startcon = 12;
			starthp = 9;
			break;
		case 7: // Warrior
			startcon = 16;
			starthp = 21;
			break;
		case 8: // Fencer
			startcon = 15;
			starthp = 21;
			break;
		default:
			break;
		}
		int _25orMore = 0;
		int _25orBelow = 0;
		if (totalCon > 25) {
			_25orBelow = 25 - startcon;
			_25orMore = (totalCon - 25) / 2;
		} else {
			_25orBelow = totalCon - startcon;
		}

		if (_25orBelow != 0) {
			returnhp += (starthp + _25orBelow);
		}

		if (_25orMore != 0) {
			returnhp += _25orMore;
		}

		return returnhp;
	}

	public static int levelUpHP(int charType, int baseMaxHp, int totalCon) {
		int startcon = 0;
		int starthp = 0;
		int returnhp = 0;
		switch (charType) {
		case 0: // Royal
			startcon = 11;
			starthp = 12;
			break;
		case 1: // Knight
			startcon = 16;
			starthp = 21;
			break;
		case 2: // Elf
			startcon = 12;
			starthp = 10;
			break;
		case 3: // Wizard
			startcon = 12;
			starthp = 7;
			break;
		case 4: // Dark Elf
			startcon = 12;
			starthp = 11;
			break;
		case 5: // Dragon Knight
			startcon = 14;
			starthp = 15;
			break;
		case 6: // Illusionist
			startcon = 12;
			starthp = 9;
			break;
		case 7: // Warrior
			startcon = 16;
			starthp = 21;
			break;
		case 8: // Fencer
			startcon = 15;
			starthp = 21;
			break;
		default:
			break;
		}

		int calcstat = totalCon - startcon;

		if (calcstat <= 0) {
			returnhp = starthp + rnd.nextInt(2);
		}
		returnhp = starthp + (calcstat + rnd.nextInt(2));

		switch (charType) {
		case 0:
			if (baseMaxHp + returnhp > Config.PRINCE_MAX_HP) {
				returnhp = (Config.PRINCE_MAX_HP - baseMaxHp);
			}
			break;
		case 1:
			if (baseMaxHp + returnhp > Config.KNIGHT_MAX_HP) {
				returnhp = (Config.KNIGHT_MAX_HP - baseMaxHp);
			}
			break;
		case 2:
			if (baseMaxHp + returnhp > Config.ELF_MAX_HP) {
				returnhp = (Config.ELF_MAX_HP - baseMaxHp);
			}
			break;
		case 3:
			if (baseMaxHp + returnhp > Config.WIZARD_MAX_HP) {
				returnhp = (Config.WIZARD_MAX_HP - baseMaxHp);
			}
			break;
		case 4:
			if (baseMaxHp + returnhp > Config.DARKELF_MAX_HP) {
				returnhp = (Config.DARKELF_MAX_HP - baseMaxHp);
			}
			break;
		case 5:
			if (baseMaxHp + returnhp > Config.DRAGONKNIGHT_MAX_HP) {
				returnhp = (Config.DRAGONKNIGHT_MAX_HP - baseMaxHp);
			}
			break;
		case 6:
			if (baseMaxHp + returnhp > Config.ILLUSIONIST_MAX_HP) {
				returnhp = (Config.ILLUSIONIST_MAX_HP - baseMaxHp);
			}
			break;
		case 7:
			if (baseMaxHp + returnhp > Config.WARRIOR_MAX_HP) {
				returnhp = (Config.WARRIOR_MAX_HP - baseMaxHp);
			}
			break;
		case 8:
			if (baseMaxHp + returnhp > Config.FENCER_MAX_HP) {
				returnhp = (Config.FENCER_MAX_HP - baseMaxHp);
			}
			break;
		default:
			break;
		}

		return returnhp;
	}

	public static int hpRecoveryTick(int con) {
		try {
			if (con < 0)
				return 5;
			if (con > 45)
				return 27;

			int result = HP_RECOVERY_TICK[con];
			if (con >= 25)
				result++;
			if (con >= 35)
				result++;
			if (con >= 45)
				result += 3;
			if (con >= 55)
				result += 5;
			if (con >= 60)
				result += 5;

			return result; // total HP recovered per tick
		} catch (Exception e) {
			e.printStackTrace();
			return 5;
		}
	}

	private static final int[] HP_RECOVERY_TICK = {
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,		// 0~10
			5, 6, 6, 7, 7, 8, 8, 9, 9, 10,			// 11~20
			10, 11, 11, 12, 13, 14, 14, 15, 15, 16,	// 21~30
			16, 17, 17, 18, 19, 20, 20, 21, 21, 22,	// 31~40
			22, 23, 23, 24, 27 };					// 41~45

	public static int hpPotionRecovery(int con) {
		try {
			int i = 0;
			if (con >= 35)
				i++;
			if (con >= 45)
				i += 2;
			if (con >= 55)
				i += 2;
			if (con >= 60)
				i += 2;

			return i; // total HP potion recovery bonus
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	////////////// STRENGTH //////////////

	public static int getMaxWeight(int str, int con) {
		return maxCarryWeight(str, con);
	}

	public static int maxCarryWeight(int str, int con) {
		int maxWeight = 1000;
		try {
			int stat = (str + con) / 2;
			if (stat <= 0)
				return maxWeight;
			maxWeight += stat * 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxWeight;
	}

	public static int calcSTRBonusDamage(int str) {
		try {
			if (str < 25)
				return 0;
			int bonusDamage = 0;
			if (str >= 25)
				bonusDamage++;
			if (str >= 35)
				bonusDamage++;
			if (str >= 45)
				bonusDamage += 3;
			if (str >= 55)
				bonusDamage += 5;
			if (str >= 60)
				bonusDamage += 5;

			return bonusDamage; // bonus damage from base STR
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int calcDamageFromSTR(int str) {
		switch (str) {
		case 10:
		case 11:
			return 3;
		case 12:
		case 13:
			return 4;
		case 14:
		case 15:
			return 5;
		case 16:
		case 17:
			return 6;
		case 18:
		case 19:
			return 7;
		case 20:
		case 21:
			return 8;
		case 22:
		case 23:
			return 9;
		case 24:
			return 10;
		case 25:
			return 11;
		case 26:
		case 27:
			return 12;
		case 28:
		case 29:
			return 13;
		case 30:
		case 31:
			return 14;
		case 32:
		case 33:
			return 15;
		case 34:
			return 16;
		case 35:
			return 17;
		case 36:
		case 37:
			return 18;
		case 38:
		case 39:
			return 19;
		case 40:
		case 41:
			return 20;
		case 42:
		case 43:
			return 21;
		case 44:
			return 22;
		case 45:
		case 46:
			return 25;
		case 47:
			return 26;
		case 48:
		case 49:
			return 27;
		case 50:
			return 28;
		case 51:
		case 52:
			return 29;
		case 53:
			return 30;
		case 54:
		case 55:
			return 31;
		case 56:
			return 32;
		case 57:
		case 58:
			return 33;
		case 59:
			return 34;
		case 60:
		case 61:
			return 35;
		case 62:
			return 36;
		case 63:
		case 64:
			return 37;
		case 65:
			return 38;
		case 66:
		case 67:
			return 39;
		case 68:
			return 40;
		case 69:
		case 70:
			return 41;
		case 71:
			return 42;
		case 72:
		case 73:
			return 43;
		case 74:
			return 44;
		case 75:
		case 76:
			return 45;
		case 77:
			return 46;
		case 78:
		case 79:
			return 47;
		case 80:
			return 48;
		case 81:
		case 82:
			return 49;
		case 83:
			return 50;
		case 84:
		case 85:
			return 51;
		case 86:
			return 52;
		case 87:
		case 88:
		case 89:
			return 53;
		default:
			if (str >= 90)
				return 54;
			else
				return 2;
		}
	}

	public static int calcSTRBonusHit(int str) {
		try {
			if (str < 25)
				return 0;
			int bonusHit = 0;
			if (str >= 25)
				bonusHit++;
			if (str >= 35)
				bonusHit++;
			if (str >= 45)
				bonusHit += 3;
			if (str >= 55)
				bonusHit += 5;
			if (str >= 60)
				bonusHit += 5;

			return bonusHit; // Bonus melee hit from base STR
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int calcMeleeHitFromSTR(int str) {
		switch (str) {
		case 9:
		case 10:
			return 6;
		case 11:
			return 7;
		case 12:
		case 13:
			return 8;
		case 14:
			return 9;
		case 15:
		case 16:
			return 10;
		case 17:
			return 11;
		case 18:
		case 19:
			return 12;
		case 20:
			return 13;
		case 21:
		case 22:
			return 14;
		case 23:
			return 15;
		case 24:
			return 16;
		case 25:
			return 17;
		case 26:
			return 18;
		case 27:
		case 28:
			return 19;
		case 29:
			return 20;
		case 30:
		case 31:
			return 21;
		case 32:
			return 22;
		case 33:
		case 34:
			return 23;
		case 35:
			return 25;
		case 36:
		case 37:
			return 26;
		case 38:
			return 27;
		case 39:
		case 40:
			return 28;
		case 41:
			return 29;
		case 42:
		case 43:
			return 30;
		case 44:
			return 31;
		case 45:
		case 46:
			return 35;
		case 47:
			return 36;
		case 48:
		case 49:
			return 37;
		case 50:
			return 38;
		case 51:
		case 52:
			return 39;
		case 53:
			return 40;
		case 54:
		case 55:
			return 41;
		case 56:
			return 42;
		case 57:
		case 58:
			return 43;
		case 59:
			return 44;
		case 60:
		case 61:
			return 45;
		case 62:
			return 46;
		case 63:
		case 64:
			return 47;
		case 65:
			return 48;
		case 66:
		case 67:
			return 49;
		case 68:
			return 50;
		case 69:
		case 70:
			return 51;
		case 71:
			return 52;
		case 72:
		case 73:
			return 53;
		case 74:
			return 54;
		case 75:
		case 76:
			return 55;
		case 77:
			return 56;
		case 78:
		case 79:
			return 57;
		case 80:
			return 58;
		case 81:
		case 82:
			return 59;
		case 83:
			return 60;
		case 84:
		case 85:
			return 61;
		case 86:
			return 62;
		case 87:
		case 88:
		case 89:
			return 63;
		default:
			if (str >= 90)
				return 64;
			else
				return 5;
		}
	}

	public static int meleeCrit(int str) {
		try {
			int i = 0;
			if (str >= 40)
				i++;
			if (str >= 45)
				i++;
			if (str >= 55)
				i += 2;
			if (str >= 60)
				i += 2;
			if (str >= 65)
				i += 2;
			if (str >= 70)
				i += 2;

			return i; // melee crit from STR
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	////////////// INTELLIGENCE //////////////

	public static int calcMagicDmg(int Int) {
	    if (Int >= 15 && Int <= 89) {
	        return (Int - 14) / 5;
	    } else if (Int >= 90) {
	        return 23;
	    } else {
	        return 0;
	    }
	}

	public static int calcINTBonusDamage(int inte) {
		try {
			if (inte < 25)
				return 0;
			int bonusDamage = 0;
			if (inte >= 25)
				bonusDamage++;
			if (inte >= 35)
				bonusDamage++;
			if (inte >= 45)
				bonusDamage += 3;
			if (inte >= 55)
				bonusDamage += 5;
			if (inte >= 60)
				bonusDamage += 5;

			return bonusDamage; // bonus damage from base INT
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int magicHit(int inte) {
		try {
			if (inte < 9)
				return -4;
			int temp = -4 + ((inte - 6) / 3);
			if (inte >= 25)
				temp++;
			if (inte >= 35)
				temp++;
			if (inte >= 45)
				temp += 3;
			if (inte >= 55)
				temp += 5;
			if (inte >= 60)
				temp += 5;

			return temp; // spell hit from INT
		} catch (Exception e) {
			e.printStackTrace();
			return -4;
		}
	}

	public static int magicCrit(int inte) { // MCrit
		try {
			int i = 0;
			if (inte >= 45)
				i++;
			if (inte >= 55)
				i += 2;
			if (inte >= 60)
				i += 2;
			if (inte >= 65)
				i += 2;
			if (inte >= 70)
				i += 2;

			return i; // magic crit from INT
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int calcMagicBonus(int inte) { // MB
	    if (inte < 12) {
	        return 2;
	    } else {
	        int temp = 2 + ((inte - 8) / 4);
	        return temp;
	    }
	}

	public static int calcMPCostReduction(int inte) {
		try {
			if (inte < 0)
				return 5;
			if (inte > 45)
				return 30;
			return MP_COST_REDUCTION[inte];
		} catch (Exception e) {
			e.printStackTrace();
			return 5;
		}
	}

	private static final int[] MP_COST_REDUCTION = {
			5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6,		// 0~10
			7, 8, 8, 9, 10, 10, 11, 12, 12, 13,		// 11~20
			14, 14, 15, 16, 16, 17, 18, 18, 19, 20, // 21~30
			20, 21, 22, 22, 23, 24, 24, 25, 26, 26, // 31~40
			27, 28, 28, 29, 30 }; 					// 41~45

	////////////// DEXTERITY //////////////

	public static int calcDEXBonusDamage(int dex) {
		try {
			if (dex < 25)
				return 0;
			int bonusDamage = 0;
			if (dex >= 25)
				bonusDamage++;
			if (dex >= 35)
				bonusDamage++;
			if (dex >= 45)
				bonusDamage += 3;
			if (dex >= 55)
				bonusDamage += 5;
			if (dex >= 60)
				bonusDamage += 5;

			return bonusDamage; // bonus damage from base DEX
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int calcDamageFromDEX(int dex) {
	    if (dex >= 9 && dex <= 89) {
	        return (dex - 8) / 3 + 2;
	    } else if (dex >= 90) {
	        return 35;
	    } else {
	        return 2;
	    }
	}

	public static int calcRangeHitUp(int dex) {
		switch (dex) {
		case 8:
			return -2;
		case 9:
			return -1;
		case 10:
			return 0;
		default:
			if (dex >= 11 && dex <= 89) {
				return (dex - 10);
			} else if (dex >= 90)
				return 82;
			else
				return -3;
			}
	}

	public static int calcRangeBonusHit(int dex) {
		try {
			if (dex < 25)
				return 0;
			int bonusHit = 0;
			if (dex >= 25)
				bonusHit++;
			if (dex >= 35)
				bonusHit++;
			if (dex >= 45)
				bonusHit += 3;
			if (dex >= 55)
				bonusHit += 5;
			if (dex >= 60)
				bonusHit += 5;

			return bonusHit; // bonus ranged hit from DEX
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int calcRangeCrit(int dex) {
		try {
			int rangedCrit = 0;
			if (dex >= 45)
				rangedCrit++;
			if (dex >= 55)
				rangedCrit += 2;
			if (dex >= 60)
				rangedCrit += 2;
			if (dex >= 65)
				rangedCrit += 2;

			return rangedCrit; // ranged crit from DEX
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int calcACFromDEX(int dex) {
		try {
			int armourClass = 10; // default AC +10
			if (dex < 9) {
				armourClass -= 2;
			} else {
				armourClass += (-2 + (((dex - 6) / 3) * -1));
			}
			return armourClass;
		} catch (Exception e) {
			e.printStackTrace();
			return 10; // -2 at ac10
		}
	}

	public static int calcRangedEvasion(int dex) {
		try {
			if (dex < 0)
				return 3;
			if (dex > 45)
				return 22;
			return RANGED_EVASION[dex];
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

	private static final int[] RANGED_EVASION = {
			3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 5,		// 0~10
			5, 6, 6, 7, 7, 8, 8, 9, 9, 10,			// 11~20
			10, 11, 11, 12, 12, 13, 13, 14, 14, 15,	// 21~30
			15, 16, 16, 17, 17, 18, 18, 19, 19, 20,	// 31~40
			20, 21, 21, 22, 22 };					// 41~45

////////////// WISDOM //////////////

	public static int mpRecoveryTick(int wis) {
		try {
			if (wis < 0)
				return 1;
			if (wis > 45)
				return 14;
			return MP_RECOVERY_TICK[wis];
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	private static final int[] MP_RECOVERY_TICK = {
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,	// 0~10
			2, 2, 2, 2, 3, 3, 3, 3, 3, 4,		// 11~20
			4, 4, 4, 4, 6, 6, 6, 6, 6, 7,		// 21~30
			7, 7, 7, 7, 9, 9, 9, 9, 9, 10,		// 31~40
			10, 10, 10, 10, 14 };				// 41~45

	public static int mpPotionRecovery(int wis) {
		try {
			if (wis < 0)
				return 1;
			if (wis > 45)
				return 23;
			return MP_POTION_RECOVERY[wis];
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	private static final int[] MP_POTION_RECOVERY = {
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,		// 0~10
			1, 2, 2, 2, 3, 4, 4, 5, 5, 6,			// 11~20
			6, 7, 7, 8, 9, 10, 10, 11, 11, 12,		// 21~30
			12, 13, 13, 14, 15, 16, 16, 17, 17, 18,	// 31~40
			18, 19, 19, 20, 23 };					// 41~45

	public static int calcMagicResistance(int type, int wis) {
		try {
			int magicResistance = 0;
			switch (type) {
			case 0: // Royal
			case 8: // Fencer
				if (11 <= wis && wis <= 65)
					magicResistance = 10 + (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 230;
				break;
			case 1: // Knight
			case 7: // Warrior
				if (11 <= wis && wis <= 65)
					magicResistance = (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 220;
				break;
			case 2: // Elf
				if (12 <= wis && wis <= 65)
					magicResistance = 25 + (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 245;
				break;
			case 3: // Wizard
				if (14 <= wis && wis <= 65)
					magicResistance = 15 + (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 235;
				break;
			case 4: // Dark Elf
				if (10 <= wis && wis <= 65)
					magicResistance = 10 + (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 230;
				break;
			case 5: // Dragon Knight
				if (10 <= wis && wis <= 65)
					magicResistance = 18 + (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 238;
				break;
			case 6: // Illusionist
				if (14 <= wis && wis <= 65)
					magicResistance = 20 + (wis - 10) * 4;
				else if (wis > 65)
					magicResistance = 240;
				break;
			default:
				break;
			}
			return magicResistance;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


}

