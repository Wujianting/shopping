package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    ICartService cartService;
    /**
     * 添加商品到购物车
     * */
    @RequestMapping("add/{productId}/{count}")
    public ServerResponse addCart(@PathVariable("productId") Integer productId,
                                  @PathVariable("count") Integer count,
                                  HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }


        return  cartService.addProductToCart(userInfo.getId(),productId,count);
    }
    /**
     * 购物车list列表
     * */
    @RequestMapping("select")
    public ServerResponse selectCartList(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  cartService.selectByuserId(userInfo.getId());
    }
    /**
     * 更新购车某个产品的数量
     * */
    @RequestMapping("update/{productId}/{count}")
    public ServerResponse updateProductCount(@PathVariable("productId")Integer productId,@PathVariable("count")Integer count,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  cartService.updateCount(userInfo.getId(),productId,count);
    }
    /**
     * 移除购物车某个产品
     * */
    @RequestMapping("delete/{productIds}")
    public ServerResponse deleteProduct(@PathVariable("productIds") String productIds, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  cartService.deleteProduct(userInfo.getId(),productIds);
    }
    /**
     * 购物车取消选中的某个产品
     * */
    @RequestMapping("un_select/{productId}")
    public ServerResponse select(@PathVariable("productId") Integer productId, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  cartService.un_select(userInfo.getId(),productId);
    }
    //购物车选中的某个产品
    @RequestMapping("select/{productId}")
    public ServerResponse un_select(@PathVariable("productId") Integer productId, HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return  cartService.select(userInfo.getId(),productId);
    }

}
