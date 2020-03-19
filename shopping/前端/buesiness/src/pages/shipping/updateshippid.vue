<template>
  <div>
    <mt-header title="修改地址">
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
  import { Toast } from 'vant';
    export default {
        name: "updateshippid",
        data(){
          return{
            lists:{},
          /*  areaList:{
              province_list:city.province_list,
              city_list: city.city_list,
              county_list: city.county_list
            },*/
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
              }
            },
            searchResult: [],
          }
        },
      mounted(){
        this.addgo();
      },
      methods:{
        //有问题不能用
        addgo:function () {
          const id=this.$route.params.id;
          var _vm=this
          this.service.get("http://localhost:8888/shipping/sselect/"+id).then(function (res) {
            if(res.data.status==0){//接口请求数据成功
              console.log(res);
              var shipping=res.data.data;
              console.log("123456"+shipping);
              var address={
                id:shipping.id,
                name:shipping.receiverName,
                tel:shipping.receiverPhone,
                province:shipping.receiverProvince,
                city:shipping.receiverCity,
                county:shipping.receiverDistrict,
                addressDetail:shipping.receiverAddress,
                postalCode:shipping.receiverZip,
              };
              _vm.lists=address;
            }
          })
        },
        onSave(content){
          //去修改
          console.log(content);
          var  _vm=this;
          this.service.get("http://localhost:8888/shipping/update.do",{
              params :{
                receiverName:content.name,
                receiverPhone:content.tel,
                receiverMobile:content.tel+content.postalCode,
                receiverProvince:content.province,
                receiverCity:content.city,
                receiverAddress:content.addressDetail,
                receiverZip:content.postalCode,
                receiverDistrict:content.county,
                id:this.$route.params.id
              }
            }
          ).then(function (res) {
            if(res.data.status==0){
              //添加成功
              Toast.success('修改成功');
            }else {
              Toast.fail(res.data.msg);
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
