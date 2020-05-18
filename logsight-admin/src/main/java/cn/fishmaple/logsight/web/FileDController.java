package cn.fishmaple.logsight.web;

import cn.fishmaple.logsight.service.setting.SettingFieldFileService;
import cn.fishmaple.logsight.service.setting.SettingGroupService;
import cn.fishmaple.logsight.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/filezone")
public class FileDController {
    @Autowired
    private SettingFieldFileService settingFieldFileService;

    @RequestMapping("")
    public String filezone(Model model, @RequestParam(defaultValue = "1") int p,@RequestParam(defaultValue = "0") int dr,
                          @RequestParam(defaultValue = "0") int sortd,@RequestParam(defaultValue = "")String sortType) {
        model.addAttribute("module","filezone");
        model.addAttribute("dr",dr);
        model.addAttribute("logFields", settingFieldFileService.getPagesLogField(p,sortd,sortType,8));
        model.addAttribute("p",p);
        model.addAttribute("pages", settingFieldFileService.getLogfieldPages(8));
        model.addAttribute("sortd",sortd);
        model.addAttribute("sortType",sortType);
        return "index";
    }

}
