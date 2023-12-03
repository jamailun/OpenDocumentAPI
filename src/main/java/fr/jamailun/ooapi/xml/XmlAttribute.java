package fr.jamailun.ooapi.xml;

public record XmlAttribute(String name, String value) {
	@Override
	public String toString() {
		return name + "=\"" + value + "\"";
	}
}
