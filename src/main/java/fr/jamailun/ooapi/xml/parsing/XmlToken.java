package fr.jamailun.ooapi.xml.parsing;

public class XmlToken {
	
	private final XmlTokenType type;
	private final String text;
	
	XmlToken(XmlTokenType type) {
		this(type, null);
	}
	
	XmlToken(XmlTokenType type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public XmlTokenType getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return type + (text == null ? "" : "(\""+text+"\")");
	}
}
