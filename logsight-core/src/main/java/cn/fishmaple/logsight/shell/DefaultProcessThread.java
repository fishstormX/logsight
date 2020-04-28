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
        } catch (ExecutionException|InterruptedException  e) {
            e.printStackTrace();
        }
        return null;
    }

    public Process getProcess(String... commands) throws ExecutionException, InterruptedException {
            List<String> commandList = new ArrayList<>();
            for(String command :commands){
                commandList.addAll(Arrays.asList(command.split(" ")));
            }
           return commands2Process(commandList);
    }

    public Process getShellProcess(String... commands) throws ExecutionException, InterruptedException {
            List<String> commandList = new ArrayList<>();
            commandList.add("/bin/sh");
            commandList.add("-c");
            StringBuilder stringBuilder = new StringBuilder();
            for(String command:commands){
                stringBuilder.append(command).append(" ");
            }
            commandList.add(stringBuilder.toString());
            logger.info(stringBuilder.toString());
           return commands2Process(commandList);
    }
    @Override
    public InputStream execute2stream(String... commands){
        Assert.notEmpty(commands,"command can not be null!");
        try {
            return getProcess(commands).getInputStream();
        } catch (ExecutionException |InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Process commands2Process(List<String> commandList){
        ProcessBuilder processBuilder = new ProcessBuilder(commandList);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
        return process;
    }



}
