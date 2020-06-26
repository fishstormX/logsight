package cn.fishmaple.logsight.api;

import cn.fishmaple.logsight.object.Result;
import cn.fishmaple.logsight.object.SearchResult;
import cn.fishmaple.logsight.service.sighting.SightingService;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sighting")
public class SightingAPI {
    @Autowired
    SightingService sightingService;
    @PostMapping("search")
    public Result<List<SearchResult>> ss(@RequestBody JSONObject jsonObject){
        return new Result<>(sightingService.getSearchPInf(jsonObject.getString("key"),jsonObject.getInteger("fieldId")));
    }
}
