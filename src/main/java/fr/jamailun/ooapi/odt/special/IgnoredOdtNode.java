package fr.jamailun.ooapi.odt.special;

import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

public final class IgnoredOdtNode extends OdtNode {

    public IgnoredOdtNode(XmlNode node) {
        super(node);
    }

    @Override
    public String getNodeName() {
        return nodeReference.getName();
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return nodeReference.niceString(indent, endl) + endl;
    }
}
