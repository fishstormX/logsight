package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.service.logline.LoglineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;



import java.util.concurrent.TimeUnit;

@RestController
public class LogLineSse {
    @Autowired
    private LoglineService loglineService;
    @Autowired
    private LogFieldFileMapper logFieldFileMapper;
    @GetMapping(value = "/logLine",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter ssePush(@RequestParam String path,@RequestParam(required = false) String searchContent) {
        return loglineService.buildSseEmitter(path,searchContent);
    }
    @GetMapping(value = "/logLine/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter retrieve(@RequestParam Integer fileId,@RequestParam(required = false) String searchContent) {
        return loglineService.buildSseEmitter(logFieldFileMapper.getFilePath(fileId),searchContent);
    }

}
