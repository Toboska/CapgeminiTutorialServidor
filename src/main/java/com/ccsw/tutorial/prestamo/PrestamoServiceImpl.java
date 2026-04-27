package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService{

    @Autowired
    PrestamoRepository prestamoRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Prestamo> findPage(PrestamoSearchDto dto){

        return this.prestamoRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Prestamo> findAll(){
        return (List<Prestamo>) this.prestamoRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Prestamo get(Long id) {

        return this.prestamoRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, PrestamoDto dto) {

        if(checkAllPrestamoRequirements(dto)){
            Prestamo prestamo;

            if (id == null) {
                prestamo = new Prestamo();
            } else {
                prestamo = this.get(id);
            }

            BeanUtils.copyProperties(dto, prestamo, "id", "client", "game");

            prestamo.setClient(clientService.get(dto.getClient().getId()));
            prestamo.setGame(gameService.get(dto.getGame().getId()));

            this.prestamoRepository.save(prestamo);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkValidDateRange(LocalDate fechaPrestamo, LocalDate fechaDevolucion){
        return (ChronoUnit.DAYS.between(fechaPrestamo,fechaDevolucion) <= 14 && (ChronoUnit.DAYS.between(fechaPrestamo,fechaDevolucion)) >= 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameAvailable(Long gameId, LocalDate fechaPrestamo, LocalDate fechaDevolucion){
        return !this.prestamoRepository.isGameOccupied(gameId, fechaPrestamo, fechaDevolucion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClientInCurrentLoan(Long clientId, LocalDate fechaPrestamo, LocalDate fechaDevolucion){
        return !this.prestamoRepository.isClientOccupied(clientId, fechaPrestamo, fechaDevolucion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAllPrestamoRequirements(PrestamoDto dto){
        if(dto.getFechaPrestamo() == null || dto.getFechaDevolucion() == null){
            return false;
        }

        return checkValidDateRange(dto.getFechaPrestamo(), dto.getFechaDevolucion())
                && isGameAvailable(dto.getGame().getId(), dto.getFechaPrestamo(), dto.getFechaDevolucion())
                && isClientInCurrentLoan(dto.getClient().getId(), dto.getFechaPrestamo(), dto.getFechaDevolucion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.prestamoRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Prestamo> find(Long idGame, Long idClient, LocalDate date, Pageable pageable) {

        PrestamoSpecification gameSpec = new PrestamoSpecification(new SearchCriteria("game.id", ":", idGame));
        PrestamoSpecification clientSpec = new PrestamoSpecification(new SearchCriteria("client.id", ":", idClient));
        PrestamoSpecification dateSpec = new PrestamoSpecification(new SearchCriteria("fechaPrestamo", ":", date));

        Specification<Prestamo> spec = Specification.unrestricted();

        spec = spec.and(gameSpec)
                .and(clientSpec)
                .and(dateSpec);

        return this.prestamoRepository.findAll(spec, pageable);
    }
}
