package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IShippingService {
    public ServerResponse add(Shipping shipping);
    public ServerResponse del(Integer id,Integer userId);

    public ServerResponse findShippingById(Integer shippingid);
    public ServerResponse updateByUserIdAndShippingId(Shipping shipping);
    public ServerResponse selectAll(Integer pageNume,Integer  pageSize);
}
