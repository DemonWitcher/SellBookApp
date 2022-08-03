package com.witcher.sellbook.util;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.witcher.sellbook.module.Book;

import java.text.SimpleDateFormat;

public class CommonUtil {

    public static String formatPrice(long price) {
        String priceStr = String.valueOf(price);
        if (priceStr.length() <= 2) {
            priceStr = "00" + priceStr;
        }
        priceStr = "￥" + priceStr.substring(0, priceStr.length() - 2) + "." + priceStr.substring(priceStr.length() - 2);
        return priceStr;
    }

    public static String formatTime(long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    public static String getBookInfo(Book book) {
        return String.format("作者: %s | 出版社: %s", book.getAuthor(), book.getPress());
    }

    public static void loadCover(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
