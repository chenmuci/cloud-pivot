package com.arthur.common.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页参数
 * @author Arthur
 */
@Data
@Schema(name = "分页参数")
public class BasePage {

    @Schema(description = "当前页码")
    private Integer pageNum;

    @Schema(description = "每页记录数")
    private Integer pageSize;

}
