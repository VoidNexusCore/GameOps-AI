<template>
  <div>
    <h2 class="page-title">玩家管理</h2><div class="page-desc">管理玩家基础资料、等级及账号状态。</div>
    <div class="table-panel">
      <div class="toolbar">
        <el-input v-model="query.keyword" placeholder="输入玩家编号或昵称" clearable style="width:240px" @keyup.enter="search" />
        <el-select v-model="query.status" placeholder="账号状态" clearable style="width:140px"><el-option label="正常" :value="0"/><el-option label="已封禁" :value="1"/></el-select>
        <el-button type="primary" @click="search">查询</el-button><el-button @click="reset">重置</el-button>
      </div>
      <el-table :data="rows" stripe v-loading="loading">
        <el-table-column prop="player_no" label="玩家编号" min-width="140"/>
        <el-table-column prop="nickname" label="玩家昵称" min-width="130"/>
        <el-table-column prop="level" label="等级" width="90"/>
        <el-table-column prop="vip_level" label="VIP 等级" width="100"/>
        <el-table-column label="账号状态" width="110"><template #default="{row}"><el-tag :type="row.status===0?'success':'danger'">{{ row.status===0?'正常':'已封禁' }}</el-tag></template></el-table-column>
        <el-table-column prop="create_time" label="注册时间" min-width="170"/>
        <el-table-column label="操作" width="130"><template #default="{row}"><el-button link type="primary" @click="toggle(row)">{{ row.status===0?'封禁':'解封' }}</el-button></template></el-table-column>
      </el-table>
      <div class="pagination"><el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="load"/></div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPlayers, updatePlayerStatus } from '../api'
const query = reactive<any>({ pageNum: 1, pageSize: 10, keyword: '', status: undefined })
const rows = ref<any[]>([]), total = ref(0), loading = ref(false)
const load = async () => { loading.value=true; try { const data:any=await getPlayers(query); rows.value=data.records; total.value=data.total } finally { loading.value=false } }
const search = () => { query.pageNum=1; load() }
const reset = () => { query.keyword=''; query.status=undefined; search() }
const toggle = async (row:any) => {
  const next = row.status===0 ? 1 : 0
  await ElMessageBox.confirm(`确定${next===1?'封禁':'解封'}玩家「${row.nickname}」吗？`, '操作确认')
  await updatePlayerStatus(row.id, next); ElMessage.success('操作成功'); load()
}
onMounted(load)
</script>
