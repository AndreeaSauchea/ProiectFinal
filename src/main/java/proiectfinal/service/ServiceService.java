package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.ServiceRequest;
import proiectfinal.controller.dto.ServiceResponse;
import proiectfinal.exception.ServiceNotFoundException;
import proiectfinal.model.Service;
import proiectfinal.repository.ServiceReopository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceService {
    @Autowired
    private ServiceReopository serviceReopository;

    public List<ServiceResponse> findAll() {
        List<Service> services = (List<Service>) serviceReopository.findAll();
        List<ServiceResponse> serviceResponseList = new ArrayList<>();
        for (Service addService : services){
            serviceResponseList.add(buildResponse(addService));
        }
        return serviceResponseList;
    }

    public ServiceResponse buildResponse(Service service) {
        ServiceResponse response = new ServiceResponse();
        response.setServiceDuration(service.getServiceDuration());
        response.setServiceName(service.getServiceName());
        response.setServicePrice(service.getServicePrice());
        response.setId(service.getId());
        return response;
    }

    public List<ServiceResponse> buildListResponse(List<Service> serviceList) {
        List<ServiceResponse> responseList = new ArrayList<>();
        for (Service service : serviceList){
            ServiceResponse response = new ServiceResponse();
            response.setServiceDuration(service.getServiceDuration());
            response.setServiceName(service.getServiceName());
            response.setServicePrice(service.getServicePrice());
            response.setId(service.getId());
            responseList.add(response);
        }
        return responseList;
    }

    public ServiceResponse save(ServiceRequest serviceRequest) {
        Service service = new Service();
        service.setServicePrice(serviceRequest.getServicePrice());
        service.setServiceDuration(serviceRequest.getServiceDuration());
        service.setServiceName(serviceRequest.getServiceName());
        Service saveService = serviceReopository.save(service);
        return buildResponse(saveService);
    }

    public ServiceResponse findById(long id) throws ServiceNotFoundException {
        return buildResponse(findService(id));
    }

    public void deleteById(long id) {
        serviceReopository.deleteById(id);
    }

    public ServiceResponse updateService(Long id, ServiceRequest newServiceRequest) throws ServiceNotFoundException {
       Service service = findService(id);
       service.setServiceDuration(newServiceRequest.getServiceDuration());
       service.setServicePrice(newServiceRequest.getServicePrice());
       Service saveService = serviceReopository.save(service);
       return buildResponse(saveService);
    }

    Service findService(Long id) throws ServiceNotFoundException {
        Optional<Service> optionalService = serviceReopository.findById(id);
        if (optionalService.isPresent()) {
            Service room = optionalService.get();
            return room;
        }else {
            throw new ServiceNotFoundException();
        }
    }
}
