package Compuer_visitorPattern2;
public class Computer implements ComputerComponent {

    private String name;
    private int memory;

    public Computer(String name, int memory) {
        this.name = name;
        this.memory = memory;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public int getMemory() {
        return memory;
    }

    @Override
    public void accept(ComputerVisitor visitor) {
        if (visitor.isValidated())
            System.out.println("Looking at computer " + toString() + " with memory " + getMemory() + " GB");
    }
    

}