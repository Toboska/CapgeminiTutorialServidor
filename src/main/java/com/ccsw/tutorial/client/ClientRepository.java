package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
    //Definimos las consultas a los datos
    boolean existsByName(String name);
}
