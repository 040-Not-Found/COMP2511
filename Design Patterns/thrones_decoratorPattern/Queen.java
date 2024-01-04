package thrones_decoratorPattern;

import java.util.Random;

/**
 * A queen can move to any square in the same column, row or diagonal as she is
 * currently on, and has a 1 in 3 chance of inflicting 12 points of damage or a
 * 2 out of 3 chance of inflicting 6 points of damage.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Queen extends CharacterBase {

    public Queen(int x, int y) {
        super(x, y);
    }

    @Override
    public void attack(Character victim) {
        if(new Random().nextInt(3) == 1) {
            victim.damage(12);
        } else if(new Random().nextInt(3) < 3) {
            victim.damage(6);
        }
        
    }

    @Override
    public boolean canMove(int dx, int dy) {
        return dy == 0 || dx == 0 || Math.abs(dx) == Math.abs(dy);
    }

}
