package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PrestamoSpecification implements Specification<Prestamo> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public PrestamoSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static Specification<Prestamo> dateBetween(LocalDate date) {
        return (Root<Prestamo> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.and(builder.lessThanOrEqualTo(root.get("fechaPrestamo"), date), builder.greaterThanOrEqualTo(root.get("fechaDevolucion"), date));
    }
    
    @Override
    public Predicate toPredicate(Root<Prestamo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {

            Path<?> path = getPath(root);

            // Strings → LIKE
            if (path.getJavaType() == String.class) {
                return builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
            }

            // Resto de tipos → igualdad
            return builder.equal(path, criteria.getValue());
        }

        return null;
    }

    private Path<?> getPath(Root<Prestamo> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<?> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

    public static Specification<Prestamo> mismoJuego(Long juegoId) {
        return (root, query, cb) ->
                cb.equal(root.get("game").get("id"), juegoId);
    }

    public static Specification<Prestamo> excluirId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.notEqual(root.get("id"), id);
    }

    public static Specification<Prestamo> seSolapa(LocalDate inicio, LocalDate fin) {
        return (root, query, cb) -> {

            return cb.and(
                    cb.lessThanOrEqualTo(root.get("fechaPrestamo"), fin),
                    cb.greaterThanOrEqualTo(root.get("fechaDevolucion"), inicio)
            );
        };
    }

    public static Specification<Prestamo> mismoCliente(Long clientId) {
        return (root, query, cb) ->
                cb.equal(root.get("client").get("id"), clientId);
    }

}