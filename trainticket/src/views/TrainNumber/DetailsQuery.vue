<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">站点管理</p>
      <el-card class="box-card">
        <el-row>
          <el-col :span="10">
            <el-input v-model="trainNumber" placeholder="请输入车次"></el-input>
          </el-col>
          <el-col :span="14">
            <el-button @click="handleBtnClick">按钮</el-button>
          </el-col>
        </el-row>
        <data-tables :data="details">
          <el-table-column prop="routerdetailId" label="站序" width="180">
          </el-table-column>
          <el-table-column prop="trainstationId" label="车次类型" width="180">
          </el-table-column>
          <el-table-column prop="routertrainId" label="车次" width="180">
          </el-table-column>
          <el-table-column prop="arrivalTime" label="到站时间" width="180">
          </el-table-column>
          <el-table-column prop="departureTime" label="出发时间" width="180">
          </el-table-column>
        </data-tables>
      </el-card>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      trainNumber: "",
      details: [],
    };
  },
  methods: {
    handleBtnClick() {
      axios
        .get("http://127.0.0.1:8888/details/query/" + this.trainNumber)
        .then((response) => {
          this.details = response.data.data;
        });
    },
  },
};
</script>
