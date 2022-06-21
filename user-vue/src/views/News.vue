<!--  -->
<template>
  <div>
    <!-- 顶部导航 -->
    <div>
      <el-page-header @back="goBack" content="更多新闻"> </el-page-header>
    </div>
    <!-- 数据部分 -->
    <div>
      <el-table
       :data="newsData"
        style="width: 100%"
        height="550"
      >
        <el-table-column fixed prop="newsId" label="新闻编号" width="250" v-model="newsData.newsId">
        </el-table-column>
        <el-table-column prop="adminId" label="发布人" width="250" v-model="newsData.adminId">
        </el-table-column>
        <el-table-column prop="newsTitle" label="新闻标题" width="250" v-model="newsData.newsTitle">
        </el-table-column>
        <el-table-column prop="newsPublishTime" label="发布时间" width="250" v-model="newsData.newsPublishTime">
        </el-table-column>
        <el-table-column prop="newsUrl" label="新闻地址" width="250" v-model="newsData.newsUrl">
        </el-table-column>
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
    // 获取新闻列表
    gteNews() {
      // 把vue对象先保存到第三方变量中
      let _this = this;
      this.myAxios
        .get("/news/all")
        .then(function (res) {
          console.log(res.data.data);
          _this.newsData = res.data.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style  scoped>
</style>
