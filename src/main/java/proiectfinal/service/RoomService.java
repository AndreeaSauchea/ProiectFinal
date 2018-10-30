package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.model.Room;
import proiectfinal.repository.RoomRepository;

import java.util.List;
import java.util.Optional;


@Component
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {

        return (List<Room>) roomRepository.findAll();
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> findById(long id) {
        return roomRepository.findById(id);
    }

    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }

    public Room updateRoom(Long id, Room newRoom) throws RoomNotFoundException {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setNumberPlaces(newRoom.getNumberPlaces());
            room.setNightlyPrice(newRoom.getNightlyPrice());
            room.setRoomNumber(newRoom.getRoomNumber());
            return roomRepository.save(room);
        } else {
            throw new RoomNotFoundException();
        }
    }
}