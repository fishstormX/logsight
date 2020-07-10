package cn.fishmaple.logsight.core;

import cn.fishmaple.logsight.object.FileDownloadStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogLineStorage {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, SseEmitter> logLineMap;

    public void setLogLine(String id,SseEmitter sseEmitter) {
        logLineMap.put(id, sseEmitter);
    }

    public void rmLogLine(String id){
        logger.info("rm Sse {}",id);
        logLineMap.remove(id);
    }

    public SseEmitter getLogLine(String id) {
        return this.logLineMap.get(id);
    }

    @PostConstruct
    public void init(){
        logLineMap = new HashMap<String,SseEmitter>();
    }
}
