package com.example.demo.repository;

import com.example.demo.database.QueryResponse;
import com.example.demo.entity.Student;
import org.springframework.data.domain.Pageable;

public interface StudentJDBC {

    QueryResponse<Student> find(Pageable pageable);

}
