package com.neuedu.service.Impl;

import com.google.common.collect.Lists;
import com.neuedu.common.OrderStatusEnum;
import com.neuedu.common.PaymentEnum;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.OrderItemMapper;
import com.neuedu.dao.OrderMapper;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.OrderItem;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IOrderItemService;
import com.neuedu.service.IShippingService;
import com.neuedu.utils.DateUtils;
import com.neuedu.vo.OrderItemVO;
import com.neuedu.vo.OrderVO;
import com.neuedu.vo.ShippingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemServiceImpl implements IOrderItemService {
    @Autowired
    IShippingService shippingService;
    @Value("${business.imageHost}")
    private String imageHost;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    OrderMapper orderMapper;
    @Override
    public ServerResponse findOrderItemByOrderNoAnduserId(Integer userId, Long orderNo) {
        if(orderNo==null){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"订单号必填");
        }
        List<Order> orderList=Lists.newArrayList();
        orderList=orderMapper.findOrderItemByOrderNoAnduserId(userId,orderNo);
        List<OrderVO> orderVOList=Lists.newArrayList();
        List<OrderItemVO> orderItemVOList=new ArrayList<OrderItemVO>();
        for(Order order:orderList){
            List<OrderItem> orderItemList=orderItemMapper.findOrderItemByOrderNoAnduserId(userId,orderNo);
            OrderVO orderVO=assemble(order,orderItemList,order.getShippingId());
            orderVOList.add(orderVO);
        }
        if(orderVOList==null||orderVOList.size()==0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"没有找到订单");
        }else {
            return ServerResponse.serverResponseBySuccess(orderVOList);
        }

    }

    @Override
    public ServerResponse findOrderItem( Long orderNo) {
        if(orderNo==null){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"订单号必填");
        }
        List<Order> orderList=Lists.newArrayList();
        orderList=orderMapper.findOrderItem(orderNo);
        List<OrderVO> orderVOList=Lists.newArrayList();
        List<OrderItemVO> orderItemVOList=new ArrayList<OrderItemVO>();
        for(Order order:orderList){
            List<OrderItem> orderItemList=orderItemMapper.findOrderItem(orderNo);
            OrderVO orderVO=assemble(order,orderItemList,order.getShippingId());
            orderVOList.add(orderVO);
        }
        if(orderVOList==null||orderVOList.size()==0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"没有找到订单");
        }else {
            return ServerResponse.serverResponseBySuccess(orderVOList);
        }

    }


    private OrderVO assemble(Order order, List<OrderItem> orderItemList, Integer shippingId){
        OrderVO orderVO=new OrderVO();

        List<OrderItemVO> orderItemVOList= Lists.newArrayList();
        for(OrderItem orderItem:orderItemList){
            OrderItemVO orderItemVO= assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }
        orderVO.setOrderItemVoList(orderItemVOList);
        orderVO.setImageHost(imageHost);
        ServerResponse<Shipping> serverResponse= shippingService.findShippingById(shippingId);
        if(!serverResponse.isSuccess()){
            return null;
        }
        Shipping shipping=serverResponse.getData();
        if(shipping!=null){
            orderVO.setShippingId(shippingId);
            ShippingVO shippingVO= assmbleShippingVO(shipping);
            orderVO.setShippingVo(shippingVO);
            orderVO.setReceiverName(shipping.getReceiverName());
        }

        orderVO.setStatus(order.getStatus());
        OrderStatusEnum orderStatusEnum=OrderStatusEnum.codeOf(order.getStatus());
        if(orderStatusEnum!=null){
            orderVO.setStatusDesc(orderStatusEnum.getDesc());
        }

        orderVO.setPostage(0);
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        PaymentEnum paymentEnum=PaymentEnum.codeOf(order.getPaymentType());
        if(paymentEnum!=null){
            orderVO.setPaymentTypeDesc(paymentEnum.getDesc());
        }
        orderVO.setOrderNo(order.getOrderNo());

        return orderVO;
    }

    private OrderItemVO assembleOrderItemVO(OrderItem orderItem){
        OrderItemVO orderItemVO=new OrderItemVO();

        if(orderItem!=null){

            orderItemVO.setQuantity(orderItem.getQuantity());
            orderItemVO.setCreateTime(DateUtils.dateToStr(orderItem.getCreateTime()));
            orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
            orderItemVO.setOrderNo(orderItem.getOrderNo());
            orderItemVO.setProductId(orderItem.getProductId());
            orderItemVO.setProductImage(orderItem.getProductImage());
            orderItemVO.setProductName(orderItem.getProductName());
            orderItemVO.setTotalPrice(orderItem.getTotalPrice());

        }

        return orderItemVO;
    }

    private ShippingVO assmbleShippingVO(Shipping shipping){
        ShippingVO shippingVO=new ShippingVO();

        if(shipping!=null){
            shippingVO.setReceiverAddress(shipping.getReceiverAddress());
            shippingVO.setReceiverCity(shipping.getReceiverCity());
            shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
            shippingVO.setReceiverMobile(shipping.getReceiverMobile());
            shippingVO.setReceiverName(shipping.getReceiverName());
            shippingVO.setReceiverPhone(shipping.getReceiverPhone());
            shippingVO.setReceiverProvince(shipping.getReceiverProvince());
            shippingVO.setReceiverZip(shipping.getReceiverZip());
        }
        return shippingVO;
    }
}
