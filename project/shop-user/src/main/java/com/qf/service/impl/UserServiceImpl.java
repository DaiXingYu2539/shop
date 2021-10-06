package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.User;
import com.qf.mapper.UserMapper;
import com.qf.service.IUserService;

import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
