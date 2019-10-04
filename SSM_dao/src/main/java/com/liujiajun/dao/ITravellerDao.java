package com.liujiajun.dao;

import com.liujiajun.domain.Traveller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 游客
 */
@Repository("travellerDao")
public interface ITravellerDao {

//    @Select("select t.* from traveller t,orders o,order_traveller ot where #{ordersId}=ot.orderId and t.id=travellerId")
    @Select("select * from traveller where id in (select travellerId from order_traveller where orderId=#{ordersId})")
    public List<Traveller> findByOrdersId(String ordersId);


    @Select("select * from traveller")
    public List<Traveller> findAll();

}
