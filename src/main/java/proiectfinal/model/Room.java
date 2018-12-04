package proiectfinal.model;



import java.util.Objects;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "room")
    private List<BookedRoom> bookedRooms;

    private int roomNumber;
    private double nightlyPrice;
    private int numberPlaces;

    public Room(){}


    public Room (int roomNumber, double nightlyPrice){
        this.roomNumber = roomNumber;
        this.nightlyPrice = nightlyPrice;
    }

    public boolean notBooked(Room room, List<Room> booked){
        for (Room room1 : booked){
            if(room.equals(room1)){
                return false;
            }
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getNightlyPrice() {
        return nightlyPrice;
    }

    public void setNightlyPrice(double nightlyPrice) {
        this.nightlyPrice = nightlyPrice;
    }


    public int getNumberPlaces() {
        return numberPlaces;
    }


    public void setNumberPlaces(int numberPlaces) {
        this.numberPlaces = numberPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
