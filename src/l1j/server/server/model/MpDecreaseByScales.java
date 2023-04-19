package l1j.server.server.model;

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;

import java.util.TimerTask;

import l1j.server.server.model.Instance.L1PcInstance;

public class MpDecreaseByScales extends TimerTask {
	// private static Logger _log =
	// Logger.getLogger(MpDecreaseByScales.class.getName());

	private final L1PcInstance _pc;

	public MpDecreaseByScales(L1PcInstance pc) {
		_pc = pc;
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead() || _pc.getCurrentMp() < 4) {
				killSkill();
				return;
			}
			regenMp();
		} catch (Throwable e) {
			e.printStackTrace();
			// _log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}

	public void regenMp() {
		int newMp = _pc.getCurrentMp() - 6;

		_pc.setCurrentMp(newMp);
	}

	public void killSkill() {
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(AWAKEN_ANTHARAS)) {
			_pc.getSkillEffectTimerSet().removeSkillEffect(AWAKEN_ANTHARAS);
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				AWAKEN_FAFURION)) {
			_pc.getSkillEffectTimerSet().removeSkillEffect(AWAKEN_FAFURION);
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				AWAKEN_VALAKAS)) {
			_pc.getSkillEffectTimerSet().removeSkillEffect(AWAKEN_VALAKAS);
		}
	}

}

