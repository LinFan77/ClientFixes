/**
 * From. LinFreedom
 * 캐릭터 스테이츠  검사
 *
 *
 *
 * */
package l1j.server.server.utils;

import l1j.server.server.model.Instance.L1PcInstance;

public class CheckInitStat {

	// KKK character's minimum stat check
	private CheckInitStat() {
	}

	/**
	 * Check your character's minimum stats
	 *
	 * @param pc
	 * @return true: normal or operator, false: abnormal
	 */
	public static boolean CheckPcStat(L1PcInstance pc) {
		if (pc == null) { // if you don't have a pc
			return false;
		}
		if (pc.isGm()) { // If the PC is the operator
			return true;
		}

		int str = pc.getAbility().getBaseStr();
		int dex = pc.getAbility().getBaseDex();
		int cha = pc.getAbility().getBaseCha();
		int con = pc.getAbility().getBaseCon();
		int intel = pc.getAbility().getBaseInt();
		int wis = pc.getAbility().getBaseWis();
		int basestr = 0;
		int basedex = 0;
		int basecon = 0;
		int baseint = 0;
		int basewis = 0;
		int basecha = 0;
		switch (pc.getType()) {
		case 0: // Royal
			basestr = 13;
			basedex = 10;
			basecon = 10;
			basewis = 11;
			basecha = 13;
			baseint = 10;
			break;
		case 1: // Knight
			basestr = 16;
			basedex = 12;
			basecon = 14;
			basewis = 9;
			basecha = 12;
			baseint = 8;
			break;
		case 2: // Elf
			basestr = 11;
			basedex = 12;
			basecon = 12;
			basewis = 12;
			basecha = 9;
			baseint = 12;
			break;
		case 3: // Wizard
			basestr = 8;
			basedex = 7;
			basecon = 12;
			basewis = 12;
			basecha = 8;
			baseint = 12;
			break;
		case 4: // Dark Elf
			basestr = 12;
			basedex = 15;
			basecon = 8;
			basewis = 10;
			basecha = 9;
			baseint = 11;
			break;
		case 5: // Dragon Knight
			basestr = 13;
			basedex = 11;
			basecon = 14;
			basewis = 12;
			basecha = 8;
			baseint = 11;
			break;
		case 6: // Illusionist
			basestr = 11;
			basedex = 10;
			basecon = 12;
			basewis = 12;
			basecha = 8;
			baseint = 12;
			break;
		}

		if (str < basestr || dex < basedex || con < basecon || cha < basecha
				|| intel < baseint || wis < basewis) { // If it is less than the initial stat
			return false;
		}
		return true;
	}
}

