package com.liujiajun.service.impl;

import com.liujiajun.dao.IOrder_TravellerDao;
import com.liujiajun.service.IOrder_TravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//旅客与订单之间是多对多关系，所以我们需要一张中间表（order_traveller）来描述。
@Service("order_TravellerService")
public class Order_TravellerServiceImpl implements IOrder_TravellerService {

    @Autowired
    private IOrder_TravellerDao order_travellerServiceDao;

    //保存
    @Override
    public void save(String orderId, String travellerId) {
        order_travellerServiceDao.save(orderId,travellerId);
    }

    //根据订单id删除数据
    @Override
    public void delete(String orderId) throws Exception {
        order_travellerServiceDao.delete(orderId);
    }

    //修改
    @Override
    public void update(String orderId, String[] travellersId) {
        //先删除原来的数据
        order_travellerServiceDao.delete(orderId);

        //在插入数据
        for (String travellerId : travellersId) {
            order_travellerServiceDao.save(orderId,travellerId);
        }

    }

}
