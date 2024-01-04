package thrones_templatePattern;

/**
 * A king can move one square in any direction (including diagonally), and
 * always causes 8 points of damage when attacking.
 *
 * @author Robert Clifton-Everest
 *
 */
public class King extends Character {

    public King(int x, int y) {
        super(x, y);
    }

    @Override
    public void attack(Character victim) {
        victim.damage(8);
        
    }

    @Override
    public boolean canMove(int dx, int dy) {
        return !(Math.abs(dx) > 1 || Math.abs(dy) > 1);
    }

}
