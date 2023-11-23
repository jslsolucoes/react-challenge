package com.workana.breakify.controller;

import com.workana.breakify.junit.AbstractMvcIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateNewHighlightControllerTest extends AbstractMvcIntegrationTest {


    @Value("${breakify.highlight.strategy}")
    private String strategy;

    @Test
    @DisplayName("should fail if first name and last name is empty")
    void shouldFailWithEmptyFirstAndLastName() throws Exception {
        var json = """
                    {
                    
                    }
                """;
        var requestBuilder = post(ApiInfo.API_V1 + "/highlights")
                .content(json)
                .with(noAuthentication());

        var oneOf = oneOf("firstName cant be blank", "lastName cant be blank");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasKey("messages")))
                .andExpect(jsonPath("$.messages", hasSize(2)))
                .andExpect(jsonPath("$.messages.[0]").value(oneOf))
                .andExpect(jsonPath("$.messages.[1]").value(oneOf));
    }

    @Test
    @DisplayName("should fail if first name is empty")
    void shouldFailWithEmptyFirstName() throws Exception {
        var json = """
                    {
                      "lastName":"some"
                    }
                """;
        var requestBuilder = post(ApiInfo.API_V1 + "/highlights")
                .content(json)
                .with(noAuthentication());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasKey("messages")))
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages.[0]").value("firstName cant be blank"));
    }

    @Test
    @DisplayName("should fail if last name is empty")
    void shouldFailWithEmptyLastName() throws Exception {
        var json = """
                    {
                      "firstName":"some"
                    }
                """;
        var requestBuilder = post(ApiInfo.API_V1 + "/highlights")
                .content(json)
                .with(noAuthentication());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasKey("messages")))
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages.[0]").value("lastName cant be blank"));
    }

    @Test
    @DisplayName("should create highlights with success even including accents, cased letters and special chars")
    void shouldCreateHighlightWithSuccessEvenIncludingAccentsCasedLettersAndSpecialChars() throws Exception {


        if ("regex".equalsIgnoreCase(strategy)) {
            assertSuccess();
            return;
        }

        var firstName = "FêrNAn@#%Da";
        var lastName = "CaráPINHeiro";

        var json = """
                    {
                      "firstName":"%s",
                      "lastName": "%s"
                    }
                """.formatted(firstName, lastName);

        var requestBuilder = post(ApiInfo.API_V1 + "/highlights")
                .content(json)
                .with(noAuthentication());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("firstName")))
                .andExpect(jsonPath("$.firstName", hasKey("word")))
                .andExpect(jsonPath("$.firstName.word").value(firstName))
                .andExpect(jsonPath("$.firstName", hasKey("highlights")))
                .andExpect(jsonPath("$.firstName.highlights", hasSize(7)))
                .andExpect(jsonPath("$.firstName.highlights.[0]", hasKey("substring")))
                .andExpect(jsonPath("$.firstName.highlights.[0]", hasKey("startIndex")))
                .andExpect(jsonPath("$.firstName.highlights.[0]", hasKey("endIndex")))
                .andExpect(jsonPath("$.firstName.highlights.[0]", hasKey("periodicElementSymbol")))
                .andExpect(jsonPath("$.firstName.highlights.[0]", hasKey("periodicElementAtomicNumber")))
                .andExpect(jsonPath("$.firstName.highlights.[0].substring").value("f"))
                .andExpect(jsonPath("$.firstName.highlights.[0].startIndex").value(0))
                .andExpect(jsonPath("$.firstName.highlights.[0].endIndex").value(1))
                .andExpect(jsonPath("$.firstName.highlights.[0].periodicElementSymbol").value("F"))
                .andExpect(jsonPath("$.firstName.highlights.[0].periodicElementAtomicNumber").value(9))
                .andExpect(jsonPath("$", hasKey("lastName")))
                .andExpect(jsonPath("$.lastName", hasKey("word")))
                .andExpect(jsonPath("$.lastName.word").value(lastName))
                .andExpect(jsonPath("$.lastName", hasKey("highlights")))
                .andExpect(jsonPath("$.lastName.highlights", hasSize(14)))
                .andExpect(jsonPath("$.lastName.highlights.[0]", hasKey("substring")))
                .andExpect(jsonPath("$.lastName.highlights.[0]", hasKey("startIndex")))
                .andExpect(jsonPath("$.lastName.highlights.[0]", hasKey("endIndex")))
                .andExpect(jsonPath("$.lastName.highlights.[0]", hasKey("periodicElementSymbol")))
                .andExpect(jsonPath("$.lastName.highlights.[0]", hasKey("periodicElementAtomicNumber")))
                .andExpect(jsonPath("$.lastName.highlights.[0].substring").value("c"))
                .andExpect(jsonPath("$.lastName.highlights.[0].startIndex").value(0))
                .andExpect(jsonPath("$.lastName.highlights.[0].endIndex").value(1))
                .andExpect(jsonPath("$.lastName.highlights.[0].periodicElementSymbol").value("C"))
                .andExpect(jsonPath("$.lastName.highlights.[0].periodicElementAtomicNumber").value(6));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a,b", "aa,bb"})
    @DisplayName("should create empty highlights for short names")
    void shouldCreateEmptyHighlightsForShortNames(final String fullName) throws Exception {
        var nameParts = fullName.split(",");
        var firstName = nameParts[0];
        var lastName = nameParts[1];

        var json = """
                    {
                      "firstName":"%s",
                      "lastName": "%s"
                    }
                """.formatted(firstName, lastName);

        var requestBuilder = post(ApiInfo.API_V1 + "/highlights")
                .content(json)
                .with(noAuthentication());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("firstName")))
                .andExpect(jsonPath("$.firstName", hasKey("word")))
                .andExpect(jsonPath("$.firstName.word").value(firstName))
                .andExpect(jsonPath("$.firstName", hasKey("highlights")))
                .andExpect(jsonPath("$.firstName.highlights", hasSize(0)));
    }

}