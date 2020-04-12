package cn.fishmaple.logsight.util;

import org.springframework.util.Assert;
import java.text.SimpleDateFormat;
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
}
