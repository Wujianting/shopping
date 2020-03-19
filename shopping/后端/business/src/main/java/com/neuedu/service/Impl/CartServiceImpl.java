package com.neuedu.service.Impl;

import com.google.common.collect.Lists;
import com.neuedu.common.CheckEnum;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.BigDecimalUtils;
import com.neuedu.utils.Const;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    IProductService productService;
    @Autowired
    CartMapper cartMapper;
    private Integer userId;
    private List<String> productIds;

    @Value("${business.imageHost}")
    private String imageHost;
    //添加商品到购物车
    @Override
    public ServerResponse addProductToCart(Integer userId, Integer produceId, Integer count) {
        //step1:参数非空判断
        if(produceId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品id必传");
        }
        if(count==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品数量不能为空");
        }
        //step2:判断是否存在
         ServerResponse<Product> serverResponse=productService.findProductById(produceId);
        if(!serverResponse.isSuccess()){//商品不存在
            return  ServerResponse.serverResponseByError(serverResponse.getStatus(),serverResponse.getMsg());
        }else {
            Product product=serverResponse.getData();
            if(product.getStock()<=0){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品已售空");
            }
        }
        //step3:判断商品是否在购物车中
          Cart cart=cartMapper.findCartByUidAndPid(userId,produceId);
        if(cart==null){
            //添加
            Cart newCart=new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(produceId);
            newCart.setQuantity(count);
            newCart.setChecked(CheckEnum.CART_PRODUCT_CHECK.getCheck());
            int result=cartMapper.insert(newCart);
            if(result<=0){
                return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"添加失败");
            }
        }else {
            //更新商品在购物车中的数量
            cart.setQuantity(cart.getQuantity()+count);
            int result=cartMapper.updateByPrimaryKey(cart);
            if(result<=0){
                return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"更新失败");
            }
        }
        //step4:封装购物车对象CartVO
        CartVO cartVO=getCartVO(userId);
        //step5:返回CartVO
        return ServerResponse.serverResponseBySuccess(cartVO);
    }
//根据用户id和选中状态查找购物车
    @Override
    public ServerResponse<List<Cart>> findCartByUseridAndChecked(Integer userId) {
       List<Cart> cartList=cartMapper.findCartByUseridAndChecked(userId);
        return ServerResponse.serverResponseBySuccess(cartList);
    }

    @Override
    public ServerResponse deleteBatch(List<Cart> cartList) {
            if(cartList==null||cartList.size()==0){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"要删除的购物车商品不能为空");
            }
           int result=cartMapper.deleteBatch(cartList);
            if(result!=cartList.size()){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"清空购物车失败");
            }
        return ServerResponse.serverResponseBySuccess();
    }
//根据用户id搜索购物车
    @Override
    public ServerResponse selectByuserId(Integer userId) {
        CartVO cartVO=getCartVO(userId);
        return ServerResponse.serverResponseBySuccess(cartVO);
    }
//更新购物车中某件商品的数量
    @Override
    public ServerResponse updateCount(Integer userId, Integer productId, Integer count) {
        //step1:参数非空判断
        if(productId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品id必传");
        }
        if(count==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品数量不能为空");
        }
        //step2:判断是否存在
        ServerResponse<Product> serverResponse=productService.findProductById(productId);
        if(!serverResponse.isSuccess()){//商品不存在
            return  ServerResponse.serverResponseByError(serverResponse.getStatus(),serverResponse.getMsg());
        }else {
            Product product=serverResponse.getData();
            if(product.getStock()<=0){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品已售空");
            }
        }
        //step3:判断商品是否在购物车中
        Cart cart=cartMapper.findCartByUidAndPid(userId,productId);
        if(cart==null){
            //添加
            Cart newCart=new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setQuantity(count);
            newCart.setChecked(CheckEnum.CART_PRODUCT_CHECK.getCheck());
            int result=cartMapper.insert(newCart);
            if(result<=0){
                return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"添加失败");
            }
        }else {
            //更新商品在购物车中的数量
            cart.setQuantity(cart.getQuantity()+count);
            int result=cartMapper.updateByPrimaryKey(cart);
            if(result<=0){
                return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"更新失败");
            }
        }
        //step4:封装购物车对象CartVO
        CartVO cartVO=getCartVO(userId);
        //step5:返回CartVO
        return ServerResponse.serverResponseBySuccess(cartVO);
    }
