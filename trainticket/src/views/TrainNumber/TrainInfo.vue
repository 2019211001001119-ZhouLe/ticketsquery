http://127.0.0.1:8888/admin/getById/qcjn472619
<template>
  <div class="pageStyle">
    <p class="titleCap">车次管理</p>
    <el-card class="box-card">
      <el-main>
        <el-row>
          <el-col :span="10">
            <el-input v-model="keyword" placeholder="请输入车次号查询"></el-input>
          </el-col>
          <el-col :span="1">
            <el-button icon="el-icon-search" circle @click="handleBtnClick(keyword)"></el-button>
          </el-col>
          <el-col :span="1">
            <el-button @click="flush($event)" style="margin-left: 0px" icon="el-icon-refresh" circle></el-button>
          </el-col>
          <el-col :span="12" class="tableButton">
            <el-button type="danger" plain :disabled="noAnySelection" @click="batchDelete(ids)"><span
                class="el-icon-close"></span> 批量删除</el-button>
            <el-button type="primary" @click="handleAddClick(); choosed = 0"><span class="el-icon-plus"></span> 添加车次
            </el-button>
          </el-col>
        </el-row>
        <!-- 数据表 -->
        <el-table ref="multipleTable" :data="trainNumbers" tooltip-effect="dark" style="width: 100%" stripe
          :row-class-name="tableRowClassName" :row-key="rowKey" @selection-change="handleSelectionChange">
          <el-table-column :reserve-selection="true" type="selection" width="65"></el-table-column>
          <el-table-column :reserve-selection="true" prop="routertrainId" label="车次" width="100">
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="trainId" label="火车名称" width="140">
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="departureStationId" label="起始站" width="140">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p style="text-align:center">{{ scope.row.departureStationId }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ stations.find(item => item.value == scope.row.departureStationId).label }}
                  </el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="arrivalStationId" label="终点站" width="140">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p style="text-align:center">{{ scope.row.arrivalStationId }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ stations.find(item => item.value == scope.row.arrivalStationId).label }}
                  </el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="departureTime" label="起始站出发时间" width="180">
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="arrivalTime" label="终点站到站时间" width="180">
          </el-table-column>
          <el-table-column :reserve-selection="true" label="操作">
            <template slot-scope="scope">
              <el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle>
              </el-button>
              <el-popconfirm title="确认删除这行吗?" @confirm="deleteTrainNumber(scope.row)">
                <el-button slot="reference" icon="el-icon-delete" type="danger" circle>
                </el-button>
              </el-popconfirm>
              <el-button @click="checkDetails(scope.$index, scope.row)" circle icon="el-icon-more" type="info">
              </el-button>
              <el-button @click="lateClick(scope.row)" circle icon="el-icon-time" type="warning"></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页栏 -->
        <div class="block">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page.sync="currentPage" :page-size="pagesize" layout="prev, pager, next, jumper"
            :total="totalStation" class="departPaging">
          </el-pagination>
        </div>
      </el-main>
      <!-- 弹出窗编辑列车 -->
      <el-dialog title="编辑车次" :visible.sync="dialogVisible">
        <el-form :model="editTrainNumbers">
          <el-form-item label="车次">
            <el-input v-model="editTrainNumbers.routertrainId" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="火车名称">
            <el-input v-model="editTrainNumbers.trainId" disabled></el-input>
          </el-form-item>
          <el-form-item label="起始站">
            <el-select v-model="editTrainNumbers.departureStationId" disabled>
              <el-option v-for="item in stations" :key="item.value" :label="item.label" :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="终点站">
            <el-select v-model="editTrainNumbers.arrivalStationId" disabled>
              <el-option v-for="item in stations" :key="item.value" :label="item.label" :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="起始站出发时间">
            <el-date-picker v-model="editTrainNumbers.departureTime" type="datetime" placeholder="选择日期时间" align="right">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="终点站到站时间">
            <el-date-picker v-model="editTrainNumbers.arrivalTime" type="datetime" placeholder="选择日期时间" align="right">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleEditSaveClick(editTrainNumbers)">保存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- 弹出窗添加列车 -->
      <el-dialog title="添加车次" :visible.sync="addVisible" fullscreen>
        <el-steps :active="active" finish-status="success">
          <el-step title="步骤 1"></el-step>
          <el-step title="步骤 2"></el-step>
          <el-step title="步骤 3"></el-step>
        </el-steps>
        <el-form :model="newTrainNumbers" :rules="rules" ref="newTrainNumbers" v-if="choosed == 0">
          <el-form-item label="车次" label-width="80px" prop="routertrainId">
            <el-input v-model="newTrainNumbers.routertrainId"></el-input>
          </el-form-item>
          <el-form-item label="火车名称" label-width="80px" prop="trainId">
            <el-autocomplete class="inline-input" v-model="newTrainNumbers.trainId" :fetch-suggestions="querySearch"
              placeholder="请输入内容" @select="handleSelect"></el-autocomplete>
          </el-form-item>
          <el-form-item label="火车类型" label-width="80px" prop="routertrainType">
            <el-input v-model="newTrainNumbers.routertrainType" disabled></el-input>
          </el-form-item>
          <el-form-item label="起始站" label-width="80px" prop="departureStationId">
            <el-select v-model="newTrainNumbers.departureStationId">
              <el-option v-for="item in stations" :key="item.value" :label="item.label" :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="终点站" label-width="80px" prop="arrivalStationId">
            <el-select v-model="newTrainNumbers.arrivalStationId">
              <el-option v-for="item in stations" :key="item.value" :label="item.label" :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="起始站出发时间" prop="departureTime">
            <el-date-picker v-model="newTrainNumbers.departureTime" type="datetime" placeholder="选择日期时间" align="right">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="终点站到站时间" prop="arrivalTime">
            <el-date-picker v-model="newTrainNumbers.arrivalTime" type="datetime" placeholder="选择日期时间" align="right">
            </el-date-picker>
          </el-form-item>
          <el-row>
            <el-col :span="2">
              <el-form-item>
              </el-form-item>
            </el-col>
            <el-col :span="2" :offset="19">
              <el-form-item>
                <el-button @click="next()">下一步</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-form v-if="choosed >= 1" label-width="80px" :model="detail" :rules="detailRules" ref="detail">
          <el-form-item label="站序" prop="routerdetailId">
            <el-input v-model="detail.routerdetailId" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="经过站" prop="trainstationId">
            <el-select v-model="detail.trainstationId">
              <el-option v-for="item in stations" :key="item.value" :label="item.label" :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="车次号" prop="routertrainId">
            <el-input v-model="detail.routertrainId" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="到站时间" prop="arrivalTime">
            <el-date-picker v-model="detail.arrivalTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="离站时间" prop="departureTime">
            <el-date-picker v-model="detail.departureTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-row>
            <el-col :span="2">
              <el-form-item>
                <el-button @click="submitDetailForm()">保存</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="2" :offset="16">
              <el-form-item>
                <el-button @click="detailNext(detail)">下一步</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-dialog>
      <!-- 弹出窗晚点 -->
      <el-dialog title="添加晚点" :visible.sync="lateShow" @close="closeLate()">
        <el-form :model="lateTable" :rules="laterules" ref="lateTable">
          <el-form-item label="车站名称" label-width="80px" prop="latestation">
            <el-autocomplete class="inline-input" v-model="lateTable.latestation" :fetch-suggestions="queryLateSta"
              placeholder="请输入内容" @select="handleLateSelect">
              <template slot-scope="{ item }">
                <div>{{ stations.find(i => i.value == item.value).label }}</div>
                <span>{{ item.value }}</span>
              </template>
            </el-autocomplete>
          </el-form-item>
          <el-form-item label="晚点时间" label-width="80px" prop="latetime">
            <el-time-picker v-model="lateTable.latetime" :picker-options="{
              selectableRange: '00:00:00 - 23:59:59',
            }" placeholder="选择晚点时间">
            </el-time-picker>
          </el-form-item>
          <el-form-item>
            <el-button @click="submitLate('lateTable')">保存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>
