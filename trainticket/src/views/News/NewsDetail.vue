<template>
  <div class="pageStyle">
    <p class="titleCap">车次管理</p>
    <el-card class="box-card">
      <el-row>
        <el-col :span="20">
          <el-input
            placeholder="请输入站点名进行搜索，可以直接回车搜索..."
            @clear="queryAllDetails"
            style="width: 350px; margin-left: 10px"
            clearable
            :clear="queryAllDetails"
            v-model="keywords"
            @keydown.enter.native="onSearch"
          >
            <i
              slot="suffix"
              class="el-icon-search"
              style="margin-top: 13px; cursor: pointer"
              @click="onSearch"
            ></i>
          </el-input>
        </el-col>
        <el-button
          @click="batchDelete(ids, $event)"
          type="danger"
          size="medium"
          :disabled="isDisable"
          >X批量删除</el-button
        >
      </el-row>
      <!--表格开始-->
      <el-table
        :data="details"
        style="width: 100%"
        stripe
        tooltip-effect="dark"
        :row-class-name="tableRowClassName"
        :row-key="rowKey"
        @selection-change="handleSelectionChange"
        ref="multipleTable"
      >
        <el-table-column
          :reserve-selection="true"
          type="selection"
          width="65"
        ></el-table-column>
        <el-table-column
          :reserve-selection="true"
          prop="routertrainId"
          label="车次号"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :reserve-selection="true"
          prop="routerdetailId"
          label="站序"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :reserve-selection="true"
          prop="trainstationName"
          label="火车站名称"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :reserve-selection="true"
          prop="laterTime"
          label="晚点时间"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :reserve-selection="true"
          prop="adminName"
          label="发布人"
          align="center"
        >
        </el-table-column>
        <el-table-column :reserve-selection="true" label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              @click="updateDetail(scope.row)"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-popconfirm
              title="确认删除这行吗?"
              @confirm="deleteDetail(scope.row)"
            >
              <el-button
                slot="reference"
                size="small"
                icon="el-icon-delete"
                circle
              ></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页栏 -->
      <div style="text-align: center">
        <el-pagination
          background
          hide-on-single-page
          layout="sizes, prev, pager, next, jumper, ->, total, slot"
          @current-change="currentChange"
          @size-change="sizeChange"
          :total="total"
        >
        </el-pagination>
      </div>
      <!--表格结束-->
      <!--弹出层开始-->
      <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="45%">
        <el-form ref="form" :model="detail" label-width="90px">
          <el-form-item label="车次号">
            <el-input
              v-model="detail.routertrainId"
              :disabled="true"
            ></el-input>
          </el-form-item>
          <el-form-item label="站序">
            <el-input
              v-model="detail.routerdetailId"
              :disabled="true"
            ></el-input>
          </el-form-item>
          <el-form-item label="火车站编号">
            <el-input v-model="detail.trainstationId"></el-input>
          </el-form-item>
          <el-form-item label="晚点状态">
            <el-input v-model="detail.routerdetailStatus"></el-input>
          </el-form-item>
          <el-form-item label="晚点时间">
            <el-input v-model="detail.laterTime"></el-input>
          </el-form-item>
          <el-form-item label="发布人编号">
            <el-input v-model="detail.adminName"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--弹出层结束-->
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Home",
  data() {
    return {
      keywords: "",
      details: [], // 显示数据
      total: 0,
      dialogTitle: "添加",
      dialogVisible: false,
      size: 10, // 分页每页10条数据
      current: 1, // 从第一页开始
      detail: [], // 存放编辑信息
      ids: [], // 存放删除的数据
      multipleSelection: [], // 多选的数据
      isDisable: true, //是否禁用
    };
  },
  methods: {
    // 分页
    currentChange(currentChange) {
      this.current = currentChange;
      this.queryAllDetails();
    },
    sizeChange(sizeChange) {
      this.size = sizeChange;
      this.queryAllDetails();
    },
    /**
     * /newsdetails  请求地址
     */
    queryAllDetails() {
      // 调用get请求
      axios
        .get("/newsdetails?current=" + this.current + "&size=" + this.size)
        .then((res) => {
          console.log(res);
          this.total = res.data.total;
          this.details = res.data.records;
        });
    },
    updateDetail(data) {
      // 填充数据  数据回显
      this.detail = data;
      console.log(this.detail);
      // 打开弹出层
      this.dialogVisible = true;
      // 改变弹出成title
      this.dialogTitle = "修改";
    },
    onSubmit() {
      console.log(this.detail);
      // 修改
      // 调用put请求
      axios.put("/details/", this.detail).then((res) => {
        console.log(res);
        this.dialogVisible = false;
        // 修改成功刷新数据
        if (res.data.code == "200") {
          this.$notify({
            title: "success",
            message: "修改成功",
            type: "success",
            duration: 1500,
          });
          this.queryAllDetails();
        }
      });
    },
    deleteDetail(data) {
      console.log(data);
      axios
        .delete("/details/" + data.routertrainId + "/" + data.trainstationId)
        .then((res) => {
          console.log(res);
          if (res.data.code == "204") {
            console.log(res);
            this.$message({
              message: "删除成功",
              type: "success",
              center: false,
              duration: 1500,
            });
            this.queryAllDetails();
          } else {
            this.$message({
              message: "删除失败",
              type: "error",
              center: false,
              duration: 1500,
            });
          }
        });
    },
    // 条件查询
    onSearch() {
      if (this.keywords) {
        axios
          .get(
            "/newdetailsbycondition?current=" +
              this.current +
              "&size=" +
              this.size +
              "&routertrainId=" +
              this.keywords
          )
          .then((res) => {
            console.log(res);
            this.details = res.data.records;
            this.total = res.data.total;
          });
      } else {
        this.$message({
          message: "请输入搜索内容",
          center: true,
          showClose: true,
          type: "warning",
        });
      }
    },
    // 多选
    handleSelectionChange(selection) {
      console.log(selection);
      this.ids = selection.map((item) => item.routerdetailId);
      // console.log(this.ids)
      this.multipleSelectionFlag = true;
      if (this.multipleSelection.length == 0) {
        // 如不进行判断则勾选完毕后批量删除按钮还是会在
        this.multipleSelectionFlag = false;
      }
      if (this.ids.length != 0) {
        this.isDisable = false;
      } else {
        this.isDisable = true;
      }
    },
    // 批量删除
    batchDelete(rows, $event) {
      let target = $event.target;
      if (target.nodeName == "I") {
        target = $event.target.parentNode;
      }
      target.blur();
      let that = this;
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
              .delete("http://127.0.0.1:8888/newdetails/" + data)
              .then((res) => {
                console.log(res);
                if (res.data.code == "204") {
                  that.$message({
                    message: "删除成功",
                    type: "success",
                    duration: 1500,
                  });
                  that.queryAllDetails();
                }
              });
          })
          .catch(() => {
            that.$refs.multipleTable.clearSelection();
          });
      } else {
        that.$message({
          message: "请选择要删除的数据",
          center: true,
          duration: 1500,
          showClose: true,
        });
      }
    },

    // 保留之前选中的数据
    rowKey(row) {
      return row.trainstationId;
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
  // 初始化数据
  mounted() {
    this.queryAllDetails();
  },
};
</script>
<style>
</style>
