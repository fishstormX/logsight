package cn.fishmaple.logsight.analyser.logFilter;

import java.util.List;

public interface LogFilter {
    FiltedState filtered(List<String> lines);
}
