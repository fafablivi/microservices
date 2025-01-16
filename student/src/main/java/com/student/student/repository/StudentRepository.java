package com.student.student.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.student.student.entity.Student;

public interface StudentRepository extends MongoRepository<Student, String>{
    
}
