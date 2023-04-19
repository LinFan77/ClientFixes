package l1j.server.server; // 변경  by feel.  160913

public class Opcodes {

	public Opcodes() {
	}
	public static final int C_PLATE = 0x01;	// Click on bulletin board
	public static final int C_LOGIN_TEST = 0x04; // Found[Bonsub] (Currently unused by feel)
	public static final int C_CHANGE_ACCOUNTINFO = 0x05; // CALL button .monitoring (currently not used by feel)
	public static final int C_BLINK = 0x08;	// Found [Bonsub]
	public static final int C_BUYABLE_SPELL = 0x0B;	// Skill purchase list
	public static final int C_HYPERTEXT_INPUT_RESULT = 0x0C; // Quantity Number of items produced
	public static final int C_ADD_BUDDY = 0x0D;			// Add to friends
	public static final int C_WITHDRAW = 0x0E; 			// Withdraw funds
	public static final int C_TELL = 0x11; 				//  found live?
	public static final int C_ACCEPT_XCHG = 0x12; 		// confirm exchange
	public static final int C_READ_NOTICE = 0x16; 		// packets after login
	public static final int C_HACTION = 0x17; 			// Npc dialog action part
	public static final int C_EXCHANGEABLE_SPELL = 0x18;// Horror magic list
	public static final int C_USE_SPELL = 0x19;			// skill use part
	public static final int C_ATTACK = 0x1A;			// general attack part
	public static final int C_UPLOAD_EMBLEM = 0x1C;		// Request text data from server
	public static final int C_MERCENARYSELECT = 0x20;	//
	public static final int C_USE_ITEM = 0x21; 			// Item use part
	public static final int C_LEAVE_PLEDGE = 0x22; 		// Clan withdrawal
	public static final int C_RANK_CONTROL = 0x24; 		// [Classes]
	public static final int C_BOOKMARK = 0x28; 			// [Remember OO]
	public static final int C_MOVE = 0x29;				// move request part
	public static final int C_SAY = 0x2A;
	public static final int C_WHO_PLEDGE = 0x2B;
	public static final int C_MERCENARYARRANGE = 0x2D;	//
	public static final int C_MONITOR_CONTROL = 0x2E;	//
	public static final int C_SLAVE_CONTROL = 0x30;		// 펫 공격 목표 지정
	public static final int C_SAVEIO = 0x31; 			// Character inventory slot information
	public static final int C_WANTED = 0x32; //
	public static final int C_EXCLUDE = 0x33; 			// [/차단 /block]
	public static final int C_DROP = 0x34;				// drop item
	public static final int C_ALIVE = 0x35;				// Comes once every minute/AFK check?
	public static final int C_BUILDER_CONTROL = 0x36;	//
	public static final int C_SELECT_TIME = 0x3A;
	public static final int C_INCLUDE = 0x3B;
	public static final int C_PERSONAL_SHOP = 0x3C;
	public static final int C_DELETE_CHARACTER = 0x3D;
	public static final int C_SHUTDOWN = 0x3F;			// Clan Pledge Notice, Memo
	public static final int C_WHO = 0x42;
	public static final int C_VERSION = 0x46;			//client server version
	public static final int C_WHO_PARTY = 0x47;
	public static final int C_LOGIN_RESULT = 0x4C;
	public static final int C_RETURN_SUMMON = 0x4D;		// 텔레포트 사용
	public static final int C_QUERY_PERSONAL_SHOP = 0x4E;	// private store buy, sell
	public static final int C_EXTENDED_PROTOBUF = 0x51;	// Comprehensive Packet
	public static final int C_BAN = 0x52;
	public static final int C_CANCEL_XCHG = 0x53;
	public static final int C_NPC_ITEM_CONTROL = 0x54;	// Using Pet Inventory Items
	public static final int C_LEAVE_PARTY = 0x55;
	public static final int C_GOTO_MAP = 0x59;
	public static final int C_ADD_XCHG = 0x5B;			// Add item to exchange window
	public static final int C_WAREHOUSE_CONTROL = 0x61;	// warehouse password
	public static final int C_MARRIAGE = 0x62;
	public static final int C_CHANNEL = 0x63;			// Report bad user (report)
	public static final int C_CONTROL_WEATHER = 0x64;
	public static final int C_GET = 0x65;				//pick up
	public static final int C_CREATE_CUSTOM_CHARACTER = 0x67;	//character creation
	public static final int C_TELEPORT = 0x69;
	public static final int C_KICK = 0x6A;
	public static final int C_ADDR = 0x6B;
	public static final int C_DEAD_RESTART = 0x6D;		// When I die in the middle of the game and press reset
	public static final int C_QUERY_CASTLE_SECURITY = 0x6E;	// Public security management in the castle
	public static final int C_GIVE = 0x72;				// Force Item Giving
	public static final int C_CHAT = 0x73;				// Macro chatting by feel
	public static final int C_MERCENARYNAME = 0x74;
	public static final int C_FIX = 0x78;				// Weapon repair, pet withdrawal
	public static final int C_QUERY_BUDDY = 0x7A;		// friend list
	public static final int C_FAR_ATTACK = 0x7B;		// bow attack part
	public static final int C_CHANGE_PASSWORD = 0x7C;	//  (not used_by feel )
	public static final int C_TELEPORT_USER = 0x7D;
	public static final int C_CHANGE_DIRECTION = 0x80;
	public static final int C_NEW_ACCOUNT = 0x82;		//  (Not used_by feel )
	public static final int C_SELECTABLE_TIME = 0x85;
	public static final int C_WISH = 0x86;
	public static final int C_EXTENDED = 0x89;			// Click on the Aden Shop icon, additional items, clan window
	public static final int C_READ_NEWS = 0x8B;			// When you press check notice
	public static final int C_OPEN = 0x8E;				// door click
	public static final int C_DUEL = 0x90;				// [duel]
	public static final int C_ASK_XCHG = 0x91;			// [exchange]
	public static final int C_REGISTER_QUIZ = 0x93;
	public static final int C_FIXABLE_ITEM = 0x96;		// weapon repair list
	public static final int C_CHECK_PK = 0x97;			// [checkpk]
	public static final int C_SERVER_SELECT = 0x99;
	public static final int C_EXTENDED_HYBRID = 0x9A;
	public static final int C_TITLE = 0x9B;				// call command
	public static final int C_ARCHERARRANGE = 0x9C;
	public static final int C_DELETE_BOOKMARK = 0x9D;	// [기억 후 기억목록클릭 delete]
	public static final int C_QUIT = 0xA0;	// 0x51 종료
	public static final int C_BOARD_READ = 0xA2;	// 게시판 읽기
	public static final int C_MERCENARYEMPLOY = 0xA4;	// 용병구매
	public static final int C_EMBLEM = 0xA7;	// 가시범위의 혈맹 마크 요청[폴더내 emblem삭제]
	public static final int C_ALT_ATTACK = 0xA8;
	public static final int C_ENTER_SHIP = 0xAB;
	public static final int C_INVITE_PARTY = 0xAC;
	public static final int C_REMOVE_BUDDY = 0xAD;	// 친구삭제
	public static final int C_EXCHANGE_SPELL = 0xAE;	// 호런 마법목록에서 OK누르기
	public static final int C_BANISH_PARTY = 0xAF;	// 파티 추방
	public static final int C_LOGOUT = 0xB0;	// 다시 로긴창으로 넘어갈때
	public static final int C_SHIFT_SERVER = 0xB3;	//  (사용안함_by feel )
	public static final int C_BOOK = 0xB7;
	public static final int C_INVITE_PARTY_TARGET = 0xB9;	// 파티 초대
	public static final int C_BOARD_DELETE = 0xBE;	// 게시판 게시글 삭제
	public static final int C_EXIT_GHOST = 0xC0;	// 무한대전 관람모드 탈출
	public static final int C_MATCH_MAKING = 0xC5;	//
	public static final int C_CHECK_INVENTORY = 0xC6;	// 펫 인벤토리[펫 메뉴]
	public static final int C_ENTER_PORTAL = 0xC7;	// (ENTER_PORTAL)
	public static final int C_THROW = 0xC8;	// 낚시 종료
	public static final int C_SILENCE = 0xC9;
	public static final int C_GOTO_PORTAL = 0xCB;	// 오른쪽 버튼으로 포탈 진입 확실치 않음
	public static final int C_WAR = 0xCE;	// 전쟁
	public static final int C_BOARD_WRITE = 0xCF;	// 게시판 쓰기
	public static final int C_VOICE_CHAT = 0xD0;	// 스텟 초기화.
	public static final int C_JOIN_PLEDGE = 0xD1;	// [가입]
	public static final int C_TAX = 0xD4;	// 세금 조정
	public static final int C_SMS = 0xD6;	// 문자
	public static final int C_BUY_SELL = 0xD7;	// 상점 결과 처리
	public static final int C_ONOFF = 0xD8;	// [환경설정->전챗켬,끔].
	public static final int C_DEPOSIT = 0xD9;	// 성 공금 입금
	public static final int C_REQUEST_ROLL = 0xDA;
	public static final int C_START_CASTING = 0xDC;
	public static final int C_BOARD_LIST = 0xDD;	// 게시판 next
	public static final int C_MAIL = 0xDF;	// 편지 읽기
	public static final int C_PLEDGE_WATCH = 0xE2;	// 문장 주시 혈맹 목록
	public static final int C_RESTART = 0xE4;	// 겜중에 리스창으로 빠짐. [ C_CHARACTERCONFIG 다음에 날라옴 ]
	public static final int C_SUMMON = 0xE5;	// CALL버튼 .감시
	public static final int C_CHAT_PARTY_CONTROL = 0xE7;	// 채팅파티채팅초대
	public static final int C_CLIENT_READY = 0xE8;
	public static final int C_LOGIN = 0xE9;	// v 계정정보가 담긴 패킷
	public static final int C_ENTER_WORLD = 0xEC;	// 리스창에서 케릭 선택
	public static final int C_ATTACK_CONTINUE = 0xED;	// 자동칼질
	public static final int C_CREATE_PLEDGE = 0xF4;	// 혈맹 창설
	public static final int C_BAN_MEMBER = 0xF5;	// 혈맹 추방 명령어
	public static final int C_ACTION = 0xF6;	// <알트+1 ~ 5 까지 액션 >
	public static final int C_CHANGE_CASTLE_SECURITY = 0xF7;	// 성내 치안 관리
	public static final int C_ANSWER = 0xF8;	// [ Y , N ] 선택 부분
	public static final int C_DESTROY_ITEM = 0xFA;	// 휴지통에 아이템 삭제
	public static final int C_SAVE = 0xFB;	// 무기수리 완료 - 예측
	public static final int C_DIALOG = 0xFC;	// Npc와 대화부분
	public static final int C_BUY_SPELL = 0xFD;	// 스킬 구입 OK


