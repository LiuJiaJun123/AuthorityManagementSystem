package com.liujiajun.dao;

import com.liujiajun.domain.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productDao")
public interface IProductDao {

    //根据id查找产品
    @Select("select * from product where id=#{id}")
    public Product findById(String id);

//    查找所有
    @Select("select * from product order by productNum")
    public List<Product> findAll() throws Exception;


//    保存
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) " +
            "values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void save(Product product);


//  根据编号查找
    @Select("select * from product where productNum=#{productNum}")
    public List<Product> findByNum(String productNum);

//    更新
    @Update("update product set productNum=#{productNum},productName=#{productName},cityName=#{cityName},departureTime=#{departureTime}" +
            ",productPrice=#{productPrice},productDesc=#{productDesc},productStatus=#{productStatus} where id=#{id}")
    public void update(Product product);


//     删除
    @Delete("delete from product where id=#{id}")
    public void delete(String id) throws Exception;

    //根据产品名称模糊查找
    @Select("select * from product where productName like #{productName}")
    List<Product> findByName(String productName);
}
