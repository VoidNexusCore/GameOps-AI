<template>
  <div>
    <h2 class="page-title">AI 智能助手</h2><div class="page-desc">通过自然语言查询游戏运营业务，辅助整理分析思路。</div>
    <div class="chat-layout">
      <div class="chat-card">
        <div class="chat-head"><div><strong>GameOps AI 运营助手</strong><div class="muted">支持玩家、充值、活动和运营数据相关问题</div></div><el-tag type="success">在线</el-tag></div>
        <div class="messages" ref="messageBox">
          <div v-for="(item,index) in messages" :key="index" :class="['message-row',item.role]">
            <div class="bubble"><div class="bubble-role">{{ item.role==='user'?'我':'AI 助手' }}</div>{{ item.content }}</div>
          </div>
          <div v-if="loading" class="muted">AI 正在整理回复...</div>
        </div>
        <div class="suggestions"><el-button size="small" plain @click="send('分析昨天的充值情况')">分析昨天的充值情况</el-button><el-button size="small" plain @click="send('如何查询高等级玩家？')">查询高等级玩家</el-button><el-button size="small" plain @click="send('给我一个周末活动运营建议')">周末活动建议</el-button></div>
        <div class="chat-input"><el-input v-model="input" type="textarea" :rows="2" resize="none" placeholder="请输入你的问题..." @keydown.ctrl.enter="send()"/><el-button type="primary" :loading="loading" @click="send()">发送</el-button></div>
        <div class="muted footnote">当前为基础接口版本，尚未连接真实大模型；请勿输入真实玩家隐私或生产敏感信息。</div>
      </div>
      <div class="chat-side panel"><div class="panel-title">能力说明</div><p>数据问答：查询运营指标和业务数据。</p><p>工具调用：后续接入玩家、订单、活动查询工具。</p><p>报告生成：汇总指标并形成运营分析建议。</p><el-divider/><div class="panel-title">安全边界</div><p>AI 不直接访问数据库。所有工具调用由后端进行身份校验、权限控制和参数验证。</p></div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { nextTick, ref } from 'vue'
import { sendAiMessage } from '../api'
const input=ref(''),loading=ref(false),messageBox=ref<HTMLElement>()
const messages=ref<any[]>([{role:'assistant',content:'你好，我是 GameOps AI 运营助手。你可以询问充值数据、玩家管理或活动运营问题。'}])
const send=async(text?:string)=>{
  const question=(text||input.value).trim()
  if(!question||loading.value)return
  messages.value.push({role:'user',content:question});input.value='';loading.value=true
  await nextTick(); messageBox.value?.scrollTo({top:messageBox.value.scrollHeight,behavior:'smooth'})
  try{const data:any=await sendAiMessage(question);messages.value.push({role:'assistant',content:data.answer})}
  catch{messages.value.push({role:'assistant',content:'请求暂时失败，请检查后端服务。'})}
  finally{loading.value=false;await nextTick();messageBox.value?.scrollTo({top:messageBox.value.scrollHeight,behavior:'smooth'})}
}
</script>
<style scoped>
.chat-layout{display:grid;grid-template-columns:minmax(600px,1fr) 280px;gap:18px}.chat-card{background:#fff;border:1px solid #edf0f5;border-radius:10px;min-height:620px;display:flex;flex-direction:column;padding:20px}.chat-head{display:flex;justify-content:space-between;padding-bottom:16px;border-bottom:1px solid #edf0f5}.muted{font-size:12px;color:#98a1b1;margin-top:6px}.messages{flex:1;min-height:350px;max-height:440px;overflow:auto;padding:18px 4px}.message-row{display:flex;margin:12px 0}.message-row.user{justify-content:flex-end}.bubble{max-width:78%;white-space:pre-wrap;line-height:1.7;background:#f3f6fb;border-radius:10px;padding:12px 15px;font-size:13px}.user .bubble{background:#eaf2ff}.bubble-role{font-size:11px;color:#7e8ba0;margin-bottom:5px}.suggestions{display:flex;gap:8px;flex-wrap:wrap;padding:12px 0}.chat-input{display:flex;align-items:flex-end;gap:10px}.footnote{margin-top:10px}.chat-side p{font-size:13px;line-height:1.7;color:#687386}
</style>
