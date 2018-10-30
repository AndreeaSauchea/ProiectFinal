package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.model.Room;
import proiectfinal.repository.RoomRepository;

import java.util.List;


@Component
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {

        List<Room> rooms = (List<Room>) roomRepository.findAll();

        return rooms;
    }
}