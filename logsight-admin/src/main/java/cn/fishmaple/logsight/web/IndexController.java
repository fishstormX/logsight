package cn.fishmaple.logsight.web;

import cn.fishmaple.logsight.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    SettingService settingService;

    @RequestMapping(value={"setting","/setting/{cased}"})
    public String setting(Model model, @PathVariable(required = false)Integer cased,@RequestParam(defaultValue = "0") int p,@RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module","setting");
        model.addAttribute("page","pages/setting");
        model.addAttribute("dr",dr);
        model.addAttribute("logFields",settingService.getPagesLogField(p));
        return "index";
    }
    @RequestMapping("/{route}")
    public String index(Model model, @PathVariable String route,@RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module",route);
        model.addAttribute("page","pages/"+route);
        model.addAttribute("dr",dr);
        return "index";
    }
}
