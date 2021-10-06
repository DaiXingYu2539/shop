package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.domain.CarGoods;
import com.qf.entity.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarMapper extends BaseMapper<Car> {
    List<CarGoods> getMySQLUserCarList(Integer id);

    CarGoods getCarGoodsById(Object gid);
}
