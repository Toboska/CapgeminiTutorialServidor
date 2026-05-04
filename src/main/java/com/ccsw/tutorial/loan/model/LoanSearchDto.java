package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

import java.time.LocalDate;

public class LoanSearchDto {

    private PageableRequest pageable;

    private Long clientId;

    private Long gameId;

    private LocalDate dateSelected;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        gameId = gameId;
    }

    public LocalDate getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(LocalDate dateSelected) {
        this.dateSelected = dateSelected;
    }

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
