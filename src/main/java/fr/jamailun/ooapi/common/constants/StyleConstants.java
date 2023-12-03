package fr.jamailun.ooapi.common.constants;

public class StyleConstants {

    public static final String NODE_STYLES_HOLDER = "office:automatic-styles";
    public static final String NODE_STYLE = "style:style";
    public static final String NODE_FONT_HOLDER = "office:font-face-decls";
    public static final String NODE_FONT = "style:font-face";

    // Main-style
    public static final String NAME = "style:name";
    public static final String PARENT_NAME = "style:parent-style-name";
    public static final String NUM_FORMAT = "style:num-format";
    public static final String FAMILY = "style:family";

    // Sub-properties
    public static final String PROPERTIES_TEXT = "style:text-properties";

    // Font
    public static final String FONT_NAME = "style:font-name";
    public static final String FONT_FAMILY = SvgConstants.FONT_FAMILY;
    public static final String FONT_FAMILY_GENERIC = "style:font-family-generic";
    public static final String FONT_PITCH = "style:font-pitch";

    // Paragraph-properties
    public static final String TEXT_AUTOSPACE = "style:text-autospace";
    public static final String PUNCTUATION_WRAP = "style:punctuation-wrap";
    public static final String LINE_BREAK = "style:line-break";
    public static final String WRITING_MODE = "style:writing-mode";

    // Text-properties
    public static final String TEXT_COLOR = "fo:color";
    public static final String TEXT_OPACITY = "loext:opacity";
    public static final String FONT_SIZE = "fo:font-size";
    public static final String COUNTRY = "fo:country";
    public static final String LANGUAGE = "fo:language";
}
