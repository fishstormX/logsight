package cn.fishmaple.logsight.analyser.commandAnalyser;

import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.SearchResult;
import cn.fishmaple.logsight.shell.ProcessThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public abstract class AbstractCommandAnalyser implements CommandAnalyser {
    @Autowired
    ProcessThread processThread;
    @Override
    public List<SearchResult> baseSearch(SearchAction searchAction){
        List<SearchResult> list = new ArrayList<>();
        for(String file:searchAction.getFiles()){
            String s = processThread.execute(baseSearchTemplate()
                    .replace("{file}",file)
                    .replace("{target}",searchAction.getTarget()));
            list.add(new SearchResult(file,Arrays.asList(s.split("\\\n")))) ;
        }
        return list;
    }

    @Override
    public InputStream forceLogStream(FileStreamAction fileStreamAction) {
        Assert.notNull(fileStreamAction.getFile(),"FileName can not be null!");
        return processThread.execute2stream(baseLogStreamTemplate().replace("{file}",fileStreamAction.getFile()));
    }
}
