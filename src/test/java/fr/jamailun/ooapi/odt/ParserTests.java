package fr.jamailun.ooapi.odt;

import static org.junit.jupiter.api.Assertions.*;

import fr.jamailun.ooapi.odt.style.Style;
import fr.jamailun.ooapi.odt.text.CoreTextNode;
import fr.jamailun.ooapi.odt.text.ParagraphNode;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.URL;

public class ParserTests {

    private URL readOdt(String name) {
        URL url = getClass().getClassLoader().getResource("odt/"+name+".odt");
        assertNotNull(url);
        return url;
    }

    @Test
    void testBasic() throws IOException {
        OpenDocument document =  OpenDocumentParser.parse(readOdt("basic"));
        CoreTextNode root = document.getContentRoot();

        System.out.println(document.getStyles());
        System.out.println(document.getContentRoot().toXml(true));

        for(int i = 1; i < root.getChildrenCount(); i++) {
            OdtNode child = root.children.get(i);
            if(child instanceof ParagraphNode p) {
                String styleName = p.getStyleName();
                assertNotNull(styleName);
                Style style = document.getStyles().getStyle(styleName);
                assertNotNull(style);
                continue;
            }
            fail("Node " + child + " is NOT a ParagraphNode.");
        }

    }

}
