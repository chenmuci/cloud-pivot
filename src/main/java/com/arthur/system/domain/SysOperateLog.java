package com.arthur.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 * @author Arthur
 */
@Data
@Schema(name = "操作日志")
public class SysOperateLog {

    @Schema(description = "日志编号")
    private Long id;

    @Schema(description = "业务类型(0-其它 1-新增 2-修改 3-删除 4-导出 5-导入 6-清空)")
    private Integer businessType;

    @Schema(description = "所属模块")
    private String module;

    @Schema(description = "日志描述")
    private String description;

    @Schema(description = "请求方式")
    private String action;

    @Schema(description = "方法名称")
    private String method;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "操作IP")
    private String ipaddr;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "请求参数")
    private String params;

    @Schema(description = "返回结果")
    private String result;

    @Schema(description = "错误信息")
    private String error;

    @Schema(description = "日志状态(0-失败 1-成功)")
    private Integer status;

    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Schema(description = "消耗时间")
    private Long costTime;
}
