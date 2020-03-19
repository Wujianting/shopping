package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Component;

@Component
public interface IOrderItemService {
    /**
     * 按订单号查询
     * */
    public ServerResponse findOrderItemByOrderNoAnduserId(Integer userId, Long orderNo);
    /**
     * 后台按订单号查询
     * */
    public ServerResponse findOrderItem(Long orderNo);
}
