package com.neuedu.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import com.neuedu.vo.shipp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingSefrviceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;
    //添加地址
    @Override
    public ServerResponse add(Shipping shipping) {
        //step1:参数非空判断
        if(shipping==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }
        Integer shippingid=shipping.getId();
        if(shippingid==null){
            //添加
           int result=shippingMapper.insert(shipping);
           if(result<=0){
               return ServerResponse.serverResponseByError(ResponseCode.ERROR,"添加地址失败");
           }else{
               return ServerResponse.serverResponseBySuccess(shipping.getId());
           }
        }else {
            //更新
        }

        return null;
    }
    //删除地址
    @Override
    public ServerResponse del(Integer id,Integer userId) {
        //step1:参数非空判断
        if (id== null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "删除地址失败");
        }else {
            //删除
            int result=shippingMapper.deleteByUserIdAndShippingId(userId,id);
            if(result>0) {
                return ServerResponse.serverResponseBySuccess(ResponseCode.SUCCESS, "删除地址成功");
            }else{
                return ServerResponse.serverResponseByError(ResponseCode.ERROR, "删除地址失败");
            }
        }

    }
    //查看具体的地址
    @Override
    public ServerResponse findShippingById(Integer shippingid) {
        if(shippingid==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"shippingid必传");
        }
        Shipping shipping=shippingMapper.selectByPrimaryKey(shippingid);
        if(shipping==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"收货地址不存在");
        }
        return ServerResponse.serverResponseBySuccess(shipping);
    }
    //更新地址
    @Override
    public ServerResponse updateByUserIdAndShippingId(Shipping shipping) {
        //step1:参数非空判断
        if (shipping== null&&shipping.getId()!=null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "参数必传");
        }else {
            //更新
            int result=shippingMapper.updateByUserIdAndShippingId(shipping);
            if(result>0) {
                return ServerResponse.serverResponseBySuccess(ResponseCode.SUCCESS, "更新地址成功");
            }else{
                return ServerResponse.serverResponseByError(ResponseCode.ERROR, "更新地址失败");
            }
        }

    }
    //查看地址列表
    @Override
    public ServerResponse selectAll(Integer pageNume,Integer  pageSize) {
        Page page= PageHelper.startPage(pageNume,pageSize);
        List<Shipping> shippings=shippingMapper.selectAll();
        List<shipp> shippList=Lists.newArrayList();
        for(Shipping shipping:shippings){
            shipp shipps=assmbleshipp(shipping);
            shippList.add(shipps);
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(shippList);
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }
    private shipp assmbleshipp(Shipping shipping){
        shipp ship=new shipp();

        if(shipping!=null){
            ship.setId(shipping.getId());
            ship.setName(shipping.getReceiverName());
            ship.setTel(shipping.getReceiverPhone());
            ship.setAddress(shipping.getReceiverProvince()+shipping.getReceiverCity()+shipping.getReceiverDistrict()+shipping.getReceiverAddress());
        }
        return ship;
    }
}
