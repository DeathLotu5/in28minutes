package com.in28minutes.springboot.firstrestapi.survey;

import com.in28minutes.springboot.firstrestapi.servey.Question;
import com.in28minutes.springboot.firstrestapi.servey.SurveyResource;
import com.in28minutes.springboot.firstrestapi.servey.SurveyService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false) //addFilters = false => ignore tất cả các filters (security)
    //Thông thường Unit Test là chúng ta test các layer của resource nên sẽ không cần dùng đến Security.
class SurveyResourceTest {

    @MockBean
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    private static String SPECIFIC_QUESTION_URL = "http://localhost:8082/surveys/survey1/questions/question1";
    private static String GENERIC_QUESTION_URL = "http://localhost:8082/surveys/survey1/questions";

    @Test
    void retrieveQuestionById_404Scenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void retrieveQuestionById_basicScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        when(surveyService.retrieveQuestionById("survey1", "question1")).thenReturn(question1);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectResponse = """
                {
                    "id": "Question1",
                    "description": "Most Popular Cloud Platform Today",
                    "correctAnswer": "AWS",
                    "options":[
                        "AWS",
                        "Azure",
                        "Google Cloud",
                        "Oracle Cloud"
                    ]
                }
                """;

        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expectResponse, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void addSurveyQuestion_basicScenario() throws Exception {
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

        //sẽ quyết định cái response trả về của method trong service khi ta gọi cái unit test này
        when(surveyService.addSurveyQuestion(anyString(), any())).thenReturn("DUONG_PH");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GENERIC_QUESTION_URL) //gửi req lên url.
                .accept(MediaType.APPLICATION_JSON) // Resp trả về ở định dạng nào.
                .content(body) // req body
                .contentType(MediaType.APPLICATION_JSON); //req body gửi lên ở định dạng nào

        // để lấy resp, sử dụng cho assert method bên dưới.
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String locationHeader = mvcResult.getResponse().getHeader("Location");

        assertEquals(201, mvcResult.getResponse().getStatus());
        assertTrue(locationHeader.contains("surveys/survey1/questions/DUONG_PH"));
    }

}
