package cn.fishmaple.logsight.service.filezone;

import cn.fishmaple.logsight.analyser.fileAnalyser.DefaultFileAnalyser;
import cn.fishmaple.logsight.analyser.fileAnalyser.FileAnalyser;
import cn.fishmaple.logsight.core.FileDownLoadStorage;
import cn.fishmaple.logsight.object.FileDownloadStatus;
import cn.fishmaple.logsight.object.LogAnalyseState;
import cn.fishmaple.logsight.object.fileAnalyse.BaseFile;
import cn.fishmaple.logsight.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Service
public class FileAnalyseService {
    @Autowired
    private FileZoneService fileZoneService;
    @Autowired
    private FileDownLoadStorage fileDownLoadStorage;

    public FileDownloadStatus getStatus(LogAnalyseState logAnalyseState,boolean init){
        FileDownloadStatus fileDownloadStatus = new FileDownloadStatus();
        List<BaseFile> fileList = new ArrayList<>();
        List<BaseFile> unSelectFilelist = new ArrayList<>();
        Map<String,Integer> fileRateMap = new LinkedHashMap<>();
        if(init){
            for(String fileName:logAnalyseState.getFiles()){
                fileList.add(new BaseFile(fileName,""));
                fileRateMap.put(logAnalyseState.getPath() +"/"+ fileName, 0);
            }
            String uuid = UUID.randomUUID().toString().replace("-","").replace("16","a");
            fileDownloadStatus.setId(uuid);
            fileDownLoadStorage.addFileDownloadStatus(fileDownloadStatus);
        }else {
            String path = fileZoneService.getNodePath(logAnalyseState.getFileId());
            File file = new File(path);
            String parentPath = file.getParent();
            logAnalyseState.setPath(parentPath);
            String[] files = new File(parentPath).list();
            List<String> listTmp = Arrays.asList(files);
            Collections.sort(listTmp);
            BaseFile mainLogFile = null;
            FileAnalyser fileAnalyser = new DefaultFileAnalyser();
            for (String fileName : listTmp) {
                File tmp = new File(parentPath + "/" + fileName);
                if (tmp.isFile()) {
                    boolean handleFlag = fileAnalyser.needHandle(fileName);
                    if (handleFlag && mainLogFile == null && fileName.indexOf(".", fileName.indexOf(".") + 1) < 0) {
                        mainLogFile = new BaseFile(fileName, FileUtil.getFileMLength(tmp) + "M");
                        continue;
                    }
                    fileRateMap.put(parentPath + "/" + fileName, 0);
                    if (handleFlag) {
                        fileList.add(new BaseFile(fileName, FileUtil.getFileMLength(tmp) + "M"));
                    } else {
                        unSelectFilelist.add(new BaseFile(fileName, FileUtil.getFileMLength(tmp) + "M"));
                    }
                }
            }
            if (null != mainLogFile) {
                fileRateMap.put(parentPath + "/" + mainLogFile, 0);
                fileList.add(mainLogFile);
            }
        }
        fileDownloadStatus.setFiles(fileList);
        fileDownloadStatus.setUnSelectFiles(unSelectFilelist);
        fileDownloadStatus.setFileRate(fileRateMap);
        fileDownloadStatus.setLogAnalyseState(logAnalyseState);
        return fileDownloadStatus;
    }

    public FileDownloadStatus getStatus(String id){
        return fileDownLoadStorage.getFileDownloadStatus(id);
    }
    @Nullable
    public InputStream getInputStream(FileAnalyser fileAnalyser,String pathName) {
       return fileAnalyser.convertFile(pathName);
    }

}
