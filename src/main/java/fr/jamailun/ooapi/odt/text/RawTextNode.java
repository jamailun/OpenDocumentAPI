package fr.jamailun.ooapi.odt.text;

import fr.jamailun.ooapi.common.TextContainer;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.odt.annotations.OdtCore;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

@OdtCore
public final class RawTextNode extends OdtNode implements TextContainer {

    public static final String XML_NAME = "";
    private String text;

    public RawTextNode(XmlNode node) {
        super(node);
        text = node.getTextContent();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text == null ? "" : text;
    }

    @Override
    public final String getNodeName() {
        return XML_NAME;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return indent + getText() + endl;
    }

}
