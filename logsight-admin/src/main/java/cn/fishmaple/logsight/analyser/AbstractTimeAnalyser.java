package cn.fishmaple.logsight.analyser;

import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.SearchResult;
import cn.fishmaple.logsight.shell.ProcessThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
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
