
public class token {
	private String type; //token鐨勭被鍒�
	private int line; //token鎵�鍦ㄨ锛岄敊璇鐞嗕娇鐢�
	private String value; //token鐨勫�硷紝鍙湁鏍囪瘑绗﹀拰甯搁噺鏈夊��

	public token(String _type,int _line, String _value) {
		type = _type;
		line = _line;
		value = _value;
	}
	

	public void setType(String _st) {
		type = _st;
	}

	public void setLine(int _line) {
		line = _line;
	}

	public void setValue(String _value) {
		value = _value;
	}

	public String getType() {
		return type;
	}

	public int getLine() {
		return line;
	}

	public String getValue() {
		return value;
	}
	

}
