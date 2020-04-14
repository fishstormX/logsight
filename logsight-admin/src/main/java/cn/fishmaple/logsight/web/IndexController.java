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
    private SettingService settingService;

    @RequestMapping(value={"setting","/setting/{cased}"})
    public String setting(Model model, @PathVariable(required = false)String cased,@RequestParam(defaultValue = "0") int p,@RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module","setting");
        model.addAttribute("case",cased);
        model.addAttribute("dr",dr);
        model.addAttribute("logFields",settingService.getPagesLogField(p));
        return "index";
    }
    @RequestMapping("")
    public String index(Model model, @RequestParam(defaultValue = "0") int dr){
        return setting(model,"logField",0,0);
    }

    @RequestMapping("/{route}")
    public String index2(Model model, @PathVariable String route,@RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module",route);
        model.addAttribute("page","pages/"+route);
        model.addAttribute("dr",dr);
        return "index";
    }
}
