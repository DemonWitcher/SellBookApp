package com.witcher.sellbook.util;

import com.witcher.sellbook.BookApp;
import com.witcher.sellbook.R;
import com.witcher.sellbook.module.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataInitUtil {

    private static final int DATA_SIZE = 20;

    public static List<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>(DATA_SIZE);
        List<String> names = Arrays.asList(BookApp.getApp().getResources().getStringArray(R.array.book_name));
        List<String> covers = Arrays.asList(BookApp.getApp().getResources().getStringArray(R.array.book_cover));
        List<String> authors = Arrays.asList(BookApp.getApp().getResources().getStringArray(R.array.book_author));
        List<String> details = Arrays.asList(BookApp.getApp().getResources().getStringArray(R.array.book_details));
        List<String> press = Arrays.asList(BookApp.getApp().getResources().getStringArray(R.array.book_press));
        List<String> publishData = Arrays.asList(BookApp.getApp().getResources().getStringArray(R.array.book_publish_data));
        int[] price = BookApp.getApp().getResources().getIntArray(R.array.book_price);
        List<Integer> priceList = new ArrayList<>(DATA_SIZE);
        for (int i : price) {
            priceList.add(i);
        }
        for (int i = 0; i < DATA_SIZE; i++) {
            Book book = new Book();
            book.setName(names.get(i));
            book.setAuthor(authors.get(i));
            book.setDetails(details.get(i));
            book.setCover(covers.get(i));
//            book.setLabels("神奇,哈哈,666");
            book.setPress(press.get(i));
            book.setPublishData(publishData.get(i));
            book.setPrice(priceList.get(i));
            books.add(book);
        }
        return books;
    }
}
