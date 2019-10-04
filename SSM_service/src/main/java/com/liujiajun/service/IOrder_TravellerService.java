package com.liujiajun.service;

//旅客与订单之间是多对多关系，所以我们需要一张中间表（order_traveller）来描述。
public interface IOrder_TravellerService {

    //添加
    void save(String orderId,String travellerId);

    //根据订单id删除数据
    void delete(String orderId) throws Exception;

    //修改
    void update(String orderId,String[] travellersId);
}
