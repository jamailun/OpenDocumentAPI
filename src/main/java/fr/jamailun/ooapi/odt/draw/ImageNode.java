package fr.jamailun.ooapi.odt.draw;

import fr.jamailun.ooapi.common.HReferable;
import fr.jamailun.ooapi.common.Mimetype;
import fr.jamailun.ooapi.common.constants.DrawConstants;
import fr.jamailun.ooapi.common.constants.XLinkConstants;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.odt.OpenDocument;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.xml.XmlNode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageNode extends OdtNode implements HReferable {

    public final static String XML_NAME = "draw:image";

    public ImageNode(XmlNode node) {
        super(node);
    }

    public String getImageType() {
        return properties.get(XLinkConstants.TYPE);
    }
    public void setImageType(String imageType) {
        properties.set(XLinkConstants.TYPE, imageType);
    }
    public String getMimetype() {
        return properties.get(DrawConstants.MIMETYPE);
    }
    public void setMimetype(String mimetype) {
        properties.set(DrawConstants.MIMETYPE, mimetype);
    }

    @Override
    public String getHref() {
        return properties.get(XLinkConstants.HREF);
    }
    public void setHref(String href) {
        properties.set(XLinkConstants.HREF, href);
    }
    public String getImageName() {
        String[] tokens = getHref().split("/");
        return tokens[tokens.length - 1];
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return indent + "<" + XML_NAME + properties.toXml() + "/>" + endl;
    }

    public void exportImage(OpenDocument document, String output) throws IOException {
        ByteArrayInputStream data = document.getHref(this);
        BufferedImage bImage2 = ImageIO.read(data);
        String extension;
        if(output.contains(".")) {
            String[] tokens = output.split("\\.");
            extension = tokens[tokens.length - 1];
        } else {
            extension = Mimetype.findExtension(getMimetype());
        }
        ImageIO.write(bImage2, extension, new File(output + "." + extension) );
    }

    @Override
    public String toString() {
        return "ImageNode{href="+getHref()+"}";
    }

}
