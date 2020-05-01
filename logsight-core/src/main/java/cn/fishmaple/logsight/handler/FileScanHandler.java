package cn.fishmaple.logsight.handler;

import cn.fishmaple.logsight.util.EnvUtil;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

@Component
public class FileScanHandler {
    public Collection<String> scanFile(String filePath){
        String splitStr ="\\";
        String pathloads[] = null;
        if(!EnvUtil.isWindows()){
            pathloads = filePath.split("/");
            splitStr = "/";
        }else{
            pathloads = filePath.split("\\\\");
        }
        if(splitStr.equals(String.valueOf(filePath.charAt(filePath.length()-1)))){
            return new LinkedList<>();
        }
        Queue<String> queueReturn=null;
        if(filePath.contains("*")){
            String rootPath = null;
            if(!EnvUtil.isWindows()){
                rootPath="/";
            }else{
                rootPath=pathloads[0];
            }
            Queue<String> queue = new LinkedList<>();
            queue.add(rootPath);
            for(String pathload:pathloads){
                if(pathload.contains("*")){
                    FileFilter fileFilter = new WildcardFileFilter(pathload);
                    int qSize = queue.size();
                    for(int i =0;i<qSize;i++) {
                        String tmpPath = queue.poll();
                        File file = new File(tmpPath + (splitStr));
                        if (file.isFile() || !file.exists()) {
                            continue;
                        }
                        File[] files = file.listFiles(fileFilter);
                        if (null != files&&files.length>0) {
                            Arrays.stream(files).forEach(
                                    s -> {
                                        queue.add(s.getAbsolutePath());
                                    });
                        }
                    }
                }else if(null!=pathload&&!pathload.contains(":")){
                    int qSize = queue.size();
                    for(int i =0;i<qSize;i++){
                        String file = queue.poll()+splitStr+pathload;
                        File file1 = new File(file);
                        if(!file1.exists()){
                            continue;
                        }
                        queue.add(file);
                    }
                }
            }
            queueReturn=queue;
        }else{
            queueReturn = new LinkedList<>();
            if(new File(filePath).exists()){
                queueReturn.add(filePath);
            }
        }
        return queueReturn;
    }
}
