package proiectfinal;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import proiectfinal.controller.dto.ServiceRequest;
import proiectfinal.controller.dto.ServiceResponse;
import proiectfinal.model.Service;
import proiectfinal.repository.ServiceReopository;
import proiectfinal.service.ServiceService;

import static org.mockito.ArgumentMatchers.any;
import static proiectfinal.service.RoomService.REQUEST_IS_NULL;
import static proiectfinal.service.ServiceService.YOU_HAVE_TO_SPECIFY_DURATION;
import static proiectfinal.service.ServiceService.YOU_HAVE_TO_SPECIFY_NAME;
import static proiectfinal.service.ServiceService.YOU_HAVE_TO_SPECIFY_PRICE;

public class ServiceServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private ServiceService serviceService;

    @Mock
    private ServiceReopository serviceReopository;

    @Test
    public void validateSaveRequestIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(REQUEST_IS_NULL);
        serviceService.save(null);
    }

    @Test
    public void validateRequestNameIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_HAVE_TO_SPECIFY_NAME);
        ServiceRequest request = new ServiceRequest();
        serviceService.save(request);
    }

    @Test
    public void validateRequestDurationIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_HAVE_TO_SPECIFY_DURATION);
        ServiceRequest request = new ServiceRequest();
        request.setServiceName("Dinner");
        serviceService.save(request);
    }

    @Test
    public void validateRequestPriceIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_HAVE_TO_SPECIFY_PRICE);
        ServiceRequest request = new ServiceRequest();
        request.setServiceName("Dinner");
        request.setServiceDuration(30);
        serviceService.save(request);
    }

    @Test
    public void validateSave(){
        ServiceRequest request = buildMockServiceRequest();
        Service service = buildMockService();
        Mockito.when(serviceReopository.save(any())).thenReturn(service);
        ServiceResponse response = serviceService.save(request);
        Assert.assertEquals(request.getServiceDuration(),response.getServiceDuration());
        Assert.assertEquals(request.getServicePrice(), response.getServicePrice(), 0);
        Assert.assertEquals(request.getServiceName(), response.getServiceName());
    }

    private Service buildMockService() {
        Service service = new Service();
        service.setServiceName("Dinner");
        service.setServiceDuration(30);
        service.setServicePrice(30);
        return service;
    }

    private ServiceRequest buildMockServiceRequest() {
        ServiceRequest request = new ServiceRequest();
        request.setServiceName("Dinner");
        request.setServiceDuration(30);
        request.setServicePrice(30);
        return request;
    }


}
