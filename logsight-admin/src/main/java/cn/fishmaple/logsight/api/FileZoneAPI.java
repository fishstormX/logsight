package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.object.FileTree;
import cn.fishmaple.logsight.service.filezone.FileResponseService;
import cn.fishmaple.logsight.service.filezone.FileSplitService;
import cn.fishmaple.logsight.service.filezone.FileZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/filezone")
public class FileZoneAPI {
    @Autowired
    FileZoneService fileZoneService;
    @Autowired
    FileResponseService fileResponseService;
    @Autowired
    FileSplitService fileSplitService;
    @RequestMapping("/fileNodes")
    public FileTree getNodes(@RequestParam Integer fieldId){
        return fileZoneService.getLocalFileTree(fieldId);
    }

    @RequestMapping("/file/{id}")
    public void dowinload(HttpServletResponse response,@PathVariable Integer id){
        String path = fileZoneService.getNodePath(id);
        fileResponseService.file2Response(path,response);
    }

    @GetMapping("/file/{id}/split")
    public void splitAndDownload(HttpServletResponse response, @RequestParam String startTime ,@RequestParam String endTime,
                                 @PathVariable Integer id) {
        String path = fileZoneService.getNodePath(id);
        fileSplitService.timeSplitWithResponse(startTime,endTime, path, response);
    }
}