package com.neuedu.controller.backend;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    /**
     * 添加类别
     * */
    @RequestMapping("/addCategory.do")
    public ServerResponse addCategory(Category category,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return categoryService.addCategory(category);
    }
    /**
     * 修改类别
     * categoryId
     * categoryName
     * categoryUrl
     * */
    @RequestMapping("/updateCategory.do")
    public ServerResponse updateCategory(Category category,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return categoryService.updateCategory(category);
    }
    /**
     * 查看平级类别
     * categoryId
     *
     * */
    @RequestMapping("/{categoryId}")
    public ServerResponse getCategory(@PathVariable("categoryId") Integer categoryId,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return categoryService.getCategory(categoryId);
    }
    /**
     * 递归平级类别
     * categoryId
     *
     * */
    @RequestMapping("/deep/{categoryId}")
    public ServerResponse deepCategory(@PathVariable("categoryId") Integer categoryId,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return categoryService.deepCategory(categoryId);
    }
}
