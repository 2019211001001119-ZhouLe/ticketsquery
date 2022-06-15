<template>
    <el-container>
        <el-header></el-header>
        <el-table :data="trains">
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
                    <el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle></el-button>
                    <el-button @click="handleDeleteClick(scope.$index, scope.row)" icon="el-icon-delete" circle></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button @click="queryTrains">刷新表格</el-button>
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
        <el-dialog title="删除" :visible.sync="deleteVisible">
            <el-button @click="handleDeleteConfirmClick(train)" type="danger">确认删除</el-button>
        </el-dialog>
    </el-container>
</template>
<script>
import axios from 'axios'
export default {
    data() {
        return {
            trains: [],
            train: [],
            newTrain: {
                trainId : "",
                trainName : "",
                trainSpeed : ""
            },
            dialogVisible: false,
            deleteVisible: false,
            addVisible: false
        }
    },
    mounted() {
        this.queryTrains()
    },
    methods: {
        queryTrains() {
            axios.get("http://127.0.0.1:8888/train").then((response) => {
                this.trains = response["data"]["data"]
                console.log(response)
            })
        },

        saveTrain(train) {
            axios.put(("http://127.0.0.1:8888/train/" + train["trainId"]), train).then((response) => {
                console.log(response)
                this.queryTrains()
            })
        },

        deleteTrain(train) {
            axios.delete("http://127.0.0.1:8888/train/" + train["trainId"]).then((response) => {
                console.log(response)
                this.queryTrains()
            })
        },

        addTrain(train) {
            axios.post("http://127.0.0.1:8888/train", train).then((response) => {
                console.log(response)
                this.queryTrains()
            })
        },

        handleEditClick(index, row) {
            console.log(index)
            this.train = row
            this.dialogVisible = true
        },

        handleEditSaveClick(train) {
            this.saveTrain(train)
            this.dialogVisible = false
        },

        handleDeleteClick(index, row) {
            console.log(index)
            this.train = row
            this.deleteVisible = true
        },

        handleDeleteConfirmClick(train) {
            this.deleteVisible = false
            this.deleteTrain(train)
        },

        handleAddClick() {
            this.addVisible = true
        },

        handleAddSaveClick(train) {
            this.addTrain(train)
            this.addVisible = false
        }
    },
}
</script>