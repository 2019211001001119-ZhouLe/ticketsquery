<!-- 首页 -->
<template>
  <div>
    <!--顶部布局的实现-->
    <div class="tl1">
      <img class="tl1_img" src="../../public/imgs/logo.jpg" />
      <div class="tl1_sh">
        <div class="tl1_sh_list"></div>
      </div>
    </div>
    <!--顶部的导航实现-->
    <el-menu
      mode="horizontal"
      router="true"
      background-color="#3b99fc"
      text-color="#ffffff"
    >
      <el-row style="text-align: center">
        <el-col :span="3" :offset="3">
          <el-menu-item index="/">首页</el-menu-item>
        </el-col>
        <el-col :span="3">
          <el-menu-item index="/QueryTrainNumber">车次查询</el-menu-item>
        </el-col>
      </el-row>
    </el-menu>
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
            ref="formInline"
          >
            <el-form-item label="始发站" prop="star">
              <el-input
                v-model="formInline.star"
                placeholder="成都"
              ></el-input> </el-form-item
            ><i
              class="el-icon-sort"
              @click="rollBack"
              style="position: absolute; left: 460px; top: 293px"
            ></i>
            <el-form-item label="终点站" prop="end">
              <el-input v-model="formInline.end" placeholder="深圳"></el-input>
            </el-form-item>
            <br />
            <el-form-item label="出发时间" prop="departureTime">
              <el-col :span="11">
                <el-date-picker
                  type="date"
                  value-format="yyyy-MM-dd"
                  :picker-options="pickerOptions"
                  placeholder="出发日期"
                  v-model="formInline.departureTime"
                ></el-date-picker>
              </el-col>
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
    <!-- 轮播新闻信息 -->
    <div class="tl9">
      <div class="tl9_msg1">
        <el-table
          :data="newsData"
          height="350"
          ref="table"
          style="width: 100%"
          @cell-click="txidUrl"
        >
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
      <p>版权所有©2022 火车车次查询系统</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newsData: [],
      formInline: {
        departureTime: "",
        star: "",
        end: "",
        // 中间值
        median: "",
      },
      // 表单校验
      rules: {
        star: [
          { required: true, message: "起始站不能为空", trigger: "blur" },
          {
            pattern: /^[a-zA-Z\u4e00-\u9fa5]+$/,
            required: true,
            message: "车站名为中文",
            trigger: "blur",
          },
        ],
        end: [
          { required: true, message: "终点站不能为空", trigger: "blur" },
          {
            pattern: /^[a-zA-Z\u4e00-\u9fa5]+$/,
            required: true,
            message: "车站名为中文",
            trigger: "blur",
          },
        ],
        departureTime: [
          {
            type: "date",
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
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
        console.log(res);
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
    toRoutePage(formInline) {
      this.$refs[formInline].validate((valid) => {
        if (valid) {
          // 跳转到路线查询列表页面
          this.$router.push({
            // 页面的路径
            path: "/routeList",
            query: {
              // 起始站
              departureStation: this.formInline.star,
              // 终点站
              arrivalStation: this.formInline.end,
              // 出发时间
              departureTime: this.formInline.departureTime,
            },
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 实现往返程
    rollBack() {
      this.formInline.median = this.formInline.end;
      this.formInline.end = this.formInline.star;
      this.formInline.star = this.formInline.median;
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
