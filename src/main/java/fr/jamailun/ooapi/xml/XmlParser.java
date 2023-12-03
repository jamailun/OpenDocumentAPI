package fr.jamailun.ooapi.xml;

import fr.jamailun.ooapi.xml.parsing.ElementsStream.TokensStream;
import fr.jamailun.ooapi.xml.parsing.MalformedXmlException;
import fr.jamailun.ooapi.xml.parsing.XmlToken;
import fr.jamailun.ooapi.xml.parsing.XmlTokenType;
import fr.jamailun.ooapi.xml.parsing.XmlTokenizator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    private XmlAttributesMap meta;
    private final TokensStream tokens;

    public static XmlDocument parse(InputStream is) throws IOException {
        String string = new String(is.readAllBytes());
        return parse(string);
    }

    public static XmlDocument parse(String string) {
        return new XmlParser(string).parse();
    }

    XmlParser(String string) {
        tokens = new XmlTokenizator(string).tokenize();
    }

    public XmlDocument parse() {
        meta = new XmlAttributesMap();

        XmlToken token;
        // Read META
        while(tokens.peek().getType() == XmlTokenType.CHEVRON_OPEN_META) {
            tokens.next();
            readMeta();
        }

        // Ignore DOCTYPE.
        while(tokens.peek().getType() == XmlTokenType.CHEVRON_OPEN_DOCTYPE) {
            while(tokens.next().getType() != XmlTokenType.CHEVRON_RIGHT) { /* done in #next */ }
        }

        // Assert start of real xml
        token = tokens.next();
        if(token.getType() != XmlTokenType.CHEVRON_LEFT_START)
            throw new MalformedXmlException("expected a '<' to start a document. Got " + token + ".");

        XmlNode root = readNode();
        return new XmlDocument(meta, root);
    }

    // on suppose qu'un CHEVRON_OPEN_META précède.
    private XmlNode readNode() {
        // name
        String name = tokens.readLabel("read node");
        // attributes
        XmlAttributesMap attributes = new XmlAttributesMap();
        attributes.add(readAttributes("[node "+name+"]"));
        // Node
        XmlNode node = new XmlNode(name, attributes);
        // Children
        if(tokens.peek().getType() == XmlTokenType.CHEVRON_RIGHT_END) {
            tokens.next();
            return node;
        } else {
            // read children
            tokens.removeOrThrow(XmlTokenType.CHEVRON_RIGHT, "closing attributes block of node " + name);
            // children
            while(tokens.peek().getType() != XmlTokenType.CHEVRON_LEFT_END) { // wait for "</"
                XmlToken t = tokens.next();
                if(t.getType() == XmlTokenType.TEXT) {
                    node.addChild(new XmlNodeRawText(t.getText()));
                } else if(t.getType() == XmlTokenType.CHEVRON_LEFT_START) {
                    XmlNode child = readNode();
                    node.addChild(child);
                } else {
                    throw new MalformedXmlException("expected '<' or 'TEXT' in child of "+node+". Got " + t + ".");
                }
            }
            // read </[LABEL]>
            tokens.next();
            String closeLabel = tokens.readLabel("closing node " + node);
            tokens.removeOrThrow(XmlTokenType.CHEVRON_RIGHT, "closing node " + node);
            if(! closeLabel.equals(name)) {
                throw new MalformedXmlException("Got '</"+closeLabel+">' but was expecting '</"+name+">'.");
            }
        }
        // node
        return node;
    }

    // on suppose qu'un CHEVRON_OPEN_META précède.
    private void readMeta() {
        meta.add(readAttributes("[meta]"));
        tokens.removeOrThrow(XmlTokenType.CHEVRON_CLOSE_META, "closing meta block");
    }

    private List<XmlAttribute> readAttributes(String parent) {
        List<XmlAttribute> list = new ArrayList<>();

        while(tokens.peek().getType() == XmlTokenType.LABEL) {
            // LABEL
            String name = tokens.readLabel("getting name of attribute in " + parent);
            // =
            tokens.removeOrThrow(XmlTokenType.EQUAL, "reading attributes of "+parent + " (attr="+name+")");
            // VALUE
            String value = tokens.readString("getting value of "+parent);

            // add it
            list.add(new XmlAttribute(name, value));
        }
        return list;
    }

}