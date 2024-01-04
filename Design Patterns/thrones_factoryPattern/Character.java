package thrones_factoryPattern;

import java.util.Random;

/**
 * A character in the simple grid game example.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class Character extends CharacterFactory {
    private int healthPoints;

    private int x, y;

    public Character() {
        healthPoints = 100;
        this.x = new Random().nextInt(5);
        this.y = new Random().nextInt(5);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Cause this character the given amount of damage.
     *
     * @param points
     */
    public void damage(int points) {
        healthPoints -= points;
    }

    /**
     * This character attacks the given victim, causing them damage according to
     * their rules.
     *
     * @param victim
     */
    public abstract void attack(Character victim);

    /**
     * Can this character move by the given amount along the x and y axes.
     *
     * @param x
     * @param y
     * @return True if they can move by that amount, false otherwise
     */
    public abstract boolean canMove(int dx, int dy);
}
