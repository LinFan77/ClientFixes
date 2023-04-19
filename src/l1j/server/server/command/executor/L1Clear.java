package l1j.server.server.command.executor;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillSound;

public class L1Clear implements L1CommandExecutor {

    private L1Clear() {
    }

    public static L1CommandExecutor getInstance() {
        return new L1Clear();
    }

    @Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
        for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 20)) { // Look for objects within range of 20
            if (obj instanceof L1MonsterInstance) { // if monster
                L1NpcInstance npc = (L1NpcInstance) obj;
                npc.receiveDamage(pc, 50000); // damage
                if (npc.getCurrentHp() <= 0) {
                    pc.sendPackets(new S_SkillSound(obj.getId(), 2059));
                    pc.broadcastPacket(new S_SkillSound(obj.getId(), 2059));
                } else {
                    pc.sendPackets(new S_SkillSound(obj.getId(), 2059));
                    pc.broadcastPacket(new S_SkillSound(obj.getId(), 2059));
                }
            } else if (obj instanceof L1PcInstance) { // for players
                L1PcInstance player = (L1PcInstance) obj;
                player.receiveDamage(player, 0); // damage
                if (player.getCurrentHp() <= 0) {
                	return;
                }
            }
        }
    }
}