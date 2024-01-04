package restaurant_strategyPattern;

public class HoildayCost implements CostBehaviour {

    @Override
    public double getCost(Boolean isMember) {
        return 1.15;
    }

    @Override
    public double getModifier() {
        return 1.15;
    }
    
}
