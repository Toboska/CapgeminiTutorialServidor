package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
    //Definimos las consultas a los datos

    /**
     * Método para saber si un nombre existe en {@link Client}
     *
     * @param name nombre del cliente a buscar
     * @return {@code true} si el nombre existe; {@code false} si no
     */
    boolean existsByName(String name);


}
