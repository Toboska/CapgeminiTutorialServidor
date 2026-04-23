package com.ccsw.tutorial.prestamo.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.Game;
import com .ccsw.tutorial.game.model.GameDto;

import java.util.Date;

/**
 * @author ccsw
 *
 */
public class PrestamoDto {

    private Long id;

    private Date fechaPrestamo;

    private Date fechaDevolucion;

    private GameDto game;

    private ClientDto client;

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
    public Date getFechaPrestamo() {
        return this.fechaPrestamo;
    }

    /**
     * @param fechaPrestamo new value of {@link #getFechaPrestamo}.
     */
    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * @return fechaDevolucion
     */
    public Date getFechaDevolucion() {
        return this.fechaDevolucion;
    }

    /**
     * @param fechaDevolucion new value of {@link #getFechaDevolucion}.
     */
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * @return game
     */
    public GameDto getGame() {
        return this.game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return client
     */
    public ClientDto getClient() {
        return this.client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }
}
