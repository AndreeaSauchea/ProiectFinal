package proiectfinal;

public class Room {

    private int numberForRoom;
    private double nightlyPrice;
    private double totalPrice;
    private int numberPlaces;
    private Client client;

    public Room (int numberForRoom){
        this.numberForRoom = numberForRoom;
        calculateTotalPrice();
    }

    public int getNumberForRoom() {
        return numberForRoom;
    }

    public void setNumberForRoom(int numberForRoom) {
        this.numberForRoom = numberForRoom;
    }

    public double getNightlyPrice() {
        return nightlyPrice;
    }

    public void setNightlyPrice(double nightlyPrice) {
        this.nightlyPrice = nightlyPrice;
    }

    public void calculateTotalPrice (){
        this.totalPrice = nightlyPrice * client.getDuration();
    }

    public int getNumberPlaces() {
        return numberPlaces;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setNumberPlaces(int numberPlaces) {
        this.numberPlaces = numberPlaces;
    }
}
