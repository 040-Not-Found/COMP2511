package restaurant_strategyPattern;

public class HappyHourCost implements CostBehaviour {

    @Override
    public double getCost(Boolean isMember) {
        if (isMember) {
            return 0.6;
        }
        return 0.7;
    }

    @Override
    public double getModifier() {
        return 0.7;
    }
    
}
