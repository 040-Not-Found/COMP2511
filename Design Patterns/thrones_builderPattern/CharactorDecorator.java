package thrones_builderPattern;

public abstract class CharactorDecorator extends Character {

    private Character characterBase;

    public CharactorDecorator(Character character) {
        super(character.getX(), character.getY());
        this.characterBase = character;
    }

    public Character getCharacterBase() {
        return characterBase;
    }

    @Override
    public abstract void damage(int points);

    @Override
    public boolean canMove(int dx, int dy) {
        return characterBase.canMove(dx, dy);
    }

    @Override
    public void attack(Character character) {
    }

    @Override
    public String toString() {
        return characterBase.getClass().getSimpleName() + super.toString();
    }
    
}
