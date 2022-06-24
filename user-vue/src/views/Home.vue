<!-- 首页 -->
<template>
  <div>
    <!--顶部布局的实现-->
    <div class="tl1">
      <img class="tl1_img" src="../../public/imgs/logo.jpg" />

      <div class="tl1_sh">
        <div class="tl1_sh_list">
          <ul>
            <li><a href="" class="tl1_sh_a">管理员登录  <i class="el-icon-user-solid"></i></a></li>
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
          <el-form
            :inline="true"
            :model="formInline"
            :rules="rules"
            class="demo-form-inline"
          >
            <el-form-item label="始发站">
              <el-input
                v-model="formInline.star"
                placeholder="成都"
                prop="star"
              ></el-input> </el-form-item
            ><br />
            <el-form-item label="终点站">
              <el-input
                v-model="formInline.end"
                placeholder="深圳"
                prop="end"
              ></el-input>
            </el-form-item>
            <br />
            <el-form-item>
              <el-button type="primary" @click="toRoutePage('formInline')"
                >查询</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <!--会员服务-->
    <div class="tl5">
      <div>
        <a href="https://cx.12306.cn/tlcx/index.html"
          ><img src="../../public\imgs\abanner01.jpg" width="100%"
        /></a>
      </div>
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
        <el-table :data="newsData" height="350" ref="table" style="width: 100%">
          <el-table-column prop="newsId" label="热搜排行" width="180">
          </el-table-column>
          <el-table-column prop="newsTitle" label="标题" width="780">
          </el-table-column>
          <el-table-column prop="newsPublishTime" label="发布时间">
          </el-table-column>
        </el-table>
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
      newsData: [],
      formInline: {
        name:"",
        star: "",
        end: "",
      },
      // 表单校验
      rules: {
        star: [
          { required: true, message: "字段名称不能为空", trigger: "blur" },
        ],
        end: [{ required: true, message: "字段类型不能为空", trigger: "blur" }],
      },
    };
  },
  // 钩子函数
  created() {
    // 自动获取首页新闻
    this.gteNews();
  },
  mounted() {
    // 初始化表格
    this.init();
  },
  methods: {
    // 获取新闻列表
    gteNews() {
      // 把vue对象先保存到第三方变量中
      let _this = this;
      this.myAxios.get("/news/all").then(function (res) {
        _this.newsData = res.data.data;
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
          departureStation: this.formInline.star,
          // 终点站
          arrivalStation: this.formInline.end,
        },
      });
    },
    init() {
      // 拿到表格挂载后的真实DOM
      const table = this.$refs.table;
      // 拿到表格中承载数据的div元素
      const divData = table.bodyWrapper;
      // 拿到元素后，对元素进行定时增加距离顶部距离，实现滚动效果(此配置为每100毫秒移动1像素)
      setInterval(() => {
        // 元素自增距离顶部1像素
        divData.scrollTop += 1;
        // 判断元素是否滚动到底部(可视高度+距离顶部=整个高度)
        if (divData.clientHeight + divData.scrollTop == divData.scrollHeight) {
          // 重置table距离顶部距离
          divData.scrollTop = 0;
        }
      }, 100);
    },
  },
};
</script>
<style  scoped>
@import "../../public/style/common.css";
</style>
