package cn.fishmaple.logsight.analyser.fileAnalyser;

import java.io.FileInputStream;
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
                case -1:
                    return null;
            }
        }catch (IOException e){
            return null;
        }
        return null;
    }


}
