package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderItemService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orderitem/")
public class OrderItemController {
    @Autowired
    IOrderItemService orderItemService;
    /**
     * 根据订单号查询订单详情
     * */
    @RequestMapping("select/{orderNO}")
    public ServerResponse selectOrder(@PathVariable("orderNO") long orderNO, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return orderItemService.findOrderItemByOrderNoAnduserId(userInfo.getId(),orderNO);
    }
}
