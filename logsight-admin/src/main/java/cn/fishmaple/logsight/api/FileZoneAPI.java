package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.object.FileTree;
import cn.fishmaple.logsight.service.FileZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/filezone")
public class FileZoneAPI {
    @Autowired
    FileZoneService fileZoneService;
    @RequestMapping("/fileNodes")
    public FileTree getNodes(@RequestParam Integer fieldId){
        return fileZoneService.getLocalFileTree(fieldId);
    }
}