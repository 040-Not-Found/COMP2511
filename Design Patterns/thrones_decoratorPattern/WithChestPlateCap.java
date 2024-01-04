package thrones_decoratorPattern;

public class WithChestPlateCap extends CharactorDecorator {

    public WithChestPlateCap(Character character) {
        super(character);
    }

    @Override
    public boolean canMove(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) <= 3) {
            return super.getCharacterBase().canMove(dx, dy);
        }
        return false;
    }

    @Override
    public void damage(int points) {
        super.getCharacterBase().damage(Math.min(points, 7));
    }
}
