package fr.jamailun.ooapi.odt.text;

import fr.jamailun.ooapi.common.TextContainer;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

/**
 * For [text:span] nodes inside a text:p.
 */
public class SpanNode extends OdtNode implements TextContainer {
    public static final String XML_NAME = "text:span";

    private String text;

    public SpanNode(XmlNode node) {
        super(node);
        text = node.getTextContent();
        if(text == null)
            text = " ";
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public final String getNodeName() {
        return XML_NAME;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return indent + toXmlPrefix() + ">" + endl
                + indent.add() + text + endl
                + indent.remove() + "</"+getNodeName()+">" + endl;
    }
}
