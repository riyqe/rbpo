package com.example.labatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;

    private String name;            // например: "Инциденты", "Лицензии и софт"
    private String description;     // описание категории
}
