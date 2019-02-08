package proiectfinal.repository;

import org.springframework.data.repository.CrudRepository;
import proiectfinal.model.BookedRoom;

import java.util.List;

public interface BookedRoomRepository extends CrudRepository<BookedRoom, Long> {

    @Override
    List<BookedRoom> findAll();
}
