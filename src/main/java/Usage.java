import fr.jamailun.jamLogger.JamLogger;
import fr.jamailun.ooapi.odt.OpenDocument;
import fr.jamailun.ooapi.odt.OpenDocumentParser;

import java.io.IOException;
import java.net.URL;

public class Usage {

    public static void main(String[] args) throws IOException {
        URL url = Usage.class.getClassLoader().getResource("odt/basic.odt");
        OpenDocument document = OpenDocumentParser.parse(url);

        JamLogger.info("\n"+document.getContentRoot().toXml(true));
    }

}
