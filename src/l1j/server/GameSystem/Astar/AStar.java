package l1j.server.GameSystem.Astar;

import java.util.ArrayList;
import java.util.List;

import javolution.util.FastTable;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1TrapInstance;

public class AStar {

	// open node, closed node list
	Node OpenNode, ClosedNode;
	private L1NpcInstance _npc = null;

	public void setnpc(L1NpcInstance npc) {
		_npc = npc;
	}

	// looping times
	static final int LIMIT_LOOP = 200;
	// private List<Node> pool;
	// private List<Node> sabu;
	private FastTable<Node> pool;
	private FastTable<Node> sabu;

	private Node getPool() {
		Node node;
		if (pool.size() > 0) {
			node = pool.get(0);
			pool.remove(0);
		} else {
			node = new Node();
		}
		return node;
	}

	private void setPool(Node node) {
		if (node != null) {
			node.close();
			if (isPoolAppend(pool, node))
				pool.add(node);
		}
	}

	// *************************************************************************
	// Name : AStar()
	// Desc : constructor
	// *************************************************************************
	public AStar() {
		// sabu = new ArrayList<Node>();
		sabu = new FastTable<>();
		OpenNode = null;
		ClosedNode = null;
		// pool = new ArrayList<Node>();
		pool = new FastTable<>();
	}

	public void clear() {
		for (Node s : sabu) {
			try {
				s.close();
			} catch (Exception e) {
			}
			s.clear();
		}
		for (Node s2 : pool) {
			try {
				s2.close();
			} catch (Exception e) {
			}
			s2.clear();
		}
		OpenNode = null;
		ClosedNode = null;
		sabu.clear();
		pool.clear();
		sabu = null;
		pool = null;
	}

	// *************************************************************************
	// Name : ResetPath()
	// Desc : Remove previously created routes
	// *************************************************************************
	public void cleanTail() {
		Node tmp;
		int cnt = 0;
		while (OpenNode != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			tmp = OpenNode.next;
			setPool(OpenNode);
			OpenNode = tmp;
		}
		cnt = 0;
		while (ClosedNode != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					ClosedNode = null;
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			tmp = ClosedNode.next;
			setPool(ClosedNode);
			ClosedNode = tmp;
		}

		/*
		 * if(cnt > 5000){
		 * System.out.println("인서트 이름 "+_npc.getName()+" x:"+_npc
		 * .getX()+" y:"+_npc.getY()+" m:"+_npc.getMapId());
		 * System.out.println(_npc.isDead()); L1PcInstance[] gm =
		 * Config.toArray접속채팅모니터(); gm[0].dx= _npc.getX(); gm[0].dy=
		 * _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
	}

	// *************************************************************************
	// Name : FindPath()
	// Desc : Receives the start position and the target position and returns a list of path nodes
	// *************************************************************************
	// monster coordinates sx, xy
	// Coordinates to move tx, ty
	public Node searchTail(L1Object o, int tx, int ty, int m, boolean obj) {
		int calcx = o.getX() - tx;
		int calcy = o.getY() - ty;
		if (o instanceof L1RobotInstance) {
			if (o.getMapId() != m || Math.abs(calcx) > 40
					|| Math.abs(calcy) > 40) {
				return null;
			}
		} else if (o.getMapId() != m || Math.abs(calcx) > 30
				|| Math.abs(calcy) > 30) {
			/*
			 * if(o instanceof L1NpcInstance){ L1NpcInstance npp =
			 * (L1NpcInstance)o; if(npp.getNpcId() >=100750 && npp.getNpcId() <=
			 * 100757){
			 *
			 * }else{ return null; } }
			 */

		}
		Node src, best = null;
		int count = 0;
		int sx = o.getX();
		int sy = o.getY();

		// Create the first start node
		src = getPool();
		src.g = 0;
		src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
		src.f = src.h;
		src.x = sx;
		src.y = sy;

		// Add start node to open node list
		OpenNode = src;

