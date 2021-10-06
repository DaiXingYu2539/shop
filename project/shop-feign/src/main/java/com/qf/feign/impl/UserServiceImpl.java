package com.qf.feign.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.feign.api.IUserService;

import java.util.ArrayList;
import java.util.Map;

public class UserServiceImpl implements IUserService {

    @Override
    public ResultEntity addUser(User user) {
        return null;
    }

    @Override
    public Page<User> getUserPage(Map<String, Object> map) {
        return null;
    }


    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public ResultEntity updateUser(User user) {
        return null;
    }

    @Override
    public ResultEntity userBatchDel(ArrayList<Integer> userIdList) {
        return null;
    }

    @Override
    public User getUserByUsername(String param) {
        return null;
    }

    @Override
    public User getUserByEmail(String emailStr) {
        return null;
    }
}
