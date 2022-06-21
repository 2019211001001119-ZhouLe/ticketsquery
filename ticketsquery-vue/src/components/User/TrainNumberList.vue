<!--  -->
<template>
  <div>
    <!-- 顶部导航 -->
    <div>
      <el-page-header @back="goBack" content="车次查询页面"> </el-page-header>
    </div>
    <!-- 数据部分 -->
    <div>
      <el-table :data="tableData" style="width: 100%" height="450">
        <el-table-column fixed prop="trainNumber" label="车次" width="150">
        </el-table-column>
        <el-table-column prop="starStation" label="始发站" width="150">
        </el-table-column>
        <el-table-column prop="starTime" label="始发时间" width="150">
        </el-table-column>
        <el-table-column prop="endStation" label="终点站" width="150">
        </el-table-column>
        <el-table-column prop="endTime" label="到达终点站时间" width="150">
        </el-table-column>
        <el-table-column prop="type" label="列车类型" width="150">
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="150">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 传递过来的车次
      trainNumber: '',
      // 储存查询到的车次信息
      tableData: []
    }
  },
  methods: {
    gteTrainNumberList () {
      // 把vue对象先保存到第三方变量中
      let _this = this
      this.axios
        .get('/train_number/', this.trainNumber)
        .then(function (res) {
          _this.tableData = res.data.data
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>

<style>
</style>
