package ru.netology.droidquest;

public class Question {
    private String text; // текст вопроса
    private boolean answerTrue; // правильный ответ (true или false)
    private boolean deceitStatus; // статус обмана - true, если вопрос был обманут

    // Конструктор класса
    public Question(String text, boolean answerTrue) {
        this.text = text;
        this.answerTrue = answerTrue;
        this.deceitStatus = false; // Изначально вопрос не обманут
    }

    // Метод для получения текста вопроса
    public String getText() {
        return text;
    }

    // Метод для проверки правильности ответа
    public boolean isAnswerTrue() {
        return answerTrue;
    }

    // Метод для получения статуса обмана
    public boolean isDeceitStatus() {
        return deceitStatus;
    }

    // Метод для установки статуса обмана
    public void setDeceitStatus(boolean deceitStatus) {
        this.deceitStatus = deceitStatus;
    }

    // Метод для получения информации о вопросе в виде строки
    @Override
    public String toString() {
        return "Question: " + text + " (Правильный ответ: " + answerTrue + ")";
    }
}
