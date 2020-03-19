package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.vo.ProductDetailVO;


public interface IProductService {
    public ServerResponse addOrUpdate(Product product);
    /**
     * 后台商品搜索
     * */
    public ServerResponse search( String productName,
                                  Integer productId,
                                  Integer pageNume,
                                  Integer pageSize);
    /**
     * 后台移除产品
     * */
    public ServerResponse dele(String productIds);

    //前台商品搜索

    public ServerResponse userfind(
            String productName,
            Integer productId,
            Integer pageNume,
            Integer pageSize);

    /**
     * 商品详情
     * */
    public ServerResponse<ProductDetailVO>detial(Integer productId);
    /**
     * 商品详情
     * */
    public ServerResponse<Product> findProduceByProductId(Integer productId);
    /**
     * 根据商品id查询商品信息（库存）
     * */
    public ServerResponse<Product> findProductById(Integer productId);
    /**
     * 扣库存
     * */
    public ServerResponse reduceStock(Integer produceId,Integer stock);
/**
 * 搜索热门商品
 * */
public ServerResponse<Product> findhot( Integer productId,Integer pageNume,Integer pageSize);
    /**
     * 搜索全部商品
     * */
    public ServerResponse<Product> allproduct(String productName,Integer pageNume,Integer pageSize);
}
