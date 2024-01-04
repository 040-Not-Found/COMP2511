package Compuer_visitorPattern1;
public abstract class Visitor {
    private boolean validated = false;

    public abstract void visit(ComputerComponent computerComponent);

    public void validate() {
        validated = true;
    }

    public boolean isValidated() {
        return validated;
    }
    
}
