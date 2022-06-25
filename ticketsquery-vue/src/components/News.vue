<template>
  <div style="margin: 50px 370px">
    <el-row>
      <span style="color: #B1B1B1;font-size: 20px">动态发布</span>
    </el-row>
    <div class="container">
      <br>
      <el-row>
        <el-col :span="1">&nbsp;</el-col>
        <el-col :span="3"><span style="line-height: 40px;font-size: 17px;color: lightgray">新闻标题</span></el-col>
        <el-col :span="10">
          <el-input v-model="title" placeholder="学生票优惠资质绑定"></el-input>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="1">&nbsp;</el-col>
        <el-col :span="3"><span style="line-height: 40px;font-size: 17px;color: lightgray">URL地址</span></el-col>
        <el-col :span="10">
          <el-input v-model="url" placeholder="http://www..."></el-input>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="1">&nbsp;</el-col>
        <el-col :span="5">
          <el-button type="info" @click="reset($event)" plain>重置</el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" :loading="isLoading" @click="publish($event)">发布</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

import axios from 'axios'

<script>
import axios from "axios";

export default {
  data() {
    return {
      title: '',
      url: '',
      isLoading: false
    }
  },
  methods: {
    // 重置
    reset($event) {
      let target = $event.target
      if (target.nodeName == 'I' || target.nodeName == 'SPAN') {
        target = $event.target.parentNode
        target.blur()
      }
      this.title = ''
      this.url = ''
    },
    // 发布
    publish($event) {
      this.isLoading = true
      let target = $event.target
      if (target.nodeName == 'I' || target.nodeName == 'SPAN') {
        target = $event.target.parentNode
        target.blur()
      }
      let data = {
        newsTitle: this.title,
        newsUrl: this.url
      }
      if (this.title && this.url) {
        axios.post('news/add', data).then((res) => {
          if (res.data.code == '200') {
            this.isLoading = false
            this.title = ''
            this.url = ''
            this.$message({
              message: '发布成功',
              type: 'success',
              center: 'true',
              duration: 1500
            })
          } else if (res.data.code == '400'){
            this.isLoading = false
            this.$message({
              message: '请检查网络配置',
              type: 'error',
              center: 'true',
              duration: 1500
            })
          }
        })
      } else {
        this.isLoading = false
        this.title = ''
        this.url = ''
        this.$message({
          message: '请输入完整的发布内容',
          type: 'warning',
          center: 'true',
          duration: 1500
        })
      }
    }
  }
}
</script>

<style>
.container {
  width: 780px;
  height: 200px;
  margin: 10px auto;
  background-color: #ffffff;
}

</style>
