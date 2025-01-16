package com.school.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.school.entity.School;
import com.school.school.repository.SchoolRepository;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    public School saveSchool(School school){
        schoolRepository.save(school);
        return school;
    }

    public School saveSchool(School school, Long id){
        Optional<School> existingSchool = schoolRepository.findById(id);
        if(existingSchool.isPresent()){
            School savedSchool = existingSchool.get();
            savedSchool.setAddress(school.getAddress());
            savedSchool.setDirectorName(school.getDirectorName());
            savedSchool.setName(school.getName());
            schoolRepository.save(savedSchool);
            return savedSchool;
        } else {
            return null;
        }
    }

    public Optional<School> getSchoolById(Long id){
        return schoolRepository.findById(id);
    }

    public List<School> getSchools(){
        return schoolRepository.findAll();
    }

    public void deleteSchoolById(Long id){
        schoolRepository.deleteById(id);
    }
}
