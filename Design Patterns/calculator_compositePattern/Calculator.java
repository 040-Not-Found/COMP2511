package calculator_compositePattern;

public class Calculator {
    public static void main(String[] args) {
        Operation o1 = new Add(new Leaf(1), new Leaf(2));
        System.out.println(o1.getExpression());
        Operation o2 = new Subtract(o1, o1);
        System.out.println(o2.getExpression());

    }
}
