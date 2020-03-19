package com.neuedu.vo;


import java.math.BigDecimal;

public class ProductListVO  {

    private Integer id;
    private Integer categoryId;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    private String  name;//
    private String  subtitle;//oppo促销进行中",

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subimages) {
        this.subImages = subimages;
    }
    private Integer stock;// 1,
    private Integer status;// 1,
    private String  mainImage;//mainimage.jpg",
    private String  imageHost;
    private String  subImages;
    private BigDecimal price;// 2999.11,

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}