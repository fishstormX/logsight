package cn.fishmaple.logsight.config;

public interface ConfigLoader {
    String getConfig(String key);
    String getConfig(String key,String defaultValue);
    Integer getIntConfig(String key,Integer defaultValue);
}
