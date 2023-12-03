package fr.jamailun.ooapi.odt;

import fr.jamailun.ooapi.common.HReferable;
import fr.jamailun.ooapi.common.constants.StyleConstants;
import fr.jamailun.ooapi.odt.meta.OpenDocumentMeta;
import fr.jamailun.ooapi.odt.text.CoreTextNode;
import fr.jamailun.ooapi.xml.XmlDocument;
import fr.jamailun.ooapi.xml.XmlNode;
import fr.jamailun.ooapi.xml.parsing.MalformedXmlException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class OpenDocument {

    public static final String MIMETYPE = "application/vnd.oasis.opendocument.text";
    public static final String ENTRY_CONTENT = "content.xml";
    public static final String ENTRY_STYLES = "styles.xml";
    public static final String ENTRY_META = "meta.xml";

    private final ZipFile zipFile;

    private final CoreTextNode contentNode;
    private final OpenDocumentMeta metaNode;
    private final OpenDocumentsStyles stylesLibrary = new OpenDocumentsStyles();

    private final XmlDocument styles;
    private final XmlDocument content;
    private final XmlDocument meta;

    public OpenDocument(ZipFile zipFile, XmlDocument content, XmlDocument styles, XmlDocument meta) {
        assert zipFile != null : "ZipFile cannot be null.";
        this.zipFile = zipFile;
        this.content = content;
        this.styles = styles;
        this.meta = meta;

        XmlNode contentBody = content.getRoot().getFirstChild("office:body");
        if(contentBody == null)
            throw new MalformedXmlException("Could not get <office:body> of content in root: " + content.getRoot());

        // Styles
        XmlNode automaticStyles = content.getRoot().getFirstChild(StyleConstants.NODE_STYLES_HOLDER);
        if(automaticStyles == null)
            throw new MalformedXmlException("Could not get <"+StyleConstants.NODE_STYLES_HOLDER+"> of in root:" + content.getRoot());
        stylesLibrary.setAutomaticStyles(automaticStyles);

        // Content
        XmlNode text = contentBody.getFirstChild(CoreTextNode.XML_NAME);
        if(text == null)
            throw new MalformedXmlException("The body of the CONTENT doesn't contain any <"+CoreTextNode.XML_NAME+">.");
        contentNode = new CoreTextNode(text);

        // Metadata
        XmlNode metaXml = meta.getRoot().getFirstChild(OpenDocumentMeta.XML_NAME);
        if(metaXml == null)
            throw new MalformedXmlException("The body of the META doesn't contain any <"+OpenDocumentMeta.XML_NAME+">.");
        metaNode = new OpenDocumentMeta(metaXml);
    }

    public <T extends OdtNode> List<T> getAllOfType(Class<T> clazz) {
        return contentNode.collectAllChildren(n -> clazz.isAssignableFrom(n.getClass()));
    }
    public List<? extends OdtNode> getAllOfType(String xmlType) {
        return contentNode.collectAllChildren(n -> n.getNodeName().equals(xmlType));
    }

    public ByteArrayInputStream getHref(HReferable node) throws IOException {
        return getHref(node.getHref());
    }

    public ByteArrayInputStream getHref(String href) throws IOException {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();
            if(name.equals(href)) {
                return new ByteArrayInputStream(zipFile.getInputStream(entry).readAllBytes());
            }
        }
        throw new IllegalArgumentException("Could not find the href '"+href+"'. Is the archive valid ?");
    }

    public CoreTextNode getContentRoot() {
        return contentNode;
    }

    public OpenDocumentMeta getMeta() {
        return metaNode;
    }

    private byte[] getBytes(ZipEntry zipEntry) throws IOException {
        return switch (zipEntry.getName()) {
            case ENTRY_CONTENT -> content.getRoot().niceString(false).getBytes(StandardCharsets.UTF_8);
            case ENTRY_STYLES -> styles.getRoot().niceString(false).getBytes(StandardCharsets.UTF_8);
            case ENTRY_META -> meta.getRoot().niceString(false).getBytes(StandardCharsets.UTF_8);
            default -> zipFile.getInputStream(zipEntry).readAllBytes();
        };
    }

    public void applyAllChanges() {
        contentNode.applyToRealXml();
        metaNode.applyToRealXml();
    }

    public File recreateFile(String path) throws IOException {
        // Get IO file
        File outputFile = new File(path);
        if(!outputFile.exists()) {
            if (!outputFile.createNewFile())
                throw new RuntimeException("Could not create file " + outputFile);
        }
        // copy all entries
        Enumeration<? extends ZipEntry> currentEntries = zipFile.entries();
        try(ZipOutputStream output = new ZipOutputStream(new FileOutputStream(outputFile))) {
            while(currentEntries.hasMoreElements()) {
                ZipEntry oldEntry = currentEntries.nextElement();
                ZipEntry entry = new ZipEntry(oldEntry.getName());
                output.putNextEntry(entry);

                byte[] data = getBytes(oldEntry);
                output.write(data, 0, data.length);

                output.closeEntry();
            }
        }
        return outputFile;
    }

    @Override
    public String toString() {
        return "OpenDocument{\""+zipFile.getName()+"\"}";
    }

    public OpenDocumentsStyles getStyles() {
        return stylesLibrary;
    }
}