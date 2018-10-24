package proiectfinal;

import java.util.List;

public class Factura {

    private BookedRoom room;
    private double totalServicePrices;
    private double total;

    public Factura(BookedRoom room){
        this.room = room;
    }


    public double generateFactura() {
       this.total = room.calculateTotalSevicePrices();
       this.total += room.getTotalPrice();
       return this.total;

    }
}
