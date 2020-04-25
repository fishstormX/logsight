package cn.fishmaple.logsight.util;

import org.springframework.util.Assert;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal();

    public static void initedFormatter(String pattern){
        simpleDateFormatThreadLocal.set(new SimpleDateFormat(pattern));
    }

    public static void initedFormatter(){
        simpleDateFormatThreadLocal.set(new SimpleDateFormat(DEFAULT_PATTERN));
    }

    public static String formatTimeUnchecked(Date date){
        Assert.notNull(simpleDateFormatThreadLocal.get(),"simpleDateFormatThreadLocal not Init Error");
        return simpleDateFormatThreadLocal.get().format(date);
    }

    public static Date getEarlyHour(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR));
        Date earltHour = calendar.getTime();
        return earltHour;
    }

    public static int getHours(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Date getEarlyHour(Integer ear,Integer minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+ear);
        Date earlyHour = calendar.getTime();
        return earlyHour;
    }
}
