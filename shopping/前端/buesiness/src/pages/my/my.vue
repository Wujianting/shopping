<template>
  <div>
      <div class="icon" @click="clickLogin" >
        <img class="icon-img" slot="icon" :src="login_img" @click="clickLogin">
        <p class="icon-p" v-text="optxex" @click="clickLogin"></p>
      </div>

  </div>

</template>

<script>
  import  {mapGetters} from 'vuex'
    export default {
        name: "my",
      data: function () {
        return {
          optxex:"",
          isLogin:false,
          login_img:'static/images/login_n.png',
        }
      },computed:{
        ...mapGetters(['getUser'])

      },
     mounted(){
          if(JSON.stringify(this.getUser)=='{}'){
              this.optxex=""
              this.isLogin=false
          }else{
            this.optxex="个人信息"
            this.isLogin=true
          }
     },
      methods:{
        clickLogin: function(){
          if(this.isLogin){ //已登录
              this.$router.push("/usercenter");
              this.login_img="static/images/login_y.png"
          }else{  //未登录
              this.$router.push("/userlogin");
              this.login_img="static/images/login_n.png"
          }
        },
        clickmyorder:function () {
          this.$router.push("/myorder")
        }
      }

    }
</script>

<style lang="stylus" scoped>
  .icon
    height :1.5rem
    background :skyblue
    .icon-img
      weight:30%
      height :1.0rem
      margin-top :0.2rem
      float:left
    .icon-p
      flex :1
      font-size :20px
      align: center
      padding-top :0.8rem
      padding-left :1.5rem
      color :salmon
  .ico
    weight:3rem
</style>
