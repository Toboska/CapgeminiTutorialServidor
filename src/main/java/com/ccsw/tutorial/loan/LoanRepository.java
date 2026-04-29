package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findAll(Pageable pageable);

    /**
     * Recupera una página de préstamos que cumplen con la especificación proporcionada.
     * @param spec     Especificación con los filtros aplicados.
     * @param pageable Configuración de paginación.
     * @return Una página de resultados.
     */
    @EntityGraph(attributePaths = { "game", "client" })
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable);

    boolean existsByClientId(Long id);
}
