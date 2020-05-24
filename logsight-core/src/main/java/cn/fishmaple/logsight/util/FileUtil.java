package cn.fishmaple.logsight.util;

import cn.fishmaple.logsight.LogsightCoreApplication;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

public class FileUtil {
    public static File getByClassPath(String path){
        URL resource = LogsightCoreApplication.class.getClassLoader().getResource("db/logsight.sql");
        File file = new File(resource.getFile());
        return file;
    }

    public static File File2Response(HttpServletResponse response,String path){

        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName = new String("mimi.log");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream ouputStream = response.getOutputStream();
        fileSpiltService.timeSplitWithOutputStream(new Date(startTime), new Date(endTime), f, ouputStream);
        ouputStream.flush();
        ouputStream.close();

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

}
