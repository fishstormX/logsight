package cn.fishmaple.logsight.service.sighting;

import cn.fishmaple.logsight.analyser.commandAnalyser.CommandAnalyser;
import cn.fishmaple.logsight.analyser.object.SearchAction;
import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.object.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SightingService {
    @Autowired
    LogFieldFileMapper logFieldFileMapper;
    @Autowired
    CommandAnalyser commandAnalyser;
    public List<SearchResult> getSearchPInf(String keyword, Integer fieldId){
        Set<String> fileList = logFieldFileMapper.getFilesForSearchByFieldId(fieldId);
        StringBuilder stringBuilder = new StringBuilder();
        return commandAnalyser.baseSearch(new SearchAction(fileList,keyword));
    }

}
