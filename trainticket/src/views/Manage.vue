<template>
  <div id="app">
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-light">
          <div class="slide-contend" @click="reFreshHome">
            <img
              class="img1"
              src="http://yanjiojio.fun/HeadIcon/火车.png"
              alt=""
            />
            <h1>火车票后台管理系统</h1>
            <el-dropdown class="userName">
              <span class="el-dropdown-link">
                {{userObj.userName}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>更换头像</el-dropdown-item>
                <el-dropdown-item>账户设置</el-dropdown-item>
                <el-dropdown-item>退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <img
              class="img2"
              :src="userObj.headIcon"
              alt=""
            />
          </div>
        </div>
      </el-col>
      <el-col :span="4" class="sideBox">
        <el-menu
          default-active="/manage/home"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          :unique-opened="true"
          router
        >
          <el-menu-item
            v-for="item in noChildren"
            :index="item.path"
            :key="item.path"
          >
            <i :class="'el-icon-' + item.icon"></i>
            <span slot="title">{{ item.label }}</span>
          </el-menu-item>
          <el-submenu
            :index="item.path"
            v-for="(item, index) in hasChildren"
            :key="index"
          >
            <template slot="title">
              <i :class="'el-icon-' + item.icon"></i>
              <span>{{ item.label }}</span>
            </template>
            <el-menu-item-group>
              <el-menu-item
                :index="subItem.path"
                v-for="(subItem, sunIndex) in item.children"
                :key="sunIndex"
                >{{ subItem.label }}</el-menu-item
              >
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-col>
      <el-col :span="20">
        <router-view></router-view>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  data() {
    return {
      // 用户数据
      userObj: {
        userName: "admin",
        headIcon: "http://yanjiojio.fun/HeadIcon/iconyou.jpg",
      },
      // 侧边栏数据
      asideMenu: [
        {
          path: "/manage/home",
          name: "home",
          label: "首页",
          icon: "s-home",
        },
        {
          path: "/manage/trainid",
          name: "trainid",
          label: "车次管理",
          icon: "s-marketing",
          children: [
            {
              path: "/manage/traintype",
              label: "车型管理",
            },
            {
              path: "/manage/traininfo",
              label: "车次管理",
            },
          ],
        },
        {
          path: "/manage/pot",
          name: "pot",
          label: "车站管理",
          icon: "s-flag",
          children: [
            {
              path: "/manage/potmanage",
              label: "站点管理",
            },
          ],
        },
        {
          path: "/manage/news",
          name: "news",
          label: "新闻动态",
          icon: "s-claim",
          children: [
            {
              path: "/manage/newsmody",
              label: "动态修改",
            },
            {
              path: "/manage/newsissue",
              label: "动态发布",
            },
          ],
        },
        {
          path: "/manage/manager",
          name: "manager",
          label: "管理员管理",
          icon: "s-custom",
          children: [
            {
              path: "/manage/manageradd",
              label: "管理员增加",
            },
            {
              path: "/manage/managerinfo",
              label: "信息管理",
            },
          ],
        },
      ],
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    reFreshHome() {
      // this.$router.push("/home");
    },
  },
  computed: {
    // 过滤出没有分层的元素
    noChildren() {
      return this.asideMenu.filter((item) => !item.children); //过滤元组
    },
    // 过滤出有分层的元素
    hasChildren() {
      return this.asideMenu.filter((item) => item.children); //过滤元组
    },
  },
};
</script>

<style scoped>
.sideBox {
  height: calc(100vh - 60px);
  background-color: white;
  border: 1px #e6e6e6 solid;
  box-sizing: border-box;
}
.userName {
  float: right;
}
.el-dropdown-link {
  cursor: pointer;
  color: #000;
  line-height: 40px;
  margin-left: 5px;
  margin-right: 10px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.slide-contend {
  cursor: pointer;
  display: inline;
}
.slide-contend .img1 {
  width: 40px;
  float: left;
}
.slide-contend .img2 {
  width: 30px;
  float: right;
  border-radius: 20px;
  margin: 5px;
}
.slide-contend h1 {
  font-weight: 300 !important;
  line-height: 40px;
  display: inline;
}
.bg-purple-light {
  background: white;
}
.grid-content {
  min-height: 40px;
  padding: 10px;
  box-shadow: 0 -30px 30px 3px black;
}
#app {
  background-color: #fafafa;
}
</style>