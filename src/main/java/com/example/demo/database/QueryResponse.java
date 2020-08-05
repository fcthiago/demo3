package com.example.demo.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryResponse<T> {

    private List<T> result = new ArrayList<>();

    private Integer page;
    private Integer total;

    public void add(T obj) {
        result.add(obj);
    }

    public List<T> getResult() {
        return Collections.unmodifiableList(result);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
