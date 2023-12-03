package fr.jamailun.ooapi.odt.draw;

import fr.jamailun.ooapi.odt.LibraryOdt;
import fr.jamailun.ooapi.odt.OdtIterableNode;
import fr.jamailun.ooapi.xml.XmlNode;

public class TextBoxNode extends OdtIterableNode {

    public final static String XML_NAME = "draw:text-box";

    public TextBoxNode(XmlNode node) {
        super(node);
        fillFromXmlNode(LibraryOdt::containsNodeName, LibraryOdt::create);
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

}
