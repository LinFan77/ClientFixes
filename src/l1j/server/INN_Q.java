package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.INN.INN;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

public class INN_Q implements Runnable {
	private final Queue<INN_IND> _queue;

	public INN_Q() {
		_queue = new ConcurrentLinkedQueue<>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(INN_IND _ind) {
		_queue.offer(_ind);
	}

	@Override
	@SuppressWarnings("static-access")
	public void run() {
		while (true) {
			try {
				Thread.sleep(30L);
				synchronized (this) // in theory, due to timing issues, is possible to enter twice,very low probability.
				{
					INN_IND _ind = _queue.peek();
					if (_ind == null) {
						continue;
					}
					final L1PcInstance player = L1World.getInstance().getPlayer(_ind._pcname);
					if ((player == null) || player.enteringDungeon) {
						_queue.remove();
						continue;
					}

					player.enteringDungeon = true;
					GeneralThreadPool.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							player.enteringDungeon = false;
						}
					}, 3000);
					INN.getInstance();
					INN.INNStart(player, _ind._objid, _ind._npcid, _ind._type, _ind._count);
					/*
					 * if(_ind._type == 0 ){ // room rental
					 *
					 * }else if(_ind._type == 1 || _ind._type == 2){// frozen a }
					 */
					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
