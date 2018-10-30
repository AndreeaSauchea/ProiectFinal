package proiectfinal.model;


import proiectfinal.exception.RoomAlreadyBooked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History {

    Map<BookedRoom, ArrayList<Client>> bookHistory = new HashMap<>();
    static List<Room> bookedNow = new ArrayList<>();


    public static BookedRoom bookRoom(Client client, Room room) throws RoomAlreadyBooked {
        if(room.notBooked(room, bookedNow)) {
            BookedRoom bookedRoom = new BookedRoom(client, room);
            bookedNow.add(room);
            return bookedRoom;
        }
        throw new RoomAlreadyBooked();
    }


}
