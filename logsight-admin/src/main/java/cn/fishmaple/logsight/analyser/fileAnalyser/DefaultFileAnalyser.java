package cn.fishmaple.logsight.analyser.fileAnalyser;

import cn.fishmaple.logsight.analyser.logFilter.FiltedState;
import cn.fishmaple.logsight.analyser.logFilter.LogFilter;
import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.core.LogLineStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.zip.GZIPInputStream;
@Component
public class DefaultFileAnalyser extends AbstractFileAnalyser {
    @Autowired
    private LogLineStorage logLineStorage;

    @Override
    public InputStream convertFile(String fileName) {
        try {
            switch (getFileType(fileName)) {
                case TEXT_FILE:
                    return new FileInputStream(fileName);
                case GZIP_FILE:
                    return new GZIPInputStream(new FileInputStream(fileName));
                case -1:
                    return null;
            }
        }catch (IOException e){
            return null;
        }
        return null;
    }

    @Override
    public FiltedState filterLog(FileStreamAction fileStreamAction, List<String> logs) {
        FiltedState filtedState = null;
        if(null==fileStreamAction){
            filtedState = new FiltedState();
            filtedState.setLines(logs);
            return filtedState;
        }
        if(null!=fileStreamAction.getLogFilters()){
            for(LogFilter logFilter :fileStreamAction.getLogFilters()){
                filtedState = logFilter.filtered(logs);
            }
        }
        if(fileStreamAction.isStrFiltered()){
            for(String log:logs){
                if(log.contains(fileStreamAction.getFilterString())){
                    for (Iterator<String> iterator = logs.iterator();iterator.hasNext();) {
                        String line = iterator.next();
                        if (!line.contains(fileStreamAction.getFilterString())) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        return filtedState;
    }


    @Override
    public void fileTail(SseEmitter sseEmitter, FileStreamAction fileStreamAction) {
        try{
            File file = new File(fileStreamAction.getFile());
            if(!file.exists()){
                return;
            }
            RandomAccessFile raf=new RandomAccessFile(file, "r");
            raf.seek(raf.length());
            new Thread(()->{
                try {
                    String line =null;
                    int i=0;
                    List<String> lines = new LinkedList<>();
                    Queue<List<String>> lineKeeper = new LinkedList<>();
                    while(null!=sseEmitter){
                        line = raf.readLine();
                        if(null==line||line.equals("")){
                            Thread.sleep(1000);
                            continue;
                        }
                        line = new String(line.getBytes("ISO-8859-1"),"UTF-8");
                        lines.add(line);
                        if(i==9){
                            FiltedState filtedState = filterLog(fileStreamAction, lines);
                            lines = filtedState.getLines();
                            lineKeeper.offer(lines);
                            if(filtedState.isEndFlag()){
                                sendLogs(lineKeeper,sseEmitter);
                            }
                        }
                        i++;
                    }
                } catch (IOException|InterruptedException e) {
                    logLineStorage.rmLogLine(fileStreamAction.getFile());
                    e.printStackTrace();
                }
            }).start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sendLogs(Queue<List<String>>lineKeeper,SseEmitter sseEmitter) throws IOException {
        while(lineKeeper.size()>0){
            List<String> list = lineKeeper.poll();
            if(null != list){
                for(String line:list){
                    sseEmitter.send(line);
                }
            }
        }
    }
}
