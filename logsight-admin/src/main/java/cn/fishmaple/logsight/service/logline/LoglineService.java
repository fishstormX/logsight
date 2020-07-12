package cn.fishmaple.logsight.service.logline;

import cn.fishmaple.logsight.analyser.commandAnalyser.CommandAnalyser;
import cn.fishmaple.logsight.analyser.fileAnalyser.FileAnalyser;
import cn.fishmaple.logsight.analyser.logFilter.LogFilter;
import cn.fishmaple.logsight.analyser.logFilter.LogFilterConsts;
import cn.fishmaple.logsight.analyser.logFilter.LogLevelLogFilter;
import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.core.LogLineStorage;
import cn.fishmaple.logsight.core.LogLineThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LoglineService {
    @Autowired
    LogLineStorage logLineStorage;
    @Autowired
    CommandAnalyser commandAnalyser;
    @Autowired
    FileAnalyser fileAnalyser;
    @Autowired
    LogLineThreadPool logLineThreadPool;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public SseEmitter buildSseEmitter(String path,String filteredStr,boolean onlyException,String logLevel){
        SseEmitter sseEmitter;
        try{
            String locked = path+onlyException+logLevel;
            synchronized(locked) {
                sseEmitter = logLineStorage.getLogLine(locked);
                if(null==sseEmitter){
                    logger.info("build sseEmitter for path {}-{}-{}",path,onlyException,logLevel);
                    sseEmitter = new SseEmitter(0L);
                    logLineStorage.setLogLine(locked,sseEmitter);
                    SseEmitter sseEmitter1 = sseEmitter;
                    List<LogFilter> logFilters = new ArrayList<>();
                    if(onlyException){
                        logFilters.add(LogFilterConsts.EXCEPTION_FILTER);
                    }else{
                        LogFilter logLevelLogFilter = getLogLevelFilter(logLevel);
                        if(null!=logLevelLogFilter){
                            logFilters.add(logLevelLogFilter);
                        }
                    }
                    logLineThreadPool.addTask(()->{
                        fileAnalyser.fileTail(sseEmitter1,new FileStreamAction(path)
                                .setFilterString(filteredStr)
                                .setLogFilters(logFilters)
                                .setStrFiltered(null==filteredStr)
                                .setStrFiltered(false)
                        );
                    });
                }else {
                    logger.info("get SseStorage for path {}",path);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sseEmitter;
    }

    public SseEmitter buildSseEmitter(String path,String filteredStr) {
        return buildSseEmitter(path, filteredStr,false,"ALL");
    }

    public List<String> getHighLightThings(boolean onlyException,String logLevel) {
        List<String> result = new ArrayList<>();
        if(onlyException){
            result.add("Exception");
            result.add("ERROR");
        }else if(null!=logLevel||!"ALL".equals(logLevel)){
            LogLevelLogFilter logLevelLogFilter = getLogLevelFilter(logLevel);
            if(null==logLevelLogFilter){
                return result;
            }
            for(String logLevelStr:logLevelLogFilter.getLogLevel()){
                result.add(" "+logLevelStr+" ");
            }
        }
        return result;
    }

    private LogLevelLogFilter getLogLevelFilter(String logLevel){
        if(logLevel.equals(LogFilterConsts.TRACE_LOGLEVEL)){
            return LogFilterConsts.TRACE_LOGLEVEL_FILTER;
        }else if(logLevel.equals(LogFilterConsts.DEBUG_LOGLEVEL)){
            return LogFilterConsts.DEBUG_LOGLEVEL_FILTER;
        }else if(logLevel.equals(LogFilterConsts.INFO_LOGLEVEL)){
            return LogFilterConsts.INFO_LOGLEVEL_FILTER;
        }else if(logLevel.equals(LogFilterConsts.WARN_LOGLEVEL)){
            return LogFilterConsts.WARN_LOGLEVEL_FILTER;
        }else if(logLevel.equals(LogFilterConsts.ERROR_LOGLEVEL)){
            return LogFilterConsts.ERROR_LOGLEVEL_FILTER;
        }
        return null;
    }
}
