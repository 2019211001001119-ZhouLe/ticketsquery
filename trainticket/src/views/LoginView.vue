<template>
  <div id="app">
    <div id="main">
      <el-row class="loginBox">
        <el-col :span="6" :offset="9">
          <el-container>
            <el-header class="proHearder">
              <h1>后台管理系统</h1>
            </el-header>
            <el-main class="proMain">
              <div class="grid-content titleH">
                <h1>用户登陆</h1>
                <el-form :model="numberValidateForm" ref="numberValidateForm" class="demo-ruleForm" :rules="rules">
                  <el-form-item prop="username">
                    <el-input type="username" placeholder="用户名" prefix-icon="el-icon-s-custom"
                      v-model="numberValidateForm.username" autocomplete="off">
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input type="password" placeholder="密码" prefix-icon="el-icon-lock"
                      v-model="numberValidateForm.password" show-password autocomplete="off">
                    </el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitForm('numberValidateForm')">登陆<i class="el-icon-right"></i>
                    </el-button>
                  </el-form-item>
                </el-form>
              </div>
            </el-main>
            <el-footer class="authorF">design by 第七组.</el-footer>
          </el-container>
        </el-col>
      </el-row>
    </div>
    <div id="bgimage"></div>
  </div>
</template>

<script>
import axios from 'axios';
import { setToken } from '../utils/tokenUtils'
export default {
  data() {
    var validateUser = (rule, value, callback) => {
      if (value == "") {
        callback(new Error("请输入账号!"));
      } else {
        callback();
      }
    };
    var validatePass1 = (rule, value, callback) => {
      console.log(value);
      if (value == "") {
        callback(new Error("请输入密码!"));
      } else {
        callback();
      }
    };
    return {
      numberValidateForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [{ validator: validateUser, trigger: "blur" }],
        password: [{ validator: validatePass1, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 设置对应的参数
          let data = {
            // 用户名
            username: this.numberValidateForm.username,
            // 密码
            password: this.numberValidateForm.password
          }
          // 发送网络请求
          axios.post("/login?username="+data.username+"&password="+data.password).then((response) => {
            console.log(response);
            // 判断用户登录是否成功
            if (response.data.code == 200) {
              // 登录成功 保存token
              // setToken()
              setToken(response.data.data.adminId)
              // 跳转到首页
              this.$router.push('/manage/home')
            } else {
              // 登录失败 提示一些登录失败相关的信息
              this.$notify.error({
                title: "登陆失败",
                message: response.data.msg,
              });
              this.numberValidateForm.username = ""
              this.numberValidateForm.password = ""
            }
          })

          // 拿到用户的token进行保存
          // 跳转到首页
        } else {
          console.log("error!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
.authorF {
  text-align: center;
  font-size: 13px;
  color: white;
  margin-top: 50px;
}

.proMain {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 4px;
  border: solid;
  border-width: thin;
  border-color: #d3dce6;
}

.proHearder {
  text-align: center;
  line-height: 60px;
}

.loginBox h1 {
  color: white;
  font-weight: 300 !important;
  font-size: 24px;
}

.loginBox {
  margin: 160px 0;
}

#app {
  overflow: hidden;
}

#bgimage {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-image: url("http://img.mp.sohu.com/upload/20170702/647f0ae6733745bfaf2b2aaa2216cef7_th.png");
  filter: blur(30px);
  transform: scale(1.1);
  text-align: center;
  overflow: hidden;
  position: fixed;
  top: 0;
  left: 0;
  z-index: -100;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 4px;
}

.bg-purple {
  background: #d3dce6;
}

.grid-content {
  border-radius: 4px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.titleH h1 {
  color: white;
  font-weight: 300 !important;
  font-size: 18px;
  margin-bottom: 25px;
}

.titleH .el-button {
  float: right;
}
</style>