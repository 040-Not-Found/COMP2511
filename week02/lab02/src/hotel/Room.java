package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class Room {
    
    private List<Booking> bookings = new ArrayList<Booking>();    
    /**
     * Checks if the room is not booked out during the given time.
     * If so, creates a booking for the room at that time.
     * @param arrival
     * @param departure
     * @return The booking object if the booking succeeded, null if failed
     */
    public Booking book(LocalDate arrival, LocalDate departure) {
        for (Booking booking : bookings) {
            if (booking.overlaps(arrival, departure)) {
                return null;
            }
        }

        Booking booking = new Booking(arrival, departure);
        bookings.add(booking);
        return booking;
    }

    /**
     * @return A JSON object of the form:
     * {
     *  "bookings": [ each booking as a JSON object, in order of creation ],
     *  "type": the type of the room (standard, ensuite, penthouse)
     * }
     */
    public JSONObject toJSON() {
        JSONObject room = new JSONObject();
        ArrayList<JSONObject> bookings = new ArrayList<>();
        for (Booking booking : this.bookings) {
            bookings.add(booking.toJSON());
        }
        room.put("bookings", bookings);
        
        if (this instanceof EnsuiteRoom) {
            room.put("type", "ensuite");
        } else if (this instanceof StandardRoom) {
            room.put("type", "standard");
        } else if (this instanceof PenthouseRoom) {
            room.put("type", "penthouse");
        }
        
        return room;
    }
    
    /**
     * Removes the given booking from the list of bookings.
     * If the booking isn't in the list of bookings, does nothing.
     * @param booking
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Changes the given booking to be from the updated arrival date to the updated departure date,
     * if and only if the room is available between the updated times.
     * Note that a room being unavailable does NOT include the time of the original booking.
     * 
     * If the room is not available between the updated times, the change fails
     * and the room's booking list remains as it was before the function was called unchanged.
     * @param booking
     * @param arrival
     * @param departure
     */
    public Booking changeBooking(Booking booking, LocalDate arrival, LocalDate departure) {
        for (Booking b : bookings) {
            if (!b.equals(booking) && b.overlaps(arrival, departure)) {
                return booking;
            }
        }
        removeBooking(booking);
        return this.book(arrival, departure);
    }

    /**
     * Prints a welcome message to the guest staying in the room.
     */
    public abstract void printWelcomeMessage();

}