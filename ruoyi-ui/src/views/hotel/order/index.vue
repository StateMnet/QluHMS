<script>
import { listOrder, getOrder, addOrder, updateOrder, delOrder, changeOrderStatus } from '@/api/hotel/order'
import { changeRoomStatus } from '@/api/hotel/room'

export default {
  name: 'Order',
  data() {
    return {
      // 订单状态字典
      orderStatus: {
        0: '未入住',
        1: '正在入住',
        2: '已退房'
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单表格数据
      orderList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomNumber: null,
        userName: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomNumber: [
          { required: true, message: '房间号不能为空', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        checkInTime: [
          { required: true, message: '入住时间不能为空', trigger: 'blur' }
        ],
        checkOutTime: [
          { required: true, message: '退房时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    statusTagType(status) {
      if (status == 0) return 'info'
      if (status == 1) return 'success'
      if (status == 2) return 'warning'
      return ''
    },
    /** 查询订单列表 */
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        roomNumber: null,
        userName: null,
        checkInTime: null,
        checkOutTime: null,
        remark: null,
        status: 0
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加订单'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改订单'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.orderId != undefined) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids
      this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项？').then(() => {
        return delOrder(orderIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    /** 状态变更操作 */
    async changeStatus(row, status) {
      try {
        await this.$modal.confirm('确认要将订单状态变更为"' + this.orderStatus[status] + '"吗？')
        // 1. 先变更订单状态
        await changeOrderStatus(row.orderId, status)
        // 2. 同步变更房间状态
        // 入住时（status==1）房间状态设为启用（1），退房时（status==2）房间状态设为停用（0）
        let roomStatus = status === 1 ? 1 : (status === 2 ? 0 : null)
        if (roomStatus !== null && row.roomId) {
          await changeRoomStatus(row.roomId, roomStatus)
        }
        this.$modal.msgSuccess('状态变更成功')
        this.getList()
      } catch (e) {
        // 可选：错误处理
      }
    },
    formatDateTime(val) {
      if (!val) return ''
      const date = new Date(val)
      const y = date.getFullYear()
      const m = (date.getMonth() + 1).toString().padStart(2, '0')
      const d = date.getDate().toString().padStart(2, '0')
      const hh = date.getHours().toString().padStart(2, '0')
      const mm = date.getMinutes().toString().padStart(2, '0')
      return `${y}-${m}-${d} ${hh}:${mm}`
    }
  }
}
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="房间号" prop="roomNumber">
        <el-input
          v-model="queryParams.roomNumber"
          placeholder="请输入房间号"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="订单状态" clearable style="width: 200px">
          <el-option v-for="(label, value) in orderStatus" :key="value" :label="label" :value="value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" prop="orderId" width="120" />
      <el-table-column label="房间号" prop="roomNumber" width="120" />
      <el-table-column label="用户" prop="userName" width="120" />
      <el-table-column label="入住时间" prop="checkInTime" width="150">
        <template slot-scope="scope">
          <span>{{ formatDateTime(scope.row.checkInTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="退房时间" prop="checkOutTime" width="150">
        <template slot-scope="scope">
          <span>{{ formatDateTime(scope.row.checkOutTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" width="120">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.status)">{{ orderStatus[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button size="mini" type="primary" v-if="scope.row.status == 0" @click="changeStatus(scope.row, 1)">入住</el-button>
          <el-button size="mini" type="warning" v-if="scope.row.status == 1" @click="changeStatus(scope.row, 2)">退房</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="form.roomNumber" placeholder="请输入房间号" />
        </el-form-item>
        <el-form-item label="用户" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="入住时间" prop="checkInTime">
          <el-date-picker v-model="form.checkInTime" type="datetime" placeholder="选择日期和时间" style="width: 100%" />
        </el-form-item>
        <el-form-item label="退房时间" prop="checkOutTime">
          <el-date-picker v-model="form.checkOutTime" type="datetime" placeholder="选择日期和时间" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.mb8 {
  margin-bottom: 8px;
}
</style>
