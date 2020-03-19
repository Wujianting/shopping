<template>
    <div class="icons">
      <swiper :options="swiperOption" ref="mySwiper" @someSwiperEvent="">
        <swiper-slide v-for="(pageArray,index) of pages" :key="index">
        <div class="icon" v-for="(item,index) of  pageArray" :key="index" @click="getselectlist(item.categoryName)">
             <div class="icon-img">
                <img class="icon-img-content" :src="item.categoryUrl"/>
             </div>
              <P class="icon-desc" :categoryName="item.categoryName">{{item.categoryName}}</P>
        </div>
          </swiper-slide>
       <!-- <swiper-slide>
        <div class="icon">
              <div class="icon-img">
                  <img class="icon-img-content" src="@/assets/img/22.jpg"/>
             </div>
              <P class="icon-desc">空调好物</P>
        </div>
          </swiper-slide>-->
   <!--     @someSwiperEvent="callback"-->
        </swiper>
    </div>
</template>

<script>
    export default {
        name: "HomeCategory",
      data(){
          return{
            swiperOption:{},
            categoryList: [
              {
                "id":1,
                "categoryUrl":"https://img30.360buyimg.com/babel/s200x200_jfs/t1/42403/23/6370/99357/5d0061e3Ec5d80f3a/bac5f5a37dccfa1e.png",
                "categoryName":"荣耀"
              },
              {
                "id":2,
                "categoryUrl":"https://m.360buyimg.com/babel/jfs/t1/25813/29/12657/304911/5c98c8e2E6bcf7d2d/25342f237b56fe97.jpg!q70.jpg",
                "categoryName":"华为"
              }, {
                "id":3,
                "categoryUrl":"https://img10.360buyimg.com/babel/jfs/t1/97048/27/2131/30045/5dcb7cbaE027ed5a2/86e181b7c36b5001.png",
                "categoryName":"平板"
              }, {
                "id":4,
                "categoryUrl":"https://m.360buyimg.com/babel/jfs/t1/88636/37/2294/198759/5dcd1ed0Ea2c619d9/5c58d1ee342e7bb4.jpg!q70.jpg",
                "categoryName":"三星"
              }, {
                "id":5,
                "categoryUrl":"https://m.360buyimg.com/babel/jfs/t1/107061/27/2641/123333/5dd4e3c0E0b49203f/210e07d8bbe4a596.jpg!q70.jpg",
                "categoryName":"OPPO"
              }, {
                "id":6,
                "categoryUrl":"https://m.360buyimg.com/babel/jfs/t1/81089/13/2913/163984/5d11ecb4Ef9cbf82a/53f7da59b6864d38.jpg!q70.jpg",
                "categoryName":"VIVO"
              }, {
                "id":7,
                "categoryUrl":"https://img11.360buyimg.com/cms/jfs/t1/62241/38/14673/223962/5dc11e79E56018892/961cda6ef81981e4.jpg",
                "categoryName":"小米"
              }, {
                "id":8,
                "categoryUrl":"https://img11.360buyimg.com/cms/jfs/t1/41566/13/14792/149059/5d7809a7E99b4ed1b/56ea66c5f8a6f724.jpg",
                "categoryName":"Apple"
              },{
                "id":9,
                "categoryUrl":"https://img11.360buyimg.com/cms/jfs/t26434/57/938304586/207033/15f91e57/5bbd5b44Nb726d818.jpg",
                "categoryName":"oppo"
              }
            ],
            hotList:[],
            categoryName:""
          }
      },
      computed:{
          pages(){
            const pages=[] //数组的长度代表页数
            this.categoryList.forEach((item,index) =>{
                //判断index下标的元素应该显示在第几页
               const page=Math.floor(index/8);
               if(!pages[page]){
                 pages[page]=[]
               }
               pages[page].push(item);

            })
            return pages;
          }
      },
      mounted(){

      },
      methods:{

        getselectlist(name){
          console.log("pppppppp"+name);
          this.$router.push('/selectList/'+name);
          /*this.service.get("http://localhost:8888/user/product/search.do?"+"productName="+name).then(this.getselectListInfo)*/
        },
      /*  getselectListInfo(res){
          console.log(res);
          if(res.data.status==0){//接口请求数据成功
            this.hotList= res.data.data.list;
          }
        }*/
      }
    }
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
      .icons >>> .swiper-container
        width :100%
        height:0
        padding-bottom :50%
        overflow :hidden
        .icon
         width :25%
         height :0
         padding-bottom :25%
         overflow :hidden
         float:left
         position :relative
         .icon-img
           position:absolute
           left:0
           right:0
           top:0
           bottom:0.44rem
           box-sizing :border-box
           padding:0.1rem
           .icon-img-content
             height :80%
             display:block
             margin :0 auto
          .icon-desc
             position :absolute
             left:0
             right:0
             bottom :0
             height :0.44rem
             color:salmon
             text-align :center
             overflow:hidden
             white-space :nowrap
             text-overflow :ellipsis
            ellipsis()
</style>
