package cn.fishmaple.logsight.analyser;

import org.springframework.stereotype.Component;

@Component
public class DefaultCommandAnalyser extends AbstractCommandAnalyser{
    @Override
    public String baseSearchTemplate() {
        return "cat {file}|grep {target}";
    }
}
