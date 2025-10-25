package com.example.labatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SLA {

    private Long id;

    private String level;        // обычный, средний, критический, блокер
    private int responseHours;   // время на реакцию
    private int resolveHours;    // время на решение
}
