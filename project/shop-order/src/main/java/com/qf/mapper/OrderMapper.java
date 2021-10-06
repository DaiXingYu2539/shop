package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {

    Integer createOrder(@Param("order") Order order, @Param("tableIndex") Integer tableIndex);

    void batchInsertOrderDetail(@Param("orderDetailList") List<OrderDetail> orderDetailList, @Param("tableIndex") Integer tableIndex);


    Order getOrderById(@Param("oid") String oid, @Param("tableIndex") Integer tableIndex);

    Integer updateOrderStatus(@Param("tableIndex") Integer tableIndex, @Param("map") Map<String, String> map);

    List<Order> getOrderListByUserId(@Param("tableIndex") Integer tableIndex, @Param("userId") Integer userId);

}

