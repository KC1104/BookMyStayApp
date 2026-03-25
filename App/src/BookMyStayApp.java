import java.util.*;

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

class RoomAllocationService {
    private Map<String, Integer> roomCounters;

    public RoomAllocationService() {
        roomCounters = new HashMap<>();
        roomCounters.put("Single", 0);
        roomCounters.put("Double", 0);
        roomCounters.put("Suite", 0);
    }

    public void allocateRoom(Reservation reservation, RoomInventory roomInventory) {
        String type = reservation.getRoomType();

        Map<String, Integer> availability = roomInventory.getRoomAvailability();

        if (availability.get(type) > 0) {

            // Increment counter
            int count = roomCounters.get(type) + 1;
            roomCounters.put(type, count);

            // Generate Room ID
            String roomId = type + "-" + count;

            // Reduce availability
            roomInventory.updateAvailability(type, availability.get(type) - 1);

            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: " + roomId);
        } else {
            System.out.println("No rooms available for " + type);
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        BookingRequestQueue bookingRequestQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingRequestQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingRequestQueue.addRequest(new Reservation("Subha", "Single"));
        bookingRequestQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        while (bookingRequestQueue.hasPendingRequests()) {
            Reservation request = bookingRequestQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}
