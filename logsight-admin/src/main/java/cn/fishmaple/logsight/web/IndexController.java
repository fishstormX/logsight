package cn.fishmaple.logsight.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int dr){
        return index(model,"main",dr);
    }
    @RequestMapping("/{route}")
    public String index(Model model, @PathVariable String route,@RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module",route);
        model.addAttribute("dr",dr);
        return "index";
    }
}
