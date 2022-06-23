<template>
  <div class="pageStyle">
    <p class="titleCap">车次管理</p>
    <el-card class="box-card">
      <el-main>
        <el-row>
          <el-col :span="10">
            <el-input
              v-model="keyword"
              placeholder="请输入起始站或终点站的ID"
            ></el-input>
          </el-col>
          <el-col :span="1">
            <el-button
              icon="el-icon-search"
              circle
              @click="handleBtnClick(keyword)"
            ></el-button>
          </el-col>
          <el-col :span="1">
            <el-button
              @click="flush($event)"
              style="margin-left: 0px"
              icon="el-icon-refresh"
              circle
            ></el-button>
          </el-col>
          <el-col :span="12" class="tableButton">
            <el-button
              type="danger"
              plain
              :disabled="noAnySelection"
              @click="batchDelete(ids)"
              ><span class="el-icon-close"></span> 批量删除</el-button
            >
            <el-button type="primary" @click="handleAddClick()"
              ><span class="el-icon-plus"></span> 添加车次</el-button
            >
          </el-col>
        </el-row>
        <el-table
          ref="multipleTable"
          :data="trainNumbers"
          tooltip-effect="dark"
          style="width: 100%"
          stripe
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="65"></el-table-column>
          <el-table-column prop="routertrainId" label="车次" width="100">
          </el-table-column>
          <el-table-column prop="routertrainType" label="车次类型" width="140">
          </el-table-column>
          <el-table-column prop="departureStationId" label="起始站" width="140">
          </el-table-column>
          <el-table-column prop="arrivalStationId" label="终点站" width="140">
          </el-table-column>
          <el-table-column
            prop="departureTime"
            label="起始站出发时间"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="arrivalTime"
            label="终点站到站时间"
            width="180"
          >
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleEditClick(scope.$index, scope.row)"
                icon="el-icon-edit"
                circle
              >
              </el-button>
              <el-popconfirm
                title="确认删除这行吗?"
                @confirm="deleteTrainNumber(scope.row)"
              >
                <el-button slot="reference" icon="el-icon-delete" circle>
                </el-button>
              </el-popconfirm>
              <el-button @click="checkDetails(scope.$index, scope.row)" circle
                >详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页栏 -->
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="4"
            layout="prev, pager, next, jumper"
            :total="totalStation"
            class="departPaging"
          >
          </el-pagination>
        </div>
      </el-main>
      <el-dialog title="编辑列车" :visible.sync="dialogVisible">
        <el-form :modle="trainNumbers">
          <el-form-item label="车次">
            <el-input
              v-model="trainNumbers.routertrainId"
              :disabled="true"
            ></el-input>
          </el-form-item>
          <el-form-item label="车次类型">
            <el-input v-model="trainNumbers.routertrainType"></el-input>
          </el-form-item>
          <el-form-item label="起始站">
            <el-input v-model="trainNumbers.departureStationId"></el-input>
          </el-form-item>
          <el-form-item label="终点站">
            <el-input v-model="trainNumbers.arrivalStationId"></el-input>
          </el-form-item>
          <el-form-item label="起始站出发时间">
            <el-date-picker
              v-model="trainNumbers.departureTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="终点站到站时间">
            <el-date-picker
              v-model="trainNumbers.arrivalTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleEditSaveClick(train)">保存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <el-dialog title="添加列车" :visible.sync="addVisible">
        <el-form :modle="newTrainNumbers">
          <el-form-item label="车次">
            <el-input v-model="trainNumbers.routertrainId"></el-input>
          </el-form-item>
          <el-form-item label="车次类型">
            <el-input v-model="trainNumbers.routertrainType"></el-input>
          </el-form-item>
          <el-form-item label="起始站">
            <el-input v-model="trainNumbers.departureStationId"></el-input>
          </el-form-item>
          <el-form-item label="终点站">
            <el-input v-model="trainNumbers.arrivalStationId"></el-input>
          </el-form-item>
          <el-form-item label="起始站出发时间">
            <el-date-picker
              v-model="trainNumbers.departureTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="终点站到站时间">
            <el-date-picker
              v-model="trainNumbers.arrivalTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleAddSaveClick(newTrainNumber)"
              >保存</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>
