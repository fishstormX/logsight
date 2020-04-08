package cn.fishmaple.logsight.shell;

import org.springframework.stereotype.Component;
import java.io.InputStream;

@Component
public interface ProcessThread {

    public String execute(String... command);

    public InputStream execute2stream(String... command);
}
