package proiectfinal.controller.dto;

import java.util.Date;
import java.util.List;

public class BookedRoomRequest {
    private String cnp;
    private int room;
    private Date checkIn;
    private Date checkOut;
    private List<Long> servicesId;

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public List<Long> getServicesId() {
        return servicesId;
    }

    public void setServicesId(List<Long> servicesId) {
        this.servicesId = servicesId;
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
}
