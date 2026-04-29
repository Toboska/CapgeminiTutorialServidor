package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.exception.BusinessBadRequestException;
import com.ccsw.tutorial.exception.BusinessConflictException;
import com.ccsw.tutorial.exception.BusinessNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) {

        Client client;

        validateNameNotNull(dto.getName());

        if (id == null) {
            validateNameNotExists(dto.getName());
            client = new Client();
        } else {
            validateClientNameNotExistsWhenId(id, dto);
            client = this.get(id);
        }

        client.setName(dto.getName());

        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateClientNameNotExistsWhenId(Long id, ClientDto dto) {
        clientRepository.findByName(dto.getName()).filter(existing -> !existing.getId().equals(id)).ifPresent(existing -> {
            throw new BusinessConflictException("THIS_NAME_ALREADY_EXISTS", "Este nombre de usuario ya está dado de alta en la aplicación.", "name");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateNameNotNull(String clientName) {
        if (!StringUtils.hasText(clientName)) {
            throw new BusinessBadRequestException("THIS_NAME_IS_NULL", "El nombre de usuario no puede estar vacío", "name");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateNameNotExists(String clientName) {
        if (this.clientRepository.existsByName(clientName)) {
            //BAD REQUEST, la petición ha llegado correctamente, sin embargo no cumple la lógica de negocio
            //Este error es un 409
            throw new BusinessConflictException("THIS_NAME_ALREADY_EXISTS", "Este nombre de usuario ya está dado de alta en la aplicación.", "name");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    //TODO hacer que tenga en cuenta el caso de que sea la fk en otro tabla
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new BusinessNotFoundException("THIS_CLIENT_NOT_EXISTS", "No hay un cliente ese id", "id");
        }

        this.clientRepository.deleteById(id);
    }
}
