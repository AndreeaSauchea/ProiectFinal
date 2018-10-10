package proiectfinal;

import java.util.List;

public class Factura {

   private Client client;
    private Room room;
    private double totalServicePrices = 0;
    private double total;

    public Factura(Client client){
        this.client = client;
    }

    public double calculateTotalSevicePrices(){
        List<Services> services1 = client.getServices();
        for (Services services : services1){
            totalServicePrices += services.getServicePrice();
        }
        return totalServicePrices;
    }

    public double generateFactura() {
       this.total = calculateTotalSevicePrices();
       this.total += room.getTotalPrice();
       return this.total;

    }
}
