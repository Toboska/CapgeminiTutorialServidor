package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @loan ccsw
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param pageable Configuración de paginación
     * @param gameId   PK del juego
     * @param clientId PK del cliente
     * @param date     Fecha a filtrar
     */
    @Operation(summary = "Find Page", description = "Method that return a page of Loans")
    @GetMapping
    public Page<LoanDto> findPage(Pageable pageable, @RequestParam(required = false) Long gameId, @RequestParam(required = false) Long clientId,
            @RequestParam(required = false, value = "date") @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date) {
        System.out.println(date);
        Page<Loan> page = loanService.findPage(pageable, gameId, clientId, date);

        return page.map(e -> mapper.map(e, LoanDto.class));
    }

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Loan")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDto dto) {

        this.loanService.save(id, dto);
    }

    /**
     * Método para borrar una {@link Category}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.loanService.delete(id);
    }

}