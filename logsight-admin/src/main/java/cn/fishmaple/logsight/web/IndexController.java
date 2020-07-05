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
        model.addAttribute("module", "dashboard");
        model.addAttribute("dr", dr);
        model.addAttribute("fileSizeData", reportService.getCachedDailyFileReport());
        model.addAttribute("maxFileData", reportService.getMax());
        model.addAttribute("fileCount", reportService.getTotalFileCount());
        model.addAttribute("fieldCount", reportService.getTotalFieldCount());
        model.addAttribute("fileSize", reportService.getTotalFileSize());
        return "index";
    }

    @RequestMapping("filed")
    public String filedown(Model model, @RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module", "filed");
        model.addAttribute("dr", dr);
        model.addAttribute("fileSizeData", reportService.getDailyFileReport());
        model.addAttribute("maxFileData", reportService.getMax());
        model.addAttribute("fileCount", reportService.getTotalFileCount());
        model.addAttribute("fieldCount", reportService.getTotalFieldCount());
        model.addAttribute("fileSize", reportService.getTotalFileSize());
        return "index";
    }

    @RequestMapping("sighting")
    public String sighting(Model model, @RequestParam(defaultValue = "1") int p,@RequestParam(defaultValue = "0") int dr,
                           @RequestParam(defaultValue = "0") int sortd,@RequestParam(defaultValue = "")String sortType){
        model.addAttribute("module", "sighting");
        model.addAttribute("dr", dr);
        model.addAttribute("logFields", settingFieldFileService.getPagesLogField(p,sortd,sortType,8));
        model.addAttribute("p",p);
        model.addAttribute("pages", settingFieldFileService.getLogfieldPages(8));
        model.addAttribute("sortd",sortd);
        model.addAttribute("sortType",sortType);
        return "index";
    }

    @RequestMapping("logline")
    public String sighting(Model model,@RequestParam(defaultValue = "0") int dr){
        model.addAttribute("module", "logline");
        model.addAttribute("dr", dr);
        model.addAttribute("logFields", settingFieldFileService.getPagesLogField(1,0,"",8));
        model.addAttribute("pages", settingFieldFileService.getLogfieldPages(8));
        model.addAttribute("p", 1);
        model.addAttribute("drawer", true);
        return "index";
    }
}
