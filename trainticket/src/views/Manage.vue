<template>
  <div id="app">
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-light">
          <div class="slide-contend">
            <img
              class="img1"
              src="http://yanjiojio.fun/HeadIcon/火车.png"
              alt=""
            />
            <h1 @click="reFreshHome">火车票后台管理系统</h1>
            <el-dropdown class="userName" @command="handleCommand">
              <span class="el-dropdown-link">
                {{ userObj.adminName
                }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="a">更改密码</el-dropdown-item>
                <el-dropdown-item command="b">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <img class="img2" :src="headIcon" alt="" />
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
      <!-- 弹出窗修改密码 -->
      <el-dialog
        title="修改密码"
        :visible.sync="modyVisible"
        :closeOnClickModal="false"
      >
        <el-form
          :model="numberValidateForm"
          ref="numberValidateForm"
          label-width="100px"
          class="demo-ruleForm"
          :rules="rules"
        >
          <el-form-item label="旧密码" prop="oldpassword">
            <el-input
              type="oldpassword"
              v-model="numberValidateForm.oldpassword"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newpassword">
            <el-input
              type="password"
              v-model="numberValidateForm.newpassword"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="passwordcheck">
            <el-input
              type="password"
              v-model="numberValidateForm.passwordcheck"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('numberValidateForm')"
              >提交</el-button
            >
            <el-button @click="resetForm('numberValidateForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import { delToken, getToken } from "../utils/tokenUtils";
export default {
  data() {
    // 修改密码限制条件
    var validateUser = (rule, value, callback) => {
      if (value == "") {
        callback(new Error("请输入密码!"));
      } else {
        callback();
      }
    };
    var validatePass1 = (rule, value, callback) => {
      console.log(value);
      if (value == "") {
        callback(new Error("请输入密码!"));
      } else if (value.length > 11) {
        callback(new Error("密码长度不能长于11个字符!"));
      } else {
        callback();
      }
    };
    var validateAno = (rule, value, callback) => {
      console.log(value);
      if (value == "") {
        callback(new Error("请再次输入密码!"));
      } else if (value !== this.numberValidateForm.newpassword) {
        callback(new Error("两次输入的密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      v : true,
      numberValidateForm: {
        oldpassword: "",
        newpassword: "",
        passwordcheck: "",
      },
      rules: {
        oldpassword: [{ validator: validateUser, trigger: "blur" }],
        newpassword: [{ validator: validatePass1, trigger: "blur" }],
        passwordcheck: [{ validator: validateAno, trigger: "blur" }],
      },
      // 修改密码的弹出框
      modyVisible: false,
      // 用户数据
      userObj: {
        adminName: "未登录",
      },
      headIcon: "http://yanjiojio.fun/HeadIcon/iconyou.jpg",
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
              label: "最新发布",
            },
            {
              path: "/manage/newsissue",
              label: "晚点新闻",
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
            // {
            //   path: "/manage/managerinfo",
            //   label: "信息管理",
            // },
          ],
        },
      ],
    };
  },
  created() {
    this.getUserData();
  },
  methods: {
    // 提交修改密码
    submitForm(formName) {
      console.log(this.numberValidateForm);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            username: this.userObj.adminId,
            password: this.numberValidateForm.oldpassword,
          };
          console.log(data);
          // 发送网络请求
          axios
            .post(
              "/login?username=" + data.username + "&password=" + data.password
            )
            .then((response) => {
              console.log(response);
              let admindata = {
                adminId: this.userObj.adminId,
                adminPwd: this.numberValidateForm.newpassword,
                adminName: this.userObj.adminName,
                permission: this.userObj.permission,
              };
              // 验证用户密码是否正确
              if (response.data.code == 200) {
                // 验证成功 修改密码
                axios.post("/admin/update", admindata)
                .then((response) => {
                  console.log(response.data.code);
                  if(response.data.code=200){
                    this.$alert("请重新登陆!", "修改成功！", {
                      type: "success",
                      confirmButtonText: "确定",
                      showClose: false,
                      center: true,
                      callback: (action) => {
                        delToken();
                        this.$router.push("/");
                      },
                    });
                  }
                  else{
                    this.$message({
                      message: "系统错误，修改失败",
                      type: "error",
                    });
                  }
                    
                });
              } else {
                // 验证失败 提示一些修改失败相关的信息
                this.$message({
                  message: "当前密码错误，修改失败",
                  type: "error",
                });
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 重置密码
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    // 重新登陆提示
    reLogin() {
      this.$alert("你还没有登陆，请先登陆!", "未登录", {
        type: "info",
        confirmButtonText: "确定",
        showClose: false,
        center: true,
        callback: (action) => {
          this.$router.push("/");
        },
      });
    },
    // 获取并渲染用户信息
    getUserData() {
      let userName = getToken();
      if (!userName) {
        this.reLogin();
      } else {
        // 发送网络请求
        axios.get("/admin/getById/" + userName).then((response) => {
          // console.log(response);
          // 判断用户登录是否成功
          if (response.data.code == 200) {
            this.userObj = response.data.data;
            // console.log(this.userObj);
            if(this.userObj.permission == '2')
              this.asideMenu.pop()

            this.$message({
              message: "欢迎回来，管理员" + this.userObj.adminName,
              type: "success",
            });
          } else {
            // 登录失败 提示一些登录失败相关的信息
            this.$notify.error({
              title: "数据获取失败",
              message: "请重新登陆",
            });
          }
        });
      }
      // console.log(userName);
    },
    // 登出
    logOut() {
      this.$confirm("你确定要退出登陆吗?", "退出登陆", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          delToken();
          this.$router.push("/");
        })
        .catch(() => {});
    },

    // 更改密码

    // 点击下拉菜单里面的按钮
    handleCommand(command) {
      if (command == "a") {
        this.modyVisible = true;
      } else if (command == "b") {
        // console.log(command);
        this.logOut();
      }
    },

    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    reFreshHome() {
      history.go(0);
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