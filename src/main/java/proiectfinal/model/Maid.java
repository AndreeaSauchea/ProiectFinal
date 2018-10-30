package proiectfinal.model;

import java.util.Set;

public class Maid extends Human {

    private Set<Room> rooms;

    public Maid(String name, String forename, String cnp){
        super(name, forename, cnp);
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public String clean(){
        return "My rooms are cleaned";
    }
}
