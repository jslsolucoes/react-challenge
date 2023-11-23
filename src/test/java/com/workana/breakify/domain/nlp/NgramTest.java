package com.workana.breakify.domain.nlp;

import com.workana.breakify.junit.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class NgramTest extends AbstractIntegrationTest {

    @Autowired
    private Ngram ngram;


    @ParameterizedTest
    @DisplayName("should return ngram of size 2 for valid word including case sensitive and accents")
    @ValueSource(strings = {"café", "CaFê"})
    void shouldReturnNgramOf2CorrectlyForValidWordAndIgnoringAccents(final String word) {
        var ngramOf = ngram.ngramOf(word, 2);
        assertEquals(3, ngramOf.size());
        var expectedEntries = List.of(
                new Ngram.NgramEntry("ca", 0, 2),
                new Ngram.NgramEntry("af", 1, 3),
                new Ngram.NgramEntry("fe", 2, 4)
        );
        assertEquals(expectedEntries, ngramOf);
    }

    @Test
    @DisplayName("should return ngram of size 2 for valid word")
    void shouldReturnNgramOf2CorrectlyForValidWord() {
        var word = "jonatan";
        var ngramOf = ngram.ngramOf(word, 2);
        assertEquals(6, ngramOf.size());

        var expectedEntries = List.of(
                new Ngram.NgramEntry("jo", 0, 2),
                new Ngram.NgramEntry("on", 1, 3),
                new Ngram.NgramEntry("na", 2, 4),
                new Ngram.NgramEntry("at", 3, 5),
                new Ngram.NgramEntry("ta", 4, 6),
                new Ngram.NgramEntry("an", 5, 7)
        );
        assertEquals(expectedEntries, ngramOf);

        for (var ngramEntry : ngramOf) {
            var substring = ngramEntry.substring();
            var startIndex = ngramEntry.startIndex();
            var endIndex = ngramEntry.endIndex();
            assertEquals(word.substring(startIndex, endIndex), substring);
        }
    }

    @Test
    @DisplayName("should return ngram of size 1 for valid word")
    void shouldReturnNgramOf1CorrectlyForValidWord() {
        var word = "jonatan";
        var ngramOf = ngram.ngramOf(word, 1);
        assertEquals(7, ngramOf.size());
        var expectedEntries = List.of(
                new Ngram.NgramEntry("j", 0, 1),
                new Ngram.NgramEntry("o", 1, 2),
                new Ngram.NgramEntry("n", 2, 3),
                new Ngram.NgramEntry("a", 3, 4),
                new Ngram.NgramEntry("t", 4, 5),
                new Ngram.NgramEntry("a", 5, 6),
                new Ngram.NgramEntry("n", 6, 7)
        );
        assertEquals(expectedEntries, ngramOf);

        for (var ngramEntry : ngramOf) {
            var substring = ngramEntry.substring();
            var startIndex = ngramEntry.startIndex();
            var endIndex = ngramEntry.endIndex();
            assertEquals(word.substring(startIndex, endIndex), substring);
        }

    }

    @Test
    @DisplayName("should return empty ngram for empty word")
    void shouldReturnEmptyNgramForEmptyWord() {
        var ngramOf = ngram.ngramOf("", 1);
        assertEquals(0, ngramOf.size());
    }

    @Test
    @DisplayName("should return empty ngram for blank word")
    void shouldReturnEmptyNgramForBlankWord() {
        var ngramOf = ngram.ngramOf(" ", 1);
        assertEquals(0, ngramOf.size());
    }

    @Test
    @DisplayName("should return empty ngram for null word")
    void shouldReturnEmptyNgramForNullWord() {
        var ngramOf = ngram.ngramOf(null, 1);
        assertEquals(0, ngramOf.size());
    }


}