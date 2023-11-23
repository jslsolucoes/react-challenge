package com.workana.breakify.domain.highlight;

import com.workana.breakify.junit.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@TestPropertySource(properties = {
        "breakify.highlight.strategy=regex",
})
class PeriodicElementFullNameHighlightWithNgramsWithRegexTest extends AbstractIntegrationTest {

    @Autowired
    private PeriodicElementFullNameHighlightWithRegex periodicElementFullNameHighlightWithRegex;

    @Test
    @DisplayName("should accepts on highlight config with regex")
    void shouldAcceptsOnHighlightConfigWithRegex() {

        var firstName = "fernanda";
        var lastName = "carapinheiro";

        var fullNameHighlightContext = createFullNameHighlightContext(firstName, lastName);
        var accepts = periodicElementFullNameHighlightWithRegex.accepts(fullNameHighlightContext);
        assertTrue(accepts);
    }


    @Test
    @DisplayName("should return full name with correctly highlight result")
    void shouldReturnFullNameWithCorrectlyHighlightResult() {

        var firstName = "fernanda";
        var lastName = "carapinheiro";

        var fullNameHighlightContext = createFullNameHighlightContext(firstName, lastName);
        var result = periodicElementFullNameHighlightWithRegex.highlight(fullNameHighlightContext);

        var firstNameFullNameWordHighlight = result.firstName();
        var firstNameFullNameWordHighlightRanges = firstNameFullNameWordHighlight.ranges();
        var firstNameFullNameWordHighlightRangeExpected = List.of(
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("fe", 0, 2, PeriodicElement.IRON),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("rn", 2, 4, PeriodicElement.RADON),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("nd", 5, 7, PeriodicElement.NEODYMIUM)
        );

        var lastNameFullNameWordHighlight = result.lastName();
        var lastNameFullNameWordHighlightRanges = lastNameFullNameWordHighlight.ranges();
        var lastNameFullNameWordHighlightRangeExpected = List.of(
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("ca", 0, 2, PeriodicElement.CALCIUM),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("ra", 2, 4, PeriodicElement.RADIUM),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("p", 4, 5, PeriodicElement.PHOSPHORUS),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("in", 5, 7, PeriodicElement.INDIUM),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("he", 7, 9, PeriodicElement.HELIUM),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("ir", 9, 11, PeriodicElement.IRIDIUM),
                new FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange("o", 11, 12, PeriodicElement.OXYGEN)
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

    FullNameHighlight.FullNameHighlightContext createFullNameHighlightContext(String firstName, String lastName) {
        return new FullNameHighlight.FullNameHighlightContext(firstName, lastName);
    }

}