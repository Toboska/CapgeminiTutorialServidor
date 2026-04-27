package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
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
public class PrestamoServiceImpl implements PrestamoService {

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
    public Page<Prestamo> findPage(Pageable pageable, Long gameId, Long clientId, LocalDate date) {

        Specification<Prestamo> spec = Specification.anyOf();

        if (gameId != null) {
            spec = spec.and(new PrestamoSpecification(new SearchCriteria("game.id", ":", gameId)));
        }

        if (clientId != null) {
            spec = spec.and(new PrestamoSpecification(new SearchCriteria("client.id", ":", clientId)));
        }

        if (date != null) {
            spec = spec.and(PrestamoSpecification.dateBetween(date));
        }

        return prestamoRepository.findAll(spec, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Prestamo> findAll() {
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

        if (checkAllPrestamoRequirements(dto)) {
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
        } else {
            throw new RuntimeException("No se cumplen las condiciones");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkValidDateRange(LocalDate fechaPrestamo, LocalDate fechaDevolucion) {

        return (ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion) <= 14 && (ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion)) >= 0);
    }

    /**
     * {@inheritDoc}
     */
    //Cambiar a Specification
    @Override
    public boolean isGameAvailable(Long gameId, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Long prestamoId) {

        return !this.prestamoRepository.isGameOccupied(gameId, fechaPrestamo, fechaDevolucion, prestamoId);
    }

    /**
     * {@inheritDoc}
     */
    //Cambiar a Specification
    @Override
    public boolean isClientInCurrentLoan(Long clientId, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Long prestamoId) {

        return !this.prestamoRepository.isClientOccupied(clientId, fechaPrestamo, fechaDevolucion, prestamoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAllPrestamoRequirements(PrestamoDto dto) {
        if (dto.getFechaPrestamo() == null || dto.getFechaDevolucion() == null) {
            return false;
        }

        return checkValidDateRange(dto.getFechaPrestamo(), dto.getFechaDevolucion()) && isGameAvailable(dto.getGame().getId(), dto.getFechaPrestamo(), dto.getFechaDevolucion(), dto.getId()) && isClientInCurrentLoan(dto.getClient().getId(),
                dto.getFechaPrestamo(), dto.getFechaDevolucion(), dto.getId());
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

}
