import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

abstract class Room{
    protected int numberOfBeds;
    protected int sqaureFeet;
    protected int pricePerNight;
    protected int availableRooms;

    public Room(int numberOfBeds, int sqaureFeet, int pricePerNight,int availableRooms){
        this.numberOfBeds = numberOfBeds;
        this.sqaureFeet = sqaureFeet;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    public void displayRoomDetails(String roomName){
        System.out.println(roomName+":");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + sqaureFeet);
        System.out.println("Price per Night: " + pricePerNight);
        System.out.println("Available Rooms: " + availableRooms);
        System.out.println();
    }
}

class RoomInventory{
    private Map<String, Integer> roomAvailability;
    public RoomInventory(){
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory(){
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability(){
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count){
        roomAvailability.put(roomType, count);
    }
}
class SingleRoom extends Room{
    public SingleRoom(int numberOfBeds, int sqaureFeet, int pricePerNight,int availableRooms){
        super(numberOfBeds, sqaureFeet, pricePerNight,availableRooms);
    }
}

class DoubleRoom extends Room{
    public DoubleRoom(int numberOfBeds, int sqaureFeet, int pricePerNight,int availableRooms){
        super(numberOfBeds, sqaureFeet, pricePerNight,availableRooms);
    }
}

class SuiteRoom extends Room{
    public SuiteRoom(int numberOfBeds, int sqaureFeet, int pricePerNight, int availableRooms){
        super(numberOfBeds, sqaureFeet, pricePerNight,availableRooms);
    }
}

class RoomSearchService {
    public void searchAvailableRooms(RoomInventory roomInventory,
                                     Room singleRoom,
                                     Room doubleRoom,
                                     Room suiteRoom) {

        System.out.println("\nRoom Search\n");

        Map<String, Integer> availability = roomInventory.getRoomAvailability();

        if (availability.get("Single") > 0) {
            singleRoom.displayRoomDetails("Single Room");
        }

        if (availability.get("Double") > 0) {
            doubleRoom.displayRoomDetails("Double Room");
        }

        if (availability.get("Suite") > 0) {
            suiteRoom.displayRoomDetails("Suite Room");
        }
    }
}

class Reservation{
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType){
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {  return roomType; }
}

class BookingRequestQueue{
    private Queue<Reservation> requestQueue;
    public BookingRequestQueue(){ requestQueue = new LinkedList<>(); }

    public void addRequest(Reservation reservation){
        requestQueue.offer(reservation);}

    public Reservation getNextRequest(){return requestQueue.poll();}

    public boolean hasPendingRequests(){
        return !requestQueue.isEmpty();
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking Request Queue");

        BookingRequestQueue bookingRequestQueue = new BookingRequestQueue();

        Reservation reservation = new Reservation("Abhi", "Single");
        Reservation reservation2 = new Reservation("Subha", "Double");
        Reservation reservation3 = new Reservation("Vanmathi", "Suite");

        bookingRequestQueue.addRequest(reservation);
        bookingRequestQueue.addRequest(reservation2);
        bookingRequestQueue.addRequest(reservation3);
    }
}
