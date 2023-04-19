package l1j.server.server.model.skill.skills;

import static l1j.server.server.model.skill.L1SkillId.STATUS_힘업6;
import static l1j.server.server.model.skill.L1SkillId.STATUS_힘업7;

import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Strup;

public class PhysicalEnchantStr {

	public static void runSkill(L1Character cha, int buffIconDuration) {
		L1PcInstance pc = (L1PcInstance) cha;
		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_힘업6))
			pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_힘업6);
		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_힘업7))
			pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_힘업7);
		pc.getAbility().addAddedStr((byte) 5);
		pc.sendPackets(new S_Strup(pc, 5, buffIconDuration));
	}

}

