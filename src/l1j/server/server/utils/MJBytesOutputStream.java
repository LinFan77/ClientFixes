package l1j.server.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MJBytesOutputStream extends OutputStream{

	private byte[] 	_buf;		// buffer
	private int		_idx;		// the index currently pointed to by the buffer
	private int		_capacity;	// size of buffer / size to be extended
	private boolean _isClosed;	// Is the stream closed?
	private boolean	_isShared;	// Whether the stream's data is sharable

	public MJBytesOutputStream(){
		this(4096);
	}

	public MJBytesOutputStream(int capacity){
		_isShared	= false;
		_isClosed	= false;
		_capacity 	= capacity;
		_buf		= new byte[_capacity];
	}

	/** Resize the stream. **/
	private void realloc(int capacity){
		_capacity 	= capacity;
		byte[] tmp 	= new byte[_capacity];
		System.arraycopy(_buf, 0, tmp, 0, _idx);
		_buf 		= tmp;
		_isShared 	= false;
	}

	/** write data **/
	@Override
	public void write(int i) throws IOException {
		if(_isClosed)
			throw new IOException("BytesOutputStream Closed...");

		if(_idx >= _capacity)
			realloc(_capacity*2+1);

		_buf[_idx++] = (byte)(i & 0xff);
	}

	/** write data **/
	@Override
	public void write(byte[] data, int offset, int length) throws IOException{
		if(data == null)
			throw new NullPointerException();

		if(offset < 0 || offset + length > data.length || length < 0)
			throw new IndexOutOfBoundsException();

		if(_isClosed)
			throw new IOException("BytesOutputStream Closed...");

		int capacity = _capacity;
		while(_idx + length > capacity)
			capacity = capacity*2+1;
		if(capacity > _capacity)
			realloc(capacity);

		System.arraycopy(data, offset, _buf, _idx, length);
		_idx += length;
	}

	/** Writes short type (2 bytes) data. **/
	public void writeH(int i) throws IOException{
		write(i 		& 0xFF);
	    write(i >> 8 	& 0xFF);
	}

	/** Write int type (4byte) data. **/
	public void writeD(int i) throws IOException{
		write(i 		& 0xFF);
	    write(i >> 8 	& 0xFF);
	    write(i >> 16 	& 0xFF);
	    write(i >> 24 	& 0xFF);
	}

	public void writeBit(long value) throws Exception
	{
		if (value < 0L) {
			String str = Integer.toBinaryString((int)value);
			value = Long.valueOf(str, 2).longValue();
		}
		int i = 0;
		while (value >> 7 * (i + 1) > 0L)
			write((int)((value >> 7 * i++) % 128L | 0x80));
		write((int)((value >> 7 * i) % 128L));
	}

	public void writeS(String text) throws IOException{
		writeS(text, "MS949");
	}

	public void writeS(String text, String encoding) throws IOException{
		if(text != null){
			byte[] b = text.getBytes(encoding);
			write(b, 0, b.length);
		}
		write(0);
	}

	public void writeSForMultiBytes(String text) throws IOException{
		writeSForMultiBytes(text, "MS949");
	}

	public void writeSForMultiBytes(String text, String encoding) throws IOException{
		if (text != null) {
			byte[] b = text.getBytes(encoding);
			int i = 0;
			while (i < b.length) {
				if ((b[i] & 0xff) >= 0x7f) {
					write(b[i + 1]);
					write(b[i]);
					i += 2;
				} else {
					write(b[i]);
					write(0);
					i += 1;
					}
				}
			}
		write(0);
		write(0);
	}

	/** Write to the new outputStream. **/
	public void writeTo(OutputStream out) throws IOException{
		out.write(_buf, 0, _idx);
	}

	/** Make it an InputStream. **/
	public InputStream toInputStream(){
		_isShared = true;
		return new MJBytesInputStream(_buf, 0, _idx);
	}

	/** reset **/
	public void reset() throws IOException{
		if(_isClosed)
			_isClosed = false;

		if(_isShared){
			_buf = new byte[_capacity];
			_isShared = false;
		}

		_idx = 0;
	}

	/** close the stream **/
	@Override
	public void close(){
		_isClosed = true;
	}

	/** Returns the contents of the stream as an array. **/
	public byte[] toArray(){
		byte[] result = new byte[_idx];
		System.arraycopy(_buf, 0, result, 0, _idx);
		return result;
	}
}

