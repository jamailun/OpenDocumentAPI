package fr.jamailun.ooapi.xml.parsing;

import fr.jamailun.ooapi.xml.parsing.XmlToken;

public enum XmlTokenType {
	
	CHEVRON_LEFT_START,     // <
	CHEVRON_LEFT_END,	    // </
	CHEVRON_RIGHT,          // >
	CHEVRON_RIGHT_END,	    // />
	
	CHEVRON_OPEN_META,      // <?xml
	CHEVRON_CLOSE_META,     // ?>
	CHEVRON_OPEN_DOCTYPE,   // <!DOCTYPE
	
	EQUAL,
	
	LABEL,  // <[LABEL] [LABEL]="[STRING]">[TEXT]</[LABEL]>
	STRING,
	TEXT;
	
	public XmlToken get() {
		return new XmlToken(this);
	}
	
	public XmlToken get(String text) {
		return new XmlToken(this, text);
	}

}
