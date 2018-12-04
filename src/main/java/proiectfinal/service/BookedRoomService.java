package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.BookedRoomRequest;
import proiectfinal.controller.dto.BookedRoomResponse;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.model.BookedRoom;
import proiectfinal.repository.BookedRoomRepository;

import java.util.ArrayList;
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

    public List<BookedRoomResponse> findAll() {
        List<BookedRoom> bookedRoomList = (List<BookedRoom>) bookedRoomRepository.findAll();
        List<BookedRoomResponse> responseList = new ArrayList<>();
        for (BookedRoom addBookedRoom : bookedRoomList){
          responseList.add(buildResponse(addBookedRoom));
        }
        return  responseList;
    }

    public BookedRoomResponse save(BookedRoomRequest request) throws ClientNotFoundException, RoomNotFoundException{
        validateRequest(request);
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setClient(clientService.findClient(request.getClientId()));
        bookedRoom.setRoom(roomService.findRoom(request.getRoomId()));
        bookedRoom.setCheckIn(request.getCheckIn());
        bookedRoom.setCheckOut(request.getCheckOut());
        BookedRoom saveBookedRoom = bookedRoomRepository.save(bookedRoom);
        return buildResponse(saveBookedRoom);

    }

    private BookedRoomResponse buildResponse(BookedRoom saveBookedRoom) {
        BookedRoomResponse response = new BookedRoomResponse();
        response.setTotalPrice(saveBookedRoom.getTotalPrice());
        response.setTotalServicePrice(saveBookedRoom.getTotalServicePrices());
        response.setCheckIn(saveBookedRoom.getCheckIn());
        response.setCheckOut(saveBookedRoom.getCheckOut());
        return response;
    }

    private void validateRequest(BookedRoomRequest request) {
        if (request.getClientId() == null) {
            throw new IllegalArgumentException("Client ID is NULL");
        }
        if (request.getRoomId() == null){
            throw new IllegalArgumentException("Room ID is NULL");
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
        bookedRoom.setRoom(roomService.findRoom(newRequest.getRoomId()));
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
}
