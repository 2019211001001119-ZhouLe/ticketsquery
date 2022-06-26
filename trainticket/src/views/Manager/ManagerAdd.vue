<template>
	<div>
		<div class="pageStyle">
			<el-card id="box-card">
				<el-header>
					<el-breadcrumb separator="/">
						<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
						<el-breadcrumb-item>列车管理</el-breadcrumb-item>
					</el-breadcrumb>
				</el-header>
				<el-row>
					<el-col :span="10">
						<el-input clearable v-model="input" suffix-icon="el-icon-trainlocation-outline" placeholder="请输入管理员账户">
						</el-input>
					</el-col>
					<el-col :span="1">
						<el-button @click="queryOne()" icon="el-icon-search" circle></el-button>
					</el-col>
					<el-col :span="1">
						<el-button @click="queryAdmin()" icon="el-icon-refresh" circle></el-button>
					</el-col>
				</el-row>
				<el-table :data="admin">
					<el-table-column prop="adminId" label="管理员账号">
					</el-table-column>
					<el-table-column prop="adminPwd" label="管理员密码">
					</el-table-column>
					<el-table-column prop="adminName" label="管理员昵称">
					</el-table-column>
					<el-table-column prop="permission" label="管理员权限">
					</el-table-column>
					<el-table-column>
						<template slot="header">
							<el-button type="primary" @click="handleAddClick()"><span class="el-icon-plus"></span> 添加管理员</el-button>
						</template>
						<template slot-scope="scope">
							<el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle>
							</el-button>
							<el-popconfirm title="确认删除这行吗?" @confirm="deleteAdmin(scope.$index, scope.row)">
								<el-button slot="reference" icon="el-icon-delete" circle>
								</el-button>
							</el-popconfirm>
						</template>
					</el-table-column>
				</el-table>
				<el-dialog title="编辑列车" :visible.sync="dialogVisible">
					<el-form :modle="editAdmin">
						<el-form-item label="管理员账号">
							<el-input v-model="editAdmin.adminId" :disabled="true"></el-input>
						</el-form-item>
						<el-form-item label="管理员密码">
							<el-input v-model="editAdmin.adminPwd"></el-input>
						</el-form-item>
						<el-form-item label="管理员昵称">
							<el-input v-model="editAdmin.adminName"></el-input>
						</el-form-item>
						<el-form-item label="管理员权限">
							<el-input v-model="editAdmin.permission"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button @click="handleEditSaveClick()">保存</el-button>
						</el-form-item>
					</el-form>
				</el-dialog>
				<el-dialog title="添加列车" :visible.sync="addVisible">
					<el-form :modle="newAdmin">
						<el-form-item label="管理员账号">
							<el-input v-model="newAdmin.adminId"></el-input>
						</el-form-item>
						<el-form-item label="管理员密码">
							<el-input v-model="newAdmin.adminPwd"></el-input>
						</el-form-item>
						<el-form-item label="管理员昵称">
							<el-input v-model="newAdmin.adminName"></el-input>
						</el-form-item>
						<el-form-item label="管理员权限">
							<el-input v-model="newAdmin.permission"></el-input>
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
			admin: [],
			editAdmin: [],
			newAdmin: {
				adminId: '',
				adminPwd: '',
				adminName: '',
				permission: ''
			},
			dialogVisible: false,
			deleteVisible: false,
			addVisible: false,
			keyword: ''
		}
	},
	mounted() {
		this.queryAdmin()
	},
	methods: {
		queryAdmin() {
			axios.get('/admin/all').then((response) => {
				this.admin = response['data']['data']
				console.log(response)
			})
		},

		updateAdmin(editAdmin) {
			axios.post(('/admin/update'), editAdmin).then((response) => {
				console.log(response)
				this.queryAdmin()
			})
		},

		deleteAdmin(index, row) {
			console.log(index)
			axios.delete('/admin/remove/' + row['adminId']).then((response) => {
				console.log(response)
				this.queryAdmin()
			})
		},

		addAdmin(newAdmin) {
			axios.post('/admin/add', newAdmin).then((response) => {
				console.log(response)
				this.queryAdmin()
			})
		},

		queryOne() {
			axios.get('/admin/getById/' + this.input).then((response) => {
				this.admin = [response.data.data]
			})
		},

		handleEditClick(index, row) {
			console.log(index)
			this.editAdmin = row
			this.dialogVisible = true
		},

		handleEditSaveClick() {
			this.updateAdmin(this.editAdmin)
			this.dialogVisible = false
		},

		handleAddClick() {
			this.addVisible = true
		},

		handleAddSaveClick() {
			this.addAdmin(this.newAdmin)
			this.addVisible = false
		}
	}
}
</script>
<style>
</style>
