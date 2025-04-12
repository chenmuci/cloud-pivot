package com.arthur.controller;

import com.arthur.common.core.BasePage;
import com.arthur.common.core.BaseResult;
import com.arthur.common.exception.BusinessException;
import com.arthur.domain.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arthur
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public BaseResult test () {
        SysUser user = new SysUser();
        user.setUsername("test");
        user.setNickname("谢志龙");
        user.setPassword("123456789");
        user.setMobile("19537605404");
        user.setEmail("xiezhilong1222@163.com");
        return BaseResult.success(user);
    }

}
