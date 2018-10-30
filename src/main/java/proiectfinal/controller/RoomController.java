package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.model.Room;
import proiectfinal.service.RoomService;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public List<Room> findRooms() {

        return roomService.findAll();

    }

    @PostMapping("/rooms")
    public Room saveRoom(@RequestBody Room newRoom) {
        return roomService.save(newRoom);
    }

    @GetMapping("/rooms/{id}")
    public Room getRoomById(@PathVariable Long id) {

        return roomService.findById(id).orElseThrow(RoomNotFoundException::new);
    }

    @PutMapping("/rooms/{id}")
    Room updateRoom(@RequestBody Room newRoom, @PathVariable Long id) {

        return roomService.updateRoom(id, newRoom);
    }

    @DeleteMapping("/rooms/{id}")
    void deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
    }


}
