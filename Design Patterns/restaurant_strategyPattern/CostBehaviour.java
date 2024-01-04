package restaurant_strategyPattern;

public interface CostBehaviour {
    public double getCost(Boolean isMember);

    public double getModifier();
}