		// Pathfinding main loop
		// Stop pathfinding when the maximum number of iterations is exceeded
		while (count < LIMIT_LOOP) {
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				}
			}
			// If there are no open nodes, all nodes have been searched, so pathfinding is stopped.
			if (OpenNode == null) {
				// System.out.println("There is no open place");
				return null;
			}

			// Get the first node of the open node and remove it from the open node
			best = OpenNode;
			OpenNode = best.next;

			// Add Imported Nodes to Closed Nodes
			best.next = ClosedNode;
			ClosedNode = best;

			// If the currently imported node is the target node, pathfinding is successful
			if (best.x == tx && best.y == ty) {
				return best;
			}

			// Expands the current node and adjacent nodes and adds them as open nodes.
			if (MakeChild(o, best, tx, ty, obj) == 0 && count == 0) {
				// System.out.println("it's blocked...");
				return null;
			}

			count++;
		}

		return null;
	}

	// *************************************************************************
	// Name : MakeChild()
	// Desc : Expands to adjacent nodes of the input node
	// *************************************************************************
	// Re-modified to fit the Lineage environment by sabu
	private char 메이크차일드(L1Object o, Node node, int tx, int ty, boolean obj) {
		int x, y;
		char flag = 0;

		x = node.x;
		y = node.y;
		boolean ckckck = false;
		/*
		 * if(o instanceof L1NpcInstance){ L1NpcInstance npp = (L1NpcInstance)o;
		 * if(npp.getNpcId() >=100750 && npp.getNpcId() <= 100757){ ckckck =
		 * true; } }
		 */
		// Check if it is possible to move to an adjacent node
		for (int i = 0; i < 8; ++i) {
			if (ckckck || World.isThroughObject(x, y, o.getMapId(), i)) {
				int nx = x + getXY(i, true);
				int ny = y + getXY(i, false);
				boolean ck = true;
				// There is no need to search for the coordinates of the goal point.
				if (tx != nx || ty != ny) {
					if (obj) {
						if (o instanceof L1DollInstance) {
							ck = true;
						} else if (World.moveTheDoor(x, y, o.getMapId(), i)) {
							if (o instanceof L1MonsterInstance) {
								L1MonsterInstance Mon = (L1MonsterInstance) o;
								if(!(Mon.getNpcId() >= 46410 && Mon.getNpcId() <= 46483)){
									ck = false;
								}
							}else ck = false;
						} else {
							ck = !World.isMapdynamic(nx, ny, o.getMapId());
						}
						if (ck && o instanceof L1RobotInstance) {
							// ck = L1World.getInstance().getVisiblePoint(new
							// L1Location(nx, ny, o.getMapId()), 0).size() == 0;
							try {
								ArrayList<L1Object> list = L1World
										.getInstance().getVisiblePoint(
												new L1Location(nx, ny,
														o.getMapId()), 0);
								if (list.size() > 0) {
									for (L1Object temp_obj : list) {
										if (temp_obj instanceof L1DollInstance
												|| temp_obj instanceof L1Inventory
												|| temp_obj instanceof L1TrapInstance) {
										} else {
											ck = false;
											break;
										}
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (ck) {
					MakeChildSub(node, nx, ny, o.getMapId(), tx, ty);
					flag = 1;
				} else if (tx != nx || ty != ny) {
					sabu.add(node);
				}
			} else {

			}
		}
		return flag;
	}

	// *************************************************************************
	// Name : FindPath()
	// Desc : Find a location close by..
	// *************************************************************************
	// monster coordinates sx, xy
	// Coordinates to move tx, ty
	public Node 근접서치타일(L1Object o, int tx, int ty, int m, boolean obj) {
		int calcx = o.getX() - tx;
		int calcy = o.getY() - ty;
		if (o instanceof L1RobotInstance) {
			if (o.getMapId() != m || Math.abs(calcx) > 40
					|| Math.abs(calcy) > 40) {
				return null;
			}
		} else if (o.getMapId() != m || Math.abs(calcx) > 30
				|| Math.abs(calcy) > 30) {
			/*
			 * if(o instanceof L1NpcInstance){ L1NpcInstance npp =
			 * (L1NpcInstance)o; if(npp.getNpcId() >=100750 && npp.getNpcId() <=
			 * 100757){ //Broadcaster.broadcastPacket(npp, new
			 * S_NpcChatPacket(npp, "1111111111111111111", 0)); }else{ return
			 * null; } }
			 */

		}

		Node src, best = null;
		int count = 0;
		int sx = o.getX();
		int sy = o.getY();

		// Create the first start node
		src = getPool();
		src.g = 0;
		src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
		src.f = src.h;
		src.x = sx;
		src.y = sy;

		// Add start node to open node list
		OpenNode = src;

		// pathfinding main loop
		// Stop pathfinding when the maximum number of iterations is exceeded
		while (count < LIMIT_LOOP) {
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				}
			}
			// If there are no open nodes, all nodes have been searched, so stop pathfinding
			if (OpenNode == null) {
				// System.out.println("no place open");
				return null;
			}

			// Get the first node of the open node and remove it from the open node
			best = OpenNode;
			OpenNode = best.next;

			// Add Imported Nodes to Closed Nodes
			best.next = ClosedNode;
			ClosedNode = best;

			// If the currently imported node is the target node, pathfinding is successful
			if (best.x == tx && best.y == ty) {
				return best;
			}

			// Expands the current node and adjacent nodes and adds them as open nodes.
			if (메이크차일드(o, best, tx, ty, obj) == 0 && count == 0) {
				// System.out.println("I'm blocked..");
				return null;
			}

			count++;
		}
		int tmpdis = 0;
		for (Node saNode : sabu) {
			int x = saNode.x;
			int y = saNode.y;
			saNode.h = (tx - x) * (tx - x) + (ty - y) * (ty - y);
			if (tmpdis == 0) {
				best = saNode;
				tmpdis = saNode.h;
			}
			if (tmpdis > saNode.h) {
				best = saNode;
				tmpdis = saNode.h;
			}
		}

		if (best == null
				|| best.h >= (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy)) {
			return null;
		}
		if (sabu.size() > 0)
			sabu.clear();
		return best;
	}

	// *************************************************************************
	// Name : MakeChild()
	// Desc : Expands to adjacent nodes of the input node
	// *************************************************************************
	// Re-modified to fit the Lineage environment by sabu

	private char MakeChild(L1Object o, Node node, int tx, int ty, boolean obj) {
		int x, y;
		char flag = 0;

		x = node.x;
		y = node.y;
		boolean ckckck = false;
		/*
		 * if(o instanceof L1NpcInstance){ L1NpcInstance npp = (L1NpcInstance)o;
		 * if(npp.getNpcId() >=100750 && npp.getNpcId() <= 100757){ ckckck =
		 * true; //Broadcaster.broadcastPacket(npp, new S_NpcChatPacket(npp,
		 * "33333333333", 0)); } }
		 */
		// Check if it is possible to move to an adjacent node

		for (int i = 0; i < 8; ++i) {
			if (ckckck || World.isThroughObject(x, y, o.getMapId(), i)) {
				int nx = x + getXY(i, true);
				int ny = y + getXY(i, false);
				boolean ck = true;
				// There is no need to search for the coordinates of the goal point.
				if (tx != nx || ty != ny) {
					if (obj) {
						if (o instanceof L1DollInstance) {
							ck = true;
						} else if (World.moveTheDoor(x, y, o.getMapId(), i)) {
							if (o instanceof L1MonsterInstance) {
								L1MonsterInstance Mon = (L1MonsterInstance) o;
								if(!(Mon.getNpcId() >= 46410 && Mon.getNpcId() <= 46483)){
									ck = false;
								}
							}else ck = false;
						} else {
							ck = !World.isMapdynamic(nx, ny, o.getMapId());
						}
						if (ck && o instanceof L1RobotInstance) {
							if (o.getMap().isCombatZone(nx, ny))
								continue;
							// ck = L1World.getInstance().getVisiblePoint(new
							// L1Location(nx, ny, o.getMapId()), 0).size() == 0;
							ArrayList<L1Object> list = L1World
									.getInstance()
									.getVisiblePoint(
											new L1Location(nx, ny, o.getMapId()),
											0);
							if (list.size() > 0) {
								for (L1Object temp_obj : list) {
									if (temp_obj instanceof L1DollInstance
											|| temp_obj instanceof L1Inventory
											|| temp_obj instanceof L1TrapInstance) {
									} else {
										ck = false;
										break;
									}
								}
							}
						}
					}
				}
				if (ck) {
					MakeChildSub(node, nx, ny, o.getMapId(), tx, ty);
					flag = 1;
				}
			} else {

			}
		}

		return flag;
	}

	// *************************************************************************
	// Name : MakeChildSub()
	// Desc : create a node. If the node is already in an open node or a closed node
	// Modify the information if f is smaller compared to the previous value
	// If it is a closed node, modify the information of all nodes connected to it as well
	// *************************************************************************
	void MakeChildSub(Node node, int x, int y, int m, int tx, int ty) {
		Node old = null, child = null;
		int g = node.g + 1;
		// if the current node is on an open node and f is smaller then modify the information
		if ((old = IsOpen(x, y, m)) != null) {
			if (g < old.g) {
				old.prev = node;
				old.g = g;
				old.f = old.h + old.g;
			}

			// If the current node is in a closed node and f is smaller then modify the information
		} else if ((old = IsClosed(x, y, m)) != null) {
			if (g < old.g) {
				old.prev = node;
				old.g = g;
				old.f = old.h + old.g;
			}
			// If it is a new node, create node information and add it to the open node
		} else {
			try {
				// create new node
				child = getPool();

				child.prev = node;
				child.g = g;
				child.h = (x - tx) * (x - tx) + (y - ty) * (y - ty);
				child.f = child.h + child.g;
				child.x = x;
				child.y = y;

				// Add new node to open node
				InsertNode(child);
			} catch (Exception e) {
			}
		}
	}

	// *************************************************************************
	// Name : IsOpen()
	// Desc : Check whether the entered node is an open node
	// *************************************************************************
	private Node IsOpen(int x, int y, int mapid) {
		Node tmp = OpenNode;
		int cnt = 0;
		while (tmp != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				} else if (cnt > 10000) {
					return null;
				}
			}
			// cnt++;
			if (tmp.x == x && tmp.y == y) {
				return tmp;
			}
			tmp = tmp.next;
		}

		/*
		 * if(cnt > 5000){
		 * System.out.println(cnt+" 오픈 x :"+x+" y :"+y+" m :"+mapid);
		 * System.out.
		 * println(" 이름"+_npc.getName()+" x:"+_npc.getX()+" y:"+_npc.getY
		 * ()+" m:"+_npc.getMapId()); System.out.println(_npc.isDead());
		 * L1PcInstance[] gm = Config.toArray접속채팅모니터(); gm[0].dx= _npc.getX();
		 * gm[0].dy= _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
		return null;
	}

	// *************************************************************************
	// Name : IsClosed()
	// Desc : Check whether the entered node is closed
	// *************************************************************************
	private Node IsClosed(int x, int y, int mapid) {
		Node tmp = ClosedNode;
		int cnt = 0;
		while (tmp != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				} else if (cnt > 10000) {
					return null;
				}
			}
			// cnt ++;
			if (tmp.x == x && tmp.y == y) {
				return tmp;
			}
			tmp = tmp.next;
		}
		/*
		 * if(cnt > 5000){
		 * System.out.println(cnt+" 클로즈 x :"+x+" y :"+y+" m :"+mapid);
		 * System.out
		 * .println(" 이름"+_npc.getName()+" x:"+_npc.getX()+" y:"+_npc.getY
		 * ()+" m:"+_npc.getMapId()); System.out.println(_npc.isDead());
		 * L1PcInstance[] gm = Config.toArray접속채팅모니터(); gm[0].dx= _npc.getX();
		 * gm[0].dy= _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
		return null;
	}

	// *************************************************************************
	// Name : InsertNode()
	// Desc : Add the input node to the open node by sorting it according to the f value
	// so that the one with the highest f value is at the top -> optimal node
	// *************************************************************************
	private void InsertNode(Node src) {
		Node old = null, tmp = null;
		int cnt = 0;
		if (OpenNode == null) {
			OpenNode = src;
			return;
		}
		tmp = OpenNode;
		while (tmp != null && (tmp.f < src.f)) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			old = tmp;
			tmp = tmp.next;
		}
		if (old != null) {
			src.next = tmp;
			old.next = src;
		} else {
			src.next = tmp;
			OpenNode = src;
		}
		/*
		 * if(cnt > 100000){
		 * System.out.println("인서트 이름 "+_npc.getName()+" x:"+_npc
		 * .getX()+" y:"+_npc.getY()+" m:"+_npc.getMapId());
		 * System.out.println(_npc.isDead()); L1PcInstance[] gm =
		 * Config.toArray접속채팅모니터(); gm[0].dx= _npc.getX(); gm[0].dy=
		 * _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
	}

	/**
	 * A function that checks if it is OK to add to pooling. : If too much
	 * registration becomes a problem, cover the appropriate line.
	 * java.lang.OutOfMemoryError: Java heap space
	 *
	 * @param c
	 * @return
	 */
	private boolean isPoolAppend(List<?> pool, Object c) {
		// Check the total number.
		return pool.size() < 200;
	}

	/**
	 * Return coordinate value settings appropriately according to direction and type
	 *
	 * @param h : direction
	 * @param type : true ? x : y
	 * @return
	 */
	public int getXY(final int h, final boolean type) {
		int loc = 0;
		switch (h) {
		case 0:
			if (!type)
				loc -= 1;
			break;
		case 1:
			if (type)
				loc += 1;
			else
				loc -= 1;
			break;
		case 2:
			if (type)
				loc += 1;
			break;
		case 3:
			loc += 1;
			break;
		case 4:
			if (!type)
				loc += 1;
			break;
		case 5:
			if (type)
				loc -= 1;
			else
				loc += 1;
			break;
		case 6:
			if (type)
				loc -= 1;
			break;
		case 7:
			loc -= 1;
			break;
		}
		return loc;
	}

	public int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}

}
