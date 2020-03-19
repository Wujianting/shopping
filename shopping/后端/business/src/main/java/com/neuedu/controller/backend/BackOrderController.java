package com.neuedu.controller.backend;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderItemService;
import com.neuedu.service.Impl.OrderServiceImpl;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/backorderController/")
public class BackOrderController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    IOrderItemService orderItemService;
    /**
     * 订单发货
     * */
    @RequestMapping("update/{orderNO}")
    public ServerResponse selectOrder(@PathVariable("orderNO") long orderNO, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return orderService.updateByuserIdAndOrderNo(orderNO);
    }
    //查找已发货订单
    @RequestMapping("send")
    public ServerResponse findsend(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return orderService.yes();
    }
    // 查找全部订单

    @RequestMapping("all")
    public ServerResponse findtall(
            @RequestParam(name="pageNume",required = false,defaultValue = "1") Integer pageNume,
            @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize,
            HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return orderService.manageallorder(pageNume,pageSize);
    }
    /**
     * 删除订单
     * */
    @RequestMapping("cancel/{orderNos}")
    public ServerResponse cancel(@PathVariable("orderNos") String orderNos,
                                 HttpSession session){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return  orderService.deles(orderNos);
    }
    /**
     * 根据订单号查询订单详情
     * */
    @RequestMapping("select/{orderNO}")
    public ServerResponse seeorderno(@PathVariable("orderNO") long orderNO, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return orderItemService.findOrderItem(orderNO);
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
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return orderService.nopay();
    }
    /**
     * 查找支付订单
     * */
    @RequestMapping("find")
    public ServerResponse findtOrder(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return orderService.pay();
    }

}
