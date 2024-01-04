package restaurant_strategyPattern;

public class DiscountCost implements CostBehaviour {

    @Override
    public double getCost(Boolean isMember) {
        if (isMember) {
            return 0.85;
        }
        return 1;
    }

    @Override
    public double getModifier() {
        return 1;
    }
    
}
