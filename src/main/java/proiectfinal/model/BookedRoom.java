package proiectfinal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bookedrooms")
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "bookedrooms_services",
            joinColumns = {@JoinColumn(name = "bookedroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private List<Service> services;

    private Date checkIn;
    @Column(name = "check_out")
    private Date checkOut;

    public Long getDuration() {
        return ((this.checkOut.getTime() - this.checkIn.getTime()) / (24 * 60 * 60 * 1000));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        if (this.services == null) {
            this.services = new ArrayList<>();
        }
        this.services.add(service);
    }

    public void removeService(Service service) {
        if (this.services == null) {
            return;
        }
        this.services.remove(service);
    }

    public double getTotalPrice() {
        if (room == null) {
            return 0;
        }
        return room.getNightlyPrice() * this.getDuration();
    }

    public double getTotalServicePrices() {
        double totalServicePrices = 0;
        if (this.services == null || this.services.isEmpty()) {
            return totalServicePrices;
        }
        for (Service service : this.services) {
            totalServicePrices += service.getServicePrice();
        }
        return totalServicePrices;
    }

}
