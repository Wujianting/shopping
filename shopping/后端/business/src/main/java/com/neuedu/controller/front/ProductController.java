package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/product/")
@CrossOrigin
public class ProductController {
    @Autowired
    IProductService productService;
    /**
     * 搜索商品
     *productName
     *productId
     * pageNume(default=1)
     * pageSize(default=10)
     * */
    @RequestMapping(value = "/search.do")
    public ServerResponse search(@RequestParam(name="productName",required = false) String productName,
                                 @RequestParam(name="productId",required = false)  Integer productId,
                                 @RequestParam(name="pageNume",required = false,defaultValue = "1") Integer pageNume,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "100") Integer pageSize,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return productService.userfind(productName, productId,pageNume,pageSize);
    }
    /**
     * 搜索热门商品
     * */
    @RequestMapping(value = "/search")
    public ServerResponse search(@RequestParam(name="productId",required = false)  Integer productId,
                                 @RequestParam(name="pageNume",required = false,defaultValue = "1") Integer pageNume,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                 HttpSession session){
        /*UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }*/
        return productService.findhot(productId,pageNume,pageSize);
    }
}
