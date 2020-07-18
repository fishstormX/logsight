package cn.fishmaple.logsight.service.filezone;

import cn.fishmaple.logsight.analyser.DefaultTimeAnalyser;
import cn.fishmaple.logsight.analyser.TimeAnalyser;
import cn.fishmaple.logsight.analyser.fileAnalyser.DefaultFileAnalyser;
import cn.fishmaple.logsight.analyser.fileAnalyser.FileAnalyser;
import cn.fishmaple.logsight.core.FileDownLoadStorage;
import cn.fishmaple.logsight.object.FileDownloadStatus;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

@Service
public class FileSplitService {
    @Autowired
    FileDownLoadStorage fileDownLoadStorage;
    @Autowired
    FileAnalyseService fileAnalyseService;

    public void timeHandledInputStreamWithOutputStream(Date startTime, Date endTime, String filepath, OutputStream outputStream, Integer timeAnalyserId){
        int state = -1;
        TimeAnalyser defaultTimeAnalyser = new DefaultTimeAnalyser();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            int bytesRead = channel.read(buffer);
            ByteBuffer stringBuffer = ByteBuffer.allocate(20);
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    if (stringBuffer.hasRemaining()) {
                        stringBuffer.put(b);
                    } else {
                        stringBuffer = reAllocate(stringBuffer);
                        stringBuffer.put(b);
                    }
                    if (b == 10 || b == 13) {
                        stringBuffer.flip();
                        final String line = Charset.forName("utf-8").decode(stringBuffer.asReadOnlyBuffer()).toString();
                        if (line.length() > 23) {
                            if (state == -1) {
                                Date date = defaultTimeAnalyser.timeFormat(line);
                                if (date != null && date.after(startTime)) {
                                    state = 0;
                                }
                            } else if (state == 0) {
                                Date date = defaultTimeAnalyser.timeFormat(line);
                                if (date != null && date.after(endTime)) {
                                    state = 1;
                                }
                            } else {
                                break;
                            }
                        }
                        if (state == 0) {
                            int len = stringBuffer.limit() - stringBuffer.position();
                            byte[] bytes1 = new byte[len];
                            stringBuffer.get(bytes1);
                            outputStream.write(bytes1);
                        }
                        stringBuffer.clear();
                    }
                }
                buffer.clear();
                bytesRead = channel.read(buffer);
                if (state == 1) {
                    break;
                }
            }
        }catch (IOException e){

        }
    }

    public void timeHandledFileWithNiOutputStream(Date startTime, Date endTime, String filepath, OutputStream outputStream, Integer timeAnalyserId){
        int state = -1;
        TimeAnalyser defaultTimeAnalyser = new DefaultTimeAnalyser();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            int bytesRead = channel.read(buffer);
            ByteBuffer stringBuffer = ByteBuffer.allocate(20);
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    if (stringBuffer.hasRemaining()) {
                        stringBuffer.put(b);
                    } else {
                        stringBuffer = reAllocate(stringBuffer);
                        stringBuffer.put(b);
                    }
                    if (b == 10 || b == 13) {
                        stringBuffer.flip();
                        final String line = Charset.forName("utf-8").decode(stringBuffer.asReadOnlyBuffer()).toString();
                        if (line.length() > 23) {
                            if (state == -1) {
                                Date date = defaultTimeAnalyser.timeFormat(line);
                                if (date != null && date.after(startTime)) {
                                    state = 0;
                                }
                            } else if (state == 0) {
                                Date date = defaultTimeAnalyser.timeFormat(line);
                                if (date != null && date.after(endTime)) {
                                    state = 1;
                                }
                            } else {
                                break;
                            }
                        }
                        if (state == 0) {
                            int len = stringBuffer.limit() - stringBuffer.position();
                            byte[] bytes1 = new byte[len];
                            stringBuffer.get(bytes1);
                            outputStream.write(bytes1);
                        }
                        stringBuffer.clear();
                    }
                }
                buffer.clear();
                bytesRead = channel.read(buffer);
                if (state == 1) {
                    break;
                }
            }
        }catch (IOException e){

        }
    }

    public void handledFileWithNiOutputStream(String filepath, OutputStream outputStream){
        int state = -1;
        TimeAnalyser defaultTimeAnalyser = new DefaultTimeAnalyser();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            int bytesRead = channel.read(buffer);
            ByteBuffer stringBuffer = ByteBuffer.allocate(20);
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    if (stringBuffer.hasRemaining()) {
                        stringBuffer.put(b);
                    } else {
                        stringBuffer = reAllocate(stringBuffer);
                        stringBuffer.put(b);
                    }
                    if (b == 10 || b == 13) {
                        stringBuffer.flip();
                        final String line = Charset.forName("utf-8").decode(stringBuffer.asReadOnlyBuffer()).toString();
                        int len = stringBuffer.limit() - stringBuffer.position();
                        byte[] bytes1 = new byte[len];
                        stringBuffer.get(bytes1);
                        outputStream.write(bytes1);
                        stringBuffer.clear();
                    }
                }
                buffer.clear();
                bytesRead = channel.read(buffer);
                if (state == 1) {
                    break;
                }
            }
        }catch (IOException e){

        }
    }


    public void timeHandledFileWithNiOutputStream(String startTime, String endTime, String filepath, OutputStream outputStream){
        TimeUtil.initedFormatter("yyyy-MM-dd HH:mm:ss");
        timeHandledFileWithNiOutputStream(TimeUtil.parseTimeUnchecked(startTime),TimeUtil.parseTimeUnchecked(endTime),filepath,outputStream,0);
    }

    public void timeSplitWithResponse(String startTime, String endTime, String filepath, HttpServletResponse response){
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName = new File(filepath).getName();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try (OutputStream outputStream =  response.getOutputStream()){
            if(null==startTime||null==endTime){
                handledFileWithNiOutputStream(filepath,outputStream);
            }else {
                timeHandledFileWithNiOutputStream(startTime, endTime, filepath, outputStream);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStream(InputStream inputStream ,Date start,Date end,TimeAnalyser timeAnalyser,OutputStream outputStream){
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bf = new BufferedReader(inputReader);
        String line;
        int state = -2;
        try{
            while ((line = bf.readLine()) != null) {
                if (line.length() > 23) {
                    if (state <0) {
                        Date date = timeAnalyser.timeFormat(line);
                        if(null==date){

                        }else if (date.before(start)) {
                            state = -1;
                        }else{
                            state = 0;
                        }
                    }
                    if (state == 0) {
                        Date date = timeAnalyser.timeFormat(line);
                        if (date != null && date.after(end)) {
                            state = 1;
                        }
                    }
                }
                if (state == 0||state==-2) {
                    line+="\r\n";
                    outputStream.write(line.getBytes());
                }
                if(state==-1){

                }
                if(state==1){
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Boolean needHandle(InputStream inputStream ,Date start,TimeAnalyser timeAnalyser){
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bf = new BufferedReader(inputReader);
        String line;
        Boolean tailFlag=false;
        int i = 0;
        try{
            while ((line = bf.readLine()) != null) {
                if (line.length() > 23) {
                    Date date = timeAnalyser.timeFormat(line);
                    if(null!=date&&date.before(start)){
                        return false;
                    }else if(null!=date&&date.after(start)){
                        return true;
                    }
                }
                i++;
                if(i>1000){
                    return true;
                }
            }
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return true;
        }
    }

    public void uuidWithResponse(String uuid, HttpServletResponse response) {
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        FileDownloadStatus fileDownloadStatus = fileDownLoadStorage.getFileDownloadStatus(uuid);
        FileAnalyser fileAnalyser = new DefaultFileAnalyser();
        TimeAnalyser defaultTimeAnalyser = new DefaultTimeAnalyser();
        TimeUtil.initedFormatter("yyyy-MM-dd HH:mm:ss");
        String fileName = uuid+".log";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try (OutputStream outputStream =  response.getOutputStream()){
            outputStream.write(new byte[0]);
            outputStream.flush();
            boolean tailFlag=false;
            InputStream lastStream = null;
            Map.Entry lastEntry = null;
            for(Map.Entry<String,Integer> entry :fileDownloadStatus.getFileRate().entrySet()){
                InputStream inputStream = fileAnalyseService.getInputStream(fileAnalyser,entry.getKey());
                if(inputStream==null){
                    entry.setValue(-1);
                    continue;
                }else{
                    Date start = TimeUtil.parseTimeUnchecked(fileDownloadStatus.getLogAnalyseState().getStartTime());
                    Date end = TimeUtil.parseTimeUnchecked(fileDownloadStatus.getLogAnalyseState().getEndTime());
                    if(needHandle(inputStream,start,defaultTimeAnalyser)&&!tailFlag){
                        tailFlag=true;
                        if(null!=lastStream){
                            writeStream(lastStream,start,end,defaultTimeAnalyser,outputStream);
                            lastEntry.setValue(100);
                        }
                        writeStream(inputStream,start,end,defaultTimeAnalyser,outputStream);
                        entry.setValue(100);
                    }
                    if(tailFlag){
                        writeStream(inputStream,start,end,defaultTimeAnalyser,outputStream);
                        entry.setValue(100);
                    }
                }
                lastStream = inputStream;
                lastEntry = entry;
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
        final int capacity = stringBuffer.capacity();
        byte[] newBuffer = new byte[capacity * 2];
        System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
        return (ByteBuffer) ByteBuffer.wrap(newBuffer).position(capacity);
    }
}
