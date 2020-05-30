package cn.fishmaple.logsight.util;

import cn.fishmaple.logsight.LogsightCoreApplication;

import java.io.File;
import java.net.URL;

public class FileUtil {
    public static File getByClassPath(String path){
        URL resource = LogsightCoreApplication.class.getClassLoader().getResource("db/logsight.sql");
        File file = new File(resource.getFile());
        return file;
    }

    public static Long getFileLength(String path){
        File file = new File(path);
        if(file.exists()&&file.isFile()){
            return file.length();
        }else{
            return 0L;
        }
    }

    public static String ext(String filename) {
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        String result = filename.substring(index + 1);
        return result;
    }

}
