package server;

import java.io.IOException;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.AccountAlreadyLoginException;
import l1j.server.server.GameServerFullException;
import l1j.server.server.clientpackets.C_NoticeClick;
import l1j.server.server.serverpackets.S_CharPass;
import l1j.server.server.serverpackets.S_LoginResult;
import l1j.server.server.serverpackets.S_Notice;

public class Authorization {
	private static Authorization uniqueInstance = null;
	private static Logger _log = Logger.getLogger(Authorization.class.getName());

	public static Authorization getInstance() {
		if (uniqueInstance == null) {
			synchronized (Authorization.class) {
				if (uniqueInstance == null)
					uniqueInstance = new Authorization();
			}
		}

		return uniqueInstance;
	}

	private Authorization() {

	}

	public void auth(LineageClient client, String accountName, String password, String ip, String host) throws IOException {
		/** Check if the account is in English and numbers **/
		//int length = accountName.length();
		char[] accountNameChars = accountName.toCharArray();
		for (char chr : accountNameChars) {
			if (Character.UnicodeBlock.of(chr) != Character.UnicodeBlock.BASIC_LATIN) {
				if (client.getActiveChar() == null) {
					client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
				}
				return;
			}
		}
		/*char chr;
		for (int i = 0; i < length; i++) {
			chr = accountName.charAt(i);
			if (Character.UnicodeBlock.of(chr) != Character.UnicodeBlock.BASIC_LATIN) {
				if (client.getActiveChar() == null) {
					client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
				}
				return;
			}
		}*/

		int accountLength = accountName.getBytes().length;
		int passwordLength = password.getBytes().length;
		if (accountLength > 12 || accountLength < 4) {
			_log.info("Account name error [" + accountName + "] ");
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_WRONG_ACCOUNT));
			return;
		} else if (passwordLength > 12 || passwordLength < 4) {
			_log.info("Password length wrong [" + passwordLength + "] ");
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_WRONG_PASSWORD));
			return;
		}

		Account account = Account.load(accountName);
		if (account == null) {
			if (Config.AUTO_CREATE_ACCOUNTS) {
				if (Account.checkLoginIP(ip)) {
					S_Notice sendNotice2 = new S_Notice("We only allow 2 accounts per IP.");
					client.sendPacket(sendNotice2);
					sendNotice2.clear();
					sendNotice2 = null;
					try {
						Thread.sleep(1500);
						client.kick();
						client.close();
					} catch (Exception e1) {
					}
				} else {
					account = Account.create(accountName, password, ip, host);
					account = Account.load(accountName);
				}
			} else {
				_log.warning("Account missing for user " + accountName);
			}
		}

		if (account == null || !account.validatePassword(accountName, password)) {
			int lfc = client.getLoginFailedCount();
			client.setLoginFailedCount(lfc + 1);
			if (lfc > 2) {
				disconnect(client);
			} else {
				client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
			}

			return;
		}

		if (account.isBanned()) {
			_log.info("Banned account access denied. Account [" + accountName + "] Host [" + host + "]");
			client.sendPacket(new S_Notice("This account was banned from the server. Please contact a GM."), true);
			disconnect(client);
			return;
		}

		try {
			LoginController.getInstance().login(client, account);
			Account.updateLastActive(account, ip); // Update last login date
			client.setAccount(account);
			client.sendPacket(new S_LoginResult());
			client.sendPacket(new S_CharPass(), true);
			sendNotice(client);
		} catch (GameServerFullException e) {
			client.sendPacket(new S_Notice("Connection to the server is denied due to too many players online.\n Please try again later"), true);
			disconnect(client);
			_log.info("The number of connections has been exceeded. [" + client.getHostname() + "] Connection attempt was terminated.");
			return;
		} catch (Exception e) {
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_ACCOUNT_IN_USE), true);
			disconnect(client);
			return;
		}
	}

	public void sendNotice(LineageClient client) {
		String accountName = client.getAccountName();
		// Check if there are notices to read
		if (S_Notice.NoticeCount(accountName) > 0) {
			client.sendPacket(new S_Notice(accountName, client), true);
		} else {
			new C_NoticeClick(client);
		}
	}

	private void disconnect(LineageClient client) throws IOException {
		client.kick();
		client.close();
	}
}

