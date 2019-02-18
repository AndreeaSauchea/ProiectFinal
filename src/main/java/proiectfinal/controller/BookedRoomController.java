package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.controller.dto.BookedRoomRequest;
import proiectfinal.controller.dto.BookedRoomResponse;
import proiectfinal.controller.dto.ClientHistoryResponse;
import proiectfinal.exception.*;
import proiectfinal.service.BookedRoomService;

import java.util.List;

@RestController
@CrossOrigin
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;

    @GetMapping("/bookedrooms/room/{roomId}")
    public BookedRoomResponse findBookedRoomByRoom(@PathVariable Long roomId) throws BookedRoomNotFoundException, RoomNotFoundException, ClientNotFoundException, NoFirstNameException, NoLastNameException, NoServicesOnThisListException {
        return bookedRoomService.findBookedRoomByRoom(roomId);
    }

    @GetMapping("/bookedrooms/client/{clientId}")
    public BookedRoomResponse findBookedRoomByClient(@PathVariable Long clientId) throws BookedRoomNotFoundException {
        return bookedRoomService.findBookedRoomByClient(clientId);
    }

    @GetMapping("/bookedrooms/history")
    public List<ClientHistoryResponse> findHistoryClients() throws BookedRoomNotFoundException, ClientNotFoundException, RoomNotFoundException, CheckOutNotFoundException, CheckInNotFoundException, NoLastNameException, NoFirstNameException, NoRoomNumberException, NoRoomIdException, HotelIsNotBookedException {
        return bookedRoomService.findHistoryClients();
    }

    @GetMapping("/bookedrooms")
    public List<BookedRoomResponse> findBookedRooms() {
        return bookedRoomService.findAll();
    }

    @PostMapping("/bookedrooms")
    public BookedRoomResponse saveBookedRoom(@RequestBody BookedRoomRequest bookedRoomRequest) throws RoomNotFoundException, ClientNotFoundException, BookedRoomNotSavedException {
        return bookedRoomService.save(bookedRoomRequest);
    }

    @GetMapping("/bookedrooms/{id}")
    public BookedRoomResponse getBookedRoomById(@PathVariable Long id) throws BookedRoomNotFoundException {
        return bookedRoomService.findById(id);
    }

    @PutMapping("/bookedrooms/{id}")
    public BookedRoomResponse updateBookedRoom(@RequestBody BookedRoomRequest newRequest, @PathVariable Long id) throws BookedRoomNotFoundException, RoomNotFoundException, NoRoomNumberException {
        return bookedRoomService.updateRoom(id, newRequest);
    }

    @DeleteMapping("/bookedrooms/{id}")
    public void deleteBookedRoom(@PathVariable Long id) {
        bookedRoomService.deleteById(id);
    }

    @PutMapping("bookedroom/{roomId}/activity/{activityId}/add")
    public void addBookedRoomActivity(@PathVariable Long roomId, @PathVariable Long activityId){
        bookedRoomService.addBookedRoomActivity(roomId,activityId);
    }

    @PutMapping("bookedroom/{roomId}/activity/{activityId}/remove")
    public void removeBookedRoomActivity(@PathVariable Long roomId, @PathVariable Long activityId){
        bookedRoomService.removeBookedRoomActivity(roomId,activityId);
    }

}
