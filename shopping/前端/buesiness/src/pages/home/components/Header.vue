<template>
    <div class="header">
      <div class="header-left"><span class="iconfont">&#xe624;</span></div>
        <!--<span class="iconfont">&#xe632;</span>-->
        <!--<span class="warn">输入要搜索的商品</span>-->
        <van-search
          class="header-content"
          v-model="value"
          placeholder="请输入搜索关键词"
          show-action
          shape="round"
        >
          <div slot="action" @click="onSearch(value)">搜索</div>
        </van-search>

    </div>
</template>

<script>
    export default {
        name: "homeHeader",
      data(){
          return{
            value:""
          }
      },
      methods:{
        onSearch(name){
          console.log("wwwwwww"+name);
          this.$router.push('/selectList/'+name);
         /* this.service.get("http://localhost:8888/user/product/search.do?productName="+name).then(this.getselectListInfo)*/
        },
      /*  getselectListInfo(res){
          if(res.data.status==0){//接口请求数据成功
            console.log(res);
          }
        }*/
      }
    }
</script>

<style lang="stylus" scoped>
  @import '~styles/varibles.styl'
        .header
          height:0.88rem
          background-color:$bgColor
          display:flex
          line-height:0.88rem
          .header-left
            width:0.72rem
            height:0.72rem
            color: #4c4abf
           .header-left >.iconfont
              font-size :0.5rem
              color #cacaca
          .header-content
            flex:1
            background-color:lightgrey
            height:0.5rem
            width 100%
            line-height:0.5rem
            margin-top:0.2rem
            margin-left 0.5rem
            margin-right:0.4rem
            border-radius :2rem
      /*      -webkit-border-radius :0.1rem
            -moz-border-radius :0.1rem*/
          .header-content > .iconfont
             padding-left:0.1rem
             color #cacaca
</style>
