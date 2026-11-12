<template>
  <div>
    <h2 class="page-title">充值订单</h2><div class="page-desc">查询充值订单及支付状态。</div>
    <div class="table-panel">
      <div class="toolbar">
        <el-input v-model="query.orderNo" placeholder="输入订单编号" clearable style="width:240px" @keyup.enter="search"/>
        <el-select v-model="query.status" placeholder="支付状态" clearable style="width:140px">
          <el-option label="待支付" :value="0"/><el-option label="支付成功" :value="1"/><el-option label="支付失败" :value="2"/><el-option label="已退款" :value="3"/>
        </el-select>
        <el-button type="primary" @click="search">查询</el-button><el-button @click="reset">重置</el-button>
      </div>
      <el-table :data="rows" stripe v-loading="loading">
        <el-table-column prop="order_no" label="订单编号" min-width="190"/>
        <el-table-column prop="player_id" label="玩家 ID" width="100"/>
        <el-table-column label="充值金额" width="130"><template #default="{row}">¥{{ Number(row.amount).toFixed(2) }}</template></el-table-column>
        <el-table-column prop="pay_type" label="支付方式" width="120"/>
        <el-table-column label="支付状态" width="120"><template #default="{row}"><el-tag :type="row.pay_status===1?'success':row.pay_status===0?'warning':'danger'">{{ statusText(row.pay_status) }}</el-tag></template></el-table-column>
        <el-table-column prop="pay_time" label="支付时间" min-width="170"/>
      </el-table>
      <div class="pagination"><el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="load"/></div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { getOrders } from '../api'
const query=reactive<any>({pageNum:1,pageSize:10,orderNo:'',status:undefined})
const rows=ref<any[]>([]),total=ref(0),loading=ref(false)
const statusText=(v:number)=>({0:'待支付',1:'支付成功',2:'支付失败',3:'已退款'} as any)[v]||'未知'
const load=async()=>{loading.value=true;try{const d:any=await getOrders(query);rows.value=d.records;total.value=d.total}finally{loading.value=false}}
const search=()=>{query.pageNum=1;load()}
const reset=()=>{query.orderNo='';query.status=undefined;search()}
onMounted(load)
</script>
