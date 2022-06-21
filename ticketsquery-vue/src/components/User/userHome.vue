<!-- 首页 -->
<template>
  <div>
    <!--顶部布局的实现-->
    <div class="tl1">
      <img class="tl1_img" src="../ticketsquery-vue/public/imgs/logo.jpg" />

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
      <div><img src="../ticketsquery-vue/public/imgs/abanner01.jpg" width="100%" /></div>
      <div class="tl5_fw">
        <img src="../ticketsquery-vue/public/imgs/abanner02.jpg" width="100%" />
      </div>
      <div class="tl5_fw2">
        <img src="../ticketsquery-vue/public/imgs/abanner03.jpg" width="100%" />
      </div>
      <div class="tl5_fw tl5_fw2">
        <img src="../ticketsquery-vue/public/imgs/abanner04.jpg" width="100%" />
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
          <img src="../ticketsquery-vue/public/imgs/scope1.jpg" width="100%" />
        </div>
      </div>
      <div class="tl7_div1 tl7_div2">
        <div class="tl7_img">
          <img src="../ticketsquery-vue/public/imgs/scope1.jpg" width="100%" />
        </div>
      </div>
      <div class="tl7_div1 tl7_div2">
        <div class="tl7_img">
          <img src="../ticketsquery-vue/public/imges/scope1.jpg" width="100%" />
        </div>
      </div>
      <div class="tl7_div1 tl7_div2">
        <div class="tl7_img">
          <img src="../ticketsquery-vue/public/imgs/scope1.jpg" width="100%" />
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
  header: ['列1', '列2', '列3'],
  data () {
    return {
      newsList: [],
      formInline: {
        star: '',
        end: ''
      },
      config: {
        data: [],
        index: true,
        columnWidth: [60],
        align: ['center'],
        hoverPause: true,
        headerBGC: '#bdc3c7',
        oddRowBGC: '#bdc3c7',
        evenRowBGC: '#bdc3c7'
      }
    }
  },
  // 钩子函数
  created () {
    // 自动获取首页新闻
    this.gteNews()
  },
  methods: {
    // 获取新闻列表
    gteNews () {
      // 把vue对象先保存到第三方变量中
      let _this = this
      this.axios
        .get('/news/all')
        .then(function (res) {
          console.log(res.data.data)
          _this.config.data = res.data.data
          // 创建新数组储存新闻数据
          var newArr = []
          //  使用map函数，在里面定义一个sevm{}对象
          res.data.data.rows.map(function (item, index) {
            var sevm = {}
            sevm['name'] = item.newsId
            sevm['value'] = item.newsTitle
            sevm['value'] = item.newsPublishTime
            newArr.push(sevm)
          })
          console.log('newarr', newArr)
          this.config = {
            data: newArr
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    toNewsPage () {
      // 跳转到新闻查询页面
      this.$router.push({
        // 页面的路径
        path: '/news'
      })
    },
    toTrainNumberPage () {
      // 跳转到车次查询页面
      this.$router.push({
        // 页面的路径
        path: '/queryTrainNumber'
      })
    },
    toRoutePage () {
      // 跳转到路线查询列表页面
      this.$router.push({
        // 页面的路径
        path: '/routeList',
        query: {
          // 起始站
          starTrainsStation: this.formInline.star,
          // 终点站
          endTrainsStation: this.formInline.end
        }
      })
    }
  }
}
</script>
<style>
* {
  margin: 0px;
  padding: 0px;
}
/**顶部布局***/
.tl1 {
  width: 100%;
  height: 80px;
  border: 0px solid red;
  position: relative;
}
.tl1_img {
  margin-top: 15px;
  margin-left: 20px;
}
.tl1_sh {
  width: 1000px;
  height: 70px;
  border: 0px solid red;
  float: right;
  margin-top: 10px;
  background-image: url(../ticketsquery-vue/public/imgs/train.png);
  background-repeat: no-repeat;
  background-position: left;
  background-size: 1000px 40px;
  line-height: 70px;
}
.tl1_sh_list {
  width: 490px;
  height: 70px;
  float: right;
  margin-right: 80px;
}
.tl1_sh_list li {
  list-style: none; /*去除列表符号*/
  float: right;
  margin-left: 22px;
}
.tl1_sh_list a {
  text-decoration: none;
  font-size: 12px;
}
.tl1_sh_a {
  color: #3b99fc;
}
.tl1_sh_a1 {
  color: #333333;
}
.tl1_sh_list span {
  font-size: 12px;
  color: #acd1f9;
}
/**顶部导航的**/
.tl2 {
  width: 100%;
  height: 40px;
  background-color: #3b99fc;
}

.tl2_cen {
  width: 1200px;
  height: 40px;
  margin: 0px auto;
  line-height: 40px;
}
.tl2_cen li {
  list-style: none;
  float: left;
  height: 40px;
  width: 148px;
  text-align: center;
}
.tl2_cen a {
  color: #ffffff;
  font-size: 14px;
  text-decoration: none;
}
.tl2_cen_first {
  background-color: #2676e3;
}
.tl2_cen li:hover {
  background-color: #2676e3;
}
/*轮播图*/
.tl3 {
  width: 100%;
  height: 450px;
  border: 0px solid red;
  background-image: url(../ticketsquery-vue/public/imgs/banner12.jpg);
  animation: 30s lbt  infinite;
}
@keyframes lbt{
  0%{background-image: url(../ticketsquery-vue/public/imgs/banner09.jpg);}
  25%{background-image: url(../ticketsquery-vue/public/imgs/banner11.jpg);}
  50%{background-image: url(../ticketsquery-vue/public/imgs/banner12.jpg);}
  75%{background-image: url(../ticketsquery-vue/public/imgs/banner13.jpg);}
  100%{background-image: url(../ticketsquery-vue/public/imgs/banner09.jpg);}
}

.tl3_select {
  width: 400px;
  height: 250px;
  margin-left: 250px;
  margin-top: 90px;
  float: left;
  background-color: #ffffff;
  /* 设置圆角 */
  border-radius: 15px;
  /* 卡片效果 */
  box-shadow: 0 4px 8px 0 rgba(255, 255, 255, 0.2);
  background-color: white;
}

.tl3_select1 span {
  font-size: 17px;
  color: rgb(31, 30, 30);
  margin-bottom: 10px;
}
.tl3_select1 {
  text-align: center;
  margin-top: 20px;
}
/**会员服务开始***/
.tl5{
  width: 1200px;
  height: 340px;
  border: 0px solid red;
  margin: 0px auto;
  margin-top: 30px;
}
.tl5 div{
  width: 590px;
  height: 160px;
  border: 0px solid green;
  float: left;
}
.tl5_fw{
  margin-left: 15px;
}
.tl5_fw2{
  margin-top: 15px;
}
/**更多服务***/
.tl6 {
  width: 1200px;
  height: 40px;
  margin: 0px auto;
  line-height: 40px;
  border-bottom: 2px solid #dedede;
  margin-top: 30px;
}
.tl6 span {
  color: #3b99fc;
  font-size: 16px;
}

/**铁路旅游信息***/
.tl7 {
  width: 1200px;
  height: 300px;
  margin: 0px auto;
  margin-top: 10px;
}
.tl7 div {
  width: 290px;
  height: 180px;
  border: 1px solid #dedede;
  float: left;
}
.tl7_div1 {
  margin-left: 10px;
}
.tl7_div2 {
  margin-top: 7px;
}
.tl7_img {
  width: 290px;
  height: 157px;
}
/**铁路最新发布***/
.tl8 {
  height: 40px;
  width: 1200px;
  border: 0px solid red;
  margin: 0px auto;
  margin-top: 30px;
}
.tl8 div {
  width: 390px;
  height: 40px;
  float: left;
  text-align: center;
  line-height: 40px;
}
.tl8_div2 {
  background-color: #3b99fc;
  color: #ffffff;
}
.tl8_div1 {
  margin-left: 30px;
  background-color: #efeff4;
  color: #333333;
  float: right;
}
.tl9 {
  width: 1204px;
  height: 255px;
  margin: 0px auto;
}
/********底部*********/
.tl10 {
  width: 100%;
  height: 75px;
  margin-top: 30px;
}

.tl11 {
  width: 100%;
  height: 50px;
  background-color: #666666;
}
.tl11 p {
  font-size: 16px;
  color: rgb(255, 255, 255);
  text-align: center;
  line-height: 50px;
}
</style>
