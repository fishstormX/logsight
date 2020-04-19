package cn.fishmaple.logsight.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {
    private static ThreadLocal<Map<String,Object>> requestStorage=new ThreadLocal<>();
    private static void init(){
        if(null==requestStorage.get()){
            requestStorage.set(new HashMap<>());
        }
    }

    public static void set(String key,Object value){
        init();
        requestStorage.get().put(key,value);
    }

    public static Object getValue(String key){
        return requestStorage.get().get(key);
    }
}
