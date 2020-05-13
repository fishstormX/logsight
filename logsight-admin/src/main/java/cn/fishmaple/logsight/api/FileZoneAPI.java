package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.object.FileNode;
import cn.fishmaple.logsight.service.FileSpiltService;
import cn.fishmaple.logsight.service.FileZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/filezone")
public class FileZoneAPI {
    @Autowired
    FileZoneService fileZoneService;
    @GetMapping("fileNodes")
    public FileNode getNodes(@RequestParam Integer fieldId){
        return fileZoneService.getLocalFileTree(fieldId);
    }
}
