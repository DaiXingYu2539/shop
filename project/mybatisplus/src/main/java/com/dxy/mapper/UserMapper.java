package com.dxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.pojo.User;
import org.springframework.stereotype.Repository;

//在对应的mapper 上实现 BaseMapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    //多有的crud操作都已经写完了


}
