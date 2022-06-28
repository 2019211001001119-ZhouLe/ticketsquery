<!--  -->
<template>
  <div>
    <!-- 顶部导航 -->
    <div>
      <el-page-header
        @back="goBack"
        :content="
          '由' +
          this.$route.query.departureStation +
          '开往' +
          this.$route.query.arrivalStation +
          '所有车次信息'
        "
        style="color: #4490f1"
      >
      </el-page-header>
    </div>
    <!-- 数据部分 -->
    <div>
      <el-table
        :data="trainNumbers"
        :cell-style="tableRowStyle"
        :header-cell-style="tableHeaderColor"
        style="width: 100%; overflow-y: auto"
      >
        <el-table-column fixed prop="routertrainId" label="车次">
        </el-table-column>
        <el-table-column prop="routertrainType" label="列车类型">
        </el-table-column>
        <el-table-column prop="departureStationId" label="始发站">
        </el-table-column>
        <el-table-column prop="arrivalStationId" label="终点站">
        </el-table-column>
        <el-table-column prop="departureTime" label="始发站时间">
        </el-table-column>
        <el-table-column prop="arrivalTime" label="到达终点站时间">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 起点站
      departureStation: "",
      // 终点站
      arrivalStation: "",
      //查询到的车次信息
      trainNumbers: [],
    };
  },
  // 生命周期钩子函数
  created() {
    // 获取到路由传参传递过来的departureStation 给data中的对应的变量进行赋值
    this.departureStation = this.$route.query.departureStation;
    // 获取到路由传参传递进来的arrivalStation
    this.arrivalStation = this.$route.query.arrivalStation;
    // 自动查询传递过来的路线车次
    this.gteRoutrList();
  },
  methods: {
    gteRoutrList() {
      // 把vue对象先保存到第三方变量中
      let _this = this;
      this.myAxios
        .get(
          "/train_number/" + _this.departureStation + "/" + _this.arrivalStation
        )
        .then(function (res) {
          console.log(res.data.data);
          _this.trainNumbers = res.data.data;
        });
    },
    //设置表格行的样式
    tableRowStyle() {
      return "background-color:white;font-size:10px; color:black;text-align:center";
    },
    //设置表头行的样式
    tableHeaderColor() {
      return "font-wight:200;font-size:15px;background-color:cadetblue;color:black;text-align:center";
    },
    // 返回函数
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style  scoped>
</style>
