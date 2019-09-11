package com.kiwi.phonelive.utils;

import android.util.Log;

import com.kiwi.phonelive.AppConfig;
import com.kiwi.phonelive.AppContext;
import com.kiwi.phonelive.im.ImDateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        String timestampString = ImDateUtil.getTimestampString(time);
        final String selectLanguage = LocalManageUtil.getSelectLanguage(AppContext.sInstance);
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            if (selectLanguage.equals("系统语言") || selectLanguage.equals("中文")) {
                return r + "年前";
            } else {
                return r + "years ago";  //months ago  days ago  hours ago  minutes ago  now
            }
        }
        if (diff > month) {
            if (selectLanguage.equals("系统语言") || selectLanguage.equals("中文")) {
                r = (diff / month);
                return r + "个月前";
            } else {
                r = (diff / month);
                return r + "months ago ";
            }

        }

        if (diff > hour) {
            if (isYesterday(time)) {
                if (selectLanguage.equals("English")) {
                    String replace = timestampString.replace("昨天", "Yesterday");
                    String s = replace.contains("上午") ? replace.replace("上午", "AM") : replace.replace("下午", "PM");
                    return s;
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("昨天 hh:mm aa");
                    String format = simpleDateFormat.format(date);
                    return format.contains("AM") ? timestampString.replace("AM", "上午") : timestampString.replace("PM", "上午");
                }
            } else {
                if (diff > hour && diff <= day) {
                    if (selectLanguage.equals("系统语言") || selectLanguage.equals("中文")) {
                        r = (diff / hour);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
                        String format = simpleDateFormat.format(date);
                        return format.contains("AM") ? format.replace("AM", "上午") : format.replace("PM", "下午");
                    } else {
                        r = (diff / hour);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
                        String format = simpleDateFormat.format(date);
                        return format;
                    }
                } else {
                    if (selectLanguage.equals("English")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, h:m:s aa", Locale.ENGLISH);
                        String format = dateFormat.format(date);
                        return format;
                    } else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M月d日 hh:mm aa");
                        String format = simpleDateFormat.format(date);
                        String s = format.contains("AM") ? timestampString.replace("AM", "上午") : timestampString.replace("PM", "下午");
                        return s;
                    }
                }
            }
        }

        if (diff > minute) {
            if (selectLanguage.equals("系统语言") || selectLanguage.equals("中文")) {
                r = (diff / minute);
                return r + "分钟前";
            } else {
                r = (diff / minute);
                return r + "Minutes Ago";
            }
        }

        if (selectLanguage.equals("系统语言") || selectLanguage.equals("中文")) {
            return "刚刚";
        } else {
            return "Just";
        }
    }

    /**
     * 是否为昨天
     */
    private static boolean isYesterday(long time) {
        boolean isYesterday = false;
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(sdf.format(new Date()));
            if (time < date.getTime() && time > (date.getTime() - 24 * 60 * 60 * 1000)) {
                isYesterday = true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isYesterday;
    }
}
