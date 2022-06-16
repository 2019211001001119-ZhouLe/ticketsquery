<template>
  <el-container>
    <el-header></el-header>
    <el-row>
      <el-col :span="12">
        <el-input clearable v-model="input" suffix-icon="el-icon-trainlocation-outline" placeholder="请输入火车站名称"></el-input>
      </el-col>
      <el-col :span="1">
        <el-button @click="searchtrainstation($event)" icon="el-icon-search" circle></el-button>
      </el-col>
    </el-row>
    <data-tables :data="trainstations">
      <el-table-column prop="trainstationId" label="车站编号" width="180">
      </el-table-column>
      <el-table-column prop="cityId" label="城市编号" width="180">
      </el-table-column>
      <el-table-column prop="trainstationName" label="车站名称" width="215">
      </el-table-column>
      <el-table-column>
        <template slot="header">
          <el-button @click="handleAddClick()" icon="el-icon-plus" circle></el-button>
          <el-button @click="flush($event)" style="margin-left: 0px;" icon="el-icon-refresh" circle></el-button>
        </template>
        <template slot-scope="scope">
          <el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle></el-button>
          <el-popconfirm title="确认删除这行吗?" @confirm="deletetrainstation(scope.$index, scope.row)">
            <el-button slot="reference" icon="el-icon-delete" circle>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </data-tables>
    <el-dialog title="编辑列车" :visible.sync="dialogVisible">
      <el-form :modle="trainstation">
        <el-form-item label="车站编号">
          <el-input v-model="trainstation.trainstationId" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="城市编号">
          <el-input v-model="trainstation.cityId"></el-input>
        </el-form-item>
        <el-form-item label="车站名称">
          <el-input v-model="trainstation.trainstationName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleEditSaveClick(trainstation)">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="添加列车" :visible.sync="addVisible">
      <el-form :modle="newtrainstation">
        <el-form-item label="车站编号">
          <el-input v-model="newtrainstation.trainstationId"></el-input>
        </el-form-item>
        <el-form-item label="城市编号">
          <el-input v-model="newtrainstation.cityId"></el-input>
        </el-form-item>
        <el-form-item label="车站名称">
          <el-input v-model="newtrainstation.trainstationName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleAddSaveClick(newtrainstation)">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="删除" :visible.sync="deleteVisible">
      <el-button @click="handleDeleteConfirmClick(trainstation)" type="danger">确认删除</el-button>
    </el-dialog>
  </el-container>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      trainstations: [],
      trainstation: [],
      newtrainstation: {
        trainstationId: '',
        cityId: '',
        trainstationName: ''
      },
      input: '',
      dialogVisible: false,
      deleteVisible: false,
      addVisible: false
    }
  },
  mounted () {
    this.querytrainstations()
  },
  methods: {
    querytrainstations () {
      axios.get('http://127.0.0.1:8888/trainstation').then((response) => {
        this.trainstations = response.data.data
      })
    },

    savetrainstation (trainstation) {
      axios.put(('http://127.0.0.1:8888/trainstation/' + trainstation['trainstationId']), trainstation).then((response) => {
        console.log(response)
        if (response.data.code == '200') {
          this.$message({
            message: '修改成功',
            type: 'success',
            center: true
          })
        }
        this.querytrainstations()
      })
    },

    deletetrainstation (index, row) {
      console.log(index)
      axios.delete('http://127.0.0.1:8888/trainstation/' + row['trainstationId']).then((response) => {
        console.log(response)
        if (response.data.code == '204') {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 1000
          })
        }
        this.querytrainstations()
      })
    },

    addtrainstation (trainstation) {
      axios.post('http://127.0.0.1:8888/trainstation', trainstation).then((response) => {
        console.log(response)
        if (response.data.code == '201') {
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success',
            duration: 1000
          })
        }
        this.querytrainstations()
      })
    },

    searchtrainstation (event) {
      let target = event.target
      if (target.nodeName == 'I') {
        target = event.target.parentNode
      }
      target.blur()
      let data = this.input
      axios.get('http://127.0.0.1:8888/trainstation/' + data).then((res) => {
        this.trainstations = res.data.data
        console.log(res.data.data)
      })
    },

    flush (event) {
      let target = event.target
      if (target.nodeName == 'I') {
        target = event.target.parentNode
      }
      target.blur()
      this.querytrainstations()
      this.input = ''
    },

    handleEditClick (index, row) {
      console.log(index)
      this.trainstation = row
      this.dialogVisible = true
    },

    handleEditSaveClick (trainstation) {
      this.savetrainstation(trainstation)
      this.dialogVisible = false
    },

    handleAddClick () {
      this.addVisible = true
    },

    handleAddSaveClick (trainstation) {
      this.addtrainstation(trainstation)
      this.addVisible = false
    }
  }
}
</script>
