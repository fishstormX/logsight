package cn.fishmaple.logsight.analyser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class AbstractTimeAnalyser implements TimeAnalyser{

    public Date timeFormat(String commandLine){
        String dateStr = commandLine.substring(0,getTimeStringLength());
        SimpleDateFormat simpleDateFormat = getDateFormat();
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }


}
