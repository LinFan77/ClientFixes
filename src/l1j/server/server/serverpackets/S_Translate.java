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
package l1j.server.server.serverpackets;

public class S_Translate {

	private static final String[] STRINGS_USA = {
		"Nothing happens.",
		"It is a magic that you have already learned.",
		"The operator changed the %1 item information.",
		"The operator %1 (%2) Was paid.",
		"The operator has changed your level to %1.",
		"You have received a polymorph buff from the operator.",
		"This is a skill you have already learned.",
		"\\fY[%1] (end) / Account:%2",
		// GMCommands
		"Command ",
		"year ",
		"month ",
		"day ",
		// UserCommands
		"buff",
		"help",
		"refresh",
		"changepassword",
		"changepassword",
		"keyword",
		"keyworddelete",
		"phone",
		"age",
		"marble",
		"privateshop",
		"blookmark",
		"droplog",
		"pledgeparty",
		"sealedoff",
		"autorooting",
		"autorootinglog",
		"light",
		"kills",
		"hunt1",
		"hunt2",
		"hunt3",
		"[help],[light],[changepassword],[phone],[blookmark]",
		"[pledgeparty],[autorooting],[droplog],[buff]",
		"[keyword],[keyworddelete]",
		"[hunt1],[hunt2],[hunt3]",
		"You have a request from [%1] for a party",
		"Only Lord's pledge can use this command.",
		"light : [off]",
		"light : [on]",
		"-light [on, off]",
		"You can not use this command in battle",
		"A delay of 20 seconds between commands is required.",
		"off",
		"on",
		"You cannot receive buffs after level 59.",
		"Surrounding objects have been reloaded.",
		"Item drop log - OFF",
		"Item drop log - ON",
		"-droplog [on/off] to turn on or off the log of the dropped items",
		"\\fYAutorouting comments is OFF.",
		"\\fYAutorouting comments is ON.",
		"-autoroutinglog [on/off] for change autorouting comments.",
		"\\fYAutorouting is OFF.",
		"\\fYAutorouting is ON.",
		"-autorouting [on/off] for change autorouting state.",
		"A delay of 10 seconds between commands is required.",
		"You can not use this command if you are dead.",
		"You can only use it in a private store.",
		"Private stores can only be opened in the market.",
		"You have already another character in a private store.",
		"Kills display is OFF",
		"Kills display is ON",
		"-kills [on/off] to activate or disable the kills display",

	};

	// language 1
	private static final String[] STRINGS_KOREA = {
		"아무일도 일어나지 않았습니다.",
		"이미 배운 마법입니다.",
		"운영자님이 %1 아이템 정보를 변경하였습니다.",
		"운영자님이 %1 (%2) 을(를) 지급하였습니다.",
		"운영자님이 당신의 레벨을 %1 으로 변경하였습니다.",
		"운영자님에게 변신버프를 받았습니다.",
		"이미 습득한 스킬 입니다.",
		"\\fY[%1] (종료) / 계정:%2",
		// GMCommands
		"커멘드 ",
		"년 ",
		"월 ",
		"일 ",
		// UserCommands
		"버프",
		"도움말",
		"렉",
		"비번변경",
		"암호변경",
		"키워드",
		"키워드삭제",
		"고정신청",
		"나이",
		"구슬",
		"무인상점",
		"혈마크",
		"드랍멘트",
		"혈맹파티",
		"봉인해제신청",
		"오토루팅",
		"오토멘트",
		"라이트",
		"킬멘트",
		"수배1단",
		"수배2단",
		"수배3단",
		"[도움말],[라이트],[비번변경],[고정신청],[혈마크]",
		"[혈맹파티],[오토루팅],[드랍멘트],[버프]",
		"[키워드],[키워드삭제]",
		"[수배1단],[수배2단],[수배3단]",
		"당신은 [%1]에게 파티를 신청했습니다.",
		"혈맹의 군주, 수호기사만 사용할수 있습니다.",
		"라이트 : [끔]",
		"라이트 : [켬]",
		"-라이트 [끔, 켬]",
		"전투중이라 사용할 수 없습니다.",
		"20초간의 지연시간이 필요합니다.",
		"끔",
		"켬",
		"59레벨 이후는 버프를 받을수 없습니다.",
		"주변 오브젝트를 재로딩 하였습니다.",
		"아이템 획득 멘트 - OFF -",
		"아이템 획득 멘트 - ON -",
		"-드랍멘트 [켬/끔]중 입력 (아이템 획득 멘트 설정)",
		"\\fY오토루팅 멘트를 끕니다.",
		"\\fY오토루팅 멘트를 켭니다.",
		"오토멘트 [켬,끔] 라고 입력해 주세요.",
		"\\fY오토루팅을 해제합니다.",
		"\\fY오토루팅을 활성화합니다.",
		"오토루팅 [켬,끔] 라고 입력해 주세요.",
		"10초간의 지연시간이 필요합니다.",
		"죽은 상태에선 사용할 수 없습니다.",
		"개인상점 상태에서 사용이 가능합니다.",
		"개인상점은 시장에서만  열수 있습니다.",
		"경고:이미 당신의 보조 캐릭터가 무인상점 상태입니다.",

		"킬멘트 를 표시하지 않습니다.",
		"킬멘트 를 표시 합니다.",
		"-킬멘트 [켬/끔] 으로 입력해 주세요."


	};

