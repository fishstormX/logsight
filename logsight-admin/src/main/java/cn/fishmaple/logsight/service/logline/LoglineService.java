package cn.fishmaple.logsight.service.logline;

import cn.fishmaple.logsight.analyser.commandAnalyser.CommandAnalyser;
import cn.fishmaple.logsight.analyser.fileAnalyser.FileAnalyser;
import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.core.LogLineStorage;
import cn.fishmaple.logsight.core.LogLineThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
    public SseEmitter buildSseEmitter(String path,String filteredStr){
        SseEmitter sseEmitter;
        try{
            synchronized(path) {
                sseEmitter = logLineStorage.getLogLine(path);
                if(null==sseEmitter){
                    logger.info("build sseEmitter for path {}",path);
                    sseEmitter = new SseEmitter(0L);
                    logLineStorage.setLogLine(path,sseEmitter);
                    SseEmitter sseEmitter1 = sseEmitter;
                    logLineThreadPool.addTask(()->{
                        fileAnalyser.fileTail(sseEmitter1,new FileStreamAction(path)
                                .setFilterString(filteredStr)
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
}
