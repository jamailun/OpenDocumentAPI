package fr.jamailun.ooapi.odt.table;

import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.odt.special.UnknownOdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TableNode extends OdtNode {

    public static final String XML_NAME = "table:table";

    private final List<TableColumnNode> columns = new ArrayList<>();
    private final List<TableRowNode> rows = new ArrayList<>();

    private final List<UnknownOdtNode> unknown = new ArrayList<>();

    public TableNode(XmlNode node) {
        super(node);
        for(XmlNode child : node.getChildren()) {
            // COLUMN
            if(child.getName().equals(TableColumnNode.XML_NAME)) {
                columns.add(new TableColumnNode(child));
            }
            // ROW
            else if(child.getName().equals(TableRowNode.XML_NAME)) {
                rows.add(new TableRowNode(child));
            }
            // UNKNOWN
            else {
                unknown.add(new UnknownOdtNode(child));
            }
        }
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

    @Override
    public List<? extends OdtNode> getChildren() {
        return Stream.concat(columns.stream(), rows.stream()).toList();
    }

    @Override
    public String toXml(Indent indent, String endl) {
        if(columns.isEmpty() && rows.isEmpty())
            return indent + toXmlPrefix() + "/>" + endl;

        StringBuilder children = new StringBuilder();
        indent.add();
        columns.forEach(child -> children.append(child.toXml(indent, endl)));
        rows.forEach(child -> children.append(child.toXml(indent, endl)));

        // add unknowns
        unknown.forEach(uk -> children.append(uk.toXml(indent, endl)));

        indent.remove();

        return indent + toXmlPrefix() + ">" + endl
                + children
                + indent + "</"+getNodeName()+">" + endl;
    }
}
