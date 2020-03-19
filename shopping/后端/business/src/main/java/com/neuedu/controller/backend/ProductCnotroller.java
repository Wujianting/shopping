package com.neuedu.controller.backend;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.service.IProductService;
import com.neuedu.service.Impl.ProductServiceImpl;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product/")
public class ProductCnotroller {
    @Autowired
    IProductService productService;
    @Autowired
    IOrderService orderService;
    /**
     * 商品添加或更新
     * */
    @RequestMapping("/save.do")
        public ServerResponse addOrUpdate(Product product, HttpSession session){
            UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
            if(userInfo==null){
                return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
            }
            int role=userInfo.getRole();
            if(role== RoleEnum.ROLE_USER.getRole()){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
            }
            return productService.addOrUpdate(product);
        }


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
            return productService.search(productName, productId, pageNume, pageSize);
        }
        //商品详情
        @RequestMapping(value = "/{productId}")
        public ServerResponse detial(@PathVariable("productId") Integer productId,HttpSession session){
            UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
            if(userInfo==null){
                return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
            }
            int role=userInfo.getRole();
            if(role== RoleEnum.ROLE_USER.getRole()){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
            }
            return  productService.detial(productId);
        }

    /**
     *搜索全部商品
     */
    @RequestMapping(value = "search")
    public ServerResponse search(@RequestParam(name="productName",required = false) String productName,
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
        return productService.allproduct(productName,pageNume,pageSize);
    }
    /**
     * 搜索热门商品
     * */
    @RequestMapping(value = "searchhot")
    public ServerResponse search(@RequestParam(name="productId",required = false)  Integer productId,
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
        return productService.findhot(productId,pageNume,pageSize);
    }
    /**
     * 删除商品
     * */
    @RequestMapping(value = "dele/{productIds}")
    public ServerResponse search(@PathVariable("productIds") String productIds,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return productService.dele(productIds);
    }
}
