package calculator_compositePattern;

public class Leaf implements Operation{
    private double value;

    public Leaf(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String getExpression() {
        return Double.toString(value);
    }
    
}
