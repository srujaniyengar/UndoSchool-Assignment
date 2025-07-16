package com.app.backend.controller;

import com.app.backend.dto.CourseSearchResponse;
import com.app.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class CourseSearchController {
    private final CourseService courseService;

    @GetMapping
    public CourseSearchResponse searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(required = false, defaultValue = "upcoming") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) throws Exception {
        return courseService.searchCourses(q, minAge, maxAge, category, type, minPrice, maxPrice, startDate, sort, page, size);
    }

    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam("q") String q) throws Exception {
        return courseService.suggestTitlePrefix(q);
    }
}