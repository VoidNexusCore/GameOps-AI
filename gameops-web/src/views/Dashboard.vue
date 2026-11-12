<template>
  <div>
    <h2 class="page-title">数据总览</h2>
    <div class="page-desc">实时掌握游戏运营核心指标，及时发现业务变化。</div>
    <div class="metric-grid">
      <div class="metric-card"><div class="metric-label">今日新增玩家</div><div class="metric-value">{{ overview.newPlayers ?? '-' }}</div><div class="metric-foot"><span class="metric-up">较昨日</span> 实时统计</div></div>
      <div class="metric-card"><div class="metric-label">今日活跃玩家</div><div class="metric-value">{{ overview.activePlayers ?? '-' }}</div><div class="metric-foot">今日登录去重人数</div></div>
      <div class="metric-card"><div class="metric-label">今日充值金额</div><div class="metric-value">¥{{ money(overview.rechargeAmount) }}</div><div class="metric-foot">支付成功订单金额</div></div>
      <div class="metric-card"><div class="metric-label">付费转化率</div><div class="metric-value">{{ overview.payRate ?? '-' }}%</div><div class="metric-foot">指标口径可按业务调整</div></div>
    </div>
    <div class="panel">
      <div class="panel-title">近七日充值趋势</div>
      <div ref="chartRef" style="height:330px;width:100%"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onBeforeUnmount, ref } from 'vue'
import * as echarts from 'echarts'
import { getOverview } from '../api'
const overview = ref<any>({})
const chartRef = ref<HTMLElement>()
let chart: echarts.ECharts | undefined
const money = (v: any) => Number(v || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
const load = async () => {
  try {
    overview.value = await getOverview() as any
    const rows = overview.value.rechargeTrend || []
    chart?.setOption({
      tooltip: { trigger: 'axis' },
      grid: { left: 45, right: 20, top: 25, bottom: 30 },
      xAxis: { type: 'category', data: rows.map((x: any) => x.day), axisLine: { lineStyle: { color: '#e8edf4' } } },
      yAxis: { type: 'value', splitLine: { lineStyle: { color: '#eef1f6' } } },
      series: [{ name: '充值金额', type: 'line', smooth: true, data: rows.map((x: any) => x.amount), areaStyle: { opacity: .08 }, itemStyle: { color: '#3979ee' }, lineStyle: { width: 3 } }]
    })
  } catch { /* 请求错误由拦截器提示 */ }
}
onMounted(() => { chart = echarts.init(chartRef.value!); load(); window.addEventListener('resize', resize) })
const resize = () => chart?.resize()
onBeforeUnmount(() => { window.removeEventListener('resize', resize); chart?.dispose() })
</script>
