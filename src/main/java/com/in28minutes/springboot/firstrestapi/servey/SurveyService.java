package com.in28minutes.springboot.firstrestapi.servey;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurvey() {
        return surveys;
    }

    public Survey retrieveSurveyById(String surveyId) {

        Predicate<? super Survey> predicate =
                survey -> survey.getId().equalsIgnoreCase(surveyId);

        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

        if (optionalSurvey.isEmpty()) return null;

        return optionalSurvey.get();
    }

    public List<Question> retrieveQuestions(String surveyId) {

        Predicate<? super Survey> predicate =
                survey -> survey.getId().equalsIgnoreCase(surveyId);

        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

        if (optionalSurvey.isEmpty()) return null;

        return optionalSurvey.get().getQuestions();
    }

    public Question retrieveQuestionById(String surveyId, String questionId) {

        Predicate<? super Survey> predicate =
                survey -> survey.getId().equalsIgnoreCase(surveyId);

        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

        if (optionalSurvey.isEmpty()) return null;

        List<Question> questions = optionalSurvey.get().getQuestions();

        Predicate<? super Question> questionPredicate =
                question -> question.getId().equalsIgnoreCase(questionId);
        Optional<Question> optionalQuestion = questions.stream().filter(questionPredicate).findFirst();

        if (optionalQuestion.isEmpty()) return null;

        return optionalQuestion.get();
    }

    public String addSurveyQuestion(String surveyId, Question question) {
        List<Question> questions = retrieveQuestions(surveyId);
        question.setId(getRandomId());
        questions.add(question);

        return question.getId();
    }

    private String getRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        String randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }

    public String deleteSpecificQuestion(String surveyId, String questionId) {
        List<Question> questions = retrieveQuestions(surveyId);

        if (questions == null) return null;

        Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
        boolean removed = questions.removeIf(predicate);

        if (!removed) return null;

        return questionId;
    }


    public void updateSpecificQuestion(String surveyId, String questionId, Question request) {
        List<Question> questions = retrieveQuestions(surveyId);
        questions.removeIf(question -> question.getId().equalsIgnoreCase(questionId));
        questions.add(request);
    }
}
