<template>
    <div>
      <mt-header title="订单确认">
        <router-link to="/go" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
      </mt-header>
      <van-address-list
        v-model="chosenAddressId"
        :list="list"
        @add="onAdd"
        add-button-text="提交订单"
        @click="getidlist"
      />
    </div>
</template>

<script>
    export default {
        name: "shippid",
      data() {
        return {
          chosenAddressId: '',
          list: [],
          productIds:"",
          Lists:[]

        }
      },
      mounted() {
        this.productIds=this.$route.params.productIds;
        this.getidlist();
      },
      methods: {
        onAdd() {
          var _vm=this;
          if(this.chosenAddressId==""){
            this.$toast("请选择一个地址")
            //确认单需要一个一个的去选择
          }
          else {

            console.log(this.chosenAddressId);
            var str = this.productIds.split(',');
            for(var i=0;i<str.length;i++){
              //去一个一个的选中它
              this.service.get("http://localhost:8888/cart/select/"+str[i]+"")
            }
            //选中完之后去创建订单
            this.service.get("http://localhost:8888/order/"+this.chosenAddressId+"").then(function (res) {
              console.log(res)
              if( res.data.status==0){
                //跳转到付款页面
                _vm.$toast("创建订单成功");
                _vm.$router.push("/pay/"+res.data.data.orderNo);
              }else {
                _vm.$toast(res.data.msg)
              }
            })
          }


        },

        getidlist() {
          this.service.get("http://localhost:8888/shipping/select").then(this.getCarListInfo)
        },
        getCarListInfo(res) {
          console.log(res);
          if (res.data.status == 0) {//接口请求数据成功
            this.list= res.data.data.list;
          }
        },
        back: function(){
          this.$router.go(-1)
        },
      }


    }
</script>

<style scoped>

</style>
