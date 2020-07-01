package cn.fishmaple.logsight.analyser.commandAnalyser;

import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.SearchResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DefaultCommandAnalyser extends AbstractCommandAnalyser{
    @Override
    public String baseSearchTemplate() {
        return "tac \"{file}\"|grep \"{target}\"";
    }
    @Override
    public String baseLogStreamTemplate() {
        return "tail -f \"{file}\"";
    }
    @Override
    public List<SearchResult> baseSearch(SearchAction searchAction){
        List<SearchResult> list = new ArrayList<>();
        for(String file:searchAction.getFiles()){
            String s = processThread.execute(baseSearchTemplate()
                    .replace("{file}",file)
                    .replace("{target}",targetHandler(searchAction.getTarget())));
            list.add(new SearchResult(file, Arrays.asList(s.split("\\\n")))) ;
        }
        return list;
    }
    private String targetHandler(String target){
        return target
                .replace("\"","\\\"")
                .replace("\\","\\\\\\\\")
                .replace("[","\\[")
                .replace("]","\\]");
    }
}
