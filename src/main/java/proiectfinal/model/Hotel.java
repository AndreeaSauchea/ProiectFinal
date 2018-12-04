package proiectfinal.model;

import proiectfinal.exception.RoomAlreadyBooked;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Hotel {
    public static void main(String[] args) {

        List<Client> clients = new ArrayList<>();
        Set<Room> rooms = new HashSet<>();
        List<Service> services = new ArrayList<>();


        Room room1 = new Room(1,50);
        Room room2 = new Room(2,50);
        Room room3 = new Room(3,50);
        Room room4 = new Room(4,50);
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);

        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");



            Client client1 = new Client( "Pop", "Ion",  "123456789123456");
            Client client2 = new Client( "Pope", "Ina",  "123456789483456");
            Client client3 = new Client( "Popa", "Ana",  "123456789543456");
            Client client4 = new Client( "Pip", "Maria",  "123456781223456");
            clients.add(client1);
            clients.add(client2);
            clients.add(client3);
            clients.add(client4);


        BookedRoom bookedRoom1 = new BookedRoom();
        BookedRoom bookedRoom2 = new BookedRoom();
        BookedRoom bookedRoom3 = new BookedRoom();
        BookedRoom bookedRoom4 = new BookedRoom();

        try{
            bookedRoom1 = History.bookRoom(clients.get(0), room1);
            bookedRoom2 = History.bookRoom(clients.get(1), room2);
            bookedRoom3 = History.bookRoom(clients.get(2), room3);
            bookedRoom4 = History.bookRoom(clients.get(3), room4);
        }catch (RoomAlreadyBooked rab){
            System.out.println("You are trying to book a room that is already booked.");
        }


        Service breackfast = new Service("breackfast", 150);
        Service dinner = new Service("dinner", 150);
        services.add(breackfast);
        services.add(dinner);

        bookedRoom1.setServices(services);

        Factura factura1 = new Factura(bookedRoom1);
        Factura factura2 = new Factura(bookedRoom2);
        Factura factura3 = new Factura(bookedRoom3);
        Factura factura4 = new Factura(bookedRoom4);


        System.out.println("Trebuie sa platiti: " + factura1.generateFactura());
        System.out.println("Trebuie sa platiti: " + factura2.generateFactura());
        System.out.println("Trebuie sa platiti: " + factura3.generateFactura());
        System.out.println("Trebuie sa platiti: " + factura4.generateFactura());


    }
}
