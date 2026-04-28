package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

        if (checkAllLoanRequirements(dto)) {
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
        } else {
            throw new RuntimeException("No se cumplen las condiciones");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkValidDateRange(LocalDate loanStartDate, LocalDate loanEndDate) {

        return (ChronoUnit.DAYS.between(loanStartDate, loanEndDate) <= 14 && (ChronoUnit.DAYS.between(loanStartDate, loanEndDate)) >= 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameAvailable(Long gameId, LocalDate loanStartDate, LocalDate loanEndDate, Long loanId) {

        Specification<Loan> spec = Specification.allOf(LoanSpecification.existsGameWithId(gameId), LoanSpecification.hasOverlapBetweenDates(loanStartDate, loanEndDate), LoanSpecification.excludeLoanId(loanId));

        return !loanRepository.exists(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClientInCurrentLoan(Long clientId, LocalDate loanStartDate, LocalDate loanEndDate, Long loanId) {

        Specification<Loan> spec = Specification.allOf(LoanSpecification.existsClientWithId(clientId), LoanSpecification.hasOverlapBetweenDates(loanStartDate, loanEndDate), LoanSpecification.excludeLoanId(loanId));

        return !loanRepository.exists(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAllLoanRequirements(LoanDto dto) {
        if (dto.getLoanStartDate() == null || dto.getLoanEndDate() == null) {
            return false;
        }

        return checkValidDateRange(dto.getLoanStartDate(), dto.getLoanEndDate()) && isGameAvailable(dto.getGame().getId(), dto.getLoanStartDate(), dto.getLoanEndDate(), dto.getId()) && isClientInCurrentLoan(dto.getClient().getId(),
                dto.getLoanStartDate(), dto.getLoanEndDate(), dto.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

}
