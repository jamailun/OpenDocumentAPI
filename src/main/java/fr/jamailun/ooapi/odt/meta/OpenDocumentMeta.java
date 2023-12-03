package fr.jamailun.ooapi.odt.meta;

import fr.jamailun.ooapi.common.constants.MetaConstants;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OpenDocumentMeta extends OdtNode {

    public static final String XML_NAME = "office:meta";

    private final List<XmlNode> otherXml = new ArrayList<>();

    private LocalDateTime lastEditingDate;
    private Duration editingDuration;
    private String generator;
    private int editingCycles;
    private OpenDocumentStatistics stats;

    public OpenDocumentMeta(XmlNode node) {
        super(node);
        node.forEach(this::fromNode);
    }

    public LocalDateTime getLastEditingDate() {
        return lastEditingDate;
    }

    public void setLastEditingDate(LocalDateTime lastEditingDate) {
        this.lastEditingDate = lastEditingDate;
    }

    public Duration getEditingDuration() {
        return editingDuration;
    }

    public void setEditingDuration(Duration editingDuration) {
        this.editingDuration = editingDuration;
    }

    public int getEditingCycles() {
        return editingCycles;
    }

    public void setEditingCycles(int editingCycles) {
        this.editingCycles = editingCycles;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public OpenDocumentStatistics getDocumentStatistics() {
        return stats;
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

    public List<XmlNode> getOtherXml() {
        return otherXml;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return indent +
                toXmlPrefix() + ">" + endl
                + toXmlChildren(indent, endl)
                + indent.remove() + "</"+getNodeName()+">" + endl;
    }

    private String toXmlChildren(Indent indent, String endl) {
        return asXmlNode(MetaConstants.DATE, lastEditingDate) + endl
                + indent + asXmlNode(MetaConstants.EDITING_CYCLE, editingCycles) + endl
                + indent + asXmlNode(MetaConstants.EDITING_DURATION, editingDuration) + endl
                + indent + asXmlNode(MetaConstants.GENERATOR, generator) + endl
                + indent + stats.toXml(indent, endl)
                + indent + XmlNode.toStringCollection(otherXml, false) + endl;
    }

    private String asXmlNode(String nodeName, Object content) {
        if(content == null || content.toString().isEmpty())
            return "<" + nodeName + "/>";
        return "<" + nodeName + ">" + content + "</" + nodeName + ">";
    }

    public void applyToRealXml() {
        String xml = toXmlPrefix() + ">"
                + XmlNode.toStringCollection(otherXml, false)
                + toXmlChildren(new Indent(), "")
                + "</"+getNodeName()+">";
        getNodeReference().forceRawWriting(xml);
    }

    private void fromNode(XmlNode node) {
        switch (node.getName()) {
            case MetaConstants.DATE -> lastEditingDate = LocalDateTime.parse(node.getTextContent());
            case MetaConstants.EDITING_DURATION -> editingDuration = Duration.parse(node.getTextContent());
            case MetaConstants.EDITING_CYCLE -> editingCycles = Integer.parseInt(node.getTextContent());
            case MetaConstants.GENERATOR -> generator = node.getTextContent();
            case MetaConstants.DOCUMENT_STATISTICS -> stats = new OpenDocumentStatistics(node);
            default -> otherXml.add(node);
        }
    }

    @Override
    public String toString() {
        return "OpenDocumentMeta{" +
                "lastEditingDate=" + lastEditingDate +
                ", editingDuration=" + editingDuration +
                ", editingCycles=" + editingCycles +
                ", stats=" + stats
                + "}";
    }
}
