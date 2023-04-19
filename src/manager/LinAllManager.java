package manager;

import java.awt.EventQueue;
import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.Account;
import l1j.server.server.datatables.CastleTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.IpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MobSkillTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SpawnTable;
import l1j.server.server.datatables.WeaponAddDamage;
import l1j.server.server.datatables.WeaponSkillTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Warehouse.PrivateWarehouse;
import l1j.server.server.model.Warehouse.WarehouseManager;
import l1j.server.server.model.item.L1TreasureBox;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.storage.CharactersItemStorage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.SQLUtil;
import manager.dialog.DropMonsterFind;
import manager.dialog.PresentDialog;
import manager.dialog.ShopNpcFind;
import server.GameServer;
import server.Server;

// Boss Appearance Notification Message


public class LinAllManager {

	public static Shell shlInbumserverManager;
	private static Text txtInbumserverByleaf;

	private static LinAllManager _instance;

	static public final String SERVER_VERSION = "0.04a";


	/**
	 * @wbp.parser.entryPoint
	 */
	public static LinAllManager getInstance() {
		if (_instance == null) {
			_instance = new LinAllManager();
			_instance.open();
			//_instance.start(null);
		}
		return _instance;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public LinAllManager(){
		//open();
	}
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint*/

	public static void main(String[] args) {
		try {
			new Server();

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					LinAllManager.getInstance();
				}
			});

			//Manager window = new Manager();
			//window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Display display;
	private static Menu menu;
	private static MenuItem mntmNewSubmenu;
	private static Menu menu_1;
	private static Text text_1;
	private static Text chatText;
	private static Text text_2;
	private static Text txtWhisper;
	private static Text text_5;
	private static Text text_6;
	private static Text txtTime_2;
	private static Text txtTime_3;
	private static Text text_10;
	private static Table table;
	private static List listCharacters;
	private static Text txtWarehouse;
	private static Text text_4;
	private static Text text_7;
	private static Label lblEXPRATE;
	private static Label lblNewLabel_6;
	private static Label lblADENARATE;
	private static Label lblNewLabel_8;
	private static Label lblDROPRATE;
	@SuppressWarnings("unused")
	private static Label label_6;
	@SuppressWarnings("unused")
	private static Label label_7;
	@SuppressWarnings("unused")
	private static Label label_8;
	@SuppressWarnings("unused")
	private static Label label_9;
	private static Label lblNewLabel_1;
	private static Label lblThreadCountNum;
	private static Label lblMbText;
	private static Label lblTxtEXP;
	private static Label lblNewLabel;
	private static Label lblMemoryNum;
	private static Label lblClassIcon;
	private static Label lblPlayerNameText;
	private static Label lblNewLabel_26;
	private static Label lblLoginText;
	private static Label lblPledgeText;
	private static Label lblHPText;
	private static Label lblNewLabel_30;

	private String errorMessage01 = "That player is not currently logged into the game server.";

