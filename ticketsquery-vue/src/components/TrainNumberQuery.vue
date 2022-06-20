<template>
  <div>
    <Manage></Manage>
    <div class="pageStyle">
      <el-row>
        <el-col :span="10">
          <el-input v-model="departureStation" placeholder="请输入起始站"></el-input>
        </el-col>
        <el-col :span="10">
          <el-input v-model="arrivalStation" placeholder="请输入终点站"></el-input>
        </el-col>
        <el-col :span="4">
          <el-button @click="handleBtnClick">按钮</el-button>
        </el-col>
      </el-row>
      <data-tables :data="trainNumbers">
        <el-table-column prop="routertrainId" label="车次" width="180">
        </el-table-column>
        <el-table-column prop="routertrainType" label="车次类型" width="180">
        </el-table-column>
        <el-table-column prop="departureStationId" label="起始站" width="180">
        </el-table-column>
        <el-table-column prop="arrivalStationId" label="终点站" width="180">
        </el-table-column>
        <el-table-column prop="departureTime" label="起始站出发时间" width="180">
        </el-table-column>
        <el-table-column prop="arrivalTime" label="终点站到站时间" width="180">
        </el-table-column>
      </data-tables>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import Manage from './Manage'

export default {
  components: {
    Manage
  },
  data () {
    return {
      departureStation: '',
      arrivalStation: '',
      trainNumbers: []
    }
  },
  methods: {
    handleBtnClick () {
      axios.get('http://127.0.0.1:8888/train_number/' + this.departureStation + '/' + this.arrivalStation).then((response) => {
        this.trainNumbers = response.data.data
      })
    }
  }
}
</script>
<style>
.pageStyle{
  width: 1250px;
  margin-left: 251px;
  margin-top: -500px;
}
.el-row{
height: 300px;
}
</style>
