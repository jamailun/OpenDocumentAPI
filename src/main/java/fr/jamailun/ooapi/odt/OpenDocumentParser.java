package fr.jamailun.ooapi.odt;

import fr.jamailun.jamLogger.JamLogger;
import fr.jamailun.ooapi.utils.StringUtils;
import fr.jamailun.ooapi.xml.XmlDocument;
import fr.jamailun.ooapi.xml.XmlParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OpenDocumentParser {
    private OpenDocumentParser() {}

    private static final String MIMETYPE = "mimetype";
    private static final List<String> LOOKED_FILES = List.of(OpenDocument.ENTRY_CONTENT, OpenDocument.ENTRY_STYLES, OpenDocument.ENTRY_META);

    public static OpenDocument parse(String filePath) throws IOException {
        assert filePath != null : "Cannot parse a null path.";
        return parse(new File(filePath));
    }

    public static OpenDocument parse(File file) throws IOException {
        assert file != null : "Cannot parse a null file.";
        assert file.exists() : "The file must exist in order to be parsed.";
        return parse(new ZipFile(file));
    }

    public static OpenDocument parse(URL url) throws IOException {
        if(url == null) throw new IOException("Null URL");
        try {
            return parse(new File(url.toURI()));
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public static OpenDocument parse(ZipFile zipFile) throws IOException {
        assert zipFile != null : "ZipFile cannot be null.";
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        XmlDocument content = null, styles = null, meta = null;
        boolean typeChecked = false;

        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();

            if(name.equals(MIMETYPE)) {
                String type = new String(zipFile.getInputStream(entry).readAllBytes());
                if(type.equals(OpenDocument.MIMETYPE)) {
                    typeChecked = true;
                } else {
                    throw new IllegalArgumentException("The mimetype is not correct for file " + zipFile.getName()+". Expected " + OpenDocument.MIMETYPE + ". Got '"+type+"'.");
                }
            }
            else if(LOOKED_FILES.contains(entry.getName())) {
                JamLogger.log("Parsing entry '" + entry.getName()+"'.");
                XmlDocument document = XmlParser.parse(zipFile.getInputStream(entry));
                switch (entry.getName()) {
                    case OpenDocument.ENTRY_CONTENT -> content = document;
                    case OpenDocument.ENTRY_STYLES -> styles = document;
                    case OpenDocument.ENTRY_META -> meta = document;
                }
            }
        }

        if(content == null || styles == null || meta == null) {
            throw new IllegalArgumentException("Incorrect open document file. Some inner-files are missing: "
                    + StringUtils.toStringWhenNull(
                    content, OpenDocument.ENTRY_CONTENT,
                    styles, OpenDocument.ENTRY_STYLES,
                    meta, OpenDocument.ENTRY_META
            )
            );
        }
        if(!typeChecked)
            throw new IllegalArgumentException("Incorrect open document file. Mimetype is missing.");

        return new OpenDocument(zipFile, content, styles, meta);
    }

}