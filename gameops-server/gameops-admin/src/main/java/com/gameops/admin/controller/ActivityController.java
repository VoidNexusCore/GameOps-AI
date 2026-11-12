package com.gameops.admin.controller;

import com.gameops.common.ApiResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final JdbcTemplate jdbc;
    public ActivityController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping
    public ApiResult<List<Map<String,Object>>> list() {
        return ApiResult.ok(jdbc.queryForList("SELECT id,activity_name,activity_type,start_time,end_time,status,create_time FROM game_activity ORDER BY id DESC"));
    }

    @PostMapping
    public ApiResult<Map<String,Object>> create(@RequestBody Map<String,Object> body) {
        String name = Objects.toString(body.get("activityName"), "").trim();
        String type = Objects.toString(body.get("activityType"), "常规活动").trim();
        String start = Objects.toString(body.get("startTime"), "");
        String end = Objects.toString(body.get("endTime"), "");
        if (name.isBlank() || start.isBlank() || end.isBlank()) return ApiResult.fail("活动名称和活动时间不能为空");
        jdbc.update("INSERT INTO game_activity(activity_name,activity_type,start_time,end_time,status,create_time) VALUES(?,?,?,?,0,NOW())",
            name, type, start, end);
        return ApiResult.ok("活动创建成功", Map.of("activityName", name));
    }
}
