package com.example.labatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private Long id;

    private String username;
    private String email;
    private String department;  // отдел, к которому принадлежит сотрудник
}
