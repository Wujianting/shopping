<template>
    <div>
      <div class="header-abs" v-show="showHeader">
        <div class="iconfont header-abs-back"  @click="back">&#xe624;</div>
      </div>
      <div class="header-fixed" :style="opacityStyle" v-show="!showHeader">
        <router-link tag="div" to="/" class="iconfont header-fixed-back"  >&#xe624;</router-link>
      </div>
    </div>
</template>

<script>
    export default {
        name: "DetialHeader",
        data(){
          return{
            showHeader:true,
            opacityStyle:{
              opacity:0
            }
          }
        },
       mounted(){
          window.addEventListener("scroll",this.handelScroll)
        },
       methods:{
         handelScroll(){
            const top=document.documentElement.scrollTop;
            console.log(top);
            if(top>60){
              //60--140之间
             let  opacity=top/140;
              opacity=(opacity>=1?1:opacity);
              this.opacityStyle={opacity};
              this.showHeader=false;
            }else{
              this.showHeader=true;
            }
         },
          back(){
           this.$router.go(-1);
          }
       }
    }
</script>

<style lang="stylus" scoped>
    .header-abs
      position absolute
      left 0.2rem
      top 0.2rem
      weight 0.8rem
      height 0.8rem
      line-height 0.8rem
      border-radius 0.4rem
      text-align center
      background rgba(0,0,0,0.8)
      .header-abs-back
        color white
        font-size 0.4rem
    .header-fixed
       position fixed
       left 0
       top 0
       right 0
       background #25a4bb
       height 0.8rem
       line-height 0.8rem
       text-align center
       .header-fixed-back
          color white
          width 0.64rem
          font-size 0.4rem
</style>
