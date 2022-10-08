package com.in28minutes.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Objects;

import static javax.swing.UIManager.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

    private static String SPECIFIC_QUESTION_URL = "/surveys/survey1/questions/question1";
    private static String GENERIC_QUESTION_URL = "/surveys/survey1/questions";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void retrieveQuestionById_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse =
                """
                    {"id":"Question1","description":"Most Popular Cloud Platform Today","correctAnswer":"AWS","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}
                        """;

        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(GENERIC_QUESTION_URL, String.class);

        String expectedResponse =
                """
                    [
                        {
                            "id": "Question1"
                        },
                        {
                            "id": "Question2"
                        },
                        {
                            "id": "Question3"
                        }
                    ]
                """;

        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }



    @Test
    void addNewSurveyQuestion_basicScenario() {
        String body = """
                {
                    "description": "Your Favorite Programming Language",
                    "correctAnswer": "Java",
                    "options": [
                        "Java",
                        "Javascript",
                        "Python",
                        "Ruby"
                    ]
                }
            """;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(GENERIC_QUESTION_URL, HttpMethod.POST, httpEntity, String.class);
        String locationHeaders = Objects.requireNonNull(responseEntity.getHeaders().get("Location")).get(0);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertTrue(locationHeaders.contains("/surveys/survey1/questions/"));

        testRestTemplate.delete(locationHeaders);
    }

}
