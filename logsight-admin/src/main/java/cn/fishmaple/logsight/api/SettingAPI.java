package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.exception.DefaultException;
import cn.fishmaple.logsight.object.LogField;
import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setting")
public class SettingAPI {
    @Autowired
    private SettingService settingService;
    @Autowired
    I18n i18n;

    @PutMapping("logField")
    public Result<Boolean> putField(@RequestBody LogField logField) throws DefaultException {
        if(logField.getPath().replace(" ","").isEmpty()){
            return new Result(-1,i18n.getMessage("i18n.setting_logfield_table_path")+i18n.getMessage("i18n.helper_notNull"));
        }
        Boolean flag = settingService.saveField(logField);
        return new Result(flag);
    }

    @GetMapping("logField")
    public Result<LogField> getFiles(@RequestParam String id) {
        LogField logField = settingService.getFieldInfo(id);
        return new Result(logField);
    }

}
