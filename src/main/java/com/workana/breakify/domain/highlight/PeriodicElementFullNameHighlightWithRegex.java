package com.workana.breakify.domain.highlight;

import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight;
import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange;
import com.workana.breakify.shared.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PeriodicElementFullNameHighlightWithRegex implements FullNameHighlight {

    private static final Pattern PERIODIC_SYMBOLS_PATTERN;

    static {
        var symbols = Arrays.stream(PeriodicElement.values())
                .filter(Predicate.not(PeriodicElement::isUnknown))
                .sorted(Comparator.comparing(PeriodicElement::symbolLength).reversed())
                .map(PeriodicElement::symbol)
                .map(String::toLowerCase)
                .collect(Collectors.joining("|"));
        PERIODIC_SYMBOLS_PATTERN = Pattern.compile("(" + symbols + ")", Pattern.CASE_INSENSITIVE);
    }

    private final String strategy;

    @Autowired
    public PeriodicElementFullNameHighlightWithRegex(@Value("${breakify.highlight.strategy}") final String strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean accepts(final FullNameHighlightContext fullNameHighligherContext) {
        return "regex".equalsIgnoreCase(strategy);
    }

    /*
     * Works in polinomial time but it's not the best solution because cant detect overlapping periodic elements
     * like in "fernanda" since does not matches ("f","fe","er","rn") instead it matches only most right symbol ("fe","rn")
     */
    @Override
    public FullNameHighlightResult highlight(final FullNameHighlightContext fullNameHighligherContext) {

        var firstName = fullNameHighligherContext.firstName();
        var lastName = fullNameHighligherContext.lastName();

        var firstNameFullNameWordHighlight = highlightForWord(firstName);
        var lastNameFullNameWordHighlight = highlightForWord(lastName);

        return new FullNameHighlightResult(firstNameFullNameWordHighlight, lastNameFullNameWordHighlight);
    }

    private FullNameWordHighlight highlightForWord(final String word) {
        var newWord = Strings.stripAccents(word.toLowerCase());
        var matcher = PERIODIC_SYMBOLS_PATTERN.matcher(newWord);
        var wordHighlightRanges = new ArrayList<WordHighlightRange>();
        while (matcher.find()) {
            var periodicElement = matcher.group();
            var startIndex = matcher.start();
            var endIndex = matcher.end();
            var newWordHighlightRange = new WordHighlightRange(
                    periodicElement,
                    startIndex,
                    endIndex,
                    PeriodicElement.forSymbol(periodicElement)
            );
            wordHighlightRanges.add(newWordHighlightRange);
        }
        return new FullNameWordHighlight(word, wordHighlightRanges);
    }
}
