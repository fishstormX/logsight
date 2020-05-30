package cn.fishmaple.logsight.analyser.fileAnalyser;

import cn.fishmaple.logsight.shell.ProcessThread;
import org.springframework.beans.factory.annotation.Autowired;
import sun.nio.ch.ChannelInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;


public class DefaultFileAnalyser extends AbstractFileAnalyser {


    @Override
    public InputStream convertFile(String fileName) {
        try {
            switch (getFileType(fileName)) {
                case TEXT_FILE:
                    return new FileInputStream(fileName);
                case GZIP_FILE:
                    return new GZIPInputStream(new FileInputStream(fileName));
            }
        }catch (IOException e){
            return null;
        }
        return null;
    }


}
