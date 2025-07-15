package com.app.backend.service;

import com.app.backend.document.CourseDocument;
import com.app.backend.dto.CourseSearchResponse;
import com.app.backend.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ObjectMapper objectMapper; // Let Spring inject (autoconfigure) it

    @PostConstruct
    public void loadSampleData() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("sample-courses.json");
            if (is != null) {
                List<CourseDocument> courses = objectMapper.readValue(is, new TypeReference<List<CourseDocument>>() {});
                courseRepository.saveAll(courses);
                System.out.println("Sample data indexed successfully!");
            } else {
                System.err.println("sample-courses.json not found in resources!");
            }
        } catch (Exception e) {
            System.err.println("Error loading sample data:");
            e.printStackTrace();
        }
    }

    public CourseSearchResponse searchCourses(
            String q,
            Integer minAge,
            Integer maxAge,
            String category,
            String type,
            Double minPrice,
            Double maxPrice,
            Instant startDate,
            String sort,
            Integer page,
            Integer size
    ) {
        Criteria criteria = new Criteria();

        // Full-text search on title or description
        if (q != null && !q.isBlank()) {
            Criteria titleCriteria = new Criteria("title").matches(q);
            Criteria descriptionCriteria = new Criteria("description").matches(q);
            criteria = criteria.subCriteria(titleCriteria).or(descriptionCriteria);
        }

        // Age filter
        if (minAge != null) {
            criteria = criteria.and(new Criteria("maxAge").greaterThanEqual(minAge));
        }
        if (maxAge != null) {
            criteria = criteria.and(new Criteria("minAge").lessThanEqual(maxAge));
        }

        // Category filter
        if (category != null && !category.isBlank()) {
            criteria = criteria.and(new Criteria("category").is(category));
        }

        // Type filter
        if (type != null && !type.isBlank()) {
            criteria = criteria.and(new Criteria("type").is(type));
        }

        // Price filter
        if (minPrice != null) {
            criteria = criteria.and(new Criteria("price").greaterThanEqual(minPrice));
        }
        if (maxPrice != null) {
            criteria = criteria.and(new Criteria("price").lessThanEqual(maxPrice));
        }

        // Date filter
        if (startDate != null) {
            criteria = criteria.and(new Criteria("nextSessionDate").greaterThanEqual(startDate.toString()));
        }

        // Sorting
        String sortField = "nextSessionDate";
        Sort.Direction direction = Sort.Direction.ASC;
        if ("priceAsc".equals(sort)) {
            sortField = "price";
            direction = Sort.Direction.ASC;
        } else if ("priceDesc".equals(sort)) {
            sortField = "price";
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10, direction, sortField);

        CriteriaQuery query = new CriteriaQuery(criteria, pageable);

        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);

        List<CourseDocument> courses = hits.get().map(SearchHit::getContent).collect(Collectors.toList());

        CourseSearchResponse response = new CourseSearchResponse();
        response.setTotal(hits.getTotalHits());
        response.setCourses(courses != null ? courses : new ArrayList<>());
        return response;
    }
}
