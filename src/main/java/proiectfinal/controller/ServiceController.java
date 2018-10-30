package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.exception.ServiceNotFoundException;
import proiectfinal.model.Service;
import proiectfinal.service.ServiceService;

import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/services")
    public List<Service> findServices() {

        return serviceService.findAll();

    }

    @PostMapping("/services")
    public Service saveService(@RequestBody Service newService) {
        return serviceService.save(newService);
    }

    @GetMapping("/services/{id}")
    public Service getServeceById(@PathVariable Long id) {

        return serviceService.findById(id).orElseThrow(ServiceNotFoundException::new);
    }

    @PutMapping("/services/{id}")
    Service updateService(@RequestBody Service newService, @PathVariable Long id) {

        return serviceService.updateService(id, newService);
    }

    @DeleteMapping("/services/{id}")
    void deleteService(@PathVariable Long id) {
        serviceService.deleteById(id);
    }


}
