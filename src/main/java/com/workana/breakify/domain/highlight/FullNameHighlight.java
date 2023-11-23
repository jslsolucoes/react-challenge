package com.workana.breakify.domain.highlight;

import java.util.List;

public interface FullNameHighlight {

    boolean accepts(final FullNameHighlightContext fullNameHighligherContext);

    FullNameHighlightResult highlight(final FullNameHighlightContext fullNameHighligherContext);

    record FullNameHighlightContext(String firstName, String lastName) {
    }

    record FullNameHighlightResult(FullNameWordHighlight firstName, FullNameWordHighlight lastName) {
        public record FullNameWordHighlight(String word, List<WordHighlightRange> ranges) {

            public record WordHighlightRange(String substring,
                                             int startIndex,
                                             int endIndex,
                                             PeriodicElement periodicElement) {

                public String periodicElementSymbol() {
                    return periodicElement.symbol();
                }

                public int periodicElementAtomicNumber() {
                    return periodicElement.atomicNumber();
                }

                public boolean isPeriodicElementUnknown() {
                    return periodicElement.isUnknown();
                }
            }
        }
    }
}