	public static final int S_CHANGE_PASSWORD_CHECK = 0x00;
	public static final int S_WEATHER = 0x01;	// 날씨 조작하기
	public static final int S_DRUNKEN = 0x02;	// 술
	public static final int S_POISON = 0x04;	// 독과 굳은 상태 : 표현 비취나 큐어포이즌 써보면 됨
	public static final int S_XCHG_RESULT = 0x05;	// 거래 취소, 완료
	public static final int S_NOT_ENOUGH_FOR_SPELL = 0x0C;	// 호런 재료 부족
	public static final int S_CHANGE_ATTR = 0x0D;	// 위치값을 이동가능&불가능 조작 부분
	public static final int S_TELL = 0x0F;	// 귓속말
	public static final int S_REQUEST_SUMMON = 0x11;	// 텔레포트[손바닥]
	public static final int S_BOARD_READ = 0x12;	// 게시판 읽기
	public static final int S_ROLL_RESULT = 0x14;
	public static final int S_REMOVE_INVENTORY = 0x15;	// 인벤토리 아이템 삭제
	public static final int S_CHANGE_DIRECTION = 0x16;	// 방향 전환 부분 [움직이는 엔피씨에 말걸어서 체크]
	public static final int S_ADD_SPELL = 0x19;	// 스킬 추가[버프패킷박스 다음]
	public static final int S_MERCENARYNAME = 0x1A;
	public static final int S_CHANGE_ALIGNMENT = 0x1D;	// 라우풀
	public static final int S_HIT_POINT = 0x20;	// HP 업데이트
	public static final int S_NOTICE = 0x21;	// 로그인후 패킷
	public static final int S_CLONE = 0x22;	// npc 변신(도펠갱어 등등)
	public static final int S_DELETE_CHARACTER_CHECK = 0x23;	// 케릭 삭제
	public static final int S_EFFECT = 0x25;	// 이팩트 부분 (헤이스트등)
	public static final int S_PERSONAL_SHOP_LIST = 0x26;	// 개인상점 물품 열람
	public static final int S_BLIND = 0x2A;	// 눈멀기 효과
	public static final int S_CREATE_CHARACTER_CHECK = 0x2D;	// 캐릭터 생성시 처리부분
	public static final int S_CHANGE_DESC = 0x2E;	// 오브젝트 네임변경시
	public static final int S_BUYABLE_SPELL_LIST = 0x30;	// 스킬 구입 창
	public static final int S_CHANGE_ITEM_USE = 0x31;
	public static final int S_ATTACK_ALL = 0x32;
	public static final int S_EXP = 0x34;	// experience update
	public static final int S_MOVE_OBJECT = 0x36;	// object movement 
	public static final int S_TAX = 0x3A;	// 세율 조정 tax adjustment
	public static final int S_VOICE_CHAT = 0x3B;	// v 스텟 초기화 길이 V status initialization length
	public static final int S_COMMAND_TARGET = 0x3D;
	public static final int S_ADD_INVENTORY_BATCH = 0x41;	// 인벤토리의 아이템리스트 Item list inventory 
	public static final int S_AGIT_MAP = 0x42;
	public static final int S_SERVER_LIST = 0x44;
	public static final int S_AC = 0x45;	// AC 및 속성방어 갱신 [블레스트 아머 사용시 나옴] AC and attribute defense update [appears when using bless armour]
	public static final int S_NEW_CHAR_INFO = 0x47;	// 케릭 새로 만든거 보내기 send newly created character information
	public static final int S_ACTION = 0x48;	// 액션 부분(맞는모습등) action part (appearance when hit)
	public static final int S_CHARACTER_INFO = 0x4B;	// v 케릭터리스트의 케릭정보 Character information in character list
	public static final int S_MAGE_STRENGTH = 0x4D;	// 힘업 strength up
	public static final int S_WITHDRAW = 0x4E;	// 공금 출금 public fund withdrawl
	public static final int S_PING = 0x4F;
	public static final int S_MASTER = 0x51;
	public static final int S_CRIMINAL = 0x52;	// 보라돌이 PK maybe? 
	public static final int S_HYPERTEXT = 0x53;	// Npc클릭 Html열람 npc click to read html
	public static final int S_CHANGE_LEVEL = 0x55;
	public static final int S_ASK = 0x57;	// [ Y , N ] 메세지 [ Y , N ] message
	public static final int S_CASTLE_OWNER = 0x59;	// 성소유목록 세팅  set castle ownership list
	public static final int S_RESTART = 0x5D;
	public static final int S_BLINK = 0x5E;
	public static final int S_SELECTABLE_TIME_LIST = 0x5F;	// 공성시간 지정 specify siege time 
	public static final int S_VERSION_CHECK = 0x61;	// 서버버전 server version 
	public static final int S_WAR = 0x63;	// 전쟁 war 
	public static final int S_AGIT_LIST = 0x64;	// 아지트 리스트 agit list
	public static final int S_CHANGE_LIGHT = 0x65;	// 밝기 brightness 
	public static final int S_SHOW_MAP = 0x67;	//
	public static final int S_RESURRECT = 0x6D;	// 부활 처리 부분 ressurection processing part
	public static final int S_PORTAL = 0x6E;	//
	public static final int S_ATTACK_MANY = 0x70;	// 파톰 어퀘등의 스킬 skills like 
	public static final int S_REMOVE_SPELL = 0x78;	// 스킬 삭제 (정령력 제거) 힘투벗기 skill deletion (removal of elemental power) fight to remove clothing
	public static final int S_EVENT = 0x79;	// v 통합 패킷 관리 담당 responsible for managing integrated packets
	public static final int S_ADD_BOOKMARK = 0x7B;	// 기억 리스트 memory list
	public static final int S_ABILITY_SCORES = 0x7C;	// 스테이터스 갱신(디크리즈,민투) update status (decrease, minimize) 
	public static final int S_IDENTIFY_CODE = 0x7D;	// 확인주문서 confirmation document
	public static final int S_HIT_RATIO = 0x7E;	// 미니 HP표현 부분 mini HP display part
	public static final int S_CHANGE_ITEM_DESC_EX = 0x80;	// 인벤 아이템 갱신
	public static final int S_NUM_CHARACTER = 0x82;	// number of characters in the account
	public static final int S_CHANGE_ITEM_DESC = 0x86;	// 아이템 착용 (E표시) inventory 
	public static final int S_INVISIBLE = 0x87;	// 투명
	public static final int S_SAY_CODE = 0x88;	// 샤우팅 글
	public static final int S_MESSAGE = 0x8A;	// 시스템 메세지 (장사 채팅).
	public static final int S_PLEDGE_WATCH = 0x8B;	// 혈맹 문장 주시
	public static final int S_SELL_LIST = 0x8D;	// 상점에 판매 부분
	public static final int S_FIXABLE_ITEM_LIST = 0x8E;	// 무기수리 리스트
	public static final int S_MAGE_SHIELD = 0x90;	// 쉴드
	public static final int S_READ_MAIL = 0x95;
	public static final int S_BOARD_LIST = 0x97;	// 게시판 클릭
	public static final int S_SLAVE_CONTROL = 0x99;	// 펫 공격 목표지정
	public static final int S_EMBLEM = 0x9A;				// 클라에 혈문장 요청
	public static final int S_ADD_XCHG = 0x9B;				// Trade window item addition part
	public static final int S_SAY = 0x9C;					// general chat
	public static final int S_STATUS = 0xA0;				// Update character information
	public static final int S_MAGE_DEXTERITY = 0xA1;		// Dex Up
	public static final int S_TITLE = 0xA2;					// name change
	public static final int S_WARNING_CODE = 0xA3;
	public static final int S_CHANGE_ITEM_TYPE = 0xA4;
	public static final int S_BOOK_LIST = 0xA5;
	public static final int S_MERCENARYARRANGE = 0xA9;		// Selected mercenary cycle
	public static final int S_ATTACK = 0xAB;				// attack expression part
	public static final int S_CLIENT_READY = 0xB2;
	public static final int S_PUT_OBJECT = 0xB3;			// drawing objects
	public static final int S_EXTENDED_PROTOBUF = 0xB4;		// Comprehensive Packet
	public static final int S_EXTENDED_HYBRID = 0xB5;
	public static final int S_RETRIEVE_LIST = 0xB6;			// warehouse list
	public static final int S_HYPERTEXT_INPUT = 0xB8;		// Quantity Number of Items [Inn]
	public static final int S_WIELD = 0xBB;					// weapon attachment and detachment
	public static final int S_POLYMORPH = 0xBC;				// transform
	public static final int S_MANA_POINT = 0xBD;			// MP update
	public static final int S_WORLD = 0xBE;
	public static final int S_SOUND_EFFECT = 0xC2;			// Sound effect part [find by pet whistle]
	public static final int S_MAIL_INFO = 0xC3;				// read the letter.
	public static final int S_EXCHANGEABLE_SPELL_LIST = 0xC6;	// Horror magic learning window
	public static final int S_ENTER_WORLD_CHECK = 0xC8;		// connection
	public static final int S_XCHG_START = 0xC9;			// trading window part
	public static final int S_TIME = 0xCA;					// game time
	public static final int S_MATCH_MAKING = 0xCC;
	public static final int S_MERCENARYSELECT = 0xCE;		// Deploy hired mercenaries
	public static final int S_LOGIN_CHECK = 0xCF;			// Packets that come when moving the portal (when entering Horon Cave) Not sure
	public static final int S_PLEDGE = 0xD0;				// Clan update, join any clan
	public static final int S_WANTED_LOGIN = 0xD1;			// PK count message
	public static final int S_KICK = 0xD2;					// Force quit the character
	public static final int S_BUY_LIST = 0xD4;				// Shop purchase part [BUY]
	public static final int S_EFFECT_LOC = 0xD5;			// Trap (Coordinate position effect) can be picked up with the Poe Slayer
	public static final int S_ARCHERARRANGE = 0xD7;
	public static final int S_DEPOSIT = 0xD8;				// public money deposit
	public static final int S_EXTENDED = 0xDB;				// Aden Shop Survival Cry, etc.
	public static final int S_CHANGE_ACCOUNTINFO_CHECK = 0xDD;
	public static final int S_CHANGE_COUNT = 0xDE;
	public static final int S_CHANGE_ABILITY = 0xE0;		// Use of Ivan and Soban infrastructure
	public static final int S_DECREE = 0xE1;
	public static final int S_MAGIC_STATUS = 0xE5;			// change sp and mr
	public static final int S_CHANGE_ITEM_BLESS = 0xE7;		// Buy a Sealing Scroll and a Sealing Scroll and apply it to the Ivory Tower weapon.
	public static final int S_NEWS = 0xE9;					// Notice
	public static final int S_KEY = 0xEB;					// Direction change part [check by talking to the moving NPC]
	public static final int S_EMOTION = 0xED;				// courage
	public static final int S_MERCENARYEMPLOY = 0xF0;		// Castle Mercenary Hire 0612 - Unchanged
	public static final int S_ADD_INVENTORY = 0xF5;			// Create an item [Drop the item and eat it]
	public static final int S_PARALYSE = 0xF9;				// Action Restriction (Casparel State) Go to Desert Scorpion and get Cursed
	public static final int S_REMOVE_OBJECT = 0xFA;			// Delete object (toggle etc)
	public static final int S_NEW_ACCOUNT_CHECK = 0xFB;
	public static final int S_SPEED = 0xFC;					// haste
	public static final int S_MESSAGE_CODE = 0xFE;			// Server message [Check for duplicate armor]
	public static final int S_BREATH = 0xFF;				// eva icon

