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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    public SseEmitter buildSseEmitter(String path){
        //InputStream inputStream = commandAnalyser.forceLogStream(new FileStreamAction(path));
        SseEmitter sseEmitter;
        try{
            synchronized(path) {
                sseEmitter = logLineStorage.getLogLine(path);
                if(null==sseEmitter){
                    logger.info("build sseEmitter for path {}",path);
                    sseEmitter = new SseEmitter(600000L);
                    logLineStorage.setLogLine(path,sseEmitter);
                    SseEmitter sseEmitter1 = sseEmitter;
                    logLineThreadPool.addTask(()->{
                        fileAnalyser.fileTail(path,sseEmitter1);
                    });
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sseEmitter;
    }
}
