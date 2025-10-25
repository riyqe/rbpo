package com.example.labatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Executor {

    private Long id;

    private String name;
    private String specialization;  // например: VPN, лицензии, мониторы и т.д.
}