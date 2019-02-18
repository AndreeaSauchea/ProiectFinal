package proiectfinal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiectfinal.model.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Override
    List<Room> findAll();

    Room findByRoomNumber(int number);

    List<Room> findByNumberPlacesGreaterThanEqual(int numberOfPersons);


}
