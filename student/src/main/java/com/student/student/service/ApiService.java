package com.student.student.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.student.student.dto.SchoolResponse;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SchoolResponse getSchoolById(String id) {
        String url = "http://school/school/" + id;
        return restTemplate.getForObject(url, SchoolResponse.class);
    }    
}
