package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_DoActionShop extends ServerBasePacket
{
  public S_DoActionShop(int object, int gfxid, byte[] message)
  {
	writeC(Opcodes.S_ACTION);
    writeD(object);
    writeC(gfxid);
    writeByte(message);
  }

  @Override
public byte[] getContent()
  {
    return getBytes();
  }

  @Override
public String getType()
  {
    return "S_DoActionShop";
  }
}
