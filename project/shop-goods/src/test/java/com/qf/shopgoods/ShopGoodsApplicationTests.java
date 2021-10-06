package com.qf.shopgoods;

import com.qf.entity.Goods;
import com.qf.service.IGoodsService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShopGoodsApplicationTests {

	@Autowired
	private IGoodsService goodsService;


	@Test
	public void contextLoads() {

		Goods goods = new Goods();
		goods.setGname("冰箱");
		goods.setGdesc("双开门");
		goods.setGtype(1);
		goods.setTempPng("a.png|b.png|3.png");
		goods.setGprice(new BigDecimal(899.9));



		boolean b = goodsService.insert(goods);
		System.out.println(b);

	}
	@Test
	public void testdemo(){

		List<Goods> goodsPage = goodsService.getGoodsPage();
		for(Goods goods :goodsPage) System.out.println(goods);

	}

}
