<template>
    <div>
      <mt-header title="用户登录">
        <router-link to="/" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
        <!--<mt-button icon="more" slot="right"></mt-button>-->
      </mt-header>
      <mt-field label="用户名" placeholder="请输入用户名" v-model="username"  name="username"></mt-field>
      <mt-field label="密码" placeholder="请输入密码" type="password" v-model="password" name="password"></mt-field>
      <mt-button type="primary"  size="large" @click="login" >登录</mt-button>
        <!--<button @click="info">获取用户信息</button>-->
    </div>
</template>

<script>
  import axios from 'axios';
  import qs from 'qs';
  import  {mapActions} from"vuex"
    export default {
        name: "UserLogin",
      data: function () {
        return {
          username: "",
          password: "",
        }
      },
      methods: {
        ...mapActions(['setUserIn']),

        info: function () {

          this.service.get("userinfo/get_information.do"
          ).then(function (response) {
            console.log(response);
          }).catch(function (err) {
            console.log(err)
          })

        },

        login: function () {
          var  _vm=this;
          this.service.post("userinfo/login.do/"+this.username+"/"+this.password

          ).then(function (response) {
            console.log(response);
            //登录成功后，将用户信息保存到vuex中
            if(response.data.status==0){ //登录成功
                _vm.setUserIn(response.data.data)
                _vm.$router.go(-1)
            }
          }).catch(function (err) {
            console.log(err)
          })

        },

        back: function(){
          this.$router.go(-1)
        }
      }


    }
</script>

<style scoped>

</style>
