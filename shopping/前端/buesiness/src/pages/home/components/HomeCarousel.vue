<template>
  <div class="h">
  <swiper :options="swiperOption" ref="mySwiper" @someSwiperEvent="">
    <!-- slides -->
    <swiper-slide>
      <img class="swiper"  src="@/assets/img/1.jpg"/>
    </swiper-slide>
    <swiper-slide>
      <img class="swiper" src="@/assets/img/2.jpg"/>
    </swiper-slide>
    <swiper-slide>
      <img class="swiper" src="@/assets/img/1.jpg"/>
    </swiper-slide>
    <!-- Optional controls -->
    <div class="swiper-pagination"  slot="pagination"></div>

  </swiper>
  </div>
</template>

<script>
    export default {
        name: "HomeCarousel",
        data (){
          return {
            swiperOption:{
              pagination:'.swiper-pagination',
              loop:true,
              autoplay:true,
              //speed是一张图划过的时间长度
              speed:3600
            },
          }
        },
    }
</script>

<style lang="stylus"scoped>
      .swiper
        width :100%
      .swiper-pagination>>>.swiper-pagination-bullet-active
        background :white
</style>
