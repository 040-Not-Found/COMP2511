package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Hotel {
    private String name;
    private List<Room> rooms = new ArrayList<Room>();

    /**
     * Makes a booking in any available room with the given preferences.
     * 
     * @param arrival - arrival
     * @param departure - arrival
     * @param standard - does the client want a standard room?
     * @param ensuite - does the client want an ensuite room?
     * @param penthouse - does the client want a penthouse room?
     * @return If there were no available rooms for the given preferences, returns false.
     * Returns true if the booking was successful
     */
    public boolean makeBooking(LocalDate arrival, LocalDate departure, boolean standard, boolean ensuite, boolean penthouse) {
        if (standard) {
            for (Room room : rooms) {
                if (room instanceof StandardRoom && room.book(arrival, departure) != null) {
                    return true;
                }
            }
        }
        if (ensuite) {
            for (Room room : rooms) {
                if (room instanceof EnsuiteRoom && room.book(arrival, departure) != null) {
                    return true;
                }
            }
        }
        if (penthouse) {
            for (Room room : rooms) {
                if (room instanceof PenthouseRoom && room.book(arrival, departure) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return A JSON Object of the form:
     * { "name": name, "rooms": [ each room as a JSON object, in order of creation ]}
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        ArrayList<JSONObject> rooms = new ArrayList<>();
        for (Room room : this.rooms) {
            rooms.add(room.toJSON());
        }
        jsonObject.put("rooms", rooms);

        return jsonObject;
    }


    public static void main(String[] args) {
        /**
         * some tests
         */
        
        Hotel hotel = new Hotel();
        hotel.name = "芜湖";
        EnsuiteRoom er = new EnsuiteRoom();
        hotel.rooms.add(er);
        StandardRoom sr = new StandardRoom();
        hotel.rooms.add(sr);
        hotel.makeBooking(LocalDate.of(2021, 9, 25), LocalDate.of(2021, 9, 28), false, true, false);
        hotel.makeBooking(LocalDate.of(2021, 9, 30), LocalDate.of(2021, 10, 3), false, true, false);
        er.printWelcomeMessage();
        hotel.makeBooking(LocalDate.now(), LocalDate.now(), true, false, false);
        sr.printWelcomeMessage();
        System.out.println(hotel.toJSON());
        // test for remove booking
        sr.removeBooking(sr.bookings.get(0));
        // test for change booking
        er.changeBooking(er.bookings.get(0), LocalDate.of(2021, 9, 27), LocalDate.of(2021, 9, 29));
        System.out.println(hotel.toJSON());
        er.changeBooking(er.bookings.get(1), LocalDate.of(2021, 10, 1), LocalDate.of(2021, 10, 5));
        System.out.println(hotel.toJSON());
        
    }   
}