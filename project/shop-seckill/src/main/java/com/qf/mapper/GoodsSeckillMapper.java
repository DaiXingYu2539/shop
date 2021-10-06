package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Goods;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Service
public interface GoodsSeckillMapper extends BaseMapper<Goods> {

    @Update("update t_goods set gstock = gstock -1 where id = #{id} and gstock > 0")
    Integer updateGoodsGstock(Goods goods);
}
