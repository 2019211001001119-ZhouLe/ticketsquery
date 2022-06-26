<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">站点管理</p>
      <el-card class="box-card">
        <el-row>
          <el-col :span="10">
            <el-input v-model="keyword" placeholder="请输入车次"></el-input>
          </el-col>
          <el-col :span="14">
            <el-button @click="handleBtnClick(keyword)">按钮</el-button>
          </el-col>
        </el-row>
        <el-table :data="details">
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
          <el-table-column prop="laterTime" label="晚点时间" width="180">
          </el-table-column>
          <el-table-column :reserve-selection="true" label="操作">
            <template slot-scope="scope">
              <el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle></el-button>
              <!-- <el-popconfirm title="确认删除这行吗?" @confirm="deletetrainNumber(scope.$index, scope.row)">
                <el-button slot="reference" icon="el-icon-delete" circle>
                </el-button>
              </el-popconfirm> -->
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
    <el-dialog title="设置晚点" :visible.sync="dialogVisible">
      <el-form :modle="updDetail">
        <!-- <el-form-item label="站序">
          <el-input v-model="updDetail.routerdetailId" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="车站ID">
          <el-input v-model="updDetail.trainstationId"></el-input>
        </el-form-item>
        <el-form-item label="车次号">
          <el-input v-model="updDetail.routertrainId"></el-input>
        </el-form-item>
        <el-form-item label="到站时间">
          <el-input v-model="updDetail.arrivalTime"></el-input>
        </el-form-item>
        <el-form-item label="离站时间">
          <el-input v-model="updDetail.departureTime"></el-input>
        </el-form-item> -->
        <el-form-item label="晚点时间">
          <el-time-picker value-format="HH:mm:ss" v-model="updDetail.laterTime" :picker-options="{
            selectableRange: '00:00:00 - 23:59:59'
          }" placeholder="选择时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleEditSaveClick(updDetail)">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      trainNumber: '',
      keyword: '',
      details: [],
      updDetail: [],
      dialogVisible: false
    };
  },
  methods: {
    handleBtnClick(keyword) {
      this.trainNumber = keyword
      this.queryKeyword()
    },
    queryAll() {
      axios.get("http://127.0.0.1:8888/details").then(res => {
        this.details = res.data.data
      })
    },
    queryKeyword() {
      axios.get("http://127.0.0.1:8888/details/query/" + this.trainNumber).then((response) => {
        console.log(this.trainNumber)
        this.details = response.data.data
      })
    },
    updateDetails(detail) {
      axios.put('http://127.0.0.1:8888/details/' + detail.routerdetailId + '/' + detail.routertrainId, detail).then(res => {
        if (res.data.code == "200")
          console.log("success")
        this.queryAll
      })
    },
    handleEditClick(index, row) {
      this.dialogVisible = true
      this.updDetail = row
    },
    handleEditSaveClick(updDetail) {
      this.updateDetails(updDetail)
      this.dialogVisible = false
    }
  },
  mounted() {
    var that = this
    this.handleBtnClick(that.$route.query.routertrainId)
  }
};
</script>
