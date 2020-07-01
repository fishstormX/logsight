package cn.fishmaple.logsight.core;

import cn.fishmaple.logsight.config.ConfigLoader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
@Component
public class LogLineThreadPool {
    @Autowired
    ConfigLoader configLoader;
    private ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
    @PostConstruct
    public void init(){
        int coreThread = configLoader.getIntConfig("num.logline.thread",10);
        taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("logline");
        taskExecutor.setCorePoolSize(coreThread);
        taskExecutor.setMaxPoolSize(coreThread);
        //任务队列最大长度
        taskExecutor.setQueueCapacity(0);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
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
