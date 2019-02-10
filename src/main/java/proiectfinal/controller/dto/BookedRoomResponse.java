package proiectfinal.controller.dto;

import proiectfinal.model.Service;

import java.util.Date;
import java.util.List;

public class BookedRoomResponse {

    private String client;
    private Long duration;
    private List<ServiceResponse> serviceList;
    private double totalPrice;
    private Date checkIn;
    private Date checkOut;


    public double getTotalPrice() {
        return totalPrice;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<ServiceResponse> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceResponse> serviceList) {
        this.serviceList = serviceList;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
