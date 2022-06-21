<template>
    <div>
        <el-row>
            <el-col :span="10">
                <el-input v-model="keyword" placeholder="请输入车次"></el-input>
            </el-col>
            <el-col :span="14">
                <el-button @click="handleBtnClick(keyword)">按钮</el-button>
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
    </div>
</template>
<script>
import axios from 'axios'
import Bus from '../utils/bus.js'

export default {
  data() {
        return {
            trainNumber: '',
            keyword: '',
            details: [],
        }
    },
    methods: {
        handleBtnClick(keyword) {
            this.trainNumber = keyword
            this.queryKeyword()
        },
        queryKeyword() {
            axios.get("http://127.0.0.1:8888/details/query/" + this.trainNumber).then((response) => {
                console.log(this.trainNumber)
                this.details = response.data.data
            })
        }
    },
    mounted() {
        var that = this
        this.handleBtnClick(that.$route.params.routertrainId)
    }
}
</script>
