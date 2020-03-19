<template>
  <div>
    <mt-header title="购物车">
      <router-link to="/" slot="left">
        <mt-button icon="back" @click="back">返回</mt-button>
      </router-link>
    </mt-header>
<!--    <van-checkbox-group class="card-goods" v-model="cartIdist" ref="checkboxGroup">
      <van-checkbox
        class="card-goods__item"
        v-for="item in cartList"
        :key="item.productId"
        :name="item.productId"
      >
        <van-card
          :title="item.productSubtitle"
          :desc="item.productName"
          :num="item.quantity"
          :price="formatPrice(item.productPrice*100)"
          :thumb="item.productImageHost+item.productMainImage"
        >

        </van-card>


      </van-checkbox>

    </van-checkbox-group>-->

    <van-checkbox-group class="card-goods" v-model="cartIdist" ref="checkboxGroup" >
      <van-cell-group>
        <van-cell
          class="card-goods__item"
          v-for="(item, index) in cartList"
          :key="index"
        >
          <van-checkbox
            :name="item.productId"
            ref="checkboxes"
            slot="right-icon"
          />

          <div style="margin-left: 6.2rem">
            <van-icon name="clear" @click="dele(item.productId)"/>
          </div>

          <van-card
            :title="item.productSubtitle"
            :desc="item.productName"
            :num="item.quantity"
            :price="formatPrice(item.productPrice*100)"
            :thumb="item.productImageHost+item.productMainImage"
            :thumb-link=" '#/deti/'+item.productId"
          >



          </van-card>

        </van-cell>

      </van-cell-group>
    </van-checkbox-group>

    <van-submit-bar
      :price="totalPrice"
      :disabled="!cartList.length"
      :button-text="submitBarText"
      @submit="toorder"
    >
      <van-button type="primary" @click="checkAll"  >全选</van-button>
    <span slot="tip">
  </span>
    </van-submit-bar>
  </div>
</template>

<script>
  import { Checkbox, CheckboxGroup, Card, SubmitBar, Toast } from 'vant';
  export default {
    name:"go",
    components: {
      [Card.name]: Card,
      [Checkbox.name]: Checkbox,
      [SubmitBar.name]: SubmitBar,
      [CheckboxGroup.name]: CheckboxGroup
    },
    data() {
      return {
        cartList: [],
        cartIdist:[],
        total:[],
        productIds:[]
      }
    },
    computed: {
      submitBarText() {
        var c = this.cartIdist.length;
        const count=0;
        return '结算' + (count ? `(${count})` : '');
      },
      totalPrice() {
        return this.cartList.reduce((total, item) => total + (this.cartIdist.indexOf(item.productId) !== -1 ? item.productPrice*item.quantity*100 : 0), 0);
      }
    },
    mounted() {
      this.getcartlist();
    },
    methods: {
      formatPrice(price) {
        return (price / 100).toFixed(2);
      },
      onSubmit() {
        Toast('点击结算');
      },
      toorder(){
        this.$router.push({
          // 你要跳转的地址
          path: '/shippid/'+this.productIds,
        })
      },
      checkAll() {
        this.$refs.checkboxGroup.toggleAll(true);
      },
     getcartlist() {
        this.service.get("http://localhost:8888/cart/select").then(this.getCarListInfo)
      },
      getCarListInfo(res) {
        console.log(res);
        if (res.data.status == 0) {//接口请求数据成功
          this.cartList= res.data.data.cartProductVOList;
          if( res.data.data.cartProductVOList!=null){
            for(var i=0;i< res.data.data.cartProductVOList.length;i++){
              this.cartIdist[i]=res.data.data.cartProductVOList.productId;
              this.total[i]=res.data.data.cartProductVOList.productTotalPrice;
              this.productIds.length=res.data.data.cartProductVOList.length;

            }
            for(var i=0;i< res.data.data.cartProductVOList.length;i++){
              if(i<res.data.data.cartProductVOList.length-1){
                this.productIds[i]=res.data.data.cartProductVOList[i].productId+",";
                continue;
              }else {
                this.productIds[i]=res.data.data.cartProductVOList[i].productId;

              }
            }
            console.log("wwwwwww"+this.productIds);
          }
        }
      },
      back: function(){
        this.$router.go(-1)
      },
      add:function () {
        this.number++
      },
      down:function () {
        if(this.number==0){

        }else{
          this.number--
        }
      },
      dele(id) {
        console.log(id);
        this.service.get("http://localhost:8888/cart/delete/"+id).then(this.getdele)
      },
      getdele(res){
        console.log(res);
        this.$router.go(0);
      },




    }
  };
</script>

<style lang="less">
  .card-goods {
    padding: 10px 0;
    background-color: #fff;
    &__item {
      position: relative;
      background-color: #fafafa;
      .van-checkbox__label {
        width: 100%;
        height: auto; // temp
        padding: 0 10px 0 15px;
        box-sizing: border-box;
      }
      .van-checkbox__icon {
        top: 50%;
        left: 10px;
        z-index: 1;
        position: absolute;
        margin-top: -10px;
      }
      .van-card__price {
        color: #f44;
      }
    }
  }
</style>
