package fr.jamailun.ooapi.odt;

import fr.jamailun.ooapi.utils.StringUtils;
import fr.jamailun.ooapi.xml.XmlAttribute;
import fr.jamailun.ooapi.xml.XmlAttributesMap;
import fr.jamailun.ooapi.xml.XmlNode;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class OdtProperties {

    private final Map<String, String> values = new HashMap<>();

    public OdtProperties() {}
    public OdtProperties(XmlNode node) {from(node);}

    public void from(XmlNode node) {
        for(XmlAttribute attribute : node.getAttributes()) {
            values.put(attribute.name(), attribute.value());
        }
    }

    public void from(String name, XmlNode node) {
        from(name, node.getAttributes());
    }

    public void from(String name, XmlAttributesMap xmlAttributes) {
        values.put(name, xmlAttributes.get(name));
    }

    public String get(String name) {
        return values.get(name);
    }

    public boolean has(String name) {
        return values.containsKey(name);
    }

    public void set(String name, String value) {
        if(value == null || value.isEmpty() || value.isBlank())
            values.remove(name);
        else
            values.put(name, value);
    }

    public String toXml() {
        StringJoiner sj = new StringJoiner(" ");
        for(Map.Entry<String, String> entry : values.entrySet()) {
            if(entry.getValue() != null)
                sj.add(entry.getKey() + "=\"" + StringUtils.escape(entry.getValue()) + "\"");
        }
        String str = sj.toString();
        return (str.isEmpty() ? "" : " " + str);
    }

    @Override
    public String toString() {
        return "ODProperties{" + values + '}';
    }

}
