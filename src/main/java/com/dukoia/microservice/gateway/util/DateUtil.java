package com.dukoia.microservice.gateway.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:JefferyChang
 * @Date:2019/5/16 19:43
 * @Desp:
 */
@Slf4j
public class DateUtil {

    public static final String DATA_FORMAT14 = "yyyyMMddHHmmss";

    public static final String DATA_FORMAT16 = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_ONLY_FORMAT6 = "HHmmss";



    public static Date parse(String formatPattern, String dateString) {
        Date parse = null;
        try {
            parse = new SimpleDateFormat(formatPattern).parse(dateString);
        } catch (ParseException e) {
            log.error("日期格式转换异常:{}", e);
        }
        return parse;
    }

    public static String format(String formatPattern, Date date) {
        return new SimpleDateFormat(formatPattern).format(date);
    }

    public static int calculateDays(String formatPattern, String sdate, String edate) throws Exception {
        return calculateDays(parse(formatPattern, sdate), parse(formatPattern, edate));
    }

    public static int calculateDays(Date sdate, Date edate) {
        int ret = 0;
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(sdate);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(edate);
        int startDay = startDate.get(Calendar.DAY_OF_YEAR);
        int endDay = endDate.get(Calendar.DAY_OF_YEAR);
        int startYear = startDate.get(Calendar.YEAR);
        int endYear = endDate.get(Calendar.YEAR);
        if (startYear != endYear) {
            //不同年
            int timeDistance = 0;
            for (int i = startYear; i < endYear; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            ret = timeDistance + (endDay - startDay);
        } else {
            //同年
            ret = endDay - startDay;
        }
        String startTime = format(TIME_ONLY_FORMAT6, startDate.getTime());
        String endTime = format(TIME_ONLY_FORMAT6, endDate.getTime());
        if ("000000".equals(startTime) && "235959".equals(endTime)) {
            ret++;
        }
        return ret;
    }

    public static int getAge(Date birthday, Date nowDate) {
        int iAge = 0;

        try {
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(birthday);
            Calendar now = Calendar.getInstance();
            now.setTime(nowDate);

            iAge = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

            birthDate.add(Calendar.YEAR, iAge);
            if ((now.getTime()).before(birthDate.getTime())) {
                iAge--;
            }

        } catch (Exception e) {
            log.error("=============================错误信息：{}",e);
        }

        return iAge;
    }

    public static boolean isAround(Date targetTime, Date startTime, Date endTime) {
        return (targetTime.after(startTime) && (targetTime.before(endTime)));
    }

    public static Date addTime(Date targetTime, int timeUnit, int time) {
        Calendar target = Calendar.getInstance();
        target.setTime(targetTime);
        target.add(timeUnit, time);
        return target.getTime();
    }

    public static Date addDayTime(Date targetTime, int time) {
        Calendar target = Calendar.getInstance();
        target.setTime(targetTime);
        target.add(Calendar.DAY_OF_MONTH, time);
        return target.getTime();
    }

    public static Date addYearTime(Date targetTime, int time) {
        Calendar target = Calendar.getInstance();
        target.setTime(targetTime);
        target.add(Calendar.YEAR, time);
        return target.getTime();
    }

    public static Date addHourTime(Date targetTime, int time) {
        Calendar target = Calendar.getInstance();
        target.setTime(targetTime);
        target.add(Calendar.HOUR_OF_DAY, time);
        return target.getTime();
    }

    public static String addStringTime(Date targetTime, int timeUnit, int time, String format) {
        return format(format, addTime(targetTime, timeUnit, time));
    }

    public static String addStringTime(Date targetTime, int timeUnit, int time) {
        return addStringTime(targetTime, timeUnit, time, DATA_FORMAT14);
    }

    public static String addStringDayTime(Date targetTime, int time) {
        return addStringTime(targetTime, Calendar.DAY_OF_MONTH, time, DATA_FORMAT14);
    }
}
