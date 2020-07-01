package cn.fishmaple.logsight.analyser.fileAnalyser;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public interface FileAnalyser {
    final int TEXT_FILE = 0;
    final int GZIP_FILE = 1;
    final int BINARY_FILE = 2;

     Integer getFileType(String fileName);
     String getFileExt(String fileName);
     InputStream convertFile(String fileName);
     Map<String,Integer> getFileTypeMap();
     boolean needHandle(String fileName);
     void fileTail(String fileName, SseEmitter sseEmitter);
}
