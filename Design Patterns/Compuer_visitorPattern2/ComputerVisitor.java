package Compuer_visitorPattern2;

public class ComputerVisitor {
    private boolean validated = false;

    public void visit(ComputerComponent computerComponent) {
        computerComponent.accept(this);
    }

    public boolean isValidated() {
        return validated;
    }

    public void validate() {
        this.validated = true;
    }

    public static void main(String[] args) {
        ComputerComponent computer = new Computer("Corelli", 500);
        ComputerComponent keyboard = new Keyboard("Mechanical keyboard");
        ComputerComponent mouse = new Mouse("Bluetooth mouse");
        ComputerVisitor visitor = new ComputerVisitor();
        visitor.validate();
        visitor.visit(computer);
        visitor.visit(keyboard);
        visitor.visit(mouse);

    }



}