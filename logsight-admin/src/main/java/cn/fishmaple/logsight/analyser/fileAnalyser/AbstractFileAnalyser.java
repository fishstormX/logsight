package cn.fishmaple.logsight.analyser.fileAnalyser;

import cn.fishmaple.logsight.util.FileUtil;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractFileAnalyser implements FileAnalyser {

    Map<String,Integer> map=null;

    String fileTypes[] = {"out","log","","gz"};
    @Override
    public Map<String, Integer> getFileTypeMap() {
        if(null==map){
            map = new HashMap<String,Integer>();
            map.put("out",TEXT_FILE);
            map.put("log",TEXT_FILE);
            map.put("",TEXT_FILE);
            map.put("gz",GZIP_FILE);
        }
        return map;
    }

    @Override
    public String getFileExt(String fileName) {
        String ext = FileUtil.ext(fileName);
        if(!FileUtil.isExtLegal(ext)){
            Integer index = fileName.indexOf(".",fileName.indexOf(".")+1);
            if(index>0){
                fileName = fileName.substring(0,index);
            }
            ext = FileUtil.ext(fileName);
        }
        return ext;
    }

    @Override
    public Integer getFileType(String filePathName){
        Integer i = getFileTypeMap().get(getFileExt(filePathName));
        return null==i?TEXT_FILE:i;
    }

    @Override
    public boolean needHandle(String fileName) {
        String fileExts[] = {"out","log","","gz"};
        if (Arrays.asList(fileExts).contains(getFileExt(fileName))){
            return true;
        }
        return false;
    }

    @Override
    public void fileTail(String fileName, SseEmitter sseEmitter) {
        try{
            File file = new File(fileName);
            if(!file.exists()){
                return;
            }
            RandomAccessFile raf=new RandomAccessFile(file, "r");
            raf.seek(raf.getFilePointer());
            new Thread(()->{
                try {
                    String line =null;
                    while(null!=sseEmitter){
                        line = raf.readLine();
                        if(line.equals("")){
                            Thread.sleep(1000);
                            continue;
                        }
                        line = new String(line.getBytes(),"UTF-8");
                        sseEmitter.send(line);
                }
                } catch (IOException|InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
