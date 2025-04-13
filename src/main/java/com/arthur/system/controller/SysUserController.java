package com.arthur.system.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.arthur.common.core.BaseController;
import com.arthur.common.core.BaseResult;
import com.arthur.common.exception.BusinessException;
import com.arthur.system.domain.SysUser;
import com.arthur.system.dto.SysUserDTO;
import com.arthur.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur
 */
@Tag(name = "用户管理")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class SysUserController extends BaseController {

    private final SysUserService service;

    @Operation(summary = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult login(@RequestBody SysUserDTO dto) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUsername, dto.getUsername());
        SysUser user = service.getOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(SaSecureUtil.sha256(dto.getPassword()))) {
            throw new BusinessException("密码错误");
        }
        StpUtil.login(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenInfo().tokenValue);
        map.put("user", user);
        return success(map);
    }

    @Operation(summary = "用户注册")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResult add(@RequestBody SysUser user) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser sysUser = service.getOne(wrapper);
        if (sysUser != null) {
            if (sysUser.getUsername().equals(user.getUsername())) {
                throw new BusinessException("用户已存在");
            }
            if (sysUser.getMobile().equals(user.getMobile())) {
                throw new BusinessException("手机号已存在");
            }
            if (sysUser.getEmail().equals(user.getEmail())) {
                throw new BusinessException("邮箱已存在");
            }
        }
        user.setPassword(SaSecureUtil.sha256(user.getPassword()));
        service.save(user);
        return success();
    }

    @Operation(summary = "用户注销")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResult logout() {
        StpUtil.logout();
        return success();
    }

    @Operation(summary = "踢人下线")
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    public BaseResult kickOut(String token) {
        StpUtil.kickoutByTokenValue(token);
        return success();
    }

}
