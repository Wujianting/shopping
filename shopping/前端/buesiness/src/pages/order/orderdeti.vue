<template>
    <div>
      <mt-header title="订单详情">
        <router-link  to="/myorder" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
        <!--<mt-button icon="more" slot="right"></mt-button>-->
      </mt-header>


      <div @click="pay">
        <ul>
          <li class="item border-bottom" v-for="(item,index) of payList" :key="index">
            <img class="item-img" :src="'http://img.cdn.imbession.top/'+item.productImage"/>
            <div class="item-info">
              <p class="item-price">订单号:{{item.orderNo}}</p>
              <p class="item-title">{{item.productName}}</p>
              <p class="item-desc">价格:{{item.currentUnitPrice}}</p>
              <p class="item-price">数量:{{item.quantity}}</p>
              <p class="item-price">总价格:￥{{item.totalPrice}}</p>
            </div>
          </li>
        </ul>
      </div>
      <p class="price">订单总价格:{{this.allprice}}</p>
      <button class="price" @click="topay"  v-show="show" style="color: red">卿，请尽快支付哦！！！点我即可支付哦！！</button>

      <button class="price" @click="nobuy"  v-show="show" style="color: aqua">取消订单</button>
    </div>
</template>

<script>
  import { Toast } from 'vant';
    export default {
        name: "orderdeti",
      data(){
        return{
          payList:[],
          allprice:0,
          orderNo:0,
          show:false,
          status:0
        }
      },
      mounted(){
        this.pay();
        this.nobuy();
      },
      methods:{
        back: function () {
          this.$router.go(-1)
        },
        pay(){
          const orderNo=this.$route.params.orderNo;
          this.service.post("http://localhost:8888/orderitem/select/"+orderNo).then(this.getpayList)
        },
        getpayList(res){
          console.log(res);
          if(res.data.status==0){//接口请求数据成功
            for(var i=0;i<res.data.data.length;i++){
              this.payList= res.data.data[i].orderItemVoList;
              console.log(res.data.data[i].payment);
              this.allprice=res.data.data[i].payment;
              this.orderNo=res.data.data[i].orderNo;
              this.status=res.data.data[i].status;
              if(this.status==10){
                this.show=true;
              }
            }
          }
        },
        topay(){
          this.$router.push("/pay/"+this.orderNo);
        },
        nobuy(){
          this.service.get("http://localhost:8888/order/cancel/"+this.orderNo).then(this.getnobuy)
        },
        getnobuy(res){
          console.log(res);
          if(res.data.status=0){
            this.$toast('取消成功');
            this.$router.push("/myorder");
          }
        }
      }

    }
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'

  .title
    background :#cacaca
    line-height: 0.8rem
    text-indent :0.2rem
  .item
    display:flex
    overflow :hidden
    height :2rem
    .item-img
      weight: 1.5rem
      height: 1.5rem
      padding:0.1rem
    .item-info
      flex:1
      padding:0.1rem
      min-width :0
      .item-title
        line-height :0.54rem
        font-size:0.30rem
        ellipsis()
      .item-desc
        line-height:0.54rem
        font-size:0.22rem
        ellipsis()
      .item-price
        color:fuchsia
.price
  color deeppink
  font-size 0.36rem
</style>
