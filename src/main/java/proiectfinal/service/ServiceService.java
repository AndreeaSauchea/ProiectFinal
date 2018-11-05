package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.exception.ServiceNotFoundException;
import proiectfinal.model.Service;
import proiectfinal.repository.ServiceReopository;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceService {
    @Autowired
    private ServiceReopository serviceReopository;

    public List<Service> findAll() {

        return (List<Service>) serviceReopository.findAll();
    }

    public Service save(Service service) {
        return serviceReopository.save(service);
    }

    public Optional<Service> findById(long id) {
        return serviceReopository.findById(id);
    }

    public void deleteById(long id) {
        serviceReopository.deleteById(id);
    }

    public Service updateService(Long id, Service newService) throws ServiceNotFoundException {
        Optional<Service> optionalService = serviceReopository.findById(id);
        if (optionalService.isPresent()) {
            Service room = optionalService.get();
            room.setServiceDuration(newService.getServiceDuration());
            room.setServicePrice(newService.getServicePrice());
            room.setServiceName(newService.getServiceName());
            return serviceReopository.save(room);
        } else {
            throw new ServiceNotFoundException();
        }
    }
}
