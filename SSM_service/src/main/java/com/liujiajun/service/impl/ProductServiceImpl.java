package com.liujiajun.service.impl;

import com.github.pagehelper.PageHelper;
import com.liujiajun.dao.IOrdersDao;
import com.liujiajun.dao.IProductDao;
import com.liujiajun.domain.Orders;
import com.liujiajun.domain.Product;
import com.liujiajun.exception.CustomException;
import com.liujiajun.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IOrdersDao ordersDao;

    //不分页查找所有产品
    @Override
    public List<Product> findAll() throws Exception {
        return productDao.findAll();
    }

    //分页查找所有产品
    @Override
    public List<Product> findAll(Integer page,Integer pageSize) throws Exception {

        PageHelper.startPage(page,pageSize);
        return productDao.findAll();
    }

//    添加产品
    @Override
    public void save(Product product) {
        productDao.save(product);
    }

//    删除产品
    @Override
    public void delete(String[] selectIds) throws Exception{
        if(selectIds!=null&&selectIds.length>0){
            for (String selectId : selectIds) {
                productDao.delete(selectId);
            }
        }

    }

    //根据id查找
    @Override
    public Product findById(String id) throws Exception {
        return productDao.findById(id);
    }

    //修改产品信息
    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    //根据产品名称模糊查找
    @Override
    public List<Product> findByName(String productName) {
        productName="%"+productName+"%";
        return productDao.findByName(productName);
    }


}
