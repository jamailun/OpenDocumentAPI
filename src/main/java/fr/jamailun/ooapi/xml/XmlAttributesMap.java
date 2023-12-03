package fr.jamailun.ooapi.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

public final class XmlAttributesMap implements Iterable<XmlAttribute> {

    private final Map<String, String> attributes;

    XmlAttributesMap() {
        attributes = new HashMap<>();
    }

    void add(Iterable<XmlAttribute> attributes) {
        for(XmlAttribute attribute : attributes)
            add(attribute);
    }

    void add(XmlAttribute attribute) {
        attributes.put(attribute.name(), attribute.value());
    }

    public boolean contains(String name) {
        return attributes.containsKey(name);
    }

    public String get(String name) {
        return attributes.get(name);
    }

    public String getOrDefault(String name, String defaultValue) {
        return attributes.getOrDefault(name, defaultValue);
    }

    public int size() {
        return attributes.size();
    }

    @Override
    public Iterator<XmlAttribute> iterator() {
        return attributes.entrySet().stream()
                .map(e -> new XmlAttribute(e.getKey(), e.getValue()))
                .iterator();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        for(XmlAttribute attribute : this) {
            sj.add(attribute.toString());
        }
        return sj.toString();
    }
}