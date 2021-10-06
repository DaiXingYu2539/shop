package com.qf.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.feign.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/userController")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/getUserPage")
    public String getUserPage(HttpServletRequest request, Model model)
    {

        // 1.创建一个map，用来装查询的才参数(分页的参数，条件的查询的参数)
        Map<String,Object> map = new HashMap<>();

        // 2.获取用户传递到额参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();

        // 3.遍历
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue()[0];

            // 把属性名称和实现值全部放入到我们定义的Map中
            map.put(key,value);
        }

        log.debug("{}",map);
        // 调用service层查询数据
        Page<User> page = userService.getUserPage(map);

        model.addAttribute("page",page);
        model.addAttribute("url","http://localhost/shop-back/userController/getUserPage");
        model.addAttribute("queryParamMap",map);

        // ${map.key}
        model.addAttribute("queryParamMap",map);
        return  "user/userList";

    }
    @RequestMapping("/toAddUserPage")
    public String getUserPage( Model model)
    {
        return  "user/addUser";
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public ResultEntity addUser( User user)
    {
        System.out.println(user.toString());
        return  userService.addUser(user);

    }

    @RequestMapping("/getUserById/{id}")
    public String getUserById(@PathVariable("id") Integer id,Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        System.out.println(user);
        return "user/updateUser";
    }
    @RequestMapping("/updateUser")
    @ResponseBody
    public ResultEntity updateUser(User user)
    {
        return   userService.updateUser(user);
    }

    @RequestMapping("/userBatchDel")
    @ResponseBody
    public ResultEntity userBatchDel(@RequestParam("userIdList[]") ArrayList<Integer> userIdList){
        log.debug("{}",userIdList);
        return userService.userBatchDel(userIdList);
    }
}
