package cn.fishmaple.logsight.service.logline;

import cn.fishmaple.logsight.core.LogLineStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@Service
public class LoglineService {
    @Autowired
    LogLineStorage logLineStorage;

    public SseEmitter buildSseEmitter(String id){
        synchronized(id) {
            SseEmitter sseEmitter = logLineStorage.getLogLine(id);
            if(null==sseEmitter){
                sseEmitter = new SseEmitter(600000L);
                logLineStorage.setLogLine(id,sseEmitter);
                return sseEmitter;
            }else{
                return sseEmitter;
            }
        }
    }
}
