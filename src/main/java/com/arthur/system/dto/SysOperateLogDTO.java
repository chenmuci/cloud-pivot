package com.arthur.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Arthur
 */
@Data
@Schema(name = "操作日志查询参数")
public class SysOperateLogDTO {

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Schema(description = "当前页码")
    private Integer pageNum;

    @Schema(description = "每页记录数")
    private Integer pageSize;

}
