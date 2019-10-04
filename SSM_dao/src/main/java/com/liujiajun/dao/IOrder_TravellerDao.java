package com.liujiajun.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

//旅客与订单之间是多对多关系，所以我们需要一张中间表（order_traveller）来描述。
@Repository
public interface IOrder_TravellerDao {


    //保存
    @Insert("insert into ORDER_TRAVELLER (orderId, travellerId) values(#{orderId},#{travellerId})")
    public void save(@Param("orderId") String orderId, @Param("travellerId") String travellerId);


    //根据订单id删除数据
    @Delete("delete from ORDER_TRAVELLER where orderId=#{orderId}")
    void delete(String orderId);


}
