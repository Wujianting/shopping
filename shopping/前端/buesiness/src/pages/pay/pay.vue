<template>
    <div>
      <mt-header title="订单支付">
        <router-link to="/myorder" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
      </mt-header>
      <img src="static/images/nologin.jpg" height="100%" width="100%"/>
      <img src="static/images/nologin.jpg" height="100%" width="100%"/>
      <van-popup v-model="show" round  :style="{ height: '50%' , width: '80%'}">

        <img :src="this.img" width="100%" height="100%">

      </van-popup>
    </div>
</template>

<script>
    export default {
        name: "pay",
      data(){
        return{
          show: true,
          img:"",
        }
      },
      mounted(){
        this.see();
      },
      methods:{
            see:function (){
              const orderNo=this.$route.params.orderNo;
              var _vm=this;
              this.service.get("http://localhost:8888/order/pay/"+orderNo).then(function (res) {
                if(res.data.status==0){
                  console.log(res);
                  console.log(res.data.data);
                  _vm.img=res.data.data.qrcode;
                  console.log(_vm.img)


                }

              })
            },
      }
    }
</script>

<style scoped>

</style>
