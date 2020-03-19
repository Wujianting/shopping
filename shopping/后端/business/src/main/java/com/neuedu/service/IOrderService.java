package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface IOrderService {
    /**
     * 创建订单
     * */
    public ServerResponse createOrder(Integer userId,Integer shippingId);
    /**
     * 按订单号查询
     * */
    public ServerResponse selectByOrderNo(Integer userId,Long orderNo);
    /**
     * 查找未支付订单
     * */
    public ServerResponse selectnopay(Integer userId);
    /**
     * 后台查找未支付订单
     * */
    public ServerResponse nopay();
    /**
     * 查找支付订单
     * */
    public ServerResponse selectpay(Integer userId);
    /**
     * 后台查找支付订单
     * */
    public ServerResponse pay();
    /**
     * 查找全部订单
     * */
    public ServerResponse allorder(Integer userId);
    // 后台查找全部订单
    public ServerResponse  manageallorder(Integer pageNume,Integer pageSize);
    //查找已发货订单
    public ServerResponse send(Integer userId);
    //查找后台已发货订单
    public ServerResponse yes();
    /**
     * 取消订单
     *
     */
    public ServerResponse  cancel(Integer userId,Long orderNo);
    //删除订单
    public ServerResponse deles(String orderNos);
    /**
     *
     * 订单发货
     */
    public ServerResponse updateByuserIdAndOrderNo(Long orderNo);
    /**
     * 获取购物车中订单明细
     * */
    ServerResponse get_order_cart_product(Integer userId);
    /**
     * 支付
     * */
    public ServerResponse pay(Integer userId,Long orderNo);

    /**
     *
     * 支付回调接口
     * */
    public String callback(Map<String,String> requestParams);
}
