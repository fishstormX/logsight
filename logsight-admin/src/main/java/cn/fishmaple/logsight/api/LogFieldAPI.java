package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.service.setting.SettingFieldFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/logField")
public class LogFieldAPI {
    @Autowired
    SettingFieldFileService settingFieldFileService;

    @RequestMapping("page")
    public Map<String,Object> download(@RequestParam String sortType,@RequestParam int sortd,@RequestParam int p){
        Map<String,Object> map = new HashMap<>();
        map.put("logFields", settingFieldFileService.getPagesLogField(p,sortd,sortType,8));
        map.put("pages", settingFieldFileService.getLogfieldPages(8));
        return map;
    }

}
