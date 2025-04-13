package com.arthur.test.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.arthur.common.core.BaseResult;
import com.arthur.system.domain.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur
 */
@RestController
public class TestController {

    @SaCheckLogin
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public BaseResult test () {
        StpUtil.login(10001);
        SysUser user = new SysUser();
        user.setUsername("test");
        user.setNickname("谢志龙");
        user.setPassword("123456789");
        user.setMobile("19537605404");
        user.setEmail("xiezhilong1222@163.com");
        StpUtil.getSession().set("user", user);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("token", tokenInfo.getTokenValue());
        map.put("user", user);
        return BaseResult.success(SaSecureUtil.sha256("123456"));
    }

}
