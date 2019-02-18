package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.AvailabilityRequest;
import proiectfinal.controller.dto.AvailabilityResponse;
import proiectfinal.controller.dto.RoomRequest;
import proiectfinal.controller.dto.RoomResponse;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Room;
import proiectfinal.repository.BookedRoomRepository;
import proiectfinal.repository.ClientRepository;
import proiectfinal.repository.RoomRepository;

import java.util.*;


@Component
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    public List<RoomResponse> findAll() {
        List<Room> rooms = roomRepository.findAll();
        Collections.sort(rooms);
        List<RoomResponse> responseList = new ArrayList<>();
        for (Room addRoom : rooms){
            responseList.add(buildResponse(addRoom));
        }
        return responseList;
    }

    private RoomResponse buildResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setNightlyPrice(room.getNightlyPrice());
        response.setNumberPlaces(room.getNumberPlaces());
        response.setRoomNumber(room.getRoomNumber());
        response.setId(room.getId());
        return response;
    }

    public RoomResponse save(RoomRequest roomRequest) {
        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setNightlyPrice(roomRequest.getNightlyPrice());
        room.setNumberPlaces(roomRequest.getNumberPlaces());
        Room saveRoom = roomRepository.save(room);
        return buildResponse(saveRoom);
    }

    public RoomResponse findById(Long id) throws RoomNotFoundException {
        return buildResponse(findRoom(id));
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public RoomResponse updateRoom(Long id, RoomRequest newRoomRequest) throws RoomNotFoundException {
       Room room = findRoom(id);
       room.setNumberPlaces(newRoomRequest.getNumberPlaces());
       room.setNightlyPrice(newRoomRequest.getNightlyPrice());
       Room saveRoom = roomRepository.save(room);
       return buildResponse(saveRoom);
    }

    Room findRoom(Long roomId) throws RoomNotFoundException {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()){
            return optionalRoom.get();
        } else {
            throw new RoomNotFoundException("room not found");
        }
    }

    public List<AvailabilityResponse> findRoomByNumberPlaces(AvailabilityRequest newAvailabilityRequest) {
        List<AvailabilityResponse> response = new ArrayList<>();
        Date checkIn = newAvailabilityRequest.getCheckIn();
        Date checkOut = newAvailabilityRequest.getCheckOut();
        List<Room> roomList = roomRepository.findByNumberPlacesGreaterThanEqual(newAvailabilityRequest.getNumberOfPersons());
        Date now = new Date();
        for (Room room : roomList){
            List<BookedRoom> bookedRoomList = bookedRoomRepository.findAllByRoomAndCheckOutAfter(room, now);
            if (bookedRoomList == null || bookedRoomList.isEmpty()){
                AvailabilityResponse newResponse = new AvailabilityResponse();
                newResponse.setRoomNumber(room.getRoomNumber());
                response.add(newResponse);
            } else {
                for (BookedRoom bookedRoom : bookedRoomList) {
                    boolean isBooked = checkRoomIfBooked(bookedRoom, checkIn, checkOut);
                    if (isBooked) {
                        AvailabilityResponse newResponse = new AvailabilityResponse();
                        newResponse.setRoomNumber(room.getRoomNumber());
                        response.add(newResponse);
                    }

                }
            }

        }
        return response;
    }

    public boolean checkRoomIfBooked(BookedRoom bookedRoom,Date checkIn, Date checkOut){
        boolean isRoomBooked = true;
        Date checkInBookedRoom = bookedRoom.getCheckIn();
        Date checkOutBookedRoom = bookedRoom.getCheckOut();
        if(checkIn.after(checkInBookedRoom) && checkIn.before(checkOutBookedRoom)){
            isRoomBooked = false;
        }
        if (checkOut.after(checkInBookedRoom) && checkOut.before(checkOutBookedRoom)){
            isRoomBooked = false;
        }
        if (checkIn.after(checkInBookedRoom) && checkIn.before(checkOutBookedRoom)
                && checkOut.after(checkInBookedRoom) && checkOut.before(checkOutBookedRoom)){
            isRoomBooked = false;
        }
        if (checkInBookedRoom.after(checkIn) && checkInBookedRoom.before(checkOut)
                && checkOutBookedRoom.after(checkIn) && checkOutBookedRoom.before(checkOut)){
            isRoomBooked = false;
        }

        return isRoomBooked;
    }

}