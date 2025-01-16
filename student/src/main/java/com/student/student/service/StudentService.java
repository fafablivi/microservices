package com.student.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.student.dto.SchoolResponse;
import com.student.student.dto.StudentResponse;
import com.student.student.entity.Student;
import com.student.student.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ApiService apiService;

    @Autowired
    public StudentService(StudentRepository studentRepository, ApiService apiService) {
        this.studentRepository = studentRepository;
        this.apiService = apiService;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student saveStudent(Student student, String id) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    if (student.getName() != null) {
                        existingStudent.setName(student.getName());
                    }
                    if (student.getGenre() != null) {
                        existingStudent.setGenre(student.getGenre());
                    }
                    if (student.getSchoolId() != null) {
                        existingStudent.setSchoolId(student.getSchoolId());
                    }
                    return studentRepository.save(existingStudent);
                })
                .orElse(null);
    }    

    public StudentResponse getStudentById(String id) {
        return studentRepository.findById(id)
                .map(student -> {
                    SchoolResponse school = apiService.getSchoolById(student.getSchoolId());
                    if (school == null) {
                        return null;
                    }
                    return new StudentResponse(student.getId(), student.getName(), student.getGenre(), school);
                })
                .orElse(null);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }
}
