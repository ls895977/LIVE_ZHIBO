package com.kiwi.phonelive.utils;

import android.net.Uri;

/**
 * ========================================
 * <p/>
 * 版 权：江苏精易达信息技术股份有限公司 版权所有 （C） 2018
 * <p/>
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/9/17 10:24
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public class Emoji {
    public static String filterCharToNormal(String oldString) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = oldString.length();
        for (int i = 0; i < length; i++) {//遍历传入的String的所有字符
            char codePoint = oldString.charAt(i);
            if (//如果当前字符为常规字符,则将该字符拼入StringBuilder
                    ((codePoint >= 0x4e00) && (codePoint <= 0x9fa5)) ||//表示汉字区间
                            ((codePoint >= 0x30) && (codePoint <= 0x39)) ||//表示数字区间
                            ((codePoint >= 0x41) && (codePoint <= 0x5a)) ||//表示大写字母区间
                            ((codePoint >= 0x61) && (codePoint <= 0x7a))) {//小写字母区间
                stringBuilder.append(codePoint);
            } else {//如果当前字符为非常规字符,则忽略掉该字符
                if (!String.valueOf(codePoint).equals("/")){
                    stringBuilder.append(encode(codePoint));
                }else {
                    stringBuilder.append(codePoint);
                }

            }
        }
        return stringBuilder.toString();
    }
      public static String encode(String s){
      return filterCharToNormal(s);
    }

    public static String encode(char s){
      String data =  Uri.encode(String.valueOf(s),"[]:,{}\"");
        while (data.contains("%20")){
            data = data.replace("%20"," ");
        }
      return data ;
    }

    public static String decode(String s){
        return Uri.decode(s);
    }


}
