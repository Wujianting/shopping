// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'styles/reset.css'
import 'styles/border.css'
import './assets/styles/iconfont.css'
import fastClick from 'fastClick'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/dist/css/swiper.css'
import MintUI from 'mint-ui'
import 'mint-ui/lib/style.css'
import Vuex from 'vuex';
import navbar from '@/pages/navbar';
import Vant from 'vant';
import 'vant/lib/index.css';
import { PullRefresh } from 'vant';
import { Loading } from 'vant';
import { Image } from 'vant';
Vue.use(Image);



// require styles
import 'swiper/dist/css/swiper.css'
Vue.use(VueAwesomeSwiper)
Vue.config.productionTip = false
fastClick.attach(document.body)
/* eslint-disable no-new */

Vue.use(MintUI)
Vue.use(Vuex)
Vue.use(PullRefresh);
Vue.use(Loading);
Vue.use(Vant);

/* eslint-disable no-new */



//需要将axios注册成为全局变量
import  axios from 'axios'
var service=axios.create({
  baseURL:"http://localhost:8888",
  // 请求预处理函数 可以把你传的param进行处理
  withCredentials: true, // 允许携带cookie
  transformRequest: [
    data => {
      // data 就是你post请求传的值
      // 一下主要是吧数据拼接成 类似get格式
      let params = ''
      for (var index in data) {
        params += index + '=' + data[index] + '&'
      }
      return params
    }
  ]
})
Vue.prototype.service=service

/*var store1= new Vuex.Store({
  state:{
    count:0
  },
  mutations:{
    increment(state){
      state.count++;
    }
  }
})*/


import {store}  from  './store/index'
new Vue({
  el: '#app',
  router,
  store,
  components: { navbar },
  template: '<navbar/>'
})
