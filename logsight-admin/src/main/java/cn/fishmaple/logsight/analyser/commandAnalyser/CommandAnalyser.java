package cn.fishmaple.logsight.analyser.commandAnalyser;

import cn.fishmaple.logsight.analyser.object.FileStreamAction;
import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.object.SearchResult;

import java.io.InputStream;
import java.util.List;

public interface CommandAnalyser {
    List<SearchResult> baseSearch(SearchAction searchAction);
    String baseSearchTemplate();
    String baseLogStreamTemplate();
    InputStream forceLogStream(FileStreamAction fileStreamAction);
}