<script>
import axios from "axios";
import Bus from "../../utils/bus.js";
import { delToken, getToken } from "../../utils/tokenUtils";
export default {
  data() {
    var validTrain = (rule, value, callback) => {
      console.log("value:")
      console.log(value)
      switch (this.newTrainNumbers.routertrainId.charAt(0)) {
        case 'k':
        case 'K':
          if (value != '快速列车')
            callback(new Error('K车次不能选择该车型'))
          else {
            callback()
            break;
          }
        case 't':
        case 'T':
          if (value != '特快列车')
            callback(new Error('T车次不能选择该车型'))
          else {
            callback()
            break;
          }
        case 'z':
        case 'Z':
          if (value != '特快直达')
            callback(new Error('Z车次不能选择该车型'))
          else {
            callback()
            break;
          }
        case 'g':
        case 'G':
          if (value != '高速动车')
            callback(new Error('G车次不能选择该车型'))
          else {
            callback()
            break;
          }
        default:
          callback();
          break;
      }
    }
    var validStation = (rule, value, callback) => {
      if (value == this.newTrainNumbers.arrivalStationId) {
        callback(new Error('起始站不能与终点站相同'));
      }
      callback();
    }
    var validStation1 = (rule, value, callback) => {
      if (value == this.newTrainNumbers.departureStationId) {
        callback(new Error('起始站不能与终点站相同'));
      }
      callback();
    }
    var validStation2 = (rule, value, callback) => {
      if (this.details.find(item => item.trainstationId == value) != undefined) {
        callback(new Error('该站已填写过'));
      }
      callback();
    }
    var validDate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入起始站出发时间'));
      } else {
        if (value - this.newTrainNumbers.arrivalTime > 0) {
          callback(new Error('起始站出发时间不得大于终点站到站时间'));
        } else if (value - this.newTrainNumbers.arrivalTime == 0) {
          callback(new Error('起始站出发时间和终点站到站时间不能相等'));
        }
        callback();
      }
    }
    var validDate1 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入终点站到站时间'));
      } else {
        if (value - this.newTrainNumbers.departureTime < 0) {
          callback(new Error('终点站到站时间不得小于起始站出发时间'));
        } else if (value - this.newTrainNumbers.departureTime == 0) {
          callback(new Error('起始站出发时间和终点站到站时间不能相等'));
        } else {
          this.$refs.newTrainNumbers.validateField('departureTime');
        }
        callback();
      }
    }
    var validDetailDate = (rule, value, callback) => {
      let tmpRID = parseInt(this.detail.routerdetailId)
      let tmpDetail = this.details[tmpRID - 1]
      var tmpDate = this.stringToDate(tmpDetail.departureTime)
      console.log("valid1:")
      console.log(tmpDate)
      console.log(value)
      let bool = value - tmpDate > 0 ? true : false
      if (value == '' || value == null) {
        callback(new Error('请输入到站时间'));
      } else {
        if (value - this.detail.departureTime > 0) {
          callback(new Error('到站时间不得大于离站时间'));
        } else if (value - this.detail.departureTime == 0) {
          callback(new Error('到站时间和离站时间不能相等'));
        }
        if (bool == false) {
          callback(new Error('到站时间应该大于上一站离站时间'));
        }
        callback();
      }
    }
    var validDetailDate1 = (rule, value, callback) => {
      let tmpDetail = this.details[0]
      var tmpDate = this.stringToDate(tmpDetail.arrivalTime)
      let bool = value - tmpDate < 0 ? true : false
      if (value == '' || value == null) {
        callback(new Error('请输入离站时间'));
      } else {
        if (value - this.detail.arrivalTime < 0) {
          callback(new Error('离站时间不得小于到站时间'));
        } else if (value - this.detail.arrivalTime == 0) {
          callback(new Error('离站时间和到站时间不能相等'));
        }
        if (bool == false) {
          callback(new Error('离站时间应该小于终点站到站时间'));
        }
        callback();
      }
    }
    return {
      rules: {
        routertrainId: [
          { required: true, message: "请输入车次", trigger: "change" },
        ],
        trainId: [
          { required: true, message: "请输入火车名称", trigger: "change" },
        ],
        routertrainType: [
          { required: true, message: "请输入火车类型", trigger: "change" },
          { validator: validTrain, trigger: "change" }
        ],
        departureStationId: [
          { required: true, message: "请输入起始站", trigger: "change" },
          { validator: validStation, trigger: "change" }
        ],
        arrivalStationId: [
          { required: true, message: "请输入终点站", trigger: "change" },
          { validator: validStation1, trigger: "change" }
        ],
        departureTime: [
          { required: true, message: "请输入起始站发车时间", trigger: "change", },
          { validator: validDate, trigger: "change" }
        ],
        arrivalTime: [
          { required: true, message: "请输入终点站到站时间", trigger: "change" },
          { validator: validDate1, trigger: "change" }
        ],
      },
      laterules: {
        latestation: [
          { required: true, message: "请输入站点", trigger: "change" },
        ],
        latetime: [
          { required: true, message: "请输入晚点时间", trigger: "change" },
        ],
      },
      detailRules: {
        trainstationId: [
          { required: true, message: "请输入站点", trigger: "change" },
          { validator: validStation2, trigger: "change" }
        ],
        arrivalTime: [
          { required: true, message: "请输入到站时间", trigger: "change" },
          { validator: validDetailDate, trigger: "change" }
        ],
        departureTime: [
          { required: true, message: "请输入离站时间", trigger: "change" },
          { validator: validDetailDate1, trigger: "change" }
        ],
      },
      // 晚点数据
      lateTable: {
        latestation: "",
        latetime: "",
      },
      // 晚点框
      lateShow: false,
      // 查询框车次号
      keyword: "",
      // 要编辑的车次信息
      trainNumbers: [],
      // 要添加的车次信息
      editTrainNumbers: [],
      newTrainNumbers: {
        routertrainId: "",
        trainId: "",
        routertrainType: "",
        departureStationId: "",
        arrivalStationId: "",
        departureTime: "",
        arrivalTime: "",
      },
      dialogVisible: false,
      deleteVisible: false,
      addVisible: false,
      // 批量删除是否可用
      noAnySelection: true,
      // 总数据条数
      totalStation: 0,
      // 分页栏中当前页
      currentPage: 1,
      pagesize: 4,
      // 要删除的车次表
      ids: [],
      // 选中的数据
      multipleSelection: [],
      // 存储修改车次的信息
      lateRouter: {},
      lateStation: {},
      // 来自车型表的数据
      trains: [],
      stations: [],
      latestations: [],
      trainsArea: [],
      stationsArea: [],
      latestationsArea: [],
      details: [
      ],
      detail:
      {
        routerdetailId: '',
        trainstationId: '',
        routertrainId: '',
        arrivalTime: '',
        departureTime: ''
      }
      ,
      choosed: 0,
      active: 0,
      addtnBool: '',
      lastDetail: {}
    };
  },
  methods: {
    // 分页查询车次数据
    queryAll() {
      axios
        .get(
          "/train_number_page?current=" +
          this.currentPage +
          "&size=" +
          this.pagesize
        )
        .then((response) => {
          console.log(response);
          this.trainNumbers = response.data.data.records;
          this.totalStation = response.data.data.total;
          if (this.currentPage > Math.ceil(this.totalStation / this.pagesize)) {
            this.currentPage--;
            this.queryAll();
          }
        });
    },

    querySearch(queryString, cb) {
      var trainsArea = this.trainsArea;
      var results = queryString
        ? trainsArea.filter(this.createFilter(queryString))
        : trainsArea;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    querySearchSta(queryString, cb) {
      var stationsArea = this.stationsArea;
      var results = queryString
        ? stationsArea.filter(this.createFilter(queryString))
        : stationsArea;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    queryLateSta(queryString, cb) {
      var latestationsArea = this.latestationsArea;
      var results = queryString
        ? latestationsArea.filter(this.createFilter(queryString))
        : latestationsArea;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (
          restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
          0
        );
      };
    },
    loadAllName() {
      return this.trains;
    },

    loadAllStation() {
      return this.stations;
    },

    loadLateStation() {
      return this.latestations;
    },
    // 点击添加上列车类型
    handleSelect(item) {
      this.newTrainNumbers.routertrainType = item.type;
    },
    // 点击获取当前车次车站信息
    handleLateSelect(item) {
      console.log("test:")
      console.log(item);
      this.getRouterStation(this.lateRouter.routertrainId, item.value);
    },
    // 点击晚点按钮
    lateClick(row) {
      this.latestations = []
      this.latestationsArea = []
      console.log(row);
      this.lateRouter = row;
      this.lateShow = true;
      this.getLateStation(row.routertrainId);
      this.latestationsArea = this.loadLateStation();
    },
    // 获取火车类型数据
    getTrainName() {
      axios.get("/train").then((response) => {
        console.log(response);
        response.data.data.forEach((element) => {
          let data = {
            value: element.trainId,
            type: element.trainName,
          };
          this.trains.push(data);
        });
      });
    },
    // 获取车站数据
    getTrainStation() {
      axios.get("/trainstationbypage?current=1&size=10000").then((response) => {
        response.data.records.forEach((element) => {
          let data = {
            value: element.trainstationId,
            label: element.trainstationName,
          };
          this.stations.push(data);
        });
      });
    },
    // 获取晚点车站数据
    getLateStation(trainID) {
      axios.get("/details/" + trainID).then((response) => {
        console.log(response);
        response.data.data.forEach((element) => {
          let data = {
            value: element.trainstationId,
          };
          this.latestations.push(data);
        });
      });
    },
    // 更改车次信息
    saveTrain(trainNumbers) {
      console.log(trainNumbers);
      trainNumbers.departureTime = this.setTimeToSec(trainNumbers.departureTime)
      trainNumbers.arrivalTime = this.setTimeToSec(trainNumbers.arrivalTime)
      console.log(trainNumbers["routertrainId"]);
      axios
        .put("/train_number/" + trainNumbers["routertrainId"], trainNumbers)
        .then((response) => {
          console.log(response);
          this.queryAll();
        });
    },

    // 单个删除某个车次
    deleteTrainNumber(row) {
      console.log(row);
      axios.delete("/train_number/" + row["routertrainId"]).then((response) => {
        console.log(response);
        if (response.data.code == 204) {
          this.$message({
            type: "success",
            message: row.routertrainId + "车次删除成功!",
          });
          this.queryAll();
          this.$refs.multipleTable.clearSelection();
        } else {
          this.$message({
            type: "error",
            message: row.routertrainId + "车次删除失败!",
          });
        }
      });
    },

    // 修改车次信息
    submitLate(formName) {
      console.log(this.$refs[formName].validate);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = this.lateStation;
          console.log(data);
          data.laterTime = this.setTimeToHour(this.lateTable.latetime);
          data.routerdetailStatus = 1;
          let userName = getToken();
          data.adminId = userName
          axios.put("/details/", data).then((response) => {
            console.log(response);
            if (response.data.code == 200) {
              this.$notify({
                title: "成功",
                message: "晚点成功",
                type: "success",
                duration: 1500,
              });
              this.latestations = []
              this.lateTable = {}
              this.lateShow = false
            } else {
              this.$notify({
                title: "失败",
                message: "晚点失败",
                type: "error",
                duration: 1500,
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    //根据车次id和车站id查询某条经停信息
    getRouterStation(routertrainId, trainstationId) {
      axios
        .get("/details/" + routertrainId + "/" + trainstationId)
        .then((response) => {
          console.log(response.data.data);
          this.lateStation = response.data.data;
        });
    },
    // 搜索栏搜索车次信息
    handleBtnClick(keyword) {
      if (keyword == "") {
        this.$message({
          type: "info",
          message: "请输入查询内容",
        });
      } else {
        console.log(keyword);
        console.log(this.trainNumbers);
        axios.get("/train_number/" + keyword).then((response) => {
          console.log(response.data.data);
          this.trainNumbers = [];
          if (response.data.data) this.trainNumbers.push(response.data.data);
          this.totalStation = 1;
          console.log(this.trainNumbers);
        });
      }
    },

    // 关闭晚点弹出窗
    closeLate() {
      this.latestations = []
      this.latestationsArea = []
      this.lateTable = {}
    },


    // 点击编辑按钮
    handleEditClick(index, row) {
      console.log(index);
      this.editTrainNumbers = row;
      this.dialogVisible = true;
    },

    handleEditSaveClick(trainNumber) {
      console.log(trainNumber);
      this.saveTrain(trainNumber);
      this.dialogVisible = false;
    },

    // 时间转换器
    setTimeToHour(thatdate) {
      let date = new Date(thatdate);
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();
      return hour + ":" + minute + ":" + second;
    },

    // 时间转换器
    setTimeToSec(thatdate) {
      let date = new Date(thatdate);
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hour +
        ":" +
        minute +
        ":" +
        second
      );
    },

    // 点击添加车次按钮，显示出添加框
    handleAddClick() {
      this.addVisible = true;
    },

    checkDetails(index, row) {
      console.log(index);
      Bus.$emit("tn", row.routertrainId);
      console.log(row);
      this.$router.push({
        path: "/manage/detailsquery",
        query: {
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
              rows.forEach((element) => {
                this.deleteTrainNumber(element);
              });
              this.queryAll();
              // 清空multipleSelection
              this.multipleSelection = [];
              this.ids = [];
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
    // 保留之前选中的数据
    rowKey(row) {
      return row.routertrainId;
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
    next() {
      this.$refs.newTrainNumbers.validate((valid) => {
        if (valid) {
          this.newTrainNumbers.departureTime = this.setTimeToSec(this.newTrainNumbers.departureTime);
          this.newTrainNumbers.arrivalTime = this.setTimeToSec(this.newTrainNumbers.arrivalTime);
          axios.post("/train_number", this.newTrainNumbers).then((response) => {
            console.log(response);
            if (response.data.code == 201) {
              this.$message({
                type: "success",
                message: "添加成功",
              });
              this.newTrainNumbers = {};
              this.queryAll();
              return true
            } else {
              this.$message({
                type: "warning",
                message: "该车次已存在",
              });
              return false
            }
          });
        } else {
          console.log("error submit!!");
          return false
        }
        this.choosed += 1;
        this.detail.routertrainId = this.newTrainNumbers.routertrainId
        let firstDetail = {
          routerdetailId: 1,
          trainstationId: this.newTrainNumbers.departureStationId,
          routertrainId: this.newTrainNumbers.routertrainId,
          arrivalTime: this.newTrainNumbers.departureTime,
          departureTime: this.newTrainNumbers.departureTime,
        }
        this.lastDetail = {
          routerdetailId: 0,
          trainstationId: this.newTrainNumbers.arrivalStationId,
          routertrainId: this.newTrainNumbers.routertrainId,
          arrivalTime: this.newTrainNumbers.arrivalTime,
          departureTime: this.newTrainNumbers.arrivalTime,
        }
        this.details.push(this.lastDetail)
        this.details.push(firstDetail)
        this.detail.routerdetailId = 2
        this.active += 1
      });
    },
    detailNext(detail) {
      let newDetail = JSON.parse(JSON.stringify(detail))
      this.$refs.detail.validate((valid) => {
        if (valid) {
          newDetail.departureTime = this.setTimeToSec(newDetail.departureTime);
          newDetail.arrivalTime = this.setTimeToSec(newDetail.arrivalTime);
          this.details.push(newDetail)
          this.detail.routerdetailId++
          this.$message({
            title: "成功",
            message: "修改下一站信息",
            type: "success",
            duration: 1500,
          });
        } else {
          this.$message({
            title: "失败",
            message: "校验未通过",
            type: "error",
            duration: 1500,
          });
          return false;
        }
      })
    },
    submitDetailForm() {
      for (let index = 0; index < this.details.length; index++) {
        if (index == 0) {
          this.details[index].routerdetailId = this.detail.routerdetailId
        }
        axios.post("/details/", this.details[index]).then(res => {
          if (res.data.code != '201') {
            this.$message({
              title: "失败",
              message: "提交失败",
              type: "error",
              duration: 1500,
            })
            return false
          }
        })
        console.log(this.details[index])
      }
      this.$message({
        title: "成功",
        message: "添加车次完成",
        type: "success",
        duration: 1500,
      })
      console.log("details:::")
      console.log(this.details)
      this.active = 3
      this.addVisible = false
    },
    stringToDate(strDate) {
      var tempStrs = strDate.split(" ");
      var dateStrs = tempStrs[0].split("-");
      var year = parseInt(dateStrs[0], 10);
      var month = parseInt(dateStrs[1], 10) - 1;
      var day = parseInt(dateStrs[2], 10);
      var timeStrs = tempStrs[1].split(":");
      var hour = parseInt(timeStrs[0], 10);
      var minute = parseInt(timeStrs[1], 10);
      var second = parseInt(timeStrs[2], 10);
      var date = new Date(year, month, day, hour, minute, second);
      return date;
    }
  },
  mounted() {
    this.getTrainStation();
    this.getTrainName();
    this.queryAll();
    this.trainsArea = this.loadAllName();
    this.stationsArea = this.loadAllStation();
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