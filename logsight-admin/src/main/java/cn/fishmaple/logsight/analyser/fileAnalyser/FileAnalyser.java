package cn.fishmaple.logsight.analyser.fileAnalyser;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public interface FileAnalyser {
    final int TEXT_FILE = 0;
    final int GZIP_FILE = 1;
    final int BINARY_FILE = 2;

    public Integer getFileType(String fileName);
    public InputStream convertFile(String fileName);
    public Map<String,Integer> getFileTypeMap();
}
