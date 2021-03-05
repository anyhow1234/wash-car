package com.xiaozhu.washcar.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 16:22
 * @Version 1.0
 */
public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public DateUtils() {
    }

    public static String now(String format) {
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(new Date());
    }

    public static String date() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(new Date());
    }

    public static Date currentDate() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat f = new SimpleDateFormat(format);
        String d = f.format(new Date());
        return getDateFromString(d, format);
    }

    public static Date now() {
        return new Date();
    }

    public static long nowMillis() {
        return System.currentTimeMillis();
    }

    public static Date StrToDate(String str, String fm) {
        if (StringUtils.isEmpty(fm)) {
            fm = "yyyy-MM-dd HH:mm:ss";
        }

        if (str != null) {
            SimpleDateFormat format = new SimpleDateFormat(fm);
            Date date = null;

            try {
                date = format.parse(str);
            } catch (ParseException var5) {
                log.error("", var5);
            }

            return date;
        } else {
            return null;
        }
    }

    public static String taskDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("s m H d M ? yyyy");
        Date now = new Date();
        String cron = null;
        if (now.getTime() > date.getTime()) {
            cron = sdf.format(new Date(now.getTime() + 5000L));
        } else {
            cron = sdf.format(date);
        }

        return cron;
    }

    public static String formatDate(Date sourceDate, String format) {
        if (sourceDate == null) {
            return "";
        } else {
            if (format.isEmpty()) {
                format = "yyyy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sourceDate);
            return dateFormat.format(calendar.getTime());
        }
    }

    public static Date getAddDate(Date sourceDate, int type, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(type, count);
        return calendar.getTime();
    }

    public static Date getAddDateByDay(Date sourceDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public static Date getAddDateBySecond(Date sourceDate, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(13, second);
        return calendar.getTime();
    }

    public static String getAddDateBySecond(Date sourceDate, int second, String format) {
        if (format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(getAddDateBySecond(sourceDate, second));
    }

    public static Date getDateFromString(String k, String dateformat) {
        if (dateformat.isEmpty()) {
            dateformat = "yyyy-MM-dd HH:mm:ss";
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat(dateformat);
            format.setLenient(false);
            return format.parse(k);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static Date getDateFromString(String k) {
        return getDateFromString(k, "yyyy-MM-dd HH:mm:ss");
    }

    public static boolean checkDate(String k, String dateformat) {
        return getDateFromString(k, dateformat) != null;
    }

    public static long dateTimeCompare(String sdate, String edate) {
        long day = 0L;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date begin_date = format.parse(sdate);
            Date end_date = format.parse(edate);
            day = (end_date.getTime() - begin_date.getTime()) / 1000L;
            return day;
        } catch (Exception var7) {
            return -1L;
        }
    }

    public static long dateCompare(String sdate, String edate) {
        long day = 0L;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date begin_date = format.parse(sdate);
            Date end_date = format.parse(edate);
            day = (end_date.getTime() - begin_date.getTime()) / 86400000L;
            return day;
        } catch (Exception var7) {
            return -1L;
        }
    }

    public static long dateTimeCompare(Date sdate, Date edate) {
        long day = (edate.getTime() - sdate.getTime()) / 1000L;
        return day;
    }

    public static long getDateDiffSecond(String sdate, String edate) {
        long day = 0L;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date begin_date = format.parse(sdate);
            Date end_date = format.parse(edate);
            day = (end_date.getTime() - begin_date.getTime()) / 1000L;
            return day;
        } catch (Exception var7) {
            return -1L;
        }
    }

    public static long getDateDiffSecond(Date sdate, Date edate) {
        long day = 0L;

        try {
            day = (edate.getTime() - sdate.getTime()) / 1000L;
            return day;
        } catch (Exception var5) {
            return -1L;
        }
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        int w = calendar.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekOfDays[w];
    }

    public static int getWeekIndexOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        int w = calendar.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return w == 0 ? 7 : w;
    }

    public static String getWeekOfDate(int weekIndex) {
        String[] weekOfDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekIndex >= 0 && weekIndex <= 6 ? weekOfDays[weekIndex] : "";
    }

    public static String getShortWeekOfDate(int weekIndex) {
        String s = getWeekOfDate(weekIndex);
        return s.replace("星期", "周");
    }

    public static String getShortWeekOfDate(Date date) {
        String s = getWeekOfDate(date);
        return s.replace("星期", "周");
    }

    public static String formatDateChina(String SourceDate, String format) {
        Date d = new Date(dateToTimestamp(SourceDate) * 1000L);
        if (format.isEmpty()) {
            format = "MM月dd日";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return dateFormat.format(calendar.getTime());
    }

    public static long dateToTimestamp(String dateString) {
        return dateToTimestamp(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static long dateToTimestamp(String dateString, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);

            Date date;
            try {
                date = df.parse(dateString);
            } catch (ParseException var6) {
                df = new SimpleDateFormat("yyyy-MM-dd");
                date = df.parse(dateString);
            }

            long s = date.getTime();
            return s / 1000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
            return 0L;
        }
    }

    public static String getDateName(String date) {
        if (date != null && !date.isEmpty()) {
            int l = (int)getDateDiff(date(), date);
            if (l == 0) {
                return "今日";
            } else if (l == 1) {
                return "明日";
            } else {
                return l == 2 ? "后日" : formatDateChina(date, "M月d日");
            }
        } else {
            return "7日后";
        }
    }

    public static long getDateDiff(String sdate, String edate) {
        long day = 0L;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date begin_date = format.parse(sdate);
            Date end_date = format.parse(edate);
            day = (end_date.getTime() - begin_date.getTime()) / 86400000L;
            return day;
        } catch (Exception var7) {
            return -1L;
        }
    }

    public static long getDateDiff(Date sdate, Date edate) {
        long var2 = 0L;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = format.format(sdate);
            String endDate = format.format(edate);
            return getDateDiff(startDate, endDate);
        } catch (Exception var7) {
            return -1L;
        }
    }
}
