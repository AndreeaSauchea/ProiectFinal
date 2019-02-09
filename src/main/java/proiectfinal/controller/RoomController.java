package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.controller.dto.RoomRequest;
import proiectfinal.controller.dto.RoomResponse;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.service.RoomService;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public List<RoomResponse> findRooms() {
        return roomService.findAll();
    }

    @PostMapping("/rooms")
    public RoomResponse saveRoom(@RequestBody RoomRequest newRoomRequest) {
        return roomService.save(newRoomRequest);
    }

    @GetMapping("/rooms/{id}")
    public RoomResponse getRoomById(@PathVariable Long id) throws RoomNotFoundException {
        return roomService.findById(id);
    }

    @PutMapping("/rooms/{id}")
    RoomResponse updateRoom(@RequestBody RoomRequest newRoomRequest, @PathVariable Long id) throws RoomNotFoundException {
        return roomService.updateRoom(id, newRoomRequest);
    }

    @DeleteMapping("/rooms/{id}")
    void deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
    }


}
