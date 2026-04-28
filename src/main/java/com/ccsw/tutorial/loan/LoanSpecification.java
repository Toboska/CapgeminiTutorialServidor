package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static Specification<Loan> dateBetween(LocalDate date) {
        return (Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.and(builder.lessThanOrEqualTo(root.get("loanStartDate"), date), builder.greaterThanOrEqualTo(root.get("loanEndDate"), date));
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {

            Path<?> path = getPath(root);

            if (path.getJavaType() == String.class) {
                return builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
            }

            return builder.equal(path, criteria.getValue());
        }

        return null;
    }

    private Path<?> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<?> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

    public static Specification<Loan> existsGameWithId(Long gameId) {
        return (root, query, cb) -> cb.equal(root.get("game").get("id"), gameId);
    }

    public static Specification<Loan> excludeLoanId(Long id) {

        return (root, query, cb) -> id == null ? null : cb.notEqual(root.get("id"), id);
    }

    public static Specification<Loan> hasOverlapBetweenDates(LocalDate loanStartDate, LocalDate loanEndDate) {
        return (root, query, cb) -> {

            return cb.and(cb.lessThanOrEqualTo(root.get("loanStartDate"), loanEndDate), cb.greaterThanOrEqualTo(root.get("loanEndDate"), loanStartDate));
        };
    }

    public static Specification<Loan> existsClientWithId(Long clientId) {

        return (root, query, cb) -> cb.equal(root.get("client").get("id"), clientId);
    }

}