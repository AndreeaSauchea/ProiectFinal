package proiectfinal.repository;

import org.springframework.data.repository.CrudRepository;
import proiectfinal.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
