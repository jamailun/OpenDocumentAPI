package fr.jamailun.ooapi.odt.meta;
import fr.jamailun.ooapi.common.constants.MetaConstants;
import fr.jamailun.ooapi.odt.OdtNode;
import fr.jamailun.ooapi.utils.Indent;
import fr.jamailun.ooapi.utils.StringUtils;
import fr.jamailun.ooapi.xml.XmlNode;

public class OpenDocumentStatistics extends OdtNode {

    public final static String XML_NAME = MetaConstants.DOCUMENT_STATISTICS;

    public OpenDocumentStatistics(XmlNode node) {
        super(node);
    }

    @Override
    public String getNodeName() {
        return XML_NAME;
    }

    public int getParagraphsCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_PARAGRAPHS));
    }
    public int getWordsCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_WORDS));
    }
    public int getCharactersCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_CHARACTERS));
    }
    public int getNonWhiteCharactersCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_NON_WHITESPACE_CHARACTERS));
    }
    public int getPagesCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_PAGES));
    }
    public int getImagesCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_IMAGES));
    }
    public int getObjectsCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_OBJECTS));
    }
    public int getTablesCount() {
        return StringUtils.toIntOrZero(properties.get(MetaConstants.COUNT_TABLES));
    }

    @Override
    public String toXml(Indent indent, String endl) {
        return indent + toXmlPrefix() + "/>" + endl;
    }

    @Override
    public String toString() {
        return "DocumentStatistics{"+properties.toXml()+"}";
    }
}
