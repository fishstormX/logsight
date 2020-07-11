package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.logline.LoglineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("logline")
public class LogLineAPI {
    @Autowired
    private LoglineService loglineService;
    @Autowired
    private LogFieldFileMapper logFieldFileMapper;
    @GetMapping(value = "/highlight")
    public Result<List<String>> ssePush(@RequestParam(defaultValue = "0") Integer onlyException, @RequestParam(defaultValue = "ALL")String logLevel) {
        return new Result(loglineService.getHighLightThings(onlyException==1,logLevel));
    }

}
