package com.liujiajun.service.impl;

import com.github.pagehelper.PageHelper;
import com.liujiajun.dao.IOrder_TravellerDao;
import com.liujiajun.dao.IOrdersDao;
import com.liujiajun.domain.Orders;
import com.liujiajun.domain.Traveller;
import com.liujiajun.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ordersService")
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao ordersDao;

    @Autowired
    private IOrder_TravellerDao order_travellerServiceDao;

    @Override
    public List<Orders> findAll(int page,int pageSize) throws Exception{

        //page:当前页码  pageSize：每页显示条数
        PageHelper.startPage(page,pageSize);
        return ordersDao.findAll();

    }

    //查询订单详情
    @Override
    public Orders findById(String id) throws Exception {
        return ordersDao.findById(id);
    }

    //根据订单编号查询订单
    @Override
    public Orders findByOrderNum(String orderNum) throws Exception {
        return ordersDao.findByOrderNum(orderNum);
    }

    //批量删除
    @Override
    public void delete(String[] selectIds) {
        if(selectIds!=null&&selectIds.length>0){
            for (String selectId : selectIds) {
                //删除订单前应该先把order_traveller中对应数据删除
                order_travellerServiceDao.delete(selectId);

                //删除订单
                ordersDao.delete(selectId);
            }
        }
    }

    //添加订单
    @Override
    public void save(Orders orders) {
       ordersDao.save(orders);
    }


    //修改订单
    @Override
    public void update(Orders orders) {
        ordersDao.update(orders);
    }


    //查找订单（搜索的时候按照订单编号搜索）
    @Override
    public List<Orders> searchByOrderNum(String orderNum) {
        orderNum="%"+orderNum+"%";
        return ordersDao.searchByOrderNum(orderNum);
    }

    //查找所有可以添加的旅客
    @Override
    public List<Traveller> findTravellerCanAdd(String id) {
        return ordersDao.findTravellerCanAdd(id);
    }


}
