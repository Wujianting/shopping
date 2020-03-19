package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IUserInfoService {



    /**
     *
     * 注册接口
     * @param userInfo
     * return ServerResponse
     * */
    public ServerResponse register(UserInfo userInfo);

    /**
     * 登录接口
     * @param username password
     *                 type: 1 普通用户  0：管理员
     * return ServerResponse
     * */
    public ServerResponse login(String username,String password,int type);
    /**
     * 根据username密保问题
     * */
    @RequestMapping("forget_get_question/{username}")
    public ServerResponse forget_get_question(@PathVariable("username") String username);
    /**
     * 提交答案
     * */
    @RequestMapping("forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer);
    /**
     * 修改密码
     * */
    @RequestMapping("forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String newpassword,String forgettoken);
    /**
     * 修改密码
     * */
    public ServerResponse update_information(UserInfo userInfo);
    /**
     * 登录状态下修改密码
     * */
   public ServerResponse reset_password(String username,String passwordOld,String  passwordNew);

    /**
     *.获取当前登录用户的详细信息
     * */
    public ServerResponse select(UserInfo userInfo);
    /**
     *.获取用户列表
     * */
    public ServerResponse selectAll(Integer pageNume, Integer pageSize);
}
