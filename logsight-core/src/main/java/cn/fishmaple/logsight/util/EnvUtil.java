package cn.fishmaple.logsight.util;

import cn.fishmaple.logsight.enums.OsEnum;

public class EnvUtil {
    private static Boolean isWindows;

    static {
        isWindows = getOs().contains(OsEnum.Windows.getSystemName());
    }
    public static String getOs(){
        System.out.println(System.getProperty("os.name")+"  ****");
        return System.getProperty("os.name").toLowerCase();

    }
    /**
     * 是否能直接利用shell读取日志
     * */
    public static boolean shellAbled(){
        return getOs().contains(OsEnum.Windows.getSystemName())||
                getOs().contains(OsEnum.Linux.getSystemName());
    }
    /**
     * 是否能直接利用shell读取日志
     * */
    public static boolean isWindows(){
        return isWindows;
    }

}
