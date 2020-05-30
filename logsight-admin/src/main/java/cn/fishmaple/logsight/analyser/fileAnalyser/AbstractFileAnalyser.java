package cn.fishmaple.logsight.analyser.fileAnalyser;

import cn.fishmaple.logsight.util.FileUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractFileAnalyser implements FileAnalyser {

    Map<String,Integer> map=null;

    @Override
    public Map<String, Integer> getFileTypeMap() {
        if(null==map){
            map = new HashMap<String,Integer>();
            map.put("out",TEXT_FILE);
            map.put("log",TEXT_FILE);
            map.put("gz",GZIP_FILE);
        }
        return map;
    }

    public Integer getFileType(String filePathName){
        return getFileTypeMap().get(FileUtil.ext(filePathName));
    }


}
