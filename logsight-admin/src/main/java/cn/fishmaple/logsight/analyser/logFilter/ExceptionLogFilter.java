package cn.fishmaple.logsight.analyser.logFilter;

import java.util.List;

public class ExceptionLogFilter implements LogFilter{
    private final String SPLIT_STRING = "---";
    @Override
    public FiltedState filtered(List<String> lines) {
        FiltedState filtedState = new FiltedState();
        filtedState.setEndFlag(true);
        for(String line:lines){
            int indexSplit = line.indexOf(SPLIT_STRING);
            int indexLevel = line.indexOf(LogFilterConsts.ERROR_LOGLEVEL);
            if((indexLevel>-1&&indexLevel<indexSplit)||line.contains("Exception in")||line.contains("\tat")||line.contains("Exception:")){
                filtedState.getLines().add(line);
            }
        }
        return filtedState;
    }
}