<script>
import axios from "axios";
import Bus from "../../utils/bus.js";
export default {
  data() {
    return {
      keyword: "",
      trainNumbers: [],
      newTrainNumbers: [],
      dialogVisible: false,
      deleteVisible: false,
      addVisible: false,
      // 批量删除是否可用
      noAnySelection: true,
      // 总数据条数
      totalStation: 0,
      // 分页栏中当前页
      currentPage: 1,
      // 要删除的车次表
      ids: [],
      // 选中的数据
      multipleSelection: [],
    };
  },
  methods: {
    // 分页查询车次数据
    queryAll() {
      axios
        .get(
          "http://127.0.0.1:8888/train_number_page?current=" +
            this.currentPage +
            "&size=4"
        )
        .then((response) => {
          this.trainNumbers = response.data.data.records;
          this.totalStation = response.data.data.total;
        });
    },

    saveTrain(trainNumbers) {
      axios
        .put(
          "http://127.0.0.1:8888/train_number/" + trainNumbers["routertrainId"],
          trainNumbers
        )
        .then((response) => {
          console.log(response);
          this.queryAll();
        });
    },

    // 单个删除某个车次
    deleteTrainNumber(row) {
      console.log(row);
      axios
        .delete("http://127.0.0.1:8888/train_number/" + row["routertrainId"])
        .then((response) => {
          console.log(response);
          this.queryAll();
          if(response.data.code==200){
            this.$message({
                type: "success",
                message: row.routertrainId+"车次删除成功!",
              });
          }else{
            this.$message({
                type: "error",
                message: row.routertrainId+"车次删除失败!",
              });
          }
        });
    },

    // 增加车次信息
    addTrain(trainNumbers) {
      axios
        .post("http://127.0.0.1:8888/train_number", trainNumbers)
        .then((response) => {
          console.log(response);
          this.queryAll();
        });
    },

    // 搜索栏搜索车次信息
    handleBtnClick(keyword) {
      console.log(keyword);
      axios
        .get("http://127.0.0.1:8888/train_number/" + keyword)
        .then((response) => {
          this.trainNumbers = response.data.data;
        });
    },

    handleEditClick(index, row) {
      console.log(index);
      this.trainNumbers = row;
      this.dialogVisible = true;
    },

    handleEditSaveClick(trainNumber) {
      this.saveTrainNumber(trainNumber);
      this.dialogVisible = false;
    },

    // 点击添加车次按钮，显示出添加框
    handleAddClick() {
      this.addVisible = true;
    },


    handleAddSaveClick(trainNumber) {
      this.addrainNumber(trainNumber);
      this.addVisible = false;
    },

    checkDetails(index, row) {
      console.log(index);
      Bus.$emit("tn", row.routertrainId);
      this.$router.push({
        path: "/manage/detailsquery",
        params: {
          routertrainId: row.routertrainId,
        },
      });
    },

    // 刷新所有车站信息
    flush($event) {
      console.log($event);
      let target = $event.target;
      if (target.nodeName == "I") {
        target = $event.target.parentNode;
      }
      target.blur();
      this.queryAll();
      this.input = "";
    },

    // 批量删除车次
    batchDelete(rows) {
      console.log(rows);
      this.$confirm("是否确认删除这" + rows.length + "条数据?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$confirm("请再次确认是否删除?", "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
          })
            .then(() => {
              // 批量删除
              rows.forEach(element => {
                this.deleteTrainNumber(element)
              });
              // 清空multipleSelection
              this.multipleSelection = [];
              this.ids=[];
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除",
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 当选中项发生改变时
    handleSelectionChange(selection) {
      console.log(selection);
      this.multipleSelection = selection;
      this.ids = selection;
      this.hasAnySelection();
    },
    // 查看是否有选中的信息
    hasAnySelection() {
      if (this.multipleSelection.length !== 0) {
        this.noAnySelection = false;
      } else {
        this.noAnySelection = true;
      }
    },
    // 当pageSize改变时
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 当当前页发生改变时
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.queryAll();
    },
    // 设置表格内每一行的样式，呈斑马条纹样式
    tableRowClassName({ row, rowIndex }) {
      console.log(rowIndex);
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
  },
  mounted() {
    this.queryAll();
  },
};
 
</script>
<style>
.tableButton button {
  float: right;
  margin: 0 20px;
}
.departPaging {
  margin: 20px 0;
  text-align: center;
}
</style>