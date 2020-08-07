package com.example.demo.database.expressions;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class InClauseExpression implements QueryExpression {

    public static final int IN_CLAUSE_LIMIT = 1000;

    private final String fieldName;
    private final List<Integer> ids;
    private final String prefix;
    private final Integer inClauseLimit;
    private final UnaryOperator<Boolean> validation;

    public InClauseExpression(String fieldName, List<Integer> ids, UnaryOperator<Boolean> validation) {
        this(fieldName, ids, null, validation);
    }

    public InClauseExpression(String fieldName, List<Integer> ids) {
        this(fieldName, ids, null, null, IN_CLAUSE_LIMIT);
    }

    public InClauseExpression(String fieldName, List<Integer> ids, String prefix) {
        this(fieldName, ids, prefix, null, IN_CLAUSE_LIMIT);
    }

    public InClauseExpression(String fieldName, List<Integer> ids, String prefix, UnaryOperator<Boolean> validation) {
        this(fieldName, ids, prefix, validation, IN_CLAUSE_LIMIT);
    }

    public InClauseExpression(String fieldName, List<Integer> ids, Integer inClauseLimit) {
        this(fieldName, ids, null, null, inClauseLimit);
    }

    public InClauseExpression(String fieldName, List<Integer> ids, UnaryOperator<Boolean> validation, Integer inClauseLimit) {
        this(fieldName, ids, null, validation, inClauseLimit);
    }

    public InClauseExpression(String fieldName, List<Integer> ids, String prefix, Integer inClauseLimit) {
        this(fieldName, ids, prefix, null, inClauseLimit);
    }

    public InClauseExpression(String fieldName, List<Integer> ids, String prefix, UnaryOperator<Boolean> validation, Integer inClauseLimit) {
        Objects.requireNonNull(fieldName, "'field name' is required.");

        this.fieldName = fieldName;
        this.ids = ids;
        this.prefix = StringUtils.trimToEmpty(prefix);
        this.inClauseLimit = inClauseLimit;
        this.validation = validation;
    }

    @Override
    public String getQuery() {
        if (CollectionUtils.isEmpty(ids))
            return EMPTY;

        if (Objects.nonNull(validation) && !validation.apply(true))
            return EMPTY;

        StringBuilder query = new StringBuilder(SPACE).append(prefix);

        if (StringUtils.isNotBlank(prefix))
            query.append(SPACE);

        query.append(fieldName).append(" IN (");

        for (int i = 1; i <= ids.size(); i++) {
            boolean newClause = i % inClauseLimit == 1 && i > 1;

            if (!newClause && i > 1)
                query.append(",");

            if (newClause)
                query.append(") OR ").append(fieldName).append(" IN (");

            query.append(i);
        }

        query.append(")");

        return query.toString();
    }
}
