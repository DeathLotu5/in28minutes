package com.in28minutes.springboot.firstrestapi.servey;

import java.util.List;

public class Question {

    public Question(String id, String description, List<String> options, String correctAnswer) {
        this.id = id;
        this.description = description;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public Question() {

    }

    private String id;

    private String description;

    private String correctAnswer;

    private List<String> options;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", options=" + options +
                '}';
    }
}
