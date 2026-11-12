<template>
  <el-container class="app-shell">
    <el-aside width="224px" class="sidebar">
      <div class="brand">
        <div class="brand-logo">G</div>
        <div>
          <div class="brand-name">GameOps AI</div>
          <div class="brand-sub">游戏运营管理平台</div>
        </div>
      </div>
      <el-menu :default-active="route.path" router class="side-menu">
        <el-menu-item index="/dashboard"><el-icon><DataBoard /></el-icon><span>数据总览</span></el-menu-item>
        <el-menu-item index="/players"><el-icon><User /></el-icon><span>玩家管理</span></el-menu-item>
        <el-menu-item index="/orders"><el-icon><Wallet /></el-icon><span>充值订单</span></el-menu-item>
        <el-menu-item index="/activities"><el-icon><Present /></el-icon><span>活动管理</span></el-menu-item>
        <el-menu-item index="/ai"><el-icon><ChatDotRound /></el-icon><span>AI 智能助手</span></el-menu-item>
      </el-menu>
      <div class="sidebar-bottom">GameOps AI · 管理控制台</div>
    </el-aside>
    <el-container>
      <el-header class="topbar">
        <div class="breadcrumb">运营管理 / <strong>{{ route.meta.title }}</strong></div>
        <div class="top-user"><el-avatar :size="30">管</el-avatar><span>运营管理员</span></div>
      </el-header>
      <el-main class="main-content"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { DataBoard, User, Wallet, Present, ChatDotRound } from '@element-plus/icons-vue'
const route = useRoute()
</script>
