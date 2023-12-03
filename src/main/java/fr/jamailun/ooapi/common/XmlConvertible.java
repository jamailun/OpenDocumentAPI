package fr.jamailun.ooapi.common;

import fr.jamailun.ooapi.utils.Indent;

public interface XmlConvertible {

    String getXmlNodeName();

    String toXml(Indent indent, String endl);

    /**
     * Create the XML equivalent of this node.
     * @param nicePrint if true, nice and clear XML. If not, one-line code.
     * @return the valid XML string for this node.
     */
    default String toXml(boolean nicePrint) {
        return nicePrint ? toXml(new Indent("\t"), "\n") : toXml(new Indent(), "");
    }

}
