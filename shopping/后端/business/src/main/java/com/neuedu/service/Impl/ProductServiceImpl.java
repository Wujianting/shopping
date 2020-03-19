package com.neuedu.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtils;
import com.neuedu.vo.ProductDetailVO;
import com.neuedu.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ICategoryService categoryService;
    @Autowired
    ProductMapper productMapper;
    @Value("${business.imageHost}")
    private String imageHost;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public ServerResponse addOrUpdate(Product product) {
        if(product==null){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }
        //subimages 1.png,2.png,3.png
        //step2:设置商品的主图 sub_images-->1.jpg,2.jpg,3.png
        String subImages=product.getSubImages();
        if(subImages!=null&&subImages.equals("")){
            String[] subImageArr=subImages.split(",");
            if(subImageArr.length>0){
                //设置商品的主图
                product.setMainImage(subImageArr[0]);
            }
        }
       Integer productId=product.getId();
        if(productId==null){
            //添加
          int result=productMapper.insert(product);
          if(result<=0){
              return ServerResponse.serverResponseByError(ResponseCode.ERROR,"添加失败");
          }else {
              return ServerResponse.serverResponseBySuccess();
          }
        }else{
            //更新
            int result=productMapper.updateByPrimaryKey(product);
            if(result<=0){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"更新失败");
            }else {
                return ServerResponse.serverResponseBySuccess();
            }
        }

    }

    @Override
    public ServerResponse search(String productName,Integer productId, Integer pageNume, Integer pageSize) {
        if(productName!=null){
            productName="%"+productName+"%";
        }
           Page page=PageHelper.startPage(pageNume,pageSize);
            List<Product> productList=productMapper.findProductByNameAndId(productId, productName);
            List<ProductListVO> productListVOList= Lists.newArrayList();
            //List<Product>------->List<ProductListVO>
            if(productList!=null&&productList.size()>0){
                for(Product product1: productList){
                    ProductListVO productListVO=assembleProductListVO(product1);
                    productListVOList.add(productListVO);
                }
            }

        PageInfo pageInfo=new PageInfo(page);
            pageInfo.setList(productListVOList);
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse userfind( String productName,Integer productId, Integer pageNume, Integer pageSize) {
        if(productName!=null){
            productName="%"+productName+"%";
        }
        Page page=PageHelper.startPage(pageNume,pageSize);
        List<Product> productList=productMapper.userfind(productId, productName);
        System.out.println(productList.size());
        List<ProductDetailVO> ProductDetailVOList= Lists.newArrayList();
        //List<Product>------->List<ProductListVO>
        if(productList!=null&&productList.size()>0){
            for(Product product1: productList){
                ProductDetailVO productDetailVO=assembleProductDetailVO(product1);
                ProductDetailVOList.add(productDetailVO);
            }
        }

        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(ProductDetailVOList);
        return ServerResponse.serverResponseBySuccess(pageInfo);

    }


    @Override
    public ServerResponse<ProductDetailVO> detial(Integer productId) {
        if(productId==null){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }

        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.serverResponseBySuccess();
        }
        ProductDetailVO productDetailVO=assembleProductDetailVO(product);
        return ServerResponse.serverResponseBySuccess(productDetailVO);
    }

    @Override
    public ServerResponse<Product> findProduceByProductId(Integer productId) {
        if(productId==null){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }

        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.serverResponseBySuccess();
        }

        return ServerResponse.serverResponseBySuccess(product);
    }

    @Override
    public ServerResponse<Product> findProductById(Integer productId) {
        if(productId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品id必传");
        }
       Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){//商品不存在
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品不存在");
        }
        return ServerResponse.serverResponseBySuccess(product);
    }

    @Override
    public ServerResponse reduceStock(Integer produceId, Integer stock) {
        if(produceId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品id必传");
        }
        if(stock==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"库存参数必传");
        }
        int result=productMapper.reduceProductStock(produceId, stock);
        if(result<=0){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"扣库存失败");
        }
        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse<Product> findhot(Integer productId,Integer pageNume,Integer pageSize) {
        Page page=PageHelper.startPage(pageNume,pageSize);
        List<Product> product=productMapper.findhot(productId);
        List<ProductDetailVO>productListvo=new ArrayList<ProductDetailVO>();
        if(product==null){//商品不存在
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品不存在");
        }
        for(int i=0;i<product.size();i++) {

            Product p = product.get(i);

            ProductDetailVO pvo = assembleProductDetailVO(p);

            productListvo.add(pvo);
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(productListvo);
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }
    //删除产品
    @Override
    public ServerResponse dele(String productIds) {
        //step1:参数非空校验
        if (productIds == null || productIds.equals("")) {
            return ServerResponse.serverResponseByError("参数不能为空");
        }
        //step2:productIds-->List<Integer>
        List<Integer> productIdList = Lists.newArrayList();
        String[] productIdsArr = productIds.split(",");
        if (productIdsArr != null && productIdsArr.length > 0) {
            for (String productIdstr : productIdsArr) {
                Integer productId = Integer.parseInt(productIdstr);
                productIdList.add(productId);
            }
        }
        //step3:调用dao
        productMapper.dele(productIdList);
        //step4:返回结果
        return ServerResponse.serverResponseBySuccess();
    }
    //搜索所有商品--管理员
    @Override
    public ServerResponse<Product> allproduct(String productName,Integer pageNume,Integer pageSize) {
        if(productName!=null){
            productName="%"+productName+"%";
        }
        Page page=PageHelper.startPage(pageNume,pageSize);
        List<Product> product=productMapper.allproduct(productName);
        List<ProductDetailVO>productListvo=new ArrayList<ProductDetailVO>();
        if(product==null){//商品不存在
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品不存在");
        }
        for(int i=0;i<product.size();i++) {

            Product p = product.get(i);

            ProductDetailVO pvo = assembleProductDetailVO(p);

            productListvo.add(pvo);
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(productListvo);
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }


    private ProductDetailVO assembleProductDetailVO(Product product){


        ProductDetailVO productDetailVO=new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(imageHost);
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setIsnew(product.getIsNew());
        productDetailVO.setIshot(product.getIsHot());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
       ServerResponse<Category> serverResponse=categoryService.selectCategory(product.getCategoryId());
       Category category=serverResponse.getData();
        if(category!=null){
           productDetailVO.setParentCategoryId(category.getParentId());
        }
        return productDetailVO;
    }
    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO=new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setImageHost(product.getImageHost());
        productListVO.setSubImages(product.getSubImages());
        productListVO.setStock(product.getStock());
        return  productListVO;
    }
}
