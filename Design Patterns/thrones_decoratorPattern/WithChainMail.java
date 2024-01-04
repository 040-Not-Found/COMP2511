package thrones_decoratorPattern;

public class WithChainMail extends CharactorDecorator {

    public WithChainMail(Character character) {
        super(character);
    }

    @Override
    public void damage(int points) {
        super.getCharacterBase().damage(Math.floorDiv(points, 2));
    }
}
