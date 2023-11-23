package com.workana.breakify.shared;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Strings {

    public static final String EMPTY = "";
    private static final Pattern STRIP_ACCENTS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private Strings() {

    }

    public static String stripAccents(final String value) {
        return stripAccents(value, STRIP_ACCENTS_PATTERN);
    }

    private static String stripAccents(final String value,
                                       final Pattern pattern) {
        if (value == null) {
            return null;
        }
        var normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        var newValue = new StringBuilder(normalized);
        for (int i = 0; i < newValue.length(); i++) {
            var currentChar = newValue.charAt(i);
            if (currentChar == '\u0141') {
                newValue.setCharAt(i, 'L');
            } else if (currentChar == '\u0142') {
                newValue.setCharAt(i, 'l');
            }
        }
        var finalValue = pattern.matcher(newValue)
                .replaceAll(EMPTY);
        return Normalizer.normalize(finalValue, Normalizer.Form.NFC);

    }
}
