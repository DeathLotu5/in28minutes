package com.in28minutes.springboot.firstrestapi.servey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SurveyResource {

    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurvey() {
       return surveyService.retrieveAllSurvey();
    }

    @RequestMapping("/surveys/{surveyId}")
    public Survey retrieveSurveyById(@PathVariable String surveyId) {
        Survey survey = surveyService.retrieveSurveyById(surveyId);

        if (survey == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return survey;
    }

    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveQuestions(@PathVariable String surveyId) {
        return surveyService.retrieveQuestions(surveyId);
    }

    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestionById(@PathVariable String surveyId, @PathVariable String questionId) {
        return surveyService.retrieveQuestionById(surveyId, questionId);
    }
}
