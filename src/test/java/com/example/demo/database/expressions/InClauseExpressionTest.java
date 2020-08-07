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
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .build("test_field")
                .getQuery();

        assertThat(query).isEmpty();
    }

    @Test
    @DisplayName("Given that empty list")
    public void givenThatEmptyList() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(Collections.emptyList())
                .build("test_field")
                .getQuery();

        assertThat(query).isEmpty();
    }

    @Test
    @DisplayName("Given that field name is null")
    public void givenThatFieldNameIsNull() {
        assertThatNullPointerException().isThrownBy(() -> {
            InClauseExpression.builder()
                    .withInClauseLimit(3)
                    .withIds(getIds(7))
                    .build(null)
                    .getQuery();

        }).withMessage("'field name' is required.");
    }

    @Test
    @DisplayName("Given that list greater than in clause")
    public void givenThatListGreaterThanInClause() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(getIds(7))
                .build("test_field")
                .getQuery();

        assertThat(query).isEqualTo(" test_field IN (1,2,3) OR test_field IN (4,5,6) OR test_field IN (7)");
    }

    @Test
    @DisplayName("Given that list equals 1")
    public void givenThatListEquals1() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(getIds(1))
                .build("test_field")
                .getQuery();

        assertThat(query).isEqualTo(" test_field IN (1)");
    }

    @Test
    @DisplayName("Given that list equals in clause")
    public void givenThatListEqualsInClause() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(getIds(3))
                .build("test_field")
                .getQuery();

        assertThat(query).isEqualTo(" test_field IN (1,2,3)");
    }

    @Test
    @DisplayName("Given that it has a prefix")
    public void givenThatItHasAPrefix() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(getIds(3))
                .withPrefix("AND")
                .build("test_field")
                .getQuery();

        assertThat(query).isEqualTo(" AND test_field IN (1,2,3)");
    }

    @Test
    @DisplayName("Given that validation is true")
    public void givenThatValidationIsTrue() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(getIds(3))
                .withValidation(t -> true)
                .build("test_field")
                .getQuery();

        assertThat(query).isEqualTo(" test_field IN (1,2,3)");
    }

    @Test
    @DisplayName("Given that validation is false")
    public void givenThatValidationIsFalse() {
        String query = InClauseExpression.builder()
                .withInClauseLimit(3)
                .withIds(getIds(3))
                .withValidation(t -> false)
                .build("test_field")
                .getQuery();

        assertThat(query).isEmpty();
    }

    private List<Integer> getIds(int quantity) {
        return IntStream.rangeClosed(1, quantity).boxed().collect(Collectors.toList());
    }


}
