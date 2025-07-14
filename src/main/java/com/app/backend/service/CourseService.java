package com.app.backend.service;

import com.app.backend.document.CourseDocument;
import com.app.backend.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadSampleData() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("sample-courses.json");
            List<CourseDocument> courses = objectMapper.readValue(is, new TypeReference<>() {});
            courseRepository.saveAll(courses);
            System.out.println("Sample data indexed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//TODO algo
}
