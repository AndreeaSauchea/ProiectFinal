package proiectfinal.repository;

import org.springframework.data.repository.CrudRepository;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Client;
import proiectfinal.model.Room;

import java.util.Date;
import java.util.List;

public interface BookedRoomRepository extends CrudRepository<BookedRoom, Long> {

    @Override
    List<BookedRoom> findAll();

    BookedRoom findFirstByRoomAndCheckOutAfterOrderByCheckOutDesc(Room room, Date today);

    BookedRoom findFirstByClientAndCheckOutAfterOrderByCheckOutDesc(Client client, Date today);

    BookedRoom findByRoom(Room room);
}
