package cn.fishmaple.logsight.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;



import java.util.concurrent.TimeUnit;

@RestController
public class SseAgent {
    @GetMapping(value = "/logLine",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter retrieve(@RequestParam String path) {
        SseEmitter  emitter = new SseEmitter(600000L);
        new Thread(()->{
            for(int i =0;i<50000;i++){
                try {
                    emitter.send(i+"");
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.completeWithError(e);
                    break;
                }
            }
            emitter.complete();
        }).start();
        return emitter;
    }

}
