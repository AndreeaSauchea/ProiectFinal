package proiectfinal.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bookedRooms")
 public class BookedRoom {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     private double totalPrice;
    private Client client;
    private Room room;
    private List<Service> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private double totalServicePrices;

   public BookedRoom() {
    }

    public BookedRoom (Client client, Room room) {
        this.client = client;
        this.room = room;
        calculateTotalPrice();
    }

     public Client getClient() {
         return client;
     }

     public void setClient(Client client) {
         this.client = client;
     }

     public Room getRoom() {
         return room;
     }

     public void setRoom(Room room) {
         this.room = room;
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
