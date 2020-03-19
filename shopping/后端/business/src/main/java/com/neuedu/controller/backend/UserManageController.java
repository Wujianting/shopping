package com.neuedu.controller.backend;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserInfoService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage")
public class UserManageController {
    /**
     * 登录接口
     * */
    @Autowired
    IUserInfoService iUserInfoService;
    @RequestMapping(value = "login.do/{username}/{password}")
    public ServerResponse login(@PathVariable("username") String username,
                                @PathVariable("password") String password, HttpSession session){
        ServerResponse serverResponse=iUserInfoService.login(username, password, RoleEnum.ROLE_ADMIN.getRole());
        //判断是否成功
        if(serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return serverResponse;
    }
    /**
     * 用户列表
     * */
    @RequestMapping(value = "list.do")
    public ServerResponse getAll( @RequestParam(name="pageNume",required = false,defaultValue = "1") Integer pageNume,
                                  @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=userInfo.getRole();
        if(role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"权限不足");
        }
        return iUserInfoService.selectAll(pageNume, pageSize);
    }
}
