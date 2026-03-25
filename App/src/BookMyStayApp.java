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

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) { super(message); }

}

class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory roomInventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Normalize input (case-insensitive)
        roomType = roomType.substring(0,1).toUpperCase() + roomType.substring(1).toLowerCase();

        Map<String, Integer> availability = roomInventory.getRoomAvailability();

        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        public class BookMyStayApp {
            public static void main(String[] args) {

                System.out.println("Booking Validation");

                Scanner input = new Scanner(System.in);

                RoomInventory roomInventory = new RoomInventory();
                ReservationValidator reservationValidator = new ReservationValidator();
                BookingRequestQueue bookingRequestQueue = new BookingRequestQueue();

                try {
                    System.out.print("Enter guest name: ");
                    String guestName = input.nextLine();

                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String roomType = input.nextLine();

                    // Validate input
                    reservationValidator.validate(guestName, roomType, roomInventory);

                    // If valid → add to queue
                    bookingRequestQueue.addRequest(new Reservation(guestName, roomType));

                    System.out.println("Booking request added successfully!");

                } catch (InvalidBookingException e) {
                    System.out.println("Booking failed: " + e.getMessage());
                } finally {
                    input.close();
                }
            }
        }
    }
}