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

    BookedRoom findFirstByRoomAndCheckInBeforeAndCheckOutAfterOrderByCheckOutDesc(Room room, Date today, Date today1);

    BookedRoom findFirstByClientAndCheckOutAfterOrderByCheckOutDesc(Client client, Date today);

    BookedRoom findByRoom(Room room);

    List<BookedRoom> findAllByRoom(Room room);

    List<BookedRoom> findAllByRoomAndCheckOutAfter(Room room, Date today);

    List<BookedRoom> findByCheckInBeforeAndCheckOutAfter(Date today,Date today1);

    BookedRoom findByRoomAndCheckInBeforeAndCheckOutAfter(Room room, Date today, Date today1);
}
