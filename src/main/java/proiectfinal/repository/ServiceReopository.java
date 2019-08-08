package proiectfinal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiectfinal.model.Service;

@Repository
public interface ServiceReopository extends CrudRepository<Service, Long> {
    Service findByServiceName(String serviceName);
}
