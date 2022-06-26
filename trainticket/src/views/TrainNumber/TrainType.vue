<template>
  <div>
    <div class="pageStyle">
      <p class="titleCap">车型管理</p>
      <el-card class="box-card">
        <!-- 使用面包屑来进行跳转处理 -->
        <el-breadcrumb
          separator-class="el-icon-arrow-right"
          style="margin-bottom: 20px"
        >
          <el-breadcrumb-item
            ><a href="/manage/traintype">车型管理</a></el-breadcrumb-item
          >
        </el-breadcrumb>
        <!-- 头部信息以及操作 -->
        <div class="tableTitle">
          <span>全部车型</span>
          <el-button
            type="danger"
            plain
            :disabled="noAnySelection"
            @click="batchDelete"
            ><span class="el-icon-close"></span> 批量删除</el-button
          >
          <el-button type="primary" @click="dialog = true"
            ><span class="el-icon-plus"></span> 添加车型</el-button
          >
        </div>
        <!-- 表格内容 -->
        <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          stripe
          :row-class-name="tableRowClassName"
          :row-key="rowKey"
          @selection-change="handleSelectionChange"
        >
          <el-table-column :reserve-selection="true" type="selection" width="55"></el-table-column>
          <el-table-column :reserve-selection="true" prop="trainName" label="列车简称" width="300">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.trainId }}</span>
              <el-input v-else v-model="scope.row.trainId" disabled></el-input>
            </template>
          </el-table-column>
          <el-table-column :reserve-selection="true" prop="trainName" label="列车全称" width="300">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.trainName }}</span>
              <el-input v-else v-model="scope.row.trainName"></el-input>
            </template>
          </el-table-column>
          <el-table-column :reserve-selection="true"
            prop="trainSpeed"
            label="列车速度(km/h)"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.trainSpeed }}</span>
              <el-input v-else v-model="scope.row.trainSpeed"></el-input>
            </template>
          </el-table-column>
          <el-table-column :reserve-selection="true" label="操作" class="operateButton">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                v-if="scope.row.visible"
                :disabled="!editShow"
                >编辑</el-button
              >
              <el-button
                size="mini"
                @click="handleFinish(scope.$index, scope.row)"
                v-if="!scope.row.visible"
              >
                完成</el-button
              >
              <el-button
                size="mini"
                @click="handleCancel(scope.$index, scope.row)"
                v-if="!scope.row.visible"
              >
                取消</el-button
              >
              <el-popconfirm
                title="确定删除这条数据吗？"
                icon="el-icon-info"
                icon-color="red"
                @confirm="handleDelete(scope.row)"
                v-if="scope.row.visible"
              >
                <el-button slot="reference" size="mini" type="danger"
                  >删除</el-button
                >
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
            :total="tableTotal"
            class="departPaging"
          >
          </el-pagination>
        </div>
        <!-- 添加车型弹出框 -->
        <el-drawer
          title="添加车型"
          :before-close="handleClose"
          :visible.sync="dialog"
          direction="ltr"
          custom-class="demo-drawer"
          ref="drawer"
        >
          <div class="demo-drawer__content formStyle">
            <el-form :model="typeForm" :rules="rules" ref="typeForm">
              <el-form-item label="列车简称" label-width="80px" prop="trainId">
                <el-input
                  v-model="typeForm.trainId"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="列车全称"
                label-width="80px"
                prop="trainName"
              >
                <el-input
                  v-model="typeForm.trainName"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="列车速度(km/h)"
                label-width="80px"
                prop="trainSpeed"
              >
                <el-input
                  v-model="typeForm.trainSpeed"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item class="formButtonStyle">
                <el-button type="primary" @click="submitForm('typeForm')"
                  >确定</el-button
                >
                <el-button @click="cancelForm">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-drawer>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  mounted() {
    this.querytrains();
  },
  methods: {
    // 分页查询所有车型
    querytrains() {
      axios
        .get(
          "/trainpage?current=" +
            this.currentPage +
            "&size="+this.pagesize
        )
        .then((response) => {
          console.log(response);
          this.tableData = [];
          response.data.data.records.forEach((element) => {
            element.visible = true;
            this.tableData.push(element);
          });
          this.tableTotal = response.data.data.total;
          if(this.currentPage>Math.ceil(this.tableTotal/this.pagesize)){
            this.currentPage--;
            this.querytrains()
          }
        });
    },
    // 点击批量删除
    batchDelete() {
      this.$confirm("此操作将删除"+this.multipleSelection.length+"条数据, 是否继续?", "提示", {
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
              this.multipleSelection.forEach(element => {
                this.handleDelete(element)
              });
              // 清空multipleTable
              this.$refs.multipleTable.clearSelection();
              // 清空multipleSelection
              this.multipleSelection = [];
              this.querytrains()
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

    // 当pageSize改变时
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 当当前页发生改变时
    handleCurrentChange(val) {
      this.querytrains()
    },
    //点击表格方框，选中一条信息
    handleSelectionChange(val) {
      this.multipleSelection=val;
      console.log(this.multipleSelection);
      // 当选择信息时，判断是否可用批量删除
      this.hasAnySelection();
    },
    // 保留之前选中的数据
    rowKey(row) {
        return row.trainId
    },
    // 设置表格内每一行的样式，呈斑马条纹样式
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
    // 点击编辑按钮，隐藏编辑按钮，显示完成按钮和输入框
    handleEdit(index, row) {
      console.log(row);
      row.visible = !row.visible;
      // 设置其他行编辑按钮失效
      this.editShow = false;
    },
    // 点击取消按钮，显示编辑按钮，隐藏完成按钮和输入框，重新渲染数据
    handleCancel(index, row) {
      row.visible = !row.visible;
      this.querytrains();
      this.$message({
        type: "info",
        message: "取消修改!",
      });
      this.editShow = true;
    },
    // 点击完成按钮，显示编辑按钮，隐藏完成按钮和输入框，发送网络请求
    handleFinish(index, row) {
      this.editShow = true;
      row.visible = !row.visible;
      let data = {
        trainId: row.trainId,
        trainName: row.trainName,
        trainSpeed: row.trainSpeed,
      };
      console.log(data);
      axios
        .put("/train/" + row.trainId, data)
        .then((response) => {
          if (response.data.code == 200) {
            this.$notify({
              title: "成功",
              message: "修改成功",
              type: "success",
              duration: 1500,
            });
          } else {
            this.$notify({
              title: "失败",
              message: "修改失败",
              type: "error",
              duration: 1500,
            });
          }
          this.querytrains();
        });
    },
    // 点击删除按钮，删除一条信息
    handleDelete(row) {
      axios
        .delete("/train/" + row["trainId"])
        .then((response) => {
          console.log(response);
          if (response.data.code == "204") {
            this.input = "";
            this.$notify({
              title: "成功",
              message: "已删除"+row["trainId"],
              type: "success",
              duration: 1500,
            });
            this.$refs.multipleTable.clearSelection();
          }
          else{
            this.$notify({
              title: "失败",
              message: "删除失败",
              type: "error",
              duration: 1500,
            });
          }
          this.querytrains();
        });
    },
    // 查看是否有选中的信息
    hasAnySelection() {
      if (this.multipleSelection.length !== 0) {
        this.noAnySelection = false;
      } else {
        this.noAnySelection = true;
      }
    },
    // 清空typeForm表单
    clearTypeForm() {
      this.typeForm = {
        trainId: "",
        trainName: "",
        trainSpeed: "",
      };
    },
    // 离开表单确认事件
    handleClose(done) {
      this.$confirm("确定要离开，将不会保存信息？")
        .then((_) => {
          this.clearTypeForm();
          done();
        })
        .catch((_) => {});
    },
    // 取消表单事件
    cancelForm() {
      this.dialog = false;
    },
    // 提交表单事件
    submitForm(formName) {
      console.log(this.typeForm);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm("是否要提交添加列车类型？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "info",
          })
            .then(() => {
              console.log(this.typeForm);
              // 发送网络请求
              axios
                .post("/train", this.typeForm)
                .then((response) => {
                  console.log(response);
                  if (response.data.code == 201) {
                    this.$message({
                      type: "success",
                      message: "添加成功!",
                    });
                  } else {
                    this.$message({
                      type: "error",
                      message: "添加失败!",
                    });
                  }
                  this.querytrains()
                  this.clearTypeForm();
                  this.cancelForm();
                });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消添加",
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
  data() {
    return {
      pagesize: 4,
      // 分页栏中当前页
      currentPage: 1,
      // 表格中的数据
      tableData: [],
      // 数据总条数
      tableTotal: 0,
      // 选中的表格中的数据
      multipleSelection: [],
      // 批量删除是否可用
      noAnySelection: true,
      // 增加表单是否出现
      dialog: false,
      // 编辑是否可用
      editShow: true,
      // 增加的弹出框表单数据
      typeForm: {
        trainId: "",
        trainName: "",
        trainSpeed: "",
      },
      rules: {
        trainId: [
          { required: true, message: "请输入列车简称", trigger: "change" },
        ],
        trainName: [
          { required: true, message: "请输入列车全称", trigger: "change" },
        ],
        trainSpeed: [
          { required: true, message: "请输入列车速度", trigger: "change" },
        ],
      },
      timer: null,
    };
  },
};
</script>

<style scoped>
.formStyle {
  margin: 20px;
}
.formButtonStyle button {
  margin: 20px;
  width: calc(40%);
}
.tableTitle span {
  font-size: 14px;
}
.tableTitle button {
  float: right;
  margin: 0 20px;
}
.imgTrain {
  width: 40px;
  height: 40px;
}
.departPaging {
  margin: 20px 0;
  text-align: center;
}
.operateButton .el-button {
  margin: 0 10px;
}
.el-table .warning-row {
  background: rgb(84, 76, 63);
}

.el-table .success-row {
  background: #3ea209;
}
</style>