package com.gameops.admin.controller;

import com.gameops.common.ApiResult;
import com.gameops.common.PageResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final JdbcTemplate jdbc;
    public PlayerController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping
    public ApiResult<PageResult<Map<String, Object>>> page(
            @RequestParam(defaultValue="1") int pageNum,
            @RequestParam(defaultValue="10") int pageSize,
            @RequestParam(required=false) String keyword,
            @RequestParam(required=false) Integer status) {
        pageNum = Math.max(pageNum, 1);
        pageSize = Math.min(Math.max(pageSize, 1), 100);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();
        if (keyword != null && !keyword.isBlank()) {
            where.append(" AND (player_no LIKE ? OR nickname LIKE ?)");
            args.add("%"+keyword.trim()+"%"); args.add("%"+keyword.trim()+"%");
        }
        if (status != null) { where.append(" AND status=?"); args.add(status); }
        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM game_player" + where, Long.class, args.toArray());
        args.add((pageNum-1)*pageSize); args.add(pageSize);
        List<Map<String,Object>> rows = jdbc.queryForList(
            "SELECT id,player_no,nickname,level,vip_level,status,create_time FROM game_player" + where +
            " ORDER BY id DESC LIMIT ?,?", args.toArray());
        return ApiResult.ok(new PageResult<>(total == null ? 0 : total, pageNum, pageSize, rows));
    }

    @PutMapping("/{id}/status")
    public ApiResult<Void> updateStatus(@PathVariable long id, @RequestBody Map<String,Integer> body) {
        Integer status = body.get("status");
        if (status == null || (status != 0 && status != 1)) return ApiResult.fail("玩家状态参数不合法");
        int rows = jdbc.update("UPDATE game_player SET status=? WHERE id=?", status, id);
        return rows > 0 ? ApiResult.ok(null) : ApiResult.fail("玩家不存在");
    }
}
