package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ccsw
 *
 */
public interface PrestamoService {

    /**
     * Método para recuperar un listado paginado de {@link Prestamo}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Prestamo}
     */
    Page<Prestamo> findPage(PrestamoSearchDto dto);

    /**
     * Recupera un listado de autores {@link Prestamo}
     *
     * @return {@link List} de {@link Prestamo}
     */
    List<Prestamo> findAll();

    /**
     * Recupera un {@link Prestamo} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Prestamo}
     */
    Prestamo get(Long id);

    /**
     * Método para crear o actualizar un {@link Prestamo}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, PrestamoDto dto);

    //FUNCIONES PARA CONTROLAR LA VIABILIDAD DEL PRÉSTAMO

    /**
     * Método para controlar la fecha de fin es mayor a la de entrada de {@link Prestamo}
     *
     * @param fechaPrestamo de la entidad
     * @param fechaDevolucion de la entidad
     * @return {boolean} true si la fecha de devolución es válida (posterior a la de inicio), false en caso contrario.
     */
    boolean checkValidDateRange(LocalDate fechaPrestamo, LocalDate fechaDevolucion);

    /**
     *
     * Método para controlar si un juego hay un juego prestado entre dos fechas {@link Prestamo}
     *
     * @param gameId del juego
     * @param fechaPrestamo de la entidad
     * @param fechaDevolucion de la entidad
     * @return {boolean true si está prestado, false en caso contrario.
     */
    boolean isGameAvailable(Long gameId, LocalDate fechaPrestamo, LocalDate fechaDevolucion);

    /**
     *
     * Método para comporbar si un usuario tiene un préstamo en curso entre dos fechas {@link Prestamo}
     *
     * @param clientId del cliente
     * @param fechaPrestamo de la entidad
     * @param fechaDevolucion de la entidad
     * @return {boolean si el cliente tiene préstamo entre las fechas, false en caso contrario.
     */
    boolean isClientInCurrentLoan(Long clientId, LocalDate fechaPrestamo, LocalDate fechaDevolucion);

    /**
     * Método para controlar que las fechas no son nulas y comprobar todos los requisitos {@link Prestamo}
     *
     * @param dto entidad
     * @return {boolean} true si se cumplen todos los requisitos, false en caso contrario.
     */
    boolean checkAllPrestamoRequirements(PrestamoDto dto);

    /**
     * Método para borrar una {@link Prestamo}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    /**
     * Recupera una página de préstamos filtrando opcionalmente por juego, cliente y/o fecha
     *
     * @param idGame   PK del juego
     * @param idClient PK del cliente
     * @param date     Fecha a filtrar
     * @param pageable Configuración de paginación
     * @return {@link Page} de {@link Prestamo}
     */
    Page<Prestamo> find(Long idGame, Long idClient, LocalDate date, Pageable pageable);
}
