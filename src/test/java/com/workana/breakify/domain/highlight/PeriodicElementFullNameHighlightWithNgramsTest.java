package com.workana.breakify.domain.highlight;

import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightContext;
import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange;
import com.workana.breakify.junit.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


class PeriodicElementFullNameHighlightWithNgramsTest extends AbstractIntegrationTest {

    @Autowired
    private PeriodicElementFullNameHighlightWithNgrams periodicElementFullNameHighlightWithNgrams;


    @Test
    @DisplayName("should accepts on highlight config with ngrams")
    void shouldAcceptsOnHighlightConfigWithNgrams() {

        var firstName = "fernanda";
        var lastName = "carapinheiro";

        var fullNameHighlightContext = createFullNameHighlightContext(firstName, lastName);
        var accepts = periodicElementFullNameHighlightWithNgrams.accepts(fullNameHighlightContext);
        assertTrue(accepts);
    }

    @Test
    @DisplayName("should return full name with correctly highlight result filtering unknown periodic element")
    void shouldReturnFullNameWithCorrectlyHighlightResult() {

        var firstName = "jonatan";
        var lastName = "lemes";

        var fullNameHighlightContext = createFullNameHighlightContext(firstName, lastName);
        var result = periodicElementFullNameHighlightWithNgrams.highlight(fullNameHighlightContext);

        var firstNameFullNameWordHighlight = result.firstName();
        var firstNameFullNameWordHighlightRanges = firstNameFullNameWordHighlight.ranges();
        var firstNameFullNameWordHighlightRangeExpected = List.of(
                new WordHighlightRange("o", 1, 2, PeriodicElement.OXYGEN),
                new WordHighlightRange("n", 2, 3, PeriodicElement.NITROGEN),
                new WordHighlightRange("n", 6, 7, PeriodicElement.NITROGEN),
                new WordHighlightRange("na", 2, 4, PeriodicElement.SODIUM),
                new WordHighlightRange("at", 3, 5, PeriodicElement.ASTATINE),
                new WordHighlightRange("ta", 4, 6, PeriodicElement.TANTALUM)
        );

        var lastNameFullNameWordHighlight = result.lastName();
        var lastNameFullNameWordHighlightRanges = lastNameFullNameWordHighlight.ranges();
        var lastNameFullNameWordHighlightRangeExpected = List.of(
                new WordHighlightRange("s", 4, 5, PeriodicElement.SULFUR),
                new WordHighlightRange("es", 3, 5, PeriodicElement.EINSTEINIUM)
        );

        assertEquals(firstName, firstNameFullNameWordHighlight.word());
        assertEquals(firstNameFullNameWordHighlightRangeExpected, firstNameFullNameWordHighlightRanges);

        for (var firstNameFullNameWordHighlightRange : firstNameFullNameWordHighlightRanges) {
            var substring = firstNameFullNameWordHighlightRange.substring();
            var startIndex = firstNameFullNameWordHighlightRange.startIndex();
            var endIndex = firstNameFullNameWordHighlightRange.endIndex();
            assertEquals(firstName.substring(startIndex, endIndex), substring);
        }


        assertEquals(lastName, lastNameFullNameWordHighlight.word());
        assertEquals(lastNameFullNameWordHighlightRangeExpected, lastNameFullNameWordHighlightRanges);

        for (var lastNameFullNameWordHighlightRange : lastNameFullNameWordHighlightRanges) {
            var substring = lastNameFullNameWordHighlightRange.substring();
            var startIndex = lastNameFullNameWordHighlightRange.startIndex();
            var endIndex = lastNameFullNameWordHighlightRange.endIndex();
            assertEquals(lastName.substring(startIndex, endIndex), substring);
        }


    }

    FullNameHighlightContext createFullNameHighlightContext(String firstName, String lastName) {
        return new FullNameHighlightContext(firstName, lastName);
    }


}