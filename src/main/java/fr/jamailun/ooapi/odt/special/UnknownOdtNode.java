package fr.jamailun.ooapi.odt.special;

import fr.jamailun.jamLogger.JamLogger;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

public final class UnknownOdtNode extends OdtNode {

    public UnknownOdtNode(XmlNode node) {
        super(node);
        JamLogger.warning("An unknown node has been created with " + node);
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
