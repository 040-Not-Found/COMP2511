package Compuer_visitorPattern1;

public class MouseVisitor extends Visitor {
    @Override
    public void visit(ComputerComponent mouse) {
        mouse.accept(this);
    }
}
