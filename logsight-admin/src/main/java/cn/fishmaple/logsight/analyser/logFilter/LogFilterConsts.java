package cn.fishmaple.logsight.analyser.logFilter;

public class LogFilterConsts {
    public final static String TRACE_LOGLEVEL = "TRACE";
    public final static String DEBUG_LOGLEVEL = "DEBUG";
    public final static String INFO_LOGLEVEL = "INFO";
    public final static String WARN_LOGLEVEL = "WARN";
    public final static String ERROR_LOGLEVEL = "ERROR";
    public final static LogFilter EXCEPTION_FILTER = new ExceptionLogFilter();
    public final static LogLevelLogFilter TRACE_LOGLEVEL_FILTER = new LogLevelLogFilter(TRACE_LOGLEVEL,DEBUG_LOGLEVEL,INFO_LOGLEVEL,WARN_LOGLEVEL,ERROR_LOGLEVEL);
    public final static LogLevelLogFilter DEBUG_LOGLEVEL_FILTER = new LogLevelLogFilter(DEBUG_LOGLEVEL,INFO_LOGLEVEL,WARN_LOGLEVEL,ERROR_LOGLEVEL);
    public final static LogLevelLogFilter INFO_LOGLEVEL_FILTER = new LogLevelLogFilter(INFO_LOGLEVEL,WARN_LOGLEVEL,ERROR_LOGLEVEL);
    public final static LogLevelLogFilter WARN_LOGLEVEL_FILTER = new LogLevelLogFilter(WARN_LOGLEVEL,ERROR_LOGLEVEL);
    public final static LogLevelLogFilter ERROR_LOGLEVEL_FILTER = new LogLevelLogFilter(ERROR_LOGLEVEL);
}
