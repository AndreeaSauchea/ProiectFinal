package proiectfinal.repository;

import org.springframework.data.repository.CrudRepository;
import proiectfinal.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    List<Client> findAll();

    Client findByCnp(String cnp);
}
