<!--  -->
<template>
  <div>
    <!-- 顶部导航 -->
    <div>
      {{ this.$route.query }}
      <el-page-header @back="goBack" content="路线查询页面"> </el-page-header>
    </div>
    <!-- 数据部分 -->
    <div>
      <el-table v-for="item in tableData" :key="item.id"  style="width: 100%" height="550">
        <el-table-column fixed prop="trainNumber" label="车次" width="250">
        </el-table-column>
        <el-table-column prop="starTime" label="始发时间" width="250">
        </el-table-column>
        <el-table-column prop="endTime" label="到达终点站时间" width="250">
        </el-table-column>
        <el-table-column prop="type" label="列车类型" width="250">
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="250">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      // 起点站
      starTrainsStation: '',
      // 终点站
      endTrainsStation: '',
      // 将查询的车次储存在这
      tableData: []
    }
  },
  // 生命周期钩子函数
  created () {
    // 获取到路由传参传递过来的starTrainsStation 给data中的对应的变量进行赋值
    this.starTrainsStation = this.$route.query.formInline.starTrainsStation
    // 获取到路由传参传递进来的endTrainsStation
    this.endTrainsStation = this.$route.query.formInline.endTrainsStation
    // 自动查询传递过来的路线车次
    this.gteRoutrList()
  },
  methods: {
    gteRoutrList () {
      // 把vue对象先保存到第三方变量中
      let _this = this
      this.axios
        .get('/trainstation/{trainstationName}')
        .then(function (res) {
          console.log(res)
          _this.tableData = res.data
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
