<template>
    <div >
      <router-view/>
      <div v-show="this.isShowFooterBar">
      <mt-tabbar v-model="selected"  :fixed="fixed" v-show="$route.meta.navShow==undefined||$route.meta.navShow">

        <mt-tab-item id="0">
          <img slot="icon" :src="home_img">
          首页
        </mt-tab-item>

        <mt-tab-item id="1">
          <img slot="icon" :src="cart_img">
          购物车
        </mt-tab-item>

        <mt-tab-item id="2">
          <img slot="icon" :src="my_img">
          我的
        </mt-tab-item>
      </mt-tabbar>
      </div>
    </div>
</template>

<script>
  import  {mapGetters} from 'vuex'
    export default {
        name: "navbar",
        data:function () {
          return {
            fixed:true,
            selected:0,
            home_img:'static/images/home_n.png',
            cart_img:'static/images/cart_n.png',
            my_img:'static/images/my_n.png'
          }
        },
        computed:{
          ...mapGetters(["isShowFooterBar"]),
          ...mapGetters(["getUser"]),
        },
        watch:{
          selected:function (value) {
            console.log("===value:"+value)
            if(value==0){
               //显示 首页组件
               this.$router.push("home");
                this.home_img="static/images/home_y.png"
            }else {
              this.home_img="static/images/home_n.png"
            }

            if(value==1){
              //显示购物车组件
              this.$router.push("go");
              this.cart_img="static/images/cart_y.png"
            }else {
              this.cart_img="static/images/cart_n.png"
            }

            if(value==2){
              //显示个人中心组件
                this.$router.push("myh");
                this.my_img = "static/images/my_y.png"

            }else{
              this.my_img="static/images/my_n.png"
            }
          }
        }
    }
</script>

<style scoped>

</style>
