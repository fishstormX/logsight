package cn.fishmaple.logsight.analyser.logFilter;

import java.util.List;

public class LogLevelLogFilter implements LogFilter{
    private String[] logLevel;
    private final String SPLIT_STRING = "---";
    public LogLevelLogFilter(String... logLevel) {
        this.logLevel = logLevel;
    }

    public String[] getLogLevel() {
        return logLevel;
    }

    @Override
    public FiltedState filtered(List<String> lines) {
        FiltedState filtedState = new FiltedState();
        filtedState.setEndFlag(true);
        for(String line:lines){
            for(String clogLevel:logLevel){
                int indexSplit = line.indexOf(SPLIT_STRING);
                if(indexSplit<0){
                    break;
                }else{
                    int indexLevel = line.indexOf(clogLevel);
                    if(indexLevel>-1&&indexLevel<indexSplit){
                        filtedState.getLines().add(line);
                        break;
                    }
                }
            }
        }
        return filtedState;
    }
}
