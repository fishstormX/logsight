package cn.fishmaple.logsight.handler;

import cn.fishmaple.logsight.util.EnvUtil;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FileScanHandler {
    public Collection<String> scanFile(String filePath){
        String pathloads[] = filePath.split("\\\\");
        Queue<String> queueReturn=null;
        if(filePath.contains("*")){
            String rootPath = null;
            if(EnvUtil.shellAbled()){
                rootPath="\\";
            }else{
                rootPath=pathloads[0];
            }
            Queue<String> queue = new LinkedList<>();
            queue.add(rootPath);
            for(String pathload:pathloads){
                if(pathload.contains("*")){
                    FileFilter fileFilter = new WildcardFileFilter(pathload);
                    int qSize = queue.size();
                    for(int i =0;i<qSize;i++){
                        String tmpPath = queue.poll();
                        File file = new File(tmpPath);
                        if(file.isFile()||!file.exists()){
                            continue;
                        }
                        Arrays.stream(file.listFiles(fileFilter)).forEach(
                                s->{
                                    queue.add(s.getAbsolutePath());
                                }); }
                }else if(!pathload.isEmpty()&&!pathload.contains(":")){
                    int qSize = queue.size();
                    for(int i =0;i<qSize;i++){
                        String file = queue.poll()+"\\"+pathload;
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
