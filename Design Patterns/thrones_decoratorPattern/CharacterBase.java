package thrones_decoratorPattern;

/**
 * A character in the simple grid game example.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class CharacterBase extends Character {
    
    public CharacterBase(int x, int y) {
        super(x, y);
    }
    
    @Override
    public abstract void attack(Character victim);

    @Override
    public abstract boolean canMove(int dx, int dy);
}
