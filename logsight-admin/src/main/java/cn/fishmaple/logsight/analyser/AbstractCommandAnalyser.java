package cn.fishmaple.logsight.analyser;

import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.object.SearchResult;
import cn.fishmaple.logsight.shell.ProcessThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractCommandAnalyser implements CommandAnalyser{
    @Autowired
    ProcessThread processThread;
    public List<SearchResult> baseSearch(SearchAction searchAction){
        List<SearchResult> list = new ArrayList<>();
        for(String file:searchAction.getFiles()){
            list.add(new SearchResult(file,processThread.execute(baseSearchTemplate()
                    .replace("{file}",file)
                    .replace("{target}",searchAction.getTarget()))));
        }
        return list;
    }


}
