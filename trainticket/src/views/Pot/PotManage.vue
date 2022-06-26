<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">站点管理</p>
      <el-card class="box-card">
        <el-header></el-header>
        <el-row>
          <el-col :span="10">
            <el-input
              clearable
              v-model="input"
              suffix-icon="el-icon-trainlocation-outline"
              placeholder="请输入火车站名称"
            ></el-input>
          </el-col>
          <el-col :span="1">
            <el-button
              @click="searchtrainstation($event)"
              icon="el-icon-search"
              circle
            ></el-button>
          </el-col>
          <el-col :span="1">
            <el-button
              @click="flush()"
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
              ><span class="el-icon-plus"></span> 添加车站</el-button
            >
          </el-col>
        </el-row>
        
        <!-- 数据表格 -->
        <el-table
          ref="multipleTable"
          :data="trainstations"
          tooltip-effect="dark"
          style="width: 100%"
          stripe
          :row-class-name="tableRowClassName"
          :row-key="rowKey"
          @selection-change="handleSelectionChange"
        >
          <el-table-column :reserve-selection="true" type="selection" width="65"></el-table-column>
          <el-table-column :reserve-selection="true" prop="trainstationName" label="车站名称" width="180">
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="trainstationId" label="车站简称" width="180">
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="provinceName" label="所属省" width="200">
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="cityName" label="所属市" width="300">
          </el-table-column>
          <el-table-column :reserve-selection="true" label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleEditClick(scope.$index, scope.row)"
                icon="el-icon-edit"
                circle
              ></el-button>
              <el-popconfirm
                title="确认删除这行吗?"
                @confirm="deletetrainstation(scope.$index, scope.row)"
              >
                <el-button slot="reference" icon="el-icon-delete" circle>
                </el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页栏 -->
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pagesize"
            layout="prev, pager, next, jumper"
            :total="totalStation"
            class="departPaging"
          >
          </el-pagination>
        </div>
        <el-dialog title="编辑车站" :visible.sync="dialogVisible">
          <el-form :modle="trainstation">
            <el-form-item label="车站编号">
              <el-input
                v-model="trainstation.trainstationId"
                :disabled="true"
              ></el-input>
            </el-form-item>
            <el-form-item label="城市编号">
              <el-input v-model="trainstation.cityId"></el-input>
            </el-form-item>
            <el-form-item label="车站名称">
              <el-input v-model="trainstation.trainstationName"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleEditSaveClick(trainstation)"
                >保存</el-button
              >
            </el-form-item>
          </el-form>
        </el-dialog>
        <el-dialog title="添加车站" :visible.sync="addVisible">
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
              <el-button @click="handleAddSaveClick(newtrainstation)"
                >保存</el-button
              >
            </el-form-item>
          </el-form>
        </el-dialog>
        <el-dialog title="删除" :visible.sync="deleteVisible">
          <el-button
            @click="handleDeleteConfirmClick(trainstation)"
            type="danger"
            >确认删除</el-button
          >
        </el-dialog>
      </el-card>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      // 批量删除是否可用
      noAnySelection: true,
      // 每页数据
      pagesize: 4,
      // 总数据条数
      totalStation: 0,
      // 分页栏中当前页
      currentPage: 1,
      trainstations: [], // 存放所有车站信息
      trainstation: [], // 存放编辑车站信息
      newtrainstation: {
        // 添加车站
        trainstationId: "",
        cityId: "",
        trainstationName: "",
      },
      ids: [], // 存放删除的数据
      multipleSelection: [], // 多选的数据
      input: "", // 输入框
      dialogVisible: false, // 编辑车站信息弹框的状态
      deleteVisible: false, // 删除提示弹框的状态
      addVisible: false, // 增加车站提示弹框的状态
    };
  },
  mounted() {
    this.querytrainstations();
  },
  methods: {
    // 查询所有车站
    querytrainstations() {
      axios
        .get(
          "/trainstationbypage?current=" +
            this.currentPage +
            "&size="+this.pagesize
        )
        .then((response) => {
          this.trainstations = response.data.records;
          this.totalStation = response.data.total;
          if(this.currentPage>Math.ceil(this.totalStation/this.pagesize)){
            this.currentPage--;
            this.querytrainstations()
          }
          console.log(response.data);
        });
    },

    // 修改车站
    savetrainstation(trainstation) {
      axios
        .put(
          "/trainstation/" +
            trainstation["trainstationId"],
          trainstation
        )
        .then((response) => {
          console.log(response);
          if (response.data.code == "200") {
            this.$message({
              message: "修改成功",
              type: "success",
              center: true,
            });
          }
          this.querytrainstations();
        });
    },

    // 删除车站
    deletetrainstation(index, row) {
      let that=this
      console.log(index);
      axios
        .delete("/trainstation/" + row["trainstationId"])
        .then((response) => {
          console.log(response);
          if (response.data.code == "204") {
            this.input = "";
            this.$notify({
              title: "成功",
              message: "删除成功",
              type: "success",
              duration: 1500,
            });
            this.querytrainstations()
            that.$refs.multipleTable.clearSelection();
          }
          else{
            this.$notify({
              title: "失败",
              message: "删除失败",
              type: "error",
              duration: 1500,
            });
          }
          this.flush();
        });
    },

    // 增加车站
    addtrainstation(trainstation) {
      console.log(trainstation);
      axios.post("/trainstation", trainstation)
        .then((response) => {
          console.log(response);
          if (response.data.code == "201") {
            this.$notify({
              title: "成功",
              message: "添加成功",
              type: "success",
              duration: 1500,
            });
          }
          else{
            this.$notify({
              title: "失败",
              message: "添加失败",
              type: "error",
              duration: 1500,
            });
          }
          this.querytrainstations();
        })
        .catch
    },

    // 通过车站名查询
    searchtrainstation($event) {
      let target = $event.target;
      if (target.nodeName == "I") {
        target = $event.target.parentNode;
      }
      target.blur();
      let data = this.input;
      axios.get("/trainstation/" + data).then((res) => {
        this.trainstations = res.data.data;
        this.totalStation=1
        this.currentPage=1
        console.log(res.data.data);
      });
    },

    // 刷新所有车站信息
    flush($event) {
      this.querytrainstations();
      this.input = "";
    },


    handleEditClick(index, row) {
      console.log(index);
      this.trainstation = row;
      this.dialogVisible = true;
    },

    handleEditSaveClick(trainstation) {
      this.savetrainstation(trainstation);
      this.dialogVisible = false;
    },

    handleAddClick() {
      this.addVisible = true;
    },

    
    handleAddSaveClick(trainstation) {
      this.addtrainstation(trainstation);
      this.addVisible = false;
    },

    // 当选中项发生改变时
    handleSelectionChange(selection) {
      console.log(selection);
      this.multipleSelection=selection
      this.ids = selection.map((item) => item.trainstationId);
      this.hasAnySelection();
    },

    // 保留之前选中的数据
    rowKey(row) {
        return row.trainstationId
    },

    // 批量删除车站
    batchDelete(rows) {
      let that=this
      console.log(rows);
      if (rows.length !== 0) {
        that
          .$confirm("是否确认删除这" + rows.length + "条数据?", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
          .then(function () {
            let data = that.ids;
            axios
              .delete("/trainstations/" + data)
              .then((res) => {
                if (res.data.code == "204") {
                  that.$message({
                    message: "删除成功",
                    type: "success",
                    duration: 1500,
                  });
                  that.$refs.multipleTable.clearSelection();
                  that.querytrainstations();
                  that.ids=[]
                }
                else{
                  that.$message({
                    message: "删除失败",
                    type: "error",
                    duration: 1500,
                  });
                }
              });
          })
      } else {
        that.$message("未选择数据！");
      }
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
      this.querytrainstations();
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
};
</script>
<style>
.tableButton button{
  float: right;
  margin: 0 20px;
}
.departPaging {
  margin: 20px 0;
  text-align: center;
}
</style>
