package fr.jamailun.ooapi.xml;

import fr.jamailun.ooapi.utils.Indent;

public class XmlNodeRawText extends XmlNode {

    private final String textContent;

    public XmlNodeRawText(String text) {
        super("", new XmlAttributesMap());
        this.textContent = text;
    }

    @Override
    public String getTextContent() {
        return textContent;
    }

    @Override
    public String niceString(Indent indent, String endl) {
        return indent + getTextContent();
    }

    @Override
    void addChild(XmlNode node) {
        throw new IllegalAccessError("Cannot add child to a raw-text XML node.");
    }
}