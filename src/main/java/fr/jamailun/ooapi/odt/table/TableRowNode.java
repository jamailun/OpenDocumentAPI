package fr.jamailun.ooapi.odt.table;

import fr.jamailun.ooapi.odt.OdtIterableNode;
import fr.jamailun.ooapi.xml.XmlNode;

public class TableRowNode extends OdtIterableNode {

    public static final String XML_NAME = "table:table-row";

    public TableRowNode(XmlNode node) {
        super(node);
        fillFromXmlNode(
                n -> n.getName().equals(TableCellNode.XML_NAME) || n.getName().equals(TableCellNode.CoveredCellNode.XML_NAME),
                this::get
        );
    }

    private TableCellNode get(XmlNode node) {
        if(node.getName().equals(TableCellNode.CoveredCellNode.XML_NAME))
            return new TableCellNode.CoveredCellNode(node);
        return new TableCellNode(node);
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

}
