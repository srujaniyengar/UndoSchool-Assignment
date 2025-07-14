package com.app.backend.controller;

import com.app.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class CourseSearchController {
    private final CourseService courseService;

    @GetMapping
    public String healthCheck() {
        return "Search endpoint is working.";
    }
}
