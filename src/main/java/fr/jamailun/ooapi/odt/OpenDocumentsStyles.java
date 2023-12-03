package fr.jamailun.ooapi.odt;

import fr.jamailun.ooapi.common.constants.StyleConstants;
import fr.jamailun.ooapi.odt.style.Style;
import fr.jamailun.ooapi.xml.XmlNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class OpenDocumentsStyles {

    private final Map<String, Style> automaticStyles = new HashMap<>();

    public void setAutomaticStyles(XmlNode node) {
        for(XmlNode styleNode : node) {
            assert styleNode.getName().equals(StyleConstants.NODE_STYLE);
            Style style = new Style(styleNode);
            automaticStyles.put(style.getName(), style);
        }
    }

    public Style getStyle(String name) {
        return automaticStyles.get(name);
    }

    public Collection<Style> getStyles() {
        return automaticStyles.values();
    }

    @Override
    public String toString() {
        StringJoiner styles = new StringJoiner("\n\t- ");
        for(Style style : automaticStyles.values()) {
            styles.add(style.toString());
        }
        return "OOD-Styles{\n"
                + "\t- " + styles
                +"\n}";
    }
}
