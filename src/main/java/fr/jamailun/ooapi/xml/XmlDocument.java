package fr.jamailun.ooapi.xml;

import java.io.IOException;
import java.io.InputStream;

public class XmlDocument {

	private final XmlAttributesMap meta;
	private final XmlNode root;
	
	XmlDocument(XmlAttributesMap meta, XmlNode root) {
		this.meta = meta;
		this.root = root;
	}
	
	public XmlAttributesMap getMeta() {
		return meta;
	}
	
	public XmlNode getRoot() {
		return root;
	}
	
	@Override
	public String toString() {
		return "XmlDocument{" +
				"meta=" + meta +
				", root=\n" + root.niceString(true) +
				"\n}";
	}
}
