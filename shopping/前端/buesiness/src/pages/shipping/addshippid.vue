<template>

  <div>
    <mt-header title="添加地址">
      <router-link to="/allshippid" slot="left">
        <mt-button icon="back" @click="back">返回</mt-button>
      </router-link>
    </mt-header>
    <van-address-edit
      :area-list="areaList"
      show-postal
      show-delete
      show-set-default
      show-search-result
      :search-result="searchResult"
      :area-columns-placeholder="['请选择', '请选择', '请选择']"
      @save="onSave"
      @delete="onDelete"
      @change-detail="onChangeDetail"
    />



  </div>


</template>

<script>
  export default {
    name: "addshippid",
    data(){
      return{
        areaList:{
          province_list: {
            110000: '北京市',
            120000: '天津市'
          },
          city_list: {
            110100: '北京市',
            110200: '县',
            120100: '天津市',
            120200: '县'
          },
          county_list: {
            110101: '东城区',
            110102: '西城区',
            110105: '朝阳区',
            110106: '丰台区',
            120101: '和平区',
            120102: '河东区',
            120103: '河西区',
            120104: '南开区',
            120105: '河北区',
            // ....

          }
        },
        searchResult: []
      }
    },

    methods: {
      onSave(e) {
        console.log(e);
        this.service.get("http://localhost:8888/shipping/add.do",{params:{
            receiverName:e.name,
            receiverPhone:e.tel,
            receiverMobile:e.tel,
            receiverProvince:e.province,
            receiverCity:e.city,
            receiverDistrict:e.county,
            receiverAddress:e.addressDetail,
            receiverZip:e.postalCode
          }
        }).then(function (res) {
          console.log(res)
        })
      },
      onDelete() {
        Toast('delete');
      },
      back: function(){
        this.$router.go(-1)
      },
    },

  }
</script>

<style scoped>

</style>
