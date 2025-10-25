package com.example.labatri.controller;

import com.example.labatri.model.Executor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/executors")
public class ExecutorController {

    private final List<Executor> executors = new ArrayList<>();

    @PostMapping
    public Executor createExecutor(@RequestBody Executor executor) {
        executors.add(executor);
        return executor;
    }

    @GetMapping
    public List<Executor> getAllExecutors() {
        return executors;
    }
}