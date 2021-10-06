package com.qf.feign.api;


import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@FeignClient("shop-user")

public interface IUserService {

    @RequestMapping("/user/addUser")
    public ResultEntity addUser(@RequestBody User user);
    @RequestMapping("/user/getUserPage")
    public Page<User> getUserPage(@RequestBody Map<String,Object> map);

    @RequestMapping("/user/getUserById/{id}")
    public User getUserById(@PathVariable("id") Integer id);
    @RequestMapping("/user/updateUser")
    public ResultEntity updateUser(@RequestBody User user);

    @RequestMapping("/user/userBatchDel")
    public ResultEntity userBatchDel(@RequestParam("userIdList") ArrayList<Integer> userIdList);

    @RequestMapping("/user/getUserByUsername")
    User getUserByUsername(String param);

    @RequestMapping("/user/getUserByEmail")
    User getUserByEmail(String emailStr);
}
