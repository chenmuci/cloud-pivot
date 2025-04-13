package com.arthur.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Arthur
 */
@Data
@Schema(name = "用户登录参数")
public class SysUserDTO {

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户密码")
    private String password;

}
