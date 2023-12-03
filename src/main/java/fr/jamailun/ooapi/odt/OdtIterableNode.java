package fr.jamailun.ooapi.odt;

import fr.jamailun.jamLogger.JamLogger;
import fr.jamailun.ooapi.odt.text.RawTextNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;
import fr.jamailun.ooapi.xml.XmlNodeRawText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public abstract class OdtIterableNode extends OdtNode implements Iterable<OdtNode> {

    protected final List<OdtNode> children;

    public OdtIterableNode(XmlNode node) {
        super(node);
        children = new ArrayList<>();
    }

    protected void fillFromXmlNode(Predicate<XmlNode> p, Function<XmlNode, OdtNode> f) {
        for(XmlNode s : getNodeReference()) {
            if(s instanceof XmlNodeRawText rawXml) {
                children.add(new RawTextNode(rawXml));
                continue;
            }
            if(p.test(s)) {
                children.add(f.apply(s));
            } else {
                JamLogger.error("Unknown child '"+s+"' in " + getNodeReference().getName()+".");
            }
        }
    }

    @Override
    public String toXml(Indent indent, String endl) {
        if(hasChildren()) {
            return indent + toXmlPrefix() + ">" + endl
                    + toXmlChildren(endl, indent)
                    + indent + "</"+getNodeName()+">" + endl;
        } else {
            return indent + toXmlPrefix() + "/>" + endl;
        }
    }

    @Override
    public Iterator<OdtNode> iterator() {
        return getChildren().listIterator();
    }

    @Override
    public List<OdtNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

}
