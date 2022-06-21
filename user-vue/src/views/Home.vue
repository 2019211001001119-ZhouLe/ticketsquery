<!-- 首页 -->
<template>
  <div>
    <!--顶部布局的实现-->
    <div class="tl1">
      <img class="tl1_img" src="../../public\imgs\logo.jpg" />

      <div class="tl1_sh">
        <div class="tl1_sh_list">
          <ul>
            <li><a href="" class="tl1_sh_a">管理员登录</a></li>
            <li><span>|</span></li>
            <li><p class="tl1_sh_a1">请先登录</p></li>
          </ul>
        </div>
      </div>
    </div>
    <!--顶部的导航实现-->
    <div class="tl2">
      <div class="tl2_cen">
        <ul>
          <li class="tl2_cen_first"><a>首页</a></li>
          <li><a @click="toTrainNumberPage">车次查询</a></li>
        </ul>
      </div>
    </div>
    <!--轮播图的实现-->
    <div class="tl3">
      <div class="tl3_select">
        <div class="tl3_select1">
          <span>路线查询</span>
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="始发站">
              <el-input
                v-model="formInline.star"
                placeholder="北京站"
              ></el-input> </el-form-item
            ><br />
            <el-form-item label="终点站">
              <el-input
                v-model="formInline.end"
                placeholder="南昌站"
              ></el-input>
            </el-form-item>
            <br />
            <el-form-item>
              <el-button type="primary" @click="toRoutePage">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <!--会员服务-->
    <div class="tl5">
      <div><img src="../../public\imgs\abanner01.jpg" width="100%" /></div>
      <div class="tl5_fw">
        <img src="../../public\imgs\abanner02.jpg" width="100%" />
      </div>
      <div class="tl5_fw2">
        <img src="../../public\imgs\abanner03.jpg" width="100%" />
      </div>
      <div class="tl5_fw tl5_fw2">
        <img src="../../public\imgs\abanner04.jpg" width="100%" />
      </div>
    </div>
    <!--更多内容-->
    <div class="tl6">
      <span>铁路旅游</span>
    </div>
    <!--铁路旅游信息展示-->
    <div class="tl7">
      <div class="tl7_div2">
        <div class="tl7_img">
          <img src="../../public\imgs\scope1.jpg" width="100%" />
        </div>
      </div>
      <div class="tl7_div1 tl7_div2">
        <div class="tl7_img">
          <img src="../../public\imgs\scope1.jpg" width="100%" />
        </div>
      </div>
      <div class="tl7_div1 tl7_div2">
        <div class="tl7_img">
          <img src="../../public\imgs\scope1.jpg" width="100%" />
        </div>
      </div>
      <div class="tl7_div1 tl7_div2">
        <div class="tl7_img">
          <img src="../../public\imgs\scope1.jpg" width="100%" />
        </div>
      </div>
    </div>
    <!--信息位置展示-->
    <div class="tl8">
      <div class="tl8_div2">最新发布新闻</div>
      <div class="tl8_div"></div>
      <div class="tl8_div1" @click="toNewsPage">更多详情</div>
    </div>
    <!-- 轮播信息 -->
    <div class="tl9">
      <div class="tl9_msg1">
        <dv-scroll-board
          :config="config"
          style="width: 1204px; height: 255px"
        />
      </div>
    </div>
    <div class="tl10"></div>
    <!-- 底部标识 -->
    <div class="tl11">
      <p>版权所有，侵权必究</p>
    </div>
  </div>
</template>

<script>
export default {
  header: ["列1", "列2", "列3"],
  data() {
    return {
      newsList: [],
      formInline: {
        star: "",
        end: "",
      },
      config: {
        data: [],
        index: true,
        columnWidth: [60],
        align: ["center"],
        hoverPause: true,
        headerBGC: "#bdc3c7",
        oddRowBGC: "#bdc3c7",
        evenRowBGC: "#bdc3c7",
      },
    };
  },
  // 钩子函数
  created() {
    // 自动获取首页新闻
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
          _this.config.data = res.data.data;
          // 创建新数组储存新闻数据
          var newArr = [];
          //  使用map函数，在里面定义一个sevm{}对象
          res.data.data.rows.map(function (item, index) {
            var sevm = {};
            sevm["name"] = item.newsId;
            sevm["value"] = item.newsTitle;
            sevm["value"] = item.newsPublishTime;
            newArr.push(sevm);
          });
          console.log("newarr", newArr);
          this.config = {
            data: newArr,
          };
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    toNewsPage() {
      // 跳转到新闻查询页面
      this.$router.push({
        // 页面的路径
        path: "/news",
      });
    },
    toTrainNumberPage() {
      // 跳转到车次查询页面
      this.$router.push({
        // 页面的路径
        path: "/queryTrainNumber",
      });
    },
    toRoutePage() {
      // 跳转到路线查询列表页面
      this.$router.push({
        // 页面的路径
        path: "/routeList",
        query: {
          // 起始站
          starTrainsStation: this.formInline.star,
          // 终点站
          endTrainsStation: this.formInline.end,
        },
      });
    },
  },
};
</script>
<style  scoped>
@import "../../public/style/common.css";
</style>
