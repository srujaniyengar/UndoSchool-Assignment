package com.app.backend.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "courses") // This will use the 'courses' index in Elasticsearch
public class CourseDocument {

    @Id
    private String id;

    private String title;
    private String description;
    private String category;
    private String type; // e.g., "online", "offline"
    private int minAge;
    private int maxAge;
    private double price;
    private Instant nextSessionDate;

    // Add other fields as needed

}
