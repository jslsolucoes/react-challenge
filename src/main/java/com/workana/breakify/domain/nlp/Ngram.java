package com.workana.breakify.domain.nlp;

import com.workana.breakify.shared.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class Ngram {

    public List<NgramEntry> ngramOf(final String word,
                                    final int n) {

        if (word == null || word.isBlank()) {
            return List.of();
        }

        var newWord = Strings.stripAccents(word.toLowerCase());
        return IntStream.range(0, newWord.length() - n + 1)
                .mapToObj(index -> new NgramEntry(newWord.substring(index, index + n), index, index + n))
                .toList();
    }

    public record NgramEntry(String substring, int startIndex, int endIndex) {
    }
}
