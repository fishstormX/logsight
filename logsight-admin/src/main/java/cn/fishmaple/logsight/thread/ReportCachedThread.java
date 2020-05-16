package cn.fishmaple.logsight.thread;

import cn.fishmaple.logsight.core.ReportCache;
import cn.fishmaple.logsight.dao.dto.FileReportDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.mapper.FileReportMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.object.PagedStat;
import cn.fishmaple.logsight.service.ApplicationContextProvider;
import cn.fishmaple.logsight.service.ReportService;
import cn.fishmaple.logsight.util.FileUtil;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class ReportCachedThread extends Thread{
    private ReportCache reportCache;
    private ReportService reportService;

    @PostConstruct
    public void init(){
        new ReportCachedThread().start();
    }
    private void initComponents(){
        while (null==reportCache||null==reportService){
            reportCache = ApplicationContextProvider.getBean(ReportCache.class);
            reportService = ApplicationContextProvider.getBean(ReportService.class);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void run(){
        initComponents();
        while (true) {
            reportCache.setDailyFileReport(reportService.getDailyFileReport());
            try {
                Thread.sleep(1000*60*30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
