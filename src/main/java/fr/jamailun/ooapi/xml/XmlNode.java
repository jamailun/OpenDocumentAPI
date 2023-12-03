package fr.jamailun.ooapi.xml;

import fr.jamailun.ooapi.utils.Indent;

import java.util.*;
import java.util.function.Predicate;

public class XmlNode implements Iterable<XmlNode> {
	private static long UUID_PROVIDER = 0;
	private final long uuid = UUID_PROVIDER++;

	private String name;
	private XmlAttributesMap attributes;
	private XmlNode parent = null;
	private final List<XmlNode> children = new ArrayList<>();
	
	public static String toStringCollection(Collection<XmlNode> nodes, boolean nicePrint) {
		StringBuilder sb = new StringBuilder();
		nodes.forEach(n -> sb.append(n.niceString(nicePrint)));
		return sb.toString();
	}
	
	public XmlNode(String name, XmlAttributesMap attributes) {
		this.name = name;
		this.attributes = attributes;
	}
	
	public void forceRawWriting(String xml) {
		XmlDocument doc = XmlParser.parse(xml);
		name = doc.getRoot().getName();
		attributes = doc.getRoot().getAttributes();
		children.clear();
		children.addAll(doc.getRoot().getChildren());
	}
	
	void addChild(XmlNode node) {
		if(node.getParent() != null) {
			node.getParent().children.remove(node);
		}
		node.parent = this;
		children.add(node);
	}

	public XmlNode getParent() {
		return parent;
	}
	
	public List<XmlNode> getChildren() {
		return getChildren(n -> !(n instanceof XmlNodeRawText));
	}

	public List<XmlNode> getAllChildren() {
		return children;
	}
	
	public int getChildrenCount() {
		return getChildren().size();
	}

	public XmlNode getChild(int index) {
		return getChild(index, false);
	}

	public XmlNode getChild(int index, boolean allowText) {
		int realChild = 0;
		for(int i = 0; i < children.size(); i++) {
			XmlNode node = children.get(i);
			if(
					(allowText && i == index)
					|| (!allowText && realChild == index)
			) {
				return node;
			}

			realChild += (node instanceof XmlNodeRawText) ? 0 : 1;
		}
		throw new IndexOutOfBoundsException("Bad index " + index + " for " + this);
	}
	
	public XmlNode getFirstChild(String name) {
		for(XmlNode child : children)
			if(child.getName().equals(name))
				return child;
		return null;
	}

	public List<XmlNode> getChildren(Predicate<XmlNode> predicate) {
		return children.stream()
				.filter(predicate)
				.toList();
	}
	
	public List<XmlNode> getChildren(String name) {
		return getChildren(n -> n.getName().equals(name));
	}

	public String getName() {
		return name;
	}
	
	public String getTextContent() {
		StringBuilder sb = new StringBuilder();
		children.forEach(c -> sb.append(c.getTextContent()));
		String s = sb.toString();
		return s.isEmpty() ? null : s;
	}

	public int getTextAsInt() {
		return Integer.parseInt(getTextContent());
	}
	public double getTextAsDouble() {
		return Double.parseDouble(getTextContent());
	}
	public boolean getTextAsBoolean() {
		return Boolean.parseBoolean(getTextContent());
	}
	
	public XmlAttributesMap getAttributes() {
		return attributes;
	}
	
	public String niceString(boolean n) {
		return niceString(new Indent(n ? "\t" : ""), n ? "\n" : "");
	}
	
	public String niceString(Indent indent, String endl) {
		StringBuilder sb = new StringBuilder(indent.toString());
		sb.append("<").append(name);
		if(attributes.size() > 0)
			sb.append(" ").append(attributes);
		if(!children.isEmpty()) {
			sb.append(">").append(endl);
		} else {
			sb.append("/>");
			return sb.toString();
		}
		
		indent.add();
		for(XmlNode child : children) {
			sb.append(child.niceString(indent, endl)).append(endl);
		}
		indent.remove();
		return sb.append(indent).append("</").append(name).append(">").toString();
	}
	
	@Override
	public String toString() {
		return niceString(true);
	}
	
	@Override
	public Iterator<XmlNode> iterator() {
		return children.listIterator();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof XmlNode)) return false;
		return Objects.equals(((XmlNode)obj).uuid, uuid);
	}
}
