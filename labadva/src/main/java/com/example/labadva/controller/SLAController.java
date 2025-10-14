package com.example.labadva.controller;

import com.example.labadva.model.SLA;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sla")
public class SLAController {

    private final List<SLA> slas = new ArrayList<>();

    @PostMapping
    public SLA createSLA(@RequestBody SLA sla) {
        slas.add(sla);
        return sla;
    }

    @GetMapping
    public List<SLA> getAllSLAs() {
        return slas;
    }
}
