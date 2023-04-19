package l1j.server.Warehouse;

public class WarehouseManager {
	private static WarehouseManager uniqueInstance = null;
	private PrivateWarehouseList plist = new PrivateWarehouseList();
	private ElfWarehouseList elist = new ElfWarehouseList();
	private ClanWarehouseList clist = new ClanWarehouseList();
	private PackageWarehouseList packlist = new PackageWarehouseList();
	private SupplementaryServiceList supplist = new SupplementaryServiceList();

	public static WarehouseManager getInstance() {
		if (uniqueInstance == null) {
			synchronized (WarehouseManager.class) {
				if (uniqueInstance == null)
					uniqueInstance = new WarehouseManager();
			}
		}
		return uniqueInstance;
	}

	public PrivateWarehouse getPersonalStorage(String name) {
		return (PrivateWarehouse) plist.findWarehouse(name);
	}

	public ElfWarehouse getElfStorage(String name) {
		return (ElfWarehouse) elist.findWarehouse(name);
	}

	public ClanWarehouse getPledgeStorage(String name) {
		return (ClanWarehouse) clist.findWarehouse(name);
	}

	public PackageWarehouse getPackageStorage(String name) {
		return (PackageWarehouse) packlist.findWarehouse(name);
	}

	public SupplementaryService getSupplementaryService(String name) {
		return (SupplementaryService) supplist.findWarehouse(name);
	}

	public void deletePersonalStorage(String name) {
		plist.delWarehouse(name);
	}

	public void deleteElfStorage(String name) {
		elist.delWarehouse(name);
	}

	public void deletePledgeStorage(String name) {
		clist.delWarehouse(name);
	}

	public void deletePackageStorage(String name) {
		packlist.delWarehouse(name);
	}

	public void delSupplementaryService(String name) {
		supplist.delWarehouse(name);
	}
}

