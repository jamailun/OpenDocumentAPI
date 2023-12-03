package fr.jamailun.ooapi.odt.style;

import fr.jamailun.ooapi.common.XmlConvertible;
import fr.jamailun.ooapi.common.constants.StyleConstants;
import fr.jamailun.ooapi.odt.OdtProperties;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Style implements XmlConvertible {
    private final OdtProperties selfProperties;

    private final Map<String, OdtProperties> properties = new HashMap<>();

    public Style(XmlNode xmlNode) {
        selfProperties = new OdtProperties(xmlNode);

        for(XmlNode child : xmlNode) {
            properties.put(child.getName(), new OdtProperties(child));
        }
    }

    public String getName() {
        return selfProperties.get(StyleConstants.NAME);
    }

    public String getParentName() {
        return selfProperties.get(StyleConstants.PARENT_NAME);
    }

    public String getFamily() {
        return selfProperties.get(StyleConstants.FAMILY);
    }

    public OdtProperties getProperties(String propertiesType) {
        return properties.get(propertiesType);
    }

    /**
     * Lookup for a specific property in the whole style.
     * If it's defined multiple time (should not be the case) will return the first occurrence.
     * @param property the name of the property to find.
     * @return null if the name was not found.
     */
    public String findProperty(String property) {
        if(selfProperties.has(property)) {
            return selfProperties.get(property);
        }
        for(OdtProperties properties : this.properties.values()) {
            if(properties.has(property)) return properties.get(property);
        }
        return null;
    }

    //

    public OdtProperties getTextProperties() {
        return getProperties(StyleConstants.PROPERTIES_TEXT);
    }

    @Override
    public String getXmlNodeName() {
        return StyleConstants.NODE_STYLE;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        StringBuilder sb = new StringBuilder("<")
                .append(getXmlNodeName())
                .append(selfProperties.toXml())
                .append(">")
                .append(endl);

        indent.add();
        for(Map.Entry<String, OdtProperties> child : properties.entrySet()) {
            sb.append(indent)
                    .append(child.getKey())
                    .append(child.getValue().toXml())
                    .append("/>")
                    .append(endl);
        }
        indent.remove();

        return sb.append("</")
                .append(getXmlNodeName())
                .append(">")
                .toString();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        sj.add(selfProperties.toXml());
        for(Map.Entry<String, OdtProperties> p : properties.entrySet()) {
            sj.add(p.getKey()+":{" + p.getValue().toXml()+"}");
        }
        return getName() + "{" + sj + "}";
    }
}
