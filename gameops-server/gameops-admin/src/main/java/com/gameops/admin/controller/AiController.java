package com.gameops.admin.controller;

import com.gameops.common.ApiResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    @PostMapping("/chat")
    public ApiResult<Map<String,Object>> chat(@RequestBody Map<String,String> body) {
        String question = body.getOrDefault("message", "").trim();
        if (question.isBlank()) return ApiResult.fail("请输入问题");
        String answer;
        if (question.contains("充值")) {
            answer = "我可以协助分析充值数据。当前版本已提供充值统计接口，下一步可接入 Spring AI，通过充值统计、玩家分层等业务工具获取实时数据。请注意：当前回复为本地示例逻辑，并非真实模型推理。";
        } else if (question.contains("玩家")) {
            answer = "玩家管理模块支持按玩家编号、昵称和账号状态查询。后续可将 queryPlayer 注册为 Agent 工具，由服务端执行权限校验后返回结果。";
        } else {
            answer = "你好，我是 GameOps AI 运营助手。你可以尝试询问玩家管理、充值订单、活动运营或数据分析。当前为基础接口版本，尚未连接真实大模型。";
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("question", question);
        data.put("answer", answer);
        data.put("createdAt", LocalDateTime.now().toString());
        return ApiResult.ok(data);
    }
}
