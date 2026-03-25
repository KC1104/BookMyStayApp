import java.security.Provider;
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

    public String allocateRoom(Reservation reservation, RoomInventory roomInventory) {
        String type = reservation.getRoomType();
        Map<String, Integer> availability = roomInventory.getRoomAvailability();

        if (availability.get(type) > 0) {

            int count = roomCounters.get(type) + 1;
            roomCounters.put(type, count);

            String roomId = type + "-" + count;

            roomInventory.updateAvailability(type, availability.get(type) - 1);

            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: " + roomId);

            return roomId; // ✅ RETURN ID
        } else {
            System.out.println("No rooms available for " + type);
            return null;
        }
    }
}

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost){
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager(){
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service){
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateCost(String reservationId){
        double total = 0;

        List<AddOnService> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}
class BookingReportService {

    public void generateReport(BookingHistory history) {

        System.out.println("\nBooking History and Reporting\n");

        System.out.println("Booking History Report");

        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();
        BookingHistory history = new BookingHistory();
        AddOnServiceManager addOnManager = new AddOnServiceManager();

        // Add booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process bookings
        while (queue.hasPendingRequests()) {
            Reservation request = queue.getNextRequest();

            String roomId = allocationService.allocateRoom(request, inventory);

            if (roomId != null) {
                history.addReservation(request);

                // Add-ons only for first booking (example)
                if (roomId.equals("Single-1")) {
                    addOnManager.addService(roomId, new AddOnService("Food", 500));
                    addOnManager.addService(roomId, new AddOnService("Spa", 1000));
                }
            }
        }

        // Add-On Output
        System.out.println("\nAdd-On Service Selection");
        System.out.println("Reservation ID: Single-1");
        double totalCost = addOnManager.calculateCost("Single-1");
        System.out.println("Total Add-On Cost: " + totalCost);

        // Report Output
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}