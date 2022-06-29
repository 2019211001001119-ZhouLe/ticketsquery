<template>
    <div>
        <div class="pageStyle">
            <p class="titleCap">最新发布</p>
            <el-card id="box-card">
                <el-row>
                    <el-col :span="10">
                        <el-input clearable v-model="input" suffix-icon="el-icon-trainlocation-outline"
                            placeholder="请输入新闻ID">
                        </el-input>
                    </el-col>
                    <el-col :span="1">
                        <el-button @click="queryOne()" icon="el-icon-search" circle></el-button>
                    </el-col>
                    <el-col :span="1">
                        <el-button @click="queryNews()" icon="el-icon-refresh" circle></el-button>
                    </el-col>
                </el-row>
                <el-table :data="news">
                    <el-table-column prop="newsId" label="新闻ID">
                    </el-table-column>
                    <el-table-column prop="adminId" label="管理员ID">
                    </el-table-column>
                    <el-table-column prop="newsTitle" label="新闻标题">
                    </el-table-column>
                    <el-table-column prop="newsPublishTime" label="新闻发布日期">
                    </el-table-column>
                    <el-table-column prop="newsUrl" label="URL">
                    </el-table-column>
                    <el-table-column>
                        <template slot="header">
                            <el-button type="primary" @click="handleAddClick()"><span class="el-icon-plus"></span> 添加新闻
                            </el-button>
                        </template>
                        <template slot-scope="scope">
                            <el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle>
                            </el-button>
                            <el-popconfirm title="确认删除这行吗?" @confirm="deleteNews(scope.$index, scope.row)">
                                <el-button slot="reference" type="danger" icon="el-icon-delete" circle>
                                </el-button>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page.sync="current" :page-size="size" layout="prev, pager, next, jumper" :total="total"
                    class="departPaging">
                </el-pagination>
                <el-dialog title="编辑新闻" :visible.sync="dialogVisible">
                    <el-form :model="editNews" ref="editNews" :rules="rule">
                        <el-form-item label="新闻ID">
                            <el-input v-model="editNews.newsId" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="管理员ID" prop="adminId">
                            <el-select v-model="editNews.adminId" placeholder="请选择" :disabled="disable">
                                <el-option v-for="item in adminOptions" :key="item.adminId" :label="item.adminName"
                                    :value="item.adminId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="新闻标题" prop="newsTitle">
                            <el-input v-model="editNews.newsTitle"></el-input>
                        </el-form-item>
                        <el-form-item label="新闻发布时间" prop="newsPublishTime">
                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="editNews.newsPublishTime"
                                type="datetime" placeholder="选择日期时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="URL">
                            <el-input v-model="editNews.newsUrl"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="handleEditSaveClick()">保存</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
                <el-dialog title="添加新闻" :visible.sync="addVisible">
                    <el-form :model="newNews" ref="newNews" :rules="rule">
                        <el-form-item label="管理员ID" prop="adminId">
                            <el-select v-model="admin.adminId" placeholder="请选择" :disabled="disable">
                                <el-option v-for="item in adminOptions" :key="item.adminId" :label="item.adminName"
                                    :value="item.adminId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="新闻标题" prop="newsTitle">
                            <el-input v-model="newNews.newsTitle"></el-input>
                        </el-form-item>
                        <el-form-item label="新闻发布时间" prop="newsPublishTime">
                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="newNews.newsPublishTime"
                                type="datetime" placeholder="选择日期时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="URL">
                            <el-input v-model="newNews.newsUrl"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="handleAddSaveClick()">保存</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
            </el-card>
        </div>
    </div>
</template>
<script>

import axios from 'axios'
import { delToken, getToken } from "../../utils/tokenUtils";
export default {

    data() {
        return {
            input: '',
            news: [],
            editNews: [],
            adminOptions: [],
            admin: [],
            disable: true,
            newNews: {
                newsId: '',
                adminId: '',
                newsTitle: '',
                newsPublishTime: '',
                newsUrl: ''
            },
            dialogVisible: false,
            deleteVisible: false,
            addVisible: false,
            keyword: '',
            current: 1,
            size: 4,
            total: 0,
            rule: {
                newsTitle: [
                    { required: true, message: "未填写新闻标题", trigger: "blur" },
                    { min: 3, max: 25, message: "新闻标题长度必须在3-25之间" }
                ],
                newsPublishTime: [
                    { required: true, message: "未填写新闻发布时间", trigger: "blur" }
                ]
            }
        }
    },
    mounted() {
        this.queryNews()
        axios.get("/admin/all").then(res => {
            this.adminOptions = res['data']['data']
        })
        axios.get("/admin/getById/" + getToken()).then(res => {
            console.log("res:")
            this.admin = res['data']['data']
            if (this.admin.permission == '1')
                this.disable = false
        })
    },
    methods: {
        queryNews() {
            axios.get('/news/page?current=' + this.current + '&size=' + this.size).then((response) => {
                this.news = response['data']['data']['records']
                this.total = response['data']['data']['total']
                console.log(response['data']['data'])
            })
        },

        updateNews(editNews) {
            axios.post(('/news/update'), editNews).then((response) => {
                console.log(response)
                this.queryNews()
            })
        },

        deleteNews(index, row) {
            if (!(this.admin.adminId == row.adminId || this.disable == false)) {
                this.$message({
                    title: "失败",
                    message: "不能删除他人发布信息",
                    type: "error",
                    duration: 1500,
                });
                return false
            }
            console.log(index)
            axios.delete('/news/delete/' + row['newsId']).then((response) => {
                console.log(response)
                this.queryNews()
            })
        },

        addNews(newNews) {
            axios.post('/news/add', newNews).then((response) => {
                console.log(response)
                this.queryNews()
            })
        },

        queryOne() {
            axios.get('/news/' + this.input).then((response) => {
                this.news = [response.data.data]
            })
        },

        handleEditClick(index, row) {
            if (!(this.admin.adminId == row.adminId || this.disable == false)) {
                this.$message({
                    title: "失败",
                    message: "不能修改他人发布信息",
                    type: "error",
                    duration: 1500,
                });
                return false
            }
            console.log(index)
            this.editNews.adminId = this.admin.adminId
            this.editNews = row
            this.dialogVisible = true
        },

        handleEditSaveClick() {
            this.$refs.editNews.validate((valid) => {  //开启校验
                if (valid) {   // 如果校验通过，请求接口，允许提交表单
                    this.updateNews(this.editNews)
                    this.dialogVisible = false
                    this.$message({
                        title: "成功",
                        message: "修改成功",
                        type: "success",
                        duration: 1500,
                    });
                } else {   //校验不通过
                    this.$message({
                        title: "失败",
                        message: "校验未通过",
                        type: "error",
                        duration: 1500,
                    });
                    return false;
                }
            });
        },

        handleAddClick() {
            this.addVisible = true
            this.newNews.adminId = this.admin.adminId
        },

        handleAddSaveClick() {
            this.$refs.newNews.validate((valid) => {  //开启校验
                if (valid) {   // 如果校验通过，请求接口，允许提交表单
                    this.addNews(this.newNews)
                    this.addVisible = false
                    this.$message({
                        title: "成功",
                        message: "修改成功",
                        type: "success",
                        duration: 1500,
                    });
                } else {   //校验不通过
                    this.$message({
                        title: "失败",
                        message: "校验未通过",
                        type: "error",
                        duration: 1500,
                    });
                    return false;
                }
            });
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.queryNews();
        },
    }
}
</script>
<style>
</style>
