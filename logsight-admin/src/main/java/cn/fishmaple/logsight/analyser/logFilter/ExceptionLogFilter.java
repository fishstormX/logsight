package cn.fishmaple.logsight.analyser.logFilter;

import java.util.List;

public class ExceptionLogFilter implements LogFilter{
    @Override
    public FiltedState filtered(List<String> lines) {
        FiltedState filtedState = new FiltedState();
        for(String line:lines){
            if(line.contains("Exception in")||line.contains("Exception in")){
                filtedState.setEndFlag(false);
                filtedState.getLines().add(line);
            }else{
                filtedState.setEndFlag(true);
            }
        }
        return filtedState;
    }
}
