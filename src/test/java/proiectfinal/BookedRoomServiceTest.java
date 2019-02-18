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
import proiectfinal.controller.dto.BookedRoomRequest;
import proiectfinal.controller.dto.BookedRoomResponse;
import proiectfinal.controller.dto.ClientHistoryResponse;
import proiectfinal.exception.*;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Client;
import proiectfinal.model.Room;
import proiectfinal.model.Service;
import proiectfinal.repository.BookedRoomRepository;
import proiectfinal.repository.ClientRepository;
import proiectfinal.repository.RoomRepository;
import proiectfinal.service.BookedRoomService;
import proiectfinal.service.ServiceService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static proiectfinal.service.BookedRoomService.*;

public class BookedRoomServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private BookedRoomService bookedRoomService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookedRoomRepository bookedRoomRepository;

    @Mock
    private ServiceService serviceService;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);



    @Test
    public void validateSaveBookedRoomRequestIsNull() throws ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(REQUEST_CAN_NOT_BE_NULL);
        bookedRoomService.save(null);
    }

    @Test
    public void validateRequestCnpIsNull() throws ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(CLIENT_CNP_IS_NULL);
        bookedRoomService.save(new BookedRoomRequest());
    }

    @Test
    public void validateRequestRoomIsNull() throws ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_DON_T_HAVE_A_ROOM);
        BookedRoomRequest request = new BookedRoomRequest();
        request.setCnp("2991025417689");
        bookedRoomService.save(request);
    }

    @Test
    public void validateRequestCheckInIsNull() throws ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THERE_IS_NO_CHECK_IN_DATE);
        BookedRoomRequest request = new BookedRoomRequest();
        request.setCnp("2991025417689");
        request.setRoom(1);
        bookedRoomService.save(request);
    }

    @Test
    public void validateRequestCheckOutIsNull() throws ClientNotFoundException, RoomNotFoundException, ParseException, BookedRoomNotSavedException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THERE_IS_NO_CHECK_OUT_DATE);
        BookedRoomRequest request = new BookedRoomRequest();
        request.setCnp("2991025417689");
        request.setRoom(1);
        request.setCheckIn(format.parse("2019-02-11"));
        bookedRoomService.save(request);
    }

    @Test
    public void validateRequestCheckInIsAfterCheckOut() throws ClientNotFoundException, RoomNotFoundException, ParseException, BookedRoomNotSavedException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(CHECK_IN_CAN_NOT_BE_AFTER_CHECK_OUT);
        BookedRoomRequest request = new BookedRoomRequest();
        request.setCnp("2991025417689");
        request.setRoom(1);
        request.setCheckIn(format.parse("2019-02-11"));
        request.setCheckOut(format.parse("2019-01-11"));
        bookedRoomService.save(request);
    }

    @Test
    public void validateBookedroomClientNotFound() throws ParseException, ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(ClientNotFoundException.class);
        expectedException.expectMessage(CLIENT_WAS_NOT_FOUND);
        BookedRoomRequest request = buildMockBookedRoomRequest();
        Mockito.when(clientRepository.findByCnp("2991025417689")).thenReturn(null);
        bookedRoomService.save(request);
    }

    @Test
    public void validateBookedroomRoomNotFound() throws ParseException, ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(RoomNotFoundException.class);
        expectedException.expectMessage(ROOM_WAS_NOT_FOUND);
        BookedRoomRequest request = buildMockBookedRoomRequest();
        Client client = buildMockClient();
        Mockito.when(clientRepository.findByCnp("2991025417689")).thenReturn(client);
        Mockito.when(roomRepository.findByRoomNumber(1)).thenReturn(null);
        bookedRoomService.save(request);
    }

    @Test
    public void validateSaveBookedRoomIfSaveFails() throws ParseException, ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        expectedException.expect(BookedRoomNotSavedException.class);
        expectedException.expectMessage(YOUR_BOOKED_ROOM_WAS_NOT_SAVED_IN_THE_DATABASE);
        BookedRoomRequest request = buildMockBookedRoomRequest();
        Client client = buildMockClient();
        Mockito.when(clientRepository.findByCnp("2991025417689")).thenReturn(client);
        Room room = buildMockRoom();
        Mockito.when(roomRepository.findByRoomNumber(1)).thenReturn(room);
        Mockito.when(bookedRoomRepository.save(any(BookedRoom.class))).thenReturn(null);
        bookedRoomService.save(request);
    }


   @Test
    public void validateBuildResponse() throws ParseException, BookedRoomNotSavedException, ClientNotFoundException, RoomNotFoundException {
        BookedRoomRequest request = buildMockBookedRoomRequest();
        Client client = buildMockClient();
        Mockito.when(clientRepository.findByCnp("2991025417689")).thenReturn(client);
        Room room = buildMockRoom();
        Mockito.when(roomRepository.findByRoomNumber(1)).thenReturn(room);
        BookedRoom saveBookedRoom = buildMockBookedRoom();
        Mockito.when(bookedRoomRepository.save(any(BookedRoom.class))).thenReturn(saveBookedRoom);
        BookedRoomResponse response = bookedRoomService.save(request);
        Assert.assertEquals(request.getCheckIn(), response.getCheckIn() );
        Assert.assertEquals(request.getCheckOut(), response.getCheckOut());
    }

    @Test
    public void validateFindHistoryClientsBookedRoomIsNull() throws ClientNotFoundException, RoomNotFoundException, CheckOutNotFoundException, CheckInNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(HotelIsNotBookedException.class);
        expectedException.expectMessage(NO_ROOM_IS_BOOKED_IN_THIS_HOTEL);
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(null);
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementCheckInIsNull() throws RoomNotFoundException, ClientNotFoundException, CheckOutNotFoundException, CheckInNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(CheckInNotFoundException.class);
        expectedException.expectMessage(THERE_IS_NO_CHECK_IN_DATE);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementCheckOutIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(CheckOutNotFoundException.class);
        expectedException.expectMessage(THERE_IS_NO_CHECK_OUT_DATE);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementClientIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(ClientNotFoundException.class);
        expectedException.expectMessage(CLIENT_WAS_NOT_FOUND);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementClientsFirstNameIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(NoFirstNameException.class);
        expectedException.expectMessage(CLIENT_HAS_NO_FIRST_NAME);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        Client client = new Client();
        bookedRoom.setClient(client);
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementClientsLastNameIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(NoLastNameException.class);
        expectedException.expectMessage(CLIENT_HAS_NO_LAST_NAME);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        Client client = new Client();
        client.setFirstname("Ion");
        bookedRoom.setClient(client);
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementRoomIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(RoomNotFoundException.class);
        expectedException.expectMessage(ROOM_WAS_NOT_FOUND);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        Client client = buildMockClient();
        bookedRoom.setClient(client);
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementRoomsNumberIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(NoRoomNumberException.class);
        expectedException.expectMessage(ROOM_HAS_NO_NUMBER);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        Client client = buildMockClient();
        bookedRoom.setClient(client);
        Room room = new Room();
        bookedRoom.setRoom(room);
        bookedRoomService.findHistoryClients();
    }

    @Test
    public void validateFindHistoryClientsElementRoomsIdIsNull() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        expectedException.expect(NoRoomIdException.class);
        expectedException.expectMessage(ROOM_HAS_NO_ID);
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        Client client = buildMockClient();
        bookedRoom.setClient(client);
        Room room = new Room();
        room.setRoomNumber(1);
        bookedRoom.setRoom(room);
        bookedRoomService.findHistoryClients();
    }

   @Test
    public void validateFindHistoryClients() throws ParseException, CheckOutNotFoundException, ClientNotFoundException, CheckInNotFoundException, RoomNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        List<BookedRoom> bookedRoomList = buildMockBookedRoomList();
        Mockito.when(bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(Mockito.any(),Mockito.any())).thenReturn(bookedRoomList);
        BookedRoom bookedRoom = bookedRoomList.get(0);
        bookedRoom.setCheckIn(format.parse("2019-02-09"));
        bookedRoom.setCheckOut(format.parse("2019-02-12"));
        Client client = buildMockClient();
        bookedRoom.setClient(client);
        Room room = buildMockRoom();
        bookedRoom.setRoom(room);
        List<ClientHistoryResponse> bookedRoomResponseList = buidMockResponsList(bookedRoomList);
        bookedRoomService.findHistoryClients();
        Assert.assertEquals(bookedRoomList.size(),bookedRoomResponseList.size());
    }

    @Test
    public void validateFindBookedRoomByRoomRoomIdIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(ID_CAN_NOT_BE_NULL);
        Long roomId = null;
        bookedRoomService.findBookedRoomByRoom(roomId);

    }

    @Test
    public void validateFindBookedRoomByRoomRoomIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(RoomNotFoundException.class);
        expectedException.expectMessage(ROOM_WAS_NOT_FOUND);
        Long roomId = 1L;
        Mockito.when(roomRepository.findById(roomId)).thenReturn(null);
        bookedRoomService.findBookedRoomByRoom(roomId);
    }

   @Test
    public void validateFindBookedRoomByRoomBookedRoomIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(BookedRoomNotFoundException.class);
        expectedException.expectMessage(THIS_ROOM_IS_NOT_BOOKED);
        Long roomId = 1L;
        Room room =buildMockRoom();
        Optional<Room> roomOptional = Optional.of(room);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(roomOptional);
        Mockito.when(bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Mockito.any(), Mockito.any())).thenReturn(null);
        bookedRoomService.findBookedRoomByRoom(roomId);
    }

    @Test
    public void validateFindBookedRoomByRoomClientIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ParseException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(ClientNotFoundException.class);
        expectedException.expectMessage(CLIENT_NOT_FOUND);
        Long roomId = 1L;
        Room room = buildMockRoom();
        Optional<Room> roomOptional = Optional.of(room);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(roomOptional);
        BookedRoom bookedRoom = buildMockBookedRoom();
        Mockito.when(bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Mockito.any(), Mockito.any())).thenReturn(bookedRoom);
        bookedRoom.setClient(null);
        bookedRoomService.findBookedRoomByRoom(roomId);
    }

    @Test
    public void validateFindBookedRoomByRoomFirstNameIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ParseException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(NoFirstNameException.class);
        expectedException.expectMessage(NO_FIRST_NAME_FOUND);
        Long roomId = 1L;
        Room room = buildMockRoom();
        Optional<Room> roomOptional = Optional.of(room);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(roomOptional);
        BookedRoom bookedRoom = buildMockBookedRoom();
        Mockito.when(bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Mockito.any(), Mockito.any())).thenReturn(bookedRoom);
        Client client = buildMockClient();
        client.setFirstname(null);
        bookedRoom.setClient(client);
        bookedRoomService.findBookedRoomByRoom(roomId);
    }

    @Test
    public void validateFindBookedRoomByRoomLastNameIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ParseException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(NoLastNameException.class);
        expectedException.expectMessage(NO_LAST_NAME_WAS_FOUND);
        Long roomId = 1L;
        Room room = buildMockRoom();
        Optional<Room> roomOptional = Optional.of(room);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(roomOptional);
        BookedRoom bookedRoom = buildMockBookedRoom();
        Mockito.when(bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Mockito.any(), Mockito.any())).thenReturn(bookedRoom);
        Client client = buildMockClient();
        client.setLastname(null);
        bookedRoom.setClient(client);
        bookedRoomService.findBookedRoomByRoom(roomId);
    }

   @Test
    public void validateFindBookedRoomByRoomListOfServicesIsNull() throws BookedRoomNotFoundException, RoomNotFoundException, ParseException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        expectedException.expect(NoServicesOnThisListException.class);
        expectedException.expectMessage(THERE_ARE_NO_SERVICES_ON_THIS_LIST);
        Long roomId = 1L;
        Room room = buildMockRoom();
        Optional<Room> roomOptional = Optional.of(room);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(roomOptional);
        BookedRoom bookedRoom = buildMockBookedRoom();
        bookedRoom.setServices(null);
        Mockito.when(bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Mockito.any(), Mockito.any())).thenReturn(bookedRoom);
        bookedRoomService.findBookedRoomByRoom(roomId);
    }

    @Test
    public void validateFindBookedRoomByRoom() throws BookedRoomNotFoundException, RoomNotFoundException, ParseException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        Long roomId = 1L;
        Room room = buildMockRoom();
        Optional<Room> roomOptional = Optional.of(room);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(roomOptional);
        BookedRoom bookedRoom = buildMockBookedRoom();
        Mockito.when(bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Mockito.any(), Mockito.any())).thenReturn(bookedRoom);
        bookedRoomService.findBookedRoomByRoom(roomId);
        Assert.assertEquals(roomId,bookedRoom.getRoom().getId());
    }

    @Test
    public void validateUpdateRoomIdIsNull() throws ParseException, BookedRoomNotFoundException, RoomNotFoundException, NoRoomNumberException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(ID_CAN_NOT_BE_NULL);
        BookedRoomRequest newRequest = buildMockBookedRoomRequest();
        bookedRoomService.updateRoom(null,newRequest);
    }

    @Test
    public void validateUpdateRoomBookedRoomIsNull() throws ParseException, BookedRoomNotFoundException, RoomNotFoundException, NoRoomNumberException {
        expectedException.expect(BookedRoomNotFoundException.class);
        expectedException.expectMessage(BOOKEDROOM_NOT_FOUND);
        Long id = 1L;
        BookedRoomRequest newRequest = buildMockBookedRoomRequest();
        bookedRoomService.updateRoom(id,newRequest);
    }

    @Test
    public void validateUpdateRoomRequestIsNull() throws ParseException, BookedRoomNotFoundException, RoomNotFoundException, NoRoomNumberException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(REQUEST_CAN_NOT_BE_NULL);
        Long id = 1L;
        BookedRoom bookedRoom = buildMockBookedRoom();
       // Mockito.when(bookedRoomRepository.findById(id)).thenReturn(bookedRoom);
        bookedRoomService.updateRoom(id,null);
    }

    private List<ClientHistoryResponse> buidMockResponsList(List<BookedRoom> bookedRoomList) {
        List<ClientHistoryResponse> responseList = new ArrayList<>();
        for (BookedRoom bookedRoom : bookedRoomList) {
            ClientHistoryResponse clientHistoryResponse = new ClientHistoryResponse();
            clientHistoryResponse.setCheckIn(bookedRoom.getCheckIn());
            clientHistoryResponse.setCheckOut(bookedRoom.getCheckOut());
            clientHistoryResponse.setFirstName(bookedRoom.getClient().getFirstname());
            clientHistoryResponse.setLastName(bookedRoom.getClient().getLastname());
            clientHistoryResponse.setRoom(bookedRoom.getRoom().getRoomNumber());
            clientHistoryResponse.setRoomId(bookedRoom.getRoom().getId());
            responseList.add(clientHistoryResponse);
        }
        return responseList;
    }

    private List<BookedRoom> buildMockBookedRoomList(){
        List<BookedRoom> bookedRoomList = new ArrayList<>();
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoomList.add(bookedRoom);
        return bookedRoomList;
    }



    private BookedRoom buildMockBookedRoom() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        Client client = buildMockClient();
        Room room = buildMockRoom();
        bookedRoom.setClient(client);
        bookedRoom.setRoom(room);
        bookedRoom.setCheckIn(format.parse("2019-02-11"));
        bookedRoom.setCheckOut(format.parse("2019-03-12"));
        List<Service> services = new ArrayList<>();
        Service service = new Service();
        service.setServiceName("Lunch");
        service.setServiceDuration(45);
        service.setServicePrice(50);
        service.setId(1L);
        services.add(service);
        bookedRoom.setServices(services);
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
        client.setNumberID("454545");
        client.setBirthday(format.parse("1999-06-12"));
        client.setCnp("2991025417689");
        client.setSeriesID("BV");
        client.setTypeID("BI");
        client.setStreet("bla bla");
        client.setStreetNumber(4);
        client.setFirstname("Ion");
        client.setLastname("Pop");
        client.setId(1L);
        return client;
    }

    private BookedRoomRequest buildMockBookedRoomRequest() throws ParseException {
        BookedRoomRequest request = new BookedRoomRequest();
        request.setCnp("2991025417689");
        request.setRoom(1);
        request.setCheckIn(format.parse("2019-02-11"));
        request.setCheckOut(format.parse("2019-03-12"));
        return request;
    }

}
