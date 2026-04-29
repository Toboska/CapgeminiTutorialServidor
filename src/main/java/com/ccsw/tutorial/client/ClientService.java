package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.exception.BusinessBadRequestException;
import com.ccsw.tutorial.exception.BusinessConflictException;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface ClientService {
    /**
     * Recupera un {@link Client} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Client}
     */
    Client get(Long id);

    /**
     * Método para recuperar todas los {@link Client}
     *
     * @return {@link List} de {@link Client}
     */
    List<Client> findAll();

    /**
     * Método para crear o actualizar un {@link Client}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ClientDto dto);

    /**
     * Método para borrar una {@link Client}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    /**
     * Valida que el nombre no esté vacío para uno {@link Client}
     *
     * @param clientName nombre del cliente a validar
     * @throws BusinessBadRequestException si está vacío
     */
    void validateNameNotNull(String clientName);

    /**
     * Valida que no exista ya un cliente con el mismo nombre. {@link Client}
     *
     * @param clientName nombre del cliente
     * @throws BusinessConflictException si ya existe un cliente con ese nombre
     */
    void validateNameNotExists(String clientName);

    /**
     * Valida que no exista otro cliente con el mismo nombre al actualizar uno existente. {@link Client}
     *
     * @param id identificador del cliente
     * @param dto datos del cliente
     * @throws BusinessConflictException si existe otro cliente con el mismo nombre
     */
    void validateClientNameNotExistsWhenId(Long id, ClientDto dto);
}
