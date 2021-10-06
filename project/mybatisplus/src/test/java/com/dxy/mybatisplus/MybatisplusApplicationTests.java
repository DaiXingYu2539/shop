package com.dxy.mybatisplus;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxy.mapper.UserMapper;
import com.dxy.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MybatisplusApplicationTests {

    @Autowired
    private UserMapper usermapper;

    @Test
    void contextLoads() {
        List<User> users = usermapper.selectList(null);
        for (User user:users){
            System.out.println(user.toString());
        }
    }
    @Test
    public void teseinsert(){
        User user =new User();
        user.setName("dxy11");
        user.setAge(3);
        user.setEmail("1144673779@qq.com");
        usermapper.insert(user);
        System.out.println(user.toString());
    }
    @Test
    public void testOptimistickLocker(){
            //1.查询用户信息
        User user = usermapper.selectById(6);

            //2.修改用户信息
        user.setAge(24);
            //3.执行更新操作
        usermapper.updateById(user);

    }
    @Test
    public void testBatch(){
         // 批量查询
        usermapper.selectBatchIds(Arrays.asList(1,2,3));
    }
    @Test
    public void testSelectByIds(){
        // 批量查询   其他字段的条件查询

        HashMap<String,Object> map = new HashMap<>();
        map.put("name","dxy");
        List<User> users = usermapper.selectByMap(map);
        users.forEach(System.out::println);

    }

    @Test
    public void testPage(){ //分页查询

        Page<User> page = new Page<>(1,4);  //

        usermapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);

    }
    @Test
    public void test(){ //删除

       usermapper.deleteById(1);
       usermapper.deleteBatchIds(Arrays.asList(1,2,3));
    }















    public Map<String,Object> wordCount(String[] s){

        List<String> list = new ArrayList<>();// 存放 字符顺序
        Map<String,Integer> map =new HashMap<>();//存放 每个字符的数量

        Map<String,Object> res =new HashMap<>();

        res.put("wordsort",list);
        res.put("wordnum",map);
        return res;
    }









}
