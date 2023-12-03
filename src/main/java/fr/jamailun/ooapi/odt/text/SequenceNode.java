package fr.jamailun.ooapi.odt.text;

import fr.jamailun.ooapi.common.TextContainer;
import fr.jamailun.ooapi.common.constants.StyleConstants;
import fr.jamailun.ooapi.common.constants.TextConstants;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;


/**
 * For [text:sequence] nodes inside a text:p.
 * <br/>
 * <a href="http://docs.oasis-open.org/office/v1.2/os/OpenDocument-v1.2-os-part1.html#element-text_sequence-decls">docs</a>
 */
public class SequenceNode extends OdtNode implements TextContainer {

    public static final String XML_NAME = "text:sequence";

    private String text;

    public SequenceNode(XmlNode node) {
        super(node);
        text = node.getTextContent();
    }

    public String getSequenceName() {
        return properties.get(TextConstants.TEXT_NAME);
    }
    public String getFormula() {
        return properties.get(TextConstants.FORMULA);
    }
    public String getNumFormat() {
        return properties.get(StyleConstants.NUM_FORMAT);
    }
    public String getRefName() {
        return properties.get(TextConstants.REF_NAME);
    }
    public void setSequenceName(String sequenceName) {
        properties.set(TextConstants.TEXT_NAME, sequenceName);
    }
    public void setFormula(String formula) {
        properties.set(TextConstants.FORMULA, formula);
    }
    public void setNumFormat(String numFormat) {
        properties.set(StyleConstants.NUM_FORMAT, numFormat);
    }
    public void setRefName(String refName) {
        properties.set(TextConstants.REF_NAME, refName);
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
