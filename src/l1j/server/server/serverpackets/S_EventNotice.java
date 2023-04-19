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

import java.util.Iterator;
import java.util.Map;

import l1j.server.GameSystem.Dungeon.DungeonInfo;
import l1j.server.GameSystem.Dungeon.DungeonSystem;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable.EventNotice;
import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_EventNotice extends ServerBasePacket {
	private static final String S_EventNotice = "[S] S_EventNotice";

	private byte[] _byte = null;

	public S_EventNotice() {
  		writeC(Opcodes.S_EXTENDED_PROTOBUF);
  		writeC(0x8d);
  		writeC(0x00);

  		writeC(0x08);
  		writeC(0x01);

  		writeC(0x10);
  		writeC(0x01);

  		for (EventNotice Notice : RotationNoticeTable.getInstance().getRotationList()){
  			String Info = null;
  			long Length = 0, MsLength = 0, Time = 0, TimeEnd = 0;
  			switch(Notice.InfoType){
  				case 2:
	  				Length = 10;
	  				if(Notice.InfoEndTime == null ||
	  				  System.currentTimeMillis() >= Notice.InfoEndTime.getTime()) continue;
	  				if(Notice.InfoLink != null)
	  					Length += Notice.InfoLink.getBytes().length;
	  				for (String Massage : Notice.Massage)
	  					MsLength += Massage.getBytes().length + 5;
	  				for (int MassageAction : Notice.MassageAction)
	  					MsLength += size7B(MassageAction);

	  				Info = Notice.Info;
	  				if(Notice.InfoTime != null){
	  					Time = Notice.InfoTime.getTime() / 1000;
	  				}else Time = System.currentTimeMillis() / 1000;
	  				if(Notice.InfoEndTime != null){
	  					TimeEnd = Notice.InfoEndTime.getTime() / 1000;
	  				}else TimeEnd = System.currentTimeMillis() / 1000 + (60 * 60 * 24) ;
	  				Length += MsLength + Info.getBytes().length  + size7B((int)Time) + size7B((int)TimeEnd);

	  				/** full size*/
	  				writeC(0x1a);
	  				writeC((int)Length);

	  				/** Action number Outputs different images for each action
	  				 * When an action number is entered, when the c value is automatically
	  				 * checked, the action value is output after 08*/
	  				writeC(0x08);
	  				writeBit(Notice.InfoAction);

	  				/** The link that will be clicked on the question mark is available only for Powerbooks */
	  				writeC(0x1a);
	  				writeC(Notice.InfoLink != null ? Notice.InfoLink.getBytes().length : 0);
	  				if(Notice.InfoLink != null) writeByte(Notice.InfoLink.getBytes());

	  				/**message to be printed */
	  				writeC(0x22);
	  				writeC(Info.getBytes().length);
	  				writeByte(Info.getBytes());

	  				/** appearance time */
	  				writeC(0x28);
	  				write7B(Time);

	  				/** delete time*/
	  				writeC(0x30);
	  				write7B(TimeEnd);

	  				/** size calculation */
	  				writeC(0x42);
	  				writeBit(MsLength);
	  				for (int i = 0; i < Notice.Massage.length; i++){
	  					writeC(0x0a);
	  					writeBit(3 + Notice.Massage[i].getBytes().length + size7B(Notice.MassageAction[i]));

	  					writeC(0x08);
	  					writeBit(Notice.MassageAction[i]);

	  					writeC(0x12);
	  					writeC(Notice.Massage[i].getBytes().length);
	  					writeByte(Notice.Massage[i].getBytes());
	  				}
	  				break;
  			}
  		}
  		writeH(0);
  	}

  	public S_EventNotice(L1PcInstance pc, boolean Event, boolean Delete) {
  		writeC(Opcodes.S_EXTENDED_PROTOBUF);
  		writeC(0x8d);
  		writeC(0x00);

  		writeC(0x08);
  		writeC(0x01);

  		writeC(0x10);
  		writeC(0x01);

  		for (EventNotice Notice : RotationNoticeTable.getInstance().getRotationList()){
  			String Info = null , TeleportMs = "4654";
  			long Length = 0, Time = 0, TimeEnd = 0;
  			switch(Notice.InfoType){
				case 0:
  					Length = 13 + size7B(Notice.Teleport[3]);
  					/**Conditional Statement: If the event is true and the action is between 19 and 20, return is not processed. */
  					if(Event && (Notice.InfoAction >= 19 && Notice.InfoAction <= 20)){ continue;
  					}else if(!pc.getInventory().checkItem(60384) && pc.getQuest().get_step(L1Quest.QUEST_55_Roon) == 0){
						Info = "Elixir Runes(Level 55)";
					}else if(!pc.getInventory().checkItem(60391) && pc.getQuest().get_step(L1Quest.QUEST_70_Roon) == 0 && pc.getLevel() >= 70){
						Info = "Elixir Runes(Level 70)";
					}else if(!pc.getInventory().checkItem(60398) && pc.getQuest().get_step(L1Quest.QUEST_80_Roon) == 0 && pc.getLevel() >= 80){
						Info = "Elixir Runes(Level 80)";
					}else if(!pc.getInventory().checkItem(60405) && pc.getQuest().get_step(L1Quest.QUEST_85_Roon) == 0 && pc.getLevel() >= 85){
						Info = "Elixir Runes(Level 85)";
					}else if(!pc.getInventory().checkItem(60412) && pc.getQuest().get_step(L1Quest.QUEST_90_Roon) == 0 && pc.getLevel() >= 90){
						Info = "Elixir Runes(Level 90)";
					}else continue;
 					if(Notice.InfoLink != null)
  						Length += Notice.InfoLink.getBytes().length;
		  			if(Notice.InfoTime != null){
		  				Time = Notice.InfoTime.getTime() / 1000;
		  			}else Time = System.currentTimeMillis() / 1000;
		  			if(Notice.InfoEndTime != null){
		  				TimeEnd = Notice.InfoEndTime.getTime() / 1000;
		  			}else TimeEnd = System.currentTimeMillis() / 1000 + (60 * 60 * 24) ;

		  			Length += Info.getBytes().length + TeleportMs.getBytes().length;
		  			Length += size7B((int)Time) + size7B((int)TimeEnd);

		  			/** full size*/
		  			writeC(0x1a);
		  			writeC((int)Length);

		  			/**Action number Outputs different images for each action
		  			 * When an action number is entered, when the c value is automatically
		  			 * checked, the action value is output after 08*/
		  			writeC(0x08);
		  			writeBit(Notice.InfoAction);

		  			/** The link that will be clicked on the question mark is available only for Powerbooks */
		  			writeC(0x1a);
		  			writeC(Notice.InfoLink != null ? Notice.InfoLink.getBytes().length : 0);
		  			if(Notice.InfoLink != null){
		  				writeByte(Notice.InfoLink.getBytes());
		  			}else writeC(0x00);

		  			/** message to be printed */
		  			writeC(0x22);
		  			writeC(Info.getBytes().length);
		  			writeByte(Info.getBytes());

		  			/** appearance time */
		  			writeC(0x28);
		  			write7B(Time);

		  			/** delete time */
		  			writeC(0x30);
		  			write7B(!Delete ? TimeEnd : System.currentTimeMillis() / 1000);

		  			/** Tell ability when output during event time */
		  			writeC(0x3a);
		  			writeC(TeleportMs.getBytes().length + size7B(Notice.Teleport[3]) + 3);

		  			/**When sending a message, the message number */
		  			writeC(0x0a);
		  			writeC(TeleportMs.getBytes().length);
		  			writeByte(TeleportMs.getBytes());

		  			/** how much would you ask for */
		  			writeC(0x10);
		  			writeBit(Notice.Teleport[3]);
  					break;

  				case 1:
  					Length = 13 + size7B(Notice.Teleport[3]);
  					if(Event && (Notice.InfoAction >= 19 && Notice.InfoAction <= 20)) continue;
  					if(Notice.InfoLink != null) Length += Notice.InfoLink.getBytes().length;

  					Info = Notice.Info;
		  			if(Notice.InfoTime != null){
		  				Time = Notice.InfoTime.getTime() / 1000;
		  			}else Time = System.currentTimeMillis() / 1000;
		  			if(Notice.InfoEndTime != null){
		  				TimeEnd = Notice.InfoEndTime.getTime() / 1000;
		  			}else TimeEnd = System.currentTimeMillis() / 1000 + (60 * 60 * 24) ;
		  			Length += Info.getBytes().length + TeleportMs.getBytes().length;
		  			Length += size7B((int)Time) + size7B((int)TimeEnd);

		  			/** full size*/
		  			writeC(0x1a);
		  			writeC((int)Length);

		  			/** Action number Outputs different images for each action
		  			 * When an action number is entered, when the c value is automatically
		  			 * checked, the action value is output after 08*/
		  			writeC(0x08);
		  			writeBit(Notice.InfoAction);

		  			/** The link that will be clicked on the question mark is available only for Powerbooks */
		  			writeC(0x1a);
		  			writeC(Notice.InfoLink != null ? Notice.InfoLink.getBytes().length : 0);
		  			if(Notice.InfoLink != null){
		  				writeByte(Notice.InfoLink.getBytes());
		  			}else writeC(0x00);

		  			/** message to be printed */
		  			writeC(0x22);
		  			writeC(Info.getBytes().length);
		  			writeByte(Info.getBytes());

		  			/** appearance time */
		  			writeC(0x28);
		  			write7B(Time);

		  			/** delete time */
		  			writeC(0x30);
		  			write7B(!Delete ? TimeEnd : System.currentTimeMillis() / 1000);

		  			/** Tell ability when output during event time */
		  			writeC(0x3a);
		  			writeC(TeleportMs.getBytes().length + size7B(Notice.Teleport[3]) + 3);

		  			/**When sending a message, the message number */
		  			writeC(0x0a);
		  			writeC(TeleportMs.getBytes().length);
		  			writeByte(TeleportMs.getBytes());

		  			/** how much would you ask for */
		  			writeC(0x10);
		  			writeBit(Notice.Teleport[3]);
  					break;
  			}
  		}
  		writeH(0);
  	}
	/**
	public S_EventNotice(int type,int time,int duration,boolean mod) {

		String name;
		switch(type){
			case 1:
				name = "에르자베";
				break;
			case 2:
				name = "샌드 웜";
				break;
			case 3:
				name = "무한 대전(저녁 9시)";
				break;
			case 4:
				name = "55레벨 룬";
				break;
			case 5:
				name = "귀걸이 개방";
				break;
			case 6:
				name = "휘장 개방";
				break;
			case 7:
				name = "70레벨 룬";
				break;
			case 8:
				name = "76반지 개방";
				break;
			case 9:
				name = "81반지 개방";
				break;
			case 10:
				name = "83견갑 개방";
				break;
			case 11:
				name = "잊혀진 섬 개방";
				break;
			case 12:
				name = "80레벨 룬";
				break;
			case 13:
				name = "85레벨 룬";
				break;
			case 14:
				name = "90레벨 룬";
				break;
			case 15:
				name = "잊혀진 섬 개방";
				break;
			default:
				name = "Joyserver";
				break;
		}
		int length = name.getBytes().length;

		int realTime,realTime2;
		if(mod){
			realTime =RealTimeClock.getInstance().getRealTime().getSeconds()+time;  //몇시간 남음 표기시간

			if(realTime <0 )
				realTime = -realTime;

			realTime2 =RealTimeClock.getInstance().getRealTime().getSeconds()+duration; //사라지는 시간

			if(realTime2 < 0 )
				realTime2 = -realTime2;

		}else{

			realTime =RealTimeClock.getInstance().getRealTime().getSeconds();

			if(realTime <0 )
				realTime = -realTime;

			realTime2 =RealTimeClock.getInstance().getRealTime().getSeconds();

			if(realTime2 < 0 )
				realTime2 = -realTime2;
		}
		int total = 0x1d+name.getBytes().length;


		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x8d);
		writeC(0x00);
		writeC(0x8);
		writeC(0x1);
		writeC(0x10);
		writeC(0x1);

		writeC(0x1a); // 0x1a
		writeBit(total);
		writeC(0x8);
		writeC(type);

		writeC(0x1a);
		writeC(0x00);
		writeC(0x22);
		writeC(length);
		writeByte(name.getBytes());


		writeC(0x28);


		writeBit(realTime);

		writeC(0x30);

		writeBit(realTime2);

		writeC(0x3a);
		writeC(0x09);
		writeC(0x0a);
		writeC(0x04);
		writeC(0x34);
		writeC(0x36);
		writeC(0x35);
		writeC(0x34);
		writeC(0x10);
		writeC(0x90);
		writeC(0x4e);
		writeH(0x00);
		}



	*/

	/** Connection-related packets */
	public static final int InDungeon = 0x08b6;
	public static final int InDungeExit = 0x08b8;
	public static final int InDungeStart = 0x08b2;
	public static final int InDungeonOut = 0x08bb;
	public static final int InDungeonOutUse = 0x08b9;
	public static final int InDungeonOutExit = 0x08bc;
	public static final int InDungeonList = 0x08a4;
	public static final int InDungeonSlot = 0x08aa;
	public static final int InDungeonStancet = 0x08ad;
	public static final int InDungeonType = 0x08a8;
	public static final int InDungeonOpen = 0x08ab;
	public static final int InDungeonRenewal = 0x08ae;
	public static final int InDungeonSetUp = 0x08a6;

	public static final int InDungeoninvite = 0x02ca;

  	/** Dungeon-related alarm part summary */
  	public S_EventNotice(int Type) {
  		writeC(Opcodes.S_EXTENDED_PROTOBUF);
  		writeH(Type);
		switch (Type) {
			/** b4 b6 08 08 01 12 05 08 c9 01 10 01 12 05 08 ca 01 10 00  */
			case InDungeon:
		  		/** seems to be the type */
		  		writeC(0x08);
		  		writeC(0x01);

		  		int TypeNumber, Counter;
		  		Map<Integer, DungeonInfo> DungeonInfoList = DungeonSystem.DungeonInfoList();
		  		for (int i = 0; i < 2; i++){
		  			TypeNumber = (i == 0 ? 201 : 202); Counter = 0;
		  			Iterator<Integer> keySetIterator = DungeonInfoList.keySet().iterator();
		  			while (keySetIterator.hasNext()) {
		  				DungeonInfo DungeonKey = DungeonInfoList.get(keySetIterator.next());
		  				if(DungeonKey.TypeNumber == TypeNumber) Counter++;
		  			}
			  		writeC(0x12);
			  		writeC(2 + size7B(TypeNumber) + size7B(Counter));

			  		writeC(0x08);
			  		writeBit(TypeNumber);

			  		writeC(0x10);
			  		writeBit(Counter);
		  		}
				break;
		}
  		writeH(0);
  	}

  	/** b4 b2 08 08 01 10 b0 60 03 81 */
  	public S_EventNotice(int Type, int InDungeType, int InDungeNum) {
  		writeC(Opcodes.S_EXTENDED_PROTOBUF);
  		writeH(Type);
		switch (Type) {
			/** b4 a8 08 08 0f 10 00 0e 00 */
			case InDungeonStancet:
			case InDungeonType:
			case InDungeonSlot:
			case InDungeExit:
			case InDungeStart:
		  		/** packet type */
		  		writeC(0x08);
		  		writeC(InDungeType);

		  		/** need to check */
		  		writeC(0x10);
		  		writeBit(InDungeNum);
				break;

			case InDungeonOut:
		  		/** packet type */
		  		writeC(0x08);
		  		writeC(0x01);

		  		/** packet type */
		  		writeC(0x10);
		  		writeC(InDungeNum);

		  		/** need to check */
		  		writeC(0x18);
		  		writeBit(InDungeType);
				break;

			/** b4 a4 08 08 01 10 01 18 01 */
			case InDungeonList:
		  		/** need to check */
		  		writeC(0x08);
		  		writeC(0x01);

		  		/** check request */
		  		writeC(0x10);
		  		writeC(0x01);

		  		/** size value */
		  		writeC(0x18);
		  		writeC(DungeonSystem.DungeonInfoList().size());

		  		Map<Integer, DungeonInfo> DungeonInfoList = DungeonSystem.DungeonInfoList();
	  			Iterator<Integer> keySetIterator = DungeonInfoList.keySet().iterator();
	  			while (keySetIterator.hasNext()) {
	  				DungeonInfo DungeonInfo = DungeonInfoList.get(keySetIterator.next());
	  				if(InDungeType != 0 && (DungeonInfo.TypeNumber != InDungeType)) continue;
			  		/** Update room information */
			  		writeC(0x22);
			  		writeC(17 + DungeonInfo.Title.getBytes().length + size7B(DungeonInfo.RoomNumber) + size7B(DungeonInfo.MaxSize) + size7B(DungeonInfo.MinSize) +
			  				size7B(DungeonInfo.DungeonList.size()) + size7B(DungeonInfo.TypeNumber) + size7B(DungeonInfo.Level) + size7B(DungeonInfo.Adena));

			  		/** room number */
			  		writeC(0x08);
			  		writeBit(DungeonInfo.RoomNumber);

			  		/** room tile */
			  		writeC(0x12);
			  		writeBit(DungeonInfo.Title.getBytes().length);
			  		writeByte(DungeonInfo.Title.getBytes());

			  		writeC(0x18);
			  		writeBit(DungeonInfo.DungeonList.size());

			  		/** open socket information */
			  		writeC(0x20);
			  		writeBit(DungeonInfo.MaxSize);

			  		writeC(0x28);
			  		writeBit(DungeonInfo.MinSize);

			  		/** room number */
			  		writeC(0x30);
			  		writeBit(DungeonInfo.TypeNumber);

			  		/** lock icon */
			  		writeC(0x38);
			  		writeC(DungeonInfo.Open);

			  		/** level */
			  		writeC(0x40);
			  		writeBit(DungeonInfo.Level);

			  		/** amount part */
			  		writeC(0x48);
			  		writeBit(DungeonInfo.Adena);

			  		writeC(0x50);
			  		writeC(0x01);
			  		/** Game Start Inplay Settings */
			  		writeC(0x58);
			  		writeC(DungeonInfo.InPlaygame ? 1 : 0);
			  		/** observer settings */
			  		writeC(0x60);
			  		writeC(0x00);
	  			}
				break;
		}
  		writeH(0);
  	}

  	public S_EventNotice(int Type, DungeonInfo DungeonInfo, L1PcInstance Pc) {
  		writeC(Opcodes.S_EXTENDED_PROTOBUF);
  		writeH(Type);
		switch (Type) {
			/** b4 a8 08 08 0f 10 00 0e 00 */
			case InDungeonOpen:
		  		/** dungeon number type */
		  		writeC(0x08);
		  		writeBit(DungeonInfo.RoomNumber);

		  		/** Observe type */
		  		writeC(0x10);
		  		writeBit(0x00);

		  		/** Initial information setting  */
		  		for (L1PcInstance PcList : DungeonInfo.isDungeonInfoUset()){
			  		writeC(0x1a);
			  		writeBit(21 + size7B(PcList.getId()) + PcList.getName().getBytes().length);

			  		writeC(0x08);
			  		writeBit(PcList.getId());

			  		/** Fixed server number 100 */
			  		writeC(0x10);
			  		writeBit(100);

			  		/** name info  */
			  		writeC(0x1a);
			  		writeBit(PcList.getName().getBytes().length);
					writeByte(PcList.getName().getBytes());

					/** class type */
			  		writeC(0x20);
			  		writeBit(PcList.getType());

			  		/** Is this far? */
			  		writeC(0x28);
			  		writeC(0x01);
			  		writeC(0x30);
			  		writeC(0x01);
			  		writeC(0x38);
			  		writeC(0x01);
			  		writeC(0x40);
			  		writeC(DungeonInfo.DungeonLeadt == PcList.getId() ? 1 : 0);
			  		writeC(0x48);
			  		writeC(DungeonInfo.isDungeonInfoCheck(PcList) ? 1 : 0);
			  		writeC(0x50);
			  		writeC(0x01);
			  		writeC(0x58);
			  		writeC(DungeonInfo.DungeonList.indexOf(DungeonInfo.isUser(PcList)));
				}

		  		/** Update room information */
		  		writeC(0x22);
		  		writeC(17 + DungeonInfo.Title.getBytes().length + size7B(DungeonInfo.RoomNumber) + size7B(DungeonInfo.MaxSize) + size7B(DungeonInfo.MinSize) +
		  				size7B(DungeonInfo.DungeonList.size()) + size7B(DungeonInfo.TypeNumber) + size7B(DungeonInfo.Level) + size7B(DungeonInfo.Adena));

		  		/**room number */
		  		writeC(0x08);
		  		writeBit(DungeonInfo.RoomNumber);

		  		/** room title */
		  		writeC(0x12);
		  		writeBit(DungeonInfo.Title.getBytes().length);
		  		writeByte(DungeonInfo.Title.getBytes());

		  		writeC(0x18);
		  		writeBit(DungeonInfo.DungeonList.size());

		  		/** open socket information */
		  		writeC(0x20);
		  		writeBit(DungeonInfo.MaxSize);

		  		writeC(0x28);
		  		writeBit(DungeonInfo.MinSize);

		  		/** room number */
		  		writeC(0x30);
		  		writeBit(DungeonInfo.TypeNumber);

		  		/** lock icon */
		  		writeC(0x38);
		  		writeC(DungeonInfo.Open);

		  		/** level */
		  		writeC(0x40);
		  		writeBit(DungeonInfo.Level);

		  		/** amount part */
		  		writeC(0x48);
		  		writeBit(DungeonInfo.Adena);

		  		writeC(0x50);
		  		writeC(0x01);
		  		/** Game Start Inplay Settings */
		  		writeC(0x58);
		  		writeC(DungeonInfo.InPlaygame ? 1 : 0);
		  		/** observer settings */
		  		writeC(0x60);
		  		writeC(0x00);
				break;

			case InDungeonRenewal:
			case InDungeonOutUse:
		  		/** dungeon number type */
		  		writeC(0x08);
		  		writeBit(DungeonInfo.RoomNumber);

		  		writeC(0x1a);
		  		writeBit(21 + size7B(Pc.getId()) + Pc.getName().getBytes().length);

		  		writeC(0x08);
		  		writeBit(Pc.getId());

		  		/** Fixed server number 100 */
		  		writeC(0x10);
		  		writeBit(100);

		  		/** name information  */
		  		writeC(0x1a);
		  		writeBit(Pc.getName().getBytes().length);
				writeByte(Pc.getName().getBytes());

				/** class type */
		  		writeC(0x20);
		  		writeBit(Pc.getType());

		  		/** Is this far? */
		  		writeC(0x28);
		  		writeC(Type == InDungeonRenewal ? 0x01 : 0x00);
		  		writeC(0x30);
		  		writeC(Type == InDungeonRenewal ? 0x01 : 0x00);
		  		writeC(0x38);
		  		writeC(0x01);
		  		/** Party Leader Awareness Check */
		  		writeC(0x40);
		  		writeC(DungeonInfo.DungeonLeadt == Pc.getId() ? 1 : 0);
		  		writeC(0x48);
		  		writeC(DungeonInfo.isDungeonInfoCheck(Pc) ? 1 : 0);
		  		writeC(0x50);
		  		writeC(Type == InDungeonRenewal ? 0x01 : 0x02);
		  		/** seat location*/
		  		writeC(0x58);
		  		writeC(DungeonInfo.DungeonList.indexOf(DungeonInfo.isUser(Pc)));
				break;

			case InDungeonOutExit:
		  		writeC(0x08);
		  		writeBit(DungeonInfo.RoomNumber);

		  		writeC(0x10);
		  		writeBit(Pc.getId());

		  		/** name information  */
		  		writeC(0x1a);
		  		writeBit(Pc.getName().getBytes().length);
				writeByte(Pc.getName().getBytes());
				break;

			/** 08 01 12 12 08 50 10 01 18 06 20 01 2a 05 08 88 27 10 00 30 c9 01 */
			case InDungeonSetUp:
		  		/** room number */
		  		writeC(0x08);
		  		writeC(0x01);

		  		/** size */
		  		writeC(0x12);
		  		writeC(10 + size7B(DungeonInfo.Level) + size7B(DungeonInfo.Type) + size7B(DungeonInfo.MaxSize) +
		  				size7B(DungeonInfo.Division) + size7B(DungeonInfo.Adena) + size7B(DungeonInfo.TypeNumber));

		  		/** level information */
		  		writeC(0x08);
		  		writeBit(DungeonInfo.Level);

		  		/** Type 1: Defense 2: Dungeon*/
		  		writeC(0x10);
		  		writeBit(DungeonInfo.Type);

		  		/** personnel socket information */
		  		writeC(0x18);
		  		writeBit(DungeonInfo.MaxSize);

		  		/** distribution method */
		  		writeC(0x20);
		  		writeBit(DungeonInfo.Division);

		  		/** size calculation */
		  		writeC(0x2a);
		  		writeC(3 + size7B(DungeonInfo.Adena));
		  		/** need adena */
		  		writeC(0x08);
		  		writeBit(DungeonInfo.Adena);

		  		/** is this far */
		  		writeC(0x10);
		  		writeBit(0x00);

		  		/** room information */
		  		writeC(0x30);
		  		writeBit(DungeonInfo.TypeNumber);
		  		break;

		}
  		writeH(0);
  	}

  	public S_EventNotice(int Type, int InDungeNum, String PcName, String UseName) {
  		writeC(Opcodes.S_EXTENDED_PROTOBUF);
  		writeH(Type);
		switch (Type) {
			/**b4 ca 02 08 91 25 12 08 c3 b5 b1 ba ba d2 c6 d0 1a 08 c3 b5 b1 ba ba d2 c6 d0 20 00 38 01 */
			case InDungeoninvite:
		  		/** packet type */
		  		writeC(0x08);
		  		writeC(InDungeNum);

		  		/** need to check */
		  		writeC(0x12);
		  		writeBit(PcName.getBytes().length);
				writeByte(PcName.getBytes());

		  		/** need to check */
		  		writeC(0x1a);
		  		writeBit(UseName.getBytes().length);
				writeByte(UseName.getBytes());

		  		/** need to check */
		  		writeC(0x20);
		  		writeBit(0x00);

		  		writeC(0x38);
		  		writeBit(InDungeNum);
				break;
		}
		writeH(0);
  	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_EventNotice;
	}
}

