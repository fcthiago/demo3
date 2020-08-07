package com.example.demo.repository;

import com.example.demo.database.QueryResponse;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentJDBCImpl implements StudentJDBC {

    private final JdbcTemplate jdbc;

    public StudentJDBCImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public QueryResponse<Student> find(Pageable pageable) {
        String sql = "select id, name, age, 3 AS total from student";
        return (QueryResponse<Student>) jdbc.query(sql, new QueryResponseExtractor(new StudentMapper()));
    }

}
