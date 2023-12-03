package fr.jamailun.ooapi.odt.text;

import fr.jamailun.jamLogger.JamLogger;
import fr.jamailun.ooapi.common.TextContainer;
import fr.jamailun.ooapi.odt.LibraryOdt;
import fr.jamailun.ooapi.odt.OdtIterableNode;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.odt.annotations.OdtCore;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

import java.util.Map;

/**
 * The only child of the [office:body] node.
 */
@OdtCore
public final class CoreTextNode extends OdtIterableNode {

    public final static String XML_NAME = "office:text";

    public CoreTextNode(XmlNode node) {
        super(node);
        for(XmlNode child : node.getChildren()) {
            OdtNode odtChild = LibraryOdt.create(child);
            children.add(odtChild);
        }
    }

    public String getRawText() {
        StringBuilder sb = new StringBuilder();
        for(TextContainer tc : collectAllTextContainers()) {
            sb.append(tc.getText());
        }
        return sb.toString();
    }

    public void replaceAll(Map<String, String> replacements) {
        for(String k : replacements.keySet()) {
            int count = 0;
            for(TextContainer tc : collectAllTextContainers()) {
                count += tc.countOccurrences(k);
            }
            JamLogger.info("count of ("+k+") = " + count);
            replaceAll(k, replacements.get(k));
        }
    }

    public void replaceAll(String source, String target) {
        for(TextContainer tc : collectAllTextContainers()) {
            tc.replace(source, target);
        }
    }

    public void applyToRealXml() {
        String xml = toXmlPrefix() + ">"
                + toXmlChildren("", new Indent())
                + "</"+getNodeName()+">";
        getNodeReference().forceRawWriting(xml);
    }

    @Override
    public final String getNodeName() {
        return XML_NAME;
    }

}
