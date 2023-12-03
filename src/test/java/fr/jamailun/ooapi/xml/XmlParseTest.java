package fr.jamailun.ooapi.xml;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class XmlParseTest {
    @Test
    void testXmlParsing() {
        XmlDocument document;
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("test.xml")) {
            assertNotNull(is);
            document = XmlParser.parse(is);
        } catch (IOException e) {
            fail(e);
            return;
        }

        // metadata
        assertEquals(2, document.getMeta().size());

        // Root
        XmlNode root = document.getRoot();
        assertEquals(3, root.getChildrenCount());
        assertEquals("test", root.getName());

        // get none
        assertNull(root.getFirstChild("xxx"));

        // get <name>
        XmlNode nodeName = root.getChild(0);
        assertNotNull(nodeName);
        assertEquals(0, nodeName.getAttributes().size());
        assertEquals(nodeName, root.getFirstChild("name"));
        assertEquals(nodeName.getTextContent(), "Hello guys");

        // <values>
        XmlNode nodeValues = root.getChild(1);
        assertEquals(nodeValues, root.getFirstChild("values"));
        assertEquals(2, nodeValues.getChildrenCount());

        // <values> <value>[]
        for(XmlNode child : nodeValues.getChildren()) {
            assertEquals("value", child.getName());
            // meta
            assertEquals(1, child.getAttributes().size());

            // Children
            assertEquals(2, child.getChildrenCount());
            XmlNode childId = child.getFirstChild("id");
            assertDoesNotThrow(childId::getTextAsInt);
            XmlNode childProcess = child.getFirstChild("process");
            assertEquals(3, childProcess.getChildrenCount());
        }

    }

}
