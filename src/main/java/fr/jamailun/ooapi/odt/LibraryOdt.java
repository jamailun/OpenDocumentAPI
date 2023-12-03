package fr.jamailun.ooapi.odt;

import fr.jamailun.jamLogger.JamLogger;
import fr.jamailun.ooapi.common.BadClassFormatException;
import fr.jamailun.ooapi.odt.special.IgnoredOdtNode;
import fr.jamailun.ooapi.odt.special.UnknownOdtNode;
import fr.jamailun.ooapi.odt.table.TableCellNode;
import fr.jamailun.ooapi.odt.table.TableColumnNode;
import fr.jamailun.ooapi.odt.table.TableNode;
import fr.jamailun.ooapi.odt.table.TableRowNode;
import fr.jamailun.ooapi.odt.text.*;
import fr.jamailun.ooapi.odt.draw.*;
import fr.jamailun.ooapi.xml.XmlNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LibraryOdt {

    private final static Set<String> ignored = new HashSet<>();
    private final static Map<String, Constructor<? extends OdtNode>> constructors = new HashMap<>();

    // REGISTER DEFAULT NODES
    static {
        try {
            // TEXT
            registerClass(ParagraphNode.class);
            registerClass(SequenceNode.class);
            registerClass(SpanNode.class);
            registerClass(TabNode.class);

            // DRAW
            registerClass(FrameNode.class);
            registerClass(ImageNode.class);
            registerClass(TextBoxNode.class);

            // TABLE
            registerClass(TableNode.class);
            registerClass(TableColumnNode.class);
            registerClass(TableRowNode.class);
            registerClass(TableCellNode.class);
            registerClass(TableCellNode.CoveredCellNode.class);

        } catch(BadClassFormatException e) {
            JamLogger.error("FATAL on loading default extension : " + e.getMessage());
        }
    }

    // REGISTER IGNORED ONES
    static {
        addToIgnored("text:sequence-decls");
    }

    public static <T extends OdtNode> void registerClass(Class<T> clazz) throws BadClassFormatException {
        registerClass(null, clazz);
    }

    public static <T extends OdtNode> void registerClass(String xmlName, Class<T> clazz) throws BadClassFormatException {
        Constructor<T> constructor;
        try {
            constructor = clazz.getConstructor(XmlNode.class);
        } catch(NoSuchMethodException e) {
            throw new BadClassFormatException(clazz, "Could not find 'XmlNode' based on default constructor.");
        }

        //XML_NAME constructor
        if(xmlName == null) {
            try {
                Field f = clazz.getDeclaredField("XML_NAME");
                xmlName = (String) f.get(null);
            } catch(NoSuchFieldException | IllegalAccessException | IllegalArgumentException | ClassCastException e) {
                throw new BadClassFormatException(clazz, "Could not find 'XmlNode' based on default constructor.");
            }
        }

        if(constructors.containsKey(xmlName)) {
            JamLogger.warning("XML-NAME '" + xmlName + "' already has been defined as a node. Overwriting it.");
        }

        constructors.put(xmlName, constructor);
        ignored.remove(xmlName);
    }

    public static Constructor<? extends OdtNode> getConstructor(String xmlName) {
        return constructors.get(xmlName);
    }

    public static OdtNode create(XmlNode xmlNode) {
        if(isIgnored(xmlNode.getName())) {
            return new IgnoredOdtNode(xmlNode);
        }
        if(!containsNodeName(xmlNode)) {
            return new UnknownOdtNode(xmlNode);
        }

        try {
            return getConstructor(xmlNode.getName()).newInstance(xmlNode);
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            JamLogger.error("c'est mal codé");
            throw new RuntimeException("Weird error. " + e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

    public static boolean contains(String xmlName) {
        return constructors.containsKey(xmlName);
    }

    public static boolean containsNodeName(XmlNode xmlNode) {
        return contains(xmlNode.getName());
    }

    public static boolean isIgnored(String xmlName) {
        return ignored.contains(xmlName);
    }

    public static void addToIgnored(String xmlName) {
        if(constructors.containsKey(xmlName)) {
            JamLogger.error("Cannot add '" + xmlName + "' to ignored. It already has been defined as a node.");
            return;
        }
        ignored.add(xmlName);
    }

}
