package com.example.demo.repository;

import com.example.demo.database.QueryResponse;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryResponseExtractor<T> implements ResultSetExtractor<QueryResponse<T>> {

    private final RowMapper<T> rowMapper;
    private final int page;

    public QueryResponseExtractor(RowMapper<T> rowMapper, int page) {
        Assert.notNull(rowMapper, "RowMapper is required");
        this.rowMapper = rowMapper;
        this.page = page;
    }

    @Override
    public QueryResponse<T> extractData(ResultSet rs) throws SQLException {
        QueryResponse<T> queryResponse = new QueryResponse<>();

        int rowNum = 0;

        while (rs.next()) {
            queryResponse.setTotal(rs.getInt("total"));
            queryResponse.add(rowMapper.mapRow(rs, rowNum++));
        }

        queryResponse.setPage(page);

        return queryResponse;
    }
}
