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
@CrossOrigin
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
    public ServiceResponse getServeceById(@PathVariable Long id) throws Exception {
        return serviceService.findById(id);
    }

    @PutMapping("/services/{serviceName}")
    ServiceResponse updateService(@RequestBody ServiceRequest newServiceRequest, @PathVariable String serviceName) throws Exception {
        return serviceService.updateService(serviceName, newServiceRequest);
    }

    @DeleteMapping("/services/{id}")
    void deleteService(@PathVariable Long id) {
        serviceService.deleteById(id);
    }


}
