package com.qf.controller;


import com.qf.service.IGoodsSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsSeckillController {
    @Autowired
    private IGoodsSeckillService goodsSeckillService;

    @RequestMapping("/seckill")
    public String seckill(Integer gid) {

        goodsSeckillService.seckillGoods(gid);

        return "抢购成功";
    }

}
