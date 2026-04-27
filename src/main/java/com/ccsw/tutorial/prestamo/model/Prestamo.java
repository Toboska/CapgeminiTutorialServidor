package com.ccsw.tutorial.prestamo.model;

import com.ccsw.tutorial.client.model.Client;
import com .ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author ccsw
 *
 */
@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fechaPrestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fechaDevolucion", nullable = false)
    private LocalDate fechaDevolucion;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return fechaPrestamo
     */
    public LocalDate getFechaPrestamo() {
        return this.fechaPrestamo;
    }

    /**
     * @param fechaPrestamo new value of {@link #getFechaPrestamo}.
     */
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * @return fechaDevolucion
     */
    public LocalDate getFechaDevolucion() {
        return this.fechaDevolucion;
    }

    /**
     * @param fechaDevolucion new value of {@link #getFechaDevolucion}.
     */
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * @return game
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return client
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
