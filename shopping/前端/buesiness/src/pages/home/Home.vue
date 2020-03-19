<template>
  <div>
    <!--<h1>home首页</h1>-->
  <!--  <router-link to="/detial">查看商品详情</router-link>-->
    <home-header></home-header>
    <home-carousel></home-carousel>
    <home-category></home-category>
    <home-hot :hotList="hotList"></home-hot>
    <van-loading type="spinner" color="#1989fa" />
  </div>


</template>

<script>
  import homeHeader from './components/header'
  import HomeCarousel from './components/HomeCarousel'
  import HomeCategory from './components/HomeCategory'
  import HomeHot from './components/HomeHot'
  import axios from 'axios'
    export default {
        name: "Home",
        components:{
          homeHeader,
          HomeCarousel,
          HomeCategory,
          HomeHot
        },
      data(){
          return {
            hotList:[]

          }
      },
      //onload
      mounted(){
          this.gethostlist();
      },
      methods:{

          gethostlist(){
            this.service.post("http://localhost:8888/user/product/search").then(this.getHotListInfo)
          },
          getHotListInfo(res){
            console.log(res);
            if(res.data.status==0){//接口请求数据成功
             this.hotList= res.data.data.list;
            }
          }


      }
    }
</script>

<style scoped>

</style>
