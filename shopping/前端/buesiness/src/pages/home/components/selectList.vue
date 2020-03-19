<template>
    <div>
      <div class="title">
        <mt-button icon="back" @click="back"></mt-button>
        {{this.$route.params.name}}
      </div>
      <!--{{selectList}}-->
      <ul>
        <router-link tag="li" :to="'/deti/'+item.id" class="item border-bottom" v-for="(item,index) of selectList" :key="index">
          <img class="item-img" :src="item.imageHost+item.mainImage"/>
          <div class="item-info">
            <p class="item-title">{{item.name}}</p>
            <p class="item-desc">{{item.name}}</p>
            <p class="item-price">￥{{item.price}}</p>
          </div>
        </router-link>
      </ul>
    </div>
</template>

<script>
    export default {
        name: "selectList",
        data(){
          return{
            selectList:[],
          }
        },
      mounted() {
        this.getselectlist();
      },
        methods:{
          getselectlist(){;
            const name=this.$route.params.name;
            console.log(name);
            this.service.get("http://localhost:8888/user/product/search.do?productName="+name).then(this.getselectListInfo)
          },
          getselectListInfo(res){
            if(res.data.status==0){//接口请求数据成功
              console.log(res);
              this.selectList=res.data.data.list;
             /* console.log(JSON.stringify(this.selectList));*/
            }
          },
          back:function () {
            this.$router.go(-1);
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
    color pink
    font-size x-large
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

</style>
