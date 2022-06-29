<!--  -->
<template>
  <div>
    <!-- 顶部导航 -->
    <div>
      <el-page-header @back="goBack" content="更多新闻" style="color: #4490f1">
      </el-page-header>
    </div>
    <!-- 数据部分 -->
    <div>
      <el-table
        :data="newsData"
        :cell-style="tableRowStyle"
        :header-cell-style="tableHeaderColor"
        style="width: 100%; overflow-y: auto"
        @cell-click="txidUrl"
      >
        <el-table-column
          prop="newsPublishTime"
          label="发布时间"
          sortable
        >
        </el-table-column>
        <el-table-column prop="newsTitle" label="新闻标题"> </el-table-column>
        <el-table-column prop="adminId" label="发布人"> </el-table-column>
        <el-table-column prop="newsPublishTime" label="新闻详情">
          <template slot- scope="scope">
            <a :href="scope.row.file" target=" blank">详情</a>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      // 储存新闻数据
      newsData: [],
    };
  },
  created() {
    // 自动获取更多新闻
    this.gteNews();
  },
  methods: {
    //新闻详情点击跳转
    txidUrl(row) {
      let url = row.newsUrl;
      window.location.href = url;
    },
    // 获取新闻列表
    gteNews() {
      // 把vue对象先保存到第三方变量中
      let _this = this;
      this.myAxios.get("/news/all").then(function (res) {
        console.log(res.data.data);
        _this.newsData = res.data.data;
      });
    },
    //设置表格行的样式
    tableRowStyle() {
      return "background-color:white;font-size:10px; color:black;text-align:center";
    },
    //设置表头行的样式
    tableHeaderColor() {
      return "font-wight:200;font-size:15px; background-color:cadetblue;color:black;text-align:center";
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style  scoped>

</style>