	public static final byte[] S_KEYBYTES = {
			(byte) (S_KEY & 0xff),

			(byte) 0x4c, (byte) 0xbe, (byte) 0x75, (byte) 0x5d,
			(byte) 0x23, (byte) 0x75, (byte) 0x5a, (byte) 0x75,
	};

	public static long getSeed(){
		long seed = S_KEYBYTES[1] & 0xff;
		seed |= ((S_KEYBYTES[2] << 8) & 0xff00);
		seed |= ((S_KEYBYTES[3] << 16) & 0xff0000);
		seed |= ((S_KEYBYTES[4] << 24) & 0xff000000);

		return seed;
	}

	public static final byte[] VERSIONBYTES_HEAD = {
			(byte) 0x38, //set_status_start_time
			//bb 9b e8 ef 05
			(byte) 0xbb, (byte) 0x9b, (byte) 0xe8, (byte) 0xef, (byte) 0x05,
			(byte) 0x40, // set_english_only_config
			(byte) 0x00,
			(byte) 0x48, // set_country_code
			(byte) 0x00,
			(byte) 0x50, // set_client_setting_switch
			//8b fb ff a7 03
			(byte) 0x8b, (byte) 0xfb, (byte) 0xff, (byte) 0xa7, (byte) 0x03,
			(byte) 0x58, // set_game_real_time
	};

	public static final byte[] VERSIONBYTES_TAIL = {
			(byte) 0x60, // set_global_cache_version
			//fc 97 87 48
			(byte) 0xfc, (byte) 0x97, (byte) 0x87, (byte) 0x48,
			(byte) 0x68, // set_tam_server_version
			//ef f8 fc 8c 07
			(byte) 0xef, (byte) 0xf8, (byte) 0xfc, (byte) 0x8c, (byte) 0x07,
			(byte) 0x70, // set_arca_server_version
			//f0 f8 fc 8c 07
			(byte) 0xf0, (byte) 0xf8, (byte) 0xfc, (byte) 0x8c, (byte) 0x07,
			(byte) 0x78, // set_hibreed_inter_server_version
			//b9 aa de 8f 07
			(byte) 0xb9, (byte) 0xaa, (byte) 0xde, (byte) 0x8f, (byte) 0x07,
			(byte) 0x80, (byte) 0x01, // set_arenaco_server_version
			//89 b1	e1 8f 07
			(byte) 0x89, (byte) 0xb1, (byte) 0xe1, (byte) 0x8f, (byte) 0x07,
			(byte) 0x88, (byte) 0x01, // set_server_type
			(byte) 0x00,
	};
}

