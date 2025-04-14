package ru.netology.bookdepository;

import java.util.UUID;

public class Book {
    private UUID mId;
    private String mTitle;

    public Book() {
        mId = UUID.randomUUID(); // Генерирование уникального идентификатора
    }

    public UUID getId() { // Изменил метод на 'getId()' (согласованность с Java convention)
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
