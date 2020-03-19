<template>
  <div class="goods">
      <detial-banner :detial="detial"></detial-banner>
      <detial-header></detial-header>
      <div class="content"></div>

    <van-cell-group v-for="(item,index) of detial" :key="index">
      <van-cell>
        <div class="goods-title">{{item.name}}</div>
        <div class="goods-price" :price="item.price">{{ formatPrice(item.price) }}&#12288;&#12288;&#12288;&#12288;&#12288; {{num}}</div>
      </van-cell>
      <van-cell class="goods-express">
       <van-button size="mini" class="mini" @click="down"><img  class="mini-s" src="static/images/up_n.png" height="20" v-model="price"/></van-button>
       <van-button size="mini" class="mini" @click="add"><img  class="mini-s" src="static/images/up_y.png" height="20"/></van-button><br/>
        <van-col span="10">运费：{{ goods.express }}</van-col>
        <van-col span="14" >库存：{{item.stock}} </van-col>
      </van-cell>
    </van-cell-group>

    <van-cell-group class="goods-cell-group">
      <van-cell value="进入店铺" icon="shop-o" is-link @click="sorry">
        <template slot="title">
          <span class="van-cell-text">有赞的店</span>
          <van-tag class="goods-tag" type="danger">官方</van-tag>
        </template>
      </van-cell>
      <van-cell title="线下门店" icon="location-o" is-link @click="sorry" />
    </van-cell-group>

    <van-cell-group class="goods-cell-group">
      <van-cell title="查看商品详情" is-link @click="sorry" />
    </van-cell-group>

    <van-goods-action>
      <van-goods-action-icon icon="chat-o" @click="sorry">
        客服
      </van-goods-action-icon>
      <van-goods-action-icon icon="cart-o" @click="onClickCart">
        购物车
      </van-goods-action-icon>
      <van-goods-action-button type="warning" @click="addgo">
        加入购物车
      </van-goods-action-button>
      <van-goods-action-button type="danger" @click="sorry">
        立即购买
      </van-goods-action-button>
    </van-goods-action>
  </div>
</template>

<script>
  import axios from 'axios';
  import  DetialBanner from './components/DetialBanner'
  import  DetialHeader from './components/DetialHeader'
  import {
    Tag,
    Col,
    Icon,
    Cell,
    CellGroup,
    Swipe,
    Toast,
    SwipeItem,
    GoodsAction,
    GoodsActionIcon,
    GoodsActionButton
  } from 'vant';
  export default {
    name:"deti",
    components: {
      [Tag.name]: Tag,
      [Col.name]: Col,
      [Icon.name]: Icon,
      [Cell.name]: Cell,
      [CellGroup.name]: CellGroup,
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
      [GoodsAction.name]: GoodsAction,
      [GoodsActionIcon.name]: GoodsActionIcon,
      [GoodsActionButton.name]: GoodsActionButton,
      DetialHeader,
      DetialBanner
    },
    data() {
      return {
        goods: {
          express: '免运费',
        },
          detial:{},
          num:0,
          stock:0

      };
    },
    mounted(){
      this.getDetial();
      this.addgo();
    },
    methods: {
      formatPrice(price) {
        return '¥' + (price).toFixed(2);
      },
      onClickCart() {
        this.$router.push({
          // 你要跳转的地址
          path: '/go',
        })
      },
      sorry() {
        Toast('暂无后续逻辑~');
      },
      getDetial(){
        const id=this.$route.params.id;
        this.service.get("http://localhost:8888/user/product/search.do?productId="+id).then(this.getDetialRes)
      },
      getDetialRes(res){
        console.log(res);
        if(res.data.status==0&&res.data.data.list){
          this.detial=res.data.data.list;
          this.stock=res.data.data.list[0].stock;
        }
      },
      add:function () {
        if(this.num>this.stock){

        }else{
          this.num++
        }
      },
      down:function () {
        if(this.num==0){

        }else{
          this.num--
        }
      },
      addgo:function () {

        const id=this.$route.params.id;
        this.service.get("http://localhost:8888/cart/add/"+id+"/"+this.num).then(function (response) {
          if(response.data.status==0){//接口请求数据成功
            this.$toast("添加购物车成功");
            console.log(res);
          }
        })
      },

    },

  };
</script>

<style lang="less">

  .goods {
    padding-bottom: 50px;
    &-swipe {
      img {
        width: 100%;
        display: block;
      }
    }
    &-title {
      font-size: 16px;
    }
    &-price {
      color: #f44;
    }
    &-express {
      color: #999;
      font-size: 12px;
      padding: 5px 15px;
      .mini{
        float: right;
      }
    }
    &-cell-group {
      margin: 15px 0;
      .van-cell__value {
        color: #999;
      }
    }
    &-tag {
      margin-left: 5px;
    }

  }
</style>
