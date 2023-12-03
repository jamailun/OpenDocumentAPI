package fr.jamailun.ooapi.odt.table;

import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

public class TableColumnNode extends OdtNode {

    public static final String XML_NAME = "table:table-column";

    public TableColumnNode(XmlNode node) {
        super(node);
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return indent + toXmlPrefix() + "/>" + endl;
    }
}
