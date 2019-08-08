package proiectfinal.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.ServiceRequest;
import proiectfinal.controller.dto.ServiceResponse;
import proiectfinal.exception.ServiceNotFoundException;
import proiectfinal.model.Service;
import proiectfinal.repository.ServiceReopository;
import proiectfinal.utils.OptionalEntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static proiectfinal.service.RoomService.REQUEST_IS_NULL;

@Component
public class ServiceService {

    public static final String NO_SERVICE_WAS_FOUND = "No service was found";
    public static final String YOU_HAVE_TO_SPECIFY_NAME = "You have to specify name";
    public static final String YOU_HAVE_TO_SPECIFY_DURATION = "You have to specify duration";
    public static final String YOU_HAVE_TO_SPECIFY_PRICE = "You have to specify price";
    @Autowired
    private ServiceReopository serviceReopository;

    public List<ServiceResponse> findAll() {
        List<Service> services = (List<Service>) serviceReopository.findAll();
        List<ServiceResponse> serviceResponseList = new ArrayList<>();
        for (Service addService : services) {
            serviceResponseList.add(buildResponse(addService));
        }
        return serviceResponseList;
    }

    private ServiceResponse buildResponse(Service service) {
        ServiceResponse response = new ServiceResponse();
        response.setServiceDuration(service.getServiceDuration());
        response.setServiceName(service.getServiceName());
        response.setServicePrice(service.getServicePrice());
        response.setId(service.getId());
        return response;
    }

    public List<ServiceResponse> buildListResponse(List<Service> serviceList) {
        List<ServiceResponse> responseList = new ArrayList<>();
        for (Service service : serviceList) {
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
        validateServiceRequest(serviceRequest);
        Service service = new Service();
        service.setServicePrice(serviceRequest.getServicePrice());
        service.setServiceDuration(serviceRequest.getServiceDuration());
        service.setServiceName(serviceRequest.getServiceName());
        Service saveService = serviceReopository.save(service);
        return buildResponse(saveService);
    }

    private void validateServiceRequest(ServiceRequest serviceRequest) {
        if (serviceRequest == null){
            throw new IllegalArgumentException(REQUEST_IS_NULL);
        }
        if (Strings.isNullOrEmpty(serviceRequest.getServiceName())){
            throw new IllegalArgumentException(YOU_HAVE_TO_SPECIFY_NAME);
        }
        if (serviceRequest.getServiceDuration() == 0){
            throw new IllegalArgumentException(YOU_HAVE_TO_SPECIFY_DURATION);
        }
        if (serviceRequest.getServicePrice() == 0){
            throw new IllegalArgumentException(YOU_HAVE_TO_SPECIFY_PRICE);
        }
    }

    public ServiceResponse findById(long id) throws Exception {
        return buildResponse(findService(id));
    }

    public void deleteById(long id) {
        serviceReopository.deleteById(id);
    }

    public ServiceResponse updateService(String serviceName, ServiceRequest newServiceRequest) {
        Service service = serviceReopository.findByServiceName(serviceName);
        service.setServiceDuration(newServiceRequest.getServiceDuration());
        service.setServicePrice(newServiceRequest.getServicePrice());
        Service saveService = serviceReopository.save(service);
        return buildResponse(saveService);
    }

    private Service findService(Long id) throws Exception {
        Service service = new OptionalEntityUtils<Service>().getEntityOrException(serviceReopository.findById(id),
                new ServiceNotFoundException(NO_SERVICE_WAS_FOUND));
        return service;
    }
}
