package codes;

/**
 * 
 * Code basic
 * @author k2d
 */
public class Code{
	private short value;
	private String desc;

	protected Code(int value, String desc){
		this.value = (short)value;
		this.desc = desc;
	}

	public short value() {
		return value;
	}

	public String desc() {
		return desc;
	}

	public String toString(){
		return "[value:" + value + ",desc:" + desc + "]";
	}
	
}
