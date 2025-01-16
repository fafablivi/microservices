package com.school.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.school.entity.School;
import com.school.school.service.SchoolService;

@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        return ResponseEntity.ok(schoolService.saveSchool(school));
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<School>> getSchools() {
        return ResponseEntity.ok(schoolService.getSchools());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<School> deleteSchoolById(@PathVariable Long id) {
        schoolService.deleteSchoolById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@RequestBody School school, @PathVariable Long id) {
        School savedSchool = schoolService.saveSchool(school, id);
        if(savedSchool != null){
            return ResponseEntity.ok(savedSchool);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
