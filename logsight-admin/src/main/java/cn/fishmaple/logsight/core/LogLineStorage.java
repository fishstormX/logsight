package cn.fishmaple.logsight.core;

import cn.fishmaple.logsight.object.FileDownloadStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogLineStorage {
    private Map<String, SseEmitter> logLineMap;

    public void setLogLine(String id,SseEmitter sseEmitter) {
        logLineMap.put(id, sseEmitter);
    }

    public SseEmitter getLogLine(String id) {
        return this.logLineMap.get(id);
    }

    @PostConstruct
    public void init(){
        logLineMap = new HashMap<String,SseEmitter>();
    }
}
