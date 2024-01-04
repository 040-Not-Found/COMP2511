package hotel;

import java.time.LocalDate;
import org.json.JSONObject;

public class Booking {
    
    LocalDate arrival;
    LocalDate departure;

    public Booking(LocalDate arrival, LocalDate departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    /**
     * @return a JSONObject of the form {"arrival": arrival, "departure": departure}
     */
    public JSONObject toJSON() {
        JSONObject booking = new JSONObject();
        booking.put("arrival", arrival);
        booking.put("departure", departure);
        return booking;
    }

    /**
     * equal function
     * @return true if bookings are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Booking other = (Booking) obj;
        if (arrival == null) {
            if (other.arrival != null)
                return false;
        } else if (!arrival.equals(other.arrival))
            return false;
        if (departure == null) {
            if (other.departure != null)
                return false;
        } else if (!departure.equals(other.departure))
            return false;
        return true;
    }

    /**
     * Check overlaps
     * @param start
     * @param end
     * @return true if two booking are overlap
     */
    public boolean overlaps(LocalDate start, LocalDate end) {
        if ((start.isAfter(this.arrival) && start.isBefore(this.departure))
            ||(end.isAfter(this.departure) && end.isBefore(this.arrival))) {
            return true;
        }
        return false;
    }

}