	// language 2
	private static final String[] STRINGS_JAPAN = {
		"何も起こらなかった。",
		"すでに学んだ魔法です。",
		"運営者様が％1アイテム情報を変更しました。"
	};

	// language 3
	private static final String[] STRINGS_CHINA = {
		"什么都没有发生",
		"这是您已经学到的魔术。",
		"操作员已更改％1的项目信息。"
	};

	private Integer indexOf(String[] arr, String str){
		for (int i = 0; i < arr.length; i++)
		   if(arr[i].equals(str)) return i;
		return -1;
	 }

	public String Text(int Language, String msg) {
		//System.out.println("estamos en Tex con idioma " + Language + " y mensaje " + msg);

		int pos = indexOf(STRINGS_USA, msg);
		if (pos == -1)
		  pos = indexOf(STRINGS_KOREA, msg);
		if (pos == -1)
		  pos = indexOf(STRINGS_JAPAN, msg);
		if (pos == -1)
		  pos = indexOf(STRINGS_CHINA, msg);
		if (pos != -1)
			switch(Language)
			{
			case 0 :
				msg = STRINGS_USA[pos];
				break;
			case 1 :
				msg = STRINGS_KOREA[pos];
				break;
			case 2 :
				msg = STRINGS_JAPAN[pos];
				break;
			case 3 :
				msg = STRINGS_CHINA[pos];
				break;
			}

		//System.out.println("estaba en " + pos + " msg : " + msg);

		return msg;
	}

	public String Text(int Language, String msg,  String[] parameters) {
		int pos = indexOf(STRINGS_USA, msg);
		if (pos == -1)
		  pos = indexOf(STRINGS_KOREA, msg);
		if (pos == -1)
		  pos = indexOf(STRINGS_JAPAN, msg);
		if (pos == -1)
		  pos = indexOf(STRINGS_CHINA, msg);
		if (pos != -1)
			switch(Language)
			{
			case 0 :
				msg = STRINGS_USA[pos];
				break;
			case 1 :
				msg = STRINGS_KOREA[pos];
				break;
			case 2 :
				msg = STRINGS_JAPAN[pos];
				break;
			case 3 :
				msg = STRINGS_CHINA[pos];
				break;
			}

		for (int i = 0 ; i < parameters.length ; i++) {
			msg.replace("/%"+i, parameters[i]);
		}

		return msg;
	}

	/**
	 * 클라이언트에 데이터의 존재하지 않는 오리지날의 메세지를 표시한다. 메세지에 nameid($xxx)가 포함되어 있는 경우는
	 * overload 된 이제(벌써) 한편을 사용한다.
	 *
	 *
	 *            - 표시하는 캐릭터 라인
	 */

	 /*public __S_SystemMessage(String msg) {
		writeC(Opcodes.S_MESSAGE);
		writeC(0x09); // 시스템메시지
		// writeC(11); //파티챗
		writeS(msg);
	}

	public __S_SystemMessage(String msg, int type) {
		writeC(Opcodes.S_MESSAGE);
		writeC(0x09); // 시스템메시지
		writeS(msg);
	}

	public __S_SystemMessage(int language, String msg) {
		//System.out.println("estamos en system message con idioma " + Language + " y el msg " + msg);

		writeC(Opcodes.S_MESSAGE);
		writeC(0x09); // 시스템메시지
		//Translate translate = new Translate();
		//writeS(translate.Text(language, msg));
	}

	public __S_SystemMessage(int language, String msg,  String[] parameters) {
		writeC(Opcodes.S_MESSAGE);
		writeC(0x09); // 시스템메시지
		//Translate translate = new Translate();
		//writeS(translate.Text(language, msg, parameters));
	}

	// 보라색 동맹 챗
	public __S_SystemMessage(L1PcInstance pc, String msg) {
		writeC(Opcodes.S_SAY);
		writeC(15);
		writeD(pc.getId());
		writeS(msg);
	}*/

	/**
	 * 클라이언트에 데이터의 존재하지 않는 오리지날의 메세지를 표시한다.
	 *
	 *
	 *            - 표시하는 캐릭터 라인
	 *
	 *            - 캐릭터 라인에 nameid($xxx)가 포함되어 있는 경우 true로 한다.
	 */
	/*public __S_SystemMessage(String msg, boolean nameid) {
		writeC(Opcodes.S_SAY_CODE);
		writeC(2);
		writeD(0);
		writeS(msg);
		// NPC 채팅 패킷이면 nameid가 해석되기 (위해)때문에 이것을 이용한다
	}*/
}
