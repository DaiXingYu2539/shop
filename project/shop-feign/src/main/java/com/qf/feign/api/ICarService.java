package com.qf.feign.api;

import com.qf.domain.CarGoods;
import com.qf.entity.Car;
import com.qf.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("shop-car")
public interface ICarService {

    @RequestMapping("carController/addCarMySQL")
    public ResultEntity addCarMySQL(@RequestBody Car car);

    @RequestMapping("/carController/getCarGoodsList/{uid}")
    public List<CarGoods> getCarGoodsList(@PathVariable("uid") Integer uid);

    @RequestMapping("/carController/clearUserCar")
    public ResultEntity clearUserCar(@RequestParam("uid") Integer uid);
}
