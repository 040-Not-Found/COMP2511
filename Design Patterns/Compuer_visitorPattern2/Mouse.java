package Compuer_visitorPattern2;

public class Mouse implements ComputerComponent {
    private String name;

    public Mouse(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(ComputerVisitor visitor) {
        if (visitor.isValidated())
            System.out.println("Looking at mouse " + toString() + ".");
    }
    
}