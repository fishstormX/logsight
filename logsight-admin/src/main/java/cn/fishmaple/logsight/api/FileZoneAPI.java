package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.object.FileDownloadStatus;
import cn.fishmaple.logsight.object.FileTree;
import cn.fishmaple.logsight.object.LogAnalyseState;
import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.service.filezone.FileAnalyseService;
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
    @Autowired
    FileAnalyseService fileAnalyseService;
    @RequestMapping("/fileNodes")
    public Result<FileTree> getNodes(@RequestParam Integer fieldId){
        return new Result(fileZoneService.getLocalFileTree(fieldId));
    }

    @RequestMapping("/file/{id}")
    public void download(HttpServletResponse response, @PathVariable Integer id){
        String path = fileZoneService.getNodePath(id);
        fileResponseService.file2Response(path,response);
    }

    @GetMapping("/file/{id}/split")
    public void splitAndDownload(HttpServletResponse response, @RequestParam(required = false) String startTime ,
                                 @RequestParam(required = false) String endTime,
                                 @PathVariable Integer id) {
        String path = fileZoneService.getNodePath(id);
        fileSplitService.timeSplitWithResponse(startTime,endTime, path, response);
    }

    @GetMapping("/rfile/{id}")
    public void splitAndDownload(HttpServletResponse response,@PathVariable String id) {
        fileSplitService.uuidWithResponse(id,response);
    }

    @PostMapping("/files/status")
    public FileDownloadStatus splitAndDownload(@RequestBody LogAnalyseState logAnalyseState,@RequestParam(defaultValue = "false") boolean init) {
        return fileAnalyseService.getStatus(logAnalyseState,init);
    }


    @GetMapping("/files/status/{id}")
    public FileDownloadStatus splitAndDownload(@PathVariable String id) {
        return fileAnalyseService.getStatus(id);
    }
}