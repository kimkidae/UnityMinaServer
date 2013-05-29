package codes;

import java.util.HashMap;


/**
 * Protocol Code
 * corresponds Client's Protocol.cs
 * @author k2d
 */
public class Protocol extends Code{
	private static final HashMap<Short, Protocol> codes = new HashMap<>();

	public static Protocol NULL = new Protocol(0,"프로토콜 없음");
	public static Protocol CHECK = new Protocol(1,"SESSION CHECK");
	public static Protocol USER_LOGIN = new Protocol(2,"유저로그인");
	public static Protocol DUPLICATE_LOGIN = new Protocol(3,"중복로그인");

	public Protocol(int code, String desc) {
		super(code, desc);
		codes.put((short)code, this);
	}

	public static Protocol getProtocol(short code){
		if(!codes.containsKey(code)){
			return Protocol.NULL;
		}
		return codes.get(code);
	}
}
