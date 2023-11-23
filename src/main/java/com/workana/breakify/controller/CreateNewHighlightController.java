package com.workana.breakify.controller;

import com.workana.breakify.domain.highlight.FullNameHighlight;
import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightContext;
import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult;
import com.workana.breakify.domain.highlight.FullNameHighlight.FullNameHighlightResult.FullNameWordHighlight.WordHighlightRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiInfo.API_V1)
@Validated
public class CreateNewHighlightController {

    private final List<FullNameHighlight> fullNameHighlights;

    @Autowired
    public CreateNewHighlightController(final List<FullNameHighlight> fullNameHighlights) {
        this.fullNameHighlights = fullNameHighlights;
    }

    @Operation(summary = "Calculate periodic table highlight based on first and last name", tags = {ApiInfo.Tags.HIGHLIGHT, ApiInfo.Tags.PUBLIC})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiInfo.StatusCode.OK),
            @ApiResponse(responseCode = ApiInfo.StatusCode.BAD_REQUEST),
    })
    @PostMapping("/highlights")
    public HighlightResponse createNewHighlight(@RequestBody @Valid final HighlightRequest highlightRequest) {
        var firstName = highlightRequest.firstName();
        var lastName = highlightRequest.lastName();
        var fullNameHighLighterContext = new FullNameHighlightContext(firstName, lastName);

        var fullNameHighlight = fullNameHighlights
                .stream()
                .filter(fnh -> fnh.accepts(fullNameHighLighterContext))
                .findFirst()
                .orElseThrow();

        var fullNameHighlightResult = fullNameHighlight.highlight(fullNameHighLighterContext);
        return new HighlightResponse(fullNameHighlightResult);
    }

    @Schema(name = "HighlightRequest")
    public record HighlightRequest(@NotBlank(message = "cant be blank") String firstName,
                                   @NotBlank(message = "cant be blank") String lastName) {
    }

    @Schema(name = "HighlightResponse")
    public record HighlightResponse(HighlightResponseWord firstName, HighlightResponseWord lastName) {

        public HighlightResponse(final FullNameHighlightResult fullNameHighlightResult) {
            this(
                    new HighlightResponseWord(
                            fullNameHighlightResult.firstName().word(),
                            fullNameHighlightResult.firstName().ranges()
                                    .stream()
                                    .map(HighlightResponseWord.HighlightResponseWordRange::new)
                                    .toList()
                    ),
                    new HighlightResponseWord(
                            fullNameHighlightResult.lastName().word(),
                            fullNameHighlightResult.lastName().ranges()
                                    .stream()
                                    .map(HighlightResponseWord.HighlightResponseWordRange::new)
                                    .toList()
                    )
            );
        }

        public record HighlightResponseWord(String word, List<HighlightResponseWordRange> highlights) {
            public record HighlightResponseWordRange(String substring,
                                                     int startIndex,
                                                     int endIndex,
                                                     String periodicElementSymbol,
                                                     int periodicElementAtomicNumber) {
                HighlightResponseWordRange(final WordHighlightRange wordHighlightRange) {
                    this(
                            wordHighlightRange.substring(),
                            wordHighlightRange.startIndex(),
                            wordHighlightRange.endIndex(),
                            wordHighlightRange.periodicElementSymbol(),
                            wordHighlightRange.periodicElementAtomicNumber()
                    );
                }
            }
        }
    }
}
