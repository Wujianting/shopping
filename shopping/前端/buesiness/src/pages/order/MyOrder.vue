<template>
    <div>
      <mt-header title="我的订单">
        <router-link to="/myh" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
        <!--<mt-button icon="more" slot="right"></mt-button>-->
      </mt-header>

      <van-tabs v-model="selected" >
        <van-tab title="已发货" name=" 1" @click="send">
          <div >
            <ul>
              <router-link tag="li" :to="'/orderdeti/'+item.orderNo" class="item border-bottom" v-for="(item,index) of sendList" :key="index">
                <img class="item-img" src="static/images/nologin.jpg"/>
                <div class="item-info">
                  <p class="item-price">订单号:{{item.orderNo}}</p>
                  <p class="item-desc">价格:{{item.payment}}</p>
                  <p class="item-price">支付方式:{{item.paymentTypeDesc}}</p>
                </div>
              </router-link>
            </ul>
          </div>
        </van-tab>
        <van-tab title="已付款" name=" 2" @click="pay">
          <div >
            <ul>
              <router-link tag="li" :to="'/orderdeti/'+item.orderNo" class="item border-bottom" v-for="(item,index) of payList" :key="index">
                <img class="item-img" src="static/images/nologin.jpg"/>

                <div class="item-info">
                  <p class="item-price">订单号:{{item.orderNo}}</p>
                  <p class="item-desc">价格:{{item.payment}}</p>
                  <p class="item-price">支付方式:{{item.paymentTypeDesc}}</p>
                </div>
              </router-link>
            </ul>
          </div>
        </van-tab>

        <van-tab title="未付款" name=" 3" @click="nopay" >
          <div>
          <ul>
            <router-link tag="li" :to="'/orderdeti/'+item.orderNo" class="item border-bottom" v-for="(item,index) of orderList" :key="index">
              <img class="item-img" src="static/images/nologin.jpg"/>
              <div class="item-info">
                <p class="item-price">订单号:{{item.orderNo}}</p>
                <p class="item-desc">价格:{{item.payment}}</p>
                <p class="item-price">支付方式:{{item.paymentTypeDesc}}</p>
              </div>
            </router-link>
          </ul>
          </div>
        </van-tab>

        <van-tab title="全部订单" name=" 4" @click="allorder">
          <div>
            <ul>
              <router-link  :to="'/orderdeti/'+item.orderNo"  class="item border-bottom" v-for="(item,index) of allList" :key="index">
                <img class="item-img" src="static/images/nologin.jpg"/>
                <div class="item-info">
                  <p class="item-price">订单号:{{item.orderNo}}</p>
                  <p class="item-desc">价格:{{item.payment}}</p>
                  <p class="item-price">支付方式:{{item.paymentTypeDesc}}</p>
                </div>
              </router-link>
            </ul>
          </div>
        </van-tab>
      </van-tabs>


    </div>
</template>

<script>
  import  {mapActions} from"vuex"
    export default {
        name: "MyOrder",
        data(){
          return{
            selected:"1",
            orderList:[],
            payList:[],
            allList:[],
            sendList:[],
            product:[],
          }
       },
      mounted(){
        this.nopay();
        this.pay();
        this.allorder();
        this.send();
      },
        methods: {
          back: function () {
            this.$router.go(-1)
          },
          ...mapActions(['isShow']),
          nopay(){
            this.service.post("http://localhost:8888/order/select").then(this.getHotListInfo)
          },
          pay(){
            this.service.post("http://localhost:8888/order/find").then(this.getpayList)
          },
          allorder(){
            this.service.post("http://localhost:8888/order/all").then(this.getallorder)
          },
          send(){
            this.service.post("http://localhost:8888/order/send").then(this.getsendorder)
          },
          getHotListInfo(res){
            console.log(res);
            if(res.data.status==0){//接口请求数据成功
              for(var i=0;i<res.data.data.length;i++){
                this.orderList.length=res.data.data.length;
                this.orderList[i]= res.data.data[i];
              }
            }
          },
          getpayList(res){
            console.log(res);
            if(res.data.status==0){//接口请求数据成功
              for(var i=0;i<res.data.data.length;i++){
                this.payList.length=res.data.data.length;
                this.payList[i]= res.data.data[i];
              };

            };

          },
          getallorder(res){
            console.log(res);
            if(res.data.status==0){//接口请求数据成功
              for(var i=0;i<res.data.data.length;i++){
                this.allList.length=res.data.data.length;
                this.allList[i]= res.data.data[i];
              }
            }
          },
          getsendorder(res){
            console.log(res);
            if(res.data.status==0){//接口请求数据成功
              for(var i=0;i<res.data.data.length;i++){
                this.sendList.length=res.data.data.length;
                this.sendList[i]= res.data.data[i];
              }
            }
          }
        },
          created(){
          var _vm=this;
            window.onscroll=function () {
              //距离顶部的距离
              var scrollTop=document.documentElement.scrollTop||document.body.scrollTop;
              //可视区的高度
              var windowHeight=document.documentElement.clientHeight || document.body.clientHeight;
              //滚动条的总高度
              var scrollHeight=document.documentElement.scrollHeight||document.body.scrollHeight;
              //滚动条到底部的条件
              if((scrollTop+windowHeight+60)>=scrollHeight){
                  //写后台加载数据的函数
                  console.log("距顶部"+scrollTop+"可视区高度"+windowHeight+"滚动条总高度"+scrollHeight);
                  _vm.isShow(false)
              }else{
                _vm.isShow(true)
              }
            }
          }


    }
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  .item
    display:flex
    overflow :hidden
    height :1.8rem
    .item-img
     weight: 1.8rem
     height: 1.8rem
    .item-info
      flex:1
      padding:0.1rem
      min-width :0
      .item-title
        line-height :0.54rem
        font-size:0.25rem
        color:deeppink
        ellipsis()
      .item-desc
        line-height:0.54rem
        font-size:0.36rem
        color darkorchid
        ellipsis()
      .item-price
        font-size  0.36rem
        color:plum
</style>
