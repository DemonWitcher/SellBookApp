package com.witcher.sellbook.util;

public class CommonUtil {

    public static String formatPrice(long price) {
        String priceStr = String.valueOf(price);
        if (priceStr.length() <= 2) {
            priceStr = "00" + priceStr;
        }
        priceStr = "￥" + priceStr.substring(0, priceStr.length() - 2) + "." + priceStr.substring(priceStr.length() - 2);
        return priceStr;
    }

}
