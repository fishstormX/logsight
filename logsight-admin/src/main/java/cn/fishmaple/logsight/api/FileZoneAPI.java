package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.object.FileTree;
import cn.fishmaple.logsight.service.filezone.FileResponseService;
import cn.fishmaple.logsight.service.filezone.FileZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/filezone")
public class FileZoneAPI {
    @Autowired
    FileZoneService fileZoneService;
    @Autowired
    FileResponseService fileResponseService;
    @RequestMapping("/fileNodes")
    public FileTree getNodes(@RequestParam Integer fieldId){
        return fileZoneService.getLocalFileTree(fieldId);
    }

    @RequestMapping("/file/{id}")
    public void dowinload(HttpServletResponse response,@PathVariable Integer id){
        String path = fileZoneService.getNodePath(id);
        fileResponseService.file2Response(path,response);
    }
}