package top.levygo.trendetf.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 时间工具类
 */
@Component
public class DateUtil {
    //获取年份
    public static int getYear(Date date){
        GregorianCalendar gc=(GregorianCalendar)Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }
    //根据String类型的日期获取年份
    public static int getYear(String date) throws ParseException {
        GregorianCalendar gc=(GregorianCalendar)Calendar.getInstance();
        gc.setTime(StringToDate(date));
        return Integer.valueOf(gc.get(1));
    }
    //获取月
    public static int getMonth(Date date) {
        GregorianCalendar gc=(GregorianCalendar)Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }
    //获取两个日期之间的相差天数
    public static int getDiffDays(Date beginDate, Date endDate) {
        if(beginDate==null||endDate==null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff=(endDate.getTime()-beginDate.getTime())/(1000*60*60*24);
        int days = new Long(diff).intValue();
        return days;
    }

    //date转String
    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    //String转date
    public static Date StringToDate(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) formatter.parse(str);
        return date;
    }
}

