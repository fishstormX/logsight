package cn.fishmaple.logsight.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomThreadPool {
    private ThreadPoolTaskExecutor taskExecutor;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomThreadPool(){
        this.init();
    }

    public void init(){
        taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setKeepAliveSeconds(2000);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
    }

    public void addTask(Runnable task) {
        taskExecutor.execute(task);
    }
    public <T> Future<T> addReturnedTask(Callable<T> task){
        return taskExecutor.submit(task);
    }

    public void shutdown() {
        try{
            taskExecutor.shutdown();
        }catch(Exception ex) {
            logger.error("关闭ThreadPoolTaskUtil失败", ex);
        }
    }
}
