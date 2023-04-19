package l1j.server.MJBookQuestSystem.Compensator;

import java.sql.ResultSet;

import l1j.server.server.model.Instance.L1PcInstance;

/** An interface to generalize a set of rewards. **/
public interface QuestCompensator {
	/** Methods to automate instance setup  **/
	public void set(ResultSet rs) throws Exception;

	/** Returns the record item with an error **/
	public String getLastRecord();

	/** Returns the difficulty level. **/
	public int getDifficulty();

	/** run the reward **/
	public void compensate(L1PcInstance pc);
}
