package Compuer_visitorPattern1;

public class ComputerVisitor extends Visitor {
    @Override
    public void visit(ComputerComponent computer) {
        computer.accept(this);
    }

    public static void main(String[] args) {
        ComputerComponent computer = new Computer("Corelli", 500);
        ComputerComponent keyboard = new Keyboard("Mechanical keyboard");
        ComputerComponent mouse = new Mouse("Bluetooth mouse");
        Visitor visitorC = new ComputerVisitor();
        Visitor visitorK = new KeyboardVisitor();
        Visitor visitorM = new MouseVisitor();

        visitorM.validate();
        visitorC.visit(computer);
        visitorK.visit(keyboard);
        visitorM.visit(mouse);
    }



}