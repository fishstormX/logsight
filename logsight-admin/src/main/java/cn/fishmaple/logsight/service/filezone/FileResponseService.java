package cn.fishmaple.logsight.service.filezone;

import cn.fishmaple.logsight.analyser.TimeAnalyser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Date;

@Service
public class FileResponseService {

    public void file2Response(String path, HttpServletResponse response){
        File file = new File(path);
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = null;
        try {
            response.setHeader("Content-disposition", "attachment;filename=" +  URLEncoder.encode(file.getName(),"UTF-8"));
            outputStream = response.getOutputStream();
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
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
