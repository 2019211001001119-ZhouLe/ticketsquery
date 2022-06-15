<template>
  <el-container id="app">
    <el-header>火车后台管理</el-header>
    <el-container>
      <el-aside>
        <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="/">
                首页
              </el-menu-item>

              <el-menu-item index="/test">
                test
              </el-menu-item>
              <el-menu-item index="/train">
                列车管理
              </el-menu-item>
              <el-menu-item index="/traintion">
                车站管理
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
            <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'App',
  methods: {
    handleOpen () {
      console.log(' open ')
    },
    handleClose () {
      console.log(' close ')
    }
  }
}
</script>

<style>
#app {
  text-align: center;
}

a {
  text-decoration: none;
}
</style>
