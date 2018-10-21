package proiectfinal;

import java.util.List;

public class BookedRoom {

    private double totalPrice;
    private Client client;
    private Room room;
    private List<Service> services;

    public BookedRoom (Client client, Room room) {
        this.client = client;
        this.room = room;
        calculateTotalPrice();
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    private void calculateTotalPrice (){
        this.totalPrice = room.getNightlyPrice() * client.getDuration();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
