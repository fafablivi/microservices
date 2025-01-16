package com.student.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponse {
    private String id;
    private String name;
    private String genre;
    private SchoolResponse school;
}
