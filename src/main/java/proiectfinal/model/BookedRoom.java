package proiectfinal.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bookedrooms")
 public class BookedRoom {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "bookedrooms_services",
            joinColumns = { @JoinColumn(name = "bookedroom_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") })
    private List<Service> services;

    private double totalPrice;

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

    public void calculateTotalPrice (){
        this.totalPrice = room.getNightlyPrice() * client.getDuration();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalServicePrices() {
        return totalServicePrices;
    }

    public void setTotalServicePrices(double totalServicePrices) {
        this.totalServicePrices = totalServicePrices;
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
