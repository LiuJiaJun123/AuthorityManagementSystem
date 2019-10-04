package com.liujiajun.dao;

import com.liujiajun.domain.Orders;
import com.liujiajun.domain.Traveller;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ordersDao")
public interface IOrdersDao {

    @Select("select * from orders order by orderNum")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",one = @One( select = "com.liujiajun.dao.IProductDao.findById"))
    })
    public List<Orders> findAll() throws Exception;


    @Select("select * from orders where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",one = @One( select = "com.liujiajun.dao.IProductDao.findById")),
            @Result(column = "id",property = "travellers",many = @Many(select ="com.liujiajun.dao.ITravellerDao.findByOrdersId")),
            @Result(column = "memberId",property = "member",one = @One(select ="com.liujiajun.dao.IMemberDao.findById")),
    })
    public Orders findById(String id) throws Exception;

    //批量删除
    @Delete("delete from orders where id=#{id}")
    void delete(String id);


    //添加订单
    @Insert("insert into orders(orderNum,orderTime,peopleCount,orderDesc,payType,orderStatus,productId,memberId) " +
            "values(#{orderNum},#{orderTime},#{peopleCount},#{orderDesc},#{payType},#{orderStatus},#{product.id},#{member.id})")
    void save(Orders orders);


    //根据订单编号查询订单
    @Select("select * from orders where orderNum=#{orderNum}")
    Orders findByOrderNum(String orderNum);


    //根据订单编号查询订单  （搜索的时候按照订单编号搜索）
    @Select("select * from orders where orderNum like #{orderNum}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",one = @One( select = "com.liujiajun.dao.IProductDao.findById"))
    })
    List<Orders> searchByOrderNum(String orderNum);


    //修改订单
    @Update("update orders set orderNum=#{orderNum},orderTime=#{orderTime},peopleCount=#{peopleCount},orderDesc=#{orderDesc}" +
            ",payType=#{payType},orderStatus=#{orderStatus},productId=#{product.id},memberId=#{member.id} where id=#{id}")
    void update(Orders orders);

    //查找所有可以添加的旅客
    @Select("select * from traveller where id not in ( select travellerId from order_traveller where orderId=#{id} )")
    List<Traveller> findTravellerCanAdd(String id);
}
