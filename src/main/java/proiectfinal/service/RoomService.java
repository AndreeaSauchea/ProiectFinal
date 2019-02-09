package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.RoomRequest;
import proiectfinal.controller.dto.RoomResponse;
import proiectfinal.exception.RoomNotFoundException;
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
    @Autowired
    private ClientRepository clientRepository;

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
}