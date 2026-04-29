package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ccsw
 *
 */
public interface LoanService {

    /**
     * Recupera una página de préstamos filtrando opcionalmente por juego, cliente y/o fecha
     *
     * @param pageable Configuración de paginación
     * @param gameId   PK del juego
     * @param clientId PK del cliente
     * @param date     Fecha a filtrar
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(Pageable pageable, Long gameId, Long clientId, LocalDate date);

    /**
     * Recupera un listado de autores {@link Loan}
     *
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findAll();

    /**
     * Recupera un {@link Loan} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto);

    //FUNCIONES PARA CONTROLAR LA VIABILIDAD DEL PRÉSTAMO

    /**
     * Método para controlar la fecha de fin es mayor a la de entrada de {@link Loan}
     *
     * @param loanStartDate de la entidad
     * @param loanEndDate de la entidad
     * @return {boolean} true si la fecha de devolución es válida (posterior a la de inicio), false en caso contrario.
     */
    void checkValidDateRange(LocalDate loanStartDate, LocalDate loanEndDate);

    /**
     *
     * Método para controlar si un juego hay un juego prestado entre dos fechas {@link Loan}
     *
     * @param gameId        del juego
     * @param loanStartDate de la entidad
     * @param loanEndDate   de la entidad
     */
    void isGameAvailable(Long gameId, LocalDate loanStartDate, LocalDate loanEndDate, Long loanId);

    /**
     *
     * Método para comporbar si un usuario tiene un préstamo en curso entre dos fechas {@link Loan}
     *
     * @param clientId      del cliente
     * @param loanStartDate de la entidad
     * @param loanEndDate   de la entidad
     */
    void isClientInCurrentLoan(Long clientId, LocalDate loanStartDate, LocalDate loanEndDate, Long loanId);

    /**
     * Método para controlar que las fechas no son nulas y comprobar todos los requisitos {@link Loan}
     *
     * @param dto entidad
     */
    void checkAllLoanRequirements(LoanDto dto);

    /**
     * Método para borrar una {@link Loan}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}
