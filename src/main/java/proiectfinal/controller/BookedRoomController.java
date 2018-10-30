package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.model.BookedRoom;
import proiectfinal.service.BookedRoomService;

import java.util.List;

@RestController
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;

    @GetMapping("/bookedrooms")
    public List<BookedRoom> findBookedRooms() {

        return bookedRoomService.findAll();

    }

    @PostMapping("/bookedrooms")
    public BookedRoom saveBookedRoom(@RequestBody BookedRoom newBookedRoom) {
        return bookedRoomService.save(newBookedRoom);
    }

    @GetMapping("/bookedrooms/{id}")
    public BookedRoom getBookedRoomById(@PathVariable Long id) {

        return bookedRoomService.findById(id).orElseThrow(BookedRoomNotFoundException::new);
    }

    @PutMapping("/bookedrooms/{id}")
    BookedRoom updateBookedRoom(@RequestBody BookedRoom newBookedRoom, @PathVariable Long id) {

        return bookedRoomService.updateRoom(id, newBookedRoom);
    }

    @DeleteMapping("/bookedrooms/{id}")
    void deleteBookedRoom(@PathVariable Long id) {
        bookedRoomService.deleteById(id);
    }


}
