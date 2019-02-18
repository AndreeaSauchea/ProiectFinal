package proiectfinal.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ClientHistoryResponse {
    private int room;
    private Long roomId;
    private String lastName;
    private String firstName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date checkIn;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date checkOut;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
