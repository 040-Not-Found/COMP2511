package Compuer_visitorPattern1;

public class KeyboardVisitor extends Visitor {

    @Override
    public void visit(ComputerComponent keyboard) {
        keyboard.accept(this);
    }
    
}