	@SuppressWarnings("unused")
	private static Label lblMp;
	private static Label label_10;
	private static Label lblCONNum;
	private static Label lblINTNum;
	private static Label lblDEXNum;
	private static Label lblWISNum;
	private static Label lblCHANum;
	private static Label lblStr;
	private static Label lblCon;
	private static Label lblInt;
	private static Label lblSTRNum;
	private static Label lblDex;
	private static Label lblWis;
	private static Label lblCha;
	private static Label lblSPText;
	private static Label lblMRText;
	private static Label lblMRNum;
	private static Label lblSPNum;
	private static Label lblERText;
	private static Label lblDGText;
	private static Label label_18;
	private static Label label_19;
	private static Label lblEvaTimeName;
	private static Label lblNewLabel_34;
	private static Label lblNewLabel_35;
	private static Label lblNewLabel_36;
	private static Label lblNewLabel_37;
	private static Label lblNewLabel_38;
	private static Label lblNewLabel_39;
	private static Label lblDethCount;
	private static Label lblNewLabel_40;
	private static Label label_20;
	private static ProgressBar progressBar_1;
	private static ProgressBar progressBar_2;
	private static Button btnCheckButton;
	private static TreeItem trtmNewTreeitem;
	private static Label lblNewLabel_49;
	private static Label label_27;
	private static Tree tree;
	private static Label lblNewLabel_47;
	private static Label lblNewLabel_46;
	private static Label label_28;
	private static Label label_25;
	private static TreeItem trtmNewTreeitem_1;
	private Table table_2;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {

		display = Display.getDefault();
		createContents();
		/*shlInbumserverManager.setBounds((display.getBounds().width / 2) - (shlInbumserverManager.getBounds().width / 2),
				(display.getBounds().height / 2) - (shlInbumserverManager.getBounds().height / 2),
				shlInbumserverManager.getBounds().width, shlInbumserverManager.getBounds().height);*/
		shlInbumserverManager.setBounds(10, 10, 1400, 900);

				CTabFolder tabFolderLogs = new CTabFolder(shlInbumserverManager, SWT.BORDER);
				tabFolderLogs.setBounds(860, 562, 513, 268);
				tabFolderLogs.setForeground(SWTResourceManager.getColor(0, 0, 0));
				tabFolderLogs.setSelectionForeground(SWTResourceManager.getColor(255, 255, 255));
				tabFolderLogs.setBackground(SWTResourceManager.getColor(204, 204, 204));
				tabFolderLogs.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

						CTabItem tbtmNewItem = new CTabItem(tabFolderLogs, SWT.NONE);
						tbtmNewItem.setText("All");

								tbtmNewItem_16 = new CTabItem(tabFolderLogs, SWT.NONE);
								tbtmNewItem_16.setText("Common");

										text_8 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
										text_8.setForeground(SWTResourceManager.getColor(255, 255, 255));
										text_8.setEditable(false);
										text_8.setBackground(SWTResourceManager.getColor(51, 51, 51));
										tbtmNewItem_16.setControl(text_8);

												CTabItem tbtmNewItem_1 = new CTabItem(tabFolderLogs, SWT.NONE);
												tbtmNewItem_1.setText("Whisper");

														txtWhisper = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
														txtWhisper.setForeground(SWTResourceManager.getColor(255, 255, 255));
														txtWhisper.setEditable(false);
														txtWhisper.setBackground(SWTResourceManager.getColor(51, 51, 51));
														tbtmNewItem_1.setControl(txtWhisper);

																CTabItem tbtmNewItem_2 = new CTabItem(tabFolderLogs, SWT.NONE);
																tbtmNewItem_2.setText("Pledge");

																		text_5 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																		text_5.setForeground(SWTResourceManager.getColor(255, 255, 255));
																		text_5.setEditable(false);
																		text_5.setBackground(SWTResourceManager.getColor(51, 51, 51));
																		tbtmNewItem_2.setControl(text_5);

																				CTabItem tbtmNewItem_3 = new CTabItem(tabFolderLogs, SWT.NONE);
																				tbtmNewItem_3.setText("Party");

																						text_6 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																						text_6.setForeground(SWTResourceManager.getColor(255, 255, 255));
																						text_6.setEditable(false);
																						text_6.setBackground(SWTResourceManager.getColor(51, 51, 51));
																						tbtmNewItem_3.setControl(text_6);

																								CTabItem tbtmNewItem_5 = new CTabItem(tabFolderLogs, SWT.NONE);
																								tbtmNewItem_5.setText("Trade");

																										txtTime_2 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																										txtTime_2.setForeground(SWTResourceManager.getColor(255, 255, 255));
																										txtTime_2.setEditable(false);
																										txtTime_2.setBackground(SWTResourceManager.getColor(51, 51, 51));
																										tbtmNewItem_5.setControl(txtTime_2);

																												CTabItem tabItem = new CTabItem(tabFolderLogs, SWT.NONE);
																												tabItem.setText("Warehouse");

																														txtWarehouse = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																														txtWarehouse.setForeground(SWTResourceManager.getColor(255, 255, 255));
																														txtWarehouse.setEditable(false);
																														txtWarehouse.setBackground(SWTResourceManager.getColor(51, 51, 51));
																														tabItem.setControl(txtWarehouse);

																																CTabItem tbtmP = new CTabItem(tabFolderLogs, SWT.NONE);
																																tbtmP.setText("E-Warehouse");

																																		text_4 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																																		text_4.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																		text_4.setEditable(false);
																																		text_4.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																		tbtmP.setControl(text_4);

																																				CTabItem tbtmNewItem_6 = new CTabItem(tabFolderLogs, SWT.NONE);
																																				tbtmNewItem_6.setText("Enchant");

																																						txtTime_3 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																																						txtTime_3.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																						txtTime_3.setEditable(false);
																																						txtTime_3.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																						tbtmNewItem_6.setControl(txtTime_3);

																																								CTabItem tbtmNewItem_7 = new CTabItem(tabFolderLogs, SWT.NONE);
																																								tbtmNewItem_7.setText("Pick Up");

																																										text_10 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																																										text_10.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																										text_10.setEditable(false);
																																										text_10.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																										tbtmNewItem_7.setControl(text_10);

																																												CTabItem tbtmNewItem_12 = new CTabItem(tabFolderLogs, SWT.NONE);
																																												tbtmNewItem_12.setText("Dead");

																																														text_7 = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																																														text_7.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																														text_7.setEditable(false);
																																														text_7.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																														tbtmNewItem_12.setControl(text_7);
																																														tabFolderLogs.setSelection(tbtmNewItem);


																																																chatText = new Text(tabFolderLogs, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
																																																tbtmNewItem.setControl(chatText);
																																																chatText.setEnabled(false);
																																																chatText.setEditable(false);

																																																		chatText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																		chatText.setBackground(SWTResourceManager.getColor(51, 51, 51));

																																																		tabItem_6 = new CTabItem(tabFolderLogs, SWT.NONE);
																																																		tabItem_6.setText("New Item");

		Composite composite = new Composite(shlInbumserverManager, SWT.NONE);
		composite.setBackground(org.eclipse.wb.swt.SWTResourceManager.getColor(51, 51, 51));
		composite.setBounds(1056, 10, 317, 531);

		TabFolder tabFolder_3 = new TabFolder(composite, SWT.NONE);
		tabFolder_3.setSize(323, 531);




		tabItem_1 = new TabItem(tabFolder_3, SWT.NONE);
		tabItem_1.setText("Boss");

				text_9 = new Text(tabFolder_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
				text_9.setForeground(SWTResourceManager.getColor(255, 255, 255));
				text_9.setEditable(false);
				text_9.setBackground(SWTResourceManager.getColor(51, 51, 51));
				tabItem_1.setControl(text_9);


				tbtmGm = new TabItem(tabFolder_3, SWT.NONE);
				tbtmGm.setText("GM");

						text_11 = new Text(tabFolder_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
						text_11.setForeground(SWTResourceManager.getColor(255, 255, 255));
						text_11.setEditable(false);
						text_11.setBackground(SWTResourceManager.getColor(51, 51, 51));
						tbtmGm.setControl(text_11);


						tabItem_2 = new TabItem(tabFolder_3, SWT.NONE);
						tabItem_2.setText("Dungeon");

								text_12 = new Text(tabFolder_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
								text_12.setForeground(SWTResourceManager.getColor(255, 255, 255));
								text_12.setEditable(false);
								text_12.setBackground(SWTResourceManager.getColor(51, 51, 51));
								tabItem_2.setControl(text_12);

								tabItem_3 = new TabItem(tabFolder_3, SWT.NONE);
								tabItem_3.setText("Snack");

										text_13 = new Text(tabFolder_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
										text_13.setForeground(SWTResourceManager.getColor(255, 255, 255));
										text_13.setEditable(false);
										text_13.setBackground(SWTResourceManager.getColor(51, 51, 51));
										tabItem_3.setControl(text_13);


										tabItem_4 = new TabItem(tabFolder_3, SWT.NONE);
										tabItem_4.setText("Siege");

												text_14 = new Text(tabFolder_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
												text_14.setForeground(SWTResourceManager.getColor(255, 255, 255));
												text_14.setEditable(false);
												text_14.setBackground(SWTResourceManager.getColor(51, 51, 51));
												tabItem_4.setControl(text_14);


												tabItem_5 = new TabItem(tabFolder_3, SWT.NONE);
												tabItem_5.setText("Produce");

														text_15 = new Text(tabFolder_3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
														text_15.setForeground(SWTResourceManager.getColor(255, 255, 255));
														text_15.setEditable(false);
														text_15.setBackground(SWTResourceManager.getColor(51, 51, 51));
														tabItem_5.setControl(text_15);

																																																																Composite composite_6 = new Composite(shlInbumserverManager, SWT.NONE);
																																																																composite_6.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																composite_6.setBounds(0, 0, 854, 841); // window bg
																																																																String srvVer = Config.SERVER_VERSION;

																																																																		txtInbumserverByleaf = new Text(composite_6, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
																																																																		txtInbumserverByleaf.setForeground(SWTResourceManager.getColor(240, 255, 255));
																																																																		txtInbumserverByleaf.setText("====================================="
																																																																				+ "\r\n\t\tL1-Open Server Manager"
																																																																				+ "\r\n\t\tServer Version: " + srvVer
																																																																				+ "\r\n\t\tGame Content: 9.2"
																																																																				+ "\r\n\t\tMaintained by: TeKniKo and the community!"
																																																																				+ "\r\n\t\tContact: discord.gg/BHBTnCw8Sy"
																																																																				+ "\r\n====================================="
																																																																				+ "\r\n[S]The server has started successfully.\r\n");
																																																																		txtInbumserverByleaf.setBackground(SWTResourceManager.getColor(0, 0, 0));
																																																																		txtInbumserverByleaf.setBounds(6, 10, 426, 821);
																																																																		listCharacters = new List(composite_6, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);

																																																																				listCharacters.setTouchEnabled(true);
																																																																				listCharacters.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																				listCharacters.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																				listCharacters.setItems(new String[] {});
																																																																				listCharacters.setBounds(438, 35, 99, 796);


																																																																						text_1 = new Text(composite_6, SWT.BORDER | SWT.CENTER);
																																																																						text_1.addKeyListener(new KeyAdapter() {
																																																																							@Override
																																																																							public void keyReleased(KeyEvent e) {
																																																																								if(e.keyCode == 13){
																																																																									charInfo(text_1.getText());
																																																																									accountInfo();
																																																																								}
																																																																							}
																																																																						});
																																																																						text_1.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																						text_1.setBackground(SWTResourceManager.getColor(102, 102, 102));
																																																																						text_1.setBounds(438, 10, 99, 19);

																																																																								CTabFolder tabFolder_1 = new CTabFolder(composite_6, SWT.BORDER);
																																																																								tabFolder_1.setBackground(SWTResourceManager.getColor(204, 204, 204));
																																																																								tabFolder_1.setSelectionForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																								tabFolder_1.setBounds(543, 10, 297, 225);
																																																																								tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

																																																																										CTabItem tbtmNewItem_8 = new CTabItem(tabFolder_1, SWT.NONE);
																																																																										tbtmNewItem_8.setText("Character Info");

																																																																												CTabItem tbtmNewItem_9 = new CTabItem(tabFolder_1, SWT.NONE);
																																																																												tbtmNewItem_9.setText("Inventory");

																																																																														table_2 = new Table(tabFolder_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
																																																																														table_2.setTouchEnabled(true);
																																																																														table_2.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																														table_2.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																														tbtmNewItem_9.setControl(table_2);
																																																																														table_2.setHeaderVisible(true);
																																																																														table_2.setLinesVisible(true);

																																																																																TableColumn tblclmnNewColumn_1 = new TableColumn(table_2, SWT.NONE);
																																																																																tblclmnNewColumn_1.setWidth(198);
																																																																																tblclmnNewColumn_1.setText("Name");

																																																																																		TableColumn tblclmnNewColumn_6 = new TableColumn(table_2, SWT.CENTER);
																																																																																		tblclmnNewColumn_6.setWidth(76);
																																																																																		tblclmnNewColumn_6.setText("Object");

																																																																																				Menu menu_4 = new Menu(table_2);
																																																																																				table_2.setMenu(menu_4);

																																																																																						MenuItem mntmNewItem_23 = new MenuItem(menu_4, SWT.NONE);
																																																																																						mntmNewItem_23.setText("More Info");

																																																																																								MenuItem mntmNewItem_21 = new MenuItem(menu_4, SWT.NONE);
																																																																																								mntmNewItem_21.setText("Edit Item");

																																																																																										new MenuItem(menu_4, SWT.SEPARATOR);

																																																																																												MenuItem mntmNewItem_22 = new MenuItem(menu_4, SWT.NONE);
																																																																																												mntmNewItem_22.addSelectionListener(new SelectionAdapter() {
																																																																																													@Override
																																																																																													public void widgetSelected(SelectionEvent e) {
																																																																																														if(table_2.getSelectionCount() <= 0){
																																																																																															MessageBox messageBox = new MessageBox(shlInbumserverManager,SWT.OK|SWT.ICON_INFORMATION);
																																																																																															messageBox.setMessage("There are no items selected.");
																																																																																															messageBox.open();
																																																																																															return;
																																																																																														}

																																																																																														MessageBox messageBox = new MessageBox(shlInbumserverManager,SWT.YES | SWT.NO |SWT.ICON_QUESTION);
																																																																																														messageBox.setMessage("Are you sure you want to delete the item?");
																																																																																														int type = messageBox.open();
																																																																																														if(type == SWT.YES){
																																																																																															itemdelete(table_2.getSelection());
																																																																																															charInfo(Pcname);

																																																																																														}
																																																																																													}
																																																																																												});
																																																																																												mntmNewItem_22.setText("Item Retrieval");

																																																																																																																CTabItem tbtmNewItem_11 = new CTabItem(tabFolder_1, SWT.NONE);
																																																																																																																tbtmNewItem_11.setText("Account Info");

																																																																																																																		Composite composite_3 = new Composite(tabFolder_1, SWT.NONE);
																																																																																																																		composite_3.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																		tbtmNewItem_11.setControl(composite_3);

																																																																																																																				tree = new Tree(composite_3, SWT.BORDER);
																																																																																																																				tree.addSelectionListener(new SelectionAdapter() {
																																																																																																																					@Override
																																																																																																																					public void widgetSelected(SelectionEvent e) {
																																																																																																																						if(e.detail == 0){
																																																																																																																							if(tree.getSelection()[0] == null)return;
																																																																																																																							String[] s = charlist.get(tree.getSelection()[0].getText());
																																																																																																																							/**tree*/
																																																																																																																							if(s == null)return;
																																																																																																																							lblNewLabel_47.setText(s[1]); //clan
																																																																																																																							lblNewLabel_46.setText(s[2]); //level
																																																																																																																							label_25.setText(s[3]); //hour
																																																																																																																							label_28.setText(s[4]); //full day

																																																																																																																						}
																																																																																																																					}
																																																																																																																				});
																																																																																																																				tree.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																				tree.setTouchEnabled(true);
																																																																																																																				tree.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																				tree.setBounds(10, 10, 137, 182);

																																																																																																																						TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.CENTER);
																																																																																																																						trclmnNewColumn.setWidth(132);
																																																																																																																						trclmnNewColumn.setText("Account");

																																																																																																																								trtmNewTreeitem = new TreeItem(tree, SWT.NONE);
																																																																																																																								trtmNewTreeitem.setChecked(true);
																																																																																																																								trtmNewTreeitem.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																								trtmNewTreeitem.setText("캐릭터 리스트");
																																																																																																																								trtmNewTreeitem.setExpanded(true);

																																																																																																																										lblNewLabel_42 = new Label(composite_3, SWT.NONE);
																																																																																																																										lblNewLabel_42.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																										lblNewLabel_42.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																										lblNewLabel_42.setBounds(153, 179, 116, 13);
																																																																																																																										lblNewLabel_42.setText("IP: 0.0.0.0");

																																																																																																																												Group group_2 = new Group(composite_3, SWT.NONE);
																																																																																																																												group_2.setForeground(SWTResourceManager.getColor(255, 0, 0));
																																																																																																																												group_2.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																												group_2.setText("Char Info");
																																																																																																																												group_2.setBounds(153, 5, 133, 96);

																																																																																																																														Label lblNewLabel_43 = new Label(group_2, SWT.NONE);
																																																																																																																														lblNewLabel_43.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																														lblNewLabel_43.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																														lblNewLabel_43.setBounds(10, 40, 28, 13);
																																																																																																																														lblNewLabel_43.setText("Lv: ");

																																																																																																																																Label lblNewLabel_45 = new Label(group_2, SWT.NONE);
																																																																																																																																lblNewLabel_45.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																lblNewLabel_45.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																lblNewLabel_45.setBounds(10, 59, 49, 13);
																																																																																																																																lblNewLabel_45.setText("Logout: ");

																																																																																																																																		lblNewLabel_46 = new Label(group_2, SWT.NONE);
																																																																																																																																		lblNewLabel_46.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																		lblNewLabel_46.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																		lblNewLabel_46.setBounds(44, 40, 79, 13);
																																																																																																																																		lblNewLabel_46.setText("0lv     00.00%");

																																																																																																																																				lblNewLabel_47 = new Label(group_2, SWT.CENTER);
																																																																																																																																				lblNewLabel_47.setAlignment(SWT.LEFT);
																																																																																																																																				lblNewLabel_47.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																				lblNewLabel_47.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																				lblNewLabel_47.setBounds(8, 21, 118, 13);
																																																																																																																																				lblNewLabel_47.setText("[==========]");

																																																																																																																																						label_25 = new Label(group_2, SWT.NONE);
																																																																																																																																						label_25.setText("0000.00.00  00:00:00");
																																																																																																																																						label_25.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																						label_25.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																						label_25.setBounds(10, 78, 113, 13);

																																																																																																																																								label_28 = new Label(group_2, SWT.NONE);
																																																																																																																																								label_28.setText("0일전");
																																																																																																																																								label_28.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																								label_28.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																								label_28.setBounds(60, 59, 63, 13);

																																																																																																																																										Group group_3 = new Group(composite_3, SWT.NONE);
																																																																																																																																										group_3.setText("Account Info");
																																																																																																																																										group_3.setForeground(SWTResourceManager.getColor(255, 0, 0));
																																																																																																																																										group_3.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																										group_3.setBounds(153, 107, 133, 66);

																																																																																																																																												Label lblNewLabel_48 = new Label(group_3, SWT.NONE);
																																																																																																																																												lblNewLabel_48.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																												lblNewLabel_48.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																												lblNewLabel_48.setBounds(10, 20, 24, 13);
																																																																																																																																												lblNewLabel_48.setText("ID: ");

																																																																																																																																														Label lblPassword = new Label(group_3, SWT.NONE);
																																																																																																																																														lblPassword.setText("PS: ");
																																																																																																																																														lblPassword.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																														lblPassword.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																														lblPassword.setBounds(10, 43, 24, 13);

																																																																																																																																																lblNewLabel_49 = new Label(group_3, SWT.NONE);
																																																																																																																																																lblNewLabel_49.setTouchEnabled(true);
																																																																																																																																																lblNewLabel_49.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																lblNewLabel_49.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																lblNewLabel_49.setBounds(38, 20, 92, 13);

																																																																																																																																																		label_27 = new Label(group_3, SWT.NONE);
																																																																																																																																																		label_27.setTouchEnabled(true);
																																																																																																																																																		label_27.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																		label_27.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																		label_27.setBounds(38, 43, 92, 13);

																																																																																																																																																				btnCheckButton = new Button(composite_3, SWT.TOGGLE);
																																																																																																																																																				btnCheckButton.setSelection(true);
																																																																																																																																																				btnCheckButton.setTouchEnabled(true);
																																																																																																																																																				btnCheckButton.setForeground(SWTResourceManager.getColor(255, 0, 0));
																																																																																																																																																				btnCheckButton.setBounds(269, 178, 16, 16);
																																																																																																																																																				btnCheckButton.setGrayed(true);

																																																																																																																																																						tabFolder_1.setSelection(tbtmNewItem_8);

																																																																																																																																																								Composite composite_2 = new Composite(tabFolder_1, SWT.NONE);
																																																																																																																																																								composite_2.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																								tbtmNewItem_8.setControl(composite_2);

																																																																																																																																																										//Label lblNewLabel_24 = new Label(composite_2, SWT.BORDER | SWT.SHADOW_IN);
																																																																																																																																																										//lblNewLabel_24.setBounds(10, 10, 49, 49);

																																																																																																																																																										lblClassIcon = new Label(composite_2, SWT.BORDER | SWT.SHADOW_IN);
																																																																																																																																																										lblClassIcon.setBounds(10, 10, 49, 49);

																																																																																																																																																												progressBar_1 = new ProgressBar(composite_2, SWT.NONE);
																																																																																																																																																												progressBar_1.setForeground(SWTResourceManager.getColor(255, 0, 0));
																																																																																																																																																												progressBar_1.setBounds(167, 29, 119, 11);

																																																																																																																																																														progressBar_2 = new ProgressBar(composite_2, SWT.NONE);
																																																																																																																																																														progressBar_2.setForeground(SWTResourceManager.getColor(0, 0, 255));
																																																																																																																																																														progressBar_2.setBounds(167, 46, 119, 11);

																																																																																																																																																																lblPlayerNameText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																lblPlayerNameText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																lblPlayerNameText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																lblPlayerNameText.setBounds(65, 27, 96, 13);

																																																																																																																																																																		lblNewLabel_26 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																		lblNewLabel_26.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																		lblNewLabel_26.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																		lblNewLabel_26.setBounds(65, 46, 96, 13);
																																																																																																																																																																		lblNewLabel_26.setText("0Lv      0.0%");

																																																																																																																																																																				lblLoginText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																				lblLoginText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																				lblLoginText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																				lblLoginText.setBounds(208, 10, 78, 15);
																																																																																																																																																																				lblLoginText.setText("Login: Off");

																																																																																																																																																																						lblPledgeText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																						lblPledgeText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																						lblPledgeText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																						lblPledgeText.setBounds(65, 8, 125, 16);
																																																																																																																																																																						lblPledgeText.setText("[========]");

																																																																																																																																																																								lblHPText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																								lblHPText.setAlignment(SWT.RIGHT);
																																																																																																																																																																								lblHPText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																								lblHPText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																								lblHPText.setBounds(10, 65, 29, 13);
																																																																																																																																																																								lblHPText.setText("HP:");

																																																																																																																																																																										Label lblMp_1 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																										lblMp_1.setText("MP:");
																																																																																																																																																																										lblMp_1.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																										lblMp_1.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																										lblMp_1.setBounds(89, 65, 29, 13);

																																																																																																																																																																												lblNewLabel_30 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																												lblNewLabel_30.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																												lblNewLabel_30.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																												lblNewLabel_30.setBounds(51, 65, 32, 13);
																																																																																																																																																																												lblNewLabel_30.setText("0/0");

																																																																																																																																																																														label_10 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																														label_10.setText("0/0");
																																																																																																																																																																														label_10.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																														label_10.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																														label_10.setBounds(121, 65, 32, 13);

																																																																																																																																																																																lblStr = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																lblStr.setAlignment(SWT.RIGHT);
																																																																																																																																																																																lblStr.setText("STR:");
																																																																																																																																																																																lblStr.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																lblStr.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																lblStr.setBounds(10, 103, 29, 13);

																																																																																																																																																																																		lblCon = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																		lblCon.setAlignment(SWT.RIGHT);
																																																																																																																																																																																		lblCon.setText("CON:");
																																																																																																																																																																																		lblCon.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																		lblCon.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																		lblCon.setBounds(10, 122, 29, 13);

																																																																																																																																																																																				lblInt = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																				lblInt.setAlignment(SWT.RIGHT);
																																																																																																																																																																																				lblInt.setText("INT:");
																																																																																																																																																																																				lblInt.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																				lblInt.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																				lblInt.setBounds(10, 141, 29, 13);

																																																																																																																																																																																						lblSTRNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																						lblSTRNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																						lblSTRNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																						lblSTRNum.setBounds(51, 103, 32, 13);
																																																																																																																																																																																						lblSTRNum.setText("0/0");

																																																																																																																																																																																								lblDex = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																								lblDex.setText("DEX: ");
																																																																																																																																																																																								lblDex.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																								lblDex.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																								lblDex.setBounds(89, 103, 31, 13);

																																																																																																																																																																																										lblWis = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																										lblWis.setText("WIS: ");
																																																																																																																																																																																										lblWis.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																										lblWis.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																										lblWis.setBounds(89, 122, 31, 13);

																																																																																																																																																																																												lblCha = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																												lblCha.setText("CHA: ");
																																																																																																																																																																																												lblCha.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																												lblCha.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																												lblCha.setBounds(89, 141, 31, 13);

																																																																																																																																																																																														lblCONNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																														lblCONNum.setText("0/0");
																																																																																																																																																																																														lblCONNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																														lblCONNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																														lblCONNum.setBounds(51, 122, 32, 13);

																																																																																																																																																																																																lblINTNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																lblINTNum.setText("0/0");
																																																																																																																																																																																																lblINTNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																lblINTNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																lblINTNum.setBounds(51, 141, 32, 13);

																																																																																																																																																																																																		lblDEXNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																		lblDEXNum.setText("0/0");
																																																																																																																																																																																																		lblDEXNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																		lblDEXNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																		lblDEXNum.setBounds(121, 103, 42, 13);

																																																																																																																																																																																																				lblWISNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																				lblWISNum.setText("0/0");
																																																																																																																																																																																																				lblWISNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																				lblWISNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																				lblWISNum.setBounds(121, 122, 42, 13);

																																																																																																																																																																																																						lblCHANum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																						lblCHANum.setText("0/0");
																																																																																																																																																																																																						lblCHANum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																						lblCHANum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																						lblCHANum.setBounds(121, 141, 42, 13);

																																																																																																																																																																																																								lblSPText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																								lblSPText.setAlignment(SWT.RIGHT);
																																																																																																																																																																																																								lblSPText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																								lblSPText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																								lblSPText.setBounds(10, 160, 29, 13);
																																																																																																																																																																																																								lblSPText.setText("SP:");

																																																																																																																																																																																																										lblMRText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																										lblMRText.setText("MR: ");
																																																																																																																																																																																																										lblMRText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																										lblMRText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																										lblMRText.setBounds(89, 160, 31, 13);

																																																																																																																																																																																																												lblMRNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																												lblMRNum.setText("0%");
																																																																																																																																																																																																												lblMRNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																												lblMRNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																												lblMRNum.setBounds(121, 160, 31, 13);

																																																																																																																																																																																																														lblSPNum = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																														lblSPNum.setText("0");
																																																																																																																																																																																																														lblSPNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																														lblSPNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																														lblSPNum.setBounds(51, 160, 18, 13);

																																																																																																																																																																																																																lblERText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																lblERText.setAlignment(SWT.RIGHT);
																																																																																																																																																																																																																lblERText.setText("ER:");
																																																																																																																																																																																																																lblERText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																lblERText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																lblERText.setBounds(10, 179, 29, 13);

																																																																																																																																																																																																																		lblDGText = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																		lblDGText.setText("DG: ");
																																																																																																																																																																																																																		lblDGText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																		lblDGText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																		lblDGText.setBounds(89, 179, 28, 13);

																																																																																																																																																																																																																				label_18 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																				label_18.setText("0");
																																																																																																																																																																																																																				label_18.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																				label_18.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																				label_18.setBounds(51, 179, 18, 13);

																																																																																																																																																																																																																						label_19 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																						label_19.setText("0");
																																																																																																																																																																																																																						label_19.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																						label_19.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																						label_19.setBounds(121, 179, 24, 13);

																																																																																																																																																																																																																								lblEvaTimeName = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																								lblEvaTimeName.setAlignment(SWT.RIGHT);
																																																																																																																																																																																																																								lblEvaTimeName.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																								lblEvaTimeName.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																								lblEvaTimeName.setBounds(144, 65, 93, 13);
																																																																																																																																																																																																																								lblEvaTimeName.setText("Eva Time:");

																																																																																																																																																																																																																														lblNewLabel_34 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																														lblNewLabel_34.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																														lblNewLabel_34.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																														lblNewLabel_34.setBounds(180, 160, 49, 15);
																																																																																																																																																																																																																														lblNewLabel_34.setText("Logout: ");

																																																																																																																																																																																																																																lblNewLabel_35 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																lblNewLabel_35.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																lblNewLabel_35.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																lblNewLabel_35.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																lblNewLabel_35.setBounds(222, 160, 64, 13);
																																																																																																																																																																																																																																lblNewLabel_35.setText("0000.00.00");

																																																																																																																																																																																																																																		lblNewLabel_36 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																		lblNewLabel_36.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																		lblNewLabel_36.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																		lblNewLabel_36.setBounds(153, 179, 37, 13);
																																																																																																																																																																																																																																		lblNewLabel_36.setText("Time: ");

																																																																																																																																																																																																																																				lblNewLabel_37 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																				lblNewLabel_37.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																				lblNewLabel_37.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																				lblNewLabel_37.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																				lblNewLabel_37.setBounds(192, 179, 94, 13);
																																																																																																																																																																																																																																				lblNewLabel_37.setText("00시 00분 00초");

																																																																																																																																																																																																																																						lblNewLabel_38 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																						lblNewLabel_38.setAlignment(SWT.RIGHT);
																																																																																																																																																																																																																																						lblNewLabel_38.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																						lblNewLabel_38.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																						lblNewLabel_38.setBounds(10, 84, 34, 13);
																																																																																																																																																																																																																																						lblNewLabel_38.setText("PK: ");

																																																																																																																																																																																																																																								lblDethCount = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																								lblDethCount.setAlignment(SWT.RIGHT);
																																																																																																																																																																																																																																								lblDethCount.setText("Death: ");
																																																																																																																																																																																																																																								lblDethCount.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																								lblDethCount.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																								lblDethCount.setBounds(72, 84, 45, 13);

																																																																																																																																																																																																																																										lblNewLabel_39 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																										lblNewLabel_39.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																										lblNewLabel_39.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																										lblNewLabel_39.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																										lblNewLabel_39.setBounds(44, 84, 22, 13);
																																																																																																																																																																																																																																										lblNewLabel_39.setText("0");

																																																																																																																																																																																																																																												label_20 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																												label_20.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																												label_20.setText("0");
																																																																																																																																																																																																																																												label_20.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																												label_20.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																												label_20.setBounds(117, 84, 22, 13);

																																																																																																																																																																																																																																														lblNewLabel_40 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																														lblNewLabel_40.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																														lblNewLabel_40.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																														lblNewLabel_40.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																														lblNewLabel_40.setBounds(237, 65, 22, 13);
																																																																																																																																																																																																																																														lblNewLabel_40.setText("0");

																																																																																																																																																																																																																																																				lblNewLabel_41 = new Label(composite_2, SWT.NONE);
																																																																																																																																																																																																																																																				lblNewLabel_41.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																				lblNewLabel_41.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																				lblNewLabel_41.setBounds(264, 65, 20, 13);
																																																																																																																																																																																																																																																				lblNewLabel_41.setText("min");

																																																																																																																																																																																																																																																										CTabFolder tabFolder_2 = new CTabFolder(composite_6, SWT.BORDER);
																																																																																																																																																																																																																																																										tabFolder_2.setSelectionForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																										tabFolder_2.setBackground(SWTResourceManager.getColor(204, 204, 204));
																																																																																																																																																																																																																																																										tabFolder_2.setBounds(543, 565, 297, 266);
																																																																																																																																																																																																																																																										tabFolder_2.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

																																																																																																																																																																																																																																																												CTabItem tbtmNewItem_13 = new CTabItem(tabFolder_2, SWT.NONE);
																																																																																																																																																																																																																																																												tbtmNewItem_13.setText("Server Info");

																																																																																																																																																																																																																																																																table = new Table(tabFolder_2, SWT.BORDER | SWT.FULL_SELECTION);
																																																																																																																																																																																																																																																																table.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																table.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																//		tbtmNewItem_14.setControl(table);
																																																																																																																																																																																																																																																																//		table.setHeaderVisible(true);
																																																																																																																																																																																																																																																																//		table.setLinesVisible(true);
																																																																																																																																																																																																																																																																		tabFolder_2.setSelection(tbtmNewItem_13);

																																																																																																																																																																																																																																																																				ScrolledComposite scrolledComposite = new ScrolledComposite(tabFolder_2, SWT.NONE);
																																																																																																																																																																																																																																																																				scrolledComposite.setTouchEnabled(true);
																																																																																																																																																																																																																																																																				scrolledComposite.setShowFocusedControl(true);
																																																																																																																																																																																																																																																																				scrolledComposite.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																				scrolledComposite.setAlwaysShowScrollBars(true);
																																																																																																																																																																																																																																																																				tbtmNewItem_13.setControl(scrolledComposite);
																																																																																																																																																																																																																																																																				scrolledComposite.setExpandHorizontal(true);
																																																																																																																																																																																																																																																																				scrolledComposite.setExpandVertical(true);

																																																																																																																																																																																																																																																																						Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
																																																																																																																																																																																																																																																																						composite_1.setBackground(SWTResourceManager.getColor(51, 51, 51));

																																																																																																																																																																																																																																																																								Group grpServerRates = new Group(composite_1, SWT.NONE);
																																																																																																																																																																																																																																																																								grpServerRates.setTouchEnabled(true);
																																																																																																																																																																																																																																																																								grpServerRates.setForeground(SWTResourceManager.getColor(204, 0, 0));
																																																																																																																																																																																																																																																																								grpServerRates.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																								grpServerRates.setText("Server Rates");
																																																																																																																																																																																																																																																																								grpServerRates.setBounds(10, 10, 275, 42);

																																																																																																																																																																																																																																																																										lblTxtEXP = new Label(grpServerRates, SWT.NONE);
																																																																																																																																																																																																																																																																										lblTxtEXP.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																																																										lblTxtEXP.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																										lblTxtEXP.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																										lblTxtEXP.setBounds(13, 18, 33, 13);
																																																																																																																																																																																																																																																																										lblTxtEXP.setText("EXP:");

																																																																																																																																																																																																																																																																												lblEXPRATE = new Label(grpServerRates, SWT.NONE);
																																																																																																																																																																																																																																																																												lblEXPRATE.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																												lblEXPRATE.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																												lblEXPRATE.setBounds(47, 18, 42, 13);

																																																																																																																																																																																																																																																																														lblNewLabel_6 = new Label(grpServerRates, SWT.NONE);
																																																																																																																																																																																																																																																																														lblNewLabel_6.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																																																														lblNewLabel_6.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																														lblNewLabel_6.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																														lblNewLabel_6.setBounds(93, 18, 39, 13);
																																																																																																																																																																																																																																																																														lblNewLabel_6.setText("Adena:");

																																																																																																																																																																																																																																																																																lblADENARATE = new Label(grpServerRates, SWT.NONE);
																																																																																																																																																																																																																																																																																lblADENARATE.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																lblADENARATE.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																lblADENARATE.setBounds(135, 18, 39, 13);

																																																																																																																																																																																																																																																																																		lblNewLabel_8 = new Label(grpServerRates, SWT.NONE);
																																																																																																																																																																																																																																																																																		lblNewLabel_8.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																																																																		lblNewLabel_8.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																		lblNewLabel_8.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																		lblNewLabel_8.setBounds(178, 18, 33, 15);
																																																																																																																																																																																																																																																																																		lblNewLabel_8.setText("Drop:");

																																																																																																																																																																																																																																																																																				lblDROPRATE = new Label(grpServerRates, SWT.NONE);
																																																																																																																																																																																																																																																																																				lblDROPRATE.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																				lblDROPRATE.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																				lblDROPRATE.setBounds(215, 18, 30, 13);

																																																																																																																																																																																																																																																																																																																																Group group_1 = new Group(composite_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																group_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
																																																																																																																																																																																																																																																																																																																																group_1.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																group_1.setText("System");
																																																																																																																																																																																																																																																																																																																																group_1.setBounds(10, 58, 275, 73);




																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		lblNewLabel_44 = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		lblNewLabel_44.setLocation(10, 38);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		lblNewLabel_44.setSize(255, 15);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		lblNewLabel_44.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		lblNewLabel_44.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		lblNewLabel_44.setText(" ");

																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		label_26 = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		label_26.setLocation(10, 38);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		label_26.setSize(92, 15);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		label_26.setText("Server Uptime: ");
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		label_26.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		label_26.setBackground(SWTResourceManager.getColor(51, 51, 51));

																																																																																																																																																																																																																																																																																																																																		lblNewLabel = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																		lblNewLabel.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																		lblNewLabel.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																		lblNewLabel.setBounds(10, 17, 46, 15);
																																																																																																																																																																																																																																																																																																																																		lblNewLabel.setText("Thread: ");

																																																																																																																																																																																																																																																																																																																																				lblNewLabel_1 = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																				lblNewLabel_1.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																				lblNewLabel_1.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																				lblNewLabel_1.setBounds(104, 17, 52, 15);
																																																																																																																																																																																																																																																																																																																																				lblNewLabel_1.setText("Memory: ");

																																																																																																																																																																																																																																																																																																																																						lblThreadCountNum = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																						lblThreadCountNum.setAlignment(SWT.CENTER);
																																																																																																																																																																																																																																																																																																																																						lblThreadCountNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																						lblThreadCountNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																						lblThreadCountNum.setBounds(58, 17, 33, 15);

																																																																																																																																																																																																																																																																																																																																								lblMemoryNum = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																								lblMemoryNum.setAlignment(SWT.RIGHT);
																																																																																																																																																																																																																																																																																																																																								lblMemoryNum.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																								lblMemoryNum.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																								lblMemoryNum.setBounds(152, 17, 33, 15);

																																																																																																																																																																																																																																																																																																																																										lblMbText = new Label(group_1, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																										lblMbText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																																																																																																																																																																																																																																																																																																										lblMbText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																																																																																																																																																																																																																																																																																																										lblMbText.setBounds(190, 17, 24, 15);
																																																																																																																																																																																																																																																																																																																																										lblMbText.setText("Mb");
																																																																																																																																																																																																																																																																																																																																										scrolledComposite.setContent(composite_1);
																																																																																																																																																																																																																																																																																																																																										scrolledComposite.setMinSize(new Point(94, 600));

																																																																																																																																																																																																																																																																																																																																												TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
																																																																																																																																																																																																																																																																																																																																												tblclmnNewColumn.setWidth(84);
																																																																																																																																																																																																																																																																																																																																												tblclmnNewColumn.setText("     캐릭명");

																																																																																																																																																																																																																																																																																																																																														TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.CENTER);
																																																																																																																																																																																																																																																																																																																																														tblclmnNewColumn_4.setResizable(false);
																																																																																																																																																																																																																																																																																																																																														tblclmnNewColumn_4.setWidth(75);
																																																																																																																																																																																																																																																																																																																																														tblclmnNewColumn_4.setText("Title");

																																																																																																																																																																																																																																																																																																																																																TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.CENTER);
																																																																																																																																																																																																																																																																																																																																																tblclmnNewColumn_3.setResizable(false);
																																																																																																																																																																																																																																																																																																																																																tblclmnNewColumn_3.setWidth(132);
																																																																																																																																																																																																																																																																																																																																																tblclmnNewColumn_3.setText("Detail");

																																																																																																																																																																																																																																																																																																																																																		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.CENTER);
																																																																																																																																																																																																																																																																																																																																																		tblclmnNewColumn_2.setResizable(false);
																																																																																																																																																																																																																																																																																																																																																		tblclmnNewColumn_2.setWidth(87);
																																																																																																																																																																																																																																																																																																																																																		tblclmnNewColumn_2.setText("Date");

																																																																																																																																																																																																																																																																																																																																																																btnMutePlayer = new Button(composite_6, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																																																btnMutePlayer.addSelectionListener(new SelectionAdapter() {
																																																																																																																																																																																																																																																																																																																																																																	@Override
																																																																																																																																																																																																																																																																																																																																																																	public void widgetSelected(SelectionEvent e) {
																																																																																																																																																																																																																																																																																																																																																																		if (Pcname == null || Pcname.length() <= 0) {
																																																																																																																																																																																																																																																																																																																																																																			toMessageBox("선택된 유저가 없습니다.");
																																																																																																																																																																																																																																																																																																																																																																			return;
																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																		MessageBox messageBox = new MessageBox(shlInbumserverManager, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
																																																																																																																																																																																																																																																																																																																																																																		messageBox.setMessage(Pcname + " 유저에게 채금을 주시겠습니까?");
																																																																																																																																																																																																																																																																																																																																																																		int type = messageBox.open();
																																																																																																																																																																																																																																																																																																																																																																		if (type == SWT.YES) {
																																																																																																																																																																																																																																																																																																																																																																			L1PcInstance pc = L1World.getInstance().getPlayer(Pcname);
																																																																																																																																																																																																																																																																																																																																																																			if (pc != null) {
																																																																																																																																																																																																																																																																																																																																																																				//pc.setSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED, 10 * 60 * 1000);
																																																																																																																																																																																																																																																																																																																																																																				pc.sendPackets(new S_SkillIconGFX(36, 10 * 60));
																																																																																																																																																																																																																																																																																																																																																																				pc.sendPackets(new S_ServerMessage(286, String.valueOf(10)));
																																																																																																																																																																																																																																																																																																																																																																			} else {
																																																																																																																																																																																																																																																																																																																																																																				toMessageBox(errorMessage01);
																																																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																																																});
																																																																																																																																																																																																																																																																																																																																																																btnMutePlayer.setText("Mute");
																																																																																																																																																																																																																																																																																																																																																																btnMutePlayer.setBounds(543, 239, 50, 25);

																																																																																																																																																																																																																																																																																																																																																																btnBanPlayer = new Button(composite_6, SWT.NONE);
																																																																																																																																																																																																																																																																																																																																																																btnBanPlayer.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(255, 0, 0));
																																																																																																																																																																																																																																																																																																																																																																btnBanPlayer.addSelectionListener(new SelectionAdapter() {
																																																																																																																																																																																																																																																																																																																																																																	@Override
																																																																																																																																																																																																																																																																																																																																																																	public void widgetSelected(SelectionEvent e) {
																																																																																																																																																																																																																																																																																																																																																																		if (Pcname == null || Pcname.length() <= 0) {
																																																																																																																																																																																																																																																																																																																																																																			toMessageBox("선택된 유저가 없습니다.");
																																																																																																																																																																																																																																																																																																																																																																			return;
																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																		MessageBox messageBox = new MessageBox(shlInbumserverManager, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
																																																																																																																																																																																																																																																																																																																																																																		messageBox.setMessage(Pcname + " 유저의 채금을 해제하시겠습니까?");
																																																																																																																																																																																																																																																																																																																																																																		int type = messageBox.open();
																																																																																																																																																																																																																																																																																																																																																																		if (type == SWT.YES) {
																																																																																																																																																																																																																																																																																																																																																																			L1PcInstance pc = L1World.getInstance().getPlayer(Pcname);
																																																																																																																																																																																																																																																																																																																																																																			if (pc != null) {
																																																																																																																																																																																																																																																																																																																																																																				if (pc.hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) {
																																																																																																																																																																																																																																																																																																																																																																					//pc.killSkillEffectTimer(L1SkillId.STATUS_CHAT_PROHIBITED);
																																																																																																																																																																																																																																																																																																																																																																					pc.removeSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED);
																																																																																																																																																																																																																																																																																																																																																																					pc.sendPackets(new S_SkillIconGFX(36, 1));
																																																																																																																																																																																																																																																																																																																																																																				} else {
																																																																																																																																																																																																																																																																																																																																																																					toMessageBox("The user is not currently in debt status.");
																																																																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																																																																			} else {
																																																																																																																																																																																																																																																																																																																																																																				toMessageBox(errorMessage01);
																																																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																																																});
																																																																																																																																																																																																																																																																																																																																																																btnBanPlayer.setText("Ban Player");
																																																																																																																																																																																																																																																																																																																																																																btnBanPlayer.setBounds(599, 239, 71, 25);

																																																														composite_5 = new Composite(shlInbumserverManager, SWT.NONE);
																																																														composite_5.setLocation(2000, 0);
																																																														composite_5.setEnabled(false);
																																																														composite_5.setSize(438, 279);
																																																														composite_5.setBackground(SWTResourceManager.getColor(51, 51, 51));

																																																														lblPortText = new Label(composite_5, SWT.NONE);
																																																														lblPortText.setText(" ");
																																																														lblPortText.setForeground(SWTResourceManager.getColor(255, 255, 255));
																																																														lblPortText.setBackground(SWTResourceManager.getColor(51, 51, 51));
																																																														lblPortText.setBounds(0, 0, 210, 15);

																																																														Button btnNewButton_1 = new Button(composite_5, SWT.NONE);
																																																														btnNewButton_1.addSelectionListener(new SelectionAdapter() {
																																																															@Override
																																																															public void widgetSelected(SelectionEvent e) {
																																																																LinAllManager.getInstance();
																																																																DropMonsterFind dialog = new DropMonsterFind(LinAllManager.getShell(),
																																																																		SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
																																																																dialog.open();
																																																															}
																																																														});
																																																														btnNewButton_1.setBounds(160, 21, 77, 22);
																																																														btnNewButton_1.setText("Drop Fix");

																																																														Button btnNewButton_2 = new Button(composite_5, SWT.NONE);
																																																														btnNewButton_2.addSelectionListener(new SelectionAdapter() {
																																																															@Override
																																																															public void widgetSelected(SelectionEvent e) {
																																																																LinAllManager.getInstance();
																																																																ShopNpcFind dialog = new ShopNpcFind(LinAllManager.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
																																																																dialog.open();
																																																															}
																																																														});
																																																														btnNewButton_2.setBounds(63, 21, 77, 22);
																																																														btnNewButton_2.setText("Edit shop");

																																																														Button button_6 = new Button(composite_5, SWT.NONE);
																																																														button_6.addSelectionListener(new SelectionAdapter() {
																																																															@Override
																																																															public void widgetSelected(SelectionEvent e) {
																																																																PresentDialog _PresentDialog = new PresentDialog(shlInbumserverManager);
																																																																_PresentDialog.open();
																																																															}
																																																														});
																																																														button_6.setText("Gift");
																																																														button_6.setBounds(252, 21, 36, 22);


		shlInbumserverManager.open();
		shlInbumserverManager.layout();

		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc.getNetConnection() != null) {
				listCharacters.add(pc.getName());
			}
		}

		/**server info thread**/
		LinAllManagerInfoThread.getInstance();

		try {
			while (!shlInbumserverManager.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch(Exception e){e.printStackTrace();}
		//savelog();
		/**recursive call*/
		open();
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("unused")
	protected void createContents() {

		shlInbumserverManager = new Shell(display,SWT.BORDER | SWT.MIN | SWT.TITLE | SWT.SYSTEM_MODAL);
		shlInbumserverManager.setImage(SWTResourceManager.getImage("data/img/L1OpenPromoIcon128.png"));
		shlInbumserverManager.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				String title = "L1Open Warning";
				String message = "Please shut down the server from the File menu or close the console window.";
				int style = SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION;
				//MessageBox dialog = new MessageBox(shell, style);
				MessageBox dialog = new MessageBox(shlInbumserverManager,SWT.OK|SWT.ICON_INFORMATION);
				dialog.setText(title);
				dialog.setMessage(message);
				int flag = dialog.open();
				if (flag == SWT.OK) {
					e.doit = false;
				} else {
					e.doit = false;
				}
			}
			@Override
			public void shellDeactivated(ShellEvent e) {
			}
		});

		shlInbumserverManager.setBackground(SWTResourceManager.getColor(102, 102, 102));
		shlInbumserverManager.setSize(1310, 900);
		shlInbumserverManager.setText("L1Open Server Manager v0.3.491r1");

		/**this part problem?*/

		/*btnSaveLog = new Button(composite, SWT.NONE);
		btnSaveLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shlInbumserverManager,SWT.YES | SWT.NO |SWT.ICON_QUESTION);
				messageBox.setMessage("Do you want to save all logs?");
				int type = messageBox.open();
				if(type == SWT.YES){
				//	savelog();
					LogAppend("Log save & clear completed.");
				}

			}
		});
		btnSaveLog.setText("Save Log");
		btnSaveLog.setBounds(349, 812, 65, 19);*/

		menu = new Menu(shlInbumserverManager, SWT.BAR);
		menu.setLocation(new Point(0, 0));
		shlInbumserverManager.setMenuBar(menu);

		mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("File");

		menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);

		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.setText("Server Settings");

		MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GameServer.getInstance().saveAllCharInfo();
				MessageBox messageBox = new MessageBox(shlInbumserverManager,SWT.OK|SWT.ICON_INFORMATION);
				messageBox.setMessage("All character information has been saved.");
				messageBox.open();
			}
		});
		mntmNewItem_1.setText("System Storage");

				MenuItem mntmNewItem_10 = new MenuItem(menu_1, SWT.NONE);
				mntmNewItem_10.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						final Tray tray = display.getSystemTray();
						if(tray != null){
							// Hide current window
							shlInbumserverManager.setVisible(false);
							// Activate tray
							final TrayItem item = new TrayItem(tray, SWT.NONE);
							item.setToolTipText( String.format("Server"));
							item.setImage( SWTResourceManager.getImage("data/img/L1OpenPromoIcon128.png") );
							// event registration
							item.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									item.dispose();
									shlInbumserverManager.setVisible(true);
									shlInbumserverManager.setFocus();
								}
							});
						}
					}
				});
				mntmNewItem_10.setText("Tray Mode");



		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mItemForceQuit = new MenuItem(menu_1, SWT.NONE);
		mItemForceQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shlInbumserverManager,SWT.YES | SWT.NO |SWT.ICON_QUESTION);
				messageBox.setMessage("Are you sure you want to forceably shut down the server? Saving may be interrupted.");
				int type = messageBox.open();
				if(type == SWT.YES){
					GameServer.getInstance().saveAllCharInfo();
					GameServer.getInstance().shutdownWithCountdown(1);
				}
			}
		});
		mItemForceQuit.setText("Force Quit");

		MenuItem mntmSaveShutdown = new MenuItem(menu_1, SWT.NONE);
		mntmSaveShutdown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shlInbumserverManager,SWT.YES | SWT.NO |SWT.ICON_QUESTION);
				messageBox.setMessage("Are you sure you want to shut down the server?");
				int type = messageBox.open();
				if(type == SWT.YES){
					GameServer.getInstance().saveAllCharInfo();
					GameServer.getInstance().shutdownWithCountdown(10);
				}
			}
		});
		mntmSaveShutdown.setText("Save + Shutdown");

