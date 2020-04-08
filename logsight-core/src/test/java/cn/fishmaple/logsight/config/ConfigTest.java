package cn.fishmaple.logsight.config;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ConfigTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConfigLoader configLoader;

    @Test
    public void getConfig(){
        Assert.notNull(configLoader.getConfig("logsight.input.log-path","/data/logsight"),configLoader.getConfig("logsight.input.log-path.ssa","12123"));
    }


}
