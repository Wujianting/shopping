<template>
    <div>
      <div class="title">热销产品</div>
      <ul>
        <router-link tag="li" :to="'/deti/'+item.id" class="item border-bottom" v-for="(item,index) of hotList" :key="index">
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
        name: "HomeHot",
        props:{
          hotList:Array
        },
        data(){
          return{

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


<!--

hotList:[
{
id:"1",
productName:"Apple 苹果X iPhoneX 全网通4G手机 深空灰色 256G标配",
productUrl:"https://img12.360buyimg.com/n7/jfs/t1/66627/25/9861/100326/5d78a45dEfc22003d/2cff36b09c78e103.png",
productPrice:"￥8468",
productDesc:"Apple 苹果X iPhoneX 全网通4G手机 深空灰色 256G标配"
}
]-->
