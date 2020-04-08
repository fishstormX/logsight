package cn.fishmaple.logsight.util;

import cn.fishmaple.logsight.enums.OsEnum;

public class EnvUtil {
    public static String getOs(){
        return System.getProperty("os.name").toLowerCase();
    }
    /**
     * 是否能直接利用shell读取日志
     * */
    public static boolean shellAbled(){
        return getOs().contains(OsEnum.Windows.getSystemName())||
                getOs().contains(OsEnum.Linux.getSystemName());
    }

}
