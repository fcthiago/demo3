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

    private String fieldName;
    private List<Integer> ids;
    private String prefix = EMPTY;
    private Integer inClauseLimit = IN_CLAUSE_LIMIT;
    private UnaryOperator<Boolean> validation;

    private InClauseExpression() {
    }

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {

        private InClauseExpression expression = new InClauseExpression();

        public Builder withIds(List<Integer> ids) {
            expression.ids = ids;
            return this;
        }

        public Builder withPrefix(String prefix) {
            expression.prefix = prefix;
            return this;
        }

        public Builder withValidation(UnaryOperator<Boolean> validation) {
            expression.validation = validation;
            return this;
        }

        public Builder withInClauseLimit(Integer inClauseLimit) {
            expression.inClauseLimit = inClauseLimit;
            return this;
        }

        public InClauseExpression build(String fieldName) {
            Objects.requireNonNull(fieldName, "'field name' is required.");
            expression.fieldName = fieldName;
            return expression;
        }

    }
}
