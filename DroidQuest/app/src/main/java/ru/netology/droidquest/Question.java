package ru.netology.droidquest;

public class Question {
    private String text; // текст вопроса
    private boolean answerTrue; // правильный ответ (true или false)

    public Question(String text, boolean answerTrue) {
        this.text = text;
        this.answerTrue = answerTrue;
    }

    public String getText() {
        return text;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }
}

