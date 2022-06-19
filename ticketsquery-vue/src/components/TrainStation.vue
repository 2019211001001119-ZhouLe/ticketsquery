<template>
  <el-container>
    <el-header></el-header>
    <el-row>
      <el-col :span="1">
        <el-button @click="batchDelete(ids)" style="margin-left: -12px" icon="el-icon-delete-solid" circle></el-button>
      </el-col>
      <el-col :span="10">
        <el-input clearable v-model="input" suffix-icon="el-icon-trainlocation-outline" placeholder="请输入火车站名称"></el-input>
      </el-col>
      <el-col :span="1">
        <el-button @click="searchtrainstation($event)" icon="el-icon-search" circle></el-button>
      </el-col>
    </el-row>
    <data-tables :data="trainstations"  @selection-change="handleSelectionChange">
      <el-table-column  type="selection" width="55"></el-table-column>
      <el-table-column prop="trainstationName" label="车站名称" width="180">
      </el-table-column>
      <el-table-column prop="trainstationId" label="车站简称" width="180">
      </el-table-column>
      <el-table-column prop="provinceName" label="所属省" width="200">
      </el-table-column>
      <el-table-column prop="cityName" label="所属市" width="200">
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
  data() {
    return {
      trainstations: [], // 存放所有车站信息
      trainstation: [], // 存放编辑车站信息
      newtrainstation: { // 添加车站
        trainstationId: '',
        cityId: '',
        trainstationName: ''
      },
      ids: [], // 存放删除的数据
      multipleSelection: [], // 多选的数据
      input: '', // 输入框
      dialogVisible: false, // 编辑车站信息弹框的状态
      deleteVisible: false, // 删除提示弹框的状态
      addVisible: false // 增加车站提示弹框的状态
    }
  },
  mounted() {
    this.querytrainstations()
  },
  methods: {

    // 查询所有车站
    querytrainstations() {
      axios.get('http://127.0.0.1:8888/trainstation').then((response) => {
        this.trainstations = response.data.data
        console.log(response.data)
      })
    },

    // 修改车站
    savetrainstation(trainstation) {
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

    // 删除车站
    deletetrainstation(index, row) {
      console.log(index)
      axios.delete('http://127.0.0.1:8888/trainstation/' + row['trainstationId']).then((response) => {
        console.log(response)
        if (response.data.code == '204') {
          this.input = ''
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 1500
          })
        }
        this.querytrainstations()
        this.flush()
      })
    },

    // 增加车站
    addtrainstation(trainstation) {
      axios.post('http://127.0.0.1:8888/trainstation', trainstation).then((response) => {
        console.log(response)
        if (response.data.code == '201') {
          this.input = ''
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success',
            duration: 1500
          })
        }
        this.querytrainstations()
      })
    },

    // 通过车站名查询
    searchtrainstation($event) {
      let target = $event.target
      if (target.nodeName == 'I') {
        target = $event.target.parentNode
      }
      target.blur()
      let data = this.input
      axios.get('http://127.0.0.1:8888/trainstation/' + data).then((res) => {
        this.trainstations = res.data.data
        console.log(res.data.data)
      })
    },

    // 刷新所有车站信息
    flush($event) {
      console.log($event)
      let target = $event.target
      if (target.nodeName == 'I') {
        target = $event.target.parentNode
      }
      target.blur()
      this.querytrainstations()
      this.input = ''
    },

    handleEditClick(index, row) {
      console.log(index)
      this.trainstation = row
      this.dialogVisible = true
    },

    handleEditSaveClick(trainstation) {
      this.savetrainstation(trainstation)
      this.dialogVisible = false
    },

    handleAddClick() {
      this.addVisible = true
    },

    handleAddSaveClick(trainstation) {
      this.addtrainstation(trainstation)
      this.addVisible = false
    },

    handleSelectionChange(selection) {
      console.log(selection)
      this.ids = selection.map(item => item.trainstationId)
      this.multipleSelectionFlag = true
      if (this.multipleSelection.length == 0) {
        // 如不进行判断则勾选完毕后批量删除按钮还是会在
        this.multipleSelectionFlag = false
      }
    },

    // 批量删除房源
    batchDelete (rows) {
      let that = this
      console.log(rows)
      if (rows.length !== 0) {
        that.$confirm('是否确认删除这' + rows.length + '条数据?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          let data = that.ids
          axios.delete('http://127.0.0.1:8888/trainstations/' + data).then((res) => {
            if (res.data.code == '204') {
              that.$message({
                message: '删除成功',
                type: 'success',
                duration: 1500
              })
              that.querytrainstations()
            }
          })
        }).then(() => {
          that.input = ''
        })
      } else {
        that.$message('未选择数据！')
      }
    }
  }
}
</script>
