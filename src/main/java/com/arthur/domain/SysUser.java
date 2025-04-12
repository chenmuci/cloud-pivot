package com.arthur.domain;

import com.arthur.common.annotation.Desensitization;
import com.arthur.common.core.BaseDomain;
import com.arthur.common.enums.DesensitizationEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统用户
 * @author Arthur
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "用户实体")
public class SysUser extends BaseDomain {

    @Schema(description = "用户编号")
    private Long id;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户昵称")
    @Desensitization(function = DesensitizationEnum.NICKNAME)
    private String nickname;

    @Schema(description = "用户密码")
    @Desensitization(function = DesensitizationEnum.PASSWORD)
    private String password;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户性别(0-保密 1-男 2-女)")
    private Integer sex;

    @Schema(description = "联系方式")
    @Desensitization(function = DesensitizationEnum.PHONE)
    private String mobile;

    @Schema(description = "邮箱地址")
    @Desensitization(function = DesensitizationEnum.EMAIL)
    private String email;

    @Schema(description = "上次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastTime;

    @Schema(description = "上次登录IP")
    private String lastIp;

    @Schema(description = "用户状态(0-禁用 1-启用)")
    private Integer status;

}
