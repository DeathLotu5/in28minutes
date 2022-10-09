package com.in28minutes.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.*;

class JsonAssertTest {

    @Test
    void testJsonAssert_learningBasic() throws JSONException {
        String expectedResponse =
                """
                    {"id":"Question1","description":"Most Popular Cloud Platform Today","correctAnswer":"AWS","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}
                        """;
        String actualResponse =
                """
                    {"id":"Question1","description":"Most Popular Cloud Platform Today","correctAnswer":"AWS","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}
                        """;
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

}
