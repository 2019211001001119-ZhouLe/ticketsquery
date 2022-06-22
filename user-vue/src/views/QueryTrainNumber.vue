<!-- 首页 -->
<template>
  <div>
    <!--顶部布局的实现-->
    <div class="tl1">
      <img class="tl1_img" src="../../public/imgs/logo.jpg" />

      <div class="tl1_sh">
        <div class="tl1_sh_list">
          <ul>
            <li><a href="" class="tl1_sh_a">管理员登录</a></li>
            <li><span>|</span></li>
            <li><p class="tl1_sh_a2">请先登录</p></li>
          </ul>
        </div>
      </div>
    </div>
    <!--顶部的导航实现-->
    <div class="tl2">
      <div class="tl2_cen">
        <ul>
          <li><a @click="toHome">首页</a></li>
          <li class="tl2_cen_first"><a>车次查询</a></li>
        </ul>
      </div>
    </div>
    <!--轮播图的实现-->
    <div class="tl3">
      <div class="tl3_select">
        <div class="tl3_select1">
          <span>车次查询</span>
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="车次">
              <el-input
                v-model="formInline.trainNunber"
                placeholder="K288"
              ></el-input> </el-form-item
            ><br />
            <el-form-item>
              <el-button type="primary" @click="toTrainNumberListPage"
                >查询</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
 <!--会员服务-->
    <div class="tl5">
      <div><a href="https://cx.12306.cn/tlcx/index.html"><img src="../../public\imgs\abanner01.jpg" width="100%" /></a></div>
      <div class="tl5_fw">
        <a href="https://exservice.12306.cn/excater/index.html">
          <img src="../../public\imgs\abanner02.jpg" width="100%" />
        </a>
        
      </div>
      <div class="tl5_fw2">
        <a href="https://kyfw.12306.cn/otn/resources/login.html">
          <img src="../../public\imgs\abanner03.jpg" width="100%" />
        </a>
        
      </div>
      <div class="tl5_fw tl5_fw2">
        <a href="https://kyfw.12306.cn/otn/resources/login.html">
           <img src="../../public\imgs\abanner04.jpg" width="100%" />
        </a>
       
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
  data() {
    return {
      newsList:[],
      formInline: {
        trainNunber: "",
      },
      config: {
        data: [
          ["公告"],
          ["关于调整互联网、电话订票起售时间的公告  (2014-11-26)"],
          ["中国铁路南宁局集团有限公司加开列车公告  (2022-06-11)"],
          [
            "中国铁路成都局集团有限公司关于2022年6月11日至19日加开部分列车的公告  (2022-06-11)",
          ],
          [
            "中国铁路上海局集团有限公司关于2022年6月10日-2022年6月12日增开部分旅客列车的公告  (2022-06-09)",
          ],
        ],
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
          _this.newsList = res.data.data;
        })
    },
    toHome() {
      // 跳转到路线查询页面
      this.$router.push({
        // 页面的路径
        path: "/",
      });
    },
    toTrainNumberListPage() {
      // 跳转到车次查询页面
      this.$router.push({
        // 页面的路径
        path: "/trainNumberList",
        // 把查询的车次号传到trainNumberList页面
        query: {
          // 车次
          trainNumber: this.formInline.trainNunber,
        },
      });
    },
     toNewsPage() {
      // 跳转到新闻查询页面
      this.$router.push({
        // 页面的路径
        path: "/news",
      });
    },
  },
};
</script>
<style  scoped>
@import "../../public/style/common.css";
</style>
