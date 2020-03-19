<template>
    <div class="container" @click="wh">
      <div class="wrapper">
        <swiper :options="swiperOption" ref="mySwiper" @someSwiperEvent="callback">
          <!-- slides -->
          <swiper-slide v-for="(item,index) of imgList" :key="index" >
            <img class="swiper"  :src="item"/>
          </swiper-slide>
          <!-- Optional controls -->
          <div class="swiper-pagination"  slot="pagination"></div>

        </swiper>
      </div>

    </div>
</template>

<script>
    export default {
        name: "Gallery",
        props:{
          imgList:Array
        },
        data(){
          return{
            swiperOption:{
              pagination:'.swiper-pagination',
              paginationType: 'fraction',
              loop:true,
              observer:true,
              observeParents:true
            }
          }
        },
      methods:{
          wh(){
          this.$emit("close");
        }
      }
    }
</script>
<style lang="stylus" scoped>
    .container >>> .swiper-container
      overflow inherit
    .container
      position fixed
      left 0
      right 0
      bottom 0
      top 0
      background plum
      z-index 99
      display flex
      flex-direction column
      justify-content center
      position fixed
      .wrapper
        height 0
        weight 100%
        padding-bottom 100%
        background white
        .swiper
          width 100%
        .swiper-pagination
          bottom  -1rem
          color sienna
</style>

