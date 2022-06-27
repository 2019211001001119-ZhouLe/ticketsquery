<template>
	<div>
		<div class="pageStyle">
			<p class="titleCap">管理员管理</p>
			<el-card id="box-card">
				<el-row>
					<el-col :span="10">
						<el-input clearable v-model="input" suffix-icon="el-icon-trainlocation-outline"
							placeholder="请输入管理员账户">
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
					<el-table-column prop="adminName" label="管理员昵称">
					</el-table-column>
					<el-table-column prop="permission" label="管理员权限">
					</el-table-column>
					<el-table-column>
						<template slot="header">
							<el-button type="primary" @click="handleAddClick()"><span class="el-icon-plus"></span> 添加管理员
							</el-button>
						</template>
						<template slot-scope="scope">
							<el-button @click="handleEditClick(scope.$index, scope.row)" icon="el-icon-edit" circle>
							</el-button>
							<el-popconfirm title="确认删除这行吗?" @confirm="deleteAdmin(scope.$index, scope.row)">
								<el-button slot="reference" icon="el-icon-delete" type="danger" circle>
								</el-button>
							</el-popconfirm>
						</template>
					</el-table-column>
				</el-table>
				<div class="block">
					<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
						:current-page.sync="currentPage" :page-size="pagesize" layout="prev, pager, next, jumper"
						:total="total" class="departPaging">
					</el-pagination>
				</div>
				<el-dialog title="编辑管理员" :visible.sync="dialogVisible">
					<el-form :model="editAdmin" :rules="rule" ref="editAdmin">
						<el-form-item label="管理员账号" prop="adminId">
							<el-input v-model="editAdmin.adminId" :disabled="true"></el-input>
						</el-form-item>
						<el-form-item label="管理员密码" prop="adminPwd">
							<el-input v-model="editAdmin.adminPwd"></el-input>
						</el-form-item>
						<el-form-item label="管理员昵称" prop="adminName">
							<el-input v-model="editAdmin.adminName"></el-input>
						</el-form-item>
						<el-form-item label="管理员权限" prop="permission">
							<el-input v-model="editAdmin.permission"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button @click="handleEditSaveClick(editAdmin)">保存</el-button>
						</el-form-item>
					</el-form>
				</el-dialog>
				<el-dialog title="添加管理员" :visible.sync="addVisible">
					<el-form :model="newAdmin" :rules="rule" ref="newAdmin">
						<el-form-item label="管理员密码" prop="adminPwd">
							<el-input type="password" v-model="newAdmin.adminPwd"></el-input>
						</el-form-item>
						<el-form-item label="管理员昵称" prop="adminName">
							<el-input v-model="newAdmin.adminName"></el-input>
						</el-form-item>
						<el-form-item label="管理员权限" prop="permission">
							<el-input v-model="newAdmin.permission"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button @click="handleAddSaveClick(newAdmin)">保存</el-button>
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
			pagesize: 4,
			// 总数据条数
			total: 0,
			// 分页栏中当前页
			currentPage: 1,
			trainstations: [], // 存放所有车站信息
			trainstation: [], // 存放编辑车站信息
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
			keyword: '',
			rule: {
				adminPwd: [
					{ required: true, message: "请输入密码", trigger: "blur" },
					{ min: 6, message: "密码不小于6位", trigger: "blur" }
				],

				adminName: [
					{ required: true, message: "请输入昵称", trigger: "blur" }
				],
				permission: [
					{ required: true, message: "请输入权限", trigger: "blur" },
					{
						type: 'enum',
						enum: ['1', '2'],
						message: "请输入1或2",
						trigger: "blur"
					}
				]
			},
		}
	},
	mounted() {
		this.queryAdmin()
	},
	methods: {
		handleSizeChange(val) {
			console.log(`每页 ${val} 条`);
		},
		// 当当前页发生改变时
		handleCurrentChange(val) {
			console.log(`当前页: ${val}`);
			this.queryAdmin();
		},
		queryAdmin() {
			axios.get('/admin/page?current='+this.currentPage+'&size='+this.pagesize).then((response) => {
				this.admin = response.data.data.records
				this.total = response.data.data.total;
				console.log(this.total)
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
			row.adminPwd = ''
			this.editAdmin = row
			this.dialogVisible = true
		},

		handleEditSaveClick(editAdmin) {
			this.$refs.editAdmin.validate((valid) => {  //开启校验
				if (valid) {   // 如果校验通过，请求接口，允许提交表单
					this.updateAdmin(editAdmin)
					this.dialogVisible = false
				} else {   //校验不通过
					return false;
				}
			});
		},

		handleAddClick() {
			this.addVisible = true
		},

		handleAddSaveClick(newAdmin) {
			this.$refs.newAdmin.validate((valid) => {  //开启校验
				if (valid) {   // 如果校验通过，请求接口，允许提交表单
					this.addAdmin(newAdmin)
					this.addVisible = false
				} else {   //校验不通过
					return false;
				}
			});

		}
	}
}
</script>
<style>
</style>
