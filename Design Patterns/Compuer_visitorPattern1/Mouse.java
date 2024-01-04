package Compuer_visitorPattern1;

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
    public void accept(Visitor visitor) {
        if (visitor.isValidated())
            System.out.println("Looking at mouse " + toString() + ".");
    }
    
}