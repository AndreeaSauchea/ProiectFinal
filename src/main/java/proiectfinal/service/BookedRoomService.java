package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.*;
import proiectfinal.exception.*;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Client;
import proiectfinal.model.Room;
import proiectfinal.model.Service;
import proiectfinal.repository.BookedRoomRepository;
import proiectfinal.repository.ClientRepository;
import proiectfinal.repository.RoomRepository;
import proiectfinal.repository.ServiceReopository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class BookedRoomService {
    public static final String REQUEST_CAN_NOT_BE_NULL = "Request can not be NULL";
    public static final String CLIENT_CNP_IS_NULL = "Client ID is NULL";
    public static final String YOU_DON_T_HAVE_A_ROOM = "You don`t have a room";
    public static final String THERE_IS_NO_CHECK_IN_DATE = "There is no check in date.";
    public static final String THERE_IS_NO_CHECK_OUT_DATE = "There is no check out date.";
    public static final String CLIENT_WAS_NOT_FOUND = "Client was not found";
    public static final String ROOM_WAS_NOT_FOUND = "Room was not found";
    public static final String YOUR_BOOKED_ROOM_WAS_NOT_SAVED_IN_THE_DATABASE = "Your booked room was not saved in the database";
    public static final String BOOKEDROOM_NOT_FOUND = "Bookedroom not found";
    public static final String NO_BOOKED_ROOMS_FOUND_FOR_THIS_DATES = "No booked rooms found for this dates";
    public static final String CLIENT_HAS_NO_FIRST_NAME = "Client has no first name";
    public static final String CLIENT_HAS_NO_LAST_NAME = "Client has no last name";
    public static final String ROOM_HAS_NO_NUMBER = "Room has no number";
    public static final String ROOM_HAS_NO_ID = "Room has no id";
    public static final String CHECK_IN_CAN_NOT_BE_AFTER_CHECK_OUT = "Check in can not be after check out";
    public static final String NO_ROOM_IS_BOOKED_IN_THIS_HOTEL = "No room is booked in this hotel";
    public static final String ID_CAN_NOT_BE_NULL = "Id can not be null";
    public static final String THIS_ROOM_IS_NOT_BOOKED = "This room is not booked";
    public static final String CLIENT_NOT_FOUND = "Client not found";
    public static final String NO_FIRST_NAME_FOUND = "No first name found";
    public static final String NO_LAST_NAME_WAS_FOUND = "No last name was found";
    public static final String THERE_IS_NO_DURATION_FOR_THIS_STAY = "There is no duration for this stay";
    public static final String THERE_ARE_NO_SERVICES_ON_THIS_LIST = "There are no services on this list";
    public static final String THERE_IS_NO_ROOM_NUMBER_IN_THE_REQUEST = "There is no room number in the request";
    public static final String NO_BOOKEDROOM_FOR_THIS_ID = "No bookedroom for this id";
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceReopository serviceReopository;
    @Autowired
    private ClientRepository clientRepository;

    public List<BookedRoomResponse> findAll() {
        List<BookedRoom> bookedRoomList = (List<BookedRoom>) bookedRoomRepository.findAll();
        List<BookedRoomResponse> responseList = new ArrayList<>();
        for (BookedRoom addBookedRoom : bookedRoomList){
          responseList.add(buildResponse(addBookedRoom));
        }
        return  responseList;
    }

    public BookedRoomResponse save(BookedRoomRequest request) throws ClientNotFoundException, RoomNotFoundException, BookedRoomNotSavedException {
        validateRequest(request);
        BookedRoom bookedRoom = new BookedRoom();
        Client client = clientRepository.findByCnp(request.getCnp());
        if (client == null){
            throw new ClientNotFoundException(CLIENT_WAS_NOT_FOUND);
        } else {
            bookedRoom.setClient(client);
        }
        Room room = roomRepository.findByRoomNumber(request.getRoom());
        if (room == null){
            throw new RoomNotFoundException(ROOM_WAS_NOT_FOUND);
        } else {
            bookedRoom.setRoom(roomRepository.findByRoomNumber(request.getRoom()));
        }
        bookedRoom.setCheckIn(request.getCheckIn());
        bookedRoom.setCheckOut(request.getCheckOut());
        BookedRoom saveBookedRoom = bookedRoomRepository.save(bookedRoom);
        if (saveBookedRoom == null){
            throw new BookedRoomNotSavedException(YOUR_BOOKED_ROOM_WAS_NOT_SAVED_IN_THE_DATABASE);

        } else {
            return buildResponse(saveBookedRoom);
        }

    }

    private void validateBookedRoom(BookedRoom bookedRoom) throws ClientNotFoundException, RoomNotFoundException, CheckInNotFoundException, CheckOutNotFoundException, NoFirstNameException, NoLastNameException, NoRoomNumberException, NoRoomIdException {
        if (bookedRoom.getCheckIn() == null){
            throw new CheckInNotFoundException(THERE_IS_NO_CHECK_IN_DATE);
        }
        if (bookedRoom.getCheckOut() == null){
            throw  new CheckOutNotFoundException(THERE_IS_NO_CHECK_OUT_DATE);
        }
        Client client = bookedRoom.getClient();
        if (client == null){
            throw new ClientNotFoundException(CLIENT_WAS_NOT_FOUND);
        } else {
            if (client.getFirstname() == null){
                throw new NoFirstNameException(CLIENT_HAS_NO_FIRST_NAME);
            } else if (client.getLastname() == null) {
                throw new NoLastNameException(CLIENT_HAS_NO_LAST_NAME);
            }
        }
        Room room = bookedRoom.getRoom();
        if (room == null){
            throw new RoomNotFoundException(ROOM_WAS_NOT_FOUND);
        } else {
            if(room.getRoomNumber() == 0){
                throw new NoRoomNumberException(ROOM_HAS_NO_NUMBER);
            } else if (room.getId() == null) {
                throw new NoRoomIdException(ROOM_HAS_NO_ID);
            }
        }


    }

    private BookedRoomResponse buildResponse(BookedRoom saveBookedRoom) {
        BookedRoomResponse response = new BookedRoomResponse();
        response.setTotalPrice(saveBookedRoom.getTotalPrice());
        response.setCheckIn(saveBookedRoom.getCheckIn());
        response.setCheckOut(saveBookedRoom.getCheckOut());
        return response;
    }

    private void validateRequest(BookedRoomRequest request) {
        if (request == null){
            throw new IllegalArgumentException(REQUEST_CAN_NOT_BE_NULL);
        }
        if (request.getCnp() == null) {
            throw new IllegalArgumentException(CLIENT_CNP_IS_NULL);
        }
        if (request.getRoom() == 0){
            throw new IllegalArgumentException(YOU_DON_T_HAVE_A_ROOM);
        }
        if (request.getCheckIn() == null){
            throw new IllegalArgumentException(THERE_IS_NO_CHECK_IN_DATE);
        }
        if (request.getCheckOut() == null){
            throw  new IllegalArgumentException(THERE_IS_NO_CHECK_OUT_DATE);
        }
        if (request.getCheckIn().after(request.getCheckOut())){
            throw new IllegalArgumentException(CHECK_IN_CAN_NOT_BE_AFTER_CHECK_OUT);
        }
    }

    public BookedRoomResponse findById(long id) throws BookedRoomNotFoundException {
        return buildResponse(findBookedRoom(id));
    }

    public void deleteById(long id) {
        bookedRoomRepository.deleteById(id);
    }

    public BookedRoomResponse updateRoom(Long id, BookedRoomRequest newRequest) throws BookedRoomNotFoundException, RoomNotFoundException, NoRoomNumberException {
        if (id == null) {
            throw new IllegalArgumentException(ID_CAN_NOT_BE_NULL);
        }
        BookedRoom bookedRoom = findBookedRoom(id);
        if (bookedRoom == null){
            throw new BookedRoomNotFoundException(BOOKEDROOM_NOT_FOUND);
        }
        int roomNumber = newRequest.getRoom();
        if (newRequest == null){
            throw new IllegalArgumentException(REQUEST_CAN_NOT_BE_NULL);
        } else {
            if (roomNumber == 0) {
                throw new NoRoomNumberException(THERE_IS_NO_ROOM_NUMBER_IN_THE_REQUEST);
            } else {
                bookedRoom.setRoom(roomRepository.findByRoomNumber(roomNumber));
            }
            bookedRoom.setCheckOut(newRequest.getCheckOut());
            bookedRoom.setCheckIn(newRequest.getCheckIn());
        }
        BookedRoom saveBookedRoom = bookedRoomRepository.save(bookedRoom);
        return buildResponse(saveBookedRoom);
    }

    private BookedRoom findBookedRoom(Long bookedRoomId) throws BookedRoomNotFoundException {
        Optional<BookedRoom> optionalBookedRoom = bookedRoomRepository.findById(bookedRoomId);
        if (optionalBookedRoom.isPresent()){
            return optionalBookedRoom.get();
        } else {
            throw new BookedRoomNotFoundException(BOOKEDROOM_NOT_FOUND);
        }
    }

    public List<ClientHistoryResponse> findHistoryClients() throws RoomNotFoundException, ClientNotFoundException, CheckOutNotFoundException, CheckInNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        List<ClientHistoryResponse> response = new ArrayList<>();
        Date now = new Date();
        List<BookedRoom> historyBookedRoom = bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(now,now);
        if (historyBookedRoom == null){
            throw new HotelIsNotBookedException(NO_ROOM_IS_BOOKED_IN_THIS_HOTEL);
        } else {
            for (BookedRoom bookedRoom : historyBookedRoom) {
                validateBookedRoom(bookedRoom);
                ClientHistoryResponse clientHistoryResponse = new ClientHistoryResponse();
                clientHistoryResponse.setCheckIn(bookedRoom.getCheckIn());
                clientHistoryResponse.setCheckOut(bookedRoom.getCheckOut());
                clientHistoryResponse.setFirstName(bookedRoom.getClient().getFirstname());
                clientHistoryResponse.setLastName(bookedRoom.getClient().getLastname());
                clientHistoryResponse.setRoom(bookedRoom.getRoom().getRoomNumber());
                clientHistoryResponse.setRoomId(bookedRoom.getRoom().getId());
                response.add(clientHistoryResponse);
            }
        }

        return response;

    }

    public BookedRoomResponse findBookedRoomByRoom(Long roomId) throws BookedRoomNotFoundException, RoomNotFoundException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        if (roomId == null){
            throw new IllegalArgumentException(ID_CAN_NOT_BE_NULL);
        } else {
            BookedRoomResponse response = new BookedRoomResponse();
            Optional<Room> roomOptional = roomRepository.findById(roomId);
            if (roomOptional == null){
                throw new RoomNotFoundException(ROOM_WAS_NOT_FOUND);
            }
            Room room = roomOptional.get();
            Date now = new Date();
            BookedRoom bookedRoom = bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(room, now);
            if (bookedRoom != null) {
                Client client = bookedRoom.getClient();
                if (client == null){
                    throw new ClientNotFoundException(CLIENT_NOT_FOUND);
                } else {
                    if (client.getFirstname() == null){
                        throw new NoFirstNameException(NO_FIRST_NAME_FOUND);
                    } else if (client.getLastname() == null){
                        throw new NoLastNameException(NO_LAST_NAME_WAS_FOUND);
                    } else {
                        response.setClient(client.getFirstname() + " " + client.getLastname());
                    }
                }
                response.setDuration(bookedRoom.getDuration());
                List<Service> services = bookedRoom.getServices();
                if (services == null) {
                    throw new NoServicesOnThisListException(THERE_ARE_NO_SERVICES_ON_THIS_LIST);
                } else {
                    response.setServiceList(serviceService.buildListResponse(services));
                }
                response.setTotalPrice(bookedRoom.getTotalPrice() + bookedRoom.getTotalServicePrices());
                return response;
            } else {
                throw new BookedRoomNotFoundException(THIS_ROOM_IS_NOT_BOOKED);
            }
        }

    }

    public BookedRoomResponse findBookedRoomByClient(Long clientId) throws BookedRoomNotFoundException {
        BookedRoomResponse response = new BookedRoomResponse();
        Client client = clientRepository.findById(clientId).get();
        Date now = new Date();
        BookedRoom bookedRoom = bookedRoomRepository.findFirstByClientAndCheckOutAfterOrderByCheckOutDesc(client, now);
        if (bookedRoom != null){
            response.setRoom(bookedRoom.getRoom().getRoomNumber());
        }
        return response;
    }

    public void addBookedRoomActivity(Long roomId,Long activityId){
        Room room = roomRepository.findById(roomId).get();
        BookedRoom bookedRoom = bookedRoomRepository.findByRoom(room);
        Service service = serviceReopository.findById(activityId).get();
        bookedRoom.addService(service);
        bookedRoomRepository.save(bookedRoom);
    }

    public void removeBookedRoomActivity(Long roomId, Long activityId) {
        Room room = roomRepository.findById(roomId).get();
        BookedRoom bookedRoom = bookedRoomRepository.findByRoom(room);
        Service service = serviceReopository.findById(activityId).get();
        bookedRoom.removeService(service);
        bookedRoomRepository.save(bookedRoom);
    }
}
