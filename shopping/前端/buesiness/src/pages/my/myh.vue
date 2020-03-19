<template>
  <div>

    <div v-if="isLogin">
      <img class="user-poster" src="http://img5.imgtn.bdimg.com/it/u=1440144058,3882593813&fm=26&gp=0.jpg">

    </div>


    <div  @click="clickLogin" v-else >
      <img class="icon-img" slot="icon" src="static/images/nologin.jpg" @click="clickLogin">
      <p class="icon-p" v-text="optxex" @click="clickLogin"></p>
      <img class="icon-img" slot="icon" src="static/images/nologin.jpg" @click="clickLogin">
    </div>
    <!--class="icon-img"-->

   <div  v-if="isLogin">

    <van-cell-group>
      <van-cell icon="records" title="我的订单" is-link  @click="clickmyorder" />
      <van-cell icon="manager"  title="个人中心" is-link  @click="me"/>
      <van-cell icon="location-o" title="我的收货地址" is-link  @click="myshippid"/>
      <van-cell icon="closed-eye" title="退出登录" is-link  @click="downlogin"/>
      <!--<van-cell icon="points"  title="回到首页" is-link  @click="tohome"/>-->
    </van-cell-group>

  </div>

  </div>

</template>

<script>
  import  {mapGetters} from 'vuex';
  import { Row, Col, Cell, CellGroup } from 'vant';
  export default {
    name: "myh",
    data: function () {
      return {
        optxex:"欢迎登录哦！！！",
        isLogin:false,
        login_img:'static/images/login_n.png',
      }
    },computed:{
      ...mapGetters(['getUser'])

    },
    components: {
      [Row.name]: Row,
      [Col.name]: Col,
      [Cell.name]: Cell,
      [CellGroup.name]: CellGroup
    },
    mounted(){
      if(JSON.stringify(this.getUser)=='{}'){
        this.optxex="欢迎登录哦！！！"
        this.isLogin=false
      }else{
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
      },
      me:function () {
        this.$router.push("/usercenter")
      },
      myshippid:function () {
        this.$router.push("/allshippid")
      },
      tohome:function(){
        this.$router.push("/")
      },
      downlogin:function () {
        this.isLogin=false;
        this.service.get("http://localhost:8888/userinfo/logout.do").then(function (res) {
           console.log(res);
           if(res.data.status==0){

           }
        }).catch(function (err) {
          console.log(err)
        })
      },
      nopay() {
        this.$router.push({
          // 你要跳转的地址
          path: '/nopay',
        })
      },
    }

  }
</script>

<style lang="less" >

    .icon-img {
      width: 100%;
      height: 100%;
    }
    .ico-p{
      color: plum;
      align-content: center;
    }
    .icon-p {
      flex: 1;
      font-size: 20px;
      align: center;
      padding-left: 2.3rem;
      color: darkorchid;
      background: plum;
      font-family: " FZCaiYun-M09S";
    }

  .ico {
    weight: 3rem;
  }
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
