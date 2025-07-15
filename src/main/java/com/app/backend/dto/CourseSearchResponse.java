package com.app.backend.dto;

import com.app.backend.document.CourseDocument;
import lombok.Data;
import java.util.List;

@Data
public class CourseSearchResponse {
    private long total;
    private List<CourseDocument> courses;
}
