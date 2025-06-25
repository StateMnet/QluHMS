<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="房间类型" prop="roomType">
        <el-select
          v-model="queryParams.roomType"
          placeholder="请选择房间类型"
          clearable
          style="width: 240px"
        >

          <el-option
            v-for="(value, key) in hotel_room_type"
            :key="key"
            :label="value"
            :value="key"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="房间号" prop="roomNumber">
        <el-input
          v-model="queryParams.roomNumber"
          placeholder="请输入房间号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="房间状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.hotel_room_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />

        </el-select>
      </el-form-item>
<!--      <el-form-item label="创建时间">-->
<!--        <el-date-picker-->
<!--          v-model="dateRange"-->
<!--          style="width: 240px"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          type="daterange"-->
<!--          range-separator="-"-->
<!--          start-placeholder="开始日期"-->
<!--          end-placeholder="结束日期"-->
<!--        ></el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['hotel:room:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hotel:room:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['hotel:room:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="房间编号" prop="roomId" width="150" />
      <el-table-column label="房间类型" prop="roomTypeName" width="150">
      </el-table-column>
      <el-table-column label="房间号" prop="roomNumber" width="120" />
      <el-table-column label="最大入住人数" prop="peopleNum" width="120" />
      <el-table-column label="房间状态" prop="status" width="120" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value=1
            :inactive-value=0
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="房间备注" prop="roomDesc" :show-overflow-tooltip="true" />

      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改房间对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="房间类型" prop="roomType">
          <el-select v-model="form.roomType" placeholder="请选择房间类型">
            <el-option
              v-for="(value, key) in hotel_room_type"
              :key="key"
              :label="value"
              :value="key"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="form.roomNumber" placeholder="请输入房间号" />
        </el-form-item>
        <el-form-item label="最大人数" prop="peopleNum">
          <el-input-number v-model="form.peopleNum" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="房间状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="房间备注">
          <el-input v-model="form.roomDesc" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listRoom, getRoom, delRoom, addRoom, updateRoom, changeRoomStatus, getRoomType} from "@/api/hotel/room"

export default {
  name: "Room",
  dicts: ['hotel_room_status'],
  data() {
    return {
      hotel_room_type:[],
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
      // 房间表格数据
      roomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomType: null, //数字
        roomNumber: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomType: [
          { required: true, message: "房间类型不能为空", trigger: "blur" }
        ],
        roomNumber: [
          { required: true, message: "房间号不能为空", trigger: "blur" }
        ],
        peopleNum: [
          { required: true, message: "最大入住人数不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getAllType()
  },
  methods: {
    /** 查询房间列表 */
    getList() {
      this.loading = true

      listRoom(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        console.log("response")
          console.log(response)
          this.roomList = response.rows
          this.total = response.total
          this.loading = false
        }
      )

    },
    getAllType(){
      getRoomType().then(response=>{
        this.hotel_room_type=response.data
        for (let i = 0; i < this.hotel_room_type.length; i++) {

          console.log(this.hotel_room_type.length.data[i])
        }
      })
    },
    // 房间状态修改
    handleStatusChange(row) {
      console.log(row)
      let text = row.status === 1 || row.status === "1" ? "启用" : "停用"
      this.$modal.confirm('确认要"' + text + '""' + row.roomNumber + '"房间吗？').then(function() {
        return changeRoomStatus(row.roomId, row.status)
      }).then(() => {
        this.$modal.msgSuccess(text + "成功")
      }).catch(function() {
        row.status = row.status === 0 ? 1 : 0
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
        roomId: null,
        roomType: null,
        roomNumber: null,
        peopleNum: 2,
        roomDesc: null,
        status: null

      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roomId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加房间"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const roomId = row.roomId || this.ids
      getRoom(roomId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改房间"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.roomId != undefined) {
            updateRoom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addRoom(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const roomIds = row.roomId || this.ids
      this.$modal.confirm('是否确认删除房间编号为"' + roomIds + '"的数据项？').then(function() {
        return delRoom(roomIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hotel/room/export', {
        ...this.queryParams
      }, `room_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.tree-border {
  margin-top: 5px;
  border: 1px solid #e5e6e7;
  background: #FFFFFF none;
  border-radius: 4px;
  width: 100%;
}
</style>
