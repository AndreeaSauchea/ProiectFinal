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
import proiectfinal.controller.dto.ClientRequest;
import proiectfinal.controller.dto.ClientResponse;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Client;
import proiectfinal.model.Room;
import proiectfinal.repository.BookedRoomRepository;
import proiectfinal.repository.ClientRepository;
import proiectfinal.service.ClientService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static proiectfinal.service.ClientService.*;

public class ClientServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BookedRoomRepository bookedRoomRepository;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Test
    public void validateSaveRequestIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(REQUEST_CAN_NOT_BE_NULL);
        clientService.save(null);
    }

    @Test
    public void validateRequestClientNameIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THIS_CLIENT_HAS_NO_NAME);
        clientService.save(new ClientRequest());
    }

    @Test
    public void validateRequestClientForNameIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THIS_CLIENT_HAS_NO_FORENAME);
        ClientRequest request = new ClientRequest();
        request.setName("Pop");
        clientService.save(request);
    }

    @Test
    public void validateRequestClientCnpIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(IT_IS_MANDATORY_TO_HAVE_CNP);
        ClientRequest request = new ClientRequest();
        request.setName("Pop");
        request.setForename("Ion");
        clientService.save(request);
    }

    @Test
    public void validateSave() throws ParseException {
        ClientRequest request = buildMockClientRequest();
        Client client = buildMockClient();
        Mockito.when(clientRepository.save(any())).thenReturn(client);
        ClientResponse response = clientService.save(request);
        Assert.assertEquals(request.getForename(),response.getForename());
        Assert.assertEquals(request.getName(),response.getName());
    }

    @Test
    public void validateFindByCnpCnpIsNull() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(IT_IS_MANDATORY_TO_HAVE_CNP);
        clientService.findByCnp(null);
    }

    @Test
    public void validateFindByCnp() throws Exception {
        Client client = buildMockClient();
        Mockito.when(clientRepository.findByCnp("1900312245687")).thenReturn(client);
        BookedRoom bookedRoom = buildMockBookedRoom();
        Mockito.when(bookedRoomRepository.findFirstByClientAndCheckOutAfterOrderByCheckOutDesc(any(), any())).thenReturn(bookedRoom);
        ClientResponse response = clientService.findByCnp("1900312245687");
        Assert.assertEquals(client.getFirstname(),response.getForename());
        Assert.assertEquals(client.getLastname(),response.getName());
        Assert.assertEquals(client.getBirthday(),response.getBirthday());
        Assert.assertEquals(client.getId(),response.getId());
        Assert.assertEquals(client.getNumberID(),response.getNumberID());
        Assert.assertEquals(client.getSeriesID(),response.getSeriesID());
        Assert.assertEquals(client.getTypeID(),response.getTypeID());
        Assert.assertEquals(client.getCnp(),response.getCnp());
    }

    @Test
    public void validateUpdateByCnpBothArgumentsAreNull() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BOTH_CNP_AND_CLIENT_REQEST_ARE_NULL);
        clientService.updateByCnp(null,null);
    }

    private BookedRoom buildMockBookedRoom() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        Client client = buildMockClient();
        Room room = buildMockRoom();
        bookedRoom.setClient(client);
        bookedRoom.setRoom(room);
        return bookedRoom;
    }

    private Room buildMockRoom() {
        Room room = new Room();
        room.setNumberPlaces(2);
        room.setNightlyPrice(100);
        room.setRoomNumber(1);
        room.setId(1L);

        return room;
    }

    private Client buildMockClient() throws ParseException {
        Client client = new Client();
        client.setFirstname("Ion");
        client.setLastname("Pop");
        client.setCnp("1900312245687");
        client.setBirthday(format.parse("1990-03-12"));
        client.setNumberID("456789");
        client.setSeriesID("BV");
        client.setTypeID("CI");
        client.setStreet("Zip");
        client.setStreetNumber(4);
        client.setId(1L);
        return client;
    }

    private ClientRequest buildMockClientRequest() throws ParseException {
        ClientRequest request = new ClientRequest();
        request.setName("Pop");
        request.setForename("Ion");
        request.setCnp("1900312245687");
        request.setBirthday(format.parse("1990-03-12"));
        request.setNumberID("456789");
        request.setSeriesID("BV");
        request.setTypeID("CI");
        request.setStreet("Zip");
        request.setStreetNumber(4);
        return request;
    }
}
