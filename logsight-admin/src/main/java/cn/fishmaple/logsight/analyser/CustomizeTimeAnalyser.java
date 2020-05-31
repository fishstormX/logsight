package cn.fishmaple.logsight.analyser;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


public class CustomizeTimeAnalyser extends AbstractTimeAnalyser {
    private String formatter = "yyyy-MM-dd HH:mm:ss.SSS";

    @Override
    public Integer getTimeStringLength() {
        return 23;
    }

    @Override
    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(formatter);
    }
}
