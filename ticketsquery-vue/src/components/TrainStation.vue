<template>
  <div style="padding: 5px 20px;">
    <br>
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="车站名称">
        <el-input placeholder="车站站点名"></el-input>
      </el-form-item>
      <el-form-item>

        <el-button type="primary" icon="el-icon-search">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" icon="el-icon-edit">添加</el-button>
      </el-form-item>
    </el-form>


    <!-- 将获取到的数据进行计算 -->
    <el-table  border :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width:100%">
      <el-table-column align="center" prop="trainstationId" label="火车站编号" width="300"></el-table-column>
      <el-table-column align="center" prop="cityId" label="所属城市编号" width="400"></el-table-column>
      <el-table-column align="center" prop="trainstationName" label="车站名称"></el-table-column>

    </el-table>
    <div class="tabListPage">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="currentPage" :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper"
                     :total="totalCount" background>
      </el-pagination>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      // 总数据
      tableData: [],
      // 默认显示第几页
      currentPage: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 1,
      // 默认每页显示的条数（可修改）
      PageSize: 10
    }
  },
  methods: {
    getData () {
      // 这里使用axios，使用时请提前引入
      this.axios.get('/trainstation').then(reponse => {
        console.log(reponse.data.data)
        // 将数据赋值给tableData
        this.tableData = reponse.data.data
        // 将数据的长度赋值给totalCount
        this.totalCount = reponse.data.data.length
      })
    },
    // 分页
    // 每页显示的条数
    handleSizeChange (val) {
      // 改变每页显示的条数
      this.PageSize = val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.currentPage = 1
    },
    // 显示第几页
    handleCurrentChange (val) {
      // 改变默认的页数
      this.currentPage = val
    }
  },
  created () {
    this.getData()
  }
}
</script>
