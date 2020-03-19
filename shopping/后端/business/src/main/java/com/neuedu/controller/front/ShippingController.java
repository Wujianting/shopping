package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IShippingService;
import com.neuedu.utils.Const;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    IShippingService shippingService;
    @RequestMapping(value = "add.do")
    public ServerResponse add(Shipping shipping, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        shipping.setUserId(userInfo.getId());
        return shippingService.add(shipping);
    }
    @RequestMapping(value = "del/{id}")
    public ServerResponse del(@PathVariable("id") Integer id,
                              HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        Integer userId=userInfo.getId();
        return shippingService.del( id,userId);
    }

    @RequestMapping(value = "update.do")
    public ServerResponse update(Shipping shipping, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        Integer userId=userInfo.getId();
        return shippingService.updateByUserIdAndShippingId(shipping);
    }
    @RequestMapping(value = "sselect/{id}")
    public ServerResponse select(@PathVariable("id") Integer id, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return shippingService.findShippingById(id);
    }
    @RequestMapping(value = "select")
    public ServerResponse select(  @RequestParam(name="pageNume",required = false,defaultValue = "1") Integer pageNume,
                                   @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return shippingService.selectAll(pageNume, pageSize);
    }
}
