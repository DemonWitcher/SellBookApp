package com.witcher.sellbook.util;

import com.witcher.sellbook.module.Book;

import java.util.ArrayList;
import java.util.List;

public class DataInitUtil {

    public static List<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            Book book = new Book();
            book.setName("满清十大酷刑"+i);
            book.setAuthor("溥仪"+i);
            book.setDetails("神奇的详情"+i);
            book.setLabels("神奇,哈哈,666");
            book.setPress("大清出版社"+i);
            book.setPublishData("1860年12月12日"+i);
            book.setPrice(6600);
            books.add(book);
        }

        return books;
    }
}
