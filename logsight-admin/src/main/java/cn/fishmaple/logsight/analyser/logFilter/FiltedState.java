package cn.fishmaple.logsight.analyser.logFilter;

import java.util.LinkedList;
import java.util.List;

public class FiltedState {
    private List<String> lines;
    private boolean endFlag;

    public FiltedState() {
        this.lines = new LinkedList<>();
        this.endFlag = true;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public boolean isEndFlag() {
        return endFlag;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }
}
