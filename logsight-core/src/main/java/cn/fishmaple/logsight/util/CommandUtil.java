package cn.fishmaple.logsight.util;

public class CommandUtil {
    public static String getRealCommand(String oriCommand){
        if('\''==oriCommand.charAt(0)||'"'==oriCommand.charAt(0)){
            return oriCommand.substring(1,oriCommand.length()-1);
        }
        return oriCommand;
    }
}
