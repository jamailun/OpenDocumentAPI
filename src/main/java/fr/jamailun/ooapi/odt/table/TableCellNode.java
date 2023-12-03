package fr.jamailun.ooapi.odt.table;

import fr.jamailun.ooapi.common.constants.TableConstants;
import fr.jamailun.ooapi.odt.LibraryOdt;
import fr.jamailun.ooapi.odt.OdtIterableNode;
import fr.jamailun.ooapi.utils.StringUtils;
import fr.jamailun.ooapi.xml.XmlNode;

public class TableCellNode extends OdtIterableNode {

    public static final String XML_NAME = "table:table-cell";

    public TableCellNode(XmlNode node) {
        super(node);
        fillFromXmlNode(LibraryOdt::containsNodeName, LibraryOdt::create);
    }

    public int getColumnSpan() {
        return StringUtils.toIntOrZero(properties.get(TableConstants.COLUMN_SPAN));
    }

    public void setColumnSpan(int count) {
        properties.set(TableConstants.COLUMN_SPAN, count + "");
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

    public static class CoveredCellNode extends TableCellNode {
        public static final String XML_NAME = "table:covered-table-cell";
        public CoveredCellNode(XmlNode node) {
            super(node);
        }
        @Override
        public String getNodeName() {
            return XML_NAME;
        }
    }

}
