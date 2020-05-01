package cn.fishmaple.logsight.analyser;

import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.SearchResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface TimeAnalyser {
    public Date timeFormat(String commandLine);
    public Integer getTimeStringLength();
    public SimpleDateFormat getDateFormat();
}
