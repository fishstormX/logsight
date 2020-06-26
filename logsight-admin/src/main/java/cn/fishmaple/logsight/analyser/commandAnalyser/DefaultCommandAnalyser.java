package cn.fishmaple.logsight.analyser.commandAnalyser;

import org.springframework.stereotype.Component;

@Component
public class DefaultCommandAnalyser extends AbstractCommandAnalyser{
    @Override
    public String baseSearchTemplate() {
        return "tac '{file}'|grep '{target}'";
    }
}
