package fr.jamailun.ooapi.odt.draw;

import fr.jamailun.ooapi.odt.LibraryOdt;
import fr.jamailun.ooapi.odt.OdtIterableNode;
import fr.jamailun.ooapi.xml.XmlNode;

public class FrameNode extends OdtIterableNode {

    public static final String XML_NAME = "draw:frame";

    public FrameNode(XmlNode node) {
        super(node);
        fillFromXmlNode(LibraryOdt::containsNodeName, LibraryOdt::create);
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

}
