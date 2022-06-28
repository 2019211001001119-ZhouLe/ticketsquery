<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">首页</p>
      <el-card class="box-card">
        <p class="dataSta">数据统计</p>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="littleCard">
              <p>车次数量</p>
              <h1>{{ trainNumberStats }}</h1>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="littleCard">
              <p>经停数量</p>
              <h1>{{ detailsStats }}</h1>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="littleCard">
              <p>列车种类</p>
              <h1>{{ trainTypeStats }}</h1>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="littleCard">
              <p>车站数量</p>
              <h1>{{ trainStationStats }}</h1>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="40">
          <el-col :span="16">
            <p class="dataSta">车次最多的市</p>
            <dv-capsule-chart :config="maxSta" class="fiveSta" />
          </el-col>
          <el-col :span="7">
            <p class="dataSta">列车类型分布图</p>
            <dv-active-ring-chart :config="trainType" class="trainType" />
          </el-col>
          <el-col :span="1"></el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
export default {
  data() {
    return {
      trainNumberStats: 0,
      detailsStats: 0,
      trainStats: 0,
      trainTypeStats: 0,
      trainStationStats: 0,
      trainNumber: [],
      train: [],

      maxSta: {
        data: [

        ],
        colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff'],
        unit: '单位',
        showValue: true,
        index: true,
        rowNum: 3
      },
      trainType: {
        data: [

        ],
      }
    }
  },
  mounted() {
    axios.get("/train").then((response) => {
      this.trainTypeStats = response.data.data.length
    })
    axios.get("/train_number/city").then((response) => {
      this.trainNumber = response.data.data;
      this.trainNumberStats = this.trainNumber.length;
      for (var count = 0; count < this.trainNumberStats; count++) {
        var data = {
          name: this.trainNumber[count].city_name,
          value: 1
        }
        this.maxSta.data.push(data)
      }
      this.maxSta.data = this.addObj(this.maxSta.data)
      this.maxSta.data.sort(this.sortObj("value"))
      this.maxSta = { ...this.maxSta }

    })
    axios.get("/details").then((response) => {
      this.detailsStats = response.data.data.length;
    })
    axios.get("/train_number").then((response) => {
      this.train = response.data.data
      this.trainStats = response.data.data.length;
      for (var count = 0; count < this.trainStats; count++) {
        var data = {
          name: this.train[count].routertrainType,
          value: 100
        }
        this.trainType.data.push(data)
      }
      this.trainType.data = this.addObj(this.trainType.data)
      this.trainType = { ...this.trainType }
    })
    axios.get("/trainstationbypage?current=1&size=10000").then((response) => {
      this.trainStationStats = response.data.records.length;
    })

  },
  methods: {
    addObj(data) {
      let result = {};
      data.forEach(item => {
        if (result[item.name]) {
          result[item.name] += item.value;
        } else {
          result[item.name] = item.value;
        }
      })
      let s = [];
      for (let k in result) {
        s.push({
          name: k, value: result[k]
        });
      }
      return s
    },
    sortObj(prop) {
      return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop]; if (val1 > val2) {
          return -1;
        } else if (val1 < val2) {
          return 1;
        } else {
          return 0;
        }
      }
    }
  }
}
</script>

<style scoped>
.trainType {
  width: 100%;
  height: 300px;
  fill: '#000' !important;
  margin: 20px;
  border: 1px solid #e4e4e4;
  border-radius: 5px;
  box-sizing: border-box;
  background-color: #282C34;
}

.fiveSta {
  width: 100%;
  height: 300px;
  margin: 20px;
  border: 1px solid #e4e4e4;
  border-radius: 5px;
  background-color: #282C34;
}

.littleCard {
  height: 140px;
  margin: 20px;
  border-radius: 5px;
  border: 1px solid #e4e4e4;
  box-sizing: border-box;
  padding: 20px 0;
}

.littleCard p {
  text-align: center;
  font-size: 14px;
  font-weight: 100;
  margin: 10px;
}

.littleCard h1 {
  text-align: center;
  font-size: 40px;
  font-weight: 200;
}

.dataSta {
  font-size: 24px;
  font-weight: normal;
  margin-left: 20px;
}

.titleCap {
  margin: 10px 5px;
  font-size: 20px;
}

.homePageStyle {
  margin: 30px;
}

.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}
</style>