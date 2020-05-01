package cn.fishmaple.logsight.analyser;

import cn.fishmaple.logsight.analyser.object.CommandAction;
import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.SearchResult;

import java.util.List;

public interface CommandAnalyser {
    public List<SearchResult> baseSearch(SearchAction searchAction);
    public String baseSearchTemplate();
}
