package cn.fishmaple.logsight.analyser.fileAnalyser;

import cn.fishmaple.logsight.analyser.logFilter.FiltedState;
import cn.fishmaple.logsight.analyser.logFilter.LogFilter;
import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.core.LogLineStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger  = LoggerFactory.getLogger(this.getClass());

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
        FiltedState filtedState = new FiltedState();
        filtedState.setLines(logs);
        if(null==fileStreamAction){
            return filtedState;
        }
        if(null!=fileStreamAction.getLogFilters()){
            for(LogFilter logFilter :fileStreamAction.getLogFilters()){
                filtedState = logFilter.filtered(filtedState.getLines());
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
        File file = new File(fileStreamAction.getFile());
        if(!file.exists()){
            return;
        }
        String line =null;
        try {
            RandomAccessFile raf=new RandomAccessFile(file, "r");
            raf.seek(raf.length());
            int i=0;
            List<String> lines = new LinkedList<>();
            Queue<List<String>> lineKeeper = new LinkedList<>();
            while(null!=sseEmitter){
                line = raf.readLine();
                if(i>9){
                    FiltedState filtedState = filterLog(fileStreamAction, lines);
                    lines = filtedState.getLines();
                    lineKeeper.offer(lines);
                    if(filtedState.isEndFlag()){
                        sendLogs(lineKeeper,sseEmitter);
                    }
                    i=0;
                    lines = new LinkedList<>();
                }
                if(null==line||line.equals("")){
                    logger.info("EmptyLine{}",lines);
                    Thread.sleep(2000);
                    if (!lines.isEmpty()){
                        i = 10;
                    }
                    continue;
                }
                line = new String(line.getBytes("ISO-8859-1"),"UTF-8");
                lines.add(line);
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            logLineStorage.rmLogLine(fileStreamAction.getFile());
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
