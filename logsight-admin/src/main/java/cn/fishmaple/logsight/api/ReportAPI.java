package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.setting.SettingFieldFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {
    @Autowired
    private SettingFieldFileService settingFieldFileService;
    @Autowired
    I18n i18n;

    @GetMapping("dashboard/fileSize")
    public Result<Boolean> get() {

        return new Result(true);
    }

}
