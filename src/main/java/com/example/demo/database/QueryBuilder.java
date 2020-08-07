package com.example.demo.database;

import com.example.demo.database.expressions.InClauseExpression;
import com.example.demo.database.expressions.QueryExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class QueryBuilder {

    private static final Pattern QUERY_EXPRESSION_PATTERN = Pattern.compile("(\\{\\{\\w+\\}\\})");

    public static String builder(String sql, QueryExpression... expressions) {
        if (StringUtils.isBlank(sql))
            return sql;

        if (Objects.isNull(expressions))
            return sql;

        List<String> elements = QUERY_EXPRESSION_PATTERN.matcher(sql)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());

        if (elements.size() != expressions.length)
            throw new IllegalArgumentException("Number of elements [" + elements.size() + "] in SQL expression " +
                    "does not equal query expression [" + expressions.length + "].");

        for (int i = 0; i < elements.size(); i++) {
            sql = sql.replace(elements.get(i), expressions[i].getQuery());
        }

        return sql;
    }

    public static void main(String[] args) {
        String sql = "SELECT id, name FROM my_table " +
                "WHERE id = :id {{inNetworkClause}} AND name LIKE %:name% " +
                "ORDER BY name ASC";

        InClauseExpression inNetworkClause = new InClauseExpression("test", Arrays.asList(1, 2, 3), "AND", 2);

        System.out.println(builder(sql, inNetworkClause));
    }

}
