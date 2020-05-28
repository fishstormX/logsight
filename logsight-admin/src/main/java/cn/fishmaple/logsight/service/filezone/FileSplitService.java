package cn.fishmaple.logsight.service.filezone;

import cn.fishmaple.logsight.analyser.DefaultTimeAnalyser;
import cn.fishmaple.logsight.analyser.TimeAnalyser;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Date;
@Service
public class FileSplitService {

    public void timeSplitWithOutputStream(Date startTime,Date endTime,String filepath,OutputStream outputStream,Integer timeAnalyserId){
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

    public void timeSplitWithOutputStream(String startTime,String endTime,String filepath,OutputStream outputStream){
        TimeUtil.initedFormatter("yyyy-MM-dd hh:mm:ss");
        timeSplitWithOutputStream(TimeUtil.parseTimeUnchecked(startTime),TimeUtil.parseTimeUnchecked(endTime),filepath,outputStream,0);
    }

    public void timeSplitWithResponse(String startTime, String endTime, String filepath, HttpServletResponse response){
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName = new File(filepath).getName();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try (OutputStream outputStream =  response.getOutputStream()){
            timeSplitWithOutputStream(startTime,endTime, filepath, outputStream);
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
