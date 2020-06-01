package cn.fishmaple.logsight.util;

import cn.fishmaple.logsight.LogsightCoreApplication;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private  static List<String> extList;
    static {
        String[] exts = {"txt","log","md","gz",};
        extList = Arrays.asList(exts);
    }

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

    public static String getFileMLength(File file){
        if(file.exists()&&file.isFile()){
            return String.format("%.2f", (double)file.length()/1024/1024);
        }else{
            return "0";
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

    public static Boolean isExtLegal(String ext) {
        if(extList.contains(ext)){
            return true;
        }return false;
    }

}
