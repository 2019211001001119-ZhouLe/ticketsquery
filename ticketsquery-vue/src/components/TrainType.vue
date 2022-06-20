<template>
  <div>
    <Manage></Manage>
    <div class="pageStyle">
      <p class="titleCap">车型管理</p>
      <el-card class="box-card">
        <el-breadcrumb
          separator-class="el-icon-arrow-right"
          style="margin-bottom: 20px"
        >
          <el-breadcrumb-item
            ><a href="/manage/traintype">车型管理</a></el-breadcrumb-item
          >
        </el-breadcrumb>
        <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          stripe
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="" width="120" prop="imageT">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">
                <img :src="scope.row.img" alt="" class="imgTrain">
              </span>
              <el-input v-else v-model="scope.row.img"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="120" >
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.name }}</span>
              <el-input v-else v-model="scope.row.name"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="地址" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.address }}</span>
              <el-input v-else v-model="scope.row.address"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                v-if="scope.row.visible"
                @click="handleEdit(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                v-else
                @click="handleEdit(scope.$index, scope.row)"
              >
                完成</el-button
              >

              <el-popconfirm
                title="这是一段内容确定删除吗？"
                icon="el-icon-info"
                icon-color="red"
                @confirm="handleDelete(scope.$index, scope.row)"
              >
                <el-button slot="reference" size="mini" type="danger"
                  >删除</el-button
                >
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="8"
            layout="prev, pager, next, jumper"
            :total="80"
            class="departPaging"
          >
          </el-pagination>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>

import Manage from './Manage'

export default {
  components: {
    Manage
  },
  methods: {
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
    },
    toggleSelection (rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    tableRowClassName ({ row, rowIndex }) {
      this.tableData[rowIndex].visible = true
      console.log(rowIndex)
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    handleEdit (index, row) {
      row.visible = !row.visible
      console.log(this.tableData[index].visible)
    },
    handleDelete (index, row) {
      this.tableData.splice(index, 1)
    }
  },
  data () {
    return {
      currentPage: 1,
      tableData: [
        {
          img: 'https://pic.baike.soso.com/ugc/baikepic2/4228/20210521133105-923552370_jpeg_1623_1080_726743.jpg/300',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          visible: true
        },
        {
          img: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          visible: true
        },
        {
          img: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          visible: true
        },
        {
          img: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          visible: true
        },
        {
          img: '2016-05-08',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          visible: true
        }
      ],
      multipleSelection: []
    }
  }
}
</script>

<style scoped>
.pageStyle{
  width: 1250px;
  margin-left: 251px;
  margin-top: -700px;
}
.imgTrain{
  width: 40px;
  height: 40px;
}
.departPaging{
  margin: 20px 0;
  text-align: center;
}
.el-table .warning-row {
  background: rgb(84, 76, 63);
}

.el-table .success-row {
  background: #3ea209;
}

</style>
