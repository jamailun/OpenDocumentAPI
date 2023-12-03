package fr.jamailun.ooapi.xml.parsing;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.ArrayUtils;

public class ElementsStream<T> {
	
	public static class CharsStream extends ElementsStream<Character> {
		public CharsStream(String string) {
			super(ArrayUtils.toObject(string.toCharArray()));
		}
	}
	
	public static class TokensStream extends ElementsStream<XmlToken> {
		public TokensStream(List<XmlToken> tokens) {
			super(tokens.toArray(new XmlToken[0]));
		}
		public String read(String context, XmlTokenType expected) {
			XmlToken token = next();
			if(token.getType() != expected)
				throw new MalformedXmlException("during " + context + " expected "+expected+". Got " + token + ".");
			return token.getText();
		}
		public String readLabel(String context) {
			return read(context, XmlTokenType.LABEL);
		}
		public String readString(String context) {
			return read(context, XmlTokenType.STRING);
		}
		public void removeOrThrow(XmlTokenType type, String context) {
			XmlToken token = next();
			if(token.getType() != type)
				throw new MalformedXmlException("during " + context + " expected "+type+", got " + token);
		}
	}
	
	private final T[] elements;
	private int index;
	
	public ElementsStream(T[] elements) {
		this.elements = elements;
		index = 0;
	}
	
	public T next() {
		return elements[index++];
	}
	
	public T peek() {
		return elements[index];
	}
	
	public void delete() {
		index++;
	}
	
	public void reset() {
		index = 0;
	}
	
	public T remote(int diff) {
		int i = index + diff;
		if(i >= elements.length) throw new IllegalArgumentException("non.");
		return elements[i];
	}
	
	public void previous() {
		index--;
		if(index < 0) index = 0;
	}
	
	public boolean hasNext() {
		return index < elements.length;
	}
	
	public void add(int delta) {
		index += delta;
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ");
		for(int i = index; i < elements.length; i++)
			sj.add(elements[i].toString());
		return "[" + sj + "]";
	}
}
