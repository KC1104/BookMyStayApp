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
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Room Initialized");

        SingleRoom single = new SingleRoom(1, 250, 1500, 5);
        DoubleRoom doubleRoom = new DoubleRoom(2, 400, 2500, 3);
        SuiteRoom suite = new SuiteRoom(3, 750, 5000, 2);

        single.displayRoomDetails("Single Room");
        doubleRoom.displayRoomDetails("Double Room");
        suite.displayRoomDetails("Suite Room");

    }
}
