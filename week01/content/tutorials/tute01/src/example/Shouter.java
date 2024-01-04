package example;

public class Shouter {
    // field to store our message
    private String message;
    // Constructor
    public Shouter(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void shout() {
        System.out.println(message.toUpperCase());
    }

    public static void main(String[] args) {
        Shouter shouter = new Shouter("message");
        System.out.println(shouter.getMessage());
        shouter.setMessage("new message");
        System.out.println(shouter.message);
    }
}
