package com.in28minutes.springboot.firstrestapi.servey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        String id = surveyService.addSurveyQuestion(surveyId, question);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{questionId}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSpecificQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        surveyService.deleteSpecificQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSpecificQuestion(@PathVariable String surveyId,
                                                         @PathVariable String questionId,
                                                         @RequestBody Question request) {
        surveyService.updateSpecificQuestion(surveyId, questionId, request);
        return ResponseEntity.noContent().build();
    }
}
