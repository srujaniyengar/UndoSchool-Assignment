package com.app.backend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;

@Data
@Document(indexName = "courses")
public class CourseDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private String category;
    private String type;
    private String gradeRange;
    private Integer minAge;
    private Integer maxAge;
    private Double price;
    private Instant nextSessionDate;
}

