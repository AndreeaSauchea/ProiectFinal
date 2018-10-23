package proiectfinal;

import java.util.List;

public class Factura {

    private BookedRoom room;
    private double totalServicePrices = 0;
    private double total;

    public Factura(BookedRoom room){
        this.room = room;
    }

    private double calculateTotalSevicePrices(){
        List<Service> service1 = room.getServices();
        if (service1 == null){
            return 0;
        } else {
            for (Service service : service1) {
                totalServicePrices += service.getServicePrice();
            }
            return totalServicePrices;
        }
    }

    public double generateFactura() {
       this.total = calculateTotalSevicePrices();
       this.total += room.getTotalPrice();
       return this.total;

    }
}
