<template>
  <div>
    <Manage></Manage>
    <div class="pageStyle">
      <el-container id="a">
        <el-header>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>列车管理</el-breadcrumb-item>
          </el-breadcrumb>

        </el-header>
        <el-main>
          <el-row align="middle" type="flex">
            <el-col :span="4">
              <div>列车名关键字搜索:</div>
            </el-col>
            <el-col :span="16">
              <el-input v-model="keyword"></el-input>
            </el-col>
            <el-col :span="4">
              <el-button @click="likeQueryTrains(keyword)">搜索</el-button>
            </el-col>
          </el-row>
          <data-tables :data="trains">
            <el-table-column prop="trainId" label="列车ID" width="180">
            </el-table-column>
            <el-table-column prop="trainName" label="列车名" width="180">
            </el-table-column>
            <el-table-column prop="trainSpeed" label="列车速度">
            </el-table-column>
            <el-table-column>
              <template slot="header">
                <el-button @click="handleAddClick()" icon="el-icon-plus" circle></el-button>
              </template>
              <template slot-scope="scope">
                <el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle>
                </el-button>
                <el-popconfirm title="确认删除这行吗?" @confirm="deleteTrain(scope.$index, scope.row)">
                  <el-button slot="reference" icon="el-icon-delete" circle>
                  </el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
          </data-tables>
          <el-button @click="queryTrains">刷新表格</el-button>
        </el-main>
        <el-dialog title="编辑列车" :visible.sync="dialogVisible">
          <el-form :modle="train">
            <el-form-item label="列车ID">
              <el-input v-model="train.trainId" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="列车名">
              <el-input v-model="train.trainName"></el-input>
            </el-form-item>
            <el-form-item label="列车速度">
              <el-input v-model="train.trainSpeed"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleEditSaveClick(train)">保存</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
        <el-dialog title="添加列车" :visible.sync="addVisible">
          <el-form :modle="newTrain">
            <el-form-item label="列车ID">
              <el-input v-model="newTrain.trainId"></el-input>
            </el-form-item>
            <el-form-item label="列车名">
              <el-input v-model="newTrain.trainName"></el-input>
            </el-form-item>
            <el-form-item label="列车速度">
              <el-input v-model="newTrain.trainSpeed"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleAddSaveClick(newTrain)">保存</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </el-container>
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
      trains: [],
      train: [],
      newTrain: {
        trainId: '',
        trainName: '',
        trainSpeed: ''
      },
      dialogVisible: false,
      deleteVisible: false,
      addVisible: false,
      keyword: ''
    }
  },
  mounted () {
    this.queryTrains()
  },
  methods: {
    queryTrains () {
      axios.get('http://127.0.0.1:8888/train').then((response) => {
        this.trains = response['data']['data']
        console.log(response)
      })
    },

    likeQueryTrains (kw) {
      axios.get('http://127.0.0.1:8888/train', {
        params: {
          keyword: kw
        }
      }).then((response) => {
        this.trains = response['data']['data']
        console.log(response)
      })
    },

    saveTrain (train) {
      axios.put(('http://127.0.0.1:8888/train/' + train['trainId']), train).then((response) => {
        console.log(response)
        this.queryTrains()
      })
    },

    deleteTrain (index, row) {
      console.log(index)
      axios.delete('http://127.0.0.1:8888/train/' + row['trainId']).then((response) => {
        console.log(response)
        this.queryTrains()
      })
    },

    addTrain (train) {
      axios.post('http://127.0.0.1:8888/train', train).then((response) => {
        console.log(response)
        this.queryTrains()
      })
    },

    handleEditClick (index, row) {
      console.log(index)
      this.train = row
      this.dialogVisible = true
    },

    handleEditSaveClick (train) {
      this.saveTrain(train)
      this.dialogVisible = false
    },

    handleAddClick () {
      this.addVisible = true
    },

    handleAddSaveClick (train) {
      this.addTrain(train)
      this.addVisible = false
    }
  }
}
</script>
<style>
.pageStyle {
  width: 1250px;
  margin-left: 251px;
  margin-top: -610px;
}
</style>
