package cn.fishmaple.logsight.web.setting;

import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping("logfield")
    public Result<List> setting(@RequestParam(defaultValue = "0") int p){
        return new Result<>(settingService.getPagesLogField(p));
    }
}
