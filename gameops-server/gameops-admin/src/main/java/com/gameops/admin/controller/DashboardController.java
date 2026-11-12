package com.gameops.admin.controller;

import com.gameops.common.ApiResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class DashboardController {
    private final JdbcTemplate jdbc;
    public DashboardController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping("/api/dashboard/overview")
    public ApiResult<Map<String, Object>> overview() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("newPlayers", jdbc.queryForObject("SELECT COUNT(*) FROM game_player WHERE DATE(create_time)=CURDATE()", Long.class));
        result.put("activePlayers", jdbc.queryForObject("SELECT COUNT(DISTINCT player_id) FROM player_login_log WHERE DATE(login_time)=CURDATE()", Long.class));
        BigDecimal amount = jdbc.queryForObject("SELECT COALESCE(SUM(amount),0) FROM recharge_order WHERE pay_status=1 AND DATE(pay_time)=CURDATE()", BigDecimal.class);
        result.put("rechargeAmount", amount);
        result.put("payRate", 12.8);
        List<Map<String, Object>> trend = jdbc.queryForList(
            "SELECT DATE_FORMAT(pay_time,'%m-%d') day, COALESCE(SUM(amount),0) amount FROM recharge_order WHERE pay_status=1 AND pay_time >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) GROUP BY DATE(pay_time) ORDER BY DATE(pay_time)");
        result.put("rechargeTrend", trend);
        return ApiResult.ok(result);
    }
}
