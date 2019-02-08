package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.controller.dto.BookedRoomRequest;
import proiectfinal.controller.dto.BookedRoomResponse;
import proiectfinal.controller.dto.ClientHistoryResponse;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.service.BookedRoomService;

import java.util.List;

@RestController
@CrossOrigin
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;

    @GetMapping("/bookedrooms/history")
    public List<ClientHistoryResponse> findHistoryClients(){
        return bookedRoomService.findHistoryClients();
    }

    @GetMapping("/bookedrooms")
    public List<BookedRoomResponse> findBookedRooms() {
        return bookedRoomService.findAll();
    }

    @PostMapping("/bookedrooms")
    public BookedRoomResponse saveBookedRoom(@RequestBody BookedRoomRequest bookedRoomRequest) throws RoomNotFoundException, ClientNotFoundException {
        return bookedRoomService.save(bookedRoomRequest);
    }

    @GetMapping("/bookedrooms/{id}")
    public BookedRoomResponse getBookedRoomById(@PathVariable Long id) throws BookedRoomNotFoundException {
        return bookedRoomService.findById(id);
    }

    @PutMapping("/bookedrooms/{id}")
    BookedRoomResponse updateBookedRoom(@RequestBody BookedRoomRequest newRequest, @PathVariable Long id) throws BookedRoomNotFoundException, RoomNotFoundException {
        return bookedRoomService.updateRoom(id, newRequest);
    }

    @DeleteMapping("/bookedrooms/{id}")
    void deleteBookedRoom(@PathVariable Long id) {
        bookedRoomService.deleteById(id);
    }


}
