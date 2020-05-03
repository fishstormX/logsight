package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.exception.DefaultException;
import cn.fishmaple.logsight.object.LogField;
import cn.fishmaple.logsight.object.LogGroup;
import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.setting.SettingFieldFileService;
import cn.fishmaple.logsight.service.setting.SettingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setting")
public class SettingAPI {
    @Autowired
    private SettingFieldFileService settingFieldFileService;
    @Autowired
    private SettingGroupService settingGroupService;
    @Autowired
    I18n i18n;

    @PutMapping("logField")
    public Result<Boolean> putField(@RequestBody LogField logField) throws DefaultException {
        if(logField.getPath().replace(" ","").isEmpty()){
            return new Result<>(-1,i18n.getMessage("i18n.setting_logfield_table_path")+i18n.getMessage("i18n.helper_notNull"));
        }else if(logField.getPath().length()>500){
            return new Result<>(-1,i18n.getMessage("i18n.setting_logfield_table_path")+i18n.getMessage("i18n.helper_tooLong"));
        }
        Boolean flag = settingFieldFileService.saveField(logField);
        return new Result<>(flag);
    }

    @PostMapping("logField")
    public Result<Boolean> updateField(@RequestBody LogField logField) throws DefaultException {
        if(logField.getPath().replace(" ","").isEmpty()){
            return new Result<>(-1,i18n.getMessage("i18n.setting_logfield_table_path")+i18n.getMessage("i18n.helper_notNull"));
        }else if(logField.getPath().length()>500){
            return new Result<>(-1,i18n.getMessage("i18n.setting_logfield_table_path")+i18n.getMessage("i18n.helper_tooLong"));
        }
        settingFieldFileService.updateField(logField);
        return new Result<>(true);
    }

    @DeleteMapping("logField")
    public Result<Boolean> deleteField(@RequestBody LogField logField) throws DefaultException {
        settingFieldFileService.deleteField(logField.getId());
        return new Result<>(true);
    }

    @GetMapping("logField/{id}")
    public Result<LogField> getField(@PathVariable String id) {
        LogField logField = settingFieldFileService.getFieldInfo(id);
        return new Result<>(logField);
    }

    @GetMapping("logField/a")
    public Result<List<LogFieldDTO>> getFields() {
        List<LogFieldDTO> logFields = settingFieldFileService.getAll();
        return new Result<>(logFields);
    }

    @PutMapping("logGroup")
    public Result<Boolean> putField(@RequestBody LogGroup logGroup) throws DefaultException {
        if(logGroup.getName().replace(" ","").isEmpty()){
            return new Result<>(-1,i18n.getMessage("i18n.setting_loggroup_table_name")+i18n.getMessage("i18n.helper_notNull"));
        }else if(logGroup.getName().length()>255){
            return new Result<>(-1,i18n.getMessage("i18n.setting_loggroup_table_name")+i18n.getMessage("i18n.helper_tooLong"));
        }else if(logGroup.getFieldIds().isEmpty()){
            return new Result<>(-1,i18n.getMessage("i18n.setting_loggroup_form_path")+i18n.getMessage("i18n.helper_notNull"));
        }
        Boolean flag = settingGroupService.saveGroup(logGroup);
        return new Result<>(flag);
    }

    @PostMapping("logGroup")
    public Result<Boolean> updateField(@RequestBody LogGroup logGroup) throws DefaultException {
        if(logGroup.getName().replace(" ","").isEmpty()){
            return new Result<>(-1,i18n.getMessage("i18n.setting_loggroup_table_name")+i18n.getMessage("i18n.helper_notNull"));
        }else if(logGroup.getName().length()>255){
            return new Result<>(-1,i18n.getMessage("i18n.setting_loggroup_table_name")+i18n.getMessage("i18n.helper_tooLong"));
        }else if(logGroup.getFieldIds().isEmpty()){
            return new Result<>(-1,i18n.getMessage("i18n.setting_loggroup_form_path")+i18n.getMessage("i18n.helper_notNull"));
        }
        Boolean flag = settingGroupService.updateGroup(logGroup);
        return new Result<>(flag);
    }

    @GetMapping("logGroup/{id}")
    public Result<LogGroup> updateField(@PathVariable Integer id) throws DefaultException {
        LogGroup logGroup= settingGroupService.getGroupById(id);
        return new Result<>(logGroup);
    }

    @DeleteMapping("logGroup/{id}")
    public Result<Boolean> deleteField(@PathVariable Integer id) throws DefaultException {
        settingGroupService.removeGroupById(id);
        return new Result<>(true);
    }



}
