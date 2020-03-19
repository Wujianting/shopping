package com.neuedu.dao;

import com.neuedu.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbg.generated
     */
    int insert(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbg.generated
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbg.generated
     */
    List<Product> selectAll();

    /**
     * 搜索热门商品
     * */
    List<Product> findhot(@Param("productId") Integer productId);
    /**
     * 搜索全部商品
     * */
    List<Product> allproduct(@Param("productName") String productName);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Product record);

    List<Product> findProductByNameAndId(@Param("productId") Integer productId,@Param("productName") String productName);


    List<Product> userfind(@Param("productId") Integer productId,@Param("productName") String productName);

    /**
     * 扣库存
     * */
    int reduceProductStock(@Param("productId")Integer productId,@Param("stock")Integer stock);

    /**
     * 移除产品
     * */
    int dele(@Param("productList") List<Integer> productList);
}