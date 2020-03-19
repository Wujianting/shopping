package com.neuedu.controller.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.Config;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    IOrderService orderService;
    /**
     * 创建订单接口
     * */
    @RequestMapping("{shippingid}")
    public ServerResponse createOrder(@PathVariable("shippingid") Integer shippingid, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderService.createOrder(userInfo.getId(),shippingid);
    }

    /**
     * .获取订单的商品信息
     * */
    @RequestMapping(value = "get_order_cart_product.do.do")
    public ServerResponse get_order_cart_product(HttpSession session){

        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  orderService.get_order_cart_product(userInfo.getId());
    }
    /**
     * 根据订单号查询订单
     * */
    @RequestMapping("select/{orderNO}")
    public ServerResponse selectOrder(@PathVariable("orderNO") long orderNO, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderService.selectByOrderNo(userInfo.getId(),orderNO);
    }
    /**
     * 查找未支付订单
     * */
    @RequestMapping("select")
    public ServerResponse selectOrder(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderService.selectnopay(userInfo.getId());
    }

      // 查找支付订单

    @RequestMapping("find")
    public ServerResponse findtOrder(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderService.selectpay(userInfo.getId());
    }


     // 查找全部订单

    @RequestMapping("all")
    public ServerResponse findtall(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderService.allorder(userInfo.getId());
    }
    //查找已发货订单
    @RequestMapping("send")
    public ServerResponse findsend(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderService.send(userInfo.getId());
    }
    /**
     * .取消订单
     * */
    @RequestMapping("cancel/{orderNO}")
    public ServerResponse cancel(HttpSession session,@PathVariable("orderNO") Long orderNo){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  orderService.cancel(userInfo.getId(),orderNo);
    }


    /**
     * 支付接口
     * */
    @RequestMapping("pay/{orderNo}")
    public  ServerResponse pay(@PathVariable("orderNo") long orderNo,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }

        return orderService.pay(userInfo.getId(),orderNo);
    }

    /**
     * 支付宝服务器接口回调商家服务器接口
     * */
    @RequestMapping("callback.do")
    public String alipay_callback(HttpServletRequest request){
        Map<String,String[]> callbackParams=request.getParameterMap();

        Map<String,String> signParams= Maps.newHashMap();
        Iterator<String> iterator= callbackParams.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String[] values=callbackParams.get(key);
            StringBuffer sbuffer=new StringBuffer();
            if(values!=null&&values.length>0){
                for( int i=0;i<values.length;i++){
                    sbuffer.append(values[i]);
                    if(i!=values.length-1){
                        sbuffer.append(",");
                    }
                }
            }
            signParams.put(key,sbuffer.toString());
        }

        //验签
        try {
            signParams.remove("sign_type");
         boolean result=AlipaySignature.rsaCheckV2(signParams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
         if(result){//验签通过
             System.out.println("通过");
                return  orderService.callback(signParams);
         }else{
             return  "fail";
         }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "ss";
    }
}