//删除产品
    @Override
    public ServerResponse deleteProduct(Integer userId,String productIds) {
        //step1:参数非空校验
        if(productIds==null||productIds.equals("")){
            return ServerResponse.serverResponseByError("参数不能为空");
        }
        //step2:productIds-->List<Integer>
        List<Integer> productIdList=Lists.newArrayList();
        String[] productIdsArr=productIds.split(",");
        if(productIdsArr!=null&&productIdsArr.length>0){
            for(String productIdstr:productIdsArr){
                Integer productId=Integer.parseInt(productIdstr);
                productIdList.add(productId);
            }
        }

        //step3:调用dao
        cartMapper.deleteProduct(userId,productIdList);
        //step4:返回结果
        return ServerResponse.serverResponseBySuccess(getCartVO(userId));
    }
//购物车中取消某个选中的产品
    @Override
    public ServerResponse un_select(Integer userId, Integer productId) {
        //step1:参数非空判断
        if(productId==null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "商品id必传");
        }
        //step2:判断是否存在
        int result=cartMapper.un_select(userId,productId);
        //step4:封装购物车对象CartVO
        CartVO cartVO=getCartVO(userId);
        //step5:返回CartVO
        return ServerResponse.serverResponseBySuccess(cartVO);
    }
    //购物车中选中某个产品
    @Override
    public ServerResponse select(Integer userId, Integer productId) {
        //step1:参数非空判断
        if(productId==null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "商品id必传");
        }
        //step2:判断是否存在
        int result=cartMapper.select(userId,productId);
        //step4:封装购物车对象CartVO
        CartVO cartVO=getCartVO(userId);
        //step5:返回CartVO
        return ServerResponse.serverResponseBySuccess(cartVO);
    }
    private CartVO getCartVO(Integer userId){
        CartVO cartVO=new CartVO();
        //step1: 根据userid查询该用户的购物信息----List<Cart>
         List<Cart> cartList=cartMapper.findCarstByUserid(userId);
         if(cartList==null||cartList.size()==0){
             return cartVO;
         }
         //定义购物车商品总价格
        BigDecimal cartTotalPrirce=new BigDecimal("0");
        //step2: List<Cart> ---> List<CartProductVO>
        List<CartProductVO> cartProductVOList= Lists.newArrayList();
        int limit_quantity=0;
         String limitQuantity=null;
            for(Cart cart: cartList){
                CartProductVO cartProductVO=new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductId(cart.getProductId());

                ServerResponse<Product> serverResponse=productService.findProductById(cart.getProductId());
                if(serverResponse.isSuccess()){
                    Product product=serverResponse.getData();
                    if(product.getStock()>=cart.getQuantity()){
                        limit_quantity=cart.getQuantity();
                        limitQuantity="LIMIT_NUM_SUCCESS";
                    }else {
                        limit_quantity=product.getStock();
                        limitQuantity="LIMIT_NUM_FAIL";
                    }
                    cartProductVO.setQuantity(limit_quantity);
                    cartProductVO.setLimitQuantity(limitQuantity);
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity()*1.0));
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductChecked(cart.getChecked());
                    cartProductVO.setProductImageHost(imageHost);

                    cartProductVOList.add(cartProductVO);
                    if(cart.getChecked()==CheckEnum.CART_PRODUCT_CHECK.getCheck()){
                        //商品已选中
                      cartTotalPrirce=BigDecimalUtils.add(cartTotalPrirce.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());
                    }
                }
            }
            cartVO.setCartProductVOList(cartProductVOList);
        //step3: 计算购物车总价格
            cartVO.setCarttotalprice(cartTotalPrirce);
        //step4: 判断是否全选
           Integer isAllChecked=cartMapper.isAllChecked(userId);
           if(isAllChecked==0){//全选
               cartVO.setIsallchecked(true);
           }else {
               cartVO.setIsallchecked(false);
           }
        //step5: 构建cartvo

        return cartVO;
    }

}
