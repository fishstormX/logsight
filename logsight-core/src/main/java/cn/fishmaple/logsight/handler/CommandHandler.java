package cn.fishmaple.logsight.handler;

import cn.fishmaple.logsight.util.CommandUtil;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {
    public String analyseColored(String command){
        String[] commands= command.split(" |\\|");
        for(int i = commands.length-1;i>0;i--){
            if("grep".equals(commands[i])){
                String keyWord = "";
                for(int j=i+1;j<commands.length;j++){
                    if(!"".equals(commands[j])){
                        keyWord = commands[j];
                        break;
                    }
                }
                return CommandUtil.getRealCommand(keyWord);
            }
        }
        return "";
    }


}
