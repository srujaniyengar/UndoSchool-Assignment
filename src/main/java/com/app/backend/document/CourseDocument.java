package com.app.backend.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "courses")
public class CourseDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private String category;
    private String type; // e.g., "COURSE", "CLUB"
    private String gradeRange;
    private int minAge;
    private int maxAge;
    private double price;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant nextSessionDate;
}