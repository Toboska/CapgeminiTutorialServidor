package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PrestamoSpecification implements Specification<Prestamo> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public PrestamoSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Prestamo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<?> path = getPath(root);

            // Caso 1: Atributos de tipo String (usamos LIKE para búsqueda parcial)
            if (path.getJavaType() == String.class) {
                return builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
            }

            // Caso 2: Atributos de tipo Date
            else if (path.getJavaType() == Date.class) {
                // Si la operación es ":" en fechas, solemos buscar registros donde la fecha buscada
                //                // esté dentro del rango del préstamo (fechaPrestamo <= buscada <= fechaDevolucion)
                // Pero si solo filtras por un campo exacto, usamos equal:
                return builder.equal(path, criteria.getValue());
            }

            // Caso 3: Resto de tipos (Long, Integer, Objetos/Entity)
            else {
                return builder.equal(path, criteria.getValue());
            }
        }

        // Caso para operaciones de comparación de fechas (opcional pero recomendado)
        if (criteria.getOperation().equalsIgnoreCase("<=")) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), (Date) criteria.getValue());
        }
        if (criteria.getOperation().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Date) criteria.getValue());
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
}