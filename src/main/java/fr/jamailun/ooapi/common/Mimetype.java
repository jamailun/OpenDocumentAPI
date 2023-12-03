package fr.jamailun.ooapi.common;

import java.util.Arrays;

public enum Mimetype {

    TEXT_PLAIN("text/plain", "txt"),

    TEXT_CSS("text/css", "css"),
    TEXT_CSV("text/csv", "csv"),
    TEXT_XML("text/xml", "xml"),
    TEXT_HTML("text/html", "html"),
    TEXT_ICS("text/calendar", "ics"),

    IMG_BMP("image/bmp", "bmp"),
    IMG_GIF("image/gif", "gif"),
    IMG_ICO("image/x-icon", "ico"),
    IMG_JPEG("image/jpeg", "jpeg"),
    IMG_PNG("image/png", "png"),
    IMG_SVG("image/svg+xml", "svg"),
    IMG_TIFF("image/tiff", "tiff"),
    IMG_WEBP("image/webp", "webp"),

    APP_JSON("application/json", "json"),
    APP_OGG("application/ogg", "ogg"),
    APP_PDF("application/pdf", "pdf"),
    APP_RAR("application/x-rar-compressed", "rar"),
    APP_RTF("application/rtf", "rtf"),

    UNKNOWN("", "")
    ;

    public static Mimetype from(String mimetype) {
        return Arrays.stream(values())
                .filter(m -> m.getName().equals(mimetype))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static String findExtension(String mimetype) {
        Mimetype type = from(mimetype);
        if(type == UNKNOWN) {
            String[] tokens = mimetype.split("/", 2);
            return tokens[tokens.length - 1];
        }
        return type.getExtension();
    }

    private final String name, extension;
    Mimetype(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }
}
