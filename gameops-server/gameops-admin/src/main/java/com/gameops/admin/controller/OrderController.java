package com.gameops.admin.controller;

import com.gameops.common.ApiResult;
import com.gameops.common.PageResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final JdbcTemplate jdbc;
    public OrderController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping
    public ApiResult<PageResult<Map<String,Object>>> page(
            @RequestParam(defaultValue="1") int pageNum,
            @RequestParam(defaultValue="10") int pageSize,
            @RequestParam(required=false) String orderNo,
            @RequestParam(required=false) Integer status) {
        pageNum = Math.max(1, pageNum);
        pageSize = Math.min(Math.max(1, pageSize), 100);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();
        if (orderNo != null && !orderNo.isBlank()) { where.append(" AND order_no LIKE ?"); args.add("%"+orderNo.trim()+"%"); }
        if (status != null) { where.append(" AND pay_status=?"); args.add(status); }
        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM recharge_order"+where, Long.class, args.toArray());
        args.add((pageNum-1)*pageSize); args.add(pageSize);
        List<Map<String,Object>> rows = jdbc.queryForList(
            "SELECT id,order_no,player_id,amount,pay_type,pay_status,pay_time,create_time FROM recharge_order"+where+" ORDER BY id DESC LIMIT ?,?", args.toArray());
        return ApiResult.ok(new PageResult<>(total == null ? 0 : total, pageNum, pageSize, rows));
    }
}
