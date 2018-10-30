package proiectfinal.model;

import java.util.List;

 public class BookedRoom {

    private double totalPrice;
    private Client client;
    private Room room;
    private List<Service> services;
    private double totalServicePrices;

   public BookedRoom() {
    }

    public BookedRoom (Client client, Room room) {
        this.client = client;
        this.room = room;
        calculateTotalPrice();
    }

    public List<Service> getServices() {
        return services;
    }

     void setServices(List<Service> services) {
        this.services = services;
    }

    private void calculateTotalPrice (){
        this.totalPrice = room.getNightlyPrice() * client.getDuration();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double calculateTotalSevicePrices(){
        List<Service> service1 = getServices();
        if (service1 == null){
            return 0;
        } else {
            for (Service service : service1) {
                totalServicePrices += service.getServicePrice();
            }
            return totalServicePrices;
        }
    }

}
