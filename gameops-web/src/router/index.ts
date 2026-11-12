import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Players from '../views/Players.vue'
import Orders from '../views/Orders.vue'
import Activities from '../views/Activities.vue'
import AiAssistant from '../views/AiAssistant.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      children: [
        { path: 'dashboard', component: Dashboard, meta: { title: '数据总览' } },
        { path: 'players', component: Players, meta: { title: '玩家管理' } },
        { path: 'orders', component: Orders, meta: { title: '充值订单' } },
        { path: 'activities', component: Activities, meta: { title: '活动管理' } },
        { path: 'ai', component: AiAssistant, meta: { title: 'AI 智能助手' } }
      ]
    }
  ]
})

export default router
