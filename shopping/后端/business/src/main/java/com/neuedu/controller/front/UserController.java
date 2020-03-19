package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserInfoService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@CrossOrigin
@RestController
@RequestMapping("/userinfo/")
public class UserController {
    @Autowired
    IUserInfoService iUserInfoService;
    /**
     * 注册接口
     * */
    @RequestMapping(value = "register.do")
    public ServerResponse register(UserInfo userInfo){
        return   iUserInfoService.register(userInfo);
    }
    /**
     * 登录接口
     * */
    @RequestMapping(value = "login.do/{username}/{password}")
    public ServerResponse login(@PathVariable("username") String username,
                                @PathVariable("password") String password, HttpSession session){
        ServerResponse serverResponse=iUserInfoService.login(username, password,1);
        //判断是否成功
       if(serverResponse.isSuccess()) {
           session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
       }
        return serverResponse;
    }
    /**
     * 根据username密保问题
     * */
    @RequestMapping("forget_get_question/{username}")
    public ServerResponse forget_get_question(@PathVariable("username") String username){
            return  iUserInfoService.forget_get_question(username);
    }
    /**
     * 提交答案
     * */
    @RequestMapping("forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        return  iUserInfoService.forget_check_answer(username, question, answer);
    }
    /**
     * 修改密码
     * */
    @RequestMapping("forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String newpassword,String forgettoken){
        return  iUserInfoService.forget_reset_password(username, newpassword, forgettoken);
    }
    /**
     * 修改用户信息
     * */
    @RequestMapping("update_information.do")
    public ServerResponse update_information(UserInfo userInfo,HttpSession session){
        UserInfo loginuserInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(loginuserInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        userInfo.setId(loginuserInfo.getId());
        ServerResponse serverResponse=iUserInfoService.update_information(userInfo);
        return serverResponse;
    }
    /**
     * 退出登录
     * */

    @RequestMapping("logout.do")
    public ServerResponse exit(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return  ServerResponse.serverResponseBySuccess();
    }
    /**
     * 登录状态下修改密码
     * */
    @RequestMapping(value = "login")
    public ServerResponse updatepwd( String oldpassword, String newpassword,  HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
          return  iUserInfoService.reset_password(userInfo.getUsername(),oldpassword,newpassword);

    }
    /**
     *.获取当前登录用户的详细信息
     * */
    @RequestMapping("get_information.do")
    public ServerResponse get_information(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        userInfo.setId(userInfo.getId());
        ServerResponse serverResponse=iUserInfoService.select(userInfo);
        return serverResponse;
    }

}
