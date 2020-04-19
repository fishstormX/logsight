package cn.fishmaple.logsight.web.setting;

import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.SettingService;
import cn.fishmaple.logsight.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @RequestMapping("logField")
    public String setting(Model model, @RequestParam(defaultValue = "1") int p,@RequestParam(defaultValue = "0") int dr,
                        @RequestParam(defaultValue = "0") int sortd,@RequestParam(defaultValue = "")String sortType) {
        model.addAttribute("module","setting");
        model.addAttribute("case","logField");
        model.addAttribute("dr",dr);
        model.addAttribute("logFields",settingService.getPagesLogField(p,sortd,sortType));
        model.addAttribute("logFields4Scan", ThreadLocalUtil.getValue("logFields4Scan"));
        model.addAttribute("p",p);
        model.addAttribute("pages",settingService.getLogfieldPages(10));
        model.addAttribute("sortd",sortd);
        model.addAttribute("sortType",sortType);
        return "index";
    }

}
