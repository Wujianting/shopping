<template>
    <div>
      <mt-header title="我的收货地址">
        <router-link to="/myh" slot="left">
          <mt-button icon="back" @click="back">返回</mt-button>
        </router-link>
      </mt-header>
      <van-address-list
        v-model="chosenAddressId"
        :list="list"
        :disabled-list="disabledList"
        disabled-text="以下地址超出配送范围"
        @add="onAdd"
        @edit="onEdit"
      />
      <van-dialog
        v-model="show"
        title=""
        show-cancel-button
      >
      <addshippid></addshippid>
      </van-dialog>
    </div>
</template>

<script>
  import addshippid from "../shipping/addshippid";
  import { Dialog } from 'vant';
    export default {
      name: "allshippid",
      data() {
        return {
          chosenAddressId: '1',
          list: [],
          disabledList: [],
          show: false
        }
      },
      mounted() {
        this.getidlist();
      },
      components: {
        [Dialog.Component.name]: Dialog.Component,
        "addshippid":addshippid
      },
      methods: {
        onAdd() {
        this.show=true;
        },
        onEdit(item, index) {
/*          Toast('编辑地址:' + index);*/
          this.$router.push({
            // 你要跳转的地址
            path: '/updateshippid/'+item.id,
          })
        },
       /* back: function(){
          this.$router.go(-1)
        },*/
        getidlist() {
          this.service.get("http://localhost:8888/shipping/select").then(this.getCarListInfo)
        },
        getCarListInfo(res) {
          console.log(res);
          if (res.data.status == 0) {//接口请求数据成功
            this.list= res.data.data.list;
          }
        },
      }
    }
</script>

<style scoped>

</style>
