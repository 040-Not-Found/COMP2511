package thrones_decoratorPattern;

public class WithHelmet extends CharactorDecorator {

    public WithHelmet(Character character) {
        super(character);
    }

    @Override
    public boolean canMove(int dx, int dy) {
        
        return super.getCharacterBase().canMove(dx, dy);
    }

    @Override
    public void damage(int points) {
        super.getCharacterBase().damage(points - 1);
    }
    
}
