package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.controller.dto.ServiceRequest;
import proiectfinal.controller.dto.ServiceResponse;
import proiectfinal.exception.ServiceNotFoundException;
import proiectfinal.model.Service;
import proiectfinal.service.ServiceService;

import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/services")
    public List<ServiceResponse> findServices() {
        return serviceService.findAll();
    }

    @PostMapping("/services")
    public ServiceResponse saveService(@RequestBody ServiceRequest newServiceRequest) {
        return serviceService.save(newServiceRequest);
    }

    @GetMapping("/services/{id}")
    public ServiceResponse getServeceById(@PathVariable Long id) throws ServiceNotFoundException {
        return serviceService.findById(id);
    }

    @PutMapping("/services/{id}")
    ServiceResponse updateService(@RequestBody ServiceRequest newServiceRequest, @PathVariable Long id) throws ServiceNotFoundException {
        return serviceService.updateService(id, newServiceRequest);
    }

    @DeleteMapping("/services/{id}")
    void deleteService(@PathVariable Long id) {
        serviceService.deleteById(id);
    }


}
