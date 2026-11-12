<template>
  <div>
    <h2 class="page-title">活动管理</h2><div class="page-desc">配置游戏运营活动，管理活动时间和状态。</div>
    <div class="table-panel">
      <div class="toolbar"><el-button type="primary" @click="dialog=true"><el-icon><Plus /></el-icon> 新建活动</el-button></div>
      <el-table :data="rows" stripe>
        <el-table-column prop="activity_name" label="活动名称" min-width="180"/>
        <el-table-column prop="activity_type" label="活动类型" width="130"/>
        <el-table-column prop="start_time" label="开始时间" min-width="170"/>
        <el-table-column prop="end_time" label="结束时间" min-width="170"/>
        <el-table-column label="活动状态" width="120"><template #default="{row}"><el-tag :type="row.status===1?'success':row.status===2?'info':'warning'">{{ row.status===1?'进行中':row.status===2?'已结束':'未开始' }}</el-tag></template></el-table-column>
      </el-table>
    </div>
    <el-dialog v-model="dialog" title="新建活动" width="520px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="活动名称"><el-input v-model="form.activityName" placeholder="请输入活动名称"/></el-form-item>
        <el-form-item label="活动类型"><el-select v-model="form.activityType"><el-option label="充值返利" value="充值返利"/><el-option label="登录奖励" value="登录奖励"/><el-option label="新手活动" value="新手活动"/></el-select></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss"/></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialog=false">取消</el-button><el-button type="primary" @click="submit">创建</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getActivities, createActivity } from '../api'
const rows=ref<any[]>([]),dialog=ref(false)
const form=reactive<any>({activityName:'',activityType:'充值返利',startTime:'',endTime:''})
const load=async()=>{rows.value=await getActivities() as any[]}
const submit=async()=>{if(!form.activityName||!form.startTime||!form.endTime){ElMessage.warning('请填写完整活动信息');return} await createActivity(form);ElMessage.success('活动创建成功');dialog.value=false;form.activityName='';load()}
onMounted(load)
</script>
