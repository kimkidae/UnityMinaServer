package net;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import codes.Code;


/**
 * configure to send packets to the network
 * corresponds client's Packet.cs
 * @author k2d
 *
 */
public class Packet {

	private static final Charset charset = Charset.forName("UTF-8");
	private static final int DEFAULT_SIZE = 16;
	//client's csharp little_endian used
	private static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
	private IoBuffer data;
	public Packet(){
		data = IoBuffer.allocate(DEFAULT_SIZE);
		data.setAutoExpand(true);//buffer auto expand
		data.order(BYTE_ORDER);
	}

	//Code 등록(Protocol, RCode)
	public Packet addCode(Code code){
		return addShort(code.value());
	}

	public Packet addByte(Byte value){
		if(value == null){
			data.put((byte)0);
		}else{
			data.put(value);
		}
		return this;
	}

	public Packet addBoolean(Boolean value){
		if(value == null){
			return addByte((byte)0);
		}else{
			return addByte(value ? (byte)0 : (byte)1);
		}
	}

	public Packet addShort(Short value){
		if(value == null){
			data.putShort((short)0);
		}else{
			data.putShort(value);
		}
		return this;
	}

	public Packet addInt(Integer value){
		if(value == null){
			data.putInt(0);
		}else{
			data.putInt(value);
		}
		return this;
	}

	public Packet addLong(Long value){
		if(value == null){
			data.putLong(0l);
		}else{
			data.putLong(value);
		}
		return this;
	}

	public Packet addFloat(Float value){
		if(value == null){
			data.putFloat(0f);
		}else{
			data.putFloat(value);
		}
		return this;
	}

	public Packet addDouble(Double value){
		if(value == null){
			data.putDouble(0);
		}else{
			data.putDouble(value);
		}
		return this;
	}

	public Packet addString(String value){
		if(value == null){
			data.putInt(0);
		}else{
			byte[] str = value.getBytes(charset);
			data.putInt(str.length);
			data.put(str);
		}
		return this;
	}
	
	public IoBuffer getData(){
		data.flip();
		return data;
	}

	public static String getString(IoBuffer buffer){
		int len = buffer.getInt();
		if(len == 0){
			return "";
		}else{
			byte[] str = new byte[len];
			buffer.get(str);
			return new String(str, charset);
		}
	}

	public static  Boolean getBoolean(IoBuffer buffer){
		byte b = buffer.get();
		return b==0 ? false : true;
	}
}
