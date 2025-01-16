package com.student.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolResponse {
    private Long id;
    private String name;
    private String address;
    private String directorName;
}
