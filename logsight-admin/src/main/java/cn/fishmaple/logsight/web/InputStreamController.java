package cn.fishmaple.logsight.web;

import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.shell.ProcessThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class InputStreamController {
    @Autowired
    private ProcessThread processThread;

    @GetMapping("shellEvent")
    public Mono<Result> getEvent(@RequestParam String exec){
         return Mono.just(
                new Result<String>(processThread.execute(exec))
        );
    }

}
