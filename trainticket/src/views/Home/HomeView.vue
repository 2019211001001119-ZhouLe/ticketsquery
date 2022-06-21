<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">首页</p>
      <el-card class="box-card">
        <p class="dataSta">数据统计</p>
        <el-row :gutter="20">
          <el-col :span="6" v-for="index in 8" :key="index">
            <div class="littleCard">
              <p>今日车次数量</p>
              <h1>5033</h1>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="40">
          <el-col :span="16">
            <p class="dataSta">五大车次最多的车站</p>
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
export default {
  data() {
    return {
      maxSta: {
        data: [
          {
            name: "南阳",
            value: 167,
          },
          {
            name: "周口",
            value: 123,
          },
          {
            name: "漯河",
            value: 98,
          },
          {
            name: "郑州",
            value: 75,
          },
          {
            name: "西峡",
            value: 66,
          },
        ],
        colors: ["#e062ae", "#fb7293", "#e690d1", "#32c5e9", "#96bfff"],
        unit: "单位",
        showValue: true,
      },
      trainType: {
        data: [
          {
            name: "周口",
            value: 55,
          },
          {
            name: "南阳",
            value: 120,
          },
          {
            name: "西峡",
            value: 78,
          },
          {
            name: "驻马店",
            value: 66,
          },
          {
            name: "新乡",
            value: 80,
          },
        ],
      },
      
    };
  },
};
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