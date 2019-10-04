package com.liujiajun.controller;

import com.github.pagehelper.PageInfo;
import com.liujiajun.domain.Member;
import com.liujiajun.domain.Orders;
import com.liujiajun.domain.Product;
import com.liujiajun.domain.Traveller;
import com.liujiajun.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private ITravellerService travellerService;

    @Autowired
    private IOrder_TravellerService order_travellerService;

    //查找所有
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true,defaultValue = "1")Integer page,
                                @RequestParam(value = "pageSize",required = true,defaultValue = "4")Integer pageSize) throws Exception{

        ModelAndView mv=new ModelAndView();
        List<Orders> ordersList = ordersService.findAll(page,pageSize);

        PageInfo pageInfo = new PageInfo(ordersList);

        mv.addObject("pageInfo",pageInfo);

//        mv.addObject("ordersList",ordersList);
        mv.setViewName("orders-list");
        return mv;
    }

    //添加订单
    @RequestMapping(value = "/save.do",method = RequestMethod.GET)
    public ModelAndView saveUI() throws Exception {

        //查找所有产品
        List<Product> productList = productService.findAll();

        //查找所有会员
        List<Member> memberList = memberService.findAll();

        //查找所有旅客
        List<Traveller> travellerList = travellerService.findAll();

        ModelAndView mv=new ModelAndView();
        mv.addObject("productList",productList);
        mv.addObject("memberList",memberList);
        mv.addObject("travellerList",travellerList);

        mv.setViewName("orders-add");
        return mv;

    }

    //添加订单
    @RequestMapping(value = "/save.do",method = RequestMethod.POST)
    public ModelAndView save(Orders orders,String[] travellersId) throws Exception {   //String orderNum

        //除了旅客之外的信息封装到Orders中， 将旅客的id封装到一个数组中

        //添加订单
        ordersService.save(orders);

        //添加订单后，要根据订单编号查询的订单的id，因为id是随机生成的
        Orders findOrders = ordersService.findByOrderNum(orders.getOrderNum());
        String orderId = findOrders.getId();

        //在中间表order_traveller中添加记录
        for (String travellerId : travellersId) {
            order_travellerService.save(orderId,travellerId);
        }

        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:findAll.do");
        return mv;
    }

    //修改订单
    @RequestMapping(value = "/update.do",method = RequestMethod.GET)
    public ModelAndView update(String id)throws Exception{

        //订单
        Orders orders = ordersService.findById(id);

        //查找所有产品
        List<Product> productList = productService.findAll();

        //查找所有会员
        List<Member> memberList = memberService.findAll();

        //查找所有旅客
//        List<Traveller> travellerList = travellerService.findAll();

        //查找所有可以添加的旅客
        List<Traveller> travellerCanAdd = ordersService.findTravellerCanAdd(id);

        ModelAndView mv=new ModelAndView();
        mv.addObject("orders",orders);
        mv.addObject("productList",productList);
        mv.addObject("memberList",memberList);
        mv.addObject("travellerCanAdd",travellerCanAdd);

        mv.setViewName("orders-update");
        return mv;
    }

    //修改订单
    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    public ModelAndView update(Orders orders,String[] travellersId) throws Exception {

        //除了旅客之外的信息封装到Orders中， 将旅客的id封装到一个数组中

        //修改订单
        ordersService.update(orders);

        //在中间表order_traveller中修改记录
        order_travellerService.update(orders.getId(),travellersId);

        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:findAll.do");
        return mv;
    }


    //删除订单
    @RequestMapping("/delete.do")
    public String delete(String[] selectIds){

        ordersService.delete(selectIds);
        return "redirect:findAll.do";

    }



    //查询订单详情
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id)throws Exception{
        ModelAndView mv=new ModelAndView();
        Orders orders = ordersService.findById(id);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }


    //搜索订单
    @RequestMapping("/findOrders.do")
    public ModelAndView findOrders(Orders orders) throws Exception {

        ModelAndView mv=new ModelAndView();

        List<Orders> findOrders = ordersService.searchByOrderNum(orders.getOrderNum());

        mv.addObject("findOrderNum",orders.getOrderNum());
        mv.addObject("findOrders",findOrders);
        mv.setViewName("orders-find-list");

        return mv;

    }




}
