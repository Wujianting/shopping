<template>
    <div>
     <detial-banner :detial="detial"></detial-banner>
      <detial-header></detial-header>
      <div class="content"></div>
    </div>
</template>

<script>
  import axios from 'axios';
  import  DetialBanner from './components/DetialBanner'
  import  DetialHeader from './components/DetialHeader'
export default {

        name: "Detial",
        components:{
          DetialHeader,
          DetialBanner
        },
        data(){
          return{
            detial:{}
          }
        },
        mounted(){
         this.getDetial()

        },
        methods:{
          getDetial(){
            const id=this.$route.params.id;
            this.service.get("http://localhost:8888/user/product/search.do?productId="+id).then(this.getDetialRes)
          },
          getDetialRes(res){
              console.log(res);
              if(res.data.status==0&&res.data.data.list){
                this.detial=res.data.data.list;
              }
          }

        }


    }
</script>

<style lang="stylus" scoped>
    .content
      height 50rem
</style>
