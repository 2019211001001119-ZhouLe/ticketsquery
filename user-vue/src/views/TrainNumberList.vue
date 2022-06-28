<!--  -->
<template>
  <div>
    <!-- 顶部导航 -->
    <div>
      <el-page-header
        @back="goBack"
        :content="this.$route.query.trainNumber + '车次信息'"
        style="color: #4490f1"
      >
      </el-page-header>
    </div>
    <!-- 数据部分 -->
    <div>
      <el-table
        :default-sort = "{prop: 'routerdetailId', order: 'ascending'}"
        :data="tableData"
        :cell-style="tableRowStyle"
        :header-cell-style="tableHeaderColor"
        style="width: 100%; overflow-y: auto"
        >
        <el-table-column fixed prop="routerdetailId" label="车次">
        </el-table-column>
        <el-table-column prop="routertrainId" label="车次">
        </el-table-column>
        <el-table-column prop="trainstationId" label="经过站">
        </el-table-column>
        <el-table-column prop="departureTime" label="该站始发时间">
        </el-table-column>
        <el-table-column prop="arrivalTime" label="到达下一站时间">
        </el-table-column>
        <el-table-column prop="laterTime" label="晚点时间">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 传递过来的车次
      trainNumber: "",
      // 储存查询到的车次信息
      tableData: [],
    };
  },
  created() {
    // 获取到路由传参传递进来的arrivalStation
    this.trainNumber = this.$route.query.trainNumber;
    // 自动查询传递过来的路线车次
    this.gteTrainNumberList();
  },
  methods: {
    gteTrainNumberList() {
      // 把vue对象先保存到第三方变量中
      let _this = this;
      this.myAxios
        .get("/details/query/" + _this.trainNumber)
        .then(function (res) {
          console.log(res);
          _this.tableData = res.data.data;
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
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style  scoped>
</style>
