package com.ccsw.tutorial.loan;

import ch.qos.logback.core.util.StringUtil;
import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.exception.BusinessBadRequestException;
import com.ccsw.tutorial.exception.BusinessConflictException;
import com.ccsw.tutorial.exception.BusinessNotFoundException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(Pageable pageable, Long gameId, Long clientId, LocalDate date) {

        Specification<Loan> spec = Specification.anyOf();

        if (gameId != null) {
            spec = spec.and(new LoanSpecification(new SearchCriteria("game.id", ":", gameId)));
        }

        if (clientId != null) {
            spec = spec.and(new LoanSpecification(new SearchCriteria("client.id", ":", clientId)));
        }

        if (date != null) {
            spec = spec.and(LoanSpecification.dateBetween(date));
        }

        return loanRepository.findAll(spec, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {

        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) {

        checkAllLoanRequirements(dto);

        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.get(id);
        }

        BeanUtils.copyProperties(dto, loan, "id", "client", "game");

        loan.setClient(clientService.get(dto.getClient().getId()));
        loan.setGame(gameService.get(dto.getGame().getId()));

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkValidDateRange(LocalDate loanStartDate, LocalDate loanEndDate) {

         if(!(ChronoUnit.DAYS.between(loanStartDate, loanEndDate) <= 14 && (ChronoUnit.DAYS.between(loanStartDate, loanEndDate)) >= 0)){
             throw new BusinessConflictException("MAX_DURATION_EXCEEDED","El periodo solicitado no puede superar los 14 días.", "loanEndDate");
         }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void isGameAvailable(Long gameId, LocalDate loanStartDate, LocalDate loanEndDate, Long loanId) {

        Specification<Loan> spec = Specification.allOf(LoanSpecification.existsGameWithId(gameId), LoanSpecification.hasOverlapBetweenDates(loanStartDate, loanEndDate), LoanSpecification.excludeLoanId(loanId));

        if(loanRepository.exists(spec)){
            throw new BusinessConflictException("GAME_ALREADY_RESERVED", "El juego ya está reservado por otro usuario en las fechas seleccionadas.", "gameId");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void isClientInCurrentLoan(Long clientId, LocalDate loanStartDate, LocalDate loanEndDate, Long loanId) {

        Specification<Loan> spec = Specification.allOf(LoanSpecification.existsClientWithId(clientId), LoanSpecification.hasOverlapBetweenDates(loanStartDate, loanEndDate), LoanSpecification.excludeLoanId(loanId));

        if(loanRepository.exists(spec)){
            throw new BusinessConflictException("CLIENT_ALREADY_IN_CURRENT_LOAN", "El cliente ya tiene una reserva activa para el periodo seleccionado", "clientId");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkAllLoanRequirements(LoanDto dto) {

        validateLoanDtoFieldsNotNull(dto);

        checkValidDateRange(dto.getLoanStartDate(), dto.getLoanEndDate());

        isGameAvailable(dto.getGame().getId(), dto.getLoanStartDate(), dto.getLoanEndDate(), dto.getId());

        isClientInCurrentLoan(dto.getClient().getId(), dto.getLoanStartDate(), dto.getLoanEndDate(), dto.getId());
    }

    @Override
    public void validateLoanDtoFieldsNotNull(LoanDto dto){
        
        if (dto.getLoanStartDate() == null) {
            throw new BusinessBadRequestException("FIELD_CANNOT_BE_EMPTY", "La fecha de inicio no puede estar vacía", "loanStartDate");
        }

        if (dto.getLoanEndDate() == null){
            throw new BusinessBadRequestException("FIELD_CANNOT_BE_EMPTY", "La fecha de fin no puede estar vacía", "loanEndDate");
        }

        if (!StringUtils.hasText(dto.getClient().getName())){
            throw new BusinessBadRequestException("THIS_NAME_IS_NULL", "El nombre de usuario no puede estar vacío", "name");
        }

        if (!StringUtils.hasText(dto.getGame().getTitle())){
            throw new BusinessBadRequestException("THIS_GAME_TITLE_IS_NULL", "El nombre del juego no puede ser nulo", "title");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new BusinessNotFoundException("THIS_LOAN_NOT_EXISTS", "No hay un préstamo con ese id", "id");
        }

        this.loanRepository.deleteById(id);
    }

}
