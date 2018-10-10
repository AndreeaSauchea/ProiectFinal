package proiectfinal;

import java.util.Date;
import java.util.List;

public class Client extends Human {

    private long duration;
    private int roomNumber;
    private List<Services> services;
    private Date checkIn;
    private Date checkOut;
    private Room room;
    private int numberFromRoom = room.getNumberForRoom();

    public Client(Date checkIn, Date checkOut, int numberFromRoom, String name, String vorname, String cnp){
        super(name, vorname, cnp);
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNumber = numberFromRoom;
        calculateDuration();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    private void calculateDuration(){
        this.duration = (this.checkOut.getTime() - this.checkIn.getTime())/(24*60*60*1000);
    }

    public long getDuration(){
        return duration;
    }

}
