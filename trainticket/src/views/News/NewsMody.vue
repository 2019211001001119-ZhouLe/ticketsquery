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
                <el-dialog title="编辑列车" :visible.sync="dialogVisible">
                    <el-form :modle="editNews">
                        <el-form-item label="新闻ID">
                            <el-input v-model="editNews.newsId" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="管理员ID">
                            <el-select v-model="editNews.adminId" placeholder="请选择">
                                <el-option v-for="item in adminOptions" :key="item.adminId" :label="item.adminName"
                                    :value="item.adminId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="新闻标题">
                            <el-input v-model="editNews.newsTitle"></el-input>
                        </el-form-item>
                        <el-form-item label="新闻发布时间">
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
                    <el-form :modle="newNews">
                        <el-form-item label="新闻ID">
                            <el-input v-model="newNews.newsId"></el-input>
                        </el-form-item>
                        <el-form-item label="管理员ID">
                            <el-input v-model="newNews.adminId"></el-input>
                        </el-form-item>
                        <el-form-item label="新闻标题">
                            <el-input v-model="newNews.newsTitle"></el-input>
                        </el-form-item>
                        <el-form-item label="新闻发布时间">
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

export default {

    data() {
        return {
            input: '',
            news: [],
            editNews: [],
            adminOptions: [],
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
            keyword: ''
        }
    },
    mounted() {
        this.queryNews()
        axios.get("/admin/all").then(res=>{
            this.adminOptions = res['data']['data']
        })
    },
    methods: {
        queryNews() {
            axios.get('/news/all').then((response) => {
                this.news = response['data']['data']
                console.log(response)
            })
        },

        updateNews(editNews) {
            axios.post(('/news/update'), editNews).then((response) => {
                console.log(response)
                this.queryNews()
            })
        },

        deleteNews(index, row) {
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
            console.log(index)
            this.editNews = row
            this.dialogVisible = true
        },

        handleEditSaveClick() {
            this.updateNews(this.editNews)
            this.dialogVisible = false
        },

        handleAddClick() {
            this.addVisible = true
        },

        handleAddSaveClick() {
            this.addNews(this.newNews)
            this.addVisible = false
        }
    }
}
</script>
<style>
</style>
