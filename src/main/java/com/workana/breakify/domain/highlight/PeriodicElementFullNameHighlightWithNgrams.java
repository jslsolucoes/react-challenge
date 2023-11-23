package com.workana.breakify.domain.highlight;

import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight;
import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange;
import com.workana.breakify.domain.nlp.Ngram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class PeriodicElementFullNameHighlightWithNgrams implements FullNameHighlight {

    private final Ngram ngram;

    private final String strategy;

    @Autowired
    public PeriodicElementFullNameHighlightWithNgrams(final Ngram ngram,
                                                      @Value("${breakify.highlight.strategy}") final String strategy) {
        this.ngram = ngram;
        this.strategy = strategy;
    }

    @Override
    public boolean accepts(final FullNameHighlightContext fullNameHighligherContext) {
        return "ngrams".equalsIgnoreCase(strategy);
    }

    /*
     * Works in O(n) time complexity and space
     */
    @Override
    public FullNameHighlightResult highlight(final FullNameHighlightContext fullNameHighligherContext) {
        var firstName = fullNameHighligherContext.firstName();
        var lastName = fullNameHighligherContext.lastName();

        var firstFullNameWordHighlight = highlightForWord(firstName);
        var lastFullNameWordHighlight = highlightForWord(lastName);

        return new FullNameHighlightResult(firstFullNameWordHighlight, lastFullNameWordHighlight);
    }


    private FullNameWordHighlight highlightForWord(final String word) {
        var symbolsOfPeriodicTable = PeriodicElement.symbolsAsMap();
        var ngrams = Stream.of(
                        ngram.ngramOf(word, 1),
                        ngram.ngramOf(word, 2)
                )
                .flatMap(List::stream)
                .toList();

        var ranges = ngrams.stream()
                .map(ngram -> {
                    var periodicElement = symbolsOfPeriodicTable
                            .getOrDefault(ngram.substring(), PeriodicElement.UNKNOWN);
                    return new WordHighlightRange(ngram.substring(), ngram.startIndex(), ngram.endIndex(), periodicElement);
                })
                .filter(Predicate.not(WordHighlightRange::isPeriodicElementUnknown))
                .map(whr -> new WordHighlightRange(
                        whr.substring(),
                        whr.startIndex(),
                        whr.endIndex(),
                        whr.periodicElement()
                ))
                .toList();

        return new FullNameWordHighlight(word, ranges);
    }
}
