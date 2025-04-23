package ru.netology.bookdepository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookLab {
    private static BookLab sBookLab;
    private List<Book> mBooks;

    // Конструктор
    private BookLab(Context context) {
        mBooks = new ArrayList<>();
    }

    // Метод для получения экземпляра BookLab (синглтон)
    public static BookLab getBookLab(Context context) {
        if (sBookLab == null) {
            sBookLab = new BookLab(context);
        }
        return sBookLab;
    }

    // Метод для добавления книги
    public void addBook(Book b) {
        mBooks.add(b);
    }

    // Метод для получения списка всех книг
    public List<Book> getBooks() {
        return mBooks;
    }

    // Метод для получения книги по ее ID
    public Book getBook(UUID id) {
        for (Book book : mBooks) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null; // Если книга не найдена
    }

    // Метод для удаления книги
    public void removeBook(UUID id) {
        for (Book book : mBooks) {
            if (book.getId().equals(id)) {
                mBooks.remove(book);
                break; // Выход из цикла после удаления
            }
        }
    }
}
