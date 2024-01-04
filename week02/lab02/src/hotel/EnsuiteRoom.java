package hotel;

public class EnsuiteRoom extends Room {

    @Override
    public void toJSON() {
        super.toJSON();
        room.put("type", "ensuite");
    }

    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to your beautiful ensuite room which overlooks the Sydney harbour. Enjoy your stay");
    }
}