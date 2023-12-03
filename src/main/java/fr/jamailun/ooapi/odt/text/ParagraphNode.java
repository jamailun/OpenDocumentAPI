package fr.jamailun.ooapi.odt.text;

import fr.jamailun.ooapi.odt.LibraryOdt;
import fr.jamailun.ooapi.odt.OdtIterableNode;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.xml.XmlNode;

/**
 * For [text:p] nodes.
 */
public class ParagraphNode extends OdtIterableNode {

    public static final String XML_NAME = "text:p";

    public ParagraphNode(XmlNode node) {
        super(node);
        fillFromXmlNode(LibraryOdt::containsNodeName, LibraryOdt::create);
    }

    @Override
    public final String getNodeName() {
        return XML_NAME;
    }

}