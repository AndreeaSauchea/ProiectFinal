package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.model.BookedRoom;
import proiectfinal.repository.BookedRoomRepository;

import java.util.List;
import java.util.Optional;

/*
BookedRoom are lista in el, la curs ai zis ca fiecare lista e o coloana, cum fac?
(o sa mai caut si pe Google)
 */

@Component
public class BookedRoomService {
    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    public List<BookedRoom> findAll() {

        return (List<BookedRoom>) bookedRoomRepository.findAll();
    }

    public BookedRoom save(BookedRoom bookedRoom) {
        return bookedRoomRepository.save(bookedRoom);
    }

    public Optional<BookedRoom> findById(long id) {
        return bookedRoomRepository.findById(id);
    }

    public void deleteById(long id) {
        bookedRoomRepository.deleteById(id);
    }

    public BookedRoom updateRoom(Long id, BookedRoom newBookedRoom) throws BookedRoomNotFoundException {
        Optional<BookedRoom> optionalBookedRoom = bookedRoomRepository.findById(id);
        if (optionalBookedRoom.isPresent()) {
            BookedRoom bookedRoom = optionalBookedRoom.get();
            bookedRoom.setClient(newBookedRoom.getClient());
            bookedRoom.setRoom(newBookedRoom.getRoom());
            return bookedRoomRepository.save(bookedRoom);
        } else {
            throw new BookedRoomNotFoundException();
        }
    }
}
