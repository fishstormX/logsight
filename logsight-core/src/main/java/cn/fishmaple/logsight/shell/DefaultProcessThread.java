package cn.fishmaple.logsight.shell;

import cn.fishmaple.logsight.thread.CustomThreadPool;
import cn.fishmaple.logsight.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class DefaultProcessThread implements ProcessThread{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CustomThreadPool customThreadPool;
    @PostConstruct
    public  void init(){
        logger.debug("DefaultProcessThreadPool Init:");
        this.customThreadPool=new CustomThreadPool();
    }

    @Override
    public String execute(String... commands){
        Assert.notEmpty(commands,"command can not be null!");
        try {
            return StreamUtil.inStream2String(this.getShellProcess(commands).getInputStream());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Process getProcess(String... commands) throws ExecutionException, InterruptedException {
        Future<Process> future= customThreadPool.addReturnedTask(
                ()->{
                    List<String> commandList = new ArrayList<>();
                        // 创建命令集合
                        for(String command :commands){
                            commandList.addAll(Arrays.asList(command.split(" ")));
                        }

                        // ProcessBuilder是一个用于创建操作系统进程的类，它的start()方法用于启动一个进行
                        ProcessBuilder processBuilder = new ProcessBuilder(commandList);
                        // 启动进程
                        Process process = null;
                        try {
                            process = processBuilder.start();
                        } catch (IOException e) {
                            //TODO
                            e.printStackTrace();
                        }
                        return process;
                });
        return future.get();
    }

    public Process getShellProcess(String... commands) throws ExecutionException, InterruptedException {
        Future<Process> future= customThreadPool.addReturnedTask(
                ()->{
                    List<String> commandList = new ArrayList<>();
                    // 创建命令集合
                    commandList.add("/bin/sh");
                    commandList.add("-c");
                    StringBuilder stringBuilder = new StringBuilder();
                    for(String command:commands){
                        stringBuilder.append(command).append(" ");
                    }
                    commandList.add(stringBuilder.toString());
                    logger.info(stringBuilder.toString());
                    // ProcessBuilder是一个用于创建操作系统进程的类，它的start()方法用于启动一个进行
                    ProcessBuilder processBuilder = new ProcessBuilder(commandList);
                    // 启动进程
                    Process process = null;
                    try {
                        process = processBuilder.start();
                    } catch (IOException e) {
                        //TODO
                        e.printStackTrace();
                    }
                    return process;
                });
        return future.get();
    }
    @Override
    public InputStream execute2stream(String... commands){
        Assert.notEmpty(commands,"command can not be null!");
        try {
            return getProcess(commands).getInputStream();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
