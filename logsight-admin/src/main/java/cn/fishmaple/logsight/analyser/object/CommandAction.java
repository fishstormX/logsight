package cn.fishmaple.logsight.analyser.object;

public class CommandAction {
    public static final Integer ACTION_INTERCEPT_HEAD = 1;
    public static final Integer ACTION_INTERCEPT_TAIL = 2;
    public static final Integer ACTION_INTERCEPT_CAT = 3;
    public static final Integer ACTION_INTERCEPT_SED = 4;
    public static final Integer ACTION_FILTER_GREP = 5;
    private String target;
    private Integer action;
    private String options;


}
