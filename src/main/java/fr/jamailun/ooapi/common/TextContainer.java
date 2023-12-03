package fr.jamailun.ooapi.common;

import fr.jamailun.ooapi.utils.StringUtils;

public interface TextContainer {

    String getText();

    void setText(String value);

    default int countOccurrences(String str) {
        return StringUtils.findOccurrences(getText(), str);
    }

    default void replace(String toFind, String replaceWith) {
        setText(getText().replace(toFind, replaceWith));
    }

}