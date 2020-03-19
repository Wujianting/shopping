<template>
    <div>
      <mt-header title="个人中心">
        <router-link to="/" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
      </mt-header>
      <mt-field label="用户名" placeholder="" type="username" v-model="this.getUser.username" ></mt-field>
      <mt-field label="邮箱" placeholder="" type="email" v-model="this.getUser.email" ></mt-field>
      <mt-field label="手机号" placeholder="" type="tel" v-model="this.getUser.phone" ></mt-field>
      <mt-field label="密保问题" placeholder="" type="question" v-model="this.getUser.question" ></mt-field>
      <mt-field label="密保答案" placeholder="" type="answer" v-model="this.getUser.answer" ></mt-field>
      <!--<button style="color: purple ;width: 2rem;height: 1.2rem;background: aqua" @click="toupdate">修改后，点我即可修改成功哦！！</button>-->
    </div>
</template>

<script>
  import {mapGetters} from  'vuex'
    export default {
        name: "UserCenter",
        data(){
          return{
            readonly:true,
            name:this.getUser.name
          }
        },
        methods:{
          back:function () {
            this.$router.go(-1)
          },
          toupdate(){
            console.log(this.name);
          /*  this.service.get("http://localhost:8888/user/product/search.do?productId="+id).then(this.getDetialRes)*/
          },
        },
      computed:{
        ...mapGetters(['getUser'])

      }
    }
</script>

<style scoped>

</style>
