<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">车次管理</p>
      <el-card class="box-card">
        <!-- 使用面包屑来进行跳转处理 -->
        <el-breadcrumb
          separator-class="el-icon-arrow-right"
          style="margin-bottom: 20px"
        >
          <el-breadcrumb-item
            ><a href="/manage/traininfo">车次管理</a></el-breadcrumb-item
          >
          <el-breadcrumb-item
            ><a href="/manage/traininfo"
              >{{ trainID }}车次</a
            ></el-breadcrumb-item
          >
        </el-breadcrumb>
        <!-- 表格内容 -->
        <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          stripe
          :row-class-name="tableRowClassName"
          :default-sort = "{prop: 'routerdetailStatus', order: 'ascending'}"
        >
          <el-table-column
            prop="routerdetailStatus"
            label="站序"
            width="180"
            type="index"
          >
          <template slot-scope="scope">
              <span v-if="scope.row.visible">{{
                scope.row.routerdetailId
              }}</span>
              <el-input
                v-else
                v-model="scope.row.routerdetailId"
                disabled
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="trainstationId" label="经过站" width="180">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{
                scope.row.trainstationId
              }}</span>
              <el-input
                v-else
                v-model="scope.row.trainstationId"
                disabled
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="routertrainId" label="车次" width="180">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{
                scope.row.routertrainId
              }}</span>
              <el-input
                v-else
                v-model="scope.row.routertrainId"
                disabled
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="arrivalTime" label="到站时间" width="200">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.arrivalTime }}</span>
              <el-date-picker
                v-else
                v-model="scope.row.arrivalTime"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
              >
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column prop="departureTime" label="出发时间" width="200">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{
                scope.row.departureTime
              }}</span>
              <el-date-picker
                v-else
                v-model="scope.row.departureTime"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
              >
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="操作" class="operateButton">
            <template slot-scope="scope">
              <el-button
                @click="handleEdit(scope.$index, scope.row)"
                v-if="scope.row.visible"
                icon="el-icon-edit"
                circle
              ></el-button>
              <el-button
                size="mini"
                @click="handleFinish(scope.$index, scope.row)"
                v-if="!scope.row.visible"
              >
                完成</el-button
              >
              <el-button
                size="mini"
                @click="handleCancel(scope.$index, scope.row)"
                v-if="!scope.row.visible"
              >
                取消</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  // 生命周期钩子函数
  created() {
    this.trainID = this.$route.query.routertrainId;
    this.hasTrainId();
    this.getDataByID();
  },
  data() {
    return {
      // 表格数据
      tableData: [],
      // 车次号码
      trainID: "",
      // 添加弹窗
      addVisible: false,
      // 添加表
      newTrainNumbers:{
        trainstationId: '',
        routertrainId: '',
        arrivalTime: '',
        departureTime: '',
      }
    };
  },
  methods: {
    // 判断当前是否传输过来了车次
    hasTrainId() {
      if (this.trainID == "") {
        this.$router.push("/manage/traininfo");
      }
    },
    // 根据车次信息获取数据
    getDataByID() {
      console.log(this.trainID);
      axios.get("/details/" + this.trainID).then((response) => {
        console.log(response);
        this.tableData = [];
        response.data.data.forEach((element) => {
          element.visible = true;
          this.tableData.push(element);
        });
      });
    },

    // 点击取消按钮，显示编辑按钮，隐藏完成按钮和输入框，重新渲染数据
    handleCancel(index, row) {
      row.visible = !row.visible;
      // 重新获取数据
      // this.querytrains();
      this.$message({
        type: "info",
        message: "取消修改!",
      });
    },
    // 点击完成按钮，显示编辑按钮，隐藏完成按钮和输入框，发送网络请求
    handleFinish(index, row) {
      row.visible = !row.visible;
      row.arrivalTime=this.setTimeToSec(row.arrivalTime)
      row.departureTime=this.setTimeToSec(row.departureTime)
      console.log(row);

      let data={
        trainstationId: row.trainstationId,
        arrivalTime: row.arrivalTime,
        departureTime: row.departureTime,
        routerdetailStatus: row.routerdetailStatus,
        routerdetailId: row.routerdetailId,
        routertrainId: row.routertrainId,
      }
      axios
        .put("/details/", data)
        .then((response) => {
          console.log(response);
          if (response.data.code == 200) {
            this.$notify({
              title: "成功",
              message: "修改成功",
              type: "success",
              duration: 1500,
            });
          } else {
            this.$notify({
              title: "失败",
              message: "修改失败",
              type: "error",
              duration: 1500,
            });
          }
          this.getDataByID();
        });
    },
    // 点击编辑按钮，隐藏编辑按钮，显示完成按钮和输入框
    handleEdit(index, row) {
      console.log(row);
      row.visible = !row.visible;
    },
    // 时间转换器
    setTimeToSec(thatdate){
      let date=new Date(thatdate)
      var year = date.getFullYear(); 
      var month = date.getMonth() + 1; 
      var day = date.getDate(); 
      var hour = date.getHours(); 
      var minute = date.getMinutes(); 
      var second = date.getSeconds(); 
      return year + "-" + month + "-" + day+ " " +hour+ ":" +minute+ ":" +second;
    },
    // 设置表格内每一行的样式，呈斑马条纹样式
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
  },
};
</script>

<style scoped>
.tableTitle span {
  font-size: 14px;
}
.tableTitle button {
  float: right;
  margin: 0 20px;
}
</style>