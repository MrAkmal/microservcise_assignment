package com.example.backend_as_frontend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcurementMethod {

    private int id;

    private String name;

    private int procurementNatureId;
}
