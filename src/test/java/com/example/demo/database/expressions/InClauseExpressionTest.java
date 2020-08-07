package com.example.demo.database.expressions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class InClauseExpressionTest {

    @Test
    @DisplayName("Given that null list")
    public void givenThatNullList() {
        String query = new InClauseExpression("test_field", null, 3).getQuery();
        assertThat(query).isEmpty();
    }

    @Test
    @DisplayName("Given that empty list")
    public void givenThatEmptyList() {
        String query = new InClauseExpression("test_field", Collections.emptyList(), 3).getQuery();
        assertThat(query).isEmpty();
    }

    @Test
    @DisplayName("Given that field name is null")
    public void givenThatFieldNameIsNull() {
        assertThatNullPointerException().isThrownBy(() -> {
            new InClauseExpression(null, getIds(7), 3).getQuery();
        }).withMessage("'field name' is required.");
    }

    @Test
    @DisplayName("Given that list greater than in clause")
    public void givenThatListGreaterThanInClause() {
        String query = new InClauseExpression("test_field", getIds(7), 3).getQuery();
        assertThat(query).isEqualTo(" test_field IN (1,2,3) OR test_field IN (4,5,6) OR test_field IN (7)");
    }

    @Test
    @DisplayName("Given that list equals 1")
    public void givenThatListEquals1() {
        String query = new InClauseExpression("test_field", getIds(1), 3).getQuery();
        assertThat(query).isEqualTo(" test_field IN (1)");
    }

    @Test
    @DisplayName("Given that list equals in clause")
    public void givenThatListEqualsInClause() {
        String query = new InClauseExpression("test_field", getIds(3), 3).getQuery();
        assertThat(query).isEqualTo(" test_field IN (1,2,3)");
    }

    @Test
    @DisplayName("Given that it has a prefix")
    public void givenThatItHasAPrefix() {
        String query = new InClauseExpression("test_field", getIds(3), "AND", 3).getQuery();
        assertThat(query).isEqualTo(" AND test_field IN (1,2,3)");
    }

    @Test
    @DisplayName("Given that validation is true")
    public void givenThatValidationIsTrue() {
        String query = new InClauseExpression("test_field", getIds(3), t -> true, 3).getQuery();
        assertThat(query).isEqualTo(" test_field IN (1,2,3)");
    }

    @Test
    @DisplayName("Given that validation is false")
    public void givenThatValidationIsFalse() {
        String query = new InClauseExpression("test_field", getIds(3), t -> false, 3).getQuery();
        assertThat(query).isEmpty();
    }

    private List<Integer> getIds(int quantity) {
        return IntStream.rangeClosed(1, quantity).boxed().collect(Collectors.toList());
    }


}
