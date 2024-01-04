package thrones_decoratorPattern;

import java.util.Random;

/**
 * A dragon can only move up, down, left or right, and has a 1 in 6 chance of
 * inflicting 20 points of damage.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dragon extends CharacterBase {

    public Dragon(int x, int y) {
        super(x, y);
    }

    @Override
    public void attack(Character victim) {
        if(new Random().nextInt(6) == 1) {
            victim.damage(20);
        }
    }

    @Override
    public boolean canMove(int dx, int dy) {
        return (Math.abs(dx) + Math.abs(dy) <= 1);
    }

}