		MenuItem mntmNewSubmenu_4 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_4.setText("Reload");

		Menu menu_5 = new Menu(mntmNewSubmenu_4);
		mntmNewSubmenu_4.setMenu(menu_5);

		MenuItem mntmNewItem_2 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Config.load();
				LogAppend("Config Update Complete...");
			}
		});
		mntmNewItem_2.setText("Config");

		MenuItem mntmNewItem_3 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				L1TreasureBox.load();
				LogAppend("Treasure Box Update Complete...");
			}
		});
		mntmNewItem_3.setText("Treasure Box");

		new MenuItem(menu_5, SWT.SEPARATOR);

		MenuItem mntmNewItem_4 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DropTable.reload();
				LogAppend("Drop List Update Complete...");
			}
		});
		mntmNewItem_4.setText("Drop List");

		MenuItem mntmNewItem_5 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SkillsTable.reload();
				LogAppend("Skills Update Complete...");
			}
		});
		mntmNewItem_5.setText("Skills");

		MenuItem mntmNewItem_6 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MobSkillTable.reload();
				LogAppend("Mob Skills Update Complete...");
			}
		});
		mntmNewItem_6.setText("Mob Skills");

		MenuItem mntmNewItem_7 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ItemTable.reload();
				LogAppend("Item Update Complete...");
			}
		});
		mntmNewItem_7.setText("Item");

		MenuItem mntmNewItem_8 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ShopTable.reload();
				LogAppend("Shop Update Complete...");
			}
		});
		mntmNewItem_8.setText("Shop");

		MenuItem mntmNewItem_17 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_17.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SpawnTable.getInstance();
				LogAppend("SpawnTable reload complete...");
			}
		});
		mntmNewItem_17.setText("Spawn Table");

		MenuItem mntmNewItem_9 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NpcTable.reload();
				LogAppend("Npc Update Complete...");
			}
		});
		mntmNewItem_9.setText("NPC");

		MenuItem mntmNewItem_11 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_11.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ClanTable.reload();
				LogAppend("Pledge Update Complete...");
			}
		});
		mntmNewItem_11.setText("Pledge");

		MenuItem mntmNewItem_12 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CastleTable.reload();
				LogAppend("Castle Update Complete...");
			}
		});
		mntmNewItem_12.setText("Castle Info");

		MenuItem mntmNewItem_13 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_13.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WeaponAddDamage.reload();
				LogAppend("Weapon Damage Update Complete...");
			}
		});
		mntmNewItem_13.setText("Weapon Dmg");

		MenuItem mntmNewItem_14 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_14.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WeaponSkillTable.reload();
				LogAppend("Weapon Skill Update Complete...");
			}
		});
		mntmNewItem_14.setText("Weapon Skill");

		new MenuItem(menu_5, SWT.SEPARATOR);


		MenuItem mntmNewItem_15 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_15.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IpTable.reload();
				LogAppend("Ban IP Update Complete...");
			}
		});
		mntmNewItem_15.setText("Ban IP");


		MenuItem mntmNewSubmenu_5 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_5.setText("About");

		Menu menu_6 = new Menu(mntmNewSubmenu_5);
		mntmNewSubmenu_5.setMenu(menu_6);

		MenuItem mntmNewItem_18 = new MenuItem(menu_6, SWT.NONE);
		mntmNewItem_18.setText("by.TeKniKo");

		MenuItem mntmDiscord = new MenuItem(menu_6, SWT.NONE);
		mntmDiscord.setText("Discord");


	}

	public static String getDate() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh-mm", Locale.KOREA);
		return s.format(Calendar.getInstance().getTime());
	}

	/**
	 * current time
	 * @return
	 */
	public static String getLogTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm:ss");
		String time = dateFormat.format(currentDate.getTime());
		return time;
	}

	public void LogAppend(final String Msg) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					txtInbumserverByleaf.append(Msg + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Inventory item deletion function
	 * @param tableitem
	 */
	public void itemdelete(final TableItem[] tableitem) {
		try{
			L1PcInstance target = L1World.getInstance().getPlayer(Pcname);
			if (target != null){
				for (TableItem table : tableitem){
					/**Delete from Inventory*/
					target.getInventory().removeItem(Integer.parseInt(table.getText(1)));
					/**Delete from item list*/
					items.remove(table.getText(1));
				}
				/**data storage*/
				target.saveInventory();
			} else {
				for (TableItem table : tableitem){
					L1ItemInstance temfitem = items.get(Integer.parseInt(table.getText(1)));
					if (temfitem != null){
						CharactersItemStorage.create().deleteItem(temfitem);
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Delete warehouse item
	 * @param tableitem
	 */
	public void wherehouseitemdelete(final TableItem[] tableitem) {
		try {

			// L1PcInstance target = L1World.getInstance().getPlayer(Pcname);
			if (ServercharInfo(Pcname)) {
			} else if (DBcharInfo(Pcname)) { // tek123
			}
			PrivateWarehouse warehouse = WarehouseManager.getInstance().getPrivateWarehouse(accountname);
			if (warehouse != null) {
				// PrivateWarehouse warehouse =
				// WarehouseManager.getInstance().getPrivateWarehouse(accountame);
				for (TableItem table : tableitem) {
					L1ItemInstance temfitem = warehouseitems.get(Integer.parseInt(table.getText(1)));
					if (temfitem != null) {
						warehouse.deleteItem(temfitem);
					}
				}
			} else {
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					for(TableItem table : tableitem){
						pstm = con.prepareStatement("DELETE FROM character_warehouse WHERE id = ?");
						pstm.setInt(1, Integer.parseInt(table.getText(1)));
						pstm.execute();
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					SQLUtil.close(pstm);
					SQLUtil.close(con);
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}



	/**
	 * character inventory
	 */
	ConcurrentHashMap<Integer,L1ItemInstance> items = new ConcurrentHashMap<>();
	public void inventoryList() {
		items.clear();
		try{
			Connection con = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			L1ItemInstance item = null;
			L1Item itemTemplate = null;
			try {

				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement("SELECT * FROM character_items WHERE char_id = ?");
				pstm.setInt(1, objid);
				rs = pstm.executeQuery();
				while (rs.next()) {
					int itemId = rs.getInt("item_id");
					itemTemplate = ItemTable.getInstance().getTemplate(itemId);
					if (itemTemplate == null) {
						continue;
					}
					item = new L1ItemInstance();
					item.setId(rs.getInt("id"));
					item.setItem(itemTemplate);
					item.setCount(rs.getInt("count"));
					item.setEquipped(rs.getInt("Is_equipped") != 0 ? true : false);
					item.setEnchantLevel(rs.getInt("enchantlvl"));
					item.setIdentified(rs.getInt("is_id") != 0 ? true : false);
					item.set_durability(rs.getInt("durability"));
					item.setChargeCount(rs.getInt("charge_count"));
					item.setRemainingTime(rs.getInt("remaining_time"));
					item.setLastUsed(rs.getTimestamp("last_used"));
					item.setBless(rs.getInt("bless"));
					item.setAttrEnchantLevel(rs.getInt("attr_enchantlvl"));
					//item.setSpecialEnchant(rs.getInt("special_enchant"));
					item.setEndTime(rs.getTimestamp("end_time"));
					/** package store **/
					item.setPackage(rs.getInt("package") != 0 ? true : false);
					items.put(item.getId(), item);
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(con);
				item = null;
				itemTemplate = null;
			}
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Warehouse item list
	 */
	ConcurrentHashMap<Integer,L1ItemInstance> warehouseitems = new ConcurrentHashMap<>();
	public void warehouseList() {
		warehouseitems.clear();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_warehouse WHERE account_name = ?");
			pstm.setString(1, accountname);
			rs = pstm.executeQuery();
			L1ItemInstance item = null;
			L1Item itemTemplate = null;
			while (rs.next()) {
				itemTemplate = ItemTable.getInstance().getTemplate(rs.getInt("item_id"));
				item = new L1ItemInstance();
				int objectId = rs.getInt("id");
				item.setId(objectId);
				item.setItem(itemTemplate);
				item.setCount(rs.getInt("count"));
				item.setEquipped(false);
				item.setEnchantLevel(rs.getInt("enchantlvl"));
				item.setIdentified(rs.getInt("is_id") != 0 ? true : false);
				item.set_durability(rs.getInt("durability"));
				item.setChargeCount(rs.getInt("charge_count"));
				item.setRemainingTime(rs.getInt("remaining_time"));
				item.setLastUsed(rs.getTimestamp("last_used"));
				item.setAttrEnchantLevel(rs.getInt("attr_enchantlvl"));
				item.setBless(rs.getInt("bless"));
				warehouseitems.put(item.getId(), item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}


	/**
	 * character access log
	 * @param name
	 * @param ip
	 */
	public void LogConnectAppend(final String name, final String ip) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					boolean ch = false;
					for (String s : listCharacters.getItems()) {
						if (name.equals(s))
							ch = true;
					}
					if (!ch) {
						txtInbumserverByleaf.append(getLogTime() + " [" + name + "] Connected - "+ (listCharacters.getItems().length + 1) +" Online.\n");
						//txtInbumserverByleaf.append("[접속] (" + name + ")\n");
						//txtInbumserverByleaf.append("IP :" + ip + " Time : " + getLogTime() + " [" + (list.getItems().length + 1) + "명]\n\n");
						listCharacters.add(name);
						if (LinAllManagerInfoThread.MaxUser < listCharacters.getItems().length) {
							LinAllManagerInfoThread.MaxUser = listCharacters.getItems().length;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * End of character connection
	 * @param name
	 * @param ip
	 */
	public synchronized void LogLogOutAppend(final String name, final String ip) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					boolean ch = false;
					for (String s : listCharacters.getItems()) {
						if (name.equals(s))
							ch = true;
					}
					if (ch) {
						txtInbumserverByleaf.append(getLogTime() + " [" + name + "] Disconnected - " + (listCharacters.getItems().length - 1) + " Online.\n");
						//txtInbumserverByleaf.append("[종료] (" + name + ")\n");
						//txtInbumserverByleaf.append("IP :" + ip + " Time : " + getLogTime() + " [" + (list.getItems().length - 1) + "명]\n\n");
						listCharacters.remove(name);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * All chat logs
	 * @param name
	 * @param msg
	 */
	public void AllChatAppend(final String name, final String msg) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					chatText.append("[" + name + "]: " + msg + "\n");
				}catch(Exception e){e.printStackTrace();}
			}
		});
	}

	// Server messages to main LinAll window
	public static void serverMessageAppend(final String msg) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					txtInbumserverByleaf.append(getLogTime() + msg + "\n");
				} catch(Exception e){e.printStackTrace();}
			}
		});
	}

	/**
	 * General chat log
	 * @param name
	 * @param msg
	 */
	public void NomalchatAppend(final String name, final String msg) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					text_8.append("[" + name + "]: " + msg + "\n");
				}catch(Exception e){e.printStackTrace();}
			}
		});
	}

	/**
	 * whisper chat log
	 * @param Aname
	 * @param Dname
	 * @param msg
	 */
	public void WhisperChatAppend(final String Aname,final String Dname,final String msg) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					txtWhisper.append(getLogTime() + "[" + Aname + "]->[" + Dname + "]: " + msg + "\n");
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}
	/**
	 * Clan chat log
	 * @param Clanname
	 * @param name
	 * @param msg
	 */

	public void pledgeChatAppend(final String Clanname,final String name,final String msg) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					text_5.append("[" + Clanname + "] " + name + ": " + msg + "\n");
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}
	/**
	 * party chat log
	 * @param partylist
	 * @param name
	 * @param msg
	 */
	public void PartyChatAppend(final String name, final String msg) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_6.append("[" + name + "]: " + msg + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * store purchase log
	 * @param Itemname
	 * @param count
	 * @param price
	 * @param npcname
	 * @param name
	 * @param msg
	 */
	/*public void ShopAppend(final String Itemname,final int count,final long price,final String npcname,
			final String name) {

		display.syncExec(new Runnable() {
			public void run() {
				try{
					txtTime_1.append("[" + npcname + "]" + name + " Time : "+getLogTime()+"\n");
					txtTime_1.append("[Item: " + Itemname + "] [Count: " + count + "] [Price: " + price + "]\n\n");
				}catch(Exception e){e.printStackTrace();}
			}
		});
	}*/
	/**
	 * transaction log
	 * @param Itemname
	 * @param count
	 * @param Aname
	 * @param Dname
	 */
	public void TradeAppend(final String Itemname,final String Aname,
			final String Dname) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					txtTime_2.append("[" + Aname + "]->[" + Dname + "] Time: "+getLogTime()+"\n");
					txtTime_2.append("[" + Itemname + "]\n\n");
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}


	/**
	 * Warehouse log Deposit type = 0 Withdraw type = 1
	 *
	 * @param Itemname
	 * @param count
	 * @param name
	 * @param type
	 */
	public void WarehouseAppend(final String Itemname,final int count,final String name, final int type) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					if(type == 0){
						txtWarehouse.append("[" + name + "]->[Deposit] Time: " + getLogTime() + "\n");
						txtWarehouse.append("[Item: " + Itemname + "]\n\n");
					}else if(type == 1){
						txtWarehouse.append("[Withdraw]->[" + name + "] Time: " + getLogTime() + "\n");
						txtWarehouse.append("[Item: " + Itemname + "]\n\n");
					}
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}

	/**
	 * Deposit Elf type = 0, Withdraw Elf: type = 1
	 * Deposit Pledge type = 2, Withdraw Pledge type = 3
	 * @param Itemname
	 * @param count
	 * @param name
	 * @param type
	 */
	public void EPWarehouseAppend(final String Itemname,final int count,final String name, final int type) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					if(type == 0){
						text_4.append("[" + name + "]->[ElfWare] Time: " + getLogTime()+"\n");
						text_4.append("[Item: " + Itemname + "]\n\n");
					}else if(type == 1){
						text_4.append("[요정창고]->[" + name + "] Time: " + getLogTime()+"\n");
						text_4.append("[Item: " + Itemname + "]\n\n");
					}else if(type == 2){
						text_4.append("[" + name + "]->[ClanWare] Time: " + getLogTime()+"\n");
						text_4.append("[Item: " + Itemname + "]\n\n");
					}else if(type == 3){
						text_4.append("[ClanWare]->[" + name + "] Time: " + getLogTime()+"\n");
						text_4.append("[Item: " + Itemname + "]\n\n");
					}
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}


	/**
	 * Enchant Log
	 * 성공 : type = 0,실패 : type = 1
	 * @param Itemname
	 * @param name
	 * @param type
	 */
	public void EnchantAppend(final String Itemname,final int oldEnchant,final int newEnchant,final String name, final int type) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					if(type == 0) {
						txtTime_3.append("[" + name + "]=> Success! Time: "+getLogTime()+"\n");
						txtTime_3.append("[Item: +" + oldEnchant + " " + Itemname + "] - > [Item: +" + newEnchant + " " + Itemname + "] \n");
					} else {
						txtTime_3.append("[" + name + "]=> Failed! Time: " + getLogTime()+"\n");
						txtTime_3.append("[Item: +" + oldEnchant + " " + Itemname + "]\n");
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});

	}
	/**
	 * drop and pick up
	 * Pickup type = 0, Drop type = 1
	 * @param Itemname
	 * @param name
	 * @param count
	 * @param type
	 */
	public void pickUpAppend(final String Itemname,final String name,final int count, final int type) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					if(type == 0) {
						text_10.append("Pickup: [" + name + "][Item: " + Itemname + "] Time: " + getLogTime() + "\n");
					} else {
						text_10.append("Drop: [" + name + "][Item: " + Itemname + "] Time: " + getLogTime() + "\n");
					}
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}

	public static void savelog() {
		try {
			savelog(txtInbumserverByleaf, "system");
			savelog(chatText, "allchat");
			savelog(txtWhisper, "whisper");
			savelog(text_5, "pledgechat");
			savelog(text_6, "partychat");
			//savelog(txtTime_1, "shop");
			savelog(txtTime_2, "trade");
			savelog(txtWarehouse, "warehouse");
			savelog(text_4, "ewarehouse");
			savelog(txtTime_3, "enchant");
			savelog(text_10, "droppickup");
			savelog(text_7, "penalty");
			savelog(text_8, "generalchat");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public static void savelog(Text textPane, String name) {
		try {
			File f = null;
			String sTemp = "";
			sTemp = getDate();
			StringTokenizer s = new StringTokenizer(sTemp, " ");
			String data = s.nextToken();
			f = new File("ManagerLog/" + data);
			if (!f.exists()) {
				f.mkdir();
			}
			flush(textPane, name, data);
			textPane.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public static void savelog(final Text textPane, final String name) {
		try {
			File f = null;
			String sTemp = "";
			sTemp = getDate();
			StringTokenizer s = new StringTokenizer(sTemp, " ");
			final String data = s.nextToken();
			f = new File("Logging/" + data);
			if (!f.exists()) {
				f.mkdir();
			}
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					flush(textPane, name, data);
					textPane.setText("");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public static void flush(Text text, String FileName, String date) {
		try {
			RandomAccessFile rnd = new RandomAccessFile("ManagerLog/" + date + "/" + FileName + ".txt", "rw");
			rnd.seek(rnd.length());
			rnd.write(text.getText().getBytes());
			rnd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Death Drop: 0, Evaporate: 1
	 * @param Itemname
	 * @param name
	 * @param count
	 * @param type
	 */
	public void PenaltyAppend(final String Itemname,final String name,final int count, final int type) {

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					if(type == 0) {
						text_7.append("Drop: [" + name + "][Item: " + Itemname + "] Time: " + getLogTime()+"\n");
					} else {
						text_7.append("Evap: [" + name + "][Item: " + Itemname + "] Time: " + getLogTime()+"\n");
					}
				}catch(Exception e){e.printStackTrace();}
			}
		});

	}


	public void GmAppend(final String name, final String cmd, final String arg) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_11.append("GMC: [" + name + "] [" + cmd + "] [" + arg + "] Time: " + getLogTime() + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void CraftInfo(final String name, final String msg, final int craftId) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_15.append("Failure: [" + name + "] [" + msg + "] [" + craftId + "] Time: " + getLogTime() + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void BossAppend(final String name) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_9.append("Boss Spawn: [" + name + "] Time: " + getLogTime() + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void TimeAppend(final String name) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_12.append("Dungeon Open: [" + name + "] Time: " + getLogTime() + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void TimeSpeed(final String name, L1PcInstance pc) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_13.append("Suspicion: [" + name + "] Polymorph: [" + pc.getTempCharGfx() + "] Class: ["+ pc.getClassName() + "] Time: " + getLogTime() + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void castleMessageAppend(final String name, final String msg) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					text_14.append("Time: " + getLogTime());
					text_14.append("[" + name + "] siege of " + msg + " has started.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	String Clanname="";	String Pcname="";
	String exp="";		String stasts=""; //login status
	String hp="";		String mp="";
	String str="";		String dex="";
	String con="";		String wis="";
	String Int="";		String cha="";
	String sp="";		String mr="";
	String er="";		String dg="";
	String Ltime="";	String toptime="";
	String gitime="";	String pk="";
	String deth="";		String logindate="";
	String logintime="";
	String accountname = "";
	int objid = 0;
	int MaxHp = 0;
	int CurrentHp = 0;
	int MaxMp = 0;
	int CurrentMp = 0;

	public static NumberFormat nf = NumberFormat.getInstance();
	private Label lblNewLabel_41;
	private Label lblNewLabel_42;
	private Label lblNewLabel_44;
	private CTabItem tbtmNewItem_16;
	private static Text text_8;

	public boolean DBcharInfo(final String name) {
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		Connection con1 = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con1 = L1DatabaseFactory.getInstance().getConnection();
			pstm = con1.prepareStatement("SELECT * FROM characters WHERE char_name=?");
			pstm.setString(1, name);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				return false;
			}
			String cname = rs.getString("Clanname");

			if (cname == null || cname.equals("")) {
				Clanname = "[NoPledge]";
			} else {
				Clanname = "[" + cname + "]";
			}
			Pcname = name;
			int lv = rs.getInt("level");
			exp = lv + "Lv     " + nf.format(ExpTable.getExpPercentagedouble(lv, rs.getInt("Exp"))) + "%";
			stasts = "Login: Offline";
			hp = rs.getShort("CurHp") + "/" + rs.getShort("MaxHp");
			mp = rs.getShort("CurMp") + "/" + rs.getShort("MaxMp");
			str = rs.getByte("Str") + "/" + rs.getByte("BaseStr");
			dex = rs.getByte("Dex") + "/" + rs.getByte("BaseDex");
			con = rs.getByte("Con") + "/" + rs.getByte("BaseCon");
			wis = rs.getByte("Wis") + "/" + rs.getByte("BaseWis");
			Int = rs.getByte("Intel") + "/" + rs.getByte("BaseIntel");
			cha = rs.getByte("Cha") + "/" + rs.getByte("BaseCha");
			sp = "" + 0;
			mr = "" + 0 + "%";
			er = "" + 0;
			dg = "" + 0;
			//Ltime = "" + (120 - (rs.getInt("RadungeonTime") % 1000));
			//toptime = "" + (60 - (rs.getInt("OrenTime") % 1000));
			//gitime = "" + (120 - (rs.getInt("GirandungeonTime") % 1000));
			Ltime = "" + 0;
			toptime = "" + 0;
			gitime = "" + 0;
			pk = "" + rs.getInt("PC_Kill");
			deth = "" + rs.getInt("PC_Death");
			if(rs.getTimestamp("lastLogoutTime") != null){
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" , Locale.CANADA );
				String str2 = sdf.format( new Date( rs.getTimestamp("lastLogoutTime").getTime()));
				logindate = str2;

				SimpleDateFormat sdf2 = new SimpleDateFormat( "HH mm ss" , Locale.CANADA );
				String str3 = sdf2.format( new Date( rs.getTimestamp("lastLogoutTime").getTime()));
				logintime = str3;
			} else {
				logindate = "0000-00-00";
				logintime = "00h 00m 00s";
			}
			MaxHp = rs.getShort("MaxHp");
			CurrentHp = rs.getShort("CurHp");
			MaxMp = rs.getShort("MaxMp");
			CurrentMp = rs.getShort("CurMp");

			objid = rs.getInt("objid");
			accountname = rs.getString("account_name");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con1);
		}
		return true;
	}
	public boolean ServercharInfo(final String name) {
		try{
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			L1PcInstance target = L1World.getInstance().getPlayer(name);
			if (target == null)
				return false;
			if (target.getClan() == null) {
				Clanname = "[ServerCharInfo]";
			} else {
				Clanname = "[" + target.getClan().getClanName() + "]";
			}
			Pcname = target.getName();
			exp = target.getLevel() + "Lv     "
			        + nf.format(ExpTable.getExpPercentagedouble(target.getLevel(), target.getExp())) + "%";
			stasts = "Login: Online";
			hp = "" + target.getCurrentHp() + "/" + target.getMaxHp();
			mp = "" + target.getCurrentMp() + "/" + target.getMaxMp();
			str = "" + target.getAbility().getTotalStr() + "/" + target.getAbility().getBaseStr();
			dex = "" + target.getAbility().getTotalDex() + "/" + target.getAbility().getBaseDex();
			con = "" + target.getAbility().getTotalCon() + "/" + target.getAbility().getBaseCon();
			wis = "" + target.getAbility().getTotalWis() + "/" + target.getAbility().getBaseWis();
			Int = "" + target.getAbility().getTotalInt() + "/" + target.getAbility().getBaseInt();
			cha = "" + target.getAbility().getTotalCha() + "/" + target.getAbility().getBaseCha();
			sp = "" + target.getAbility().getSp();
			mr = "" + target.getResistance().getEffectedMrBySkill() + "%";
			er = "" + target.get_Er();
			dg = "" + target.getDg();
			Ltime = "" + (120 - (target.getravatime() / 60));
			toptime = "" + (60 - (target.getivorytime() / 60));
			gitime = "" + (120 - (target.getgirantime() / 60));
			pk = "" + target.getKills();
			deth = "" + target.getDeaths();

			if (target.getLastLoginTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
				String str1 = sdf.format(new Date(target.getLastLoginTime().getTime()));
				logindate = str1;
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH시 mm분 ss초", Locale.CANADA);
				String str2 = sdf2.format(new Date(target.getLastLoginTime().getTime()));
				logintime = str2;
			} else {
				logindate = "0/0/0";
				logintime = "0:0:0";
			}


			MaxHp = target.getMaxHp();
			CurrentHp = target.getCurrentHp();
			MaxMp = target.getMaxMp();
			CurrentMp = target.getCurrentMp();
			objid = target.getId();
			accountname = target.getAccountName();
		}catch(Exception e){e.printStackTrace();}
		return true;
	}


	public static int getDiffDayCount(String fromDate, String toDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate)
					.getTime()) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String nowDate(){
		Calendar cal = Calendar.getInstance();
		java.util.Date currentTime = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String ndate = formatter.format(currentTime);
		return ndate;
	}

	ConcurrentHashMap<String,String[]> charlist = new ConcurrentHashMap<>();

	/*public LetterComposite getLetterComposite() {
		return composite_4;
	}*/
	private Button btnMutePlayer;
	private Button btnBanPlayer;
	private TabItem tabItem_1;
	private Text text_9;
	private TabItem tbtmGm;
	private Text text_11;
	//private Button btnSaveLog;
	private TabItem tabItem_2;
	private Text text_12;
	private Label label_26;
	private Composite composite_5;
	private TabItem tabItem_3;
	private Text text_13;
	private Label lblPortText;
	private TabItem tabItem_4;
	private Text text_14;
	private TabItem tabItem_5;
	private Text text_15;
	private CTabItem tabItem_6;
	public void accountCharDBInfo() {
		charlist.clear();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = L1DatabaseFactory.getInstance().getConnection();
			pstm = conn.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
			pstm.setString(1, accountname);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String name = rs.getString("char_name");
				String clanname = "["+rs.getString("Clanname")+"]";
				if(clanname.equals("[]")||clanname.equals("[null]")){
					clanname = "[No Pledge]";
				}

				int lv = rs.getInt("level");
				String exp = lv+"Lv     "+nf.format(ExpTable.getExpPercentagedouble(lv,rs.getInt("Exp")))+"%";
				String login = "0000-00-00  00:00:00";
				String loginbefore = "0일전";
				if(rs.getTimestamp("lastLogoutTime") != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.CANADA);

					String str2 = sdf.format( new Date( rs.getTimestamp("lastLogoutTime").getTime()));
					login = str2;
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
					String str3 = sdf2.format( new Date(rs.getTimestamp("lastLogoutTime").getTime()));
					loginbefore = getDiffDayCount(str3, nowDate()) + "일전";
				}
				charlist.put(name, new String[]{name, clanname, exp, login, loginbefore});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(conn);
		}
	}


	/**
	 * Acquire account information
	 * @param name
	 */
	public void accountInfo() {
		accountCharDBInfo();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					Account account = Account.load(accountname);
					lblNewLabel_49.setText(account.getName());
					label_27.setText(account.getPassword());
					lblNewLabel_42.setText("IP: " + account.getHost());
					if (account.isBanned())
						btnCheckButton.setSelection(true);
					else
						btnCheckButton.setSelection(false);
					trtmNewTreeitem.removeAll();
					for (String[] s : charlist.values()) {
						trtmNewTreeitem_1 = new TreeItem(trtmNewTreeitem, SWT.NONE);
						trtmNewTreeitem_1.setForeground(SWTResourceManager.getColor(255, 255, 255));
						trtmNewTreeitem_1.setBackground(SWTResourceManager.getColor(51, 51, 51));
						trtmNewTreeitem_1.setText(s[0]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * character information
	 * @param name
	 */
	public void charInfo(final String name) {
		L1PcInstance pc = L1World.getInstance().getPlayer(name);
		if (pc == null) {
			MessageBox messageBox = new MessageBox(shlInbumserverManager, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("No such character name exists!");
			messageBox.open();
			return;
		}
		if (ServercharInfo(name)) {
			inventoryList();
			warehouseList();
		} else if (DBcharInfo(name)) {
			inventoryList();
			warehouseList();
		} else {
			MessageBox messageBox = new MessageBox(shlInbumserverManager, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("No such character name exists!");
			messageBox.open();
			return;
		}

		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					if (pc.isCrown()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/0.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/1.png"));
						}
					}
					if (pc.isKnight()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/2.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/3.png"));
						}
					}
					if (pc.isElf()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/4.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/5.png"));
						}
					}
					if (pc.isWizard()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/6.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/7.png"));
						}
					}
					if (pc.isDarkelf()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/8.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/9.png"));
						}
					}
					if (pc.isDragonknight()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/10.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/11.png"));
						}
					}
					if (pc.isIllusionist()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/12.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/13.png"));
						}
					}
					if (pc.isWarrior()) {
						if (pc.get_sex() == 0) {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/14.png"));
						} else {
							lblClassIcon.setBackgroundImage(SWTResourceManager.getImage("data/img/15.png"));
						}
					}

					lblPledgeText.setText(Clanname);	// Clan name [ ] included
					lblPlayerNameText.setText(Pcname);	// character name
					lblNewLabel_26.setText(exp);		// level experience percentage
					lblLoginText.setText(stasts); 		//Login Status Login : included
					lblNewLabel_30.setText(hp);			//hp 111/111
					label_10.setText(mp); 				//mp 111/111
					lblSTRNum.setText(str);				//str
					lblDEXNum.setText(dex);				//dex
					lblCONNum.setText(con);				//con
					lblWISNum.setText(wis);				//Wiz
					lblINTNum.setText(Int);				//int
					lblCHANum.setText(cha);				//cha
					lblSPNum.setText(sp);				//sp
					lblMRNum.setText(mr);				//mr %
					label_18.setText(er);				//ER
					label_19.setText(dg);				//DG
					lblNewLabel_40.setText(Ltime);		//lastabad time
					lblNewLabel_39.setText(pk);			//PK
					label_20.setText(deth);				//death
					lblNewLabel_35.setText(logindate); 	// logout date
					lblNewLabel_37.setText(logintime); 	// logout time

					progressBar_1.setMaximum(MaxHp);
					progressBar_1.setMinimum(0);
					progressBar_1.setSelection(CurrentHp);

					progressBar_2.setMaximum(MaxMp);
					progressBar_2.setMinimum(0);
					progressBar_2.setSelection(CurrentMp);


					/**Inventory*/
					table_2.removeAll();
					for(L1ItemInstance item : items.values()){
						if(item.getItem().getItemId() == 40308){
							TableItem tableItem = new TableItem(table_2, SWT.NONE);
							item.setIdentified(true);
							if(item.getBless() == 0)
								tableItem.setForeground(SWTResourceManager.getColor(255, 255, 102));
							else if(item.getBless() == 1)
								tableItem.setForeground(SWTResourceManager.getColor(255, 255, 255));
							else if(item.getBless() == 2)
								tableItem.setForeground(SWTResourceManager.getColor(255, 0, 0));
							tableItem.setText(new String[] {item.getLogName(), "" + item.getId()});

							break;
						}
					}

					for(L1ItemInstance item : items.values()){
						if(item.getItem().getItemId() == 40308)continue;
						TableItem tableItem = new TableItem(table_2, SWT.NONE);
						item.setIdentified(true);
						if(item.getBless() == 0)
							tableItem.setForeground(SWTResourceManager.getColor(255, 255, 102));
						else if(item.getBless() == 1)
							tableItem.setForeground(SWTResourceManager.getColor(255, 255, 255));
						else if(item.getBless() == 2)
							tableItem.setForeground(SWTResourceManager.getColor(255, 0, 0));
						tableItem.setText(new String[] {item.getLogName(), "" + item.getId()});
					}
					/**Inventory*/

					/**Warehouse*/
					/*table_3.removeAll();
					for(L1ItemInstance item : warehouseitems.values()){
						if(item.getItem().getItemId() == 40308){
							TableItem tableItem = new TableItem(table_3, SWT.NONE);
							item.setIdentified(true);
							if(item.getBless() == 0)
								tableItem.setForeground(SWTResourceManager.getColor(255, 255, 102));
							else if(item.getBless() == 1)
								tableItem.setForeground(SWTResourceManager.getColor(255, 255, 255));
							else if(item.getBless() == 2)
								tableItem.setForeground(SWTResourceManager.getColor(255, 0, 0));
							tableItem.setText(new String[] {item.getLogName(), "" + item.getId()});

							break;
						}
					}


					for(L1ItemInstance item : warehouseitems.values()){
						if(item.getItem().getItemId() == 40308)continue;
						TableItem tableItem = new TableItem(table_3, SWT.NONE);
						item.setIdentified(true);
						if(item.getBless() == 0)
							tableItem.setForeground(SWTResourceManager.getColor(255, 255, 102));
						else if(item.getBless() == 1)
							tableItem.setForeground(SWTResourceManager.getColor(255, 255, 255));
						else if(item.getBless() == 2)
							tableItem.setForeground(SWTResourceManager.getColor(255, 0, 0));
						tableItem.setText(new String[] {item.getLogName(), ""+item.getId()});
					}*/
					/**Warehouse*/


				}catch(Exception e){e.printStackTrace();}
			}
		});
	}

	public void ServerInfoPrint(final String AdenMake,final String AdenConsume,final String AdenTax,
			final String Bugdividend,final String AccountCount,final String CharCount,final String PvPCount,
			final String PenaltyCount,final String ClanMaker,final String Maxuser,final String ThreadCount,final String Memory) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					lblEXPRATE.setText(""+(int)Config.RATE_XP); // experience multiplier
					lblADENARATE.setText(""+(int)Config.RATE_DROP_ADENA); //adena multiplier
					lblDROPRATE.setText(""+(int)Config.RATE_DROP_ITEMS); //drop multiplier
					/*lblNewLabel_20.setText(AdenMake); // Aden Total
					label.setText(AdenConsume); //Aden Total Recovery
					lblNewLabel_21.setText(AdenTax+"%"); //tax multiplier
					lblNewLabel_22.setText(Bugdividend); //maximum dividend
					label_1.setText(AccountCount); // accounts created
					lblNewLabel_23.setText(CharCount); //캐릭 생성
					label_2.setText(PvPCount); //pvp number
					label_4.setText(PenaltyCount); //number of penalties
					label_3.setText(ClanMaker); // Clan Creation
					lblMAXUSERS.setText(Maxuser);*/
					lblThreadCountNum.setText(ThreadCount);
					lblMemoryNum.setText(Memory); //memory capacity

					Calendar cal = Calendar.getInstance();
					long timeMin = ((cal.getTimeInMillis() - Server.StartTime.getTimeInMillis()) / 1000) / 60;
					long timeHour = timeMin / 60;
					timeMin -= timeHour * 60;
					long timeDay = timeHour / 24;
					timeHour -= timeDay * 24;
					lblNewLabel_44.setText(timeDay + " days, " + timeHour + " hours, " + timeMin + " mins"); //server uptime
					lblPortText.setText("Port: ["+ Config.GAME_SERVER_PORT +"]");
					//progressBar.setSelection(0);

				}catch(Exception e){e.printStackTrace();}
			}
		});

	}

	/*public void progressBarPrint(final int value) {
		display.syncExec(new Runnable() {
			public void run() {
				try {
					if ((progressBar.getSelection() + value) <= 60)
						progressBar.setSelection(progressBar.getSelection() + value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public static Shell getShell() {
		return shlInbumserverManager;
	}


	static public void toMessageBox(final String msg) {
		toMessageBox(SERVER_VERSION, msg);
	}

	static public void toMessageBox(final String title, final String msg) {
		MessageBox messageBox = new MessageBox(shlInbumserverManager, SWT.ICON_WARNING);
		messageBox.setText(String.format("WARNING: %s", title));
		messageBox.setMessage(msg);
		messageBox.open();
	}
}

