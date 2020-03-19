package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Cart;

import java.util.List;

public interface ICartService {
    /**
     *
     * 添加商品到购物车
     */

    public ServerResponse addProductToCart(Integer userId,Integer produceId,Integer count);
    /**
     * 根据用户id查看购物车中已选中的商品
     * */
    public ServerResponse<List<Cart>> findCartByUseridAndChecked(Integer userId);
    /**
     * 批量删除购物车商品
     * */
    public ServerResponse deleteBatch(List<Cart> cartList);
    /**
     * 查询购物车List列表
     * */
    public ServerResponse selectByuserId(Integer userId);
    /**
     * 更新购物车中某个产品的数量
     * */
    public ServerResponse updateCount(Integer userId,Integer productId,Integer count);
/**
 * 移除购物车某个产品
 * */
    public ServerResponse deleteProduct(Integer userId,String productIds);
    /**
     * 购物车取消选中的某个产品
     * */
    public ServerResponse un_select(Integer userId,Integer productId);
    /**
     * 购物车选中的某个产品
     * */
    public ServerResponse select(Integer userId,Integer productId);
}
