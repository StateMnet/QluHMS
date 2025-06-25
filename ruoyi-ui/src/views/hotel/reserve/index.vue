<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="房型名称" prop="name">
        <el-select
          v-model="queryParams.name"
          placeholder="请选择房型名称"
          clearable
          style="width: 240px">
          <el-option
            v-for="item in typeOptions"
            :key="item.typeId"
            :label="item.name"
            :value="item.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="最大入住人数" prop="capacity">
        <el-input
          v-model="queryParams.capacity"
          placeholder="请输入最大入住人数"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="房型名称" prop="name" width="150" />
      <el-table-column label="设施" prop="amenities" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="最大入住人数" prop="capacity" width="120" />
      <el-table-column label="图片" prop="imageUrl" width="120">
        <template slot-scope="scope">
          <img v-if="scope.row.imageUrl" :src="scope.row.imageUrl" alt="图片" style="width: 60px; height: 40px; object-fit: cover;" />
        </template>
      </el-table-column>
      <el-table-column label="选择" align="center" width="100">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleSelect(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="orderDialogVisible" title="填写预定信息" width="600px">
      <el-form :model="orderForm" :rules="orderRules" ref="orderForm" label-width="90px">
        <el-form-item label="房型" prop="roomTypeName">
          <el-input v-model="orderForm.roomTypeName" disabled />
        </el-form-item>
        <el-form-item label="入住时间" prop="checkInTime">
          <el-date-picker v-model="orderForm.checkInTime" type="datetime" placeholder="选择入住时间" style="width:100%" />
        </el-form-item>
        <el-form-item label="退房时间" prop="checkOutTime">
          <el-date-picker v-model="orderForm.checkOutTime" type="datetime" placeholder="选择退房时间" style="width:100%" />
        </el-form-item>
        <el-form-item label="入住人数" prop="peopleNum">
          <el-input-number v-model="orderForm.peopleNum" :min="1" :max="orderForm.capacity || 10" />
        </el-form-item>
        <el-form-item label="同行人" prop="othersName">
          <el-input v-model="orderForm.othersName" placeholder="请输入同行人姓名（可选）" />
        </el-form-item>
        <el-form-item label="预定人姓名" prop="userName">
          <el-input v-model="orderForm.userName" placeholder="请输入预定人姓名" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="orderDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitOrder">提交预定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listType, getAllTypes } from '@/api/hotel/type'
import { addOrder } from '@/api/hotel/order'

export default {
  name: 'ReserveRoomType',
  data() {
    return {
      loading: true,
      showSearch: true,
      typeList: [],
      typeOptions: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        capacity: null
      },
      selected: null,
      orderDialogVisible: false,
      orderForm: {
        userId: '',
        reserveId: '',
        userName: '',
        roomType: null,
        roomTypeName: '',
        checkInTime: '',
        checkOutTime: '',
        peopleNum: 1,
        othersName: '',
        status: 0,
        capacity: 0
      },
      orderRules: {
        userName: [{ required: true, message: '请输入预定人姓名', trigger: 'blur' }],
        checkInTime: [{ required: true, message: '请选择入住时间', trigger: 'blur' }],
        checkOutTime: [{ required: true, message: '请选择退房时间', trigger: 'blur' }],
        peopleNum: [{ required: true, message: '请输入入住人数', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
    this.getTypeOptions()
  },
  methods: {
    getList() {
      this.loading = true
      listType(this.queryParams).then(response => {
        this.typeList = response.rows
        this.loading = false
      })
    },
    getTypeOptions() {
      getAllTypes().then(res => {
        this.typeOptions = res.data || []
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.queryParams.name = null
      this.queryParams.capacity = null
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.selected = selection
    },
    handleSelect(row) {
      // 获取当前用户id和姓名，优先从getters取
      const userId = this.$store.getters.userId || this.$store.state.user.userId || ''
      const userName = this.$store.getters.name || this.$store.state.user.name || ''
      this.orderForm = {
        userId: userId,
        reserveId: userId,
        userName: userName,
        roomType: row.typeId,
        roomTypeName: row.name,
        checkInTime: '',
        checkOutTime: '',
        peopleNum: 1,
        othersName: '',
        status: 0,
        capacity: row.capacity
      }
      this.orderDialogVisible = true
    },
    submitOrder() {
      this.$refs.orderForm.validate(valid => {
        if (!valid) return
        // 只传需要的字段
        const data = {
          userId: this.orderForm.userId,
          reserveId: this.orderForm.reserveId,
          userName: this.orderForm.userName,
          roomType: this.orderForm.roomType,
          checkInTime: this.orderForm.checkInTime,
          checkOutTime: this.orderForm.checkOutTime,
          peopleNum: this.orderForm.peopleNum,
          othersName: this.orderForm.othersName,
          status: 0
        }
        addOrder(data).then(res => {
          this.$message.success('预定成功')
          this.orderDialogVisible = false
        })
      })
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style> 