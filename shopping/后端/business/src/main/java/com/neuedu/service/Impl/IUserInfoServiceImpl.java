package com.neuedu.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserInfoService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IUserInfoServiceImpl implements IUserInfoService {
    /**
     * 注册
     * */
    @Autowired
   private UserInfoMapper userInfoMapper;
    @Override
    public ServerResponse register(UserInfo userInfo) {
        //step1:参数校验
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
        }
        //step2:判断用户名是否存在
       int result=userInfoMapper.isexitsname(userInfo.getUsername());
        if(result>0){//用户名已存在
            return ServerResponse.serverResponseByError(ResponseCode.USERNAME_EXITS,"用户名已存在");
        }
        //step3:判断邮箱是否存在
        int resultemail=userInfoMapper.isexitsemail(userInfo.getEmail());
        if(resultemail>0){//邮箱已存在
            return ServerResponse.serverResponseByError(ResponseCode.EMAIL_EXITS,"邮箱已存在");
        }
        //step4:MD5密码加密,设置用户角色
         userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
         userInfo.setRole(RoleEnum.ROLE_USER.getRole());
        //step5:注册
        int insertResult=userInfoMapper.insert(userInfo);
        if(insertResult<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"注册失败");
        }
        //step6:返回
        return ServerResponse.serverResponseBySuccess();
    }

    /**
     * 登录
     * */
    @Override
    public ServerResponse login(String username, String password,int type) {
        //step1: 参数校验
        if(username==null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"用户名不能为空");
        }
        if(password==null||password.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"密码不能为空");
        }
        //step2:判断用户名是否存在
        int result=userInfoMapper.isexitsname(username);
        if(result<=0){//用户名不存在
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"用户名不存在");
        }
        //step3:密码加密
        password=MD5Utils.getMD5Code(password);
        //step4:登录
        UserInfo userInfo=userInfoMapper.finduserbynameandpwd(username, password);
        if(userInfo==null){//密码错误
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"密码错误");
        }
        if(type==0){//管理员登录
            if(userInfo.getRole()==RoleEnum.ROLE_USER.getRole()){//没有管理员权限
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"登录权限不足");
            }
        }
        return ServerResponse.serverResponseBySuccess(userInfo);
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        //step1: 参数非空校验
        if(username==null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"用户名不能为空");
        }
        //step2:根据用户名查询问题
        String question=userInfoMapper.forget_get_question(username);
        if(question==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"没有查询到密保问题");
        }
        //step3:返回结果
        return ServerResponse.serverResponseBySuccess(question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //step1: 参数非空校验
        if(username==null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"用户名不能为空");
        }
        if(question==null||question.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"密保问题不能为空");
        }
        if(answer==null||answer.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"答案不能为空");
        }
        //step2:校验答案
        int result=userInfoMapper.forget_check_answer(username,question,answer);
        if(result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"答案错误");
        }
        //step3:返回结果
        //生成token
        String token=UUID.randomUUID().toString();
        TokenCache.set("username:"+username,token);
        return ServerResponse.serverResponseBySuccess(token);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String newpassword, String forgettoken) {
        //step1: 参数非空校验
        if(username==null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"用户名不能为空");
        }
        if(newpassword==null||newpassword.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"新密码不能为空");
        }
        if(forgettoken==null||forgettoken.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"token不能为空");
        }
        //是否修改自己的账号
        String token=TokenCache.get("username:"+username);
        if(token==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"不能修改别人的账号或者token已过期");
        }
        if(!token.equals(forgettoken)){
          return ServerResponse.serverResponseByError(ResponseCode.ERROR,"无效的token");
        }
       int result=userInfoMapper.forget_reset_password(username, MD5Utils.getMD5Code(newpassword));
        if(result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"密码修改失败");
        }
     return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse update_information(UserInfo userInfo) {
        //step1: 参数非空校验
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
       int result=userInfoMapper.updateUser(userInfo);
        if(result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"修改失败");
        }
        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse reset_password(String username, String passwordOld, String passwordNew) {
        //step1:参数非空校验
        if(passwordOld==null||passwordOld.equals("")){
            return ServerResponse.serverResponseByError("用户名旧密码不能为空");
        }
        if(passwordNew==null||passwordNew.equals("")){
            return ServerResponse.serverResponseByError("用户新密码不能为空");
        }
        //step2:根据username和passwordOld
        UserInfo userInfo= userInfoMapper.finduserbynameandpwd(username,MD5Utils.getMD5Code(passwordOld));
        if(userInfo==null){
            return ServerResponse.serverResponseByError("旧密码错误");
        }
        //step3:修改密码
        userInfo.setPassword(MD5Utils.getMD5Code(passwordNew));
        int result=userInfoMapper.updateByKey(userInfo);
        if(result>0){
            return  ServerResponse.serverResponseBySuccess();
        }
        return ServerResponse.serverResponseByError("密码修改失败");
    }

    @Override
    public ServerResponse select(UserInfo userInfo) {
        //step1: 参数非空校验
        if(userInfo==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        UserInfo userInfo1=userInfoMapper.select(userInfo);
        if(userInfo1==null||userInfo1.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"查看失败");
        }
        return ServerResponse.serverResponseBySuccess(userInfo1);
    }

    @Override
    public ServerResponse selectAll(Integer pageNume, Integer pageSize) {
        Page page= PageHelper.startPage(pageNume,pageSize);
       List<UserInfo> userInfos=userInfoMapper.selectAll();
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(userInfos);
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }
}
