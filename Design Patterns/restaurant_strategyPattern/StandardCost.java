package restaurant_strategyPattern;

public class StandardCost implements CostBehaviour {

    @Override
    public double getCost(Boolean isMember) {
        return 1;
    }

    @Override
    public double getModifier() {
        return 1;
    }
    
}
