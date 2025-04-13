package com.arthur.system.service.impl;

import com.arthur.system.domain.SysUser;
import com.arthur.system.mapper.SysUserMapper;
import com.arthur.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Arthur
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
