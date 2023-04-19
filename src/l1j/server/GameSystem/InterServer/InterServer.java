package l1j.server.GameSystem.InterServer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.C_SelectCharacter;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_LoginResult;
import l1j.server.server.serverpackets.S_ServerVersion;
import l1j.server.server.serverpackets.S_Unknown1;
import server.LineageClient;
public class InterServer implements Runnable {

	private static InterServer _instance;
	private static Queue<LineageClient> _queue;

	public static InterServer getInstance() {
		if (_instance == null) {
			_instance = new InterServer();
		}
		return _instance;
	}

	public InterServer() {
		_queue = new ConcurrentLinkedQueue<>();
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	public static void RequestInterServer(LineageClient Client) {
		_queue.offer(Client);
	}

	@Override
	public void run() {
		while (true) {
			try {
				LineageClient Client; L1PcInstance Player;
				String InterServerName;

				synchronized (this) {
					try {
						Thread.sleep(10L);
						Client = _queue.poll();
						if(Client == null) continue;

						InterServerName = Client.getInterServerName();
						Player = L1World.getInstance().getPlayer(InterServerName);
						if(Player == null) {
							/** Character information update */
							Client.sendPacket(new S_ServerVersion(S_ServerVersion.ServerInter, 1, null));
							Client.sendPacket(new S_LoginResult());
							Client.sendPacket(new S_Unknown1(0));

							/** When cleanup finished, process as login packet */
							new C_SelectCharacter(Client, Client.getInterServerName());

							/** If completed, reset the interserver memory information slightly */
							Client.setInterServerName(null);
						} else _queue.offer(Client);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
