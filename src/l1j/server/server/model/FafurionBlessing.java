package l1j.server.server.model;

import java.util.TimerTask;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillSound;

public class FafurionBlessing extends TimerTask {
	// private static Logger _log =
	// Logger.getLogger(PapuBlessing.class.getName());

	private final L1PcInstance _pc;

	public FafurionBlessing(L1PcInstance pc) {
		_pc = pc;
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead()) {
				return;
			}
			fafurionRegen();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}


	public void fafurionRegen() {
		_pc.sendPackets(new S_SkillSound(_pc.getId(), 2245));
		_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2245));
		}
}

