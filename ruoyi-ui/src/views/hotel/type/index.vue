<script>
import { listType, getType, addType, updateType, delType, exportType, getAllTypes } from "@/api/hotel/type";
import { parseTime } from "@/utils/ruoyi";

export default {
  name: "HotelType",
  data() {
    return {
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
      // 房型表格数据
      typeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        capacity: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "房型名称不能为空", trigger: "blur" }
        ],
        capacity: [
          { required: true, message: "最大入住人数不能为空", trigger: "blur" }
        ]
      },
      // 房型下拉数据
      typeOptions: []
    };
  },
  created() {
    this.getList();
    this.getTypeOptions();
  },
  methods: {
    parseTime,
    /** 查询房型列表 */
    getList() {
      this.loading = true;
      listType(this.queryParams).then(response => {
        this.typeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        typeId: null,
        name: null,
        amenities: null,
        capacity: null,
        imageUrl: null
      };
      this.resetForm && this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm && this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.typeId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const typeId = row?.typeId || this.ids[0];
      getType(typeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改房型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.typeId != undefined) {
            updateType(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getTypeOptions();
            });
          } else {
            addType(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getTypeOptions();
            });
          }
        }
      });

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const typeIds = row?.typeId || this.ids;
      this.$modal.confirm('是否确认删除房型编号为"' + typeIds + '"的数据项？').then(() => {
        return delType(typeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});

    },
    /** 导出按钮操作 */
    handleExport() {
      exportType(this.queryParams);
    },
    getTypeOptions() {
      getAllTypes().then(res => {
        this.typeOptions = res.data || [];
      });
    }
  }
};
</script>

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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="房型ID" prop="typeId" width="100" />
      <el-table-column label="房型名称" prop="name" width="150" />
      <el-table-column label="设施" prop="amenities" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="最大入住人数" prop="capacity" width="120" />
      <el-table-column label="图片" prop="imageUrl" width="120">
        <template slot-scope="scope">
          <img v-if="scope.row.imageUrl" :src="scope.row.imageUrl" alt="图片" style="width: 60px; height: 40px; object-fit: cover;" />
        </template>
      </el-table-column>
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

    <!-- 添加或修改房型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="房型名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入房型名称" />
        </el-form-item>
        <el-form-item label="设施" prop="amenities">
          <el-input v-model="form.amenities" placeholder="请输入设施" />
        </el-form-item>
        <el-form-item label="最大入住人数" prop="capacity">
          <el-input v-model="form.capacity" placeholder="请输入最大入住人数" />
        </el-form-item>
        <el-form-item label="图片URL" prop="imageUrl">
          <el-input v-model="form.imageUrl" placeholder="请输入图片URL" />
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
.app-container {
  padding: 20px;
}
</style>
