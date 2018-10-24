package proiectfinal;

import java.util.List;
import java.util.Objects;

public class Room {

    private int roomNumber;
    private double nightlyPrice;
    private int numberPlaces;

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
