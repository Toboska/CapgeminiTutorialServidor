package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long> {
    /**
     * Método para recuperar un listado paginado de {@link Prestamo}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Prestamo}
     */
    Page<Prestamo> findAll(Pageable pageable);

    /**
     *
     * Método para controlar si un juego hay un juego prestado entre dos fechas {@link Prestamo}
     *
     * @param gameId del juego
     * @param fechaPrestamo de la entidad
     * @param fechaDevolucion de la entidad
     * @return {boolean true si está prestado, false en caso contrario.
     */
    @Query("SELECT COUNT(p) > 0 FROM Prestamo p " +
            "WHERE p.game.id = :gameId " +
            "AND (:start <= p.fechaDevolucion AND :end >= p.fechaPrestamo)")
    boolean isGameOccupied(
            @Param("gameId") Long gameId,
            @Param("start") LocalDate fechaPrestamo,
            @Param("end") LocalDate fechaDevolucion
    );

    /**
     *
     * Método para comporbar si un usuario tiene un préstamo en curso entre dos fechas {@link Prestamo}
     *
     * @param clientId del cliente
     * @param fechaPrestamo de la entidad
     * @param fechaDevolucion de la entidad
     * @return {boolean si el cliente tiene préstamo entre las fechas, false en caso contrario.
     */
    @Query("SELECT count(p) > 0 FROM Prestamo p " +
            "WHERE p.client.id = :clientId " +
            "AND (:start <= p.fechaDevolucion AND :end >= p.fechaPrestamo)")
    boolean isClientOccupied(
            @Param("clientId") Long clientId,
            @Param("start") LocalDate fechaPrestamo,
            @Param("end") LocalDate fechaDevolucion
    );

    /**
     * Recupera una página de préstamos que cumplen con la especificación proporcionada.
     * * @param spec     Especificación con los filtros aplicados.
     * @param pageable Configuración de paginación.
     * @return Una página de resultados.
     */
    @EntityGraph(attributePaths = {"game", "client"})
    Page<Prestamo> findAll(Specification<Prestamo> spec, Pageable pageable);
}
