package cn.fishmaple.logsight.analyser.object;


import cn.fishmaple.logsight.analyser.logFilter.LogFilter;

import java.util.Collection;

public class FileStreamAction {
    private String file;
    private String filterString;
    private Integer bindLine;
    private boolean strFiltered;
    private Collection<LogFilter> logFilters;

    public String getFilterString() {
        return filterString;
    }

    public FileStreamAction setFilterString(String filterString) {
        this.filterString = filterString;
        return this;
    }

    public Integer getBindLine() {
        return bindLine;
    }

    public FileStreamAction setBindLine(Integer bindLine) {
        this.bindLine = bindLine;
        return this;
    }

    public Collection<LogFilter> getLogFilters() {
        return logFilters;
    }

    public FileStreamAction setLogFilters(Collection<LogFilter> logFilters) {
        this.logFilters = logFilters;
        return this;
    }

    public FileStreamAction(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public FileStreamAction setFile(String file) {
        this.file = file;
        return this;
    }

    public boolean isStrFiltered() {
        return strFiltered;
    }

    public FileStreamAction setStrFiltered(boolean strFiltered) {
        this.strFiltered = strFiltered;
        return this;
    }
}
