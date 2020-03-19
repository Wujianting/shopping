import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/pages/home/Home'
import selectList from '@/pages/home/components/selectList'
import Product from '@/pages/product/Product'
import Detial from '@/pages/detial/Detial'
import navbar from '@/pages/navbar'
import my from "@/pages/my/my"
import UserLogin from "@/pages/my/UserLogin"
import UserCenter from "@/pages/my/UserCenter"
import MyOrder from "@/pages/order/MyOrder"
import go from"@/pages/cart/go"
import myhome from "@/pages/my/myhome"
import myh from "@/pages/my/myh"
import deti from  '@/pages/detial/deti'
import orderdeti from "@/pages/order/orderdeti"
import shippid from "@/pages/shipping/shippid"
import allshippid from "@/pages/shipping/allshippid"
import pay from "@/pages/pay/pay"
import addshippid from "@/pages/shipping/addshippid"
import updateshippid from "@/pages/shipping/updateshippid"
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    }, {
      path: '/list',
      name: 'Product',
      component: Product
    },{
      path: '/detial/:id',
      name: 'Detial',
      component: Detial
    },
    {
      path: '/nav',
      name: 'navbar',
      component: navbar
    },
    {
      path: '/my',
      name: 'my',
      component: my
    }, {
      path: '/home',
      name: 'Home',
      component: Home
    }, {
      path: '/userlogin',
      name: 'UserLogin',
      component: UserLogin
    }, {
      path: '/usercenter',
      name: 'UserCenter',
      component: UserCenter
    },{
      path: '/myorder',
      name: 'MyOrder',
      component: MyOrder
    },{
      path: '/go',
      name: 'go',
      component: go
    },{
      path: '/myhome',
      name: 'myhome',
      component: myhome
    },{
      path: '/myh',
      name: 'myh',
      component: myh
    },{
      path: '/deti/:id',
      name: 'deti',
      component: deti,
      meta:{navShow:false}
    },{
      path: '/orderdeti/:orderNo',
      name: 'orderdeti',
      component: orderdeti,
    },{
      path: '/shippid/:productIds',
      name: 'shippid',
      component: shippid,
    },{
      path: '/addshippid',
      name: 'addshippid',
      component: addshippid,
    },{
      path: '/pay/:orderNo',
      name: 'pay',
      component: pay,
    },{
      path: '/allshippid',
      name: 'allshippid',
      component: allshippid,
    },{
      path: '/updateshippid/:id',
      name: 'updateshippid',
      component: updateshippid,
    },{
      path: '/selectList/:name',
      name: 'selectList',
      component: selectList,
    }
]
})
