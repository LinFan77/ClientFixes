package l1j.server.MJInstanceSystem;

import java.util.ArrayList;

import l1j.server.server.clientpackets.C_Attr;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.ServerBasePacket;

public class MJInstanceEnums {
	/** Indicates the user's instance space state. **/
	public enum InstStatus{
		INST_USERSTATUS_NONE(1),	// when not in instance space
		INST_USERSTATUS_LFCREADY(2),
		INST_USERSTATUS_LFCINREADY(4),
		INST_USERSTATUS_LFC(8);

		private int _status;
		InstStatus(int i){
			_status = i;
		}
	}

	public enum InstSpcMessages{
		INSTANCE_SPACE_FULL("The instance dungeon is full. Please try again in a few minutes.");

		private String _msg;
		InstSpcMessages(String msg){
			_msg = msg;
		}

		public String get(){
			return _msg;
		}
		public void sendSystemMsg(L1PcInstance pc){
			pc.sendPackets(new S_SystemMessage(get()));
		}
		public void sendSystemMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_SystemMessage(new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		public void sendGreenMsg(L1PcInstance pc){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, get()));
		}
		public void sendGreenMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, new StringBuilder(get()).append(" ").append(msg).toString()));
		}
	}

	/** Registration related message **/
	public enum LFCMessages{
		REGIST_SUCCESS("Registration is complete."),
		REGIST_ERR_ININST("Not available in instance state."),
		REGIST_ERR_NOADENA("The required item is missing."),
		REGIST_ERR_INTHEMILL("Still preparing."),
		REGIST_ERR_ADENA("The bet amount is incorrect."),
		REGIST_ERR_LEVEL("Level condition not met."),
		CREATE_ERR_TARGET_CANNOT("Your opponent (team) is currently unavailable to participate."),
		CREATE_ERR_CANNOT_INPARTYPLAY("You cannot apply for an individual exhibition during a party."),
		CREATE_ERR_RVR("RVR content is party content."),
		CREATE_ERR_PVP("PVP content is non-party content."),
		CREATE_ERR_ONLYLEADER("Only the party leader can apply."),
		CREATE_ERR_PARTYMEMBER("There are party members who cannot participate."),
		CREATE_ERR_PARTYMAXSIZE("You have exceeded the maximum number of party members."),
		CREATE_ERR_PARTYMINSIZE("The minimum number of party members could not be met."),
		CREATE_NOTIFY_CANCEL_INPARTYPLAY("There was an application for the Colosseum, but it was canceled due to being in a party (individual exhibition) / not in a party (group exhibition)."),
		CREATE_SUBSCRIBE("The Colosseum application came in. If you do not accept within 15 seconds, it will be considered as cancellation."),
		CREATE_SUCCESS("Colosseum application has been completed. If the opponent does not accept within 15 seconds, it will be canceled."),
		CREATE_CANCEL_OWNERUSER("The other party rejected the application."),
		CREATE_CANCEL("The match has been cancelled."),
		INGAME_CLOSE("The match is over. After a while, after the result is judged, we move to the village."),
		INGAME_CLOSE_FORGM("The game was forced to end by the GM."),
		INGAME_NOTIFY_WINNER("I won. Victory reward items will be distributed soon."),
		INGAME_NOTIFY_LOSER("You lost the LFC match."),
		INGAME_NOTIFY_READY("[Game preparation] "),
		INGAME_NOTIFY_START("Start!"),
		INGAME_NOTIFY_CLOSETIME("[End imminent] "),
		INGAME_NOTIFY_LOTTO("congratulations. You have won a random reward.");
		private String _msg;
		LFCMessages(String msg){
			_msg = msg;
		}

		public String get(){
			return _msg;
		}
		public void sendSystemMsg(L1PcInstance pc){
			pc.sendPackets(new S_SystemMessage(get()));
		}
		public void sendSystemMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_SystemMessage(new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		public void sendSystemMsgToList(ArrayList<L1PcInstance> pcs){
			sendList(pcs, new S_SystemMessage(get()));
		}
		public void sendSystemMsgToList(ArrayList<L1PcInstance> pcs, String msg){
			sendList(pcs, new S_SystemMessage(new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		public void sendGreenMsg(L1PcInstance pc){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, get()));
		}
		public void sendGreenMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		public void sendGreenMsgToList(ArrayList<L1PcInstance> pcs){
			sendList(pcs, new S_PacketBox(S_PacketBox.GREEN_MESSAGE, get()));
		}
		public void sendGreenMsgToList(ArrayList<L1PcInstance> pcs, String msg){
			sendList(pcs, new S_PacketBox(S_PacketBox.GREEN_MESSAGE, new StringBuilder(get()).append(" ").append(msg).toString()));
		}

		private void sendList(ArrayList<L1PcInstance> pcs, ServerBasePacket pck){
			int size 		= pcs.size();
			for(int i=0; i<size; i++)
				pcs.get(i).sendPackets(pck, false);
			pck.clear();
		}

		public void sendSurvey(L1PcInstance pc){
			pc.sendPackets(new S_Message_YN(C_Attr.MSGCODE_622_LFC, 622, get()));
		}
	}
}
