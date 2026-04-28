package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.time.LocalDate;

/**
 * @author ccsw
 *
 */
public class LoanDto {

    private Long id;

    private LocalDate loanStartDate;

    private LocalDate loanEndDate;

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
     * @return loanStartDate
     */
    public LocalDate getLoanStartDate() {
        return this.loanStartDate;
    }

    /**
     * @param loanStartDate new value of {@link #getLoanStartDate}.
     */
    public void setLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    /**
     * @return loanEndDate
     */
    public LocalDate getLoanEndDate() {
        return this.loanEndDate;
    }

    /**
     * @param loanEndDate new value of {@link #getLoanEndDate}.
     */
    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
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
