package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.*;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.exception.ServiceNotFoundException;
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
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RoomService roomService;
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

    public BookedRoomResponse save(BookedRoomRequest request) throws ClientNotFoundException, RoomNotFoundException, BookedRoomNotFoundException {
        validateRequest(request);
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setClient(clientRepository.findByCnp(request.getCnp()));
        bookedRoom.setRoom(roomRepository.findByRoomNumber(request.getRoom()));
        bookedRoom.setCheckIn(request.getCheckIn());
        bookedRoom.setCheckOut(request.getCheckOut());
        BookedRoom saveBookedRoom = bookedRoomRepository.save(bookedRoom);
        return buildResponse(saveBookedRoom);

    }

    private BookedRoomResponse buildResponse(BookedRoom saveBookedRoom) {
        BookedRoomResponse response = new BookedRoomResponse();
        response.setTotalPrice(saveBookedRoom.getTotalPrice());
        response.setCheckIn(saveBookedRoom.getCheckIn());
        response.setCheckOut(saveBookedRoom.getCheckOut());
        return response;
    }

    private void validateRequest(BookedRoomRequest request) {
        if (request.getCnp() == null) {
            throw new IllegalArgumentException("Client ID is NULL");
        }
        if (request.getRoom() == 0){
            throw new IllegalArgumentException("You don`t have a room");
        }
        if (request.getCheckIn() == null){
            throw new IllegalArgumentException("There is no check in date.");
        }
        if (request.getCheckOut() == null){
            throw  new IllegalArgumentException("There is no check out date.");
        }
    }

    public BookedRoomResponse findById(long id) throws BookedRoomNotFoundException {
        return buildResponse(findBookedRoom(id));
    }

    public void deleteById(long id) {
        bookedRoomRepository.deleteById(id);
    }

    public BookedRoomResponse updateRoom(Long id, BookedRoomRequest newRequest) throws BookedRoomNotFoundException, RoomNotFoundException {
        BookedRoom bookedRoom = findBookedRoom(id);
        bookedRoom.setRoom(roomRepository.findByRoomNumber(newRequest.getRoom()));
        bookedRoom.setCheckOut(newRequest.getCheckOut());
        bookedRoom.setCheckIn(newRequest.getCheckIn());
        BookedRoom saveBookedRoom = bookedRoomRepository.save(bookedRoom);
        return buildResponse(saveBookedRoom);
    }

    private BookedRoom findBookedRoom (Long bookedRoomId) throws BookedRoomNotFoundException {
        Optional<BookedRoom> optionalBookedRoom = bookedRoomRepository.findById(bookedRoomId);
        if (optionalBookedRoom.isPresent()){
            return optionalBookedRoom.get();
        } else {
            throw new BookedRoomNotFoundException("Bookedroom not found");
        }
    }

    public List<ClientHistoryResponse> findHistoryClients() {
        List<ClientHistoryResponse> response = new ArrayList<>();
        Date now = new Date();
        List<BookedRoom> historyBookedRoom = bookedRoomRepository.findByCheckInBeforeAndCheckOutAfter(now,now);
        for (BookedRoom bookedRoom : historyBookedRoom) {
            ClientHistoryResponse clientHistoryResponse = new ClientHistoryResponse();
            clientHistoryResponse.setCheckIn(bookedRoom.getCheckIn());
            clientHistoryResponse.setCheckOut(bookedRoom.getCheckOut());
            clientHistoryResponse.setFirstName(bookedRoom.getClient().getFirstname());
            clientHistoryResponse.setLastName(bookedRoom.getClient().getLastname());
            clientHistoryResponse.setRoom(bookedRoom.getRoom().getRoomNumber());
            response.add(clientHistoryResponse);
        }
        return response;
    }

    public BookedRoomResponse findBookedRoomByRoom(Long roomId) throws BookedRoomNotFoundException {
        BookedRoomResponse response = new BookedRoomResponse();
        Room room = roomRepository.findById(roomId).get();
        Date now = new Date();
        BookedRoom bookedRoom = bookedRoomRepository.findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(room,now);
        if (bookedRoom != null){
            response.setClient(bookedRoom.getClient().getFirstname() + " " + bookedRoom.getClient().getLastname());
            response.setDuration(bookedRoom.getDuration());
            response.setServiceList(serviceService.buildListResponse(bookedRoom.getServices()));
            response.setTotalPrice(bookedRoom.getTotalPrice() + bookedRoom.getTotalServicePrices());
            return response;
        } else {
            throw new BookedRoomNotFoundException("This room is not booked");
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
