package com.liujiajun.service;

import com.liujiajun.domain.Product;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface IProductService {

    //不分页查找所有产品
    public List<Product> findAll() throws Exception;

    //分页查找所有产品
    public List<Product> findAll(Integer page,Integer pageSize) throws Exception;

    //添加产品
    public void save(Product product);

    //删除产品
    void delete(String[] selectIds) throws Exception;

    //根据id查找
    Product findById(String id) throws Exception;

    //修改产品信息
    void update(Product product);

    //根据产品名称模糊查找
    List<Product> findByName(String productName);


}
