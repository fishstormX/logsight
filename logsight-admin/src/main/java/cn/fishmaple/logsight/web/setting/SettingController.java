package cn.fishmaple.logsight.web.setting;

import cn.fishmaple.logsight.service.setting.SettingFieldFileService;
import cn.fishmaple.logsight.service.setting.SettingGroupService;
import cn.fishmaple.logsight.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingFieldFileService settingFieldFileService;
    @Autowired
    private SettingGroupService settingGroupService;

    @RequestMapping("logField")
    public String logField(Model model, @RequestParam(defaultValue = "1") int p,@RequestParam(defaultValue = "0") int dr,
                        @RequestParam(defaultValue = "0") int sortd,@RequestParam(defaultValue = "")String sortType) {
        model.addAttribute("module","setting");
        model.addAttribute("case","logField");
        model.addAttribute("dr",dr);
        model.addAttribute("logFields", settingFieldFileService.getPagesLogField(p,sortd,sortType));
        model.addAttribute("logFields4Scan", ThreadLocalUtil.getValue("logFields4Scan"));
        model.addAttribute("p",p);
        model.addAttribute("pages", settingFieldFileService.getLogfieldPages(10));
        model.addAttribute("sortd",sortd);
        model.addAttribute("sortType",sortType);
        return "index";
    }
    @RequestMapping("logGroup")
    public String logGroup(Model model, @RequestParam(defaultValue = "1") int p,@RequestParam(defaultValue = "0") int dr,
                           @RequestParam(defaultValue = "0") int sortd,@RequestParam(defaultValue = "")String sortType) {
        model.addAttribute("module","setting");
        model.addAttribute("case","logGroup");
        model.addAttribute("dr",dr);
        model.addAttribute("logGroups", settingGroupService.getPagesLogGroup(p,sortd,sortType));
        model.addAttribute("p",p);
        model.addAttribute("pages", settingGroupService.getGroupPages(10));
        model.addAttribute("sortd",sortd);
        model.addAttribute("sortType",sortType);
        return "index";
    }

    @RequestMapping("")
    public String setting(Model model, @RequestParam(defaultValue = "1") int p,@RequestParam(defaultValue = "0") int dr,
                          @RequestParam(defaultValue = "0") int sortd,@RequestParam(defaultValue = "")String sortType) {
        return logField(model, p, dr, sortd, sortType);
    }

}
