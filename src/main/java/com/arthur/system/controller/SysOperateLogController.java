package com.arthur.system.controller;

import com.arthur.common.core.BaseController;
import com.arthur.common.core.BaseResult;
import com.arthur.system.domain.SysOperateLog;
import com.arthur.system.dto.SysOperateLogDTO;
import com.arthur.system.service.SysOperateLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arthur
 */
@Tag(name = "操作日志")
@RestController
@AllArgsConstructor
@RequestMapping("/operate/log")
public class SysOperateLogController extends BaseController {

    private final SysOperateLogService service;

    @Operation(summary = "查询操作日志列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(SysOperateLogDTO dto) {
        Page<SysOperateLog> logPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<SysOperateLog> wrapper = new QueryWrapper<>();
        wrapper.lambda().between(SysOperateLog::getCreateTime, dto.getStartTime(), dto.getEndTime());
        return success(service.page(logPage, wrapper));
    }

    @Operation(summary = "清空操作日志")
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public BaseResult clean() {
        service.remove(new QueryWrapper<>());
        return success();
    }

}
