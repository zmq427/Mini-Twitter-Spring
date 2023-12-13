package com.mq.controller;

import com.mq.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @GetMapping("/dau")
    public R getDAU() {
        return null;
    }

    @GetMapping("/uv")
    public R getUV() {
        return null;
    }
}
