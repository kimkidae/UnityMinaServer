package codes;

import java.util.HashMap;


/**
 * ResultCode
 * corresponds Client's RCode.cs
 * @author k2d
 */
public class RCode extends Code{
	private static final HashMap<Short, RCode> codes = new HashMap<>();

	public static RCode NULL = new RCode(0,"NULL");
	public static RCode SUCESS = new RCode(1,"SUCESS");
	public static RCode FAIL = new RCode(2,"FAIL");

	public static RCode LOGIN_FAIL = new RCode(3,"LOGIN_FAIL");
	public static RCode DUPLICATE_LOGIN = new RCode(4,"DUPLICATE_LOGIN");

	public RCode(int code, String desc) {
		super(code, desc);
		codes.put((short)code, this);
	}

	public static RCode getRCode(short code){
		if(!codes.containsKey(code)){
			return RCode.NULL;
		}
		return codes.get(code);
	}
}
