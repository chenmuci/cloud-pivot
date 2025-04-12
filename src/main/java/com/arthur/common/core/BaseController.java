package com.arthur.common.core;

/**
 * 简化 Controller 返回响应
 * @author Arthur
 */
public class BaseController {

    public BaseResult success (){
        return BaseResult.success();
    }

    public BaseResult success (Object data){
        return BaseResult.success(data);
    }

    public BaseResult fail (){
        return BaseResult.fail();
    }

    public BaseResult auto (int rows){
        return rows > 0 ? success() : fail();
    }

}
