package com.arthur.common.core;

import cn.hutool.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回格式
 * @author Arthur
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "统一返回格式")
public class BaseResult {

    @Schema(description = "状态码")
    private int code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "响应结果")
    private Object data;

    public static BaseResult success (){
        return new BaseResult(HttpStatus.HTTP_OK, "success", null);
    }

    public static BaseResult success (Object data){
        return new BaseResult(HttpStatus.HTTP_OK, "success", data);
    }

    public static BaseResult fail (){
        return new BaseResult(HttpStatus.HTTP_BAD_REQUEST, "fail", null);
    }

    public static BaseResult fail (int code, String msg){
        return new BaseResult(code, msg, null);
    }

}
