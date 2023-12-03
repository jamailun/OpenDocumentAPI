package fr.jamailun.ooapi.xml.parsing;

import fr.jamailun.jamLogger.JamLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlTokenizator {
	
	private final static String ALLOWED_LABEL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-:0123456789";
	private final static char[] DOCTYPE = "<!DOCTYPE ".toCharArray();
	private final static char[] XML_META = "<?xml ".toCharArray();
	private final static char[] XML_COMMENT = "<!---".toCharArray();
	private final static char[] XML_COMMENT_END = "-->".toCharArray();
	
	List<XmlToken> tokens;
	ElementsStream.CharsStream chars;
	
	public static ElementsStream.TokensStream tokenize(String string) {
		return new XmlTokenizator(string).tokenize();
	}
	
	public XmlTokenizator(String string) {
		tokens = new ArrayList<>();
		chars = new ElementsStream.CharsStream(string);
	}
	
	public ElementsStream.TokensStream tokenize() {
		tokens.clear();
		chars.reset();
		
		char current;
		boolean afterChevronRight = false;
		while(chars.hasNext()) {
			current = chars.next();
			
			if(current == '\n' || current == '\r' || current == '\t' || current == ' ') {
				//rien
				continue;
			}
			
			if(current == '\"') {
				afterChevronRight = false;
				String string = getString();
				tokens.add(XmlTokenType.STRING.get(string));
				continue;
			}
			
			if(current == '<') {
				afterChevronRight = false;
				char peeked = chars.peek();
				if(peeked == '/') { // </
					chars.next();
					tokens.add(XmlTokenType.CHEVRON_LEFT_END.get());
				} else if(peeked == '?') { // <?
					if(tryString(current, XML_META))
						tokens.add(XmlTokenType.CHEVRON_OPEN_META.get());
					else
						throw new IllegalArgumentException("Malformed OPEN_META.");
				} else if (peeked == '!') { // <!
					if(tryString(current, DOCTYPE))
						tokens.add(XmlTokenType.CHEVRON_OPEN_DOCTYPE.get());
					else if(tryString(current, XML_COMMENT))
						consumeComment();
					else
						throw new IllegalArgumentException("Malformed OPEN_DOCTYPE.");
				} else {
					tokens.add(XmlTokenType.CHEVRON_LEFT_START.get());
				}
			}
			
			// essaie de texte / label
			else if(afterChevronRight) {
				String text = getText(current);
				tokens.add(XmlTokenType.TEXT.get(text));
				afterChevronRight = false;
			} else if(isLabelAllowed(current)) {
				String label = getLabel(current);
				tokens.add(XmlTokenType.LABEL.get(label));
			}
			
			else if(current == '/') {
				char next = chars.next();
				if(next == '>') { // />
					tokens.add(XmlTokenType.CHEVRON_RIGHT_END.get());
				} else {
					throw new MalformedXmlException("unexpected '"+next+"' after a '" + current + "'.");
				}
			}
			else if(current == '?') {
				char next = chars.next();
				if(next == '>') { // ?>
					tokens.add(XmlTokenType.CHEVRON_CLOSE_META.get());
				} else {
					JamLogger.error(tokens);
					throw new MalformedXmlException("unexpected '"+next+"' after a '" + current + "'.");
				}
			}
			
			else if(current == '>') {
				tokens.add(XmlTokenType.CHEVRON_RIGHT.get());
				afterChevronRight = true;
			}
			else if(current == '=') {
				tokens.add(XmlTokenType.EQUAL.get());
			}
		}
		
		return new ElementsStream.TokensStream(tokens);
	}
	
	private void consumeComment() {
		while(chars.hasNext()) {
			if(chars.next() == '-') {
				if(tryString('-', XML_COMMENT_END))
					return;
			}
		}
	}
	
	private String getText(char first) {
		StringBuilder sb = new StringBuilder(""+first);
		boolean escape = false;
		while(chars.hasNext()) {
			char c = chars.next();
			if(c == '\\') {
				if(escape) {
					escape = false;
					sb.append("\\\\");
				} else {
					escape = true;
				}
			} else if(c == '<' && !escape) {
				chars.previous();
				return sb.toString();
			} else {
				if(escape) {
					sb.append("\\");
					escape = false;
				}
				if(c != '\n' && c != '\r' && c != '\t')
					sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private String getLabel(char first) {
		StringBuilder sb = new StringBuilder(""+first);
		while(chars.hasNext()) {
			char c = chars.peek();
			if(isLabelAllowed(c)) {
				chars.next();
				sb.append(c);
			} else {
				break;
			}
		}
		return sb.toString();
	}
	
	private String getString() {
		StringBuilder sb = new StringBuilder();
		boolean escape = false;
		while(chars.hasNext()) {
			char c = chars.next();
			if(c == '\\') {
				if(escape) {
					escape = false;
					sb.append("\\\\");
				} else {
					escape = true;
				}
			} else if(c == '\"' && !escape) {
				return sb.toString();
			} else {
				if(escape) {
					sb.append("\\");
					escape = false;
				}
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private boolean tryString(char current, char[] compa) {
		if(current != compa[0]) throw new RuntimeException("wtf that's unexptected ("+current+" / " + Arrays.toString(compa) + ")");
		for(int i = 1; i < compa.length; i++) {
			if(chars.remote(i-1) != compa[i]) {
				System.out.println("NOP: ("+chars.remote(i)+") != (" + compa[i] +")");
				return false;
			}
		}
		chars.add(compa.length - 1);
		return true;
	}
	
	private boolean isLabelAllowed(char c) {
		return ALLOWED_LABEL.contains(c+"");
	}
	
}
