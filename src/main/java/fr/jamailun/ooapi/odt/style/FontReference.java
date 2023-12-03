package fr.jamailun.ooapi.odt.style;

import fr.jamailun.ooapi.common.XmlConvertible;
import fr.jamailun.ooapi.common.constants.StyleConstants;
import fr.jamailun.ooapi.odt.OdtProperties;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

public class FontReference implements XmlConvertible {
    private final OdtProperties properties;

    public FontReference(XmlNode xmlNode) {
        properties = new OdtProperties(xmlNode);
    }

    public String getName() {
        return properties.get(StyleConstants.NAME);
    }

    public String getFamily() {
        return properties.get(StyleConstants.FONT_FAMILY);
    }

    public String getFamilyGeneric() {
        return properties.get(StyleConstants.FONT_FAMILY_GENERIC);
    }

    @Override
    public String getXmlNodeName() {
        return StyleConstants.NODE_FONT;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return "<" + getXmlNodeName() + properties.toXml() + "/>";
    }
}
