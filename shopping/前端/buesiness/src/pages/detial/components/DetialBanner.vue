<template>
  <div>
    <div class="banner" @click="see" >
     <img class="banner-img" :src="this.detial[0].imageHost+this.detial[0].mainImage" />
      <div class="banner-info">
        <div class="banner-number">
          <span class="iconfont banner-icon">&#xe694;</span>
          {{imgList.length}}
        </div>
      </div>

    </div>
    <gallery :imgList="imgList" v-show="showgall" @close="nosee"></gallery>
  </div>
</template>

<script>
  import Gallery from '@/common/gallery/Gallery'
    export default {
        name: "DetialBanner",
        props:{
          detial:Array
        },
        data(){
          return {
            showgall:false ,

          }
        },
        components:{
          Gallery
        },
      methods:{
        see(){
          this.showgall=true
        },
        nosee(){
          this.showgall=false
        }
      },
      computed:{
          imgList:function(){

           const imgList=(this.detial[0].subImages || "").split(",");
           for(var i=0;i<imgList.length;i++){
               imgList[i]=this.detial[0].imageHost+imgList[i];
           }
           return imgList;
          }
      }
    }
</script>

<style lang="stylus" scoped>
    .banner
      overflow hidden
      height 0
      padding-bottom 70%
      position relative
      .banner-img
        weight 100%
      .banner-info
        position absolute
        left 0
        right 0
        bottom  0
        background rgba(255,255,255,0.6)
        .banner-number
          float right
          margin-right  0.1rem
          background  rgba(0,0,0,.8)
          color  white
          border-radius  0.1rem
          padding  0.05rem
          font-size 0.24rem
          .banner-icon
            font-size  0.24rem

</style>
