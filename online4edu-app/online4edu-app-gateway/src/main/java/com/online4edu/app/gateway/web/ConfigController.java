package com.online4edu.app.gateway.web;

import com.online4edu.app.gateway.domain.SysAdministrativeRegion;
import com.online4edu.app.gateway.service.SysAdministrativeRegionService;
import com.online4edu.dependencies.utils.result.ResponseMsgUtil;
import com.online4edu.dependencies.utils.result.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "配置类")
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private SysAdministrativeRegionService sysAdministrativeRegionService;


    @GetMapping("/{id}")
    public Result<SysAdministrativeRegion> get(@PathVariable String id) {
        return ResponseMsgUtil.success(sysAdministrativeRegionService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<SysAdministrativeRegion>> list() {
        return ResponseMsgUtil.success(sysAdministrativeRegionService.list());
    }

    @PostMapping("/{id}")
    public Result<Void> update(@PathVariable String id) {
        SysAdministrativeRegion byId = sysAdministrativeRegionService.getById(id);
        sysAdministrativeRegionService.updateAllColumnById(byId);
        return ResponseMsgUtil.success();
    }

}