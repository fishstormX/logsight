package cn.fishmaple.logsight.service.logline;

import cn.fishmaple.logsight.analyser.commandAnalyser.CommandAnalyser;
import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.core.LogLineStorage;
import cn.fishmaple.logsight.core.LogLineThreadPool;
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
    LogLineThreadPool logLineThreadPool;

    public SseEmitter buildSseEmitter(String path){
        InputStream inputStream = commandAnalyser.forceLogStream(new FileStreamAction(path));
        SseEmitter sseEmitter;
        try{
            synchronized(path) {
                sseEmitter = logLineStorage.getLogLine(path);
                if(null==sseEmitter){
                    sseEmitter = new SseEmitter(600000L);
                    logLineStorage.setLogLine(path,sseEmitter);
                }
            }
            SseEmitter sseEmitter1 = sseEmitter;
            System.out.println("--");
            logLineThreadPool.addTask(()->{
                try (InputStream in = new BufferedInputStream(inputStream)){
                    byte[] b = new byte[1024];
                    int len = 0;
                    while (null!=logLineStorage.getLogLine(path)) {
                        len = in.read(b);
                        if (len > 0) {
                            sseEmitter1.send(new String(b, StandardCharsets.UTF_8));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }System.out.println("---");
        return sseEmitter;
    }
}
