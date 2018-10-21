package proiectfinal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hotel {
    public static void main(String[] args) {

        List<Client> clients = new ArrayList<>();
        List<Maid> maids = new ArrayList<>();
        Set<Room> rooms = new HashSet<>();
        Set<BookedRoom> bookedRooms = new HashSet<>();
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



        try {
            Client client1 = new Client(dateformat.parse("17/07/1989"), dateformat.parse("20/07/1989"), "Pop", "Ion",  "123456789123456");
            Client client2 = new Client(dateformat.parse("17/10/1989"), dateformat.parse("20/10/1989"), "Pope", "Ina",  "123456789483456");
            Client client3 = new Client(dateformat.parse("17/11/1989"), dateformat.parse("20/11/1989"), "Popa", "Ana",  "123456789543456");
            Client client4 = new Client(dateformat.parse("17/12/1989"), dateformat.parse("20/12/1989"), "Pip", "Maria",  "123456781223456");
            clients.add(client1);
            clients.add(client2);
            clients.add(client3);
            clients.add(client4);
        } catch (ParseException pe){
            System.out.println("Ceva nu e ok la date");
        }

        BookedRoom bookedRoom1 = new BookedRoom(clients.get(0), room1);
        BookedRoom bookedRoom2 = new BookedRoom(clients.get(1), room2);
        BookedRoom bookedRoom3 = new BookedRoom(clients.get(2), room3);
        BookedRoom bookedRoom4 = new BookedRoom(clients.get(3), room4);
        bookedRooms.add(bookedRoom1);
        bookedRooms.add(bookedRoom2);
        bookedRooms.add(bookedRoom3);
        bookedRooms.add(bookedRoom4);

        Maid maid1 = new Maid("Pop", "Irina", "28746541352123", rooms);
        Maid maid2 = new Maid("Popa", "Elena", "28749547352123", rooms);
        maids.add(maid1);
        maids.add(maid2);

        Service breackfast = new Service();
        Service dinner = new Service();
        services.add(breackfast);
        services.add(dinner);

        Factura factura1 = new Factura(bookedRoom1);

        System.out.println("Trebuie sa platiti: " + factura1.generateFactura());


    }
}
