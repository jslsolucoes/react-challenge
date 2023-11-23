package com.workana.breakify.domain.highlight;

import com.workana.breakify.junit.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PeriodicElementTest extends AbstractIntegrationTest {


    @Test
    @DisplayName("Should return map with symbol lower case as key and periodic element as value")
    void shouldReturnMapWithSymbolLowerCaseAsKeyAndPeriodicElementAsValue() {
        var symbolsAsMap = PeriodicElement.symbolsAsMap();
        assertEquals(119, symbolsAsMap.size());
        assertEquals(PeriodicElement.OGANESSON, symbolsAsMap.get("og"));
    }

}