package fr.jamailun.ooapi.xml.parsing;

public final class MalformedXmlException extends RuntimeException {
	
	public MalformedXmlException(String msg) {
		super("Malformed XML : " + msg);
	}
	
}
