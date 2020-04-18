package cn.fishmaple.logsight.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ThreadPoolService {

    private ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
    @PostConstruct
    public void init(){
        taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(100);
        taskExecutor.setMaxPoolSize(500);
        //任务队列最大长度
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setKeepAliveSeconds(2000);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
    }

    public void addTask(Runnable task) {
        taskExecutor.execute(task);
    }
    //Callable任务
    public Future addReturnedTask(Callable task){
        return taskExecutor.submit(task);
    }
}
