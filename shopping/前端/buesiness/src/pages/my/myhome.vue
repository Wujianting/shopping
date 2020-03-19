<template>
  <div>
  <div >
    <img class="user-poster" src="http://img5.imgtn.bdimg.com/it/u=1440144058,3882593813&fm=26&gp=0.jpg">
    <van-row class="user-links">
        <van-cell  icon="records" title="我的订单" is-link  @click="clickmyorder"/>
    </van-row>
    <van-cell-group>
      <van-cell icon="points"  title="我的积分" is-link />
      <van-cell icon="gold-coin-o" title="我的优惠券" is-link />
      <van-cell icon="gift-o" title="我收到的礼物" is-link />
    </van-cell-group>

  </div>

  </div>
</template>

<script>
  import { Row, Col, Cell, CellGroup } from 'vant';
    export default {
        name: "myhome",
      components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup
      },
      methods:{
        clickmyorder:function() {
          this.$router.push("/myorder")
        }
   }

    }
</script>

<style lang="less">
  .user {
  &-poster {
     width: 100%;
     height: 53vw;
     display: block;
   }
  &-group {
     margin-bottom: 15px;
   }
  &-links {
     padding: 15px 0;
     font-size: 12px;
     text-align: center;
     background-color: #fff;
     .van-icon {
      display: block;
      font-size: 24px;
    }
  }
  }
</style>
