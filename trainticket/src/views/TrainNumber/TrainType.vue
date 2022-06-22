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
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="" width="120" prop="imageT">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">
                <img :src="scope.row.img" alt="" class="imgTrain" />
              </span>
              <el-input v-else v-model="scope.row.img"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="fullName" label="列车全称" width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.fullName }}</span>
              <el-input v-else v-model="scope.row.fullName"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop="easyName"
            label="列车简称"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.easyName }}</span>
              <el-input v-else v-model="scope.row.easyName"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop="trainSpeed"
            label="列车速度"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span v-if="scope.row.visible">{{ scope.row.trainSpeed }}</span>
              <el-input v-else v-model="scope.row.trainSpeed"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" class="operateButton">
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
                title="确定删除这条数据吗？"
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
        <!-- 分页栏 -->
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
              <el-form-item label="图片url" label-width="80px" prop="img">
                <el-input v-model="typeForm.img" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="列车全称" label-width="80px" prop="fullName">
                <el-input
                  v-model="typeForm.fullName"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="列车简称" label-width="80px" prop="easyName">
                <el-input
                  v-model="typeForm.easyName"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="列车速度"
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
export default {
  methods: {
    // 点击批量删除
    batchDelete() {
      this.$confirm("此操作将删除多条, 是否继续?", "提示", {
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
              // 设置参数
              let data = this.multipleSelection;
              // 发送网络请求
              // 赋值操作
              // 清空multipleSelection
              this.multipleSelection = [];
              // 重新获取数据 handleCurrentChange()方法
              this.$message({
                type: "success",
                message: "删除成功!",
              });
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
      console.log(`当前页: ${val}`);
      // 获取当前页,设置参数
      let data = {
        currentPage: this.currentPage,
      };
      // 发送网络请求
      // 将请求到的数据赋值给tableData
    },
    //点击表格方框，选中一条信息
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // 当选择信息时，判断是否可用批量删除
      this.hasAnySelection();
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
    // 点击编辑按钮，隐藏编辑按钮，显示完成按钮和输入框
    handleEdit(index, row) {
      row.visible = !row.visible;
      console.log(this.tableData[index].visible);
    },
    // 点击删除按钮，删除一条信息
    handleDelete(index, row) {
      this.tableData.splice(index, 1);
      // 设置参数
      let data = {
        row,
      };
      // 发送网络请求
      // 重新获取数据 handleCurrentChange()方法
    },
    // 查看是否有选中的信息
    hasAnySelection() {
      if (this.multipleSelection.length !== 0) {
        this.noAnySelection = false;
      } else {
        this.noAnySelection = true;
      }
    },
    // 清空typeForm表单事件
    clearTypeForm() {
      this.typeForm = {
        img: "",
        fullName: "",
        easyName: "",
        trainSpeed: "",
      };
    },
    // 离开表单确认事件
    handleClose(done) {
      this.$confirm("确定要离开，将不会保存信息？")
        .then((_) => {
          this.clearTypeForm()
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
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm("是否要提交添加列车类型？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "info",
          })
            .then(() => {
              this.clearTypeForm()
              this.cancelForm()
              // 设置参数
              // 发送网络请求
              this.$message({
                type: "success",
                message: "添加成功!",
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
      // 分页栏中当前页
      currentPage: 1,
      // 表格中的数据
      tableData: [
        {
          img: "https://pic.baike.soso.com/ugc/baikepic2/4228/20210521133105-923552370_jpeg_1623_1080_726743.jpg/300",
          fullName: "王小虎",
          easyName: "上海市普陀区金沙江路 1518 弄",
          trainSpeed: 100,
          visible: true,
        },
        {
          img: "https://pic.baike.soso.com/ugc/baikepic2/4228/20210521133105-923552370_jpeg_1623_1080_726743.jpg/300",
          fullName: "王小虎",
          easyName: "上海市普陀区金沙江路 1518 弄",
          trainSpeed: 100,
          visible: true,
        },
        {
          img: "https://pic.baike.soso.com/ugc/baikepic2/4228/20210521133105-923552370_jpeg_1623_1080_726743.jpg/300",
          fullName: "王小虎",
          easyName: "上海市普陀区金沙江路 1518 弄",
          trainSpeed: 100,
          visible: true,
        },
        {
          img: "https://pic.baike.soso.com/ugc/baikepic2/4228/20210521133105-923552370_jpeg_1623_1080_726743.jpg/300",
          fullName: "王小虎",
          easyName: "上海市普陀区金沙江路 1518 弄",
          trainSpeed: 100,
          visible: true,
        },
        {
          img: "https://pic.baike.soso.com/ugc/baikepic2/4228/20210521133105-923552370_jpeg_1623_1080_726743.jpg/300",
          fullName: "王小虎",
          easyName: "上海市普陀区金沙江路 1518 弄",
          trainSpeed: 100,
          visible: true,
        },
      ],
      // 选中的表格中的数据
      multipleSelection: [],
      // 批量删除是否可用
      noAnySelection: true,
      dialog: false,
      loading: false,
      typeForm: {
        img: "",
        fullName: "",
        easyName: "",
        trainSpeed: "",
      },
      rules: {
        img: [{ required: true, message: "请输入图片的url", trigger: "blur" }],
        fullName: [
          { required: true, message: "请输入列车全称", trigger: "change" },
        ],
        easyName: [
          { required: true, message: "请输入列车简称", trigger: "change" },
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