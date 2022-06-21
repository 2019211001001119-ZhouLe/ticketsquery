<template>
  <div class="pageStyle">
    <p class="titleCap">车次管理</p>
    <el-card class="box-card">
      <el-main>
        <el-row>
          <el-col :span="16">
            <el-input
              v-model="keyword"
              placeholder="请输入车次关键字"
            ></el-input>
          </el-col>
          <el-col :span="4">
            <el-button @click="handleBtnClick()">搜索</el-button>
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
          <el-table-column>
            <template slot="header">
              <el-button
                @click="handleAddClick()"
                icon="el-icon-plus"
                circle
              ></el-button>
            </template>
            <template slot-scope="scope">
              <el-button
                @click="handleEditClick(scope.$index, scope.row)"
                icon="el-icon-edit"
                circle
              >
              </el-button>
              <el-popconfirm
                title="确认删除这行吗?"
                @confirm="deleteTrainNumber(scope.$index, scope.row)"
              >
                <el-button slot="reference" icon="el-icon-delete" circle>
                </el-button>
              </el-popconfirm>
              <el-button @click="checkDetails(scope.$index, scope.row)" circle
                >详情</el-button
              >
            </template>
          </el-table-column>
        </data-tables>
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
    };
  },
  methods: {
    queryAll() {
      axios.get("http://127.0.0.1:8888/train_number/").then((response) => {
        this.trainNumbers = response.data.data;
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

    deleteTrain(index, row) {
      console.log(index);
      axios
        .delete("http://127.0.0.1:8888/train_number/" + row["routertrainId"])
        .then((response) => {
          console.log(response);
          this.queryAll();
        });
    },

    addTrain(trainNumbers) {
      axios
        .post("http://127.0.0.1:8888/train_number", trainNumbers)
        .then((response) => {
          console.log(response);
          this.queryAll();
        });
    },

    handleBtnClick() {
      console.log(this.trainNumbers);
      axios
        .get("http://127.0.0.1:8888/train_number/" + this.trainNumbers)
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
  },
  mounted() {
    this.queryAll();
  },
};
</script>