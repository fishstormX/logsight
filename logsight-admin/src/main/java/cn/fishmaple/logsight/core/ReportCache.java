package cn.fishmaple.logsight.core;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportCache {
    private List<String> dailyFileReport;

    public List<String> getDailyFileReport() {
        return dailyFileReport;
    }

    public void setDailyFileReport(List<String> dailyFileReport) {
        this.dailyFileReport = dailyFileReport;
    }

    @PostConstruct
    public void init(){
        dailyFileReport = new ArrayList<>();
    }
}
