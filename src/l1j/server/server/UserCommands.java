/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package l1j.server.server;

import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import l1j.server.Base64;
import l1j.server.L1DatabaseFactory;
import l1j.server.MJCTSystem.Loader.MJCTLoadManager;
import l1j.server.server.clientpackets.C_SelectCharacter;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_Ability;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_Message_YN;//혈맹파티
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewSkillIcons;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_Translate;
import l1j.server.server.serverpackets.S_Unknown2;
import l1j.server.server.serverpackets.S_War;
import l1j.server.server.serverpackets.S_문장주시;
import l1j.server.server.utils.CalcStat;
import l1j.server.server.utils.SQLUtil;

import server.LineageClient;

public class UserCommands {
	private static Logger _log = Logger.getLogger(UserCommands.class.getName());

	private static UserCommands _instance;

	private UserCommands() {
	}

	public static UserCommands getInstance() {
		if (_instance == null) {
			_instance = new UserCommands();
		}
		return _instance;
	}

	public void handleCommands(L1PcInstance pc, String cmdLine) {
		//System.out.println("In handleCommans (user) with command: " + cmdLine);
		StringTokenizer token = new StringTokenizer(cmdLine);
		if (!token.hasMoreTokens()) {
			return;
		}
		// the first space is treated as a command, and the space after that is treated as a parameter separated by a space.
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();

		S_Translate translate = new S_Translate();
		cmd = translate.Text(0, cmd);
		param = translate.Text(0, param);

		StringBuilder sb = new StringBuilder();
		sb.append("UserCMD[").append(cmd).append("] Param[").append(param).append("] Player[").append(pc.getName()).append("]");
		String msg = sb.toString();
		_log.info(msg);

		if (cmd.equalsIgnoreCase("help")) {
			help(pc);
		} else if (cmd.equalsIgnoreCase("refresh")) {
			tell(pc);
		} else if (cmd.equalsIgnoreCase("droplog")) {
			Ment(pc, param);
		} else if (cmd.equalsIgnoreCase("light")) {
			maphack(pc, param);
		} else if (cmd.equalsIgnoreCase("buff")) {
			buff(pc);
		} else if (cmd.equalsIgnoreCase("killnotice")) {
			killment(pc, cmd, param);
		} else if (cmd.equalsIgnoreCase("info")) {
			info(pc);
		} else if (cmd.equalsIgnoreCase("advinfo")) {
			advancedInfo(pc);
		/*} else if (cmd.equalsIgnoreCase("changepassword")) {
			changePassword(pc, param);
		} else if (cmd.equalsIgnoreCase("keyword")) {
			quize(pc, param);
		} else if (cmd.equalsIgnoreCase("keyworddelete")) {
			quize2(pc, param);
		} else if (cmd.equalsIgnoreCase("phone")) {
			phone(pc, param);
		} else if (cmd.equalsIgnoreCase("age")) {
			age(pc, param);
		} else if (cmd.equalsIgnoreCase("marble")) {
			MJCTLoadManager.commands(pc, param);
		} else if (cmd.equalsIgnoreCase("privateshop")) {
			privateShop1(pc);
		} else if (cmd.equalsIgnoreCase("telehome")) {
			telrek(pc);
		} else if (cmd.equalsIgnoreCase("bloodmark")) {
			혈마크(pc, param);*/
		/*} else if (cmd.equalsIgnoreCase("pledgeparty")) {
			ClanParty(pc);
		} else if (cmd.equalsIgnoreCase("sealedoff")) {
			Sealedoff(pc, param);
		} else if (cmd.equalsIgnoreCase("autorooting")) {
			autoroot(pc, cmd, param);
		} else if (cmd.equalsIgnoreCase("autorootinglog")) {
			ment(pc, cmd, param);*/
		/*} else if (cmd.equalsIgnoreCase("hunt1")) {
			Hunt(pc, param);
		} else if (cmd.equalsIgnoreCase("hunt2")) {
			Hunt2(pc, param);
		} else if (cmd.equalsIgnoreCase("hunt3")) {
			Hunt3(pc, param);*/
		} else {
			S_ChatPacket s_chatpacket = new S_ChatPacket(pc, cmdLine, Opcodes.S_SAY, 0);
			if (!pc.getExcludingList().contains(pc.getName())) {
				pc.sendPackets(s_chatpacket);
			}
			for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
				if (!listner.getExcludingList().contains(pc.getName())) {
					listner.sendPackets(s_chatpacket);
				}
			}
			// doppel treatment
			L1MonsterInstance mob = null;
			for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
				if (obj instanceof L1MonsterInstance) {
					mob = (L1MonsterInstance) obj;
					if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
						Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, cmdLine, 0), true);
					}
				}
			}
		}
	}

	private void info(L1PcInstance pc) {
		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 60 > curtime) {
			pc.sendPackets(new S_SystemMessage("You must wait 60 seconds before you can use this command again."));
			return;
		}
		if (pc.isDead()) {
			pc.sendPackets(new S_SystemMessage("You can not use this command when you are dead."));
			return;
		}
		int level = pc.getLevel();
		int curLevelExp = ExpTable.getExpByLevel(level);
		int nextLevelExp = ExpTable.getExpByLevel(level + 1);
		double needExp = nextLevelExp - curLevelExp;
		double curExp = pc.getExp() - curLevelExp;
		int percentExp = (int) ((curExp / needExp) * 100.0);
		int hpRegen = pc.getHpr() + pc.getInventory().hpRegenPerTick();
		int mpRegen = pc.getMpr() + pc.getInventory().mpRegenPerTick();

		pc.sendPackets(new S_SystemMessage(pc, "Information request started."), true);
		pc.sendPackets(new S_SystemMessage("========================================================"), true);
		pc.sendPackets(new S_SystemMessage("Name: " + pc.getName() + "/" + pc.getTitle() + " Pledge: " + pc.getClanname() + " Level: " + level + " [" + percentExp + "%]"), true);
		pc.sendPackets(new S_SystemMessage(" AC: " + pc.getAC().getAc() + " MR: " + pc.getResistance().getMr() + " SP: " + pc.getAbility().getSp()
				+ " Alignment: " + pc.getLawful() + " PKCount: " + pc.get_PKcount() + " Deaths: " + pc.getDeaths()), true);

		pc.sendPackets(new S_SystemMessage(" HP: [" + pc.getCurrentHp() + '/' + pc.getMaxHp() + "] HPR: " + hpRegen
				+ " MP: [" + pc.getCurrentMp() + '/' + pc.getMaxMp() + "] MPR: " + mpRegen), true);
		pc.sendPackets(new S_SystemMessage(" STR: " + pc.getAbility().getBaseStr() + "/" + pc.getAbility().getTotalStr() + " "
				+ "DEX: " + pc.getAbility().getBaseDex() + "/" + pc.getAbility().getTotalDex() + " "
				+ "CON: " + pc.getAbility().getBaseCon() + "/" + pc.getAbility().getTotalCon() + " "
				+ "INT: " + pc.getAbility().getBaseInt() + "/" + pc.getAbility().getTotalInt() + " "
				+ "WIS: " + pc.getAbility().getBaseWis() + "/" + pc.getAbility().getTotalWis() + " "
				+ "CHA: " + pc.getAbility().getTotalCha()), true);
		pc.sendPackets(new S_SystemMessage(" Dodge: " + pc.getDg() + " Evasion: " + pc.get_Er()), true);
		pc.sendPackets(new S_SystemMessage("[Skill Resistance]" + "\n Spirit: " + pc.getSpiritTolerance() + " Dragon: " + pc.getDragonLangTolerance()
		+ " Fear: " + pc.getFearTolerance() + " Technique: " + pc.getTechniqueTolerance() + " All Resist: " + pc.getAllTolerance()), true);
		pc.sendPackets(new S_SystemMessage("[Elemental Resistance]" + "\n Earth: " + pc.getResistance().getEarth() + " Fire: " + pc.getResistance().getFire() + " Wind: " + pc.getResistance().getWind() + " Water: " + pc.getResistance().getWater()), true);
		pc.sendPackets(new S_SystemMessage("[Skill Hit]" + "\n Spirit: " + pc.getSpiritHit() + " Dragon: " + pc.getDragonLangHit()
		+ " Fear: " + pc.getFearHit() + " Technique: " + pc.getTechniqueHit() + " All Resist: " + pc.getAllHit()), true);
		pc.sendPackets(new S_SystemMessage("[Attack Stats]" + "\n Ranged Hit: " + pc.getBowHitup() + " Melee Hit: " + pc.getHitup() + " Magic Hit: " + CalcStat.magicHit(pc.getAbility().getTotalInt())), true);
		pc.sendPackets(new S_SystemMessage("========================================================"), true);
		pc.setQuizTime(curtime);
	}

	private void advancedInfo(L1PcInstance pc) {
		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 60 > curtime) {
			pc.sendPackets(new S_SystemMessage("You must wait 60 seconds before you can use this command again."));
			return;
		}
		if (pc.isDead()) {
			pc.sendPackets(new S_SystemMessage("You can not use this command when you are dead."));
			return;
		}
		pc.sendPackets(new S_SystemMessage("========================================================"), true);
		pc.sendPackets(new S_SystemMessage("Movement Speed: " + pc.getMoveSpeed() + " Heading: " + pc.getHeading() + " Invis: " + pc.isInvisble()), true);
		pc.sendPackets(new S_SystemMessage("Access Level: " + pc.getAccessLevel() + " Account: " + pc.getAccountName() + " Einhasad: " + pc.getAinHasad()), true);
		pc.setQuizTime(curtime);
	}

	private void help(L1PcInstance pc) {
		pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "[advinfo] [changepassword] [droplog] [help] [info] [killnotice]"), true);
		pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "[light] [refresh] [time]"), true);

		/*pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "\\\\aA시장 내 아이템 판매 가격 및 매입가격 검색 요령"), true);
		pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "\\aA ex)/시세 무관의 양손검O or/시세 무관의 O %띄어쓰기 주의 및 앞글자로 검색%"));
		pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "\\aA ex)/시세 무관의양손검X or/시세 양손검X %띄어쓰기 주의 및 앞글자로 검색%"));*/
	}

	/** Clan Party Application Command **/
	public void ClanParty(L1PcInstance pc) {
		int ClanId = pc.getClanid();
		if (ClanId != 0 && (pc.getClanRank() == L1Clan.CLAN_RANK_GUARDIAN || pc.isCrown())) { // Clan[O] [군주,수호기사]
			for (L1PcInstance SearchBlood : L1World.getInstance().getAllPlayers()) {
				if (SearchBlood.getClanid() != ClanId || SearchBlood.isPrivateShop() || SearchBlood.isInParty()) {
					continue;
				} else if (SearchBlood.getName() != pc.getName()) {
					pc.setPartyType(1); // Party type setting
					SearchBlood.setPartyID(pc.getId()); //Party ID setting
					SearchBlood.sendPackets(new S_Message_YN(954, pc.getName())); // split party application
					String[] parameters = {SearchBlood.getName()};
					pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "You have a request from [%1] for a party", parameters));
				}
			}
		} else { // No Clan or Lord or Guardian Knight [X]
			pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "Only pledge Royal can use this command."));
		}
	}

	private void maphack(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String on = st.nextToken();
			if (on.equalsIgnoreCase("on")) {
				pc.sendPackets(new S_Ability(3, true));
				pc.sendPackets(new S_SystemMessage("Light is now [ON]"));
			} else if (on.equals("off")) {
				pc.sendPackets(new S_Ability(3, false));
				pc.sendPackets(new S_SystemMessage("Light is now [OFF]"));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("CMD Error: Please use .light [on/off]"));
		}
	}

	private void tell(L1PcInstance pc) {
		try {
			if (pc.isPinkName()) {
				pc.sendPackets(new S_SystemMessage("You can not use this command in combat."));
				return;
			}
			if (pc.isFishing() || pc.isFishingReady() || pc.isPrivateShop()) {
				pc.sendPackets(new S_SystemMessage("You can not use this command here."));
				return;
			}
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 120 > curtime) {
				pc.sendPackets(new S_SystemMessage("You must wait at least 2 minutes before using this command again."));
				return;
			}
			//LinAllManager.serverMessageAppend(" UCmd[refresh] Param[0] Player[" + pc.getName() + "]");
			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
			pc.sendPackets(new S_SystemMessage("Surrounding objects have been reloaded."));
			pc.setQuizTime(curtime);
		} catch (Exception exception35) {
		}
	}

	private void Ment(L1PcInstance pc, String param) {
		if (param.equalsIgnoreCase("off")) {
			pc.sendPackets(new S_SystemMessage("Drop logging is now [OFF]"));
			pc.RootMent = false;
			pc.PartyRootMent = false;
		} else if (param.equalsIgnoreCase("on")) {
			pc.sendPackets(new S_SystemMessage("Drop logging is now [ON]"));
			pc.RootMent = true;
			pc.PartyRootMent = true;
		} else {
			pc.sendPackets(new S_SystemMessage("CMD Error: Please use .droplog [on/off]"));
		}
	}

	public void ment(L1PcInstance pc, String cmd, String param) { // by사부 멘트
		if (param.equalsIgnoreCase("off")) {
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_MENT, 0);
			pc.sendPackets(new S_SystemMessage("\\fYAutorouting comments is OFF."));
		} else if (param.equalsIgnoreCase("on")) {
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_MENT);
			pc.sendPackets(new S_SystemMessage("\\fYAutorouting comments is ON."));
		} else {
			pc.sendPackets(new S_SystemMessage("-automatic [on/off] for change autorouting comments."));
		}
	}

	public void autoroot(L1PcInstance pc, String cmd, String param) {
		if (param.equalsIgnoreCase("off")) { // byMaster auto-routing on command
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_AUTOROOT, 0);
			pc.sendPackets(new S_SystemMessage("\\fYAutorouting is OFF."));

		} else if (param.equalsIgnoreCase("on")) { // by사부 오토루팅 켬끔 명령어
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_AUTOROOT);
			pc.sendPackets(new S_SystemMessage("\\fYAutorouting is ON."));

		} else { // byMaster auto-routing on command
			pc.sendPackets(new S_SystemMessage("-autorouting [on/off] for change autorouting state."));
		}
	}

	private void 혈마크(L1PcInstance pc, String param) {
		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 10 > curtime) {
			pc.sendPackets(new S_SystemMessage("A delay of 10 seconds between commands is required."));
			return;
		}
		if (pc.isDead()) {
			pc.sendPackets(new S_SystemMessage("You can not use this command if you are dead."));
			return;
		}
		int i = 1;
		if (pc.문장주시) {
			i = 3;
			pc.문장주시 = false;
			pc.sendPackets(new S_문장주시(pc.getClan(), i), false);
			pc.sendPackets(new S_ClanWindow(S_ClanWindow.혈마크띄우기, pc.getClan().getmarkon()), false);
			pc.sendPackets(new S_NewUI(0x19, pc.getClan().getClanName(), pc), false);
		} else
			pc.문장주시 = true;
		for (L1Clan clan : L1World.getInstance().getAllClans()) {
			if (clan != null) {
				pc.sendPackets(new S_War(i, pc.getClanname(), clan.getClanName()));
				pc.sendPackets(new S_문장주시(pc.getClan(), i), true);
				pc.sendPackets(new S_ClanWindow(S_ClanWindow.혈마크띄우기, pc.getClan().getmarkon()), true);
				pc.sendPackets(new S_NewUI(0x19, pc.getClan().getClanName(), pc), true);
			}
		}
		pc.setQuizTime(curtime);
	}

	public static void privateShop1(L1PcInstance pc) {
		try {
			if (!pc.isPrivateShop()) {
				pc.sendPackets(new S_SystemMessage("You can only use it in a private store."), true);
				return;
			}
			if (pc.getMapId() != 800) {
				pc.sendPackets(new S_SystemMessage("Private stores can only be opened in the market."));
				return;
			}
			for (L1PcInstance target : L1World.getInstance().getAllPlayers3()) {
				if (target.getId() != pc.getId() && target.getAccountName().toLowerCase().equals(pc.getAccountName().toLowerCase()) && target.isPrivateShop()) {
					pc.sendPackets(new S_ChatPacket(pc, "You have already another character in a private store."));
					return;
				}
			}
			LineageClient client = pc.getNetConnection();

			pc.setNetConnection(null);
			pc.setPacketOutput(null);
			pc.stopHpRegenerationByDoll();
			pc.stopMpRegenerationByDoll();
			pc.setAIprivateShop(true);
			try {
				pc.save();
				pc.saveInventory();
			} catch (Exception e) {
			}
			client.setActiveChar(null);
			client.CharReStart(true);
			client.sendPacket(new S_Unknown2(1)); // 리스버튼을 위한 구조변경 // Episode U
		} catch (Exception e) {
		}
	}

	public void killment(L1PcInstance pc, String cmd, String param) { // by사부 멘트
		if (param.equalsIgnoreCase("off")) {
			pc.킬멘트 = false;
			pc.sendPackets(new S_SystemMessage("Kills display is OFF"));
		} else if (param.equalsIgnoreCase("on")) {
			pc.킬멘트 = true;
			pc.sendPackets(new S_SystemMessage("Kills display is ON"));
		} else {
			pc.sendPackets(new S_SystemMessage("-kills [on/off] to activate or disable the kills display"));
		}
	}

	private static boolean isDisitAlpha(String str) {
		boolean check = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)) // 숫자가 아니라면
					&& Character.isLetterOrDigit(str.charAt(i)) // 특수문자라면
					&& !Character.isUpperCase(str.charAt(i)) // 대문자가 아니라면
					&& !Character.isLowerCase(str.charAt(i))) { // 소문자가 아니라면
				check = false;
				break;
			}
		}
		return check;
	}

	public static String checkPassword(String accountName) {
		String _inputPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			// statement =
			// con.prepareStatement("select account_name from characters where char_name
			// Like '"
			// + pc.getName() + "'");
			pstm = con.prepareStatement("select password from accounts where login = ?");
			pstm.setString(1, accountName);
			rs = pstm.executeQuery();
			if (rs.next()) {
				_inputPwd = rs.getString("password");
			}
			return _inputPwd;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return _inputPwd;
	}

	public static boolean checkPassword(String accountName, String _pwd, String rawPassword) {
		String _inputPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT password(?) as pwd ");

			pstm.setString(1, rawPassword);
			rs = pstm.executeQuery();
			if (rs.next()) {
				_inputPwd = rs.getString("pwd");
			}
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			if (_pwd.equals(_inputPwd)) { // 동일하다면
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	/*private static String encodePassword(final String rawPassword) {
		try {
			byte[] buf = rawPassword.getBytes("UTF-8");
	        buf = MessageDigest.getInstance("SHA-512").digest(buf);
	        return Base64.encodeBytes(buf);
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
	    }
	}*/

	/*private void to_Change_Passwd(L1PcInstance pc, String passwd) {
		PreparedStatement statement = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		java.sql.Connection con = null;
		try {
			String login = null;
			String password = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			password = encodePassword(passwd);
			statement = con.prepareStatement("select account_name from characters where char_name Like '" + pc.getName() + "'");
			rs = statement.executeQuery();

			while (rs.next()) {
				login = rs.getString(1);
				pstm = con.prepareStatement("UPDATE accounts SET password=? WHERE login Like '" + login + "'");
				pstm.setString(1, password);
				pstm.execute();
				pc.sendPackets(new S_ChatPacket(pc, "New Pass Info: (" + passwd + ") has been set.", Opcodes.S_SAY, 2));
				pc.sendPackets(new S_SystemMessage("Password change has been completed successfully."));
			}
			login = null;
			password = null;
		} catch (Exception e) {
			System.out.println("to_Change_Passwd() Error : " + e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(statement);
			SQLUtil.close(con);
		}
	}*/

	/*private void changePassword(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String passwd = tok.nextToken();
			Account account = Account.load(pc.getAccountName()); // 추가
			if (account.getquize() != null) {
				pc.sendPackets(new S_SystemMessage("You cannot change the keyword unless you delete it."));
				pc.sendPackets(new S_SystemMessage("Yes) .Delete Keyword"));
				return;
			} // When changing the password, if the quiz is not set, it cannot be changed.
			if (passwd.length() < 4) {
				pc.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 짧습니다."));
				pc.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."));
				return;
			}

			if (passwd.length() > 12) {
				pc.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 깁니다."));
				pc.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."));
				return;
			}

			if (!isDisitAlpha(passwd)) {
				pc.sendPackets(new S_SystemMessage("비번에 허용되지 않는 문자가 포함 되어 있습니다."));
				return;
			}

			to_Change_Passwd(pc, passwd);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".비번변경 [변경할 암호]를 입력 해주세요."));
		}
	}*/

	/*private void phone(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String name = tok.nextToken();
			String phone = tok.nextToken();

			if (name.length() > 12 || phone.length() > 12) {
				pc.sendPackets(new S_SystemMessage("잘못된 길이 입니다."));
				return;
			}
			L1Quest quest = pc.getQuest();
			int lv1_step = quest.get_step(L1Quest.QUEST_PHONE);
			if (lv1_step == L1Quest.QUEST_END) {
				phonenumber(name, phone, pc.getName());
				pc.sendPackets(new S_SystemMessage("\\fT(" + name + ") 님 " + phone + " 번호로 변경 되셨습니다. 감사합니다."));
			} else {
				long sysTime = System.currentTimeMillis();
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(sysTime + (3600000 * 24 * (long) 1) + 10000);// 7일
				pc.sendPackets(new S_NewSkillIcons(L1SkillId.PC_ROOM, true, -1));
				pc.PCROOM_BUFF = true;
				String s = "08 01 f1 d5";// 피씨방..
				pc.sendPackets(new S_NewCreateItem(126, s));
				pc.getNetConnection().getAccount().setBuff_PC방(deleteTime);
				pc.getNetConnection().getAccount().update피씨방();
				pc.getQuest().set_end(L1Quest.QUEST_PHONE);
				phonenumber(name, phone, pc.getName());
				pc.sendPackets(new S_SystemMessage("\\fT(" + name + ") 님 " + phone + " 번호로 고정 신청 되셨습니다. 감사합니다."));
				pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[고정신청 보상] PC방 혜택이 적용 됩니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".고정신청 [성함 폰번호] 로 입력해주세요.[]문자는 제외"));
			pc.sendPackets(new S_SystemMessage("EX).고정신청 홍길동 0000000000 (번호에 스페이스가 들어가면 안됩니다.)"));
		}
	}*/

	/*private void telrek(L1PcInstance pc) {
		try {
			int castle_id = L1CastleLocation.getCastleIdByArea(pc);
			// /////////////////////// 타임/////////////////////////////////
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime) {
				pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "A delay of 20 seconds between commands is required."));
				return;
			}
			// /////////////////////// 타임/////////////////////////////////
			if (pc.getMapId() == 5302 || pc.getMapId() == 5490) {
				pc.sendPackets(new S_SystemMessage("이곳에서는 사용할 수 없습니다."));
				return;
			}
			if (CharPosUtil.getZoneType(pc) == 0 && castle_id != 0) {
				pc.sendPackets(new S_SystemMessage("성주변에서는 사용 할 수 없습니다."));
				return;
			}
			if (pc.getMapId() == 350 || pc.getMapId() == 5153) {
				pc.sendPackets(new S_SystemMessage("이곳에서는 사용할 수 없습니다."));
				return;
			}
			if (pc.isPinkName() || pc.isParalyzed() || pc.isSleeped()) {
				pc.sendPackets(new S_SystemMessage("보라중 마비중 잠수중에는 사용할 수 없습니다."));
				return;
			}
			if (pc.isDead()) {
				pc.sendPackets(new S_SystemMessage("죽은 상태에선 사용할 수 없습니다."));
				return;
			}
			if (!(pc.getInventory().checkItem(40308, 1000))) {
				pc.sendPackets(new S_SystemMessage("1000 아데나가 부족합니다."));
				return;
			}
			pc.getInventory().consumeItem(40308, 1000);

			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
			pc.sendPackets(new S_SystemMessage("1000 아데나가 소모 되었습니다."));
			// pc.sendPackets(new S_SystemMessage("주변 오브젝트를 재로딩 하였습니다."));
			pc.setQuizTime(curtime);
			// } catch (Exception e) {
		} catch (Exception exception35) {
		}
	}*/

	/*private void age(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String AGE = tok.nextToken();
			int AGEint = Integer.parseInt(AGE);
			if (AGEint > 60 || AGEint < 12) {
				pc.sendPackets(new S_SystemMessage(pc, "Set it to your actual age."));
				return;
			}
			pc.setAge(AGEint);
			pc.save();
			pc.sendPackets(new S_SystemMessage(pc, "The age " + AGEint + "Vertical set. Appears during clan chat."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(pc, ".나이 숫자 형식으로 입력.(혈맹 채팅 시 표시됨)"));
		}
	}*/

	private void Hunt(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			// int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()) {
					return;
				}

				if (pc.getClanid() != target.getClanid()) {
					pc.sendPackets(new S_SystemMessage("You cannot be wanted by other clan members. If you try again, 100 million Aden is consumed."));
					return;
				}
				// if (char_name.equals(pc.getName())) {
				// pc.sendPackets(new S_SystemMessage("자신에게 현상금을 걸수 없습니다."));
				// return;
				// }
				if ((target.getHuntCount() == 1) || (target.getHuntCount() == 2) || (target.getHuntCount() == 3)) {
					pc.sendPackets(new S_SystemMessage("is already wanted"));
					return;
				}

				if (!(pc.getInventory().checkItem(40308, 1000000))) {
					pc.sendPackets(new S_SystemMessage("There is not enough adena. [Required amount: 1 million adena]"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("Please enter a short reason in 20 characters"));
					return;
				}

				target.setHuntCount(1);
				target.setHuntPrice(1000000);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastServerMessage("\\aD[" + target.getName() + "]A bounty has been placed on the head of.");
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ Wanted ]:  " + target.getName() + "  ]"));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ Reason ]: " + story + "  "));
				pc.getInventory().consumeItem(40308, 1000000);
				// huntoption(pc);[ 수배자 ]:
				L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
				pc.initBeWanted();
				int[] beWanted = { 1, 1, 1, 1, 1, 1 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			} else {
				pc.sendPackets(new S_SystemMessage("You are not connected."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".Wanted 1st [character name] [reason]"));
			pc.sendPackets(new S_SystemMessage("====== Wanted effect content ======"));
			pc.sendPackets(new S_SystemMessage("=== Hit +1 / Damage +1 / SP +1 ==="));
		}
	}

	private void Hunt2(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			// int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()) {
					return;
				}

				if (pc.getClanid() != target.getClanid()) {
					pc.sendPackets(new S_SystemMessage("다른 혈맹원에게 수배를 걸수가없습니다 한번더 시도시 1억아덴 소모"));
					return;
				}
				// if (char_name.equals(pc.getName())) {
				// pc.sendPackets(new S_SystemMessage("자신에게 현상금을 걸수 없습니다."));
				// return;
				// }

				if ((target.getHuntCount() == 2) || (target.getHuntCount() == 3)) {
					pc.sendPackets(new S_SystemMessage("이미 수배 되어있습니다"));
					return;
				}

				if (!(pc.getInventory().checkItem(40308, 5000000))) {
					pc.sendPackets(new S_SystemMessage("아데나가 부족합니다.[필요수량:500만 아데나]"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("이유는 짧게 20글자로 입력하세요"));
					return;
				}

				target.setHuntCount(2);
				target.setHuntPrice(5000000);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastServerMessage("\\aD[" + target.getName() + "]의 목에 현상금이 걸렸습니다.");
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ 수배자 ]:  " + target.getName() + "  ]"));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ 이유 ]: " + story + "  "));
				pc.getInventory().consumeItem(40308, 5000000);
				// huntoption(pc);
				L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
				pc.initBeWanted();
				int[] beWanted = { 2, 2, 2, 2, 2, 2 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			} else {
				pc.sendPackets(new S_SystemMessage("접속중이지 않습니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".수배2단 [캐릭터명] [이유]"));
			pc.sendPackets(new S_SystemMessage("====== 수배 효과 내용 ======"));
			pc.sendPackets(new S_SystemMessage("=추타+2 / 리덕+2 / SP+2="));

		}
	}

	private void Hunt3(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			// int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()) {
					return;
				}
				if (pc.getClanid() != target.getClanid()) {
					pc.sendPackets(new S_SystemMessage("다른 혈맹원에게 수배를 걸수가없습니다 한번더 시도시 1억아덴 소모"));
					return;
				}
				// if (char_name.equals(pc.getName())) {
				// pc.sendPackets(new S_SystemMessage("자신에게 현상금을 걸수 없습니다."));
				// return;
				// }

				if (target.getHuntCount() == 3) {
					pc.sendPackets(new S_SystemMessage("이미 수배 되어있습니다"));
					return;
				}

				if (!(pc.getInventory().checkItem(40308, 10000000))) {
					pc.sendPackets(new S_SystemMessage("아데나가 부족합니다.[필요수량:1000만 아데나]"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("이유는 짧게 20글자로 입력하세요"));
					return;
				}

				target.setHuntCount(3);
				target.setHuntPrice(10000000);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastServerMessage("\\aD[" + target.getName() + "]의 목에 현상금이 걸렸습니다.");
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ 수배자 ]:  " + target.getName() + "  ]"));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ 이유 ]: " + story + "  "));
				pc.getInventory().consumeItem(40308, 10000000);
				// huntoption(pc);
				L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
				pc.initBeWanted();
				int[] beWanted = { 5, 5, 5, 5, 5, 5 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			} else {
				pc.sendPackets(new S_SystemMessage("접속중이지 않습니다."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".수배3단 [캐릭터명] [이유]"));
			pc.sendPackets(new S_SystemMessage("====== 수배 효과 내용 ======"));
			pc.sendPackets(new S_SystemMessage("=추타+5 / 리덕+5 / SP+5="));

		}
	}

	private void quize(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			// String user = tok.nextToken();
			String quize = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize.length() < 6) {
				pc.sendPackets(new S_SystemMessage("최소 6자 이상 입력해 주십시오."));
				return;
			}

			if (quize.length() > 12) {
				pc.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."));
				return;
			}
			if (!isDisitAlpha(quize)) {
				pc.sendPackets(new S_SystemMessage("키워드에 허용되지 않는 문자가 포함되었습니다."));
				return;
			}

			if (account.getquize() != null) {
				pc.sendPackets(new S_SystemMessage("이미 키워드가 설정되어 있습니다."));
				return;
			}
			account.setquize(quize);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("키워드가 [" + quize + "]으로 입력되었습니다. 키워드는 다시 확인과 변경이 불가능하니 유의하시기 바랍니다."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".키워드 설정하실키워드를 입력해주세요.\n최소 6자 이상 입력하시길 바랍니다."));
		}
	}

	private void quize2(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String quize2 = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize2.length() < 4) {
				pc.sendPackets(new S_SystemMessage("입력하신 키워드의 자릿수가 너무 짧습니다."));
				pc.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."));
				return;
			}

			if (quize2.length() > 12) {
				pc.sendPackets(new S_SystemMessage("입력하신 키워드의 자릿수가 너무 깁니다."));
				pc.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."));
				return;
			}

			if (account.getquize() == null || account.getquize() == "") {
				pc.sendPackets(new S_SystemMessage("키워드가 설정되어 있지 않습니다."));
				return;
			}

			if (!quize2.equals(account.getquize())) {
				pc.sendPackets(new S_SystemMessage("설정된 키워드와 일치하지 않습니다."));
				return;
			}

			if (!isDisitAlpha(quize2)) {
				pc.sendPackets(new S_SystemMessage("키워드에 허용되지 않는 문자가 포함되었습니다."));
				return;
			}
			account.setquize(null);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("키워드가 삭제되었습니다."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("사용 예).키워드삭제 암호(키워드)"));
		}
	}

	private void Sealedoff(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String param1 = tok.nextToken();
			int off = Integer.parseInt(param1);
			if (off > 15 || off < 0) {
				pc.sendPackets(new S_SystemMessage("Cancellation orders cannot be applied for more than [15]."));
				return;
			}
			if (off == 0) {
				pc.setSealScrollCount(0);
				pc.setSealScrollTime(0);
				pc.sendPackets(new S_SystemMessage("Cancel order request has been initialized."));
			} else {
				int sealScrollTime = (int) (System.currentTimeMillis() / 1000) + 3 * 24 * 3600;
				pc.setSealScrollTime(sealScrollTime);
				pc.setSealScrollCount(off);
				pc.sendPackets(new S_SystemMessage("release order [" + off + "]The chapter has been applied for."));
				pc.sendPackets(new S_SystemMessage("Automatically disbursed [3] days from today's date."));
			}
			pc.save();
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".Seal release application [Number of copies to apply for]"));
		}
	}

	public void phonenumber(String name, String phone, String chaname) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "INSERT INTO UserPhone SET name=?,pnumber=?, chaname=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, name);
			pstm.setString(2, phone);
			pstm.setString(3, chaname);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static final int[] allBuffSkill = { L1SkillId.FIRE_SHIELD, PHYSICAL_ENCHANT_DEX, PHYSICAL_ENCHANT_STR, BLESS_WEAPON, ADVANCE_SPIRIT };

	private void buff(L1PcInstance pc) {
		if (pc.isDead())
			return;
		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 20 > curtime) {
			pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "A delay of 20 seconds between commands is required."));
			return;
		}
		if (pc.getLevel() <= 59) {
			try {
				L1SkillUse l1skilluse = new L1SkillUse();
				for (int element : allBuffSkill) {
					l1skilluse.handleCommands(pc, element, pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
					pc.setQuizTime(curtime);
				}
			} catch (Exception e) {
			}
		} else {
			pc.sendPackets(new S_SystemMessage(pc.getLanguage(), "You cannot receive buffs after level 59."));
		}
	}
}

