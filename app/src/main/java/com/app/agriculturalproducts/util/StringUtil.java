package com.app.agriculturalproducts.util;

import java.text.SimpleDateFormat;

/**
 * Created by ALPHONSO on 2016/1/15.
 */
public class StringUtil {
    public static String getTimeFormatStr(long time){
        String ISO_FORMAT = "MM/dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        return sdf.format(time);
    }

    public static String getMaskedStr(String input,int len,char mask) {
        if (input != null) {
            StringBuilder strb = new StringBuilder(input);
            for (int i = 0; i < len; i++) {
                strb.setCharAt(strb.length() - 1 - i, mask);
            }
            return strb.toString();
        }
        return null;
    }
}
