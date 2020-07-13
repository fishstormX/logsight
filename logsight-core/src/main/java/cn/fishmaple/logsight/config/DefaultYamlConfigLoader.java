package cn.fishmaple.logsight.config;

import cn.fishmaple.logsight.enums.OsEnum;
import cn.fishmaple.logsight.util.EnvUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Service
public class DefaultYamlConfigLoader implements ConfigLoader{

    @Value("${logsight.config-path:#{null}}")
    private String configPath;

    private static Map<String, Object> properties;

    @PostConstruct
    private void init() {
        Yaml yaml = new Yaml();
        if(null==configPath){
            if(EnvUtil.getOs().contains(OsEnum.Windows.getSystemName())){
                configPath = "C:/data/logsight/logsight.yml";
            }else{
                configPath = "/data/logsight/logsight.yml";
            }
        }
        if(configPath.lastIndexOf("/")==configPath.length()-1){
            configPath = configPath.substring(0,configPath.length()-1);
        }
        try {
            properties = yaml.load(new FileInputStream(configPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private String getConfig(Map configMap,String key){
        if(null==configMap){
            return null;
        }
        if(key.indexOf(".")>0){
            return getConfig(getConfigMap(configMap,key),key.substring(key.indexOf(".")+1));
        }else{
            Object propertie = configMap.get(key);
            if (null == propertie){
                return null;
            }
            return propertie.toString();
        }
    }
    @Override
    public String getConfig(String key,String defaultValue){
        String propertie = getConfig(properties, key);
        if(null == propertie){
            return defaultValue;
        }
        return propertie;
    }
    @Override
    public Integer getIntConfig(String key,Integer defaultValue){
        String propertie = getConfig(properties, key);
        if(null==propertie){
            return defaultValue;
        }else {
            Integer goal = defaultValue;
            try {
                goal = Integer.parseInt(propertie);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
            return goal;
        }
    }
    @Override
    public String getConfig(String key){
        return getConfig(properties,key);
    }
    public Map getConfigMap(Map configMap,String key){
        Map map = null;
        try {
            map = (Map) configMap.get(key.substring(0, key.indexOf(".")));
        }catch(ClassCastException e){
            return null;
        }
        return map;
    }

}
