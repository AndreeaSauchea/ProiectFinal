package proiectfinal;

import java.util.List;
import java.util.Set;

public class Maid extends Human {

    private List<Room> rooms;

    public Maid(String name, String vorname, String cnp, Set<Room> rooms){
        super(name, vorname, cnp);
        this.getRooms();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String clean(){
        return "My rooms are cleaned";
    }
}
