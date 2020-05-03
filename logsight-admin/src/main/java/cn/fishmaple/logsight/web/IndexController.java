package cn.fishmaple.logsight.web;

import cn.fishmaple.logsight.service.ReportService;
import cn.fishmaple.logsight.service.setting.SettingFieldFileService;
import cn.fishmaple.logsight.web.setting.SettingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private SettingFieldFileService settingFieldFileService;
    @Autowired
    private SettingController settingController;
    @Autowired
    private ReportService reportService;

    @RequestMapping("")
    public String index(Model model, @RequestParam(defaultValue = "0") int dr){
        return dashboard(model, dr);
    }

    @RequestMapping("dashboard")
    public String dashboard(Model model, @RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module","dashboard");
        model.addAttribute("dr",dr);
        model.addAttribute("fileSizeData",reportService.getDailyFileReport());
        model.addAttribute("maxFileData",reportService.getMax());
        model.addAttribute("fileCount",reportService.getTotalFileCount());
        model.addAttribute("fieldCount",reportService.getTotalFieldCount());
        model.addAttribute("fileSize",reportService.getTotalFileSize());
        return "index";
    }
}
