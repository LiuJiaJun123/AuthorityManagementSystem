package com.liujiajun.controller;

import com.github.pagehelper.PageInfo;
import com.liujiajun.domain.Product;
import com.liujiajun.exception.CustomException;
import com.liujiajun.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true,defaultValue = "1")Integer page,
                                @RequestParam(value = "pageSize",required = true,defaultValue = "4")Integer pageSize) throws Exception{

        ModelAndView mv=new ModelAndView();

        List<Product> productList = productService.findAll(page,pageSize);
        PageInfo pageInfo = new PageInfo(productList);

        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("product-list");

        return mv;
    }


    //添加产品
    @RequestMapping("save.do")
    public String save(Product product){
        productService.save(product);
        return "redirect:findAll.do";

    }

   //删除产品
   @RequestMapping("delete.do")
   public String delete(String[] selectIds) throws Exception {

       try {
           productService.delete(selectIds);
       } catch (Exception e) {
           e.printStackTrace();
           throw new  CustomException("删除产品失败，请先删除包含该产品的订单！");
       }
       return "redirect:findAll.do";

   }

    //修改产品信息
    @RequestMapping(value = "update.do",method = RequestMethod.GET)
    public ModelAndView updateUI(String id) throws Exception {
        Product product = productService.findById(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("productInfo",product);
        mv.setViewName("product-update");
        return mv;

    }

    //修改产品信息
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    public String update(Product product){
        productService.update(product);
        return "redirect:findAll.do";

    }


    //搜索课程
    @RequestMapping("/findProduct.do")
    public ModelAndView findProduct(Product product) throws Exception {

        ModelAndView mv=new ModelAndView();

        List<Product> findProduct = productService.findByName(product.getProductName());

        mv.addObject("findName",product.getProductName());
        mv.addObject("findProduct",findProduct);
        mv.setViewName("product-find-list");

        return mv;

    }



}
