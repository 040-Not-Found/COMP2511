package calculator_compositePattern;

public class Add implements Operation {
    private double value;
    private Operation leftOperation;
    private Operation rightOperation;

    public Add(Operation leftOperation, Operation rightOperation) {
        value = leftOperation.getValue() + rightOperation.getValue();
        this.leftOperation = leftOperation;
        this.rightOperation = rightOperation;
    }

    @Override
    public double getValue() {
        
        return value;
    }

    @Override
    public String getExpression() {
        return leftOperation.getExpression() + " + " + rightOperation.getExpression() + " = " + value;
    }
    
}
